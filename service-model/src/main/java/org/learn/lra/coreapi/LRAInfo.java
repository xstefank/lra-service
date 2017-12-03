package org.learn.lra.coreapi;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(as = LRAInfoImpl.class)
@JsonSerialize(as = LRAInfoImpl.class)
public interface LRAInfo<T> {

    T getData();

    Class<T> getType();

}
