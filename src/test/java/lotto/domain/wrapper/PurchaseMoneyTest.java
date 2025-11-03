package lotto.domain.wrapper;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("구매 금액 테스트")
class PurchaseMoneyTest {

    @ParameterizedTest(name = "{displayName} - 입력값: {0}")
    @ValueSource(ints = {1001, 999, 500, -1000, 0})
    @DisplayName("실패 : 유효하지 않은 입력(규칙, 형식, null) 시 생성 불가")
    void createPurchaseMoney_Fail_AllInvalidInputs(int invalidAmount) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PurchaseMoney(invalidAmount))
                .withMessageContaining("[ERROR]");
    }
}