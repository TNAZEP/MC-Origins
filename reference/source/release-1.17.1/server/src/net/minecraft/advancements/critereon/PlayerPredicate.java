package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PlayerPredicate {
   public static final PlayerPredicate ANY = new PlayerPredicate.Builder().build();
   public static final int LOOKING_AT_RANGE = 100;
   private final MinMaxBounds.Ints level;
   @Nullable
   private final GameType gameType;
   private final Map<Stat<?>, MinMaxBounds.Ints> stats;
   private final Object2BooleanMap<ResourceLocation> recipes;
   private final Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> advancements;
   private final EntityPredicate lookingAt;

   private static PlayerPredicate.AdvancementPredicate advancementPredicateFromJson(JsonElement var0) {
      if (â˜ƒ.isJsonPrimitive()) {
         boolean â˜ƒ = â˜ƒ.getAsBoolean();
         return new PlayerPredicate.AdvancementDonePredicate(â˜ƒ);
      } else {
         Object2BooleanMap<String> â˜ƒ = new Object2BooleanOpenHashMap();
         JsonObject â˜ƒx = GsonHelper.convertToJsonObject(â˜ƒ, "criterion data");
         â˜ƒx.entrySet().forEach(var1x -> {
            boolean â˜ƒ = GsonHelper.convertToBoolean((JsonElement)var1x.getValue(), "criterion test");
            â˜ƒ.put((String)var1x.getKey(), â˜ƒ);
         });
         return new PlayerPredicate.AdvancementCriterionsPredicate(â˜ƒ);
      }
   }

   PlayerPredicate(
      MinMaxBounds.Ints var1,
      @Nullable GameType var2,
      Map<Stat<?>, MinMaxBounds.Ints> var3,
      Object2BooleanMap<ResourceLocation> var4,
      Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> var5,
      EntityPredicate var6
   ) {
      this.level = â˜ƒ;
      this.gameType = â˜ƒ;
      this.stats = â˜ƒ;
      this.recipes = â˜ƒ;
      this.advancements = â˜ƒ;
      this.lookingAt = â˜ƒ;
   }

   public boolean matches(Entity var1) {
      if (this == ANY) {
         return true;
      } else if (!(â˜ƒ instanceof ServerPlayer)) {
         return false;
      } else {
         ServerPlayer â˜ƒ = (ServerPlayer)â˜ƒ;
         if (!this.level.matches(â˜ƒ.experienceLevel)) {
            return false;
         } else if (this.gameType != null && this.gameType != â˜ƒ.gameMode.getGameModeForPlayer()) {
            return false;
         } else {
            StatsCounter â˜ƒ = â˜ƒ.getStats();

            for(Entry<Stat<?>, MinMaxBounds.Ints> â˜ƒx : this.stats.entrySet()) {
               int â˜ƒxx = â˜ƒ.getValue((Stat<?>)â˜ƒx.getKey());
               if (!((MinMaxBounds.Ints)â˜ƒx.getValue()).matches(â˜ƒxx)) {
                  return false;
               }
            }

            RecipeBook â˜ƒx = â˜ƒ.getRecipeBook();

            for(it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<ResourceLocation> â˜ƒxx : this.recipes.object2BooleanEntrySet()) {
               if (â˜ƒx.contains((ResourceLocation)â˜ƒxx.getKey()) != â˜ƒxx.getBooleanValue()) {
                  return false;
               }
            }

            if (!this.advancements.isEmpty()) {
               PlayerAdvancements â˜ƒxx = â˜ƒ.getAdvancements();
               ServerAdvancementManager â˜ƒxxx = â˜ƒ.getServer().getAdvancements();

               for(Entry<ResourceLocation, PlayerPredicate.AdvancementPredicate> â˜ƒxxxx : this.advancements.entrySet()) {
                  Advancement â˜ƒxxxxx = â˜ƒxxx.getAdvancement((ResourceLocation)â˜ƒxxxx.getKey());
                  if (â˜ƒxxxxx == null || !((PlayerPredicate.AdvancementPredicate)â˜ƒxxxx.getValue()).test(â˜ƒxx.getOrStartProgress(â˜ƒxxxxx))) {
                     return false;
                  }
               }
            }

            if (this.lookingAt != EntityPredicate.ANY) {
               Vec3 â˜ƒxx = â˜ƒ.getEyePosition();
               Vec3 â˜ƒxxx = â˜ƒ.getViewVector(1.0F);
               Vec3 â˜ƒxxxx = â˜ƒxx.add(â˜ƒxxx.x * 100.0, â˜ƒxxx.y * 100.0, â˜ƒxxx.z * 100.0);
               EntityHitResult â˜ƒxxxxx = ProjectileUtil.getEntityHitResult(
                  â˜ƒ.level, â˜ƒ, â˜ƒxx, â˜ƒxxxx, new AABB(â˜ƒxx, â˜ƒxxxx).inflate(1.0), var0 -> !var0.isSpectator(), 0.0F
               );
               if (â˜ƒxxxxx == null || â˜ƒxxxxx.getType() != HitResult.Type.ENTITY) {
                  return false;
               }

               Entity â˜ƒxx = â˜ƒxxxxx.getEntity();
               if (!this.lookingAt.matches(â˜ƒ, â˜ƒxx) || !â˜ƒ.hasLineOfSight(â˜ƒxx)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static PlayerPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "player");
         MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("level"));
         String â˜ƒxx = GsonHelper.getAsString(â˜ƒ, "gamemode", "");
         GameType â˜ƒxxx = GameType.byName(â˜ƒxx, null);
         Map<Stat<?>, MinMaxBounds.Ints> â˜ƒxxxx = Maps.<Stat<?>, MinMaxBounds.Ints>newHashMap();
         JsonArray â˜ƒxxxxx = GsonHelper.getAsJsonArray(â˜ƒ, "stats", null);
         if (â˜ƒxxxxx != null) {
            for(JsonElement â˜ƒxxxxxx : â˜ƒxxxxx) {
               JsonObject â˜ƒxxxxxxx = GsonHelper.convertToJsonObject(â˜ƒxxxxxx, "stats entry");
               ResourceLocation â˜ƒxxxxxxxx = new ResourceLocation(GsonHelper.getAsString(â˜ƒxxxxxxx, "type"));
               StatType<?> â˜ƒxxxxxxxxx = Registry.STAT_TYPE.get(â˜ƒxxxxxxxx);
               if (â˜ƒxxxxxxxxx == null) {
                  throw new JsonParseException("Invalid stat type: " + â˜ƒxxxxxxxx);
               }

               ResourceLocation â˜ƒxxxxxxx = new ResourceLocation(GsonHelper.getAsString(â˜ƒxxxxxxx, "stat"));
               Stat<?> â˜ƒxxxxxxxx = getStat(â˜ƒxxxxxxxxx, â˜ƒxxxxxxx);
               MinMaxBounds.Ints â˜ƒxxxxxxxxx = MinMaxBounds.Ints.fromJson(â˜ƒxxxxxxx.get("value"));
               â˜ƒxxxx.put(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            }
         }

         Object2BooleanMap<ResourceLocation> â˜ƒ = new Object2BooleanOpenHashMap<>();
         JsonObject â˜ƒx = GsonHelper.getAsJsonObject(â˜ƒ, "recipes", new JsonObject());

         for(Entry<String, JsonElement> â˜ƒxx : â˜ƒx.entrySet()) {
            ResourceLocation â˜ƒxxx = new ResourceLocation((String)â˜ƒxx.getKey());
            boolean â˜ƒxxxx = GsonHelper.convertToBoolean((JsonElement)â˜ƒxx.getValue(), "recipe present");
            â˜ƒ.put(â˜ƒxxx, â˜ƒxxxx);
         }

         Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> â˜ƒxx = Maps.<ResourceLocation, PlayerPredicate.AdvancementPredicate>newHashMap();
         JsonObject â˜ƒxxx = GsonHelper.getAsJsonObject(â˜ƒ, "advancements", new JsonObject());

         for(Entry<String, JsonElement> â˜ƒxxxx : â˜ƒxxx.entrySet()) {
            ResourceLocation â˜ƒxxxxx = new ResourceLocation((String)â˜ƒxxxx.getKey());
            PlayerPredicate.AdvancementPredicate â˜ƒxxxxxx = advancementPredicateFromJson((JsonElement)â˜ƒxxxx.getValue());
            â˜ƒxx.put(â˜ƒxxxxx, â˜ƒxxxxxx);
         }

         EntityPredicate â˜ƒxxxx = EntityPredicate.fromJson(â˜ƒ.get("looking_at"));
         return new PlayerPredicate(â˜ƒx, â˜ƒxxx, â˜ƒxxxx, â˜ƒ, â˜ƒxx, â˜ƒxxxx);
      } else {
         return ANY;
      }
   }

   private static <T> Stat<T> getStat(StatType<T> var0, ResourceLocation var1) {
      Registry<T> â˜ƒ = â˜ƒ.getRegistry();
      T â˜ƒx = â˜ƒ.get(â˜ƒ);
      if (â˜ƒx == null) {
         throw new JsonParseException("Unknown object " + â˜ƒ + " for stat type " + Registry.STAT_TYPE.getKey(â˜ƒ));
      } else {
         return â˜ƒ.get(â˜ƒx);
      }
   }

   private static <T> ResourceLocation getStatValueId(Stat<T> var0) {
      return â˜ƒ.getType().getRegistry().getKey(â˜ƒ.getValue());
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("level", this.level.serializeToJson());
         if (this.gameType != null) {
            â˜ƒ.addProperty("gamemode", this.gameType.getName());
         }

         if (!this.stats.isEmpty()) {
            JsonArray â˜ƒ = new JsonArray();
            this.stats.forEach((var1x, var2x) -> {
               JsonObject â˜ƒ = new JsonObject();
               â˜ƒ.addProperty("type", Registry.STAT_TYPE.getKey(var1x.getType()).toString());
               â˜ƒ.addProperty("stat", getStatValueId(var1x).toString());
               â˜ƒ.add("value", var2x.serializeToJson());
               â˜ƒ.add(â˜ƒ);
            });
            â˜ƒ.add("stats", â˜ƒ);
         }

         if (!this.recipes.isEmpty()) {
            JsonObject â˜ƒ = new JsonObject();
            this.recipes.forEach((var1x, var2x) -> â˜ƒ.addProperty(var1x.toString(), var2x));
            â˜ƒ.add("recipes", â˜ƒ);
         }

         if (!this.advancements.isEmpty()) {
            JsonObject â˜ƒ = new JsonObject();
            this.advancements.forEach((var1x, var2x) -> â˜ƒ.add(var1x.toString(), var2x.toJson()));
            â˜ƒ.add("advancements", â˜ƒ);
         }

         â˜ƒ.add("looking_at", this.lookingAt.serializeToJson());
         return â˜ƒ;
      }
   }

   static class AdvancementCriterionsPredicate implements PlayerPredicate.AdvancementPredicate {
      private final Object2BooleanMap<String> criterions;

      public AdvancementCriterionsPredicate(Object2BooleanMap<String> var1) {
         this.criterions = â˜ƒ;
      }

      @Override
      public JsonElement toJson() {
         JsonObject â˜ƒ = new JsonObject();
         this.criterions.forEach(â˜ƒ::addProperty);
         return â˜ƒ;
      }

      public boolean test(AdvancementProgress var1) {
         for(it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String> â˜ƒ : this.criterions.object2BooleanEntrySet()) {
            CriterionProgress â˜ƒx = â˜ƒ.getCriterion((String)â˜ƒ.getKey());
            if (â˜ƒx == null || â˜ƒx.isDone() != â˜ƒ.getBooleanValue()) {
               return false;
            }
         }

         return true;
      }
   }

   static class AdvancementDonePredicate implements PlayerPredicate.AdvancementPredicate {
      private final boolean state;

      public AdvancementDonePredicate(boolean var1) {
         this.state = â˜ƒ;
      }

      @Override
      public JsonElement toJson() {
         return new JsonPrimitive(this.state);
      }

      public boolean test(AdvancementProgress var1) {
         return â˜ƒ.isDone() == this.state;
      }
   }

   interface AdvancementPredicate extends Predicate<AdvancementProgress> {
      JsonElement toJson();
   }

   public static class Builder {
      private MinMaxBounds.Ints level = MinMaxBounds.Ints.ANY;
      @Nullable
      private GameType gameType;
      private final Map<Stat<?>, MinMaxBounds.Ints> stats = Maps.<Stat<?>, MinMaxBounds.Ints>newHashMap();
      private final Object2BooleanMap<ResourceLocation> recipes = new Object2BooleanOpenHashMap<>();
      private final Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> advancements = Maps.<ResourceLocation, PlayerPredicate.AdvancementPredicate>newHashMap(
         
      );
      private EntityPredicate lookingAt = EntityPredicate.ANY;

      public static PlayerPredicate.Builder player() {
         return new PlayerPredicate.Builder();
      }

      public PlayerPredicate.Builder setLevel(MinMaxBounds.Ints var1) {
         this.level = â˜ƒ;
         return this;
      }

      public PlayerPredicate.Builder addStat(Stat<?> var1, MinMaxBounds.Ints var2) {
         this.stats.put(â˜ƒ, â˜ƒ);
         return this;
      }

      public PlayerPredicate.Builder addRecipe(ResourceLocation var1, boolean var2) {
         this.recipes.put(â˜ƒ, â˜ƒ);
         return this;
      }

      public PlayerPredicate.Builder setGameType(GameType var1) {
         this.gameType = â˜ƒ;
         return this;
      }

      public PlayerPredicate.Builder setLookingAt(EntityPredicate var1) {
         this.lookingAt = â˜ƒ;
         return this;
      }

      public PlayerPredicate.Builder checkAdvancementDone(ResourceLocation var1, boolean var2) {
         this.advancements.put(â˜ƒ, new PlayerPredicate.AdvancementDonePredicate(â˜ƒ));
         return this;
      }

      public PlayerPredicate.Builder checkAdvancementCriterions(ResourceLocation var1, Map<String, Boolean> var2) {
         this.advancements.put(â˜ƒ, new PlayerPredicate.AdvancementCriterionsPredicate(new Object2BooleanOpenHashMap(â˜ƒ)));
         return this;
      }

      public PlayerPredicate build() {
         return new PlayerPredicate(this.level, this.gameType, this.stats, this.recipes, this.advancements, this.lookingAt);
      }
   }
}
