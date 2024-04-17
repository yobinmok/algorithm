package SWEA;

import java.io.*;
import java.util.*;

//16:45
public class SWEA_무선충전 {

	static int M, A, N = 10;
	static int[] moveA, moveB, dr = {0, -1, 0, 1, 0}, dc = {0, 0, 1, 0, -1};
	static List<BC> list;
	static List<Integer>[][] board;
	static class BC{
		int r, c, range, p;

		public BC(int r, int c, int range, int p) {
			super();
			this.r = r;
			this.c = c;
			this.range = range;
			this.p = p;
		}

		@Override
		public String toString() {
			return "BC [r=" + r + ", c=" + c + ", range=" + range + ", p=" + p + "]";
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("input.txt"));
		BufferedReader Br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(Br.readLine());
		
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(Br.readLine());
			
			M = Integer.parseInt(st.nextToken()); // 총 이동시간
			A = Integer.parseInt(st.nextToken()); // BC의 개수
			
			board = new ArrayList[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					board[i][j] = new ArrayList<>();
				}
			}
			
			moveA = new int[M+1];
			moveB = new int[M+1];
			st = new StringTokenizer(Br.readLine());
			moveA[0] = 0;
			for (int i = 1; i <= M; i++) {
				moveA[i] = Integer.parseInt(st.nextToken());
			}
			
			st = new StringTokenizer(Br.readLine());
			moveB[0] = 0;
			for (int i = 1; i <= M; i++) {
				moveB[i] = Integer.parseInt(st.nextToken());
			}
			
			list = new ArrayList<>();
			for (int i = 0; i < A; i++) {
				st = new StringTokenizer(Br.readLine());
				int c = Integer.parseInt(st.nextToken())-1;
				int r = Integer.parseInt(st.nextToken())-1;
				int range = Integer.parseInt(st.nextToken());
				int p = Integer.parseInt(st.nextToken());
				
				BC bc = new BC(r, c, range, p);
//				bfs(r, c, i, range);
				list.add(bc);
			}
			
			// 이동하기
			int score = 0;
			int[] a = {0, 0};
			int[] b = {9, 9};
			for (int i = 0; i <= M; i++) {
				int ar = a[0] + dr[moveA[i]];
				int ac = a[1] + dc[moveA[i]];
				int br = b[0] + dr[moveB[i]];
				int bc = b[1] + dc[moveB[i]];
				
				List<Integer> aBC = new ArrayList<>();
				List<Integer> bBC = new ArrayList<>();
				for (int j = 0; j < list.size(); j++) {
					BC ch = list.get(j);
					if(ch.range >= distance(ar, ac, ch.r, ch.c)) aBC.add(j);
					if(ch.range >= distance(br, bc, ch.r, ch.c)) bBC.add(j);
				}
				
				// 성능이 높은순으로 정렬
				Collections.sort(aBC, (Integer a1, Integer a2) -> {
					return list.get(a2).p - list.get(a1).p;
				});
				
				Collections.sort(bBC, (Integer a1, Integer a2) -> {
					return list.get(a2).p - list.get(a1).p;
				});
				
				if(aBC.size() != 0 && bBC.size() != 0) {
					// 두명 다 bc 위에 있는 경우
					if(aBC.get(0) == bBC.get(0)) { // 둘이 같은 bc에 있는 경우
						if(aBC.size() > 1 && bBC.size() > 1) {
							// 1순위는 같은 bc이므로 2순위 크기 비교
							if(list.get(aBC.get(1)).p > list.get(bBC.get(1)).p) {
								score += list.get(aBC.get(1)).p;
								score += list.get(bBC.get(0)).p;
							}else {
								score += list.get(aBC.get(0)).p;
								score += list.get(bBC.get(1)).p;
							}
						}else if(aBC.size() > 1) { // a만 2개 이상 겹쳐있을 때
							score += list.get(aBC.get(1)).p;
							score += list.get(bBC.get(0)).p;
						}else if(bBC.size() > 1) { // b만 2개 이상 겹쳐있을 때
							score += list.get(aBC.get(0)).p;
							score += list.get(bBC.get(1)).p;
						}else {
							score += list.get(aBC.get(0)).p;
						}
					}else { // 둘이 다른 bc에 있는 경우
						score += list.get(aBC.get(0)).p;
						score += list.get(bBC.get(0)).p;
					}
				}else if(aBC.size() != 0) { // a만 bc 위에 있는 경우
					score += list.get(aBC.get(0)).p;
				}else if(bBC.size() != 0) { // b만 bc 위에 있는 경우
					score += list.get(bBC.get(0)).p;
				}
				a[0] = ar; a[1] = ac;
				b[0] = br; b[1] = bc;
			}
			System.out.printf("#%d %d\n", t, score);
		}
	}
	
	static int distance(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) + Math.abs(c1 - c2);
    }
	
	static void bfs(int r, int c, int num, int range) {
		Queue<int[]> que = new ArrayDeque<>();
		que.add(new int[] {r, c});
		int depth = 0;
		
		while(!que.isEmpty()) {
			if(depth == range) break;
			int size = que.size();
			for (int i = 0; i < size; i++) {
				int[] cur = que.poll();
				
				for (int d = 0; d < 5; d++) {
					int nr = cur[0] + dr[d];
					int nc = cur[1] + dc[d];
					if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
					if(board[nr][nc].contains(num)) continue;
					
					board[nr][nc].add(num);
					que.offer(new int[] {nr, nc});
				}
			}
			depth += 1;
		}
		
	}
	
	static void print() {
		for (List<Integer>[] lists : board) {
			System.out.println(Arrays.toString(lists));
		}
	}
}
