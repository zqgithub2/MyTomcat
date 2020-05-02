package com.zq.tomcat.servlet;

import com.zq.tomcat.http.ZQRequest;
import com.zq.tomcat.http.ZQResponse;
import com.zq.tomcat.http.ZQServlet;

public class FirstServlet extends ZQServlet {
    @Override
    public void doGet(ZQRequest request, ZQResponse response) throws Exception {
        this.doPost(request,response);
    }

    @Override
    public void doPost(ZQRequest request, ZQResponse response) throws Exception {
        response.write("this is FirstServlet");
        System.out.println("请求FirstServlet成功返回");

    }
}
