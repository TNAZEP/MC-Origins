package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;

public class EntityUUIDFix extends AbstractUUIDFix {
   private static final Set<String> ABSTRACT_HORSES = Sets.newHashSet();
   private static final Set<String> TAMEABLE_ANIMALS = Sets.newHashSet();
   private static final Set<String> ANIMALS = Sets.newHashSet();
   private static final Set<String> MOBS = Sets.newHashSet();
   private static final Set<String> LIVING_ENTITIES = Sets.newHashSet();
   private static final Set<String> PROJECTILES = Sets.newHashSet();

   public EntityUUIDFix(Schema var1) {
      super(â˜ƒ, References.ENTITY);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("EntityUUIDFixes", this.getInputSchema().getType(this.typeReference), var1 -> {
         var1 = var1.update(DSL.remainderFinder(), EntityUUIDFix::updateEntityUUID);

         for(String â˜ƒ : ABSTRACT_HORSES) {
            var1 = this.updateNamedChoice(var1, â˜ƒ, EntityUUIDFix::updateAnimalOwner);
         }

         for(String â˜ƒ : TAMEABLE_ANIMALS) {
            var1 = this.updateNamedChoice(var1, â˜ƒ, EntityUUIDFix::updateAnimalOwner);
         }

         for(String â˜ƒ : ANIMALS) {
            var1 = this.updateNamedChoice(var1, â˜ƒ, EntityUUIDFix::updateAnimal);
         }

         for(String â˜ƒ : MOBS) {
            var1 = this.updateNamedChoice(var1, â˜ƒ, EntityUUIDFix::updateMob);
         }

         for(String â˜ƒ : LIVING_ENTITIES) {
            var1 = this.updateNamedChoice(var1, â˜ƒ, EntityUUIDFix::updateLivingEntity);
         }

         for(String â˜ƒ : PROJECTILES) {
            var1 = this.updateNamedChoice(var1, â˜ƒ, EntityUUIDFix::updateProjectile);
         }

         var1 = this.updateNamedChoice(var1, "minecraft:bee", EntityUUIDFix::updateHurtBy);
         var1 = this.updateNamedChoice(var1, "minecraft:zombified_piglin", EntityUUIDFix::updateHurtBy);
         var1 = this.updateNamedChoice(var1, "minecraft:fox", EntityUUIDFix::updateFox);
         var1 = this.updateNamedChoice(var1, "minecraft:item", EntityUUIDFix::updateItem);
         var1 = this.updateNamedChoice(var1, "minecraft:shulker_bullet", EntityUUIDFix::updateShulkerBullet);
         var1 = this.updateNamedChoice(var1, "minecraft:area_effect_cloud", EntityUUIDFix::updateAreaEffectCloud);
         var1 = this.updateNamedChoice(var1, "minecraft:zombie_villager", EntityUUIDFix::updateZombieVillager);
         var1 = this.updateNamedChoice(var1, "minecraft:evoker_fangs", EntityUUIDFix::updateEvokerFangs);
         return this.updateNamedChoice(var1, "minecraft:piglin", EntityUUIDFix::updatePiglin);
      });
   }

   private static Dynamic<?> updatePiglin(Dynamic<?> var0) {
      return â˜ƒ.update(
         "Brain",
         var0x -> var0x.update(
               "memories", var0xx -> var0xx.update("minecraft:angry_at", var0xxx -> (Dynamic)replaceUUIDString(var0xxx, "value", "value").orElseGet(() -> {
                        LOGGER.warn("angry_at has no value.");
                        return var0xxx;
                     }))
            )
      );
   }

   private static Dynamic<?> updateEvokerFangs(Dynamic<?> var0) {
      return (Dynamic<?>)replaceUUIDLeastMost(â˜ƒ, "OwnerUUID", "Owner").orElse(â˜ƒ);
   }

   private static Dynamic<?> updateZombieVillager(Dynamic<?> var0) {
      return (Dynamic<?>)replaceUUIDLeastMost(â˜ƒ, "ConversionPlayer", "ConversionPlayer").orElse(â˜ƒ);
   }

