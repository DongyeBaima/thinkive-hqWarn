package com.thinkive.base.util;

import com.thinkive.base.config.Configuration;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 描述 : 定制化配置文件读取
 * 版权 : Copyright-(c) 2016
 * 公司 : Thinkive
 *
 * @auter 王嵊俊
 * @create 2016-09-14 15:56
 */
public class CustomConfiguration
{
    private Logger              logger = Logger.getLogger(Configuration.class);
    private Map<String, String> items  = new HashMap<>();
    private String config_file_name;

    public CustomConfiguration(String fileName)
    {
        config_file_name = fileName;
        loadConfig();
    }

    private void loadConfig()
    {
        try
        {
            try
            {
                Document ex = XMLHelper.getDocument(Configuration.class, config_file_name);
                if (ex != null)
                {
                    Element  systemElement = ex.getRootElement();
                    List     catList       = systemElement.elements("category");
                    Iterator catIter       = catList.iterator();

                    while (true)
                    {
                        Element catElement;
                        String  catName;
                        do
                        {
                            if (!catIter.hasNext())
                            {
                                return;
                            }

                            catElement = (Element) catIter.next();
                            catName = catElement.attributeValue("name");
                        } while (StringHelper.isEmpty(catName));

                        List     itemList = catElement.elements("item");
                        Iterator itemIter = itemList.iterator();

                        while (itemIter.hasNext())
                        {
                            Element itemElement = (Element) itemIter.next();
                            String  itemName    = itemElement.attributeValue("name");
                            String  value       = itemElement.attributeValue("value");
                            if (!StringHelper.isEmpty(itemName))
                            {
                                items.put(catName + "." + itemName, value);
                            }
                        }
                    }
                }
            } catch (Exception var14)
            {
                logger.error("读入配置文件出错", var14);
            }

        } finally
        {
            ;
        }
    }

    public String getString(String name)
    {
        String value = items.get(name);
        return value == null ? "" : value;
    }

    public String getString(String name, String defaultValue)
    {
        String value = items.get(name);
        return value != null && value.length() > 0 ? value : defaultValue;
    }

    public int getInt(String name)
    {
        String value = getString(name);

        try
        {
            return Integer.parseInt(value);
        } catch (NumberFormatException var3)
        {
            logger.debug("配置文件key[" + name + "]配置错误，return 0", var3);
            return 0;
        }
    }

    public int getInt(String name, int defaultValue)
    {
        String value = getString(name);
        if ("".equals(value))
        {
            return defaultValue;
        }
        else
        {
            try
            {
                return Integer.parseInt(value);
            } catch (NumberFormatException var4)
            {
                logger.debug("配置文件key[" + name + "]配置错误，return " + defaultValue, var4);
                return defaultValue;
            }
        }
    }

    public boolean getBoolean(String name)
    {
        String value = getString(name);
        return Boolean.valueOf(value).booleanValue();
    }

    public double getDouble(String name, double defaultValue)
    {
        String value = getString(name);

        try
        {
            return Double.parseDouble(value);
        } catch (NumberFormatException var5)
        {
            logger.error("配置文件key[" + name + "]配置错误，return " + defaultValue, var5);
            return defaultValue;
        }
    }

    public Map<String, String> getItems()
    {
        return items;
    }

}
