package com.fool.demo;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author fool
 * @date 2021/9/3 13:40
 */
public class XmlPostTest {


    public static void main(String[] args) {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("soapenv:Envelope")
                .addNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/")
                .addNamespace("srv", "http://srv.camrgo.com");
        root.addElement("soapenv:Header");
        Element body = root.addElement("soapenv:Body");
        Element method = body.addElement("srv:getReading");
        Element arg0 = method.addElement("arg0");


        List<Map<String, String>> data = new ArrayList<>();

        HashMap<String, String> item = new HashMap<>();
        item.put("customerno", "11111");
        item.put("data_dt", "20200720");
        item.put("datavalue", "111");

        data.add(item);

        HashMap<String, Object> params = new HashMap<>();
        params.put("companycode", "001");
        params.put("data", data);
        arg0.setText(JSONObject.toJSONString(params));
        // arg0.setText("{\"companycode\":\"001\",\"data\":[{\"customerno\":\"11111\",\"data_dt\":\"20200720\",\"datavalue\":\"111\"} ,{\"customerno\":\"12111\",\"data_dt\":\"20200720\",\"datavalue\":\"121\"}]}");
        System.out.println(document.asXML());

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);

        HttpEntity<String> entity = new HttpEntity<String>(document.asXML(), httpHeaders);
        String url = "http://221.232.102.255:9099/CronJobSrv_hs/webservice/getReading";
        String responseStr = restTemplate.postForObject(url, entity, String.class);
        System.out.println(responseStr);
        JSONObject aReturn = Optional.ofNullable(responseStr)
                .map(text -> {
                    try {
                        return DocumentHelper.parseText(text);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).map(Document::getRootElement)
                .map(r -> r.element("Body"))
                .map(e -> e.element("getReadingResponse"))
                .map(e -> e.element("return"))
                .map(Element::getText)
                .map(JSONObject::parseObject)
                .orElse(new JSONObject());
    }

}
