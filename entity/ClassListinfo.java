package com.example.administrator.phonesefe.entity;

/**
 * Created by Administrator on 2016/12/15.
 */

public class ClassListinfo {
    private String name;
    private int idx;

    public ClassListinfo(String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public int getIdx() {
        return idx;
    }

    @Override
    public String toString() {
        return name+idx;
    }
}
