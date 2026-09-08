package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class LocationTrigger extends SimpleCriterionTrigger<LocationTrigger.TriggerInstance> {
   final ResourceLocation id;

   public LocationTrigger(ResourceLocation var1) {
      this.id = â˜ƒ;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   public LocationTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      JsonObject â˜ƒ = GsonHelper.getAsJsonObject(â˜ƒ, "location", â˜ƒ);
      LocationPredicate â˜ƒx = LocationPredicate.fromJson(â˜ƒ);
      return new LocationTrigger.TriggerInstance(this.id, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1) {
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ.getLevel(), â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ()));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final LocationPredicate location;

      public TriggerInstance(ResourceLocation var1, EntityPredicate.Composite var2, LocationPredicate var3) {
         super(â˜ƒ, â˜ƒ);
         this.location = â˜ƒ;
      }

      public static LocationTrigger.TriggerInstance located(LocationPredicate var0) {
         return new LocationTrigger.TriggerInstance(CriteriaTriggers.LOCATION.id, EntityPredicate.Composite.ANY, â˜ƒ);
      }

      public static LocationTrigger.TriggerInstance located(EntityPredicate var0) {
         return new LocationTrigger.TriggerInstance(CriteriaTriggers.LOCATION.id, EntityPredicate.Composite.wrap(â˜ƒ), LocationPredicate.ANY);
      }

      public static LocationTrigger.TriggerInstance sleptInBed() {
         return new LocationTrigger.TriggerInstance(CriteriaTriggers.SLEPT_IN_BED.id, EntityPredicate.Composite.ANY, LocationPredicate.ANY);
      }

      public static LocationTrigger.TriggerInstance raidWon() {
         return new LocationTrigger.TriggerInstance(CriteriaTriggers.RAID_WIN.id, EntityPredicate.Composite.ANY, LocationPredicate.ANY);
      }

      public static LocationTrigger.TriggerInstance walkOnBlockWithEquipment(Block var0, Item var1) {
         return located(
            EntityPredicate.Builder.entity()
               .equipment(EntityEquipmentPredicate.Builder.equipment().feet(ItemPredicate.Builder.item().of(â˜ƒ).build()).build())
               .steppingOn(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(â˜ƒ).build()).build())
               .build()
         );
      }

      public boolean matches(ServerLevel var1, double var2, double var4, double var6) {
         return this.location.matches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("location", this.location.serializeToJson());
         return â˜ƒ;
      }
   }
}
