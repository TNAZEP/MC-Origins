package net.minecraft.client.tutorial;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.toasts.TutorialToast;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class FindTreeStep implements ITutorialStep {
   private static final Set<Block> field_193268_a = Sets.<Block>newHashSet(
      Blocks.field_196617_K,
      Blocks.field_196618_L,
      Blocks.field_196619_M,
      Blocks.field_196620_N,
      Blocks.field_196621_O,
      Blocks.field_196623_P,
      Blocks.field_196626_Q,
      Blocks.field_196629_R,
      Blocks.field_196631_S,
      Blocks.field_196634_T,
      Blocks.field_196637_U,
      Blocks.field_196639_V,
      Blocks.field_196642_W,
      Blocks.field_196645_X,
      Blocks.field_196647_Y,
      Blocks.field_196648_Z,
      Blocks.field_196572_aa,
      Blocks.field_196574_ab
   );
   private static final ITextComponent field_193269_b = new TextComponentTranslation("tutorial.find_tree.title");
   private static final ITextComponent field_193270_c = new TextComponentTranslation("tutorial.find_tree.description");
   private final Tutorial field_193271_d;
   private TutorialToast field_193272_e;
   private int field_193273_f;

   public FindTreeStep(Tutorial var1) {
      this.field_193271_d = ☃;
   }

   @Override
   public void func_193245_a() {
      ++this.field_193273_f;
      if (this.field_193271_d.func_194072_f() != GameType.SURVIVAL) {
         this.field_193271_d.func_193292_a(TutorialSteps.NONE);
      } else {
         if (this.field_193273_f == 1) {
            EntityPlayerSP ☃ = this.field_193271_d.func_193295_e().field_71439_g;
            if (☃ != null) {
               for(Block ☃x : field_193268_a) {
                  if (☃.field_71071_by.func_70431_c(new ItemStack(☃x))) {
                     this.field_193271_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
                     return;
                  }
               }

               if (func_194070_a(☃)) {
                  this.field_193271_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
                  return;
               }
            }
         }

         if (this.field_193273_f >= 6000 && this.field_193272_e == null) {
            this.field_193272_e = new TutorialToast(TutorialToast.Icons.TREE, field_193269_b, field_193270_c, false);
            this.field_193271_d.func_193295_e().func_193033_an().func_192988_a(this.field_193272_e);
         }
      }
   }

   @Override
   public void func_193248_b() {
      if (this.field_193272_e != null) {
         this.field_193272_e.func_193670_a();
         this.field_193272_e = null;
      }
   }

   @Override
   public void func_193246_a(WorldClient var1, RayTraceResult var2) {
      if (☃.field_72313_a == RayTraceResult.Type.BLOCK && ☃.func_178782_a() != null) {
         IBlockState ☃ = ☃.func_180495_p(☃.func_178782_a());
         if (field_193268_a.contains(☃.func_177230_c())) {
            this.field_193271_d.func_193292_a(TutorialSteps.PUNCH_TREE);
         }
      }
   }

   @Override
   public void func_193252_a(ItemStack var1) {
      for(Block ☃ : field_193268_a) {
         if (☃.func_77973_b() == ☃.func_199767_j()) {
            this.field_193271_d.func_193292_a(TutorialSteps.CRAFT_PLANKS);
            return;
         }
      }
   }

   public static boolean func_194070_a(EntityPlayerSP var0) {
      for(Block ☃ : field_193268_a) {
         if (☃.func_146107_m().func_77444_a(StatList.field_188065_ae.func_199076_b(☃)) > 0) {
            return true;
         }
      }

      return false;
   }
}
