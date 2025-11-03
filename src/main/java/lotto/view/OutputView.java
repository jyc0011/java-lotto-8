package lotto.view;

import java.text.NumberFormat;
import java.util.List;
import lotto.domain.Lottos;
import lotto.domain.Rank;
import lotto.dto.StatDto;

/**
 * 출력 클래스
 */
public class OutputView {

    private static final List<Rank> RANKS_TO_PRINT = List.of(Rank.FIFTH, Rank.FOURTH, Rank.THIRD, Rank.SECOND,
            Rank.FIRST);

    /**
     * 구매 로또 수와 번호 목록을 출력
     *
     * @param lottos 구매한 로또 묶음 (일급 컬렉션)
     */
    public void printLottos(Lottos lottos) {
        System.out.printf("\n%d개를 구매했습니다.\n", lottos.size());
        lottos.stream().forEach(System.out::println);
    }

    /**
     * 당첨 통계 출력
     *
     * @param result 통계 결과 DTO
     */
    public void printStatistics(StatDto result) {
        System.out.println("\n당첨 통계");
        System.out.println("---");
        RANKS_TO_PRINT.stream()
                .map(rank -> formatRankMessage(rank, result.getRankCounts().get(rank)))
                .forEach(System.out::println);
        String formattedRate = result.getFormattedProfitRate();
        System.out.printf("총 수익률은 %s입니다.\n", formattedRate);
    }

    /**
     * Rank 문자열로 포매팅
     */
    private String formatRankMessage(Rank rank, long count) {
        String prize = NumberFormat.getIntegerInstance().format(rank.getPrizeMoney());
        String matchMessage = "";
        if (rank.needsBonus()) {
            matchMessage = String.format("%d개 일치, 보너스 볼 일치", rank.getMatchCount());
        }
        if (!rank.needsBonus()) {
            matchMessage = String.format("%d개 일치", rank.getMatchCount());
        }
        return String.format("%s (%s원) - %d개", matchMessage, prize, count);
    }

    /**
     * 에러 메시지 출력
     *
     * @param message
     */
    public void printError(String message) {
        System.out.println(message);
    }
}