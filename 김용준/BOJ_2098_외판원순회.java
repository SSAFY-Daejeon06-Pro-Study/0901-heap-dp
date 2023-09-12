import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 10:26
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * 외판원 순회
 * 1번부터 N번까지 번호가 매겨진 도시가 있음
 * 한 외판원이 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 다시 처음 도시로 돌아오는 최소 비용을 구해야 함
 *
 * 구해야 하는 것
 * 한 외판원이 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 다시 처음 도시로 돌아오는 최소 비용을 구해야 함
 *
 * 문제 입력
 * 첫째 줄 : N
 * 둘째 줄 ~ N개 줄 : 비용 행렬
 *  - W[i][j] = i에서 j로 가기 위한 비용
 *
 * 제한 요소
 * 2 <= N <= 16
 * 1 <= W[i][j] <= 1_000_000
 * 갈 수 없는 경우 0
 * 항상 순회할 수 있는 경우만 주어짐
 *
 * 생각나는 풀이
 * 외판원 순회
 * dp[N][1 << N]
 * dp[k][flag] = 현재 위치 k, 방문한 도시 flag일 때 최소 비용
 * INF > 16 * 1000000 = 16000000
 *
 * 구현해야 하는 기능
 * 1. dp배열
 * 2. 입력에 따른 도시간 이동 비용 배열
 * 3. dfs
 * --------------
 * dp를 초기화할 때 INF로 하니까 시간초과나네
 * 찾아보니까 마지막 갈 곳이 없을 때 return INF 과정에서
 * INF가 미방문이라 INF인건지 아니면 경로가 존재하지 않아서 INF인건지
 * 모르기 때문에 결국 같은 곳 또 체크하는듯
 */
public class BOJ_2098_외판원순회 {
    static final int INF = 987654321;
    static int N, allVisit;
    static int[][] dp;
    static int[][] cost;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        dp = new int[N][1 << N];
        cost = new int[N][N];
        allVisit = (1 << N) - 1;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
            Arrays.fill(dp[i], -1); // INF로 초기화하면 문제생김
        }

        System.out.println(dfs(0, 1));
    }

    private static int dfs(int now, int flag) {
        if (flag == allVisit) {
            return cost[now][0] == 0 ? INF : cost[now][0];
        }

        if (dp[now][flag] != -1) return dp[now][flag];
        dp[now][flag] = INF;
        for (int i = 0; i < N; i++) {
            if ((flag & 1 << i) == 0 && cost[now][i] != 0) {
                dp[now][flag] = Math.min(dp[now][flag], dfs(i, flag | 1 << i) + cost[now][i]);
            }
        }

        return dp[now][flag];
    }

}