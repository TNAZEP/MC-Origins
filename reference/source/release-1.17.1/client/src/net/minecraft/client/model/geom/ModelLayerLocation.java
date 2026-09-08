package net.minecraft.client.model.geom;

import net.minecraft.resources.ResourceLocation;

public final class ModelLayerLocation {
   private final ResourceLocation model;
   private final String layer;

   public ModelLayerLocation(ResourceLocation var1, String var2) {
      this.model = â˜ƒ;
      this.layer = â˜ƒ;
   }

   public ResourceLocation getModel() {
      return this.model;
   }

   public String getLayer() {
      return this.layer;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof ModelLayerLocation)) {
         return false;
      } else {
         ModelLayerLocation â˜ƒ = (ModelLayerLocation)â˜ƒ;
         return this.model.equals(â˜ƒ.model) && this.layer.equals(â˜ƒ.layer);
      }
   }

   public int hashCode() {
      int â˜ƒ = this.model.hashCode();
      return 31 * â˜ƒ + this.layer.hashCode();
   }

   public String toString() {
      return this.model + "#" + this.layer;
   }
}
