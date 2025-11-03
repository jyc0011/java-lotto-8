package lotto.view;

import camp.nextstep.edu.missionutils.Console;

/**
 * 입력 클래스
 */
public class InputView {

    /**
     * 로또 구입 금액 입력
     *
     * @return 사용자가 입력한 문자열
     */
    public String readPurchaseMoney() {
        System.out.println("구입금액을 입력해 주세요.");
        return Console.readLine();
    }

    /**
     * 당첨 번호 입력
     *
     * @return 사용자가 입력한 문자열
     */
    public String readWinningNumbers() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        return Console.readLine();
    }

    /**
     * 보너스 번호 입력
     *
     * @return 사용자가 입력한 문자열
     */
    public String readBonusNumber() {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        return Console.readLine();
    }
}