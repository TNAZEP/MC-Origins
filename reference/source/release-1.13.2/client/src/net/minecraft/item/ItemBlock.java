package net.minecraft.item;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ItemBlock extends Item {
   @Deprecated
   private final Block field_150939_a;

   public ItemBlock(Block var1, Item.Properties var2) {
      super(☃);
      this.field_150939_a = ☃;
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      return this.func_195942_a(new BlockItemUseContext(☃));
   }

   public EnumActionResult func_195942_a(BlockItemUseContext var1) {
      if (!☃.func_196011_b()) {
         return EnumActionResult.FAIL;
      } else {
         IBlockState ☃ = this.func_195945_b(☃);
         if (☃ == null) {
            return EnumActionResult.FAIL;
         } else if (!this.func_195941_b(☃, ☃)) {
            return EnumActionResult.FAIL;
         } else {
            BlockPos ☃ = ☃.func_195995_a();
            World ☃x = ☃.func_195991_k();
            EntityPlayer ☃xx = ☃.func_195999_j();
            ItemStack ☃xxx = ☃.func_195996_i();
            IBlockState ☃xxxx = ☃x.func_180495_p(☃);
            Block ☃xxxxx = ☃xxxx.func_177230_c();
            if (☃xxxxx == ☃.func_177230_c()) {
               this.func_195943_a(☃, ☃x, ☃xx, ☃xxx, ☃xxxx);
               ☃xxxxx.func_180633_a(☃x, ☃, ☃xxxx, ☃xx, ☃xxx);
               if (☃xx instanceof EntityPlayerMP) {
                  CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)☃xx, ☃, ☃xxx);
               }
            }

            SoundType ☃ = ☃xxxxx.func_185467_w();
            ☃x.func_184133_a(☃xx, ☃, ☃.func_185841_e(), SoundCategory.BLOCKS, (☃.func_185843_a() + 1.0F) / 2.0F, ☃.func_185847_b() * 0.8F);
            ☃xxx.func_190918_g(1);
            return EnumActionResult.SUCCESS;
         }
      }
   }

   protected boolean func_195943_a(BlockPos var1, World var2, @Nullable EntityPlayer var3, ItemStack var4, IBlockState var5) {
      return func_179224_a(☃, ☃, ☃, ☃);
   }

   @Nullable
   protected IBlockState func_195945_b(BlockItemUseContext var1) {
      IBlockState ☃ = this.func_179223_d().func_196258_a(☃);
      return ☃ != null && this.func_195944_a(☃, ☃) ? ☃ : null;
   }

   protected boolean func_195944_a(BlockItemUseContext var1, IBlockState var2) {
      return ☃.func_196955_c(☃.func_195991_k(), ☃.func_195995_a()) && ☃.func_195991_k().func_195584_a(☃, ☃.func_195995_a());
   }

   protected boolean func_195941_b(BlockItemUseContext var1, IBlockState var2) {
      return ☃.func_195991_k().func_180501_a(☃.func_195995_a(), ☃, 11);
   }

   public static boolean func_179224_a(World var0, @Nullable EntityPlayer var1, BlockPos var2, ItemStack var3) {
      MinecraftServer ☃ = ☃.func_73046_m();
      if (☃ == null) {
         return false;
      } else {
         NBTTagCompound ☃ = ☃.func_179543_a("BlockEntityTag");
         if (☃ != null) {
            TileEntity ☃x = ☃.func_175625_s(☃);
            if (☃x != null) {
               if (!☃.field_72995_K && ☃x.func_183000_F() && (☃ == null || !☃.func_195070_dx())) {
                  return false;
               }

               NBTTagCompound ☃xx = ☃x.func_189515_b(new NBTTagCompound());
               NBTTagCompound ☃xxx = ☃xx.func_74737_b();
               ☃xx.func_197643_a(☃);
               ☃xx.func_74768_a("x", ☃.func_177958_n());
               ☃xx.func_74768_a("y", ☃.func_177956_o());
               ☃xx.func_74768_a("z", ☃.func_177952_p());
               if (!☃xx.equals(☃xxx)) {
                  ☃x.func_145839_a(☃xx);
                  ☃x.func_70296_d();
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public String func_77658_a() {
      return this.func_179223_d().func_149739_a();
   }

   @Override
   public void func_150895_a(ItemGroup var1, NonNullList<ItemStack> var2) {
      if (this.func_194125_a(☃)) {
         this.func_179223_d().func_149666_a(☃, ☃);
      }
   }

   @Override
   public void func_77624_a(ItemStack var1, @Nullable World var2, List<ITextComponent> var3, ITooltipFlag var4) {
      super.func_77624_a(☃, ☃, ☃, ☃);
      this.func_179223_d().func_190948_a(☃, ☃, ☃, ☃);
   }

   public Block func_179223_d() {
      return this.field_150939_a;
   }

   public void func_195946_a(Map<Block, Item> var1, Item var2) {
      ☃.put(this.func_179223_d(), ☃);
   }
}
