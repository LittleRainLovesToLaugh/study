package com.xioayu.栈;


import com.xioayu.arithmetic.dynamic.array.ArrayList2;
import com.xioayu.arithmetic.dynamic.array.List;

public class Stack<E> {

    private List<E> list = new ArrayList2<>();

    /**
     * 元素的数量
     * @return
     */
    public int size() {
        return list.size();
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }


    public void push(E element) {
        list.add(element);
    }

    public E pop() {
        return list.remove(list.size() - 1);
    }

    public E top() {
        return list.get(list.size() - 1);
    }

    public static void main(String[] args) {

    }

}
