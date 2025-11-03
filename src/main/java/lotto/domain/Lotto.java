package lotto.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lotto.view.ErrorMessage;

/**
 * Lotto 클래스
 */
public class Lotto {
    private final List<Integer> numbers;

    /**
     * 로또 번호 리스트로 Lotto 객체 생성
     * 생성 시 유효성 확인과 정렬 수행
     *
     * @param numbers 6개의 로또 번호
     * @throws IllegalArgumentException
     */
    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    private void validate(List<Integer> numbers) {
        validateSize(numbers);
        validateRange(numbers);
        validateDuplicate(numbers);
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_INVALID_SIZE.getMessage());
        }
    }

    private void validateRange(List<Integer> numbers) {
        boolean outOfRange = numbers.stream()
                .anyMatch(number -> number < 1 || number > 45);
        if (outOfRange) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_NUMBER_OUT_OF_RANGE.getMessage());
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_DUPLICATE_NUMBER.getMessage());
        }
    }

    /**
     * 특정 번호를 포함하는지 확인
     *
     * @param number 확인할 숫자
     * @return 포함하면 true
     */
    public boolean contains(int number) {
        return this.numbers.contains(number);
    }

    /**
     * 다른 로또(당첨 번호)와 비교해 일치 번호 수 출력
     *
     * @param otherLotto 비교 대상이 되는 로또
     * @return 일치하는 번호의 개수
     */
    public int countMatchingNumbers(Lotto otherLotto) {
        long matchCount = this.numbers.stream()
                .filter(otherLotto.numbers::contains)
                .count();
        return (int) matchCount;
    }

    /**
     * 객체의 문자열 리턴
     */
    @Override
    public String toString() {
        return this.numbers.toString();
    }
}
