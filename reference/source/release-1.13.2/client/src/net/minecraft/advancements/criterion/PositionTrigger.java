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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;

public class PositionTrigger implements ICriterionTrigger<PositionTrigger.Instance> {
   private final ResourceLocation field_192217_a;
   private final Map<PlayerAdvancements, PositionTrigger.Listeners> field_192218_b = Maps.<PlayerAdvancements, PositionTrigger.Listeners>newHashMap();

   public PositionTrigger(ResourceLocation var1) {
      this.field_192217_a = ☃;
   }

   @Override
   public ResourceLocation func_192163_a() {
      return this.field_192217_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<PositionTrigger.Instance> var2) {
      PositionTrigger.Listeners ☃ = (PositionTrigger.Listeners)this.field_192218_b.get(☃);
      if (☃ == null) {
         ☃ = new PositionTrigger.Listeners(☃);
         this.field_192218_b.put(☃, ☃);
      }

      ☃.func_192510_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<PositionTrigger.Instance> var2) {
      PositionTrigger.Listeners ☃ = (PositionTrigger.Listeners)this.field_192218_b.get(☃);
      if (☃ != null) {
         ☃.func_192507_b(☃);
         if (☃.func_192508_a()) {
            this.field_192218_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192218_b.remove(☃);
   }

   public PositionTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      LocationPredicate ☃ = LocationPredicate.func_193454_a(☃);
      return new PositionTrigger.Instance(this.field_192217_a, ☃);
   }

   public void func_192215_a(EntityPlayerMP var1) {
      PositionTrigger.Listeners ☃ = (PositionTrigger.Listeners)this.field_192218_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193462_a(☃.func_71121_q(), ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final LocationPredicate field_193205_a;

      public Instance(ResourceLocation var1, LocationPredicate var2) {
         super(☃);
         this.field_193205_a = ☃;
      }

      public static PositionTrigger.Instance func_203932_a(LocationPredicate var0) {
         return new PositionTrigger.Instance(CriteriaTriggers.field_192135_o.field_192217_a, ☃);
      }

      public static PositionTrigger.Instance func_203931_c() {
         return new PositionTrigger.Instance(CriteriaTriggers.field_192136_p.field_192217_a, LocationPredicate.field_193455_a);
      }

      public boolean func_193204_a(WorldServer var1, double var2, double var4, double var6) {
         return this.field_193205_a.func_193452_a(☃, ☃, ☃, ☃);
      }

      @Override
      public JsonElement func_200288_b() {
         return this.field_193205_a.func_204009_a();
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192511_a;
      private final Set<ICriterionTrigger.Listener<PositionTrigger.Instance>> field_192512_b = Sets.<ICriterionTrigger.Listener<PositionTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192511_a = ☃;
      }

      public boolean func_192508_a() {
         return this.field_192512_b.isEmpty();
      }

      public void func_192510_a(ICriterionTrigger.Listener<PositionTrigger.Instance> var1) {
         this.field_192512_b.add(☃);
      }

      public void func_192507_b(ICriterionTrigger.Listener<PositionTrigger.Instance> var1) {
         this.field_192512_b.remove(☃);
      }

      public void func_193462_a(WorldServer var1, double var2, double var4, double var6) {
         List<ICriterionTrigger.Listener<PositionTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<PositionTrigger.Instance> ☃x : this.field_192512_b) {
            if (☃x.func_192158_a().func_193204_a(☃, ☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<PositionTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<PositionTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192511_a);
            }
         }
      }
   }
}
