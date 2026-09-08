package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.datafix.TypeReferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrappedChestTileEntitySplit extends DataFix {
   private static final Logger field_212536_a = LogManager.getLogger();

   public TrappedChestTileEntitySplit(Schema var1, boolean var2) {
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
         OpticFinder<? extends List<?>> ☃x = DSL.fieldFinder("TileEntities", ☃);
         Type<?> ☃xx = this.getInputSchema().getType(TypeReferences.field_211287_c);
         OpticFinder<?> ☃xxx = ☃xx.findField("Level");
         OpticFinder<?> ☃xxxx = ☃xxx.type().findField("Sections");
         Type<?> ☃xxxxx = ☃xxxx.type();
         if (!(☃xxxxx instanceof ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
         } else {
            Type<?> ☃ = ((ListType)☃xxxxx).getElement();
            OpticFinder<?> ☃x = DSL.typeFinder(☃);
            return TypeRewriteRule.seq(
               new AddNewChoices(this.getOutputSchema(), "AddTrappedChestFix", TypeReferences.field_211294_j).makeRule(),
               this.fixTypeEverywhereTyped("Trapped Chest fix", ☃xx, var5x -> var5x.updateTyped(☃, var4x -> {
                     Optional<? extends Typed<?>> ☃ = var4x.getOptionalTyped(☃);
                     if (!☃.isPresent()) {
                        return var4x;
                     } else {
                        List<? extends Typed<?>> ☃ = ((Typed)☃.get()).getAllTyped(☃);
                        IntSet ☃x = new IntOpenHashSet();
   
                        for(Typed<?> ☃xx : ☃) {
                           TrappedChestTileEntitySplit.Section ☃xxx = new TrappedChestTileEntitySplit.Section(☃xx, this.getInputSchema());
                           if (!☃xxx.func_208461_a()) {
                              for(int ☃xxxx = 0; ☃xxxx < 4096; ++☃xxxx) {
                                 int ☃xxxxx = ☃xxx.func_208453_a(☃xxxx);
                                 if (☃xxx.func_212511_a(☃xxxxx)) {
                                    ☃x.add(☃xxx.func_208456_b() << 12 | ☃xxxx);
                                 }
                              }
                           }
                        }
   
                        Dynamic<?> ☃xx = var4x.get(DSL.remainderFinder());
                        int ☃xxx = ☃xx.getInt("xPos");
                        int ☃xxxx = ☃xx.getInt("zPos");
                        TaggedChoiceType<String> ☃xxxxx = this.getInputSchema().findChoiceType(TypeReferences.field_211294_j);
                        return var4x.updateTyped(☃, var4xx -> var4xx.updateTyped(☃.finder(), var4xxx -> {
                              Dynamic<?> ☃ = var4xxx.getOrCreate(DSL.remainderFinder());
                              int ☃x = ☃.getInt("x") - (☃ << 4);
                              int ☃xx = ☃.getInt("y");
                              int ☃xxx = ☃.getInt("z") - (☃ << 4);
                              return ☃.contains(LeavesFix.func_208411_a(☃x, ☃xx, ☃xxx)) ? var4xxx.update(☃.finder(), var0x -> var0x.mapFirst(var0xx -> {
                                    if (!Objects.equals(var0xx, "minecraft:chest")) {
                                       field_212536_a.warn("Block Entity was expected to be a chest");
                                    }
   
                                    return "minecraft:trapped_chest";
                                 })) : var4xxx;
                           }));
                     }
                  }))
            );
         }
      }
   }

   public static final class Section extends LeavesFix.Section {
      @Nullable
      private IntSet field_212512_f;

      public Section(Typed<?> var1, Schema var2) {
         super(☃, ☃);
      }

      @Override
      protected boolean func_212508_a() {
         this.field_212512_f = new IntOpenHashSet();

         for(int ☃ = 0; ☃ < this.field_208469_d.size(); ++☃) {
            Dynamic<?> ☃x = (Dynamic)this.field_208469_d.get(☃);
            String ☃xx = ☃x.getString("Name");
            if (Objects.equals(☃xx, "minecraft:trapped_chest")) {
               this.field_212512_f.add(☃);
            }
         }

         return this.field_212512_f.isEmpty();
      }

      public boolean func_212511_a(int var1) {
         return this.field_212512_f.contains(☃);
      }
   }
}
