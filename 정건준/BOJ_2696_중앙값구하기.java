package 정건준;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/***
 * [문제]
 * 수열이 주어졌을때, 홀수번째 수를 읽을 때마다 지금까지 입력받은 값의 중앙값 출력
 *
 * [입력]
 * T (1<=T<=1000)
 * M (수열의 크기, 1<=M<=9999)
 *
 * [풀이]
 * 중앙 값과 중앙 값보다 작은 수를 저장하는 pqA (최대 힙)
 * 중앙 값보다 큰 수를 저장하는 pqB (최소 힙)
 *
 * 1. M만큼 반복, for (int i=0; i<M; i++)
 * 1-1. i가 짝수 (pqA 사이즈 + 1)
 *      if(pqB.peek >= number[i]) pqA.push(number[i])
 *      else pqA.push(pqB.pop()), pqB.push(number[i]),
 *      pqA.peek 출력
 *
 * 1-2. i가 홀수 (pqB 사이즈 + 1)
 *      if(pqA.peek >= number[i]) pqB.push(number[i])
 *      else pqB.push(pqA.pop()), pqA.push(number[i])
 *
 */


public class BOJ_2696_중앙값구하기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int testCase = Integer.parseInt(br.readLine());

        for(int t=0; t<testCase; t++) {
            int M = Integer.parseInt(br.readLine());
            int[] number = new int[M];

            st = new StringTokenizer(br.readLine());
            for(int i=0; i<M; i++) {
                if(i != 0 && i % 10 == 0) st = new StringTokenizer(br.readLine());
                number[i] = Integer.parseInt(st.nextToken());
            }

            int printCount = 0;
            PriorityQueue<Integer> pqA = new PriorityQueue<>((a, b) -> b.compareTo(a));
            PriorityQueue<Integer> pqB = new PriorityQueue<>();
            pqA.add(number[0]);

            sb.append(M / 2 + 1).append('\n');
            sb.append(number[0]).append(' ');

            for(int i=1; i<M; i++) {
                if(i % 2 == 0) {
                    if(pqB.peek() >= number[i]) pqA.offer(number[i]);
                    else {
                        pqA.offer(pqB.poll());
                        pqB.offer(number[i]);
                    }
                    sb.append(pqA.peek()).append(' ');
                    printCount++;
                    if((printCount + 1) % 10 == 0) sb.append('\n');
                }
                else {
                    if(pqA.peek() <= number[i]) pqB.offer(number[i]);
                    else {
                        pqB.offer(pqA.poll());
                        pqA.offer(number[i]);
                    }
                }
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}
