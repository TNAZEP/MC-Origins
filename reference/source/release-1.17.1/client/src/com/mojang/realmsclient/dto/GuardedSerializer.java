package com.mojang.realmsclient.dto;

import com.google.gson.Gson;
import javax.annotation.Nullable;

public class GuardedSerializer {
   private final Gson gson = new Gson();

   public String toJson(ReflectionBasedSerialization var1) {
      return this.gson.toJson(â˜ƒ);
   }

   @Nullable
   public <T extends ReflectionBasedSerialization> T fromJson(String var1, Class<T> var2) {
      return this.gson.fromJson(â˜ƒ, â˜ƒ);
   }
}
