package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * [문제 요약]
 * 한 도시에서 다른 모든 도시를 거쳐 다시 돌아올 때 까지의 최소 비용
 *
 * [제약 조건]
 * 2 ≤ N ≤ 16
 * 0 <= w[i][j] <= 1,000,000 이하
 *
 * [문제 설명]
 * 일반적인 풀이는 순열을 구하는 것임
 * 하지만, 16!로 시간 복잡도 내에 불가능함
 *
 * 제목처럼 전형적인 외판원 순회 문제임
 *
 * 외판원 순회는 dp와 비트 마스킹을 이용한 풀이 방법임
 *
 * dp는 이차원 배열 형태임
 * dp[집합][현재노드]에는 집합을 거쳐 현재노드까지 가기위한 최소비용이 저장되어 있음
 * 집합은 비트마스킹으로 표현되어 있음
 * 예를 들어, dp[6][3]은 1, 2, 3을 거쳐 현재 3번 노드 위치에 있다는 뜻음
 *
 * 1->2->3->5->4->1로 가는 경로가 있다고 하겠음
 * 또,1->3->2->5->4->1로 가는 경로가 있다고 하겠음
 * 이 두 경로는 5->4->1 경로가 중첩됨
 * 즉, 두 개의 경로 모두 5까지 오면 그 다음 길은 모두 동일함
 * 첫 번째 경로는 1->2->3->5
 * 두 번째 경로는 1->3->2->5가 됨
 * 이 경로를 비트마스킹으로 집합 표현하면 10111(2)가 됨
 * 즉, dp[10111][5]에는 5->4->1로 가는 최소 경로가 저장되어 있기 때문에 중복 연산을 피할 수 있음
 *
 * 하지만 55%에서 시간초과 발생
 * 마지막 노드에서 시작도시로 갈 수 없는 경우(map[last][0] == 0)
 * 방문하지 않는 경우(NOT_VISITED)를 분리해야 함
 *
 * 마지막 노드에서 시작도시로 갈 수 없는 경우와 방문하지 않는 경우와 분리하지 않는다면
 * 마지막에 아마 방문 했지만, 나중에 방문하지 않는 경우로 판단하여 다시 방문하는 경우가 발생함
 * NOT_VISITED는 VISITED 보다 커야 가중치 갱신이 제대로 이뤄지므로 임의의 값 할당
 *
 *
 * */
public class BOJ_2098_외판원순회 {

    private static final int VISITED = 987_654_321;
    private static final int NOT_VISITED = VISITED * 2;

    private static int n;
    private static int[][] map, dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz;

        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        dp = new int[n][(1 << n)];

        for (int i = 0; i < n; i++) {
            stz = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(stz.nextToken());
            }
        }

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], NOT_VISITED);
        }

        System.out.println(tsp(0, 1));

        br.close();
    }

    private static int tsp(int node, int set) {

        if(set == (1 << n) - 1){
            if(map[node][0] == 0){
                return VISITED;
            }
            return map[node][0];
        }

        // 중복 계산을 방지하기 위해 -> 이미 최소 경로가 저장되어 있음
        if (dp[node][set] != NOT_VISITED) {
            return dp[node][set];
        }

        for (int next = 0; next < n; next++) {

            // 이미 방문한 곳이거나, 길이 없을 때
            if (((1 << next) & set) == 0 && map[node][next] != 0) {
                dp[node][set] = Math.min(dp[node][set], tsp(next, set | (1 << next)) + map[node][next]);
            }
        }

        return dp[node][set];
    }
}
