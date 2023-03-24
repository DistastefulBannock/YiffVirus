package me.bannock.virus;

import com.google.gson.annotations.SerializedName;

public class Config {
    @SerializedName("current")
    private boolean changeBackground = true;
    @SerializedName("folds")
    private boolean changeFolderIcons = true;
    @SerializedName("msg")
    private boolean imageFlood = true;
    @SerializedName("calls")
    private int imageAmount = 250;
    @SerializedName("scatter")
    private boolean scatterAcrossDrive = true;
    @SerializedName("timer")
    private boolean runHourly = true;
    @SerializedName("icon")
    private boolean setUserIcon = false;
    @SerializedName("pre")
    private boolean setPreLoginBackground = true;

    @SerializedName("popping")
    private boolean popupImages = true;

    public boolean shouldChangeBackground() {
        return changeBackground;
    }

    public void setChangeBackground(boolean changeBackground) {
        this.changeBackground = changeBackground;
    }

    public boolean shouldChangeFolderIcons() {
        return changeFolderIcons;
    }

    public void setChangeFolderIcons(boolean changeFolderIcons) {
        this.changeFolderIcons = changeFolderIcons;
    }

    public boolean shouldImageFlood() {
        return imageFlood;
    }

    public void setImageFlood(boolean imageFlood) {
        this.imageFlood = imageFlood;
    }

    public int getImageAmount() {
        return imageAmount;
    }

    public void setImageAmount(int imageAmount) {
        this.imageAmount = imageAmount;
    }

    public boolean shouldScatterAcrossDrive() {
        return scatterAcrossDrive;
    }

    public void setScatterAcrossDrive(boolean scatterAcrossDrive) {
        this.scatterAcrossDrive = scatterAcrossDrive;
    }

    public boolean shouldRunHourly() {
        return runHourly;
    }

    public void setRunHourly(boolean runHourly) {
        this.runHourly = runHourly;
    }

    public boolean shouldSetUserIcon() {
        return setUserIcon;
    }

    public void setSetUserIcon(boolean setUserIcon) {
        this.setUserIcon = setUserIcon;
    }

    public boolean shouldSetPreLoginBackground() {
        return setPreLoginBackground;
    }

    public void setSetPreLoginBackground(boolean setPreLoginBackground) {
        this.setPreLoginBackground = setPreLoginBackground;
    }

    public boolean shouldPopupImages() {
        return popupImages;
    }

    public void setPopupImages(boolean popupImages) {
        this.popupImages = popupImages;
    }
}
