package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DisplayInfo {
   private final Component title;
   private final Component description;
   private final ItemStack icon;
   private final ResourceLocation background;
   private final FrameType frame;
   private final boolean showToast;
   private final boolean announceChat;
   private final boolean hidden;
   private float x;
   private float y;

   public DisplayInfo(ItemStack var1, Component var2, Component var3, @Nullable ResourceLocation var4, FrameType var5, boolean var6, boolean var7, boolean var8) {
      this.title = â˜ƒ;
      this.description = â˜ƒ;
      this.icon = â˜ƒ;
      this.background = â˜ƒ;
      this.frame = â˜ƒ;
      this.showToast = â˜ƒ;
      this.announceChat = â˜ƒ;
      this.hidden = â˜ƒ;
   }

   public void setLocation(float var1, float var2) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
   }

   public Component getTitle() {
      return this.title;
   }

   public Component getDescription() {
      return this.description;
   }

   public ItemStack getIcon() {
      return this.icon;
   }

   @Nullable
   public ResourceLocation getBackground() {
      return this.background;
   }

   public FrameType getFrame() {
      return this.frame;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public boolean shouldShowToast() {
      return this.showToast;
   }

   public boolean shouldAnnounceChat() {
      return this.announceChat;
   }

   public boolean isHidden() {
      return this.hidden;
   }

   public static DisplayInfo fromJson(JsonObject var0) {
      Component â˜ƒ = Component.Serializer.fromJson(â˜ƒ.get("title"));
      Component â˜ƒx = Component.Serializer.fromJson(â˜ƒ.get("description"));
      if (â˜ƒ != null && â˜ƒx != null) {
         ItemStack â˜ƒxx = getIcon(GsonHelper.getAsJsonObject(â˜ƒ, "icon"));
         ResourceLocation â˜ƒxxx = â˜ƒ.has("background") ? new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "background")) : null;
         FrameType â˜ƒxxxx = â˜ƒ.has("frame") ? FrameType.byName(GsonHelper.getAsString(â˜ƒ, "frame")) : FrameType.TASK;
         boolean â˜ƒxxxxx = GsonHelper.getAsBoolean(â˜ƒ, "show_toast", true);
         boolean â˜ƒxxxxxx = GsonHelper.getAsBoolean(â˜ƒ, "announce_to_chat", true);
         boolean â˜ƒxxxxxxx = GsonHelper.getAsBoolean(â˜ƒ, "hidden", false);
         return new DisplayInfo(â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   private static ItemStack getIcon(JsonObject var0) {
      if (!â˜ƒ.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         Item â˜ƒ = GsonHelper.getAsItem(â˜ƒ, "item");
         if (â˜ƒ.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            ItemStack â˜ƒ = new ItemStack(â˜ƒ);
            if (â˜ƒ.has("nbt")) {
               try {
                  CompoundTag â˜ƒx = TagParser.parseTag(GsonHelper.convertToString(â˜ƒ.get("nbt"), "nbt"));
                  â˜ƒ.setTag(â˜ƒx);
               } catch (CommandSyntaxException var4) {
                  throw new JsonSyntaxException("Invalid nbt tag: " + var4.getMessage());
               }
            }

            return â˜ƒ;
         }
      }
   }

   public void serializeToNetwork(FriendlyByteBuf var1) {
      â˜ƒ.writeComponent(this.title);
      â˜ƒ.writeComponent(this.description);
      â˜ƒ.writeItem(this.icon);
      â˜ƒ.writeEnum(this.frame);
      int â˜ƒ = 0;
      if (this.background != null) {
         â˜ƒ |= 1;
      }

      if (this.showToast) {
         â˜ƒ |= 2;
      }

      if (this.hidden) {
         â˜ƒ |= 4;
      }

      â˜ƒ.writeInt(â˜ƒ);
      if (this.background != null) {
         â˜ƒ.writeResourceLocation(this.background);
      }

      â˜ƒ.writeFloat(this.x);
      â˜ƒ.writeFloat(this.y);
   }

   public static DisplayInfo fromNetwork(FriendlyByteBuf var0) {
      Component â˜ƒ = â˜ƒ.readComponent();
      Component â˜ƒx = â˜ƒ.readComponent();
      ItemStack â˜ƒxx = â˜ƒ.readItem();
      FrameType â˜ƒxxx = â˜ƒ.readEnum(FrameType.class);
      int â˜ƒxxxx = â˜ƒ.readInt();
      ResourceLocation â˜ƒxxxxx = (â˜ƒxxxx & 1) != 0 ? â˜ƒ.readResourceLocation() : null;
      boolean â˜ƒxxxxxx = (â˜ƒxxxx & 2) != 0;
      boolean â˜ƒxxxxxxx = (â˜ƒxxxx & 4) != 0;
      DisplayInfo â˜ƒxxxxxxxx = new DisplayInfo(â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxxxx, false, â˜ƒxxxxxxx);
      â˜ƒxxxxxxxx.setLocation(â˜ƒ.readFloat(), â˜ƒ.readFloat());
      return â˜ƒxxxxxxxx;
   }

   public JsonElement serializeToJson() {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.add("icon", this.serializeIcon());
      â˜ƒ.add("title", Component.Serializer.toJsonTree(this.title));
      â˜ƒ.add("description", Component.Serializer.toJsonTree(this.description));
      â˜ƒ.addProperty("frame", this.frame.getName());
      â˜ƒ.addProperty("show_toast", this.showToast);
      â˜ƒ.addProperty("announce_to_chat", this.announceChat);
      â˜ƒ.addProperty("hidden", this.hidden);
      if (this.background != null) {
         â˜ƒ.addProperty("background", this.background.toString());
      }

      return â˜ƒ;
   }

   private JsonObject serializeIcon() {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.addProperty("item", Registry.ITEM.getKey(this.icon.getItem()).toString());
      if (this.icon.hasTag()) {
         â˜ƒ.addProperty("nbt", this.icon.getTag().toString());
      }

      return â˜ƒ;
   }
}
