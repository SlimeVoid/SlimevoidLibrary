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
package eurysmods.core;

import eurysmods.api.ICommonProxy;
import eurysmods.api.ICore;
import eurysmods.api.IPacketHandling;

public class Core implements ICore {
	private String modName;
	private String modDir;
	private String modChannel;
	ICommonProxy proxy;
	IPacketHandling packethandling;
	private String itemSheet = "gui/items.png";
	private String blockSheet = "terrain.png";

	public Core(ICommonProxy proxyParam) {
		this.proxy = proxyParam;
		this.packethandling = proxy.getPacketHandler();
	}

	@Override
	public String getBlockSheet() {
		String concat = this.modDir + this.blockSheet;
		return concat;
	}

	@Override
	public String getItemSheet() {
		String concat = this.modDir + this.itemSheet;
		return concat;
	}

	@Override
	public void setBlockSheet(String sheet) {
		this.blockSheet = sheet;
	}

	@Override
	public void setItemSheet(String sheet) {
		this.itemSheet = sheet;
	}

	@Override
	public void setModName(String name) {
		this.modName = name;
		this.setModDir(this.modName);
	}

	@Override
	public void setModDir(String dir) {
		this.modDir = "/" + dir + "/";
	}

	@Override
	public String getModName() {
		return this.modName;
	}

	@Override
	public String getModDir() {
		return this.modDir;
	}

	@Override
	public void setModChannel(String channel) {
		this.modChannel = channel;
	}

	@Override
	public String getModChannel() {
		return this.modChannel;
	}

	@Override
	public ICommonProxy getProxy() {
		return this.proxy;
	}

	@Override
	public IPacketHandling getPacketHandler() {
		return this.packethandling;
	}
}
