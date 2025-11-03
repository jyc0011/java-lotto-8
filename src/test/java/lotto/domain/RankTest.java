package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Rank 테스트")
class RankTest {

    /**
     * 모든 당첨 케이스 확인
     */
    @ParameterizedTest(name = "{displayName} - {0}개 일치, 보너스 {1} -> {2}")
    @CsvSource({"6, true,  FIRST", "6, false, FIRST", "5, true,  SECOND", "5, false, THIRD",
            "4, true,  FOURTH", "4, false, FOURTH", "3, true,  FIFTH", "3, false, FIFTH",
            "2, true,  MISS", "2, false, MISS", "1, true,  MISS", "1, false, MISS",
            "0, true,  MISS", "0, false, MISS"})
    @DisplayName("성공 : 일치 개수와 보너스 여부로 등수 출력")
    void rankOf_ReturnsCorrectRank(int matchCount, boolean hasBonus, Rank expectedRank) {
        Rank actualRank = Rank.of(matchCount, hasBonus);
        assertThat(actualRank).isEqualTo(expectedRank);
    }

    /**
     * 모든 Rank의 상금 확인
     */
    @ParameterizedTest
    @EnumSource(Rank.class)
    @DisplayName("성공 : 등수로 상금 출력")
    void getPrizeMoney_ReturnsCorrectAmount(Rank rank) {
        long prize = rank.getPrizeMoney();
        long expectedPrize = switch (rank) {
            case FIRST -> 2_000_000_000L;
            case SECOND -> 30_000_000L;
            case THIRD -> 1_500_000L;
            case FOURTH -> 50_000L;
            case FIFTH -> 5_000L;
            case MISS -> 0L;
        };
        assertThat(prize).isEqualTo(expectedPrize);
    }
}