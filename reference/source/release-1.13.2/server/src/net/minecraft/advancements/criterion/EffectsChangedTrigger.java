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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class EffectsChangedTrigger implements ICriterionTrigger<EffectsChangedTrigger.Instance> {
   private static final ResourceLocation field_193154_a = new ResourceLocation("effects_changed");
   private final Map<PlayerAdvancements, EffectsChangedTrigger.Listeners> field_193155_b = Maps.<PlayerAdvancements, EffectsChangedTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_193154_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> var2) {
      EffectsChangedTrigger.Listeners ☃ = (EffectsChangedTrigger.Listeners)this.field_193155_b.get(☃);
      if (☃ == null) {
         ☃ = new EffectsChangedTrigger.Listeners(☃);
         this.field_193155_b.put(☃, ☃);
      }

      ☃.func_193431_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> var2) {
      EffectsChangedTrigger.Listeners ☃ = (EffectsChangedTrigger.Listeners)this.field_193155_b.get(☃);
      if (☃ != null) {
         ☃.func_193429_b(☃);
         if (☃.func_193430_a()) {
            this.field_193155_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193155_b.remove(☃);
   }

   public EffectsChangedTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      MobEffectsPredicate ☃ = MobEffectsPredicate.func_193471_a(☃.get("effects"));
      return new EffectsChangedTrigger.Instance(☃);
   }

   public void func_193153_a(EntityPlayerMP var1) {
      EffectsChangedTrigger.Listeners ☃ = (EffectsChangedTrigger.Listeners)this.field_193155_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193432_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final MobEffectsPredicate field_193196_a;

      public Instance(MobEffectsPredicate var1) {
         super(EffectsChangedTrigger.field_193154_a);
         this.field_193196_a = ☃;
      }

      public static EffectsChangedTrigger.Instance func_203917_a(MobEffectsPredicate var0) {
         return new EffectsChangedTrigger.Instance(☃);
      }

      public boolean func_193195_a(EntityPlayerMP var1) {
         return this.field_193196_a.func_193472_a(☃);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("effects", this.field_193196_a.func_204013_b());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193433_a;
      private final Set<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>> field_193434_b = Sets.<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193433_a = ☃;
      }

      public boolean func_193430_a() {
         return this.field_193434_b.isEmpty();
      }

      public void func_193431_a(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> var1) {
         this.field_193434_b.add(☃);
      }

      public void func_193429_b(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> var1) {
         this.field_193434_b.remove(☃);
      }

      public void func_193432_a(EntityPlayerMP var1) {
         List<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> ☃x : this.field_193434_b) {
            if (☃x.func_192158_a().func_193195_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<EffectsChangedTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<EffectsChangedTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193433_a);
            }
         }
      }
   }
}
