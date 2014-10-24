package oemc.utils;

import java.util.HashMap;

/**
 * @author moroz
 */
public class Storage {
    
    private final HashMap<String, String> buf;
    
    private static Storage singleton;
    
    private Storage() {
        System.out.println("storage created");
        buf = new HashMap<String, String>(15);
    }
    
    public static Storage getSingle() {
        if (singleton == null) {
            singleton = new Storage();
        }
        return singleton;
    }
    
    public void put(String key, String val) {
        buf.put(key, val);
    }
    
    public String get(String key) {
        return buf.get(key);
    }
    
}
