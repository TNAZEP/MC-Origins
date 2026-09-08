package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrappedChestBlockEntityFix extends DataFix {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int SIZE = 4096;
   private static final short SIZE_BITS = 12;

   public TrappedChestBlockEntityFix(Schema var1, boolean var2) {
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
         OpticFinder<? extends List<?>> â˜ƒx = DSL.fieldFinder("TileEntities", â˜ƒ);
         Type<?> â˜ƒxx = this.getInputSchema().getType(References.CHUNK);
         OpticFinder<?> â˜ƒxxx = â˜ƒxx.findField("Level");
         OpticFinder<?> â˜ƒxxxx = â˜ƒxxx.type().findField("Sections");
         Type<?> â˜ƒxxxxx = â˜ƒxxxx.type();
         if (!(â˜ƒxxxxx instanceof ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
         } else {
            Type<?> â˜ƒ = ((ListType)â˜ƒxxxxx).getElement();
            OpticFinder<?> â˜ƒx = DSL.typeFinder(â˜ƒ);
            return TypeRewriteRule.seq(
               new AddNewChoices(this.getOutputSchema(), "AddTrappedChestFix", References.BLOCK_ENTITY).makeRule(),
               this.fixTypeEverywhereTyped(
                  "Trapped Chest fix",
                  â˜ƒxx,
                  var5x -> var5x.updateTyped(
                        â˜ƒ,
                        var4x -> {
                           Optional<? extends Typed<?>> â˜ƒ = var4x.getOptionalTyped(â˜ƒ);
                           if (!â˜ƒ.isPresent()) {
                              return var4x;
                           } else {
                              List<? extends Typed<?>> â˜ƒ = ((Typed)â˜ƒ.get()).getAllTyped(â˜ƒ);
                              IntSet â˜ƒx = new IntOpenHashSet();
         
                              for(Typed<?> â˜ƒxx : â˜ƒ) {
                                 TrappedChestBlockEntityFix.TrappedChestSection â˜ƒxxx = new TrappedChestBlockEntityFix.TrappedChestSection(
                                    â˜ƒxx, this.getInputSchema()
                                 );
                                 if (!â˜ƒxxx.isSkippable()) {
                                    for(int â˜ƒxxxx = 0; â˜ƒxxxx < 4096; ++â˜ƒxxxx) {
                                       int â˜ƒxxxxx = â˜ƒxxx.getBlock(â˜ƒxxxx);
                                       if (â˜ƒxxx.isTrappedChest(â˜ƒxxxxx)) {
                                          â˜ƒx.add(â˜ƒxxx.getIndex() << 12 | â˜ƒxxxx);
                                       }
                                    }
                                 }
                              }
         
                              Dynamic<?> â˜ƒxx = var4x.get(DSL.remainderFinder());
                              int â˜ƒxxx = â˜ƒxx.get("xPos").asInt(0);
                              int â˜ƒxxxx = â˜ƒxx.get("zPos").asInt(0);
                              TaggedChoiceType<String> â˜ƒxxxxx = this.getInputSchema().findChoiceType(References.BLOCK_ENTITY);
                              return var4x.updateTyped(â˜ƒ, var4xx -> var4xx.updateTyped(â˜ƒ.finder(), var4xxx -> {
                                    Dynamic<?> â˜ƒ = var4xxx.getOrCreate(DSL.remainderFinder());
                                    int â˜ƒx = â˜ƒ.get("x").asInt(0) - (â˜ƒ << 4);
                                    int â˜ƒxx = â˜ƒ.get("y").asInt(0);
                                    int â˜ƒxxx = â˜ƒ.get("z").asInt(0) - (â˜ƒ << 4);
                                    return â˜ƒ.contains(LeavesFix.getIndex(â˜ƒx, â˜ƒxx, â˜ƒxxx))
                                       ? var4xxx.update(â˜ƒ.finder(), var0x -> var0x.mapFirst(var0xx -> {
                                             if (!Objects.equals(var0xx, "minecraft:chest")) {
                                                LOGGER.warn("Block Entity was expected to be a chest");
                                             }
            
                                             return "minecraft:trapped_chest";
                                          }))
                                       : var4xxx;
                                 }));
                           }
                        }
                     )
               )
            );
         }
      }
   }

   public static final class TrappedChestSection extends LeavesFix.Section {
      @Nullable
      private IntSet chestIds;

      public TrappedChestSection(Typed<?> var1, Schema var2) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected boolean skippable() {
         this.chestIds = new IntOpenHashSet();

         for(int â˜ƒ = 0; â˜ƒ < this.palette.size(); ++â˜ƒ) {
            Dynamic<?> â˜ƒx = (Dynamic)this.palette.get(â˜ƒ);
            String â˜ƒxx = â˜ƒx.get("Name").asString("");
            if (Objects.equals(â˜ƒxx, "minecraft:trapped_chest")) {
               this.chestIds.add(â˜ƒ);
            }
         }

         return this.chestIds.isEmpty();
      }

      public boolean isTrappedChest(int var1) {
         return this.chestIds.contains(â˜ƒ);
      }
   }
}
