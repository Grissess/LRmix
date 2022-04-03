package nl.minvenj.nfi.lrmixstudio.automation;

import nl.minvenj.nfi.lrmixstudio.model.ConfigurationData;

import java.io.File;

public class ArgumentData {
    public ConfigurationData config = new ConfigurationData();
    public File output = null;
    public boolean lastRelOk = false;

    public static class HypothesisProxy {
        public boolean relativeSet = false;
    }

    HypothesisProxy prosecution;
    HypothesisProxy defense;
}
