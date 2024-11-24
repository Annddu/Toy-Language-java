package model.types;

import model.value.IValue;
import model.value.RefValue;

public class RefType implements IType {
    IType inner;

    public RefType(IType inner){
        this.inner = inner;
    }

    @Override
    public boolean equals(IType another){
        return another instanceof RefType && ((RefType) another).getInner().equals(inner);
    }

    public IValue defaultValue(){
        return new RefValue(0, inner);
    }

    public IType getInner(){
        return inner;
    }

    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }
}
