package net.minecraft.world.storage.loot.properties;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.ResourceLocation;

public class EntityPropertyManager {
   private static final Map<ResourceLocation, EntityProperty.Serializer<?>> field_186647_a = Maps.<ResourceLocation, EntityProperty.Serializer<?>>newHashMap();
   private static final Map<Class<? extends EntityProperty>, EntityProperty.Serializer<?>> field_186648_b = Maps.newHashMap();

   public static <T extends EntityProperty> void func_186644_a(EntityProperty.Serializer<? extends T> var0) {
      ResourceLocation ☃ = ☃.func_186649_a();
      Class<T> ☃x = ☃.func_186651_b();
      if (field_186647_a.containsKey(☃)) {
         throw new IllegalArgumentException("Can't re-register entity property name " + ☃);
      } else if (field_186648_b.containsKey(☃x)) {
         throw new IllegalArgumentException("Can't re-register entity property class " + ☃x.getName());
      } else {
         field_186647_a.put(☃, ☃);
         field_186648_b.put(☃x, ☃);
      }
   }

   public static EntityProperty.Serializer<?> func_186646_a(ResourceLocation var0) {
      EntityProperty.Serializer<?> ☃ = (EntityProperty.Serializer)field_186647_a.get(☃);
      if (☃ == null) {
         throw new IllegalArgumentException("Unknown loot entity property '" + ☃ + "'");
      } else {
         return ☃;
      }
   }

   public static <T extends EntityProperty> EntityProperty.Serializer<T> func_186645_a(T var0) {
      EntityProperty.Serializer<?> ☃ = (EntityProperty.Serializer)field_186648_b.get(☃.getClass());
      if (☃ == null) {
         throw new IllegalArgumentException("Unknown loot entity property " + ☃);
      } else {
         return ☃;
      }
   }

   static {
      func_186644_a(new EntityOnFire.Serializer());
   }
}
