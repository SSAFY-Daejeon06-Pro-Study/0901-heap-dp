import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 8:30
 * 풀이 완료 : 11:20
 * 풀이 시간 :
 *
 * 문제 해석
 * N개의 수로 이루어진 일차원 배열이 있음
 * 배열에서 M개의 구간을 선택해 아래의 조건을 만족하면서 구간에 속한 수들의 총 합이 최대가 되어야 함
 * 1. 각 구간은 한 개 이상의 연속된 수로 이루어짐
 * 2. 서로 다른 구간끼리 겹쳐있거나 인접하면 안됨
 * 3. 정확히 M개의 구간이 있어야 함.
 *
 * 구해야 하는 것
 * N개의 수가 주어졌을 때 답을 구해야 함
 *
 * 문제 입력
 * 첫째 줄 : N, M
 * 둘째 줄 ~ N개 줄 : 배열을 이루는 수 A[i]
 *
 * 제한 요소
 * 1 <= N <= 100
 * 1 ≤ M ≤ ⌈(N/2)⌉ (N / 2 이상의 최소 정수 : 천장 함수)
 * -32768 <= A[i] <= 32767
 *
 * 생각나는 풀이
 * dp긴 한거같은데
 * 구간합도 필요할 것 같고
 *
 * [a, b, c] = a에서 b까지 범위 내의 c 번째 구간합 중 최댓값
 * i번 칸을 끝으로 하는 첫 번째 구간이라면
 *  => [1, i, 1], [2, i, 1], [3, i, 1], ... , [i, i, 1] 중 최댓값
 * j번 칸을 끝으로 하는 두 번째 구간이라면
 *  => ([1, 1, 1] + [3, j, 1]), ([1, 2, 1] + [4, j, 1]), ([1, 3, 1], [5, j, 1]), ... , ([1, j - 2, 1] + [j, j, 1]) 중 최댓값
 * x번 칸을 끝으로 하는 k 번째 구간이라면
 *  => ([1, 1, k - 1] + [3, x, 1]), ([1, 2, k - 1] + [4, x, 1]), ([1, 3, k - 1], [5, x, 1]), ... , ([1, x - 2, k - 1] + [x, x, 1]) 중 최댓값
 *
 * dp[k][i] = i번 칸을 끝으로 하는 k번째 구간까지의 최댓값
 *  => dp[k][i] = max(dp[k - 1][i - 2] + prefix[i][i], dp[k - 1][i - 3] + prefix[i - 1][i], ... dp[k - 1][1] + prefix[3][i])
 *
 * 구현해야 하는 기능
 * 위의 조건을 구현
 * 단 중복이 많으므로 메모이제이션하여 이미 구한 값은 바로 넘겨줌
 * 1. dp배열
 *  - dp[k][i] = i번 칸을 끝으로 하는 k번째 구간까지의 최댓값
 * 2. 구간합
 *  - prefix[i] = 1 ~ i 까지의 구간함
 *
 *
 *
 * ----------------------------------
 * 2시간 넘게 풀다 실패해서 답봤는데도 6번틀림
 *
 */
public class BOJ_2228_구간나누기 {
    static final int NO_VISIT = -987654321;
    static int N, M;
    static int[] prefixSum;
    static int[][] dp;
    static boolean[][] check; // 체크배열이 왜 필요한지 모르겠음
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dp = new int[M + 1][N + 1];
        check = new boolean[M + 1][N + 1];

        for (int i = 0; i <= M; i++) {
            Arrays.fill(dp[i], NO_VISIT);
        }

        prefixSum = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            prefixSum[i] = prefixSum[i - 1] + Integer.parseInt(br.readLine());
        }

        System.out.println(memo(M, N));
    }

    private static int memo(int section, int nowIdx) {
        if (section == 0) return 0;
        if (nowIdx < 0) return NO_VISIT;

        // if(dp[section][nowIdx] != NO_VISIT) 이렇게 하면 시간초과나던데 왜지????
        if (check[section][nowIdx]) return dp[section][nowIdx];
        check[section][nowIdx] = true;

        // 현재 인덱스를 이전 구간에 붙이는 경우
        dp[section][nowIdx] = memo(section, nowIdx - 1);
        // 현재 인덱스가 새로운 구간의 시작인 경우
        for (int i = nowIdx; i >= 1; i--) {
            dp[section][nowIdx] = Math.max(dp[section][nowIdx], memo(section - 1, i - 2) + prefixSum[nowIdx] - prefixSum[i - 1]);
        }

        return dp[section][nowIdx];
    }

}