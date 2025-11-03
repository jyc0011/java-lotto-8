package lotto.util;

/**
 * 에러 메시지 Enum
 */
public enum ErrorMessage {
    // InputParser
    NOT_A_NUMBER("[ERROR] 입력은 숫자여야 합니다."),
    CONTAINS_BLANK("[ERROR] 입력에 공백이 포함될 수 없습니다."),
    INVALID_FORMAT("[ERROR] 입력 형식이 올바르지 않습니다."),

    // PurchaseMoney
    AMOUNT_NOT_DIVISIBLE("[ERROR] 구매 금액은 1,000원 단위여야 합니다."),
    AMOUNT_LESS_THAN_MINIMUM("[ERROR] 최소 구매 금액은 1,000원입니다."),

    // Lotto
    LOTTO_INVALID_SIZE("[ERROR] 로또 번호는 6개여야 합니다."),
    LOTTO_DUPLICATE_NUMBER("[ERROR] 로또 번호에 중복된 숫자가 있습니다."),
    LOTTO_NUMBER_OUT_OF_RANGE("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다."),

    // BonusNumber
    BONUS_NUMBER_OUT_OF_RANGE("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다."),
    BONUS_NUMBER_DUPLICATE("[ERROR] 보너스 번호가 당첨 번호와 중복됩니다.");


    private final String message;

    /**
     * 에러 메시지 생성자
     *
     * @param message "[ERROR]"가 포함된 에러 메시지 문자열
     */
    ErrorMessage(String message) {
        this.message = message;
    }

    /**
     * 에러 메시지 출력
     *
     * @return 에러 메시지
     */
    public String getMessage() {
        return message;
    }
}