package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class TagEntry extends LootPoolSingletonContainer {
   final Tag<Item> tag;
   final boolean expand;

   TagEntry(Tag<Item> var1, boolean var2, int var3, int var4, LootItemCondition[] var5, LootItemFunction[] var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.tag = â˜ƒ;
      this.expand = â˜ƒ;
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntries.TAG;
   }

   @Override
   public void createItemStack(Consumer<ItemStack> var1, LootContext var2) {
      this.tag.getValues().forEach(var1x -> â˜ƒ.accept(new ItemStack(var1x)));
   }

   private boolean expandTag(LootContext var1, Consumer<LootPoolEntry> var2) {
      if (!this.canRun(â˜ƒ)) {
         return false;
      } else {
         for(final Item â˜ƒ : this.tag.getValues()) {
            â˜ƒ.accept(new LootPoolSingletonContainer.EntryBase() {
               @Override
               public void createItemStack(Consumer<ItemStack> var1, LootContext var2) {
                  â˜ƒ.accept(new ItemStack(â˜ƒ));
               }
            });
         }

         return true;
      }
   }

   @Override
   public boolean expand(LootContext var1, Consumer<LootPoolEntry> var2) {
      return this.expand ? this.expandTag(â˜ƒ, â˜ƒ) : super.expand(â˜ƒ, â˜ƒ);
   }

   public static LootPoolSingletonContainer.Builder<?> tagContents(Tag<Item> var0) {
      return simpleBuilder((var1, var2, var3, var4) -> new TagEntry(â˜ƒ, false, var1, var2, var3, var4));
   }

   public static LootPoolSingletonContainer.Builder<?> expandTag(Tag<Item> var0) {
      return simpleBuilder((var1, var2, var3, var4) -> new TagEntry(â˜ƒ, true, var1, var2, var3, var4));
   }

   public static class Serializer extends LootPoolSingletonContainer.Serializer<TagEntry> {
      public void serializeCustom(JsonObject var1, TagEntry var2, JsonSerializationContext var3) {
         super.serializeCustom(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty(
            "name",
            SerializationTags.getInstance().getIdOrThrow(Registry.ITEM_REGISTRY, â˜ƒ.tag, () -> new IllegalStateException("Unknown item tag")).toString()
         );
         â˜ƒ.addProperty("expand", â˜ƒ.expand);
      }

      protected TagEntry deserialize(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, LootItemCondition[] var5, LootItemFunction[] var6) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "name"));
         Tag<Item> â˜ƒx = SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, â˜ƒ, var0 -> new JsonParseException("Can't find tag: " + var0));
         boolean â˜ƒxx = GsonHelper.getAsBoolean(â˜ƒ, "expand");
         return new TagEntry(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
