package com.github.DenFade.Saturn.center;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ISingleCenter<E> {

    private final List<E> list = new ArrayList();

    public ISingleCenter<E> register(E element){
        list.add(element);
        return this;
    }

    public void unregister(E element){
        list.remove(element);
    }

    public void unregister(int index){
        list.remove(index);
    }

    @Nullable
    public E get(int index){
        return list.get(index);
    }

}
