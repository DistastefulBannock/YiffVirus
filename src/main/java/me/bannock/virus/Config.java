package me.bannock.virus;

public class Config {
    private boolean changeBackground = true;
    private boolean changeFolderIcons = true;
    private boolean imageFlood = true;
    private int imageAmount = 250;
    private boolean scatterAcrossDrive = true;
    private boolean repeatHourly = true;
    private boolean setUserIcon = true;
    private boolean setPreLoginBackground = true;

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

    public boolean shouldRepeatHourly() {
        return repeatHourly;
    }

    public void setRepeatHourly(boolean repeatHourly) {
        this.repeatHourly = repeatHourly;
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
}
