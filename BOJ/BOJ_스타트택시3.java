package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_스타트택시3 {

	static Pos start;
	static int[][] board;
	static List<Pos[]> customer;
	static int N, M, fuel, minDis;
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
	
	// 79% !!!!!!!!!!!!!!!!!!!!!!!!!!ㅠㅠㅠㅠㅠㅠㅠㅠ -> 1시간 반
	// 손님을 도착지로 데려다주면 연료 충전
	// 특정 위치로 이동할 때 항상 최단 경로 이동 -> BFS?
	// 한 승객을 태워 목적지로 이동 -> M번 반복
	// 현재 위치에서 최단거리가 가장 짧은 승객을 고름 -> 그런 승객이 여러명이면 행 번호-열 번호가 가장 작은 승객 고르기
	// 연료는 한 칸 이동할 때마다 1만큼 소모 -> 사용한 연료의 2배 충전
	// 모든 승객을 성공적으로 데려다 줄 수 있으면 남는 연료 출력, 
	// 연료가 바닥나거나 모든 손님을 이동시킬 수 없으면 -1 출력 -> 연료가 바닥나지 않아도 손님을 이동시킬 수 없는 경우 유의
	
	// BFS, 구현 
	// n <= 20, 시간은 1초
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // <= 20
		M = Integer.parseInt(st.nextToken());
		fuel = Integer.parseInt(st.nextToken()); // 초기연료 <= 500,000
		
		board = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken()); // 1은 벽
			}
		}
		
		st = new StringTokenizer(br.readLine());
		start = new Pos(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
		
		// 승객 자료구조를 뭐로 해야 좋을까. 계속 삭제해야 되니까 Linked가 나을 것 같은데 시간이 너무 오래걸려서 ㅠ
		// 최대 400갠데 으으으ㅡㅡㅇㅁ
		customer = new LinkedList<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			Pos[] temp = new Pos[2];
			temp[0] = new Pos(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
			temp[1] = new Pos(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
			customer.add(temp);
			board[temp[0].r][temp[0].c] = i+2;
		}
		
		int cnt = M;
		while(cnt > 0) {
			// 태울 승객 찾기
			Pos[] target = bfs(); // start에서 가장 가까운 승객 찾기
			if(target == null) break; // 태울 수 있는 승객이 없으면
			board[target[0].r][target[0].c] = 0;
			cnt -= 1;
			
			// 승객 위치로 이동
			fuel -= minDis;
			if(fuel < 0) break; // 연료가 바닥나면
			
			// 승객 운반 -> 승객 운반을 못하는 경우의 수는 고려 안하는거겠지!? => 고려함
			int dis = bfs(target[0], target[1]);
			if(fuel - dis < 0) break; // 연료가 바닥나면 
			fuel += dis;
			
			// 도착지로 이동
			start.r = target[1].r;
			start.c = target[1].c;
		}
		
		System.out.println(cnt > 0 ? -1 : fuel);
	}
	
	static Pos[] bfs() {
		PriorityQueue<Pos[]> pq = new PriorityQueue<>((Pos[] p1, Pos[] p2)->{
			if(p1[0].r == p2[0].r) return p1[0].c - p2[0].c;
			return p1[0].r - p2[0].r;
		});
		
		boolean[][] visited = new boolean[N][N];
		Queue<Pos> que = new ArrayDeque<>();
		visited[start.r][start.c] = true;
		que.add(start);
		int dis = 0;
		
		while(!que.isEmpty()) {
			int size = que.size();
			boolean flag = false;
			for (int i = 0; i < size; i++) {
				Pos cur = que.poll();
				
				if(board[cur.r][cur.c] != 0) {
					minDis = dis;
					pq.offer(customer.get(board[cur.r][cur.c]-2));
					flag = true;
				}
				
				for (int d = 0; d < 4; d++) {
					int nr = cur.r + dr[d];
					int nc = cur.c + dc[d];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
					if(visited[nr][nc] || board[nr][nc] == 1) continue;
					
					visited[nr][nc] = true;
					que.add(new Pos(nr, nc));
				}
			}
			dis += 1;
			if(flag) break;
		}
		return !pq.isEmpty() ? pq.poll() : null;
	}
	
	// 이동할 수 없으면 -1 반환
	static int bfs(Pos from, Pos to) {
		boolean[][] visited = new boolean[N][N];
		visited[from.r][from.c] = true;
		Queue<Pos> que = new ArrayDeque<>();
		que.add(from);
		int dis = 0; boolean possible = false;
		
		L: while(!que.isEmpty()) {
			int size = que.size();
			for (int i = 0; i < size; i++) {
				Pos t = que.poll();
				
				if(t.r == to.r && to.c == t.c) {
					possible = true;
					break L;
				}
				
				for (int d = 0; d < 4; d++) {
					int nr = t.r + dr[d];
					int nc = t.c + dc[d];
					
					// 이동 중 다른 승객이 있는 곳을 지나가는 경우는 고려 안하는거임? 일단 안하겠음..
					if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
					if(visited[nr][nc] || board[nr][nc] == 1) continue;
					
					visited[nr][nc] = true;
					que.add(new Pos(nr, nc));
				}
			}
			dis += 1;
		}
		
		return possible ? dis : -1;
	}
}
