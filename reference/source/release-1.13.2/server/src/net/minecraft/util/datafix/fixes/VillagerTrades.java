package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.TypeReferences;

public class VillagerTrades extends NamedEntityFix {
   public VillagerTrades(Schema var1, boolean var2) {
      super(☃, ☃, "Villager trade fix", TypeReferences.field_211299_o, "minecraft:villager");
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      OpticFinder<?> ☃ = ☃.getType().findField("Offers");
      OpticFinder<?> ☃x = ☃.type().findField("Recipes");
      Type<?> ☃xx = ☃x.type();
      if (!(☃xx instanceof ListType)) {
         throw new IllegalStateException("Recipes are expected to be a list.");
      } else {
         ListType<?> ☃ = (ListType)☃xx;
         Type<?> ☃x = ☃.getElement();
         OpticFinder<?> ☃xx = DSL.typeFinder(☃x);
         OpticFinder<?> ☃xxx = ☃x.findField("buy");
         OpticFinder<?> ☃xxxx = ☃x.findField("buyB");
         OpticFinder<?> ☃xxxxx = ☃x.findField("sell");
         OpticFinder<Pair<String, String>> ☃xxxxxx = DSL.fieldFinder("id", DSL.named(TypeReferences.field_211301_q.typeName(), DSL.namespacedString()));
         Function<Typed<?>, Typed<?>> ☃xxxxxxx = var2x -> this.func_210482_a(☃, var2x);
         return ☃.updateTyped(
            ☃, var6x -> var6x.updateTyped(☃, var5x -> var5x.updateTyped(☃, var4x -> var4x.updateTyped(☃, ☃).updateTyped(☃, ☃).updateTyped(☃, ☃)))
         );
      }
   }

   private Typed<?> func_210482_a(OpticFinder<Pair<String, String>> var1, Typed<?> var2) {
      return ☃.update(☃, var0 -> var0.mapSecond(var0x -> Objects.equals(var0x, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : var0x));
   }
}
