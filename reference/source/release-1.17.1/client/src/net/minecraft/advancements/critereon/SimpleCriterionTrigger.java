package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;

public abstract class SimpleCriterionTrigger<T extends AbstractCriterionTriggerInstance> implements CriterionTrigger<T> {
   private final Map<PlayerAdvancements, Set<CriterionTrigger.Listener<T>>> players = Maps.newIdentityHashMap();

   @Override
   public final void addPlayerListener(PlayerAdvancements var1, CriterionTrigger.Listener<T> var2) {
      ((Set)this.players.computeIfAbsent(â˜ƒ, var0 -> Sets.newHashSet())).add(â˜ƒ);
   }

   @Override
   public final void removePlayerListener(PlayerAdvancements var1, CriterionTrigger.Listener<T> var2) {
      Set<CriterionTrigger.Listener<T>> â˜ƒ = (Set)this.players.get(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.remove(â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            this.players.remove(â˜ƒ);
         }
      }
   }

   @Override
   public final void removePlayerListeners(PlayerAdvancements var1) {
      this.players.remove(â˜ƒ);
   }

   protected abstract T createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3);

   public final T createInstance(JsonObject var1, DeserializationContext var2) {
      EntityPredicate.Composite â˜ƒ = EntityPredicate.Composite.fromJson(â˜ƒ, "player", â˜ƒ);
      return this.createInstance(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void trigger(ServerPlayer var1, Predicate<T> var2) {
      PlayerAdvancements â˜ƒ = â˜ƒ.getAdvancements();
      Set<CriterionTrigger.Listener<T>> â˜ƒx = (Set)this.players.get(â˜ƒ);
      if (â˜ƒx != null && !â˜ƒx.isEmpty()) {
         LootContext â˜ƒxx = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
         List<CriterionTrigger.Listener<T>> â˜ƒxxx = null;

         for(CriterionTrigger.Listener<T> â˜ƒxxxx : â˜ƒx) {
            T â˜ƒxxxxx = â˜ƒxxxx.getTriggerInstance();
            if (â˜ƒ.test(â˜ƒxxxxx) && â˜ƒxxxxx.getPlayerPredicate().matches(â˜ƒxx)) {
               if (â˜ƒxxx == null) {
                  â˜ƒxxx = Lists.<CriterionTrigger.Listener<T>>newArrayList();
               }

               â˜ƒxxx.add(â˜ƒxxxx);
            }
         }

         if (â˜ƒxxx != null) {
            for(CriterionTrigger.Listener<T> â˜ƒxxxx : â˜ƒxxx) {
               â˜ƒxxxx.run(â˜ƒ);
            }
         }
      }
   }
}
