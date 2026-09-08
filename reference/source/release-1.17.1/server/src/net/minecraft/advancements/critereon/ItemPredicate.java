package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;

public class ItemPredicate {
   public static final ItemPredicate ANY = new ItemPredicate();
   @Nullable
   private final Tag<Item> tag;
   @Nullable
   private final Set<Item> items;
   private final MinMaxBounds.Ints count;
   private final MinMaxBounds.Ints durability;
   private final EnchantmentPredicate[] enchantments;
   private final EnchantmentPredicate[] storedEnchantments;
   @Nullable
   private final Potion potion;
   private final NbtPredicate nbt;

   public ItemPredicate() {
      this.tag = null;
      this.items = null;
      this.potion = null;
      this.count = MinMaxBounds.Ints.ANY;
      this.durability = MinMaxBounds.Ints.ANY;
      this.enchantments = EnchantmentPredicate.NONE;
      this.storedEnchantments = EnchantmentPredicate.NONE;
      this.nbt = NbtPredicate.ANY;
   }

   public ItemPredicate(
      @Nullable Tag<Item> var1,
      @Nullable Set<Item> var2,
      MinMaxBounds.Ints var3,
      MinMaxBounds.Ints var4,
      EnchantmentPredicate[] var5,
      EnchantmentPredicate[] var6,
      @Nullable Potion var7,
      NbtPredicate var8
   ) {
      this.tag = â˜ƒ;
      this.items = â˜ƒ;
      this.count = â˜ƒ;
      this.durability = â˜ƒ;
      this.enchantments = â˜ƒ;
      this.storedEnchantments = â˜ƒ;
      this.potion = â˜ƒ;
      this.nbt = â˜ƒ;
   }

   public boolean matches(ItemStack var1) {
      if (this == ANY) {
         return true;
      } else if (this.tag != null && !â˜ƒ.is(this.tag)) {
         return false;
      } else if (this.items != null && !this.items.contains(â˜ƒ.getItem())) {
         return false;
      } else if (!this.count.matches(â˜ƒ.getCount())) {
         return false;
      } else if (!this.durability.isAny() && !â˜ƒ.isDamageableItem()) {
         return false;
      } else if (!this.durability.matches(â˜ƒ.getMaxDamage() - â˜ƒ.getDamageValue())) {
         return false;
      } else if (!this.nbt.matches(â˜ƒ)) {
         return false;
      } else {
         if (this.enchantments.length > 0) {
            Map<Enchantment, Integer> â˜ƒ = EnchantmentHelper.deserializeEnchantments(â˜ƒ.getEnchantmentTags());

            for(EnchantmentPredicate â˜ƒx : this.enchantments) {
               if (!â˜ƒx.containedIn(â˜ƒ)) {
                  return false;
               }
            }
         }

         if (this.storedEnchantments.length > 0) {
            Map<Enchantment, Integer> â˜ƒ = EnchantmentHelper.deserializeEnchantments(EnchantedBookItem.getEnchantments(â˜ƒ));

            for(EnchantmentPredicate â˜ƒx : this.storedEnchantments) {
               if (!â˜ƒx.containedIn(â˜ƒ)) {
                  return false;
               }
            }
         }

         Potion â˜ƒ = PotionUtils.getPotion(â˜ƒ);
         return this.potion == null || this.potion == â˜ƒ;
      }
   }

