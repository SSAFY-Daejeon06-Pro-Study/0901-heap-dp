import java.io.*;
import java.util.*;

/*
 * [문제 요약]
 * 뱀이 범위를 넘어가거나, 몸통에 부딪치는 시간
 *
 * [제약 조건]
 * L(1 ≤ L ≤ 10^8)
 * N(0 ≤ N ≤ 10^3)
 * i(1 ≤ i ≤ N)번째 줄에 정수 ti(1 ≤ ti ≤ 2 · 10^8)와 diri 가 차례로 주어지며 diri 는 문자 L, 또는 R 중 하나
 *
 * 한 변의 길이는 (2L+1) -> 2억 + 1
 *
 * [문제 설명]
 * 단순 구현으로 풀기에는 입력 범위가 너무  큼
 * 뱀의 이동을 선분으로 표현해야 함
 * 뱀의 이동 선분을 리스트에 저장
 *     - 뱀의 시작 위치와 끝 위치 저장, 방향 -> 시작과 끝을 알고 있으니 길이는 저장 안해도 될 듯
 *
 * 뱀이 죽는 경우는 두 가지
 * 1. 범위 밖을 벗어 날 경우
 * 2. 자신의 몸통에 부딪칠 경우
 *
 * 뱀은 (L,L)에서 시작
 *
 * 회전이 발생 할 때 까지 직선으로 이동
 * 회전은 L과 R로만 이뤄지지만, 이동하고 있는 방향에 따라 달라짐
 *
 *  상
 *      - 좌회전 : 좌
 *      - 우회전 : 우
 *  하
 *      - 좌회전 : 우
 *      - 우회전 : 좌
 *  좌
 *      - 좌회전 : 하
 *      - 우회전 : 상
 *  우
 *      - 좌회전 : 상
 *      - 우회전 : 하
 *
 * [문제 풀이 순서]
 * 0. 맵은 (0,0) ~ (2L, 2L)까지 사용
 *     - 뱀의 초기 위치는 (L, L)임
 * 1. 처음 회전이 발생하기 전 까지가 뱀이 우측으로 이동하는 길이임
 * 2. 회전이 발생하면 방향에 맞춰 뱀의 머리 위치를 바꿈
 * 3. 이동된 방향에 맞춰 이동
 * 4. 초기화 이동 후에 선분 겹침 확인
 *
 * [선분의 겹침]
 * 뱀의 이미 이동한 몸통 선분을 x1, y1에서 x2, y2라고 하겠음
 * 그리고, 뱀이 머리를 움직여 이동할 선분을 sx, sy에서 ex, ey라고 하겠음
 *
 * 선분이 겹치는 경우
 *
 * 1. 상
 *  - 아래에서 위로 이동
 *  - 몸통 성분의 네 방향에 따라 조건 확인
 *      - 상
 *          - 수평
 *          - 같은 dy인지 확인(y1 == sy)
 *              - 큰 값을 기준으로 비교 (x1< x2, sx < ex)로 값을 사용
 *              - 아래 조건이 아니라면 충돌 발생
 *                  - (ex < x1) || (x2 < sx)
 *
 *      - 하
 *          - 수평
 *
 *      - 좌
 *          - 수직
 *              - 큰 값을 기준으로 비교 (x1< x2, sx < ex)로 값을 사용
 *              - 아래 조건을 만족하면 충돌 발생
 *                  - sy <= y1 && y1 <= ey && x1 <= sx && sx <= x2
 *
 *      - 우
 *          - 수직
 *
 *
 * 2. 아래
 *  - 상과 거의 동일
 *
 * 3. 좌
 *  - 우에서 좌로 이동
 *  - 몸통 성분의 네 방향에 따라 조건 확인
 *      - 상
 *          - 수직
 *          - 
 *
 *      - 하
 *          - 수직
 *
 *      - 좌
 *          - 수평
 *          - 같은 dy인지 확인
 *              - 큰 값을 기준으로 비교 (x1< x2, sx < ex)로 값을 사용
 *              - 네 가지 조건 중 하나라도 발생하면 충돌 발생
 *                  - sy < y1 & y1 < ey
 *                  - y1 < sy & ey < y2
 *                  - sy < y2 & y2 < ey
 *                  - sy <= y1 & y2 <= ey
 *
 *      - 우
 *
 * 4. 우
 *
 *
 *
 * */
public class BOJ_10875_뱀 {

	private static final int[] DX = {-1, 0, 1, 0};
	private static final int[] DY = {0, -1, 0, 1};

	private static final int UP = 0; // 상 좌 하 우
	private static final int LEFT = 1;
	private static final int DOWN = 2;
	private static final int RIGHT = 3;
	private static final int MAX = 200_000_002;

	private static int l;
	private static String[][] rotateInfo;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long answer = 0;

		l = Integer.parseInt(br.readLine());
		int n = Integer.parseInt(br.readLine());

		rotateInfo = new String[n+1][2];
		for (int i = 0; i < n; i++) {
			String[] sp = br.readLine().split(" ");
			rotateInfo[i][0] = sp[0];
			rotateInfo[i][1] = sp[1];
		}

		// 뱀이 마지막에 벽까지 이동할 수 있도록 변의 최대 길이만큼 이동 경로 추가
		rotateInfo[n][0] = String.valueOf(MAX);

		answer += move();
		System.out.println(answer);

