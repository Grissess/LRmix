package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.io.PopulationStatisticsReader;

import java.io.IOException;
import java.util.Arrays;

public class PopulationArgument implements Argument {
    String[] _names = new String[]{"-P", "--population"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) throws IOException {
        PopulationStatisticsReader reader = new PopulationStatisticsReader(args[0]);
        data.config.setStatistics(reader.getStatistics());
        System.err.println("* Set global population to " + args[0]);
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String description() {
        return "set the population for the configuration (and the _default_ population for the hypotheses--can be overridden with -HdP/-HpP)";
    }
}
