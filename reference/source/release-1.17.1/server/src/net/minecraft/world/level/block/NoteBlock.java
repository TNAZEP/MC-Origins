package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.phys.BlockHitResult;

public class NoteBlock extends Block {
   public static final EnumProperty<NoteBlockInstrument> INSTRUMENT = BlockStateProperties.NOTEBLOCK_INSTRUMENT;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final IntegerProperty NOTE = BlockStateProperties.NOTE;

   public NoteBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(INSTRUMENT, NoteBlockInstrument.HARP).setValue(NOTE, Integer.valueOf(0)).setValue(POWERED, Boolean.valueOf(false))
      );
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(INSTRUMENT, NoteBlockInstrument.byState(â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().below())));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ == Direction.DOWN ? â˜ƒ.setValue(INSTRUMENT, NoteBlockInstrument.byState(â˜ƒ)) : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      boolean â˜ƒ = â˜ƒ.hasNeighborSignal(â˜ƒ);
      if (â˜ƒ != â˜ƒ.getValue(POWERED)) {
         if (â˜ƒ) {
            this.playNote(â˜ƒ, â˜ƒ);
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒ)), 3);
      }
   }

   private void playNote(Level var1, BlockPos var2) {
      if (â˜ƒ.getBlockState(â˜ƒ.above()).isAir()) {
         â˜ƒ.blockEvent(â˜ƒ, this, 0, 0);
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         â˜ƒ = â˜ƒ.cycle(NOTE);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
         this.playNote(â˜ƒ, â˜ƒ);
         â˜ƒ.awardStat(Stats.TUNE_NOTEBLOCK);
         return InteractionResult.CONSUME;
      }
   }

   @Override
   public void attack(BlockState var1, Level var2, BlockPos var3, Player var4) {
      if (!â˜ƒ.isClientSide) {
         this.playNote(â˜ƒ, â˜ƒ);
         â˜ƒ.awardStat(Stats.PLAY_NOTEBLOCK);
      }
   }

   @Override
   public boolean triggerEvent(BlockState var1, Level var2, BlockPos var3, int var4, int var5) {
      int â˜ƒ = â˜ƒ.getValue(NOTE);
      float â˜ƒx = (float)Math.pow(2.0, (double)(â˜ƒ - 12) / 12.0);
      â˜ƒ.playSound(null, â˜ƒ, ((NoteBlockInstrument)â˜ƒ.getValue(INSTRUMENT)).getSoundEvent(), SoundSource.RECORDS, 3.0F, â˜ƒx);
      â˜ƒ.addParticle(ParticleTypes.NOTE, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 1.2, (double)â˜ƒ.getZ() + 0.5, (double)â˜ƒ / 24.0, 0.0, 0.0);
      return true;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(INSTRUMENT, POWERED, NOTE);
   }
}
