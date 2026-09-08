package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1906 extends NamespacedSchema {
   public V1906(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerBlockEntities(â˜ƒ);
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:barrel");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:smoker");
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:blast_furnace");
      â˜ƒ.register(â˜ƒ, "minecraft:lectern", (Function<String, TypeTemplate>)(var1x -> DSL.optionalFields("Book", References.ITEM_STACK.in(â˜ƒ))));
      â˜ƒ.registerSimple(â˜ƒ, "minecraft:bell");
      return â˜ƒ;
   }

   protected static void registerInventory(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)))));
   }
}
