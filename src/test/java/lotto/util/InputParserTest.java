package lotto.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("입력 파서(InputParser) 테스트")
class InputParserTest {

    private InputParser parser;

    @BeforeEach
    void setUp() {
        parser = new InputParser();
    }

    @ParameterizedTest(name = "{displayName} - 입력: {0}")
    @ValueSource(strings = {"8000", "7", "1000"})
    @DisplayName("성공: 단일 숫자 문자열을 int로 파싱")
    void parseInt_Success(String input) {
        int result = parser.parseInt(input);
        assertThat(result).isEqualTo(Integer.parseInt(input));
    }

    @ParameterizedTest(name = "{displayName} - 입력: \"{0}\"")
    @ValueSource(strings = {"abc", " ", "", "1000 ", " 1000", "1 000"})
    @NullSource
    @DisplayName("실패: 유효하지 않은 형식")
    void parseInt_Fail_InvalidFormat(String invalidInput) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> parser.parseInt(invalidInput))
                .withMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("성공: 쉼표로 구분된 6개의 숫자 문자열")
    void parseWinningNumbers_Success() {
        String input = "1,2,3,4,5,6";
        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> result = parser.parseWinningNumbers(input);
        assertThat(result).isEqualTo(expected);
    }


    @ParameterizedTest(name = "{displayName} - 입력: \"{0}\"")
    @ValueSource(strings = {
            "1,2,3,4,5", "1,2,3,4,5,6,7", "1,2,3,4,5,a", "1, 2, 3, 4, 5, 6", "1,2,3,4,5,"})
    @NullSource
    @DisplayName("실패: 유효하지 않은 형식")
    void parseWinningNumbers_Fail_InvalidFormatOrCount(String invalidInput) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> parser.parseWinningNumbers(invalidInput))
                .withMessageContaining("[ERROR]");
    }
}