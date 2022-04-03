package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;

public class LastRelWinsArgument implements Argument {
    String[] _names = new String[]{"--last-rel-wins"};
    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) throws Exception {
        data.lastRelOk = true;
        return args;
    }

    @Override
    public String description() {
        return null;
    }
}
