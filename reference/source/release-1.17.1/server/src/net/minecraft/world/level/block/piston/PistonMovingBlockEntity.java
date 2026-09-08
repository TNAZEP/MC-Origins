package net.minecraft.world.level.block.piston;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PistonMovingBlockEntity extends BlockEntity {
   private static final int TICKS_TO_EXTEND = 2;
   private static final double PUSH_OFFSET = 0.01;
   public static final double TICK_MOVEMENT = 0.51;
   private BlockState movedState;
   private Direction direction;
   private boolean extending;
   private boolean isSourcePiston;
   private static final ThreadLocal<Direction> NOCLIP = ThreadLocal.withInitial(() -> null);
   private float progress;
   private float progressO;
   private long lastTicked;
   private int deathTicks;

   public PistonMovingBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.PISTON, â˜ƒ, â˜ƒ);
   }

   public PistonMovingBlockEntity(BlockPos var1, BlockState var2, BlockState var3, Direction var4, boolean var5, boolean var6) {
      this(â˜ƒ, â˜ƒ);
      this.movedState = â˜ƒ;
      this.direction = â˜ƒ;
      this.extending = â˜ƒ;
      this.isSourcePiston = â˜ƒ;
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   public boolean isExtending() {
      return this.extending;
   }

   public Direction getDirection() {
      return this.direction;
   }

   public boolean isSourcePiston() {
      return this.isSourcePiston;
   }

   public float getProgress(float var1) {
      if (â˜ƒ > 1.0F) {
         â˜ƒ = 1.0F;
      }

      return Mth.lerp(â˜ƒ, this.progressO, this.progress);
   }

   public float getXOff(float var1) {
      return (float)this.direction.getStepX() * this.getExtendedProgress(this.getProgress(â˜ƒ));
   }

   public float getYOff(float var1) {
      return (float)this.direction.getStepY() * this.getExtendedProgress(this.getProgress(â˜ƒ));
   }

   public float getZOff(float var1) {
      return (float)this.direction.getStepZ() * this.getExtendedProgress(this.getProgress(â˜ƒ));
   }

   private float getExtendedProgress(float var1) {
      return this.extending ? â˜ƒ - 1.0F : 1.0F - â˜ƒ;
   }

   private BlockState getCollisionRelatedBlockState() {
      return !this.isExtending() && this.isSourcePiston() && this.movedState.getBlock() instanceof PistonBaseBlock
         ? Blocks.PISTON_HEAD
            .defaultBlockState()
            .setValue(PistonHeadBlock.SHORT, Boolean.valueOf(this.progress > 0.25F))
            .setValue(PistonHeadBlock.TYPE, this.movedState.is(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT)
            .setValue(PistonHeadBlock.FACING, (Direction)this.movedState.getValue(PistonBaseBlock.FACING))
         : this.movedState;
   }

   private static void moveCollidedEntities(Level var0, BlockPos var1, float var2, PistonMovingBlockEntity var3) {
      Direction â˜ƒ = â˜ƒ.getMovementDirection();
      double â˜ƒx = (double)(â˜ƒ - â˜ƒ.progress);
      VoxelShape â˜ƒxx = â˜ƒ.getCollisionRelatedBlockState().getCollisionShape(â˜ƒ, â˜ƒ);
      if (!â˜ƒxx.isEmpty()) {
         AABB â˜ƒxxx = moveByPositionAndProgress(â˜ƒ, â˜ƒxx.bounds(), â˜ƒ);
         List<Entity> â˜ƒxxxx = â˜ƒ.getEntities(null, PistonMath.getMovementArea(â˜ƒxxx, â˜ƒ, â˜ƒx).minmax(â˜ƒxxx));
         if (!â˜ƒxxxx.isEmpty()) {
            List<AABB> â˜ƒxxxxx = â˜ƒxx.toAabbs();
            boolean â˜ƒxxxxxx = â˜ƒ.movedState.is(Blocks.SLIME_BLOCK);
            Iterator var12 = â˜ƒxxxx.iterator();

            while(true) {
               Entity â˜ƒ;
               while(true) {
                  if (!var12.hasNext()) {
                     return;
                  }

                  â˜ƒ = (Entity)var12.next();
                  if (â˜ƒ.getPistonPushReaction() != PushReaction.IGNORE) {
                     if (!â˜ƒxxxxxx) {
                        break;
                     }

                     if (!(â˜ƒ instanceof ServerPlayer)) {
                        Vec3 â˜ƒxxxxxxx = â˜ƒ.getDeltaMovement();
                        double â˜ƒxxxxxxxx = â˜ƒxxxxxxx.x;
                        double â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.y;
                        double â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx.z;
                        switch(â˜ƒ.getAxis()) {
                           case X:
                              â˜ƒxxxxxxxx = (double)â˜ƒ.getStepX();
                              break;
                           case Y:
                              â˜ƒxxxxxxxxx = (double)â˜ƒ.getStepY();
                              break;
                           case Z:
                              â˜ƒxxxxxxxxxx = (double)â˜ƒ.getStepZ();
                        }

                        â˜ƒ.setDeltaMovement(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
                        break;
                     }
                  }
               }

               double â˜ƒxxxxxxx = 0.0;

               for(AABB â˜ƒxxxxxxxx : â˜ƒxxxxx) {
                  AABB â˜ƒxxxxxxxxx = PistonMath.getMovementArea(moveByPositionAndProgress(â˜ƒ, â˜ƒxxxxxxxx, â˜ƒ), â˜ƒ, â˜ƒx);
                  AABB â˜ƒxxxxxxxxxx = â˜ƒ.getBoundingBox();
                  if (â˜ƒxxxxxxxxx.intersects(â˜ƒxxxxxxxxxx)) {
                     â˜ƒxxxxxxx = Math.max(â˜ƒxxxxxxx, getMovement(â˜ƒxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxx));
                     if (â˜ƒxxxxxxx >= â˜ƒx) {
                        break;
                     }
                  }
               }

               if (!(â˜ƒxxxxxxx <= 0.0)) {
                  â˜ƒxxxxxxx = Math.min(â˜ƒxxxxxxx, â˜ƒx) + 0.01;
                  moveEntityByPiston(â˜ƒ, â˜ƒ, â˜ƒxxxxxxx, â˜ƒ);
                  if (!â˜ƒ.extending && â˜ƒ.isSourcePiston) {
                     fixEntityWithinPistonBase(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
                  }
               }
            }
         }
      }
   }

   private static void moveEntityByPiston(Direction var0, Entity var1, double var2, Direction var4) {
      NOCLIP.set(â˜ƒ);
      â˜ƒ.move(MoverType.PISTON, new Vec3(â˜ƒ * (double)â˜ƒ.getStepX(), â˜ƒ * (double)â˜ƒ.getStepY(), â˜ƒ * (double)â˜ƒ.getStepZ()));
      NOCLIP.set(null);
   }

   private static void moveStuckEntities(Level var0, BlockPos var1, float var2, PistonMovingBlockEntity var3) {
      if (â˜ƒ.isStickyForEntities()) {
         Direction â˜ƒ = â˜ƒ.getMovementDirection();
         if (â˜ƒ.getAxis().isHorizontal()) {
            double â˜ƒx = â˜ƒ.movedState.getCollisionShape(â˜ƒ, â˜ƒ).max(Direction.Axis.Y);
            AABB â˜ƒxx = moveByPositionAndProgress(â˜ƒ, new AABB(0.0, â˜ƒx, 0.0, 1.0, 1.5000000999999998, 1.0), â˜ƒ);
            double â˜ƒxxx = (double)(â˜ƒ - â˜ƒ.progress);

            for(Entity â˜ƒxxxx : â˜ƒ.getEntities((Entity)null, â˜ƒxx, var1x -> matchesStickyCritera(â˜ƒ, var1x))) {
               moveEntityByPiston(â˜ƒ, â˜ƒxxxx, â˜ƒxxx, â˜ƒ);
            }
         }
      }
   }

   private static boolean matchesStickyCritera(AABB var0, Entity var1) {
      return â˜ƒ.getPistonPushReaction() == PushReaction.NORMAL
         && â˜ƒ.isOnGround()
         && â˜ƒ.getX() >= â˜ƒ.minX
         && â˜ƒ.getX() <= â˜ƒ.maxX
         && â˜ƒ.getZ() >= â˜ƒ.minZ
         && â˜ƒ.getZ() <= â˜ƒ.maxZ;
   }

   private boolean isStickyForEntities() {
      return this.movedState.is(Blocks.HONEY_BLOCK);
   }

   public Direction getMovementDirection() {
      return this.extending ? this.direction : this.direction.getOpposite();
   }

   private static double getMovement(AABB var0, Direction var1, AABB var2) {
      switch(â˜ƒ) {
         case EAST:
            return â˜ƒ.maxX - â˜ƒ.minX;
         case WEST:
            return â˜ƒ.maxX - â˜ƒ.minX;
         case UP:
         default:
            return â˜ƒ.maxY - â˜ƒ.minY;
         case DOWN:
            return â˜ƒ.maxY - â˜ƒ.minY;
         case SOUTH:
            return â˜ƒ.maxZ - â˜ƒ.minZ;
         case NORTH:
            return â˜ƒ.maxZ - â˜ƒ.minZ;
      }
   }

   private static AABB moveByPositionAndProgress(BlockPos var0, AABB var1, PistonMovingBlockEntity var2) {
      double â˜ƒ = (double)â˜ƒ.getExtendedProgress(â˜ƒ.progress);
      return â˜ƒ.move(
         (double)â˜ƒ.getX() + â˜ƒ * (double)â˜ƒ.direction.getStepX(),
         (double)â˜ƒ.getY() + â˜ƒ * (double)â˜ƒ.direction.getStepY(),
         (double)â˜ƒ.getZ() + â˜ƒ * (double)â˜ƒ.direction.getStepZ()
      );
   }

   private static void fixEntityWithinPistonBase(BlockPos var0, Entity var1, Direction var2, double var3) {
      AABB â˜ƒ = â˜ƒ.getBoundingBox();
      AABB â˜ƒx = Shapes.block().bounds().move(â˜ƒ);
      if (â˜ƒ.intersects(â˜ƒx)) {
         Direction â˜ƒxx = â˜ƒ.getOpposite();
         double â˜ƒxxx = getMovement(â˜ƒx, â˜ƒxx, â˜ƒ) + 0.01;
         double â˜ƒxxxx = getMovement(â˜ƒx, â˜ƒxx, â˜ƒ.intersect(â˜ƒx)) + 0.01;
         if (Math.abs(â˜ƒxxx - â˜ƒxxxx) < 0.01) {
            â˜ƒxxx = Math.min(â˜ƒxxx, â˜ƒ) + 0.01;
            moveEntityByPiston(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxx);
         }
      }
   }

   public BlockState getMovedState() {
      return this.movedState;
   }

   public void finalTick() {
      if (this.level != null && (this.progressO < 1.0F || this.level.isClientSide)) {
         this.progress = 1.0F;
         this.progressO = this.progress;
         this.level.removeBlockEntity(this.worldPosition);
         this.setRemoved();
         if (this.level.getBlockState(this.worldPosition).is(Blocks.MOVING_PISTON)) {
            BlockState â˜ƒ;
            if (this.isSourcePiston) {
               â˜ƒ = Blocks.AIR.defaultBlockState();
            } else {
               â˜ƒ = Block.updateFromNeighbourShapes(this.movedState, this.level, this.worldPosition);
            }

            this.level.setBlock(this.worldPosition, â˜ƒ, 3);
            this.level.neighborChanged(this.worldPosition, â˜ƒ.getBlock(), this.worldPosition);
         }
      }
   }

   public static void tick(Level var0, BlockPos var1, BlockState var2, PistonMovingBlockEntity var3) {
      â˜ƒ.lastTicked = â˜ƒ.getGameTime();
      â˜ƒ.progressO = â˜ƒ.progress;
      if (â˜ƒ.progressO >= 1.0F) {
         if (â˜ƒ.isClientSide && â˜ƒ.deathTicks < 5) {
            ++â˜ƒ.deathTicks;
         } else {
            â˜ƒ.removeBlockEntity(â˜ƒ);
            â˜ƒ.setRemoved();
            if (â˜ƒ.movedState != null && â˜ƒ.getBlockState(â˜ƒ).is(Blocks.MOVING_PISTON)) {
               BlockState â˜ƒ = Block.updateFromNeighbourShapes(â˜ƒ.movedState, â˜ƒ, â˜ƒ);
               if (â˜ƒ.isAir()) {
                  â˜ƒ.setBlock(â˜ƒ, â˜ƒ.movedState, 84);
                  Block.updateOrDestroy(â˜ƒ.movedState, â˜ƒ, â˜ƒ, â˜ƒ, 3);
               } else {
                  if (â˜ƒ.hasProperty(BlockStateProperties.WATERLOGGED) && â˜ƒ.getValue(BlockStateProperties.WATERLOGGED)) {
                     â˜ƒ = â˜ƒ.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(false));
                  }

                  â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 67);
                  â˜ƒ.neighborChanged(â˜ƒ, â˜ƒ.getBlock(), â˜ƒ);
               }
            }
         }
      } else {
         float â˜ƒ = â˜ƒ.progress + 0.5F;
         moveCollidedEntities(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         moveStuckEntities(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.progress = â˜ƒ;
         if (â˜ƒ.progress >= 1.0F) {
            â˜ƒ.progress = 1.0F;
         }
      }
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.movedState = NbtUtils.readBlockState(â˜ƒ.getCompound("blockState"));
      this.direction = Direction.from3DDataValue(â˜ƒ.getInt("facing"));
      this.progress = â˜ƒ.getFloat("progress");
      this.progressO = this.progress;
      this.extending = â˜ƒ.getBoolean("extending");
      this.isSourcePiston = â˜ƒ.getBoolean("source");
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.put("blockState", NbtUtils.writeBlockState(this.movedState));
      â˜ƒ.putInt("facing", this.direction.get3DDataValue());
      â˜ƒ.putFloat("progress", this.progressO);
      â˜ƒ.putBoolean("extending", this.extending);
      â˜ƒ.putBoolean("source", this.isSourcePiston);
      return â˜ƒ;
   }

   public VoxelShape getCollisionShape(BlockGetter var1, BlockPos var2) {
      VoxelShape â˜ƒ;
      if (!this.extending && this.isSourcePiston) {
         â˜ƒ = this.movedState.setValue(PistonBaseBlock.EXTENDED, Boolean.valueOf(true)).getCollisionShape(â˜ƒ, â˜ƒ);
      } else {
         â˜ƒ = Shapes.empty();
      }

      Direction â˜ƒ = (Direction)NOCLIP.get();
      if ((double)this.progress < 1.0 && â˜ƒ == this.getMovementDirection()) {
         return â˜ƒ;
      } else {
         BlockState â˜ƒ;
         if (this.isSourcePiston()) {
            â˜ƒ = Blocks.PISTON_HEAD
               .defaultBlockState()
               .setValue(PistonHeadBlock.FACING, this.direction)
               .setValue(PistonHeadBlock.SHORT, Boolean.valueOf(this.extending != 1.0F - this.progress < 0.25F));
         } else {
            â˜ƒ = this.movedState;
         }

         float â˜ƒ = this.getExtendedProgress(this.progress);
         double â˜ƒx = (double)((float)this.direction.getStepX() * â˜ƒ);
         double â˜ƒxx = (double)((float)this.direction.getStepY() * â˜ƒ);
         double â˜ƒxxx = (double)((float)this.direction.getStepZ() * â˜ƒ);
         return Shapes.or(â˜ƒ, â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ).move(â˜ƒx, â˜ƒxx, â˜ƒxxx));
      }
   }

   public long getLastTicked() {
      return this.lastTicked;
   }
}
