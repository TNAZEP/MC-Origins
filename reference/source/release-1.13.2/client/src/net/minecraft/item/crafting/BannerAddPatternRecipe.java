package net.minecraft.item.crafting;

import javax.annotation.Nullable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BannerAddPatternRecipe extends IRecipeHidden {
   public BannerAddPatternRecipe(ResourceLocation var1) {
      super(☃);
   }

   @Override
   public boolean func_77569_a(IInventory var1, World var2) {
      if (!(☃ instanceof InventoryCrafting)) {
         return false;
      } else {
         boolean ☃ = false;

         for(int ☃x = 0; ☃x < ☃.func_70302_i_(); ++☃x) {
            ItemStack ☃xx = ☃.func_70301_a(☃x);
            if (☃xx.func_77973_b() instanceof ItemBanner) {
               if (☃) {
                  return false;
               }

               if (TileEntityBanner.func_175113_c(☃xx) >= 6) {
                  return false;
               }

               ☃ = true;
            }
         }

         return ☃ && this.func_201838_c(☃) != null;
      }
   }

   @Override
   public ItemStack func_77572_b(IInventory var1) {
      ItemStack ☃ = ItemStack.field_190927_a;

      for(int ☃x = 0; ☃x < ☃.func_70302_i_(); ++☃x) {
         ItemStack ☃xx = ☃.func_70301_a(☃x);
         if (!☃xx.func_190926_b() && ☃xx.func_77973_b() instanceof ItemBanner) {
            ☃ = ☃xx.func_77946_l();
            ☃.func_190920_e(1);
            break;
         }
      }

      BannerPattern ☃x = this.func_201838_c(☃);
      if (☃x != null) {
         EnumDyeColor ☃xx = EnumDyeColor.WHITE;

         for(int ☃xxx = 0; ☃xxx < ☃.func_70302_i_(); ++☃xxx) {
            Item ☃xxxx = ☃.func_70301_a(☃xxx).func_77973_b();
            if (☃xxxx instanceof ItemDye) {
               ☃xx = ((ItemDye)☃xxxx).func_195962_g();
               break;
            }
         }

         NBTTagCompound ☃xxxx = ☃.func_190925_c("BlockEntityTag");
         NBTTagList ☃xxx;
         if (☃xxxx.func_150297_b("Patterns", 9)) {
            ☃xxx = ☃xxxx.func_150295_c("Patterns", 10);
         } else {
            ☃xxx = new NBTTagList();
            ☃xxxx.func_74782_a("Patterns", ☃xxx);
         }

         NBTTagCompound ☃xxx = new NBTTagCompound();
         ☃xxx.func_74778_a("Pattern", ☃x.func_190993_b());
         ☃xxx.func_74768_a("Color", ☃xx.func_196059_a());
         ☃xxx.add((INBTBase)☃xxx);
      }

      return ☃;
   }

   @Nullable
   private BannerPattern func_201838_c(IInventory var1) {
      for(BannerPattern ☃ : BannerPattern.values()) {
         if (☃.func_191000_d()) {
            boolean ☃x = true;
            if (☃.func_190999_e()) {
               boolean ☃xx = false;
               boolean ☃xxx = false;

               for(int ☃xxxx = 0; ☃xxxx < ☃.func_70302_i_() && ☃x; ++☃xxxx) {
                  ItemStack ☃xxxxx = ☃.func_70301_a(☃xxxx);
                  if (!☃xxxxx.func_190926_b() && !(☃xxxxx.func_77973_b() instanceof ItemBanner)) {
                     if (☃xxxxx.func_77973_b() instanceof ItemDye) {
                        if (☃xxx) {
                           ☃x = false;
                           break;
                        }

                        ☃xxx = true;
                     } else {
                        if (☃xx || !☃xxxxx.func_77969_a(☃.func_190998_f())) {
                           ☃x = false;
                           break;
                        }

                        ☃xx = true;
                     }
                  }
               }

               if (!☃xx || !☃xxx) {
                  ☃x = false;
               }
            } else if (☃.func_70302_i_() == ☃.func_190996_c().length * ☃.func_190996_c()[0].length()) {
               EnumDyeColor ☃x = null;

               for(int ☃xx = 0; ☃xx < ☃.func_70302_i_() && ☃x; ++☃xx) {
                  int ☃xxx = ☃xx / 3;
                  int ☃xxxx = ☃xx % 3;
                  ItemStack ☃xxxxx = ☃.func_70301_a(☃xx);
                  Item ☃xxxxxx = ☃xxxxx.func_77973_b();
                  if (!☃xxxxx.func_190926_b() && !(☃xxxxxx instanceof ItemBanner)) {
                     if (!(☃xxxxxx instanceof ItemDye)) {
                        ☃x = false;
                        break;
                     }

                     EnumDyeColor ☃xxxxxxx = ((ItemDye)☃xxxxxx).func_195962_g();
                     if (☃x != null && ☃x != ☃xxxxxxx) {
                        ☃x = false;
                        break;
                     }

                     if (☃.func_190996_c()[☃xxx].charAt(☃xxxx) == ' ') {
                        ☃x = false;
                        break;
                     }

                     ☃x = ☃xxxxxxx;
                  } else if (☃.func_190996_c()[☃xxx].charAt(☃xxxx) != ' ') {
                     ☃x = false;
                     break;
                  }
               }
            } else {
               ☃x = false;
            }

            if (☃x) {
               return ☃;
            }
         }
      }

      return null;
   }

   @Override
   public boolean func_194133_a(int var1, int var2) {
      return ☃ >= 3 && ☃ >= 3;
   }

   @Override
   public IRecipeSerializer<?> func_199559_b() {
      return RecipeSerializers.field_199587_m;
   }
}
