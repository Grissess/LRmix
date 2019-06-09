package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;

import java.io.File;
import java.util.Arrays;

public class OutputArgument implements Argument {
    String[] _names = new String[]{"-o", "--output"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        data.output = new File(args[0]);
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String description() {
        return "set the output file (default stdout, but logs get dumped there too)";
    }
}
