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

    public LRAImpl(String name, List<Action> actions) {
        this.name = name;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }
}
