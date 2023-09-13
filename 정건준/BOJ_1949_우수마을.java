package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 [문제]
 N(1<=N<=10000)개 마을, 1부터 N번 노드, 트리 구조로 이어져있음
 N개의 마을 중 몇 개의 마을을 우수 마을로 선정
 우수 마을의 주민 수 총 합 출력
 
 1. 우수 마을 끼리는 서로 인접 X
 2. 우수 마을로 선정된 마을 주민수의 총 합을 최대로
 3. 우수 마을로 선정되지 못한 마을은 적어도 하나의 우수 마을과 인접

 [DP 풀이]
 dp[2][N+1]
 dp[0][i] = i번 노드가 우수마을이 아닐때, i번 노드를 루트로 하는 서브트리의 우수 마을 주민 수 최대합
 dp[1][i] = i번 노드가 우수마을 일때, i번 노드를 루트로 하는 서브트리의 우수 마을 주민 수 최대합

 dp[0][i] += Math.max(dp[0][child], dp[1][child])
 dp[1][i] += dp[0][child]
 */
 
public class BOJ_1949_우수마을 {
	static class Node {
		int num;
		Node next;
		Node(int num, Node next) {
			this.num = num;
			this.next = next;
		}
	}

	static int[][] dp;
	static Node[] adjList;
	static int[] people;
	static boolean[] visit;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());

		dp = new int[2][N+1];
		adjList = new Node[N+1];
		people = new int[N+1];
		visit = new boolean[N+1];
		
		st = new StringTokenizer(br.readLine());
		for(int i=1; i<=N; i++) people[i] = Integer.parseInt(st.nextToken()); 
		
		for(int i=0; i<N-1; i++) {
			st = new StringTokenizer(br.readLine());
			int to = Integer.parseInt(st.nextToken());
			int from = Integer.parseInt(st.nextToken());
			adjList[to] = new Node(from, adjList[to]);
			adjList[from] = new Node(to, adjList[from]);
		}

		int root = 1;
		for(int i=1; i<=N; i++) {
			if(adjList[i].next == null) {
				root = i;
				break;
			}
		}

		dfs(root);
		System.out.println(Math.max(dp[0][root], dp[1][root]));
	}

	static void dfs(int root) {
		visit[root] = true;
		dp[0][root] = 0;
		dp[1][root] = people[root];

		for(Node child = adjList[root]; child != null; child = child.next) {
			if(visit[child.num]) continue;
			dfs(child.num);
			dp[0][root] += Math.max(dp[0][child.num], dp[1][child.num]);
			dp[1][root] += dp[0][child.num];
		}
	}
}
