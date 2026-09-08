package net.minecraft.advancements.critereon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;

public class EntityPredicate {
   public static final EntityPredicate ANY = new EntityPredicate(
      EntityTypePredicate.ANY,
      DistancePredicate.ANY,
      LocationPredicate.ANY,
      LocationPredicate.ANY,
      MobEffectsPredicate.ANY,
      NbtPredicate.ANY,
      EntityFlagsPredicate.ANY,
      EntityEquipmentPredicate.ANY,
      PlayerPredicate.ANY,
      FishingHookPredicate.ANY,
      LighthingBoltPredicate.ANY,
      null,
      null
   );
   private final EntityTypePredicate entityType;
   private final DistancePredicate distanceToPlayer;
   private final LocationPredicate location;
   private final LocationPredicate steppingOnLocation;
   private final MobEffectsPredicate effects;
   private final NbtPredicate nbt;
   private final EntityFlagsPredicate flags;
   private final EntityEquipmentPredicate equipment;
   private final PlayerPredicate player;
   private final FishingHookPredicate fishingHook;
   private final LighthingBoltPredicate lighthingBolt;
   private final EntityPredicate vehicle;
   private final EntityPredicate passenger;
   private final EntityPredicate targetedEntity;
   @Nullable
   private final String team;
   @Nullable
   private final ResourceLocation catType;

   private EntityPredicate(
      EntityTypePredicate var1,
      DistancePredicate var2,
      LocationPredicate var3,
      LocationPredicate var4,
      MobEffectsPredicate var5,
      NbtPredicate var6,
      EntityFlagsPredicate var7,
      EntityEquipmentPredicate var8,
      PlayerPredicate var9,
      FishingHookPredicate var10,
      LighthingBoltPredicate var11,
      @Nullable String var12,
      @Nullable ResourceLocation var13
   ) {
      this.entityType = â˜ƒ;
      this.distanceToPlayer = â˜ƒ;
      this.location = â˜ƒ;
      this.steppingOnLocation = â˜ƒ;
      this.effects = â˜ƒ;
      this.nbt = â˜ƒ;
      this.flags = â˜ƒ;
      this.equipment = â˜ƒ;
      this.player = â˜ƒ;
      this.fishingHook = â˜ƒ;
      this.lighthingBolt = â˜ƒ;
      this.passenger = this;
      this.vehicle = this;
      this.targetedEntity = this;
      this.team = â˜ƒ;
      this.catType = â˜ƒ;
   }

   EntityPredicate(
      EntityTypePredicate var1,
      DistancePredicate var2,
      LocationPredicate var3,
      LocationPredicate var4,
      MobEffectsPredicate var5,
      NbtPredicate var6,
      EntityFlagsPredicate var7,
      EntityEquipmentPredicate var8,
      PlayerPredicate var9,
      FishingHookPredicate var10,
      LighthingBoltPredicate var11,
      EntityPredicate var12,
      EntityPredicate var13,
      EntityPredicate var14,
      @Nullable String var15,
      @Nullable ResourceLocation var16
   ) {
      this.entityType = â˜ƒ;
      this.distanceToPlayer = â˜ƒ;
      this.location = â˜ƒ;
      this.steppingOnLocation = â˜ƒ;
      this.effects = â˜ƒ;
      this.nbt = â˜ƒ;
      this.flags = â˜ƒ;
      this.equipment = â˜ƒ;
      this.player = â˜ƒ;
      this.fishingHook = â˜ƒ;
      this.lighthingBolt = â˜ƒ;
      this.vehicle = â˜ƒ;
      this.passenger = â˜ƒ;
      this.targetedEntity = â˜ƒ;
      this.team = â˜ƒ;
      this.catType = â˜ƒ;
   }

   public boolean matches(ServerPlayer var1, @Nullable Entity var2) {
      return this.matches(â˜ƒ.getLevel(), â˜ƒ.position(), â˜ƒ);
   }

