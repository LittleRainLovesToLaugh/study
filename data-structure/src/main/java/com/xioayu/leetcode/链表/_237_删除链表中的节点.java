package com.xioayu.leetcode.链表;

/**
 * Description 一个单链表的 head，我们想删除它其中的一个节点 node。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/delete-node-in-a-linked-list
 *
 * @author 小雨
 * createTime 2022年09月24日 11:16:00
 */
public class _237_删除链表中的节点 {


    /**
     * 删除链表中的节点
     *
     * @param node 传入的节点信息
     */
    public void deleteNode(ListNode node) {
        // 思路，既然找不到上一个节点，而只需要删除当前节点就可以了，
        // 做法，把当前节点的值覆盖即可
        node.val = node.next.val;
        node.next = node.next.next;
    }


}
