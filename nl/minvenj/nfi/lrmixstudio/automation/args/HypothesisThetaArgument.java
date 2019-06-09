package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;

import java.util.Arrays;

public class HypothesisThetaArgument implements Argument {
    String[] _namesDefense = new String[]{"-Hdt"};
    String[] _namesProsecution = new String[]{"-Hpt"};
    protected PerHypothesis perHypothesis;

    public HypothesisThetaArgument(PerHypothesis ph) {
        perHypothesis = ph;
    }

    @Override
    public String[] names() {
        return perHypothesis.isProsecution() ? _namesProsecution : _namesDefense;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        perHypothesis.getHypothesis(data.config).setThetaCorrection(Double.parseDouble(args[0]));
        System.err.println("* Set " + perHypothesis.getHypothesisName() + " theta to " + perHypothesis.getHypothesis(data.config).getThetaCorrection());
        return Arrays.copyOfRange(args,1, args.length);
    }

    @Override
    public String description() {
        return "sets the theta correction for the " + perHypothesis.getHypothesisName() + " hypothesis";
    }
}
