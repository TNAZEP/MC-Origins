package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DaylightDetectorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DaylightDetectorBlock extends BaseEntityBlock {
   public static final IntegerProperty POWER = BlockStateProperties.POWER;
   public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;
   protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);

   public DaylightDetectorBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(POWER, Integer.valueOf(0)).setValue(INVERTED, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return true;
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWER);
   }

   private static void updateSignalStrength(BlockState var0, Level var1, BlockPos var2) {
      int â˜ƒ = â˜ƒ.getBrightness(LightLayer.SKY, â˜ƒ) - â˜ƒ.getSkyDarken();
      float â˜ƒx = â˜ƒ.getSunAngle(1.0F);
      boolean â˜ƒxx = â˜ƒ.getValue(INVERTED);
      if (â˜ƒxx) {
         â˜ƒ = 15 - â˜ƒ;
      } else if (â˜ƒ > 0) {
         float â˜ƒ = â˜ƒx < (float) Math.PI ? 0.0F : (float) (Math.PI * 2);
         â˜ƒx += (â˜ƒ - â˜ƒx) * 0.2F;
         â˜ƒ = Math.round((float)â˜ƒ * Mth.cos(â˜ƒx));
      }

      â˜ƒ = Mth.clamp(â˜ƒ, 0, 15);
      if (â˜ƒ.getValue(POWER) != â˜ƒ) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWER, Integer.valueOf(â˜ƒ)), 3);
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.mayBuild()) {
         if (â˜ƒ.isClientSide) {
            return InteractionResult.SUCCESS;
         } else {
            BlockState â˜ƒ = â˜ƒ.cycle(INVERTED);
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 4);
            updateSignalStrength(â˜ƒ, â˜ƒ, â˜ƒ);
            return InteractionResult.CONSUME;
         }
      } else {
         return super.use(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new DaylightDetectorBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return !â˜ƒ.isClientSide && â˜ƒ.dimensionType().hasSkyLight()
         ? createTickerHelper(â˜ƒ, BlockEntityType.DAYLIGHT_DETECTOR, DaylightDetectorBlock::tickEntity)
         : null;
   }

   private static void tickEntity(Level var0, BlockPos var1, BlockState var2, DaylightDetectorBlockEntity var3) {
      if (â˜ƒ.getGameTime() % 20L == 0L) {
         updateSignalStrength(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(POWER, INVERTED);
   }
}
