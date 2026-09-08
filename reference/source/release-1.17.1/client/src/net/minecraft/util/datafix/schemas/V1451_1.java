package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1451_1 extends NamespacedSchema {
   public V1451_1(int var1, Schema var2) {
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
                  DSL.list(DSL.optionalFields("Palette", DSL.list(References.BLOCK_STATE.in(â˜ƒ))))
               )
            )
      );
   }
}
