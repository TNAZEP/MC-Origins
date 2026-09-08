package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobSpawnSettings {
   public static final Logger LOGGER = LogManager.getLogger();
   private static final float DEFAULT_CREATURE_SPAWN_PROBABILITY = 0.1F;
   public static final WeightedRandomList<MobSpawnSettings.SpawnerData> EMPTY_MOB_LIST = WeightedRandomList.create();
   public static final MobSpawnSettings EMPTY = new MobSpawnSettings(
      0.1F,
      (Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>>)Stream.of(MobCategory.values())
         .collect(ImmutableMap.toImmutableMap(var0 -> var0, var0 -> EMPTY_MOB_LIST)),
      ImmutableMap.of(),
      false
   );
   public static final MapCodec<MobSpawnSettings> CODEC = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.floatRange(0.0F, 0.9999999F).optionalFieldOf("creature_spawn_probability", 0.1F).forGetter(var0x -> var0x.creatureGenerationProbability),
               Codec.simpleMap(
                     MobCategory.CODEC,
                     WeightedRandomList.codec(MobSpawnSettings.SpawnerData.CODEC).promotePartial(Util.prefix("Spawn data: ", LOGGER::error)),
                     StringRepresentable.keys(MobCategory.values())
                  )
                  .fieldOf("spawners")
                  .forGetter(var0x -> var0x.spawners),
               Codec.simpleMap(Registry.ENTITY_TYPE, MobSpawnSettings.MobSpawnCost.CODEC, Registry.ENTITY_TYPE)
                  .fieldOf("spawn_costs")
                  .forGetter(var0x -> var0x.mobSpawnCosts),
               Codec.BOOL.fieldOf("player_spawn_friendly").orElse(false).forGetter(MobSpawnSettings::playerSpawnFriendly)
            )
            .apply(var0, MobSpawnSettings::new)
   );
   private final float creatureGenerationProbability;
   private final Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> spawners;
   private final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> mobSpawnCosts;
   private final boolean playerSpawnFriendly;

   MobSpawnSettings(
      float var1, Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> var2, Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> var3, boolean var4
   ) {
      this.creatureGenerationProbability = â˜ƒ;
      this.spawners = ImmutableMap.copyOf(â˜ƒ);
      this.mobSpawnCosts = ImmutableMap.copyOf(â˜ƒ);
      this.playerSpawnFriendly = â˜ƒ;
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobs(MobCategory var1) {
      return (WeightedRandomList<MobSpawnSettings.SpawnerData>)this.spawners.getOrDefault(â˜ƒ, EMPTY_MOB_LIST);
   }

   @Nullable
   public MobSpawnSettings.MobSpawnCost getMobSpawnCost(EntityType<?> var1) {
      return (MobSpawnSettings.MobSpawnCost)this.mobSpawnCosts.get(â˜ƒ);
   }

   public float getCreatureProbability() {
      return this.creatureGenerationProbability;
   }

   public boolean playerSpawnFriendly() {
      return this.playerSpawnFriendly;
   }

   public static class Builder {
      private final Map<MobCategory, List<MobSpawnSettings.SpawnerData>> spawners = (Map<MobCategory, List<MobSpawnSettings.SpawnerData>>)Stream.of(
            MobCategory.values()
         )
         .collect(ImmutableMap.toImmutableMap(var0 -> var0, var0 -> Lists.newArrayList()));
      private final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> mobSpawnCosts = Maps.<EntityType<?>, MobSpawnSettings.MobSpawnCost>newLinkedHashMap();
      private float creatureGenerationProbability = 0.1F;
      private boolean playerCanSpawn;

      public MobSpawnSettings.Builder addSpawn(MobCategory var1, MobSpawnSettings.SpawnerData var2) {
         ((List)this.spawners.get(â˜ƒ)).add(â˜ƒ);
         return this;
      }

      public MobSpawnSettings.Builder addMobCharge(EntityType<?> var1, double var2, double var4) {
         this.mobSpawnCosts.put(â˜ƒ, new MobSpawnSettings.MobSpawnCost(â˜ƒ, â˜ƒ));
         return this;
      }

      public MobSpawnSettings.Builder creatureGenerationProbability(float var1) {
         this.creatureGenerationProbability = â˜ƒ;
         return this;
      }

      public MobSpawnSettings.Builder setPlayerCanSpawn() {
         this.playerCanSpawn = true;
         return this;
      }

      public MobSpawnSettings build() {
         return new MobSpawnSettings(
            this.creatureGenerationProbability,
            (Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>>)this.spawners
               .entrySet()
               .stream()
               .collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> WeightedRandomList.create((List)var0.getValue()))),
            ImmutableMap.copyOf(this.mobSpawnCosts),
            this.playerCanSpawn
         );
      }
   }

   public static class MobSpawnCost {
      public static final Codec<MobSpawnSettings.MobSpawnCost> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.DOUBLE.fieldOf("energy_budget").forGetter(var0x -> var0x.energyBudget), Codec.DOUBLE.fieldOf("charge").forGetter(var0x -> var0x.charge)
               )
               .apply(var0, MobSpawnSettings.MobSpawnCost::new)
      );
      private final double energyBudget;
      private final double charge;

      MobSpawnCost(double var1, double var3) {
         this.energyBudget = â˜ƒ;
         this.charge = â˜ƒ;
      }

      public double getEnergyBudget() {
         return this.energyBudget;
      }

      public double getCharge() {
         return this.charge;
      }
   }

   public static class SpawnerData extends WeightedEntry.IntrusiveBase {
      public static final Codec<MobSpawnSettings.SpawnerData> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Registry.ENTITY_TYPE.fieldOf("type").forGetter(var0x -> var0x.type),
                  Weight.CODEC.fieldOf("weight").forGetter(WeightedEntry.IntrusiveBase::getWeight),
                  Codec.INT.fieldOf("minCount").forGetter(var0x -> var0x.minCount),
                  Codec.INT.fieldOf("maxCount").forGetter(var0x -> var0x.maxCount)
               )
               .apply(var0, MobSpawnSettings.SpawnerData::new)
      );
      public final EntityType<?> type;
      public final int minCount;
      public final int maxCount;

      public SpawnerData(EntityType<?> var1, int var2, int var3, int var4) {
         this(â˜ƒ, Weight.of(â˜ƒ), â˜ƒ, â˜ƒ);
      }

      public SpawnerData(EntityType<?> var1, Weight var2, int var3, int var4) {
         super(â˜ƒ);
         this.type = â˜ƒ.getCategory() == MobCategory.MISC ? EntityType.PIG : â˜ƒ;
         this.minCount = â˜ƒ;
         this.maxCount = â˜ƒ;
      }

      public String toString() {
         return EntityType.getKey(this.type) + "*(" + this.minCount + "-" + this.maxCount + "):" + this.getWeight();
      }
   }
}
