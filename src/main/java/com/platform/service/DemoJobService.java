package com.platform.service;

/**
 * @author jianghy
 * @Description:
 * @date 2021/2/20 14:13
 */
import org.springframework.stereotype.Service;

@Service
public class DemoJobService {
    public void demoTest(String s1) {
        System.out.println("==============" + s1);
    }
}