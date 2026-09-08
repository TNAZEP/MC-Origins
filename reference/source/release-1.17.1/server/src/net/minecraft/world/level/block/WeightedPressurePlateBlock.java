package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class WeightedPressurePlateBlock extends BasePressurePlateBlock {
   public static final IntegerProperty POWER = BlockStateProperties.POWER;
   private final int maxWeight;

   protected WeightedPressurePlateBlock(int var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(POWER, Integer.valueOf(0)));
      this.maxWeight = â˜ƒ;
   }

   @Override
   protected int getSignalStrength(Level var1, BlockPos var2) {
      int â˜ƒ = Math.min(â˜ƒ.getEntitiesOfClass(Entity.class, TOUCH_AABB.move(â˜ƒ)).size(), this.maxWeight);
      if (â˜ƒ > 0) {
         float â˜ƒx = (float)Math.min(this.maxWeight, â˜ƒ) / (float)this.maxWeight;
         return Mth.ceil(â˜ƒx * 15.0F);
      } else {
         return 0;
      }
   }

   @Override
   protected void playOnSound(LevelAccessor var1, BlockPos var2) {
      â˜ƒ.playSound(null, â˜ƒ, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.90000004F);
   }

   @Override
   protected void playOffSound(LevelAccessor var1, BlockPos var2) {
      â˜ƒ.playSound(null, â˜ƒ, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.75F);
   }

   @Override
   protected int getSignalForState(BlockState var1) {
      return â˜ƒ.getValue(POWER);
   }

   @Override
   protected BlockState setSignalForState(BlockState var1, int var2) {
      return â˜ƒ.setValue(POWER, Integer.valueOf(â˜ƒ));
   }

   @Override
   protected int getPressedTime() {
      return 10;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(POWER);
   }
}
