import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 8:00
 * 풀이 완료 : 이튿날 8:40
 * 풀이 시간 : 측정 불가
 *
 * 문제 해석
 * 가로, 세로 길이가 모두 2L + 1인 격자판이 있음
 * 격자판의 가운데 칸의 좌표는 (0,0), 맨 왼쪽 맨 아래 칸의 좌표는 (-L,-L), 맨 오른쪽 맨 윗칸의 좌표는 (L,L)
 * x좌표는 왼쪽에서 오른쪽으로 갈수록, y좌표는 아래에서 위로 갈수록 증가
 * 격자판의 (0,0)에는 한 마리의 뱀이 있음. 초기 크기는 1칸, 머리 방향은 오른쪽
 * 1초 동안 일어나는 일
 * 1. 자신이 바라보고 있는 방향으로 몸을 한 칸 늘려서 2칸을 차지함
 * 일정한 규칙에 따라 머리를 회전해야 함
 * 1번째 회전은 뱀이 출발한 지 t1초 후에 일어남
 * i번째 회전은 i-1번째 회전이 일어난 후 ti초 후에 일어남
 * 뱀은 ti칸만큼 몸을 늘린 후에 머리를 회전하며, 머리 회전에 드는 시간은 없음
 * 뱀이 숨을 거두는 조건
 * 1. 뱀의 머리가 격자판 밖으로 나감
 * 2. 뱀의 머리가 몸과 부딪힘
 * 뱀이 머리를 회전하는 규칙과 그 방향에 대한 정보가 주어졌을 때 뱀이 몇 초 뒤에 숨을 거두는지 구해야 함
 *
 * 구해야 하는 것
 * 뱀이 머리를 회전하는 규칙과 그 방향에 대한 정보가 주어졌을 때 뱀이 몇 초 뒤에 숨을 거두는지 구해야 함
 *
 * 문제 입력
 * 첫째 줄 : L
 * 둘째 줄 : 머리 방향 회전 수 N
 * 셋째 줄 ~ N개 줄 : 뱀이 머리를 어떻게 회전하는지에 대한 정보
 *  - t : 시간, d : 방향(L or R)
 *
 * 제한 요소
 * 1 <= L <= 10^8
 * 0 <= N <= 1000
 * 1 <= ti <= 2*10^8
 *
 * 생각나는 풀이
 *
 * -------------------초기 설계-----------------------
 * 단순 구현이면 시간초과 날 가능성이 있음
 * 뱀의 몸을 어떻게 표현해야 시간을 줄일 수 있을까
 * 뱀이 방향을 바꾸는 과정은 총 1000번
 * => 이걸 이용하면 가능하지 않을까
 * => 링크드리스트로 각 몸의 크기, 시작 좌표와 끝좌표를 저장해놓고
 * 머리를 움직여 이동하기 전에 이전 링크의 헤드부터 탐색하면서
 * 내가 현재 갈 곳이 그 범위에 걸리면 종료
 * 그러면 반복 횟수가 0 + 1 + 2 + 3 + ... + 999 = 1000 * 500 = 500000정도면 가능할 듯
 *
 * 구현해야 하는 기능
 * 1. 뱀의 모습을 저장할 링크드리스트
 * 2. 구현
 *  2-1. 회전하기 직전까지 뱀 몸의 상태를 저장하는 클래스
 *      - 멤버 : 시작좌표 (x,y), 끝좌표(x,y), 길이, 방향
 *  2-2. 회전하기 전에 헤드에 있는 노드부터 비교하며 내 예상 몸이 걸릴지 체크
 *      - 현재 내 방향이 가로, 체크할 노드의 방향이 세로
 *          - targetX가 startX, endX 사이에 있으면 부딪히는 것
 *          - 현재까지 길이 + Math.abs(targetX - startX)가 답
 *      - 현재 내 방향이 가로, 체크할 노드의 방향이 가로
 *          - 비교할 노드의 startX나 endX가 현재 노드의 startX와 endX 사이에 있다면
 *          - 현재까지 길이 + Math.min(Math.abs(targetStartX - startX, targetEndX - startX))
 *      - 현재 내 방향이 세로, 체크할 노드의 방향이 세로
 *          - 비교할 노드의 startY나 endY가 현재 노드의 startY와 endY 사이에 있다면
 *          - 현재까지 길이 + Math.min(Math.abs(targetStartY - startY, targetEndY - startY))
 *      - 현재 내 방향이 세로, 체크할 노드의 방향이 가로
 *          - targetY가 startY, endY 사이에 있으면 부딪히는 것
 *          - 현재까지 길이 + Math.abs(targetY - startY)가 답
 *
 * 만약 끝까지 안박았다면 마지막 머리 방향을 벽에 박을때까지 늘렸다고 치고 계산해야 함
 * -------------------초기 설계-----------------------
 *
 * 큰 틀은 맞았지만 세부적인 설정에서 문제가 있었음
 * 1. 링크드리스트의 헤드부터 탐색하면서 가장 처음 겹치는 선분이 나타나면 종료하는 것
 *  => 헤드부터 연결된 순으로 탐색하며 조건에 걸리면 바로 종료하므로 내가 선택한 선분이 현재 시작 위치에서 가장 가까운 선분이 아닐 가능성이 있음
 * 2. 회전이 종료되고도 몸에 부딪히지 않을 수 있는데, 그걸 처리하기 위해 마지막 이동 거리를 2 * L로 준 것
 *  => 이렇게 한다면 마지막 회전한 위치가 -L이고, 반대 방향으로 2 * L을 이동한다 해도 격자를 벗어나지 않음, 즉 마지막에 더해주는 값이 달라짐
 */
