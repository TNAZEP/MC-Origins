package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class ChunkGenStatus extends DataFix {
   public ChunkGenStatus(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211287_c);
      Type<?> ☃x = this.getOutputSchema().getType(TypeReferences.field_211287_c);
      Type<?> ☃xx = ☃.findFieldType("Level");
      Type<?> ☃xxx = ☃x.findFieldType("Level");
      Type<?> ☃xxxx = ☃xx.findFieldType("TileTicks");
      OpticFinder<?> ☃xxxxx = DSL.fieldFinder("Level", ☃xx);
      OpticFinder<?> ☃xxxxxx = DSL.fieldFinder("TileTicks", ☃xxxx);
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped(
            "ChunkToProtoChunkFix",
            ☃,
            this.getOutputSchema().getType(TypeReferences.field_211287_c),
            var3x -> var3x.updateTyped(
                  ☃,
                  ☃,
                  var2x -> {
                     Optional<? extends Stream<? extends Dynamic<?>>> ☃x = var2x.getOptionalTyped(☃).map(Typed::write).flatMap(Dynamic::getStream);
                     Dynamic<?> ☃xx = var2x.get(DSL.remainderFinder());
                     boolean ☃xxx = ☃xx.getBoolean("TerrainPopulated")
                        && (!☃xx.get("LightPopulated").flatMap(Dynamic::getNumberValue).isPresent() || ☃xx.getBoolean("LightPopulated"));
                     ☃xx = ☃xx.set("Status", ☃xx.createString(☃xxx ? "mobs_spawned" : "empty"));
                     ☃xx = ☃xx.set("hasLegacyStructureData", ☃xx.createBoolean(true));
                     Dynamic<?> ☃;
                     if (☃xxx) {
                        Optional<ByteBuffer> ☃xxxx = ☃xx.get("Biomes").flatMap(Dynamic::getByteBuffer);
                        if (☃xxxx.isPresent()) {
                           ByteBuffer ☃xxxxx = (ByteBuffer)☃xxxx.get();
                           int[] ☃xxxxxx = new int[256];
         
                           for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxxx.length; ++☃xxxxxxx) {
                              if (☃xxxxxxx < ☃xxxxx.capacity()) {
                                 ☃xxxxxx[☃xxxxxxx] = ☃xxxxx.get(☃xxxxxxx) & 255;
                              }
                           }
         
                           ☃xx = ☃xx.set("Biomes", ☃xx.createIntList(Arrays.stream(☃xxxxxx)));
                        }
         
                        Dynamic<?> ☃xxxx = ☃xx;
                        List<Dynamic<?>> ☃xxxxx = (List)IntStream.range(0, 16).mapToObj(var1x -> ☃.createList(Stream.empty())).collect(Collectors.toList());
                        if (☃x.isPresent()) {
                           ((Stream)☃x.get()).forEach(var2xx -> {
                              int ☃ = var2xx.getInt("x");
                              int ☃x = var2xx.getInt("y");
                              int ☃xx = var2xx.getInt("z");
                              short ☃xxx = func_210975_a(☃, ☃x, ☃xx);
                              ☃.set(☃x >> 4, ((Dynamic)☃.get(☃x >> 4)).merge(☃.createShort(☃xxx)));
                           });
                           ☃xx = ☃xx.set("ToBeTicked", ☃xx.createList(☃xxxxx.stream()));
                        }
         
                        ☃ = var2x.set(DSL.remainderFinder(), ☃xx).write();
                     } else {
                        ☃ = ☃xx;
                     }
         
                     return (Typed)((Optional)☃.readTyped(☃).getSecond()).orElseThrow(() -> new IllegalStateException("Could not read the new chunk"));
                  }
               )
         ),
         this.writeAndRead(
            "Structure biome inject",
            this.getInputSchema().getType(TypeReferences.field_211303_s),
            this.getOutputSchema().getType(TypeReferences.field_211303_s)
         )
      );
   }

   private static short func_210975_a(int var0, int var1, int var2) {
      return (short)(☃ & 15 | (☃ & 15) << 4 | (☃ & 15) << 8);
   }
}
