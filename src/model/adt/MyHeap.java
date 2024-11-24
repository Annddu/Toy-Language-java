package model.adt;

import exceptions.ADTException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements MyIHeap{
    private Map<Integer, IValue> map;
    private Integer firstFree;

    public MyHeap(Map<Integer, IValue> map){
        this.map = map;
        this.firstFree = 1;
    }

    public MyHeap(){
        this.map = new HashMap<>();
        this.firstFree = 1;
    }

    @Override
    public Integer getFirstFree(){
        return firstFree;
    }

    @Override
    public IValue getValue(Integer key){
        return map.get(key);
    }

    @Override
    public Integer add(IValue value){
        map.put(firstFree, value);
        firstFree++;
        return firstFree - 1;
    }

    @Override
    public void update(Integer position, IValue value) throws ADTException{
        if(!map.containsKey(position))
            throw new ADTException("The key does not exist in the heap");
        map.put(position, value);
    }

    @Override
    public Map<Integer, IValue> getMap(){
        return map;
    }

    @Override
    public boolean containsKey(Integer key){
        return map.containsKey(key);
    }

    @Override
    public void setContent(Map<Integer, IValue> integerIValueMap){
        map = integerIValueMap;
    }

    @Override
    public MyHeap getContent(){
        return this;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for(Integer key: map.keySet()){
            str.append("   ");
            str.append(key).append(" -> ").append(map.get(key)).append("\n");
        }
        return "Heap:\n" + str;
    }
}
