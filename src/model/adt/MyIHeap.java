package model.adt;

import exceptions.ADTException;
import model.value.IValue;

import java.util.Map;

public interface MyIHeap {
    Integer getFirstFree();
    public void update(Integer position, IValue value) throws ADTException;
    public IValue getValue(Integer key);
    public Integer add(IValue value);
    Map<Integer, IValue> getMap();
    public boolean containsKey(Integer key);

    void setContent(Map<Integer, IValue> integerIValueMap);
    MyIHeap getContent();
}
