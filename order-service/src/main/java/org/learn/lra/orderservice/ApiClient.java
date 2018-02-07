package org.learn.lra.orderservice;

import feign.Headers;
import feign.RequestLine;
import org.learn.lra.coreapi.LRADefinition;
import org.learn.lra.coreapi.LRAResult;

public interface ApiClient {

    @RequestLine("POST /api/lra")
    @Headers("Content-Type: application/json")
    rx.Observable<LRAResult> processLRA(LRADefinition lraDefinition);

}
