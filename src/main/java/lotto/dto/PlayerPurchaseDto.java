package lotto.dto;

import lotto.domain.Lottos;
import lotto.domain.wrapper.PurchaseMoney;

/**
 * 사용자의 구매 정보 DTO
 * 데이터 전달 파라미터 객체
 */
public class PlayerPurchaseDto {
    private final Lottos lottos;
    private final PurchaseMoney money;

    /**
     * 구매 정보 DTO 생성
     *
     * @param lottos 사용자가 구매한 로또 묶음 (일급 컬렉션)
     * @param money  사용자가 지불한 구매 금액
     */
    public PlayerPurchaseDto(Lottos lottos, PurchaseMoney money) {
        this.lottos = lottos;
        this.money = money;
    }

    /**
     * 구매한 로또 묶음 리턴
     *
     * @return 구매한 로또 묶음
     */
    public Lottos getLottos() {
        return lottos;
    }

    /**
     * 구매 금액 반환
     *
     * @return 구매 금액
     */
    public PurchaseMoney getMoney() {
        return money;
    }
}