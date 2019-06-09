package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;
import nl.minvenj.nfi.lrmixstudio.domain.Hypothesis;

import java.util.Arrays;

public class HypothesisUnknownArgument implements Argument {
    String[] _namesDefense = new String[]{"-Hdu"};
    String[] _namesProsecution = new String[]{"-Hpu"};
    protected PerHypothesis perHypothesis;

    public HypothesisUnknownArgument(PerHypothesis ph) {
        perHypothesis = ph;
    }

    @Override
    public String[] names() {
        return perHypothesis.isProsecution() ? _namesProsecution : _namesDefense;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        int numUnknown = Integer.parseInt(args[0]);
        double dropout = Double.parseDouble(args[1]);

        Hypothesis hypothesis = perHypothesis.getHypothesis(data.config);
        hypothesis.setUnknownCount(numUnknown);
        hypothesis.setUnknownDropoutProbability(dropout);

        System.err.println("* Set " + hypothesis.getUnknownCount() + " unknowns with drop-out " + hypothesis.getUnknownDropoutProbability() + " for " + perHypothesis.getHypothesisName() + " hyp");

        return Arrays.copyOfRange(args, 2, args.length);
    }

    @Override
    public String description() {
        return "'num drop' sets unknown count to 'num' and dropout probability for unknowns to 'drop' for the " + perHypothesis.getHypothesisName() + " hypothesis";
    }
}
