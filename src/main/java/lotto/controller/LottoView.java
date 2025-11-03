package lotto.controller;

import lotto.domain.Lottos;
import lotto.dto.StatDto;
import lotto.view.InputView;
import lotto.view.OutputView;

/**
 * 입출력(I/O)을 담당하는 퍼사드(Facade)입니다.
 * (인스턴스 변수 2개 규칙 준수)
 */
public class LottoView {

    private final InputView inputView;
    private final OutputView outputView;

    /**
     * I/O Facade 생성
     *
     * @param inputView
     * @param outputView
     */
    public LottoView(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    /**
     * 구매 금액 입력 요청
     *
     * @return 문자열
     */
    public String readPurchaseMoney() {
        return inputView.readPurchaseMoney();
    }

    /**
     * 당첨 번호 입력 요청
     *
     * @return  문자열
     */
    public String readWinningNumbers() {
        return inputView.readWinningNumbers();
    }

    /**
     * 보너스 번호 입력 요청
     *
     * @return 문자열
     */
    public String readBonusNumber() {
        return inputView.readBonusNumber();
    }

    /**
     * 로또 목록 출력
     *
     * @param lottos 구매한 로또 묶음
     */
    public void printLottos(Lottos lottos) {
        outputView.printLottos(lottos);
    }

    /**
     * 통계 DTO 출력
     *
     * @param result 통계 DTO
     */
    public void printStatistics(StatDto result) {
        outputView.printStatistics(result);
    }

    /**
     * 에러 메시지 출력
     *
     * @param message 에러 메시지
     */
    public void printError(String message) {
        outputView.printError(message);
    }
}