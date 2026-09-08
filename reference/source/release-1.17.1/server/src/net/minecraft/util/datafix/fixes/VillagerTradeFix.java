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
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class VillagerTradeFix extends NamedEntityFix {
   public VillagerTradeFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "Villager trade fix", References.ENTITY, "minecraft:villager");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      OpticFinder<?> â˜ƒ = â˜ƒ.getType().findField("Offers");
      OpticFinder<?> â˜ƒx = â˜ƒ.type().findField("Recipes");
      Type<?> â˜ƒxx = â˜ƒx.type();
      if (!(â˜ƒxx instanceof ListType)) {
         throw new IllegalStateException("Recipes are expected to be a list.");
      } else {
         ListType<?> â˜ƒ = (ListType)â˜ƒxx;
         Type<?> â˜ƒx = â˜ƒ.getElement();
         OpticFinder<?> â˜ƒxx = DSL.typeFinder(â˜ƒx);
         OpticFinder<?> â˜ƒxxx = â˜ƒx.findField("buy");
         OpticFinder<?> â˜ƒxxxx = â˜ƒx.findField("buyB");
         OpticFinder<?> â˜ƒxxxxx = â˜ƒx.findField("sell");
         OpticFinder<Pair<String, String>> â˜ƒxxxxxx = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
         Function<Typed<?>, Typed<?>> â˜ƒxxxxxxx = var2x -> this.updateItemStack(â˜ƒ, var2x);
         return â˜ƒ.updateTyped(
            â˜ƒ,
            var6x -> var6x.updateTyped(â˜ƒ, var5x -> var5x.updateTyped(â˜ƒ, var4x -> var4x.updateTyped(â˜ƒ, â˜ƒ).updateTyped(â˜ƒ, â˜ƒ).updateTyped(â˜ƒ, â˜ƒ)))
         );
      }
   }

   private Typed<?> updateItemStack(OpticFinder<Pair<String, String>> var1, Typed<?> var2) {
      return â˜ƒ.update(â˜ƒ, var0 -> var0.mapSecond(var0x -> Objects.equals(var0x, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : var0x));
   }
}
