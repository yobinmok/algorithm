package com.edu.week02;

import java.io.*;
import java.util.*;

// 16:10
public class SWEA_보호필름 {

	static int D, W, K, N, min;
	static int[][] film;
	
	// 보호 필름: 투명한 막을 D장 쌓아서 제작 -> [D][W] 크기의 2차원 배열
	// 막은 행이라고 생각하면 됨
	// 세로 특성이 중요 -> 모든 열에 대해 동일한 특정의 셀 들이 K개 이상 연속적으로 있는 경우에만 성능 검사 통과
	// 막에 A 약품을 넣으면 모든 특성이 A로 바뀜
	// 약품 투입 횟수의 최솟값을 구해라, 약품을 투약하지 않고도 성능 검사를 통과하는 경우는 0 출력
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			D = Integer.parseInt(st.nextToken()); // <= 13
			W = Integer.parseInt(st.nextToken()); // <= 20 
			K = Integer.parseInt(st.nextToken());
			
			film = new int[D][W];
			for (int i = 0; i < D; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < W; j++) {
					film[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// 약품 주입 -> dC0(초기 상태) ~ dCk-1
			min = K;
			recur(0, 0);
			
			sb.append("#" + t + " " + min + "\n");
		}
		System.out.println(sb);
	}
	
	static void recur(int n, int idx) {
		if(n == D) {
			if(testFilm()) {
				min = Math.min(min, idx);
			}
			return;
		}
		
		if(idx >= min) return;
		
		// 주입 X 
		recur(n+1, idx);
		
		int[] temp = film[n].clone();
		
		// 0 주입
		Arrays.fill(film[n], 0);
		recur(n+1, idx+1);
		
		// 1 주입
		Arrays.fill(film[n], 1);
		recur(n+1, idx+1);

		film[n] = temp;
	}

	static boolean testFilm() {
		L: for (int c = 0; c < W; c++) {
			int same = 1;
			for (int r = 0; r < D-1; r++) {
				if(film[r][c] == film[r+1][c]) same += 1;
				else same = 1;
				
				if(same >= K) continue L;
			}
			return false;
		}
		return true; // 성능 검사 통과 여부
	}
}
