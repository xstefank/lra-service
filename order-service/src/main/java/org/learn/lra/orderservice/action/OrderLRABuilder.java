package org.learn.lra.orderservice.action;

import org.learn.lra.coreapi.Action;

import java.util.List;

public class OrderLRABuilder {

    private String id;
    private List<Action> actions;

    public OrderLRABuilder(String id) {
        this.id = id;
    }

    public OrderLRABuilder withAction(Action action) {
        actions.add(action);
        return this;
    }

    public OrderLRA build() {
        return new OrderLRA(id, actions);
    }

}
