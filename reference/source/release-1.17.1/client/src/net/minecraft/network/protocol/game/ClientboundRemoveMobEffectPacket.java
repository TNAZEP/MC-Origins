package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientboundRemoveMobEffectPacket implements Packet<ClientGamePacketListener> {
   private final int entityId;
   private final MobEffect effect;

   public ClientboundRemoveMobEffectPacket(int var1, MobEffect var2) {
      this.entityId = â˜ƒ;
      this.effect = â˜ƒ;
   }

   public ClientboundRemoveMobEffectPacket(FriendlyByteBuf var1) {
      this.entityId = â˜ƒ.readVarInt();
      this.effect = MobEffect.byId(â˜ƒ.readUnsignedByte());
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.entityId);
      â˜ƒ.writeByte(MobEffect.getId(this.effect));
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleRemoveMobEffect(this);
   }

   @Nullable
   public Entity getEntity(Level var1) {
      return â˜ƒ.getEntity(this.entityId);
   }

   @Nullable
   public MobEffect getEffect() {
      return this.effect;
   }
}
