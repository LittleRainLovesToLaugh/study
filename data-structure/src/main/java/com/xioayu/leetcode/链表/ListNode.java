package com.xioayu.leetcode.链表;

/**
 * Description 公共类istNode
 *
 * @author 小雨
 * createTime 2022年09月24日 13:13:00
 */
public class ListNode {

    int val;

    ListNode next;

    public ListNode(int x) {
        val = x;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
