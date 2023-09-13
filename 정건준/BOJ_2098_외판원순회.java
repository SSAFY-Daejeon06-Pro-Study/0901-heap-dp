package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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
 * 최소 비용 외판원 순회 경로는 어떤 정점에서 시작하든 같음
 *
 * [무식하게 접근]
 * 한 정점에서 출발하는 모든 경로를 살펴봐야하므로 16! 경우의 수 발생 => 시간초과
 *
 * [dp 적용]
 * f(시작 정점, 방문 배열) = 시작 정점에서 출발했을 때, 아직 방문하지 않은 나머지 모든 정점을 순회하는 최소 비용
 * 재귀 함수 f가 반복 계산되고 있음, 메모이제이션 적용

 * [dp 배열을 어떻게 만들것인가? 즉. 입력 값이 시작 정점, 방문 배열을 받는 메모리 구조를 어떻게 만들것인가?]
 * 방문 배열은 이진수, 즉 하나의 정수(비트마스킹)로 표현할 수 있다. 따라서 메모이제이션 저장공간은 2차원 배열로 표현 가능
 * dp[0][00001(2)] = 0정점에서 출발했을 때, 아직 방문하지 않은(비트가 0인) 나머지 모든 정점을 순회하는 최소 비용
 *
 * [0정점이 1정점과 4정점과 연결되어있다고 가정한다면?]
 * dp[0][00001(2)] = min(dp[4][10001(2)] + w[0][4], dp[2][00011(2)] + w[0][2]
 *
 * (주의사항 1)
 * f(x, 11111)의 답을 계산할 때 x는 제일 마지막에 방문하는 도시
 * x가 시작 정점(0)와 인접하지 않다면 순회 비용을 만들 수가 없음
 * 따라서 f(x,11111)의 답은 x가 시작 정점와 인접하다면 W[x][0]을, 인접하지 않다면 무한대(유효하지 않음)를 리턴
 *
 * (주의사항 2)
 * dp 값의 초기화는 -1, 무한대를 잡게 되면 발생하는 문제점
 * 유효하지 않은 경로인지, 아직 계산하지 않은 경로인지 알 수가 없음
 */

class BOJ_2098_외판원순회 {
    static int N;
    static int[][] W;
    static int[][] dp;
    static final int INVAILD = 1_000_000_000;
    static int maxVisit;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        W = new int[N][N];
        dp = new int[N][(1<<16)];
        maxVisit = (1 << N) - 1;

        for(int i=0; i<N; i++) {
            Arrays.fill(dp[i], -1);
        }

        for(int i=0; i<N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(getDp(0,1));
    }
    static int getDp(int from, int visit) {
        //다 방문됬으면 주의사항 체크
        if(visit == maxVisit) {
            if(W[from][0] == 0) return INVAILD;
            return W[from][0];
        }

        //이미 메모이제이션 됬으면
        if(dp[from][visit] != -1) return dp[from][visit];
        
        //유효하지 않은 경로로 초기화
        dp[from][visit] = INVAILD;    
        
        for(int to=0; to<N; to++) {
            if(W[from][to] != 0 && (visit & (1<<to)) == 0) {
                if(dp[from][visit] == -1) dp[from][visit] = getDp(to, visit | (1<<to)) + W[from][to];
                else dp[from][visit] = Math.min(dp[from][visit], getDp(to, visit | (1<<to)) + W[from][to]);
            }
        }
        return dp[from][visit];
    }
}


