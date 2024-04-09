package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_상어중학교 {

	static int N, M;
	static int[][] board;
	static int[] dr = {0, 1, 0, -1}, dc = {1, 0, -1, 0};
	static class Block{
		int r, c, size, rainbow;

		public Block(int r, int c, int size, int rainbow) {
			super();
			this.r = r;
			this.c = c;
			this.size = size;
			this.rainbow = rainbow;
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
	 	너무 더러운데요 -10분
	 * */
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		PriorityQueue<Block> pq  = new PriorityQueue<>((Block b1, Block b2) ->{
			if(b2.size == b1.size) {
				if(b2.rainbow == b1.rainbow) {
					if(b2.r == b1.r) return b2.c - b1.c;
					return b2.r - b1.r;
				}
				return b2.rainbow - b1.rainbow;
			}
			return b2.size - b1.size;
		});
		

		while(true) {
			// 1. 블록그룹 찾아 pq에 넣기 -> pq가 비어있으면 break;
			// 0이 인접한 경우 그냥 다 포함해서 처리 -> 어차피 가장 큰 그룹 하나만 쓰니까
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if(board[i][j] == -1 || board[i][j] == -1) continue;
					Block block = bfs(i, j);
					if(block != null) pq.offer(block);
				}
			}
			// 2. 가장 큰 블록 제거
			Block big = pq.poll(); // 제거된 코드는 9로 해야징
			
			// 3. 중력 -> 반시계 -> 중력
			// 중력 작용 시 검은 블록을 제외한 모든 블록이 아래로 이동, 다른 블록이나 경계를 만날때까지 이동
		}
	}
	
	static Block bfs(int r, int c) {
		boolean[][] visited = new boolean[N][N];
		Queue<int[]> que = new ArrayDeque<>();
		que.add(new int[] {r, c});
		visited[r][c] = true;

		int size = 0, rainbow = 0;
		while(!que.isEmpty()) {
			int s = que.size();
			for (int i = 0; i < s; i++) {
				int[] cur = que.poll();
				
				for (int d = 0; d < 4; d++) {
					int nr = cur[0] + dr[d];
					int nc = cur[1] + dc[d];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
					if(visited[nr][nc] || board[nr][nc] == -1) continue;
					
					if(board[nr][nc] == 0) rainbow += 1;
					size += 1;
					visited[nr][nc] = true;
					que.offer(new int[] {nr, nc});
				}
			}
		}
		
		if(size == 0) return null;
		return new Block(r, c, size, rainbow);
	}
}
