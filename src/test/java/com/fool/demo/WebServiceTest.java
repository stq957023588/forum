package com.fool.demo;

/**
 * @author fool
 * @date 2021/9/2 17:05
 */
public class WebServiceTest {

    public static void main(String[] args) {
        test2();
    }
    public static void test2() {

    }

    public static void test1() {
        // // 创建动态客户端
        // JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        // Client client = dcf.createClient("http://221.232.102.255:9099/CronJobSrv_hs/webservice/");
        // try {
        //     String params = "{\"companycode\":\"001\",\"data\":[{\"customerno\":\"11111\",\"data_dt\":\"20200720\",\"datavalue\":\"111\"} ,{\"customerno\":\"12111\",\"data_dt\":\"20200720\",\"datavalue\":\"121\"}]} ";
        //     //按照各自的文档传入参数
        //     Object[] objects = client.invoke("getReading", params);
        //     System.out.println("返回数据:" + JSONObject.toJSONString(objects[0]));
        // } catch (java.lang.Exception e) {
        //     e.printStackTrace();
        // }
    }

}