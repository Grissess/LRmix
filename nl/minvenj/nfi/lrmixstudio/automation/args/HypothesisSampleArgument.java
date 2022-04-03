package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;
import nl.minvenj.nfi.lrmixstudio.domain.Sample;
import nl.minvenj.nfi.lrmixstudio.model.ConfigurationData;

import java.util.Arrays;

public class HypothesisSampleArgument implements Argument {
    String[] _namesDefense = new String[]{"-Hd"};
    String[] _namesProsecution = new String[]{"-Hp"};
    protected PerHypothesis perHypothesis;

    public HypothesisSampleArgument(PerHypothesis ph) {
        perHypothesis = ph;
    }

    @Override
    public String[] names() {
        return perHypothesis.isProsecution() ? _namesProsecution : _namesDefense;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) {
        double prob = Double.parseDouble(args[1]);

        for(Sample sample: data.config.getAllProfiles()) {
            if(sample.getId().equals(args[0])) {
                addContributor(data.config, sample, prob);
                return Arrays.copyOfRange(args, 2, args.length);
            }
        }

        throw new IllegalArgumentException("Could not find sample named " + args[0]);
    }

    void addContributor(ConfigurationData config, Sample sample, double prob) {
        perHypothesis.getHypothesis(config).addContributor(sample, prob);
        System.err.println("* Added contrib " + sample.getId() + " (" + sample.getSourceFile() + ") to " + perHypothesis.getHypothesisName() + " hyp with drop-out prob " + perHypothesis.getHypothesis(config).getContributor(sample).getDropoutProbability());
    }

    @Override
    public String description() {
        return "'samp prob' adds sample ID 'samp' with dropout probability 'prob' to the " + perHypothesis.getHypothesisName() + " hypothesis";
    }
}
