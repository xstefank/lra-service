package org.learn.lra.orderservice;

import feign.Headers;
import feign.RequestLine;
import org.learn.lra.coreapi.LRADefinition;

public interface ApiClient {

    @RequestLine("POST /api/lra")
    @Headers("Content-Type: application/json")
    String processLRA(LRADefinition lraDefinition);

}
