package net.slimevoid.library.util.helpers;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHelper {
    public static boolean moveItemStack(Container container, ItemStack stackInSlot, int slotToMove) {
        Slot slot = (Slot) container.inventorySlots.get(slotToMove);
        ItemStack itemstack1 = slot.getStack();
        if (itemstack1 == null) {
            slot.putStack(stackInSlot.copy());
            slot.getStack().stackSize = 1;
            slot.onSlotChanged();
            --stackInSlot.stackSize;
            return true;
        }
        return false;
    }

    public static boolean mergeItemStack(Container container, ItemStack stackToMerge, int slotStart, int slotEnd, boolean reverseOrder) {
        boolean stackMerged = false;
        int realSlotStart = slotStart;

        if (reverseOrder) {
            realSlotStart = slotEnd - 1;
        }

        Slot slot;
        ItemStack stackInSlot;

        if (stackToMerge.isStackable()) {
            while (stackToMerge.stackSize > 0
                   && (!reverseOrder && realSlotStart < slotEnd || reverseOrder
                                                                   && realSlotStart >= slotStart)) {
                slot = (Slot) container.inventorySlots.get(realSlotStart);
                stackInSlot = slot.getStack();

                if (stackInSlot != null
                    && stackInSlot.getItem() == stackToMerge.getItem()
                    && (!stackToMerge.getHasSubtypes() || stackToMerge.getItemDamage() == stackInSlot.getItemDamage())
                    && ItemStack.areItemStackTagsEqual(stackToMerge,
                                                       stackInSlot)) {
                    int l = stackInSlot.stackSize + stackToMerge.stackSize;

                    if (l <= stackToMerge.getMaxStackSize()
                        && l <= slot.getSlotStackLimit()) {
                        stackToMerge.stackSize = 0;
                        stackInSlot.stackSize = l;
                        slot.onSlotChanged();
                        stackMerged = true;
                    } else if (stackInSlot.stackSize < stackToMerge.getMaxStackSize()
                               && stackInSlot.stackSize < slot.getSlotStackLimit()) {
                        stackToMerge.stackSize -= stackToMerge.getMaxStackSize()
                                                  - stackInSlot.stackSize;
                        stackInSlot.stackSize = stackToMerge.getMaxStackSize();
                        slot.onSlotChanged();
                        stackMerged = true;
                    }
                }

                if (reverseOrder) {
                    --realSlotStart;
                } else {
                    ++realSlotStart;
                }
            }
        }

        if (stackToMerge.stackSize > 0) {
            if (reverseOrder) {
                realSlotStart = slotEnd - 1;
            } else {
                realSlotStart = slotStart;
            }

            while (!reverseOrder && realSlotStart < slotEnd || reverseOrder
                   && realSlotStart >= slotStart) {
                slot = (Slot) container.inventorySlots.get(realSlotStart);
                stackInSlot = slot.getStack();

                if (stackInSlot == null && slot.isItemValid(stackToMerge)) {
                    slot.putStack(stackToMerge.copy());
                    if (stackToMerge.stackSize > slot.getSlotStackLimit()) {
                        slot.getStack().stackSize = slot.getSlotStackLimit();
                        stackToMerge.stackSize -= slot.getSlotStackLimit();
                    } else {
                        stackToMerge.stackSize = 0;
                        stackMerged = true;
                    }
                    slot.onSlotChanged();
                    break;
                }

                if (reverseOrder) {
                    --realSlotStart;
                } else {
                    ++realSlotStart;
                }
            }
        }

        return stackMerged;
    }
}
