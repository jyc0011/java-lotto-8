# java-lotto-precourse

> 관련 함수를 묶어 클래스를 만들고, 객체들이 협력하여 하나의 큰 기능을 수행하도록 한다. 
> 클래스와 함수에 대한 단위 테스트를 통해 의도한 대로 정확하게 작동하는 영역을 확보한다. 
> 2주 차 공통 피드백(디스코드 참고)을 최대한 반영한다.

---

## 1. 요구사항

### 기능
간단한 로또 발매기를 구현한다.
- 로또 번호 숫자 범위 -> 1 ~ 45
- 1개의 로또에는 중복되지 않는 6개의 숫자
- 당첨 번호는 중복되지 않는 숫자 6개, 보너스 번호 1개
- 당첨은 1등부터 5등
- 입력한 구입 금액만큼 로또 발행, 가격은 1천원
- 당첨 번호와 보너스 번호를 입력받고, 로또와 당첨 번호를 비교해 당첨 내역 및 수익률을 출력, 로또 게임 종료
- 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException, 에러 메시지 출력, 그 부분부터 입력 다시

    | 구분 |  1등  |  2등   |  3등   |  4등   |  5등   |
    |:--:|:----:|:-----:|:-----:|:-----:|:-----:|
    | 일치 |  6  |  5+a  |   5   |   4   |   3   |
    | 상금 |  2억  | 3000만 | 150만  |  5만   |  5천   |

### 입출력
#### 입력
- 로또 구입 금액 입력
    - 구입 금액 1,000원 단위
    - 1,000원으로 나누어 떨어지지 않는 경우 -> IllegalArgumentException
- 당첨 번호 입력
    - 번호는 쉼표(,)로 구분
    - 중복 있을 시 ->  IllegalArgumentException
    - 공백, null, 문자, 46 이상의 수 -> IllegalArgumentException
- 보너스 번호 입력
    - 공백, null, 문자, 46 이상의 수 -> IllegalArgumentException
    - 당첨번호와 중복 시 ->  IllegalArgumentException

#### 출력
- 발행한 로또 수량 및 번호 출력, 번호는 오름차순 정렬
    ```text
    8개를 구매했습니다.
    [8, 21, 23, 41, 42, 43]
    ```
- 당첨 내역 출력
    - 수익률은 소수점 둘째 자리에서 반올림
    ```text
    당첨 통계
    ---
    3개 일치 (5,000원) - 1개
    4개 일치 (50,000원) - 0개
    5개 일치 (1,500,000원) - 0개
    5개 일치, 보너스 볼 일치 (30,000,000원) - 0개
    6개 일치 (2,000,000,000원) - 0개
    총 수익률은 62.5%입니다.
    ```
- 예외 상황 시 에러 문구를 출력("[ERROR]"로 시작)
    ```text
    [ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.
    ```

### 프로그래밍
- JDK 21
- Application의 main()에서 실행 
- build.gradle 수정 금지, 외부 라이브러리 사용 금지 
- System.exit() 호출 금지 
- 제공된 Lotto 클래스 사용.
- indent depth 2까지만 허용 (3 초과 금지)
- else 예약어 사용 금지 (if/return, Enum, 다형성 활용)
- 메서드(함수) 길이 15라인 이하
- Java Enum 적극 활용 
- UI 로직(View)을 제외한 모든 도메인/래퍼 클래스 단위 테스트(JUnit 5, AssertJ) 작성

---

## 2. 문제 분석
로또 발매부터 당첨 통계까지 과정을 시뮬레이션 해야한다. 시뮬레이션은 아래와 같은 흐름으로 진행된다.

- 입력, 검증
  - 구입 금액, 당첨 번호, 보너스 번호 입력
  - 모든 입력은 요구 사항에 따라 유효성 검증
  - 오류 발생 시, [ERROR] 문구를 출력하고 해당 입력 단계부터 재시도
- 발행
  - 구입 금액에 맞는 로또 발매 장수 계산
  - 장수만큼 Randoms API를 이용해 로또 자동 발매
  - 발매한 로또 목록 출력
- 추첨 결과 확인 및 통계 
  - 발행된 로또와 당첨 번호 비교, 일치 여부 확인 
  - 보너스 번호 일치 여부 추가 확인
  - 당첨 내역 통계 생성 
  - 최종 통계와 총수익률 계산, 출력

객체지향적인 풀이를 위해, 아래의 사항을 적용한다.

- Domain
  - Lotto: 로또 1장의 정보
  - Rank: 당첨 등급(1등~5등, 꽝)과 상금, 당첨 조건 -> Enum 
  - Stat: 최종 통계, 수익률
- 클린코드
    - else 금지, indent depth 2 제한
        - Rank.of() 같은 Enum 메서드나 전략 패턴, Guard Clause 사용
    - wrapper
        - PurchaseMoney, LottoNumber, BonusNumber
        - 생성 시점에 모든 유효성 검증 수핼
        - 도메인은 유효함이 보장된 불변 객체만을 다룬다.
    - getter 지양
        - 객체가 데이터를 get 하여 계산하는 것이 아니라, 스스로 처리
