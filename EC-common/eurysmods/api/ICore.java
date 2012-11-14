package eurysmods.api;

public interface ICore {
	String getBlockSheet();

	String getItemSheet();

	void setBlockSheet(String sheet);

	void setItemSheet(String sheet);

	void setModName(String name);

	void setModDir(String dir);

	void setModChannel(String string);

	String getModName();

	String getModDir();

	String getModChannel();

	ICommonProxy getProxy();

	IPacketHandling getPacketHandler();
}
