package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_상어중학교 {

	static int N, M, score;
	static int[][] board;
	static boolean[][] visited;
	static int[] dr = {0, 1, 0, -1}, dc = {1, 0, -1, 0}; // 시계방향
	
	static class Block implements Comparable<Block>{
		int r, c, size, rainbow;

		public Block(int r, int c, int size, int rainbow) {
			super();
			this.r = r;
			this.c = c;
			this.size = size;
			this.rainbow = rainbow;
		}

		@Override
		public String toString() {
			return "Block [r=" + r + ", c=" + c + ", size=" + size + ", rainbow=" + rainbow + "]";
		}

		@Override
		public int compareTo(Block b) {
			if(b.size == this.size) {
				if(b.rainbow == this.rainbow) {
					if(b.r == this.r) return b.c - this.c;
					return b.r - this.r;
				}
				return b.rainbow - this.rainbow;
			}
			return b.size - this.size;
		}
	}
	
	/*
	 블록 -> 검은색, 무지개, 일반
	 - 일반 블록: M가지 색상
	 - 검은색 블록: -1
	 - 무지개 블록: 0
	 인접칸
	 
	 블록그룹 = 연결된 블록의 집합
	 - 그룹에는 일반 블록이 적어도 1개 존재 -> 색이 모두 같아야 함
	 - 검은색은 있으면 안됨, 무지개는 몇개든 상관 없음
	 - 그룹 내 블록 개수는 2 이상
	 - 모든 연결되어 있어야 함 -> 한 블록에서 다른 블록으로 이동이 가능해야 함
	 - 기준 블록 = 무지개가 아닌 블록 중 상단-좌측에 위치한 블록
	 
	 while(블록 그룹이 존재하는 동안)
	 	1. 크기가 가장 큰 블록그룹 찾기
	 	   : 포함된 무지개 블록이 많은 -> 기준 블록의 행이 가장 큼 -> 열이 가장 큼
	 	2. 블록 제거 -> score += 블록 수 ^ 2;
	 	3. 격자에 중력 작용
	 	4. 반시계 회전
	 	5. 중력 작용
	 * */
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		while(true) {
			PriorityQueue<Block> pq  = new PriorityQueue<>();
			// 1. 블록그룹 찾아 pq에 넣기 -> pq가 비어있으면 break;
			// 0이 인접한 경우 그냥 다 포함해서 처리 -> 어차피 가장 큰 그룹 하나만 쓰니까
			visited = new boolean[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(board[i][j] == 0 || board[i][j] == -1 || board[i][j] == 9) continue;
					if(visited[i][j]) continue;
					Block block = bfs(i, j);
					if(block != null) pq.offer(block);
				}
			}
			
			if(pq.isEmpty()) break;
			// 2. 가장 큰 블록 제거
			Block big = pq.poll(); // 제거된 코드는 9로 해야징
			score += Math.pow(big.size, 2);
			removeBlock(big.r, big.c);
			// 3. 중력 -> 반시계 -> 중력
			gravity();
			rotate();
			gravity();
		}
		
		System.out.println(score);
	}
	
	static void removeBlock(int r, int c) {
		Queue<int[]> que = new ArrayDeque<>();
		que.add(new int[] {r, c});
		int color = board[r][c]; 
		board[r][c] = 9;

		while(!que.isEmpty()) {
			int s = que.size();
			for (int i = 0; i < s; i++) {
				int[] cur = que.poll();
				
				for (int d = 0; d < 4; d++) {
					int nr = cur[0] + dr[d];
					int nc = cur[1] + dc[d];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
					if(board[nr][nc] == -1 || board[nr][nc] == 9) continue;
					if(board[nr][nc] != 0 && board[nr][nc] != color) continue;
					
					board[nr][nc] = 9;
					que.offer(new int[] {nr, nc});
				}
			}
		}
	}

	static Block bfs(int r, int c) {
		boolean[][] t_visited = new boolean[N][N];
		Queue<int[]> que = new ArrayDeque<>();
		que.add(new int[] {r, c});
		visited[r][c] = true;
		t_visited[r][c] = true;
		int color = board[r][c]; 

		int size = 0, rainbow = 0;
		while(!que.isEmpty()) {
			int s = que.size();
			for (int i = 0; i < s; i++) {
				int[] cur = que.poll();
				
				for (int d = 0; d < 4; d++) {
					int nr = cur[0] + dr[d];
					int nc = cur[1] + dc[d];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
					if(board[nr][nc] == -1 || board[nr][nc] == 9 || t_visited[nr][nc]) continue;
					if(board[nr][nc] != 0) {
						if(board[nr][nc] != color) continue;
						visited[nr][nc] = true; // 0이 아닌 경우에만 방문처리
					}else rainbow += 1;
					
					size += 1;
					t_visited[nr][nc] = true;
					que.offer(new int[] {nr, nc});
				}
			}
		}
		
		if(size == 0) return null;
		return new Block(r, c, size+1, rainbow);
	}
	
	static void gravity() {
		// 검은색 블록 제외하고 다른 블록이나 격자의 경계를 만날때까지 밑으로 내려감
		for (int c = 0; c < N; c++) {
			int bottom = N-1;
			Queue<Integer> que = new ArrayDeque<>();
			for (int r = N-1; r >= 0; r--) {
				// 남은 블록 큐에 담기
				if(board[r][c] == 9) continue;
				if(board[r][c] == -1) {
					while(!que.isEmpty()) {
						board[bottom--][c] = que.poll();
					}
					bottom = r-1;
					continue;
				}
				que.offer(board[r][c]);
				board[r][c] = 9;
			}
			
			// bottom 위로 쌓기 -> 큐 비우기
			while(!que.isEmpty()) {
				board[bottom--][c] = que.poll();
			}
		}
		
	}
	
	static void rotate() {
		// 반시계 회전
		int[][] temp = copy();
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				board[r][c] = temp[c][N-1-r];
			}
		}
	}

	static int[][] copy() {
		int[][] temp = new int[N][N];
		for (int i = 0; i < N; i++) {
			temp[i] = board[i].clone();
		}
		return temp;
	}
	
	static void print() {
		for (int[] a : board) {
			System.out.println(Arrays.toString(a));
		}
		System.out.println();
	}
}

//static Block bfs(int r, int c) {
//	boolean[][] t_visited = new boolean[N][N];
//	Queue<int[]> que = new ArrayDeque<>();
//	que.add(new int[] {r, c});
//	visited[r][c] = true;
//	t_visited[r][c] = true;
//	int color = board[r][c]; 
//
//	int size = 0, rainbow = 0;
//	while(!que.isEmpty()) {
//		int s = que.size();
//		for (int i = 0; i < s; i++) {
//			int[] cur = que.poll();
//			
//			for (int d = 0; d < 4; d++) {
//				int nr = cur[0] + dr[d];
//				int nc = cur[1] + dc[d];
//				
//				if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
//				if(board[nr][nc] == -1) continue;
//				if(board[nr][nc] != 0 && visited[nr][nc]) continue;
//				if(board[nr][nc] != 0 && board[nr][nc] != color) continue;
//				
//				if(board[nr][nc] == 0) rainbow += 1;
//				size += 1;
//				visited[nr][nc] = true;
//				que.offer(new int[] {nr, nc});
//			}
//		}
//	}
//	
//	if(size == 0) return null;
//	return new Block(r, c, size+1, rainbow);
//}
