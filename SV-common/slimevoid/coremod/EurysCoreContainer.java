package slimevoid.coremod;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class EurysCoreContainer extends DummyModContainer {

	public EurysCoreContainer()
	{
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
        meta.modId       = "EurysCore_CoreMod";
        meta.name        = "EurysCore CoreMod";
        meta.version     = "1.0";
        meta.authorList  = Arrays.asList("Eurymachus");
        meta.description = "Base core mod for SlimeVoid mods.";
        meta.url         = "http://www.slimevoid.net";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;		
	}
	
	@Subscribe
    public void init(FMLInitializationEvent event)
    {
    }
}
