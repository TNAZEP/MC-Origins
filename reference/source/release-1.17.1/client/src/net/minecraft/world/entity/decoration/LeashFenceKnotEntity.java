package net.minecraft.world.entity.decoration;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LeashFenceKnotEntity extends HangingEntity {
   public static final double OFFSET_Y = 0.375;

   public LeashFenceKnotEntity(EntityType<? extends LeashFenceKnotEntity> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public LeashFenceKnotEntity(Level var1, BlockPos var2) {
      super(EntityType.LEASH_KNOT, â˜ƒ, â˜ƒ);
      this.setPos((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
   }

   @Override
   protected void recalculateBoundingBox() {
      this.setPosRaw((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.375, (double)this.pos.getZ() + 0.5);
      double â˜ƒ = (double)this.getType().getWidth() / 2.0;
      double â˜ƒx = (double)this.getType().getHeight();
      this.setBoundingBox(new AABB(this.getX() - â˜ƒ, this.getY(), this.getZ() - â˜ƒ, this.getX() + â˜ƒ, this.getY() + â˜ƒx, this.getZ() + â˜ƒ));
   }

   @Override
   public void setDirection(Direction var1) {
   }

   @Override
   public int getWidth() {
      return 9;
   }

   @Override
   public int getHeight() {
      return 9;
   }

   @Override
   protected float getEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.0625F;
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      return â˜ƒ < 1024.0;
   }

   @Override
   public void dropItem(@Nullable Entity var1) {
      this.playSound(SoundEvents.LEASH_KNOT_BREAK, 1.0F, 1.0F);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
   }

   @Override
   public InteractionResult interact(Player var1, InteractionHand var2) {
      if (this.level.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         boolean â˜ƒ = false;
         double â˜ƒx = 7.0;
         List<Mob> â˜ƒxx = this.level
            .getEntitiesOfClass(
               Mob.class, new AABB(this.getX() - 7.0, this.getY() - 7.0, this.getZ() - 7.0, this.getX() + 7.0, this.getY() + 7.0, this.getZ() + 7.0)
            );

         for(Mob â˜ƒxxx : â˜ƒxx) {
            if (â˜ƒxxx.getLeashHolder() == â˜ƒ) {
               â˜ƒxxx.setLeashedTo(this, true);
               â˜ƒ = true;
            }
         }

         if (!â˜ƒ) {
            this.discard();
            if (â˜ƒ.getAbilities().instabuild) {
               for(Mob â˜ƒxxx : â˜ƒxx) {
                  if (â˜ƒxxx.isLeashed() && â˜ƒxxx.getLeashHolder() == this) {
                     â˜ƒxxx.dropLeash(true, false);
                  }
               }
            }
         }

         return InteractionResult.CONSUME;
      }
   }

   @Override
   public boolean survives() {
      return this.level.getBlockState(this.pos).is(BlockTags.FENCES);
   }

   public static LeashFenceKnotEntity getOrCreateKnot(Level var0, BlockPos var1) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();

      for(LeashFenceKnotEntity â˜ƒxxx : â˜ƒ.getEntitiesOfClass(
         LeashFenceKnotEntity.class,
         new AABB((double)â˜ƒ - 1.0, (double)â˜ƒx - 1.0, (double)â˜ƒxx - 1.0, (double)â˜ƒ + 1.0, (double)â˜ƒx + 1.0, (double)â˜ƒxx + 1.0)
      )) {
         if (â˜ƒxxx.getPos().equals(â˜ƒ)) {
            return â˜ƒxxx;
         }
      }

      LeashFenceKnotEntity â˜ƒxxx = new LeashFenceKnotEntity(â˜ƒ, â˜ƒ);
      â˜ƒ.addFreshEntity(â˜ƒxxx);
      return â˜ƒxxx;
   }

   @Override
   public void playPlacementSound() {
      this.playSound(SoundEvents.LEASH_KNOT_PLACE, 1.0F, 1.0F);
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this, this.getType(), 0, this.getPos());
   }

   @Override
   public Vec3 getRopeHoldPosition(float var1) {
      return this.getPosition(â˜ƒ).add(0.0, 0.2, 0.0);
   }

   @Override
   public ItemStack getPickResult() {
      return new ItemStack(Items.LEAD);
   }
}
