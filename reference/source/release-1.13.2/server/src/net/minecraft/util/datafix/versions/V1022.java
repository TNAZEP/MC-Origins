package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.TypeReferences;

public class V1022 extends Schema {
   public V1022(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(☃, ☃, ☃);
      ☃.registerType(false, TypeReferences.field_211304_t, () -> DSL.constType(DSL.namespacedString()));
      ☃.registerType(
         false,
         TypeReferences.field_211286_b,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", TypeReferences.field_211298_n.in(☃)),
               "Inventory",
               DSL.list(TypeReferences.field_211295_k.in(☃)),
               "EnderItems",
               DSL.list(TypeReferences.field_211295_k.in(☃)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  TypeReferences.field_211298_n.in(☃),
                  "ShoulderEntityRight",
                  TypeReferences.field_211298_n.in(☃),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(TypeReferences.field_211304_t.in(☃)), "toBeDisplayed", DSL.list(TypeReferences.field_211304_t.in(☃)))
               )
            )
      );
      ☃.registerType(false, TypeReferences.field_211288_d, () -> DSL.compoundList(DSL.list(TypeReferences.field_211295_k.in(☃))));
   }
}
