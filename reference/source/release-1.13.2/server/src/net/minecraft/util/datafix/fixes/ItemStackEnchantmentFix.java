package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class ItemStackEnchantmentFix extends DataFix {
   private static final Int2ObjectMap<String> field_208047_a = DataFixUtils.make(new Int2ObjectOpenHashMap(), var0 -> {
      var0.put(0, "minecraft:protection");
      var0.put(1, "minecraft:fire_protection");
      var0.put(2, "minecraft:feather_falling");
      var0.put(3, "minecraft:blast_protection");
      var0.put(4, "minecraft:projectile_protection");
      var0.put(5, "minecraft:respiration");
      var0.put(6, "minecraft:aqua_affinity");
      var0.put(7, "minecraft:thorns");
      var0.put(8, "minecraft:depth_strider");
      var0.put(9, "minecraft:frost_walker");
      var0.put(10, "minecraft:binding_curse");
      var0.put(16, "minecraft:sharpness");
      var0.put(17, "minecraft:smite");
      var0.put(18, "minecraft:bane_of_arthropods");
      var0.put(19, "minecraft:knockback");
      var0.put(20, "minecraft:fire_aspect");
      var0.put(21, "minecraft:looting");
      var0.put(22, "minecraft:sweeping");
      var0.put(32, "minecraft:efficiency");
      var0.put(33, "minecraft:silk_touch");
      var0.put(34, "minecraft:unbreaking");
      var0.put(35, "minecraft:fortune");
      var0.put(48, "minecraft:power");
      var0.put(49, "minecraft:punch");
      var0.put(50, "minecraft:flame");
      var0.put(51, "minecraft:infinity");
      var0.put(61, "minecraft:luck_of_the_sea");
      var0.put(62, "minecraft:lure");
      var0.put(65, "minecraft:loyalty");
      var0.put(66, "minecraft:impaling");
      var0.put(67, "minecraft:riptide");
      var0.put(68, "minecraft:channeling");
      var0.put(70, "minecraft:mending");
      var0.put(71, "minecraft:vanishing_curse");
   });

   public ItemStackEnchantmentFix(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211295_k);
      OpticFinder<?> ☃x = ☃.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemStackEnchantmentFix", ☃, var2x -> var2x.updateTyped(☃, var1x -> var1x.update(DSL.remainderFinder(), this::func_209627_a))
      );
   }

   private Dynamic<?> func_209627_a(Dynamic<?> var1) {
      Optional<Dynamic<?>> ☃ = ☃.get("ench")
         .flatMap(Dynamic::getStream)
         .map(var0 -> var0.map(var0x -> var0x.set("id", var0x.createString((String)field_208047_a.getOrDefault(var0x.getInt("id"), "null")))))
         .map(☃::createList);
      if (☃.isPresent()) {
         ☃ = ☃.remove("ench").set("Enchantments", (Dynamic<?>)☃.get());
      }

      return ☃.update(
         "StoredEnchantments",
         var0 -> DataFixUtils.orElse(
               var0.getStream()
                  .map(var0x -> var0x.map(var0xx -> var0xx.set("id", var0xx.createString((String)field_208047_a.getOrDefault(var0xx.getInt("id"), "null")))))
                  .map(var0::createList),
               var0
            )
      );
   }
}
