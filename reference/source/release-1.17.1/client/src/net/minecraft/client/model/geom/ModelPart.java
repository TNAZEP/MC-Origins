package net.minecraft.client.model.geom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.Direction;

public final class ModelPart {
   public float x;
   public float y;
   public float z;
   public float xRot;
   public float yRot;
   public float zRot;
   public boolean visible = true;
   private final List<ModelPart.Cube> cubes;
   private final Map<String, ModelPart> children;

   public ModelPart(List<ModelPart.Cube> var1, Map<String, ModelPart> var2) {
      this.cubes = â˜ƒ;
      this.children = â˜ƒ;
   }

   public PartPose storePose() {
      return PartPose.offsetAndRotation(this.x, this.y, this.z, this.xRot, this.yRot, this.zRot);
   }

   public void loadPose(PartPose var1) {
      this.x = â˜ƒ.x;
      this.y = â˜ƒ.y;
      this.z = â˜ƒ.z;
      this.xRot = â˜ƒ.xRot;
      this.yRot = â˜ƒ.yRot;
      this.zRot = â˜ƒ.zRot;
   }

   public void copyFrom(ModelPart var1) {
      this.xRot = â˜ƒ.xRot;
      this.yRot = â˜ƒ.yRot;
      this.zRot = â˜ƒ.zRot;
      this.x = â˜ƒ.x;
      this.y = â˜ƒ.y;
      this.z = â˜ƒ.z;
   }

