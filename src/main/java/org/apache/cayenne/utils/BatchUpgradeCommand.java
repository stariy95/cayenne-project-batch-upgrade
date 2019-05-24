package org.apache.cayenne.utils;

import java.io.File;

import javax.inject.Inject;

import io.bootique.cli.Cli;
import io.bootique.command.CommandOutcome;
import io.bootique.command.CommandWithMetadata;
import io.bootique.meta.application.CommandMetadata;
import io.bootique.meta.application.OptionMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BatchUpgradeCommand extends CommandWithMetadata {

    private static Logger logger = LoggerFactory.getLogger(ProjectFileUpgrader.class);

    @Inject
    private ProjectFileLocator fileLocator;

    @Inject
    private ProjectFileUpgrader fileUpgrader;

    public BatchUpgradeCommand() {
        super(createMetadata());
    }

    @Override
    public CommandOutcome run(Cli cli) {
        if(!cli.hasOption("dir")) {
            return CommandOutcome.failed(1, "No base dir set");
        }

        File baseDir = new File(cli.optionString("dir"));
        if(!baseDir.isDirectory()) {
            return CommandOutcome.failed(1, "Invalid directory: " + baseDir);
        }

        logger.info("Processing content for path: {}", baseDir.getAbsolutePath());

        fileLocator.find(baseDir).forEach(fileUpgrader::upgrade);

        return CommandOutcome.succeeded();
    }

    private static CommandMetadata createMetadata() {
        return CommandMetadata.builder(BatchUpgradeCommand.class)
                .shortName('u')
                .addOption(OptionMetadata.builder("dir").shortName('d').valueRequired()
                        .description("Base directory to search for project files").build())
                .description("Perform batch upgrade of Cayenne projects.")
                .build();
    }
}