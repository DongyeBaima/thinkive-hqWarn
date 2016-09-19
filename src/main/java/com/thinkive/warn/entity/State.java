package com.thinkive.warn.entity;

public enum State
{
    /** 初始化中 */
    initializing,
    /**工作 */
    working,
    /**热备 */
    hotspare,
    /** 异常 */
    excption,
    /** 连接失败：自选股 */
    disConn_option,
    /**初始化失败     */
    initFail
}
