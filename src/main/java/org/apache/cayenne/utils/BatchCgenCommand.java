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


public class BatchCgenCommand extends CommandWithMetadata {

    private static Logger logger = LoggerFactory.getLogger(BatchCgenCommand.class);

    @Inject
    private ProjectFileLocator fileLocator;

    @Inject
    private CgenService cgenService;

    public BatchCgenCommand() {
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

        fileLocator.find(baseDir, ".map.xml").forEach(cgenService::cgen);

        return CommandOutcome.succeeded();
    }

    private static CommandMetadata createMetadata() {
        return CommandMetadata.builder(BatchCgenCommand.class)
                .shortName('g')
                .addOption(OptionMetadata.builder("dir").shortName('d').valueRequired()
                        .description("Base directory to search for project files").build())
                .description("Perform batch cgen for found Cayenne map xml files.")
                .build();
    }
}