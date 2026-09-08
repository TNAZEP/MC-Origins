package net.minecraft.stats;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class Stat<T> extends ScoreCriteria {
   private final IStatFormater field_75976_b;
   private final T field_197922_p;
   private final StatType<T> field_197923_q;

   protected Stat(StatType<T> var1, T var2, IStatFormater var3) {
      super(func_197918_a(☃, ☃));
      this.field_197923_q = ☃;
      this.field_75976_b = ☃;
      this.field_197922_p = ☃;
   }

   public static <T> String func_197918_a(StatType<T> var0, T var1) {
      return func_197919_a(IRegistry.field_212634_w.func_177774_c(☃)) + ":" + func_197919_a(☃.func_199080_a().func_177774_c(☃));
   }

   private static <T> String func_197919_a(@Nullable ResourceLocation var0) {
      return ☃.toString().replace(':', '.');
   }

   public StatType<T> func_197921_a() {
      return this.field_197923_q;
   }

   public T func_197920_b() {
      return this.field_197922_p;
   }

   public String func_75968_a(int var1) {
      return this.field_75976_b.format(☃);
   }

   public boolean equals(Object var1) {
      return this == ☃ || ☃ instanceof Stat && Objects.equals(this.func_96636_a(), ((Stat)☃).func_96636_a());
   }

   public int hashCode() {
      return this.func_96636_a().hashCode();
   }

   public String toString() {
      return "Stat{name=" + this.func_96636_a() + ", formatter=" + this.field_75976_b + '}';
   }
}
