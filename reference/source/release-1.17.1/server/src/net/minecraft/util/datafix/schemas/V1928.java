package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1928 extends NamespacedSchema {
   public V1928(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected static TypeTemplate equipment(Schema var0) {
      return DSL.optionalFields("ArmorItems", DSL.list(References.ITEM_STACK.in(â˜ƒ)), "HandItems", DSL.list(References.ITEM_STACK.in(â˜ƒ)));
   }

   protected static void registerMob(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      â˜ƒ.register(â˜ƒ, â˜ƒ, (Supplier<TypeTemplate>)(() -> equipment(â˜ƒ)));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerEntities(â˜ƒ);
      â˜ƒ.remove("minecraft:illager_beast");
      registerMob(â˜ƒ, â˜ƒ, "minecraft:ravager");
      return â˜ƒ;
   }
}
