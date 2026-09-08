package net.minecraft.world.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;

public class Marker extends Entity {
   private static final String DATA_TAG = "data";
   private CompoundTag data = new CompoundTag();

   public Marker(EntityType<?> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.noPhysics = true;
   }

   @Override
   public void tick() {
   }

   @Override
   protected void defineSynchedData() {
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      this.data = â˜ƒ.getCompound("data");
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.put("data", this.data.copy());
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      throw new IllegalStateException("Markers should never be sent");
   }

   @Override
   protected void addPassenger(Entity var1) {
      â˜ƒ.stopRiding();
   }
}
