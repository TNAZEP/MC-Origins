package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1022 extends Schema {
   public V1022(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.registerType(false, References.RECIPE, () -> DSL.constType(NamespacedSchema.namespacedString()));
      â˜ƒ.registerType(
         false,
         References.PLAYER,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", References.ENTITY_TREE.in(â˜ƒ)),
               "Inventory",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               "EnderItems",
               DSL.list(References.ITEM_STACK.in(â˜ƒ)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  References.ENTITY_TREE.in(â˜ƒ),
                  "ShoulderEntityRight",
                  References.ENTITY_TREE.in(â˜ƒ),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(References.RECIPE.in(â˜ƒ)), "toBeDisplayed", DSL.list(References.RECIPE.in(â˜ƒ)))
               )
            )
      );
      â˜ƒ.registerType(false, References.HOTBAR, () -> DSL.compoundList(DSL.list(References.ITEM_STACK.in(â˜ƒ))));
   }
}
