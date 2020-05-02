package com.zq.tomcat.http;

import java.io.IOException;
import java.io.InputStream;

public class ZQRequest {
    private String method;
    private String url;

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public ZQRequest(InputStream is){

        try {
            //拿到http协议内容
            String content= "";
            byte[] buffer = new byte[1024];
            int len = 0;
            if((len=is.read(buffer))>0){
                content = new String(buffer,0,len);
                System.out.println("content="+content);
            }else {
                return;
            }
            String line = content.split("\\n")[0];
            String [] arr = line.split("\\s");
            this.method=arr[0];
            System.out.println("arr[1]="+arr[1]);
            this.url = arr[1].split("\\?")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
