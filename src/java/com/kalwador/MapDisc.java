package com.kalwador;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kalwador
 */
public class MapDisc {

    private HashMap<String, ArrayList<String>> files;

    public MapDisc() {
        this.files = new HashMap<>();
    }

    public void map(String path) {
        try {
            String tempPath = path;
            final Path sourceDir = Paths.get(tempPath);
            Files.walkFileTree(sourceDir, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                    new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (!files.containsKey(dir)) {
                        ArrayList<String> temp = new ArrayList<>();
                        files.put(Utils.replaceOut(dir.toString()), temp);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileFullPath = Utils.replaceOut(file.toString());
                    String fileName = Utils.replaceOut(file.getFileName().toString());
                    String filePath = fileFullPath.substring(0, fileFullPath.length() - fileName.length() - 1);

                    ArrayList<String> temp = files.get(filePath);
                    if (temp == null) {
                        temp = new ArrayList<>();
                        temp.add(fileName);
                        files.put(filePath, temp);
                    } else {
                        temp.add(fileName);
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(MapDisc.class.getName()).log(Level.SEVERE, null, ex);
            System.err.print(ex);
        }
    }

    public HashMap<String, ArrayList<String>> getFiles() {
        return files;
    }
}