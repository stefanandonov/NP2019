package mk.ukim.finki.av5_petok;

import java.util.DoubleSummaryStatistics;
import java.util.Random;

class NumericProcessor {
    DoubleSummaryStatistics statistics;

    public NumericProcessor(DoubleSummaryStatistics statistics) {
        this.statistics = statistics;
    }

    public NumericProcessor() {
        this.statistics = new DoubleSummaryStatistics();
    }

    public <T extends Number> void addNumber (T number) {
        statistics.accept(number.doubleValue());
    }

    public NumericProcessor merge (NumericProcessor other) {
        DoubleSummaryStatistics newStatistics = new DoubleSummaryStatistics();
        newStatistics.combine(this.statistics);
        newStatistics.combine(other.statistics);
        return new NumericProcessor(newStatistics);
    }

    @Override
    public String toString() {
        return statistics.toString();
    }
}

public class NumericProcessorTester {

    public static void main(String[] args) {
        NumericProcessor integerProcessor = new NumericProcessor();

        Random random = new Random();
        for (int i=0;i<10000000;i++) {
            integerProcessor.addNumber(random.nextInt(10)+1);
        }

        System.out.println(integerProcessor);

        NumericProcessor doublesProcessor = new NumericProcessor();
        Random random1 = new Random();
        for (int i=0;i<10000000;i++) {
            integerProcessor.addNumber(random1.nextDouble()*9+1);
        }
        System.out.println(doublesProcessor);

        NumericProcessor combined = integerProcessor.merge(doublesProcessor);
        System.out.println(combined);





    }
}
