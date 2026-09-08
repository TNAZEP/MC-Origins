package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ClientboundSetObjectivePacket implements Packet<ClientGamePacketListener> {
   public static final int METHOD_ADD = 0;
   public static final int METHOD_REMOVE = 1;
   public static final int METHOD_CHANGE = 2;
   private final String objectiveName;
   private final Component displayName;
   private final ObjectiveCriteria.RenderType renderType;
   private final int method;

   public ClientboundSetObjectivePacket(Objective var1, int var2) {
      this.objectiveName = â˜ƒ.getName();
      this.displayName = â˜ƒ.getDisplayName();
      this.renderType = â˜ƒ.getRenderType();
      this.method = â˜ƒ;
   }

   public ClientboundSetObjectivePacket(FriendlyByteBuf var1) {
      this.objectiveName = â˜ƒ.readUtf(16);
      this.method = â˜ƒ.readByte();
      if (this.method != 0 && this.method != 2) {
         this.displayName = TextComponent.EMPTY;
         this.renderType = ObjectiveCriteria.RenderType.INTEGER;
      } else {
         this.displayName = â˜ƒ.readComponent();
         this.renderType = â˜ƒ.readEnum(ObjectiveCriteria.RenderType.class);
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeUtf(this.objectiveName);
      â˜ƒ.writeByte(this.method);
      if (this.method == 0 || this.method == 2) {
         â˜ƒ.writeComponent(this.displayName);
         â˜ƒ.writeEnum(this.renderType);
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleAddObjective(this);
   }

   public String getObjectiveName() {
      return this.objectiveName;
   }

   public Component getDisplayName() {
      return this.displayName;
   }

   public int getMethod() {
      return this.method;
   }

   public ObjectiveCriteria.RenderType getRenderType() {
      return this.renderType;
   }
}
