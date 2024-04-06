package SWEA;
import java.io.*;
import java.util.*;


public class SWEA_보급로 {

	static int N, min;
	static int INF = Integer.MAX_VALUE;
	static int[][] board, minDis;
	static int[] dr = {0, 1, 0, -1}, dc = {1, 0, -1, 0};
	static class Pos{
		int r, c, dis;

		public Pos(int r, int c, int dis) {
			super();
			this.r = r;
			this.c = c;
			this.dis = dis;
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
	static void dijkstra() {
		minDis = new int[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(minDis[i], INF);
		}
		minDis[0][0] = 0;
		
		PriorityQueue<Pos> pq = new PriorityQueue<>((Pos p1, Pos p2) -> p1.dis - p2.dis);
		pq.add(new Pos(0,0,0));
		
		while(!pq.isEmpty()) {
			Pos cur = pq.poll();

            if(cur.dis > minDis[cur.r][cur.c]) continue;
			
			for(int i=0; i< 4; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr <0 || nr >= N || nc < 0 || nc >= N) continue;
				
				//nr nc의 값이 현재 값보다 큰 경우만 탐색(갱신)
				int nextTime = cur.dis + board[nr][nc];
				if(minDis[nr][nc] > nextTime) {
					minDis[nr][nc] = nextTime;
					pq.add(new Pos(nr, nc, nextTime));
				}
			}
		}
	}
	
	static void bfs() {
		boolean[][] visited = new boolean[N][N];
		PriorityQueue<Pos> pq = new PriorityQueue<>((Pos p1, Pos p2) -> p1.dis - p2.dis);
		pq.add(new Pos(0, 0, 0));
		visited[0][0] = true;
		
		while(!pq.isEmpty()) {
			Pos cur = pq.poll();
			if(cur.r == N-1 && cur.c == N-1) {
				min = cur.dis;
				break;
			}
			
			for(int d = 0; d < 4; d++) {
				int nr = cur.r + dr[d];
				int nc = cur.c + dc[d];
				
				if(nr <0 || nr >= N || nc < 0 || nc >= N || visited[nr][nc]) continue;
				
				visited[nr][nc] = true;
				pq.offer(new Pos(nr, nc, cur.dis + board[nr][nc]));
			}
		}
	}
}
