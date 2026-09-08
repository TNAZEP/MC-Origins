package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SweetBerryBushBlock extends BushBlock implements BonemealableBlock {
   private static final float HURT_SPEED_THRESHOLD = 0.003F;
   public static final int MAX_AGE = 3;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
   private static final VoxelShape SAPLING_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
   private static final VoxelShape MID_GROWTH_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public SweetBerryBushBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return new ItemStack(Items.SWEET_BERRIES);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (â˜ƒ.getValue(AGE) == 0) {
         return SAPLING_SHAPE;
      } else {
         return â˜ƒ.getValue(AGE) < 3 ? MID_GROWTH_SHAPE : super.getShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return â˜ƒ.getValue(AGE) < 3;
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      int â˜ƒ = â˜ƒ.getValue(AGE);
      if (â˜ƒ < 3 && â˜ƒ.nextInt(5) == 0 && â˜ƒ.getRawBrightness(â˜ƒ.above(), 0) >= 9) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒ + 1)), 2);
      }
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (â˜ƒ instanceof LivingEntity && â˜ƒ.getType() != EntityType.FOX && â˜ƒ.getType() != EntityType.BEE) {
         â˜ƒ.makeStuckInBlock(â˜ƒ, new Vec3(0.8F, 0.75, 0.8F));
         if (!â˜ƒ.isClientSide && â˜ƒ.getValue(AGE) > 0 && (â˜ƒ.xOld != â˜ƒ.getX() || â˜ƒ.zOld != â˜ƒ.getZ())) {
            double â˜ƒ = Math.abs(â˜ƒ.getX() - â˜ƒ.xOld);
            double â˜ƒx = Math.abs(â˜ƒ.getZ() - â˜ƒ.zOld);
            if (â˜ƒ >= 0.003F || â˜ƒx >= 0.003F) {
               â˜ƒ.hurt(DamageSource.SWEET_BERRY_BUSH, 1.0F);
            }
         }
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      int â˜ƒ = â˜ƒ.getValue(AGE);
      boolean â˜ƒx = â˜ƒ == 3;
      if (!â˜ƒx && â˜ƒ.getItemInHand(â˜ƒ).is(Items.BONE_MEAL)) {
         return InteractionResult.PASS;
      } else if (â˜ƒ > 1) {
         int â˜ƒ = 1 + â˜ƒ.random.nextInt(2);
         popResource(â˜ƒ, â˜ƒ, new ItemStack(Items.SWEET_BERRIES, â˜ƒ + (â˜ƒx ? 1 : 0)));
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + â˜ƒ.random.nextFloat() * 0.4F);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(AGE, Integer.valueOf(1)), 2);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return super.use(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE);
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return â˜ƒ.getValue(AGE) < 3;
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      int â˜ƒ = Math.min(3, â˜ƒ.getValue(AGE) + 1);
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒ)), 2);
   }
}
