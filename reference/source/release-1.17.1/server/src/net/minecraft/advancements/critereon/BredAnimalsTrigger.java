package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.storage.loot.LootContext;

public class BredAnimalsTrigger extends SimpleCriterionTrigger<BredAnimalsTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("bred_animals");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public BredAnimalsTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      EntityPredicate.Composite â˜ƒ = EntityPredicate.Composite.fromJson(â˜ƒ, "parent", â˜ƒ);
      EntityPredicate.Composite â˜ƒx = EntityPredicate.Composite.fromJson(â˜ƒ, "partner", â˜ƒ);
      EntityPredicate.Composite â˜ƒxx = EntityPredicate.Composite.fromJson(â˜ƒ, "child", â˜ƒ);
      return new BredAnimalsTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public void trigger(ServerPlayer var1, Animal var2, Animal var3, @Nullable AgeableMob var4) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      LootContext â˜ƒx = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      LootContext â˜ƒxx = â˜ƒ != null ? EntityPredicate.createContext(â˜ƒ, â˜ƒ) : null;
      this.trigger(â˜ƒ, var3x -> var3x.matches(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite parent;
      private final EntityPredicate.Composite partner;
      private final EntityPredicate.Composite child;

      public TriggerInstance(EntityPredicate.Composite var1, EntityPredicate.Composite var2, EntityPredicate.Composite var3, EntityPredicate.Composite var4) {
         super(BredAnimalsTrigger.ID, â˜ƒ);
         this.parent = â˜ƒ;
         this.partner = â˜ƒ;
         this.child = â˜ƒ;
      }

      public static BredAnimalsTrigger.TriggerInstance bredAnimals() {
         return new BredAnimalsTrigger.TriggerInstance(
            EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY
         );
      }

      public static BredAnimalsTrigger.TriggerInstance bredAnimals(EntityPredicate.Builder var0) {
         return new BredAnimalsTrigger.TriggerInstance(
            EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ.build())
         );
      }

      public static BredAnimalsTrigger.TriggerInstance bredAnimals(EntityPredicate var0, EntityPredicate var1, EntityPredicate var2) {
         return new BredAnimalsTrigger.TriggerInstance(
            EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ), EntityPredicate.Composite.wrap(â˜ƒ), EntityPredicate.Composite.wrap(â˜ƒ)
         );
      }

      public boolean matches(LootContext var1, LootContext var2, @Nullable LootContext var3) {
         if (this.child == EntityPredicate.Composite.ANY || â˜ƒ != null && this.child.matches(â˜ƒ)) {
            return this.parent.matches(â˜ƒ) && this.partner.matches(â˜ƒ) || this.parent.matches(â˜ƒ) && this.partner.matches(â˜ƒ);
         } else {
            return false;
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("parent", this.parent.toJson(â˜ƒ));
         â˜ƒ.add("partner", this.partner.toJson(â˜ƒ));
         â˜ƒ.add("child", this.child.toJson(â˜ƒ));
         return â˜ƒ;
      }
   }
}
