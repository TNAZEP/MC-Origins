package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.IRegistry;

public class EnterBlockTrigger implements ICriterionTrigger<EnterBlockTrigger.Instance> {
   private static final ResourceLocation field_192196_a = new ResourceLocation("enter_block");
   private final Map<PlayerAdvancements, EnterBlockTrigger.Listeners> field_192197_b = Maps.<PlayerAdvancements, EnterBlockTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_192196_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<EnterBlockTrigger.Instance> var2) {
      EnterBlockTrigger.Listeners ☃ = (EnterBlockTrigger.Listeners)this.field_192197_b.get(☃);
      if (☃ == null) {
         ☃ = new EnterBlockTrigger.Listeners(☃);
         this.field_192197_b.put(☃, ☃);
      }

      ☃.func_192472_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<EnterBlockTrigger.Instance> var2) {
      EnterBlockTrigger.Listeners ☃ = (EnterBlockTrigger.Listeners)this.field_192197_b.get(☃);
      if (☃ != null) {
         ☃.func_192469_b(☃);
         if (☃.func_192470_a()) {
            this.field_192197_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192197_b.remove(☃);
   }

   public EnterBlockTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      Block ☃ = null;
      if (☃.has("block")) {
         ResourceLocation ☃x = new ResourceLocation(JsonUtils.func_151200_h(☃, "block"));
         if (!IRegistry.field_212618_g.func_212607_c(☃x)) {
            throw new JsonSyntaxException("Unknown block type '" + ☃x + "'");
         }

         ☃ = IRegistry.field_212618_g.func_82594_a(☃x);
      }

      Map<IProperty<?>, Object> ☃ = null;
      if (☃.has("state")) {
         if (☃ == null) {
            throw new JsonSyntaxException("Can't define block state without a specific block type");
         }

         StateContainer<Block, IBlockState> ☃x = ☃.func_176194_O();

         for(Entry<String, JsonElement> ☃xx : JsonUtils.func_152754_s(☃, "state").entrySet()) {
            IProperty<?> ☃xxx = ☃x.func_185920_a((String)☃xx.getKey());
            if (☃xxx == null) {
               throw new JsonSyntaxException(
                  "Unknown block state property '" + (String)☃xx.getKey() + "' for block '" + IRegistry.field_212618_g.func_177774_c(☃) + "'"
               );
            }

            String ☃xxx = JsonUtils.func_151206_a((JsonElement)☃xx.getValue(), (String)☃xx.getKey());
            Optional<?> ☃xxxx = ☃xxx.func_185929_b(☃xxx);
            if (!☃xxxx.isPresent()) {
               throw new JsonSyntaxException(
                  "Invalid block state value '"
                     + ☃xxx
                     + "' for property '"
                     + (String)☃xx.getKey()
                     + "' on block '"
                     + IRegistry.field_212618_g.func_177774_c(☃)
                     + "'"
               );
            }

            if (☃ == null) {
               ☃ = Maps.<IProperty<?>, Object>newHashMap();
            }

            ☃.put(☃xxx, ☃xxxx.get());
         }
      }

      return new EnterBlockTrigger.Instance(☃, ☃);
   }

   public void func_192193_a(EntityPlayerMP var1, IBlockState var2) {
      EnterBlockTrigger.Listeners ☃ = (EnterBlockTrigger.Listeners)this.field_192197_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_192471_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final Block field_192261_a;
      private final Map<IProperty<?>, Object> field_192262_b;

      public Instance(@Nullable Block var1, @Nullable Map<IProperty<?>, Object> var2) {
         super(EnterBlockTrigger.field_192196_a);
         this.field_192261_a = ☃;
         this.field_192262_b = ☃;
      }

      public static EnterBlockTrigger.Instance func_203920_a(Block var0) {
         return new EnterBlockTrigger.Instance(☃, null);
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         if (this.field_192261_a != null) {
            ☃.addProperty("block", IRegistry.field_212618_g.func_177774_c(this.field_192261_a).toString());
            if (this.field_192262_b != null && !this.field_192262_b.isEmpty()) {
               JsonObject ☃x = new JsonObject();

               for(Entry<IProperty<?>, ?> ☃xx : this.field_192262_b.entrySet()) {
                  ☃x.addProperty(((IProperty)☃xx.getKey()).func_177701_a(), Util.func_200269_a((IProperty)☃xx.getKey(), ☃xx.getValue()));
               }

               ☃.add("state", ☃x);
            }
         }

         return ☃;
      }

      public boolean func_192260_a(IBlockState var1) {
         if (this.field_192261_a != null && ☃.func_177230_c() != this.field_192261_a) {
            return false;
         } else {
            if (this.field_192262_b != null) {
               for(Entry<IProperty<?>, Object> ☃ : this.field_192262_b.entrySet()) {
                  if (☃.func_177229_b((IProperty)☃.getKey()) != ☃.getValue()) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192473_a;
      private final Set<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>> field_192474_b = Sets.<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192473_a = ☃;
      }

      public boolean func_192470_a() {
         return this.field_192474_b.isEmpty();
      }

      public void func_192472_a(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> var1) {
         this.field_192474_b.add(☃);
      }

      public void func_192469_b(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> var1) {
         this.field_192474_b.remove(☃);
      }

      public void func_192471_a(IBlockState var1) {
         List<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> ☃x : this.field_192474_b) {
            if (☃x.func_192158_a().func_192260_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<EnterBlockTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<EnterBlockTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192473_a);
            }
         }
      }
   }
}
