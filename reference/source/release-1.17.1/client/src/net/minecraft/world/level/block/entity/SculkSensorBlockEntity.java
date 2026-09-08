package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.vibrations.VibrationListener;

public class SculkSensorBlockEntity extends BlockEntity implements VibrationListener.VibrationListenerConfig {
   private final VibrationListener listener;
   private int lastVibrationFrequency;

   public SculkSensorBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.SCULK_SENSOR, â˜ƒ, â˜ƒ);
      this.listener = new VibrationListener(new BlockPositionSource(this.worldPosition), ((SculkSensorBlock)â˜ƒ.getBlock()).getListenerRange(), this);
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.lastVibrationFrequency = â˜ƒ.getInt("last_vibration_frequency");
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.putInt("last_vibration_frequency", this.lastVibrationFrequency);
      return â˜ƒ;
   }

   public VibrationListener getListener() {
      return this.listener;
   }

   public int getLastVibrationFrequency() {
      return this.lastVibrationFrequency;
   }

   @Override
   public boolean shouldListen(Level var1, GameEventListener var2, BlockPos var3, GameEvent var4, @Nullable Entity var5) {
      boolean â˜ƒ = â˜ƒ == GameEvent.BLOCK_DESTROY && â˜ƒ.equals(this.getBlockPos());
      boolean â˜ƒx = â˜ƒ == GameEvent.BLOCK_PLACE && â˜ƒ.equals(this.getBlockPos());
      return !â˜ƒ && !â˜ƒx && SculkSensorBlock.canActivate(this.getBlockState());
   }

   @Override
   public void onSignalReceive(Level var1, GameEventListener var2, GameEvent var3, int var4) {
      BlockState â˜ƒ = this.getBlockState();
      if (!â˜ƒ.isClientSide() && SculkSensorBlock.canActivate(â˜ƒ)) {
         this.lastVibrationFrequency = SculkSensorBlock.VIBRATION_STRENGTH_FOR_EVENT.getInt(â˜ƒ);
         SculkSensorBlock.activate(â˜ƒ, this.worldPosition, â˜ƒ, getRedstoneStrengthForDistance(â˜ƒ, â˜ƒ.getListenerRadius()));
      }
   }

   public static int getRedstoneStrengthForDistance(int var0, int var1) {
      double â˜ƒ = (double)â˜ƒ / (double)â˜ƒ;
      return Math.max(1, 15 - Mth.floor(â˜ƒ * 15.0));
   }
}
