package BOJ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_주사위굴리기 {

	static int N, M, r, c, K;
	static int[][] board;
	static int[] dice, dr = {0, 0, -1, 1}, dc = {1, -1, 0, 0};
	
	/*
	 지도 위에 주사위가 하나 있다.
	 처음에 주사위 모든 면에 0이 적혀있음
	 
	 주사위를 굴렸을 때
	 - 이동한 칸에 쓰여있는 수가 0이면 주사위 바단에 쓰인 수가 칸에 적힘: 주사위 -> 지도
	 - 0이 아니면 칸에 쓰인 수가 주사위 바다겡 적힘: 지도 -> 주사위, 지도 = 0
	 주사위가 이동했을 때마다 상단에 쓰여있는 값을 구해라.
	 바깥으로 이동하는 명령이 있을 경우 무시
	 
	 지도의 각 칸에는 10미만의 자연수 or 0
	 주사위 자료구조를 어떻게 해야되냐?
	 
	 * */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		dice = new int[6];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < K; i++) {
			int k = Integer.parseInt(st.nextToken());
			
		}
	}
}
