package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


class LottoTest {
    @DisplayName("실패 : 로또 번호 갯수 6개 초ㅅ과 불가")
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("실패 : 로또 번호 중복 숫자 불가")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("실패: 1~45 범위 이탈")
    @ParameterizedTest(name = "{displayName} - {0}")
    @ValueSource(ints = {0, 46})
    void createLotto_Fail_OutOfRange(int outOfRangeNumber) {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, outOfRangeNumber)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    @DisplayName("성공 : 두 로또의 일치 번호 개수 확인")
    @ParameterizedTest(name = "{displayName} - {1}개 일치")
    @CsvSource({"'7,8,9,10,11,12', 0", "'1,2,3,10,11,12', 3", "'1,2,3,4,5,6', 6"})
    void countMatchingNumbers_ReturnsCorrectCount(String numbersStr, int expectedCount) {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        List<Integer> otherNumbers = java.util.Arrays.stream(numbersStr.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
        Lotto otherLotto = new Lotto(otherNumbers);
        int matchCount = lotto.countMatchingNumbers(otherLotto);
        assertThat(matchCount).isEqualTo(expectedCount);
    }

    @DisplayName("성공 : 로또 특정 번호 포함 여부 확인")
    @Test
    void contains_ReturnsCorrectBoolean() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThat(lotto.contains(6)).isTrue();
        assertThat(lotto.contains(7)).isFalse();
    }

    @DisplayName("성공 : 정렬된 결과 반환")
    @Test
    void getSortedNumbers_ReturnsSortedList() {
        Lotto lotto = new Lotto(List.of(6, 5, 4, 3, 2, 1));
        String expected = "[1, 2, 3, 4, 5, 6]";
        String sortedNumbers = lotto.toString();
        assertThat(sortedNumbers).isEqualTo(expected);
    }
}
