package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.phys.Vec3;

public class LighthingBoltPredicate {
   public static final LighthingBoltPredicate ANY = new LighthingBoltPredicate(MinMaxBounds.Ints.ANY, EntityPredicate.ANY);
   private static final String BLOCKS_SET_ON_FIRE_KEY = "blocks_set_on_fire";
   private static final String ENTITY_STRUCK_KEY = "entity_struck";
   private final MinMaxBounds.Ints blocksSetOnFire;
   private final EntityPredicate entityStruck;

   private LighthingBoltPredicate(MinMaxBounds.Ints var1, EntityPredicate var2) {
      this.blocksSetOnFire = â˜ƒ;
      this.entityStruck = â˜ƒ;
   }

   public static LighthingBoltPredicate blockSetOnFire(MinMaxBounds.Ints var0) {
      return new LighthingBoltPredicate(â˜ƒ, EntityPredicate.ANY);
   }

   public static LighthingBoltPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "lightning");
         return new LighthingBoltPredicate(MinMaxBounds.Ints.fromJson(â˜ƒ.get("blocks_set_on_fire")), EntityPredicate.fromJson(â˜ƒ.get("entity_struck")));
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("blocks_set_on_fire", this.blocksSetOnFire.serializeToJson());
         â˜ƒ.add("entity_struck", this.entityStruck.serializeToJson());
         return â˜ƒ;
      }
   }

   public boolean matches(Entity var1, ServerLevel var2, @Nullable Vec3 var3) {
      if (this == ANY) {
         return true;
      } else if (!(â˜ƒ instanceof LightningBolt)) {
         return false;
      } else {
         LightningBolt â˜ƒ = (LightningBolt)â˜ƒ;
         return this.blocksSetOnFire.matches(â˜ƒ.getBlocksSetOnFire())
            && (this.entityStruck == EntityPredicate.ANY || â˜ƒ.getHitEntities().anyMatch(var3x -> this.entityStruck.matches(â˜ƒ, â˜ƒ, var3x)));
      }
   }
}
