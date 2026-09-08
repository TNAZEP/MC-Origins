package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NetherPortalBlock extends Block {
   public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
   protected static final int AABB_OFFSET = 2;
   protected static final VoxelShape X_AXIS_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

   public NetherPortalBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      switch((Direction.Axis)â˜ƒ.getValue(AXIS)) {
         case Z:
            return Z_AXIS_AABB;
         case X:
         default:
            return X_AXIS_AABB;
      }
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.dimensionType().natural() && â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && â˜ƒ.nextInt(2000) < â˜ƒ.getDifficulty().getId()) {
         while(â˜ƒ.getBlockState(â˜ƒ).is(this)) {
            â˜ƒ = â˜ƒ.below();
         }

         if (â˜ƒ.getBlockState(â˜ƒ).isValidSpawn(â˜ƒ, â˜ƒ, EntityType.ZOMBIFIED_PIGLIN)) {
            Entity â˜ƒ = EntityType.ZOMBIFIED_PIGLIN.spawn(â˜ƒ, null, null, null, â˜ƒ.above(), MobSpawnType.STRUCTURE, false, false);
            if (â˜ƒ != null) {
               â˜ƒ.setPortalCooldown();
            }
         }
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      Direction.Axis â˜ƒ = â˜ƒ.getAxis();
      Direction.Axis â˜ƒx = â˜ƒ.getValue(AXIS);
      boolean â˜ƒxx = â˜ƒx != â˜ƒ && â˜ƒ.isHorizontal();
      return !â˜ƒxx && !â˜ƒ.is(this) && !new PortalShape(â˜ƒ, â˜ƒ, â˜ƒx).isComplete()
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.isPassenger() && !â˜ƒ.isVehicle() && â˜ƒ.canChangeDimensions()) {
         â˜ƒ.handleInsidePortal(â˜ƒ);
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.nextInt(100) == 0) {
         â˜ƒ.playLocalSound(
            (double)â˜ƒ.getX() + 0.5,
            (double)â˜ƒ.getY() + 0.5,
            (double)â˜ƒ.getZ() + 0.5,
            SoundEvents.PORTAL_AMBIENT,
            SoundSource.BLOCKS,
            0.5F,
            â˜ƒ.nextFloat() * 0.4F + 0.8F,
            false
         );
      }

      for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
         double â˜ƒx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
         double â˜ƒxx = (double)â˜ƒ.getY() + â˜ƒ.nextDouble();
         double â˜ƒxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
         double â˜ƒxxxx = ((double)â˜ƒ.nextFloat() - 0.5) * 0.5;
         double â˜ƒxxxxx = ((double)â˜ƒ.nextFloat() - 0.5) * 0.5;
         double â˜ƒxxxxxx = ((double)â˜ƒ.nextFloat() - 0.5) * 0.5;
         int â˜ƒxxxxxxx = â˜ƒ.nextInt(2) * 2 - 1;
         if (!â˜ƒ.getBlockState(â˜ƒ.west()).is(this) && !â˜ƒ.getBlockState(â˜ƒ.east()).is(this)) {
            â˜ƒx = (double)â˜ƒ.getX() + 0.5 + 0.25 * (double)â˜ƒxxxxxxx;
            â˜ƒxxxx = (double)(â˜ƒ.nextFloat() * 2.0F * (float)â˜ƒxxxxxxx);
         } else {
            â˜ƒxxx = (double)â˜ƒ.getZ() + 0.5 + 0.25 * (double)â˜ƒxxxxxxx;
            â˜ƒxxxxxx = (double)(â˜ƒ.nextFloat() * 2.0F * (float)â˜ƒxxxxxxx);
         }

         â˜ƒ.addParticle(ParticleTypes.PORTAL, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
      }
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return ItemStack.EMPTY;
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      switch(â˜ƒ) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch((Direction.Axis)â˜ƒ.getValue(AXIS)) {
               case Z:
                  return â˜ƒ.setValue(AXIS, Direction.Axis.X);
               case X:
                  return â˜ƒ.setValue(AXIS, Direction.Axis.Z);
               default:
                  return â˜ƒ;
            }
         default:
            return â˜ƒ;
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AXIS);
   }
}
