package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.stream.LongStream;
import net.minecraft.util.Mth;

public class BitStorageAlignFix extends DataFix {
   private static final int BIT_TO_LONG_SHIFT = 6;
   private static final int SECTION_WIDTH = 16;
   private static final int SECTION_HEIGHT = 16;
   private static final int SECTION_SIZE = 4096;
   private static final int HEIGHTMAP_BITS = 9;
   private static final int HEIGHTMAP_SIZE = 256;

   public BitStorageAlignFix(Schema var1) {
      super(â˜ƒ, false);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      Type<?> â˜ƒx = â˜ƒ.findFieldType("Level");
      OpticFinder<?> â˜ƒxx = DSL.fieldFinder("Level", â˜ƒx);
      OpticFinder<?> â˜ƒxxx = â˜ƒxx.type().findField("Sections");
      Type<?> â˜ƒxxxx = ((ListType)â˜ƒxxx.type()).getElement();
      OpticFinder<?> â˜ƒxxxxx = DSL.typeFinder(â˜ƒxxxx);
      Type<Pair<String, Dynamic<?>>> â˜ƒxxxxxx = DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType());
      OpticFinder<List<Pair<String, Dynamic<?>>>> â˜ƒxxxxxxx = DSL.fieldFinder("Palette", DSL.list(â˜ƒxxxxxx));
      return this.fixTypeEverywhereTyped(
         "BitStorageAlignFix",
         â˜ƒ,
         this.getOutputSchema().getType(References.CHUNK),
         var5x -> var5x.updateTyped(â˜ƒ, var4x -> this.updateHeightmaps(updateSections(â˜ƒ, â˜ƒ, â˜ƒ, var4x)))
      );
   }

   private Typed<?> updateHeightmaps(Typed<?> var1) {
      return â˜ƒ.update(
         DSL.remainderFinder(),
         var0 -> var0.update("Heightmaps", var1x -> var1x.updateMapValues(var1xx -> var1xx.mapSecond(var1xxx -> updateBitStorage(var0, var1xxx, 256, 9))))
      );
   }

   private static Typed<?> updateSections(OpticFinder<?> var0, OpticFinder<?> var1, OpticFinder<List<Pair<String, Dynamic<?>>>> var2, Typed<?> var3) {
      return â˜ƒ.updateTyped(
         â˜ƒ,
         var2x -> var2x.updateTyped(
               â˜ƒ,
               var1x -> {
                  int â˜ƒ = var1x.getOptional(â˜ƒ).map(var0x -> Math.max(4, DataFixUtils.ceillog2(var0x.size()))).orElse(0);
                  return â˜ƒ != 0 && !Mth.isPowerOfTwo(â˜ƒ)
                     ? var1x.update(DSL.remainderFinder(), var1xx -> var1xx.update("BlockStates", var2xx -> updateBitStorage(var1xx, var2xx, 4096, â˜ƒ)))
                     : var1x;
               }
            )
      );
   }

   private static Dynamic<?> updateBitStorage(Dynamic<?> var0, Dynamic<?> var1, int var2, int var3) {
      long[] â˜ƒ = â˜ƒ.asLongStream().toArray();
      long[] â˜ƒx = addPadding(â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ.createLongList(LongStream.of(â˜ƒx));
   }

   public static long[] addPadding(int var0, int var1, long[] var2) {
      int â˜ƒ = â˜ƒ.length;
      if (â˜ƒ == 0) {
         return â˜ƒ;
      } else {
         long â˜ƒ = (1L << â˜ƒ) - 1L;
         int â˜ƒx = 64 / â˜ƒ;
         int â˜ƒxx = (â˜ƒ + â˜ƒx - 1) / â˜ƒx;
         long[] â˜ƒxxx = new long[â˜ƒxx];
         int â˜ƒxxxx = 0;
         int â˜ƒxxxxx = 0;
         long â˜ƒxxxxxx = 0L;
         int â˜ƒxxxxxxx = 0;
         long â˜ƒxxxxxxxx = â˜ƒ[0];
         long â˜ƒxxxxxxxxx = â˜ƒ > 1 ? â˜ƒ[1] : 0L;

         for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < â˜ƒ; ++â˜ƒxxxxxxxxxx) {
            int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx * â˜ƒ;
            int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx >> 6;
            int â˜ƒxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxx + 1) * â˜ƒ - 1 >> 6;
            int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx ^ â˜ƒxxxxxxxxxxxx << 6;
            if (â˜ƒxxxxxxxxxxxx != â˜ƒxxxxxxx) {
               â˜ƒxxxxxxxx = â˜ƒxxxxxxxxx;
               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxxx + 1 < â˜ƒ ? â˜ƒ[â˜ƒxxxxxxxxxxxx + 1] : 0L;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxx;
            }

            long â˜ƒxxxxxxxxxxx;
            if (â˜ƒxxxxxxxxxxxx == â˜ƒxxxxxxxxxxxxx) {
               â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx >>> â˜ƒxxxxxxxxxxxxxx & â˜ƒ;
            } else {
               int â˜ƒxxxxxxxxxxx = 64 - â˜ƒxxxxxxxxxxxxxx;
               â˜ƒxxxxxxxxxxx = (â˜ƒxxxxxxxx >>> â˜ƒxxxxxxxxxxxxxx | â˜ƒxxxxxxxxx << â˜ƒxxxxxxxxxxx) & â˜ƒ;
            }

            int â˜ƒxxxxxxxxxxx = â˜ƒxxxxx + â˜ƒ;
            if (â˜ƒxxxxxxxxxxx >= 64) {
               â˜ƒxxx[â˜ƒxxxx++] = â˜ƒxxxxxx;
               â˜ƒxxxxxx = â˜ƒxxxxxxxxxxx;
               â˜ƒxxxxx = â˜ƒ;
            } else {
               â˜ƒxxxxxx |= â˜ƒxxxxxxxxxxx << â˜ƒxxxxx;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxxx;
            }
         }

         if (â˜ƒxxxxxx != 0L) {
            â˜ƒxxx[â˜ƒxxxx] = â˜ƒxxxxxx;
         }

         return â˜ƒxxx;
      }
   }
}
