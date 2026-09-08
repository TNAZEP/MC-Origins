package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class TrackingEmitter extends NoRenderParticle {
   private final Entity entity;
   private int life;
   private final int lifeTime;
   private final ParticleOptions particleType;

   public TrackingEmitter(ClientLevel var1, Entity var2, ParticleOptions var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, 3);
   }

   public TrackingEmitter(ClientLevel var1, Entity var2, ParticleOptions var3, int var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getDeltaMovement());
   }

   private TrackingEmitter(ClientLevel var1, Entity var2, ParticleOptions var3, int var4, Vec3 var5) {
      super(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(0.5), â˜ƒ.getZ(), â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
      this.entity = â˜ƒ;
      this.lifeTime = â˜ƒ;
      this.particleType = â˜ƒ;
      this.tick();
   }

   @Override
   public void tick() {
      for(int â˜ƒ = 0; â˜ƒ < 16; ++â˜ƒ) {
         double â˜ƒx = (double)(this.random.nextFloat() * 2.0F - 1.0F);
         double â˜ƒxx = (double)(this.random.nextFloat() * 2.0F - 1.0F);
         double â˜ƒxxx = (double)(this.random.nextFloat() * 2.0F - 1.0F);
         if (!(â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx > 1.0)) {
            double â˜ƒxxxx = this.entity.getX(â˜ƒx / 4.0);
            double â˜ƒxxxxx = this.entity.getY(0.5 + â˜ƒxx / 4.0);
            double â˜ƒxxxxxx = this.entity.getZ(â˜ƒxxx / 4.0);
            this.level.addParticle(this.particleType, false, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒx, â˜ƒxx + 0.2, â˜ƒxxx);
         }
      }

      ++this.life;
      if (this.life >= this.lifeTime) {
         this.remove();
      }
   }
}
