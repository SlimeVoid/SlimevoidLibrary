package net.slimevoid.library.sounds;

import net.minecraft.block.Block.SoundType;

public class SlimevoidStepSound extends SoundType {

    /**
     * This is a facade class used for the BlockBase of Slimevoid Mods
     * 
     * @param stepSoundName
     * @param volume
     * @param pitch
     */
    public SlimevoidStepSound(String stepSoundName, float volume, float pitch) {
        super(stepSoundName, volume, pitch);
    }

    @Override
    public String getBreakSound() {
        return "dig.slimevoid." + this.soundName;
    }

    @Override
    public String getStepResourcePath() {
        return this.getStepSound();
    }

    public String getStepSound() {
        return "step.slimevoid." + this.soundName;
    }

    @Override
    public String func_150496_b() {
        return this.getPlaceSound();
    }

    public String getPlaceSound() {
        return "place.slimevoid." + this.soundName;
    }

}
