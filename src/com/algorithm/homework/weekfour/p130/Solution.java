package com.algorithm.homework.weekfour.p130;

import java.util.Arrays;

/**
 *   @author qiuch
 * p200岛屿的数量问题升级
 * 求不靠边的岛屿，并替换成X
 * DFS遍历的同时替换
 * Time complexity : O(n×m)
 * Space complexity : O(n*m*2)->O(n*m)
 */
class Solution {
    int row, column;
    int[][] used;

    public void solve(char[][] board) {
        row = board.length;
        if (row == 0) {
            return;
        }
        column = board[0].length;
        if (row <= 2 || column <= 2) {
            //2行或者两列的情况下全部都贴边，不用替换
            return;
        }
        used = new int[row][column];

        //从边上一圈开始遍历，是O岛屿的不用替换成X
        //头尾列
        for (int i = 0; i < row; i++) {
            dfs(board, i, 0, false);
            dfs(board, i, column - 1, false);
        }
        //头尾行，四角不用遍历
        for (int i = 1; i < column - 1; i++) {
            dfs(board, 0, i, false);
            dfs(board, row - 1, i, false);
        }

        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                if (used[i][j] > 0) {
                    continue;
                }
                dfs(board, i, j, true);
            }
        }
    }

    public void dfs(char[][] board, int x, int y, boolean OisX) {
        if (x < 0 || x >= row || y < 0 || y >= column || used[x][y] == 1) {
            return;
        }
        used[x][y] = 1;
        System.out.println(x + "//" + y + "//" + board[x][y]);
        if (board[x][y] == 'X') {
            return;
        }
        if (OisX) {
            board[x][y] = 'X';
        }
        dfs(board, x + 1, y, OisX);
        dfs(board, x - 1, y, OisX);
        dfs(board, x, y + 1, OisX);
        dfs(board, x, y - 1, OisX);
    }


    public static void main(String[] args) {
        //new Solution().solve(new char[][]{{'O', 'O', 'O'}, {'O', 'O', 'O'}, {'O', 'O', 'O'}});
        new Solution().solve(new char[][]{
                {'X', 'O', 'X', 'X'},
                {'O', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'O'},
                {'O', 'X', 'O', 'X'}});
    }


}