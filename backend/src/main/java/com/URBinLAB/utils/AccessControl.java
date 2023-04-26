package com.URBinLAB.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AccessControl {

    public final static int TIME = 1000*60*60*24*14; //2 semanas
    private static final Map<String, Map<Feature, Boolean>> permissions = new TreeMap<>();

    public static void innit() {
        permissions.put("all", setAll());
        permissions.put("researcher", setResearcher());
        permissions.put("admin", setAdmin());
    }

    public static Boolean access(Feature feature, String level) {
        return permissions.get(level).get(feature);
    }

    private static Map<Feature, Boolean> setAll() {
        Map<Feature, Boolean> map = new HashMap<>();

        map.put(Feature.ALL, true);
        map.put(Feature.MASTER, false);
        map.put(Feature.RESEARCHER, false);
        return map;
    }

    private static Map<Feature, Boolean> setResearcher() {
        Map<Feature, Boolean> map = new HashMap<>();

        map.put(Feature.ALL, true);
        map.put(Feature.MASTER, false);
        map.put(Feature.RESEARCHER, true);
        return map;
    }

    private static Map<Feature, Boolean> setAdmin() {
        Map<Feature, Boolean> map = new HashMap<>();

        map.put(Feature.ALL, true);
        map.put(Feature.MASTER, true);
        map.put(Feature.RESEARCHER, true);
        return map;
    }
}
