import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 5:20
 * 풀이 완료 : 5:53
 * 풀이 시간 : 33분
 *
 * 문제 해석
 * N개의 마을로 이루어진 나라가 있음
 * 나라는 트리 구조
 * 아래의 조건을 만족하며 우수 마을 선정하려고 함
 * 1. 우수마을로 선정된 마을 주민 수의 총 합을 최대로 해야 함
 * 2. 우수마을끼리 인접할 수 없음
 * 3. 우수마을로 선정되ㅎ지 못한 마을은 적어도 하나의 우수마을과 인접해야 함
 *
 * 구해야 하는 것
 * 주어진 조건을 만족하면서 우수마을을 선정할 때 우수 마을 주민 수의 총 합
 *
 * 문제 입력
 * 첫째 줄 : 마을의 수 N
 * 둘째 줄 : i번째 마을의 주민 수를 나타내는 N개의 정수
 * 셋째 줄 ~ N - 1개 줄 : 간선 정보
 *
 * 제한 요소
 * 1 <= N <= 10000
 * 0 <= P[i] <= 10000
 * 1 <= 마을 번호 <= N
 *
 * 생각나는 풀이
 * 트리 dp
 * dp[2][N + 1]
 * dp[0][i] = i번 마을이 우수마을이 아닐 때 가능한 최댓값, dp[1][i] = i번 마을이 우수마을일 때 가능한 최댓값
 * 루트를 어디로 잡아도 값은 같음
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 트리 구현
 * 2. dfs돌면서 dp배열 채움
 * 3. 루트에서 최댓값 출력
 */
public class BOJ_1949_우수마을 {
    static int N;
    static int[][] dp;
    static int[] population;
    static ArrayList<Integer>[] tree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        population = new int[N + 1];
        dp = new int[2][N + 1];
        tree = new ArrayList[N + 1];
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], -1);
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            population[i] = Integer.parseInt(st.nextToken());
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            tree[a].add(b);
            tree[b].add(a);
        }

        System.out.println(Math.max(dfs(0, 1, 0), dfs(1, 1, 0)));
    }

    private static int dfs(int status, int idx, int parent) {
        if (dp[status][idx] != -1) return dp[status][idx];
        dp[status][idx] = 0;

        if (status == 0) { // idx번 마을이 우수마을이 아닐 때
            for (int child : tree[idx]) {
                if (child == parent) continue;
                dp[status][idx] += Math.max(dfs(0, child, idx), dfs(1, child, idx));
            }
        } else { // idx번 마을이 우수마을일 때
            dp[status][idx] = population[idx];
            for (int child : tree[idx]) {
                if (child == parent) continue;
                dp[status][idx] += dfs(0, child, idx);
            }
        }

        return dp[status][idx];
    }

}