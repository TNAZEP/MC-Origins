package net.minecraft.world.entity;

import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ExperienceOrb extends Entity {
   private static final int LIFETIME = 6000;
   private static final int ENTITY_SCAN_PERIOD = 20;
   private static final int MAX_FOLLOW_DIST = 8;
   private static final int ORB_GROUPS_PER_AREA = 40;
   private static final double ORB_MERGE_DISTANCE = 0.5;
   private int age;
   private int health = 5;
   private int value;
   private int count = 1;
   private Player followingPlayer;

   public ExperienceOrb(Level var1, double var2, double var4, double var6, int var8) {
      this(EntityType.EXPERIENCE_ORB, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setYRot((float)(this.random.nextDouble() * 360.0));
      this.setDeltaMovement(
         (this.random.nextDouble() * 0.2F - 0.1F) * 2.0, this.random.nextDouble() * 0.2 * 2.0, (this.random.nextDouble() * 0.2F - 0.1F) * 2.0
      );
      this.value = â˜ƒ;
   }

   public ExperienceOrb(EntityType<? extends ExperienceOrb> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   @Override
   protected void defineSynchedData() {
   }

   @Override
   public void tick() {
      super.tick();
      this.xo = this.getX();
      this.yo = this.getY();
      this.zo = this.getZ();
      if (this.isEyeInFluid(FluidTags.WATER)) {
         this.setUnderwaterMovement();
      } else if (!this.isNoGravity()) {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.03, 0.0));
      }

      if (this.level.getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
         this.setDeltaMovement(
            (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F), 0.2F, (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F)
         );
      }

      if (!this.level.noCollision(this.getBoundingBox())) {
         this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
      }

      if (this.tickCount % 20 == 1) {
         this.scanForEntities();
      }

      if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying())) {
         this.followingPlayer = null;
      }

      if (this.followingPlayer != null) {
         Vec3 â˜ƒ = new Vec3(
            this.followingPlayer.getX() - this.getX(),
            this.followingPlayer.getY() + (double)this.followingPlayer.getEyeHeight() / 2.0 - this.getY(),
            this.followingPlayer.getZ() - this.getZ()
         );
         double â˜ƒx = â˜ƒ.lengthSqr();
         if (â˜ƒx < 64.0) {
            double â˜ƒxx = 1.0 - Math.sqrt(â˜ƒx) / 8.0;
            this.setDeltaMovement(this.getDeltaMovement().add(â˜ƒ.normalize().scale(â˜ƒxx * â˜ƒxx * 0.1)));
         }
      }

      this.move(MoverType.SELF, this.getDeltaMovement());
      float â˜ƒ = 0.98F;
      if (this.onGround) {
         â˜ƒ = this.level.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0, this.getZ())).getBlock().getFriction() * 0.98F;
      }

      this.setDeltaMovement(this.getDeltaMovement().multiply((double)â˜ƒ, 0.98, (double)â˜ƒ));
      if (this.onGround) {
         this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, -0.9, 1.0));
      }

      ++this.age;
      if (this.age >= 6000) {
         this.discard();
      }
   }

   private void scanForEntities() {
      if (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64.0) {
         this.followingPlayer = this.level.getNearestPlayer(this, 8.0);
      }

      if (this.level instanceof ServerLevel) {
         for(ExperienceOrb â˜ƒ : this.level.getEntities(EntityTypeTest.forClass(ExperienceOrb.class), this.getBoundingBox().inflate(0.5), this::canMerge)) {
            this.merge(â˜ƒ);
         }
      }
   }

   public static void award(ServerLevel var0, Vec3 var1, int var2) {
      while(â˜ƒ > 0) {
         int â˜ƒ = getExperienceValue(â˜ƒ);
         â˜ƒ -= â˜ƒ;
         if (!tryMergeToExisting(â˜ƒ, â˜ƒ, â˜ƒ)) {
            â˜ƒ.addFreshEntity(new ExperienceOrb(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z(), â˜ƒ));
         }
      }
   }

   private static boolean tryMergeToExisting(ServerLevel var0, Vec3 var1, int var2) {
      AABB â˜ƒ = AABB.ofSize(â˜ƒ, 1.0, 1.0, 1.0);
      int â˜ƒx = â˜ƒ.getRandom().nextInt(40);
      List<ExperienceOrb> â˜ƒxx = â˜ƒ.getEntities(EntityTypeTest.forClass(ExperienceOrb.class), â˜ƒ, var2x -> canMerge(var2x, â˜ƒ, â˜ƒ));
      if (!â˜ƒxx.isEmpty()) {
         ExperienceOrb â˜ƒxxx = (ExperienceOrb)â˜ƒxx.get(0);
         ++â˜ƒxxx.count;
         â˜ƒxxx.age = 0;
         return true;
      } else {
         return false;
      }
   }

   private boolean canMerge(ExperienceOrb var1) {
      return â˜ƒ != this && canMerge(â˜ƒ, this.getId(), this.value);
   }

   private static boolean canMerge(ExperienceOrb var0, int var1, int var2) {
      return !â˜ƒ.isRemoved() && (â˜ƒ.getId() - â˜ƒ) % 40 == 0 && â˜ƒ.value == â˜ƒ;
   }

   private void merge(ExperienceOrb var1) {
      this.count += â˜ƒ.count;
      this.age = Math.min(this.age, â˜ƒ.age);
      â˜ƒ.discard();
   }

   private void setUnderwaterMovement() {
      Vec3 â˜ƒ = this.getDeltaMovement();
      this.setDeltaMovement(â˜ƒ.x * 0.99F, Math.min(â˜ƒ.y + 5.0E-4F, 0.06F), â˜ƒ.z * 0.99F);
   }

   @Override
   protected void doWaterSplashEffect() {
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         this.markHurt();
         this.health = (int)((float)this.health - â˜ƒ);
         if (this.health <= 0) {
            this.discard();
         }

         return true;
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putShort("Health", (short)this.health);
      â˜ƒ.putShort("Age", (short)this.age);
      â˜ƒ.putShort("Value", (short)this.value);
      â˜ƒ.putInt("Count", this.count);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      this.health = â˜ƒ.getShort("Health");
      this.age = â˜ƒ.getShort("Age");
      this.value = â˜ƒ.getShort("Value");
      this.count = Math.max(â˜ƒ.getInt("Count"), 1);
   }

   @Override
   public void playerTouch(Player var1) {
      if (!this.level.isClientSide) {
         if (â˜ƒ.takeXpDelay == 0) {
            â˜ƒ.takeXpDelay = 2;
            â˜ƒ.take(this, 1);
            int â˜ƒ = this.repairPlayerItems(â˜ƒ, this.value);
            if (â˜ƒ > 0) {
               â˜ƒ.giveExperiencePoints(â˜ƒ);
            }

            --this.count;
            if (this.count == 0) {
               this.discard();
            }
         }
      }
   }

   private int repairPlayerItems(Player var1, int var2) {
      Entry<EquipmentSlot, ItemStack> â˜ƒ = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, â˜ƒ, ItemStack::isDamaged);
      if (â˜ƒ != null) {
         ItemStack â˜ƒx = (ItemStack)â˜ƒ.getValue();
         int â˜ƒxx = Math.min(this.xpToDurability(this.value), â˜ƒx.getDamageValue());
         â˜ƒx.setDamageValue(â˜ƒx.getDamageValue() - â˜ƒxx);
         int â˜ƒxxx = â˜ƒ - this.durabilityToXp(â˜ƒxx);
         return â˜ƒxxx > 0 ? this.repairPlayerItems(â˜ƒ, â˜ƒxxx) : 0;
      } else {
         return â˜ƒ;
      }
   }

   private int durabilityToXp(int var1) {
      return â˜ƒ / 2;
   }

   private int xpToDurability(int var1) {
      return â˜ƒ * 2;
   }

   public int getValue() {
      return this.value;
   }

   public int getIcon() {
      if (this.value >= 2477) {
         return 10;
      } else if (this.value >= 1237) {
         return 9;
      } else if (this.value >= 617) {
         return 8;
      } else if (this.value >= 307) {
         return 7;
      } else if (this.value >= 149) {
         return 6;
      } else if (this.value >= 73) {
         return 5;
      } else if (this.value >= 37) {
         return 4;
      } else if (this.value >= 17) {
         return 3;
      } else if (this.value >= 7) {
         return 2;
      } else {
         return this.value >= 3 ? 1 : 0;
      }
   }

   public static int getExperienceValue(int var0) {
      if (â˜ƒ >= 2477) {
         return 2477;
      } else if (â˜ƒ >= 1237) {
         return 1237;
      } else if (â˜ƒ >= 617) {
         return 617;
      } else if (â˜ƒ >= 307) {
         return 307;
      } else if (â˜ƒ >= 149) {
         return 149;
      } else if (â˜ƒ >= 73) {
         return 73;
      } else if (â˜ƒ >= 37) {
         return 37;
      } else if (â˜ƒ >= 17) {
         return 17;
      } else if (â˜ƒ >= 7) {
         return 7;
      } else {
         return â˜ƒ >= 3 ? 3 : 1;
      }
   }

   @Override
   public boolean isAttackable() {
      return false;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddExperienceOrbPacket(this);
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.AMBIENT;
   }
}
