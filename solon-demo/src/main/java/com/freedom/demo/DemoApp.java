package com.freedom.demo;

import org.noear.solon.Solon;
import org.noear.solon.SolonProps;
import org.noear.solon.annotation.*;
import org.noear.solon.scheduling.annotation.EnableAsync;
import org.noear.solon.scheduling.annotation.EnableScheduling;
import org.smartboot.http.common.utils.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zengzeng
 */
@SolonMain
@EnableScheduling
@EnableAsync
public class DemoApp {
    public static void main(String[] args) throws UnknownHostException {
        Solon.start(DemoApp.class,args);
        init();
    }

    public static void init () throws UnknownHostException {
        SolonProps cfg = Solon.cfg();
        String host ;
        if(StringUtils.isBlank(cfg.serverHost())){
            host = InetAddress.getLocalHost().getHostAddress();
        }else {
            host = cfg.serverHost();
        }
        String str = "   External :  [http://"+host+":"+cfg.serverPort()+cfg.serverContextPath()+"]";
        String local = "   Local    :  [http://localhost:"+cfg.serverPort()+cfg.serverContextPath()+"]";
        StringBuilder path = new StringBuilder();
        path.append("-".repeat(str.length()));
        System.out.println(path);
        System.out.println("   application name -> :["+cfg.appName()+"]");
        System.out.println(str);
        System.out.println(local);
        System.out.println(path);
    }
}