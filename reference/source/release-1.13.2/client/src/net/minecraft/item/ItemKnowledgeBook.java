package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemKnowledgeBook extends Item {
   private static final Logger field_194126_a = LogManager.getLogger();

   public ItemKnowledgeBook(Item.Properties var1) {
      super(☃);
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      NBTTagCompound ☃x = ☃.func_77978_p();
      if (!☃.field_71075_bZ.field_75098_d) {
         ☃.func_184611_a(☃, ItemStack.field_190927_a);
      }

      if (☃x != null && ☃x.func_150297_b("Recipes", 9)) {
         if (!☃.field_72995_K) {
            NBTTagList ☃ = ☃x.func_150295_c("Recipes", 8);
            List<IRecipe> ☃x = Lists.<IRecipe>newArrayList();

            for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
               String ☃xxx = ☃.func_150307_f(☃xx);
               IRecipe ☃xxxx = ☃.func_73046_m().func_199529_aN().func_199517_a(new ResourceLocation(☃xxx));
               if (☃xxxx == null) {
                  field_194126_a.error("Invalid recipe: {}", ☃xxx);
                  return new ActionResult<>(EnumActionResult.FAIL, ☃);
               }

               ☃x.add(☃xxxx);
            }

            ☃.func_195065_a(☃x);
            ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
         }

         return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
      } else {
         field_194126_a.error("Tag not valid: {}", ☃x);
         return new ActionResult<>(EnumActionResult.FAIL, ☃);
      }
   }
}
