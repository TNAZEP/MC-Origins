package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class ValueObject {
   public String toString() {
      StringBuilder â˜ƒ = new StringBuilder("{");

      for(Field â˜ƒx : this.getClass().getFields()) {
         if (!isStatic(â˜ƒx)) {
            try {
               â˜ƒ.append(getName(â˜ƒx)).append("=").append(â˜ƒx.get(this)).append(" ");
            } catch (IllegalAccessException var7) {
            }
         }
      }

      â˜ƒ.deleteCharAt(â˜ƒ.length() - 1);
      â˜ƒ.append('}');
      return â˜ƒ.toString();
   }

   private static String getName(Field var0) {
      SerializedName â˜ƒ = (SerializedName)â˜ƒ.getAnnotation(SerializedName.class);
      return â˜ƒ != null ? â˜ƒ.value() : â˜ƒ.getName();
   }

   private static boolean isStatic(Field var0) {
      return Modifier.isStatic(â˜ƒ.getModifiers());
   }
}
