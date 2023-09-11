package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.*;
import java.util.PriorityQueue;


/*
* [문제 요약]
* 각 입력마다 중간값을 구해야 함
*
* [문제 요약]
* N은 1보다 크거나 같고, 100,000보다 작거나 같은 자연수
* 정수는 -10,000보다 크거나 같고, 10,000보다 작거나 같다.
*
* [문제 설명]
* 두 개의 힙 사용
* 최대 힙 - 작은 값을 저장함
* 최소 힙 - 큰 값을 저장함
*
* 첫 번째 수를 최대 힙에 저장
* 두 번째 수 부터 최대 힙의 peek와 비교하여 크면 최소 힙에 저장
*   - 작거나 같으면 최대 힙에 저장
* 저장되고 최대 힙의 peek가 중간값
*
* 이러면 짝수일 때 문제가 생김
*
* 최대 힙과 최소 힙을 사용하되,
* 원소 개수 차이에 따라 삽입 방식이 달라짐
*
* 첫 원소는 최대 힙에 삽입
* 두 번째 원소부터 최소 힙의 크기가 최대 힙보다 size가 작으면 최소 힙에 우선 삽입
* 삽입 후 peek를 비교해서 witch
*
* 만약 size가 같다면 최대힙에 삽입 후 peek 비교
*
* */
public class BOJ_1949_우수마을 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();


        int n = Integer.parseInt(br.readLine()) - 1;
        int initNumber = Integer.parseInt(br.readLine());
        maxHeap.add(initNumber);
        bw.write(initNumber + System.lineSeparator());

        while (n-- > 0){
            int num = Integer.parseInt(br.readLine());

            if(maxHeap.size() != minHeap.size()){
                minHeap.add(num);
            }else{
                maxHeap.add(num);
            }

            if(maxHeap.peek() > minHeap.peek()){
                int a = maxHeap.poll();
                int b = minHeap.poll();

                maxHeap.add(b);
                minHeap.add(a);
            }

            bw.write(maxHeap.peek()+System.lineSeparator());
        }

        bw.flush();
        bw.close();
        br.close();
    }
}
