package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_미세먼지안녕 {

	static int N, M, T, bottom;
	static int[][] board;
	static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1}, // 시계
			rr = {1, 0, -1 ,0}, rc = {0, 1, 0, -1}; // 반시계 -> 시계 회전 시 사용하면 됨
	static class Pos{
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	
	/*
	 1. 미세먼지 확산 -> BFS 사용
	 	- 먼지/5만큼 사방으로 확산
	      => 같은 칸으로 확산되면 더해진 값이 저장됨
	 	- 원래 위치 먼지도 줄어듦 org -= org/5 * (확산된 방향 개수)
	 	
 	 2. 공기청정기 작동
 	 	- 위는 반시계
 	 	- 아래는 시계
 	 	
 	T초가 지난 후 남은 미세먼지 양
 	
 	[해결해야 할 문제]
 	- 기존에 있던 먼지가 아니라 퍼진 먼지를 어케 구별? 더할 놈들을 리스트에 담는 것도 괜찮은듯? --> 안괜찮아
	 * */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
	
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == -1) bottom = i;
			}
		}
		for(int t = 0; t<T; t++) {
			// 미세먼지 확산
			dustMove();
			// 공기청정기 작동
			clean();
		}
		
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				cnt += board[i][j];
			}
		}
		System.out.println(cnt + 2);
	}
	
	static void clean() {
		int tr = bottom-1, tc = 0, d = 0;
		
		while(d < 4) {
			int nr = tr + dr[d];
			int nc = tc + dc[d];
			if(nr >= 0 && nr < bottom && nc >= 0 && nc < M) {
				swap(tr, tc, nr, nc);
				tr = nr;
				tc = nc;
			}else d += 1;
		}
		board[bottom-1][1] = 0;
		
		// 하단
		int br = bottom, bc = 0;
		d = 0;

		while(d < 4) {
			int nr = br + rr[d];
			int nc = bc + rc[d];
			if(nr < N && nr >= bottom && nc >= 0 && nc < M) {
				swap(br, bc, nr, nc);
				br = nr;
				bc = nc;
			}else d += 1;
		}
		board[bottom][1] = 0;
	}
	
	static void swap(int r1, int c1, int r2, int c2) {
		int temp = board[r1][c1];
		board[r1][c1] = board[r2][c2];
		board[r2][c2] = temp;
	}
	
	static void dustMove() {
		int[][] prev = copy();
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				int cnt = 0;
				if(prev[r][c] <= 0) continue;
				for (int d = 0; d < 4; d++) {
					int nr = r + dr[d];
					int nc = c + dc[d];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= M || board[nr][nc] == -1) continue;
					
					cnt += 1;
					board[nr][nc] += prev[r][c]/5;
				}
				board[r][c] -= prev[r][c]/5*cnt;
			}
		}
	}
	
	static int[][] copy() {
		int[][] temp = new int[N][M];
		for (int i = 0; i < N; i++) {
			temp[i] = board[i].clone();
		}
		return temp;
	}
	
	static void print() {
		for (int[] arr : board) {
			System.out.println(Arrays.toString(arr));
		}
		System.out.println();
	}
	
}
