package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public abstract class SingleQuadParticle extends Particle {
   protected float quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;

   protected SingleQuadParticle(ClientLevel var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected SingleQuadParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void render(VertexConsumer var1, Camera var2, float var3) {
      Vec3 â˜ƒx = â˜ƒ.getPosition();
      float â˜ƒxx = (float)(Mth.lerp((double)â˜ƒ, this.xo, this.x) - â˜ƒx.x());
      float â˜ƒxxx = (float)(Mth.lerp((double)â˜ƒ, this.yo, this.y) - â˜ƒx.y());
      float â˜ƒxxxx = (float)(Mth.lerp((double)â˜ƒ, this.zo, this.z) - â˜ƒx.z());
      Quaternion â˜ƒ;
      if (this.roll == 0.0F) {
         â˜ƒ = â˜ƒ.rotation();
      } else {
         â˜ƒ = new Quaternion(â˜ƒ.rotation());
         float â˜ƒ = Mth.lerp(â˜ƒ, this.oRoll, this.roll);
         â˜ƒ.mul(Vector3f.ZP.rotation(â˜ƒ));
      }

      Vector3f â˜ƒ = new Vector3f(-1.0F, -1.0F, 0.0F);
      â˜ƒ.transform(â˜ƒ);
      Vector3f[] â˜ƒx = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float â˜ƒxx = this.getQuadSize(â˜ƒ);

      for(int â˜ƒxxx = 0; â˜ƒxxx < 4; ++â˜ƒxxx) {
         Vector3f â˜ƒxxxx = â˜ƒx[â˜ƒxxx];
         â˜ƒxxxx.transform(â˜ƒ);
         â˜ƒxxxx.mul(â˜ƒxx);
         â˜ƒxxxx.add(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      }

      float â˜ƒxxx = this.getU0();
      float â˜ƒxxxx = this.getU1();
      float â˜ƒxxxxx = this.getV0();
      float â˜ƒxxxxxx = this.getV1();
      int â˜ƒxxxxxxx = this.getLightColor(â˜ƒ);
      â˜ƒ.vertex((double)â˜ƒx[0].x(), (double)â˜ƒx[0].y(), (double)â˜ƒx[0].z())
         .uv(â˜ƒxxxx, â˜ƒxxxxxx)
         .color(this.rCol, this.gCol, this.bCol, this.alpha)
         .uv2(â˜ƒxxxxxxx)
         .endVertex();
      â˜ƒ.vertex((double)â˜ƒx[1].x(), (double)â˜ƒx[1].y(), (double)â˜ƒx[1].z())
         .uv(â˜ƒxxxx, â˜ƒxxxxx)
         .color(this.rCol, this.gCol, this.bCol, this.alpha)
         .uv2(â˜ƒxxxxxxx)
         .endVertex();
      â˜ƒ.vertex((double)â˜ƒx[2].x(), (double)â˜ƒx[2].y(), (double)â˜ƒx[2].z())
         .uv(â˜ƒxxx, â˜ƒxxxxx)
         .color(this.rCol, this.gCol, this.bCol, this.alpha)
         .uv2(â˜ƒxxxxxxx)
         .endVertex();
      â˜ƒ.vertex((double)â˜ƒx[3].x(), (double)â˜ƒx[3].y(), (double)â˜ƒx[3].z())
         .uv(â˜ƒxxx, â˜ƒxxxxxx)
         .color(this.rCol, this.gCol, this.bCol, this.alpha)
         .uv2(â˜ƒxxxxxxx)
         .endVertex();
   }

   public float getQuadSize(float var1) {
      return this.quadSize;
   }

   @Override
   public Particle scale(float var1) {
      this.quadSize *= â˜ƒ;
      return super.scale(â˜ƒ);
   }

   protected abstract float getU0();

   protected abstract float getU1();

   protected abstract float getV0();

   protected abstract float getV1();
}
