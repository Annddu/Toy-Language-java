package model.adt;

import exceptions.KeyNotFoundException;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

// stores variables and their values for the program at runtime
public class MyDictionary<K, V> implements MyIDictionary<K, V>{
    // The dictionary is implemented using a HashMap
    private Map<K, V> map;

    // Constructor for MyDictionary
    public MyDictionary() {
        this.map = new HashMap<>();
    }


    @Override
    // Method to add a new key-value pair to the dictionary
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    // Method to get the value of a key in the dictionary
    public V getValue(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key doesn't exist");
        }
        return this.map.get(key);
    }

    @Override
    // Method to remove the value of a key in the dictionary
    public void remove(K key) throws KeyNotFoundException {
        if (!this.map.containsKey(key)) {
            throw new KeyNotFoundException("Key doesn't exist");
        }
        this.map.remove(key);
    }

    @Override
    // Method to verify if a key is in the dictionary
    public boolean contains(K key) {
        return map.containsKey(key);
    }

    // Method to return the dictionary
    public Map<K, V> getMap() {
        return map;
    }

    @Override
    // Method to String
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(K key: this.map.keySet()){
            str.append("   ");
            str.append(key).append(" -> ").append(this.map.get(key)).append("\n");
        }
        return "SymTable:\n" + str;
    }

    @Override
    // Method to return the keys of the dictionary
    public Set<K> getKeys() {
        return this.map.keySet();
    }

    // Method to return the content of the dictionary
    public Map<K, V> getContent() {
        return this.map;
    }

    public MyDictionary<K, V> deepCopy() {
        MyDictionary<K, V> copy = new MyDictionary<>();
        for (K key : this.map.keySet()) {
            copy.insert(key, this.map.get(key));
        }
        return copy;
    }
}
