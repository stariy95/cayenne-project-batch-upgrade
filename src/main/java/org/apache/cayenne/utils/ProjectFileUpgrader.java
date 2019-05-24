package org.apache.cayenne.utils;

import java.io.File;

public interface ProjectFileUpgrader {
    
    void upgrade(File file);
    
}
