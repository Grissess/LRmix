package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.AutomationMain;
import nl.minvenj.nfi.lrmixstudio.gui.ApplicationSettings;

public class VersionArgument implements Argument {
    String[] _names = new String[]{"-V", "--version"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        System.err.println("LRMixStudio version: " + ApplicationSettings.getProgramVersion());
        System.err.println("Automation version: " + AutomationMain.version);
        System.exit(0);
        return args;  // unreachable
    }

    @Override
    public String description() {
        return "print version and exit";
    }
}
