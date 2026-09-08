package net.minecraft.network.chat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class Style {
   public static final Style EMPTY = new Style(null, null, null, null, null, null, null, null, null, null);
   public static final ResourceLocation DEFAULT_FONT = new ResourceLocation("minecraft", "default");
   @Nullable
   final TextColor color;
   @Nullable
   final Boolean bold;
   @Nullable
   final Boolean italic;
   @Nullable
   final Boolean underlined;
   @Nullable
   final Boolean strikethrough;
   @Nullable
   final Boolean obfuscated;
   @Nullable
   final ClickEvent clickEvent;
   @Nullable
   final HoverEvent hoverEvent;
   @Nullable
   final String insertion;
   @Nullable
   final ResourceLocation font;

   Style(
      @Nullable TextColor var1,
      @Nullable Boolean var2,
      @Nullable Boolean var3,
      @Nullable Boolean var4,
      @Nullable Boolean var5,
      @Nullable Boolean var6,
      @Nullable ClickEvent var7,
      @Nullable HoverEvent var8,
      @Nullable String var9,
      @Nullable ResourceLocation var10
   ) {
      this.color = â˜ƒ;
      this.bold = â˜ƒ;
      this.italic = â˜ƒ;
      this.underlined = â˜ƒ;
      this.strikethrough = â˜ƒ;
      this.obfuscated = â˜ƒ;
      this.clickEvent = â˜ƒ;
      this.hoverEvent = â˜ƒ;
      this.insertion = â˜ƒ;
      this.font = â˜ƒ;
   }

   @Nullable
   public TextColor getColor() {
      return this.color;
   }

   public boolean isBold() {
      return this.bold == Boolean.TRUE;
   }

   public boolean isItalic() {
      return this.italic == Boolean.TRUE;
   }

   public boolean isStrikethrough() {
      return this.strikethrough == Boolean.TRUE;
   }

   public boolean isUnderlined() {
      return this.underlined == Boolean.TRUE;
   }

   public boolean isObfuscated() {
      return this.obfuscated == Boolean.TRUE;
   }

   public boolean isEmpty() {
      return this == EMPTY;
   }

   @Nullable
   public ClickEvent getClickEvent() {
      return this.clickEvent;
   }

   @Nullable
   public HoverEvent getHoverEvent() {
      return this.hoverEvent;
   }

   @Nullable
   public String getInsertion() {
      return this.insertion;
   }

   public ResourceLocation getFont() {
      return this.font != null ? this.font : DEFAULT_FONT;
   }

   public Style withColor(@Nullable TextColor var1) {
      return new Style(
         â˜ƒ, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withColor(@Nullable ChatFormatting var1) {
      return this.withColor(â˜ƒ != null ? TextColor.fromLegacyFormat(â˜ƒ) : null);
   }

   public Style withColor(int var1) {
      return this.withColor(TextColor.fromRgb(â˜ƒ));
   }

   public Style withBold(@Nullable Boolean var1) {
      return new Style(
         this.color, â˜ƒ, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withItalic(@Nullable Boolean var1) {
      return new Style(
         this.color, this.bold, â˜ƒ, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withUnderlined(@Nullable Boolean var1) {
      return new Style(
         this.color, this.bold, this.italic, â˜ƒ, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withStrikethrough(@Nullable Boolean var1) {
      return new Style(this.color, this.bold, this.italic, this.underlined, â˜ƒ, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font);
   }

   public Style withObfuscated(@Nullable Boolean var1) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, â˜ƒ, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withClickEvent(@Nullable ClickEvent var1) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, â˜ƒ, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withHoverEvent(@Nullable HoverEvent var1) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, â˜ƒ, this.insertion, this.font
      );
   }

   public Style withInsertion(@Nullable String var1) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, â˜ƒ, this.font
      );
   }

   public Style withFont(@Nullable ResourceLocation var1) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, â˜ƒ
      );
   }

   public Style applyFormat(ChatFormatting var1) {
      TextColor â˜ƒ = this.color;
      Boolean â˜ƒx = this.bold;
      Boolean â˜ƒxx = this.italic;
      Boolean â˜ƒxxx = this.strikethrough;
      Boolean â˜ƒxxxx = this.underlined;
      Boolean â˜ƒxxxxx = this.obfuscated;
      switch(â˜ƒ) {
         case OBFUSCATED:
            â˜ƒxxxxx = true;
            break;
         case BOLD:
            â˜ƒx = true;
            break;
         case STRIKETHROUGH:
            â˜ƒxxx = true;
            break;
         case UNDERLINE:
            â˜ƒxxxx = true;
            break;
         case ITALIC:
            â˜ƒxx = true;
            break;
         case RESET:
            return EMPTY;
         default:
            â˜ƒ = TextColor.fromLegacyFormat(â˜ƒ);
      }

      return new Style(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxx, â˜ƒxxx, â˜ƒxxxxx, this.clickEvent, this.hoverEvent, this.insertion, this.font);
   }

   public Style applyLegacyFormat(ChatFormatting var1) {
      TextColor â˜ƒ = this.color;
      Boolean â˜ƒx = this.bold;
      Boolean â˜ƒxx = this.italic;
      Boolean â˜ƒxxx = this.strikethrough;
      Boolean â˜ƒxxxx = this.underlined;
      Boolean â˜ƒxxxxx = this.obfuscated;
      switch(â˜ƒ) {
         case OBFUSCATED:
            â˜ƒxxxxx = true;
            break;
         case BOLD:
            â˜ƒx = true;
            break;
         case STRIKETHROUGH:
            â˜ƒxxx = true;
            break;
         case UNDERLINE:
            â˜ƒxxxx = true;
            break;
         case ITALIC:
            â˜ƒxx = true;
            break;
         case RESET:
            return EMPTY;
         default:
            â˜ƒxxxxx = false;
            â˜ƒx = false;
            â˜ƒxxx = false;
            â˜ƒxxxx = false;
            â˜ƒxx = false;
            â˜ƒ = TextColor.fromLegacyFormat(â˜ƒ);
      }

      return new Style(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxx, â˜ƒxxx, â˜ƒxxxxx, this.clickEvent, this.hoverEvent, this.insertion, this.font);
   }

   public Style applyFormats(ChatFormatting... var1) {
      TextColor â˜ƒ = this.color;
      Boolean â˜ƒx = this.bold;
      Boolean â˜ƒxx = this.italic;
      Boolean â˜ƒxxx = this.strikethrough;
      Boolean â˜ƒxxxx = this.underlined;
      Boolean â˜ƒxxxxx = this.obfuscated;

      for(ChatFormatting â˜ƒxxxxxx : â˜ƒ) {
         switch(â˜ƒxxxxxx) {
            case OBFUSCATED:
               â˜ƒxxxxx = true;
               break;
            case BOLD:
               â˜ƒx = true;
               break;
            case STRIKETHROUGH:
               â˜ƒxxx = true;
               break;
            case UNDERLINE:
               â˜ƒxxxx = true;
               break;
            case ITALIC:
               â˜ƒxx = true;
               break;
            case RESET:
               return EMPTY;
            default:
               â˜ƒ = TextColor.fromLegacyFormat(â˜ƒxxxxxx);
         }
      }

      return new Style(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxx, â˜ƒxxx, â˜ƒxxxxx, this.clickEvent, this.hoverEvent, this.insertion, this.font);
   }

   public Style applyTo(Style var1) {
      if (this == EMPTY) {
         return â˜ƒ;
      } else {
         return â˜ƒ == EMPTY
            ? this
            : new Style(
               this.color != null ? this.color : â˜ƒ.color,
               this.bold != null ? this.bold : â˜ƒ.bold,
               this.italic != null ? this.italic : â˜ƒ.italic,
               this.underlined != null ? this.underlined : â˜ƒ.underlined,
               this.strikethrough != null ? this.strikethrough : â˜ƒ.strikethrough,
               this.obfuscated != null ? this.obfuscated : â˜ƒ.obfuscated,
               this.clickEvent != null ? this.clickEvent : â˜ƒ.clickEvent,
               this.hoverEvent != null ? this.hoverEvent : â˜ƒ.hoverEvent,
               this.insertion != null ? this.insertion : â˜ƒ.insertion,
               this.font != null ? this.font : â˜ƒ.font
            );
      }
   }

   public String toString() {
      return "Style{ color="
         + this.color
         + ", bold="
         + this.bold
         + ", italic="
         + this.italic
         + ", underlined="
         + this.underlined
         + ", strikethrough="
         + this.strikethrough
         + ", obfuscated="
         + this.obfuscated
         + ", clickEvent="
         + this.getClickEvent()
         + ", hoverEvent="
         + this.getHoverEvent()
         + ", insertion="
         + this.getInsertion()
         + ", font="
         + this.getFont()
         + "}";
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof Style)) {
         return false;
      } else {
         Style â˜ƒ = (Style)â˜ƒ;
         return this.isBold() == â˜ƒ.isBold()
            && Objects.equals(this.getColor(), â˜ƒ.getColor())
            && this.isItalic() == â˜ƒ.isItalic()
            && this.isObfuscated() == â˜ƒ.isObfuscated()
            && this.isStrikethrough() == â˜ƒ.isStrikethrough()
            && this.isUnderlined() == â˜ƒ.isUnderlined()
            && Objects.equals(this.getClickEvent(), â˜ƒ.getClickEvent())
            && Objects.equals(this.getHoverEvent(), â˜ƒ.getHoverEvent())
            && Objects.equals(this.getInsertion(), â˜ƒ.getInsertion())
            && Objects.equals(this.getFont(), â˜ƒ.getFont());
      }
   }

   public int hashCode() {
      return Objects.hash(
         new Object[]{
            this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion
         }
      );
   }

   public static class Serializer implements JsonDeserializer<Style>, JsonSerializer<Style> {
      @Nullable
      public Style deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (â˜ƒ.isJsonObject()) {
            JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
            if (â˜ƒ == null) {
               return null;
            } else {
               Boolean â˜ƒ = getOptionalFlag(â˜ƒ, "bold");
               Boolean â˜ƒx = getOptionalFlag(â˜ƒ, "italic");
               Boolean â˜ƒxx = getOptionalFlag(â˜ƒ, "underlined");
               Boolean â˜ƒxxx = getOptionalFlag(â˜ƒ, "strikethrough");
               Boolean â˜ƒxxxx = getOptionalFlag(â˜ƒ, "obfuscated");
               TextColor â˜ƒxxxxx = getTextColor(â˜ƒ);
               String â˜ƒxxxxxx = getInsertion(â˜ƒ);
               ClickEvent â˜ƒxxxxxxx = getClickEvent(â˜ƒ);
               HoverEvent â˜ƒxxxxxxxx = getHoverEvent(â˜ƒ);
               ResourceLocation â˜ƒxxxxxxxxx = getFont(â˜ƒ);
               return new Style(â˜ƒxxxxx, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxx);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static ResourceLocation getFont(JsonObject var0) {
         if (â˜ƒ.has("font")) {
            String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "font");

            try {
               return new ResourceLocation(â˜ƒ);
            } catch (ResourceLocationException var3) {
               throw new JsonSyntaxException("Invalid font name: " + â˜ƒ);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static HoverEvent getHoverEvent(JsonObject var0) {
         if (â˜ƒ.has("hoverEvent")) {
            JsonObject â˜ƒ = GsonHelper.getAsJsonObject(â˜ƒ, "hoverEvent");
            HoverEvent â˜ƒx = HoverEvent.deserialize(â˜ƒ);
            if (â˜ƒx != null && â˜ƒx.getAction().isAllowedFromServer()) {
               return â˜ƒx;
            }
         }

         return null;
      }

      @Nullable
      private static ClickEvent getClickEvent(JsonObject var0) {
         if (â˜ƒ.has("clickEvent")) {
            JsonObject â˜ƒ = GsonHelper.getAsJsonObject(â˜ƒ, "clickEvent");
            String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "action", null);
            ClickEvent.Action â˜ƒxx = â˜ƒx == null ? null : ClickEvent.Action.getByName(â˜ƒx);
            String â˜ƒxxx = GsonHelper.getAsString(â˜ƒ, "value", null);
            if (â˜ƒxx != null && â˜ƒxxx != null && â˜ƒxx.isAllowedFromServer()) {
               return new ClickEvent(â˜ƒxx, â˜ƒxxx);
            }
         }

         return null;
      }

      @Nullable
      private static String getInsertion(JsonObject var0) {
         return GsonHelper.getAsString(â˜ƒ, "insertion", null);
      }

      @Nullable
      private static TextColor getTextColor(JsonObject var0) {
         if (â˜ƒ.has("color")) {
            String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "color");
            return TextColor.parseColor(â˜ƒ);
         } else {
            return null;
         }
      }

      @Nullable
      private static Boolean getOptionalFlag(JsonObject var0, String var1) {
         return â˜ƒ.has(â˜ƒ) ? â˜ƒ.get(â˜ƒ).getAsBoolean() : null;
      }

      @Nullable
      public JsonElement serialize(Style var1, Type var2, JsonSerializationContext var3) {
         if (â˜ƒ.isEmpty()) {
            return null;
         } else {
            JsonObject â˜ƒ = new JsonObject();
            if (â˜ƒ.bold != null) {
               â˜ƒ.addProperty("bold", â˜ƒ.bold);
            }

            if (â˜ƒ.italic != null) {
               â˜ƒ.addProperty("italic", â˜ƒ.italic);
            }

            if (â˜ƒ.underlined != null) {
               â˜ƒ.addProperty("underlined", â˜ƒ.underlined);
            }

            if (â˜ƒ.strikethrough != null) {
               â˜ƒ.addProperty("strikethrough", â˜ƒ.strikethrough);
            }

            if (â˜ƒ.obfuscated != null) {
               â˜ƒ.addProperty("obfuscated", â˜ƒ.obfuscated);
            }

            if (â˜ƒ.color != null) {
               â˜ƒ.addProperty("color", â˜ƒ.color.serialize());
            }

            if (â˜ƒ.insertion != null) {
               â˜ƒ.add("insertion", â˜ƒ.serialize(â˜ƒ.insertion));
            }

            if (â˜ƒ.clickEvent != null) {
               JsonObject â˜ƒ = new JsonObject();
               â˜ƒ.addProperty("action", â˜ƒ.clickEvent.getAction().getName());
               â˜ƒ.addProperty("value", â˜ƒ.clickEvent.getValue());
               â˜ƒ.add("clickEvent", â˜ƒ);
            }

            if (â˜ƒ.hoverEvent != null) {
               â˜ƒ.add("hoverEvent", â˜ƒ.hoverEvent.serialize());
            }

            if (â˜ƒ.font != null) {
               â˜ƒ.addProperty("font", â˜ƒ.font.toString());
            }

            return â˜ƒ;
         }
      }
   }
}
