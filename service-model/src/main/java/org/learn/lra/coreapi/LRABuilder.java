package org.learn.lra.coreapi;

import java.util.ArrayList;
import java.util.List;

public class LRABuilder {

    private String id;
    private String name;
    private List<Action> actions = new ArrayList<>();

    public LRABuilder id(String id) {
        this.id = id;
        return this;
    }

    public LRABuilder name(String name) {
        this.name = name;
        return this;
    }

    public LRABuilder withAction(Action action) {
        this.actions.add(action);
        return this;
    }

    public LRA build() {
        return new LRAImpl(id, name, actions);
    }
}