   public boolean matches(ServerLevel var1, @Nullable Vec3 var2, @Nullable Entity var3) {
      if (this == ANY) {
         return true;
      } else if (â˜ƒ == null) {
         return false;
      } else if (!this.entityType.matches(â˜ƒ.getType())) {
         return false;
      } else {
         if (â˜ƒ == null) {
            if (this.distanceToPlayer != DistancePredicate.ANY) {
               return false;
            }
         } else if (!this.distanceToPlayer.matches(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ())) {
            return false;
         }

         if (!this.location.matches(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ())) {
            return false;
         } else {
            if (this.steppingOnLocation != LocationPredicate.ANY) {
               Vec3 â˜ƒ = Vec3.atCenterOf(â˜ƒ.getOnPos());
               if (!this.steppingOnLocation.matches(â˜ƒ, â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z())) {
                  return false;
               }
            }

            if (!this.effects.matches(â˜ƒ)) {
               return false;
            } else if (!this.nbt.matches(â˜ƒ)) {
               return false;
            } else if (!this.flags.matches(â˜ƒ)) {
               return false;
            } else if (!this.equipment.matches(â˜ƒ)) {
               return false;
            } else if (!this.player.matches(â˜ƒ)) {
               return false;
            } else if (!this.fishingHook.matches(â˜ƒ)) {
               return false;
            } else if (!this.lighthingBolt.matches(â˜ƒ, â˜ƒ, â˜ƒ)) {
               return false;
            } else if (!this.vehicle.matches(â˜ƒ, â˜ƒ, â˜ƒ.getVehicle())) {
               return false;
            } else if (this.passenger != ANY && â˜ƒ.getPassengers().stream().noneMatch(var3x -> this.passenger.matches(â˜ƒ, â˜ƒ, var3x))) {
               return false;
            } else if (!this.targetedEntity.matches(â˜ƒ, â˜ƒ, â˜ƒ instanceof Mob ? ((Mob)â˜ƒ).getTarget() : null)) {
               return false;
            } else {
               if (this.team != null) {
                  Team â˜ƒ = â˜ƒ.getTeam();
                  if (â˜ƒ == null || !this.team.equals(â˜ƒ.getName())) {
                     return false;
                  }
               }

               return this.catType == null || â˜ƒ instanceof Cat && ((Cat)â˜ƒ).getResourceLocation().equals(this.catType);
            }
         }
      }
   }

