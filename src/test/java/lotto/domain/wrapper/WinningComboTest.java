package lotto.domain.wrapper;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("WinningCombo 테스트")
class WinningComboTest {

    private WinningCombo winningCombo;

    /**
     * 테스트에 사용할 당첨 조합 생성
     */
    @BeforeEach
    void setUp() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber(7, winningLotto); // 7
        this.winningCombo = new WinningCombo(winningLotto, bonusNumber);
    }

    @ParameterizedTest(name = "{displayName} - {0} -> {1}")
    @CsvSource({"'1,2,3,4,5,6',   FIRST", "'1,2,3,4,5,7',   SECOND", "'1,2,3,4,5,8',   THIRD",
            "'1,2,3,4,8,9',   FOURTH", "'1,2,3,8,9,10',  FIFTH",
            "'1,2,8,9,10,11', MISS", "'8,9,10,11,12,13', MISS"})
    @DisplayName("성공 : 구매한 로또를 받아 정확한 등수 출력")
    void determineRank_ReturnsCorrectRank(String lottoNumbersStr, Rank expectedRank) {
        List<Integer> numbers = Arrays.stream(lottoNumbersStr.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        Lotto purchasedLotto = new Lotto(numbers);
        Rank actualRank = winningCombo.determineRank(purchasedLotto);
        assertThat(actualRank).isEqualTo(expectedRank);
    }
}