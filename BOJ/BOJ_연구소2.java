package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_연구소2 {

	static int N, M, min, size;
	static int[][] board;
	static boolean possible, selected[];
	static List<Pos> virus_pos;
	static int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
	static class Pos{
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}

		@Override
		public String toString() {
			return "Pos [r=" + r + ", c=" + c + "]";
		}
	}
	
	/*
	 바이러스가 모두 퍼지는 최단 시간
	 최단 + n=50이므로 BFS 
	 * */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // <= 50
		M = Integer.parseInt(st.nextToken()); // 바이러스 개수
		
		board = new int[N][N];
		virus_pos = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 2) {
					virus_pos.add(new Pos(i, j));
					board[i][j] = 0;
				}else if(board[i][j] == 1) size -= 1;
			}
		}
		
		size += N * N - M; // 전체크기-벽-바이러스 개수
		possible = false;
		min = Integer.MAX_VALUE;
		selected = new boolean[virus_pos.size()];
		
		dfs(0, 0);
		
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}
	
	static void dfs(int n, int idx) {
		if(n == M) {
			// 바이러스 놓을 위치를 다 골랐으면
			int time = bfs();
			if(time != -1) {
				min = Math.min(min, time);
				possible = true;
			}
			return;
		}
		
		for(int i = idx; i<virus_pos.size(); i++) {
			selected[i] = true;
			dfs(n+1, i+1);
			selected[i] = false;
		}
		
	}
	static int bfs() {
		int[][] visited = new int[N][N];
		Queue<Pos> que = new ArrayDeque<>();
		
		for (int i = 0; i < selected.length; i++) {
			if(!selected[i]) continue;
			Pos v = virus_pos.get(i);
			que.add(v);
			visited[v.r][v.c] = 1;
		}
		
		int time = 0;
		int cnt = 0;
		
		while(!que.isEmpty()) {
			int size = que.size();
			for (int i = 0; i < size; i++) {
				Pos virus = que.poll();
				
				for (int d = 0; d < 4; d++) {
					int nr = virus.r + dr[d];
					int nc = virus.c + dc[d];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= N ) continue;
					if(board[nr][nc] == 1 || visited[nr][nc] >= 1) continue;
					
					cnt += 1;
					visited[nr][nc] = time+1;
					que.offer(new Pos(nr, nc));
				}
			}
			time += 1;
		}
		
		if(size - cnt == 0) return time-1;
		else return -1;
	}
	
	static void print(int[][] visited) {
		for (int[] v : visited) {
			System.out.println(Arrays.toString(v));
		}
		System.out.println();
	}
}
