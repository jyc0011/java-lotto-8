package lotto.domain;

import java.util.List;
import java.util.stream.Stream;

/**
 * Lotto의 일급 컬렉션
 */
public class Lottos {

    private final List<Lotto> lottos;

    /**
     * Lottos 객체 생성
     *
     * @param lottos LottoFactory에서 생성된 Lotto 객체 리스트
     */
    public Lottos(List<Lotto> lottos) {
        this.lottos = List.copyOf(lottos);
    }

    /**
     * 구매한 로또의 총 개수를 반환
     *
     * @return 로또 개수
     */
    public int size() {
        return this.lottos.size();
    }

    /**
     * 로또 목록 순회
     *
     * @return Lottos Stream
     */
    public Stream<Lotto> stream() {
        return this.lottos.stream();
    }


}