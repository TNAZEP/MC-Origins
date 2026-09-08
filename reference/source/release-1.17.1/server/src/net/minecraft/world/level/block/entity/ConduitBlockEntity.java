package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ConduitBlockEntity extends BlockEntity {
   private static final int BLOCK_REFRESH_RATE = 2;
   private static final int EFFECT_DURATION = 13;
   private static final float ROTATION_SPEED = -0.0375F;
   private static final int MIN_ACTIVE_SIZE = 16;
   private static final int MIN_KILL_SIZE = 42;
   private static final int KILL_RANGE = 8;
   private static final Block[] VALID_BLOCKS = new Block[]{Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.DARK_PRISMARINE};
   public int tickCount;
   private float activeRotation;
   private boolean isActive;
   private boolean isHunting;
   private final List<BlockPos> effectBlocks = Lists.<BlockPos>newArrayList();
   @Nullable
   private LivingEntity destroyTarget;
   @Nullable
   private UUID destroyTargetUUID;
   private long nextAmbientSoundActivation;

   public ConduitBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.CONDUIT, â˜ƒ, â˜ƒ);
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      if (â˜ƒ.hasUUID("Target")) {
         this.destroyTargetUUID = â˜ƒ.getUUID("Target");
      } else {
         this.destroyTargetUUID = null;
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      if (this.destroyTarget != null) {
         â˜ƒ.putUUID("Target", this.destroyTarget.getUUID());
      }

      return â˜ƒ;
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 5, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   public static void clientTick(Level var0, BlockPos var1, BlockState var2, ConduitBlockEntity var3) {
      ++â˜ƒ.tickCount;
      long â˜ƒ = â˜ƒ.getGameTime();
      List<BlockPos> â˜ƒx = â˜ƒ.effectBlocks;
      if (â˜ƒ % 40L == 0L) {
         â˜ƒ.isActive = updateShape(â˜ƒ, â˜ƒ, â˜ƒx);
         updateHunting(â˜ƒ, â˜ƒx);
      }

      updateClientTarget(â˜ƒ, â˜ƒ, â˜ƒ);
      animationTick(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ.destroyTarget, â˜ƒ.tickCount);
      if (â˜ƒ.isActive()) {
         ++â˜ƒ.activeRotation;
      }
   }

   public static void serverTick(Level var0, BlockPos var1, BlockState var2, ConduitBlockEntity var3) {
      ++â˜ƒ.tickCount;
      long â˜ƒ = â˜ƒ.getGameTime();
      List<BlockPos> â˜ƒx = â˜ƒ.effectBlocks;
      if (â˜ƒ % 40L == 0L) {
         boolean â˜ƒxx = updateShape(â˜ƒ, â˜ƒ, â˜ƒx);
         if (â˜ƒxx != â˜ƒ.isActive) {
            SoundEvent â˜ƒxxx = â˜ƒxx ? SoundEvents.CONDUIT_ACTIVATE : SoundEvents.CONDUIT_DEACTIVATE;
            â˜ƒ.playSound(null, â˜ƒ, â˜ƒxxx, SoundSource.BLOCKS, 1.0F, 1.0F);
         }

         â˜ƒ.isActive = â˜ƒxx;
         updateHunting(â˜ƒ, â˜ƒx);
         if (â˜ƒxx) {
            applyEffects(â˜ƒ, â˜ƒ, â˜ƒx);
            updateDestroyTarget(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
         }
      }

      if (â˜ƒ.isActive()) {
         if (â˜ƒ % 80L == 0L) {
            â˜ƒ.playSound(null, â˜ƒ, SoundEvents.CONDUIT_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
         }

         if (â˜ƒ > â˜ƒ.nextAmbientSoundActivation) {
            â˜ƒ.nextAmbientSoundActivation = â˜ƒ + 60L + (long)â˜ƒ.getRandom().nextInt(40);
            â˜ƒ.playSound(null, â˜ƒ, SoundEvents.CONDUIT_AMBIENT_SHORT, SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }
   }

   private static void updateHunting(ConduitBlockEntity var0, List<BlockPos> var1) {
      â˜ƒ.setHunting(â˜ƒ.size() >= 42);
   }

   private static boolean updateShape(Level var0, BlockPos var1, List<BlockPos> var2) {
      â˜ƒ.clear();

      for(int â˜ƒ = -1; â˜ƒ <= 1; ++â˜ƒ) {
         for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
            for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
               BlockPos â˜ƒxxx = â˜ƒ.offset(â˜ƒ, â˜ƒx, â˜ƒxx);
               if (!â˜ƒ.isWaterAt(â˜ƒxxx)) {
                  return false;
               }
            }
         }
      }

      for(int â˜ƒ = -2; â˜ƒ <= 2; ++â˜ƒ) {
         for(int â˜ƒx = -2; â˜ƒx <= 2; ++â˜ƒx) {
            for(int â˜ƒxx = -2; â˜ƒxx <= 2; ++â˜ƒxx) {
               int â˜ƒxxx = Math.abs(â˜ƒ);
               int â˜ƒxxxx = Math.abs(â˜ƒx);
               int â˜ƒxxxxx = Math.abs(â˜ƒxx);
               if ((â˜ƒxxx > 1 || â˜ƒxxxx > 1 || â˜ƒxxxxx > 1)
                  && (â˜ƒ == 0 && (â˜ƒxxxx == 2 || â˜ƒxxxxx == 2) || â˜ƒx == 0 && (â˜ƒxxx == 2 || â˜ƒxxxxx == 2) || â˜ƒxx == 0 && (â˜ƒxxx == 2 || â˜ƒxxxx == 2))
                  )
                {
                  BlockPos â˜ƒxxxxxx = â˜ƒ.offset(â˜ƒ, â˜ƒx, â˜ƒxx);
                  BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);

                  for(Block â˜ƒxxxxxxxx : VALID_BLOCKS) {
                     if (â˜ƒxxxxxxx.is(â˜ƒxxxxxxxx)) {
                        â˜ƒ.add(â˜ƒxxxxxx);
                     }
                  }
               }
            }
         }
      }

      return â˜ƒ.size() >= 16;
   }

   private static void applyEffects(Level var0, BlockPos var1, List<BlockPos> var2) {
      int â˜ƒ = â˜ƒ.size();
      int â˜ƒx = â˜ƒ / 7 * 16;
      int â˜ƒxx = â˜ƒ.getX();
      int â˜ƒxxx = â˜ƒ.getY();
      int â˜ƒxxxx = â˜ƒ.getZ();
      AABB â˜ƒxxxxx = new AABB((double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒxxxx, (double)(â˜ƒxx + 1), (double)(â˜ƒxxx + 1), (double)(â˜ƒxxxx + 1))
         .inflate((double)â˜ƒx)
         .expandTowards(0.0, (double)â˜ƒ.getHeight(), 0.0);
      List<Player> â˜ƒxxxxxx = â˜ƒ.getEntitiesOfClass(Player.class, â˜ƒxxxxx);
      if (!â˜ƒxxxxxx.isEmpty()) {
         for(Player â˜ƒxxxxxxx : â˜ƒxxxxxx) {
            if (â˜ƒ.closerThan(â˜ƒxxxxxxx.blockPosition(), (double)â˜ƒx) && â˜ƒxxxxxxx.isInWaterOrRain()) {
               â˜ƒxxxxxxx.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 260, 0, true, true));
            }
         }
      }
   }

   private static void updateDestroyTarget(Level var0, BlockPos var1, BlockState var2, List<BlockPos> var3, ConduitBlockEntity var4) {
      LivingEntity â˜ƒ = â˜ƒ.destroyTarget;
      int â˜ƒx = â˜ƒ.size();
      if (â˜ƒx < 42) {
         â˜ƒ.destroyTarget = null;
      } else if (â˜ƒ.destroyTarget == null && â˜ƒ.destroyTargetUUID != null) {
         â˜ƒ.destroyTarget = findDestroyTarget(â˜ƒ, â˜ƒ, â˜ƒ.destroyTargetUUID);
         â˜ƒ.destroyTargetUUID = null;
      } else if (â˜ƒ.destroyTarget == null) {
         List<LivingEntity> â˜ƒ = â˜ƒ.getEntitiesOfClass(
            LivingEntity.class, getDestroyRangeAABB(â˜ƒ), var0x -> var0x instanceof Enemy && var0x.isInWaterOrRain()
         );
         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.destroyTarget = (LivingEntity)â˜ƒ.get(â˜ƒ.random.nextInt(â˜ƒ.size()));
         }
      } else if (!â˜ƒ.destroyTarget.isAlive() || !â˜ƒ.closerThan(â˜ƒ.destroyTarget.blockPosition(), 8.0)) {
         â˜ƒ.destroyTarget = null;
      }

      if (â˜ƒ.destroyTarget != null) {
         â˜ƒ.playSound(
            null,
            â˜ƒ.destroyTarget.getX(),
            â˜ƒ.destroyTarget.getY(),
            â˜ƒ.destroyTarget.getZ(),
            SoundEvents.CONDUIT_ATTACK_TARGET,
            SoundSource.BLOCKS,
            1.0F,
            1.0F
         );
         â˜ƒ.destroyTarget.hurt(DamageSource.MAGIC, 4.0F);
      }

      if (â˜ƒ != â˜ƒ.destroyTarget) {
         â˜ƒ.sendBlockUpdated(â˜ƒ, â˜ƒ, â˜ƒ, 2);
      }
   }

   private static void updateClientTarget(Level var0, BlockPos var1, ConduitBlockEntity var2) {
      if (â˜ƒ.destroyTargetUUID == null) {
         â˜ƒ.destroyTarget = null;
      } else if (â˜ƒ.destroyTarget == null || !â˜ƒ.destroyTarget.getUUID().equals(â˜ƒ.destroyTargetUUID)) {
         â˜ƒ.destroyTarget = findDestroyTarget(â˜ƒ, â˜ƒ, â˜ƒ.destroyTargetUUID);
         if (â˜ƒ.destroyTarget == null) {
            â˜ƒ.destroyTargetUUID = null;
         }
      }
   }

   private static AABB getDestroyRangeAABB(BlockPos var0) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();
      return new AABB((double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒxx, (double)(â˜ƒ + 1), (double)(â˜ƒx + 1), (double)(â˜ƒxx + 1)).inflate(8.0);
   }

   @Nullable
   private static LivingEntity findDestroyTarget(Level var0, BlockPos var1, UUID var2) {
      List<LivingEntity> â˜ƒ = â˜ƒ.getEntitiesOfClass(LivingEntity.class, getDestroyRangeAABB(â˜ƒ), var1x -> var1x.getUUID().equals(â˜ƒ));
      return â˜ƒ.size() == 1 ? (LivingEntity)â˜ƒ.get(0) : null;
   }

   private static void animationTick(Level var0, BlockPos var1, List<BlockPos> var2, @Nullable Entity var3, int var4) {
      Random â˜ƒ = â˜ƒ.random;
      double â˜ƒx = (double)(Mth.sin((float)(â˜ƒ + 35) * 0.1F) / 2.0F + 0.5F);
      â˜ƒx = (â˜ƒx * â˜ƒx + â˜ƒx) * 0.3F;
      Vec3 â˜ƒxx = new Vec3((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 1.5 + â˜ƒx, (double)â˜ƒ.getZ() + 0.5);

      for(BlockPos â˜ƒxxx : â˜ƒ) {
         if (â˜ƒ.nextInt(50) == 0) {
            BlockPos â˜ƒxxxx = â˜ƒxxx.subtract(â˜ƒ);
            float â˜ƒxxxxx = -0.5F + â˜ƒ.nextFloat() + (float)â˜ƒxxxx.getX();
            float â˜ƒxxxxxx = -2.0F + â˜ƒ.nextFloat() + (float)â˜ƒxxxx.getY();
            float â˜ƒxxxxxxx = -0.5F + â˜ƒ.nextFloat() + (float)â˜ƒxxxx.getZ();
            â˜ƒ.addParticle(ParticleTypes.NAUTILUS, â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z, (double)â˜ƒxxxxx, (double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx);
         }
      }

      if (â˜ƒ != null) {
         Vec3 â˜ƒxxx = new Vec3(â˜ƒ.getX(), â˜ƒ.getEyeY(), â˜ƒ.getZ());
         float â˜ƒxxxx = (-0.5F + â˜ƒ.nextFloat()) * (3.0F + â˜ƒ.getBbWidth());
         float â˜ƒxxxxx = -1.0F + â˜ƒ.nextFloat() * â˜ƒ.getBbHeight();
         float â˜ƒxxxxxx = (-0.5F + â˜ƒ.nextFloat()) * (3.0F + â˜ƒ.getBbWidth());
         Vec3 â˜ƒxxxxxxx = new Vec3((double)â˜ƒxxxx, (double)â˜ƒxxxxx, (double)â˜ƒxxxxxx);
         â˜ƒ.addParticle(ParticleTypes.NAUTILUS, â˜ƒxxx.x, â˜ƒxxx.y, â˜ƒxxx.z, â˜ƒxxxxxxx.x, â˜ƒxxxxxxx.y, â˜ƒxxxxxxx.z);
      }
   }

   public boolean isActive() {
      return this.isActive;
   }

   public boolean isHunting() {
      return this.isHunting;
   }

   private void setHunting(boolean var1) {
      this.isHunting = â˜ƒ;
   }

   public float getActiveRotation(float var1) {
      return (this.activeRotation + â˜ƒ) * -0.0375F;
   }
}
