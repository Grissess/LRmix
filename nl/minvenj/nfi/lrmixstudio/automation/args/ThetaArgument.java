package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;

public class ThetaArgument implements Argument {
    String[] _names = new String[]{"-t", "--theta"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        (new HypothesisThetaArgument(PerHypothesis.DEFENSE)).consume(args, data);
        return (new HypothesisThetaArgument(PerHypothesis.PROSECUTION)).consume(args, data);
    }

    @Override
    public String description() {
        return "set the theta correction for both hypotheses simultaneously";
    }
}
