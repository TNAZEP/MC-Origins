package net.minecraft.world.level.block;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;

public class CarvedPumpkinBlock extends HorizontalDirectionalBlock implements Wearable {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   @Nullable
   private BlockPattern snowGolemBase;
   @Nullable
   private BlockPattern snowGolemFull;
   @Nullable
   private BlockPattern ironGolemBase;
   @Nullable
   private BlockPattern ironGolemFull;
   private static final Predicate<BlockState> PUMPKINS_PREDICATE = var0 -> var0 != null && (var0.is(Blocks.CARVED_PUMPKIN) || var0.is(Blocks.JACK_O_LANTERN));

   protected CarvedPumpkinBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         this.trySpawnGolem(â˜ƒ, â˜ƒ);
      }
   }

   public boolean canSpawnGolem(LevelReader var1, BlockPos var2) {
      return this.getOrCreateSnowGolemBase().find(â˜ƒ, â˜ƒ) != null || this.getOrCreateIronGolemBase().find(â˜ƒ, â˜ƒ) != null;
   }

   private void trySpawnGolem(Level var1, BlockPos var2) {
      BlockPattern.BlockPatternMatch â˜ƒ = this.getOrCreateSnowGolemFull().find(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         for(int â˜ƒx = 0; â˜ƒx < this.getOrCreateSnowGolemFull().getHeight(); ++â˜ƒx) {
            BlockInWorld â˜ƒxx = â˜ƒ.getBlock(0, â˜ƒx, 0);
            â˜ƒ.setBlock(â˜ƒxx.getPos(), Blocks.AIR.defaultBlockState(), 2);
            â˜ƒ.levelEvent(2001, â˜ƒxx.getPos(), Block.getId(â˜ƒxx.getState()));
         }

         SnowGolem â˜ƒx = EntityType.SNOW_GOLEM.create(â˜ƒ);
         BlockPos â˜ƒxx = â˜ƒ.getBlock(0, 2, 0).getPos();
         â˜ƒx.moveTo((double)â˜ƒxx.getX() + 0.5, (double)â˜ƒxx.getY() + 0.05, (double)â˜ƒxx.getZ() + 0.5, 0.0F, 0.0F);
         â˜ƒ.addFreshEntity(â˜ƒx);

         for(ServerPlayer â˜ƒxxx : â˜ƒ.getEntitiesOfClass(ServerPlayer.class, â˜ƒx.getBoundingBox().inflate(5.0))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(â˜ƒxxx, â˜ƒx);
         }

         for(int â˜ƒxxx = 0; â˜ƒxxx < this.getOrCreateSnowGolemFull().getHeight(); ++â˜ƒxxx) {
            BlockInWorld â˜ƒxxxx = â˜ƒ.getBlock(0, â˜ƒxxx, 0);
            â˜ƒ.blockUpdated(â˜ƒxxxx.getPos(), Blocks.AIR);
         }
      } else {
         â˜ƒ = this.getOrCreateIronGolemFull().find(â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            for(int â˜ƒ = 0; â˜ƒ < this.getOrCreateIronGolemFull().getWidth(); ++â˜ƒ) {
               for(int â˜ƒx = 0; â˜ƒx < this.getOrCreateIronGolemFull().getHeight(); ++â˜ƒx) {
                  BlockInWorld â˜ƒxx = â˜ƒ.getBlock(â˜ƒ, â˜ƒx, 0);
                  â˜ƒ.setBlock(â˜ƒxx.getPos(), Blocks.AIR.defaultBlockState(), 2);
                  â˜ƒ.levelEvent(2001, â˜ƒxx.getPos(), Block.getId(â˜ƒxx.getState()));
               }
            }

            BlockPos â˜ƒ = â˜ƒ.getBlock(1, 2, 0).getPos();
            IronGolem â˜ƒx = EntityType.IRON_GOLEM.create(â˜ƒ);
            â˜ƒx.setPlayerCreated(true);
            â˜ƒx.moveTo((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.05, (double)â˜ƒ.getZ() + 0.5, 0.0F, 0.0F);
            â˜ƒ.addFreshEntity(â˜ƒx);

            for(ServerPlayer â˜ƒxx : â˜ƒ.getEntitiesOfClass(ServerPlayer.class, â˜ƒx.getBoundingBox().inflate(5.0))) {
               CriteriaTriggers.SUMMONED_ENTITY.trigger(â˜ƒxx, â˜ƒx);
            }

            for(int â˜ƒxx = 0; â˜ƒxx < this.getOrCreateIronGolemFull().getWidth(); ++â˜ƒxx) {
               for(int â˜ƒxxx = 0; â˜ƒxxx < this.getOrCreateIronGolemFull().getHeight(); ++â˜ƒxxx) {
                  BlockInWorld â˜ƒxxxx = â˜ƒ.getBlock(â˜ƒxx, â˜ƒxxx, 0);
                  â˜ƒ.blockUpdated(â˜ƒxxxx.getPos(), Blocks.AIR);
               }
            }
         }
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getHorizontalDirection().getOpposite());
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING);
   }

   private BlockPattern getOrCreateSnowGolemBase() {
      if (this.snowGolemBase == null) {
         this.snowGolemBase = BlockPatternBuilder.start()
            .aisle(" ", "#", "#")
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
            .build();
      }

      return this.snowGolemBase;
   }

   private BlockPattern getOrCreateSnowGolemFull() {
      if (this.snowGolemFull == null) {
         this.snowGolemFull = BlockPatternBuilder.start()
            .aisle("^", "#", "#")
            .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
            .build();
      }

      return this.snowGolemFull;
   }

   private BlockPattern getOrCreateIronGolemBase() {
      if (this.ironGolemBase == null) {
         this.ironGolemBase = BlockPatternBuilder.start()
            .aisle("~ ~", "###", "~#~")
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
            .where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
            .build();
      }

      return this.ironGolemBase;
   }

   private BlockPattern getOrCreateIronGolemFull() {
      if (this.ironGolemFull == null) {
         this.ironGolemFull = BlockPatternBuilder.start()
            .aisle("~^~", "###", "~#~")
            .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
            .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
            .where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
            .build();
      }

      return this.ironGolemFull;
   }
}
