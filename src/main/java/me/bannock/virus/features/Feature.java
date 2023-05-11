package me.bannock.virus.features;

import me.bannock.virus.Config;

public abstract class Feature {

    protected Config config = new Config();

    /**
     * Sets the config
     * @param config The config to set
     * @return The same reference of the object
     */
    public Feature setConfig(Config config) {
        this.config = config;
        return this;
    }

    /**
     * The code for the features are defined in the subclasses
     */
    public abstract void run();

}
