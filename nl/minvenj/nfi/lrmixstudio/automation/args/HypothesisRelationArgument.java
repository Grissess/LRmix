package nl.minvenj.nfi.lrmixstudio.automation.args;

import nl.minvenj.nfi.lrmixstudio.automation.Argument;
import nl.minvenj.nfi.lrmixstudio.automation.ArgumentData;
import nl.minvenj.nfi.lrmixstudio.automation.PerHypothesis;
import nl.minvenj.nfi.lrmixstudio.domain.Relatedness;
import nl.minvenj.nfi.lrmixstudio.domain.Sample;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HypothesisRelationArgument implements Argument {
    String[] _namesDefense = new String[]{"-Hdr"};
    String[] _namesProsecution = new String[]{"-Hpr"};
    protected PerHypothesis perHypothesis;

    public HypothesisRelationArgument(PerHypothesis ph) {
        perHypothesis = ph;
    }
    @Override
    public String[] names() {
        return perHypothesis.isProsecution() ? _namesProsecution : _namesDefense;
    }

    @Override
    public String[] consume(String[] args, ArgumentData data) throws Exception {
        ArgumentData.HypothesisProxy proxy = perHypothesis.getProxy(data);
        if(proxy.relativeSet) {
            if(data.lastRelOk) {
                System.err.println("* Allowing redundant set of " + perHypothesis.getHypothesisName() + " relatedness because you asked");
            } else {
                throw new IllegalStateException("Only one relation can be applied to a hypothesis; as long as you understand this, you can use --last-rel-wins to use only the last relative per hypothesis.");
            }
        }
        proxy.relativeSet = true;

        Relatedness.Relation rel;
        try {
            rel = Relatedness.Relation.valueOf(args[1]);
        } catch(IllegalArgumentException e) {
            System.err.println("No such relation "+ args[1] + "!");
            System.err.println("Possible values:");
            for(Relatedness.Relation r : Relatedness.Relation.values()) {
                System.err.println("- " + r.name());
            }
            throw e;
        }
        
        for(Sample samp : data.config.getAllProfiles()) {
            if(samp.getId().equals(args[0])) {
                Relatedness relatedness = perHypothesis.getHypothesis(data.config).getRelatedness();
                relatedness.setRelation(rel);
                relatedness.setRelative(samp);
                System.err.println("* Set " + perHypothesis.getHypothesisName() + " relatedness of " + rel.name() + " (" + rel.toString() + ") to sample " + samp.getId() + " (" + samp.getSourceFile() + ")");
                return Arrays.copyOfRange(args, 2, args.length);
            }
        }

        throw new IllegalArgumentException("Cannot find sample with ID " + args[0]);
    }

    @Override
    public String description() {
        return "'samp rel' sets the sample ID 'samp' to relation 'rel' (one of " +
                Arrays.stream(Relatedness.Relation.values())
                        .map(a -> "`" + a.name() + "`")
                        .collect(Collectors.joining(", ")) +
                ")";
    }
}
