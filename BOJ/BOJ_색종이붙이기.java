package BOJ;

import java.io.*;
import java.util.*;

// 19:25
public class BOJ_색종이붙이기 {

	static int min, cnt, N = 10;
	static int[] colored;
	static int[][] board;
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
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 1) cnt += 1;
			}
		}
		
		if(cnt != 0) {
			colored = new int[6];
			Arrays.fill(colored, 5);
			recur(0, 0, cnt, 0);
		}else min = 0;
		
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}
	
	// cnt: 남은 1 개수, num: 붙인 색종이 개수
	static void recur(int r, int c, int cnt, int num) {
		if(num > min) return; // 가지치기
		if(cnt == 0) { 
			min = Math.min(min, num);
			System.out.println(num);
			print();
			return;
		}
		
		for (int i = r; i < N; i++) {
			for (int j = c; j < N; j++) {
				if(board[i][j] != 1) continue;
				for (int size = 1; size <= 5; size++) {
					if(colored[size] == 0) continue; // 색종이를 다 사용했으면 넘어가기
					
					if(!attach(i, j, size)) continue;

					colored[size] -= 1;
					recur(r, c, cnt-size*size, num + 1);
					colored[size] += 1;
					detach(i, j, size);
				}
			}
		}
	}
	
	static void print() {
		for (int[] a : board) {
			System.out.println(Arrays.toString(a));
		}
		System.out.println();
	}
	static boolean attach(int r, int c, int size) {
		for (int i = r; i < r+size; i++) {
			for (int j = c; j < c+size; j++) {
				if(board[i][j] == 0) {
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
				if(board[i][j] == 0) return;
				board[i][j] = 1;
			}
		}
	}
}
