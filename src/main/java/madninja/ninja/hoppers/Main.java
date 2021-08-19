package madninja.ninja.hoppers;

import madninja.ninja.hoppers.block.NetheriteHopperBlock;
import madninja.ninja.hoppers.block.entity.NetheriteHopperBlockEntity;
import madninja.ninja.hoppers.screen.NetheriteHopperScreenHandler;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {

	public static final String MOD_ID = "ninjahoppers";

	private static final Identifier NETHERITE_HOPPER_ID = new Identifier(MOD_ID, "netherite_hopper");
	public static final Block NETHERITE_HOPPER = new NetheriteHopperBlock(FabricBlockSettings.copyOf(Blocks.HOPPER).materialColor(MaterialColor.NETHER));
	public static final Item NETHERITE_HOPPER_ITEM = new BlockItem(NETHERITE_HOPPER, new Item.Settings().group(ItemGroup.REDSTONE));

	public static final BlockEntityType<NetheriteHopperBlockEntity> NETHERITE_HOPPER_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.create(NetheriteHopperBlockEntity::new, NETHERITE_HOPPER).build(null);
	public static final ScreenHandlerType<NetheriteHopperScreenHandler> NETHERITE_HOPPER_SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(NETHERITE_HOPPER_ID, NetheriteHopperScreenHandler::new);


	@Override
	public void onInitialize() {
		Registry.register(Registry.BLOCK, NETHERITE_HOPPER_ID, NETHERITE_HOPPER);
		Registry.register(Registry.ITEM, NETHERITE_HOPPER_ID, NETHERITE_HOPPER_ITEM);

		Registry.register(Registry.BLOCK_ENTITY_TYPE, NETHERITE_HOPPER_ID, NETHERITE_HOPPER_BLOCK_ENTITY_TYPE);
	}
}
