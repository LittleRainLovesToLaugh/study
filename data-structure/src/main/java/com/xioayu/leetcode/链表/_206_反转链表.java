package com.xioayu.leetcode.链表;

/**
 * Description
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 * @author 小雨
 * createTime 2022年09月24日 11:46:00
 */
public class _206_反转链表 {


    /**
     * 假设listNode内容为54321，头节点为5
     * 反转链表-使用递归解决！
     *
     * @param head 传入的是头节点
     * @return 反转后的头节点
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 递归思路，传递node.next进去，反转除了head节点之外的所有节点
        ListNode newHead = reverseList(head.next);
        // 剩余的一个head节点就是 head.next的next指向head 即可完成反转
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 假设listNode内容为54321，头节点为5
     * 反转链表-使用循环迭代
     *
     * @param head 传入的是头节点
     * @return 反转后的头节点
     */
    public static ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = null;
        while (head != null) {
            // temp = 4
            ListNode temp = head.next;
            // 5指向的节点先赋值为null
            head.next = newHead;
            // newHead = 5（此时一个新的5指向null的倒转节点诞生）
            newHead = head;
            // head=4；4赋值给head，进行下一轮循环替换操作
            head = temp;
        }
        return newHead;
    }
    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        node.next.next.next.next.next = new ListNode(6);
        System.out.println(reverseList2(node));
    }
}
