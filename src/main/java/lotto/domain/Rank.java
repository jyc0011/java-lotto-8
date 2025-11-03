package lotto.domain;

import java.util.Arrays;

/**
 * 로또 당첨 등수와 상금 정의
 */
public enum Rank {
    FIRST(6, 2_000_000_000L, false),
    SECOND(5, 30_000_000L, true),
    THIRD(5, 1_500_000L, false),
    FOURTH(4, 50_000L, false),
    FIFTH(3, 5_000L, false),
    MISS(0, 0L, false);

    private final int matchCount;
    private final long prizeMoney;
    private final boolean needsBonus;

    /**
     * Rank Enum 생성자
     *
     * @param matchCount 당첨에 필요한 일치 개수
     * @param prizeMoney 당첨 상금
     * @param needsBonus 당첨에 보너스 번호가 필요한지 여부 (2등만 true)
     */
    Rank(int matchCount, long prizeMoney, boolean needsBonus) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
        this.needsBonus = needsBonus;
    }

    /**
     * 일치 개수와 보너스 여부에 따라 Rank 결정
     *
     * @param matchCount 일치하는 번호의 개수
     * @param hasBonus   보너스 번호 일치 여부
     * @return 결정된 Rank
     */
    public static Rank of(int matchCount, boolean hasBonus) {
        if (matchCount == 5 && hasBonus) {
            return SECOND;
        }
        return Arrays.stream(values())
                .filter(rank -> rank != SECOND && rank.matchCount == matchCount)
                .findFirst()
                .orElse(MISS);
    }

    /**
     * 당첨 상금 출력
     *
     * @return 당첨 상금 (long)
     */
    public long getPrizeMoney() {
        return prizeMoney;
    }

    /**
     * 일치 개수 출력
     * @return 일치 개수 (int)
     */
    public int getMatchCount() {
        return matchCount;
    }

    /**
     * 보너스 번호 필요 여부 출력
     * @return 보너스 필요 여부 (boolean)
     */
    public boolean needsBonus() {
        return needsBonus;
    }
}