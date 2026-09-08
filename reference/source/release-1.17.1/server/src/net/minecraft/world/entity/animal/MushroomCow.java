package net.minecraft.world.entity.animal;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.apache.commons.lang3.tuple.Pair;

public class MushroomCow extends Cow implements Shearable {
   private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(MushroomCow.class, EntityDataSerializers.STRING);
   private static final int MUTATE_CHANCE = 1024;
   private MobEffect effect;
   private int effectDuration;
   private UUID lastLightningBoltUUID;

   public MushroomCow(EntityType<? extends MushroomCow> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public float getWalkTargetValue(BlockPos var1, LevelReader var2) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).is(Blocks.MYCELIUM) ? 10.0F : â˜ƒ.getBrightness(â˜ƒ) - 0.5F;
   }

   public static boolean checkMushroomSpawnRules(EntityType<MushroomCow> var0, LevelAccessor var1, MobSpawnType var2, BlockPos var3, Random var4) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).is(Blocks.MYCELIUM) && â˜ƒ.getRawBrightness(â˜ƒ, 0) > 8;
   }

   @Override
   public void thunderHit(ServerLevel var1, LightningBolt var2) {
      UUID â˜ƒ = â˜ƒ.getUUID();
      if (!â˜ƒ.equals(this.lastLightningBoltUUID)) {
         this.setMushroomType(this.getMushroomType() == MushroomCow.MushroomType.RED ? MushroomCow.MushroomType.BROWN : MushroomCow.MushroomType.RED);
         this.lastLightningBoltUUID = â˜ƒ;
         this.playSound(SoundEvents.MOOSHROOM_CONVERT, 2.0F, 1.0F);
      }
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_TYPE, MushroomCow.MushroomType.RED.type);
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.BOWL) && !this.isBaby()) {
         boolean â˜ƒxx = false;
         ItemStack â˜ƒx;
         if (this.effect != null) {
            â˜ƒxx = true;
            â˜ƒx = new ItemStack(Items.SUSPICIOUS_STEW);
            SuspiciousStewItem.saveMobEffect(â˜ƒx, this.effect, this.effectDuration);
            this.effect = null;
            this.effectDuration = 0;
         } else {
            â˜ƒx = new ItemStack(Items.MUSHROOM_STEW);
         }

         ItemStack â˜ƒxx = ItemUtils.createFilledResult(â˜ƒ, â˜ƒ, â˜ƒx, false);
         â˜ƒ.setItemInHand(â˜ƒ, â˜ƒxx);
         SoundEvent â˜ƒx;
         if (â˜ƒxx) {
            â˜ƒx = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
         } else {
            â˜ƒx = SoundEvents.MOOSHROOM_MILK;
         }

         this.playSound(â˜ƒx, 1.0F, 1.0F);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else if (â˜ƒ.is(Items.SHEARS) && this.readyForShearing()) {
         this.shear(SoundSource.PLAYERS);
         this.gameEvent(GameEvent.SHEAR, â˜ƒ);
         if (!this.level.isClientSide) {
            â˜ƒ.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
         }

         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else if (this.getMushroomType() == MushroomCow.MushroomType.BROWN && â˜ƒ.is(ItemTags.SMALL_FLOWERS)) {
         if (this.effect != null) {
            for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
               this.level
                  .addParticle(
                     ParticleTypes.SMOKE,
                     this.getX() + this.random.nextDouble() / 2.0,
                     this.getY(0.5),
                     this.getZ() + this.random.nextDouble() / 2.0,
                     0.0,
                     this.random.nextDouble() / 5.0,
                     0.0
                  );
            }
         } else {
            Optional<Pair<MobEffect, Integer>> â˜ƒ = this.getEffectFromItemStack(â˜ƒ);
            if (!â˜ƒ.isPresent()) {
               return InteractionResult.PASS;
            }

            Pair<MobEffect, Integer> â˜ƒ = (Pair)â˜ƒ.get();
            if (!â˜ƒ.getAbilities().instabuild) {
               â˜ƒ.shrink(1);
            }

            for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
               this.level
                  .addParticle(
                     ParticleTypes.EFFECT,
                     this.getX() + this.random.nextDouble() / 2.0,
                     this.getY(0.5),
                     this.getZ() + this.random.nextDouble() / 2.0,
                     0.0,
                     this.random.nextDouble() / 5.0,
                     0.0
                  );
            }

            this.effect = â˜ƒ.getLeft();
            this.effectDuration = â˜ƒ.getRight();
            this.playSound(SoundEvents.MOOSHROOM_EAT, 2.0F, 1.0F);
         }

         return InteractionResult.sidedSuccess(this.level.isClientSide);
      } else {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void shear(SoundSource var1) {
      this.level.playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, â˜ƒ, 1.0F, 1.0F);
      if (!this.level.isClientSide()) {
         ((ServerLevel)this.level).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
         this.discard();
         Cow â˜ƒ = EntityType.COW.create(this.level);
         â˜ƒ.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
         â˜ƒ.setHealth(this.getHealth());
         â˜ƒ.yBodyRot = this.yBodyRot;
         if (this.hasCustomName()) {
            â˜ƒ.setCustomName(this.getCustomName());
            â˜ƒ.setCustomNameVisible(this.isCustomNameVisible());
         }

         if (this.isPersistenceRequired()) {
            â˜ƒ.setPersistenceRequired();
         }

         â˜ƒ.setInvulnerable(this.isInvulnerable());
         this.level.addFreshEntity(â˜ƒ);

         for(int â˜ƒ = 0; â˜ƒ < 5; ++â˜ƒ) {
            this.level
               .addFreshEntity(
                  new ItemEntity(this.level, this.getX(), this.getY(1.0), this.getZ(), new ItemStack(this.getMushroomType().blockState.getBlock()))
               );
         }
      }
   }

   @Override
   public boolean readyForShearing() {
      return this.isAlive() && !this.isBaby();
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putString("Type", this.getMushroomType().type);
      if (this.effect != null) {
         â˜ƒ.putByte("EffectId", (byte)MobEffect.getId(this.effect));
         â˜ƒ.putInt("EffectDuration", this.effectDuration);
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setMushroomType(MushroomCow.MushroomType.byType(â˜ƒ.getString("Type")));
      if (â˜ƒ.contains("EffectId", 1)) {
         this.effect = MobEffect.byId(â˜ƒ.getByte("EffectId"));
      }

      if (â˜ƒ.contains("EffectDuration", 3)) {
         this.effectDuration = â˜ƒ.getInt("EffectDuration");
      }
   }

   private Optional<Pair<MobEffect, Integer>> getEffectFromItemStack(ItemStack var1) {
      Item â˜ƒ = â˜ƒ.getItem();
      if (â˜ƒ instanceof BlockItem) {
         Block â˜ƒxx = ((BlockItem)â˜ƒ).getBlock();
         if (â˜ƒxx instanceof FlowerBlock â˜ƒx) {
            return Optional.of(Pair.of(â˜ƒx.getSuspiciousStewEffect(), â˜ƒx.getEffectDuration()));
         }
      }

      return Optional.empty();
   }

   private void setMushroomType(MushroomCow.MushroomType var1) {
      this.entityData.set(DATA_TYPE, â˜ƒ.type);
   }

   public MushroomCow.MushroomType getMushroomType() {
      return MushroomCow.MushroomType.byType(this.entityData.get(DATA_TYPE));
   }

   public MushroomCow getBreedOffspring(ServerLevel var1, AgeableMob var2) {
      MushroomCow â˜ƒ = EntityType.MOOSHROOM.create(â˜ƒ);
      â˜ƒ.setMushroomType(this.getOffspringType((MushroomCow)â˜ƒ));
      return â˜ƒ;
   }

   private MushroomCow.MushroomType getOffspringType(MushroomCow var1) {
      MushroomCow.MushroomType â˜ƒx = this.getMushroomType();
      MushroomCow.MushroomType â˜ƒxx = â˜ƒ.getMushroomType();
      MushroomCow.MushroomType â˜ƒ;
      if (â˜ƒx == â˜ƒxx && this.random.nextInt(1024) == 0) {
         â˜ƒ = â˜ƒx == MushroomCow.MushroomType.BROWN ? MushroomCow.MushroomType.RED : MushroomCow.MushroomType.BROWN;
      } else {
         â˜ƒ = this.random.nextBoolean() ? â˜ƒx : â˜ƒxx;
      }

      return â˜ƒ;
   }

   public static enum MushroomType {
      RED("red", Blocks.RED_MUSHROOM.defaultBlockState()),
      BROWN("brown", Blocks.BROWN_MUSHROOM.defaultBlockState());

      final String type;
      final BlockState blockState;

      private MushroomType(String var3, BlockState var4) {
         this.type = â˜ƒ;
         this.blockState = â˜ƒ;
      }

      public BlockState getBlockState() {
         return this.blockState;
      }

      static MushroomCow.MushroomType byType(String var0) {
         for(MushroomCow.MushroomType â˜ƒ : values()) {
            if (â˜ƒ.type.equals(â˜ƒ)) {
               return â˜ƒ;
            }
         }

         return RED;
      }
   }
}
