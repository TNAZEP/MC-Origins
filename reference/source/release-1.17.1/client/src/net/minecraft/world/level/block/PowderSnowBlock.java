package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PowderSnowBlock extends Block implements BucketPickup {
   private static final float HORIZONTAL_PARTICLE_MOMENTUM_FACTOR = 0.083333336F;
   private static final float IN_BLOCK_HORIZONTAL_SPEED_MULTIPLIER = 0.9F;
   private static final float IN_BLOCK_VERTICAL_SPEED_MULTIPLIER = 1.5F;
   private static final float NUM_BLOCKS_TO_FALL_INTO_BLOCK = 2.5F;
   private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0, 0.0, 0.0, 1.0, 0.9F, 1.0);

   public PowderSnowBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
      return â˜ƒ.is(this) ? true : super.skipRendering(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getOcclusionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return Shapes.empty();
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!(â˜ƒ instanceof LivingEntity) || â˜ƒ.getFeetBlockState().is(this)) {
         â˜ƒ.makeStuckInBlock(â˜ƒ, new Vec3(0.9F, 1.5, 0.9F));
         if (â˜ƒ.isClientSide) {
            Random â˜ƒ = â˜ƒ.getRandom();
            boolean â˜ƒx = â˜ƒ.xOld != â˜ƒ.getX() || â˜ƒ.zOld != â˜ƒ.getZ();
            if (â˜ƒx && â˜ƒ.nextBoolean()) {
               â˜ƒ.addParticle(
                  ParticleTypes.SNOWFLAKE,
                  â˜ƒ.getX(),
                  (double)(â˜ƒ.getY() + 1),
                  â˜ƒ.getZ(),
                  (double)(Mth.randomBetween(â˜ƒ, -1.0F, 1.0F) * 0.083333336F),
                  0.05F,
                  (double)(Mth.randomBetween(â˜ƒ, -1.0F, 1.0F) * 0.083333336F)
               );
            }
         }
      }

      â˜ƒ.setIsInPowderSnow(true);
      if (!â˜ƒ.isClientSide) {
         if (â˜ƒ.isOnFire() && (â˜ƒ.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || â˜ƒ instanceof Player) && â˜ƒ.mayInteract(â˜ƒ, â˜ƒ)) {
            â˜ƒ.destroyBlock(â˜ƒ, false);
         }

         â˜ƒ.setSharedFlagOnFire(false);
      }
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (â˜ƒ instanceof EntityCollisionContext â˜ƒ) {
         Optional<Entity> â˜ƒx = â˜ƒ.getEntity();
         if (â˜ƒx.isPresent()) {
            Entity â˜ƒxx = (Entity)â˜ƒx.get();
            if (â˜ƒxx.fallDistance > 2.5F) {
               return FALLING_COLLISION_SHAPE;
            }

            boolean â˜ƒxx = â˜ƒxx instanceof FallingBlockEntity;
            if (â˜ƒxx || canEntityWalkOnPowderSnow(â˜ƒxx) && â˜ƒ.isAbove(Shapes.block(), â˜ƒ, false) && !â˜ƒ.isDescending()) {
               return super.getCollisionShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }

      return Shapes.empty();
   }

   @Override
   public VoxelShape getVisualShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return Shapes.empty();
   }

   public static boolean canEntityWalkOnPowderSnow(Entity var0) {
      if (â˜ƒ.getType().is(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)) {
         return true;
      } else {
         return â˜ƒ instanceof LivingEntity ? ((LivingEntity)â˜ƒ).getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS) : false;
      }
   }

   @Override
   public ItemStack pickupBlock(LevelAccessor var1, BlockPos var2, BlockState var3) {
      â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 11);
      if (!â˜ƒ.isClientSide()) {
         â˜ƒ.levelEvent(2001, â˜ƒ, Block.getId(â˜ƒ));
      }

      return new ItemStack(Items.POWDER_SNOW_BUCKET);
   }

   @Override
   public Optional<SoundEvent> getPickupSound() {
      return Optional.of(SoundEvents.BUCKET_FILL_POWDER_SNOW);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return true;
   }
}
