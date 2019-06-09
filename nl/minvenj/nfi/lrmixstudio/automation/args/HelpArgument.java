package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentHandler;

public class HelpArgument implements Argument{
    String[] _names = new String[]{"-h", "--help"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        System.err.println("Runs an LRMix analysis.");
        System.err.println("When neither --help/-h or --version/-V are specified, on non-error exit, writes a result CSV to stdout.\n");
        System.err.println("Options understood by this program:");
        for(Argument arg: ArgumentHandler.arguments) {
            System.err.println(String.join(", ", arg.names()) + ": " + arg.description());
        }
        System.exit(0);
        return args;  // never reached
    }

    @Override
    public String description() {
        return "Print out all recognized options and exit.";
    }
}
