package lotto.config;

import lotto.controller.LottoController;
import lotto.controller.LottoProcessor;
import lotto.controller.LottoView;
import lotto.domain.factory.LottoFactory;
import lotto.domain.strategy.AutoGenStrategy;
import lotto.domain.strategy.LottoGenStrategy;
import lotto.service.LottoService;
import lotto.util.InputParser;
import lotto.view.InputView;
import lotto.view.OutputView;

/**
 * 애플리케이션의 객체 생성과 의존성 주입 담당
 */
public class AppConfig {

    private InputView inputView;
    private OutputView outputView;
    private InputParser inputParser;
    private LottoGenStrategy lottoGenStrategy;
    private LottoFactory lottoFactory;
    private LottoService lottoService;
    private LottoView lottoView;
    private LottoProcessor lottoProcessor;
    private LottoController lottoController;

    /**
     * LottoController 호출, 프로그램 실행
     *
     * @return LottoController 인스턴스
     */
    public LottoController lottoController() {
        if (lottoController == null) {
            lottoController = new LottoController(lottoView(), lottoProcessor());
        }
        return lottoController;
    }

    private LottoView lottoView() {
        if (lottoView == null) {
            lottoView = new LottoView(inputView(), outputView());
        }
        return lottoView;
    }

    private LottoProcessor lottoProcessor() {
        if (lottoProcessor == null) {
            lottoProcessor = new LottoProcessor(lottoService(), inputParser());
        }
        return lottoProcessor;
    }

    private LottoService lottoService() {
        if (lottoService == null) {
            lottoService = new LottoService(lottoFactory());
        }
        return lottoService;
    }

    private LottoFactory lottoFactory() {
        if (lottoFactory == null) {
            lottoFactory = new LottoFactory(lottoGenStrategy());
        }
        return lottoFactory;
    }

    private LottoGenStrategy lottoGenStrategy() {
        if (lottoGenStrategy == null) {
            lottoGenStrategy = new AutoGenStrategy();
        }
        return lottoGenStrategy;
    }

    private InputParser inputParser() {
        if (inputParser == null) {
            inputParser = new InputParser();
        }
        return inputParser;
    }

    private InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }
        return inputView;
    }

    private OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }
        return outputView;
    }
}