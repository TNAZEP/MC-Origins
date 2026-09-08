package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class UsedEnderEyeTrigger implements ICriterionTrigger<UsedEnderEyeTrigger.Instance> {
   private static final ResourceLocation field_192242_a = new ResourceLocation("used_ender_eye");
   private final Map<PlayerAdvancements, UsedEnderEyeTrigger.Listeners> field_192243_b = Maps.<PlayerAdvancements, UsedEnderEyeTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_192242_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> var2) {
      UsedEnderEyeTrigger.Listeners ☃ = (UsedEnderEyeTrigger.Listeners)this.field_192243_b.get(☃);
      if (☃ == null) {
         ☃ = new UsedEnderEyeTrigger.Listeners(☃);
         this.field_192243_b.put(☃, ☃);
      }

      ☃.func_192546_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> var2) {
      UsedEnderEyeTrigger.Listeners ☃ = (UsedEnderEyeTrigger.Listeners)this.field_192243_b.get(☃);
      if (☃ != null) {
         ☃.func_192544_b(☃);
         if (☃.func_192545_a()) {
            this.field_192243_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192243_b.remove(☃);
   }

   public UsedEnderEyeTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      MinMaxBounds.FloatBound ☃ = MinMaxBounds.FloatBound.func_211356_a(☃.get("distance"));
      return new UsedEnderEyeTrigger.Instance(☃);
   }

   public void func_192239_a(EntityPlayerMP var1, BlockPos var2) {
      UsedEnderEyeTrigger.Listeners ☃ = (UsedEnderEyeTrigger.Listeners)this.field_192243_b.get(☃.func_192039_O());
      if (☃ != null) {
         double ☃x = ☃.field_70165_t - (double)☃.func_177958_n();
         double ☃xx = ☃.field_70161_v - (double)☃.func_177952_p();
         ☃.func_192543_a(☃x * ☃x + ☃xx * ☃xx);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final MinMaxBounds.FloatBound field_192289_a;

      public Instance(MinMaxBounds.FloatBound var1) {
         super(UsedEnderEyeTrigger.field_192242_a);
         this.field_192289_a = ☃;
      }

      public boolean func_192288_a(double var1) {
         return this.field_192289_a.func_211351_a(☃);
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192547_a;
      private final Set<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>> field_192548_b = Sets.<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192547_a = ☃;
      }

      public boolean func_192545_a() {
         return this.field_192548_b.isEmpty();
      }

      public void func_192546_a(ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> var1) {
         this.field_192548_b.add(☃);
      }

      public void func_192544_b(ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> var1) {
         this.field_192548_b.remove(☃);
      }

      public void func_192543_a(double var1) {
         List<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> ☃x : this.field_192548_b) {
            if (☃x.func_192158_a().func_192288_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<UsedEnderEyeTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192547_a);
            }
         }
      }
   }
}
