package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_아기상어 {

	static int N, cnt, time;
	static Shark s;
	static int[][] board;
	static int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
	static class Shark{
		int r, c, size, eat;

		public Shark(int r, int c, int size, int eat) {
			super();
			this.r = r;
			this.c = c;
			this.size = size;
			this.eat = eat;
		}

		@Override
		public String toString() {
			return "Shark [r=" + r + ", c=" + c + ", size=" + size + ", eat=" + eat + "]";
		}
	}
	/*
	 아기 상어의 초기 크기: 2
	 - 아기상어는 자신보다 큰 물고기가 있는 칸은 지나갈 수 없다. == 이동은 아기상어 >= 물고기
	 - 아기상어는 자신보다 작은 물고기만 먹을 수 있다. == 잡아먹기는 아기상어 > 물고기
	 즉, 크기가 같은 물고기를 먹을 순 없지만 그 물고기가 있는 칸은 지나갈 수 있다.
	 
	 - 물고기 먹기
	 먹을 수 있는 물고기가 1마리면 그 물고기 먹기
	 먹을 수 있는 물고기가 2마리 이상이면 가장 가까운 물고기 먹기
	 	- 거리: 아기상어-물고기 최단거리 ==> BFS 
	 	- 거리가 가까운 물고기가 많으면 가장 위-왼쪽 물고기 먹기
	 	
	 자신의 크기와 같은 수의 물고기를 먹을 때 크기 1 증가
	 
	 Q) 아기 상어가 엄마에게 도움을 요청하지 않고 물고기를 잡아먹을 수 있는 시간 출력 
	 2s, 메모리 넉넉
	 * */
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine()); // <= 20
		board = new int[N][N];
	
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {;
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 9) {
					s = new Shark(i, j, 2, 0);
					board[i][j] = 0; ///////////////////////////
				}
				else if(board[i][j] != 0) cnt += 1;
			}
		}
		
		while(true) {
			if(!bfs()) break;
		}
		
		System.out.println(time);
	}
	
	static boolean bfs() {
		// 최단거리 물고기 먹기 -> 주어진 조건 만족 위해 PQ 사용
		PriorityQueue<Shark> pq = new PriorityQueue<>((Shark f1, Shark f2) ->{
			if(f1.r == f2.r) return f1.c - f2.c;
			return f1.r - f2.r;
		});

		boolean[][] visited = new boolean[N][N];
		Queue<Shark> que = new ArrayDeque<>();
		visited[s.r][s.c] = true;
		que.add(s);
		int depth = 0;
		
		// 먹을 물고기까지 이동
		while(!que.isEmpty()) {
			int size = que.size();
			for (int i = 0; i < size; i++) {
				Shark cur = que.poll();
				
				for (int d = 0; d < 4; d++) {
					int nr = cur.r + dr[d];
					int nc = cur.c + dc[d];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= N || visited[nr][nc]) continue;
					if(board[nr][nc] > cur.size) continue; // 상어가 물고기보다 작으면 못지나감
					
					// 물고기가 상어보다 작으면 먹을 수 있음
					if(board[nr][nc] >= 1 && board[nr][nc] < cur.size) { 
						pq.add(new Shark(nr, nc, board[nr][nc], 0));
					}
					
					visited[nr][nc] = true;
					que.add(new Shark(nr, nc, cur.size, cur.eat)); // 아 왤케 풀어본 것 같지 이 부분 -> 미생물?
				}
			}
			depth += 1;
			
			if(!pq.isEmpty()) { // 먹은 물고기가 있으면
				Shark t = pq.poll();
				// 먹음
				board[t.r][t.c] = 0;
				s.eat += 1;
				
				// 이동
				s.r = t.r;
				s.c = t.c;
				time += depth; 
				
				if(s.eat == s.size) {
					s.size += 1; // 크기 = 먹은 물고기 수이면 크기 증가
					s.eat = 0; //////////////////////
				}
				return true;
			}
		}
		
		return false;
	}
}
