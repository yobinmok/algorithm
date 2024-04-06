package SWEA;
import java.io.*;
import java.util.*;

public class SWEA_벽돌깨기 {
	
	static int N, W, H, min;
	static int[][] board, copy;
	static int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1}, cases;
	static class Pos{
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	// N개의 구슬을 쏴 가장 많은 벽돌을 제거하는 경우를 구해라.
	// 완탐? 12*12*12*12(20,000) => 최대 경우의 수
	public static void main(String args[]) throws Exception{
		System.setIn(new FileInputStream("sample_input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++){
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); // <= 4
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			board = new int[H][W];
			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < W; j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			cases = new int[N];
			min = Integer.MAX_VALUE;
			// 1. recursive로 W^N 경우 구하기?
			recur(0);
			
			sb.append("#" + test_case + " " + min + "\n");
		}
		System.out.println(sb);
	}
	
	static void recur(int n) {
		if(n == N) {
			// 2. 구슬 쏘기
			copy = new int[H][W];
			for (int i = 0; i<H; i++) {
				copy[i] = board[i].clone();
			}
			
			for (int ball: cases) { // ball열의 첫 번째 벽돌 깨트림 -> 연쇄 작용 ㅇ
				// 벽돌 부수기
				int i;
				for(i = 0; i<H; i++) {
					if(copy[i][ball] != 0) break;
				}
				
				if(i == H) continue; // 한 열에 벽돌이 하나도 없는 경우
				Queue<Pos> crash = new ArrayDeque<>();
				crash.add(new Pos(i, ball));
				
				while(!crash.isEmpty()) {
					Pos cur = crash.poll();
					int size = copy[cur.r][cur.c];
					copy[cur.r][cur.c] = 0;
					
					for (int d = 0; d < 4; d++) {
						for (int j = 1; j < size; j++) {
							int nr = cur.r + dr[d] * j;
							int nc = cur.c + dc[d] * j;
							if(nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
							if(copy[nr][nc] == 0) continue;
							
							crash.add(new Pos(nr, nc));
						}
					}
				}
				
				for (int c = 0; c < W; c++) {
					for(int r = H-1; r>=0; r--) {
						if(copy[r][c] == 0) {
							for(int rr = r-1; rr>=0; rr--) {
								if(copy[rr][c] > 0) {
									copy[r--][c] = copy[rr][c];
									copy[rr][c] = 0;
								}
							}
							break;
						}
					}
				}
			}
			
			// 벽돌 세기
			int cnt = 0;
			for (int[] arr : copy) {
				for (int brick : arr) {
					if(brick != 0) cnt += 1;
				}
			}
			
			min = Math.min(cnt, min);
			return;
		}
		
		for(int i = 0; i<W; i++) {
			cases[n] = i;
			recur(n+1);
		}
	}
}
