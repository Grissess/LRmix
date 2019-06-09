package nl.minvenj.nfi.lrmixstudio.automation;

import nl.minvenj.nfi.lrmixstudio.domain.Hypothesis;
import nl.minvenj.nfi.lrmixstudio.domain.LikelihoodRatio;
import nl.minvenj.nfi.lrmixstudio.domain.LocusProbabilities;
import nl.minvenj.nfi.lrmixstudio.domain.Ratio;
import nl.minvenj.nfi.lrmixstudio.model.AnalysisProgressListener;
import nl.minvenj.nfi.lrmixstudio.model.ConfigurationData;
import nl.minvenj.nfi.lrmixstudio.model.LRMathModel;
import nl.minvenj.nfi.lrmixstudio.model.LRMathModelFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class AutomationMain {
    public static String version = "0.0.1";

    private static class AutomationMainListener implements AnalysisProgressListener {
        ArgumentData data;

        AutomationMainListener(ArgumentData data) {
            this.data = data;
        }

        @Override
        public void analysisStarted() {
            System.err.println("Analysis begins...");
        }

        @Override
        public void analysisFinished(LikelihoodRatio lr) {
            System.err.println("Analysis succeeded");
            PrintStream out;
            if(data.output == null) {
                out = System.out;
            } else {
                try {
                    out = new PrintStream(data.output);
                } catch(FileNotFoundException e) {
                    out = System.out;
                }
            }
            out.println("Locus,ProbProsecution,ProbDefense,LR,LRLog10");
            double pp = 1, pd = 1;
            for(Ratio ratio: lr.getRatios()) {
                out.println(ratio.getLocusName() + "," + ratio.getProsecutionProbability() + "," + ratio.getDefenseProbability() + "," + ratio.getRatio() + "," + Math.log10(ratio.getRatio()));
                pp *= ratio.getProsecutionProbability();
                pd *= ratio.getDefenseProbability();
            }
            out.println("_OVERALL_," + pp + "," + pd + "," + lr.getOverallRatio().getRatio() + "," + Math.log(lr.getOverallRatio().getRatio()));
            System.exit(0);
        }

        @Override
        public void analysisFinished(Throwable e) {
            System.err.println("Analysis finished with error: " + e.toString());
            System.exit(1);
        }

        @Override
        public void hypothesisStarted(Hypothesis hypothesis) {}

        @Override
        public void hypothesisFinished(Hypothesis hypothesis, LocusProbabilities probability) {}

        @Override
        public void locusStarted(Hypothesis hypothesis, String locusName, long jobsize) {}

        @Override
        public void locusFinished(Hypothesis hypothesis, String locusName, Double locusProbability) {}
    }

    public static void main(String[] args) {
        try {
            ArgumentHandler handler = new ArgumentHandler(args);
            ArgumentData data = handler.getData();
            LRMathModel model = LRMathModelFactory.getMathematicalModel(data.config.getMathematicalModelName());
            System.err.println("Starting analysis...");
            model.addProgressListener(new AutomationMainListener(data));
            model.startAnalysis(data.config);
        } catch(Exception e) {
            System.err.println("Error during initialization:");
            e.printStackTrace(System.err);
            return;
        }
    }
}
