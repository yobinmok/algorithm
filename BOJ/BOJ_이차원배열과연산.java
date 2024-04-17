package BOJ;

import java.io.*;
import java.util.*;
public class BOJ_이차원배열과연산 {

	static int r, c, k, R, C;
	static int[][] arr;
	
	/*
	 3 * 3 배열 -> 인덱스는 1부터
	 1초가 지날때마다 배열에 연산 적용
	 
	 R 연산: 모든 행 정렬, 행 >= 열인 경우
	 C 연산: 모든 열 정렬, 행 < 열인 경우
	 
	 수의 등장 횟수가 커지는 순으로, 등장 횟수가 같으면 작은수부터 정렬
	 정렬 결과를 배열에 넣을 때는 수-등장 횟수 순으로 넣는다.
	 
	 정렬 결과를 배열에 다시 넣으면 행, 열의 크기가 변한다.
	 R 연산이 적용되면 가장 큰 행을 기준으로 모든 행의 크기가 변하고, C 연산의 경우 열도 마찬가지이다.
	 커진 곳에는 0이 채워짐 -> default 이므로 채우는 건 신경쓰지 않아도 됨
	 수 정렬 시 0은 무시
	 
	 100개를 넘어가면 처음 100개를 제외한 나머지는 버린다.
	 a[r][c]값이 k가 되기 위한 최소 시간을 구해라.
	 100초가 지나도 값을 구할 수 없으면 -1 출력
	 
	 * */
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		r = Integer.parseInt(st.nextToken())-1;
		c = Integer.parseInt(st.nextToken())-1; // <= 100
		k = Integer.parseInt(st.nextToken());

		arr = new int[100][100];
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 3; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int time = 0;
		R = 3; C = 3; // 행, 열의 최댓값
		while(true) {
			if(arr[r][c] == k) break;
			if(time > 100) {
				time = -1;
				break;
			}
			// 정렬
			if(R >= C) { // R 연산
				sortR();
			}else { // C 연산
				sortC();
			}
			time += 1;
		}
		System.out.println(time);
	}
	
	// Map -> 숫자: 등장 횟수 ===> 등장횟수 오름차순 -> 수 오름차순
	// 1. for문 돌며 map에 값 넣어주기
	// 2. map 내 값을 pq에 넣어주기
	// 3. 정렬된 값을 다시 배열에 넣어주기
	static void sortR() { // R은 고정이고 C만 가변
		PriorityQueue<int[]> pq = new PriorityQueue<>((int[] a1, int[] a2) -> {
			if(a1[1] == a2[1]) return a1[0] - a2[0];
			return a1[1] - a2[1];
		});
		
		int[][] temp = new int[R][100];
		for (int r = 0; r < R; r++) {
			Map<Integer, Integer> map = new HashMap<>();
			for (int c = 0; c < C; c++) {
				if(arr[r][c] == 0) continue;
				map.put(arr[r][c], map.getOrDefault(arr[r][c], 0)+1);
			}
			for (int num: map.keySet()) {
				pq.add(new int[] {num, map.get(num)});
			}
			
			int size = pq.size() > 100 ? 100 : pq.size()*2;
			C = Math.max(C, size); 
			for(int c = 0; c < size; c+=2) {
				int[] p = pq.poll();
				temp[r][c] = p[0];
				temp[r][c+1] = p[1];
			}
			
			arr[r] = temp[r].clone();
		}
	}
	
	static void sortC() { // R는 가변이고 C만 고정
		PriorityQueue<int[]> pq = new PriorityQueue<>((int[] a1, int[] a2) -> {
			if(a1[1] == a2[1]) return a1[0] - a2[0];
			return a1[1] - a2[1];
		});
		
		int[][] temp = new int[100][C];
		for (int c = 0; c < C; c++) {
			Map<Integer, Integer> map = new HashMap<>();
			for (int r = 0; r < R; r++) {
				if(arr[r][c] == 0) continue;
				map.put(arr[r][c], map.getOrDefault(arr[r][c], 0)+1);
			}
			
			for (int num: map.keySet()) {
				pq.add(new int[] {num, map.get(num)});
			}

			int size = pq.size() > 100 ? 100 : pq.size()*2;
			R = Math.max(R, size);
			for(int r = 0; r < size; r +=2) {
				int[] p = pq.poll();
				temp[r][c] = p[0];
				temp[r+1][c] = p[1];
			}
			
			for (int r = 0; r < 100; r++) {
				arr[r][c] = temp[r][c];
			}
		}
	}
}