		br.close();
	}

	private static long move() {
		List<Line> lines = new ArrayList<>();
		long time = 0;

		// 회전 정보만큼 이동
		for(int k = 0; k<rotateInfo.length; k++){
			int power = Integer.parseInt(rotateInfo[k][0]); // 얼마큼 이동할 지

			// 자신의 몸통 선분과 충돌이 발생 했는지
			boolean isCollapse = false;
			long collapseTime = Integer.MAX_VALUE;

			// 범위 밖을 벗어 났는지
			boolean isOutOfRange = false;
			long outOfRangeTime = 0;

			// 뱀의 머리 정보를 가져옴 -> 선분 마지막에 있음. 초기는 (l, l)
			int headX = l;
			int headY = l;
			int direction = RIGHT;
			if(lines.size() > 0){
				headX = lines.get(lines.size() - 1).x2;
				headY = lines.get(lines.size() - 1).y2;
				direction = lines.get(lines.size() - 1).direction;

				char rotate = rotateInfo[k-1][1].charAt(0); // 회전 방향

				int nextDirection = -1;
				if (rotate == 'L') {
					nextDirection = (direction + 1) % 4;
				} else {
					nextDirection = (direction == 0) ? 3 : direction - 1;
				}

				direction = nextDirection;
			}

			// 다음 이동 방향으로 움직였을 때, 범위를 벗어나는지 체크
			int nextHeadX = headX + (DX[direction] * power);
			int nextHeadY = headY + (DY[direction] * power);

			// 범위를 벗어나면 해당 값 만큼 더해줌
			if (!inRange(nextHeadX, nextHeadY)) {
				if(nextHeadX < 0){
					outOfRangeTime = headX;
				}
				else if(nextHeadY < 0){
					outOfRangeTime = headY;
				}
				else if(nextHeadX > (2 * l)){
					outOfRangeTime = (2*l) - headX;
				}
				else if(nextHeadY > (2 * l)){
					outOfRangeTime = (2*l) - headY;
				}

				// 밖으로 움직이는 동작 추가
				outOfRangeTime++;
				isOutOfRange = true;
			}

			// 충돌 발생 여부를 확인하기 위해  사용
			int sx = Math.min(headX, nextHeadX);
			int sy = Math.min(headY, nextHeadY);
			int ex = Math.max(headX, nextHeadX);
			int ey = Math.max(headY, nextHeadY);

			// 이전의 몸통 선분들과 충돌이 발생하는지 판단
			// 이전 것과 충돌은 발생하지 않기 때문에 이전의 것은 무시
			for(int i=0; i<lines.size()-1; i++){
				Line body = lines.get(i);

				// 1에는 작은 값, 2에는 큰 값 저장
				int x1 = Math.min(body.x1, body.x2);
				int y1 = Math.min(body.y1, body.y2);
				int x2 = Math.max(body.x1, body.x2);
				int y2 = Math.max(body.y1, body.y2);
				int d = body.direction;

				if(direction == UP || direction == DOWN){ // 상 or 하
					if(d == UP || d == DOWN){ // 수평 중접 확인
						if(y1 == sy){ // y축이 같으면
							// 겹치지 않았을 때
							if((ex < x1) || (x2 < sx)){
								continue;
							}
							// 겹쳤을 때 -> 뱀이 죽음
							else{
								if(direction == UP){
									collapseTime = Math.min(collapseTime, (ex - x2));
								}else{
									collapseTime = Math.min(collapseTime, (x1 - sx));
								}

								isCollapse = true;
								continue;
							}
						}

					}else{ // 수직 중첩 확인
						if(y1 <= sy && sy <= y2 && sx <= x1 && x1 <= ex){
							if(direction == UP){
								collapseTime = Math.min(collapseTime, (ex - x2));
							}else{
								collapseTime = Math.min(collapseTime, (x1 - sx));
							}

							isCollapse = true;
							continue;
						}
					}
				}else{ // 좌 or 우
					if(d == LEFT || d == RIGHT){ // 수평 중접 확인
						if(x1 == sx){ // x축이 같으면
							// 겹치지 않았을 때
							if((ey < y1) || (y2 < sy)){
								continue;
							}
							// 겹쳤을 때 -> 뱀이 죽음
							else{
								if(direction == LEFT){
									collapseTime = Math.min(collapseTime, (ey - y2));
								}else{
									collapseTime = Math.min(collapseTime, (y1 - sy));
								}
								isCollapse = true;
								continue;
							}
						}
					}else{ // 수직 중첩 확인
						if(sy <= y1 && y1 <= ey && x1 <= sx && sx <= x2){
							if(direction == LEFT){
								collapseTime = Math.min(collapseTime, (ey - y2));
							}else{
								collapseTime = Math.min(collapseTime, (y1 - sy));
							}
							isCollapse = true;
							continue;
						}
					}
				}
			}

			// 충돌이 발생하거나, 범위를 벗어나게 되는 경우
			if(isCollapse || isOutOfRange){
				if(isCollapse && isOutOfRange){
					time += Math.min(collapseTime, outOfRangeTime);
				}else if(isCollapse){
					time += collapseTime;
				}else {
					time += outOfRangeTime;
				}

				break;
			}else{
				time += power;
			}

			// 이동한 정보가 새로운 몸통 선분이 됨
			lines.add(new Line(headX, headY, nextHeadX, nextHeadY, direction));
		}


		return time;
	}

	private static boolean inRange(int x, int y) {
		return x >= 0 && y >= 0 && x <= (2 * l) && y <= (2 * l);
	}

	private static class Line {
		int x1, y1, x2, y2, direction;

		public Line(int x1, int y1, int x2, int y2, int direction) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
			this.direction = direction;
		}
	}
}