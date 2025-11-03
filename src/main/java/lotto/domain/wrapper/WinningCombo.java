package lotto.domain.wrapper;

import lotto.domain.Lotto;
import lotto.domain.Rank;

/**
 * 당첨 번호 + 보너스 번호
 */
public class WinningCombo {

    private final Lotto winningNumber;
    private final BonusNumber bonusNumber;

    /**
     * 당첨 조합 생성
     *
     * @param winningNumber 당첨 번호 6개
     * @param bonusNumber  보너스 번호 1개
     */
    public WinningCombo(Lotto winningNumber, BonusNumber bonusNumber) {
        this.winningNumber = winningNumber;
        this.bonusNumber = bonusNumber;
    }

    /**
     * 주어진 로또와 당첨 번호를 비교해 등수 결정
     *
     * @param lotto 사용자가 구매한 로또 1장
     * @return Rank (1등 ~ 꽝)
     */
    public Rank determineRank(Lotto lotto) {
        int matchCount = lotto.countMatchingNumbers(winningNumber);
        boolean hasBonus = bonusNumber.isContainedIn(lotto);
        return Rank.of(matchCount, hasBonus);
    }
}