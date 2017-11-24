package org.learn.lra.orderservice;

import feign.RequestLine;

public interface ApiClient {

    @RequestLine("GET /test")
    String test();

}
