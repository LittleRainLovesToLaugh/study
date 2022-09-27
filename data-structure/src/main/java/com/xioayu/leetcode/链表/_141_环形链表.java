package com.xioayu.leetcode.链表;

/**
 * Description 给你一个链表的头节点 head ，判断链表中是否有环。
 * 输入：head = [3,2,0,-4], pos = 1  注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 * 链接：https://leetcode.cn/problems/linked-list-cycle
 *
 * @author 小雨
 * createTime 2022年09月24日 17:01:00
 */
public class _141_环形链表 {

    /**
     * 思路，快慢指针的思想，慢指针一次走一步，快指针一次走两步,如果有环，这两个指针肯定会相遇
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        // 慢指针
        ListNode slow = head;
        // 快指针,不能在同一个节点开始，避免一开始就相遇，不好判断
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            // 一次一步
            slow = slow.next;
            // 一次两步
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

}
