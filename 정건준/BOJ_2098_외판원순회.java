package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 * [문제]
 * 1번부터 N번까지 번호가 매겨진 도시가 있음
 * 도시마다 길이 있으며 길을 건너면 비용이 듬
 * 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 다시 원래의 도시로 돌아오는 최소 여행 비용 출력
 * 단. 한번 방문한 도시는 갈 수 없음(마지막 도시에서 출발지 도시로 돌아오는건 허용)
 *
 * N (도시의 수, 2 <= N <= 16)
 * W[i][j] (비용 행렬, 도시 i에서 j로 갈 수 있는 비용, 0이면 도시 i에서 j로 갈 수 없음)
 * 항상 순회할 수 있는 경우만 입력으로 주어짐
 *
 * [풀이]
 * dp[현재 방문한 도시][방문 배열 - int형]
 */


public class BOJ_2098_외판원순회 {
    static int N, END;
    static int[][] cost;
    static int[][] dp;
    static final int INF = 1000000000;

    static int TSP(int now, int visited) {
        //모든 노드 방문
        if(visited == END) {
            if(cost[now][0] > 0) return cost[now][0];
            return INF;
        }

        if(dp[now][visited] != 0) return dp[now][visited];
        dp[now][visited] = INF;

        for(int i = 0; i < N; i++){
            // 현재 노드에서 i번 노드로 가는 경로가 없으면 패스
            if(cost[now][i] == 0) continue;

            // 이미 방문한 노드면 패스
            if((visited & (1 << i)) > 0) continue;

            // i번 노드 방문 처리 후 최소 비용 반환
            int temp = TSP(i, visited | 1 << i);
            dp[now][visited] = Math.min(dp[now][visited], cost[now][i] + temp);
        }
        return dp[now][visited];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        END = (1 << N) - 1;
        cost = new int[16][16];
        dp = new int[16][1 << 16];

        for(int i=0; i<N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(TSP(0,1));
    }
}
