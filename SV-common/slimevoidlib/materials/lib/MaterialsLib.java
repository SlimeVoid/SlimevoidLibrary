package slimevoidlib.materials.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import slimevoidlib.materials.api.IMaterialHandler;
import slimevoidlib.util.helpers.ItemHelper;

import net.minecraft.block.Block;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class MaterialsLib {

	public static final int							minimumlength		= 50;
	private static ArrayList<ItemStack>				materials			= new ArrayList<ItemStack>();
	private static ArrayList<String>				descs				= new ArrayList<String>();
	private static ArrayList<IMaterialHandler>		materialHandlers	= new ArrayList<IMaterialHandler>();
	private static HashMap<List<Integer>, Integer>	materialIndex		= new HashMap<List<Integer>, Integer>();
	public static int								brickIndex			= 5;

	public static int getSize() {
		return materials.size();
	}

	public static void initMaterials() {
		descs.clear();
		materials.clear();
		addMaterial(Block.stone, "Stone");
		addMaterial(Block.grass, "Grass");
		addMaterial(Block.dirt, "Dirt");
		addMaterial(Block.cobblestone, "Cobblestone");
		addMaterial(Block.planks, "Oak Plank");
		addMaterial(Block.planks, 1, "Spruce Plank");
		addMaterial(Block.planks, 2, "Birch Plank");
		addMaterial(Block.planks, 3, "Jungle Plank");
		addMaterial(Block.wood, 0, "Oak Wood");
		addMaterial(Block.wood, 1, "Spruce Wood");
		addMaterial(Block.wood, 2, "Birch Wood");
		addMaterial(Block.wood, 3, "Jungle Wood");
		addMaterial(Block.glass, "Glass");
		addMaterial(Block.blockLapis, "Lapis Lazuli");
		addMaterial(Block.sandStone, "Sandstone");
		addMaterial(Block.sandStone, 1, "Chisled Sandstone");
		addMaterial(Block.sandStone, 2, "Smooth Sandstone");
		for (int i = 0; i < 16; i++) {
			addMaterial(Block.cloth,
						i,
						(new StringBuilder())
								.append(ItemHelper.correctName(ItemDye.dyeColorNames[15 - i]))
								.append(" Wool").toString());
		}
		addMaterial(Block.blockGold, "Gold");
		addMaterial(Block.blockIron, "Iron");
		addMaterial(Block.brick, "Brick");
		addMaterial(Block.bookShelf, "Bookshelf");
		addMaterial(Block.cobblestoneMossy, "Moss Stone");
		addMaterial(Block.obsidian, "Obsidian");
		addMaterial(Block.blockDiamond, "Diamond");
		addMaterial(Block.blockSnow, "Snow");
		addMaterial(Block.blockClay, "Clay");
		addMaterial(Block.pumpkin, "Pumpkin");
		addMaterial(Block.netherrack, "Netherrack");
		addMaterial(Block.slowSand, "Soul Sand");
		addMaterial(Block.glowStone, "Glowstone");
		addMaterial(Block.stoneBrick, 0, "Stone Brick");
		addMaterial(Block.stoneBrick, 1, "Mossy Stone Brick");
		addMaterial(Block.stoneBrick, 2, "Cracked Stone Brick");
		addMaterial(Block.stoneBrick, 3, "Chisled Stone Brick");
		addMaterial(Block.melon, "Melon");
		addMaterial(Block.netherBrick, "Nether Brick");
		addMaterial(Block.whiteStone, "EndStone");
		addMaterial(Block.blockEmerald, "Emerald");
		addMaterial(Block.blockNetherQuartz, "Quartz");
		addMaterial(Block.blockNetherQuartz, 1, "Chisled Quartz");
		addMaterial(Block.blockNetherQuartz, 2, "Pillar Quartz");
		

	}

	public static void addMaterialHandler(IMaterialHandler handler) {
		for (int i = 0; i < materials.size(); i++) {
			if (materials.get(i) != null) {
				handler.addMaterialReference(i);
			}
		}

		materialHandlers.add(handler);
	}

	public static Integer getMaterial(ItemStack ist) {
		return (Integer) materialIndex.get(Arrays.asList(new Integer[] {
				Integer.valueOf(ist.itemID),
				Integer.valueOf(ist.getItemDamage()) }));
	}

	public static void addMaterial(int blockId, int md, String desc) {
		Block block = Block.blocksList[blockId];
		if (block != null) {
			addMaterial(block, md, desc);
		} else {
			throw new IndexOutOfBoundsException(
					"Block ID: " + blockId + " is not a valid block");
		}
	}

	public static void addMaterial(Block bl, String desc) {
		addMaterial(bl, 0, desc);
	}

	public static void addMaterial(Block bl, int md, String desc) {
		ItemStack ist = new ItemStack(bl, 1, md);
		materials.add(ist);
		descs.add(desc);
		materialIndex.put(	Arrays.asList(new Integer[] {
									Integer.valueOf(bl.blockID),
									Integer.valueOf(md) }),
							Integer.valueOf(materials.size()));
		IMaterialHandler imh;
		for (Iterator i$ = materialHandlers.iterator(); i$.hasNext(); imh
				.addMaterialReference(materials.size())) {
			imh = (IMaterialHandler) i$.next();
		}
	}

	private static int damageToCoverData(int dmg) {
		// 524288, 262144, 131072, 65536
		// 32768, 16384, 8192, 4096
		// 2048, 1024, 512, 256
		// 128, 64, 32, 16
		// 8, 4, 2, 1
		int hd = dmg >> 12; // (Skips the first 8 bits)
		// System.out.println("Damage to hd: " + hd);
		int cn = dmg & 0xfff; // 0xfff = 4095 (Retrieves the first 12 bits)
		// System.out.println("Damage to cn: " + cn);
		switch (hd) {
		case 0: // '\0'
			cn |= 0x10000; // 0001 0000 0000 xxxx xxxx
			break;

		case 16: // '\020'
			cn |= 0x20100; // 0010 0000 0001 xxxx xxxx
			break;

		case 17: // '\021'
			cn |= 0x40200; // 0100 0000 0010 xxxx xxxx
			break;

		case 24: // '\030'
			cn |= 0x110300;
			break;

		case 25: // '\031'
			cn |= 0x120400;
			break;

		case 26: // '\032'
			cn |= 0x140500;
			break;

		case 27: // '\033'
			cn |= 0x30600;
			break;

		case 28: // '\034'
			cn |= 0x50700;
			break;

		case 29: // '\035'
			cn |= 0x60800;
			break;

		case 30: // '\036'
			cn |= 0x70900;
			break;

		case 31: // '\037'
			cn |= 0x130a00;
			break;

		case 32: // ' '
			cn |= 0x150b00;
			break;

		case 33: // '!'
			cn |= 0x160c00;
			break;

		case 34: // '"'
			cn |= 0x170d00;
			break;

		case 18: // '\022'
			cn |= 0x2010000;
			break;

		case 19: // '\023'
			cn |= 0x2020100;
			break;

		case 20: // '\024'
			cn |= 0x2040200;
			break;

		case 35: // '#'
			cn |= 0x2030300;
			break;

		case 36: // '$'
			cn |= 0x2050400;
			break;

		case 37: // '%'
			cn |= 0x2060500;
			break;

		case 38: // '&'
			cn |= 0x2070600;
			break;

		case 21: // '\025'
			cn |= 0x1010000;
			break;

		case 22: // '\026'
			cn |= 0x1020100;
			break;

		case 23: // '\027'
			cn |= 0x1040200;
			break;

		case 39: // '\''
			cn |= 0x1030300;
			break;

		case 40: // '('
			cn |= 0x1050400;
			break;

		case 41: // ')'
			cn |= 0x1060500;
			break;

		case 42: // '*'
			cn |= 0x1070600;
			break;

		case 43: // '+'
			cn |= 0x3020000;
			break;

		case 44: // ','
			cn |= 0x3040100;
			break;

		case 45: // '-'
			cn |= 0x3060200;
			break;
		}
		return cn;
	}

	public static Icon getIconForSide(int n, int side) {
		n = n % materials.size();
		ItemStack ist = getItemStack(n);
		if (ist == null) {
			return Block.stone.getIcon(side, 0);
		}
		return Block.blocksList[ist.itemID].getIcon(side, ist.getItemDamage());

	}

	public static int damageToMaterialValue(int dmg) {
		return damageToCoverData(dmg) & 0xffff; // = 65535
	}

	public static ItemStack getItemStack(int n) {
		n = n % materials.size();
		return materials.get(n);
	}

	public static Block getBlock(int n) {
		n = n % materials.size();
		ItemStack ist = materials.get(n);
		return Block.blocksList[ist.itemID];
	}

	public static int getBlockDmg(int n) {
		n = n % materials.size();
		ItemStack ist = materials.get(n);
		return ist.getItemDamage();
	}

	public static String getName(int n) {
		n = n % materials.size();
		ItemStack bl = getItemStack(n);
		String name = getBlock(n).getUnlocalizedName();
		if (name.endsWith(".name")) {
			name = name.substring(0, name.length() - 6);
		}
		if (getBlockDmg(n) > 0) {
			// add damage to name for better localization
			name += "." + getBlockDmg(n);
		}
		return name;
	}

	public static String getDesc(int n) {
		n = n % materials.size();
		return descs.get(n);
	}
}