public class BOJ_10875_뱀 {
    static long length;
    static int L, N;
    static int[] direction;
    static int[] time;
    static Node head;
    static Node tail;

    static class Node {
        int startX, startY, endX, endY, dir;
        Node next;

        public Node(int startX, int startY, int endX, int endY, int dir) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.dir = dir;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        L = Integer.parseInt(br.readLine());
        N = Integer.parseInt(br.readLine());
        direction = new int[N + 1]; // 0번째(초기) 방향은 우측 고정
        time = new int[N + 1];
        time[N] = 2 * L + 2; // 회전을 전부 마친 후에도 살아있을 수 있음. => 그렇다면 벽에 부딪힐 수 있게끔 격자판의 크기보다 큰 값만큼 이동하게 함

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            // i번째 입력받는 시간은 i - 1번째 회전 이후부터 i번째 회전 직전까지의 시간
            // 즉 i - 1번째 선분의 길이는 i번째 입력받는 시간
            time[i - 1] = Integer.parseInt(st.nextToken());
            direction[i] = st.nextToken().charAt(0) == 'L' ? (direction[i - 1] + 3) : direction[i - 1] + 1; // L이면 기존 방향에서 반시계방향으로, R이면 기존 방향에서 시계방향으로 회전한 값
            direction[i] %= 4;
        }

        // 외부가 뱀의 몸으로 둘러져 있다고 생각하면 외부 체크를 따로 할 필요 없음 >> 이건 답 보고 안 사실
        head = new Node(-L - 1, -L - 1, L + 1, -L - 1, 0); // 아래쪽 경계
        tail = head;
        tail.next = new Node(-L - 1, L + 1, -L - 1, -L - 1, 1); // 왼쪽 경계
        tail = tail.next;
        tail.next = new Node(-L - 1, L + 1, L + 1, L + 1, 0); // 위쪽 경계
        tail = tail.next;
        tail.next = new Node(L + 1, L + 1, L + 1, -L - 1, 1); // 오른쪽 경계
        tail = tail.next;

        // 만약 이전 선분의 끝 점을 시작점으로 잡게 되면 이전 선분과 현재 선분이 무조건 교차한다고 판정되어버림
        // 그래서 이전 선분의 길이를 1 줄임
        int[] dx = {-1, 0, 1, 0}; // 우하좌상
        int[] dy = {0, 1, 0, -1}; // 우하좌상

