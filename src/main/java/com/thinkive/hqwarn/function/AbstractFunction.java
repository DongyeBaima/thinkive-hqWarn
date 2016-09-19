package com.thinkive.hqwarn.function;

import static com.thinkive.hqwarn.util.FuncHelper.PARAM_STOCKCODELIST;
import static com.thinkive.hqwarn.util.FuncHelper.PARAM_USERID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinkive.hqwarn.bean.UserInfo;
import com.thinkive.hqwarn.bean.WarnInfo;
import com.thinkive.hqwarn.cache.ServerState;
import com.thinkive.hqwarn.util.FuncHelper;
import com.thinkive.hqwarn.util.WarnCacheHelper;
import com.thinkive.warn.factory.BeanFactory;
import com.thinkive.warn.factory.BeanFactory.Key;
import com.thinkive.web.bean.Request;
import com.thinkive.web.bean.RequestImpl;
import com.thinkive.web.bean.Response;

public abstract class AbstractFunction implements IFunction
{
    protected Log                             logger       = LogFactory.getLog("Server");

    private static final Map<Integer, String> errorInfoMap = FuncHelper.laodErrorInfo();

    protected final ServerState               serverState  = BeanFactory.getBean(Key.ServerState);

    @Override
    public void service(Request req, Response resp)
    {
        try
        {
            if ( serverState.isInit() || req.getFuncNo() == FuncHelper.HEART_FUNCNO )
            {
                Map<String, Object> params = new HashMap<>();

                int errorNo = processParams(req, params);

                if ( errorNo == FuncHelper.SUCCESS )
                {
                    service(params, resp);
                }
                else if ( errorNo != FuncHelper.SUCCESS_IGNORE )
                {
                    resp.setErrorNo(errorNo);
                }
                else
                {
                    resp.setErrorNo(FuncHelper.SUCCESS);
                }
            }
            else
            {
                resp.setErrorNo(FuncHelper.ERROR_NOTINIT);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            resp.setErrorInfo(e.toString());
            resp.setErrorNo(FuncHelper.ERROR_UNKNOWN);
            logger.warn("	--	@接口错误	--	功能号 : " + req.getFuncNo(), e);
        }

        responseData(req, resp);
    }

    private void responseData(Request req, Response resp)
    {
        int errorNo = resp.getErrorNo();
        String errorInfo = errorInfoMap.get(errorNo);

        if ( !resp.getErrorInfo().isEmpty() )
        {
            errorInfo += " : " + resp.getErrorInfo();
        }

        Object result = resp.getResult();
        if ( errorNo == FuncHelper.SUCCESS )
        {
            if ( result == null )
            {
                result = FuncHelper.RESPONSE_SUCCESS;
            }
        }
        else
        {
            result = null;
        }

        resp.write(result);
        resp.setErrorNo(errorNo);
        resp.setErrorInfo(errorInfo);
    }

    /**
     * @描述: 校验加工参数
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 上午10:53:01
     * @param orgParams ： 输入参数
     * @param params	： 输出参数
     * @return			： 返回错误号
     * 							 0	：	正常
     * 							-1	:	正常 且不用继续执行service
     */
    protected abstract int processParams(Request request, Map<String, Object> params);

    /**
     * @描述: 处理业务，返回参数
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 上午10:59:48
     * @param params
     * @param resp
     */
    protected abstract void service(Map<String, Object> params, Response response) throws Exception;

    /**
     * @描述: 校验参数 UserID  是否正确
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 下午4:07:41
     * @param request
     * @param params
     */
    protected final int checkUserID(Request request, Map<String, Object> params)
    {
        Object userID = ((RequestImpl) request).getParameter(PARAM_USERID);

        if ( userID == null )
        {
            return FuncHelper.ERROR_NULL_USERID;
        }

        if ( userID instanceof String )
        {
            UserInfo user = WarnCacheHelper.getUserInfo((String) userID);

            if ( user == null )
            {
                return FuncHelper.SUCCESS_IGNORE;
            }

            params.put(PARAM_USERID, user);

            return FuncHelper.SUCCESS;
        }
        else
        {
            return FuncHelper.ERROR_ILLEGAL_USERID;
        }
    }

    /**
     * @描述: 检查参数 OS_ID
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 下午5:16:34
     * @return
     */
    protected final int checkOSID(Request request, Map<String, Object> params)
    {
        Object osId = ((RequestImpl) request).getParameter(FuncHelper.PARAM_OS_ID);
        Object token = ((RequestImpl) request).getParameter(FuncHelper.PARAM_TOKEN);
        Object mobileId = ((RequestImpl) request).getParameter(FuncHelper.PARAM_MOBILEID);

        if ( osId == null )
        {
            return FuncHelper.ERROR_NULL_OS_ID;
        }

        if ( osId instanceof Long )
        {
            try
            {
                params.put(FuncHelper.PARAM_OS_ID, ((Long) osId).intValue());
                params.put(FuncHelper.PARAM_TOKEN, token);
                params.put(FuncHelper.PARAM_MOBILEID, mobileId);

                return FuncHelper.SUCCESS;

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block

                return FuncHelper.ERROR_ILLEGAL_OS_ID;
            }
        }
        else
        {
            return FuncHelper.ERROR_ILLEGAL_OS_ID;
        }
    }

    /**
     * @描述: 校验参数 StockCodeList  是否正确
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 下午4:07:41
     * @param request
     * @param params
     */
    protected final int checkStockCodeList(Request request, Map<String, Object> params)
    {
        Object stockCodeList = ((RequestImpl) request).getParameter(PARAM_STOCKCODELIST);

        if ( stockCodeList == null )
        {
            return FuncHelper.ERROR_NULL_STOCKCODELIST;
        }

        if ( stockCodeList instanceof String )
        {

            String stockCodes[] = ((String) stockCodeList).split("\\|");

            for (String stockCode : stockCodes)
            {
                if ( isStockCodeLegal(stockCode) )
                {
                    continue;

                }
                else
                {
                    return FuncHelper.ERROR_ILLEGAL_STOCKCODELIST;
                }
            }

            params.put(PARAM_STOCKCODELIST, stockCodes);

            return FuncHelper.SUCCESS;
        }
        else
        {

            return FuncHelper.ERROR_ILLEGAL_STOCKCODELIST;
        }
    }

    /**
     * @描述: 校验参数 StockCodeList  是否正确
     * @作者: 王嵊俊
     * @创建日期: 2016年5月5日 下午4:07:41
     * @param request
     * @param params
     */
    protected final int checkStockCode(Request request, Map<String, Object> params)
    {
        Object stockCode = ((RequestImpl) request).getParameter(FuncHelper.PARAM_STOCKCODE);

        if ( stockCode == null )
        {
            return FuncHelper.ERROR_NULL_STOCKCODELIST;
        }

        if ( stockCode instanceof String )
        {
            if ( isStockCodeLegal((String) stockCode) )
            {
                params.put(FuncHelper.PARAM_STOCKCODE, stockCode);
                return FuncHelper.SUCCESS;

            }
            else
            {
                return FuncHelper.ERROR_ILLEGAL_STOCKCODE;
            }

        }
        else
        {
            return FuncHelper.ERROR_ILLEGAL_STOCKCODE;
        }
    }

    /**
     * @描述: 校验参数 WarnInfo
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 下午3:45:10
     * @param request
     * @param params
     * @return
     */
    protected final int checkWarnInfo(Request request, Map<String, Object> params)
    {
        Object sWarnInfoList = ((RequestImpl) request).getParameter(FuncHelper.PARAM_WARNINFO);

        if ( sWarnInfoList == null )
        {
            return FuncHelper.ERROR_NULL_WARNINFO;
        }

        List<WarnInfo> warnInfoList = new ArrayList<>();

        if ( sWarnInfoList instanceof String )
        {
            try
            {
                if ( !"".equals(sWarnInfoList) )
                {
                    String warnInfos[] = ((String) sWarnInfoList).split("\\|");
                    WarnInfo warnInfo = null;
                    for (String sWarnInfo : warnInfos)
                    {
                        warnInfo = FuncHelper.getWarnInfo(sWarnInfo);

                        if ( warnInfo == null )
                        {
                            return FuncHelper.ERROR_ILLEGAL_WARNINFO;
                        }

                        warnInfoList.add(warnInfo);
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block

                return FuncHelper.ERROR_ILLEGAL_WARNINFO;
            }
            params.put(FuncHelper.PARAM_WARNINFO, warnInfoList);

            return FuncHelper.SUCCESS;
        }
        else
        {
            return FuncHelper.ERROR_ILLEGAL_WARNINFO;
        }
    }

    /**
     * @描述: 判断股票代码是否合法
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 上午11:07:44
     * @param stockCode
     * @return
     */
    private final boolean isStockCodeLegal(String stockCode)
    {
        if ( stockCode.length() == 8 )
        {
            if ( (stockCode.startsWith("SH") || stockCode.startsWith("SZ")) )
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        if ( stockCode.length() == 7 )
        {
            if ( stockCode.startsWith("HK") )
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    /**
     * @描述: 检查参数 WarnType
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 下午5:16:34
     * @return
     */
    protected final int checkWarnType(Request request, Map<String, Object> params)
    {
        Object WarnTypeList = ((RequestImpl) request).getParameter(FuncHelper.PARAM_WARNTYPE);

        if ( WarnTypeList == null )
        {
            return FuncHelper.ERROR_NULL_WARNTYPE;
        }

        if ( WarnTypeList instanceof String )
        {
            try
            {
                String sWarnTypes[] = ((String) WarnTypeList).split("\\|");

                int warnTypes[] = new int[sWarnTypes.length];

                for (int i = 0, len = sWarnTypes.length; i < len; i++)
                {
                    warnTypes[i] = Integer.parseInt(sWarnTypes[i]);
                }

                params.put(FuncHelper.PARAM_WARNTYPE, warnTypes);

                return FuncHelper.SUCCESS;

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block

                return FuncHelper.ERROR_ILLEGAL_WARNTYPE;
            }
        }
        else
        {
            return FuncHelper.ERROR_ILLEGAL_WARNTYPE;
        }
    }

    /**
     * @描述: 检查参数 serverID
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 下午5:16:34
     * @return
     */
    protected final int checkSererId(Request request, Map<String, Object> params)
    {
        Object serverId = ((RequestImpl) request).getParameter(FuncHelper.HEART_PARAM_SERVERID);

        if ( serverId == null )
        {
            return FuncHelper.ERROR_NULL_SERVERID;
        }

        if ( serverId instanceof String )
        {
            try
            {
                params.put(FuncHelper.HEART_PARAM_SERVERID, serverId);

                return FuncHelper.SUCCESS;

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block

                return FuncHelper.ERROR_ILLEGAL_SERVERID;
            }
        }
        else
        {
            return FuncHelper.ERROR_ILLEGAL_SERVERID;
        }
    }

    /**
     * @描述: 检查参数 serverID
     * @作者: 王嵊俊
     * @创建日期: 2016年5月6日 下午5:16:34
     * @return
     */
    protected final int checkZoneId(Request request, Map<String, Object> params)
    {
        Object zoneId = ((RequestImpl) request).getParameter(FuncHelper.HEART_PARAM_ZONEID);

        if ( zoneId == null )
        {
            return FuncHelper.ERROR_NULL_ZONEID;
        }

        if ( zoneId instanceof Long )
        {
            try
            {
                params.put(FuncHelper.HEART_PARAM_ZONEID, (int) ((long) zoneId));

                return FuncHelper.SUCCESS;

            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block

                return FuncHelper.ERROR_ILLEGAL_ZONEID;
            }
        }
        else
        {
            return FuncHelper.ERROR_ILLEGAL_ZONEID;
        }
    }

}