   private static Dynamic<?> updateAreaEffectCloud(Dynamic<?> var0) {
      return (Dynamic<?>)replaceUUIDLeastMost(â˜ƒ, "OwnerUUID", "Owner").orElse(â˜ƒ);
   }

   private static Dynamic<?> updateShulkerBullet(Dynamic<?> var0) {
      â˜ƒ = (Dynamic)replaceUUIDMLTag(â˜ƒ, "Owner", "Owner").orElse(â˜ƒ);
      return (Dynamic<?>)replaceUUIDMLTag(â˜ƒ, "Target", "Target").orElse(â˜ƒ);
   }

   private static Dynamic<?> updateItem(Dynamic<?> var0) {
      â˜ƒ = (Dynamic)replaceUUIDMLTag(â˜ƒ, "Owner", "Owner").orElse(â˜ƒ);
      return (Dynamic<?>)replaceUUIDMLTag(â˜ƒ, "Thrower", "Thrower").orElse(â˜ƒ);
   }

   private static Dynamic<?> updateFox(Dynamic<?> var0) {
      Optional<Dynamic<?>> â˜ƒ = â˜ƒ.get("TrustedUUIDs")
         .result()
         .map(var1x -> â˜ƒ.createList(var1x.asStream().map(var0x -> (Dynamic)createUUIDFromML(var0x).orElseGet(() -> {
                  LOGGER.warn("Trusted contained invalid data.");
                  return var0x;
               }))));
      return DataFixUtils.orElse(â˜ƒ.map(var1x -> â˜ƒ.remove("TrustedUUIDs").set("Trusted", var1x)), â˜ƒ);
   }

   private static Dynamic<?> updateHurtBy(Dynamic<?> var0) {
      return (Dynamic<?>)replaceUUIDString(â˜ƒ, "HurtBy", "HurtBy").orElse(â˜ƒ);
   }

   private static Dynamic<?> updateAnimalOwner(Dynamic<?> var0) {
      Dynamic<?> â˜ƒ = updateAnimal(â˜ƒ);
      return (Dynamic<?>)replaceUUIDString(â˜ƒ, "OwnerUUID", "Owner").orElse(â˜ƒ);
   }

   private static Dynamic<?> updateAnimal(Dynamic<?> var0) {
      Dynamic<?> â˜ƒ = updateMob(â˜ƒ);
      return (Dynamic<?>)replaceUUIDLeastMost(â˜ƒ, "LoveCause", "LoveCause").orElse(â˜ƒ);
   }

   private static Dynamic<?> updateMob(Dynamic<?> var0) {
      return updateLivingEntity(â˜ƒ).update("Leash", var0x -> (Dynamic)replaceUUIDLeastMost(var0x, "UUID", "UUID").orElse(var0x));
   }

   public static Dynamic<?> updateLivingEntity(Dynamic<?> var0) {
      return â˜ƒ.update(
         "Attributes",
         var1 -> â˜ƒ.createList(
               var1.asStream()
                  .map(
                     var0x -> var0x.update(
                           "Modifiers",
                           var1x -> var0x.createList(var1x.asStream().map(var0xx -> (Dynamic)replaceUUIDLeastMost(var0xx, "UUID", "UUID").orElse(var0xx)))
                        )
                  )
            )
      );
   }

   private static Dynamic<?> updateProjectile(Dynamic<?> var0) {
      return DataFixUtils.orElse(â˜ƒ.get("OwnerUUID").result().map(var1 -> â˜ƒ.remove("OwnerUUID").set("Owner", var1)), â˜ƒ);
   }

   public static Dynamic<?> updateEntityUUID(Dynamic<?> var0) {
      return (Dynamic<?>)replaceUUIDLeastMost(â˜ƒ, "UUID", "UUID").orElse(â˜ƒ);
   }

