package net.minecraft.server.management;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList<K, V extends UserListEntry<K>> {
   protected static final Logger field_152693_a = LogManager.getLogger();
   protected final Gson field_152694_b;
   private final File field_152695_c;
   private final Map<String, V> field_152696_d = Maps.newHashMap();
   private boolean field_152697_e = true;
   private static final ParameterizedType field_152698_f = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{UserListEntry.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };

   public UserList(File var1) {
      this.field_152695_c = ☃;
      GsonBuilder ☃ = new GsonBuilder().setPrettyPrinting();
      ☃.registerTypeHierarchyAdapter(UserListEntry.class, new UserList.Serializer());
      this.field_152694_b = ☃.create();
   }

   public boolean func_152689_b() {
      return this.field_152697_e;
   }

   public void func_152686_a(boolean var1) {
      this.field_152697_e = ☃;
   }

   public File func_152691_c() {
      return this.field_152695_c;
   }

   public void func_152687_a(V var1) {
      this.field_152696_d.put(this.func_152681_a(☃.func_152640_f()), ☃);

      try {
         this.func_152678_f();
      } catch (IOException var3) {
         field_152693_a.warn("Could not save the list after adding a user.", var3);
      }
   }

   @Nullable
   public V func_152683_b(K var1) {
      this.func_152680_h();
      return (V)this.field_152696_d.get(this.func_152681_a(☃));
   }

   public void func_152684_c(K var1) {
      this.field_152696_d.remove(this.func_152681_a(☃));

      try {
         this.func_152678_f();
      } catch (IOException var3) {
         field_152693_a.warn("Could not save the list after removing a user.", var3);
      }
   }

   public void func_199042_b(UserListEntry<K> var1) {
      this.func_152684_c(☃.func_152640_f());
   }

   public String[] func_152685_a() {
      return (String[])this.field_152696_d.keySet().toArray(new String[this.field_152696_d.size()]);
   }

   public boolean func_152690_d() {
      return this.field_152696_d.size() < 1;
   }

   protected String func_152681_a(K var1) {
      return ☃.toString();
   }

   protected boolean func_152692_d(K var1) {
      return this.field_152696_d.containsKey(this.func_152681_a(☃));
   }

   private void func_152680_h() {
      List<K> ☃ = Lists.<K>newArrayList();

      for(V ☃x : this.field_152696_d.values()) {
         if (☃x.func_73682_e()) {
            ☃.add(☃x.func_152640_f());
         }
      }

      for(K ☃x : ☃) {
         this.field_152696_d.remove(this.func_152681_a(☃x));
      }
   }

   protected UserListEntry<K> func_152682_a(JsonObject var1) {
      return new UserListEntry<>((K)null, ☃);
   }

   public Collection<V> func_199043_f() {
      return this.field_152696_d.values();
   }

   public void func_152678_f() throws IOException {
      Collection<V> ☃ = this.field_152696_d.values();
      String ☃x = this.field_152694_b.toJson(☃);
      BufferedWriter ☃xx = null;

      try {
         ☃xx = Files.newWriter(this.field_152695_c, StandardCharsets.UTF_8);
         ☃xx.write(☃x);
      } finally {
         IOUtils.closeQuietly(☃xx);
      }
   }

   public void func_152679_g() throws FileNotFoundException {
      if (this.field_152695_c.exists()) {
         BufferedReader ☃ = null;

         try {
            ☃ = Files.newReader(this.field_152695_c, StandardCharsets.UTF_8);
            Collection<UserListEntry<K>> ☃x = JsonUtils.func_193841_a(this.field_152694_b, ☃, field_152698_f);
            if (☃x != null) {
               this.field_152696_d.clear();

               for(UserListEntry<K> ☃xx : ☃x) {
                  if (☃xx.func_152640_f() != null) {
                     this.field_152696_d.put(this.func_152681_a(☃xx.func_152640_f()), ☃xx);
                  }
               }
            }
         } finally {
            IOUtils.closeQuietly(☃);
         }
      }
   }

   class Serializer implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>> {
      private Serializer() {
      }

      public JsonElement serialize(UserListEntry<K> var1, Type var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();
         ☃.func_152641_a(☃);
         return ☃;
      }

      public UserListEntry<K> deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (☃.isJsonObject()) {
            JsonObject ☃ = ☃.getAsJsonObject();
            return UserList.this.func_152682_a(☃);
         } else {
            return null;
         }
      }
   }
}
