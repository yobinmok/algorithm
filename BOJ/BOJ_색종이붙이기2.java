package BOJ;

import java.io.*;
import java.util.*;

// 19:25
public class BOJ_색종이붙이기2 {

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

	

	/*
	 브루트포스
	 
	 1이면 색종이 붙이기
	 - recur로 배열처리 해야 함 -> 넘길까 어쩔까
	 - 색종이 다 붙였는지 확인: cnt? 10*10 배열 돌긴 싫어
	 
	 1이 적힌 칸을 모두 붙이는 데 필요한 색종이의 최소 개수
	 */
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
	
//1 1 1 1 1 1 1 0 0 0
//1 1 1 1 1 1 1 0 0 0
//1 1 1 1 1 1 1 0 0 0
//1 1 1 1 1 1 1 0 0 0
//1 1 1 1 1 1 1 0 0 0
//1 1 1 1 1 0 0 0 0 0
//1 1 1 1 1 0 0 0 0 0
//1 1 1 1 1 0 0 0 0 0
//1 1 1 1 1 0 0 0 0 0
//0 0 0 0 0 0 0 0 0 0
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
			if(!attach(cur.r, cur.c, size)) continue;
			colored[size] -= 1;
			recur(idx + 1, num + 1);
			colored[size] += 1;
			detach(cur.r, cur.c, size);
		}
	}
	static boolean attach(int r, int c, int size) {
		for (int i = r; i < r+size; i++) {
			for (int j = c; j < c+size; j++) {
				if(board[i][j] != 1) {
					detach(r, c, size);
					return false; // 색종이 붙이기 실패
				}
				board[i][j] = 2;
			}
		}
		return true;
	}
	
	static void detach(int r, int c, int size) {
		for (int i = r; i < r+size; i++) {
			for (int j = c; j < c+size; j++) {
				if(board[i][j] == 2) board[i][j] = 1;
				else return;
			}
		}
	}
}
