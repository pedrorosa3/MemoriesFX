package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationSettings {

   // Pastas relativas à raiz do projeto, para funcionar em qualquer sistema operativo.
   // Podem ser alteradas com -Dmemoriesfx.importFolder=... e -Dmemoriesfx.managedFolder=...
   public static String importFolder =
           folder("memoriesfx.importFolder", "fotos/FotosParaImportar");
   public static final String managedImagesFolder =
           folder("memoriesfx.managedFolder", "fotos/ManagerFotos");

   /** Caminho absoluto, sempre com '/' e terminado em '/' (o resto do código assume este formato). */
   private static String folder(String property, String defaultPath) {
       String path = System.getProperty(property, defaultPath);
       return Paths.get(path).toAbsolutePath().normalize().toString().replace('\\', '/') + "/";
   }

    public static final int ThumbnailHeight = 300;

    public ApplicationSettings() {

    }

    public List<Path> getPaths() throws IOException {

        Stream<Path> pathStream = Files.find(Paths.get(ApplicationSettings.importFolder), 999, (p, bfa) -> bfa.isRegularFile() && (p.toString().endsWith("jpg") || p.toString().endsWith("JPG")));
        List<Path> pathList = pathStream.collect(Collectors.toList());
        return pathList;

    }
}
