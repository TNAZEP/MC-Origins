package net.minecraft.client;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class KeyMapping implements Comparable<KeyMapping> {
   private static final Map<String, KeyMapping> ALL = Maps.newHashMap();
   private static final Map<InputConstants.Key, KeyMapping> MAP = Maps.<InputConstants.Key, KeyMapping>newHashMap();
   private static final Set<String> CATEGORIES = Sets.newHashSet();
   public static final String CATEGORY_MOVEMENT = "key.categories.movement";
   public static final String CATEGORY_MISC = "key.categories.misc";
   public static final String CATEGORY_MULTIPLAYER = "key.categories.multiplayer";
   public static final String CATEGORY_GAMEPLAY = "key.categories.gameplay";
   public static final String CATEGORY_INVENTORY = "key.categories.inventory";
   public static final String CATEGORY_INTERFACE = "key.categories.ui";
   public static final String CATEGORY_CREATIVE = "key.categories.creative";
   private static final Map<String, Integer> CATEGORY_SORT_ORDER = Util.make(Maps.newHashMap(), var0 -> {
      var0.put("key.categories.movement", 1);
      var0.put("key.categories.gameplay", 2);
      var0.put("key.categories.inventory", 3);
      var0.put("key.categories.creative", 4);
      var0.put("key.categories.multiplayer", 5);
      var0.put("key.categories.ui", 6);
      var0.put("key.categories.misc", 7);
   });
   private final String name;
   private final InputConstants.Key defaultKey;
   private final String category;
   private InputConstants.Key key;
   private boolean isDown;
   private int clickCount;

   public static void click(InputConstants.Key var0) {
      KeyMapping â˜ƒ = (KeyMapping)MAP.get(â˜ƒ);
      if (â˜ƒ != null) {
         ++â˜ƒ.clickCount;
      }
   }

   public static void set(InputConstants.Key var0, boolean var1) {
      KeyMapping â˜ƒ = (KeyMapping)MAP.get(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.setDown(â˜ƒ);
      }
   }

   public static void setAll() {
      for(KeyMapping â˜ƒ : ALL.values()) {
         if (â˜ƒ.key.getType() == InputConstants.Type.KEYSYM && â˜ƒ.key.getValue() != InputConstants.UNKNOWN.getValue()) {
            â˜ƒ.setDown(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), â˜ƒ.key.getValue()));
         }
      }
   }

   public static void releaseAll() {
      for(KeyMapping â˜ƒ : ALL.values()) {
         â˜ƒ.release();
      }
   }

   public static void resetMapping() {
      MAP.clear();

      for(KeyMapping â˜ƒ : ALL.values()) {
         MAP.put(â˜ƒ.key, â˜ƒ);
      }
   }

   public KeyMapping(String var1, int var2, String var3) {
      this(â˜ƒ, InputConstants.Type.KEYSYM, â˜ƒ, â˜ƒ);
   }

   public KeyMapping(String var1, InputConstants.Type var2, int var3, String var4) {
      this.name = â˜ƒ;
      this.key = â˜ƒ.getOrCreate(â˜ƒ);
      this.defaultKey = this.key;
      this.category = â˜ƒ;
      ALL.put(â˜ƒ, this);
      MAP.put(this.key, this);
      CATEGORIES.add(â˜ƒ);
   }

   public boolean isDown() {
      return this.isDown;
   }

   public String getCategory() {
      return this.category;
   }

   public boolean consumeClick() {
      if (this.clickCount == 0) {
         return false;
      } else {
         --this.clickCount;
         return true;
      }
   }

   private void release() {
      this.clickCount = 0;
      this.setDown(false);
   }

   public String getName() {
      return this.name;
   }

   public InputConstants.Key getDefaultKey() {
      return this.defaultKey;
   }

   public void setKey(InputConstants.Key var1) {
      this.key = â˜ƒ;
   }

   public int compareTo(KeyMapping var1) {
      return this.category.equals(â˜ƒ.category)
         ? I18n.get(this.name).compareTo(I18n.get(â˜ƒ.name))
         : ((Integer)CATEGORY_SORT_ORDER.get(this.category)).compareTo((Integer)CATEGORY_SORT_ORDER.get(â˜ƒ.category));
   }

   public static Supplier<Component> createNameSupplier(String var0) {
      KeyMapping â˜ƒ = (KeyMapping)ALL.get(â˜ƒ);
      return â˜ƒ == null ? () -> new TranslatableComponent(â˜ƒ) : â˜ƒ::getTranslatedKeyMessage;
   }

   public boolean same(KeyMapping var1) {
      return this.key.equals(â˜ƒ.key);
   }

   public boolean isUnbound() {
      return this.key.equals(InputConstants.UNKNOWN);
   }

   public boolean matches(int var1, int var2) {
      if (â˜ƒ == InputConstants.UNKNOWN.getValue()) {
         return this.key.getType() == InputConstants.Type.SCANCODE && this.key.getValue() == â˜ƒ;
      } else {
         return this.key.getType() == InputConstants.Type.KEYSYM && this.key.getValue() == â˜ƒ;
      }
   }

   public boolean matchesMouse(int var1) {
      return this.key.getType() == InputConstants.Type.MOUSE && this.key.getValue() == â˜ƒ;
   }

   public Component getTranslatedKeyMessage() {
      return this.key.getDisplayName();
   }

   public boolean isDefault() {
      return this.key.equals(this.defaultKey);
   }

   public String saveString() {
      return this.key.getName();
   }

   public void setDown(boolean var1) {
      this.isDown = â˜ƒ;
   }
}
