package net.minecraft.world.level.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class SignBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final float AABB_OFFSET = 4.0F;
   protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   private final WoodType type;

   protected SignBlock(BlockBehaviour.Properties var1, WoodType var2) {
      super(â˜ƒ);
      this.type = â˜ƒ;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public boolean isPossibleToRespawnInThis() {
      return true;
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new SignBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      Item â˜ƒx = â˜ƒ.getItem();
      boolean â˜ƒxx = â˜ƒx instanceof DyeItem;
      boolean â˜ƒxxx = â˜ƒ.is(Items.GLOW_INK_SAC);
      boolean â˜ƒxxxx = â˜ƒ.is(Items.INK_SAC);
      boolean â˜ƒxxxxx = (â˜ƒxxx || â˜ƒxx || â˜ƒxxxx) && â˜ƒ.getAbilities().mayBuild;
      if (â˜ƒ.isClientSide) {
         return â˜ƒxxxxx ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
      } else {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (!(â˜ƒ instanceof SignBlockEntity)) {
            return InteractionResult.PASS;
         } else {
            SignBlockEntity â˜ƒ = (SignBlockEntity)â˜ƒ;
            boolean â˜ƒx = â˜ƒ.hasGlowingText();
            if ((!â˜ƒxxx || !â˜ƒx) && (!â˜ƒxxxx || â˜ƒx)) {
               if (â˜ƒxxxxx) {
                  boolean â˜ƒxx;
                  if (â˜ƒxxx) {
                     â˜ƒ.playSound(null, â˜ƒ, SoundEvents.GLOW_INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                     â˜ƒxx = â˜ƒ.setHasGlowingText(true);
                     if (â˜ƒ instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)â˜ƒ, â˜ƒ, â˜ƒ);
                     }
                  } else if (â˜ƒxxxx) {
                     â˜ƒ.playSound(null, â˜ƒ, SoundEvents.INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                     â˜ƒxx = â˜ƒ.setHasGlowingText(false);
                  } else {
                     â˜ƒ.playSound(null, â˜ƒ, SoundEvents.DYE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                     â˜ƒxx = â˜ƒ.setColor(((DyeItem)â˜ƒx).getDyeColor());
                  }

                  if (â˜ƒxx) {
                     if (!â˜ƒ.isCreative()) {
                        â˜ƒ.shrink(1);
                     }

                     â˜ƒ.awardStat(Stats.ITEM_USED.get(â˜ƒx));
                  }
               }

               return â˜ƒ.executeClickCommands((ServerPlayer)â˜ƒ) ? InteractionResult.SUCCESS : InteractionResult.PASS;
            } else {
               return InteractionResult.PASS;
            }
         }
      }
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   public WoodType type() {
      return this.type;
   }
}
