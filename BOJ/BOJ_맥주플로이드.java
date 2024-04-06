package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_맥주플로이드 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 0; t < T; t++) {
			int N = Integer.parseInt(br.readLine()); // 맥주를 파는 편의점의 개수
			
			// 0: 상근이 집, N+1: 페스티벌
			int[][] node = new int[N+2][N+2];
			int[][] board = new int[N+2][N+2];
			for (int i = 0; i < N+2; i++) {
				for (int j = 0; j < N+2; j++) {
					board[i][j] = 100000;
				}
			}
			
			for (int i = 0; i < N+2; i++) {
				st = new StringTokenizer(br.readLine());
				node[i][0] = Integer.parseInt(st.nextToken());
				node[i][1] = Integer.parseInt(st.nextToken());
			}
			
			// 2차원 배열에 각 점 간 거리 저장하기
			for (int i = 0; i < N+2; i++) {
				for (int j = 0; j < N+2; j++) {
					if(getDis(node[i][0], node[i][1], node[j][0], node[j][1]) <= 1000) {
						board[i][j] = 1;
					}
				}
			}
			
			for (int k = 0; k < N+2; k++) {
				for (int i = 0; i < N+2; i++) {
					for (int j = 0; j < N+2; j++) {
						if(board[i][j] > board[i][k] + board[k][j]) {
							board[i][j] = board[i][k] + board[k][j];
						}
					}
				}
			}

			sb.append(board[0][N+1] < 100000 ? "happy\n" : "sad\n");
		}
		System.out.println(sb);
	}
	
	static int getDis(int r1, int c1, int r2, int c2) {
		return Math.abs(r1-r2) + Math.abs(c1-c2);
	}
}
