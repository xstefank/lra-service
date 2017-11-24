package org.learn.lra.orderservice.lra;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.narayana.lra.client.LRAClient;

public interface ApiClient {

    @RequestLine("GET /test")
    @Headers(LRAClient.LRA_HTTP_HEADER + ": {xlra}")
    String test(@Param("xlra") String xlra);

}
