package com.xioayu.leetcode.链表;

/**
 * Description
 * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
 *
 * @author 小雨
 * createTime 2022年09月25日 12:27:00
 */
public class _83_删除排序链表中的重复元素 {

    /**
     * 输入：head = [1,1,2]
     * 输出：[1,2]
     */
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode temp = head;
        while (temp.next != null) {
            if (temp.val == temp.next.val) {
                temp.next = temp.next.next;
            }else {
                temp = temp.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(1);
        node.next.next = new ListNode(2);
        node.next.next.next = new ListNode(3);
        node.next.next.next.next = new ListNode(3);
        node.next.next.next.next.next = new ListNode(3);
        System.out.println(node);
        System.out.println(deleteDuplicates(node));
    }

}