   public ModelPart getChild(String var1) {
      ModelPart â˜ƒ = (ModelPart)this.children.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw new NoSuchElementException("Can't find part " + â˜ƒ);
      } else {
         return â˜ƒ;
      }
   }

   public void setPos(float var1, float var2, float var3) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
   }

   public void setRotation(float var1, float var2, float var3) {
      this.xRot = â˜ƒ;
      this.yRot = â˜ƒ;
      this.zRot = â˜ƒ;
   }

   public void render(PoseStack var1, VertexConsumer var2, int var3, int var4) {
      this.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void render(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      if (this.visible) {
         if (!this.cubes.isEmpty() || !this.children.isEmpty()) {
            â˜ƒ.pushPose();
            this.translateAndRotate(â˜ƒ);
            this.compile(â˜ƒ.last(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);

            for(ModelPart â˜ƒ : this.children.values()) {
               â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }

            â˜ƒ.popPose();
         }
      }
   }

   public void visit(PoseStack var1, ModelPart.Visitor var2) {
      this.visit(â˜ƒ, â˜ƒ, "");
   }

   private void visit(PoseStack var1, ModelPart.Visitor var2, String var3) {
      if (!this.cubes.isEmpty() || !this.children.isEmpty()) {
         â˜ƒ.pushPose();
         this.translateAndRotate(â˜ƒ);
         PoseStack.Pose â˜ƒ = â˜ƒ.last();

         for(int â˜ƒx = 0; â˜ƒx < this.cubes.size(); ++â˜ƒx) {
            â˜ƒ.visit(â˜ƒ, â˜ƒ, â˜ƒx, (ModelPart.Cube)this.cubes.get(â˜ƒx));
         }

         String â˜ƒx = â˜ƒ + "/";
         this.children.forEach((var3x, var4x) -> var4x.visit(â˜ƒ, â˜ƒ, â˜ƒ + var3x));
         â˜ƒ.popPose();
      }
   }

   public void translateAndRotate(PoseStack var1) {
      â˜ƒ.translate((double)(this.x / 16.0F), (double)(this.y / 16.0F), (double)(this.z / 16.0F));
      if (this.zRot != 0.0F) {
         â˜ƒ.mulPose(Vector3f.ZP.rotation(this.zRot));
      }

      if (this.yRot != 0.0F) {
         â˜ƒ.mulPose(Vector3f.YP.rotation(this.yRot));
      }

      if (this.xRot != 0.0F) {
         â˜ƒ.mulPose(Vector3f.XP.rotation(this.xRot));
      }
   }

   private void compile(PoseStack.Pose var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      for(ModelPart.Cube â˜ƒ : this.cubes) {
         â˜ƒ.compile(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public ModelPart.Cube getRandomCube(Random var1) {
      return (ModelPart.Cube)this.cubes.get(â˜ƒ.nextInt(this.cubes.size()));
   }

   public boolean isEmpty() {
      return this.cubes.isEmpty();
   }

   public Stream<ModelPart> getAllParts() {
      return Stream.concat(Stream.of(this), this.children.values().stream().flatMap(ModelPart::getAllParts));
   }

   public static class Cube {
      private final ModelPart.Polygon[] polygons;
      public final float minX;
      public final float minY;
      public final float minZ;
      public final float maxX;
      public final float maxY;
      public final float maxZ;

      public Cube(
         int var1,
         int var2,
         float var3,
         float var4,
         float var5,
         float var6,
         float var7,
         float var8,
         float var9,
         float var10,
         float var11,
         boolean var12,
         float var13,
         float var14
      ) {
         this.minX = â˜ƒ;
         this.minY = â˜ƒ;
         this.minZ = â˜ƒ;
         this.maxX = â˜ƒ + â˜ƒ;
         this.maxY = â˜ƒ + â˜ƒ;
         this.maxZ = â˜ƒ + â˜ƒ;
         this.polygons = new ModelPart.Polygon[6];
         float â˜ƒ = â˜ƒ + â˜ƒ;
         float â˜ƒx = â˜ƒ + â˜ƒ;
         float â˜ƒxx = â˜ƒ + â˜ƒ;
         â˜ƒ -= â˜ƒ;
         â˜ƒ -= â˜ƒ;
         â˜ƒ -= â˜ƒ;
         â˜ƒ += â˜ƒ;
         â˜ƒx += â˜ƒ;
         â˜ƒxx += â˜ƒ;
         if (â˜ƒ) {
            float â˜ƒxxx = â˜ƒ;
            â˜ƒ = â˜ƒ;
            â˜ƒ = â˜ƒxxx;
         }

         ModelPart.Vertex â˜ƒ = new ModelPart.Vertex(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F);
         ModelPart.Vertex â˜ƒx = new ModelPart.Vertex(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 8.0F);
         ModelPart.Vertex â˜ƒxx = new ModelPart.Vertex(â˜ƒ, â˜ƒx, â˜ƒ, 8.0F, 8.0F);
         ModelPart.Vertex â˜ƒxxx = new ModelPart.Vertex(â˜ƒ, â˜ƒx, â˜ƒ, 8.0F, 0.0F);
         ModelPart.Vertex â˜ƒxxxx = new ModelPart.Vertex(â˜ƒ, â˜ƒ, â˜ƒxx, 0.0F, 0.0F);
         ModelPart.Vertex â˜ƒxxxxx = new ModelPart.Vertex(â˜ƒ, â˜ƒ, â˜ƒxx, 0.0F, 8.0F);
         ModelPart.Vertex â˜ƒxxxxxx = new ModelPart.Vertex(â˜ƒ, â˜ƒx, â˜ƒxx, 8.0F, 8.0F);
         ModelPart.Vertex â˜ƒxxxxxxx = new ModelPart.Vertex(â˜ƒ, â˜ƒx, â˜ƒxx, 8.0F, 0.0F);
         float â˜ƒxxxxxxxx = (float)â˜ƒ;
         float â˜ƒxxxxxxxxx = (float)â˜ƒ + â˜ƒ;
         float â˜ƒxxxxxxxxxx = (float)â˜ƒ + â˜ƒ + â˜ƒ;
         float â˜ƒxxxxxxxxxxx = (float)â˜ƒ + â˜ƒ + â˜ƒ + â˜ƒ;
         float â˜ƒxxxxxxxxxxxx = (float)â˜ƒ + â˜ƒ + â˜ƒ + â˜ƒ;
         float â˜ƒxxxxxxxxxxxxx = (float)â˜ƒ + â˜ƒ + â˜ƒ + â˜ƒ + â˜ƒ;
         float â˜ƒxxxxxxxxxxxxxx = (float)â˜ƒ;
         float â˜ƒxxxxxxxxxxxxxxx = (float)â˜ƒ + â˜ƒ;
         float â˜ƒxxxxxxxxxxxxxxxx = (float)â˜ƒ + â˜ƒ + â˜ƒ;
         this.polygons[2] = new ModelPart.Polygon(
            new ModelPart.Vertex[]{â˜ƒxxxxx, â˜ƒxxxx, â˜ƒ, â˜ƒx},
            â˜ƒxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            Direction.DOWN
         );
         this.polygons[3] = new ModelPart.Polygon(
            new ModelPart.Vertex[]{â˜ƒxx, â˜ƒxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx},
            â˜ƒxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            Direction.UP
         );
         this.polygons[1] = new ModelPart.Polygon(
            new ModelPart.Vertex[]{â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxx, â˜ƒxxx},
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            Direction.WEST
         );
         this.polygons[4] = new ModelPart.Polygon(
            new ModelPart.Vertex[]{â˜ƒx, â˜ƒ, â˜ƒxxx, â˜ƒxx},
            â˜ƒxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            Direction.NORTH
         );
         this.polygons[0] = new ModelPart.Polygon(
            new ModelPart.Vertex[]{â˜ƒxxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxxxxx},
            â˜ƒxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            Direction.EAST
         );
         this.polygons[5] = new ModelPart.Polygon(
            new ModelPart.Vertex[]{â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx},
            â˜ƒxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            â˜ƒ,
            â˜ƒ,
            â˜ƒ,
            Direction.SOUTH
         );
      }

      public void compile(PoseStack.Pose var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         Matrix4f â˜ƒ = â˜ƒ.pose();
         Matrix3f â˜ƒx = â˜ƒ.normal();

         for(ModelPart.Polygon â˜ƒxx : this.polygons) {
            Vector3f â˜ƒxxx = â˜ƒxx.normal.copy();
            â˜ƒxxx.transform(â˜ƒx);
            float â˜ƒxxxx = â˜ƒxxx.x();
            float â˜ƒxxxxx = â˜ƒxxx.y();
            float â˜ƒxxxxxx = â˜ƒxxx.z();

            for(ModelPart.Vertex â˜ƒxxxxxxx : â˜ƒxx.vertices) {
               float â˜ƒxxxxxxxx = â˜ƒxxxxxxx.pos.x() / 16.0F;
               float â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.pos.y() / 16.0F;
               float â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx.pos.z() / 16.0F;
               Vector4f â˜ƒxxxxxxxxxxx = new Vector4f(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, 1.0F);
               â˜ƒxxxxxxxxxxx.transform(â˜ƒ);
               â˜ƒ.vertex(
                  â˜ƒxxxxxxxxxxx.x(),
                  â˜ƒxxxxxxxxxxx.y(),
                  â˜ƒxxxxxxxxxxx.z(),
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒxxxxxxx.u,
                  â˜ƒxxxxxxx.v,
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒxxxx,
                  â˜ƒxxxxx,
                  â˜ƒxxxxxx
               );
            }
         }
      }
   }

   static class Polygon {
      public final ModelPart.Vertex[] vertices;
      public final Vector3f normal;

      public Polygon(ModelPart.Vertex[] var1, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8, Direction var9) {
         this.vertices = â˜ƒ;
         float â˜ƒ = 0.0F / â˜ƒ;
         float â˜ƒx = 0.0F / â˜ƒ;
         â˜ƒ[0] = â˜ƒ[0].remap(â˜ƒ / â˜ƒ - â˜ƒ, â˜ƒ / â˜ƒ + â˜ƒx);
         â˜ƒ[1] = â˜ƒ[1].remap(â˜ƒ / â˜ƒ + â˜ƒ, â˜ƒ / â˜ƒ + â˜ƒx);
         â˜ƒ[2] = â˜ƒ[2].remap(â˜ƒ / â˜ƒ + â˜ƒ, â˜ƒ / â˜ƒ - â˜ƒx);
         â˜ƒ[3] = â˜ƒ[3].remap(â˜ƒ / â˜ƒ - â˜ƒ, â˜ƒ / â˜ƒ - â˜ƒx);
         if (â˜ƒ) {
            int â˜ƒxx = â˜ƒ.length;

            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx / 2; ++â˜ƒxxx) {
               ModelPart.Vertex â˜ƒxxxx = â˜ƒ[â˜ƒxxx];
               â˜ƒ[â˜ƒxxx] = â˜ƒ[â˜ƒxx - 1 - â˜ƒxxx];
               â˜ƒ[â˜ƒxx - 1 - â˜ƒxxx] = â˜ƒxxxx;
            }
         }

         this.normal = â˜ƒ.step();
         if (â˜ƒ) {
            this.normal.mul(-1.0F, 1.0F, 1.0F);
         }
      }
   }

   static class Vertex {
      public final Vector3f pos;
      public final float u;
      public final float v;

      public Vertex(float var1, float var2, float var3, float var4, float var5) {
         this(new Vector3f(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ, â˜ƒ);
      }

      public ModelPart.Vertex remap(float var1, float var2) {
         return new ModelPart.Vertex(this.pos, â˜ƒ, â˜ƒ);
      }

      public Vertex(Vector3f var1, float var2, float var3) {
         this.pos = â˜ƒ;
         this.u = â˜ƒ;
         this.v = â˜ƒ;
      }
   }

   @FunctionalInterface
   public interface Visitor {
      void visit(PoseStack.Pose var1, String var2, int var3, ModelPart.Cube var4);
   }
}
