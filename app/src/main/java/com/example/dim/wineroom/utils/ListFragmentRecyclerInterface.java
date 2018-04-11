package com.example.dim.wineroom.utils;

import java.io.Serializable;

public interface ListFragmentRecyclerInterface<T> extends Serializable
{
    public void updateSelectedItem(T selected);

    public void addBottle(T selected);

    public void removeBottle(T selected);

    //public void updateListFragment(T item, boolean isDelete);
    //public void addNewItem();
}
