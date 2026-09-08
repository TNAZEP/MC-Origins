package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;

public class EntityEquipmentPredicate {
   public static final EntityEquipmentPredicate ANY = new EntityEquipmentPredicate(
      ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY
   );
   public static final EntityEquipmentPredicate CAPTAIN = new EntityEquipmentPredicate(
      ItemPredicate.Builder.item().of(Items.WHITE_BANNER).hasNbt(Raid.getLeaderBannerInstance().getTag()).build(),
      ItemPredicate.ANY,
      ItemPredicate.ANY,
      ItemPredicate.ANY,
      ItemPredicate.ANY,
      ItemPredicate.ANY
   );
   private final ItemPredicate head;
   private final ItemPredicate chest;
   private final ItemPredicate legs;
   private final ItemPredicate feet;
   private final ItemPredicate mainhand;
   private final ItemPredicate offhand;

   public EntityEquipmentPredicate(ItemPredicate var1, ItemPredicate var2, ItemPredicate var3, ItemPredicate var4, ItemPredicate var5, ItemPredicate var6) {
      this.head = â˜ƒ;
      this.chest = â˜ƒ;
      this.legs = â˜ƒ;
      this.feet = â˜ƒ;
      this.mainhand = â˜ƒ;
      this.offhand = â˜ƒ;
   }

   public boolean matches(@Nullable Entity var1) {
      if (this == ANY) {
         return true;
      } else if (!(â˜ƒ instanceof LivingEntity)) {
         return false;
      } else {
         LivingEntity â˜ƒ = (LivingEntity)â˜ƒ;
         if (!this.head.matches(â˜ƒ.getItemBySlot(EquipmentSlot.HEAD))) {
            return false;
         } else if (!this.chest.matches(â˜ƒ.getItemBySlot(EquipmentSlot.CHEST))) {
            return false;
         } else if (!this.legs.matches(â˜ƒ.getItemBySlot(EquipmentSlot.LEGS))) {
            return false;
         } else if (!this.feet.matches(â˜ƒ.getItemBySlot(EquipmentSlot.FEET))) {
            return false;
         } else if (!this.mainhand.matches(â˜ƒ.getItemBySlot(EquipmentSlot.MAINHAND))) {
            return false;
         } else {
            return this.offhand.matches(â˜ƒ.getItemBySlot(EquipmentSlot.OFFHAND));
         }
      }
   }

   public static EntityEquipmentPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "equipment");
         ItemPredicate â˜ƒx = ItemPredicate.fromJson(â˜ƒ.get("head"));
         ItemPredicate â˜ƒxx = ItemPredicate.fromJson(â˜ƒ.get("chest"));
         ItemPredicate â˜ƒxxx = ItemPredicate.fromJson(â˜ƒ.get("legs"));
         ItemPredicate â˜ƒxxxx = ItemPredicate.fromJson(â˜ƒ.get("feet"));
         ItemPredicate â˜ƒxxxxx = ItemPredicate.fromJson(â˜ƒ.get("mainhand"));
         ItemPredicate â˜ƒxxxxxx = ItemPredicate.fromJson(â˜ƒ.get("offhand"));
         return new EntityEquipmentPredicate(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.add("head", this.head.serializeToJson());
         â˜ƒ.add("chest", this.chest.serializeToJson());
         â˜ƒ.add("legs", this.legs.serializeToJson());
         â˜ƒ.add("feet", this.feet.serializeToJson());
         â˜ƒ.add("mainhand", this.mainhand.serializeToJson());
         â˜ƒ.add("offhand", this.offhand.serializeToJson());
         return â˜ƒ;
      }
   }

   public static class Builder {
      private ItemPredicate head = ItemPredicate.ANY;
      private ItemPredicate chest = ItemPredicate.ANY;
      private ItemPredicate legs = ItemPredicate.ANY;
      private ItemPredicate feet = ItemPredicate.ANY;
      private ItemPredicate mainhand = ItemPredicate.ANY;
      private ItemPredicate offhand = ItemPredicate.ANY;

      public static EntityEquipmentPredicate.Builder equipment() {
         return new EntityEquipmentPredicate.Builder();
      }

      public EntityEquipmentPredicate.Builder head(ItemPredicate var1) {
         this.head = â˜ƒ;
         return this;
      }

      public EntityEquipmentPredicate.Builder chest(ItemPredicate var1) {
         this.chest = â˜ƒ;
         return this;
      }

      public EntityEquipmentPredicate.Builder legs(ItemPredicate var1) {
         this.legs = â˜ƒ;
         return this;
      }

      public EntityEquipmentPredicate.Builder feet(ItemPredicate var1) {
         this.feet = â˜ƒ;
         return this;
      }

      public EntityEquipmentPredicate.Builder mainhand(ItemPredicate var1) {
         this.mainhand = â˜ƒ;
         return this;
      }

      public EntityEquipmentPredicate.Builder offhand(ItemPredicate var1) {
         this.offhand = â˜ƒ;
         return this;
      }

      public EntityEquipmentPredicate build() {
         return new EntityEquipmentPredicate(this.head, this.chest, this.legs, this.feet, this.mainhand, this.offhand);
      }
   }
}
