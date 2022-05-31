package com.dsmh.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

public class SnowFlakeUtil {

    private static long workerId = getWorkerId();
    private static final long DATA_CENTER_ID = 1;
    private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(workerId, DATA_CENTER_ID);

    private static long getWorkerId() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        } catch (Exception e) {
            e.printStackTrace();
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
        workerId = workerId >> 16 & 31;
        return workerId;
    }

    public static synchronized long snowflakeId() {
        return SNOWFLAKE.nextId();
    }
}
