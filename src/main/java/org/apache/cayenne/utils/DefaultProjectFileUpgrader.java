package org.apache.cayenne.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import org.apache.cayenne.di.Injector;
import org.apache.cayenne.project.upgrade.UpgradeService;
import org.apache.cayenne.project.upgrade.UpgradeMetaData;
import org.apache.cayenne.resource.Resource;
import org.apache.cayenne.resource.URLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultProjectFileUpgrader implements ProjectFileUpgrader {

    private static Logger logger = LoggerFactory.getLogger(ProjectFileUpgrader.class);

    @Inject
    private Injector injector;

    @Override
    public void upgrade(File file) {
        logger.info("Processing file {}", file.getName());
        URL url;
        try{
            url = file.toURI().toURL();
        } catch (MalformedURLException ex) {
            logger.warn("error", ex);
            return;
        }

        Resource rootSource = new URLResource(url);

        UpgradeService upgrader = injector.getInstance(UpgradeService.class);
        UpgradeMetaData md = upgrader.getUpgradeType(rootSource);

        switch (md.getUpgradeType()) {
            case UPGRADE_NOT_NEEDED:
                logger.info("\t\tupgrade not needed");
                return;
            case UPGRADE_NEEDED:
                upgrader.upgradeProject(rootSource);
                logger.info("\t\tupgrade done");
                break;
            case DOWNGRADE_NEEDED:
                logger.warn("\t\tunable to upgrade, file has newer version");
                break;
            case INTERMEDIATE_UPGRADE_NEEDED:
                logger.warn("\t\tunable to upgrade, manual upgrade needed via older Modeler");
                break;
        }
    }
}
