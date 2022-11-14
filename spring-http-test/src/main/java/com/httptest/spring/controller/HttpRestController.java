package com.httptest.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpRestController {

    @GetMapping("/size/{sizeInBytes}")
    public String getResponseOfSizeByInput(@PathVariable("sizeInBytes") String sizeInBytes, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        long startTime = System.nanoTime();
        int size = Integer.valueOf(sizeInBytes);
        StringBuilder stringBuilder = new StringBuilder(RandomStringUtils.randomAscii(size));
        long endTime = System.nanoTime();
        long processingTime = endTime-startTime;
        httpServletResponse.addHeader("processing-time", String.valueOf(processingTime));
        //System.out.println("version : "+httpServletRequest.getProtocol()+" response time : "+processingTime);
        return stringBuilder.toString();
    }

}
