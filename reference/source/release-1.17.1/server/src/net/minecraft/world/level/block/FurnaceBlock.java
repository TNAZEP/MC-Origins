package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FurnaceBlock extends AbstractFurnaceBlock {
   protected FurnaceBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new FurnaceBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return createFurnaceTicker(â˜ƒ, â˜ƒ, BlockEntityType.FURNACE);
   }

   @Override
   protected void openContainer(Level var1, BlockPos var2, Player var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof FurnaceBlockEntity) {
         â˜ƒ.openMenu((MenuProvider)â˜ƒ);
         â˜ƒ.awardStat(Stats.INTERACT_WITH_FURNACE);
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LIT)) {
         double â˜ƒ = (double)â˜ƒ.getX() + 0.5;
         double â˜ƒx = (double)â˜ƒ.getY();
         double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5;
         if (â˜ƒ.nextDouble() < 0.1) {
            â˜ƒ.playLocalSound(â˜ƒ, â˜ƒx, â˜ƒxx, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         }

         Direction â˜ƒ = â˜ƒ.getValue(FACING);
         Direction.Axis â˜ƒx = â˜ƒ.getAxis();
         double â˜ƒxx = 0.52;
         double â˜ƒxxx = â˜ƒ.nextDouble() * 0.6 - 0.3;
         double â˜ƒxxxx = â˜ƒx == Direction.Axis.X ? (double)â˜ƒ.getStepX() * 0.52 : â˜ƒxxx;
         double â˜ƒxxxxx = â˜ƒ.nextDouble() * 6.0 / 16.0;
         double â˜ƒxxxxxx = â˜ƒx == Direction.Axis.Z ? (double)â˜ƒ.getStepZ() * 0.52 : â˜ƒxxx;
         â˜ƒ.addParticle(ParticleTypes.SMOKE, â˜ƒ + â˜ƒxxxx, â˜ƒx + â˜ƒxxxxx, â˜ƒxx + â˜ƒxxxxxx, 0.0, 0.0, 0.0);
         â˜ƒ.addParticle(ParticleTypes.FLAME, â˜ƒ + â˜ƒxxxx, â˜ƒx + â˜ƒxxxxx, â˜ƒxx + â˜ƒxxxxxx, 0.0, 0.0, 0.0);
      }
   }
}
