package BOJ;

import java.io.*;

public class BOJ_일로만들기 {

	// 주어진 수에 연산 3개를 적절히 사용해 1로 만들 때, 연산을 사용하는 횟수의 최솟값
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); // <= 10^6
		
		int[] dp = new int[N+1];
		
		for(int i = 2; i <= N; i++) {
			dp[i] = dp[i-1] + 1;
			if(i % 6 == 0) {
				dp[i] = Math.min(dp[i/3]+ 1, Math.min(dp[i/2]+1, dp[i]));
			}else if(i % 3 == 0) {
				dp[i] = Math.min(dp[i/3]+ 1, dp[i]);
			}else if(i % 2 == 0) {
				dp[i] = Math.min(dp[i/2]+ 1, dp[i]);
			}
		}
		
		System.out.println(dp[N]);
	}
}