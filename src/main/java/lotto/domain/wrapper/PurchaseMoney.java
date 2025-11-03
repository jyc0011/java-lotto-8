package lotto.domain.wrapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lotto.domain.Lotto;
import lotto.domain.Lottos;
import lotto.domain.strategy.LottoGenStrategy;
import lotto.view.ErrorMessage;

/**
 * 로또 구입 금액 wrapper 생성할 때 1,000원 단위 및 1,000원 이상 여부 검증
 */
public class PurchaseMoney {
    private static final int LOTTO_PRICE = 1000;
    private final int amount;

    /**
     * 구매 금액 생성
     *
     * @param amount 구매 금액
     * @throws IllegalArgumentException 유효하지 않은 형식 또는 금액 규칙 위반 시
     */
    public PurchaseMoney(int amount) {
        validateUnit(amount);
        validateMinimum(amount);
        this.amount = amount;
    }

    private void validateUnit(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(ErrorMessage.AMOUNT_NOT_DIVISIBLE.getMessage());
        }
    }

    private void validateMinimum(int amount) {
        if (amount < LOTTO_PRICE) {
            throw new IllegalArgumentException(ErrorMessage.AMOUNT_LESS_THAN_MINIMUM.getMessage());
        }
    }

    /**
     * 구매 금액에 해당하는 로또 생성
     *
     * @param strategy 로또 생성 전략
     * @return 생성된 로또 묶음 (Lottos)
     */
    public Lottos purchaseLottos(LottoGenStrategy strategy) {
        int count = this.amount / LOTTO_PRICE;
        List<Lotto> generatedLottos = IntStream.range(0, count)
                .mapToObj(i -> new Lotto(strategy.generate()))
                .collect(Collectors.toList());
        return new Lottos(generatedLottos);
    }

    /**
     * 구매 금액에 대한 수익률  계산
     *
     * @return 구매 금액
     */
    public Profit calculateProfit(long totalPrize) {
        return new Profit(totalPrize, this.amount);
    }
}