package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class ChanneledLightningTrigger extends SimpleCriterionTrigger<ChanneledLightningTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("channeled_lightning");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public ChanneledLightningTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      EntityPredicate.Composite[] â˜ƒ = EntityPredicate.Composite.fromJsonArray(â˜ƒ, "victims", â˜ƒ);
      return new ChanneledLightningTrigger.TriggerInstance(â˜ƒ, â˜ƒ);
   }

   public void trigger(ServerPlayer var1, Collection<? extends Entity> var2) {
      List<LootContext> â˜ƒ = (List)â˜ƒ.stream().map(var1x -> EntityPredicate.createContext(â˜ƒ, var1x)).collect(Collectors.toList());
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite[] victims;

      public TriggerInstance(EntityPredicate.Composite var1, EntityPredicate.Composite[] var2) {
         super(ChanneledLightningTrigger.ID, â˜ƒ);
         this.victims = â˜ƒ;
      }

      public static ChanneledLightningTrigger.TriggerInstance channeledLightning(EntityPredicate... var0) {
         return new ChanneledLightningTrigger.TriggerInstance(
            EntityPredicate.Composite.ANY,
            (EntityPredicate.Composite[])Stream.of(â˜ƒ).map(EntityPredicate.Composite::wrap).toArray(var0x -> new EntityPredicate.Composite[var0x])
         );
      }

      public boolean matches(Collection<? extends LootContext> var1) {
         for(EntityPredicate.Composite â˜ƒ : this.victims) {
            boolean â˜ƒx = false;

            for(LootContext â˜ƒxx : â˜ƒ) {
               if (â˜ƒ.matches(â˜ƒxx)) {
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

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("victims", EntityPredicate.Composite.toJson(this.victims, â˜ƒ));
         return â˜ƒ;
      }
   }
}
