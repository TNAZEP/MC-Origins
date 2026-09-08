package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1920 extends NamespacedSchema {
   public V1920(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected static void registerInventory(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(â˜ƒ)))));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerBlockEntities(â˜ƒ);
      registerInventory(â˜ƒ, â˜ƒ, "minecraft:campfire");
      return â˜ƒ;
   }
}
