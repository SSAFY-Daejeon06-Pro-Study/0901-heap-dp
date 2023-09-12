import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 풀이 시작 : 5:10
 * 풀이 완료 : 5:17
 * 풀이 시간 : 7분
 *
 * 문제 해석
 * N개의 정수가 있다.
 * 정수를 하나 입력받을 때마다 지금까지 입력받은 값 중 중앙값을 말해야 함
 * 짝수개라면 두 수 중에서 작은 수를 말해야 함
 *
 * 구해야 하는 것
 * N개의 정수를 입력받을 때, 현재까지 입력받은 정수의 중앙값을 출력
 *
 * 문제 입력
 * 첫째 줄 : N
 * 둘째 줄 ~ N개 줄 : 정수 한 개
 *
 * 제한 요소
 * 1 <= N <= 100000
 * -10000 <= A[i] <= 10000
 *
 * 생각나는 풀이
 * 중앙값구하기와 동일한 풀이
 *
 * 구현해야 하는 기능
 * 1. minHeap, maxHeap
 * 2. 원소 추가
 *  2-1. 홀수번째 턴에는 maxHeap에 추가
 *  2-2. 짝수번째 턴에는 minHeap에 추가
 * 3. maxHeap.peek > minHeap.peek라면 교환
 * 4. maxHeap.peek 출력
 *
 */
public class BOJ_1655_가운데를말해요 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        while (N-- > 0) {
            int nowValue = Integer.parseInt(br.readLine());

            if (minHeap.size() < maxHeap.size()) minHeap.offer(nowValue);
            else maxHeap.offer(nowValue);

            if (!minHeap.isEmpty()) {
                if (minHeap.peek() < maxHeap.peek()) {
                    int temp = maxHeap.poll();
                    maxHeap.offer(minHeap.poll());
                    minHeap.offer(temp);
                }
            }

            sb.append(maxHeap.peek()).append('\n');
        }

        System.out.println(sb);
    }

}