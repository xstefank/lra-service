package org.learn.lra.coreapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonSerialize(as = LRAImpl.class)
@JsonDeserialize(as = LRAImpl.class)
public interface LRA {

    String getName();

    List<Action> getActions();

}
