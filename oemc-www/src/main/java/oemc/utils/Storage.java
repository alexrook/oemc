package oemc.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author moroz
 */
public class Storage {
    
    /*
     * peep on: 
     *       http://youtu.be/5Iu4ZUcrJ0g?list=PL6jg6AGdCNaWtTjsYJ9t0VaITpIZm4pMt
     *       many thanks to the author of this video
     */
    public static class SimpleLRUCache<K, V> extends LinkedHashMap<K, V> {
        
        private final int capacity;
        
        public SimpleLRUCache(int capacity) {
            /*
             *   
             *   loadFactor>1 означает не расширять карту при заполнении
             *   accessOrder=true - порядок по "доступу" 
             *   наиболее часто запрашиваемые сущности попадают в конец карты
             *   наименее часто запрашиваемые попадают в начало и становятся кандидатами на удаление методом removeEldestEntry
             *
             */
            super(capacity + 1, 1.1f, true);
            this.capacity = capacity;
        }
        
        @Override
        protected boolean removeEldestEntry(Entry<K, V> eldest) {
            return this.size() > capacity;
        }
        
    }
    
    private static Storage singleton;
    private final Map<String, String> cache;
    
    private Storage() {
        //I do not have more than 12 users
        cache = Collections.synchronizedMap(new SimpleLRUCache<String, String>(12));
    }
    
    public static Storage getSingle() {
        if (singleton == null) {
            singleton = new Storage();
        }
        return singleton;
    }
    
    public void put(String key, String val) {
        cache.put(key, val);
    }
    
    public String get(String key) {
        return cache.get(key);
    }
    
}
