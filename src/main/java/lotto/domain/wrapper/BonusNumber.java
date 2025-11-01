package lotto.domain.wrapper;

import lotto.domain.Lotto;
// import lotto.view.ErrorMessage; // ErrorMessage Enum 경로

import java.util.Objects;

/**
 * 보너스 번호 1개를 포장하는 래퍼 클래스입니다.
 * 생성 시점에 1~45 범위 검증 및 당첨 번호와의 중복 검증을 수행합니다.
 */
public class BonusNumber {

    private final int number;

    /**
     * 보너스 번호를 생성합니다.
     *
     * @param number       보너스 번호 (1~45)
     * @param winningLotto 당첨 번호(Lotto 객체)
     * @throws IllegalArgumentException 1~45 범위를 벗어나거나 당첨 번호와 중복될 경우
     */
    public BonusNumber(int number, Lotto winningLotto) {
        validateRange(number);
        validateDuplicate(number, winningLotto);
        this.number = number;
    }

    /**
     * 보너스 번호가 1~45 범위 내에 있는지 검증합니다.
     */
    private void validateRange(int number) {
        if (number < 1 || number > 45) {
            // TODO: ErrorMessage Enum에서 메시지 가져오기
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    /**
     * 보너스 번호가 당첨 번호와 중복되는지 검증합니다.
     * (Lotto.contains(int) 메서드에 의존합니다.)
     */
    private void validateDuplicate(int number, Lotto winningLotto) {
        if (winningLotto.contains(number)) {
            // TODO: ErrorMessage Enum에서 메시지 가져오기
            throw new IllegalArgumentException("[ERROR] 보너스 번호가 당첨 번호와 중복됩니다.");
        }
    }

    /**
     * 포장된 보너스 번호(int)를 반환합니다.
     *
     * @return 보너스 번호
     */
    public int getNumber() {
        return number;
    }

    /**
     * BonusNumber 객체의 동등성을 비교합니다.
     *
     * @param o 비교할 객체
     * @return number 필드 값이 같으면 true
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonusNumber that = (BonusNumber) o;
        return number == that.number;
    }

    /**
     * number 필드 값을 기반으로 해시 코드를 생성합니다.
     *
     * @return 해시 코드
     */
    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}