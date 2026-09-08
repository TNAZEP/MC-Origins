package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class JukeboxRecordItem extends NamedEntityFix {
   public JukeboxRecordItem(Schema var1, boolean var2) {
      super(☃, ☃, "BlockEntityJukeboxFix", TypeReferences.field_211294_j, "minecraft:jukebox");
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      Type<?> ☃ = this.getInputSchema().getChoiceType(TypeReferences.field_211294_j, "minecraft:jukebox");
      Type<?> ☃x = ☃.findFieldType("RecordItem");
      OpticFinder<?> ☃xx = DSL.fieldFinder("RecordItem", ☃x);
      Dynamic<?> ☃xxx = ☃.get(DSL.remainderFinder());
      int ☃xxxx = ☃xxx.getInt("Record");
      if (☃xxxx > 0) {
         ☃xxx.remove("Record");
         String ☃xxxxx = ItemStackDataFlattening.func_199175_a(ItemIntIDToString.func_199173_a(☃xxxx), 0);
         if (☃xxxxx != null) {
            Dynamic<?> ☃xxxxxx = ☃xxx.emptyMap();
            ☃xxxxxx = ☃xxxxxx.set("id", ☃xxxxxx.createString(☃xxxxx));
            ☃xxxxxx = ☃xxxxxx.set("Count", ☃xxxxxx.createByte((byte)1));
            return ☃.set(
                  ☃xx, (Typed)((Optional)☃x.readTyped(☃xxxxxx).getSecond()).orElseThrow(() -> new IllegalStateException("Could not create record item stack."))
               )
               .set(DSL.remainderFinder(), ☃xxx);
         }
      }

      return ☃;
   }
}
