package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class ItemDye extends Item {
   private static final Map<EnumDyeColor, ItemDye> field_195963_a = Maps.newEnumMap(EnumDyeColor.class);
   private final EnumDyeColor field_195964_b;

   public ItemDye(EnumDyeColor var1, Item.Properties var2) {
      super(☃);
      this.field_195964_b = ☃;
      field_195963_a.put(☃, this);
   }

   @Override
   public boolean func_111207_a(ItemStack var1, EntityPlayer var2, EntityLivingBase var3, EnumHand var4) {
      if (☃ instanceof EntitySheep) {
         EntitySheep ☃ = (EntitySheep)☃;
         if (!☃.func_70892_o() && ☃.func_175509_cj() != this.field_195964_b) {
            ☃.func_175512_b(this.field_195964_b);
            ☃.func_190918_g(1);
         }

         return true;
      } else {
         return false;
      }
   }

   public EnumDyeColor func_195962_g() {
      return this.field_195964_b;
   }

   public static ItemDye func_195961_a(EnumDyeColor var0) {
      return (ItemDye)field_195963_a.get(☃);
   }
}
