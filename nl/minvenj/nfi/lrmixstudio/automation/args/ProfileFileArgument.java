package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.domain.Locus;
import nl.minvenj.nfi.lrmixstudio.domain.Sample;
import nl.minvenj.nfi.lrmixstudio.io.SampleReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class ProfileFileArgument implements Argument {
    String[] _names = new String[] {"-p", "--profile"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) throws IOException {
        SampleReader reader = new SampleReader(args[0], false);
        Collection<Sample> samples = reader.getSamples();

        processSamples(samples);
        data.config.addProfiles(samples);

        System.err.println("* Added profile from " + args[0] + " with samples:");
        for(Sample sample: samples) {
            System.err.println("  - " + sample.getId());
        }

        return Arrays.copyOfRange(args, 1, args.length);
    }

    public void processSamples(Collection<Sample> samples) {
        // Homozygotic processing
        for(Sample sample : samples) {
            for(Locus locus : sample.getLoci()) {
                if(locus.size() == 1) {
                    locus.addAllele(locus.getAlleles().iterator().next());
                    locus.setTreatedAsHomozygote();
                }
            }
        }
    }

    @Override
    public String description() {
        return "Add a profile using the named CSV. Homozygotic processing is applied; if only one allele A is present, it is assumed to be an AA sample.";
    }
}
