package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

public class LottoController {

    private static int price;
    private static List<Integer> winNumber;
    private static IOController ioController;
    private static LottoGenerator lottoGenerator;
    private static List<Lotto> lottoList;
    private static Map<Integer, Integer> winLottoCount;
    private static int bonusNumber;

    public LottoController() {
        init();
        input();
        compareTotalLottoList();
        calcuratePrizeMoney();
        printResult();

    }

    private void calcuratePrizeMoney() {
        int totalPrizeMoney = 0;

        for (Map.Entry<Integer, Integer> entry : winLottoCount.entrySet()) {
            int matchingNumbers = entry.getValue();
            Prize prize = getPrize(matchingNumbers);
            totalPrizeMoney += prize.getAmount();
        }

        System.out.println("총 당첨금액: " + totalPrizeMoney + "원");
    }

    private Prize getPrize(int matchingNumbers) {

        if (matchingNumbers == 3)
            return Prize.THREE_MATCH;
        if (matchingNumbers == 4)
            return Prize.FOUR_MATCH;
        if (matchingNumbers == 5) {
            if (hasBonusBall())
                return Prize.FIVE_MATCH_BONUS;
            return Prize.FIVE_MATCH;
        }
        if (matchingNumbers == 6)
            return Prize.SIX_MATCH;

        return Prize.ZERO_MATCH;
    }

    private void input() {
        price = ioController.inputPrice();
        generateLottoNumber();
        winNumber = ioController.inputWinNumber();
        bonusNumber = ioController.inputBonusNumber();
    }

    private void printResult() {
        // 결과 출력하는 메서드
        //ioController.printResult();
    }

    private void compareTotalLottoList() {
        for (Lotto lotto : lottoList) {
            compareLotto(lotto);
        }

        System.out.println("winLottoCount:" + winLottoCount);
    }

    private void compareLotto(Lotto lotto) {
        int matchedNumbers = 0;
        for (int userNumber : lotto.getNumbers()) {
            if (winNumber.contains(userNumber)) {
                matchedNumbers++;
            }
        }
        winLottoCount.put(matchedNumbers, winLottoCount.getOrDefault(matchedNumbers, 0) + 1);
    }

    private void generateLottoNumber() {
        int CountOfLotto = price / 1000;

        System.out.println("\n" + CountOfLotto + InstructionMessage.PRINT_LOTTO_COUNT.getMessageText());

        for (int makeLotto = 1; makeLotto <= CountOfLotto; makeLotto++) {
            lottoList.add(new Lotto(makeRandomNumber()));
        }

        ioController.printUserLottoNumbers(lottoList);

    }

    public List<Integer> makeRandomNumber() {

        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
        numbers.sort(Comparator.naturalOrder());
        return numbers;
    }

    public static void init() {
        lottoList = new LinkedList<>();
        lottoGenerator = new LottoGenerator();
        ioController = new IOController();
        winLottoCount = new HashMap<>();

    }
}
