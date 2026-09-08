package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldTemplatePaginatedList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<WorldTemplate> templates;
   public int page;
   public int size;
   public int total;

   public WorldTemplatePaginatedList() {
   }

   public WorldTemplatePaginatedList(int var1) {
      this.templates = Collections.emptyList();
      this.page = 0;
      this.size = â˜ƒ;
      this.total = -1;
   }

   public boolean isLastPage() {
      return this.page * this.size >= this.total && this.page > 0 && this.total > 0 && this.size > 0;
   }

   public static WorldTemplatePaginatedList parse(String var0) {
      WorldTemplatePaginatedList â˜ƒ = new WorldTemplatePaginatedList();
      â˜ƒ.templates = Lists.<WorldTemplate>newArrayList();

      try {
         JsonParser â˜ƒx = new JsonParser();
         JsonObject â˜ƒxx = â˜ƒx.parse(â˜ƒ).getAsJsonObject();
         if (â˜ƒxx.get("templates").isJsonArray()) {
            Iterator<JsonElement> â˜ƒxxx = â˜ƒxx.get("templates").getAsJsonArray().iterator();

            while(â˜ƒxxx.hasNext()) {
               â˜ƒ.templates.add(WorldTemplate.parse(((JsonElement)â˜ƒxxx.next()).getAsJsonObject()));
            }
         }

         â˜ƒ.page = JsonUtils.getIntOr("page", â˜ƒxx, 0);
         â˜ƒ.size = JsonUtils.getIntOr("size", â˜ƒxx, 0);
         â˜ƒ.total = JsonUtils.getIntOr("total", â˜ƒxx, 0);
      } catch (Exception var5) {
         LOGGER.error("Could not parse WorldTemplatePaginatedList: {}", var5.getMessage());
      }

      return â˜ƒ;
   }
}
