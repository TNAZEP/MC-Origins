package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.TypeReferences;

public class V0102 extends Schema {
   public V0102(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(☃, ☃, ☃);
      ☃.registerType(
         true,
         TypeReferences.field_211295_k,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  TypeReferences.field_211301_q.in(☃),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag",
                     TypeReferences.field_211298_n.in(☃),
                     "BlockEntityTag",
                     TypeReferences.field_211294_j.in(☃),
                     "CanDestroy",
                     DSL.list(TypeReferences.field_211300_p.in(☃)),
                     "CanPlaceOn",
                     DSL.list(TypeReferences.field_211300_p.in(☃))
                  )
               ),
               V0099.field_206691_b,
               HookFunction.IDENTITY
            )
      );
   }
}
