package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.domain.Sample;
import nl.minvenj.nfi.lrmixstudio.io.SampleReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class ReplicateFileArgument implements Argument {
    String[] _names = new String[] {"-r", "--replicate"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) throws IOException {
        SampleReader reader = new SampleReader(args[0], true);
        Collection<Sample> samples = reader.getSamples();
        data.config.addReplicates(samples);

        System.err.println("* Added replicate/evidence from " + args[0] + " with samples:");
        for(Sample sample: samples) {
            System.err.println("  - " + sample.getId());
        }

        return Arrays.copyOfRange(args, 1, args.length);
    }

    @Override
    public String description() {
        return "Add a replicate (evidence) using the named CSV.";
    }
}
