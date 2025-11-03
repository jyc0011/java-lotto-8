package lotto.domain.wrapper;

import lotto.domain.Lotto;
import lotto.view.ErrorMessage;

/**
 * 보너스 번호 1개에 대한 Wrapper 생성할 때 유효성 검증
 */
public class BonusNumber {

    private final int number;

    /**
     * 보너스 번호 생성
     *
     * @param number        보너스 번호 (1~45)
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
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_OUT_OF_RANGE.getMessage());
        }
    }

    /**
     * 보너스 번호와 당첨 번호 중복 검증
     */
    private void validateDuplicate(int number, Lotto winningNumber) {
        if (winningNumber.contains(number)) {
            throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_DUPLICATE.getMessage());
        }
    }

    /**
     * 주어진 로또(lotto)에 포함되어 있는지 확인
     *
     * @param lotto 확인할 로또 1장
     * @return 포함되어 있으면 true
     */
    public boolean isContainedIn(Lotto lotto) {
        return lotto.contains(this.number);
    }

}