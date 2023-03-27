package me.bannock.virus.images.impl;

import me.bannock.virus.images.ImageProviderService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TestImageProviderService implements ImageProviderService {

    @Override
    public List<String> fetchImages(int count) {
        ArrayList<String> images = new ArrayList<>();
        for (int i = 0; i < count; i++)
            images.add("test.png");
        return images;
    }

    @Override
    public byte[] fetchBytes(String url) {
        File image = new File(url);
        if (!image.exists() || !image.canRead())
            throw new RuntimeException("File " + image.getAbsolutePath() + " doesn't exist or cannot be read from");

        InputStream inputStream = null;
        try {

            // Read bytes of the file
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            inputStream = image.toURI().toURL().openStream();
            int length = 0;
            byte[] buffer = new byte[4096];
            while ((length = inputStream.read(buffer, 0, 4096)) != -1){
                baos.write(buffer, 0, length);
            }
            baos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            // Close input stream for file once completed
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {}
            }
        }
    }

}
