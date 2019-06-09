package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.model.LRMathModelFactory;

import java.util.Arrays;

public class ModelNameArgument implements Argument {
    String[] _names = new String[]{"-m", "--model"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        data.config.setMathematicalModelName(args[0]);
        System.err.println("* Set model to " + data.config.getMathematicalModelName());
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String description() {
        return "the model to be used by LRmix (supported models: " + String.join(", ", LRMathModelFactory.getAllModelNames()) + ")";
    }
}
