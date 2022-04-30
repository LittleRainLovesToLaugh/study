package com.xioayu.sparse.array;

import java.io.*;

/**
 * Description 二维数组与稀疏数组转换
 *
 * @author XD
 * createTime 2022年02月15日 12:34:00
 */
public class SparseArray {

    public static void main(String[] args) throws IOException {
        // 创建一个原始的二维数组,并进行赋值
        int[][] doubleDimensionalArray = new int[10][10];
        doubleDimensionalArray[0][0] = 2;
        doubleDimensionalArray[3][1] = 4;
        doubleDimensionalArray[5][2] = 7;
        doubleDimensionalArray[7][5] = 8;
        doubleDimensionalArray[9][7] = 9;
        // 输出原始的二维数组，并统计不为0的个数
        int sum = 0;
        System.out.println("输出原始的二维数组");
        for (int[] row : doubleDimensionalArray) {
            for (int data : row) {
                System.out.printf("%d\t", data);
                if (data != 0) {
                    sum++;
                }
            }
            System.out.println();
        }
        System.out.println("不为0的个数为：" + sum);
        // 二维数组转稀疏数组的思路
        // 1. 遍历原始的二维数组，得到有效数据的个数sum
        // 2. 根据sum就可以创建稀疏数组sparseArray int[sum +1][3];
        // 3. 将二维数组中的有效数据存储到稀疏数组中
        // 4. 初始化稀疏数组并赋值, 固定3列，分别记录 行、列、值
        int[][] sparseArr = new int[sum + 1][3];
        sparseArr[0][0] = doubleDimensionalArray.length;
        sparseArr[0][1] = doubleDimensionalArray.length;
        sparseArr[0][2] = sum;
        // count 用于稀疏数组的下标移动，也就是第几个非零数据
        int count = 1;
        for (int i = 0; i < doubleDimensionalArray.length; i++) {
            for (int j = 0; j < doubleDimensionalArray.length; j++) {
                if (doubleDimensionalArray[i][j] != 0) {
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = doubleDimensionalArray[i][j];
                    count++;
                }
            }
        }


        // 稀疏数组恢复为二维数组
        // 1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        // 2. 读取稀疏数组后几行的数据，并赋值给二维数组
        int[][] sparseArray = new int[sparseArr[0][0]][sparseArr[0][1]];
        for (int i = 1; i < sparseArr.length; i++) {
            sparseArray[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        System.out.println("输出稀疏数组恢复为二维数组的数据：");
        for (int[] row : sparseArray) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        // // IO操作
        // OutputStream outputStream = new FileOutputStream("test.txt");
        //
        // System.out.println("sparseArr");
        // for (int[] row : sparseArr) {
        //     for (int data : row) {
        //         outputStream.write(data);
        //         System.out.printf("%d\t", data);
        //     }
        //     System.out.println();
        // }
        // outputStream.close();
        // InputStream inputStream = new FileInputStream("test.txt");
        // int n = 0;
        // StringBuilder stringBuilder = new StringBuilder();
        // //当n不等于-1,则代表未到末尾
        // while (n != -1) {
        //     // 读取文件的一个字节(8个二进制位)
        //     n = inputStream.read();
        //     stringBuilder.append(-1 == n ? "" : n);
        // }
        // inputStream.close();
        // System.out.println(stringBuilder);
    }

}
