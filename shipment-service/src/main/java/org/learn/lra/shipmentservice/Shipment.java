package org.learn.lra.shipmentservice;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@ToString
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotNull
    private String orderId;

    private int price;

    private boolean comleted;

    public Shipment(String orderId, int price) {
        this(orderId, price, false);
    }

    public Shipment(String orderId, int price, boolean comleted) {
        this.orderId = orderId;
        this.price = price;
        this.comleted = comleted;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getPrice() {
        return price;
    }

    public boolean isComleted() {
        return comleted;
    }
}
