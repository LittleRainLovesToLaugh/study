package com.xioayu;

/**
 * Description 单链表演示
 *
 * @author XD
 * createTime 2022年03月09日 14:48:00
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        Node node1 = new Node(1, "爱笑的小雨1");
        Node node2 = new Node(2, "爱笑的小雨2");
        Node node3 = new Node(3, "爱笑的小雨3");
        Node node4 = new Node(4, "爱笑的小雨4");
        Node node5 = new Node(5, "爱笑的小雨5");
        Node node6 = new Node(5, "爱笑的小雨55555555");
        // 创建一个链表,并把节点数据添加到链表中
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        // singleLinkedList.add(node1);
        // singleLinkedList.add(node2);
        // singleLinkedList.add(node3);
        // singleLinkedList.add(node4);
        // singleLinkedList.add(node5);
        // singleLinkedList.add(node22);
        singleLinkedList.addOrderBy(node1);
        singleLinkedList.addOrderBy(node4);
        singleLinkedList.addOrderBy(node2);
        singleLinkedList.addOrderBy(node5);
        singleLinkedList.addOrderBy(node3);
        // 显示链表数据
        singleLinkedList.showList();
        singleLinkedList.update(node6);
        System.out.println();
        singleLinkedList.showList();
        singleLinkedList.delete(node6);
        System.out.println();
        singleLinkedList.showList();
    }

}

class SingleLinkedList {
    /**
     * 初始化一个头结点，头结点不能动
     */
    private final Node headNode = new Node(0, "头结点");

    /**
     * 添加节点到单项链表中：不考虑排序
     * 1.找到最后一个节点
     * 2.将最后这个节点的next指向新的节点
     */
    public void add(Node node) {
        Node temp = headNode;
        // 循环变量查找链表的最后一个节点，需要使用一个临时变量辅助进行遍历查询操作
        while (true) {
            // 当temp.next == null时代表找到了链表的最后一个节点
            if (temp.next == null) {
                temp.next = node;
                break;
            }
            // 没有找到就把下一个节点temp，然后继续查找
            temp = temp.next;
        }
    }

    /**
     * 根据编号删除链表
     */
    public void delete(Node node) {
        Node temp = headNode;
        if (temp.next == null) {
            System.err.println("链表为空！");
        }
        // 循环变量查找链表的最后一个节点，需要使用一个临时变量辅助进行遍历查询操作
        // 当temp.next == null时代表找到了链表的最后一个节点
        while (temp.next != null) {
            if (temp.next.no == node.no) {
                temp.next = null;
                return;
            }
            // 没有找到就把下一个节点temp，然后继续查找
            temp = temp.next;
        }
        System.out.println("根据编号删除链表:没有找到此编号!");
    }

    /**
     * 根据编号修改链表名称
     */
    public void update(Node node) {
        Node temp = headNode;
        if (temp.next == null) {
            System.err.println("链表为空！");
        }
        // 循环变量查找链表的最后一个节点，需要使用一个临时变量辅助进行遍历查询操作
        // 当temp.next == null时代表找到了链表的最后一个节点
        while (temp.next != null) {
            if (temp.next.no == node.no) {
                temp.next.name = node.name;
                return;
            }
            // 没有找到就把下一个节点temp，然后继续查找
            temp = temp.next;
        }
        System.out.println("根据编号修改链表名称：没有找到此编号!");
    }

    /**
     * 添加节点到单项链表中：根据编号排序
     * 如果编号冲突，则添加失败，并给出提示
     */
    public void addOrderBy(Node node) {
        // 因为头结点是固定不能动的，所以还是需要一个临时变量查找对应的添加位置
        Node temp = headNode;
        // 标志添加的编号是否存在，默认为false
        boolean flag = false;
        // 循环变量查找链表的最后一个节点，需要使用一个临时变量辅助进行遍历查询操作
        while (temp.next != null) {
            // 当temp.next == null时代表找到了链表的最后一个节点
            // 位置找到了,退出循环
            if (temp.next.no > node.no) {
                break;
            } else if (temp.next.no == node.no) {
                // temp.next.no == node.no说明需要添加的编号已经存在
                flag = true;
                break;
            }
            // 没有找到就把下一个节点temp，然后继续查找
            temp = temp.next;
        }
        if (flag) {
            System.out.printf("当前%d编号已经存在，不能加入\n", node.no);
        } else {
            // 把找到的这个节点后移一位，把当前的node插入到这个节点上
            node.next = temp.next;
            temp.next = node;
        }
    }


    /**
     * 显示队列数据
     */
    public void showList() {
        Node temp = headNode;
        // 是否还有数据
        while (temp.next != null) {
            temp = temp.next;
            // 输出节点信息
            System.out.println(temp);
        }
    }


}

/**
 * 定义一个节点对象 Node，每一个Node对象就是一个节点,节点对象信息可自行定义
 */
class Node {
    /**
     * 编号
     */
    public int no;
    /**
     * 名字
     */
    public String name;

    /**
     * 指向下一个节点
     */
    public Node next;

    public Node(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}