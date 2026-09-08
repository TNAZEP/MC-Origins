package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.UUID;

public class EntityStringUuidFix extends DataFix {
   public EntityStringUuidFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityStringUuidFix",
         this.getInputSchema().getType(References.ENTITY),
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> {
                  Optional<String> â˜ƒ = var0x.get("UUID").asString().result();
                  if (â˜ƒ.isPresent()) {
                     UUID â˜ƒx = UUID.fromString((String)â˜ƒ.get());
                     return var0x.remove("UUID")
                        .set("UUIDMost", var0x.createLong(â˜ƒx.getMostSignificantBits()))
                        .set("UUIDLeast", var0x.createLong(â˜ƒx.getLeastSignificantBits()));
                  } else {
                     return var0x;
                  }
               }
            )
      );
   }
}
