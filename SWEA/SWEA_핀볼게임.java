package SWEA;

import java.io.*;
import java.util.*;

public class SWEA_핀볼게임 {
	
	static int N, max;
	static int[][] board;
	static Map<Integer, List<Pos>> warm;
	static int[] dr = {-1, 0, 1, 0}, dc = {0, 1, 0, -1};
	static class Pos{
		int r, c, d, score;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
		
		public Pos(int r, int c, int d, int score) {
			super();
			this.r = r;
			this.c = c;
			this.d = d;
			this.score = score;
		}

		@Override
		public String toString() {
			return "Pos [r=" + r + ", c=" + c + "]";
		}
		
	}
	/*
	 4가지 형태의 삼각형 블록(1,2,3,4), 정사각형 블록(5), 웜홀(6~10), 블랙홀(-1) 
	 - 수평면을 만나면 방향이 반대로 바뀌고, 경사면을 만나면 90도 꺾임
	 - 웜홀을 만나면 동일한 숫자를 가진 반대편 웜홀로 빠져나옴, 진행방향은 유지됨
	 - 블랙홀을 만나면 핀볼이 사라지며 게임 끝
	 게임은 핀볼이 블랙홀이 빠지거나 시작위치에 돌아오면 끝난다.
	 벽이나 블록에 부딪힌 횟수가 점수이다.
	 Q) 게임판 위 출발위치와 진행방향을 임의로 선정할 수 있을 때 게임에서 얻을 수 있는 점수 최댓값을 구해라 ㄷㄷ
	 
	 [조건]
	 - 웜홀은 반드시 쌍으로 존재하며 최대 5쌍 존재
	 - 블랙홀 최대 5개
	 - 블록, 홀이 있는 위치에서는 출발할 수 없음 -> 즉, 0에서만 출발 가능
	 
	 - 삼각블록: 경사면만 방향에 따라 다르게 처리하고 나머지는 다 반대로
	 - 사각형: 다 반대로
	 - 웜홀: 맵에 위치 저장 -> 어차피 2개니까 그냥 돌아
	 - 블랙홀: 끝
	 
	 ** 벽이나 블록에 부딪힐때만 점수 증가!
	 * */
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			max = 0;
			N = Integer.parseInt(br.readLine()); // <= 100
			
			board = new int[N][N];
			warm = new HashMap<>();
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					int n = Integer.parseInt(st.nextToken());
					board[i][j] = n;
					if(n >= 6) {
						if(warm.get(n) == null) warm.put(n, new ArrayList<>());
						warm.get(n).add(new Pos(i, j));
					}
				}
			}
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(board[i][j] != 0) continue;
					for (int d = 0; d < 4; d++) {
						move(i, j, d);
					}
				}
			}
			System.out.printf("#%d %d\n", t, max);
		}
	}
	
	static void move(int r, int c, int d) {
		int score = 0;
		int nr, nc;
		int tr = r, tc = c;
		while(true) {
			nr = tr + dr[d];
			nc = tc + dc[d];
			
			// 벽에 닿은 경우
			if(nr < 0 || nr >= N || nc < 0 || nc >= N) {
				d = changeDir(d, 5);
				score += 1;
			}else{
				if(nr == r && nc == c || board[nr][nc] == -1) {
					// 제 자리로 돌아오거나 블랙홀에 들어간 경우
					max = Math.max(max, score);
					break;
				}else if(board[nr][nc] >= 1 && board[nr][nc] <= 5) { // 블록에 닿은 경우
					d = changeDir(d, board[nr][nc]);
					score += 1;
				}else if(board[nr][nc] >= 6){ // 웜홀에 들어간 경우
					List<Pos> list = warm.get(board[nr][nc]);
					for (Pos p : list) {
						if(p.r != nr && p.c != nc) {
							nr = p.r;
							nc = p.c;
							break;
						}
					}
				}
			}
			tr = nr;
			tc = nc;
		}
	}
	
	// 여기 너무 맘에 안들어 
	static int changeDir(int d, int num) { // 현재 방향과 블록 번호
		if(num == 1) {
			if(d == 2) d = 1;
			else if(d == 3) d = 0;
			else num = 5; 
		}else if(num == 2){
			if(d == 0) d = 1;
			else if(d == 3) d = 2;
			else num = 5; 
		}else if(num == 3){
			if(d == 0) d = 3;
			else if(d == 1) d = 2;
			else num = 5; 
		}else if(num == 4){
			if(d == 2) d = 3;
			else if(d == 1) d = 0;
			else num = 5; 
		}
		
		if(num == 5){
			if(d == 0 || d == 1) d += 2;
			else d -= 2;
		}
		return d;
	}
}

