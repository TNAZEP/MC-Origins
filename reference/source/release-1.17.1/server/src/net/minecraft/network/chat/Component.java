package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.LowerCaseEnumTypeAdapterFactory;

public interface Component extends Message, FormattedText {
   Style getStyle();

   String getContents();

   @Override
   default String getString() {
      return FormattedText.super.getString();
   }

   default String getString(int var1) {
      StringBuilder â˜ƒ = new StringBuilder();
      this.visit(var2x -> {
         int â˜ƒ = â˜ƒ - â˜ƒ.length();
         if (â˜ƒ <= 0) {
            return STOP_ITERATION;
         } else {
            â˜ƒ.append(var2x.length() <= â˜ƒ ? var2x : var2x.substring(0, â˜ƒ));
            return Optional.empty();
         }
      });
      return â˜ƒ.toString();
   }

   List<Component> getSiblings();

   MutableComponent plainCopy();

   MutableComponent copy();

   FormattedCharSequence getVisualOrderText();

   @Override
   default <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> var1, Style var2) {
      Style â˜ƒ = this.getStyle().applyTo(â˜ƒ);
      Optional<T> â˜ƒx = this.visitSelf(â˜ƒ, â˜ƒ);
      if (â˜ƒx.isPresent()) {
         return â˜ƒx;
      } else {
         for(Component â˜ƒ : this.getSiblings()) {
            Optional<T> â˜ƒx = â˜ƒ.visit(â˜ƒ, â˜ƒ);
            if (â˜ƒx.isPresent()) {
               return â˜ƒx;
            }
         }

         return Optional.empty();
      }
   }

   @Override
   default <T> Optional<T> visit(FormattedText.ContentConsumer<T> var1) {
      Optional<T> â˜ƒ = this.visitSelf(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         return â˜ƒ;
      } else {
         for(Component â˜ƒ : this.getSiblings()) {
            Optional<T> â˜ƒx = â˜ƒ.visit(â˜ƒ);
            if (â˜ƒx.isPresent()) {
               return â˜ƒx;
            }
         }

         return Optional.empty();
      }
   }

   default <T> Optional<T> visitSelf(FormattedText.StyledContentConsumer<T> var1, Style var2) {
      return â˜ƒ.accept(â˜ƒ, this.getContents());
   }

   default <T> Optional<T> visitSelf(FormattedText.ContentConsumer<T> var1) {
      return â˜ƒ.accept(this.getContents());
   }

   default List<Component> toFlatList(Style var1) {
      List<Component> â˜ƒ = Lists.<Component>newArrayList();
      this.visit((var1x, var2x) -> {
         if (!var2x.isEmpty()) {
            â˜ƒ.add(new TextComponent(var2x).withStyle(var1x));
         }

         return Optional.empty();
      }, â˜ƒ);
      return â˜ƒ;
   }

   static Component nullToEmpty(@Nullable String var0) {
      return (Component)(â˜ƒ != null ? new TextComponent(â˜ƒ) : TextComponent.EMPTY);
   }

   public static class Serializer implements JsonDeserializer<MutableComponent>, JsonSerializer<Component> {
      private static final Gson GSON = Util.make(() -> {
         GsonBuilder â˜ƒ = new GsonBuilder();
         â˜ƒ.disableHtmlEscaping();
         â˜ƒ.registerTypeHierarchyAdapter(Component.class, new Component.Serializer());
         â˜ƒ.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
         â˜ƒ.registerTypeAdapterFactory(new LowerCaseEnumTypeAdapterFactory());
         return â˜ƒ.create();
      });
      private static final Field JSON_READER_POS = Util.make(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field â˜ƒ = JsonReader.class.getDeclaredField("pos");
            â˜ƒ.setAccessible(true);
            return â˜ƒ;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", var1);
         }
      });
      private static final Field JSON_READER_LINESTART = Util.make(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field â˜ƒ = JsonReader.class.getDeclaredField("lineStart");
            â˜ƒ.setAccessible(true);
            return â˜ƒ;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", var1);
         }
      });

      public MutableComponent deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (â˜ƒ.isJsonPrimitive()) {
            return new TextComponent(â˜ƒ.getAsString());
         } else if (!â˜ƒ.isJsonObject()) {
            if (â˜ƒ.isJsonArray()) {
               JsonArray â˜ƒ = â˜ƒ.getAsJsonArray();
               MutableComponent â˜ƒx = null;

               for(JsonElement â˜ƒxx : â˜ƒ) {
                  MutableComponent â˜ƒxxx = this.deserialize(â˜ƒxx, â˜ƒxx.getClass(), â˜ƒ);
                  if (â˜ƒx == null) {
                     â˜ƒx = â˜ƒxxx;
                  } else {
                     â˜ƒx.append(â˜ƒxxx);
                  }
               }

               return â˜ƒx;
            } else {
               throw new JsonParseException("Don't know how to turn " + â˜ƒ + " into a Component");
            }
         } else {
            JsonObject â˜ƒx = â˜ƒ.getAsJsonObject();
            MutableComponent â˜ƒ;
            if (â˜ƒx.has("text")) {
               â˜ƒ = new TextComponent(GsonHelper.getAsString(â˜ƒx, "text"));
            } else if (â˜ƒx.has("translate")) {
               String â˜ƒ = GsonHelper.getAsString(â˜ƒx, "translate");
               if (â˜ƒx.has("with")) {
                  JsonArray â˜ƒx = GsonHelper.getAsJsonArray(â˜ƒx, "with");
                  Object[] â˜ƒxx = new Object[â˜ƒx.size()];

                  for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.length; ++â˜ƒxxx) {
                     â˜ƒxx[â˜ƒxxx] = this.deserialize(â˜ƒx.get(â˜ƒxxx), â˜ƒ, â˜ƒ);
                     if (â˜ƒxx[â˜ƒxxx] instanceof TextComponent â˜ƒxxxx && â˜ƒxxxx.getStyle().isEmpty() && â˜ƒxxxx.getSiblings().isEmpty()) {
                        â˜ƒxx[â˜ƒxxx] = â˜ƒxxxx.getText();
                     }
                  }

                  â˜ƒ = new TranslatableComponent(â˜ƒ, â˜ƒxx);
               } else {
                  â˜ƒ = new TranslatableComponent(â˜ƒ);
               }
            } else if (â˜ƒx.has("score")) {
               JsonObject â˜ƒ = GsonHelper.getAsJsonObject(â˜ƒx, "score");
               if (!â˜ƒ.has("name") || !â˜ƒ.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               â˜ƒ = new ScoreComponent(GsonHelper.getAsString(â˜ƒ, "name"), GsonHelper.getAsString(â˜ƒ, "objective"));
            } else if (â˜ƒx.has("selector")) {
               Optional<Component> â˜ƒ = this.parseSeparator(â˜ƒ, â˜ƒ, â˜ƒx);
               â˜ƒ = new SelectorComponent(GsonHelper.getAsString(â˜ƒx, "selector"), â˜ƒ);
            } else if (â˜ƒx.has("keybind")) {
               â˜ƒ = new KeybindComponent(GsonHelper.getAsString(â˜ƒx, "keybind"));
            } else {
               if (!â˜ƒx.has("nbt")) {
                  throw new JsonParseException("Don't know how to turn " + â˜ƒ + " into a Component");
               }

               String â˜ƒ = GsonHelper.getAsString(â˜ƒx, "nbt");
               Optional<Component> â˜ƒx = this.parseSeparator(â˜ƒ, â˜ƒ, â˜ƒx);
               boolean â˜ƒxx = GsonHelper.getAsBoolean(â˜ƒx, "interpret", false);
               if (â˜ƒx.has("block")) {
                  â˜ƒ = new NbtComponent.BlockNbtComponent(â˜ƒ, â˜ƒxx, GsonHelper.getAsString(â˜ƒx, "block"), â˜ƒx);
               } else if (â˜ƒx.has("entity")) {
                  â˜ƒ = new NbtComponent.EntityNbtComponent(â˜ƒ, â˜ƒxx, GsonHelper.getAsString(â˜ƒx, "entity"), â˜ƒx);
               } else {
                  if (!â˜ƒx.has("storage")) {
                     throw new JsonParseException("Don't know how to turn " + â˜ƒ + " into a Component");
                  }

                  â˜ƒ = new NbtComponent.StorageNbtComponent(â˜ƒ, â˜ƒxx, new ResourceLocation(GsonHelper.getAsString(â˜ƒx, "storage")), â˜ƒx);
               }
            }

            if (â˜ƒx.has("extra")) {
               JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒx, "extra");
               if (â˜ƒ.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
                  â˜ƒ.append(this.deserialize(â˜ƒ.get(â˜ƒ), â˜ƒ, â˜ƒ));
               }
            }

            â˜ƒ.setStyle(â˜ƒ.deserialize(â˜ƒ, Style.class));
            return â˜ƒ;
         }
      }

      private Optional<Component> parseSeparator(Type var1, JsonDeserializationContext var2, JsonObject var3) {
         return â˜ƒ.has("separator") ? Optional.of(this.deserialize(â˜ƒ.get("separator"), â˜ƒ, â˜ƒ)) : Optional.empty();
      }

      private void serializeStyle(Style var1, JsonObject var2, JsonSerializationContext var3) {
         JsonElement â˜ƒ = â˜ƒ.serialize(â˜ƒ);
         if (â˜ƒ.isJsonObject()) {
            JsonObject â˜ƒx = (JsonObject)â˜ƒ;

            for(Entry<String, JsonElement> â˜ƒxx : â˜ƒx.entrySet()) {
               â˜ƒ.add((String)â˜ƒxx.getKey(), (JsonElement)â˜ƒxx.getValue());
            }
         }
      }

      public JsonElement serialize(Component var1, Type var2, JsonSerializationContext var3) {
         JsonObject â˜ƒ = new JsonObject();
         if (!â˜ƒ.getStyle().isEmpty()) {
            this.serializeStyle(â˜ƒ.getStyle(), â˜ƒ, â˜ƒ);
         }

         if (!â˜ƒ.getSiblings().isEmpty()) {
            JsonArray â˜ƒ = new JsonArray();

            for(Component â˜ƒx : â˜ƒ.getSiblings()) {
               â˜ƒ.add(this.serialize(â˜ƒx, â˜ƒx.getClass(), â˜ƒ));
            }

            â˜ƒ.add("extra", â˜ƒ);
         }

         if (â˜ƒ instanceof TextComponent) {
            â˜ƒ.addProperty("text", ((TextComponent)â˜ƒ).getText());
         } else if (â˜ƒ instanceof TranslatableComponent â˜ƒ) {
            â˜ƒ.addProperty("translate", â˜ƒ.getKey());
            if (â˜ƒ.getArgs() != null && â˜ƒ.getArgs().length > 0) {
               JsonArray â˜ƒx = new JsonArray();

               for(Object â˜ƒxx : â˜ƒ.getArgs()) {
                  if (â˜ƒxx instanceof Component) {
                     â˜ƒx.add(this.serialize((Component)â˜ƒxx, â˜ƒxx.getClass(), â˜ƒ));
                  } else {
                     â˜ƒx.add(new JsonPrimitive(String.valueOf(â˜ƒxx)));
                  }
               }

               â˜ƒ.add("with", â˜ƒx);
            }
         } else if (â˜ƒ instanceof ScoreComponent â˜ƒ) {
            JsonObject â˜ƒx = new JsonObject();
            â˜ƒx.addProperty("name", â˜ƒ.getName());
            â˜ƒx.addProperty("objective", â˜ƒ.getObjective());
            â˜ƒ.add("score", â˜ƒx);
         } else if (â˜ƒ instanceof SelectorComponent â˜ƒ) {
            â˜ƒ.addProperty("selector", â˜ƒ.getPattern());
            this.serializeSeparator(â˜ƒ, â˜ƒ, â˜ƒ.getSeparator());
         } else if (â˜ƒ instanceof KeybindComponent â˜ƒ) {
            â˜ƒ.addProperty("keybind", â˜ƒ.getName());
         } else {
            if (!(â˜ƒ instanceof NbtComponent)) {
               throw new IllegalArgumentException("Don't know how to serialize " + â˜ƒ + " as a Component");
            }

            NbtComponent â˜ƒx = (NbtComponent)â˜ƒ;
            â˜ƒ.addProperty("nbt", â˜ƒx.getNbtPath());
            â˜ƒ.addProperty("interpret", â˜ƒx.isInterpreting());
            this.serializeSeparator(â˜ƒ, â˜ƒ, â˜ƒx.separator);
            if (â˜ƒ instanceof NbtComponent.BlockNbtComponent â˜ƒ) {
               â˜ƒ.addProperty("block", â˜ƒ.getPos());
            } else if (â˜ƒ instanceof NbtComponent.EntityNbtComponent â˜ƒ) {
               â˜ƒ.addProperty("entity", â˜ƒ.getSelector());
            } else {
               if (!(â˜ƒ instanceof NbtComponent.StorageNbtComponent)) {
                  throw new IllegalArgumentException("Don't know how to serialize " + â˜ƒ + " as a Component");
               }

               NbtComponent.StorageNbtComponent â˜ƒ = (NbtComponent.StorageNbtComponent)â˜ƒ;
               â˜ƒ.addProperty("storage", â˜ƒ.getId().toString());
            }
         }

         return â˜ƒ;
      }

      private void serializeSeparator(JsonSerializationContext var1, JsonObject var2, Optional<Component> var3) {
         â˜ƒ.ifPresent(var3x -> â˜ƒ.add("separator", this.serialize(var3x, var3x.getClass(), â˜ƒ)));
      }

      public static String toJson(Component var0) {
         return GSON.toJson(â˜ƒ);
      }

      public static JsonElement toJsonTree(Component var0) {
         return GSON.toJsonTree(â˜ƒ);
      }

      @Nullable
      public static MutableComponent fromJson(String var0) {
         return GsonHelper.fromJson(GSON, â˜ƒ, MutableComponent.class, false);
      }

      @Nullable
      public static MutableComponent fromJson(JsonElement var0) {
         return GSON.fromJson(â˜ƒ, MutableComponent.class);
      }

      @Nullable
      public static MutableComponent fromJsonLenient(String var0) {
         return GsonHelper.fromJson(GSON, â˜ƒ, MutableComponent.class, true);
      }

      public static MutableComponent fromJson(com.mojang.brigadier.StringReader var0) {
         try {
            JsonReader â˜ƒ = new JsonReader(new StringReader(â˜ƒ.getRemaining()));
            â˜ƒ.setLenient(false);
            MutableComponent â˜ƒx = GSON.<MutableComponent>getAdapter(MutableComponent.class).read(â˜ƒ);
            â˜ƒ.setCursor(â˜ƒ.getCursor() + getPos(â˜ƒ));
            return â˜ƒx;
         } catch (StackOverflowError | IOException var3) {
            throw new JsonParseException(var3);
         }
      }

      private static int getPos(JsonReader var0) {
         try {
            return JSON_READER_POS.getInt(â˜ƒ) - JSON_READER_LINESTART.getInt(â˜ƒ) + 1;
         } catch (IllegalAccessException var2) {
            throw new IllegalStateException("Couldn't read position of JsonReader", var2);
         }
      }
   }
}
