package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Lottos 테스트")
class LottosTest {

    private List<Lotto> mutableList;
    private Lottos lottos;
    private Lotto lotto1;
    private Lotto lotto2;

    @BeforeEach
    void setUp() {
        lotto1 = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        lotto2 = new Lotto(List.of(7, 8, 9, 10, 11, 12));
        mutableList = new ArrayList<>(List.of(lotto1, lotto2));
        lottos = new Lottos(mutableList);
    }

    @Test
    @DisplayName("성공 : 구매 로또 수 확인")
    void size_ReturnsCorrectCount() {
        assertThat(lottos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("성공 : 로또 순회 Stream 확인")
    void stream_ReturnsValidStream() {
        long count = lottos.stream().count();
        assertThat(count).isEqualTo(2);
        assertThat(lottos.stream()).containsExactly(lotto1, lotto2);
    }

    @Test
    @DisplayName("기능 : 불변 객체 확인")
    void constructor_MakesDefensiveCopy() {
        int originalSize = lottos.size();
        mutableList.add(new Lotto(List.of(13, 14, 15, 16, 17, 18)));
        assertThat(lottos.size()).isEqualTo(originalSize);
        assertThat(mutableList.size()).isEqualTo(3);
    }
}