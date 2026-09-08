package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class TickTrigger implements ICriterionTrigger<TickTrigger.Instance> {
   public static final ResourceLocation field_193183_a = new ResourceLocation("tick");
   private final Map<PlayerAdvancements, TickTrigger.Listeners> field_193184_b = Maps.<PlayerAdvancements, TickTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_193183_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<TickTrigger.Instance> var2) {
      TickTrigger.Listeners ☃ = (TickTrigger.Listeners)this.field_193184_b.get(☃);
      if (☃ == null) {
         ☃ = new TickTrigger.Listeners(☃);
         this.field_193184_b.put(☃, ☃);
      }

      ☃.func_193502_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<TickTrigger.Instance> var2) {
      TickTrigger.Listeners ☃ = (TickTrigger.Listeners)this.field_193184_b.get(☃);
      if (☃ != null) {
         ☃.func_193500_b(☃);
         if (☃.func_193501_a()) {
            this.field_193184_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193184_b.remove(☃);
   }

   public TickTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      return new TickTrigger.Instance();
   }

   public void func_193182_a(EntityPlayerMP var1) {
      TickTrigger.Listeners ☃ = (TickTrigger.Listeners)this.field_193184_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193503_b();
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      public Instance() {
         super(TickTrigger.field_193183_a);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193504_a;
      private final Set<ICriterionTrigger.Listener<TickTrigger.Instance>> field_193505_b = Sets.<ICriterionTrigger.Listener<TickTrigger.Instance>>newHashSet();

      public Listeners(PlayerAdvancements var1) {
         this.field_193504_a = ☃;
      }

      public boolean func_193501_a() {
         return this.field_193505_b.isEmpty();
      }

      public void func_193502_a(ICriterionTrigger.Listener<TickTrigger.Instance> var1) {
         this.field_193505_b.add(☃);
      }

      public void func_193500_b(ICriterionTrigger.Listener<TickTrigger.Instance> var1) {
         this.field_193505_b.remove(☃);
      }

      public void func_193503_b() {
         for(ICriterionTrigger.Listener<TickTrigger.Instance> ☃ : Lists.newArrayList(this.field_193505_b)) {
            ☃.func_192159_a(this.field_193504_a);
         }
      }
   }
}
