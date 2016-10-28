package com.github.xzzpig.cheil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chest {
	public enum SIDE {
		UP,LEFT,DOWN,RIGHT, ;
	}

	private static Chest instance;

	private static final Random random = new Random();
	
	public static Chest getInstance() {
		if(instance==null) new Chest();
		return instance;
	}

	public final int size;
	
	private int socre;

	private int[][] data;

	public Chest() {
		this(4);
	}

	public Chest(int size) {
		this.size = size;
		data = new int[size][size];
		instance = this;
		move(SIDE.UP);
	}

	private int[][] addData(int[][] data_add) {
		int[][] ret = copyData(data_add);
		List<Integer[]> empty = getEmptyLoc(ret);
		if(empty.size()==0)
			return ret;
		Integer[] loc = empty.get(random.nextInt(empty.size()));
		int a = (random.nextInt(2)+1)*2;
		ret[loc[0]][loc[1]]=a;
		return ret;
	}

	private int[][] clear2048(int[][] data_c) {
		int[][] ret = copyData(data_c);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (ret[i][j] == 2048)
					ret[i][j] = 0;
			}
		}
		return ret;
	}

	private int[][] copyData(int[][] data_copy) {
		int[][] copy = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				copy[i][j] = data_copy[i][j];
			}
		}
		return copy;
	}

	public int[][] getData(){
		return data;
	}

	private int[][] merge(SIDE side, int[][] data_merge) {
		int[][] ret = copyData(data_merge);
		ret = rotate(side.ordinal(), ret);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int y = j - 1; y >= 0; y--) {
					if (ret[i][j] == ret[i][y]) {
						ret[i][j] = 0;
						ret[i][y] *= 2;
						socre+=ret[i][y];
						break;
					}
				}
			}
		}
		if (side != SIDE.UP)
			ret = rotate(4 - side.ordinal(), ret);
		return ret;
	}
	
	private boolean canmerge(SIDE side, int[][] data_merge) {
		int[][] ret = data_merge;
		ret = rotate(side.ordinal(), ret);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int y = j - 1; y >= 0; y--) {
					if (ret[i][j] == ret[i][y]&&ret[i][j]!=0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void move(SIDE side) {
		data = addData(addData(move(side, clear2048(merge(side, data)))));
	}

	private int[][] move(SIDE side, int[][] data_move) {
		int[][] ret = copyData(data_move);
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
	
	private int[][] rotate(int times, int[][] data_r) {
		int[][] ret = copyData(data_r);
		for (int i = 0; i < times; i++) {
			ret = rotate(ret);
		}
		return ret;
	}
	
	private int[][] rotate(int[][] data_r) {
		int[][] old = copyData(data_r), ret = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				ret[size - j - 1][i] = old[i][j];
			}
		}
		return ret;
	}
	
	private List<Integer[]> getEmptyLoc(int[][] data_e){
		List<Integer[]> ret = new ArrayList<Integer[]>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(data_e[i][j]==0)
					ret.add(new Integer[]{i,j});
			}
		}
		return ret;
	}
	
	public boolean isGameOver(){
		if(getEmptyLoc(data).size()!=0) return false;
		//printData(move(SIDE.UP,data));
		if(canmerge(SIDE.UP, data)) return false;
		if(canmerge(SIDE.DOWN, data)) return false;
		if(canmerge(SIDE.LEFT, data)) return false;
		if(canmerge(SIDE.RIGHT, data)) return false;
		return true;
	}
	
	public int getScore(){
		return socre;
	}
}
