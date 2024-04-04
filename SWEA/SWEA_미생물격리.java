package com.edu.week02;

import java.io.*;
import java.util.*;

public class SWEA_미생물격리 {
	
	static int N, M, K;
	static int[][] board;
	static List<Micro> list;
	static int[] dr = {-1, 1, 0, 0}, dc = {0, 0, -1, 1};
	static class Micro{
		int r, c, n, d;

		public Micro(int r, int c, int n, int d) {
			super();
			this.r = r;
			this.c = c;
			this.n = n;
			this.d = d;
		}

		@Override
		public String toString() {
			return "Micro [r=" + r + ", c=" + c + ", n=" + n + ", d=" + d + "]";
		}
	}
	
	/*
	 N * N
	 가장 자리에는 약품이 칠해져 있음
	 군집은 1시간마다 이동방향으로 이동
	 - 가장자리에 도착하면 미생물 절반이 죽고, 방향 반대로 바뀜
	 - 두 군집이 한 셀에 모이면 군집이 합쳐짐
	 	- 미생물은 합쳐지고 방향은 미생물이 더 많은 곳의 방향
	 
	 Q) M시간 후 남아있는 미생물 총합
	 - 조건: 5초
	 
	 [고려사항]
	 - 군집 자료구조를 머로 하지? 그냥 board에 넣기 어어 그르자 -> 조졌다
	 1. 군집 리스트, board(인덱스로)에 모두 저장 
	 2. 이동 시 리스트 순회 후 다음 위치 pq에 담기
	 	이 때, 기존 위치 null로 바꿔주기
	 3. pq 우선순위는 미생물 수 -> 미생물이 더 많은 군집이 자리를 선점하므로 합쳐질 때 수 비교가 필요없음
	 * */
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		StringTokenizer st;
		
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken()); // <= 100
			M = Integer.parseInt(st.nextToken()); // 미생물 군집 <= 1000
			K = Integer.parseInt(st.nextToken()); //
		
			board = new int[N][N];
			list = new ArrayList<>();
			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int r = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int n = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken())-1;
				
				list.add(new Micro(r, c, n, d));
			}
			
			PriorityQueue<Micro> pq = new PriorityQueue<>((Micro m1, Micro m2) -> m2.n - m1.n );

			for (int time = 0; time < M; time++) {
				// 미생물 이동
				for (Micro m : list) {
					int nr = m.r + dr[m.d];
					int nc = m.c + dc[m.d];
					board[m.r][m.c] = 0; // 기존에 있던 미생물은 지워주기
					pq.add(new Micro(nr, nc, m.n, m.d));
				}
				
				list.clear();
				
				while(!pq.isEmpty()) {
					Micro cur = pq.poll();
					
					//  가장자리 벗어났을 경우
					if(cur.r == 0 || cur.r == N-1 || cur.c == 0 || cur.c == N-1) {
						cur.n /= 2;
						
						if(cur.d == 0 || cur.d == 2) cur.d += 1;
						else cur.d -= 1;
						
						if(cur.n != 0) {
							list.add(cur);
							board[cur.r][cur.c] = list.size(); // board에 군집 번호 찍기 -> 다른 군집이 있는지 확인하는 용
						}
					}else {
						if(board[cur.r][cur.c] == 0) { // 칸에 아무도 없을 경우 
							list.add(cur);
							board[cur.r][cur.c] = list.size();
						}else { // 한 칸에 여러 박테리아가 모였을 경우 
							// -> 어차피 가장 미생물이 많은 군집이 자리를 선점하므로 수 비교 필요 없다
							Micro m = list.get(board[cur.r][cur.c]-1);
							m.n += cur.n;
						}
					}
				}
				
			}
			
			int ans = 0;
			for (Micro m : list) {
				ans += m.n;
			}
			sb.append("#" + t + " " + ans + "\n");
		}
		System.out.println(sb);
	}
	
	static void print() {
		for (int[] arr : board) {
			System.out.println(Arrays.toString(arr));
		}
		System.out.println();
	}
}
