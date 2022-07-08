package org.apache.cayenne.utils;

import io.bootique.BQModuleProvider;
import io.bootique.di.BQModule;

public class UpgradeModuleProvider implements BQModuleProvider {

    @Override
    public BQModule module() {
        return new UpgradeModule();
    }

}
