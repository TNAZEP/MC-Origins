package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;

public class ClientboundCustomSoundPacket implements Packet<ClientGamePacketListener> {
   public static final float LOCATION_ACCURACY = 8.0F;
   private final ResourceLocation name;
   private final SoundSource source;
   private final int x;
   private final int y;
   private final int z;
   private final float volume;
   private final float pitch;

   public ClientboundCustomSoundPacket(ResourceLocation var1, SoundSource var2, Vec3 var3, float var4, float var5) {
      this.name = â˜ƒ;
      this.source = â˜ƒ;
      this.x = (int)(â˜ƒ.x * 8.0);
      this.y = (int)(â˜ƒ.y * 8.0);
      this.z = (int)(â˜ƒ.z * 8.0);
      this.volume = â˜ƒ;
      this.pitch = â˜ƒ;
   }

   public ClientboundCustomSoundPacket(FriendlyByteBuf var1) {
      this.name = â˜ƒ.readResourceLocation();
      this.source = â˜ƒ.readEnum(SoundSource.class);
      this.x = â˜ƒ.readInt();
      this.y = â˜ƒ.readInt();
      this.z = â˜ƒ.readInt();
      this.volume = â˜ƒ.readFloat();
      this.pitch = â˜ƒ.readFloat();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeResourceLocation(this.name);
      â˜ƒ.writeEnum(this.source);
      â˜ƒ.writeInt(this.x);
      â˜ƒ.writeInt(this.y);
      â˜ƒ.writeInt(this.z);
      â˜ƒ.writeFloat(this.volume);
      â˜ƒ.writeFloat(this.pitch);
   }

   public ResourceLocation getName() {
      return this.name;
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
      â˜ƒ.handleCustomSoundEvent(this);
   }
}
