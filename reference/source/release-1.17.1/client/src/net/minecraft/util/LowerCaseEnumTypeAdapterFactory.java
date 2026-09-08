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

public class LowerCaseEnumTypeAdapterFactory implements TypeAdapterFactory {
   @Nullable
   @Override
   public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
      Class<T> â˜ƒ = (Class<T>)â˜ƒ.getRawType();
      if (!â˜ƒ.isEnum()) {
         return null;
      } else {
         final Map<String, T> â˜ƒ = Maps.newHashMap();

         for(T â˜ƒx : â˜ƒ.getEnumConstants()) {
            â˜ƒ.put(this.toLowercase(â˜ƒx), â˜ƒx);
         }

         return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter var1, T var2) throws IOException {
               if (â˜ƒ == null) {
                  â˜ƒ.nullValue();
               } else {
                  â˜ƒ.value(LowerCaseEnumTypeAdapterFactory.this.toLowercase(â˜ƒ));
               }
            }

            @Nullable
            @Override
            public T read(JsonReader var1) throws IOException {
               if (â˜ƒ.peek() == JsonToken.NULL) {
                  â˜ƒ.nextNull();
                  return null;
               } else {
                  return (T)â˜ƒ.get(â˜ƒ.nextString());
               }
            }
         };
      }
   }

   String toLowercase(Object var1) {
      return â˜ƒ instanceof Enum ? ((Enum)â˜ƒ).name().toLowerCase(Locale.ROOT) : â˜ƒ.toString().toLowerCase(Locale.ROOT);
   }
}
