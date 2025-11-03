package lotto.controller;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.Lottos;
import lotto.dto.PlayerPurchaseDto;
import lotto.domain.wrapper.WinningCombo;
import lotto.dto.StatDto;
import lotto.domain.wrapper.BonusNumber;
import lotto.domain.wrapper.PurchaseMoney;
import lotto.service.LottoService;
import lotto.util.InputParser;

/**
 * 서비스 호출과 파싱등을 호출
 */
public class LottoProcessor {

    private final LottoService lottoService;
    private final InputParser parser;

    /**
     * 로직 Facade를 생성합니다.
     *
     * @param lottoService
     * @param parser
     */
    public LottoProcessor(LottoService lottoService, InputParser parser) {
        this.lottoService = lottoService;
        this.parser = parser;
    }

    /**
     * 문자열 입력 파싱, 검증, PurchaseMoney 객체 생성
     *
     * @param input 입력 문자열
     * @return 유효성이 검증된 PurchaseMoney 객체
     * @throws IllegalArgumentException
     */
    public PurchaseMoney createPurchaseMoney(String input) {
        int amount = parser.parseInt(input);
        return new PurchaseMoney(amount);
    }

    /**
     * 문자열 입력 파싱, 검증, 당첨 Lotto 객체 생성
     *
     * @param input 입력 문자열
     * @return 유효성이 검증된 Lotto 객체
     * @throws IllegalArgumentException
     */
    public Lotto createWinningLotto(String input) {
        List<Integer> numbers = parser.parseWinningNumbers(input);
        return new Lotto(numbers);
    }

    /**
     * 문자열 입력 파싱, 검증, BonusNumber 객체 생성
     *
     * @param input        입력 문자열
     * @param winningLotto 중복 검증을 위한 당첨 번호
     * @return 유효성이 검증된 BonusNumber 객체
     * @throws IllegalArgumentException
     */
    public BonusNumber createBonusNumber(String input, Lotto winningLotto) {
        int number = parser.parseInt(input);
        return new BonusNumber(number, winningLotto);
    }

    /**
     * LottoService에 로또 생성 위임
     *
     * @param money 구매 금액
     * @return 생성된 로또 묶음
     */
    public PlayerPurchaseDto purchaseLottos(PurchaseMoney money) {
        Lottos lottos = lottoService.purchaseLottos(money);
        return new PlayerPurchaseDto(lottos, money);
    }

    /**
     * LottoService에 통계 계산 위임
     *
     * @return 통계 DTO
     */
    public StatDto calculateStatistics(PlayerPurchaseDto purchase, WinningCombo winningCombo) {
        return lottoService.calculateStatistics(purchase.getLottos(), winningCombo, purchase.getMoney());
    }
}