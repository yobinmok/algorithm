package BOJ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_주사위굴리기 {

	static int N, M, r, c, K;
	static int[][] board, dArr = { // 눈 별 방향별로 이동 시 바닥에 닿는 면
			{2, 3, 1, 4}, 
			{2, 3, 5, 0}, 
			{5, 0, 1, 4}, 
			{0, 5, 1, 4}, 
			{2, 3, 0, 5}, 
			{2, 3, 4, 1}};
	static int[] dr = {0, 0, -1, 1}, dc = {1, -1, 0, 0};
	
	/*
	 지도 위에 주사위가 하나 있다.
	 처음에 주사위 모든 면에 0이 적혀있음
	 
	 주사위를 굴렸을 때
	 - 이동한 칸에 쓰여있는 수가 0이면 주사위 바단에 쓰인 수가 칸에 적힘: 주사위 -> 지도
	 - 0이 아니면 칸에 쓰인 수가 주사위 바닥에 적힘: 지도 -> 주사위, 지도 = 0
	 주사위가 이동했을 때마다 상단에 쓰여있는 값을 구해라.
	 바깥으로 이동하는 명령이 있을 경우 무시
	 
	 지도의 각 칸에는 10미만의 자연수 or 0
	 * */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 모든 수는 0부터 시작한다. 인덱스 주의!
		// 동0 서1 북2 남3
		int[] dice = new int[6];
		int cur = 0; // 주사위 윗면
		int tr = r, tc = c;
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < K; i++) {
			int k = Integer.parseInt(st.nextToken())-1;
			
			int nr = tr + dr[k];
			int nc = tc + dc[k];
			if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
			
			// 주사위 굴리기
			int cur_bottom = dArr[cur][k]; // 주사위 윗면이 cur일 때 k 방향으로 돌렸을 때의 바닥
			if(board[nr][nc] == 0) { // 이동한 지도칸이 0이면 주사위 -> 지도
				board[nr][nc] = dice[cur_bottom];
			} else { // 이동한 지도칸이 0이 아니면 지도 -> 주사위, 지도 = 0
				dice[cur_bottom] = board[nr][nc];
				board[nr][nc] = 0;
			}
			
			// 이동 후 값 갱신
			cur = 5 - cur_bottom;
			tr = nr;
			tc = nc;
			System.out.println("현재 윗면: " + cur + ", 회전방향: " + k);
			System.out.println(Arrays.toString(dice));
			System.out.println(dice[cur]);
			sb.append(dice[cur] + "\n");
		}
		
		System.out.println(sb);
	}
//	{2, 3, 1, 4}, 
//	{2, 3, 5, 0}, 
//	{5, 0, 1, 4}, 
//	{0, 5, 1, 4}, 
//	{2, 3, 0, 5}, 
//	{2, 3, 4, 1}};
}
