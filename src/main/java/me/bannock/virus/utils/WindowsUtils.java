package me.bannock.virus.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.win32.W32APIOptions;

public class WindowsUtils {

    /**
     * Uses JNA to change the background on a Windows pc
     *
     * @param pathToImage The path to the image that should be displayed on the background
     */
    public static void changeBackground(String pathToImage) {
        User32.INSTANCE.SystemParametersInfo(0x0014 /* SPI_SETDESKWALLPAPER */,
                0, pathToImage, 1 /* SPIF_SENDCHANGE */);
    }

    /**
     * Totally not stolen from this stackoverflow answer
     * https://stackoverflow.com/a/4350688
     * @return True if the program is running in administrator mode, otherwise false
     */
    public static boolean isRunningAsAdmin() {
        String[] groups = (new com.sun.security.auth.module.NTSystem()).getGroupIDs();
        for (String group : groups) {
            if (group.equals("S-1-5-32-544"))
                return true;
        }
        return false;
    }

    /**
     * Totally not stolen from stackoverflow
     */
    public interface User32 extends Library {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        /**
         * <a href="https://learn.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-systemparametersinfoa?redirectedfrom=MSDN">...</a>
         *
         * @param uiAction Read docs above
         * @param uiParam Read docs above
         * @param pvParam Read docs above
         * @param fWinIni Read docs above
         * @return
         */
        boolean SystemParametersInfo(int uiAction, int uiParam, String pvParam, int fWinIni);
    }

}
