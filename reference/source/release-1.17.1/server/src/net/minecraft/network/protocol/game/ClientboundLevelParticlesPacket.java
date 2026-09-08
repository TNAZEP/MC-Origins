package net.minecraft.network.protocol.game;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundLevelParticlesPacket implements Packet<ClientGamePacketListener> {
   private final double x;
   private final double y;
   private final double z;
   private final float xDist;
   private final float yDist;
   private final float zDist;
   private final float maxSpeed;
   private final int count;
   private final boolean overrideLimiter;
   private final ParticleOptions particle;

   public <T extends ParticleOptions> ClientboundLevelParticlesPacket(
      T var1, boolean var2, double var3, double var5, double var7, float var9, float var10, float var11, float var12, int var13
   ) {
      this.particle = â˜ƒ;
      this.overrideLimiter = â˜ƒ;
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.xDist = â˜ƒ;
      this.yDist = â˜ƒ;
      this.zDist = â˜ƒ;
      this.maxSpeed = â˜ƒ;
      this.count = â˜ƒ;
   }

   public ClientboundLevelParticlesPacket(FriendlyByteBuf var1) {
      ParticleType<?> â˜ƒ = Registry.PARTICLE_TYPE.byId(â˜ƒ.readInt());
      if (â˜ƒ == null) {
         â˜ƒ = ParticleTypes.BARRIER;
      }

      this.overrideLimiter = â˜ƒ.readBoolean();
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.xDist = â˜ƒ.readFloat();
      this.yDist = â˜ƒ.readFloat();
      this.zDist = â˜ƒ.readFloat();
      this.maxSpeed = â˜ƒ.readFloat();
      this.count = â˜ƒ.readInt();
      this.particle = this.readParticle(â˜ƒ, â˜ƒ);
   }

   private <T extends ParticleOptions> T readParticle(FriendlyByteBuf var1, ParticleType<T> var2) {
      return â˜ƒ.getDeserializer().fromNetwork(â˜ƒ, â˜ƒ);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(Registry.PARTICLE_TYPE.getId(this.particle.getType()));
      â˜ƒ.writeBoolean(this.overrideLimiter);
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeFloat(this.xDist);
      â˜ƒ.writeFloat(this.yDist);
      â˜ƒ.writeFloat(this.zDist);
      â˜ƒ.writeFloat(this.maxSpeed);
      â˜ƒ.writeInt(this.count);
      this.particle.writeToNetwork(â˜ƒ);
   }

   public boolean isOverrideLimiter() {
      return this.overrideLimiter;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public float getXDist() {
      return this.xDist;
   }

   public float getYDist() {
      return this.yDist;
   }

   public float getZDist() {
      return this.zDist;
   }

   public float getMaxSpeed() {
      return this.maxSpeed;
   }

   public int getCount() {
      return this.count;
   }

   public ParticleOptions getParticle() {
      return this.particle;
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleParticleEvent(this);
   }
}
