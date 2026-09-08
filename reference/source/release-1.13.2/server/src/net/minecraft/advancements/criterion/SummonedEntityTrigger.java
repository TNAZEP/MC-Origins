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
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class SummonedEntityTrigger implements ICriterionTrigger<SummonedEntityTrigger.Instance> {
   private static final ResourceLocation field_192232_a = new ResourceLocation("summoned_entity");
   private final Map<PlayerAdvancements, SummonedEntityTrigger.Listeners> field_192233_b = Maps.<PlayerAdvancements, SummonedEntityTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_192232_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> var2) {
      SummonedEntityTrigger.Listeners ☃ = (SummonedEntityTrigger.Listeners)this.field_192233_b.get(☃);
      if (☃ == null) {
         ☃ = new SummonedEntityTrigger.Listeners(☃);
         this.field_192233_b.put(☃, ☃);
      }

      ☃.func_192534_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> var2) {
      SummonedEntityTrigger.Listeners ☃ = (SummonedEntityTrigger.Listeners)this.field_192233_b.get(☃);
      if (☃ != null) {
         ☃.func_192531_b(☃);
         if (☃.func_192532_a()) {
            this.field_192233_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192233_b.remove(☃);
   }

   public SummonedEntityTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      EntityPredicate ☃ = EntityPredicate.func_192481_a(☃.get("entity"));
      return new SummonedEntityTrigger.Instance(☃);
   }

   public void func_192229_a(EntityPlayerMP var1, Entity var2) {
      SummonedEntityTrigger.Listeners ☃ = (SummonedEntityTrigger.Listeners)this.field_192233_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192533_a(☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate field_192284_a;

      public Instance(EntityPredicate var1) {
         super(SummonedEntityTrigger.field_192232_a);
         this.field_192284_a = ☃;
      }

      public static SummonedEntityTrigger.Instance func_203937_a(EntityPredicate.Builder var0) {
         return new SummonedEntityTrigger.Instance(☃.func_204000_b());
      }

      public boolean func_192283_a(EntityPlayerMP var1, Entity var2) {
         return this.field_192284_a.func_192482_a(☃, ☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("entity", this.field_192284_a.func_204006_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192535_a;
      private final Set<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>> field_192536_b = Sets.<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192535_a = ☃;
      }

      public boolean func_192532_a() {
         return this.field_192536_b.isEmpty();
      }

      public void func_192534_a(ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> var1) {
         this.field_192536_b.add(☃);
      }

      public void func_192531_b(ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> var1) {
         this.field_192536_b.remove(☃);
      }

      public void func_192533_a(EntityPlayerMP var1, Entity var2) {
         List<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> ☃x : this.field_192536_b) {
            if (☃x.func_192158_a().func_192283_a(☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<SummonedEntityTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<SummonedEntityTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192535_a);
            }
         }
      }
   }
}
