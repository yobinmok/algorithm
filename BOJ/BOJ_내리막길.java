package com.edu.week01;
import java.io.*;
import java.util.*;

public class BOJ_내리막길 {

	static int N, M;
	static int[][] board;
	static int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
	
	// 높이가 더 낮은 지점으로만 이동 가능 -> 경로 개수를 구해라.
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // <= 500
		M = Integer.parseInt(st.nextToken()); 
		
		board = new int[N][M];
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
	} 
}
