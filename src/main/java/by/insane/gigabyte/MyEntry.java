/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.insane.gigabyte;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Андрій
 */
public final class MyEntry<K, V> implements Map.Entry<K, V>, Serializable {

    private final K key;
    private V value;
    private boolean editable;
    private V newValue;

    public MyEntry(K key, V value) {
        this.key = key;
        this.value = value;
        editable = false;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        newValue = value;
        this.value = value;
        return old;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public V getNewValue() {
        return newValue;
    }

    public void setNewValue(V newValue) {
        if("".equals(newValue) || newValue == null)
            return;
        this.newValue = newValue;
    }
    
}
