package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class FishingRodHookedTrigger extends SimpleCriterionTrigger<FishingRodHookedTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("fishing_rod_hooked");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public FishingRodHookedTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      ItemPredicate â˜ƒ = ItemPredicate.fromJson(â˜ƒ.get("rod"));
      EntityPredicate.Composite â˜ƒx = EntityPredicate.Composite.fromJson(â˜ƒ, "entity", â˜ƒ);
      ItemPredicate â˜ƒxx = ItemPredicate.fromJson(â˜ƒ.get("item"));
      return new FishingRodHookedTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public void trigger(ServerPlayer var1, ItemStack var2, FishingHook var3, Collection<ItemStack> var4) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, (Entity)(â˜ƒ.getHookedIn() != null ? â˜ƒ.getHookedIn() : â˜ƒ));
      this.trigger(â˜ƒ, var3x -> var3x.matches(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate rod;
      private final EntityPredicate.Composite entity;
      private final ItemPredicate item;

      public TriggerInstance(EntityPredicate.Composite var1, ItemPredicate var2, EntityPredicate.Composite var3, ItemPredicate var4) {
         super(FishingRodHookedTrigger.ID, â˜ƒ);
         this.rod = â˜ƒ;
         this.entity = â˜ƒ;
         this.item = â˜ƒ;
      }

      public static FishingRodHookedTrigger.TriggerInstance fishedItem(ItemPredicate var0, EntityPredicate var1, ItemPredicate var2) {
         return new FishingRodHookedTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, EntityPredicate.Composite.wrap(â˜ƒ), â˜ƒ);
      }

      public boolean matches(ItemStack var1, LootContext var2, Collection<ItemStack> var3) {
         if (!this.rod.matches(â˜ƒ)) {
            return false;
         } else if (!this.entity.matches(â˜ƒ)) {
            return false;
         } else {
            if (this.item != ItemPredicate.ANY) {
               boolean â˜ƒx = false;
               Entity â˜ƒxx = â˜ƒ.getParamOrNull(LootContextParams.THIS_ENTITY);
               if (â˜ƒxx instanceof ItemEntity â˜ƒ && this.item.matches(â˜ƒ.getItem())) {
                  â˜ƒx = true;
               }

               for(ItemStack â˜ƒ : â˜ƒ) {
                  if (this.item.matches(â˜ƒ)) {
                     â˜ƒx = true;
                     break;
                  }
               }

               if (!â˜ƒx) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("rod", this.rod.serializeToJson());
         â˜ƒ.add("entity", this.entity.toJson(â˜ƒ));
         â˜ƒ.add("item", this.item.serializeToJson());
         return â˜ƒ;
      }
   }
}
