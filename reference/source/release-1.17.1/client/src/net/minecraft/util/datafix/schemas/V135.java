package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V135 extends Schema {
   public V135(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.registerType(
         false,
         References.PLAYER,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", References.ENTITY_TREE.in(â˜ƒ)),
               "Inventory",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               "EnderItems",
               DSL.list(References.ITEM_STACK.in(â˜ƒ))
            )
      );
      â˜ƒ.registerType(
         true, References.ENTITY_TREE, () -> DSL.optionalFields("Passengers", DSL.list(References.ENTITY_TREE.in(â˜ƒ)), References.ENTITY.in(â˜ƒ))
      );
   }
}
