package com.edu.week02;

import java.io.*;
import java.util.*;

public class SWEA_보급로 {

	static int N;
	static int INF = Integer.MAX_VALUE;
	static int[][] board, minDis;
	static int[] dr = {0, 1, 0, -1}, dc = {1, 0, -1, 0};
	static class Pos{
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	
	/*
	 도로가 파손된 상태 -> 복구작업을 빠르게 하려고 함
	 도로가 파여진 깊이에 비례해 복구시간 증가
	 복구시간이 가장 짧은 경로에 대한 복구 시간을 구해라.
	 
	 현재 위치한 칸의 도로를 복구해야만 다른 곳으로 이동 가능
	 
	 가중치 그래프 최단거리 -> 다익스트라(DP식)
	 	- 다익스트라: 시작정점에서 다른 모든 정점으로의 최단경로를 구함!
	 	- 프림/프루스칼(최소 신장 트리): 그래프 내의 모든 정점들을 가장 적은 비용으로 연결하기 위해 사용
	 	
	 "이동하는 시간에 비해 복구하는데 필요한 시간은 매우 크다고 가정한다.
	 따라서, 출발지에서 도착지까지 거리에 대해서는 고려할 필요가 없다." ? 이건 뭐야
	 
	 가중치 그래프 최단거리라는 건 알았는데 어떤 알고리즘이었는지는 까먹어서 정리해둔거 참고함
	 BFS, DFS는 뭐야 어케해
	 * */
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine()); // <= 100
			board = new int[N][N];
			for (int i = 0; i < N; i++) {
				String str = br.readLine();
				for (int j = 0; j < N; j++) {
					board[i][j] = str.charAt(j) - 48;
				}
			}
			
			dijkstra();
			System.out.println(minDis[N-1][N-1]);
		}
	}

	
//	1. 그래프, visited 배열, 최소거리 배열 선언 및 초기화
//	2. 미방문 정점 중 출발지에서 가장 가까운 정점 선택 → 경유지처럼 사용
//	3. 미방문 정점들에 대해 선택된 경유지를 거쳐가는 비용과 기존 최소비용 비교해 업데이트 → 경유지를 들러 가는 것이 더 작다면 바꿔주기
//	    ⇒ 모든 정점이 다 처리될 때까지 반복
	
//	3. 현재 위치에서 사방을 탐색해서 갈 수 있는 곳이라면 다음 위치의 dp값과 (현재 위치의 dp값 + 다음 위치 복구시간)을 비교해서 
//	다음 위치의 dp값이 더 크다면 다음 위치를 deq에 담고 다음 위치의 dp값을 (현재 위치의 dp값 + 다음위치복구시간)으로 갱신시켜 준다.
//	4. 그렇게 함수 실행이 끝나고 나면, dp의 도착지 위치에는 도착지까지 가는 최단 시간이 담기게 된다.
//	https://january-diary.tistory.com/entry/SWEA-1249-%EB%B3%B4%EA%B8%89%EB%A1%9C-JAVA
	static void dijkstra() {
		// 1. 거리 배열 -> 무한대로 초기화 & (방문 배열도)
		boolean[][] visited = new boolean[N][N];
		minDis = new int[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(minDis[i], INF);
		}
		
		visited[0][0] = true;
		minDis[0][0] = 0;
		
		Queue<Pos> que = new ArrayDeque<>();
		que.add(new Pos(0, 0));
		
		while(!que.isEmpty()) {
			Pos cur = que.poll();
			for (int d = 0; d < 4; d++) {
				int nr = cur.r + dr[d];
				int nc = cur.c + dc[d];
				
				if(nr < 0 || nr >= N || nc < 0 || nc >= N || visited[nr][nc]) continue;
				
				visited[nr][nc] = true;
				// 다음 위치의 dp값, 현재 위치의 dp값 + 다음 위치 시간 중 최소
				minDis[nr][nc] = Math.min(minDis[nr][nc], minDis[cur.r][cur.c] + board[nr][nc]);
				que.offer(new Pos(nr, nc));
			}
		}
//		for (int r = 0; r < N; r++) {
//			for (int c = 0; c < N; c++) {
//				for (int d = 0; d < 4; d++) {
//					int nr = r + dr[d];
//					int nc = c + dc[d];
//					
//					if(nr < 0 || nr >= N || nc < 0 || nc >= N || visited[nr][nc]) continue;
//					
//					visited[nr][nc] = true;
//					// 다음 위치의 dp값, 현재 위치의 dp값 + 다음 위치 시간 중 최소
//					minDis[nr][nc] = Math.min(minDis[nr][nc], minDis[r][c] + board[nr][nc]);
//				}
//			}
//		}
		
		for (int[] arr : minDis) {
			System.out.println(Arrays.toString(arr));
		}
	}
}
