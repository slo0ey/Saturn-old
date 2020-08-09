package com.acceler8tion.Saturn.center;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ICenter<K, V>{

    private final Map<K, V> map = new HashMap<>();

    public ICenter<K, V> register(K key, V entity){
        map.put(key, entity);
        return this;
    }

    public void unregister(K key){
        map.remove(key);
    }

    @Nullable
    public V find(K key){
        return map.get(key);
    }

    public boolean has(K key){
        return map.containsKey(key);
    }

    public int size(){
        return map.size();
    }

}
