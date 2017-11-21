package com.github.xzzpig.cheil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chessboard {
    private static final Random random = new Random();
    private static Chessboard instance;
    public final int size;
    private int score;
    private int[][] data;
    private boolean win = false;

    public Chessboard() {
        this(4);
    }

    public Chessboard(int size) {
        this.size = size;
        data = new int[size][size];
        instance = this;
        move(SIDE.UP);
        data = addData(data);
        data = addData(data);
    }

    public static Chessboard getInstance() {
        if (instance == null)
            new Chessboard();
        return instance;
    }

    private int[][] addData(int[][] data_quo) {
        int[][] ret = copyData(data_quo);
        List<Integer[]> empty = getEmptyLoc(ret);
        if (empty.size() == 0)
            return ret;
        Integer[] loc = empty.get(random.nextInt(empty.size()));
        int a;
        if (random.nextInt(100) < 90) {
            a = 2;
        } else {
            a = 4;
        }
        ret[loc[0]][loc[1]] = a;
        return ret;
    }

    private int[][] copyData(int[][] data_quo) {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(data_quo[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    public int[][] getData() {
        return data;
    }

    private boolean canMove(SIDE side, int[][] data_quo) {
        int[][] ret = copyData(data_quo);
        boolean flag1, flag2;
        ret = rotate(side.ordinal(), ret);
        for (int i = 0; i < size; i++) {
            flag1 = false;
            flag2 = false;
            for (int j = 0; j < size; j++) {
                if ((!flag1 && !flag2) && (ret[i][j] == 0)) {
                    flag1 = true;
                } else if ((flag1 && !flag2) && (ret[i][j] != 0)) {
                    flag2 = true;
                    break;
                }
            }
            if (flag1 && flag2) {
                return true;
            }
        }
        return false;
    }

    private int[][] move(SIDE side, int[][] data_quo) {
        int[][] ret = copyData(data_quo);
        ret = rotate(side.ordinal(), ret);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int y = 0; y < j; y++) {
                    if (ret[i][y] == 0) {
                        ret[i][y] = ret[i][j];
                        ret[i][j] = 0;
                        break;
                    }
                }
            }
        }
        if (side != SIDE.UP)
            ret = rotate(4 - side.ordinal(), ret);
        return ret;
    }

    public void move(SIDE side) {
        if (canMove(side, data) || canMerge(side, data)) {
            data = (addData(move(side, clear2048(merge(side, data)))));
        }
    }

    private int[][] clear2048(int[][] data_quo) {
        int[][] ret = copyData(data_quo);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (ret[i][j] == 2048)
                    win = true;
            }
        }
        return ret;
    }

    public boolean isWin() {
        return win;
    }

    private int[][] rotate(int[][] data_quo) {
        int[][] old = copyData(data_quo), ret = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ret[size - j - 1][i] = old[i][j];
            }
        }
        return ret;
    }

    private int[][] rotate(int times, int[][] data_quo) {
        int[][] ret = copyData(data_quo);
        for (int i = 0; i < times; i++) {
            ret = rotate(ret);
        }
        return ret;
    }

    private void printData(int[][] data) {
        for (int j = 0; j < data[0].length; j++) {
            for (int[] aData : data) System.out.print(aData[j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    private boolean canMerge(SIDE side, int[][] data_quo) {
        int[][] ret = data_quo;
        ret = rotate(side.ordinal(), ret);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                for (int y = j + 1; y < size; y++) {
                    if (ret[i][y] != 0 && ret[i][j] != ret[i][y])
                        break;
                    if (ret[i][j] == ret[i][y]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int[][] merge(SIDE side, int[][] data_quo) {
        int[][] ret = copyData(data_quo);
        ret = rotate(side.ordinal(), ret);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                for (int y = j + 1; y < size; y++) {
                    if (ret[i][y] != 0 && ret[i][j] != ret[i][y])
                        break;
                    if (ret[i][j] == ret[i][y]) {
                        ret[i][j] *= 2;
                        ret[i][y] = 0;
                        score += ret[i][j];
                        break;
                    }
                }
            }
        }
        if (side != SIDE.UP)
            ret = rotate(4 - side.ordinal(), ret);
        return ret;
    }

    public int getScore() {
        return score;
    }

    private List<Integer[]> getEmptyLoc(int[][] data_quo) {
        List<Integer[]> ret = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (data_quo[i][j] == 0)
                    ret.add(new Integer[]{i, j});
            }
        }
        return ret;
    }

    public boolean isGameOver() {
        if (getEmptyLoc(data).size() != 0)
            return false;
        if (canMerge(SIDE.UP, data))
            return false;
        if (canMerge(SIDE.DOWN, data))
            return false;
        if (canMerge(SIDE.LEFT, data))
            return false;
        return !canMerge(SIDE.RIGHT, data);
    }

    public enum SIDE {
        UP, LEFT, DOWN, RIGHT
    }
}
