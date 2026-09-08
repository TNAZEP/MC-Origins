package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WaterlilyBlock extends BushBlock {
   protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

   protected WaterlilyBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      super.entityInside(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ instanceof ServerLevel && â˜ƒ instanceof Boat) {
         â˜ƒ.destroyBlock(new BlockPos(â˜ƒ), true, â˜ƒ);
      }
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return AABB;
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      FluidState â˜ƒ = â˜ƒ.getFluidState(â˜ƒ);
      FluidState â˜ƒx = â˜ƒ.getFluidState(â˜ƒ.above());
      return (â˜ƒ.getType() == Fluids.WATER || â˜ƒ.getMaterial() == Material.ICE) && â˜ƒx.getType() == Fluids.EMPTY;
   }
}
