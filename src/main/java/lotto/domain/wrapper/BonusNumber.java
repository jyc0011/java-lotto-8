package lotto.domain.wrapper;

import lotto.domain.Lotto;
import lotto.view.ErrorMessage;

import java.util.Objects;

/**
 * 보너스 번호 1개에 대한 Wrapper
 * 생성할 때 유효성 검증
 */
public class BonusNumber {

    private final int number;

    /**
     * 보너스 번호 생성
     *
     * @param number       보너스 번호 (1~45)
     * @param winningNumber 당첨 번호(Lotto 객체)
     * @throws IllegalArgumentException 1~45 사이의 수가 아님 or 당첨 번호와 중복될
     */
    public BonusNumber(int number, Lotto winningNumber) {
        validateRange(number);
        validateDuplicate(number, winningNumber);
        this.number = number;
    }

    /**
     * 보너스 번호가 1~45 사이의 수인지
     */
    private void validateRange(int number) {
        if (number < 1 || number > 45) {
            // TODO: ErrorMessage Enum에서 메시지 가져오기
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    /**
     * 보너스 번호와 당첨 번호 중복 검증
     */
    private void validateDuplicate(int number, Lotto winningNumber) {
        if (winningNumber.contains(number)) {
            // TODO: ErrorMessag에서 메시지 가져오기
            throw new IllegalArgumentException("[ERROR] 보너스 번호가 당첨 번호와 중복됩니다.");
        }
    }

    /**
     * 보너스 번호 리턴
     *
     * @return 보너스 번호
     */
    public int getNumber() {
        return number;
    }

}