package me.bannock.virus.images;

import java.util.List;

public interface ImageProviderService {

    /**
     * Fetches a specific amount of images and returns the urls to reach them
     * @param count The amount of images to fetch
     * @return A list containing the urls to reach the images at
     */
    List<String> fetchImages(int count);

    /**
     * Fetches an image and returns its bytes
     * @param url The url the image can be found at
     * @return The bytes of the image
     */
    byte[] fetchBytes(String url);

}
