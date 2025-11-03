package lotto.domain.wrapper;


public class Profit {
    private final double rate;

    /**
     * 총상금과 구매금액으로 수익률 객체 생성
     *
     * @param totalPrize     총 획득 상금
     * @param purchaseAmount 총 구매 금액
     */
    public Profit(long totalPrize, int purchaseAmount) {
        this.rate = calculateRate(totalPrize, purchaseAmount);
    }

    private double calculateRate(long totalPrize, int purchaseAmount) {
        if (purchaseAmount == 0) {
            return 0.0;
        }
        return (double) totalPrize / purchaseAmount;
    }

    /**
     * 계산된 수익률을 요구사항에 맞게 포매팅
     *
     * @return 포매팅된 수익률 문자열
     */
    public String formattedRate() {
        double percentage = Math.round(this.rate * 1000) / 10.0;
        return String.format("%.1f%%", percentage);
    }
}