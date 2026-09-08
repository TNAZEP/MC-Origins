package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContextExtended;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public enum GenLayerZoom implements IAreaTransformer1 {
   NORMAL,
   FUZZY {
      @Override
      protected int func_202715_a(IContextExtended<?> var1, int var2, int var3, int var4, int var5) {
         return ☃.func_202697_a(☃, ☃, ☃, ☃);
      }
   };

   private GenLayerZoom() {
   }

   @Override
   public AreaDimension func_202706_a(AreaDimension var1) {
      int ☃ = ☃.func_202690_a() >> 1;
      int ☃x = ☃.func_202691_b() >> 1;
      int ☃xx = (☃.func_202688_c() >> 1) + 3;
      int ☃xxx = (☃.func_202689_d() >> 1) + 3;
      return new AreaDimension(☃, ☃x, ☃xx, ☃xxx);
   }

   @Override
   public int func_202712_a(IContextExtended<?> var1, AreaDimension var2, IArea var3, int var4, int var5) {
      int ☃ = ☃.func_202690_a() >> 1;
      int ☃x = ☃.func_202691_b() >> 1;
      int ☃xx = ☃ + ☃.func_202690_a();
      int ☃xxx = ☃ + ☃.func_202691_b();
      int ☃xxxx = (☃xx >> 1) - ☃;
      int ☃xxxxx = ☃xxxx + 1;
      int ☃xxxxxx = (☃xxx >> 1) - ☃x;
      int ☃xxxxxxx = ☃xxxxxx + 1;
      int ☃xxxxxxxx = ☃.func_202678_a(☃xxxx, ☃xxxxxx);
      ☃.func_202698_a((long)(☃xx >> 1 << 1), (long)(☃xxx >> 1 << 1));
      int ☃xxxxxxxxx = ☃xx & 1;
      int ☃xxxxxxxxxx = ☃xxx & 1;
      if (☃xxxxxxxxx == 0 && ☃xxxxxxxxxx == 0) {
         return ☃xxxxxxxx;
      } else {
         int ☃ = ☃.func_202678_a(☃xxxx, ☃xxxxxxx);
         int ☃x = ☃.func_202697_a(☃xxxxxxxx, ☃);
         if (☃xxxxxxxxx == 0 && ☃xxxxxxxxxx == 1) {
            return ☃x;
         } else {
            int ☃ = ☃.func_202678_a(☃xxxxx, ☃xxxxxx);
            int ☃x = ☃.func_202697_a(☃xxxxxxxx, ☃);
            if (☃xxxxxxxxx == 1 && ☃xxxxxxxxxx == 0) {
               return ☃x;
            } else {
               int ☃ = ☃.func_202678_a(☃xxxxx, ☃xxxxxxx);
               return this.func_202715_a(☃, ☃xxxxxxxx, ☃, ☃, ☃);
            }
         }
      }
   }

   protected int func_202715_a(IContextExtended<?> var1, int var2, int var3, int var4, int var5) {
      if (☃ == ☃ && ☃ == ☃) {
         return ☃;
      } else if (☃ == ☃ && ☃ == ☃) {
         return ☃;
      } else if (☃ == ☃ && ☃ == ☃) {
         return ☃;
      } else if (☃ == ☃ && ☃ == ☃) {
         return ☃;
      } else if (☃ == ☃ && ☃ != ☃) {
         return ☃;
      } else if (☃ == ☃ && ☃ != ☃) {
         return ☃;
      } else if (☃ == ☃ && ☃ != ☃) {
         return ☃;
      } else if (☃ == ☃ && ☃ != ☃) {
         return ☃;
      } else if (☃ == ☃ && ☃ != ☃) {
         return ☃;
      } else {
         return ☃ == ☃ && ☃ != ☃ ? ☃ : ☃.func_202697_a(☃, ☃, ☃, ☃);
      }
   }
}