        int startX = 0, startY = 0;
        for (int i = 0; i <= N; i++) {
            int nowDir = direction[i]; // i번째 선분이 뻗는 방향
            int nowTime = time[i]; // i번째 선분의 길이
            int endX = makeEndX(startX, nowDir, nowTime);
            int endY = makeEndY(startY, nowDir, nowTime);
            int minX = Math.min(startX, endX); // 조건식을 조금이라도 줄이기 위해
            int maxX = Math.max(startX, endX); // 조건식을 조금이라도 줄이기 위해
            int minY = Math.min(startY, endY); // 조건식을 조금이라도 줄이기 위해
            int maxY = Math.max(startY, endY); // 조건식을 조금이라도 줄이기 위해

            int t = Integer.MAX_VALUE; // 만나는 선분이 있는지 체크 및 만나는 선분이 있다면 최종적으로 더해줄 값
            for (Node prev = head; prev != null; prev = prev.next) { // 링크드리스트의 순회 : 이전까지 그어진 선분들을 의미함
                int prevDir = prev.dir;
                int prevMinX = Math.min(prev.startX, prev.endX); // 조건식을 조금이라도 줄이기 위해
                int prevMaxX = Math.max(prev.startX, prev.endX); // 조건식을 조금이라도 줄이기 위해
                int prevMinY = Math.min(prev.startY, prev.endY); // 조건식을 조금이라도 줄이기 위해
                int prevMaxY = Math.max(prev.startY, prev.endY); // 조건식을 조금이라도 줄이기 위해

                // 선분이 만날 수 있는 경우는 크게 4가지, (가로-세로), (가로-가로), (세로-세로), (세로-가로)
                if (nowDir % 2 == 0 && prevDir % 2 == 1) { // 현재 내 방향이 가로, 체크할 노드의 방향이 세로
                    if ((minX <= prev.startX && prev.startX <= maxX) &&
                            (prevMinY <= startY && startY <= prevMaxY)) { // targetX가 startX, endX 사이에 있으면 부딪히는 것
                        t = Math.min(t, Math.abs(prev.startX - startX)); // Math.abs(targetX - startX)
                    }
                } else if (nowDir % 2 == 0 && prevDir % 2 == 0) { // 현재 내 방향이 가로, 체크할 노드의 방향이 가로
                    // 비교할 노드의 startX나 endX가 현재 노드의 startX와 endX 사이에 있다면
                    if (startY == prevMinY && ((minX <= prev.startX && prev.startX <= maxX) ||
                            (minX <= prev.endX && prev.endX <= maxX))) {
                        // Math.min(Math.abs(targetStartX - startX, targetEndX - startX))
                        t = Math.min(t, Math.min(Math.abs(prev.startX - startX), Math.abs(prev.endX - startX)));
                    }
                } else if (nowDir % 2 == 1 && prevDir % 2 == 1) { // 현재 내 방향이 세로, 체크할 노드의 방향이 세로
                    // 비교할 노드의 startY나 endY가 현재 노드의 startY와 endY 사이에 있다면
                    if (startX == prevMinX && ((minY <= prev.startY && prev.startY <= maxY) ||
                            (minY <= prev.endY && prev.endY <= maxY))) {
                        // Math.min(Math.abs(targetStartY - startY, targetEndY - startY))
                        t = Math.min(t, Math.min(Math.abs(prev.startY - startY), Math.abs(prev.endY - startY)));
                    }
                } else if (nowDir % 2 == 1 && prevDir % 2 == 0) { // 현재 내 방향이 세로, 체크할 노드의 방향이 가로
                    if ((minY <= prev.startY && prev.startY <= maxY) &&
                            (prevMinX <= startX && startX <= prevMaxX)) { // targetY가 startY, endY 사이에 있으면 부딪히는 것
                        // Math.abs(targetY - startY)
                        t = Math.min(t, Math.abs(prev.startY - startY));
                    }
                }
            }

            if (t != Integer.MAX_VALUE) { // 지금까지 그은 선 중 겹치는 게 있었다면
                length += t; // 겹치는 거리 중 최솟값
                break;
            }

            // 겹치는 게 없었다면
            length += nowTime; // 현재 길이를 더해줌
            tail.next = new Node(startX, startY, endX + dx[nowDir], endY + dy[nowDir], nowDir); // 끝의 길이를 1만큼 줄여서 링크드리스트에 추가
            tail = tail.next; // tail 트래킹
            startX = endX;
            startY = endY;
        }

        System.out.println(length);
    }

    private static int makeEndX(int startX, int nowDir, int nowTime) {
        if (nowDir % 2 == 0) { // 우좌
            return nowDir == 0 ? startX + nowTime : startX - nowTime;
        } else { // 하상
            return startX;
        }
    }

    private static int makeEndY(int startY, int nowDir, int nowTime) {
        if (nowDir % 2 == 0) { // 우좌
            return startY;
        } else { // 하상
            return nowDir == 1 ? startY - nowTime : startY + nowTime;
        }
    }
}