package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.ChatVisiblity;

public class ServerboundClientInformationPacket implements Packet<ServerGamePacketListener> {
   public static final int MAX_LANGUAGE_LENGTH = 16;
   private final String language;
   private final int viewDistance;
   private final ChatVisiblity chatVisibility;
   private final boolean chatColors;
   private final int modelCustomisation;
   private final HumanoidArm mainHand;
   private final boolean textFilteringEnabled;

   public ServerboundClientInformationPacket(String var1, int var2, ChatVisiblity var3, boolean var4, int var5, HumanoidArm var6, boolean var7) {
      this.language = â˜ƒ;
      this.viewDistance = â˜ƒ;
      this.chatVisibility = â˜ƒ;
      this.chatColors = â˜ƒ;
      this.modelCustomisation = â˜ƒ;
      this.mainHand = â˜ƒ;
      this.textFilteringEnabled = â˜ƒ;
   }

   public ServerboundClientInformationPacket(FriendlyByteBuf var1) {
      this.language = â˜ƒ.readUtf(16);
      this.viewDistance = â˜ƒ.readByte();
      this.chatVisibility = â˜ƒ.readEnum(ChatVisiblity.class);
      this.chatColors = â˜ƒ.readBoolean();
      this.modelCustomisation = â˜ƒ.readUnsignedByte();
      this.mainHand = â˜ƒ.readEnum(HumanoidArm.class);
      this.textFilteringEnabled = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.language);
      â˜ƒ.writeByte(this.viewDistance);
      â˜ƒ.writeEnum(this.chatVisibility);
      â˜ƒ.writeBoolean(this.chatColors);
      â˜ƒ.writeByte(this.modelCustomisation);
      â˜ƒ.writeEnum(this.mainHand);
      â˜ƒ.writeBoolean(this.textFilteringEnabled);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleClientInformation(this);
   }

   public String getLanguage() {
      return this.language;
   }

   public int getViewDistance() {
      return this.viewDistance;
   }

   public ChatVisiblity getChatVisibility() {
      return this.chatVisibility;
   }

   public boolean getChatColors() {
      return this.chatColors;
   }

   public int getModelCustomisation() {
      return this.modelCustomisation;
   }

   public HumanoidArm getMainHand() {
      return this.mainHand;
   }

   public boolean isTextFilteringEnabled() {
      return this.textFilteringEnabled;
   }
}
