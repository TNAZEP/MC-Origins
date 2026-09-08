package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EndPortalBlock extends BaseEntityBlock {
   protected static final VoxelShape SHAPE = Block.box(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);

   protected EndPortalBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new TheEndPortalBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (â˜ƒ instanceof ServerLevel
         && !â˜ƒ.isPassenger()
         && !â˜ƒ.isVehicle()
         && â˜ƒ.canChangeDimensions()
         && Shapes.joinIsNotEmpty(
            Shapes.create(â˜ƒ.getBoundingBox().move((double)(-â˜ƒ.getX()), (double)(-â˜ƒ.getY()), (double)(-â˜ƒ.getZ()))),
            â˜ƒ.getShape(â˜ƒ, â˜ƒ),
            BooleanOp.AND
         )) {
         ResourceKey<Level> â˜ƒ = â˜ƒ.dimension() == Level.END ? Level.OVERWORLD : Level.END;
         ServerLevel â˜ƒx = ((ServerLevel)â˜ƒ).getServer().getLevel(â˜ƒ);
         if (â˜ƒx == null) {
            return;
         }

         â˜ƒ.changeDimension(â˜ƒx);
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      double â˜ƒ = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
      double â˜ƒx = (double)â˜ƒ.getY() + 0.8;
      double â˜ƒxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
      â˜ƒ.addParticle(ParticleTypes.SMOKE, â˜ƒ, â˜ƒx, â˜ƒxx, 0.0, 0.0, 0.0);
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return ItemStack.EMPTY;
   }

   @Override
   public boolean canBeReplaced(BlockState var1, Fluid var2) {
      return false;
   }
}
