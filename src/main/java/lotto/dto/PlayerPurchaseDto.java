package lotto.dto;

import lotto.domain.Lottos;
import lotto.domain.wrapper.PurchaseMoney;

public class PlayerPurchaseDto {
    private final Lottos lottos;
    private final PurchaseMoney money;

    public PlayerPurchaseDto(Lottos lottos, PurchaseMoney money) {
        this.lottos = lottos;
        this.money = money;
    }

    public Lottos getLottos() {
        return lottos;
    }

    public PurchaseMoney getMoney() {
        return money;
    }
}