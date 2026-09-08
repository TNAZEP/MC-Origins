package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.util.datafix.TypeReferences;

public class SwimStatsRename extends DataFix {
   public SwimStatsRename(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getOutputSchema().getType(TypeReferences.field_211291_g);
      Type<?> ☃x = this.getInputSchema().getType(TypeReferences.field_211291_g);
      OpticFinder<?> ☃xx = ☃x.findField("stats");
      OpticFinder<?> ☃xxx = ☃xx.type().findField("minecraft:custom");
      OpticFinder<String> ☃xxxx = DSL.namespacedString().finder();
      return this.fixTypeEverywhereTyped(
         "SwimStatsRenameFix", ☃x, ☃, var3x -> var3x.updateTyped(☃, var2x -> var2x.updateTyped(☃, var1x -> var1x.update(☃, var0x -> {
                     if (var0x.equals("minecraft:swim_one_cm")) {
                        return "minecraft:walk_on_water_one_cm";
                     } else {
                        return var0x.equals("minecraft:dive_one_cm") ? "minecraft:walk_under_water_one_cm" : var0x;
                     }
                  })))
      );
   }
}
