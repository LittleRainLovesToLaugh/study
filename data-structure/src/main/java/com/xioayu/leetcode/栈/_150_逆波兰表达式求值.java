package com.xioayu.leetcode.栈;

import java.util.*;

/**
 * 根据 逆波兰表示法，求表达式的值。
 * <p>
 * 有效的算符包括 +、-、*、/ 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
 * 注意 两个整数之间的除法只保留整数部分。
 * 可以保证给定的逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
 *
 * @author xiedi
 * @date 2022/12/7 17:51
 */
public class _150_逆波兰表达式求值 {


    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put("+", "+");
        map.put("-", "-");
        map.put("*", "*");
        map.put("/", "/");
    }

    public int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            // 遇到运算符，弹出栈顶的两个元素进行相应的计算
            if (map.containsKey(token)) {
                int pop2 = stack.pop();
                int pop1 = stack.pop();
                switch (map.get(token)) {
                    case "+":
                        stack.push(pop1 + pop2);
                        break;
                    case "-":
                        stack.push(pop1 - pop2);
                        break;
                    case "*":
                        stack.push(pop1 * pop2);
                        break;
                    case "/":
                        stack.push(pop1 / pop2);
                        break;
                    default:
                        break;
                }
                // 计算结果，然后入栈
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    public int evalRPN2(String[] tokens) {
        Deque<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int num2 = stack.pop();
                int num1 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push(num1 * num2);
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;
                    default:
                }
            }
        }
        return stack.pop();
    }

    public boolean isNumber(String token) {
        return !("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token));
    }

}