- 설계 패턴
    - SRP (단일 책임) / DIP (의존 역전)
        - AppConfig 의존성 주입
        - Controller 흐름 제어
        - Service 비지니스 로직 수행
        - View 입출력
        - Domain 데이터별 규칙 수행
        - Factory 생성
    - OCP (개방-폐쇄)
        - 로또 생성 방식 전략 패턴 인터페이스로 분리
        - 수동 생성이 추가되더라도 Controller 수정 없이 확장 가능

---

## 3. 구현 기능 목록
1. Wrapper
    - PurchaseMoney (immutable)
        - [ ] 문자열 입력을 int로 변환, 유효성 검증
        - [ ] input이 숫자가 아닐 경우 IllegalArgumentException
        - [ ] 변환된 int가 1,000원 미만일 경우 IllegalArgumentException
        - [ ] 변환된 int가 1,000으로 나누어 떨어지지 않을 경우 IllegalArgumentException
        - [ ] 구매한 로또 개수 계산
        - [ ] 원본 금액 제공
    - BonusNumber (immutable)
        - [ ] 1~45 범위 유효성 검증 -> 아닐시 IllegalArgumentException
        - [ ] 당첨 번호 중복 확인 -> 중복시 IllegalArgumentException
        - [ ] 통계 계산 시 비교를 위해 LottoNumber 제공

2. Domain
    - Lotto (immutable)
        - [ ] 로또는 6개의 Integer로 구성 (개수, 중복 위반 시 IllegalArgumentException)
        - [ ] 1~45 범위 유효성 검증 -> 아닐시 IllegalArgumentException
        - [ ] 번호의 동일 여부 확인
        - [ ] 다른 로또(당첨 번호)와 일치하는 번호의 개수를 계산
        - [ ] 특정 LottoNumber(보너스 번호)를 포함하는지 확인
        - [ ] 출력을 위해 정렬된 번호 목록을 제공
    - Lottos (immutable)
        - [ ] 발행한 Lotto 객체 목록 관리
        - [ ] 전체 구매 개수 반환
        - [ ] 내부 로또 목록을 순회
    - Rank (Enum, immutable)
        - [ ] 1등부터 5등, 꽝(MISS)까지의 당첨 규칙과 상금 저장
        - [ ] 일치하는 개수와 보너스 여부에 따라 Rank 결정
    - Stat (immutable)
        - [ ] Lottos, Lotto, BonusNumber로 당첨 통계 생성
        - [ ] Rank별 당첨 횟수 관리
        - [ ] PurchaseMoney로 총수익률 계산 -> 결과 StatDto로 반환

3. Factory
    - LottoFactory 
        - [ ] LottoGenStrategy 주입
        - [ ] 구매 개수만큼 Lotto를 생성, Lottos로 리턴

4. Strategy
    - LottoGenStrategy (Interface)
        - [ ] 로또 번호 6개 생성 규칙 정의
    - AutoGenStrategy 
        - [ ] Randoms.pickUniqueNumbersInRange(1, 45, 6)를 호출해 랜덤 번호를 생성

4. DTO
    - StatDto
        - [ ] Stat이 계산한 Rank별 당첨 횟수로 수익률 저장
        - [ ] View가 접근할 수 있는 데이터 제공

5. Service
    - LottoService
        - [ ] LottoFactory를 주입받습니다.
        - [ ] Controller 요청 -> 로또 구매를 LottoFactory에 위임
        - [ ] Controller 요청 -> 당첨 통계 계산을 Stat에 위임, DTO 리턴

6. Controller
    - LottoController 
        - [ ] LottoView, LottoProcessor 주입
        - [ ] 전체 게임 실행 제어
            - view에서 입력을 받고, processor을 통해 파싱 및 연산 수행, view를 통해 출력
    - LottoView
        - [ ] InputView, OutputView 주입
        - [ ] 입력을 InputView에게서 받음
        - [ ] 입력 재시도 제어
        - [ ] 출력 요청을 OutputView에 위임
    - LottoProcessor 
        - [ ] LottoService, InputParser 주입
        - [ ] 파싱 및 도메인 객체 생성 요청 수행
        - [ ] 비즈니스 로직 실행을 LottoService에 위임

7. View
    - InputView
        - [ ] 구입금액, 당첨 번호, 보너스 번호 입력 요청 -> Console.readLine() 리턴
     - OutputView
        - [ ] 구매한 로또 개수와 번호 목록 출력
        - [ ] 당첨 통계를 정해진 형식으로 출력
        - [ ] 총수익률을 소수점 둘째 자리에서 반올림해 출력
        - [ ] 에러 출력
    - ErrorMessage (Enum)
        - [ ] 에러 메시지 유형 정의
            - INVALID_AMOUNT
            - NOT_DIVISIBLE
            - OUT_OF_RANGE
            - DUPLICATE_NUMBER
        - [ ] 에러 메시지 문자열 반환

8. Configure
    - AppConfig 
        - [ ] 애플리케이션 실행에 필요한 객체 생성, 의존성 주입
        - [ ] View, Strategy, Factory, Parser, Service, Controller를 싱글톤으로 관리
        - [ ] Application이 LottoController()를 호출하도록 함

