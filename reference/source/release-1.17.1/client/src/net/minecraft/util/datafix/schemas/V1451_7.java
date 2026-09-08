package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V1451_7 extends NamespacedSchema {
   public V1451_7(int var1, Schema var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(â˜ƒ, â˜ƒ, â˜ƒ);
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
               )
            )
      );
   }
}
