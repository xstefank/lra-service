package org.learn.lra.invoiceservice;

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
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @NotNull
    private String orderId;

    @NotNull
    private String lraId;

    private String invoiceString;

    private boolean comleted;

    public Invoice(String orderId, String lraId, String invoiceString) {
        this(orderId, lraId, invoiceString, false);
    }

    public Invoice(String orderId, String lraId, String invoiceString, boolean comleted) {
        this.orderId = orderId;
        this.lraId = lraId;
        this.invoiceString = invoiceString;
        this.comleted = comleted;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getLraId() {
        return lraId;
    }

    public String getInvoiceString() {
        return invoiceString;
    }

    public boolean isComleted() {
        return comleted;
    }

    public void setComleted(boolean comleted) {
        this.comleted = comleted;
    }
}
