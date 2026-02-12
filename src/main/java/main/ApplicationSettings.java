package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationSettings {

   public static String importFolder = "/home/cssuser/Downloads/FotosParaImportar/";
   public static final String managedImagesFolder = "/home/cssuser/Desktop/ManagerFotos/";

    public static final int ThumbnailHeight = 300;

    public ApplicationSettings() {

    }

    public List<Path> getPaths() throws IOException {

        Stream<Path> pathStream = Files.find(Paths.get(ApplicationSettings.importFolder), 999, (p, bfa) -> bfa.isRegularFile() && (p.toString().endsWith("jpg") || p.toString().endsWith("JPG")));
        List<Path> pathList = pathStream.collect(Collectors.toList());
        return pathList;

    }
}
