package BOJ;

import java.io.*;
import java.util.*;

// 19:25
public class Main {

	static int min, N = 10;
	static int[] colored;
	static int[][] board;
	static List<Pos> list;
	static class Pos{
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		min = Integer.MAX_VALUE;
		board = new int[N][N];
		list = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 1) list.add(new Pos(i, j));
			}
		}
		
		if(list.size() != 0) {
			colored = new int[6];
			Arrays.fill(colored, 5);
			recur(0, 0);
		}else min = 0;
		
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}
	
	static void print() {
		for (int[] a : board) {
			System.out.println(Arrays.toString(a));
		}
		System.out.println();
	}
	
	static void recur(int idx, int num) {
		if(num >= min) return; // 가지치기
		if(idx == list.size()) { 
			min = Math.min(min, num);
			print();
			return;
		}
		
		Pos cur = list.get(idx);
		if(board[cur.r][cur.c] != 1) {
			recur(idx+1, num);
			return;
		}
		
		for (int size = 1; size <= 5; size++) {
			if(colored[size] == 0) continue; // 색종이를 다 사용했으면 넘어가기
			if(cur.r + size > N || cur.c + size > N) continue;
			if(!isAttachable(cur.r, cur.c, size)) continue;
			
			attach(cur.r, cur.c, size, size);
			colored[size] -= 1;
			recur(idx + 1, num + 1);
			colored[size] += 1;
			attach(cur.r, cur.c, size, 1);
		}
	}
	
	// 붙일 수 있는지 확인
	static boolean isAttachable(int r, int c, int size) {
		for (int i = r; i < r+size; i++) {
			for (int j = c; j < c+size; j++) {
				if(board[i][j] != 1) return false;
			}
		}
		return true;
	}
	
	static void attach(int r, int c, int size, int color) {
		for (int i = r; i < r+size; i++) {
			for (int j = c; j < c+size; j++) {
				board[i][j] = color;
			}
		}
	}
	
}