   static {
      ABSTRACT_HORSES.add("minecraft:donkey");
      ABSTRACT_HORSES.add("minecraft:horse");
      ABSTRACT_HORSES.add("minecraft:llama");
      ABSTRACT_HORSES.add("minecraft:mule");
      ABSTRACT_HORSES.add("minecraft:skeleton_horse");
      ABSTRACT_HORSES.add("minecraft:trader_llama");
      ABSTRACT_HORSES.add("minecraft:zombie_horse");
      TAMEABLE_ANIMALS.add("minecraft:cat");
      TAMEABLE_ANIMALS.add("minecraft:parrot");
      TAMEABLE_ANIMALS.add("minecraft:wolf");
      ANIMALS.add("minecraft:bee");
      ANIMALS.add("minecraft:chicken");
      ANIMALS.add("minecraft:cow");
      ANIMALS.add("minecraft:fox");
      ANIMALS.add("minecraft:mooshroom");
      ANIMALS.add("minecraft:ocelot");
      ANIMALS.add("minecraft:panda");
      ANIMALS.add("minecraft:pig");
      ANIMALS.add("minecraft:polar_bear");
      ANIMALS.add("minecraft:rabbit");
      ANIMALS.add("minecraft:sheep");
      ANIMALS.add("minecraft:turtle");
      ANIMALS.add("minecraft:hoglin");
      MOBS.add("minecraft:bat");
      MOBS.add("minecraft:blaze");
      MOBS.add("minecraft:cave_spider");
      MOBS.add("minecraft:cod");
      MOBS.add("minecraft:creeper");
      MOBS.add("minecraft:dolphin");
      MOBS.add("minecraft:drowned");
      MOBS.add("minecraft:elder_guardian");
      MOBS.add("minecraft:ender_dragon");
      MOBS.add("minecraft:enderman");
      MOBS.add("minecraft:endermite");
      MOBS.add("minecraft:evoker");
      MOBS.add("minecraft:ghast");
      MOBS.add("minecraft:giant");
      MOBS.add("minecraft:guardian");
      MOBS.add("minecraft:husk");
      MOBS.add("minecraft:illusioner");
      MOBS.add("minecraft:magma_cube");
      MOBS.add("minecraft:pufferfish");
      MOBS.add("minecraft:zombified_piglin");
      MOBS.add("minecraft:salmon");
      MOBS.add("minecraft:shulker");
      MOBS.add("minecraft:silverfish");
      MOBS.add("minecraft:skeleton");
      MOBS.add("minecraft:slime");
      MOBS.add("minecraft:snow_golem");
      MOBS.add("minecraft:spider");
      MOBS.add("minecraft:squid");
      MOBS.add("minecraft:stray");
      MOBS.add("minecraft:tropical_fish");
      MOBS.add("minecraft:vex");
      MOBS.add("minecraft:villager");
      MOBS.add("minecraft:iron_golem");
      MOBS.add("minecraft:vindicator");
      MOBS.add("minecraft:pillager");
      MOBS.add("minecraft:wandering_trader");
      MOBS.add("minecraft:witch");
      MOBS.add("minecraft:wither");
      MOBS.add("minecraft:wither_skeleton");
      MOBS.add("minecraft:zombie");
      MOBS.add("minecraft:zombie_villager");
      MOBS.add("minecraft:phantom");
      MOBS.add("minecraft:ravager");
      MOBS.add("minecraft:piglin");
      LIVING_ENTITIES.add("minecraft:armor_stand");
      PROJECTILES.add("minecraft:arrow");
      PROJECTILES.add("minecraft:dragon_fireball");
      PROJECTILES.add("minecraft:firework_rocket");
      PROJECTILES.add("minecraft:fireball");
      PROJECTILES.add("minecraft:llama_spit");
      PROJECTILES.add("minecraft:small_fireball");
      PROJECTILES.add("minecraft:snowball");
      PROJECTILES.add("minecraft:spectral_arrow");
      PROJECTILES.add("minecraft:egg");
      PROJECTILES.add("minecraft:ender_pearl");
      PROJECTILES.add("minecraft:experience_bottle");
      PROJECTILES.add("minecraft:potion");
      PROJECTILES.add("minecraft:trident");
      PROJECTILES.add("minecraft:wither_skull");
   }
}
