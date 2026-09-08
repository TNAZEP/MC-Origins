package com.mojang.realmsclient.dto;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Set;

public class Ops extends ValueObject {
   public Set<String> ops = Sets.newHashSet();

   public static Ops parse(String var0) {
      Ops â˜ƒ = new Ops();
      JsonParser â˜ƒx = new JsonParser();

      try {
         JsonElement â˜ƒxx = â˜ƒx.parse(â˜ƒ);
         JsonObject â˜ƒxxx = â˜ƒxx.getAsJsonObject();
         JsonElement â˜ƒxxxx = â˜ƒxxx.get("ops");
         if (â˜ƒxxxx.isJsonArray()) {
            for(JsonElement â˜ƒxxxxx : â˜ƒxxxx.getAsJsonArray()) {
               â˜ƒ.ops.add(â˜ƒxxxxx.getAsString());
            }
         }
      } catch (Exception var8) {
      }

      return â˜ƒ;
   }
}
