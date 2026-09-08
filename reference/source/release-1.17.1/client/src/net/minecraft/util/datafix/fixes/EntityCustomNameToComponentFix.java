package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class EntityCustomNameToComponentFix extends DataFix {
   public EntityCustomNameToComponentFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      OpticFinder<String> â˜ƒ = DSL.fieldFinder("id", NamespacedSchema.namespacedString());
      return this.fixTypeEverywhereTyped(
         "EntityCustomNameToComponentFix", this.getInputSchema().getType(References.ENTITY), var1x -> var1x.update(DSL.remainderFinder(), var2 -> {
               Optional<String> â˜ƒ = var1x.getOptional(â˜ƒ);
               return â˜ƒ.isPresent() && Objects.equals(â˜ƒ.get(), "minecraft:commandblock_minecart") ? var2 : fixTagCustomName(var2);
            })
      );
   }

   public static Dynamic<?> fixTagCustomName(Dynamic<?> var0) {
      String â˜ƒ = â˜ƒ.get("CustomName").asString("");
      return â˜ƒ.isEmpty() ? â˜ƒ.remove("CustomName") : â˜ƒ.set("CustomName", â˜ƒ.createString(Component.Serializer.toJson(new TextComponent(â˜ƒ))));
   }
}
