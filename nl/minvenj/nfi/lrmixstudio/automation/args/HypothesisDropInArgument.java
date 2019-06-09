package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;

import java.util.Arrays;

public class HypothesisDropInArgument implements Argument {
    String[] _namesDefense = new String[]{"-Hdi"};
    String[] _namesProsecution = new String[]{"-Hpi"};
    protected PerHypothesis perHypothesis;

    public HypothesisDropInArgument(PerHypothesis ph) {
        perHypothesis = ph;
    }

    @Override
    public String[] names() {
        return perHypothesis.isProsecution() ? _namesProsecution : _namesDefense;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        perHypothesis.getHypothesis(data.config).setDropInProbability(Double.parseDouble(args[0]));
        System.err.println("* Drop-in for " + perHypothesis.getHypothesisName() + " hyp set to " + perHypothesis.getHypothesis(data.config).getDropInProbability());
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String description() {
        return "Set drop-in for the " + perHypothesis.getHypothesisName() + " hypothesis";
    }
}
