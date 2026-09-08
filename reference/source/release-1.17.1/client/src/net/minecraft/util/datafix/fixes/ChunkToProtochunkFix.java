package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ChunkToProtochunkFix extends DataFix {
   private static final int NUM_SECTIONS = 16;

   public ChunkToProtochunkFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.CHUNK);
      Type<?> â˜ƒx = this.getOutputSchema().getType(References.CHUNK);
      Type<?> â˜ƒxx = â˜ƒ.findFieldType("Level");
      Type<?> â˜ƒxxx = â˜ƒx.findFieldType("Level");
      Type<?> â˜ƒxxxx = â˜ƒxx.findFieldType("TileTicks");
      OpticFinder<?> â˜ƒxxxxx = DSL.fieldFinder("Level", â˜ƒxx);
      OpticFinder<?> â˜ƒxxxxxx = DSL.fieldFinder("TileTicks", â˜ƒxxxx);
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped(
            "ChunkToProtoChunkFix",
            â˜ƒ,
            this.getOutputSchema().getType(References.CHUNK),
            var3x -> var3x.updateTyped(
                  â˜ƒ,
                  â˜ƒ,
                  var2x -> {
                     Optional<? extends Stream<? extends Dynamic<?>>> â˜ƒx = var2x.getOptionalTyped(â˜ƒ)
                        .flatMap(var0x -> var0x.write().result())
                        .flatMap(var0x -> var0x.asStreamOpt().result());
                     Dynamic<?> â˜ƒxx = var2x.get(DSL.remainderFinder());
                     boolean â˜ƒxxx = â˜ƒxx.get("TerrainPopulated").asBoolean(false)
                        && (!â˜ƒxx.get("LightPopulated").asNumber().result().isPresent() || â˜ƒxx.get("LightPopulated").asBoolean(false));
                     â˜ƒxx = â˜ƒxx.set("Status", â˜ƒxx.createString(â˜ƒxxx ? "mobs_spawned" : "empty"));
                     â˜ƒxx = â˜ƒxx.set("hasLegacyStructureData", â˜ƒxx.createBoolean(true));
                     Dynamic<?> â˜ƒ;
                     if (â˜ƒxxx) {
                        Optional<ByteBuffer> â˜ƒxxxx = â˜ƒxx.get("Biomes").asByteBufferOpt().result();
                        if (â˜ƒxxxx.isPresent()) {
                           ByteBuffer â˜ƒxxxxx = (ByteBuffer)â˜ƒxxxx.get();
                           int[] â˜ƒxxxxxx = new int[256];
         
                           for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxxxx.length; ++â˜ƒxxxxxxx) {
                              if (â˜ƒxxxxxxx < â˜ƒxxxxx.capacity()) {
                                 â˜ƒxxxxxx[â˜ƒxxxxxxx] = â˜ƒxxxxx.get(â˜ƒxxxxxxx) & 255;
                              }
                           }
         
                           â˜ƒxx = â˜ƒxx.set("Biomes", â˜ƒxx.createIntList(Arrays.stream(â˜ƒxxxxxx)));
                        }
         
                        Dynamic<?> â˜ƒxxxx = â˜ƒxx;
                        List<ShortList> â˜ƒxxxxx = (List)IntStream.range(0, 16).mapToObj(var0x -> new ShortArrayList()).collect(Collectors.toList());
                        if (â˜ƒx.isPresent()) {
                           ((Stream)â˜ƒx.get()).forEach(var1x -> {
                              int â˜ƒ = var1x.get("x").asInt(0);
                              int â˜ƒx = var1x.get("y").asInt(0);
                              int â˜ƒxx = var1x.get("z").asInt(0);
                              short â˜ƒxxx = packOffsetCoordinates(â˜ƒ, â˜ƒx, â˜ƒxx);
                              ((ShortList)â˜ƒ.get(â˜ƒx >> 4)).add(â˜ƒxxx);
                           });
                           â˜ƒxx = â˜ƒxx.set(
                              "ToBeTicked", â˜ƒxx.createList(â˜ƒxxxxx.stream().map(var1x -> â˜ƒ.createList(var1x.stream().map(â˜ƒ::createShort))))
                           );
                        }
         
                        â˜ƒ = DataFixUtils.orElse(var2x.set(DSL.remainderFinder(), â˜ƒxx).write().result(), â˜ƒxx);
                     } else {
                        â˜ƒ = â˜ƒxx;
                     }
         
                     return (Typed)((Pair)â˜ƒ.readTyped(â˜ƒ).result().orElseThrow(() -> new IllegalStateException("Could not read the new chunk"))).getFirst();
                  }
               )
         ),
         this.writeAndRead(
            "Structure biome inject", this.getInputSchema().getType(References.STRUCTURE_FEATURE), this.getOutputSchema().getType(References.STRUCTURE_FEATURE)
         )
      );
   }

   private static short packOffsetCoordinates(int var0, int var1, int var2) {
      return (short)(â˜ƒ & 15 | (â˜ƒ & 15) << 4 | (â˜ƒ & 15) << 8);
   }
}
