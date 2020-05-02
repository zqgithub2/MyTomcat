package com.zq.tomcat;

import com.zq.tomcat.http.ZQRequest;
import com.zq.tomcat.http.ZQResponse;
import com.zq.tomcat.http.ZQServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyTomcat {
    private int port = 8080;
    private ServerSocket server;
    private Map<String, ZQServlet> servletMapping = new HashMap<String, ZQServlet>();
    private Properties webxml = new Properties();

    public void init() {

        try {
            //加载web.xml文件,同时初始化 ServletMapping对象
            String web_inf = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(web_inf + "web.properties");
            webxml.load(fis);
            for (Object k : webxml.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    //单实例，多线程
                    ZQServlet zqServlet = (ZQServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, zqServlet);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        //1、加载配置文件，初始化ServeltMapping
        init();
        try {
            server = new ServerSocket(this.port);
            System.out.println("GP Tomcat 已启动，监听的端口是：" + this.port);
            //2、等待用户请求,用一个死循环来等待用户请求
            while (true){
               Socket client = server.accept();
               // HTTP请求，发送的数据就是字符串，有规律的字符串（HTTP协议）
               process(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(Socket client) throws Exception {
        System.out.println("处理请求");
        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();
        ZQRequest request = new ZQRequest(is);
        ZQResponse response = new ZQResponse(os);
        //从协议内容中拿到URL，把相应的Servlet用反射进行实例化
        String url = request.getUrl();
        if (servletMapping.containsKey(url)) {
            //调用实例化对象的service()方法，执行具体的逻辑doGet/doPost方法
            servletMapping.get(url).service(request, response);
        } else {
            response.write("404 - not found");
        }
        os.flush();
        os.close();
        is.close();
        client.close();
    }

    public static void main(String[] args) {
        new MyTomcat().start();
    }


}
