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
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.ResourceLocation;

public class ConstructBeaconTrigger implements ICriterionTrigger<ConstructBeaconTrigger.Instance> {
   private static final ResourceLocation field_192181_a = new ResourceLocation("construct_beacon");
   private final Map<PlayerAdvancements, ConstructBeaconTrigger.Listeners> field_192182_b = Maps.<PlayerAdvancements, ConstructBeaconTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_192181_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> var2) {
      ConstructBeaconTrigger.Listeners ☃ = (ConstructBeaconTrigger.Listeners)this.field_192182_b.get(☃);
      if (☃ == null) {
         ☃ = new ConstructBeaconTrigger.Listeners(☃);
         this.field_192182_b.put(☃, ☃);
      }

      ☃.func_192355_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> var2) {
      ConstructBeaconTrigger.Listeners ☃ = (ConstructBeaconTrigger.Listeners)this.field_192182_b.get(☃);
      if (☃ != null) {
         ☃.func_192353_b(☃);
         if (☃.func_192354_a()) {
            this.field_192182_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192182_b.remove(☃);
   }

   public ConstructBeaconTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      MinMaxBounds.IntBound ☃ = MinMaxBounds.IntBound.func_211344_a(☃.get("level"));
      return new ConstructBeaconTrigger.Instance(☃);
   }

   public void func_192180_a(EntityPlayerMP var1, TileEntityBeacon var2) {
      ConstructBeaconTrigger.Listeners ☃ = (ConstructBeaconTrigger.Listeners)this.field_192182_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192352_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final MinMaxBounds.IntBound field_192253_a;

      public Instance(MinMaxBounds.IntBound var1) {
         super(ConstructBeaconTrigger.field_192181_a);
         this.field_192253_a = ☃;
      }

      public static ConstructBeaconTrigger.Instance func_203912_a(MinMaxBounds.IntBound var0) {
         return new ConstructBeaconTrigger.Instance(☃);
      }

      public boolean func_192252_a(TileEntityBeacon var1) {
         return this.field_192253_a.func_211339_d(☃.func_191979_s());
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("level", this.field_192253_a.func_200321_c());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192356_a;
      private final Set<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>> field_192357_b = Sets.<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192356_a = ☃;
      }

      public boolean func_192354_a() {
         return this.field_192357_b.isEmpty();
      }

      public void func_192355_a(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> var1) {
         this.field_192357_b.add(☃);
      }

      public void func_192353_b(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> var1) {
         this.field_192357_b.remove(☃);
      }

      public void func_192352_a(TileEntityBeacon var1) {
         List<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> ☃x : this.field_192357_b) {
            if (☃x.func_192158_a().func_192252_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<ConstructBeaconTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192356_a);
            }
         }
      }
   }
}