   public static ItemPredicate fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "item");
         MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("count"));
         MinMaxBounds.Ints â˜ƒxx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("durability"));
         if (â˜ƒ.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            NbtPredicate â˜ƒ = NbtPredicate.fromJson(â˜ƒ.get("nbt"));
            Set<Item> â˜ƒx = null;
            JsonArray â˜ƒxx = GsonHelper.getAsJsonArray(â˜ƒ, "items", null);
            if (â˜ƒxx != null) {
               ImmutableSet.Builder<Item> â˜ƒxxx = ImmutableSet.builder();

               for(JsonElement â˜ƒxxxx : â˜ƒxx) {
                  ResourceLocation â˜ƒxxxxx = new ResourceLocation(GsonHelper.convertToString(â˜ƒxxxx, "item"));
                  â˜ƒxxx.add((Item)Registry.ITEM.getOptional(â˜ƒxxxxx).orElseThrow(() -> new JsonSyntaxException("Unknown item id '" + â˜ƒ + "'")));
               }

               â˜ƒx = â˜ƒxxx.build();
            }

            Tag<Item> â˜ƒ = null;
            if (â˜ƒ.has("tag")) {
               ResourceLocation â˜ƒx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "tag"));
               â˜ƒ = SerializationTags.getInstance()
                  .getTagOrThrow(Registry.ITEM_REGISTRY, â˜ƒx, var0x -> new JsonSyntaxException("Unknown item tag '" + var0x + "'"));
            }

            Potion â˜ƒ = null;
            if (â˜ƒ.has("potion")) {
               ResourceLocation â˜ƒx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "potion"));
               â˜ƒ = (Potion)Registry.POTION.getOptional(â˜ƒx).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + â˜ƒ + "'"));
            }

            EnchantmentPredicate[] â˜ƒ = EnchantmentPredicate.fromJsonArray(â˜ƒ.get("enchantments"));
            EnchantmentPredicate[] â˜ƒx = EnchantmentPredicate.fromJsonArray(â˜ƒ.get("stored_enchantments"));
            return new ItemPredicate(â˜ƒ, â˜ƒx, â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
         }
      } else {
         return ANY;
      }
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (this.items != null) {
            JsonArray â˜ƒx = new JsonArray();

            for(Item â˜ƒxx : this.items) {
               â˜ƒx.add(Registry.ITEM.getKey(â˜ƒxx).toString());
            }

            â˜ƒ.add("items", â˜ƒx);
         }

         if (this.tag != null) {
            â˜ƒ.addProperty(
               "tag",
               SerializationTags.getInstance().getIdOrThrow(Registry.ITEM_REGISTRY, this.tag, () -> new IllegalStateException("Unknown item tag")).toString()
            );
         }

         â˜ƒ.add("count", this.count.serializeToJson());
         â˜ƒ.add("durability", this.durability.serializeToJson());
         â˜ƒ.add("nbt", this.nbt.serializeToJson());
         if (this.enchantments.length > 0) {
            JsonArray â˜ƒ = new JsonArray();

            for(EnchantmentPredicate â˜ƒx : this.enchantments) {
               â˜ƒ.add(â˜ƒx.serializeToJson());
            }

            â˜ƒ.add("enchantments", â˜ƒ);
         }

         if (this.storedEnchantments.length > 0) {
            JsonArray â˜ƒ = new JsonArray();

            for(EnchantmentPredicate â˜ƒx : this.storedEnchantments) {
               â˜ƒ.add(â˜ƒx.serializeToJson());
            }

            â˜ƒ.add("stored_enchantments", â˜ƒ);
         }

         if (this.potion != null) {
            â˜ƒ.addProperty("potion", Registry.POTION.getKey(this.potion).toString());
         }

         return â˜ƒ;
      }
   }

   public static ItemPredicate[] fromJsonArray(@Nullable JsonElement var0) {
      if (â˜ƒ != null && !â˜ƒ.isJsonNull()) {
         JsonArray â˜ƒ = GsonHelper.convertToJsonArray(â˜ƒ, "items");
         ItemPredicate[] â˜ƒx = new ItemPredicate[â˜ƒ.size()];

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.length; ++â˜ƒxx) {
            â˜ƒx[â˜ƒxx] = fromJson(â˜ƒ.get(â˜ƒxx));
         }

         return â˜ƒx;
      } else {
         return new ItemPredicate[0];
      }
   }

   public static class Builder {
      private final List<EnchantmentPredicate> enchantments = Lists.<EnchantmentPredicate>newArrayList();
      private final List<EnchantmentPredicate> storedEnchantments = Lists.<EnchantmentPredicate>newArrayList();
      @Nullable
      private Set<Item> items;
      @Nullable
      private Tag<Item> tag;
      private MinMaxBounds.Ints count = MinMaxBounds.Ints.ANY;
      private MinMaxBounds.Ints durability = MinMaxBounds.Ints.ANY;
      @Nullable
      private Potion potion;
      private NbtPredicate nbt = NbtPredicate.ANY;

      private Builder() {
      }

      public static ItemPredicate.Builder item() {
         return new ItemPredicate.Builder();
      }

      public ItemPredicate.Builder of(ItemLike... var1) {
         this.items = (Set)Stream.of(â˜ƒ).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
         return this;
      }

      public ItemPredicate.Builder of(Tag<Item> var1) {
         this.tag = â˜ƒ;
         return this;
      }

      public ItemPredicate.Builder withCount(MinMaxBounds.Ints var1) {
         this.count = â˜ƒ;
         return this;
      }

      public ItemPredicate.Builder hasDurability(MinMaxBounds.Ints var1) {
         this.durability = â˜ƒ;
         return this;
      }

      public ItemPredicate.Builder isPotion(Potion var1) {
         this.potion = â˜ƒ;
         return this;
      }

      public ItemPredicate.Builder hasNbt(CompoundTag var1) {
         this.nbt = new NbtPredicate(â˜ƒ);
         return this;
      }

      public ItemPredicate.Builder hasEnchantment(EnchantmentPredicate var1) {
         this.enchantments.add(â˜ƒ);
         return this;
      }

      public ItemPredicate.Builder hasStoredEnchantment(EnchantmentPredicate var1) {
         this.storedEnchantments.add(â˜ƒ);
         return this;
      }

      public ItemPredicate build() {
         return new ItemPredicate(
            this.tag,
            this.items,
            this.count,
            this.durability,
            (EnchantmentPredicate[])this.enchantments.toArray(EnchantmentPredicate.NONE),
            (EnchantmentPredicate[])this.storedEnchantments.toArray(EnchantmentPredicate.NONE),
            this.potion,
            this.nbt
         );
      }
   }
}
