package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;

import java.util.Arrays;

public class ThreadsArgument implements Argument {
    String[] _names = new String[]{"-j", "--threads"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        data.config.setThreadCount(Integer.parseInt(args[0]));
        System.err.println("* Set threads to " + data.config.getThreadCount());
        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String description() {
        return "set the number of threads";
    }
}
