package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class HorseSplit extends EntityRename {
   public HorseSplit(Schema var1, boolean var2) {
      super("EntityHorseSplitFix", ☃, ☃);
   }

   @Override
   protected Pair<String, Typed<?>> func_209149_a(String var1, Typed<?> var2) {
      Dynamic<?> ☃ = ☃.get(DSL.remainderFinder());
      if (Objects.equals("EntityHorse", ☃)) {
         int ☃xx = ☃.getInt("Type");
         String ☃x;
         switch(☃xx) {
            case 0:
            default:
               ☃x = "Horse";
               break;
            case 1:
               ☃x = "Donkey";
               break;
            case 2:
               ☃x = "Mule";
               break;
            case 3:
               ☃x = "ZombieHorse";
               break;
            case 4:
               ☃x = "SkeletonHorse";
         }

         ☃.remove("Type");
         Type<?> ☃x = (Type)this.getOutputSchema().findChoiceType(TypeReferences.field_211299_o).types().get(☃x);
         return Pair.of(
            ☃x, (Typed<?>)((Optional)☃x.readTyped(☃.write()).getSecond()).orElseThrow(() -> new IllegalStateException("Could not parse the new horse"))
         );
      } else {
         return Pair.of(☃, ☃);
      }
   }
}
