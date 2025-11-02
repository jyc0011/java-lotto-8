package lotto.domain.wrapper;

import lotto.view.ErrorMessage;

/**
 * 로또 구입 금액 wrapper
 * 생성할 때 1,000원 단위 및 1,000원 이상 여부 검증
 */
public class PurchaseMoney {
    private final int amount;

    private static final int LOTTO_PRICE = 1000;

    /**
     * 구매 금액 생성
     *
     * @param amount 구매 금액
     * @throws IllegalArgumentException 유효하지 않은 형식 또는 금액 규칙 위반 시
     */
    public PurchaseMoney(int amount) {
        validateUnit(amount);
        validateMinimum(amount);
        this.amount = amount;
    }

    /**
     *1000원 단위인지 확인
     */
    private void validateUnit(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            // TODO: ErrorMessage Enum에서 메시지 가져오기
            throw new IllegalArgumentException("[ERROR] 금액은 1,000원 단위여야 합니다.");
        }
    }

    /**
     * 최소 금액 이상인지 확인
     */
    private void validateMinimum(int amount) {
        if (amount < LOTTO_PRICE) {
            // TODO: ErrorMessage Enum에서 메시지 가져오기
            throw new IllegalArgumentException("[ERROR] 최소 구입 금액은 1,000원입니다.");
        }
    }

    /**
     * 구매 로또 개수 리턴
     *
     * @return 로또 개수
     */
    public int getLottoCount() {
        return amount / LOTTO_PRICE;
    }

    /**
     * 구매 금액 리턴
     *
     * @return 구매 금액
     */
    public int getAmount() {
        return amount;
    }
}