/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
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
