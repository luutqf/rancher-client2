package cn.luutqf.rancher.client.constant;

import java.math.BigInteger;

/**
 * @Author: ZhenYang
 * @date: 2019/1/16
 * @description:
 */
public class Constants {
    public static final String VNC_PASSWD = "HTTP_PASSWORD";
    public static final String CONSOL_VNC_PASSWD = "VNC_PW";
    public static final String VNC_TYPE = "vnc";
    public static final String JUPYTER_TYPE = "jupyter";
    public static final String CONTAINER_TYPE = "default";
    public static final String LANGUAGE = "zh_CN:zh";
    public static final String LANG = "zh_CN.UTF-8";
    public static final String GRANT_SUDO = "yes";
    public static final BigInteger MEMORY = BigInteger.valueOf(268435456);
    public static final BigInteger CPU_SHARES = BigInteger.valueOf(256);
    public static final String LOCAL_TIME = "/etc/localtime:/etc/localtime:ro";
    public static final String CPU_INFO = "/var/lib/lxcfs/proc/cpuinfo:/proc/cpuinfo:rw";
    public static final String DISK_STATS = "/var/lib/lxcfs/proc/diskstats:/proc/diskstats:rw";
    public static final String MEM_INFO = "/var/lib/lxcfs/proc/meminfo:/proc/meminfo:rw";
    public static final String STAT = "/var/lib/lxcfs/proc/stat:/proc/stat:rw";
    public static final String SWAPS = "/var/lib/lxcfs/proc/swaps:/proc/swaps:rw";
    public static final String UP_TIME = "/var/lib/lxcfs/proc/uptime:/proc/uptime:rw";

}
