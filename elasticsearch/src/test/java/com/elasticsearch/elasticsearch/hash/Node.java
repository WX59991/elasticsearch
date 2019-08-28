package com.elasticsearch.elasticsearch.hash;

import lombok.Data;

/**
 * @author wangxia
 * @date 2019/8/28 15:42
 * @Description:
 */
@Data
public class Node<K,V> {

    final int hash;
    final K key;
    V value;
    Node<K,V> next;

    Node(int hash, K key, V value,Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public boolean nodeIsEqual(Node<K,V> node){
        return this.key.toString().equals(key.toString());
    }

}
