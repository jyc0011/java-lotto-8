package lotto.domain;

import java.util.EnumMap;
import java.util.Map;
import lotto.domain.wrapper.Profit;
import lotto.domain.wrapper.PurchaseMoney;
import lotto.domain.wrapper.WinningCombo;
import lotto.dto.StatDto;

/**
 * 당첨 통계 계산
 */
public class Stat {

    /**
     * 로또 구매 내역과 당첨 번호 통계 및 수익률 계산
     *
     * @param purchasedLottos 구매한 모든 로또 (일급 컬렉션)
     * @param winningCombo    당첨 조합 (Lotto + BonusNumber)
     * @param money           구매 금액 (PurchaseMoney)
     * @return 통계 결과 DTO (StatResult)
     */
    public StatDto calculate(Lottos purchasedLottos, WinningCombo winningCombo, PurchaseMoney money) {
        Map<Rank, Long> rankCounts = calculateRankCounts(purchasedLottos, winningCombo);
        Profit profitRate = calculateProfitRate(rankCounts, money);
        return new StatDto(rankCounts, profitRate);
    }

    private Map<Rank, Long> calculateRankCounts(Lottos purchasedLottos, WinningCombo winningCombo) {
        Map<Rank, Long> rankCounts = new EnumMap<>(Rank.class);
        for (Rank rank : Rank.values()) {
            rankCounts.put(rank, 0L);
        }
        purchasedLottos.stream()
                .map(winningCombo::determineRank)
                .forEach(rank -> rankCounts.put(rank, rankCounts.get(rank) + 1));
        return rankCounts;
    }

    private Profit calculateProfitRate(Map<Rank, Long> rankCounts, PurchaseMoney money) {
        long totalPrize = rankCounts.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
        return money.calculateProfit(totalPrize);
    }
}