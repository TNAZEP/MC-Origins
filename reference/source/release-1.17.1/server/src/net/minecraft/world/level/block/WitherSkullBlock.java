package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.material.Material;

public class WitherSkullBlock extends SkullBlock {
   @Nullable
   private static BlockPattern witherPatternFull;
   @Nullable
   private static BlockPattern witherPatternBase;

   protected WitherSkullBlock(BlockBehaviour.Properties var1) {
      super(SkullBlock.Types.WITHER_SKELETON, â˜ƒ);
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, @Nullable LivingEntity var4, ItemStack var5) {
      super.setPlacedBy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof SkullBlockEntity) {
         checkSpawn(â˜ƒ, â˜ƒ, (SkullBlockEntity)â˜ƒ);
      }
   }

   public static void checkSpawn(Level var0, BlockPos var1, SkullBlockEntity var2) {
      if (!â˜ƒ.isClientSide) {
         BlockState â˜ƒ = â˜ƒ.getBlockState();
         boolean â˜ƒx = â˜ƒ.is(Blocks.WITHER_SKELETON_SKULL) || â˜ƒ.is(Blocks.WITHER_SKELETON_WALL_SKULL);
         if (â˜ƒx && â˜ƒ.getY() >= â˜ƒ.getMinBuildHeight() && â˜ƒ.getDifficulty() != Difficulty.PEACEFUL) {
            BlockPattern â˜ƒxx = getOrCreateWitherFull();
            BlockPattern.BlockPatternMatch â˜ƒxxx = â˜ƒxx.find(â˜ƒ, â˜ƒ);
            if (â˜ƒxxx != null) {
               for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxx.getWidth(); ++â˜ƒxxxx) {
                  for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxx.getHeight(); ++â˜ƒxxxxx) {
                     BlockInWorld â˜ƒxxxxxx = â˜ƒxxx.getBlock(â˜ƒxxxx, â˜ƒxxxxx, 0);
                     â˜ƒ.setBlock(â˜ƒxxxxxx.getPos(), Blocks.AIR.defaultBlockState(), 2);
                     â˜ƒ.levelEvent(2001, â˜ƒxxxxxx.getPos(), Block.getId(â˜ƒxxxxxx.getState()));
                  }
               }

               WitherBoss â˜ƒxxxx = EntityType.WITHER.create(â˜ƒ);
               BlockPos â˜ƒxxxxx = â˜ƒxxx.getBlock(1, 2, 0).getPos();
               â˜ƒxxxx.moveTo(
                  (double)â˜ƒxxxxx.getX() + 0.5,
                  (double)â˜ƒxxxxx.getY() + 0.55,
                  (double)â˜ƒxxxxx.getZ() + 0.5,
                  â˜ƒxxx.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F,
                  0.0F
               );
               â˜ƒxxxx.yBodyRot = â˜ƒxxx.getForwards().getAxis() == Direction.Axis.X ? 0.0F : 90.0F;
               â˜ƒxxxx.makeInvulnerable();

               for(ServerPlayer â˜ƒxxxxxx : â˜ƒ.getEntitiesOfClass(ServerPlayer.class, â˜ƒxxxx.getBoundingBox().inflate(50.0))) {
                  CriteriaTriggers.SUMMONED_ENTITY.trigger(â˜ƒxxxxxx, â˜ƒxxxx);
               }

               â˜ƒ.addFreshEntity(â˜ƒxxxx);

               for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxx.getWidth(); ++â˜ƒxxxxxx) {
                  for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxx.getHeight(); ++â˜ƒxxxxxxx) {
                     â˜ƒ.blockUpdated(â˜ƒxxx.getBlock(â˜ƒxxxxxx, â˜ƒxxxxxxx, 0).getPos(), Blocks.AIR);
                  }
               }
            }
         }
      }
   }

   public static boolean canSpawnMob(Level var0, BlockPos var1, ItemStack var2) {
      if (â˜ƒ.is(Items.WITHER_SKELETON_SKULL) && â˜ƒ.getY() >= â˜ƒ.getMinBuildHeight() + 2 && â˜ƒ.getDifficulty() != Difficulty.PEACEFUL && !â˜ƒ.isClientSide) {
         return getOrCreateWitherBase().find(â˜ƒ, â˜ƒ) != null;
      } else {
         return false;
      }
   }

   private static BlockPattern getOrCreateWitherFull() {
      if (witherPatternFull == null) {
         witherPatternFull = BlockPatternBuilder.start()
            .aisle("^^^", "###", "~#~")
            .where('#', var0 -> var0.getState().is(BlockTags.WITHER_SUMMON_BASE_BLOCKS))
            .where(
               '^',
               BlockInWorld.hasState(
                  BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_SKULL).or(BlockStatePredicate.forBlock(Blocks.WITHER_SKELETON_WALL_SKULL))
               )
            )
            .where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
            .build();
      }

      return witherPatternFull;
   }

   private static BlockPattern getOrCreateWitherBase() {
      if (witherPatternBase == null) {
         witherPatternBase = BlockPatternBuilder.start()
            .aisle("   ", "###", "~#~")
            .where('#', var0 -> var0.getState().is(BlockTags.WITHER_SUMMON_BASE_BLOCKS))
            .where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
            .build();
      }

      return witherPatternBase;
   }
}
