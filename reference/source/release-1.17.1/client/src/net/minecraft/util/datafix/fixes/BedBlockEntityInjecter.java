package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class BedBlockEntityInjecter extends DataFix {
   public BedBlockEntityInjecter(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getOutputSchema().getType(References.CHUNK);
      Type<?> â˜ƒx = â˜ƒ.findFieldType("Level");
      Type<?> â˜ƒxx = â˜ƒx.findFieldType("TileEntities");
      if (!(â˜ƒxx instanceof ListType)) {
         throw new IllegalStateException("Tile entity type is not a list type.");
      } else {
         ListType<?> â˜ƒ = (ListType)â˜ƒxx;
         return this.cap(â˜ƒx, â˜ƒ);
      }
   }

   private <TE> TypeRewriteRule cap(Type<?> var1, ListType<TE> var2) {
      Type<TE> â˜ƒ = â˜ƒ.getElement();
      OpticFinder<?> â˜ƒx = DSL.fieldFinder("Level", â˜ƒ);
      OpticFinder<List<TE>> â˜ƒxx = DSL.fieldFinder("TileEntities", â˜ƒ);
      int â˜ƒxxx = 416;
      return TypeRewriteRule.seq(
         this.fixTypeEverywhere(
            "InjectBedBlockEntityType",
            this.getInputSchema().findChoiceType(References.BLOCK_ENTITY),
            this.getOutputSchema().findChoiceType(References.BLOCK_ENTITY),
            var0 -> var0x -> var0x
         ),
         this.fixTypeEverywhereTyped(
            "BedBlockEntityInjecter",
            this.getOutputSchema().getType(References.CHUNK),
            var3x -> {
               Typed<?> â˜ƒ = var3x.getTyped(â˜ƒ);
               Dynamic<?> â˜ƒx = â˜ƒ.get(DSL.remainderFinder());
               int â˜ƒxx = â˜ƒx.get("xPos").asInt(0);
               int â˜ƒxxx = â˜ƒx.get("zPos").asInt(0);
               List<TE> â˜ƒxxxx = Lists.<TE>newArrayList(â˜ƒ.getOrCreate(â˜ƒ));
               List<? extends Dynamic<?>> â˜ƒxxxxx = â˜ƒx.get("Sections").asList(Function.identity());
      
               for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxxx.size(); ++â˜ƒxxxxxx) {
                  Dynamic<?> â˜ƒxxxxxxx = (Dynamic)â˜ƒxxxxx.get(â˜ƒxxxxxx);
                  int â˜ƒxxxxxxxx = â˜ƒxxxxxxx.get("Y").asInt(0);
                  Stream<Integer> â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.get("Blocks").asStream().map(var0x -> var0x.asInt(0));
                  int â˜ƒxxxxxxxxxx = 0;
      
                  for(int â˜ƒxxxxxxxxxxx : â˜ƒxxxxxxxxx::iterator) {
                     if (416 == (â˜ƒxxxxxxxxxxx & 0xFF) << 4) {
                        int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx & 15;
                        int â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx >> 8 & 15;
                        int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx >> 4 & 15;
                        Map<Dynamic<?>, Dynamic<?>> â˜ƒxxxxxxxxxxxxxxx = Maps.<Dynamic<?>, Dynamic<?>>newHashMap();
                        â˜ƒxxxxxxxxxxxxxxx.put(â˜ƒxxxxxxx.createString("id"), â˜ƒxxxxxxx.createString("minecraft:bed"));
                        â˜ƒxxxxxxxxxxxxxxx.put(â˜ƒxxxxxxx.createString("x"), â˜ƒxxxxxxx.createInt(â˜ƒxxxxxxxxxxxx + (â˜ƒxx << 4)));
                        â˜ƒxxxxxxxxxxxxxxx.put(â˜ƒxxxxxxx.createString("y"), â˜ƒxxxxxxx.createInt(â˜ƒxxxxxxxxxxxxx + (â˜ƒxxxxxxxx << 4)));
                        â˜ƒxxxxxxxxxxxxxxx.put(â˜ƒxxxxxxx.createString("z"), â˜ƒxxxxxxx.createInt(â˜ƒxxxxxxxxxxxxxx + (â˜ƒxxx << 4)));
                        â˜ƒxxxxxxxxxxxxxxx.put(â˜ƒxxxxxxx.createString("color"), â˜ƒxxxxxxx.createShort((short)14));
                        â˜ƒxxxx.add(
                           ((Pair)â˜ƒ.read(â˜ƒxxxxxxx.createMap(â˜ƒxxxxxxxxxxxxxxx))
                                 .result()
                                 .orElseThrow(() -> new IllegalStateException("Could not parse newly created bed block entity.")))
                              .getFirst()
                        );
                     }
      
                     ++â˜ƒxxxxxxxxxx;
                  }
               }
      
               return !â˜ƒxxxx.isEmpty() ? var3x.set(â˜ƒ, â˜ƒ.set(â˜ƒ, â˜ƒxxxx)) : var3x;
            }
         )
      );
   }
}
