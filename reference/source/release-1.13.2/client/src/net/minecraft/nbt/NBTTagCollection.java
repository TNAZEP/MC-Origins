package net.minecraft.nbt;

import java.util.AbstractList;

public abstract class NBTTagCollection<T extends INBTBase> extends AbstractList<T> implements INBTBase {
   public abstract int size();

   public T get(int var1) {
      return this.func_197647_c(☃);
   }

   public T set(int var1, T var2) {
      T ☃ = this.get(☃);
      this.func_197648_a(☃, ☃);
      return ☃;
   }

   public abstract T func_197647_c(int var1);

   public abstract void func_197648_a(int var1, INBTBase var2);

   public abstract void func_197649_b(int var1);
}
