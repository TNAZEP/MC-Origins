package net.minecraft.network.protocol.game;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.SynchedEntityData;

public class ClientboundSetEntityDataPacket implements Packet<ClientGamePacketListener> {
   private final int id;
   @Nullable
   private final List<SynchedEntityData.DataItem<?>> packedItems;

   public ClientboundSetEntityDataPacket(int var1, SynchedEntityData var2, boolean var3) {
      this.id = â˜ƒ;
      if (â˜ƒ) {
         this.packedItems = â˜ƒ.getAll();
         â˜ƒ.clearDirty();
      } else {
         this.packedItems = â˜ƒ.packDirty();
      }
   }

   public ClientboundSetEntityDataPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.packedItems = SynchedEntityData.unpack(â˜ƒ);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      SynchedEntityData.pack(this.packedItems, â˜ƒ);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetEntityData(this);
   }

   @Nullable
   public List<SynchedEntityData.DataItem<?>> getUnpackedData() {
      return this.packedItems;
   }

   public int getId() {
      return this.id;
   }
}
