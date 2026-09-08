package net.minecraft.client.model.geom.builders;

import net.minecraft.client.model.geom.ModelPart;

public class LayerDefinition {
   private final MeshDefinition mesh;
   private final MaterialDefinition material;

   private LayerDefinition(MeshDefinition var1, MaterialDefinition var2) {
      this.mesh = â˜ƒ;
      this.material = â˜ƒ;
   }

   public ModelPart bakeRoot() {
      return this.mesh.getRoot().bake(this.material.xTexSize, this.material.yTexSize);
   }

   public static LayerDefinition create(MeshDefinition var0, int var1, int var2) {
      return new LayerDefinition(â˜ƒ, new MaterialDefinition(â˜ƒ, â˜ƒ));
   }
}
