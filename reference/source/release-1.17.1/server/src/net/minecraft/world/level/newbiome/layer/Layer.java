package net.minecraft.world.level.newbiome.layer;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.newbiome.area.AreaFactory;
import net.minecraft.world.level.newbiome.area.LazyArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Layer {
   private static final Logger LOGGER = LogManager.getLogger();
   private final LazyArea area;

   public Layer(AreaFactory<LazyArea> var1) {
      this.area = â˜ƒ.make();
   }

   public Biome get(Registry<Biome> var1, int var2, int var3) {
      int â˜ƒ = this.area.get(â˜ƒ, â˜ƒ);
      ResourceKey<Biome> â˜ƒx = Biomes.byId(â˜ƒ);
      if (â˜ƒx == null) {
         throw new IllegalStateException("Unknown biome id emitted by layers: " + â˜ƒ);
      } else {
         Biome â˜ƒ = â˜ƒ.get(â˜ƒx);
         if (â˜ƒ == null) {
            Util.logAndPauseIfInIde("Unknown biome id: " + â˜ƒ);
            return â˜ƒ.get(Biomes.byId(0));
         } else {
            return â˜ƒ;
         }
      }
   }
}
