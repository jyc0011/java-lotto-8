package lotto.domain;

import lotto.domain.wrapper.WinningCombo;
import lotto.dto.StatDto;
import lotto.domain.wrapper.BonusNumber;
import lotto.domain.wrapper.PurchaseMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Stat 테스트")
class StatTest {

    private Stat stat;
    private Lotto winningLotto;
    private BonusNumber bonusNumber;

    @BeforeEach
    void setUp() {
        this.stat = new Stat();
        this.winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        this.bonusNumber = new BonusNumber(7, winningLotto);
    }

    @Test
    @DisplayName("시나리오 1: 1000원 구매, 5등 1개 당첨 (수익률 500.0%)")
    void calculate_OneFifthPrize() {
        PurchaseMoney money = new PurchaseMoney(1000);
        Lotto fifthPrizeLotto = new Lotto(List.of(1, 2, 3, 10, 11, 12));
        Lottos lottos = new Lottos(List.of(fifthPrizeLotto));
        WinningCombo winningCombo = new WinningCombo(winningLotto,bonusNumber);
        StatDto result = stat.calculate(lottos, winningCombo, money);
        assertThat(result.getRankCounts().get(Rank.FIFTH)).isEqualTo(1L);
        assertThat(result.getRankCounts().get(Rank.MISS)).isEqualTo(0L);
        assertThat(result.getFormattedProfitRate()).isEqualTo("500.0%");
    }

    @Test
    @DisplayName("시나리오 2: 2000원 구매, 모두 꽝 (수익률 0.0%)")
    void calculate_AllMiss() {
        PurchaseMoney money = new PurchaseMoney(2000);
        Lotto missLotto1 = new Lotto(List.of(10, 11, 12, 13, 14, 15));
        Lotto missLotto2 = new Lotto(List.of(16, 17, 18, 19, 20, 21));
        Lottos lottos = new Lottos(List.of(missLotto1, missLotto2));
        WinningCombo winningCombo = new WinningCombo(winningLotto,bonusNumber);
        StatDto result = stat.calculate(lottos, winningCombo, money);
        assertThat(result.getRankCounts().get(Rank.MISS)).isEqualTo(2L);
        assertThat(result.getRankCounts().get(Rank.FIFTH)).isEqualTo(0L);
        assertThat(result.getFormattedProfitRate()).isEqualTo("0.0%");
    }

    @Test
    @DisplayName("시나리오 3: 모든 등수 1개씩 + 꽝 1개 (총 6000원 구매)")
    void calculate_AllRanksOnce() {
        PurchaseMoney money = new PurchaseMoney(6000);
        Lotto first = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto second = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        Lotto third = new Lotto(List.of(1, 2, 3, 4, 5, 8));
        Lotto fourth = new Lotto(List.of(1, 2, 3, 4, 8, 9));
        Lotto fifth = new Lotto(List.of(1, 2, 3, 8, 9, 10));
        Lotto miss = new Lotto(List.of(10, 11, 12, 13, 14, 15));
        Lottos lottos = new Lottos(List.of(first, second, third, fourth, fifth, miss));
        WinningCombo winningCombo = new WinningCombo(winningLotto,bonusNumber);
        StatDto result = stat.calculate(lottos, winningCombo, money);
        assertThat(result.getRankCounts())
                .containsEntry(Rank.FIRST, 1L)
                .containsEntry(Rank.SECOND, 1L)
                .containsEntry(Rank.THIRD, 1L)
                .containsEntry(Rank.FOURTH, 1L)
                .containsEntry(Rank.FIFTH, 1L)
                .containsEntry(Rank.MISS, 1L);
        assertThat(result.getFormattedProfitRate()).isEqualTo("33859250.0%");
    }
}