package lotto.domain.wrapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("수익률(ProfitRate) 테스트")
class ProfitTest {

    @ParameterizedTest(name = "{displayName} - 총상금: {0}원, 구매금액: {1}원 -> {2}")
    @CsvSource({"5000,8000,'62.5%'", "5000,1000,'500.0%'", "8000,8000,'100.0%'", "0, 8000,'0.0%'", "1000,3000,'33.3%'"})
    @DisplayName("총상금과 구매금액으로 수익률을 정확히 계산하고 포매팅한다.")
    void getFormattedRate_Success(long totalPrize, int purchaseAmount, String expectedFormat) {
        Profit profitRate = new Profit(totalPrize, purchaseAmount);
        assertThat(profitRate.formattedRate()).isEqualTo(expectedFormat);
    }

    @Test
    @DisplayName("구매금액이 0일 경우(Division by zero)에도 0.0%를 안전하게 반환한다.")
    void getFormattedRate_Handle_DivisionByZero() {
        long totalPrize = 5000;
        int purchaseAmount = 0;
        Profit profitRate = new Profit(totalPrize, purchaseAmount);
        assertThat(profitRate.formattedRate()).isEqualTo("0.0%");
    }

    @Test
    @DisplayName("요구사항의 반올림(소수점 둘째 자리)을 정확히 수행한다.")
    void getFormattedRate_RoundingTest() {
        long totalPrize = 5156;
        int purchaseAmount = 10000;
        Profit profitRate = new Profit(totalPrize, purchaseAmount);
        assertThat(profitRate.formattedRate()).isEqualTo("51.6%");
    }
}