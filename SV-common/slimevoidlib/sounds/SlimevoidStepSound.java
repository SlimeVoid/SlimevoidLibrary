package slimevoidlib.sounds;

import net.minecraft.block.StepSound;

public class SlimevoidStepSound extends StepSound {

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
        return "dig.slimevoid." + this.stepSoundName;
    }

    @Override
    public String getStepSound() {
        return "step.slimevoid." + this.stepSoundName;
    }

    @Override
    public String getPlaceSound() {
        return "place.slimevoid." + this.stepSoundName;
    }

}
