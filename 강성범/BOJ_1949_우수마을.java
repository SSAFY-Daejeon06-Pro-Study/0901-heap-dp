package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.*;
import java.util.PriorityQueue;


/*
 * [문제 요약]
 * 조건에 맞게 우수 마을을 선정하여 우수 마을의 인원 수 합이 최대가 되도록 하시오
 *
 * [제약 조건]
 * 1 ≤ N ≤ 10,000
 * 주민수 10,000 이하
 *
 * 최대 N/2의 마을이 만들어지고, 각 우수마을 인원 수가 10,000이라 할 때
 * 5,000*10,000 = 50,000,000 -> int 가능
 *
 * [조건]
 * 1. 우수 마을'로 선정된 마을 주민 수의 총 합을 최대로 해야 한다.
 * 2. 두 마을이 인접해 있으면 두 마을을 모두 '우수 마을'로 선정할 수는 없다.
 * 3. '우수 마을'로 선정되지 못한 마을은 적어도 하나의 '우수 마을'과는 인접해 있어야 한다.
 *
 * [문제 풀이]
 * 완탐 -> 파워셋
 * 10,000! -> 불가능
 *
 *
 * dfs를 활용한 dp
 * 리프노드 부터 해당 마을이 우수마을 일 때와 아닐 때를 구분해서 저장
 * dp[2][n]
 * 0 : 우수마을
 * 1 : 우수마을이 아님
 *
 * 현재 마을이 우수마을이면 자신의 자식 노드는 우수마을아 이니어야함
 * dp[0][n] = dp[1][child] + w
 *
 * 현재 마을이 우수마을이 아니면, 자식이 우수마을 일 수도 있고 아닐 수도 있음
 * dp[1][n] = max(dp[0][child], dp[1][child]);
 *
 * 최대
 * max = max(dp[0][0], dp[0][1]) -> 트리이기 때문에 임의로 루트는 0으로 상정함
 *
 *
 * */
public class BOJ_1949_우수마을 {
    private static int n;
    private static int[] num;
    private static int[][] dp;

    private static List<Integer>[] tree;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz;

        n = Integer.parseInt(br.readLine());
        num = new int[n];
        tree = new List[n];
        dp = new int[2][n];

        stz = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++){
            num[i] = Integer.parseInt(stz.nextToken());
            tree[i] = new ArrayList<>();
        }

        // 간선의 길이는 n-1개
        for(int i=0; i<n-1; i++){
            stz = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(stz.nextToken()) -1;
            int b = Integer.parseInt(stz.nextToken()) -1;

            tree[a].add(b);
            tree[b].add(a);
        }

        // 임의의 정점 0에서 시작
        boolean[] visited = new boolean[n];
        visited[0] = true;
        dfs(visited, 0);

        System.out.println(Math.max(dp[0][0], dp[1][0]));

        br.close();
    }

    private static void dfs(boolean[] visited, int node) {
        dp[0][node] = num[node]; // 자신이 우수 마을일 때 초깃값 저장

        for(int nn : tree[node]){
            if(visited[nn]) continue;

            visited[nn] = true;
            dfs(visited, nn);

            //우수마을일 때
            dp[0][node] += dp[1][nn];

            // 우수마을이 아닐 때
            dp[1][node] += Math.max(dp[0][nn], dp[1][nn]);
        }
    }
}
