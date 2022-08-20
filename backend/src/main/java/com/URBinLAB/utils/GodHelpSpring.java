package com.URBinLAB.utils;

import javax.websocket.server.PathParam;

public class GodHelpSpring {

    String name;
    String provider;
    Integer max;
    Integer min;
    Long archiver;
    String[] types;
    Integer page;

    public GodHelpSpring(String name, String provider, Integer max, Integer min, Long archiver, String[] types, Integer page) {
        this.name = name;
        this.provider = provider;
        this.max = max;
        this.min = min;
        this.archiver = archiver;
        this.types = types;
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public String getProvider() {
        return provider;
    }

    public Integer getMax() {
        return max;
    }

    public Integer getMin() {
        return min;
    }

    public Long getArchiver() {
        return archiver;
    }

    public String[] getTypes() {
        return types;
    }

    public Integer getPage() {
        return page;
    }
}
