package com.xioayu.leetcode.栈;


import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 给定一个平衡括号字符串S，按下述规则计算该字符串的分数：
 * () 得 1 分。
 * AB 得A + B分，其中 A 和 B 是平衡括号字符串。
 * (A) 得2 * A分，其中 A 是平衡括号字符串。
 * 示例 1：
 * 输入： "()"
 * 输出： 1
 * 示例 2：
 * 输入： "(())"
 * 输出： 2
 * 示例3：
 * 输入： "()()"
 * 输出： 2
 * 示例4：
 * 输入： "(()(()))"
 * 输出： 6
 * 来源：力扣（LeetCode）
 * 提示：
 * S 是平衡括号字符串，且只含有 ( 和 ) 。 2 <= S.length <= 50
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/score-of-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _856_括号的分数 {



    public int scoreOfParentheses(String s) {
        Deque<Integer> st = new ArrayDeque<>();
        st.push(0);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                st.push(0);
            } else {
                // 从这个deque表示的堆栈中弹出一个元素。换句话说，删除并返回这个deque的第一个元素。
                int v = st.pop();
                int top = st.pop() + Math.max(2 * v, 1);
                st.push(top);
            }
        }
        return st.peek();
    }

}
