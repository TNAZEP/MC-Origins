package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class LocationCheck implements LootItemCondition {
   final LocationPredicate predicate;
   final BlockPos offset;

   LocationCheck(LocationPredicate var1, BlockPos var2) {
      this.predicate = â˜ƒ;
      this.offset = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.LOCATION_CHECK;
   }

   public boolean test(LootContext var1) {
      Vec3 â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.ORIGIN);
      return â˜ƒ != null
         && this.predicate
            .matches(â˜ƒ.getLevel(), â˜ƒ.x() + (double)this.offset.getX(), â˜ƒ.y() + (double)this.offset.getY(), â˜ƒ.z() + (double)this.offset.getZ());
   }

   public static LootItemCondition.Builder checkLocation(LocationPredicate.Builder var0) {
      return () -> new LocationCheck(â˜ƒ.build(), BlockPos.ZERO);
   }

   public static LootItemCondition.Builder checkLocation(LocationPredicate.Builder var0, BlockPos var1) {
      return () -> new LocationCheck(â˜ƒ.build(), â˜ƒ);
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LocationCheck> {
      public void serialize(JsonObject var1, LocationCheck var2, JsonSerializationContext var3) {
         â˜ƒ.add("predicate", â˜ƒ.predicate.serializeToJson());
         if (â˜ƒ.offset.getX() != 0) {
            â˜ƒ.addProperty("offsetX", â˜ƒ.offset.getX());
         }

         if (â˜ƒ.offset.getY() != 0) {
            â˜ƒ.addProperty("offsetY", â˜ƒ.offset.getY());
         }

         if (â˜ƒ.offset.getZ() != 0) {
            â˜ƒ.addProperty("offsetZ", â˜ƒ.offset.getZ());
         }
      }

      public LocationCheck deserialize(JsonObject var1, JsonDeserializationContext var2) {
         LocationPredicate â˜ƒ = LocationPredicate.fromJson(â˜ƒ.get("predicate"));
         int â˜ƒx = GsonHelper.getAsInt(â˜ƒ, "offsetX", 0);
         int â˜ƒxx = GsonHelper.getAsInt(â˜ƒ, "offsetY", 0);
         int â˜ƒxxx = GsonHelper.getAsInt(â˜ƒ, "offsetZ", 0);
         return new LocationCheck(â˜ƒ, new BlockPos(â˜ƒx, â˜ƒxx, â˜ƒxxx));
      }
   }
}
