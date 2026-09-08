package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public class GenLayerEdge {
   public static enum CoolWarm implements ICastleTransformer {
      INSTANCE;

      @Override
      public int func_202748_a(IContext var1, int var2, int var3, int var4, int var5, int var6) {
         return ☃ != 1 || ☃ != 3 && ☃ != 3 && ☃ != 3 && ☃ != 3 && ☃ != 4 && ☃ != 4 && ☃ != 4 && ☃ != 4 ? ☃ : 2;
      }
   }

   public static enum HeatIce implements ICastleTransformer {
      INSTANCE;

      @Override
      public int func_202748_a(IContext var1, int var2, int var3, int var4, int var5, int var6) {
         return ☃ != 4 || ☃ != 1 && ☃ != 1 && ☃ != 1 && ☃ != 1 && ☃ != 2 && ☃ != 2 && ☃ != 2 && ☃ != 2 ? ☃ : 3;
      }
   }

   public static enum Special implements IC0Transformer {
      INSTANCE;

      @Override
      public int func_202726_a(IContext var1, int var2) {
         if (!LayerUtil.func_203631_b(☃) && ☃.func_202696_a(13) == 0) {
            ☃ |= 1 + ☃.func_202696_a(15) << 8 & 3840;
         }

         return ☃;
      }
   }
}
