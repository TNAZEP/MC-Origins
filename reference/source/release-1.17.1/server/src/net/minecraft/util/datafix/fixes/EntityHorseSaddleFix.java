package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class EntityHorseSaddleFix extends NamedEntityFix {
   public EntityHorseSaddleFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ, "EntityHorseSaddleFix", References.ENTITY, "EntityHorse");
   }

   @Override
   protected Typed<?> fix(Typed<?> var1) {
      OpticFinder<Pair<String, String>> â˜ƒ = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      Type<?> â˜ƒx = this.getInputSchema().getTypeRaw(References.ITEM_STACK);
      OpticFinder<?> â˜ƒxx = DSL.fieldFinder("SaddleItem", â˜ƒx);
      Optional<? extends Typed<?>> â˜ƒxxx = â˜ƒ.getOptionalTyped(â˜ƒxx);
      Dynamic<?> â˜ƒxxxx = â˜ƒ.get(DSL.remainderFinder());
      if (!â˜ƒxxx.isPresent() && â˜ƒxxxx.get("Saddle").asBoolean(false)) {
         Typed<?> â˜ƒxxxxx = (Typed)â˜ƒx.pointTyped(â˜ƒ.getOps()).orElseThrow(IllegalStateException::new);
         â˜ƒxxxxx = â˜ƒxxxxx.set(â˜ƒ, Pair.of(References.ITEM_NAME.typeName(), "minecraft:saddle"));
         Dynamic<?> â˜ƒxxxxxx = â˜ƒxxxx.emptyMap();
         â˜ƒxxxxxx = â˜ƒxxxxxx.set("Count", â˜ƒxxxxxx.createByte((byte)1));
         â˜ƒxxxxxx = â˜ƒxxxxxx.set("Damage", â˜ƒxxxxxx.createShort((short)0));
         â˜ƒxxxxx = â˜ƒxxxxx.set(DSL.remainderFinder(), â˜ƒxxxxxx);
         â˜ƒxxxx.remove("Saddle");
         return â˜ƒ.set(â˜ƒxx, â˜ƒxxxxx).set(DSL.remainderFinder(), â˜ƒxxxx);
      } else {
         return â˜ƒ;
      }
   }
}
