package org.apache.cayenne.utils;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;

public class UpgradeModuleProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new UpgradeModule();
    }

}
