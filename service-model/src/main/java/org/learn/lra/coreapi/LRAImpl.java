package org.learn.lra.coreapi;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@ToString
@NoArgsConstructor
public class LRAImpl implements LRA {

    private String id;
    private String name;
    private List<Action> actions;

    public LRAImpl(String id, String name, List<Action> actions) {
        this.id = id;
        this.name = name;
        this.actions = actions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Action> getActions() {
        return Collections.unmodifiableList(actions);
    }
}
