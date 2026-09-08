package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class EntityHorseSplitFix extends EntityRenameFix {
   public EntityHorseSplitFix(Schema var1, boolean var2) {
      super("EntityHorseSplitFix", â˜ƒ, â˜ƒ);
   }

   @Override
   protected Pair<String, Typed<?>> fix(String var1, Typed<?> var2) {
      Dynamic<?> â˜ƒ = â˜ƒ.get(DSL.remainderFinder());
      if (Objects.equals("EntityHorse", â˜ƒ)) {
         int â˜ƒ = â˜ƒ.get("Type").asInt(0);

         String var4 = switch(â˜ƒ) {
            default -> "Horse";
            case 1 -> "Donkey";
            case 2 -> "Mule";
            case 3 -> "ZombieHorse";
            case 4 -> "SkeletonHorse";
         };
         â˜ƒ.remove("Type");
         Type<?> â˜ƒx = (Type)this.getOutputSchema().findChoiceType(References.ENTITY).types().get(var4);
         return Pair.of(
            var4,
            (Typed<?>)((Pair)â˜ƒ.write().flatMap(â˜ƒx::readTyped).result().orElseThrow(() -> new IllegalStateException("Could not parse the new horse")))
               .getFirst()
         );
      } else {
         return Pair.of(â˜ƒ, â˜ƒ);
      }
   }
}
