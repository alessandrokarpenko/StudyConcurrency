package org.maintask;

import java.security.SecureRandom;

public enum Detail {
    HEAD,
    LEFT_HAND,
    RIGHT_HAND,
    LEFT_LEG,
    RIGHT_LEG,
    BODY,
    CPU,
    HDD,
    RAM;

    public static Detail getRandomDetail() {
        int count = values().length;
        int i = new SecureRandom().nextInt(count);
        return values()[i];
    }

}
