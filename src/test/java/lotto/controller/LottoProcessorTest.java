package lotto.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.Lottos;
import lotto.domain.factory.LottoFactory;
import lotto.domain.strategy.AutoGenStrategy;
import lotto.domain.strategy.LottoGenStrategy;
import lotto.domain.wrapper.BonusNumber;
import lotto.domain.wrapper.PurchaseMoney;
import lotto.domain.wrapper.WinningCombo;
import lotto.dto.PlayerPurchaseDto;
import lotto.dto.StatDto;
import lotto.service.LottoService;
import lotto.util.InputParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LottoProcessor 테스트")
class LottoProcessorTest {

    private LottoProcessor processor;

    @BeforeEach
    void setUp() {
        InputParser parser = new InputParser();
        LottoGenStrategy strategy = new AutoGenStrategy();
        LottoFactory factory = new LottoFactory(strategy);
        LottoService service = new LottoService(factory);
        processor = new LottoProcessor(service, parser);
    }

    @Test
    @DisplayName("실패: parser에서 PurchaseMoney 생성 실패")
    void createPurchaseMoney_Fail_InvalidFormat() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> processor.createPurchaseMoney("abc"))
                .withMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("실패: wrapper에서 PurchaseMoney 생성 실패")
    void createPurchaseMoney_Fail_InvalidRule() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> processor.createPurchaseMoney("999"))
                .withMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("성공 : 로또 생성")
    void purchaseLottos_IntegrationSuccess() {
        PurchaseMoney money = new PurchaseMoney(3000);
        PlayerPurchaseDto purchase = processor.purchaseLottos(money);
        assertThat(purchase.getLottos().size()).isEqualTo(3);
        assertThat(purchase.getMoney()).isEqualTo(money);
    }

    @Test
    @DisplayName("성공 : Service와 Stat으로 통계 계산")
    void calculateStatistics_IntegrationSuccess() {
        PurchaseMoney money = new PurchaseMoney(1000);
        Lotto fifthPrizeLotto = new Lotto(List.of(1, 2, 3, 10, 11, 12));
        Lottos lottos = new Lottos(List.of(fifthPrizeLotto));
        PlayerPurchaseDto purchase = new PlayerPurchaseDto(lottos, money);

        Lotto winningNumber = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber(7, winningNumber);
        WinningCombo winningCombo = new WinningCombo(winningNumber, bonusNumber);
        StatDto result = processor.calculateStatistics(purchase, winningCombo);
        assertThat(result.getFormattedProfitRate()).isEqualTo("500.0%");
    }
}