   public static EntityPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "entity");
         EntityTypePredicate â˜ƒx = EntityTypePredicate.fromJson(â˜ƒ.get("type"));
         DistancePredicate â˜ƒxx = DistancePredicate.fromJson(â˜ƒ.get("distance"));
         LocationPredicate â˜ƒxxx = LocationPredicate.fromJson(â˜ƒ.get("location"));
         LocationPredicate â˜ƒxxxx = LocationPredicate.fromJson(â˜ƒ.get("stepping_on"));
         MobEffectsPredicate â˜ƒxxxxx = MobEffectsPredicate.fromJson(â˜ƒ.get("effects"));
         NbtPredicate â˜ƒxxxxxx = NbtPredicate.fromJson(â˜ƒ.get("nbt"));
         EntityFlagsPredicate â˜ƒxxxxxxx = EntityFlagsPredicate.fromJson(â˜ƒ.get("flags"));
         EntityEquipmentPredicate â˜ƒxxxxxxxx = EntityEquipmentPredicate.fromJson(â˜ƒ.get("equipment"));
         PlayerPredicate â˜ƒxxxxxxxxx = PlayerPredicate.fromJson(â˜ƒ.get("player"));
         FishingHookPredicate â˜ƒxxxxxxxxxx = FishingHookPredicate.fromJson(â˜ƒ.get("fishing_hook"));
         EntityPredicate â˜ƒxxxxxxxxxxx = fromJson(â˜ƒ.get("vehicle"));
         EntityPredicate â˜ƒxxxxxxxxxxxx = fromJson(â˜ƒ.get("passenger"));
         EntityPredicate â˜ƒxxxxxxxxxxxxx = fromJson(â˜ƒ.get("targeted_entity"));
         LighthingBoltPredicate â˜ƒxxxxxxxxxxxxxx = LighthingBoltPredicate.fromJson(â˜ƒ.get("lightning_bolt"));
         String â˜ƒxxxxxxxxxxxxxxx = GsonHelper.getAsString(â˜ƒ, "team", null);
         ResourceLocation â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ.has("catType") ? new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "catType")) : null;
         return new EntityPredicate.Builder()
            .entityType(â˜ƒx)
            .distance(â˜ƒxx)
            .located(â˜ƒxxx)
            .steppingOn(â˜ƒxxxx)
            .effects(â˜ƒxxxxx)
            .nbt(â˜ƒxxxxxx)
            .flags(â˜ƒxxxxxxx)
            .equipment(â˜ƒxxxxxxxx)
            .player(â˜ƒxxxxxxxxx)
            .fishingHook(â˜ƒxxxxxxxxxx)
            .lighthingBolt(â˜ƒxxxxxxxxxxxxxx)
            .team(â˜ƒxxxxxxxxxxxxxxx)
            .vehicle(â˜ƒxxxxxxxxxxx)
            .passenger(â˜ƒxxxxxxxxxxxx)
            .targetedEntity(â˜ƒxxxxxxxxxxxxx)
            .catType(â˜ƒxxxxxxxxxxxxxxxx)
            .build();
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("type", this.entityType.serializeToJson());
         â˜ƒ.add("distance", this.distanceToPlayer.serializeToJson());
         â˜ƒ.add("location", this.location.serializeToJson());
         â˜ƒ.add("stepping_on", this.steppingOnLocation.serializeToJson());
         â˜ƒ.add("effects", this.effects.serializeToJson());
         â˜ƒ.add("nbt", this.nbt.serializeToJson());
         â˜ƒ.add("flags", this.flags.serializeToJson());
         â˜ƒ.add("equipment", this.equipment.serializeToJson());
         â˜ƒ.add("player", this.player.serializeToJson());
         â˜ƒ.add("fishing_hook", this.fishingHook.serializeToJson());
         â˜ƒ.add("lightning_bolt", this.lighthingBolt.serializeToJson());
         â˜ƒ.add("vehicle", this.vehicle.serializeToJson());
         â˜ƒ.add("passenger", this.passenger.serializeToJson());
         â˜ƒ.add("targeted_entity", this.targetedEntity.serializeToJson());
         â˜ƒ.addProperty("team", this.team);
         if (this.catType != null) {
            â˜ƒ.addProperty("catType", this.catType.toString());
         }

         return â˜ƒ;
      }
   }

   public static LootContext createContext(ServerPlayer var0, Entity var1) {
      return new LootContext.Builder(â˜ƒ.getLevel())
         .withParameter(LootContextParams.THIS_ENTITY, â˜ƒ)
         .withParameter(LootContextParams.ORIGIN, â˜ƒ.position())
         .withRandom(â˜ƒ.getRandom())
         .create(LootContextParamSets.ADVANCEMENT_ENTITY);
   }

   public static class Builder {
      private EntityTypePredicate entityType = EntityTypePredicate.ANY;
      private DistancePredicate distanceToPlayer = DistancePredicate.ANY;
      private LocationPredicate location = LocationPredicate.ANY;
      private LocationPredicate steppingOnLocation = LocationPredicate.ANY;
      private MobEffectsPredicate effects = MobEffectsPredicate.ANY;
      private NbtPredicate nbt = NbtPredicate.ANY;
      private EntityFlagsPredicate flags = EntityFlagsPredicate.ANY;
      private EntityEquipmentPredicate equipment = EntityEquipmentPredicate.ANY;
      private PlayerPredicate player = PlayerPredicate.ANY;
      private FishingHookPredicate fishingHook = FishingHookPredicate.ANY;
      private LighthingBoltPredicate lighthingBolt = LighthingBoltPredicate.ANY;
      private EntityPredicate vehicle = EntityPredicate.ANY;
      private EntityPredicate passenger = EntityPredicate.ANY;
      private EntityPredicate targetedEntity = EntityPredicate.ANY;
      private String team;
      private ResourceLocation catType;

      public static EntityPredicate.Builder entity() {
         return new EntityPredicate.Builder();
      }

      public EntityPredicate.Builder of(EntityType<?> var1) {
         this.entityType = EntityTypePredicate.of(â˜ƒ);
         return this;
      }

      public EntityPredicate.Builder of(Tag<EntityType<?>> var1) {
         this.entityType = EntityTypePredicate.of(â˜ƒ);
         return this;
      }

      public EntityPredicate.Builder of(ResourceLocation var1) {
         this.catType = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder entityType(EntityTypePredicate var1) {
         this.entityType = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder distance(DistancePredicate var1) {
         this.distanceToPlayer = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder located(LocationPredicate var1) {
         this.location = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder steppingOn(LocationPredicate var1) {
         this.steppingOnLocation = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder effects(MobEffectsPredicate var1) {
         this.effects = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder nbt(NbtPredicate var1) {
         this.nbt = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder flags(EntityFlagsPredicate var1) {
         this.flags = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder equipment(EntityEquipmentPredicate var1) {
         this.equipment = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder player(PlayerPredicate var1) {
         this.player = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder fishingHook(FishingHookPredicate var1) {
         this.fishingHook = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder lighthingBolt(LighthingBoltPredicate var1) {
         this.lighthingBolt = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder vehicle(EntityPredicate var1) {
         this.vehicle = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder passenger(EntityPredicate var1) {
         this.passenger = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder targetedEntity(EntityPredicate var1) {
         this.targetedEntity = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder team(@Nullable String var1) {
         this.team = â˜ƒ;
         return this;
      }

      public EntityPredicate.Builder catType(@Nullable ResourceLocation var1) {
         this.catType = â˜ƒ;
         return this;
      }

      public EntityPredicate build() {
         return new EntityPredicate(
            this.entityType,
            this.distanceToPlayer,
            this.location,
            this.steppingOnLocation,
            this.effects,
            this.nbt,
            this.flags,
            this.equipment,
            this.player,
            this.fishingHook,
            this.lighthingBolt,
            this.vehicle,
            this.passenger,
            this.targetedEntity,
            this.team,
            this.catType
         );
      }
   }

   public static class Composite {
      public static final EntityPredicate.Composite ANY = new EntityPredicate.Composite(new LootItemCondition[0]);
      private final LootItemCondition[] conditions;
      private final Predicate<LootContext> compositePredicates;

      private Composite(LootItemCondition[] var1) {
         this.conditions = â˜ƒ;
         this.compositePredicates = LootItemConditions.andConditions(â˜ƒ);
      }

      public static EntityPredicate.Composite create(LootItemCondition... var0) {
         return new EntityPredicate.Composite(â˜ƒ);
      }

      public static EntityPredicate.Composite fromJson(JsonObject var0, String var1, DeserializationContext var2) {
         JsonElement â˜ƒ = â˜ƒ.get(â˜ƒ);
         return fromElement(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public static EntityPredicate.Composite[] fromJsonArray(JsonObject var0, String var1, DeserializationContext var2) {
         JsonElement â˜ƒ = â˜ƒ.get(â˜ƒ);
         if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
            JsonArray â˜ƒx = GsonHelper.convertToJsonArray(â˜ƒ, â˜ƒ);
            EntityPredicate.Composite[] â˜ƒxx = new EntityPredicate.Composite[â˜ƒx.size()];

            for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx.size(); ++â˜ƒxxx) {
               â˜ƒxx[â˜ƒxxx] = fromElement(â˜ƒ + "[" + â˜ƒxxx + "]", â˜ƒ, â˜ƒx.get(â˜ƒxxx));
            }

            return â˜ƒxx;
         } else {
            return new EntityPredicate.Composite[0];
         }
      }

      private static EntityPredicate.Composite fromElement(String var0, DeserializationContext var1, @Nullable JsonElement var2) {
         if (â˜ƒ != null && â˜ƒ.isJsonArray()) {
            LootItemCondition[] â˜ƒ = â˜ƒ.deserializeConditions(
               â˜ƒ.getAsJsonArray(), â˜ƒ.getAdvancementId() + "/" + â˜ƒ, LootContextParamSets.ADVANCEMENT_ENTITY
            );
            return new EntityPredicate.Composite(â˜ƒ);
         } else {
            EntityPredicate â˜ƒ = EntityPredicate.fromJson(â˜ƒ);
            return wrap(â˜ƒ);
         }
      }

      public static EntityPredicate.Composite wrap(EntityPredicate var0) {
         if (â˜ƒ == EntityPredicate.ANY) {
            return ANY;
         } else {
            LootItemCondition â˜ƒ = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, â˜ƒ).build();
            return new EntityPredicate.Composite(new LootItemCondition[]{â˜ƒ});
         }
      }

      public boolean matches(LootContext var1) {
         return this.compositePredicates.test(â˜ƒ);
      }

      public JsonElement toJson(SerializationContext var1) {
         return (JsonElement)(this.conditions.length == 0 ? JsonNull.INSTANCE : â˜ƒ.serializeConditions(this.conditions));
      }

      public static JsonElement toJson(EntityPredicate.Composite[] var0, SerializationContext var1) {
         if (â˜ƒ.length == 0) {
            return JsonNull.INSTANCE;
         } else {
            JsonArray â˜ƒ = new JsonArray();

            for(EntityPredicate.Composite â˜ƒx : â˜ƒ) {
               â˜ƒ.add(â˜ƒx.toJson(â˜ƒ));
            }

            return â˜ƒ;
         }
      }
   }
}
