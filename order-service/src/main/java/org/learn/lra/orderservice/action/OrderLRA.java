package org.learn.lra.orderservice.action;

import org.jetbrains.annotations.NotNull;
import org.learn.lra.coreapi.Action;
import org.learn.lra.coreapi.LRA;

import java.util.Collections;
import java.util.List;

public class OrderLRA implements LRA {

    private final String id;
    private List<Action> actions;

    public OrderLRA(String id, List<Action> actions) {
        this.id = id;
        this.actions = actions;
    }

    @NotNull
    @Override
    public String getId() {
        return id;
    }

    @NotNull
    @Override
    public String getName() {
        return "Order LRA";
    }

    @NotNull
    @Override
    public List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }

}
