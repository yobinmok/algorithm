package com.edu.week02;

import java.io.*;
import java.util.*;

public class SWEA_숫자만들기 {

	static int N, max, min;
	static int[] op, num, order;
	static boolean[] selected;
	
	// 숫자 위치 정해짐
	// 연산자를 넣어 결과 찾기
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("input (1).txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 1; t <= T; t++) {
			N = Integer.parseInt(br.readLine());
			
			op = new int[4];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 4; i++) {
				op[i] = Integer.parseInt(st.nextToken());
			}
			
			num = new int[N];
			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				num[i] = Integer.parseInt(st.nextToken());
			}
			
			order = new int[N-1]; // 연산자 순서
			selected = new boolean[N-1];
			max = -1000000000;
			min = 1000000000;
			
			perm(0);
					
			System.out.printf("#%d %d\n", t, max - min);
		}
	}
	
	static void perm(int n) {
		if(n == N-1) {
			int result = calc();
			max = Math.max(result, max);
			min = Math.min(result, min);
			return;
		}
		
		for (int i = 0; i < 4; i++) {
			if(op[i] == 0) continue;
			op[i] -= 1;
			order[n] = i;
			perm(n+1);
			op[i] += 1;
		}
	}
	
	static int calc() {
		int result = num[0];
		int idx = 1;
		for (int oper : order) {
			switch(oper) {
			case 0:
				result += num[idx++];
				break;
			case 1:
				result -= num[idx++];
				break;
			case 2:
				result *= num[idx++];
				break;
			case 3:
				result /= num[idx++];
				break;
			}
		}
		
		return result;
	}
}
