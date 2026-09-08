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
import net.minecraft.world.WorldServer;

public class NetherTravelTrigger implements ICriterionTrigger<NetherTravelTrigger.Instance> {
   private static final ResourceLocation field_193169_a = new ResourceLocation("nether_travel");
   private final Map<PlayerAdvancements, NetherTravelTrigger.Listeners> field_193170_b = Maps.<PlayerAdvancements, NetherTravelTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_193169_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<NetherTravelTrigger.Instance> var2) {
      NetherTravelTrigger.Listeners ☃ = (NetherTravelTrigger.Listeners)this.field_193170_b.get(☃);
      if (☃ == null) {
         ☃ = new NetherTravelTrigger.Listeners(☃);
         this.field_193170_b.put(☃, ☃);
      }

      ☃.func_193484_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<NetherTravelTrigger.Instance> var2) {
      NetherTravelTrigger.Listeners ☃ = (NetherTravelTrigger.Listeners)this.field_193170_b.get(☃);
      if (☃ != null) {
         ☃.func_193481_b(☃);
         if (☃.func_193482_a()) {
            this.field_193170_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193170_b.remove(☃);
   }

   public NetherTravelTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      LocationPredicate ☃ = LocationPredicate.func_193454_a(☃.get("entered"));
      LocationPredicate ☃x = LocationPredicate.func_193454_a(☃.get("exited"));
      DistancePredicate ☃xx = DistancePredicate.func_193421_a(☃.get("distance"));
      return new NetherTravelTrigger.Instance(☃, ☃x, ☃xx);
   }

   public void func_193168_a(EntityPlayerMP var1, Vec3d var2) {
      NetherTravelTrigger.Listeners ☃ = (NetherTravelTrigger.Listeners)this.field_193170_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193483_a(☃.func_71121_q(), ☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final LocationPredicate field_193207_a;
      private final LocationPredicate field_193208_b;
      private final DistancePredicate field_193209_c;

      public Instance(LocationPredicate var1, LocationPredicate var2, DistancePredicate var3) {
         super(NetherTravelTrigger.field_193169_a);
         this.field_193207_a = ☃;
         this.field_193208_b = ☃;
         this.field_193209_c = ☃;
      }

      public static NetherTravelTrigger.Instance func_203933_a(DistancePredicate var0) {
         return new NetherTravelTrigger.Instance(LocationPredicate.field_193455_a, LocationPredicate.field_193455_a, ☃);
      }

      public boolean func_193206_a(WorldServer var1, Vec3d var2, double var3, double var5, double var7) {
         if (!this.field_193207_a.func_193452_a(☃, ☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c)) {
            return false;
         } else if (!this.field_193208_b.func_193452_a(☃, ☃, ☃, ☃)) {
            return false;
         } else {
            return this.field_193209_c.func_193422_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, ☃, ☃, ☃);
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("entered", this.field_193207_a.func_204009_a());
         ☃.add("exited", this.field_193208_b.func_204009_a());
         ☃.add("distance", this.field_193209_c.func_203994_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193485_a;
      private final Set<ICriterionTrigger.Listener<NetherTravelTrigger.Instance>> field_193486_b = Sets.<ICriterionTrigger.Listener<NetherTravelTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193485_a = ☃;
      }

      public boolean func_193482_a() {
         return this.field_193486_b.isEmpty();
      }

      public void func_193484_a(ICriterionTrigger.Listener<NetherTravelTrigger.Instance> var1) {
         this.field_193486_b.add(☃);
      }

      public void func_193481_b(ICriterionTrigger.Listener<NetherTravelTrigger.Instance> var1) {
         this.field_193486_b.remove(☃);
      }

      public void func_193483_a(WorldServer var1, Vec3d var2, double var3, double var5, double var7) {
         List<ICriterionTrigger.Listener<NetherTravelTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<NetherTravelTrigger.Instance> ☃x : this.field_193486_b) {
            if (☃x.func_192158_a().func_193206_a(☃, ☃, ☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<NetherTravelTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<NetherTravelTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193485_a);
            }
         }
      }
   }
}
