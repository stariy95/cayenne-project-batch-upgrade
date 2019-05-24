package org.apache.cayenne.utils;

import java.io.File;
import java.util.Collection;

public interface ProjectFileLocator {
    
    Collection<File> find(File base, String fileExtension);
    
    default Collection<File> find(File base) {
        return find(base, ".xml");
    }
    
}
