package net.minecraft.stats;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class Stat<T> extends ObjectiveCriteria {
   private final StatFormatter formatter;
   private final T value;
   private final StatType<T> type;

   protected Stat(StatType<T> var1, T var2, StatFormatter var3) {
      super(buildName(â˜ƒ, â˜ƒ));
      this.type = â˜ƒ;
      this.formatter = â˜ƒ;
      this.value = â˜ƒ;
   }

   public static <T> String buildName(StatType<T> var0, T var1) {
      return locationToKey(Registry.STAT_TYPE.getKey(â˜ƒ)) + ":" + locationToKey(â˜ƒ.getRegistry().getKey(â˜ƒ));
   }

   private static <T> String locationToKey(@Nullable ResourceLocation var0) {
      return â˜ƒ.toString().replace(':', '.');
   }

   public StatType<T> getType() {
      return this.type;
   }

   public T getValue() {
      return this.value;
   }

   public String format(int var1) {
      return this.formatter.format(â˜ƒ);
   }

   public boolean equals(Object var1) {
      return this == â˜ƒ || â˜ƒ instanceof Stat && Objects.equals(this.getName(), ((Stat)â˜ƒ).getName());
   }

   public int hashCode() {
      return this.getName().hashCode();
   }

   public String toString() {
      return "Stat{name=" + this.getName() + ", formatter=" + this.formatter + "}";
   }
}
