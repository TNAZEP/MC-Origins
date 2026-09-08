package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class KilledTrigger implements ICriterionTrigger<KilledTrigger.Instance> {
   private final Map<PlayerAdvancements, KilledTrigger.Listeners> field_192213_a = Maps.<PlayerAdvancements, KilledTrigger.Listeners>newHashMap();
   private final ResourceLocation field_192214_b;

   public KilledTrigger(ResourceLocation var1) {
      this.field_192214_b = ☃;
   }

   @Override
   public ResourceLocation func_192163_a() {
      return this.field_192214_b;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<KilledTrigger.Instance> var2) {
      KilledTrigger.Listeners ☃ = (KilledTrigger.Listeners)this.field_192213_a.get(☃);
      if (☃ == null) {
         ☃ = new KilledTrigger.Listeners(☃);
         this.field_192213_a.put(☃, ☃);
      }

      ☃.func_192504_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<KilledTrigger.Instance> var2) {
      KilledTrigger.Listeners ☃ = (KilledTrigger.Listeners)this.field_192213_a.get(☃);
      if (☃ != null) {
         ☃.func_192501_b(☃);
         if (☃.func_192502_a()) {
            this.field_192213_a.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192213_a.remove(☃);
   }

   public KilledTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      return new KilledTrigger.Instance(
         this.field_192214_b, EntityPredicate.func_192481_a(☃.get("entity")), DamageSourcePredicate.func_192447_a(☃.get("killing_blow"))
      );
   }

   public void func_192211_a(EntityPlayerMP var1, Entity var2, DamageSource var3) {
      KilledTrigger.Listeners ☃ = (KilledTrigger.Listeners)this.field_192213_a.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192503_a(☃, ☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_192271_a;
      private final DamageSourcePredicate field_192272_b;

      public Instance(ResourceLocation var1, EntityPredicate var2, DamageSourcePredicate var3) {
         super(☃);
         this.field_192271_a = ☃;
         this.field_192272_b = ☃;
      }

      public static KilledTrigger.Instance func_203928_a(EntityPredicate.Builder var0) {
         return new KilledTrigger.Instance(CriteriaTriggers.field_192122_b.field_192214_b, ☃.func_204000_b(), DamageSourcePredicate.field_192449_a);
      }

      public static KilledTrigger.Instance func_203927_c() {
         return new KilledTrigger.Instance(CriteriaTriggers.field_192122_b.field_192214_b, EntityPredicate.field_192483_a, DamageSourcePredicate.field_192449_a);
      }

      public static KilledTrigger.Instance func_203929_a(EntityPredicate.Builder var0, DamageSourcePredicate.Builder var1) {
         return new KilledTrigger.Instance(CriteriaTriggers.field_192122_b.field_192214_b, ☃.func_204000_b(), ☃.func_203979_b());
      }

      public static KilledTrigger.Instance func_203926_d() {
         return new KilledTrigger.Instance(CriteriaTriggers.field_192123_c.field_192214_b, EntityPredicate.field_192483_a, DamageSourcePredicate.field_192449_a);
      }

      public boolean func_192270_a(EntityPlayerMP var1, Entity var2, DamageSource var3) {
         return !this.field_192272_b.func_193418_a(☃, ☃) ? false : this.field_192271_a.func_192482_a(☃, ☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("entity", this.field_192271_a.func_204006_a());
         ☃.add("killing_blow", this.field_192272_b.func_203991_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192505_a;
      private final Set<ICriterionTrigger.Listener<KilledTrigger.Instance>> field_192506_b = Sets.<ICriterionTrigger.Listener<KilledTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192505_a = ☃;
      }

      public boolean func_192502_a() {
         return this.field_192506_b.isEmpty();
      }

      public void func_192504_a(ICriterionTrigger.Listener<KilledTrigger.Instance> var1) {
         this.field_192506_b.add(☃);
      }

      public void func_192501_b(ICriterionTrigger.Listener<KilledTrigger.Instance> var1) {
         this.field_192506_b.remove(☃);
      }

      public void func_192503_a(EntityPlayerMP var1, Entity var2, DamageSource var3) {
         List<ICriterionTrigger.Listener<KilledTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<KilledTrigger.Instance> ☃x : this.field_192506_b) {
            if (☃x.func_192158_a().func_192270_a(☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<KilledTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<KilledTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192505_a);
            }
         }
      }
   }
}
