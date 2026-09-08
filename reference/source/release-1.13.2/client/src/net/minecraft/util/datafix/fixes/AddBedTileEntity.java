package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class AddBedTileEntity extends DataFix {
   public AddBedTileEntity(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getOutputSchema().getType(TypeReferences.field_211287_c);
      Type<?> ☃x = ☃.findFieldType("Level");
      Type<?> ☃xx = ☃x.findFieldType("TileEntities");
      if (!(☃xx instanceof ListType)) {
         throw new IllegalStateException("Tile entity type is not a list type.");
      } else {
         ListType<?> ☃ = (ListType)☃xx;
         return this.func_206296_a(☃x, ☃);
      }
   }

   private <TE> TypeRewriteRule func_206296_a(Type<?> var1, ListType<TE> var2) {
      Type<TE> ☃ = ☃.getElement();
      OpticFinder<?> ☃x = DSL.fieldFinder("Level", ☃);
      OpticFinder<List<TE>> ☃xx = DSL.fieldFinder("TileEntities", ☃);
      int ☃xxx = 416;
      return TypeRewriteRule.seq(
         this.fixTypeEverywhere(
            "InjectBedBlockEntityType",
            this.getInputSchema().findChoiceType(TypeReferences.field_211294_j),
            this.getOutputSchema().findChoiceType(TypeReferences.field_211294_j),
            var0 -> var0x -> var0x
         ),
         this.fixTypeEverywhereTyped(
            "BedBlockEntityInjecter",
            this.getOutputSchema().getType(TypeReferences.field_211287_c),
            var3x -> {
               Typed<?> ☃ = var3x.getTyped(☃);
               Dynamic<?> ☃x = ☃.get(DSL.remainderFinder());
               int ☃xx = ☃x.getInt("xPos");
               int ☃xxx = ☃x.getInt("zPos");
               List<TE> ☃xxxx = Lists.<TE>newArrayList(☃.getOrCreate(☃));
               List<? extends Dynamic<?>> ☃xxxxx = (List)((Stream)☃x.get("Sections").flatMap(Dynamic::getStream).orElse(Stream.empty()))
                  .collect(Collectors.toList());
      
               for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxxxx.size(); ++☃xxxxxx) {
                  Dynamic<?> ☃xxxxxxx = (Dynamic)☃xxxxx.get(☃xxxxxx);
                  int ☃xxxxxxxx = ((Number)☃xxxxxxx.get("Y").flatMap(Dynamic::getNumberValue).orElse(0)).intValue();
                  Stream<Integer> ☃xxxxxxxxx = ((Stream)☃xxxxxxx.get("Blocks").flatMap(Dynamic::getStream).orElse(Stream.empty()))
                     .map(var0x -> ((Number)var0x.getNumberValue().orElse(0)).intValue());
                  int ☃xxxxxxxxxx = 0;
      
                  for(int ☃xxxxxxxxxxx : ☃xxxxxxxxx::iterator) {
                     if (416 == (☃xxxxxxxxxxx & 0xFF) << 4) {
                        int ☃xxxxxxxxxxxx = ☃xxxxxxxxxx & 15;
                        int ☃xxxxxxxxxxxxx = ☃xxxxxxxxxx >> 8 & 15;
                        int ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxx >> 4 & 15;
                        Map<Dynamic<?>, Dynamic<?>> ☃xxxxxxxxxxxxxxx = Maps.<Dynamic<?>, Dynamic<?>>newHashMap();
                        ☃xxxxxxxxxxxxxxx.put(☃xxxxxxx.createString("id"), ☃xxxxxxx.createString("minecraft:bed"));
                        ☃xxxxxxxxxxxxxxx.put(☃xxxxxxx.createString("x"), ☃xxxxxxx.createInt(☃xxxxxxxxxxxx + (☃xx << 4)));
                        ☃xxxxxxxxxxxxxxx.put(☃xxxxxxx.createString("y"), ☃xxxxxxx.createInt(☃xxxxxxxxxxxxx + (☃xxxxxxxx << 4)));
                        ☃xxxxxxxxxxxxxxx.put(☃xxxxxxx.createString("z"), ☃xxxxxxx.createInt(☃xxxxxxxxxxxxxx + (☃xxx << 4)));
                        ☃xxxxxxxxxxxxxxx.put(☃xxxxxxx.createString("color"), ☃xxxxxxx.createShort((short)14));
                        ☃xxxx.add(
                           ((Optional)☃.read(☃xxxxxxx.createMap(☃xxxxxxxxxxxxxxx)).getSecond())
                              .orElseThrow(() -> new IllegalStateException("Could not parse newly created bed block entity."))
                        );
                     }
      
                     ++☃xxxxxxxxxx;
                  }
               }
      
               return !☃xxxx.isEmpty() ? var3x.set(☃, ☃.set(☃, ☃xxxx)) : var3x;
            }
         )
      );
   }
}
