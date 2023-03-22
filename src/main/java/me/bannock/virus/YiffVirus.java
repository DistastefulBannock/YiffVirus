package me.bannock.virus;


import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.images.impl.TestImageProviderService;
import me.bannock.virus.images.impl.YiffProviderService;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.util.Arrays;

public class YiffVirus extends JFrame {

    private Config config = new Config();
    private final ImageProviderService imageProviderService = new TestImageProviderService();

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

        setVisible(true);
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
                    // TODO: Auto load config and run virus
                }break;
            }
        }

        // Display gui if wanted by user
        if (showGui)
            yiffVirus.setVisible(true);

    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public ImageProviderService getImageProviderService() {
        return imageProviderService;
    }
}
