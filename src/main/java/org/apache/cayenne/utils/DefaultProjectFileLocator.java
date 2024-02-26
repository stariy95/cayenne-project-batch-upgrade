package org.apache.cayenne.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultProjectFileLocator implements ProjectFileLocator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProjectFileLocator.class);

    private static final String DATA_MAP_EXT = ".map.xml";

    @Override
    public Collection<File> find(File base, String fileExtension) {
        if(base.isDirectory()) {
            return findInDirectory(base, fileExtension);
        }
        
        if(match(base, fileExtension)) {
            return Collections.singletonList(base);
        }
        
        return Collections.emptyList();
    }
    
    private boolean match(File file, String fileExtension) {
        if(!DATA_MAP_EXT.equals(fileExtension)) {
            return file.getName().endsWith(fileExtension) && !file.getName().endsWith(DATA_MAP_EXT);
        }
        return file.getName().endsWith(DATA_MAP_EXT);
    }

    private Collection<File> findInDirectory(File dir, String fileExtension) {
        LOGGER.info("Searching in dir " + dir.getAbsolutePath());
        File[] files = dir.listFiles(pathname -> match(pathname, fileExtension));
        if(files == null) {
            throw new RuntimeException("Unable to read directory: " + dir.getAbsolutePath());
        }
        LOGGER.info("Found {} files", files.length);
        return Arrays.asList(files);
    }
}
