package net.minecraft.client.settings;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.Util;

public class KeyBinding implements Comparable<KeyBinding> {
   private static final Map<String, KeyBinding> field_74516_a = Maps.newHashMap();
   private static final Map<InputMappings.Input, KeyBinding> field_74514_b = Maps.<InputMappings.Input, KeyBinding>newHashMap();
   private static final Set<String> field_151473_c = Sets.newHashSet();
   private static final Map<String, Integer> field_193627_d = Util.func_200696_a(Maps.newHashMap(), var0 -> {
      var0.put("key.categories.movement", 1);
      var0.put("key.categories.gameplay", 2);
      var0.put("key.categories.inventory", 3);
      var0.put("key.categories.creative", 4);
      var0.put("key.categories.multiplayer", 5);
      var0.put("key.categories.ui", 6);
      var0.put("key.categories.misc", 7);
   });
   private final String field_74515_c;
   private final InputMappings.Input field_151472_e;
   private final String field_151471_f;
   private InputMappings.Input field_74512_d;
   private boolean field_74513_e;
   private int field_151474_i;

   public static void func_197981_a(InputMappings.Input var0) {
      KeyBinding ☃ = (KeyBinding)field_74514_b.get(☃);
      if (☃ != null) {
         ++☃.field_151474_i;
      }
   }

   public static void func_197980_a(InputMappings.Input var0, boolean var1) {
      KeyBinding ☃ = (KeyBinding)field_74514_b.get(☃);
      if (☃ != null) {
         ☃.field_74513_e = ☃;
      }
   }

   public static void func_186704_a() {
      for(KeyBinding ☃ : field_74516_a.values()) {
         if (☃.field_74512_d.func_197938_b() == InputMappings.Type.KEYSYM && ☃.field_74512_d.func_197937_c() != -1) {
            ☃.field_74513_e = InputMappings.func_197956_a(☃.field_74512_d.func_197937_c());
         }
      }
   }

   public static void func_74506_a() {
      for(KeyBinding ☃ : field_74516_a.values()) {
         ☃.func_74505_d();
      }
   }

   public static void func_74508_b() {
      field_74514_b.clear();

      for(KeyBinding ☃ : field_74516_a.values()) {
         field_74514_b.put(☃.field_74512_d, ☃);
      }
   }

   public KeyBinding(String var1, int var2, String var3) {
      this(☃, InputMappings.Type.KEYSYM, ☃, ☃);
   }

   public KeyBinding(String var1, InputMappings.Type var2, int var3, String var4) {
      this.field_74515_c = ☃;
      this.field_74512_d = ☃.func_197944_a(☃);
      this.field_151472_e = this.field_74512_d;
      this.field_151471_f = ☃;
      field_74516_a.put(☃, this);
      field_74514_b.put(this.field_74512_d, this);
      field_151473_c.add(☃);
   }

   public boolean func_151470_d() {
      return this.field_74513_e;
   }

   public String func_151466_e() {
      return this.field_151471_f;
   }

   public boolean func_151468_f() {
      if (this.field_151474_i == 0) {
         return false;
      } else {
         --this.field_151474_i;
         return true;
      }
   }

   private void func_74505_d() {
      this.field_151474_i = 0;
      this.field_74513_e = false;
   }

   public String func_151464_g() {
      return this.field_74515_c;
   }

   public InputMappings.Input func_197977_i() {
      return this.field_151472_e;
   }

   public void func_197979_b(InputMappings.Input var1) {
      this.field_74512_d = ☃;
   }

   public int compareTo(KeyBinding var1) {
      return this.field_151471_f.equals(☃.field_151471_f)
         ? I18n.func_135052_a(this.field_74515_c).compareTo(I18n.func_135052_a(☃.field_74515_c))
         : ((Integer)field_193627_d.get(this.field_151471_f)).compareTo((Integer)field_193627_d.get(☃.field_151471_f));
   }

   public static Supplier<String> func_193626_b(String var0) {
      KeyBinding ☃ = (KeyBinding)field_74516_a.get(☃);
      return ☃ == null ? () -> ☃ : ☃::func_197978_k;
   }

   public boolean func_197983_b(KeyBinding var1) {
      return this.field_74512_d.equals(☃.field_74512_d);
   }

   public boolean func_197986_j() {
      return this.field_74512_d.equals(InputMappings.field_197958_a);
   }

   public boolean func_197976_a(int var1, int var2) {
      if (☃ == -1) {
         return this.field_74512_d.func_197938_b() == InputMappings.Type.SCANCODE && this.field_74512_d.func_197937_c() == ☃;
      } else {
         return this.field_74512_d.func_197938_b() == InputMappings.Type.KEYSYM && this.field_74512_d.func_197937_c() == ☃;
      }
   }

   public boolean func_197984_a(int var1) {
      return this.field_74512_d.func_197938_b() == InputMappings.Type.MOUSE && this.field_74512_d.func_197937_c() == ☃;
   }

   public String func_197978_k() {
      return this.field_74512_d.func_197936_a();
   }

   public boolean func_197985_l() {
      return this.field_74512_d.equals(this.field_151472_e);
   }

   public String func_197982_m() {
      return this.field_74512_d.func_197935_d();
   }
}
