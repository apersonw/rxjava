package top.rxjava.common.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author happy
 */
public class SnowFlakeUtils {
    private long machineId ;
    private long dataCenterId ;


    public SnowFlakeUtils(long machineId, long dataCenterId) {
        this.machineId = machineId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 成员类，SnowFlakeUtil的实例对象的保存域
     */
    private static class IdGenHolder {
        private static final SnowFlakeUtils INSTANCE = new SnowFlakeUtils();
    }

    /**
     * 外部调用获取SnowFlakeUtil的实例对象，确保不可变
     */
    public static SnowFlakeUtils get() {
        return IdGenHolder.INSTANCE;
    }

    /**
     * 初始化构造，无参构造有参函数，默认节点都是0
     */
    public SnowFlakeUtils() {
        this(0L, 0L);
    }

    private final Snowflake snowflake = IdUtil.getSnowflake(machineId,dataCenterId);

    public synchronized long id(){
        return snowflake.nextId();
    }

    public static Long getId() {
        return SnowFlakeUtils.get().id();
    }

}