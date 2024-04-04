package com.edu.week01;

import java.io.*;

public class BOJ_계단오르기 {

	// 연속된 3개의 계단을 밟아서는 안된다.
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); 
		
		int[] dp = new int[N+1];
		int[] s = new int[N+1];
		for(int i = 1; i <= N; i++) {
			s[i] = Integer.parseInt(br.readLine()); 
		}
		
		for(int i = 1; i <= N; i++) {
			if(i == 1) dp[i] = s[i];
			else if(i == 2) dp[i] = s[i-1] + s[i];
			else {
				// ?oxo, oxoo
				dp[i] = Math.max(dp[i-2], dp[i-3] + s[i-1]) + s[i];
			}
		}
		
		System.out.println(dp[N]);
	}
}