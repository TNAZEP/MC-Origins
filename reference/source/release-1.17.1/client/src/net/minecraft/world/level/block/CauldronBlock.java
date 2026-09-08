package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class CauldronBlock extends AbstractCauldronBlock {
   private static final float RAIN_FILL_CHANCE = 0.05F;
   private static final float POWDER_SNOW_FILL_CHANCE = 0.1F;

   public CauldronBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ, CauldronInteraction.EMPTY);
   }

   @Override
   public boolean isFull(BlockState var1) {
      return false;
   }

   protected static boolean shouldHandlePrecipitation(Level var0, Biome.Precipitation var1) {
      if (â˜ƒ == Biome.Precipitation.RAIN) {
         return â˜ƒ.getRandom().nextFloat() < 0.05F;
      } else if (â˜ƒ == Biome.Precipitation.SNOW) {
         return â˜ƒ.getRandom().nextFloat() < 0.1F;
      } else {
         return false;
      }
   }

   @Override
   public void handlePrecipitation(BlockState var1, Level var2, BlockPos var3, Biome.Precipitation var4) {
      if (shouldHandlePrecipitation(â˜ƒ, â˜ƒ)) {
         if (â˜ƒ == Biome.Precipitation.RAIN) {
            â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.WATER_CAULDRON.defaultBlockState());
            â˜ƒ.gameEvent(null, GameEvent.FLUID_PLACE, â˜ƒ);
         } else if (â˜ƒ == Biome.Precipitation.SNOW) {
            â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.POWDER_SNOW_CAULDRON.defaultBlockState());
            â˜ƒ.gameEvent(null, GameEvent.FLUID_PLACE, â˜ƒ);
         }
      }
   }

   @Override
   protected boolean canReceiveStalactiteDrip(Fluid var1) {
      return true;
   }

   @Override
   protected void receiveStalactiteDrip(BlockState var1, Level var2, BlockPos var3, Fluid var4) {
      if (â˜ƒ == Fluids.WATER) {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.WATER_CAULDRON.defaultBlockState());
         â˜ƒ.levelEvent(1047, â˜ƒ, 0);
         â˜ƒ.gameEvent(null, GameEvent.FLUID_PLACE, â˜ƒ);
      } else if (â˜ƒ == Fluids.LAVA) {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.LAVA_CAULDRON.defaultBlockState());
         â˜ƒ.levelEvent(1046, â˜ƒ, 0);
         â˜ƒ.gameEvent(null, GameEvent.FLUID_PLACE, â˜ƒ);
      }
   }
}
