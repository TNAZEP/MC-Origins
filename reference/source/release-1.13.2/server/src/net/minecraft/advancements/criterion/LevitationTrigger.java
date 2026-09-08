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
import net.minecraft.util.math.Vec3d;

public class LevitationTrigger implements ICriterionTrigger<LevitationTrigger.Instance> {
   private static final ResourceLocation field_193164_a = new ResourceLocation("levitation");
   private final Map<PlayerAdvancements, LevitationTrigger.Listeners> field_193165_b = Maps.<PlayerAdvancements, LevitationTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_193164_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<LevitationTrigger.Instance> var2) {
      LevitationTrigger.Listeners ☃ = (LevitationTrigger.Listeners)this.field_193165_b.get(☃);
      if (☃ == null) {
         ☃ = new LevitationTrigger.Listeners(☃);
         this.field_193165_b.put(☃, ☃);
      }

      ☃.func_193449_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<LevitationTrigger.Instance> var2) {
      LevitationTrigger.Listeners ☃ = (LevitationTrigger.Listeners)this.field_193165_b.get(☃);
      if (☃ != null) {
         ☃.func_193446_b(☃);
         if (☃.func_193447_a()) {
            this.field_193165_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193165_b.remove(☃);
   }

   public LevitationTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      DistancePredicate ☃ = DistancePredicate.func_193421_a(☃.get("distance"));
      MinMaxBounds.IntBound ☃x = MinMaxBounds.IntBound.func_211344_a(☃.get("duration"));
      return new LevitationTrigger.Instance(☃, ☃x);
   }

   public void func_193162_a(EntityPlayerMP var1, Vec3d var2, int var3) {
      LevitationTrigger.Listeners ☃ = (LevitationTrigger.Listeners)this.field_193165_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193448_a(☃, ☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final DistancePredicate field_193202_a;
      private final MinMaxBounds.IntBound field_193203_b;

      public Instance(DistancePredicate var1, MinMaxBounds.IntBound var2) {
         super(LevitationTrigger.field_193164_a);
         this.field_193202_a = ☃;
         this.field_193203_b = ☃;
      }

      public static LevitationTrigger.Instance func_203930_a(DistancePredicate var0) {
         return new LevitationTrigger.Instance(☃, MinMaxBounds.IntBound.field_211347_e);
      }

      public boolean func_193201_a(EntityPlayerMP var1, Vec3d var2, int var3) {
         if (!this.field_193202_a.func_193422_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v)) {
            return false;
         } else {
            return this.field_193203_b.func_211339_d(☃);
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("distance", this.field_193202_a.func_203994_a());
         ☃.add("duration", this.field_193203_b.func_200321_c());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193450_a;
      private final Set<ICriterionTrigger.Listener<LevitationTrigger.Instance>> field_193451_b = Sets.<ICriterionTrigger.Listener<LevitationTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193450_a = ☃;
      }

      public boolean func_193447_a() {
         return this.field_193451_b.isEmpty();
      }

      public void func_193449_a(ICriterionTrigger.Listener<LevitationTrigger.Instance> var1) {
         this.field_193451_b.add(☃);
      }

      public void func_193446_b(ICriterionTrigger.Listener<LevitationTrigger.Instance> var1) {
         this.field_193451_b.remove(☃);
      }

      public void func_193448_a(EntityPlayerMP var1, Vec3d var2, int var3) {
         List<ICriterionTrigger.Listener<LevitationTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<LevitationTrigger.Instance> ☃x : this.field_193451_b) {
            if (☃x.func_192158_a().func_193201_a(☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<LevitationTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<LevitationTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193450_a);
            }
         }
      }
   }
}
