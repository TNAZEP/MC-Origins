package net.minecraft.network.protocol.login;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.core.SerializableUUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundGameProfilePacket implements Packet<ClientLoginPacketListener> {
   private final GameProfile gameProfile;

   public ClientboundGameProfilePacket(GameProfile var1) {
      this.gameProfile = â˜ƒ;
   }

   public ClientboundGameProfilePacket(FriendlyByteBuf var1) {
      int[] â˜ƒ = new int[4];

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         â˜ƒ[â˜ƒx] = â˜ƒ.readInt();
      }

      UUID â˜ƒx = SerializableUUID.uuidFromIntArray(â˜ƒ);
      String â˜ƒxx = â˜ƒ.readUtf(16);
      this.gameProfile = new GameProfile(â˜ƒx, â˜ƒxx);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      for(int â˜ƒ : SerializableUUID.uuidToIntArray(this.gameProfile.getId())) {
         â˜ƒ.writeInt(â˜ƒ);
      }

      â˜ƒ.writeUtf(this.gameProfile.getName());
   }

   public void handle(ClientLoginPacketListener var1) {
      â˜ƒ.handleGameProfile(this);
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }
}
