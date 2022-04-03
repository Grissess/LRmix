package nl.minvenj.nfi.lrmixstudio.automation;

import nl.minvenj.nfi.lrmixstudio.automation.args.*;
import nl.minvenj.nfi.lrmixstudio.domain.Contributor;
import nl.minvenj.nfi.lrmixstudio.domain.Hypothesis;
import nl.minvenj.nfi.lrmixstudio.domain.PopulationStatistics;
import nl.minvenj.nfi.lrmixstudio.model.ConfigurationData;
import nl.minvenj.nfi.lrmixstudio.model.LRMathModelFactory;

import java.util.Arrays;
import java.util.HashMap;

public class ArgumentHandler {
    ArgumentData data = new ArgumentData();

    public static Argument[] arguments = new Argument[]{
            new DropInArgument(),
            new HelpArgument(),
            new HypothesisDropInArgument(PerHypothesis.DEFENSE), new HypothesisDropInArgument(PerHypothesis.PROSECUTION),
            new HypothesisNonContribArgument(PerHypothesis.DEFENSE), new HypothesisNonContribArgument(PerHypothesis.PROSECUTION),
            new HypothesisPopulationArgument(PerHypothesis.DEFENSE), new HypothesisPopulationArgument(PerHypothesis.PROSECUTION),
            new HypothesisSampleArgument(PerHypothesis.DEFENSE), new HypothesisSampleArgument(PerHypothesis.PROSECUTION),
            new HypothesisThetaArgument(PerHypothesis.DEFENSE), new HypothesisThetaArgument(PerHypothesis.PROSECUTION),
            new HypothesisUnknownArgument(PerHypothesis.DEFENSE), new HypothesisUnknownArgument(PerHypothesis.PROSECUTION),
            new HypothesisRelationArgument(PerHypothesis.DEFENSE), new HypothesisRelationArgument(PerHypothesis.PROSECUTION),
            new ModelNameArgument(),
            new OutputArgument(),
            new PopulationArgument(),
            new ProfileFileArgument(),
            new ProfileFileNoHzArgument(),
            new RareAlleleArgument(),
            new ReplicateFileArgument(),
            new ThetaArgument(),
            new ThreadsArgument(),
            new VersionArgument(),
            new LastRelWinsArgument(),
    };

    protected HashMap<String, Argument> argumentsMap;

    public ArgumentHandler() {
        data.config.setDefense(new Hypothesis("Defense", null));
        data.config.setProsecution(new Hypothesis("Prosecution", null));

        argumentsMap = new HashMap<>();
        for(Argument arg: arguments) {
            for(String name: arg.names()) {
                argumentsMap.put(name, arg);
            }
        }
    }

    public ArgumentHandler(String[] args) throws Exception {
        this();
        processArguments(args);
    }

    public void processArguments(String[] args) throws Exception {
        String[] current = args;
        while(current.length > 0) {
            String name = current[0];
            Argument arg = argumentsMap.get(name);
            if(arg != null) {
                current = arg.consume(Arrays.copyOfRange(current, 1, current.length), data);
                continue;
            }

            throw new IllegalArgumentException(name + " is not a recognized argument");
        }

        if(data.config.getDefense().getPopulationStatistics().getFileName().equals("Empty"))
            data.config.setDefense(installPopStats(data.config.getDefense(), data.config.getStatistics()));
        if(data.config.getProsecution().getPopulationStatistics().getFileName().equals("Empty"))
            data.config.setProsecution((installPopStats(data.config.getProsecution(), data.config.getStatistics())));
        if(data.config.getMathematicalModelName() == null)
            data.config.setMathematicalModelName(LRMathModelFactory.getDefaultModelName());
    }

    public ArgumentData getData() {
        return data;
    }

    public static Hypothesis installPopStats(Hypothesis hypothesis, PopulationStatistics popStat) {
        Hypothesis newHypothesis = new Hypothesis(
                hypothesis.getId(),
                hypothesis.getUnknownCount(),
                popStat,
                hypothesis.getDropInProbability(),
                hypothesis.getUnknownDropoutProbability(),
                hypothesis.getThetaCorrection()
        );

        for(Contributor contributor: hypothesis.getContributors()) {
            newHypothesis.addContributor(contributor.getSample(), contributor.getDropOutProbability(false));
        }
        for(Contributor nonContributor: hypothesis.getNonContributors()) {
            newHypothesis.addNonContributor(nonContributor.getSample(), nonContributor.getDropOutProbability(false));
        }

        return newHypothesis;
    }
}
