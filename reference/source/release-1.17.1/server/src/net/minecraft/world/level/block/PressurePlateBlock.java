package net.minecraft.world.level.block;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;

public class PressurePlateBlock extends BasePressurePlateBlock {
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   private final PressurePlateBlock.Sensitivity sensitivity;

   protected PressurePlateBlock(PressurePlateBlock.Sensitivity var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
      this.sensitivity = â˜ƒ;
   }

   @Override
   protected int getSignalForState(BlockState var1) {
      return â˜ƒ.getValue(POWERED) ? 15 : 0;
   }

   @Override
   protected BlockState setSignalForState(BlockState var1, int var2) {
      return â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒ > 0));
   }

   @Override
   protected void playOnSound(LevelAccessor var1, BlockPos var2) {
      if (this.material != Material.WOOD && this.material != Material.NETHER_WOOD) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.6F);
      } else {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.8F);
      }
   }

   @Override
   protected void playOffSound(LevelAccessor var1, BlockPos var2) {
      if (this.material != Material.WOOD && this.material != Material.NETHER_WOOD) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.5F);
      } else {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.7F);
      }
   }

   @Override
   protected int getSignalStrength(Level var1, BlockPos var2) {
      AABB â˜ƒx = TOUCH_AABB.move(â˜ƒ);
      List<? extends Entity> â˜ƒ;
      switch(this.sensitivity) {
         case EVERYTHING:
            â˜ƒ = â˜ƒ.getEntities(null, â˜ƒx);
            break;
         case MOBS:
            â˜ƒ = â˜ƒ.getEntitiesOfClass(LivingEntity.class, â˜ƒx);
            break;
         default:
            return 0;
      }

      if (!â˜ƒ.isEmpty()) {
         for(Entity â˜ƒ : â˜ƒ) {
            if (!â˜ƒ.isIgnoringBlockTriggers()) {
               return 15;
            }
         }
      }

      return 0;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(POWERED);
   }

   public static enum Sensitivity {
      EVERYTHING,
      MOBS;
   }
}
