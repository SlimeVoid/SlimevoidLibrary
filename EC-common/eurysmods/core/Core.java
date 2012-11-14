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
