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
import javax.annotation.Nullable;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;

public class ChangeDimensionTrigger implements ICriterionTrigger<ChangeDimensionTrigger.Instance> {
   private static final ResourceLocation field_193144_a = new ResourceLocation("changed_dimension");
   private final Map<PlayerAdvancements, ChangeDimensionTrigger.Listeners> field_193145_b = Maps.<PlayerAdvancements, ChangeDimensionTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_193144_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> var2) {
      ChangeDimensionTrigger.Listeners ☃ = (ChangeDimensionTrigger.Listeners)this.field_193145_b.get(☃);
      if (☃ == null) {
         ☃ = new ChangeDimensionTrigger.Listeners(☃);
         this.field_193145_b.put(☃, ☃);
      }

      ☃.func_193233_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> var2) {
      ChangeDimensionTrigger.Listeners ☃ = (ChangeDimensionTrigger.Listeners)this.field_193145_b.get(☃);
      if (☃ != null) {
         ☃.func_193231_b(☃);
         if (☃.func_193232_a()) {
            this.field_193145_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193145_b.remove(☃);
   }

   public ChangeDimensionTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      DimensionType ☃ = ☃.has("from") ? DimensionType.func_193417_a(new ResourceLocation(JsonUtils.func_151200_h(☃, "from"))) : null;
      DimensionType ☃x = ☃.has("to") ? DimensionType.func_193417_a(new ResourceLocation(JsonUtils.func_151200_h(☃, "to"))) : null;
      return new ChangeDimensionTrigger.Instance(☃, ☃x);
   }

   public void func_193143_a(EntityPlayerMP var1, DimensionType var2, DimensionType var3) {
      ChangeDimensionTrigger.Listeners ☃ = (ChangeDimensionTrigger.Listeners)this.field_193145_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193234_a(☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      @Nullable
      private final DimensionType field_193191_a;
      @Nullable
      private final DimensionType field_193192_b;

      public Instance(@Nullable DimensionType var1, @Nullable DimensionType var2) {
         super(ChangeDimensionTrigger.field_193144_a);
         this.field_193191_a = ☃;
         this.field_193192_b = ☃;
      }

      public static ChangeDimensionTrigger.Instance func_203911_a(DimensionType var0) {
         return new ChangeDimensionTrigger.Instance(null, ☃);
      }

      public boolean func_193190_a(DimensionType var1, DimensionType var2) {
         if (this.field_193191_a != null && this.field_193191_a != ☃) {
            return false;
         } else {
            return this.field_193192_b == null || this.field_193192_b == ☃;
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         if (this.field_193191_a != null) {
            ☃.addProperty("from", DimensionType.func_212678_a(this.field_193191_a).toString());
         }

         if (this.field_193192_b != null) {
            ☃.addProperty("to", DimensionType.func_212678_a(this.field_193192_b).toString());
         }

         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193235_a;
      private final Set<ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance>> field_193236_b = Sets.<ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193235_a = ☃;
      }

      public boolean func_193232_a() {
         return this.field_193236_b.isEmpty();
      }

      public void func_193233_a(ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> var1) {
         this.field_193236_b.add(☃);
      }

      public void func_193231_b(ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> var1) {
         this.field_193236_b.remove(☃);
      }

      public void func_193234_a(DimensionType var1, DimensionType var2) {
         List<ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> ☃x : this.field_193236_b) {
            if (☃x.func_192158_a().func_193190_a(☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<ChangeDimensionTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193235_a);
            }
         }
      }
   }
}
