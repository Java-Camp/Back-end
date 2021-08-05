package com.jcf.orm.annotation;

public enum CascadeType {
    ALL,
    PERSIST,
    MERGE,
    REMOVE,
    REFRESH,
    DETACH;

    private CascadeType() {
    }
}