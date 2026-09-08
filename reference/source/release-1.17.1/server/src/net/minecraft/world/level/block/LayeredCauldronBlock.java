package net.minecraft.world.level.block;

import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class LayeredCauldronBlock extends AbstractCauldronBlock {
   public static final int MIN_FILL_LEVEL = 1;
   public static final int MAX_FILL_LEVEL = 3;
   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;
   private static final int BASE_CONTENT_HEIGHT = 6;
   private static final double HEIGHT_PER_LEVEL = 3.0;
   public static final Predicate<Biome.Precipitation> RAIN = var0 -> var0 == Biome.Precipitation.RAIN;
   public static final Predicate<Biome.Precipitation> SNOW = var0 -> var0 == Biome.Precipitation.SNOW;
   private final Predicate<Biome.Precipitation> fillPredicate;

   public LayeredCauldronBlock(BlockBehaviour.Properties var1, Predicate<Biome.Precipitation> var2, Map<Item, CauldronInteraction> var3) {
      super(â˜ƒ, â˜ƒ);
      this.fillPredicate = â˜ƒ;
      this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(1)));
   }

   @Override
   public boolean isFull(BlockState var1) {
      return â˜ƒ.getValue(LEVEL) == 3;
   }

   @Override
   protected boolean canReceiveStalactiteDrip(Fluid var1) {
      return â˜ƒ == Fluids.WATER && this.fillPredicate == RAIN;
   }

   @Override
   protected double getContentHeight(BlockState var1) {
      return (6.0 + (double)((Integer)â˜ƒ.getValue(LEVEL)).intValue() * 3.0) / 16.0;
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.isClientSide && â˜ƒ.isOnFire() && this.isEntityInsideContent(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.clearFire();
         if (â˜ƒ.mayInteract(â˜ƒ, â˜ƒ)) {
            this.handleEntityOnFireInside(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   protected void handleEntityOnFireInside(BlockState var1, Level var2, BlockPos var3) {
      lowerFillLevel(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void lowerFillLevel(BlockState var0, Level var1, BlockPos var2) {
      int â˜ƒ = â˜ƒ.getValue(LEVEL) - 1;
      â˜ƒ.setBlockAndUpdate(â˜ƒ, â˜ƒ == 0 ? Blocks.CAULDRON.defaultBlockState() : â˜ƒ.setValue(LEVEL, Integer.valueOf(â˜ƒ)));
   }

   @Override
   public void handlePrecipitation(BlockState var1, Level var2, BlockPos var3, Biome.Precipitation var4) {
      if (CauldronBlock.shouldHandlePrecipitation(â˜ƒ, â˜ƒ) && â˜ƒ.getValue(LEVEL) != 3 && this.fillPredicate.test(â˜ƒ)) {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, â˜ƒ.cycle(LEVEL));
      }
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return â˜ƒ.getValue(LEVEL);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(LEVEL);
   }

   @Override
   protected void receiveStalactiteDrip(BlockState var1, Level var2, BlockPos var3, Fluid var4) {
      if (!this.isFull(â˜ƒ)) {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, â˜ƒ.setValue(LEVEL, Integer.valueOf(â˜ƒ.getValue(LEVEL) + 1)));
         â˜ƒ.levelEvent(1047, â˜ƒ, 0);
      }
   }
}
