package com.elasticsearch.elasticsearch.hash;

/**
 * @author wangxia
 * @date 2019/8/28 15:33
 * @Description:  实现分离链表法hashMap
 */
public class SeparateChainingHash <K,V>{

    //hash表大小
    int tablesize=8;

    //当前使用的大小
    int count=0;

    Node<K,V>[] table;

    /**
     * 根据key生成
     */
    public int hash(K key){
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public void putKey(K key,V val){
        int hash=hash(key);
        int i=(tablesize - 1) & hash;
        if(table==null){
            table=new Node[tablesize];
        }
        Node<K,V> insertNode=new Node<>(hash,key,val,null);
        if(table[i]!=null){
            Node<K,V> tempNode=table[i];
            while (!(tempNode.nodeIsEqual(insertNode) || tempNode.next==null)){
                tempNode=tempNode.next;
            }
            if(tempNode.nodeIsEqual(insertNode)){
                tempNode.setValue(val);
                return;
            }
            //原來链表最后放插入
            tempNode.next=insertNode;
        }
        if(count+1>tablesize){
            //重新分配空间
            resize();
            putKey(key,val);
            return;
        }
        count++;
        table[i]=insertNode;

    }

    public void resize(){
        tablesize=tablesize*2;
        Node<K,V>[] oldTab=table;
        table=new Node[tablesize];
        if(oldTab!=null){
            for(Node<K,V> temp:oldTab){
                while(temp!=null){
                    putKey(temp.getKey(),temp.getValue());
                    temp=temp.next;
                }
            }
        }
    }

    public void Print(){
        if(table==null)
            return;
        for(Node<K,V> val:table){
            while(val!=null){
                System.out.println("key: "+val.key+" value："+val.getValue());
                val=val.next;
            }
        }
    }

    public static void main(String[] args) {
        SeparateChainingHash<String,String> map=new SeparateChainingHash<>();
        map.putKey("test","123");
        map.putKey("test2","123");
        map.putKey("test3","123");
        map.Print();
        System.out.println("--------------测试重插----------------------");
        map.putKey("test","测试重插");
        map.Print();
        System.out.println("--------------测试分配新空间----------------------");
        map.putKey("test4","123");
        map.putKey("test5","123");
        map.putKey("test6","123");
        map.putKey("test7","123");
        map.putKey("test8","123");
        map.putKey("test9","123");
        map.putKey("test","测试分配新空间");
        map.Print();
    }




}
