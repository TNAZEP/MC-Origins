package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContextExtended;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public enum GenLayerVoronoiZoom implements IAreaTransformer1 {
   INSTANCE;

   @Override
   public int func_202712_a(IContextExtended<?> var1, AreaDimension var2, IArea var3, int var4, int var5) {
      int ☃ = ☃ + ☃.func_202690_a() - 2;
      int ☃x = ☃ + ☃.func_202691_b() - 2;
      int ☃xx = ☃.func_202690_a() >> 2;
      int ☃xxx = ☃.func_202691_b() >> 2;
      int ☃xxxx = (☃ >> 2) - ☃xx;
      int ☃xxxxx = (☃x >> 2) - ☃xxx;
      ☃.func_202698_a((long)(☃xxxx + ☃xx << 2), (long)(☃xxxxx + ☃xxx << 2));
      double ☃xxxxxx = ((double)☃.func_202696_a(1024) / 1024.0 - 0.5) * 3.6;
      double ☃xxxxxxx = ((double)☃.func_202696_a(1024) / 1024.0 - 0.5) * 3.6;
      ☃.func_202698_a((long)(☃xxxx + ☃xx + 1 << 2), (long)(☃xxxxx + ☃xxx << 2));
      double ☃xxxxxxxx = ((double)☃.func_202696_a(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
      double ☃xxxxxxxxx = ((double)☃.func_202696_a(1024) / 1024.0 - 0.5) * 3.6;
      ☃.func_202698_a((long)(☃xxxx + ☃xx << 2), (long)(☃xxxxx + ☃xxx + 1 << 2));
      double ☃xxxxxxxxxx = ((double)☃.func_202696_a(1024) / 1024.0 - 0.5) * 3.6;
      double ☃xxxxxxxxxxx = ((double)☃.func_202696_a(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
      ☃.func_202698_a((long)(☃xxxx + ☃xx + 1 << 2), (long)(☃xxxxx + ☃xxx + 1 << 2));
      double ☃xxxxxxxxxxxx = ((double)☃.func_202696_a(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
      double ☃xxxxxxxxxxxxx = ((double)☃.func_202696_a(1024) / 1024.0 - 0.5) * 3.6 + 4.0;
      int ☃xxxxxxxxxxxxxx = ☃ & 3;
      int ☃xxxxxxxxxxxxxxx = ☃x & 3;
      double ☃xxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxx - ☃xxxxxxx) * ((double)☃xxxxxxxxxxxxxxx - ☃xxxxxxx)
         + ((double)☃xxxxxxxxxxxxxx - ☃xxxxxx) * ((double)☃xxxxxxxxxxxxxx - ☃xxxxxx);
      double ☃xxxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxx - ☃xxxxxxxxx) * ((double)☃xxxxxxxxxxxxxxx - ☃xxxxxxxxx)
         + ((double)☃xxxxxxxxxxxxxx - ☃xxxxxxxx) * ((double)☃xxxxxxxxxxxxxx - ☃xxxxxxxx);
      double ☃xxxxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxx - ☃xxxxxxxxxxx) * ((double)☃xxxxxxxxxxxxxxx - ☃xxxxxxxxxxx)
         + ((double)☃xxxxxxxxxxxxxx - ☃xxxxxxxxxx) * ((double)☃xxxxxxxxxxxxxx - ☃xxxxxxxxxx);
      double ☃xxxxxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxx) * ((double)☃xxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxx)
         + ((double)☃xxxxxxxxxxxxxx - ☃xxxxxxxxxxxx) * ((double)☃xxxxxxxxxxxxxx - ☃xxxxxxxxxxxx);
      if (☃xxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxx && ☃xxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxxx && ☃xxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxxxx) {
         return ☃.func_202678_a(☃xxxx + 0, ☃xxxxx + 0);
      } else if (☃xxxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxx && ☃xxxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxxx && ☃xxxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxxxx) {
         return ☃.func_202678_a(☃xxxx + 1, ☃xxxxx + 0) & 0xFF;
      } else {
         return ☃xxxxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxx && ☃xxxxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxx && ☃xxxxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxxxx
            ? ☃.func_202678_a(☃xxxx + 0, ☃xxxxx + 1)
            : ☃.func_202678_a(☃xxxx + 1, ☃xxxxx + 1) & 0xFF;
      }
   }

   @Override
   public AreaDimension func_202706_a(AreaDimension var1) {
      int ☃ = ☃.func_202690_a() >> 2;
      int ☃x = ☃.func_202691_b() >> 2;
      int ☃xx = (☃.func_202688_c() >> 2) + 2;
      int ☃xxx = (☃.func_202689_d() >> 2) + 2;
      return new AreaDimension(☃, ☃x, ☃xx, ☃xxx);
   }
}
