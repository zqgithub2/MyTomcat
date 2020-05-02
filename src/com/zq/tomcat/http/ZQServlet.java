package com.zq.tomcat.http;

public abstract class ZQServlet {
    public void service(ZQRequest request,ZQResponse response) throws Exception {
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else {
            doPost(request,response);
        }
    }

    public abstract  void  doGet(ZQRequest request,ZQResponse response) throws  Exception;
    public abstract  void  doPost(ZQRequest request,ZQResponse response) throws  Exception;
}
