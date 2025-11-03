package lotto.domain.strategy;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;

/**
 * `Randoms` 사용, 자동 로또 번호 생성 전략
 */
public class AutoGenStrategy implements LottoGenStrategy {

    /**
     * 1부터 45 사이의 무중복 숫자 6개 무작위 생성
     *
     * @return 6개의 중복 없는 로또 번호 리스트
     */
    @Override
    public List<Integer> generate() {
        return Randoms.pickUniqueNumbersInRange(1, 45, 6);
    }
}