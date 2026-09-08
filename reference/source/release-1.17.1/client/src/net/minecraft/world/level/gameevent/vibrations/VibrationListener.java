package net.minecraft.world.level.gameevent.vibrations;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.GameEventTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class VibrationListener implements GameEventListener {
   protected final PositionSource listenerSource;
   protected final int listenerRange;
   protected final VibrationListener.VibrationListenerConfig config;
   protected Optional<GameEvent> receivingEvent = Optional.empty();
   protected int receivingDistance;
   protected int travelTimeInTicks = 0;

   public VibrationListener(PositionSource var1, int var2, VibrationListener.VibrationListenerConfig var3) {
      this.listenerSource = â˜ƒ;
      this.listenerRange = â˜ƒ;
      this.config = â˜ƒ;
   }

   public void tick(Level var1) {
      if (this.receivingEvent.isPresent()) {
         --this.travelTimeInTicks;
         if (this.travelTimeInTicks <= 0) {
            this.travelTimeInTicks = 0;
            this.config.onSignalReceive(â˜ƒ, this, (GameEvent)this.receivingEvent.get(), this.receivingDistance);
            this.receivingEvent = Optional.empty();
         }
      }
   }

   @Override
   public PositionSource getListenerSource() {
      return this.listenerSource;
   }

   @Override
   public int getListenerRadius() {
      return this.listenerRange;
   }

   @Override
   public boolean handleGameEvent(Level var1, GameEvent var2, @Nullable Entity var3, BlockPos var4) {
      if (!this.isValidVibration(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         Optional<BlockPos> â˜ƒ = this.listenerSource.getPosition(â˜ƒ);
         if (!â˜ƒ.isPresent()) {
            return false;
         } else {
            BlockPos â˜ƒ = (BlockPos)â˜ƒ.get();
            if (!this.config.shouldListen(â˜ƒ, this, â˜ƒ, â˜ƒ, â˜ƒ)) {
               return false;
            } else if (this.isOccluded(â˜ƒ, â˜ƒ, â˜ƒ)) {
               return false;
            } else {
               this.sendSignal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               return true;
            }
         }
      }
   }

   private boolean isValidVibration(GameEvent var1, @Nullable Entity var2) {
      if (this.receivingEvent.isPresent()) {
         return false;
      } else if (!GameEventTags.VIBRATIONS.contains(â˜ƒ)) {
         return false;
      } else {
         if (â˜ƒ != null) {
            if (GameEventTags.IGNORE_VIBRATIONS_SNEAKING.contains(â˜ƒ) && â˜ƒ.isSteppingCarefully()) {
               return false;
            }

            if (â˜ƒ.occludesVibrations()) {
               return false;
            }
         }

         return â˜ƒ == null || !â˜ƒ.isSpectator();
      }
   }

   private void sendSignal(Level var1, GameEvent var2, BlockPos var3, BlockPos var4) {
      this.receivingEvent = Optional.of(â˜ƒ);
      if (â˜ƒ instanceof ServerLevel) {
         this.receivingDistance = Mth.floor(Math.sqrt(â˜ƒ.distSqr(â˜ƒ, false)));
         this.travelTimeInTicks = this.receivingDistance;
         ((ServerLevel)â˜ƒ).sendVibrationParticle(new VibrationPath(â˜ƒ, this.listenerSource, this.travelTimeInTicks));
      }
   }

   private boolean isOccluded(Level var1, BlockPos var2, BlockPos var3) {
      return â˜ƒ.isBlockInLine(new ClipBlockStateContext(Vec3.atCenterOf(â˜ƒ), Vec3.atCenterOf(â˜ƒ), var0 -> var0.is(BlockTags.OCCLUDES_VIBRATION_SIGNALS)))
            .getType()
         == HitResult.Type.BLOCK;
   }

   public interface VibrationListenerConfig {
      boolean shouldListen(Level var1, GameEventListener var2, BlockPos var3, GameEvent var4, @Nullable Entity var5);

      void onSignalReceive(Level var1, GameEventListener var2, GameEvent var3, int var4);
   }
}
