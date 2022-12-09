package com.xioayu.leetcode.栈;

import java.util.*;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']'的字符串 s ，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的相同类型的左括号。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author xiedi
 * @date 2022/12/2 15:40
 */
public class _20_有效的括号 {

    /**
     * 有效的括号校验、最优解
     */
    public boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            // 左字符匹配
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                //    如果栈时空，说明括号无效
                if (stack.isEmpty()) return false;
                char left = stack.pop();
                // 左右字符不匹配，字符无效
                if (left == '(' && c != ')') return false;
                if (left == '{' && c != '}') return false;
                if (left == '[' && c != ']') return false;
            }
        }
        // 栈为空，说明括号有效，栈不为空，括号无效
        return stack.isEmpty();
    }

    /**
     * 最笨的方法
     */
    public boolean isValid2(String s) {
        while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
            s = s.replace("()", "");
            s = s.replace("{}", "");
            s = s.replace("[]", "");
        }
        return s.isEmpty();
    }


    private static final Map<Character, Character> map = new HashMap<>();

    static {
        map.put('(', ')');
        map.put('{', '}');
        map.put('[', ']');
    }

    /**
     * 使用map替代 isValid方法 多余的if判断
     */
    public boolean isValid3(String s) {
        Deque<Character> stack = new LinkedList<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            // 左字符匹配
            if (map.containsKey(c)) {
                stack.push(c);
            } else {
                // 如果栈时空，说明括号无效
                if (stack.isEmpty()) return false;
                // 如果左边的括号没有对应的右边的括号，就返回false
                if (c != map.get(stack.pop())) return false;
            }
        }
        // 栈为空，说明括号有效，栈不为空，括号无效
        return stack.isEmpty();
    }

}
