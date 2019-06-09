package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;

public class DropInArgument implements Argument {
    String[] _names = new String[]{"-D", "-di", "--drop-in"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        (new HypothesisDropInArgument(PerHypothesis.DEFENSE)).consume(args, data);
        return (new HypothesisDropInArgument(PerHypothesis.PROSECUTION)).consume(args, data);
    }

    @Override
    public String description() {
        return "set both hypothesis drop-in rates";
    }
}
