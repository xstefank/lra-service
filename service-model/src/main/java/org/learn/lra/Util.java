package org.learn.lra;

import java.util.UUID;

public class Util {

    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
