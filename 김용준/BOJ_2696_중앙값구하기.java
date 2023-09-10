import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 4:28
 * 풀이 완료 : 4:50
 * 풀이 시간 : 22분
 *
 * 문제 해석
 * 어떤 수열이 주어질 때, 홀수번째 수가 들어올 때마다 지금까지 입력받은 값의 중앙값을 출력해야 함
 *
 * 구해야 하는 것
 * 어떤 수열이 주어질 때, 홀수번째 수가 들어올 때마다 지금까지 입력받은 값의 중앙값을 출력해야 함
 *
 * 문제 입력
 * 첫째 줄 : 테케 수 T
 * 테케당 입력
 * 첫째 줄 : 수열의 크기 M
 * 둘째 줄 ~ M / 10개 줄 : 수열의 원소 A[i]가 공백을 두고 주어짐, 한 줄에 원소는 10개씩 주어짐
 *
 *
 * 제한 요소
 * 1 <= M <= 9999
 * M % 2 == 1
 * A[i] = Integer
 *
 * 생각나는 풀이
 * minHeap, maxHeap 2개 사용해서 문제 풀어야 할 듯
 * 홀수번째에는 maxHeap에 삽입
 * 짝수번째에는 minHeap에 삽입
 * 만약 maxHeap의 최댓값이 minHeap의 최솟값보다 크면 둘이 교환
 * maxHeap의 최댓값을 출력
 *
 * 구현해야 하는 기능
 * 1. minheap (중앙값 초과의 값), maxHeap(중앙값 이하의 값)
 * 2. 원소 추가 규칙
 *  2-1. 홀수번째 원소이면 maxHeap에, 짝수번째 원소이면 minHeap에 추가
 *  2-2. maxHeap의 최댓값과 minHeap의 최솟값을 비교
 *      - maxHeap.peek > minHeap.peek => 교환
 *  2-3. 홀수번째 턴이면 maxHeap의 최댓값 출력
 *
 */
public class BOJ_2696_중앙값구하기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        while (T-- > 0) {
            minHeap.clear();
            maxHeap.clear();

            int N = Integer.parseInt(br.readLine());
            sb.append((N + 1) >> 1).append('\n'); // 수열의 길이는 홀수이므로 (수열의 길이 + 1) / 2가 출력하는 요소의 개수

            for (int i = 1; i <= N; i++) {
                if (i % 10 == 1) st = new StringTokenizer(br.readLine()); // 한 줄에 10개씩 있으므로 조건 만족할 때마다 다음 줄 파싱

                int nowValue = Integer.parseInt(st.nextToken());

                if ((i & 1) == 1) { // 홀수번째 수
                    maxHeap.offer(nowValue);
                } else { // 짝수번째 수
                    minHeap.offer(nowValue);
                }

                if (!minHeap.isEmpty()) { // 첫 번째 경우 빼고 전부 만족
                    if (maxHeap.peek() > minHeap.peek()) { // 정렬이 제대로 되지 않았다면 교환
                        int temp = maxHeap.poll();
                        maxHeap.offer(minHeap.poll());
                        minHeap.offer(temp);
                    }
                }
                if (i % 20 == 0) sb.append('\n'); // 20의 배수번째 요소를 넣고 나면 10의 배수만큼 출력했으므로 줄바꿈
                if ((i & 1) == 1) sb.append(maxHeap.peek()).append(' '); // 홀수번째 턴이라면 중앙값 출력
            }
            sb.append('\n'); // 테케마다 줄바꿈
        }

        System.out.println(sb);
    }

}