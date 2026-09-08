package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.stream.Stream;

public class AttributesRename extends DataFix {
   private static final Map<String, String> RENAMES = ImmutableMap.builder()
      .put("generic.maxHealth", "generic.max_health")
      .put("Max Health", "generic.max_health")
      .put("zombie.spawnReinforcements", "zombie.spawn_reinforcements")
      .put("Spawn Reinforcements Chance", "zombie.spawn_reinforcements")
      .put("horse.jumpStrength", "horse.jump_strength")
      .put("Jump Strength", "horse.jump_strength")
      .put("generic.followRange", "generic.follow_range")
      .put("Follow Range", "generic.follow_range")
      .put("generic.knockbackResistance", "generic.knockback_resistance")
      .put("Knockback Resistance", "generic.knockback_resistance")
      .put("generic.movementSpeed", "generic.movement_speed")
      .put("Movement Speed", "generic.movement_speed")
      .put("generic.flyingSpeed", "generic.flying_speed")
      .put("Flying Speed", "generic.flying_speed")
      .put("generic.attackDamage", "generic.attack_damage")
      .put("generic.attackKnockback", "generic.attack_knockback")
      .put("generic.attackSpeed", "generic.attack_speed")
      .put("generic.armorToughness", "generic.armor_toughness")
      .build();

   public AttributesRename(Schema var1) {
      super(â˜ƒ, false);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.ITEM_STACK);
      OpticFinder<?> â˜ƒx = â˜ƒ.findField("tag");
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped("Rename ItemStack Attributes", â˜ƒ, var1x -> var1x.updateTyped(â˜ƒ, AttributesRename::fixItemStackTag)),
         this.fixTypeEverywhereTyped("Rename Entity Attributes", this.getInputSchema().getType(References.ENTITY), AttributesRename::fixEntity),
         this.fixTypeEverywhereTyped("Rename Player Attributes", this.getInputSchema().getType(References.PLAYER), AttributesRename::fixEntity)
      );
   }

   private static Dynamic<?> fixName(Dynamic<?> var0) {
      return DataFixUtils.orElse(â˜ƒ.asString().result().map(var0x -> (String)RENAMES.getOrDefault(var0x, var0x)).map(â˜ƒ::createString), â˜ƒ);
   }

   private static Typed<?> fixItemStackTag(Typed<?> var0) {
      return â˜ƒ.update(
         DSL.remainderFinder(),
         var0x -> var0x.update(
               "AttributeModifiers",
               var0xx -> DataFixUtils.orElse(
                     var0xx.asStreamOpt()
                        .result()
                        .map(var0xxx -> var0xxx.map(var0xxxx -> var0xxxx.update("AttributeName", AttributesRename::fixName)))
                        .map(var0xx::createList),
                     var0xx
                  )
            )
      );
   }

   private static Typed<?> fixEntity(Typed<?> var0) {
      return â˜ƒ.update(
         DSL.remainderFinder(),
         var0x -> var0x.update(
               "Attributes",
               var0xx -> DataFixUtils.orElse(
                     var0xx.asStreamOpt()
                        .result()
                        .map(var0xxx -> var0xxx.map(var0xxxx -> var0xxxx.update("Name", AttributesRename::fixName)))
                        .map(var0xx::createList),
                     var0xx
                  )
            )
      );
   }
}
