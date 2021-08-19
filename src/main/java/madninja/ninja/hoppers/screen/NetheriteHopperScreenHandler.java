package madninja.ninja.hoppers.screen;

import madninja.ninja.hoppers.Main;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class NetheriteHopperScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final Inventory filterInventory;
    private final Slot[] filterSlots;

    public NetheriteHopperScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, Inventory filterInventory) {
        super(Main.NETHERITE_HOPPER_SCREEN_HANDLER_TYPE, syncId);

        // Inventory
        this.inventory = inventory;
        //this passed for 5 - ive set it to 1 - eff knows
        //ScreenHandler.checkSize(this.inventory, 5);

        this.addSlot(new Slot(this.inventory,0,  8, 50));

        this.inventory.onOpen(playerInventory.player);

        // Filter
        this.filterInventory = filterInventory;

        //TODO: work out why this errors (all evidence points to it being 9)
        //ScreenHandler.checkSize(this.filterInventory, 9);
        filterSlots = new Slot[9];
        for (int i=0;i<9;i++) {
            this.filterSlots[0] = this.addSlot(new Slot(this.filterInventory, i, 8 + (i * 18), 19) {
                @Override
                public int getMaxItemCount() {
                    return 1;
                }
            });
        }

        this.filterInventory.onOpen(playerInventory.player);

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, column * 18 + 8, row * 18 + 83));
            }
        }

        // Hotbar
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, column * 18 + 8, 141));
        }
    }

    public NetheriteHopperScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1), new SimpleInventory(9));
        System.out.println("Screen handler");
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasStack()) return ItemStack.EMPTY;

        ItemStack slotStack = slot.getStack();
        ItemStack stack = slotStack.copy();

        if (index < this.inventory.size()) {
            if (!this.insertItem(slotStack, this.inventory.size() + 1, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else if (!this.insertItem(slotStack, 0, this.inventory.size(), false)) {
            return ItemStack.EMPTY;
        }

        if (slotStack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }

        return stack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);

        this.inventory.onClose(player);
        this.filterInventory.onClose(player);
    }
}