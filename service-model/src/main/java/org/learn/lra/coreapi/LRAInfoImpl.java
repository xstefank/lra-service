package org.learn.lra.coreapi;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class LRAInfoImpl<T> implements LRAInfo<T> {

    private T data;
    private Class<T> type;

    public LRAInfoImpl(T data) {
        this.data = data;
        this.type = (Class<T>) data.getClass();
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public Class<T> getType() {
        return type;
    }
}
