package net.minecraft.network.chat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HoverEvent {
   static final Logger LOGGER = LogManager.getLogger();
   private final HoverEvent.Action<?> action;
   private final Object value;

   public <T> HoverEvent(HoverEvent.Action<T> var1, T var2) {
      this.action = â˜ƒ;
      this.value = â˜ƒ;
   }

   public HoverEvent.Action<?> getAction() {
      return this.action;
   }

   @Nullable
   public <T> T getValue(HoverEvent.Action<T> var1) {
      return this.action == â˜ƒ ? â˜ƒ.cast(this.value) : null;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         HoverEvent â˜ƒ = (HoverEvent)â˜ƒ;
         return this.action == â˜ƒ.action && Objects.equals(this.value, â˜ƒ.value);
      } else {
         return false;
      }
   }

   public String toString() {
      return "HoverEvent{action=" + this.action + ", value='" + this.value + "'}";
   }

   public int hashCode() {
      int â˜ƒ = this.action.hashCode();
      return 31 * â˜ƒ + (this.value != null ? this.value.hashCode() : 0);
   }

   @Nullable
   public static HoverEvent deserialize(JsonObject var0) {
      String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "action", null);
      if (â˜ƒ == null) {
         return null;
      } else {
         HoverEvent.Action<?> â˜ƒ = HoverEvent.Action.getByName(â˜ƒ);
         if (â˜ƒ == null) {
            return null;
         } else {
            JsonElement â˜ƒ = â˜ƒ.get("contents");
            if (â˜ƒ != null) {
               return â˜ƒ.deserialize(â˜ƒ);
            } else {
               Component â˜ƒ = Component.Serializer.fromJson(â˜ƒ.get("value"));
               return â˜ƒ != null ? â˜ƒ.deserializeFromLegacy(â˜ƒ) : null;
            }
         }
      }
   }

   public JsonObject serialize() {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.addProperty("action", this.action.getName());
      â˜ƒ.add("contents", this.action.serializeArg(this.value));
      return â˜ƒ;
   }

   public static class Action<T> {
      public static final HoverEvent.Action<Component> SHOW_TEXT = new HoverEvent.Action<>(
         "show_text", true, Component.Serializer::fromJson, Component.Serializer::toJsonTree, Function.identity()
      );
      public static final HoverEvent.Action<HoverEvent.ItemStackInfo> SHOW_ITEM = new HoverEvent.Action<>(
         "show_item", true, HoverEvent.ItemStackInfo::create, HoverEvent.ItemStackInfo::serialize, HoverEvent.ItemStackInfo::create
      );
      public static final HoverEvent.Action<HoverEvent.EntityTooltipInfo> SHOW_ENTITY = new HoverEvent.Action<>(
         "show_entity", true, HoverEvent.EntityTooltipInfo::create, HoverEvent.EntityTooltipInfo::serialize, HoverEvent.EntityTooltipInfo::create
      );
      private static final Map<String, HoverEvent.Action<?>> LOOKUP = (Map<String, HoverEvent.Action<?>>)Stream.of(SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY)
         .collect(ImmutableMap.toImmutableMap(HoverEvent.Action::getName, var0 -> var0));
      private final String name;
      private final boolean allowFromServer;
      private final Function<JsonElement, T> argDeserializer;
      private final Function<T, JsonElement> argSerializer;
      private final Function<Component, T> legacyArgDeserializer;

      public Action(String var1, boolean var2, Function<JsonElement, T> var3, Function<T, JsonElement> var4, Function<Component, T> var5) {
         this.name = â˜ƒ;
         this.allowFromServer = â˜ƒ;
         this.argDeserializer = â˜ƒ;
         this.argSerializer = â˜ƒ;
         this.legacyArgDeserializer = â˜ƒ;
      }

      public boolean isAllowedFromServer() {
         return this.allowFromServer;
      }

      public String getName() {
         return this.name;
      }

      @Nullable
      public static HoverEvent.Action<?> getByName(String var0) {
         return (HoverEvent.Action<?>)LOOKUP.get(â˜ƒ);
      }

      T cast(Object var1) {
         return (T)â˜ƒ;
      }

      @Nullable
      public HoverEvent deserialize(JsonElement var1) {
         T â˜ƒ = (T)this.argDeserializer.apply(â˜ƒ);
         return â˜ƒ == null ? null : new HoverEvent(this, â˜ƒ);
      }

      @Nullable
      public HoverEvent deserializeFromLegacy(Component var1) {
         T â˜ƒ = (T)this.legacyArgDeserializer.apply(â˜ƒ);
         return â˜ƒ == null ? null : new HoverEvent(this, â˜ƒ);
      }

      public JsonElement serializeArg(Object var1) {
         return (JsonElement)this.argSerializer.apply(this.cast(â˜ƒ));
      }

      public String toString() {
         return "<action " + this.name + ">";
      }
   }

   public static class EntityTooltipInfo {
      public final EntityType<?> type;
      public final UUID id;
      @Nullable
      public final Component name;
      @Nullable
      private List<Component> linesCache;

      public EntityTooltipInfo(EntityType<?> var1, UUID var2, @Nullable Component var3) {
         this.type = â˜ƒ;
         this.id = â˜ƒ;
         this.name = â˜ƒ;
      }

      @Nullable
      public static HoverEvent.EntityTooltipInfo create(JsonElement var0) {
         if (!â˜ƒ.isJsonObject()) {
            return null;
         } else {
            JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
            EntityType<?> â˜ƒx = Registry.ENTITY_TYPE.get(new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "type")));
            UUID â˜ƒxx = UUID.fromString(GsonHelper.getAsString(â˜ƒ, "id"));
            Component â˜ƒxxx = Component.Serializer.fromJson(â˜ƒ.get("name"));
            return new HoverEvent.EntityTooltipInfo(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }
      }

      @Nullable
      public static HoverEvent.EntityTooltipInfo create(Component var0) {
         try {
            CompoundTag â˜ƒ = TagParser.parseTag(â˜ƒ.getString());
            Component â˜ƒx = Component.Serializer.fromJson(â˜ƒ.getString("name"));
            EntityType<?> â˜ƒxx = Registry.ENTITY_TYPE.get(new ResourceLocation(â˜ƒ.getString("type")));
            UUID â˜ƒxxx = UUID.fromString(â˜ƒ.getString("id"));
            return new HoverEvent.EntityTooltipInfo(â˜ƒxx, â˜ƒxxx, â˜ƒx);
         } catch (CommandSyntaxException | JsonSyntaxException var5) {
            return null;
         }
      }

      public JsonElement serialize() {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("type", Registry.ENTITY_TYPE.getKey(this.type).toString());
         â˜ƒ.addProperty("id", this.id.toString());
         if (this.name != null) {
            â˜ƒ.add("name", Component.Serializer.toJsonTree(this.name));
         }

         return â˜ƒ;
      }

      public List<Component> getTooltipLines() {
         if (this.linesCache == null) {
            this.linesCache = Lists.<Component>newArrayList();
            if (this.name != null) {
               this.linesCache.add(this.name);
            }

            this.linesCache.add(new TranslatableComponent("gui.entity_tooltip.type", this.type.getDescription()));
            this.linesCache.add(new TextComponent(this.id.toString()));
         }

         return this.linesCache;
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
            HoverEvent.EntityTooltipInfo â˜ƒ = (HoverEvent.EntityTooltipInfo)â˜ƒ;
            return this.type.equals(â˜ƒ.type) && this.id.equals(â˜ƒ.id) && Objects.equals(this.name, â˜ƒ.name);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int â˜ƒ = this.type.hashCode();
         â˜ƒ = 31 * â˜ƒ + this.id.hashCode();
         return 31 * â˜ƒ + (this.name != null ? this.name.hashCode() : 0);
      }
   }

   public static class ItemStackInfo {
      private final Item item;
      private final int count;
      @Nullable
      private final CompoundTag tag;
      @Nullable
      private ItemStack itemStack;

      ItemStackInfo(Item var1, int var2, @Nullable CompoundTag var3) {
         this.item = â˜ƒ;
         this.count = â˜ƒ;
         this.tag = â˜ƒ;
      }

      public ItemStackInfo(ItemStack var1) {
         this(â˜ƒ.getItem(), â˜ƒ.getCount(), â˜ƒ.getTag() != null ? â˜ƒ.getTag().copy() : null);
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
            HoverEvent.ItemStackInfo â˜ƒ = (HoverEvent.ItemStackInfo)â˜ƒ;
            return this.count == â˜ƒ.count && this.item.equals(â˜ƒ.item) && Objects.equals(this.tag, â˜ƒ.tag);
         } else {
            return false;
         }
      }

      public int hashCode() {
         int â˜ƒ = this.item.hashCode();
         â˜ƒ = 31 * â˜ƒ + this.count;
         return 31 * â˜ƒ + (this.tag != null ? this.tag.hashCode() : 0);
      }

      public ItemStack getItemStack() {
         if (this.itemStack == null) {
            this.itemStack = new ItemStack(this.item, this.count);
            if (this.tag != null) {
               this.itemStack.setTag(this.tag);
            }
         }

         return this.itemStack;
      }

      private static HoverEvent.ItemStackInfo create(JsonElement var0) {
         if (â˜ƒ.isJsonPrimitive()) {
            return new HoverEvent.ItemStackInfo(Registry.ITEM.get(new ResourceLocation(â˜ƒ.getAsString())), 1, null);
         } else {
            JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "item");
            Item â˜ƒx = Registry.ITEM.get(new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "id")));
            int â˜ƒxx = GsonHelper.getAsInt(â˜ƒ, "count", 1);
            if (â˜ƒ.has("tag")) {
               String â˜ƒxxx = GsonHelper.getAsString(â˜ƒ, "tag");

               try {
                  CompoundTag â˜ƒxxxx = TagParser.parseTag(â˜ƒxxx);
                  return new HoverEvent.ItemStackInfo(â˜ƒx, â˜ƒxx, â˜ƒxxxx);
               } catch (CommandSyntaxException var6) {
                  HoverEvent.LOGGER.warn("Failed to parse tag: {}", â˜ƒxxx, var6);
               }
            }

            return new HoverEvent.ItemStackInfo(â˜ƒx, â˜ƒxx, null);
         }
      }

      @Nullable
      private static HoverEvent.ItemStackInfo create(Component var0) {
         try {
            CompoundTag â˜ƒ = TagParser.parseTag(â˜ƒ.getString());
            return new HoverEvent.ItemStackInfo(ItemStack.of(â˜ƒ));
         } catch (CommandSyntaxException var2) {
            HoverEvent.LOGGER.warn("Failed to parse item tag: {}", â˜ƒ, var2);
            return null;
         }
      }

      private JsonElement serialize() {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("id", Registry.ITEM.getKey(this.item).toString());
         if (this.count != 1) {
            â˜ƒ.addProperty("count", this.count);
         }

         if (this.tag != null) {
            â˜ƒ.addProperty("tag", this.tag.toString());
         }

         return â˜ƒ;
      }
   }
}
