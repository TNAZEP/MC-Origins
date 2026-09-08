package net.minecraft.item;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.lang3.StringUtils;

public class ItemSkull extends ItemWallOrFloor {
   public ItemSkull(Block var1, Block var2, Item.Properties var3) {
      super(☃, ☃, ☃);
   }

   @Override
   public ITextComponent func_200295_i(ItemStack var1) {
      if (☃.func_77973_b() == Items.field_196184_dx && ☃.func_77942_o()) {
         String ☃ = null;
         NBTTagCompound ☃x = ☃.func_77978_p();
         if (☃x.func_150297_b("SkullOwner", 8)) {
            ☃ = ☃x.func_74779_i("SkullOwner");
         } else if (☃x.func_150297_b("SkullOwner", 10)) {
            NBTTagCompound ☃ = ☃x.func_74775_l("SkullOwner");
            if (☃.func_150297_b("Name", 8)) {
               ☃ = ☃.func_74779_i("Name");
            }
         }

         if (☃ != null) {
            return new TextComponentTranslation(this.func_77658_a() + ".named", ☃);
         }
      }

      return super.func_200295_i(☃);
   }

   @Override
   public boolean func_179215_a(NBTTagCompound var1) {
      super.func_179215_a(☃);
      if (☃.func_150297_b("SkullOwner", 8) && !StringUtils.isBlank(☃.func_74779_i("SkullOwner"))) {
         GameProfile ☃ = new GameProfile(null, ☃.func_74779_i("SkullOwner"));
         ☃ = TileEntitySkull.func_174884_b(☃);
         ☃.func_74782_a("SkullOwner", NBTUtil.func_180708_a(new NBTTagCompound(), ☃));
         return true;
      } else {
         return false;
      }
   }
}
