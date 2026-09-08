package net.minecraft.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenLayerRiver implements ICastleTransformer {
   INSTANCE;

   public static final int field_202767_b = IRegistry.field_212624_m.func_148757_b(Biomes.field_76781_i);

   @Override
   public int func_202748_a(IContext var1, int var2, int var3, int var4, int var5, int var6) {
      int ☃ = func_151630_c(☃);
      return ☃ == func_151630_c(☃) && ☃ == func_151630_c(☃) && ☃ == func_151630_c(☃) && ☃ == func_151630_c(☃) ? -1 : field_202767_b;
   }

   private static int func_151630_c(int var0) {
      return ☃ >= 2 ? 2 + (☃ & 1) : ☃;
   }
}
