package net.minecraft.network.protocol.game;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.apache.commons.lang3.Validate;

public class ClientboundSoundPacket implements Packet<ClientGamePacketListener> {
   public static final float LOCATION_ACCURACY = 8.0F;
   private final SoundEvent sound;
   private final SoundSource source;
   private final int x;
   private final int y;
   private final int z;
   private final float volume;
   private final float pitch;

   public ClientboundSoundPacket(SoundEvent var1, SoundSource var2, double var3, double var5, double var7, float var9, float var10) {
      Validate.notNull(â˜ƒ, "sound");
      this.sound = â˜ƒ;
      this.source = â˜ƒ;
      this.x = (int)(â˜ƒ * 8.0);
      this.y = (int)(â˜ƒ * 8.0);
      this.z = (int)(â˜ƒ * 8.0);
      this.volume = â˜ƒ;
      this.pitch = â˜ƒ;
   }

   public ClientboundSoundPacket(FriendlyByteBuf var1) {
      this.sound = Registry.SOUND_EVENT.byId(â˜ƒ.readVarInt());
      this.source = â˜ƒ.readEnum(SoundSource.class);
      this.x = â˜ƒ.readInt();
      this.y = â˜ƒ.readInt();
      this.z = â˜ƒ.readInt();
      this.volume = â˜ƒ.readFloat();
      this.pitch = â˜ƒ.readFloat();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(Registry.SOUND_EVENT.getId(this.sound));
      â˜ƒ.writeEnum(this.source);
      â˜ƒ.writeInt(this.x);
      â˜ƒ.writeInt(this.y);
      â˜ƒ.writeInt(this.z);
      â˜ƒ.writeFloat(this.volume);
      â˜ƒ.writeFloat(this.pitch);
   }

   public SoundEvent getSound() {
      return this.sound;
   }

   public SoundSource getSource() {
      return this.source;
   }

   public double getX() {
      return (double)((float)this.x / 8.0F);
   }

   public double getY() {
      return (double)((float)this.y / 8.0F);
   }

   public double getZ() {
      return (double)((float)this.z / 8.0F);
   }

   public float getVolume() {
      return this.volume;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSoundEvent(this);
   }
}
