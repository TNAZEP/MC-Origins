package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerRecipeBook extends RecipeBook {
   private static final Logger field_192828_d = LogManager.getLogger();
   private final RecipeManager field_199641_f;

   public ServerRecipeBook(RecipeManager var1) {
      this.field_199641_f = ☃;
   }

   public int func_197926_a(Collection<IRecipe> var1, EntityPlayerMP var2) {
      List<ResourceLocation> ☃ = Lists.<ResourceLocation>newArrayList();
      int ☃x = 0;

      for(IRecipe ☃xx : ☃) {
         ResourceLocation ☃xxx = ☃xx.func_199560_c();
         if (!this.field_194077_a.contains(☃xxx) && !☃xx.func_192399_d()) {
            this.func_209118_a(☃xxx);
            this.func_209120_c(☃xxx);
            ☃.add(☃xxx);
            CriteriaTriggers.field_192126_f.func_192225_a(☃, ☃xx);
            ++☃x;
         }
      }

      this.func_194081_a(SPacketRecipeBook.State.ADD, ☃, ☃);
      return ☃x;
   }

   public int func_197925_b(Collection<IRecipe> var1, EntityPlayerMP var2) {
      List<ResourceLocation> ☃ = Lists.<ResourceLocation>newArrayList();
      int ☃x = 0;

      for(IRecipe ☃xx : ☃) {
         ResourceLocation ☃xxx = ☃xx.func_199560_c();
         if (this.field_194077_a.contains(☃xxx)) {
            this.func_209119_b(☃xxx);
            ☃.add(☃xxx);
            ++☃x;
         }
      }

      this.func_194081_a(SPacketRecipeBook.State.REMOVE, ☃, ☃);
      return ☃x;
   }

   private void func_194081_a(SPacketRecipeBook.State var1, EntityPlayerMP var2, List<ResourceLocation> var3) {
      ☃.field_71135_a
         .func_147359_a(
            new SPacketRecipeBook(☃, ☃, Collections.emptyList(), this.field_192818_b, this.field_192819_c, this.field_202885_e, this.field_202886_f)
         );
   }

   public NBTTagCompound func_192824_e() {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74757_a("isGuiOpen", this.field_192818_b);
      ☃.func_74757_a("isFilteringCraftable", this.field_192819_c);
      ☃.func_74757_a("isFurnaceGuiOpen", this.field_202885_e);
      ☃.func_74757_a("isFurnaceFilteringCraftable", this.field_202886_f);
      NBTTagList ☃x = new NBTTagList();

      for(ResourceLocation ☃xx : this.field_194077_a) {
         ☃x.add((INBTBase)(new NBTTagString(☃xx.toString())));
      }

      ☃.func_74782_a("recipes", ☃x);
      NBTTagList ☃xx = new NBTTagList();

      for(ResourceLocation ☃xxx : this.field_194078_b) {
         ☃xx.add((INBTBase)(new NBTTagString(☃xxx.toString())));
      }

      ☃.func_74782_a("toBeDisplayed", ☃xx);
      return ☃;
   }

   public void func_192825_a(NBTTagCompound var1) {
      this.field_192818_b = ☃.func_74767_n("isGuiOpen");
      this.field_192819_c = ☃.func_74767_n("isFilteringCraftable");
      this.field_202885_e = ☃.func_74767_n("isFurnaceGuiOpen");
      this.field_202886_f = ☃.func_74767_n("isFurnaceFilteringCraftable");
      NBTTagList ☃ = ☃.func_150295_c("recipes", 8);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         ResourceLocation ☃xx = new ResourceLocation(☃.func_150307_f(☃x));
         IRecipe ☃xxx = this.field_199641_f.func_199517_a(☃xx);
         if (☃xxx == null) {
            field_192828_d.error("Tried to load unrecognized recipe: {} removed now.", ☃xx);
         } else {
            this.func_194073_a(☃xxx);
         }
      }

      NBTTagList ☃x = ☃.func_150295_c("toBeDisplayed", 8);

      for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
         ResourceLocation ☃xxx = new ResourceLocation(☃x.func_150307_f(☃xx));
         IRecipe ☃xxxx = this.field_199641_f.func_199517_a(☃xxx);
         if (☃xxxx == null) {
            field_192828_d.error("Tried to load unrecognized recipe: {} removed now.", ☃xxx);
         } else {
            this.func_193825_e(☃xxxx);
         }
      }
   }

   public void func_192826_c(EntityPlayerMP var1) {
      ☃.field_71135_a
         .func_147359_a(
            new SPacketRecipeBook(
               SPacketRecipeBook.State.INIT,
               this.field_194077_a,
               this.field_194078_b,
               this.field_192818_b,
               this.field_192819_c,
               this.field_202885_e,
               this.field_202886_f
            )
         );
   }
}
