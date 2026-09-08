package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class ItemPredicate {
   public static final ItemPredicate field_192495_a = new ItemPredicate();
   @Nullable
   private final Tag<Item> field_200018_b;
   @Nullable
   private final Item field_192496_b;
   private final MinMaxBounds.IntBound field_192498_d;
   private final MinMaxBounds.IntBound field_193444_e;
   private final EnchantmentPredicate[] field_192499_e;
   @Nullable
   private final PotionType field_192500_f;
   private final NBTPredicate field_193445_h;

   public ItemPredicate() {
      this.field_200018_b = null;
      this.field_192496_b = null;
      this.field_192500_f = null;
      this.field_192498_d = MinMaxBounds.IntBound.field_211347_e;
      this.field_193444_e = MinMaxBounds.IntBound.field_211347_e;
      this.field_192499_e = new EnchantmentPredicate[0];
      this.field_193445_h = NBTPredicate.field_193479_a;
   }

   public ItemPredicate(
      @Nullable Tag<Item> var1,
      @Nullable Item var2,
      MinMaxBounds.IntBound var3,
      MinMaxBounds.IntBound var4,
      EnchantmentPredicate[] var5,
      @Nullable PotionType var6,
      NBTPredicate var7
   ) {
      this.field_200018_b = ☃;
      this.field_192496_b = ☃;
      this.field_192498_d = ☃;
      this.field_193444_e = ☃;
      this.field_192499_e = ☃;
      this.field_192500_f = ☃;
      this.field_193445_h = ☃;
   }

   public boolean func_192493_a(ItemStack var1) {
      if (this.field_200018_b != null && !this.field_200018_b.func_199685_a_(☃.func_77973_b())) {
         return false;
      } else if (this.field_192496_b != null && ☃.func_77973_b() != this.field_192496_b) {
         return false;
      } else if (!this.field_192498_d.func_211339_d(☃.func_190916_E())) {
         return false;
      } else if (!this.field_193444_e.func_211335_c() && !☃.func_77984_f()) {
         return false;
      } else if (!this.field_193444_e.func_211339_d(☃.func_77958_k() - ☃.func_77952_i())) {
         return false;
      } else if (!this.field_193445_h.func_193478_a(☃)) {
         return false;
      } else {
         Map<Enchantment, Integer> ☃ = EnchantmentHelper.func_82781_a(☃);

         for(int ☃x = 0; ☃x < this.field_192499_e.length; ++☃x) {
            if (!this.field_192499_e[☃x].func_192463_a(☃)) {
               return false;
            }
         }

         PotionType ☃x = PotionUtils.func_185191_c(☃);
         return this.field_192500_f == null || this.field_192500_f == ☃x;
      }
   }

   public static ItemPredicate func_192492_a(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "item");
         MinMaxBounds.IntBound ☃x = MinMaxBounds.IntBound.func_211344_a(☃.get("count"));
         MinMaxBounds.IntBound ☃xx = MinMaxBounds.IntBound.func_211344_a(☃.get("durability"));
         if (☃.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            NBTPredicate ☃ = NBTPredicate.func_193476_a(☃.get("nbt"));
            Item ☃x = null;
            if (☃.has("item")) {
               ResourceLocation ☃xx = new ResourceLocation(JsonUtils.func_151200_h(☃, "item"));
               ☃x = IRegistry.field_212630_s.func_212608_b(☃xx);
               if (☃x == null) {
                  throw new JsonSyntaxException("Unknown item id '" + ☃xx + "'");
               }
            }

            Tag<Item> ☃ = null;
            if (☃.has("tag")) {
               ResourceLocation ☃x = new ResourceLocation(JsonUtils.func_151200_h(☃, "tag"));
               ☃ = ItemTags.func_199903_a().func_199910_a(☃x);
               if (☃ == null) {
                  throw new JsonSyntaxException("Unknown item tag '" + ☃x + "'");
               }
            }

            EnchantmentPredicate[] ☃ = EnchantmentPredicate.func_192465_b(☃.get("enchantments"));
            PotionType ☃x = null;
            if (☃.has("potion")) {
               ResourceLocation ☃xx = new ResourceLocation(JsonUtils.func_151200_h(☃, "potion"));
               if (!IRegistry.field_212621_j.func_212607_c(☃xx)) {
                  throw new JsonSyntaxException("Unknown potion '" + ☃xx + "'");
               }

               ☃x = IRegistry.field_212621_j.func_82594_a(☃xx);
            }

            return new ItemPredicate(☃, ☃x, ☃x, ☃xx, ☃, ☃x, ☃);
         }
      } else {
         return field_192495_a;
      }
   }

   public JsonElement func_200319_a() {
      if (this == field_192495_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject ☃ = new JsonObject();
         if (this.field_192496_b != null) {
            ☃.addProperty("item", IRegistry.field_212630_s.func_177774_c(this.field_192496_b).toString());
         }

         if (this.field_200018_b != null) {
            ☃.addProperty("tag", this.field_200018_b.func_199886_b().toString());
         }

         ☃.add("count", this.field_192498_d.func_200321_c());
         ☃.add("durability", this.field_193444_e.func_200321_c());
         ☃.add("nbt", this.field_193445_h.func_200322_a());
         if (this.field_192499_e.length > 0) {
            JsonArray ☃ = new JsonArray();

            for(EnchantmentPredicate ☃x : this.field_192499_e) {
               ☃.add(☃x.func_200306_a());
            }

            ☃.add("enchantments", ☃);
         }

         if (this.field_192500_f != null) {
            ☃.addProperty("potion", IRegistry.field_212621_j.func_177774_c(this.field_192500_f).toString());
         }

         return ☃;
      }
   }

   public static ItemPredicate[] func_192494_b(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         JsonArray ☃ = JsonUtils.func_151207_m(☃, "items");
         ItemPredicate[] ☃x = new ItemPredicate[☃.size()];

         for(int ☃xx = 0; ☃xx < ☃x.length; ++☃xx) {
            ☃x[☃xx] = func_192492_a(☃.get(☃xx));
         }

         return ☃x;
      } else {
         return new ItemPredicate[0];
      }
   }

   public static class Builder {
      private final List<EnchantmentPredicate> field_200312_a = Lists.<EnchantmentPredicate>newArrayList();
      @Nullable
      private Item field_200313_b;
      @Nullable
      private Tag<Item> field_200314_c;
      private MinMaxBounds.IntBound field_200315_d = MinMaxBounds.IntBound.field_211347_e;
      private MinMaxBounds.IntBound field_200316_e = MinMaxBounds.IntBound.field_211347_e;
      @Nullable
      private PotionType field_200317_f;
      private NBTPredicate field_200318_g = NBTPredicate.field_193479_a;

      private Builder() {
      }

      public static ItemPredicate.Builder func_200309_a() {
         return new ItemPredicate.Builder();
      }

      public ItemPredicate.Builder func_200308_a(IItemProvider var1) {
         this.field_200313_b = ☃.func_199767_j();
         return this;
      }

      public ItemPredicate.Builder func_200307_a(Tag<Item> var1) {
         this.field_200314_c = ☃;
         return this;
      }

      public ItemPredicate.Builder func_200311_a(MinMaxBounds.IntBound var1) {
         this.field_200315_d = ☃;
         return this;
      }

      public ItemPredicate func_200310_b() {
         return new ItemPredicate(
            this.field_200314_c,
            this.field_200313_b,
            this.field_200315_d,
            this.field_200316_e,
            (EnchantmentPredicate[])this.field_200312_a.toArray(new EnchantmentPredicate[0]),
            this.field_200317_f,
            this.field_200318_g
         );
      }
   }
}
