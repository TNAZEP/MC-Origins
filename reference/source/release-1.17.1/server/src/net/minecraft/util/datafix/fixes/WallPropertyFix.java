package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Set;

public class WallPropertyFix extends DataFix {
   private static final Set<String> WALL_BLOCKS = ImmutableSet.of(
      "minecraft:andesite_wall",
      "minecraft:brick_wall",
      "minecraft:cobblestone_wall",
      "minecraft:diorite_wall",
      "minecraft:end_stone_brick_wall",
      "minecraft:granite_wall",
      "minecraft:mossy_cobblestone_wall",
      "minecraft:mossy_stone_brick_wall",
      "minecraft:nether_brick_wall",
      "minecraft:prismarine_wall",
      "minecraft:red_nether_brick_wall",
      "minecraft:red_sandstone_wall",
      "minecraft:sandstone_wall",
      "minecraft:stone_brick_wall"
   );

   public WallPropertyFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "WallPropertyFix",
         this.getInputSchema().getType(References.BLOCK_STATE),
         var0 -> var0.update(DSL.remainderFinder(), WallPropertyFix::upgradeBlockStateTag)
      );
   }

   private static String mapProperty(String var0) {
      return "true".equals(â˜ƒ) ? "low" : "none";
   }

   private static <T> Dynamic<T> fixWallProperty(Dynamic<T> var0, String var1) {
      return â˜ƒ.update(â˜ƒ, var0x -> DataFixUtils.orElse(var0x.asString().result().map(WallPropertyFix::mapProperty).map(var0x::createString), var0x));
   }

   private static <T> Dynamic<T> upgradeBlockStateTag(Dynamic<T> var0) {
      boolean â˜ƒ = â˜ƒ.get("Name").asString().result().filter(WALL_BLOCKS::contains).isPresent();
      return !â˜ƒ ? â˜ƒ : â˜ƒ.update("Properties", var0x -> {
         Dynamic<?> â˜ƒ = fixWallProperty(var0x, "east");
         â˜ƒ = fixWallProperty(â˜ƒ, "west");
         â˜ƒ = fixWallProperty(â˜ƒ, "north");
         return fixWallProperty(â˜ƒ, "south");
      });
   }
}
