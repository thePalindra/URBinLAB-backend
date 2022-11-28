package com.URBinLAB.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AccessControl {

    public final static int TIME = 1000*60*60*24*14; //2 semanas
    private static final Map<String, Map<Feature, Boolean>> permissions = new TreeMap<>();

    public static void innit() {
        permissions.put("all", setAll());
        permissions.put("admin", setAdmin());
    }

    public static Boolean access(Feature feature, String level) {
        return permissions.get(level).get(feature);
    }

    private static Map<Feature, Boolean> setAll() {
        Map<Feature, Boolean> map = new HashMap<>();

        map.put(Feature.SIGNUP, true);
        map.put(Feature.LOGIN, true);
        map.put(Feature.LOGOUT, false);
        map.put(Feature.ADD_DOCUMENT, false);
        map.put(Feature.SPATIAL_QUERY, true);
        map.put(Feature.AUX_QUERY, true);
        map.put(Feature.ADD_KEYWORD, false);
        return map;
    }

    private static Map<Feature, Boolean> setAdmin() {
        Map<Feature, Boolean> map = new HashMap<>();

        map.put(Feature.SIGNUP, false);
        map.put(Feature.LOGIN, false);
        map.put(Feature.LOGOUT, true);
        map.put(Feature.ADD_DOCUMENT, true);
        map.put(Feature.SPATIAL_QUERY, true);
        map.put(Feature.AUX_QUERY, true);
        map.put(Feature.ADD_KEYWORD, true);
        return map;
    }
}
