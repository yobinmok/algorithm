package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_맥주마시면서걸어가기 {
	
	static class Pos{
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	
	// 시작점 -> 끝점
	// 편의점을 들르는 모든 경우의 수 확인?
	// 편의점을 들르는 순서, 들르는 편의점 수 등 너무 복잡하지 않나?
	// 근데 다른 방법을 모르겠다면..
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 0; t < T; t++) {
			int N = Integer.parseInt(br.readLine()); // 맥주를 파는 편의점의 개수
			
			// 0: 상근이 집, N+1: 페스티벌
			Pos[] conv = new Pos[N+2];
			for (int i = 0; i < N+2; i++) {
				st = new StringTokenizer(br.readLine());
				conv[i] = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			
			String ans = "sad";
			boolean[] visited = new boolean[N+2];
			Queue<Integer> que = new ArrayDeque<>();
			
			que.add(0);
			visited[0] = true;
			
			while(!que.isEmpty()) {
				int idx = que.poll();
				Pos cur = conv[idx];
				
				if (idx == N+1) {
					ans = "happy";
					break;
				}
				
				for (int i = 1; i < N+2; i++) {
					if(visited[i] || idx == i) continue;
					if(getDis(cur.r, cur.c, conv[i].r, conv[i].c) <= 1000) {
						que.add(i);
						visited[i] = true;
					}
				}
			}
			
			sb.append(ans + "\n");
		}
		System.out.println(sb);
	}
	
	static int getDis(int r1, int c1, int r2, int c2) {
		return Math.abs(r1-r2) + Math.abs(c1-c2);
	}
}
