package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


/*
* [문제 요약]
* 조건에 맞춰 구간을 나눴을 때, 구간에 속한 수들의 총합이 최대가 되는 값
*
* [제약 조건]
* N(1 ≤ N ≤ 100)
* M(1 ≤ M ≤ ⌈(N/2)⌉)
* 수들은 -32768 이상 32767
*
* [문제 풀이]
* 조건들을 dp 점화식을 바꿀 수 있음
* 이차원 dp 필요
* 구간합이기 때문에 누적합(sum)사용
*
* 행 : 해당 위치의 수 까지 사용
* 열 : 구간 개수
* dp[i][j]를 i번째 수 까지 j개의 구간으로 나눴을 때 최댓값으로 정의할 수 있음
*
* [조건]
* 1. 각 구간은 한 개 이상의 연속된 수들로 이루어진다.
*   - 각 구간은 최소 하나의 수로 이루어져 있음
*
* 2. 서로 다른 두 구간끼리 겹쳐있거나 인접해 있어서는 안 된다.
*   -  특정 위치 i가 현재 구간에 포함되어 있을 경우
*       - 현재 구간을 m이라고 했을 때, 특정 위치 k에서 부터 i까지 m번째 구간임
*       - 인접할 수 없기 때문에 m-1번째 구간은 k-2위치 까지만 가능
*       - (sum[i] - sum[k-1]) + dp[k-2][j-1]
*
*   - 포함되어 있지 않을 경우
*       - max(dp[i-1][j], sum[i])
*
* 3. 정확히 M개의 구간이 있어야 한다. M개 미만이어서는 안 된다.
*
*
*
* */
public class BOJ_2228_구간나누기 {

    private static final int MIN = -32769 * 100;

    private static int[] sum;
    private static boolean[][] check;
    private static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer stz = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(stz.nextToken());
        int m = Integer.parseInt(stz.nextToken());

        sum = new int[n+1];
        check = new boolean[n+1][m+1];
        dp = new int[n+1][m+1];

        // 누적합
        for (int i = 1; i < n+1; i++) {
            int num = Integer.parseInt(br.readLine());
            sum[i] = num + sum[i-1];
        }

        for(int i=1; i< n+1; i++){
            Arrays.fill(dp[i], MIN);
        }

        bw.write(String.valueOf(solve(n, m)));

        bw.flush();
        bw.close();
        br.close();
    }

    private static int solve(int i, int m) {
        if(m == 0){
            return 0;
        }
        if(i < 0) {
            return MIN;
        }
        if(check[i][m]){
            return dp[i][m];
        }
        check[i][m] = true;

        // i가 이번 m 부분에 포함되지 않을 때
        dp[i][m] = solve(i-1, m);
        // k~i가 이번 m부분에 포함될 때
        for(int k=i; k>0; k--){
            dp[i][m] = Math.max(dp[i][m],
                    solve(k-2, m-1) + sum[i] - sum[k-1]);
        }

        return dp[i][m];
    }
}
