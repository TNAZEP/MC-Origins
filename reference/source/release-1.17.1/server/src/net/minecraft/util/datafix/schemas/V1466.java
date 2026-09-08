package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1466 extends NamespacedSchema {
   public V1466(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.registerType(
         false,
         References.CHUNK,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(References.ENTITY_TREE.in(â˜ƒ)),
                  "TileEntities",
                  DSL.list(References.BLOCK_ENTITY.in(â˜ƒ)),
                  "TileTicks",
                  DSL.list(DSL.fields("i", References.BLOCK_NAME.in(â˜ƒ))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(References.BLOCK_STATE.in(â˜ƒ)))),
                  "Structures",
                  DSL.optionalFields("Starts", DSL.compoundList(References.STRUCTURE_FEATURE.in(â˜ƒ)))
               )
            )
      );
      â˜ƒ.registerType(
         false,
         References.STRUCTURE_FEATURE,
         () -> DSL.optionalFields(
               "Children",
               DSL.list(
                  DSL.optionalFields(
                     "CA",
                     References.BLOCK_STATE.in(â˜ƒ),
                     "CB",
                     References.BLOCK_STATE.in(â˜ƒ),
                     "CC",
                     References.BLOCK_STATE.in(â˜ƒ),
                     "CD",
                     References.BLOCK_STATE.in(â˜ƒ)
                  )
               ),
               "biome",
               References.BIOME.in(â˜ƒ)
            )
      );
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> â˜ƒ = super.registerBlockEntities(â˜ƒ);
      â˜ƒ.put("DUMMY", DSL::remainder);
      return â˜ƒ;
   }
}
