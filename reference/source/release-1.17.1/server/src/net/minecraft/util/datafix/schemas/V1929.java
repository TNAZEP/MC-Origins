package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1929 extends NamespacedSchema {
   public V1929(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerEntities(â˜ƒ);
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:wandering_trader",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Inventory",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               "Offers",
               DSL.optionalFields(
                  "Recipes",
                  DSL.list(
                     DSL.optionalFields("buy", References.ITEM_STACK.in(â˜ƒ), "buyB", References.ITEM_STACK.in(â˜ƒ), "sell", References.ITEM_STACK.in(â˜ƒ))
                  )
               ),
               V100.equipment(â˜ƒ)
            ))
      );
      â˜ƒ.register(
         â˜ƒ,
         "minecraft:trader_llama",
         (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields(
               "Items",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               "SaddleItem",
               References.ITEM_STACK.in(â˜ƒ),
               "DecorItem",
               References.ITEM_STACK.in(â˜ƒ),
               V100.equipment(â˜ƒ)
            ))
      );
      return â˜ƒ;
   }
}
