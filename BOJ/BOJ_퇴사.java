package com.edu.week01;

import java.io.*;
import java.util.*;

public class BOJ_퇴사 {

	static int N, max;
	static Consult[] cons;
	static class Consult{
		int t, p;

		public Consult(int t, int p) {
			super();
			this.t = t;
			this.p = p;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		cons = new Consult[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			cons[i] = new Consult(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		
		int[] dp = new int[N+1];
		// i인 경우-> i번째 상담을 하고 돈을 버는 날을 기준으로 계산
		for (int i = 0; i < N; i++) {
			if(cons[i].t + i <= N) {
				dp[cons[i].t + i] = Math.max(dp[cons[i].t + i], dp[i] + cons[i].p);
			}
			dp[i+1] = Math.max(dp[i], dp[i+1]);
		}
		
		System.out.println(Arrays.toString(dp));
	}
	
}
