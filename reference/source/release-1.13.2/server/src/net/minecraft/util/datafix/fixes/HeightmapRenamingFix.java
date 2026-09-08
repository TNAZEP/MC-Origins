package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class HeightmapRenamingFix extends DataFix {
   public HeightmapRenamingFix(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211287_c);
      OpticFinder<?> ☃x = ☃.findField("Level");
      return this.fixTypeEverywhereTyped(
         "HeightmapRenamingFix", ☃, var2x -> var2x.updateTyped(☃, var1x -> var1x.update(DSL.remainderFinder(), this::func_209766_a))
      );
   }

   private Dynamic<?> func_209766_a(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> ☃ = ☃.get("Heightmaps");
      if (!☃.isPresent()) {
         return ☃;
      } else {
         Dynamic<?> ☃ = (Dynamic)☃.get();
         Optional<? extends Dynamic<?>> ☃x = ☃.get("LIQUID");
         if (☃x.isPresent()) {
            ☃ = ☃.remove("LIQUID");
            ☃ = ☃.set("WORLD_SURFACE_WG", (Dynamic<?>)☃x.get());
         }

         Optional<? extends Dynamic<?>> ☃ = ☃.get("SOLID");
         if (☃.isPresent()) {
            ☃ = ☃.remove("SOLID");
            ☃ = ☃.set("OCEAN_FLOOR_WG", (Dynamic<?>)☃.get());
            ☃ = ☃.set("OCEAN_FLOOR", (Dynamic<?>)☃.get());
         }

         Optional<? extends Dynamic<?>> ☃ = ☃.get("LIGHT");
         if (☃.isPresent()) {
            ☃ = ☃.remove("LIGHT");
            ☃ = ☃.set("LIGHT_BLOCKING", (Dynamic<?>)☃.get());
         }

         Optional<? extends Dynamic<?>> ☃ = ☃.get("RAIN");
         if (☃.isPresent()) {
            ☃ = ☃.remove("RAIN");
            ☃ = ☃.set("MOTION_BLOCKING", (Dynamic<?>)☃.get());
            ☃ = ☃.set("MOTION_BLOCKING_NO_LEAVES", (Dynamic<?>)☃.get());
         }

         return ☃.set("Heightmaps", ☃);
      }
   }
}
