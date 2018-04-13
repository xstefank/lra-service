package org.learn.lra.coreapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(as = LRADefinitionImpl.class)
@JsonDeserialize(as = LRADefinitionImpl.class)
public interface LRADefinition {

    String getName();

    List<Action> getActions();

    Object getInfo();

}
