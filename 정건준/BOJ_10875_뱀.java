package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/***
 * [문제]
 * 가로 길이와 세로 길이가 2L + 1인 격자판
 * 격자판의 각 칸은 (x,y)로 표현, 가운데 칸의 좌표는 (0,0)
 * 초기 뱀은 (0,0), 뱀의 머리는 오른쪽 방향을 바라보고 있음
 * 격자판을 벗어나거나, 뱀의 머리가 몸통과 부딪히면 뱀 죽음
 *
 * 뱀은 1초마다 현재 방향으로 몸을 한 칸 늘림
 * 1번째 회전 - t1초 동안 몸을 늘리고 dir1 방향으로 회전
 * n(n>1)번째 회전 - tN초 후 동안 몸을 늘리고 dirN 방향으로 회전
 *
 * [입력, 제약 사항]
 * L (1 <= L <= 10의 8승)
 * N (머리 방향 회전 횟수, 0 <= N <= 1000)
 * t (1 <= t <= 2 * 10의 8승), dir(L-반시계 또는 R-시계)
 *
 * 머리 방향 회전 횟수가 최대 1000회 밖에 안되네?
 * N개의 구간을 저장하고 비교
 * 시간 복잡도 = 1000 * 1000 = 1000000
 * 설계,풀이는 너무 바껴서 생략
 */

/*
public class BOJ_10875_뱀 {
    static class Line {
        int startX;
        int startY;
        int endX;
        int endY;
        int dir;
        Line(int startX, int startY, int endX, int endY, int dir) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.dir = dir;
        }
    }

    static int[][] pos = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} }; //우 하 좌 상
    static int L, N, minRange, maxRange;
    static List<Line> lines;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        L = Integer.parseInt(br.readLine());
        N = Integer.parseInt(br.readLine());
        int second = 0;
        int snakeDir = 0;
        lines = new ArrayList<Line>();
    }

    //충돌 체크
    static int collisionCheck(Line target) {
        for(int i=0; i<lines.size(); i++) {
            //새 선분은 ㅡ 모양
            if(target.dir == 0 || target.dir == 2) {
                //충돌날 가능성이 없음
                if(lines.get(i).dir != 0 && lines.get(i).dir != 2) continue;
                if()
            }
        }
    }
}
 */
