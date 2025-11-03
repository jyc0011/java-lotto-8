package lotto.dto;

import lotto.domain.Rank;
import lotto.domain.wrapper.Profit;

import java.util.Collections;
import java.util.Map;

/**
 * Stat 계산 결과를 View 계층으로 전달하는 DTO
 */
public class StatDto {

    private final Map<Rank, Long> rankCounts;
    private final Profit profit;

    /**
     * 통계 결과 DTO 생성
     *
     * @param rankCounts 등수별 당첨 횟수 맵
     * @param profit 계산된 수익률 객체
     */
    public StatDto(Map<Rank, Long> rankCounts, Profit profit) {
        this.rankCounts = Collections.unmodifiableMap(rankCounts);
        this.profit = profit;
    }

    /**
     * 등수별 당첨 횟수 출력
     *
     * @return Map (Key: Rank, Value: 횟수)
     */
    public Map<Rank, Long> getRankCounts() {
        return rankCounts;
    }

    /**
     * 수익률 출력
     *
     * @return ProfitRate 래퍼 객체
     */
    public String getFormattedProfitRate() {
        return profit.formattedRate();
    }
}