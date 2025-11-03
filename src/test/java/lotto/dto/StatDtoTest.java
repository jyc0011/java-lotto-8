package lotto.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;
import lotto.domain.Rank;
import lotto.domain.wrapper.Profit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("StatDto 테스트")
class StatDtoTest {

    @Test
    @DisplayName("성공 : 생성자로 전달된 데이터가 getter로 확인됨")
    void createAndGet_Success() {
        Map<Rank, Long> rankCounts = new EnumMap<>(Rank.class);
        rankCounts.put(Rank.FIFTH, 1L);
        Profit profit = new Profit(5000L, 8000);
        String expectedFormattedRate = "62.5%";
        StatDto dto = new StatDto(rankCounts, profit);
        assertThat(dto.getRankCounts()).isEqualTo(rankCounts);
        assertThat(dto.getFormattedProfitRate()).isEqualTo(expectedFormattedRate);
    }
}