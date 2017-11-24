package org.learn.lra.orderservice;

import feign.RequestLine;

public interface ApiClient {

    @RequestLine("POST /api/test")
    String test(String msg);

}
