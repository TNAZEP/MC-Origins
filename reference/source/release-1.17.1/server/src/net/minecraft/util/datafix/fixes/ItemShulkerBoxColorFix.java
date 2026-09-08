package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class ItemShulkerBoxColorFix extends DataFix {
   public static final String[] NAMES_BY_COLOR = new String[]{
      "minecraft:white_shulker_box",
      "minecraft:orange_shulker_box",
      "minecraft:magenta_shulker_box",
      "minecraft:light_blue_shulker_box",
      "minecraft:yellow_shulker_box",
      "minecraft:lime_shulker_box",
      "minecraft:pink_shulker_box",
      "minecraft:gray_shulker_box",
      "minecraft:silver_shulker_box",
      "minecraft:cyan_shulker_box",
      "minecraft:purple_shulker_box",
      "minecraft:blue_shulker_box",
      "minecraft:brown_shulker_box",
      "minecraft:green_shulker_box",
      "minecraft:red_shulker_box",
      "minecraft:black_shulker_box"
   };

   public ItemShulkerBoxColorFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<Pair<String, String>> â˜ƒx = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
      OpticFinder<?> â˜ƒxx = â˜ƒ.findField("tag");
      OpticFinder<?> â˜ƒxxx = â˜ƒxx.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped(
         "ItemShulkerBoxColorFix",
         â˜ƒ,
         var3x -> {
            Optional<Pair<String, String>> â˜ƒ = var3x.getOptional(â˜ƒ);
            if (â˜ƒ.isPresent() && Objects.equals(((Pair)â˜ƒ.get()).getSecond(), "minecraft:shulker_box")) {
               Optional<? extends Typed<?>> â˜ƒx = var3x.getOptionalTyped(â˜ƒ);
               if (â˜ƒx.isPresent()) {
                  Typed<?> â˜ƒxx = (Typed)â˜ƒx.get();
                  Optional<? extends Typed<?>> â˜ƒxxx = â˜ƒxx.getOptionalTyped(â˜ƒ);
                  if (â˜ƒxxx.isPresent()) {
                     Typed<?> â˜ƒxxxx = (Typed)â˜ƒxxx.get();
                     Dynamic<?> â˜ƒxxxxx = â˜ƒxxxx.get(DSL.remainderFinder());
                     int â˜ƒxxxxxx = â˜ƒxxxxx.get("Color").asInt(0);
                     â˜ƒxxxxx.remove("Color");
                     return var3x.set(â˜ƒ, â˜ƒxx.set(â˜ƒ, â˜ƒxxxx.set(DSL.remainderFinder(), â˜ƒxxxxx)))
                        .set(â˜ƒ, Pair.of(References.ITEM_NAME.typeName(), NAMES_BY_COLOR[â˜ƒxxxxxx % 16]));
                  }
               }
            }
   
            return var3x;
         }
      );
   }
}
