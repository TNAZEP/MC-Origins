package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class StatsCounterFix extends DataFix {
   private static final Set<String> SKIP = ImmutableSet.builder()
      .add("stat.craftItem.minecraft.spawn_egg")
      .add("stat.useItem.minecraft.spawn_egg")
      .add("stat.breakItem.minecraft.spawn_egg")
      .add("stat.pickup.minecraft.spawn_egg")
      .add("stat.drop.minecraft.spawn_egg")
      .build();
   private static final Map<String, String> CUSTOM_MAP = ImmutableMap.builder()
      .put("stat.leaveGame", "minecraft:leave_game")
      .put("stat.playOneMinute", "minecraft:play_one_minute")
      .put("stat.timeSinceDeath", "minecraft:time_since_death")
      .put("stat.sneakTime", "minecraft:sneak_time")
      .put("stat.walkOneCm", "minecraft:walk_one_cm")
      .put("stat.crouchOneCm", "minecraft:crouch_one_cm")
      .put("stat.sprintOneCm", "minecraft:sprint_one_cm")
      .put("stat.swimOneCm", "minecraft:swim_one_cm")
      .put("stat.fallOneCm", "minecraft:fall_one_cm")
      .put("stat.climbOneCm", "minecraft:climb_one_cm")
      .put("stat.flyOneCm", "minecraft:fly_one_cm")
      .put("stat.diveOneCm", "minecraft:dive_one_cm")
      .put("stat.minecartOneCm", "minecraft:minecart_one_cm")
      .put("stat.boatOneCm", "minecraft:boat_one_cm")
      .put("stat.pigOneCm", "minecraft:pig_one_cm")
      .put("stat.horseOneCm", "minecraft:horse_one_cm")
      .put("stat.aviateOneCm", "minecraft:aviate_one_cm")
      .put("stat.jump", "minecraft:jump")
      .put("stat.drop", "minecraft:drop")
      .put("stat.damageDealt", "minecraft:damage_dealt")
      .put("stat.damageTaken", "minecraft:damage_taken")
      .put("stat.deaths", "minecraft:deaths")
      .put("stat.mobKills", "minecraft:mob_kills")
      .put("stat.animalsBred", "minecraft:animals_bred")
      .put("stat.playerKills", "minecraft:player_kills")
      .put("stat.fishCaught", "minecraft:fish_caught")
      .put("stat.talkedToVillager", "minecraft:talked_to_villager")
      .put("stat.tradedWithVillager", "minecraft:traded_with_villager")
      .put("stat.cakeSlicesEaten", "minecraft:eat_cake_slice")
      .put("stat.cauldronFilled", "minecraft:fill_cauldron")
      .put("stat.cauldronUsed", "minecraft:use_cauldron")
      .put("stat.armorCleaned", "minecraft:clean_armor")
      .put("stat.bannerCleaned", "minecraft:clean_banner")
      .put("stat.brewingstandInteraction", "minecraft:interact_with_brewingstand")
      .put("stat.beaconInteraction", "minecraft:interact_with_beacon")
      .put("stat.dropperInspected", "minecraft:inspect_dropper")
      .put("stat.hopperInspected", "minecraft:inspect_hopper")
      .put("stat.dispenserInspected", "minecraft:inspect_dispenser")
      .put("stat.noteblockPlayed", "minecraft:play_noteblock")
      .put("stat.noteblockTuned", "minecraft:tune_noteblock")
      .put("stat.flowerPotted", "minecraft:pot_flower")
      .put("stat.trappedChestTriggered", "minecraft:trigger_trapped_chest")
      .put("stat.enderchestOpened", "minecraft:open_enderchest")
      .put("stat.itemEnchanted", "minecraft:enchant_item")
      .put("stat.recordPlayed", "minecraft:play_record")
      .put("stat.furnaceInteraction", "minecraft:interact_with_furnace")
      .put("stat.craftingTableInteraction", "minecraft:interact_with_crafting_table")
      .put("stat.chestOpened", "minecraft:open_chest")
      .put("stat.sleepInBed", "minecraft:sleep_in_bed")
      .put("stat.shulkerBoxOpened", "minecraft:open_shulker_box")
      .build();
   private static final String BLOCK_KEY = "stat.mineBlock";
   private static final String NEW_BLOCK_KEY = "minecraft:mined";
   private static final Map<String, String> ITEM_KEYS = ImmutableMap.builder()
      .put("stat.craftItem", "minecraft:crafted")
      .put("stat.useItem", "minecraft:used")
      .put("stat.breakItem", "minecraft:broken")
      .put("stat.pickup", "minecraft:picked_up")
      .put("stat.drop", "minecraft:dropped")
      .build();
   private static final Map<String, String> ENTITY_KEYS = ImmutableMap.builder()
      .put("stat.entityKilledBy", "minecraft:killed_by")
      .put("stat.killEntity", "minecraft:killed")
      .build();
   private static final Map<String, String> ENTITIES = ImmutableMap.builder()
      .put("Bat", "minecraft:bat")
      .put("Blaze", "minecraft:blaze")
      .put("CaveSpider", "minecraft:cave_spider")
      .put("Chicken", "minecraft:chicken")
      .put("Cow", "minecraft:cow")
      .put("Creeper", "minecraft:creeper")
      .put("Donkey", "minecraft:donkey")
      .put("ElderGuardian", "minecraft:elder_guardian")
      .put("Enderman", "minecraft:enderman")
      .put("Endermite", "minecraft:endermite")
      .put("EvocationIllager", "minecraft:evocation_illager")
      .put("Ghast", "minecraft:ghast")
      .put("Guardian", "minecraft:guardian")
      .put("Horse", "minecraft:horse")
      .put("Husk", "minecraft:husk")
      .put("Llama", "minecraft:llama")
      .put("LavaSlime", "minecraft:magma_cube")
      .put("MushroomCow", "minecraft:mooshroom")
      .put("Mule", "minecraft:mule")
      .put("Ozelot", "minecraft:ocelot")
      .put("Parrot", "minecraft:parrot")
      .put("Pig", "minecraft:pig")
      .put("PolarBear", "minecraft:polar_bear")
      .put("Rabbit", "minecraft:rabbit")
      .put("Sheep", "minecraft:sheep")
      .put("Shulker", "minecraft:shulker")
      .put("Silverfish", "minecraft:silverfish")
      .put("SkeletonHorse", "minecraft:skeleton_horse")
      .put("Skeleton", "minecraft:skeleton")
      .put("Slime", "minecraft:slime")
      .put("Spider", "minecraft:spider")
      .put("Squid", "minecraft:squid")
      .put("Stray", "minecraft:stray")
      .put("Vex", "minecraft:vex")
      .put("Villager", "minecraft:villager")
      .put("VindicationIllager", "minecraft:vindication_illager")
      .put("Witch", "minecraft:witch")
      .put("WitherSkeleton", "minecraft:wither_skeleton")
      .put("Wolf", "minecraft:wolf")
      .put("ZombieHorse", "minecraft:zombie_horse")
      .put("PigZombie", "minecraft:zombie_pigman")
      .put("ZombieVillager", "minecraft:zombie_villager")
      .put("Zombie", "minecraft:zombie")
      .build();
   private static final String NEW_CUSTOM_KEY = "minecraft:custom";

   public StatsCounterFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getOutputSchema().getType(References.STATS);
      return this.fixTypeEverywhereTyped(
         "StatsCounterFix",
         this.getInputSchema().getType(References.STATS),
         â˜ƒ,
         var2 -> {
            Dynamic<?> â˜ƒ = var2.get(DSL.remainderFinder());
            Map<Dynamic<?>, Dynamic<?>> â˜ƒx = Maps.<Dynamic<?>, Dynamic<?>>newHashMap();
            Optional<? extends Map<? extends Dynamic<?>, ? extends Dynamic<?>>> â˜ƒxx = â˜ƒ.getMapValues().result();
            if (â˜ƒxx.isPresent()) {
               for(Entry<? extends Dynamic<?>, ? extends Dynamic<?>> â˜ƒxxx : ((Map)â˜ƒxx.get()).entrySet()) {
                  if (((Dynamic)â˜ƒxxx.getValue()).asNumber().result().isPresent()) {
                     String â˜ƒxxxx = ((Dynamic)â˜ƒxxx.getKey()).asString("");
                     if (!SKIP.contains(â˜ƒxxxx)) {
                        String â˜ƒxxxxx;
                        String â˜ƒxxxxxx;
                        if (CUSTOM_MAP.containsKey(â˜ƒxxxx)) {
                           â˜ƒxxxxx = "minecraft:custom";
                           â˜ƒxxxxxx = (String)CUSTOM_MAP.get(â˜ƒxxxx);
                        } else {
                           int â˜ƒxxxxx = StringUtils.ordinalIndexOf(â˜ƒxxxx, ".", 2);
                           if (â˜ƒxxxxx < 0) {
                              continue;
                           }
   
                           String â˜ƒxxxxx = â˜ƒxxxx.substring(0, â˜ƒxxxxx);
                           if ("stat.mineBlock".equals(â˜ƒxxxxx)) {
                              â˜ƒxxxxx = "minecraft:mined";
                              â˜ƒxxxxxx = this.upgradeBlock(â˜ƒxxxx.substring(â˜ƒxxxxx + 1).replace('.', ':'));
                           } else if (ITEM_KEYS.containsKey(â˜ƒxxxxx)) {
                              â˜ƒxxxxx = (String)ITEM_KEYS.get(â˜ƒxxxxx);
                              String â˜ƒxxxxx = â˜ƒxxxx.substring(â˜ƒxxxxx + 1).replace('.', ':');
                              String â˜ƒxxxxxx = this.upgradeItem(â˜ƒxxxxx);
                              â˜ƒxxxxxx = â˜ƒxxxxxx == null ? â˜ƒxxxxx : â˜ƒxxxxxx;
                           } else {
                              if (!ENTITY_KEYS.containsKey(â˜ƒxxxxx)) {
                                 continue;
                              }
   
                              â˜ƒxxxxx = (String)ENTITY_KEYS.get(â˜ƒxxxxx);
                              String â˜ƒxxxxx = â˜ƒxxxx.substring(â˜ƒxxxxx + 1).replace('.', ':');
                              â˜ƒxxxxxx = (String)ENTITIES.getOrDefault(â˜ƒxxxxx, â˜ƒxxxxx);
                           }
                        }
   
                        Dynamic<?> â˜ƒxxxxx = â˜ƒ.createString(â˜ƒxxxxx);
                        Dynamic<?> â˜ƒxxxxxx = (Dynamic)â˜ƒx.computeIfAbsent(â˜ƒxxxxx, var1x -> â˜ƒ.emptyMap());
                        â˜ƒx.put(â˜ƒxxxxx, â˜ƒxxxxxx.set(â˜ƒxxxxxx, (Dynamic<?>)â˜ƒxxx.getValue()));
                     }
                  }
               }
            }
   
            return (Typed)((Pair)â˜ƒ.readTyped(â˜ƒ.emptyMap().set("stats", â˜ƒ.createMap(â˜ƒx)))
                  .result()
                  .orElseThrow(() -> new IllegalStateException("Could not parse new stats object.")))
               .getFirst();
         }
      );
   }

   @Nullable
   protected String upgradeItem(String var1) {
      return ItemStackTheFlatteningFix.updateItem(â˜ƒ, 0);
   }

   protected String upgradeBlock(String var1) {
      return BlockStateData.upgradeBlock(â˜ƒ);
   }
}
