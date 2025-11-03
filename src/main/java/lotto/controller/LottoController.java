package lotto.controller;

import lotto.domain.Lotto;
import lotto.dto.PlayerPurchaseDto;
import lotto.domain.wrapper.WinningCombo;
import lotto.dto.StatDto;
import lotto.domain.wrapper.BonusNumber;
import lotto.domain.wrapper.PurchaseMoney;

/**
 * 메인 컨트롤러
 */
public class LottoController {

    private final LottoView view;
    private final LottoProcessor processor;

    /**
     * I/O Facade와 로직 Facade 주입, 컨트롤러 생성
     *
     * @param view
     * @param processor
     */
    public LottoController(LottoView view, LottoProcessor processor) {
        this.view = view;
        this.processor = processor;
    }

    /**
     * 로또 게임 실행
     */
    public void run() {
        PurchaseMoney money = getPurchaseMoneyWithRetry();
        PlayerPurchaseDto purchase = processor.purchaseLottos(money);
        view.printLottos(purchase.getLottos());
        WinningCombo winningCombo = getWinningCombinationWithRetry();
        StatDto result = processor.calculateStatistics(purchase, winningCombo);
        view.printStatistics(result);
    }

    private PurchaseMoney getPurchaseMoneyWithRetry() {
        while (true) {
            try {
                String input = view.readPurchaseMoney();
                return processor.createPurchaseMoney(input);
            } catch (IllegalArgumentException e) {
                view.printError(e.getMessage());
            }
        }
    }
    private WinningCombo getWinningCombinationWithRetry() {
        Lotto winningLotto = getWinningNumbersWithRetry();
        BonusNumber bonusNumber = getBonusNumberWithRetry(winningLotto);
        return new WinningCombo(winningLotto, bonusNumber);
    }

    private Lotto getWinningNumbersWithRetry() {
        while (true) {
            try {
                String input = view.readWinningNumbers();
                return processor.createWinningLotto(input);
            } catch (IllegalArgumentException e) {
                view.printError(e.getMessage());
            }
        }
    }

    private BonusNumber getBonusNumberWithRetry(Lotto winningLotto) {
        while (true) {
            try {
                String input = view.readBonusNumber();
                return processor.createBonusNumber(input, winningLotto);
            } catch (IllegalArgumentException e) {
                view.printError(e.getMessage());
            }
        }
    }
}