9. Util
    - InputParser
        - [ ] String을 알맞은 형식으로 파싱
        - [ ] 형식에 대한 검증, 실패 시 IllegalArgumentException

10. Test
    - 단위 테스트
        - PurchaseMoneyTest
            - [ ] 1000, 8000로 객체 생성
            - [ ] 8000 입력 시 로또 8개 계산
            - [ ] '1001', '999', 'abc', ' ', null 등 유효하지 않은 금액 입력 시 -> IllegalArgumentException
        - BonusNumberTest
            - [ ] 1 ~ 45 사이의 수로 객체 생성
            - [ ] x<1, x>45 등 불가능한 번호 ->IllegalArgumentException
            - [ ] new BonusNumber(10) = new BonusNumber(10)
            - [ ] 당첨 번호에 포함되지 않는 번호인 경우 생성
            - [ ] 당첨 번호에 이미 포함된 번호로 생성 시 -> IllegalArgumentException
        - LottoTest
            - [ ] 유효한 6개의 숫자 (중복 X, 범위 1-45)로 로또 생성
            - [ ] 번호 5개, 7개 -> IllegalArgumentException
            - [ ] 중복 번호 포함 -> IllegalArgumentException
            - [ ] 범위 벗어난 번호 -> IllegalArgumentException
            - [ ] 로또와 일치하는 번호 개수 계산
            - [ ] 특정 번호 포함 여부 확인
        - RankTest
            - [ ] 일치하는 수와 보너스 여부에 따라 등수 결정
        - StatTest
            - [ ] 시나리오별 당첨 통계 계산
        - LottoFactoryTest (Mock)
            - [ ] PurchaseMoney 전달 -> Lotto 생성, 알맞은 size Lottos 객체 리턴 여부 확인
        - LottoServiceTest
            - [ ] LottoFactory의 로또 생성 확인
            - [ ] Stat 객체 생성, 결과 DTO 리턴
        - LottoControllerTest
            - [ ] InputView, LottoService, OutputView 호출 여부 확인
            - [ ] 구매 금액 입력 실패 시 printError, 재입력 받아 LottoService 호출
            - [ ] 당첨 번호 입력 실패 시 printError, 재입력 받아 LottoService 호출
            - [ ] 보너스 번호 입력 실패 시 printError, 재입력 받아 LottoService 호출

    - 기능 테스트
        - [ ] 5000으로 service를 호출했을 때, size()가 5인 Lottos 반환 확인
        - [ ] 입력이 제대로 parsing 되고 있는지 확인

    - 통합 테스트
        - ApplicationTest
            - [ ] 1개 확인
                - Given: Randoms가 예제 1 ([8, 21, 23, 41, 42, 43])을 반환하도록 설정
                - When: 사용자 입력(Mock Console)으로 1000 -> 1,2,3,8,21,41 -> 42 입력
                - Then: 
                    - "1개를 구매했습니다."
                    - "[8, 21, 23, 41, 42, 43]"
                    - "4개 일치 (50,000원) - 1개"
                    - "총 수익률은 5000.0%입니다."
            - [ ] 예외 포함 확인
                - Given: Randoms가 예제 8개 목록 리턴
                - When: 사용자 입력
                    - 1001 (구매금액 1차 실패)
                    - abc (구매금액 2차 실패)
                    - 8000 (구매금액 성공)
                    - 1,2,3,4,5,5 (당첨번호 1차 실패)
                    - 1,2,3,4,5,6 (당첨번호 성공)
                    - 6 (보너스 1차 실패 - 중복)
                    - 7 (보너스 성공)
                - Then: System.out에
                    - [ERROR] 1,000원으로 나누어 떨어지지 않습니다.
                    - [ERROR] ... (숫자 아님)
                    - "8개를 구매했습니다."
                    - [ERROR] ... (중복 번호)
                    - [ERROR] ... (보너스 중복)
                    - "3개 일치 (5,000원) - 1개"
                    - "총 수익률은 62.5%입니다."
---

## 4. 프로젝트 구조
```text
   📁lotto
   ├── Application.java
   │
   ├── 📁config
   │   └── AppConfig.java
   │
   ├── 📁controller
   │   ├── LottoController.java
   │   ├── LottoView.java
   │   └── LottoProcessor.java
   │
   ├── 📁domain
   │   ├── Lotto.java
   │   ├── Lottos.java
   │   ├── Rank.java
   │   ├── Stat.java
   │   │
   │   ├── 📁wrapper
   │   │   ├── PurchaseMoney.java
   │   │   └── BonusNumber.java
   │   │
   │   ├── 📁factory
   │   │   └── LottoFactory.java
   │   │
   │   └── 📁strategy
   │       ├── LottoGenStrategy.java
   │       └── AutoGenStrategy.java
   │
   ├── 📁dto
   │   └── StatDto.java
   │
   ├── 📁service
   │   └── LottoService.java
   │
   ├── 📁util
   │   └── InputParser.java
   │
   └── 📁view
       ├── InputView.java
       ├── OutputView.java
       └── ErrorMessage.java
```

---