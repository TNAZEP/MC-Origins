package net.minecraft.util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class EnumTypeAdapterFactory implements TypeAdapterFactory {
   @Nullable
   @Override
   public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
      Class<T> ☃ = (Class<T>)☃.getRawType();
      if (!☃.isEnum()) {
         return null;
      } else {
         final Map<String, T> ☃ = Maps.newHashMap();

         for(T ☃x : ☃.getEnumConstants()) {
            ☃.put(this.func_151232_a(☃x), ☃x);
         }

         return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter var1, T var2) throws IOException {
               if (☃ == null) {
                  ☃.nullValue();
               } else {
                  ☃.value(EnumTypeAdapterFactory.this.func_151232_a(☃));
               }
            }

            @Nullable
            @Override
            public T read(JsonReader var1) throws IOException {
               if (☃.peek() == JsonToken.NULL) {
                  ☃.nextNull();
                  return null;
               } else {
                  return (T)☃.get(☃.nextString());
               }
            }
         };
      }
   }

   private String func_151232_a(Object var1) {
      return ☃ instanceof Enum ? ((Enum)☃).name().toLowerCase(Locale.ROOT) : ☃.toString().toLowerCase(Locale.ROOT);
   }
}
