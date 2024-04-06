package SWEA;
import java.io.*;
import java.util.*;

public class SWEA_수영대회결승전 {

	static int N;
	static int[][] board;
	static Pos start, end;
	static int[] dr = {1, -1, 0, 0}, dc = {0, 0, 1, -1};
	static class Pos{
		int r, c, cnt;

		public Pos(int r, int c, int cnt) {
			super();
			this.r = r;
			this.c = c;
			this.cnt = cnt;
		}

		@Override
		public String toString() {
			return "Pos [r=" + r + ", c=" + c + ", cnt=" + cnt + "]";
		}
	}
	
	// board 안에서 가장 빠른 길 -> 최단 거리
	// 소용돌이는 2초 유지 -> 1초 잠잠 반복 
	// => 한 번 지나간 소용돌이 위에서는 머물러 있을 수 있음 : 이 다음 소용돌이를 가기위해 기다려도 된다는 의미일듯?
	// 도착하지 않는다면 -1 리턴
	
	// BFS 문제
	// 소용돌이는 현재 cnt에 따라 소용돌이를 지나갈 수 있는지, 몇 초를 기다려야 하는지를 따져 처리해주기
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for(int test_case = 1; test_case <= T; test_case++){
			N = Integer.parseInt(br.readLine());
			
			board = new int[N][N];
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			st = new StringTokenizer(br.readLine());
			start = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0);
			
			st = new StringTokenizer(br.readLine());
			end = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0);
			
			System.out.printf("#%d %d\n", test_case, bfs(start, end));
			
		}
	}

	static int bfs(Pos start, Pos end) {
		boolean[][] visited = new boolean[N][N];
		Queue<Pos> que = new ArrayDeque<>();
		visited[start.r][start.c] = true;
		que.add(start);
		
		while(!que.isEmpty()) {
			Pos cur = que.poll();
			if(cur.r == end.r && cur.c == end.c) {
				return cur.cnt;
			}
			
			for (int d = 0; d < 4; d++) {
				int nr = cur.r + dr[d];
				int nc = cur.c + dc[d];
				
				if(nr < 0 || nr >= N || nc < 0 || nc >= N ) continue;
				if(visited[nr][nc] || board[nr][nc] == 1) continue;
				
				// 소용돌이를 지나가는 경우
				// 0 1 (2) 3 4 (5) 6 7 (8)
				if(board[nr][nc] == 2) { // 소용돌이를 이미 지난 경우를 board에 어떤 표시를 해줘야되나? 그냥 visited로도 가능?
					if(cur.cnt % 3 == 2) { // 소용돌이를 지날 수 있을 때
						visited[nr][nc] = true;
						que.add(new Pos(nr, nc, cur.cnt + 1));
					}else { // 지날 수 없을 때는 기다리기
						que.add(new Pos(cur.r, cur.c, cur.cnt + 1));
					}
				}else {
					visited[nr][nc] = true;
					que.add(new Pos(nr, nc, cur.cnt + 1));
				}
			}
		}
		// 좀 꼬인 것 같냐..
		return -1;
	}
}

