package lotto.service;

import lotto.domain.Lottos;
import lotto.domain.Stat;
import lotto.domain.factory.LottoFactory;
import lotto.domain.wrapper.PurchaseMoney;
import lotto.domain.wrapper.WinningCombo;
import lotto.dto.StatDto;

/**
 * 서비스
 */
public class LottoService {
    private final LottoFactory lottoFactory;

    /**
     * LottoFactory 주입, 객체 생성
     *
     * @param lottoFactory
     */
    public LottoService(LottoFactory lottoFactory) {
        this.lottoFactory = lottoFactory;
    }

    /**
     * 구매 금액으로 로또 생성, 해당 부분을 factory에 위임
     *
     * @param money 구매 금액 (Wrapper)
     * @return 생성된 로또 묶음 (일급 컬렉션)
     */
    public Lottos purchaseLottos(PurchaseMoney money) {
        return lottoFactory.createLottos(money);
    }

    /**
     * 당첨 통계 계산을 stat에 위임
     *
     * @param purchasedLottos 구매한 로또 묶음
     * @param winningCombo    당첨 조합 (Lotto + BonusNumber)
     * @param money           구매 금액
     * @return 통계 결과 DTO
     */
    public StatDto calculateStatistics(Lottos purchasedLottos, WinningCombo winningCombo, PurchaseMoney money) {
        Stat stat = new Stat();
        return stat.calculate(purchasedLottos, winningCombo, money);
    }
}