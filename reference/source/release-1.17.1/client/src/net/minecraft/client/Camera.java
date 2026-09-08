package net.minecraft.client;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Camera {
   private boolean initialized;
   private BlockGetter level;
   private Entity entity;
   private Vec3 position = Vec3.ZERO;
   private final BlockPos.MutableBlockPos blockPosition = new BlockPos.MutableBlockPos();
   private final Vector3f forwards = new Vector3f(0.0F, 0.0F, 1.0F);
   private final Vector3f up = new Vector3f(0.0F, 1.0F, 0.0F);
   private final Vector3f left = new Vector3f(1.0F, 0.0F, 0.0F);
   private float xRot;
   private float yRot;
   private final Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
   private boolean detached;
   private float eyeHeight;
   private float eyeHeightOld;
   public static final float FOG_DISTANCE_SCALE = 0.083333336F;

   public void setup(BlockGetter var1, Entity var2, boolean var3, boolean var4, float var5) {
      this.initialized = true;
      this.level = â˜ƒ;
      this.entity = â˜ƒ;
      this.detached = â˜ƒ;
      this.setRotation(â˜ƒ.getViewYRot(â˜ƒ), â˜ƒ.getViewXRot(â˜ƒ));
      this.setPosition(
         Mth.lerp((double)â˜ƒ, â˜ƒ.xo, â˜ƒ.getX()),
         Mth.lerp((double)â˜ƒ, â˜ƒ.yo, â˜ƒ.getY()) + (double)Mth.lerp(â˜ƒ, this.eyeHeightOld, this.eyeHeight),
         Mth.lerp((double)â˜ƒ, â˜ƒ.zo, â˜ƒ.getZ())
      );
      if (â˜ƒ) {
         if (â˜ƒ) {
            this.setRotation(this.yRot + 180.0F, -this.xRot);
         }

         this.move(-this.getMaxZoom(4.0), 0.0, 0.0);
      } else if (â˜ƒ instanceof LivingEntity && ((LivingEntity)â˜ƒ).isSleeping()) {
         Direction â˜ƒ = ((LivingEntity)â˜ƒ).getBedOrientation();
         this.setRotation(â˜ƒ != null ? â˜ƒ.toYRot() - 180.0F : 0.0F, 0.0F);
         this.move(0.0, 0.3, 0.0);
      }
   }

   public void tick() {
      if (this.entity != null) {
         this.eyeHeightOld = this.eyeHeight;
         this.eyeHeight += (this.entity.getEyeHeight() - this.eyeHeight) * 0.5F;
      }
   }

   private double getMaxZoom(double var1) {
      for(int â˜ƒ = 0; â˜ƒ < 8; ++â˜ƒ) {
         float â˜ƒx = (float)((â˜ƒ & 1) * 2 - 1);
         float â˜ƒxx = (float)((â˜ƒ >> 1 & 1) * 2 - 1);
         float â˜ƒxxx = (float)((â˜ƒ >> 2 & 1) * 2 - 1);
         â˜ƒx *= 0.1F;
         â˜ƒxx *= 0.1F;
         â˜ƒxxx *= 0.1F;
         Vec3 â˜ƒxxxx = this.position.add((double)â˜ƒx, (double)â˜ƒxx, (double)â˜ƒxxx);
         Vec3 â˜ƒxxxxx = new Vec3(
            this.position.x - (double)this.forwards.x() * â˜ƒ + (double)â˜ƒx + (double)â˜ƒxxx,
            this.position.y - (double)this.forwards.y() * â˜ƒ + (double)â˜ƒxx,
            this.position.z - (double)this.forwards.z() * â˜ƒ + (double)â˜ƒxxx
         );
         HitResult â˜ƒxxxxxx = this.level.clip(new ClipContext(â˜ƒxxxx, â˜ƒxxxxx, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, this.entity));
         if (â˜ƒxxxxxx.getType() != HitResult.Type.MISS) {
            double â˜ƒxxxxxxx = â˜ƒxxxxxx.getLocation().distanceTo(this.position);
            if (â˜ƒxxxxxxx < â˜ƒ) {
               â˜ƒ = â˜ƒxxxxxxx;
            }
         }
      }

      return â˜ƒ;
   }

   protected void move(double var1, double var3, double var5) {
      double â˜ƒ = (double)this.forwards.x() * â˜ƒ + (double)this.up.x() * â˜ƒ + (double)this.left.x() * â˜ƒ;
      double â˜ƒx = (double)this.forwards.y() * â˜ƒ + (double)this.up.y() * â˜ƒ + (double)this.left.y() * â˜ƒ;
      double â˜ƒxx = (double)this.forwards.z() * â˜ƒ + (double)this.up.z() * â˜ƒ + (double)this.left.z() * â˜ƒ;
      this.setPosition(new Vec3(this.position.x + â˜ƒ, this.position.y + â˜ƒx, this.position.z + â˜ƒxx));
   }

   protected void setRotation(float var1, float var2) {
      this.xRot = â˜ƒ;
      this.yRot = â˜ƒ;
      this.rotation.set(0.0F, 0.0F, 0.0F, 1.0F);
      this.rotation.mul(Vector3f.YP.rotationDegrees(-â˜ƒ));
      this.rotation.mul(Vector3f.XP.rotationDegrees(â˜ƒ));
      this.forwards.set(0.0F, 0.0F, 1.0F);
      this.forwards.transform(this.rotation);
      this.up.set(0.0F, 1.0F, 0.0F);
      this.up.transform(this.rotation);
      this.left.set(1.0F, 0.0F, 0.0F);
      this.left.transform(this.rotation);
   }

   protected void setPosition(double var1, double var3, double var5) {
      this.setPosition(new Vec3(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   protected void setPosition(Vec3 var1) {
      this.position = â˜ƒ;
      this.blockPosition.set(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public Vec3 getPosition() {
      return this.position;
   }

   public BlockPos getBlockPosition() {
      return this.blockPosition;
   }

   public float getXRot() {
      return this.xRot;
   }

   public float getYRot() {
      return this.yRot;
   }

   public Quaternion rotation() {
      return this.rotation;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public boolean isDetached() {
      return this.detached;
   }

   public Camera.NearPlane getNearPlane() {
      Minecraft â˜ƒ = Minecraft.getInstance();
      double â˜ƒx = (double)â˜ƒ.getWindow().getWidth() / (double)â˜ƒ.getWindow().getHeight();
      double â˜ƒxx = Math.tan(â˜ƒ.options.fov * (float) (Math.PI / 180.0) / 2.0) * 0.05F;
      double â˜ƒxxx = â˜ƒxx * â˜ƒx;
      Vec3 â˜ƒxxxx = new Vec3(this.forwards).scale(0.05F);
      Vec3 â˜ƒxxxxx = new Vec3(this.left).scale(â˜ƒxxx);
      Vec3 â˜ƒxxxxxx = new Vec3(this.up).scale(â˜ƒxx);
      return new Camera.NearPlane(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
   }

   public FogType getFluidInCamera() {
      if (!this.initialized) {
         return FogType.NONE;
      } else {
         FluidState â˜ƒ = this.level.getFluidState(this.blockPosition);
         if (â˜ƒ.is(FluidTags.WATER) && this.position.y < (double)((float)this.blockPosition.getY() + â˜ƒ.getHeight(this.level, this.blockPosition))) {
            return FogType.WATER;
         } else {
            Camera.NearPlane â˜ƒ = this.getNearPlane();

            for(Vec3 â˜ƒx : Arrays.asList(â˜ƒ.forward, â˜ƒ.getTopLeft(), â˜ƒ.getTopRight(), â˜ƒ.getBottomLeft(), â˜ƒ.getBottomRight())) {
               Vec3 â˜ƒxx = this.position.add(â˜ƒx);
               BlockPos â˜ƒxxx = new BlockPos(â˜ƒxx);
               FluidState â˜ƒxxxx = this.level.getFluidState(â˜ƒxxx);
               if (â˜ƒxxxx.is(FluidTags.LAVA)) {
                  if (â˜ƒxx.y <= (double)(â˜ƒxxxx.getHeight(this.level, â˜ƒxxx) + (float)â˜ƒxxx.getY())) {
                     return FogType.LAVA;
                  }
               } else {
                  BlockState â˜ƒxx = this.level.getBlockState(â˜ƒxxx);
                  if (â˜ƒxx.is(Blocks.POWDER_SNOW)) {
                     return FogType.POWDER_SNOW;
                  }
               }
            }

            return FogType.NONE;
         }
      }
   }

   public final Vector3f getLookVector() {
      return this.forwards;
   }

   public final Vector3f getUpVector() {
      return this.up;
   }

   public final Vector3f getLeftVector() {
      return this.left;
   }

   public void reset() {
      this.level = null;
      this.entity = null;
      this.initialized = false;
   }

   public static class NearPlane {
      final Vec3 forward;
      private final Vec3 left;
      private final Vec3 up;

      NearPlane(Vec3 var1, Vec3 var2, Vec3 var3) {
         this.forward = â˜ƒ;
         this.left = â˜ƒ;
         this.up = â˜ƒ;
      }

      public Vec3 getTopLeft() {
         return this.forward.add(this.up).add(this.left);
      }

      public Vec3 getTopRight() {
         return this.forward.add(this.up).subtract(this.left);
      }

      public Vec3 getBottomLeft() {
         return this.forward.subtract(this.up).add(this.left);
      }

      public Vec3 getBottomRight() {
         return this.forward.subtract(this.up).subtract(this.left);
      }

      public Vec3 getPointOnPlane(float var1, float var2) {
         return this.forward.add(this.up.scale((double)â˜ƒ)).subtract(this.left.scale((double)â˜ƒ));
      }
   }
}
