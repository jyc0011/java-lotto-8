package lotto.domain.factory;

import lotto.domain.Lotto;
import lotto.domain.Lottos;
import lotto.domain.strategy.LottoGenStrategy;
import lotto.domain.wrapper.PurchaseMoney;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LottoFactory 테스트")
class LottoFactoryTest {

    @Test
    @DisplayName("성공 : 8000원(8장) 입력시 전략 8회 호출, Lottos 객체 출력")
    void createLottos_ShouldCallStrategy_MultipleTimes() {
        SpyLottoGenStrategy spyStrategy = new SpyLottoGenStrategy();
        LottoFactory lottoFactory = new LottoFactory(spyStrategy);
        PurchaseMoney money = new PurchaseMoney(8000);
        Lottos lottos = lottoFactory.createLottos(money);
        assertThat(lottos.size()).isEqualTo(8);
        assertThat(spyStrategy.getCallCount()).isEqualTo(8);
        List<String> lottoStrings = lottos.stream()
                .map(Lotto::toString)
                .toList();
        String expectedLottoString = "[1, 2, 3, 4, 5, 6]";
        assertThat(lottoStrings).allMatch(str -> str.equals(expectedLottoString));
    }

    /**
     * Strategy 호출 횟수 확인을 위한 테스트용 클래스
     */
    private static class SpyLottoGenStrategy implements LottoGenStrategy {
        private int callCount = 0;
        private final List<Integer> numbersToReturn = List.of(1, 2, 3, 4, 5, 6);

        @Override
        public List<Integer> generate() {
            callCount++;
            return numbersToReturn;
        }

        public int getCallCount() {
            return callCount;
        }
    }
}