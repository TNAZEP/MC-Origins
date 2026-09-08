package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SpawnerBlockEntity extends BlockEntity {
   private final BaseSpawner spawner = new BaseSpawner() {
      @Override
      public void broadcastEvent(Level var1, BlockPos var2, int var3) {
         â˜ƒ.blockEvent(â˜ƒ, Blocks.SPAWNER, â˜ƒ, 0);
      }

      @Override
      public void setNextSpawnData(@Nullable Level var1, BlockPos var2, SpawnData var3) {
         super.setNextSpawnData(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
            â˜ƒ.sendBlockUpdated(â˜ƒ, â˜ƒ, â˜ƒ, 4);
         }
      }
   };

   public SpawnerBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.MOB_SPAWNER, â˜ƒ, â˜ƒ);
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.spawner.load(this.level, this.worldPosition, â˜ƒ);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      this.spawner.save(this.level, this.worldPosition, â˜ƒ);
      return â˜ƒ;
   }

   public static void clientTick(Level var0, BlockPos var1, BlockState var2, SpawnerBlockEntity var3) {
      â˜ƒ.spawner.clientTick(â˜ƒ, â˜ƒ);
   }

   public static void serverTick(Level var0, BlockPos var1, BlockState var2, SpawnerBlockEntity var3) {
      â˜ƒ.spawner.serverTick((ServerLevel)â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 1, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      CompoundTag â˜ƒ = this.save(new CompoundTag());
      â˜ƒ.remove("SpawnPotentials");
      return â˜ƒ;
   }

   @Override
   public boolean triggerEvent(int var1, int var2) {
      return this.spawner.onEventTriggered(this.level, â˜ƒ) ? true : super.triggerEvent(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean onlyOpCanSetNbt() {
      return true;
   }

   public BaseSpawner getSpawner() {
      return this.spawner;
   }
}
