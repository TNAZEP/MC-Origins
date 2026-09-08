package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1466 extends NamespacedSchema {
   public V1466(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(☃, ☃, ☃);
      ☃.registerType(
         false,
         TypeReferences.field_211287_c,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(TypeReferences.field_211298_n.in(☃)),
                  "TileEntities",
                  DSL.list(TypeReferences.field_211294_j.in(☃)),
                  "TileTicks",
                  DSL.list(DSL.fields("i", TypeReferences.field_211300_p.in(☃))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(TypeReferences.field_211296_l.in(☃)))),
                  "Structures",
                  DSL.optionalFields("Starts", DSL.compoundList(TypeReferences.field_211303_s.in(☃)))
               )
            )
      );
      ☃.registerType(
         false,
         TypeReferences.field_211303_s,
         () -> DSL.optionalFields(
               "Children",
               DSL.list(
                  DSL.optionalFields(
                     "CA",
                     TypeReferences.field_211296_l.in(☃),
                     "CB",
                     TypeReferences.field_211296_l.in(☃),
                     "CC",
                     TypeReferences.field_211296_l.in(☃),
                     "CD",
                     TypeReferences.field_211296_l.in(☃)
                  )
               ),
               "biome",
               TypeReferences.field_211305_u.in(☃)
            )
      );
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerBlockEntities(☃);
      ☃.put("DUMMY", DSL::remainder);
      return ☃;
   }
}
