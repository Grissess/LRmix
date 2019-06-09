/**
 * Copyright (C) 2013, 2014 Netherlands Forensic Institute
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package nl.minvenj.nfi.lrmixstudio.model;

import nl.minvenj.nfi.lrmixstudio.domain.Hypothesis;
import nl.minvenj.nfi.lrmixstudio.domain.LikelihoodRatio;
import nl.minvenj.nfi.lrmixstudio.domain.LocusProbabilities;

public class DefaultAnalysisProgressListenerImpl implements AnalysisProgressListener {
    @Override
    public void analysisStarted() {
    }

    @Override
    public void analysisFinished(final LikelihoodRatio lr) {
    }

    @Override
    public void hypothesisStarted(final Hypothesis hypothesis) {
    }

    @Override
    public void hypothesisFinished(final Hypothesis hypothesis, final LocusProbabilities probabilities) {
    }

    @Override
    public void locusStarted(final Hypothesis hypothesis, final String locusName, final long jobsize) {
    }

    @Override
    public void locusFinished(final Hypothesis hypothesis, final String locusName, final Double locusProbability) {
    }

    @Override
    public void analysisFinished(final Throwable e) {
    }
}
