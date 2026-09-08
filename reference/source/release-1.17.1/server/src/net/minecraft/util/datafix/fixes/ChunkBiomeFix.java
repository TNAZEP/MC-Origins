package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public class ChunkBiomeFix extends DataFix {
   public ChunkBiomeFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      OpticFinder<?> â˜ƒx = â˜ƒ.findField("Level");
      return this.fixTypeEverywhereTyped("Leaves fix", â˜ƒ, var1x -> var1x.updateTyped(â˜ƒ, var0x -> var0x.update(DSL.remainderFinder(), var0xx -> {
               Optional<IntStream> â˜ƒ = var0xx.get("Biomes").asIntStreamOpt().result();
               if (!â˜ƒ.isPresent()) {
                  return var0xx;
               } else {
                  int[] â˜ƒ = ((IntStream)â˜ƒ.get()).toArray();
                  int[] â˜ƒx = new int[1024];

                  for(int â˜ƒxx = 0; â˜ƒxx < 4; ++â˜ƒxx) {
                     for(int â˜ƒxxx = 0; â˜ƒxxx < 4; ++â˜ƒxxx) {
                        int â˜ƒxxxx = (â˜ƒxxx << 2) + 2;
                        int â˜ƒxxxxx = (â˜ƒxx << 2) + 2;
                        int â˜ƒxxxxxx = â˜ƒxxxxx << 4 | â˜ƒxxxx;
                        â˜ƒx[â˜ƒxx << 2 | â˜ƒxxx] = â˜ƒxxxxxx < â˜ƒ.length ? â˜ƒ[â˜ƒxxxxxx] : -1;
                     }
                  }

                  for(int â˜ƒxx = 1; â˜ƒxx < 64; ++â˜ƒxx) {
                     System.arraycopy(â˜ƒx, 0, â˜ƒx, â˜ƒxx * 16, 16);
                  }

                  return var0xx.set("Biomes", var0xx.createIntList(Arrays.stream(â˜ƒx)));
               }
            })));
   }
}
