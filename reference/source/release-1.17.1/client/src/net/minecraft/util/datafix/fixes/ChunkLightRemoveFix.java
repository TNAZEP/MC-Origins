package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class ChunkLightRemoveFix extends DataFix {
   public ChunkLightRemoveFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      Type<?> â˜ƒx = â˜ƒ.findFieldType("Level");
      OpticFinder<?> â˜ƒxx = DSL.fieldFinder("Level", â˜ƒx);
      return this.fixTypeEverywhereTyped(
         "ChunkLightRemoveFix",
         â˜ƒ,
         this.getOutputSchema().getType(References.CHUNK),
         var1x -> var1x.updateTyped(â˜ƒ, var0x -> var0x.update(DSL.remainderFinder(), var0xx -> var0xx.remove("isLightOn")))
      );
   }
}
