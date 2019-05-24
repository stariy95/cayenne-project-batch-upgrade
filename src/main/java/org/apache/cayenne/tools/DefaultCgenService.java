package org.apache.cayenne.tools;

import java.io.File;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.cayenne.utils.CgenService;
import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.di.Injector;
import org.apache.cayenne.gen.CgenConfiguration;
import org.apache.cayenne.gen.ClassGenerationAction;
import org.apache.cayenne.map.DataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCgenService implements CgenService {

    private static Logger logger = LoggerFactory.getLogger(DefaultCgenService.class);

    @Inject
    private Injector injector;

    @Override
    public void cgen(File file) {
        File destDir = getDestDir(file);

        try {
            CayenneGeneratorMapLoaderAction loaderAction = new CayenneGeneratorMapLoaderAction(injector);
            loaderAction.setMainDataMapFile(file);
            DataMap dataMap = loaderAction.getMainDataMap();

            ClassGenerationAction generator = createGenerator(destDir, dataMap);
            generator.execute();
        } catch (Exception e) {
            throw new CayenneRuntimeException("Error generating classes: ", e);
        }
    }

    private ClassGenerationAction createGenerator(File destDir, DataMap dataMap) {
        CgenConfiguration config = new CgenConfiguration();
        config.setEncoding("UTF-8");
        config.setTimestamp(System.currentTimeMillis());
        config.setDataMap(dataMap);
        config.setEncoding("UTF-8");
        config.setArtifactsGenerationMode("entity");
        config.setOverwrite(true);
        config.setRootPath(destDir.toPath());
        config.setCreatePKProperties(true);
        config.setRelPath(Paths.get("."));


        ClassGenerationAction generator = new ClassGenerationAction(config);
        injector.injectMembers(generator);
        generator.setLogger(logger);
        generator.addEntities(dataMap.getObjEntities());
        generator.addEmbeddables(dataMap.getEmbeddables());
        generator.addQueries(dataMap.getQueryDescriptors());
        return generator;
    }

    private File getDestDir(File file) {
        String filePath = file.getAbsolutePath();
        String srcDir;
        if(filePath.contains("src/main/resources")) {
            srcDir = filePath.substring(0, filePath.indexOf("src/main/resources")) + "src/main/java/";
        } else if(filePath.contains("src/test/resources")) {
            srcDir = filePath.substring(0, filePath.indexOf("src/test/resources")) + "src/test/java/";
        } else {
            srcDir = file.getParent();
        }

        return new File(srcDir);
    }
}
