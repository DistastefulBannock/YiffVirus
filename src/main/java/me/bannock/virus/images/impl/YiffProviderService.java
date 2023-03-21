package me.bannock.virus.images.impl;

import me.bannock.virus.images.ImageProviderService;

import java.util.List;

public class YiffProviderService implements ImageProviderService {

    @Override
    public List<String> fetchImages(int count) {
        return null;
    }

    @Override
    public byte[] fetchBytes(String url) {
        return new byte[0];
    }

}
