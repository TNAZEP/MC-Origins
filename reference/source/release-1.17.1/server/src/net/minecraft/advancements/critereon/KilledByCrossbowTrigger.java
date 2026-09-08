package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;

public class KilledByCrossbowTrigger extends SimpleCriterionTrigger<KilledByCrossbowTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("killed_by_crossbow");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public KilledByCrossbowTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      EntityPredicate.Composite[] â˜ƒ = EntityPredicate.Composite.fromJsonArray(â˜ƒ, "victims", â˜ƒ);
      MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("unique_entity_types"));
      return new KilledByCrossbowTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, Collection<Entity> var2) {
      List<LootContext> â˜ƒ = Lists.<LootContext>newArrayList();
      Set<EntityType<?>> â˜ƒx = Sets.<EntityType<?>>newHashSet();

      for(Entity â˜ƒxx : â˜ƒ) {
         â˜ƒx.add(â˜ƒxx.getType());
         â˜ƒ.add(EntityPredicate.createContext(â˜ƒ, â˜ƒxx));
      }

      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ, â˜ƒ.size()));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite[] victims;
      private final MinMaxBounds.Ints uniqueEntityTypes;

      public TriggerInstance(EntityPredicate.Composite var1, EntityPredicate.Composite[] var2, MinMaxBounds.Ints var3) {
         super(KilledByCrossbowTrigger.ID, â˜ƒ);
         this.victims = â˜ƒ;
         this.uniqueEntityTypes = â˜ƒ;
      }

      public static KilledByCrossbowTrigger.TriggerInstance crossbowKilled(EntityPredicate.Builder... var0) {
         EntityPredicate.Composite[] â˜ƒ = new EntityPredicate.Composite[â˜ƒ.length];

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
            EntityPredicate.Builder â˜ƒxx = â˜ƒ[â˜ƒx];
            â˜ƒ[â˜ƒx] = EntityPredicate.Composite.wrap(â˜ƒxx.build());
         }

         return new KilledByCrossbowTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, MinMaxBounds.Ints.ANY);
      }

      public static KilledByCrossbowTrigger.TriggerInstance crossbowKilled(MinMaxBounds.Ints var0) {
         EntityPredicate.Composite[] â˜ƒ = new EntityPredicate.Composite[0];
         return new KilledByCrossbowTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, â˜ƒ);
      }

      public boolean matches(Collection<LootContext> var1, int var2) {
         if (this.victims.length > 0) {
            List<LootContext> â˜ƒ = Lists.<LootContext>newArrayList(â˜ƒ);

            for(EntityPredicate.Composite â˜ƒx : this.victims) {
               boolean â˜ƒxx = false;
               Iterator<LootContext> â˜ƒxxx = â˜ƒ.iterator();

               while(â˜ƒxxx.hasNext()) {
                  LootContext â˜ƒxxxx = (LootContext)â˜ƒxxx.next();
                  if (â˜ƒx.matches(â˜ƒxxxx)) {
                     â˜ƒxxx.remove();
                     â˜ƒxx = true;
                     break;
                  }
               }

               if (!â˜ƒxx) {
                  return false;
               }
            }
         }

         return this.uniqueEntityTypes.matches(â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("victims", EntityPredicate.Composite.toJson(this.victims, â˜ƒ));
         â˜ƒ.add("unique_entity_types", this.uniqueEntityTypes.serializeToJson());
         return â˜ƒ;
      }
   }
}
