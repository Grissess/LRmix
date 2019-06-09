package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.domain.Sample;

import java.util.Collection;

public class ProfileFileNoHzArgument extends ProfileFileArgument {
    String[] _names = new String[]{"--profile-no-hz"};

    @Override
    public String[] names() {
        return _names;
    }

    @Override
    public void processSamples(Collection<Sample> samples) {}

    @Override
    public String description() {
        return "Add a profile using the named CSV. No homozygotic processing is done; if only allele A is present, the other may have dropped.";
    }
}
