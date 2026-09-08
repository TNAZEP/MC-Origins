package net.minecraft.world.entity.decoration;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;

public abstract class HangingEntity extends Entity {
   protected static final Predicate<Entity> HANGING_ENTITY = var0 -> var0 instanceof HangingEntity;
   private int checkInterval;
   protected BlockPos pos;
   protected Direction direction = Direction.SOUTH;

   protected HangingEntity(EntityType<? extends HangingEntity> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected HangingEntity(EntityType<? extends HangingEntity> var1, Level var2, BlockPos var3) {
      this(â˜ƒ, â˜ƒ);
      this.pos = â˜ƒ;
   }

   @Override
   protected void defineSynchedData() {
   }

   protected void setDirection(Direction var1) {
      Validate.notNull(â˜ƒ);
      Validate.isTrue(â˜ƒ.getAxis().isHorizontal());
      this.direction = â˜ƒ;
      this.setYRot((float)(this.direction.get2DDataValue() * 90));
      this.yRotO = this.getYRot();
      this.recalculateBoundingBox();
   }

   protected void recalculateBoundingBox() {
      if (this.direction != null) {
         double â˜ƒ = (double)this.pos.getX() + 0.5;
         double â˜ƒx = (double)this.pos.getY() + 0.5;
         double â˜ƒxx = (double)this.pos.getZ() + 0.5;
         double â˜ƒxxx = 0.46875;
         double â˜ƒxxxx = this.offs(this.getWidth());
         double â˜ƒxxxxx = this.offs(this.getHeight());
         â˜ƒ -= (double)this.direction.getStepX() * 0.46875;
         â˜ƒxx -= (double)this.direction.getStepZ() * 0.46875;
         â˜ƒx += â˜ƒxxxxx;
         Direction â˜ƒxxxxxx = this.direction.getCounterClockWise();
         â˜ƒ += â˜ƒxxxx * (double)â˜ƒxxxxxx.getStepX();
         â˜ƒxx += â˜ƒxxxx * (double)â˜ƒxxxxxx.getStepZ();
         this.setPosRaw(â˜ƒ, â˜ƒx, â˜ƒxx);
         double â˜ƒxxxxxxx = (double)this.getWidth();
         double â˜ƒxxxxxxxx = (double)this.getHeight();
         double â˜ƒxxxxxxxxx = (double)this.getWidth();
         if (this.direction.getAxis() == Direction.Axis.Z) {
            â˜ƒxxxxxxxxx = 1.0;
         } else {
            â˜ƒxxxxxxx = 1.0;
         }

         â˜ƒxxxxxxx /= 32.0;
         â˜ƒxxxxxxxx /= 32.0;
         â˜ƒxxxxxxxxx /= 32.0;
         this.setBoundingBox(new AABB(â˜ƒ - â˜ƒxxxxxxx, â˜ƒx - â˜ƒxxxxxxxx, â˜ƒxx - â˜ƒxxxxxxxxx, â˜ƒ + â˜ƒxxxxxxx, â˜ƒx + â˜ƒxxxxxxxx, â˜ƒxx + â˜ƒxxxxxxxxx));
      }
   }

   private double offs(int var1) {
      return â˜ƒ % 32 == 0 ? 0.5 : 0.0;
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         this.checkOutOfWorld();
         if (this.checkInterval++ == 100) {
            this.checkInterval = 0;
            if (!this.isRemoved() && !this.survives()) {
               this.discard();
               this.dropItem(null);
            }
         }
      }
   }

   public boolean survives() {
      if (!this.level.noCollision(this)) {
         return false;
      } else {
         int â˜ƒ = Math.max(1, this.getWidth() / 16);
         int â˜ƒx = Math.max(1, this.getHeight() / 16);
         BlockPos â˜ƒxx = this.pos.relative(this.direction.getOpposite());
         Direction â˜ƒxxx = this.direction.getCounterClockWise();
         BlockPos.MutableBlockPos â˜ƒxxxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒ; ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒx; ++â˜ƒxxxxxx) {
               int â˜ƒxxxxxxx = (â˜ƒ - 1) / -2;
               int â˜ƒxxxxxxxx = (â˜ƒx - 1) / -2;
               â˜ƒxxxx.set(â˜ƒxx).move(â˜ƒxxx, â˜ƒxxxxx + â˜ƒxxxxxxx).move(Direction.UP, â˜ƒxxxxxx + â˜ƒxxxxxxxx);
               BlockState â˜ƒxxxxxxxxx = this.level.getBlockState(â˜ƒxxxx);
               if (!â˜ƒxxxxxxxxx.getMaterial().isSolid() && !DiodeBlock.isDiode(â˜ƒxxxxxxxxx)) {
                  return false;
               }
            }
         }

