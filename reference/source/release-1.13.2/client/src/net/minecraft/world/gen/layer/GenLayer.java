package net.minecraft.world.gen.layer;

import javax.annotation.Nullable;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;

public class GenLayer {
   private final IAreaFactory<LazyArea> field_202834_a;

   public GenLayer(IAreaFactory<LazyArea> var1) {
      this.field_202834_a = ☃;
   }

   public Biome[] func_202833_a(int var1, int var2, int var3, int var4, @Nullable Biome var5) {
      AreaDimension ☃ = new AreaDimension(☃, ☃, ☃, ☃);
      LazyArea ☃x = this.field_202834_a.make(☃);
      Biome[] ☃xx = new Biome[☃ * ☃];

      for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
         for(int ☃xxxx = 0; ☃xxxx < ☃; ++☃xxxx) {
            ☃xx[☃xxxx + ☃xxx * ☃] = Biome.func_180276_a(☃x.func_202678_a(☃xxxx, ☃xxx), ☃);
         }
      }

      return ☃xx;
   }
}
