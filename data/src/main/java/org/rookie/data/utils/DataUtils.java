package org.rookie.data.utils;

import cn.hutool.core.lang.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataUtils {
    public  static  <T> Pair<Set<T>,Set<T>> getSetAdd(List oldList, List newList){

        Set<T> oldSet = new HashSet<>(oldList);
        Set<T> newSet = new HashSet<>(newList);

        Set<T> toDelete=new HashSet<>(oldSet);
        toDelete.removeAll(newSet);

        Set<T> toAdd=new HashSet<>(newList);
        toAdd.removeAll(oldSet);

        return new Pair<>(toDelete,toAdd);

    }

}
