package lotto.domain.wrapper;

import lotto.domain.Lotto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("보너스 번호 테스트")
class BonusNumberTest {

    private Lotto winningNumber;

    /**
     * 당첨 번호와의 중복 여부를 검사하기 때문에, 미리 생성
     */
    @BeforeEach
    void setUp() {
        winningNumber = new Lotto(List.of(1, 2, 3, 4, 5, 6));
    }

    @Test
    @DisplayName("성공 : 유효한 번호로 생성")
    void createBonusNumber_Success() {
        int validNumber = 7;
        BonusNumber bonusNumber = new BonusNumber(validNumber, winningNumber);
        assertThat(bonusNumber.getNumber()).isEqualTo(7);
    }

    @ParameterizedTest(name = "{displayName} - {1} (입력값: {0})")
    @CsvSource({"0,  '1~45 범위를 벗어남'", "46, '1~45 범위를 벗어남'", "6,  '당첨 번호와 중복'"})
    @DisplayName("실패 : 유효하지 않은 번호는 생성 불가")
    void createBonusNumber_Fail_InvalidInput(int invalidNumber, String reason) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new BonusNumber(invalidNumber, winningNumber))
                .withMessageContaining("[ERROR]");
    }
}