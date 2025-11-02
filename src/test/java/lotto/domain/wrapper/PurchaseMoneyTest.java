package lotto.domain.wrapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("구매 금액 테스트")
class PurchaseMoneyTest {

    @ParameterizedTest
    @CsvSource({"1000, 1", "8000, 8", "120000, 120"})
    @DisplayName("성공 : 유효한 금액 입력")
    void createPurchaseMoney_Success(int amount, int expectedCount) {
        PurchaseMoney money = new PurchaseMoney(amount);
        assertThat(money.getLottoCount()).isEqualTo(expectedCount);
        assertThat(money.getAmount()).isEqualTo(amount);
    }

    @ParameterizedTest(name = "{displayName} - 입력값: {0}")
    @ValueSource(ints = {1001, 999, 500, -1000, 0})
    @DisplayName("실패 : 유효하지 않은 입력(규칙, 형식, null) 시 생성 불가")
    void createPurchaseMoney_Fail_AllInvalidInputs(int invalidAmount) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PurchaseMoney(invalidAmount))
                .withMessageContaining("[ERROR]");
    }
}