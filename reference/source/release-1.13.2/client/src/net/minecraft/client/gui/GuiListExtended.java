package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.AbstractList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;

public abstract class GuiListExtended<E extends GuiListExtended.IGuiListEntry<E>> extends GuiSlot {
   private final List<E> field_195087_v = new GuiListExtended.UpdatingList();

   public GuiListExtended(Minecraft var1, int var2, int var3, int var4, int var5, int var6) {
      super(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected boolean func_195078_a(int var1, int var2, double var3, double var5) {
      return this.func_148180_b(☃).mouseClicked(☃, ☃, ☃);
   }

   @Override
   protected boolean func_148131_a(int var1) {
      return false;
   }

   @Override
   protected void func_148123_a() {
   }

   @Override
   protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
      this.func_148180_b(☃)
         .func_194999_a(this.func_148139_c(), ☃, ☃, ☃, this.func_195079_b((double)☃, (double)☃) && this.func_195083_a((double)☃, (double)☃) == ☃, ☃);
   }

   @Override
   protected void func_192639_a(int var1, int var2, int var3, float var4) {
      this.func_148180_b(☃).func_195000_a(☃);
   }

   @Override
   public final List<E> func_195074_b() {
      return this.field_195087_v;
   }

   protected final void func_195086_c() {
      this.field_195087_v.clear();
   }

   private E func_148180_b(int var1) {
      return (E)this.func_195074_b().get(☃);
   }

   protected final void func_195085_a(E var1) {
      this.field_195087_v.add(☃);
   }

   @Override
   public void func_195080_b(int var1) {
      this.field_148168_r = ☃;
      this.field_148167_s = Util.func_211177_b();
   }

   @Override
   protected final int func_148127_b() {
      return this.func_195074_b().size();
   }

   public abstract static class IGuiListEntry<E extends GuiListExtended.IGuiListEntry<E>> implements IGuiEventListener {
      protected GuiListExtended<E> field_195004_a;
      protected int field_195005_b;

      protected GuiListExtended<E> func_194998_a() {
         return this.field_195004_a;
      }

      protected int func_195003_b() {
         return this.field_195005_b;
      }

      protected int func_195001_c() {
         return this.field_195004_a.field_148153_b
            + 4
            - this.field_195004_a.func_148148_g()
            + this.field_195005_b * this.field_195004_a.field_148149_f
            + this.field_195004_a.field_148160_j;
      }

      protected int func_195002_d() {
         return this.field_195004_a.field_148152_e + this.field_195004_a.field_148155_a / 2 - this.field_195004_a.func_148139_c() / 2 + 2;
      }

      protected void func_195000_a(float var1) {
      }

      public abstract void func_194999_a(int var1, int var2, int var3, int var4, boolean var5, float var6);
   }

   class UpdatingList extends AbstractList<E> {
      private final List<E> field_198174_b = Lists.<E>newArrayList();

      private UpdatingList() {
      }

      public E get(int var1) {
         return (E)this.field_198174_b.get(☃);
      }

      public int size() {
         return this.field_198174_b.size();
      }

      public E set(int var1, E var2) {
         E ☃ = (E)this.field_198174_b.set(☃, ☃);
         ☃.field_195004_a = GuiListExtended.this;
         ☃.field_195005_b = ☃;
         return ☃;
      }

      public void add(int var1, E var2) {
         this.field_198174_b.add(☃, ☃);
         ☃.field_195004_a = GuiListExtended.this;
         ☃.field_195005_b = ☃;
         int ☃ = ☃ + 1;

         while(☃ < this.size()) {
            this.get(☃).field_195005_b = ☃++;
         }
      }

      public E remove(int var1) {
         E ☃ = (E)this.field_198174_b.remove(☃);
         int ☃x = ☃;

         while(☃x < this.size()) {
            this.get(☃x).field_195005_b = ☃x++;
         }

         return ☃;
      }
   }
}
