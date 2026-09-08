package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;

public class ClientboundResourcePackPacket implements Packet<ClientGamePacketListener> {
   public static final int MAX_HASH_LENGTH = 40;
   private final String url;
   private final String hash;
   private final boolean required;
   @Nullable
   private final Component prompt;

   public ClientboundResourcePackPacket(String var1, String var2, boolean var3, @Nullable Component var4) {
      if (â˜ƒ.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + â˜ƒ.length() + ")");
      } else {
         this.url = â˜ƒ;
         this.hash = â˜ƒ;
         this.required = â˜ƒ;
         this.prompt = â˜ƒ;
      }
   }

   public ClientboundResourcePackPacket(FriendlyByteBuf var1) {
      this.url = â˜ƒ.readUtf();
      this.hash = â˜ƒ.readUtf(40);
      this.required = â˜ƒ.readBoolean();
      if (â˜ƒ.readBoolean()) {
         this.prompt = â˜ƒ.readComponent();
      } else {
         this.prompt = null;
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.url);
      â˜ƒ.writeUtf(this.hash);
      â˜ƒ.writeBoolean(this.required);
      if (this.prompt != null) {
         â˜ƒ.writeBoolean(true);
         â˜ƒ.writeComponent(this.prompt);
      } else {
         â˜ƒ.writeBoolean(false);
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleResourcePack(this);
   }

   public String getUrl() {
      return this.url;
   }

   public String getHash() {
      return this.hash;
   }

   public boolean isRequired() {
      return this.required;
   }

   @Nullable
   public Component getPrompt() {
      return this.prompt;
   }
}
