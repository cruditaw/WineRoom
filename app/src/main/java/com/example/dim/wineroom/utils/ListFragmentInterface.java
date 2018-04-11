package com.example.dim.wineroom.utils;

public interface ListFragmentInterface<T> {

    public void updateRecyclerAdpater(T item, boolean isDelete);

    public void updateRecyclerAdapter();
}
