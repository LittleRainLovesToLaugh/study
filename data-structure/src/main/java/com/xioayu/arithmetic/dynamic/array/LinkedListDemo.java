package com.xioayu.arithmetic.dynamic.array;

/**
 * Description LinkedList练习
 *
 * @author 小雨
 * createTime 2022年09月18日 22:40:00
 */
public class LinkedListDemo<E> extends AbstractList<E>  implements List<E> {

    private int size;

    private Node<E> first;

    /**
     * 节点信息
     */
    private static class  Node<E>{

        E element;

        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }


    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(E element) {
        return 0;
    }
}
