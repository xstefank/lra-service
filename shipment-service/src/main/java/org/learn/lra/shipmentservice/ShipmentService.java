package org.learn.lra.shipmentservice;

import org.learn.lra.coreapi.OrderInfo;

import javax.ejb.Stateless;

@Stateless
public class ShipmentService {

    public int computeShipment(OrderInfo orderInfo) {
        //return testing stub
        return 42;
    }
}
