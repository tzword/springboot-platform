package com.platform.interceptor;

/**
 * @author jianghy
 * @Description: redis的key
 * @date 2021/2/5 10:55
 */
public class AccessKey {
    private int seconds;

    public AccessKey(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public static AccessKey withExpire(int seconds) {
        return new AccessKey(seconds);
    }

}
