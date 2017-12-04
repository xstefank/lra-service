package org.learn.lra.coreapi;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
@NoArgsConstructor
public class LRAImpl implements LRA {

    private String name;
    private List<Action> actions;
    private Object info;

    public LRAImpl(String name, List<Action> actions, Object info) {
        this.name = name;
        this.actions = actions;
        this.info = info;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }

    @Override
    public Object getInfo() {
        return info;
    }

}
