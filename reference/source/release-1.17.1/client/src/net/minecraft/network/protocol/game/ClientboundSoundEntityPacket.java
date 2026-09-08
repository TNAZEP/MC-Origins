package net.minecraft.network.protocol.game;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.Validate;

public class ClientboundSoundEntityPacket implements Packet<ClientGamePacketListener> {
   private final SoundEvent sound;
   private final SoundSource source;
   private final int id;
   private final float volume;
   private final float pitch;

   public ClientboundSoundEntityPacket(SoundEvent var1, SoundSource var2, Entity var3, float var4, float var5) {
      Validate.notNull(â˜ƒ, "sound");
      this.sound = â˜ƒ;
      this.source = â˜ƒ;
      this.id = â˜ƒ.getId();
      this.volume = â˜ƒ;
      this.pitch = â˜ƒ;
   }

   public ClientboundSoundEntityPacket(FriendlyByteBuf var1) {
      this.sound = Registry.SOUND_EVENT.byId(â˜ƒ.readVarInt());
      this.source = â˜ƒ.readEnum(SoundSource.class);
      this.id = â˜ƒ.readVarInt();
      this.volume = â˜ƒ.readFloat();
      this.pitch = â˜ƒ.readFloat();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(Registry.SOUND_EVENT.getId(this.sound));
      â˜ƒ.writeEnum(this.source);
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeFloat(this.volume);
      â˜ƒ.writeFloat(this.pitch);
   }

   public SoundEvent getSound() {
      return this.sound;
   }

   public SoundSource getSource() {
      return this.source;
   }

   public int getId() {
      return this.id;
   }

   public float getVolume() {
      return this.volume;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSoundEntityEvent(this);
   }
}
