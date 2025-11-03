package lotto.domain.factory;

import lotto.domain.Lottos;
import lotto.domain.strategy.LottoGenStrategy;
import lotto.domain.wrapper.PurchaseMoney;

/**
 * Lotto와 Lottos 생성, LottoGenStrategy 주입
 */
public class LottoFactory {
    private final LottoGenStrategy strategy;

    /**
     * LottoGenStrategy 주입, 생성
     *
     * @param strategy
     */
    public LottoFactory(LottoGenStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * 구매 금액만큼 로또 생성
     *
     * @param money 구매 금액
     * @return 생성된 Lottos
     */
    public Lottos createLottos(PurchaseMoney money) {
        return money.purchaseLottos(this.strategy);
    }
}