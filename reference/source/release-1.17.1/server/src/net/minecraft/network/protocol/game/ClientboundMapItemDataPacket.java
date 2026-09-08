package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class ClientboundMapItemDataPacket implements Packet<ClientGamePacketListener> {
   private final int mapId;
   private final byte scale;
   private final boolean locked;
   @Nullable
   private final List<MapDecoration> decorations;
   @Nullable
   private final MapItemSavedData.MapPatch colorPatch;

   public ClientboundMapItemDataPacket(int var1, byte var2, boolean var3, @Nullable Collection<MapDecoration> var4, @Nullable MapItemSavedData.MapPatch var5) {
      this.mapId = â˜ƒ;
      this.scale = â˜ƒ;
      this.locked = â˜ƒ;
      this.decorations = â˜ƒ != null ? Lists.<MapDecoration>newArrayList(â˜ƒ) : null;
      this.colorPatch = â˜ƒ;
   }

   public ClientboundMapItemDataPacket(FriendlyByteBuf var1) {
      this.mapId = â˜ƒ.readVarInt();
      this.scale = â˜ƒ.readByte();
      this.locked = â˜ƒ.readBoolean();
      if (â˜ƒ.readBoolean()) {
         this.decorations = â˜ƒ.readList(var0 -> {
            MapDecoration.Type â˜ƒ = var0.readEnum(MapDecoration.Type.class);
            return new MapDecoration(â˜ƒ, var0.readByte(), var0.readByte(), (byte)(var0.readByte() & 15), var0.readBoolean() ? var0.readComponent() : null);
         });
      } else {
         this.decorations = null;
      }

      int â˜ƒ = â˜ƒ.readUnsignedByte();
      if (â˜ƒ > 0) {
         int â˜ƒx = â˜ƒ.readUnsignedByte();
         int â˜ƒxx = â˜ƒ.readUnsignedByte();
         int â˜ƒxxx = â˜ƒ.readUnsignedByte();
         byte[] â˜ƒxxxx = â˜ƒ.readByteArray();
         this.colorPatch = new MapItemSavedData.MapPatch(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒx, â˜ƒxxxx);
      } else {
         this.colorPatch = null;
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.mapId);
      â˜ƒ.writeByte(this.scale);
      â˜ƒ.writeBoolean(this.locked);
      if (this.decorations != null) {
         â˜ƒ.writeBoolean(true);
         â˜ƒ.writeCollection(this.decorations, (var0, var1x) -> {
            var0.writeEnum(var1x.getType());
            var0.writeByte(var1x.getX());
            var0.writeByte(var1x.getY());
            var0.writeByte(var1x.getRot() & 15);
            if (var1x.getName() != null) {
               var0.writeBoolean(true);
               var0.writeComponent(var1x.getName());
            } else {
               var0.writeBoolean(false);
            }
         });
      } else {
         â˜ƒ.writeBoolean(false);
      }

      if (this.colorPatch != null) {
         â˜ƒ.writeByte(this.colorPatch.width);
         â˜ƒ.writeByte(this.colorPatch.height);
         â˜ƒ.writeByte(this.colorPatch.startX);
         â˜ƒ.writeByte(this.colorPatch.startY);
         â˜ƒ.writeByteArray(this.colorPatch.mapColors);
      } else {
         â˜ƒ.writeByte(0);
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleMapItemData(this);
   }

   public int getMapId() {
      return this.mapId;
   }

   public void applyToMap(MapItemSavedData var1) {
      if (this.decorations != null) {
         â˜ƒ.addClientSideDecorations(this.decorations);
      }

      if (this.colorPatch != null) {
         this.colorPatch.applyToMap(â˜ƒ);
      }
   }

   public byte getScale() {
      return this.scale;
   }

   public boolean isLocked() {
      return this.locked;
   }
}
