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
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.WorldServer;

public class PlacedBlockTrigger implements ICriterionTrigger<PlacedBlockTrigger.Instance> {
   private static final ResourceLocation field_193174_a = new ResourceLocation("placed_block");
   private final Map<PlayerAdvancements, PlacedBlockTrigger.Listeners> field_193175_b = Maps.<PlayerAdvancements, PlacedBlockTrigger.Listeners>newHashMap();

   @Override
   public ResourceLocation func_192163_a() {
      return field_193174_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> var2) {
      PlacedBlockTrigger.Listeners ☃ = (PlacedBlockTrigger.Listeners)this.field_193175_b.get(☃);
      if (☃ == null) {
         ☃ = new PlacedBlockTrigger.Listeners(☃);
         this.field_193175_b.put(☃, ☃);
      }

      ☃.func_193490_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> var2) {
      PlacedBlockTrigger.Listeners ☃ = (PlacedBlockTrigger.Listeners)this.field_193175_b.get(☃);
      if (☃ != null) {
         ☃.func_193487_b(☃);
         if (☃.func_193488_a()) {
            this.field_193175_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_193175_b.remove(☃);
   }

   public PlacedBlockTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
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

      LocationPredicate ☃ = LocationPredicate.func_193454_a(☃.get("location"));
      ItemPredicate ☃x = ItemPredicate.func_192492_a(☃.get("item"));
      return new PlacedBlockTrigger.Instance(☃, ☃, ☃, ☃x);
   }

   public void func_193173_a(EntityPlayerMP var1, BlockPos var2, ItemStack var3) {
      IBlockState ☃ = ☃.field_70170_p.func_180495_p(☃);
      PlacedBlockTrigger.Listeners ☃x = (PlacedBlockTrigger.Listeners)this.field_193175_b.get(☃.func_192039_O());
      if (☃x != null) {
         ☃x.func_193489_a(☃, ☃, ☃.func_71121_q(), ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final Block field_193211_a;
      private final Map<IProperty<?>, Object> field_193212_b;
      private final LocationPredicate field_193213_c;
      private final ItemPredicate field_193214_d;

      public Instance(@Nullable Block var1, @Nullable Map<IProperty<?>, Object> var2, LocationPredicate var3, ItemPredicate var4) {
         super(PlacedBlockTrigger.field_193174_a);
         this.field_193211_a = ☃;
         this.field_193212_b = ☃;
         this.field_193213_c = ☃;
         this.field_193214_d = ☃;
      }

      public static PlacedBlockTrigger.Instance func_203934_a(Block var0) {
         return new PlacedBlockTrigger.Instance(☃, null, LocationPredicate.field_193455_a, ItemPredicate.field_192495_a);
      }

      public boolean func_193210_a(IBlockState var1, BlockPos var2, WorldServer var3, ItemStack var4) {
         if (this.field_193211_a != null && ☃.func_177230_c() != this.field_193211_a) {
            return false;
         } else {
            if (this.field_193212_b != null) {
               for(Entry<IProperty<?>, Object> ☃ : this.field_193212_b.entrySet()) {
                  if (☃.func_177229_b((IProperty)☃.getKey()) != ☃.getValue()) {
                     return false;
                  }
               }
            }

            if (!this.field_193213_c.func_193453_a(☃, (float)☃.func_177958_n(), (float)☃.func_177956_o(), (float)☃.func_177952_p())) {
               return false;
            } else {
               return this.field_193214_d.func_192493_a(☃);
            }
         }
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         if (this.field_193211_a != null) {
            ☃.addProperty("block", IRegistry.field_212618_g.func_177774_c(this.field_193211_a).toString());
         }

         if (this.field_193212_b != null) {
            JsonObject ☃ = new JsonObject();

            for(Entry<IProperty<?>, Object> ☃x : this.field_193212_b.entrySet()) {
               ☃.addProperty(((IProperty)☃x.getKey()).func_177701_a(), Util.func_200269_a((IProperty)☃x.getKey(), ☃x.getValue()));
            }

            ☃.add("state", ☃);
         }

         ☃.add("location", this.field_193213_c.func_204009_a());
         ☃.add("item", this.field_193214_d.func_200319_a());
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_193491_a;
      private final Set<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>> field_193492_b = Sets.<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_193491_a = ☃;
      }

      public boolean func_193488_a() {
         return this.field_193492_b.isEmpty();
      }

      public void func_193490_a(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> var1) {
         this.field_193492_b.add(☃);
      }

      public void func_193487_b(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> var1) {
         this.field_193492_b.remove(☃);
      }

      public void func_193489_a(IBlockState var1, BlockPos var2, WorldServer var3, ItemStack var4) {
         List<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> ☃x : this.field_193492_b) {
            if (☃x.func_192158_a().func_193210_a(☃, ☃, ☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<PlacedBlockTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<PlacedBlockTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_193491_a);
            }
         }
      }
   }
}
