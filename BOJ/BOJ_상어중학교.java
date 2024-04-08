package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_상어중학교 {

	static int N, M;
	static int[][] board;
	
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
		
		
	}
}
