package net.minecraft.client.model.geom.builders;

import com.mojang.math.Vector3f;
import javax.annotation.Nullable;
import net.minecraft.client.model.geom.ModelPart;

public final class CubeDefinition {
   @Nullable
   private final String comment;
   private final Vector3f origin;
   private final Vector3f dimensions;
   private final CubeDeformation grow;
   private final boolean mirror;
   private final UVPair texCoord;
   private final UVPair texScale;

   protected CubeDefinition(
      @Nullable String var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      CubeDeformation var10,
      boolean var11,
      float var12,
      float var13
   ) {
      this.comment = â˜ƒ;
      this.texCoord = new UVPair(â˜ƒ, â˜ƒ);
      this.origin = new Vector3f(â˜ƒ, â˜ƒ, â˜ƒ);
      this.dimensions = new Vector3f(â˜ƒ, â˜ƒ, â˜ƒ);
      this.grow = â˜ƒ;
      this.mirror = â˜ƒ;
      this.texScale = new UVPair(â˜ƒ, â˜ƒ);
   }

   public ModelPart.Cube bake(int var1, int var2) {
      return new ModelPart.Cube(
         (int)this.texCoord.u(),
         (int)this.texCoord.v(),
         this.origin.x(),
         this.origin.y(),
         this.origin.z(),
         this.dimensions.x(),
         this.dimensions.y(),
         this.dimensions.z(),
         this.grow.growX,
         this.grow.growY,
         this.grow.growZ,
         this.mirror,
         (float)â˜ƒ * this.texScale.u(),
         (float)â˜ƒ * this.texScale.v()
      );
   }
}
