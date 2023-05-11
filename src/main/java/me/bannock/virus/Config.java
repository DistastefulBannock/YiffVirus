package me.bannock.virus;

import com.google.gson.annotations.SerializedName;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.images.impl.YiffProviderService;

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
    private boolean setUserIcon = true;
    @SerializedName("popping")
    private boolean popupImages = true;
    @SerializedName("provider")
    private String imageProvider = YiffProviderService.class.getName();

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

    public boolean shouldSetUserIcons() {
        return setUserIcon;
    }

    public void setSetUserIcon(boolean setUserIcon) {
        this.setUserIcon = setUserIcon;
    }

    public boolean shouldPopupImages() {
        return popupImages;
    }

    public void setPopupImages(boolean popupImages) {
        this.popupImages = popupImages;
    }

    public String getImageProvider() {
        return imageProvider;
    }

    public ImageProviderService getImageProvidmurr() {
        try {
            return (ImageProviderService) Class.forName(imageProvider, false, getClass().getClassLoader()).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {}
        return new YiffProviderService();
    }

    public boolean shouldSetUserIcon() {
        return setUserIcon;
    }

    public void setImageProvider(String imageProvider) {
        this.imageProvider = imageProvider;
    }

    public void setImageProvider(Class<? extends ImageProviderService> imageProvider) {
        this.imageProvider = imageProvider.getName();
    }

}
