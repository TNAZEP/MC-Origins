package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.TypeReferences;

public class ShulkerBoxItemColor extends DataFix {
   public static final String[] field_191278_a = new String[]{
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

   public ShulkerBoxItemColor(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211295_k);
      OpticFinder<Pair<String, String>> ☃x = DSL.fieldFinder("id", DSL.named(TypeReferences.field_211301_q.typeName(), DSL.namespacedString()));
      OpticFinder<?> ☃xx = ☃.findField("tag");
      OpticFinder<?> ☃xxx = ☃xx.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped(
         "ItemShulkerBoxColorFix",
         ☃,
         var3x -> {
            Optional<Pair<String, String>> ☃ = var3x.getOptional(☃);
            if (☃.isPresent() && Objects.equals(((Pair)☃.get()).getSecond(), "minecraft:shulker_box")) {
               Optional<? extends Typed<?>> ☃x = var3x.getOptionalTyped(☃);
               if (☃x.isPresent()) {
                  Typed<?> ☃xx = (Typed)☃x.get();
                  Optional<? extends Typed<?>> ☃xxx = ☃xx.getOptionalTyped(☃);
                  if (☃xxx.isPresent()) {
                     Typed<?> ☃xxxx = (Typed)☃xxx.get();
                     Dynamic<?> ☃xxxxx = ☃xxxx.get(DSL.remainderFinder());
                     int ☃xxxxxx = ☃xxxxx.getInt("Color");
                     ☃xxxxx.remove("Color");
                     return var3x.set(☃, ☃xx.set(☃, ☃xxxx.set(DSL.remainderFinder(), ☃xxxxx)))
                        .set(☃, Pair.of(TypeReferences.field_211301_q.typeName(), field_191278_a[☃xxxxxx % 16]));
                  }
               }
            }
   
            return var3x;
         }
      );
   }
}
