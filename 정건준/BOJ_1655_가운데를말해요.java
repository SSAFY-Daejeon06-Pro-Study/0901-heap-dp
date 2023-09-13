package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

/***
 * [문제]
 * N (백준이가 외치는 정수의 수, 1<=N<=100,000)
 *
 */
public class BOJ_1655_가운데를말해요 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> pqA = new PriorityQueue<>((a, b)-> b.compareTo(a));
        PriorityQueue<Integer> pqB = new PriorityQueue<>();

        int num = Integer.parseInt(br.readLine());
        pqA.offer(num);
        sb.append(num).append('\n');

        for(int i=1; i<N; i++) {
            num = Integer.parseInt(br.readLine());

            //pqA를 채워야함
            if(i % 2 == 0) {
                if(num <= pqB.peek()) pqA.offer(num);
                else {
                    pqA.offer(pqB.poll());
                    pqB.offer(num);
                }

            }
            //pqB를 채워야함
            else {
                if(num >= pqA.peek()) pqB.offer(num);
                else {
                    pqB.offer(pqA.poll());
                    pqA.offer(num);
                }
            }

            sb.append(pqA.peek()).append('\n');
        }
        System.out.print(sb);
    }
}
