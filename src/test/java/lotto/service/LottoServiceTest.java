package lotto.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.Lottos;
import lotto.domain.Rank;
import lotto.domain.factory.LottoFactory;
import lotto.domain.strategy.AutoGenStrategy;
import lotto.domain.strategy.LottoGenStrategy;
import lotto.domain.wrapper.BonusNumber;
import lotto.domain.wrapper.PurchaseMoney;
import lotto.domain.wrapper.WinningCombo;
import lotto.dto.StatDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("로또 서비스(LottoService) 테스트")
class LottoServiceTest {

    @Test
    @DisplayName("성공 : Stat 계산하여 DTO 출력")
    void calculateStatistics_ShouldOrchestrate_StatCalculation() {
        LottoService lottoService = new LottoService(null);
        PurchaseMoney money = new PurchaseMoney(1000);
        Lotto winningNumber = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber(7, winningNumber);
        WinningCombo winningCombo = new WinningCombo(winningNumber, bonusNumber);
        Lotto fifthPrizeLotto = new Lotto(List.of(1, 2, 3, 10, 11, 12));
        Lottos lottos = new Lottos(List.of(fifthPrizeLotto));
        StatDto result = lottoService.calculateStatistics(lottos, winningCombo, money);
        assertThat(result.getRankCounts().get(Rank.FIFTH)).isEqualTo(1L);
        assertThat(result.getFormattedProfitRate()).isEqualTo("500.0%");
    }

    @Test
    @DisplayName("성공 : 팩토리를 생성해 구매 금액만큼 로또 생성")
    void purchaseLottos_ShouldCallFactory_AndReturnLottos() {
        LottoGenStrategy strategy = new AutoGenStrategy();
        LottoFactory factory = new LottoFactory(strategy);
        LottoService lottoService = new LottoService(factory);
        PurchaseMoney money = new PurchaseMoney(8000);
        Lottos lottos = lottoService.purchaseLottos(money);
        assertThat(lottos.size()).isEqualTo(8);
    }
}