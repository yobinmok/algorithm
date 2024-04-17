package BOJ;

import java.io.*;
import java.util.*;

public class BOJ_괄호추가하기 {

	static int N, cnt, max;
	static char[] expr;
	static boolean[] selected;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		String str = br.readLine();		
		expr = new char[N];
		selected = new boolean[str.length()];
		for (int i = 0; i < str.length(); i++) {
			expr[i] = str.charAt(i);
		}
		
		// 가능한 괄호 개수: N/4개
		max = Integer.MIN_VALUE;
		for(int i = 0; i<=(N/2+1)/2; i++) {
			cnt = i; // 괄호가 cnt개인 경우 계산
			selected = new boolean[N];
			recur(0, 0);
		}
		calc();
		System.out.println(max);
	}
	
	// 조합이랑 비슷한 느낌으로..
	static void recur(int n, int idx) {
		if(n == cnt) {
			max = Math.max(max, calc());
			return;
		}
		
		for (int i = idx; i < N/2; i++) {
			selected[i*2] = true;
			recur(n+1, i+2);
			selected[i*2] = false;
		}
	}

	static int calc() {
		List<String> ex = new ArrayList<>();
		// 괄호 계산
		for (int i = 0; i < N; i++) {
			if(!selected[i]) {
				ex.add(Character.toString(expr[i]));
				continue;
			}
			int start = expr[i] - 48;
			if(expr[i+1] == '+') start += expr[i+2]-48;
			else if(expr[i+1] == '-') start -= expr[i+2]-48;
			else if(expr[i+1] == '*') start *= expr[i+2]-48;
			
			ex.add(Integer.toString(start));
			i += 2;
		}
		
		// 최종 식 계산
		int result = Integer.parseInt(ex.get(0));
		for (int i = 1; i < ex.size(); i+=2) {
			String n = ex.get(i);
			if(n.equals("+")) {
				result += Integer.parseInt(ex.get(i+1));
			}else if(n.equals("-")) {
				result -= Integer.parseInt(ex.get(i+1));
			}else if(n.equals("*")) {
				result *= Integer.parseInt(ex.get(i+1));
			}
		}
		
		return result;
	}         
}

