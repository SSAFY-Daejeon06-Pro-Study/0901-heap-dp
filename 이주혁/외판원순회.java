import java.io.*;
import java.util.StringTokenizer;

/**
 * 
 * 완탐을 이용해 최소 비용을 갱신하자.
 * 
 * => dp로 줄여야할듯 ㅠ
 * 
 * 언제 길을 닫아야하는가?
 * 
 * 
 * @author SSAFY
 *
 */
public class 외판원순회 {

	private static final int INF = (int)Math.pow(10, 8);
	
	private static int N, adjMatrix[][], dp[][];
	
	private static int dfs(int start, int visited) {
		
		// 기저 조건
//		if(visited == Math.pow(2, N)-1) {
		if(visited == (1 << N)-1) {
			
			if(adjMatrix[start][0] == 0) {
				return INF/2;
			}
			
			return adjMatrix[start][0];
		}
		
		if(dp[start][visited] != INF) {
			return dp[start][visited];
		}
		
		
		for(int i=0; i<N; i++) {
			
			// 갈 길이 없거나 이미 방문한 길이면 continue
			if(adjMatrix[start][i] != 0 && (visited & 1 << i) == 0) {
				dp[start][visited] = Math.min(dp[start][visited], dfs(i, visited | 1 << i) + adjMatrix[start][i] );
			}
			
		}
		
		return dp[start][visited];
		
	}
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());

		adjMatrix = new int[N][N];
		
		dp = new int[N][(1<<N)-1];
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int j=0; j<N; j++) {
				adjMatrix[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = INF;
			}
			for(int j=N; j<(1 << N)-1; j++) {
				dp[i][j] = INF;
			}
		}
		
		System.out.println(dfs(0, 1));
	}
	
}