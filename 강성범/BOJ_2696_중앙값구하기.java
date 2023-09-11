package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


/*
* [문제 요약]
* 홀수번째 수를 읽을 때 마다, 중앙값
*
* [제약 조건]
* T(1 ≤ T ≤ 1,000)
* M(1 ≤ M ≤ 9999, M은 홀수)
* 입력 원소는 한 줄에 10개씩 나누어져있고, 32비트 부호있는 정수
*
* [문제 설명]
* 두 가지 pq를 사용
* 두 수를 입력받아 작은 것을 최대 힙에 저장
* 큰 것을 최소 힙에 저장
* 최대 힙과 최소 힙의 peek를 비교해서 최대 힙이 더 클 경우 교체
*
*
* */
public class BOJ_2696_중앙값구하기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer stz;

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0){
            int m = Integer.parseInt(br.readLine());
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            StringBuilder sb = new StringBuilder();

            int[] arr = new int[m];
            int count = 0;
            for(int i=0; i < m/10; i++){
                stz = new StringTokenizer(br.readLine());
                for(int j=0; j<10; j++){
                    arr[count++] = Integer.parseInt(stz.nextToken());
                }
            }

            stz = new StringTokenizer(br.readLine());
            for(int i=0; i<m%10; i++){
                arr[count++] = Integer.parseInt(stz.nextToken());
            }

            maxHeap.add(arr[0]);
            sb.append(arr[0]).append(" ");
            count = 1;

            for(int i=1; i<m; i+=2){
                minHeap.add(Math.max(arr[i], arr[i+1]));
                maxHeap.add(Math.min(arr[i], arr[i+1]));

                if(maxHeap.peek() > minHeap.peek()){
                    int a = maxHeap.poll();
                    int b = minHeap.poll();

                    maxHeap.add(b);
                    minHeap.add(a);
                }

                sb.append(maxHeap.peek()).append(" ");

                if((++count)  % 10 == 0){
                    sb.append(System.lineSeparator());
                }
            }

            bw.write(count + System.lineSeparator());
            sb.append(System.lineSeparator());
            bw.write(sb.toString());
        }

        bw.flush();
        bw.close();
        br.close();
    }
}
