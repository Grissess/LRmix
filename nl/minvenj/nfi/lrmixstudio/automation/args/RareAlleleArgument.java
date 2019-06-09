package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;

import java.util.Arrays;

public class RareAlleleArgument implements Argument {
    String[] _names = new String[]{"-R", "--rare"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        data.config.setRareAlleleFrequency(Double.parseDouble(args[0]));
        System.err.println("* Set rare allele frequency to " + data.config.getRareAlleleFrequency());
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String description() {
        return "set the rare allele rate";
    }
}
