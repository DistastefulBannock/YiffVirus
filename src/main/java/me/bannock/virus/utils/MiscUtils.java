package me.bannock.virus.utils;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public class MiscUtils {

    /**
     * Recursively goes through directories and adds all non admin ones to the passed in hashmap
     * @param dir The directory to start at
     * @param directories The hashmap to add the directories to
     */
    public static void getNonAdminDirectories(File dir, HashMap<File, File> directories) {

        // Make sure valid dir
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        if (!dir.canRead()){
            return;
        }

        // Get all sub directories
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        // Loop through and recursively call this method for all valid sub directories
        for (File subDir : files) {
            if (subDir == null || !subDir.exists() || !subDir.isDirectory() || directories.containsKey(subDir)) {
                continue;
            }
            if (Files.isWritable(subDir.toPath())) {
                directories.put(subDir, subDir);
            }
            getNonAdminDirectories(subDir, directories);
        }
    }

}
