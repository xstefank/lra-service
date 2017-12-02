package org.learn.lra.coreapi;

import java.util.ArrayList;
import java.util.List;

public class LRABuilder {

    private String name;
    private List<Action> actions = new ArrayList<>();

    public LRABuilder name(String name) {
        this.name = name;
        return this;
    }

    public LRABuilder withAction(Action action) {
        this.actions.add(action);
        return this;
    }

    public LRA build() {
        return new LRAImpl(name, actions);
    }
}
