package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;
import net.minecraft.world.phys.Vec3;

public class VibrationSignalParticle extends TextureSheetParticle {
   private final VibrationPath vibrationPath;
   private float yRot;
   private float yRotO;

   VibrationSignalParticle(ClientLevel var1, VibrationPath var2, int var3) {
      super(
         â˜ƒ,
         (double)((float)â˜ƒ.getOrigin().getX() + 0.5F),
         (double)((float)â˜ƒ.getOrigin().getY() + 0.5F),
         (double)((float)â˜ƒ.getOrigin().getZ() + 0.5F),
         0.0,
         0.0,
         0.0
      );
      this.quadSize = 0.3F;
      this.vibrationPath = â˜ƒ;
      this.lifetime = â˜ƒ;
   }

   @Override
   public void render(VertexConsumer var1, Camera var2, float var3) {
      float â˜ƒ = Mth.sin(((float)this.age + â˜ƒ - (float) (Math.PI * 2)) * 0.05F) * 2.0F;
      float â˜ƒx = Mth.lerp(â˜ƒ, this.yRotO, this.yRot);
      float â˜ƒxx = 1.0472F;
      this.renderSignal(â˜ƒ, â˜ƒ, â˜ƒ, var2x -> {
         var2x.mul(Vector3f.YP.rotation(â˜ƒ));
         var2x.mul(Vector3f.XP.rotation(-1.0472F));
         var2x.mul(Vector3f.YP.rotation(â˜ƒ));
      });
      this.renderSignal(â˜ƒ, â˜ƒ, â˜ƒ, var2x -> {
         var2x.mul(Vector3f.YP.rotation((float) -Math.PI + â˜ƒ));
         var2x.mul(Vector3f.XP.rotation(1.0472F));
         var2x.mul(Vector3f.YP.rotation(â˜ƒ));
      });
   }

   private void renderSignal(VertexConsumer var1, Camera var2, float var3, Consumer<Quaternion> var4) {
      Vec3 â˜ƒ = â˜ƒ.getPosition();
      float â˜ƒx = (float)(Mth.lerp((double)â˜ƒ, this.xo, this.x) - â˜ƒ.x());
      float â˜ƒxx = (float)(Mth.lerp((double)â˜ƒ, this.yo, this.y) - â˜ƒ.y());
      float â˜ƒxxx = (float)(Mth.lerp((double)â˜ƒ, this.zo, this.z) - â˜ƒ.z());
      Vector3f â˜ƒxxxx = new Vector3f(0.5F, 0.5F, 0.5F);
      â˜ƒxxxx.normalize();
      Quaternion â˜ƒxxxxx = new Quaternion(â˜ƒxxxx, 0.0F, true);
      â˜ƒ.accept(â˜ƒxxxxx);
      Vector3f â˜ƒxxxxxx = new Vector3f(-1.0F, -1.0F, 0.0F);
      â˜ƒxxxxxx.transform(â˜ƒxxxxx);
      Vector3f[] â˜ƒxxxxxxx = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float â˜ƒxxxxxxxx = this.getQuadSize(â˜ƒ);

      for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 4; ++â˜ƒxxxxxxxxx) {
         Vector3f â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx[â˜ƒxxxxxxxxx];
         â˜ƒxxxxxxxxxx.transform(â˜ƒxxxxx);
         â˜ƒxxxxxxxxxx.mul(â˜ƒxxxxxxxx);
         â˜ƒxxxxxxxxxx.add(â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }

      float â˜ƒxxxxxxxxx = this.getU0();
      float â˜ƒxxxxxxxxxx = this.getU1();
      float â˜ƒxxxxxxxxxxx = this.getV0();
      float â˜ƒxxxxxxxxxxxx = this.getV1();
      int â˜ƒxxxxxxxxxxxxx = this.getLightColor(â˜ƒ);
      â˜ƒ.vertex((double)â˜ƒxxxxxxx[0].x(), (double)â˜ƒxxxxxxx[0].y(), (double)â˜ƒxxxxxxx[0].z())
         .uv(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx)
         .color(this.rCol, this.gCol, this.bCol, this.alpha)
         .uv2(â˜ƒxxxxxxxxxxxxx)
         .endVertex();
      â˜ƒ.vertex((double)â˜ƒxxxxxxx[1].x(), (double)â˜ƒxxxxxxx[1].y(), (double)â˜ƒxxxxxxx[1].z())
         .uv(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx)
         .color(this.rCol, this.gCol, this.bCol, this.alpha)
         .uv2(â˜ƒxxxxxxxxxxxxx)
         .endVertex();
      â˜ƒ.vertex((double)â˜ƒxxxxxxx[2].x(), (double)â˜ƒxxxxxxx[2].y(), (double)â˜ƒxxxxxxx[2].z())
         .uv(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxx)
         .color(this.rCol, this.gCol, this.bCol, this.alpha)
         .uv2(â˜ƒxxxxxxxxxxxxx)
         .endVertex();
      â˜ƒ.vertex((double)â˜ƒxxxxxxx[3].x(), (double)â˜ƒxxxxxxx[3].y(), (double)â˜ƒxxxxxxx[3].z())
         .uv(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxxx)
         .color(this.rCol, this.gCol, this.bCol, this.alpha)
         .uv2(â˜ƒxxxxxxxxxxxxx)
         .endVertex();
   }

   @Override
   public int getLightColor(float var1) {
      return 240;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   @Override
   public void tick() {
      super.tick();
      Optional<BlockPos> â˜ƒ = this.vibrationPath.getDestination().getPosition(this.level);
      if (!â˜ƒ.isPresent()) {
         this.remove();
      } else {
         double â˜ƒ = (double)this.age / (double)this.lifetime;
         BlockPos â˜ƒx = this.vibrationPath.getOrigin();
         BlockPos â˜ƒxx = (BlockPos)â˜ƒ.get();
         this.x = Mth.lerp(â˜ƒ, (double)â˜ƒx.getX() + 0.5, (double)â˜ƒxx.getX() + 0.5);
         this.y = Mth.lerp(â˜ƒ, (double)â˜ƒx.getY() + 0.5, (double)â˜ƒxx.getY() + 0.5);
         this.z = Mth.lerp(â˜ƒ, (double)â˜ƒx.getZ() + 0.5, (double)â˜ƒxx.getZ() + 0.5);
         this.yRotO = this.yRot;
         this.yRot = (float)Mth.atan2(this.x - (double)â˜ƒxx.getX(), this.z - (double)â˜ƒxx.getZ());
      }
   }

   public static class Provider implements ParticleProvider<VibrationParticleOption> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(
         VibrationParticleOption var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13
      ) {
         VibrationSignalParticle â˜ƒ = new VibrationSignalParticle(â˜ƒ, â˜ƒ.getVibrationPath(), â˜ƒ.getVibrationPath().getArrivalInTicks());
         â˜ƒ.pickSprite(this.sprite);
         â˜ƒ.setAlpha(1.0F);
         return â˜ƒ;
      }
   }
}
