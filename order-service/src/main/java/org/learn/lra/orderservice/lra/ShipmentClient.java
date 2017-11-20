package org.learn.lra.orderservice.lra;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.narayana.lra.client.LRAClient;
import org.learn.lra.coreapi.ShipmentInfo;

public interface ShipmentClient {

    //TODO should be post with some order info
    @RequestLine("GET /request")
    @Headers(LRAClient.LRA_HTTP_HEADER + ": {xlra}")
    ShipmentInfo requestShipment(@Param("xlra") String xlra);

}
