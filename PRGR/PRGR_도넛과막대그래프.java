package PRGR;

import java.util.*;

public class PRGR_도넛과막대그래프 {
    
    int N, cnt, MAX = 1000000;
    int[] answer;
    boolean[] vertex;
    Set<Integer> in, out;
    Map<Integer, List<Integer>> graph;
    
    public int[] solution(int[][] edges) {
        // edges(간선), 정점 <= 1,000,000
        // 정점 시작은 1
        answer = new int[4]; 
        graph = new HashMap<>();
        in = new HashSet<>();
        out = new HashSet<>();
        
        for(int[] edge: edges){
            out.add(edge[0]);
            in.add(edge[1]);
            List<Integer> temp = graph.getOrDefault(edge[0], new ArrayList<>());
            temp.add(edge[1]);
            graph.put(edge[0], temp);
        }
        
        // new: in에는 없고, graph[new] >= 2
        for(int v: out){
            if(!in.contains(v) && graph.get(v).size() >= 2){
                cnt = graph.get(v).size(); // 그래프 총 개수
                answer[0] = v;
                break;
            }
        }
        
        in.addAll(out); // in -> vertex 전체
        N = in.size(); // vertex 전체 개수
        vertex = new boolean[MAX+1];
        vertex[answer[0]] = true;
        // answer[도넛, 막대, 8자]
        for(int i: in){
            if(vertex[i]) continue;
            bfs(i);
        }
        
        answer[2] = cnt - answer[1] - answer[3];
        return answer;
    }
    
    void bfs(int v){
        Queue<Integer> que = new ArrayDeque<>();
        vertex[v] = true;
        que.add(v);
        boolean eight = false, donut = false;
        
        while(!que.isEmpty()){
            int cur = que.poll();
            List<Integer> cur_list = graph.get(cur);
            if(cur_list == null) continue;
            if(cur_list.size() >= 2) eight = true;
            
            for(int next: cur_list){
                if(next == v) donut = true;
                if(vertex[next]) continue; // 이미 방문한 곳이면 지나가기
               
                vertex[next] = true;
                que.add(next);
            }
        }
        
        if(eight) answer[3] += 1;
        else if(donut) answer[1] += 1;
    }
}

/*
그래프: 1개 이상의 정점, 단방향 간선
그래프 종류 3가지 -> 도넛, 막대, 8자 모양
1. 도넛: n개의 정점, n개의 간선 -> 간선을 따라가다 보면 출발점으로 돌아옴
=> 순회
2. 막대: n개의 정점, n-1개의 간선 -> 간선을 따라가면 n-1개의 정점을 한 번씩 방문하게 되는 정점이 1개! 존재
3. 8자 모양: 2n+1개의 정점, 2n+2개의 간선 -> 크기가 동일한 2개의 도넛모양 그래프에서 정점을 하나씩 골라 결합시킨 형태

Q. 그래프의 간선 정보가 주어지면 1)생성한 정점 번호, 정점을 생성하기 전 각 그래프의 수를 구해라....................?
return: [생성 정점 번호, 도넛그래프 수, 막대 그래프 수, 8자 그래프 수]

*** 새로운 정점에서는 모든 그래프들로의 간선이 있음

1. 새로운 정점 찾기: 진입이 없으면 새로운 정점
-> for문 돌면서 contains 확인
2. 정점 돌며... 어떤 그래프인지 확인하기 -> 진입, 진출을 따지기?
    => 그래프인걸 확인하면 boolean true 처리
  - 도넛: 간선을 따라갔는데 자기 자신이 나오면 도넛
  - 막대: 나머지
  - 8자 모양: 돌다 진출이 2개인 정점을 만나면? 진입과 진출이 2개인 경우
*/
