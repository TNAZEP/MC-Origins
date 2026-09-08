package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.util.RewindableStream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public abstract class Particle {
   private static final AABB INITIAL_AABB = new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   protected final ClientLevel level;
   protected double xo;
   protected double yo;
   protected double zo;
   protected double x;
   protected double y;
   protected double z;
   protected double xd;
   protected double yd;
   protected double zd;
   private AABB bb = INITIAL_AABB;
   protected boolean onGround;
   protected boolean hasPhysics = true;
   private boolean stoppedByCollision;
   protected boolean removed;
   protected float bbWidth = 0.6F;
   protected float bbHeight = 1.8F;
   protected final Random random = new Random();
   protected int age;
   protected int lifetime;
   protected float gravity;
   protected float rCol = 1.0F;
   protected float gCol = 1.0F;
   protected float bCol = 1.0F;
   protected float alpha = 1.0F;
   protected float roll;
   protected float oRoll;
   protected float friction = 0.98F;
   protected boolean speedUpWhenYMotionIsBlocked = false;

   protected Particle(ClientLevel var1, double var2, double var4, double var6) {
      this.level = â˜ƒ;
      this.setSize(0.2F, 0.2F);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.xo = â˜ƒ;
      this.yo = â˜ƒ;
      this.zo = â˜ƒ;
      this.lifetime = (int)(4.0F / (this.random.nextFloat() * 0.9F + 0.1F));
   }

   public Particle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.xd = â˜ƒ + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.yd = â˜ƒ + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.zd = â˜ƒ + (Math.random() * 2.0 - 1.0) * 0.4F;
      double â˜ƒ = (Math.random() + Math.random() + 1.0) * 0.15F;
      double â˜ƒx = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
      this.xd = this.xd / â˜ƒx * â˜ƒ * 0.4F;
      this.yd = this.yd / â˜ƒx * â˜ƒ * 0.4F + 0.1F;
      this.zd = this.zd / â˜ƒx * â˜ƒ * 0.4F;
   }

   public Particle setPower(float var1) {
      this.xd *= (double)â˜ƒ;
      this.yd = (this.yd - 0.1F) * (double)â˜ƒ + 0.1F;
      this.zd *= (double)â˜ƒ;
      return this;
   }

   public void setParticleSpeed(double var1, double var3, double var5) {
      this.xd = â˜ƒ;
      this.yd = â˜ƒ;
      this.zd = â˜ƒ;
   }

   public Particle scale(float var1) {
      this.setSize(0.2F * â˜ƒ, 0.2F * â˜ƒ);
      return this;
   }

   public void setColor(float var1, float var2, float var3) {
      this.rCol = â˜ƒ;
      this.gCol = â˜ƒ;
      this.bCol = â˜ƒ;
   }

   protected void setAlpha(float var1) {
      this.alpha = â˜ƒ;
   }

   public void setLifetime(int var1) {
      this.lifetime = â˜ƒ;
   }

   public int getLifetime() {
      return this.lifetime;
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.yd -= 0.04 * (double)this.gravity;
         this.move(this.xd, this.yd, this.zd);
         if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
            this.xd *= 1.1;
            this.zd *= 1.1;
         }

         this.xd *= (double)this.friction;
         this.yd *= (double)this.friction;
         this.zd *= (double)this.friction;
         if (this.onGround) {
            this.xd *= 0.7F;
            this.zd *= 0.7F;
         }
      }
   }

   public abstract void render(VertexConsumer var1, Camera var2, float var3);

   public abstract ParticleRenderType getRenderType();

   public String toString() {
      return this.getClass().getSimpleName()
         + ", Pos ("
         + this.x
         + ","
         + this.y
         + ","
         + this.z
         + "), RGBA ("
         + this.rCol
         + ","
         + this.gCol
         + ","
         + this.bCol
         + ","
         + this.alpha
         + "), Age "
         + this.age;
   }

   public void remove() {
      this.removed = true;
   }

   protected void setSize(float var1, float var2) {
      if (â˜ƒ != this.bbWidth || â˜ƒ != this.bbHeight) {
         this.bbWidth = â˜ƒ;
         this.bbHeight = â˜ƒ;
         AABB â˜ƒ = this.getBoundingBox();
         double â˜ƒx = (â˜ƒ.minX + â˜ƒ.maxX - (double)â˜ƒ) / 2.0;
         double â˜ƒxx = (â˜ƒ.minZ + â˜ƒ.maxZ - (double)â˜ƒ) / 2.0;
         this.setBoundingBox(new AABB(â˜ƒx, â˜ƒ.minY, â˜ƒxx, â˜ƒx + (double)this.bbWidth, â˜ƒ.minY + (double)this.bbHeight, â˜ƒxx + (double)this.bbWidth));
      }
   }

   public void setPos(double var1, double var3, double var5) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      float â˜ƒ = this.bbWidth / 2.0F;
      float â˜ƒx = this.bbHeight;
      this.setBoundingBox(new AABB(â˜ƒ - (double)â˜ƒ, â˜ƒ, â˜ƒ - (double)â˜ƒ, â˜ƒ + (double)â˜ƒ, â˜ƒ + (double)â˜ƒx, â˜ƒ + (double)â˜ƒ));
   }

   public void move(double var1, double var3, double var5) {
      if (!this.stoppedByCollision) {
         double â˜ƒ = â˜ƒ;
         double â˜ƒx = â˜ƒ;
         double â˜ƒxx = â˜ƒ;
         if (this.hasPhysics && (â˜ƒ != 0.0 || â˜ƒ != 0.0 || â˜ƒ != 0.0)) {
            Vec3 â˜ƒxxx = Entity.collideBoundingBoxHeuristically(
               null, new Vec3(â˜ƒ, â˜ƒ, â˜ƒ), this.getBoundingBox(), this.level, CollisionContext.empty(), new RewindableStream<>(Stream.empty())
            );
            â˜ƒ = â˜ƒxxx.x;
            â˜ƒ = â˜ƒxxx.y;
            â˜ƒ = â˜ƒxxx.z;
         }

         if (â˜ƒ != 0.0 || â˜ƒ != 0.0 || â˜ƒ != 0.0) {
            this.setBoundingBox(this.getBoundingBox().move(â˜ƒ, â˜ƒ, â˜ƒ));
            this.setLocationFromBoundingbox();
         }

         if (Math.abs(â˜ƒx) >= 1.0E-5F && Math.abs(â˜ƒ) < 1.0E-5F) {
            this.stoppedByCollision = true;
         }

         this.onGround = â˜ƒx != â˜ƒ && â˜ƒx < 0.0;
         if (â˜ƒ != â˜ƒ) {
            this.xd = 0.0;
         }

         if (â˜ƒxx != â˜ƒ) {
            this.zd = 0.0;
         }
      }
   }

   protected void setLocationFromBoundingbox() {
      AABB â˜ƒ = this.getBoundingBox();
      this.x = (â˜ƒ.minX + â˜ƒ.maxX) / 2.0;
      this.y = â˜ƒ.minY;
      this.z = (â˜ƒ.minZ + â˜ƒ.maxZ) / 2.0;
   }

   protected int getLightColor(float var1) {
      BlockPos â˜ƒ = new BlockPos(this.x, this.y, this.z);
      return this.level.hasChunkAt(â˜ƒ) ? LevelRenderer.getLightColor(this.level, â˜ƒ) : 0;
   }

   public boolean isAlive() {
      return !this.removed;
   }

   public AABB getBoundingBox() {
      return this.bb;
   }

   public void setBoundingBox(AABB var1) {
      this.bb = â˜ƒ;
   }

   public Optional<ParticleGroup> getParticleGroup() {
      return Optional.empty();
   }
}
