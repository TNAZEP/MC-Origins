package net.minecraft.command.arguments;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentTranslation;

public class ItemInput implements Predicate<ItemStack> {
   private static final Dynamic2CommandExceptionType field_197322_a = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("arguments.item.overstacked", var0, var1)
   );
   private final Item field_197323_b;
   @Nullable
   private final NBTTagCompound field_197324_c;

   public ItemInput(Item var1, @Nullable NBTTagCompound var2) {
      this.field_197323_b = ☃;
      this.field_197324_c = ☃;
   }

   public Item func_197319_a() {
      return this.field_197323_b;
   }

   public boolean test(ItemStack var1) {
      return ☃.func_77973_b() == this.field_197323_b && NBTUtil.func_181123_a(this.field_197324_c, ☃.func_77978_p(), true);
   }

   public ItemStack func_197320_a(int var1, boolean var2) throws CommandSyntaxException {
      ItemStack ☃ = new ItemStack(this.field_197323_b, ☃);
      if (this.field_197324_c != null) {
         ☃.func_77982_d(this.field_197324_c);
      }

      if (☃ && ☃ > ☃.func_77976_d()) {
         throw field_197322_a.create(IRegistry.field_212630_s.func_177774_c(this.field_197323_b), ☃.func_77976_d());
      } else {
         return ☃;
      }
   }

   public String func_197321_c() {
      StringBuilder ☃ = new StringBuilder(IRegistry.field_212630_s.func_148757_b(this.field_197323_b));
      if (this.field_197324_c != null) {
         ☃.append(this.field_197324_c);
      }

      return ☃.toString();
   }
}
