package nl.minvenj.nfi.lrmixstudio.automation;

import nl.minvenj.nfi.lrmixstudio.domain.Hypothesis;
import nl.minvenj.nfi.lrmixstudio.model.ConfigurationData;

public class PerHypothesis {
    public static PerHypothesis DEFENSE = new PerHypothesis(false);
    public static PerHypothesis PROSECUTION = new PerHypothesis(true);

    protected boolean prosecution;

    PerHypothesis(boolean prosecution) {
        this.prosecution = prosecution;
    }

    public Hypothesis getHypothesis(ConfigurationData config) {
        return prosecution ? config.getProsecution() : config.getDefense();
    }

    public void setHypothesis(ConfigurationData config, Hypothesis hy) {
        if(prosecution) {
            config.setProsecution(hy);
        } else {
            config.setDefense(hy);
        }
    }

    public String getHypothesisName() {
        return prosecution ? "prosecution" : "defense";
    }

    public boolean isProsecution() {
        return prosecution;
    }
}
