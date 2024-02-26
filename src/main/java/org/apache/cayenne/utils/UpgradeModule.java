package org.apache.cayenne.utils;

import io.bootique.BQCoreModule;
import io.bootique.di.BQModule;
import io.bootique.di.Binder;
import io.bootique.di.Provides;
import org.apache.cayenne.configuration.runtime.CoreModule;
import org.apache.cayenne.configuration.xml.DataChannelMetaData;
import org.apache.cayenne.configuration.xml.DefaultDataChannelMetaData;
import org.apache.cayenne.configuration.xml.HandlerFactory;
import org.apache.cayenne.dbsync.reverse.configuration.ToolsModule;
import org.apache.cayenne.di.DIBootstrap;
import org.apache.cayenne.di.Injector;
import org.apache.cayenne.gen.CgenModule;
import org.apache.cayenne.project.ProjectModule;
import org.apache.cayenne.project.extension.ExtensionAwareHandlerFactory;
import org.apache.cayenne.project.extension.info.InfoExtension;
import org.apache.cayenne.tools.DefaultCgenService;
import org.slf4j.LoggerFactory;

public class UpgradeModule implements BQModule {

    @Override
    public void configure(Binder binder) {
        BQCoreModule.extend(binder)
                .addCommand(BatchUpgradeCommand.class)
                .addCommand(BatchCgenCommand.class);
        
        binder.bind(ProjectFileLocator.class).to(DefaultProjectFileLocator.class);
        binder.bind(ProjectFileUpgrader.class).to(DefaultProjectFileUpgrader.class);
        binder.bind(CgenService.class).to(DefaultCgenService.class);
    }

    /**
     * @return Cayenne DI injector used by upgrade and cgen
     */
    @Provides
    Injector createInjector() {
        return DIBootstrap.createInjector(
                new ProjectModule(),
                new CoreModule(),
                new ToolsModule(LoggerFactory.getLogger(UpgradeModule.class)),
                new CgenModule(),
                binder -> {
                    binder.bind(HandlerFactory.class).to(ExtensionAwareHandlerFactory.class);
                    binder.bind(DataChannelMetaData.class).to(DefaultDataChannelMetaData.class);

                    // include all extensions that normally used by modeler
                    ProjectModule.extend(binder).addExtension(InfoExtension.class);
                });
    }
}
