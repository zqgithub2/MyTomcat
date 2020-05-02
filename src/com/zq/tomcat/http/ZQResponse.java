package com.zq.tomcat.http;

import java.io.IOException;
import java.io.OutputStream;

public class ZQResponse {
    private OutputStream os;

    public ZQResponse(OutputStream os){
        this.os = os;
    }

    public void write(String str) throws IOException {
        //用的是HTTP协议，输出也要遵循HTTP协议
        //给到一个状态码 200
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html;\n")
                .append("\r\n")
                .append(str);
        os.write(sb.toString().getBytes());
    }
}
