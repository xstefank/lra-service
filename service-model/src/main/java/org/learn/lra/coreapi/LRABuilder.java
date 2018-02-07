package org.learn.lra.coreapi;

import java.util.ArrayList;
import java.util.List;

public class LRABuilder {

    private String name;
    private List<Action> actions = new ArrayList<>();
    private Object lraInfo;


    public LRABuilder name(String name) {
        this.name = name;
        return this;
    }

    public LRABuilder withAction(Action action) {
        this.actions.add(action);
        return this;
    }

    public LRABuilder lraInfo(Object lraInfo) {
        this.lraInfo = lraInfo;
        return this;
    }

    public LRADefinition build() {
        return new LRADefinitionImpl(name, actions, lraInfo);
    }
}
