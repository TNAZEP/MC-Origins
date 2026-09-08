package net.minecraft.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRecord extends Item {
   private static final Map<SoundEvent, ItemRecord> field_150928_b = Maps.<SoundEvent, ItemRecord>newHashMap();
   private static final List<ItemRecord> field_195976_b = Lists.<ItemRecord>newArrayList();
   private final int field_195977_c;
   private final SoundEvent field_185076_b;

   protected ItemRecord(int var1, SoundEvent var2, Item.Properties var3) {
      super(☃);
      this.field_195977_c = ☃;
      this.field_185076_b = ☃;
      field_150928_b.put(this.field_185076_b, this);
      field_195976_b.add(this);
   }

   public static ItemRecord func_195974_a(Random var0) {
      return (ItemRecord)field_195976_b.get(☃.nextInt(field_195976_b.size()));
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      if (☃xx.func_177230_c() == Blocks.field_150421_aI && !☃xx.func_177229_b(BlockJukebox.field_176432_a)) {
         ItemStack ☃xxx = ☃.func_195996_i();
         if (!☃.field_72995_K) {
            ((BlockJukebox)Blocks.field_150421_aI).func_176431_a(☃, ☃x, ☃xx, ☃xxx);
            ☃.func_180498_a(null, 1010, ☃x, Item.func_150891_b(this));
            ☃xxx.func_190918_g(1);
            EntityPlayer ☃xxxx = ☃.func_195999_j();
            if (☃xxxx != null) {
               ☃xxxx.func_195066_a(StatList.field_188092_Z);
            }
         }

         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.PASS;
      }
   }

   public int func_195975_g() {
      return this.field_195977_c;
   }

   @Override
   public void func_77624_a(ItemStack var1, @Nullable World var2, List<ITextComponent> var3, ITooltipFlag var4) {
      ☃.add(this.func_200299_h().func_211708_a(TextFormatting.GRAY));
   }

   public ITextComponent func_200299_h() {
      return new TextComponentTranslation(this.func_77658_a() + ".desc");
   }

   @Nullable
   public static ItemRecord func_185074_a(SoundEvent var0) {
      return (ItemRecord)field_150928_b.get(☃);
   }

   public SoundEvent func_185075_h() {
      return this.field_185076_b;
   }
}
