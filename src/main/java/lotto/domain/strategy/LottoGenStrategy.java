package lotto.domain.strategy;

import java.util.List;

/**
 * 로또 번호 생성 전략 인터페이스
 */
public interface LottoGenStrategy {

    /**
     * 정의된 전략에 따라 로또 리스트를 생성
     *
     * @return 6개의 중복 없는 로또 번호 리스트
     */
    List<Integer> generate();
}