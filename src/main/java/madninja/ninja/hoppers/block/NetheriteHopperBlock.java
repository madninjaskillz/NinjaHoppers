package madninja.ninja.hoppers.block;

import madninja.ninja.hoppers.Main;
import madninja.ninja.hoppers.block.entity.NetheriteHopperBlockEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class NetheriteHopperBlock extends HopperBlock {
    public NetheriteHopperBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof NetheriteHopperBlockEntity) {
                ((NetheriteHopperBlockEntity) blockEntity).scatterFilter();
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new NetheriteHopperBlockEntity();
    }
}