package SWEA;
import java.io.*;
import java.util.*;

public class SWEA_장훈이의높은선반 {

	static int N, B, min;
	static int[] height;
	
	// 높이가 B인 선반
	// n명의 점원의 키를 합쳐 B보다 높은 값 중 가장 낮은 탑을 구해라..
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());
			
			height = new int[N];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				height[i] = Integer.parseInt(st.nextToken());
			}
			
			min = Integer.MAX_VALUE;
			recur(0, 0);
			System.out.printf("#%d %d\n", t, min-B);
		}
	}
	
	// 다 더해도 높이가 N보다 낮은 경우는 없겠지?
	static void recur(int n, int h) {
		if(h >= min) return;
		
		if(h >= B) {
			min = Math.min(min, h);
			return;
		}
		
		if(n == N) {
			return;
		}
		
		recur(n+1, h + height[n]); // n 번째 점원을 더한 경우
		recur(n+1, h); // n 번째 점원을 더하지 않은 경우

	}
}
