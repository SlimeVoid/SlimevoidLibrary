package eurysmods.core;

import net.minecraft.src.ModLoader;

public class EurysCore {
	public static void console(String modName, String s, int type) {
		switch (type) {
		case 0:
			ModLoader.getLogger().info("[" + modName + "] " + s);
			break;
		case 1:
			ModLoader.getLogger().warning("[" + modName + "] " + s);
			break;
		case 2:
			ModLoader.getLogger().severe("[" + modName + "] " + s);
			break;
		default:
			ModLoader.getLogger().info("[" + modName + "] " + s);
			break;
		}
	}

	public static void console(String modName, String s) {
		console(modName, s, 0);
	}
}
