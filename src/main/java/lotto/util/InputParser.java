package lotto.util;

// import lotto.view.ErrorMessage; // ErrorMessage Enum 경로

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lotto.view.ErrorMessage;

/**
 * 사용자 입력을 파싱(Parsing)하고, 입력 형식(Format)을 검증하는 책임을 가지는 클래스입니다. (Stateless)
 */
public class InputParser {

    private static final String WINNING_NUMBER_DELIMITER = ",";
    private static final String REGEX_ONLY_DIGITS = "\\d+";
    private static final int LOTTO_NUMBER_COUNT = 6;

    /**
     * 단일 숫자 입력을 파싱합니다. (구매 금액, 보너스 번호용)
     *
     * @param input 사용자의 원시 입력
     * @return 파싱된 int
     * @throws IllegalArgumentException 숫자가 아니거나 공백이 포함된 경우
     */
    public int parseInt(String input) {
        validateNotNullOrBlank(input);
        validateNumericFormat(input);
        return Integer.parseInt(input);
    }

    /**
     * 당첨 번호 입력을 파싱합니다. (e.g., "1,2,3,4,5,6")
     *
     * @param input 사용자의 원시 입력
     * @return 파싱된 List<Integer>
     * @throws IllegalArgumentException 6개가 아니거나, 공백이 있거나, 숫자가 아닌 경우
     */
    public List<Integer> parseWinningNumbers(String input) {
        validateNotNullOrBlank(input);
        validateNoSpaces(input); // "1, 2, 3" 같은 입력 방지

        String[] numberStrings = input.split(WINNING_NUMBER_DELIMITER);
        validateCount(numberStrings);

        // 'parseInt' 메서드를 재활용하여 각 숫자를 파싱 및 검증
        try {
            return Arrays.stream(numberStrings)
                    .map(this::parseInt)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessage.NOT_A_NUMBER.getMessage());
        }
    }

    private void validateNotNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(ErrorMessage.CONTAINS_BLANK.getMessage());
        }
    }

    private void validateNumericFormat(String input) {
        if (!input.matches(REGEX_ONLY_DIGITS)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT.getMessage());
        }
    }

    private void validateNoSpaces(String input) {
        if (input.contains(" ")) {
            throw new IllegalArgumentException(ErrorMessage.CONTAINS_BLANK.getMessage());
        }
    }

    private void validateCount(String[] parts) {
        if (parts.length != LOTTO_NUMBER_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_INVALID_SIZE.getMessage());
        }
    }
}