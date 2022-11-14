package com.httptest.spring.client;

import static java.net.http.HttpClient.Version.HTTP_1_1;
import static java.net.http.HttpClient.Version.HTTP_2;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicLong;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class BenchmarkClient {

    private HttpClient client;

    public BenchmarkClient() {
        this.client = getHttpClient();
    }

    private HttpClient getHttpClient() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (GeneralSecurityException ex) {
            System.out.println("Error in configuring SSL");
        }
        HttpClient client = HttpClient.newBuilder()
          .sslContext(sc)
          .build();
        return client;
    }

    public long makeRestCallWithHttpV1(int size) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
          .uri(URI.create("https://localhost:8080/size/"+size))
          .version(HTTP_1_1)
          .GET()
          .build();
        try {
            long startTime = System.nanoTime();
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            long endTime = System.nanoTime();
            AtomicLong serverProcessingTime = new AtomicLong(0L);
            httpResponse.headers()
              .firstValue("processing-time").ifPresentOrElse(s -> serverProcessingTime.set(Long.parseLong(s)), () -> serverProcessingTime.set(0));
            //System.out.println("server processing time : "+serverProcessingTime);
            return (endTime - (startTime + serverProcessingTime.get()));
            //System.out.println("http version : " + httpResponse.version() + ", total time : " + (endTime - startTime));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long makeRestCallWithHttpV2(int size) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
          .uri(URI.create("https://localhost:8080/size/"+size))
          .version(HTTP_2)
          .GET()
          .build();
        try {
            long startTime = System.nanoTime();
            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            long endTime = System.nanoTime();
            AtomicLong serverProcessingTime = new AtomicLong(0L);
            httpResponse.headers()
              .firstValue("processing-time").ifPresentOrElse(s -> serverProcessingTime.set(Long.parseLong(s)), () -> serverProcessingTime.set(0));
            //System.out.println("server processing time : "+serverProcessingTime);
            return (endTime - (startTime + serverProcessingTime.get()));
            //System.out.println("http version : " + httpResponse.version() + ", total time : " + (endTime - startTime));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
