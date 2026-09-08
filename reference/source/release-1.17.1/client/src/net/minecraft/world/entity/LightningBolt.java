package net.minecraft.world.entity;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightningBolt extends Entity {
   private static final int START_LIFE = 2;
   private static final double DAMAGE_RADIUS = 3.0;
   private static final double DETECTION_RADIUS = 15.0;
   private int life;
   public long seed;
   private int flashes;
   private boolean visualOnly;
   @Nullable
   private ServerPlayer cause;
   private final Set<Entity> hitEntities = Sets.<Entity>newHashSet();
   private int blocksSetOnFire;

   public LightningBolt(EntityType<? extends LightningBolt> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.noCulling = true;
      this.life = 2;
      this.seed = this.random.nextLong();
      this.flashes = this.random.nextInt(3) + 1;
   }

   public void setVisualOnly(boolean var1) {
      this.visualOnly = â˜ƒ;
   }

   @Override
   public SoundSource getSoundSource() {
      return SoundSource.WEATHER;
   }

   @Nullable
   public ServerPlayer getCause() {
      return this.cause;
   }

   public void setCause(@Nullable ServerPlayer var1) {
      this.cause = â˜ƒ;
   }

   private void powerLightningRod() {
      BlockPos â˜ƒ = this.getStrikePosition();
      BlockState â˜ƒx = this.level.getBlockState(â˜ƒ);
      if (â˜ƒx.is(Blocks.LIGHTNING_ROD)) {
         ((LightningRodBlock)â˜ƒx.getBlock()).onLightningStrike(â˜ƒx, this.level, â˜ƒ);
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.life == 2) {
         if (this.level.isClientSide()) {
            this.level
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  SoundEvents.LIGHTNING_BOLT_THUNDER,
                  SoundSource.WEATHER,
                  10000.0F,
                  0.8F + this.random.nextFloat() * 0.2F,
                  false
               );
            this.level
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  SoundEvents.LIGHTNING_BOLT_IMPACT,
                  SoundSource.WEATHER,
                  2.0F,
                  0.5F + this.random.nextFloat() * 0.2F,
                  false
               );
         } else {
            Difficulty â˜ƒ = this.level.getDifficulty();
            if (â˜ƒ == Difficulty.NORMAL || â˜ƒ == Difficulty.HARD) {
               this.spawnFire(4);
            }

            this.powerLightningRod();
            clearCopperOnLightningStrike(this.level, this.getStrikePosition());
            this.gameEvent(GameEvent.LIGHTNING_STRIKE);
         }
      }

      --this.life;
      if (this.life < 0) {
         if (this.flashes == 0) {
            if (this.level instanceof ServerLevel) {
               List<Entity> â˜ƒ = this.level
                  .getEntities(
                     this,
                     new AABB(this.getX() - 15.0, this.getY() - 15.0, this.getZ() - 15.0, this.getX() + 15.0, this.getY() + 6.0 + 15.0, this.getZ() + 15.0),
                     var1x -> var1x.isAlive() && !this.hitEntities.contains(var1x)
                  );

               for(ServerPlayer â˜ƒx : ((ServerLevel)this.level).getPlayers(var1x -> var1x.distanceTo(this) < 256.0F)) {
                  CriteriaTriggers.LIGHTNING_STRIKE.trigger(â˜ƒx, this, â˜ƒ);
               }
            }

            this.discard();
         } else if (this.life < -this.random.nextInt(10)) {
            --this.flashes;
            this.life = 1;
            this.seed = this.random.nextLong();
            this.spawnFire(0);
         }
      }

      if (this.life >= 0) {
         if (!(this.level instanceof ServerLevel)) {
            this.level.setSkyFlashTime(2);
         } else if (!this.visualOnly) {
            List<Entity> â˜ƒ = this.level
               .getEntities(
                  this,
                  new AABB(this.getX() - 3.0, this.getY() - 3.0, this.getZ() - 3.0, this.getX() + 3.0, this.getY() + 6.0 + 3.0, this.getZ() + 3.0),
                  Entity::isAlive
               );

            for(Entity â˜ƒx : â˜ƒ) {
               â˜ƒx.thunderHit((ServerLevel)this.level, this);
            }

            this.hitEntities.addAll(â˜ƒ);
            if (this.cause != null) {
               CriteriaTriggers.CHANNELED_LIGHTNING.trigger(this.cause, â˜ƒ);
            }
         }
      }
   }

   private BlockPos getStrikePosition() {
      Vec3 â˜ƒ = this.position();
      return new BlockPos(â˜ƒ.x, â˜ƒ.y - 1.0E-6, â˜ƒ.z);
   }

   private void spawnFire(int var1) {
      if (!this.visualOnly && !this.level.isClientSide && this.level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
         BlockPos â˜ƒ = this.blockPosition();
         BlockState â˜ƒx = BaseFireBlock.getState(this.level, â˜ƒ);
         if (this.level.getBlockState(â˜ƒ).isAir() && â˜ƒx.canSurvive(this.level, â˜ƒ)) {
            this.level.setBlockAndUpdate(â˜ƒ, â˜ƒx);
            ++this.blocksSetOnFire;
         }

         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
            BlockPos â˜ƒx = â˜ƒ.offset(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
            â˜ƒx = BaseFireBlock.getState(this.level, â˜ƒx);
            if (this.level.getBlockState(â˜ƒx).isAir() && â˜ƒx.canSurvive(this.level, â˜ƒx)) {
               this.level.setBlockAndUpdate(â˜ƒx, â˜ƒx);
               ++this.blocksSetOnFire;
            }
         }
      }
   }

   private static void clearCopperOnLightningStrike(Level var0, BlockPos var1) {
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
      BlockPos â˜ƒ;
      BlockState â˜ƒx;
      if (â˜ƒxx.is(Blocks.LIGHTNING_ROD)) {
         â˜ƒ = â˜ƒ.relative(((Direction)â˜ƒxx.getValue(LightningRodBlock.FACING)).getOpposite());
         â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      } else {
         â˜ƒ = â˜ƒ;
         â˜ƒx = â˜ƒxx;
      }

      if (â˜ƒx.getBlock() instanceof WeatheringCopper) {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, WeatheringCopper.getFirst(â˜ƒ.getBlockState(â˜ƒ)));
         BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
         int â˜ƒx = â˜ƒ.random.nextInt(3) + 3;

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
            int â˜ƒxxx = â˜ƒ.random.nextInt(8) + 1;
            randomWalkCleaningCopper(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx);
         }
      }
   }

   private static void randomWalkCleaningCopper(Level var0, BlockPos var1, BlockPos.MutableBlockPos var2, int var3) {
      â˜ƒ.set(â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         Optional<BlockPos> â˜ƒx = randomStepCleaningCopper(â˜ƒ, â˜ƒ);
         if (!â˜ƒx.isPresent()) {
            break;
         }

         â˜ƒ.set((Vec3i)â˜ƒx.get());
      }
   }

   private static Optional<BlockPos> randomStepCleaningCopper(Level var0, BlockPos var1) {
      for(BlockPos â˜ƒ : BlockPos.randomInCube(â˜ƒ.random, 10, â˜ƒ, 1)) {
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         if (â˜ƒx.getBlock() instanceof WeatheringCopper) {
            WeatheringCopper.getPrevious(â˜ƒx).ifPresent(var2 -> â˜ƒ.setBlockAndUpdate(â˜ƒ, var2));
            â˜ƒ.levelEvent(3002, â˜ƒ, -1);
            return Optional.of(â˜ƒ);
         }
      }

      return Optional.empty();
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      double â˜ƒ = 64.0 * getViewScale();
      return â˜ƒ < â˜ƒ * â˜ƒ;
   }

   @Override
   protected void defineSynchedData() {
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }

   public int getBlocksSetOnFire() {
      return this.blocksSetOnFire;
   }

   public Stream<Entity> getHitEntities() {
      return this.hitEntities.stream().filter(Entity::isAlive);
   }
}
