package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.StringUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;

public class ItemWrittenBook extends Item {
   public ItemWrittenBook(Item.Properties var1) {
      super(☃);
   }

   public static boolean func_77828_a(@Nullable NBTTagCompound var0) {
      if (!ItemWritableBook.func_150930_a(☃)) {
         return false;
      } else if (!☃.func_150297_b("title", 8)) {
         return false;
      } else {
         String ☃ = ☃.func_74779_i("title");
         return ☃.length() > 32 ? false : ☃.func_150297_b("author", 8);
      }
   }

   public static int func_179230_h(ItemStack var0) {
      return ☃.func_77978_p().func_74762_e("generation");
   }

   @Override
   public ITextComponent func_200295_i(ItemStack var1) {
      if (☃.func_77942_o()) {
         NBTTagCompound ☃ = ☃.func_77978_p();
         String ☃x = ☃.func_74779_i("title");
         if (!StringUtils.func_151246_b(☃x)) {
            return new TextComponentString(☃x);
         }
      }

      return super.func_200295_i(☃);
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (!☃.field_72995_K) {
         this.func_179229_a(☃, ☃);
      }

      ☃.func_184814_a(☃, ☃);
      ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
      return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
   }

   private void func_179229_a(ItemStack var1, EntityPlayer var2) {
      NBTTagCompound ☃ = ☃.func_77978_p();
      if (☃ != null && !☃.func_74767_n("resolved")) {
         ☃.func_74757_a("resolved", true);
         if (func_77828_a(☃)) {
            NBTTagList ☃x = ☃.func_150295_c("pages", 8);

            for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
               String ☃xxx = ☃x.func_150307_f(☃xx);

               ITextComponent ☃;
               try {
                  ☃ = ITextComponent.Serializer.func_186877_b(☃xxx);
                  ☃ = TextComponentUtils.func_197680_a(☃.func_195051_bN(), ☃, ☃);
               } catch (Exception var9) {
                  ☃ = new TextComponentString(☃xxx);
               }

               ☃x.set(☃xx, (INBTBase)(new NBTTagString(ITextComponent.Serializer.func_150696_a(☃))));
            }

            ☃.func_74782_a("pages", ☃x);
            if (☃ instanceof EntityPlayerMP && ☃.func_184614_ca() == ☃) {
               Slot ☃xx = ☃.field_71070_bA.func_75147_a(☃.field_71071_by, ☃.field_71071_by.field_70461_c);
               ((EntityPlayerMP)☃).field_71135_a.func_147359_a(new SPacketSetSlot(0, ☃xx.field_75222_d, ☃));
            }
         }
      }
   }
}
