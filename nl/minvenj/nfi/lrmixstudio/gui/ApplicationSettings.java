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
package nl.minvenj.nfi.lrmixstudio.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.minvenj.nfi.lrmixstudio.domain.PopulationStatistics;

public class ApplicationSettings {
    private static long _settingsLastModified;

    private ApplicationSettings() {
    }

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationSettings.class);
    private static final HashMap<String, String> properties = new HashMap<>();
    private static final Properties staticProperties = new Properties();
    /**
     * Dynamic properties sourced from the LRmixStudio.properties file in the
     * install directory
     */
    private static final String SETTINGS_FILENAME = "LRmixStudio.properties";
    private static final String CASEFILES_PATH = "caseFilesPath";
    private static final String ALLELEFREQUENCIES_PATH = "alleleFrequenciesPath";
    private static final String RARE_ALLELE_FREQUENCY = "rareAlleleFrequency";
    private static final String THREADCOUNT = "threadCount";
    private static final String VALIDATION_MODE = "validationMode";
    private static final String HIGHLIGHT_COLOR = "highlightColor";
    private static final String HIGHLIGHT_BACKGROUND_COLOR = "highlightBackgroundColor";
    private static final String HIGHLIGHT_UNDERLINE = "highlightUnderline";
    private static final String HIGHLIGHT_BOLD = "highlightBold";
    private static final String HIGHLIGHT_ITALIC = "highlightItalic";
    private static final int DEFAULT_THREADCOUNT = Runtime.getRuntime().availableProcessors();
    private static final String LOSTTIMETHRESHOLD = "acceptableLostTimeOnSessionClear";
    private static final String MRU = "mostRecentlyUsed";
    private static final String MRU_MAX_ENTRIES = "maxMostRecentlyUsedEntries";
    private static final long DEFAULT_LOSTTIMETHRESHOLD = 60000;
    private static final int DEFAULT_MAX_MRU_ENTRIES = 4;
    private static final String ADVANCED_MODE = "advancedMode";

    /**
     * Static properties sourced from LRmixStudio.properties in the resources
     * package
     */
    private static final String VERSION = "version";
    private static final String ICON = "icon.";
    private static final String TRAYICON = "trayIcon.";
    private static final String TRAYICON_IDLE = TRAYICON + "idle.";
    private static final String TRAYICON_BUSY = TRAYICON + "busy.";
    private static final String SHOWGRID = "showGrid";
    private static final String SAMPLES_SHOWGRID = "samples." + SHOWGRID;
    private static final String OVERVIEW_SHOWGRID = "overview." + SHOWGRID;

    private static void load() {
        final File f = new File(SETTINGS_FILENAME);
        if (f.lastModified() != _settingsLastModified) {
            try {
                _settingsLastModified = f.lastModified();
                properties.clear();
                final FileInputStream fis = new FileInputStream(SETTINGS_FILENAME);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!(line.startsWith("#") || line.isEmpty())) {
                        final int idx = line.indexOf("=");
                        properties.put(line.substring(0, idx).trim(), line.substring(idx + 1).trim());
                    }
                }
            }
            catch (final FileNotFoundException ex) {
            }
            catch (final IOException ex) {
                LOG.warn("Error reading from {}", SETTINGS_FILENAME, ex);
            }
        }
    }

    private static void store() {
        try {
            final FileOutputStream fos = new FileOutputStream(SETTINGS_FILENAME);
            for (final String key : properties.keySet()) {
                fos.write((key + "=" + properties.get(key) + "\n").getBytes());
            }
        }
        catch (final FileNotFoundException ex) {
            LOG.warn("Error writing to {}", SETTINGS_FILENAME, ex);
        } catch (final IOException ex) {
            LOG.warn("Error writing to {}", SETTINGS_FILENAME, ex);
        }
    }

    private static void removeByPrefix(final String prefix) {
        final ArrayList<String> toRemove = new ArrayList<String>();
        for (final String key : properties.keySet()) {
            if (key.startsWith(prefix)) {
                toRemove.add(key);
            }
        }

        for (final String removeThisKey : toRemove) {
            properties.remove(removeThisKey);
        }

        store();
    }

    private static void set(final String key, final String value) {
        if (value != null) {
        properties.put(key, value);
        } else {
            properties.remove(key);
        }
        store();
    }

    private static String get(final String key) {
        load();
        if (properties.containsKey(key)) {
            return properties.get(key);
        }
        return "";
    }

    public static void setHighlightColor(final Color c) {
        if (c == null) {
            set(HIGHLIGHT_COLOR, "");
        } else {
            set(HIGHLIGHT_COLOR, Integer.toHexString(c.getRGB()));
        }
    }

    public static Color getHighlightColor() {
        String colorString = get(HIGHLIGHT_COLOR);
        if (colorString.isEmpty()) {
            return Color.white;
        }
        if (colorString.length() > 6) {
            colorString = colorString.substring(colorString.length() - 6);
        }
        return new Color(Integer.parseInt(colorString, 16));
    }

    public static boolean isSetHighlightColor() {
        return !get(HIGHLIGHT_COLOR).isEmpty();
    }

    public static void setHighlightBackgroundColor(final Color c) {
        if (c == null) {
            set(HIGHLIGHT_BACKGROUND_COLOR, "");
        } else {
            set(HIGHLIGHT_BACKGROUND_COLOR, Integer.toHexString(c.getRGB()));
        }
    }

    public static Color getHighlightBackgroundColor() {
        String colorString = get(HIGHLIGHT_BACKGROUND_COLOR);
        if (colorString.isEmpty()) {
            return Color.blue;
        }
        if (colorString.length() > 6) {
            colorString = colorString.substring(colorString.length() - 6);
        }
        return new Color(Integer.parseInt(colorString, 16));
    }

    public static boolean isSetHighlightBackgroundColor() {
        return !get(HIGHLIGHT_BACKGROUND_COLOR).isEmpty();
    }

    public static void setHighlightUnderlined(final boolean isUnderlined) {
        set(HIGHLIGHT_UNDERLINE, "" + isUnderlined);
    }

    public static boolean getHighlightUnderlined() {
        final String isUnderlined = get(HIGHLIGHT_UNDERLINE);
        if (isUnderlined.isEmpty()) {
            return true;
        }
        return Boolean.parseBoolean(isUnderlined);
    }

    public static void setHighlightBold(final boolean isBold) {
        set(HIGHLIGHT_BOLD, "" + isBold);
    }

    public static boolean getHighlightBold() {
        final String isBold = get(HIGHLIGHT_BOLD);
        if (isBold.isEmpty()) {
            return true;
        }
        return Boolean.parseBoolean(isBold);
    }

    public static void setHighlightItalic(final boolean isItalic) {
        set(HIGHLIGHT_ITALIC, "" + isItalic);
    }

    public static boolean getHighlightItalic() {
        final String isItalic = get(HIGHLIGHT_ITALIC);
        if (isItalic.isEmpty()) {
            return true;
        }
        return Boolean.parseBoolean(isItalic);
    }

    private static boolean _validationMode = false;

    public static void setValidationMode(final boolean mode) {
        _validationMode = mode;
    }

    public static boolean isValidationMode() {
        return _validationMode;
    }

    public static String getCaseFilesPath() {
        return get(CASEFILES_PATH);
    }

    public static void setCaseFilesPath(final String path) {
        set(CASEFILES_PATH, path);
    }

    public static String getAlleleFrequenciesPath() {
        return get(ALLELEFREQUENCIES_PATH);
    }

    public static void setAlleleFrequenciesPath(final String path) {
        set(ALLELEFREQUENCIES_PATH, path);
    }

    public static String getRareAlleleFrequency(final PopulationStatistics statistics) {
        final String rare = get(RARE_ALLELE_FREQUENCY + (statistics == null ? "" : statistics.getFileHash()));
        if (rare.isEmpty()) {
            return "" + PopulationStatistics.DEFAULT_FREQUENCY;
        }
        return rare;
    }

    public static void setRareAlleleFrequency(final PopulationStatistics statistics, final double freq) {
        set(RARE_ALLELE_FREQUENCY + (statistics == null ? "" : statistics.getFileHash()), "" + freq);
    }

    public static void setThreadCount(final int threadCount) {
        set(THREADCOUNT, "" + threadCount);
    }

    public static Iterable<String> getMostRecentlyUsed() {
        final ArrayList<String> mru = new ArrayList<>();
        int idx = 0;
        String mruSession;
        while (idx < getMaxMostRecentlyUsedEntries() && !(mruSession = get(MRU + idx)).isEmpty()) {
            idx++;
            mru.add(mruSession);
        }
        return mru;
    }

    public static void addMostRecentlyUsed(final String fileName) {
        final ArrayList<String> mru = new ArrayList<>();
        mru.add(fileName);
        int idx = 0;
        String mruSession;
        while (idx < getMaxMostRecentlyUsedEntries() && !(mruSession = get(MRU + idx)).isEmpty()) {
            idx++;
            if (!mruSession.equalsIgnoreCase(fileName)) {
                mru.add(mruSession);
            }
        }
        idx = 0;
        for (final String session : mru) {
            set(MRU + idx++, session);
        }
    }

    public static void removeMostRecentlyUsed(final String fileName) {
        final ArrayList<String> mru = new ArrayList<>();
        int idx = 0;
        String mruSession;
        while (idx < getMaxMostRecentlyUsedEntries() && !(mruSession = get(MRU + idx)).isEmpty()) {
            idx++;
            if (!mruSession.equalsIgnoreCase(fileName)) {
                mru.add(mruSession);
            }
        }
        removeByPrefix(MRU);
        idx = 0;
        for (final String session : mru) {
            set(MRU + idx++, session);
        }
    }

    public static int getThreadCount() {
        final String threadCount = get(THREADCOUNT);
        try {
            return Integer.parseInt(threadCount);
        } catch (final NumberFormatException nfe) {
            return DEFAULT_THREADCOUNT;
        }
    }

    public static void setLostTimeThreshold(final long threshold) {
        set(LOSTTIMETHRESHOLD, "" + threshold);
    }

    public static long getLostTimeThreshold() {
        final String threshold = get(LOSTTIMETHRESHOLD);
        try {
            return Long.parseLong(threshold);
        } catch (final NumberFormatException nfe) {
            return DEFAULT_LOSTTIMETHRESHOLD;
        }
    }

    public static void setMaxMostRecentlyUsedEntries(final int maxMru) {
        set(MRU_MAX_ENTRIES, "" + maxMru);
    }

    public static int getMaxMostRecentlyUsedEntries() {
        final String maxMru = get(MRU_MAX_ENTRIES);
        try {
            return Integer.parseInt(maxMru);
        } catch (final NumberFormatException nfe) {
            return DEFAULT_MAX_MRU_ENTRIES;
        }
    }

    private static void staticInit() {
        if (staticProperties.isEmpty()) {
            try {
                staticProperties.load(ApplicationSettings.class.getResourceAsStream("/resources/LRmixStudio.properties"));
            } catch (final IOException ex) {
            }
        }
    }

    public static String getProgramVersion() {
        final String version = LRmixStudio.class.getPackage().getImplementationVersion();
        if (version == null)
            return "Debug";
        return version;
    }

    public static String getIcon(final int idx) {
        staticInit();
        return staticProperties.getProperty(ICON + idx);
    }

    public static String getIdleTrayIcon(final int idx) {
        staticInit();
        return staticProperties.getProperty(TRAYICON_IDLE + idx);
    }

    public static String getBusyTrayIcon(final int idx) {
        staticInit();
        return staticProperties.getProperty(TRAYICON_BUSY + idx);
    }

    public static boolean isOverviewGridShown() {
        staticInit();
        return Boolean.parseBoolean(staticProperties.getProperty(OVERVIEW_SHOWGRID, "true"));
    }

    public static Color getColor(final String propertyName) {
        staticInit();
        final String colorString = staticProperties.getProperty(propertyName, "255,255,255");
        final String[] components = colorString.split(",");
        return new Color(Integer.parseInt(components[0]), Integer.parseInt(components[1]), Integer.parseInt(components[2]));
    }

    public static Color getOverviewEvenRowColor() {
        return getColor("overview.color.evenRows");
    }

    public static Color getOverviewOddRowColor() {
        return getColor("overview.color.oddRows");
    }

    public static Color getOverviewReplicateRowColor() {
        return getColor("overview.color.replicateRows");
    }

    public static Color getOverviewReplicateRowTextColor() {
        return getColor("overview.color.replicateRows.text");
    }

    public static Color getEvenRowColor(final String name) {
        return getColor(name + ".color.evenRows");
    }

    public static Color getOddRowColor(final String name) {
        return getColor(name + ".color.oddRows");
    }

    public static boolean isAdvancedMode() {
        return "true".equalsIgnoreCase(get(ADVANCED_MODE));
    }
}
