package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemStackUUIDFix extends AbstractUUIDFix {
   public ItemStackUUIDFix(Schema var1) {
      super(â˜ƒ, References.ITEM_STACK);
   }

   @Override
   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> â˜ƒ = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      return this.fixTypeEverywhereTyped("ItemStackUUIDFix", this.getInputSchema().getType(this.typeReference), var2 -> {
         OpticFinder<?> â˜ƒ = var2.getType().findField("tag");
         return var2.updateTyped(â˜ƒ, var3x -> var3x.update(DSL.remainderFinder(), var3xx -> {
               var3xx = this.updateAttributeModifiers(var3xx);
               if (var2.getOptional(â˜ƒ).map(var0 -> "minecraft:player_head".equals(var0.getSecond())).orElse(false)) {
                  var3xx = this.updateSkullOwner(var3xx);
               }

               return var3xx;
            }));
      });
   }

   private Dynamic<?> updateAttributeModifiers(Dynamic<?> var1) {
      return â˜ƒ.update(
         "AttributeModifiers", var1x -> â˜ƒ.createList(var1x.asStream().map(var0x -> (Dynamic)replaceUUIDLeastMost(var0x, "UUID", "UUID").orElse(var0x)))
      );
   }

   private Dynamic<?> updateSkullOwner(Dynamic<?> var1) {
      return â˜ƒ.update("SkullOwner", var0 -> (Dynamic)replaceUUIDString(var0, "Id", "Id").orElse(var0));
   }
}
