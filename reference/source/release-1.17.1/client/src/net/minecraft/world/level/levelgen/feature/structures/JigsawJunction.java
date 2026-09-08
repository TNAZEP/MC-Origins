package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;

public class JigsawJunction {
   private final int sourceX;
   private final int sourceGroundY;
   private final int sourceZ;
   private final int deltaY;
   private final StructureTemplatePool.Projection destProjection;

   public JigsawJunction(int var1, int var2, int var3, int var4, StructureTemplatePool.Projection var5) {
      this.sourceX = â˜ƒ;
      this.sourceGroundY = â˜ƒ;
      this.sourceZ = â˜ƒ;
      this.deltaY = â˜ƒ;
      this.destProjection = â˜ƒ;
   }

   public int getSourceX() {
      return this.sourceX;
   }

   public int getSourceGroundY() {
      return this.sourceGroundY;
   }

   public int getSourceZ() {
      return this.sourceZ;
   }

   public int getDeltaY() {
      return this.deltaY;
   }

   public StructureTemplatePool.Projection getDestProjection() {
      return this.destProjection;
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> var1) {
      Builder<T, T> â˜ƒ = ImmutableMap.builder();
      â˜ƒ.put(â˜ƒ.createString("source_x"), â˜ƒ.createInt(this.sourceX))
         .put(â˜ƒ.createString("source_ground_y"), â˜ƒ.createInt(this.sourceGroundY))
         .put(â˜ƒ.createString("source_z"), â˜ƒ.createInt(this.sourceZ))
         .put(â˜ƒ.createString("delta_y"), â˜ƒ.createInt(this.deltaY))
         .put(â˜ƒ.createString("dest_proj"), â˜ƒ.createString(this.destProjection.getName()));
      return new Dynamic<>(â˜ƒ, â˜ƒ.createMap(â˜ƒ.build()));
   }

   public static <T> JigsawJunction deserialize(Dynamic<T> var0) {
      return new JigsawJunction(
         â˜ƒ.get("source_x").asInt(0),
         â˜ƒ.get("source_ground_y").asInt(0),
         â˜ƒ.get("source_z").asInt(0),
         â˜ƒ.get("delta_y").asInt(0),
         StructureTemplatePool.Projection.byName(â˜ƒ.get("dest_proj").asString(""))
      );
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         JigsawJunction â˜ƒ = (JigsawJunction)â˜ƒ;
         if (this.sourceX != â˜ƒ.sourceX) {
            return false;
         } else if (this.sourceZ != â˜ƒ.sourceZ) {
            return false;
         } else if (this.deltaY != â˜ƒ.deltaY) {
            return false;
         } else {
            return this.destProjection == â˜ƒ.destProjection;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int â˜ƒ = this.sourceX;
      â˜ƒ = 31 * â˜ƒ + this.sourceGroundY;
      â˜ƒ = 31 * â˜ƒ + this.sourceZ;
      â˜ƒ = 31 * â˜ƒ + this.deltaY;
      return 31 * â˜ƒ + this.destProjection.hashCode();
   }

   public String toString() {
      return "JigsawJunction{sourceX="
         + this.sourceX
         + ", sourceGroundY="
         + this.sourceGroundY
         + ", sourceZ="
         + this.sourceZ
         + ", deltaY="
         + this.deltaY
         + ", destProjection="
         + this.destProjection
         + "}";
   }
}
