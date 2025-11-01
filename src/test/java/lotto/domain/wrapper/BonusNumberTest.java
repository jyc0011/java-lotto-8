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

@DisplayName("보너스 번호(BonusNumber) 테스트")
class BonusNumberTest {

    private Lotto winningLotto;

    /**
     * BonusNumber는 Lotto(당첨 번호)에 의존하므로, 테스트용 Lotto 객체를 미리 생성합니다.
     * [전제 조건] 이 테스트를 통과하려면 'Lotto.java'의 생성자 및 'contains(int)' 메서드가 구현되어 있어야 합니다.
     */
    @BeforeEach
    void setUp() {
        winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
    }

    @Test
    @DisplayName("유효한 번호(범위 O, 중복 X)로 생성에 성공한다.")
    void createBonusNumber_Success() {
        // given
        int validNumber = 7;

        // when
        BonusNumber bonusNumber = new BonusNumber(validNumber, winningLotto);

        // then
        assertThat(bonusNumber.getNumber()).isEqualTo(7);
    }

    @ParameterizedTest(name = "{displayName} - {1} (입력값: {0})")
    @CsvSource({
            "0,  '1~45 범위를 벗어남'",
            "46, '1~45 범위를 벗어남'",
            "6,  '당첨 번호와 중복'"
    })
    @DisplayName("유효하지 않은 번호로 생성 시 예외가 발생한다.")
    void createBonusNumber_Fail_InvalidInput(int invalidNumber, String reason) {
        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new BonusNumber(invalidNumber, winningLotto))
                .withMessageContaining("[ERROR]"); // ErrorMessage Enum 사용을 가정
    }

    @Test
    @DisplayName("동일한 값을 가진 BonusNumber는 동등하다.")
    void equals_And_HashCode_Test() {
        // given
        // BonusNumber의 동등성은 'Lotto'가 아닌 'number' 값 자체를 기준으로 합니다.
        BonusNumber bonusA = new BonusNumber(7, winningLotto);
        BonusNumber bonusB = new BonusNumber(7, winningLotto);
        BonusNumber bonusC = new BonusNumber(8, winningLotto);

        // when & then
        assertThat(bonusA).isEqualTo(bonusB);
        assertThat(bonusA.hashCode()).isEqualTo(bonusB.hashCode());
        assertThat(bonusA).isNotEqualTo(bonusC);
        assertThat(bonusA.hashCode()).isNotEqualTo(bonusC.hashCode());
    }
}