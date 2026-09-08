package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetBannerPatternFunction extends LootItemConditionalFunction {
   final List<Pair<BannerPattern, DyeColor>> patterns;
   final boolean append;

   SetBannerPatternFunction(LootItemCondition[] var1, List<Pair<BannerPattern, DyeColor>> var2, boolean var3) {
      super(â˜ƒ);
      this.patterns = â˜ƒ;
      this.append = â˜ƒ;
   }

   @Override
   protected ItemStack run(ItemStack var1, LootContext var2) {
      CompoundTag â˜ƒx = â˜ƒ.getOrCreateTagElement("BlockEntityTag");
      BannerPattern.Builder â˜ƒxx = new BannerPattern.Builder();
      this.patterns.forEach(â˜ƒxx::addPattern);
      ListTag â˜ƒxxx = â˜ƒxx.toListTag();
      ListTag â˜ƒ;
      if (this.append) {
         â˜ƒ = â˜ƒx.getList("Patterns", 10).copy();
         â˜ƒ.addAll(â˜ƒxxx);
      } else {
         â˜ƒ = â˜ƒxxx;
      }

      â˜ƒx.put("Patterns", â˜ƒ);
      return â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.SET_BANNER_PATTERN;
   }

   public static SetBannerPatternFunction.Builder setBannerPattern(boolean var0) {
      return new SetBannerPatternFunction.Builder(â˜ƒ);
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetBannerPatternFunction.Builder> {
      private final ImmutableList.Builder<Pair<BannerPattern, DyeColor>> patterns = ImmutableList.builder();
      private final boolean append;

      Builder(boolean var1) {
         this.append = â˜ƒ;
      }

      protected SetBannerPatternFunction.Builder getThis() {
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new SetBannerPatternFunction(this.getConditions(), this.patterns.build(), this.append);
      }

      public SetBannerPatternFunction.Builder addPattern(BannerPattern var1, DyeColor var2) {
         this.patterns.add(Pair.of(â˜ƒ, â˜ƒ));
         return this;
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetBannerPatternFunction> {
      public void serialize(JsonObject var1, SetBannerPatternFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         JsonArray â˜ƒ = new JsonArray();
         â˜ƒ.patterns.forEach(var1x -> {
            JsonObject â˜ƒ = new JsonObject();
            â˜ƒ.addProperty("pattern", ((BannerPattern)var1x.getFirst()).getFilename());
            â˜ƒ.addProperty("color", ((DyeColor)var1x.getSecond()).getName());
            â˜ƒ.add(â˜ƒ);
         });
         â˜ƒ.add("patterns", â˜ƒ);
         â˜ƒ.addProperty("append", â˜ƒ.append);
      }

      public SetBannerPatternFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         ImmutableList.Builder<Pair<BannerPattern, DyeColor>> â˜ƒ = ImmutableList.builder();
         JsonArray â˜ƒx = GsonHelper.getAsJsonArray(â˜ƒ, "patterns");

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            JsonObject â˜ƒxxx = GsonHelper.convertToJsonObject(â˜ƒx.get(â˜ƒxx), "pattern[" + â˜ƒxx + "]");
            String â˜ƒxxxx = GsonHelper.getAsString(â˜ƒxxx, "pattern");
            BannerPattern â˜ƒxxxxx = BannerPattern.byFilename(â˜ƒxxxx);
            if (â˜ƒxxxxx == null) {
               throw new JsonSyntaxException("Unknown pattern: " + â˜ƒxxxx);
            }

            String â˜ƒxxx = GsonHelper.getAsString(â˜ƒxxx, "color");
            DyeColor â˜ƒxxxx = DyeColor.byName(â˜ƒxxx, null);
            if (â˜ƒxxxx == null) {
               throw new JsonSyntaxException("Unknown color: " + â˜ƒxxx);
            }

            â˜ƒ.add(Pair.of(â˜ƒxxxxx, â˜ƒxxxx));
         }

         boolean â˜ƒxx = GsonHelper.getAsBoolean(â˜ƒ, "append");
         return new SetBannerPatternFunction(â˜ƒ, â˜ƒ.build(), â˜ƒxx);
      }
   }
}
