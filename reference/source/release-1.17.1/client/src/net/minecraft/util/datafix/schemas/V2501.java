package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V2501 extends NamespacedSchema {
   public V2501(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private static void registerFurnace(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(
         â˜ƒ,
         â˜ƒ,
         (Supplier<TypeTemplate>)(() -> DSL.optionalFields(
               "Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)), "RecipesUsed", DSL.compoundList(References.RECIPE.in(â˜ƒ), DSL.constType(DSL.intType()))
            ))
      );
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerBlockEntities(â˜ƒ);
      registerFurnace(â˜ƒ, â˜ƒ, "minecraft:furnace");
      registerFurnace(â˜ƒ, â˜ƒ, "minecraft:smoker");
      registerFurnace(â˜ƒ, â˜ƒ, "minecraft:blast_furnace");
      return â˜ƒ;
   }
}