         return this.level.getEntities(this, this.getBoundingBox(), HANGING_ENTITY).isEmpty();
      }
   }

   @Override
   public boolean isPickable() {
      return true;
   }

   @Override
   public boolean skipAttackInteraction(Entity var1) {
      if (â˜ƒ instanceof Player â˜ƒ) {
         return !this.level.mayInteract(â˜ƒ, this.pos) ? true : this.hurt(DamageSource.playerAttack(â˜ƒ), 0.0F);
      } else {
         return false;
      }
   }

   @Override
   public Direction getDirection() {
      return this.direction;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         if (!this.isRemoved() && !this.level.isClientSide) {
            this.kill();
            this.markHurt();
            this.dropItem(â˜ƒ.getEntity());
         }

         return true;
      }
   }

   @Override
   public void move(MoverType var1, Vec3 var2) {
      if (!this.level.isClientSide && !this.isRemoved() && â˜ƒ.lengthSqr() > 0.0) {
         this.kill();
         this.dropItem(null);
      }
   }

   @Override
   public void push(double var1, double var3, double var5) {
      if (!this.level.isClientSide && !this.isRemoved() && â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ > 0.0) {
         this.kill();
         this.dropItem(null);
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      BlockPos â˜ƒ = this.getPos();
      â˜ƒ.putInt("TileX", â˜ƒ.getX());
      â˜ƒ.putInt("TileY", â˜ƒ.getY());
      â˜ƒ.putInt("TileZ", â˜ƒ.getZ());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      this.pos = new BlockPos(â˜ƒ.getInt("TileX"), â˜ƒ.getInt("TileY"), â˜ƒ.getInt("TileZ"));
   }

   public abstract int getWidth();

   public abstract int getHeight();

   public abstract void dropItem(@Nullable Entity var1);

   public abstract void playPlacementSound();

   @Override
   public ItemEntity spawnAtLocation(ItemStack var1, float var2) {
      ItemEntity â˜ƒ = new ItemEntity(
         this.level,
         this.getX() + (double)((float)this.direction.getStepX() * 0.15F),
         this.getY() + (double)â˜ƒ,
         this.getZ() + (double)((float)this.direction.getStepZ() * 0.15F),
         â˜ƒ
      );
      â˜ƒ.setDefaultPickUpDelay();
      this.level.addFreshEntity(â˜ƒ);
      return â˜ƒ;
   }

   @Override
   protected boolean repositionEntityAfterLoad() {
      return false;
   }

   @Override
   public void setPos(double var1, double var3, double var5) {
      this.pos = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.recalculateBoundingBox();
      this.hasImpulse = true;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   @Override
   public float rotate(Rotation var1) {
      if (this.direction.getAxis() != Direction.Axis.Y) {
         switch(â˜ƒ) {
            case CLOCKWISE_180:
               this.direction = this.direction.getOpposite();
               break;
            case COUNTERCLOCKWISE_90:
               this.direction = this.direction.getCounterClockWise();
               break;
            case CLOCKWISE_90:
               this.direction = this.direction.getClockWise();
         }
      }

      float â˜ƒ = Mth.wrapDegrees(this.getYRot());
      switch(â˜ƒ) {
         case CLOCKWISE_180:
            return â˜ƒ + 180.0F;
         case COUNTERCLOCKWISE_90:
            return â˜ƒ + 90.0F;
         case CLOCKWISE_90:
            return â˜ƒ + 270.0F;
         default:
            return â˜ƒ;
      }
   }

   @Override
   public float mirror(Mirror var1) {
      return this.rotate(â˜ƒ.getRotation(this.direction));
   }

   @Override
   public void thunderHit(ServerLevel var1, LightningBolt var2) {
   }

   @Override
   public void refreshDimensions() {
   }
}
