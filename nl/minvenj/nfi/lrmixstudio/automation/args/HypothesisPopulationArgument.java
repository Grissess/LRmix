package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentHandler;
import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;
import nl.minvenj.nfi.lrmixstudio.io.PopulationStatisticsReader;

import java.io.IOException;
import java.util.Arrays;

public class HypothesisPopulationArgument implements Argument {
    String[] _namesDefense = new String[]{"-HdP"};
    String[] _namesProsecution = new String[]{"-HpP"};
    protected PerHypothesis perHypothesis;

    public HypothesisPopulationArgument(PerHypothesis ph) {
        perHypothesis = ph;
    }

    @Override
    public String[] names() {
        return perHypothesis.isProsecution() ? _namesProsecution : _namesDefense;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) throws IOException {
        PopulationStatisticsReader reader = new PopulationStatisticsReader(args[0]);
        perHypothesis.setHypothesis(data.config, ArgumentHandler.installPopStats(perHypothesis.getHypothesis(data.config), reader.getStatistics()));
        System.err.println("* Set " + perHypothesis.getHypothesisName() + " hyp population to " + perHypothesis.getHypothesis(data.config).getPopulationStatistics().getFileName());
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String description() {
        return "sets population for the " + perHypothesis.getHypothesisName() + " hypothesis";
    }
}
