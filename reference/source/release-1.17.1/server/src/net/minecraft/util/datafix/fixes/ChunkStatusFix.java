package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class ChunkStatusFix extends DataFix {
   public ChunkStatusFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      Type<?> â˜ƒx = â˜ƒ.findFieldType("Level");
      OpticFinder<?> â˜ƒxx = DSL.fieldFinder("Level", â˜ƒx);
      return this.fixTypeEverywhereTyped("ChunkStatusFix", â˜ƒ, this.getOutputSchema().getType(References.CHUNK), var1x -> var1x.updateTyped(â˜ƒ, var0x -> {
            Dynamic<?> â˜ƒ = var0x.get(DSL.remainderFinder());
            String â˜ƒx = â˜ƒ.get("Status").asString("empty");
            if (Objects.equals(â˜ƒx, "postprocessed")) {
               â˜ƒ = â˜ƒ.set("Status", â˜ƒ.createString("fullchunk"));
            }

            return var0x.set(DSL.remainderFinder(), â˜ƒ);
         }));
   }
}
