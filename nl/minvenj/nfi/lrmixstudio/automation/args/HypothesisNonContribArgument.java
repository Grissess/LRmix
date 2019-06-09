package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;
import nl.minvenj.nfi.lrmixstudio.domain.Sample;
import nl.minvenj.nfi.lrmixstudio.model.ConfigurationData;

public class HypothesisNonContribArgument extends HypothesisSampleArgument {
    String[] _namesDefense = new String[]{"-Hdnc"};
    String[] _namesProsecution = new String[]{"-Hpnc"};

    public HypothesisNonContribArgument(PerHypothesis ph) {
        super(ph);
    }

    @Override
    public String[] names() {
        return perHypothesis.isProsecution() ? _namesProsecution : _namesDefense;
    }

    @Override
    void addContributor(ConfigurationData config, Sample sample, double prob) {
        perHypothesis.getHypothesis(config).addNonContributor(sample, prob);
        System.err.println("* Added non-contrib " + sample.getId() + " to " + perHypothesis.getHypothesisName() + " hyp with drop-out prob " + perHypothesis.getHypothesis(config).getContributor(sample).getDropoutProbability());
    }

    @Override
    public String description() {
        return super.description() + " as a non-contributor";
    }
}
