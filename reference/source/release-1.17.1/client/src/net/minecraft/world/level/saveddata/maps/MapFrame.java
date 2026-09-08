package net.minecraft.world.level.saveddata.maps;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

public class MapFrame {
   private final BlockPos pos;
   private final int rotation;
   private final int entityId;

   public MapFrame(BlockPos var1, int var2, int var3) {
      this.pos = â˜ƒ;
      this.rotation = â˜ƒ;
      this.entityId = â˜ƒ;
   }

   public static MapFrame load(CompoundTag var0) {
      BlockPos â˜ƒ = NbtUtils.readBlockPos(â˜ƒ.getCompound("Pos"));
      int â˜ƒx = â˜ƒ.getInt("Rotation");
      int â˜ƒxx = â˜ƒ.getInt("EntityId");
      return new MapFrame(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public CompoundTag save() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.put("Pos", NbtUtils.writeBlockPos(this.pos));
      â˜ƒ.putInt("Rotation", this.rotation);
      â˜ƒ.putInt("EntityId", this.entityId);
      return â˜ƒ;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public int getRotation() {
      return this.rotation;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public String getId() {
      return frameId(this.pos);
   }

   public static String frameId(BlockPos var0) {
      return "frame-" + â˜ƒ.getX() + "," + â˜ƒ.getY() + "," + â˜ƒ.getZ();
   }
}
