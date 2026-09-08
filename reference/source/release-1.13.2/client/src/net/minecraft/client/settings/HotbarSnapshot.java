package net.minecraft.client.settings;

import com.google.common.collect.ForwardingList;
import java.util.List;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;

public class HotbarSnapshot extends ForwardingList<ItemStack> {
   private final NonNullList<ItemStack> field_199424_a = NonNullList.func_191197_a(InventoryPlayer.func_70451_h(), ItemStack.field_190927_a);

   @Override
   protected List<ItemStack> delegate() {
      return this.field_199424_a;
   }

   public NBTTagList func_192834_a() {
      NBTTagList ☃ = new NBTTagList();

      for(ItemStack ☃x : this.delegate()) {
         ☃.add((INBTBase)☃x.func_77955_b(new NBTTagCompound()));
      }

      return ☃;
   }

   public void func_192833_a(NBTTagList var1) {
      List<ItemStack> ☃ = this.delegate();

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         ☃.set(☃x, ItemStack.func_199557_a(☃.func_150305_b(☃x)));
      }
   }

   @Override
   public boolean isEmpty() {
      for(ItemStack ☃ : this.delegate()) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }
}
