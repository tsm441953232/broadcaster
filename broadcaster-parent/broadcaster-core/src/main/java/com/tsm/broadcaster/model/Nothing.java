package com.tsm.broadcaster.model;

public class Nothing {
    public static final Nothing instance = new Nothing();
    private Nothing() {
    }
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else {
            return other instanceof Nothing;
        }
    }

    public int hashCode() {
        return super.hashCode();
    }
}
