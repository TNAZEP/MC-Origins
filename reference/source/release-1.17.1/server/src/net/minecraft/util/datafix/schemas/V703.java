package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V703 extends Schema {
   public V703(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerEntities(â˜ƒ);
      â˜ƒ.remove("EntityHorse");
      â˜ƒ.register(
         â˜ƒ,
         "Horse",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "ArmorItem", References.ITEM_STACK.in(â˜ƒ), "SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)
            ))
      );
      â˜ƒ.register(
         â˜ƒ,
         "Donkey",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)), "SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)
            ))
      );
      â˜ƒ.register(
         â˜ƒ,
         "Mule",
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)), "SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ)
            ))
      );
      â˜ƒ.register(â˜ƒ, "ZombieHorse", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ))));
      â˜ƒ.register(â˜ƒ, "SkeletonHorse", (Supplier<TypeTemplate>)(() -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in(â˜ƒ), V100.equipment(â˜ƒ))));
      return â˜ƒ;
   }
}
