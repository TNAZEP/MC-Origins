package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class BlockEntityJukeboxFix extends NamedEntityFix {
   public BlockEntityJukeboxFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "BlockEntityJukeboxFix", References.BLOCK_ENTITY, "minecraft:jukebox");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      Type<?> â˜ƒ = this.getInputSchema().getChoiceType(References.BLOCK_ENTITY, "minecraft:jukebox");
      Type<?> â˜ƒx = â˜ƒ.findFieldType("RecordItem");
      OpticFinder<?> â˜ƒxx = DSL.fieldFinder("RecordItem", â˜ƒx);
      Dynamic<?> â˜ƒxxx = â˜ƒ.get(DSL.remainderFinder());
      int â˜ƒxxxx = â˜ƒxxx.get("Record").asInt(0);
      if (â˜ƒxxxx > 0) {
         â˜ƒxxx.remove("Record");
         String â˜ƒxxxxx = ItemStackTheFlatteningFix.updateItem(ItemIdFix.getItem(â˜ƒxxxx), 0);
         if (â˜ƒxxxxx != null) {
            Dynamic<?> â˜ƒxxxxxx = â˜ƒxxx.emptyMap();
            â˜ƒxxxxxx = â˜ƒxxxxxx.set("id", â˜ƒxxxxxx.createString(â˜ƒxxxxx));
            â˜ƒxxxxxx = â˜ƒxxxxxx.set("Count", â˜ƒxxxxxx.createByte((byte)1));
            return â˜ƒ.set(
                  â˜ƒxx,
                  (Typed)((Pair)â˜ƒx.readTyped(â˜ƒxxxxxx).result().orElseThrow(() -> new IllegalStateException("Could not create record item stack.")))
                     .getFirst()
               )
               .set(DSL.remainderFinder(), â˜ƒxxx);
         }
      }

      return â˜ƒ;
   }
}
