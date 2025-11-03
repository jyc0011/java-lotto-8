package lotto.domain.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("자동 생성 전략(AutoGenStrategy) 테스트")
class AutoGenStrategyTest {

    @Test
    @DisplayName("성공 : generate() 호출 시, 1부터 45 사이의 무중복 숫자 6개 무작위 생성")
    void generate_ShouldReturnValidLottoNumbers() {
        LottoGenStrategy strategy = new AutoGenStrategy();
        List<Integer> actualList = strategy.generate();
        assertThat(actualList).hasSize(6);
        Set<Integer> uniqueNumbers = new HashSet<>(actualList);
        assertThat(actualList.size()).isEqualTo(uniqueNumbers.size());
        assertThat(actualList).allMatch(number -> number >= 1 && number <= 45);
    }
}