package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.TypeReferences;

public class V0106 extends Schema {
   public V0106(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(☃, ☃, ☃);
      ☃.registerType(
         true,
         TypeReferences.field_211302_r,
         () -> DSL.optionalFields(
               "SpawnPotentials", DSL.list(DSL.fields("Entity", TypeReferences.field_211298_n.in(☃))), "SpawnData", TypeReferences.field_211298_n.in(☃)
            )
      );
   }
}
