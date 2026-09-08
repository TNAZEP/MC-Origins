package net.minecraft.block;

import java.util.function.Predicate;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockCarvedPumpkin extends BlockHorizontal {
   public static final DirectionProperty field_196359_a = BlockHorizontal.field_185512_D;
   private BlockPattern field_196361_b;
   private BlockPattern field_196362_c;
   private BlockPattern field_196363_y;
   private BlockPattern field_196364_z;
   private static final Predicate<IBlockState> field_196360_A = var0 -> var0 != null
         && (var0.func_177230_c() == Blocks.field_196625_cS || var0.func_177230_c() == Blocks.field_196628_cT);

   protected BlockCarvedPumpkin(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_196359_a, EnumFacing.NORTH));
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         this.func_196358_b(☃, ☃);
      }
   }

   public boolean func_196354_a(IWorldReaderBase var1, BlockPos var2) {
      return this.func_196353_d().func_177681_a(☃, ☃) != null || this.func_196356_f().func_177681_a(☃, ☃) != null;
   }

   private void func_196358_b(World var1, BlockPos var2) {
      BlockPattern.PatternHelper ☃ = this.func_196355_e().func_177681_a(☃, ☃);
      if (☃ != null) {
         for(int ☃x = 0; ☃x < this.func_196355_e().func_177685_b(); ++☃x) {
            BlockWorldState ☃xx = ☃.func_177670_a(0, ☃x, 0);
            ☃.func_180501_a(☃xx.func_177508_d(), Blocks.field_150350_a.func_176223_P(), 2);
         }

         EntitySnowman ☃x = new EntitySnowman(☃);
         BlockPos ☃xx = ☃.func_177670_a(0, 2, 0).func_177508_d();
         ☃x.func_70012_b((double)☃xx.func_177958_n() + 0.5, (double)☃xx.func_177956_o() + 0.05, (double)☃xx.func_177952_p() + 0.5, 0.0F, 0.0F);
         ☃.func_72838_d(☃x);

         for(EntityPlayerMP ☃xxx : ☃.func_72872_a(EntityPlayerMP.class, ☃x.func_174813_aQ().func_186662_g(5.0))) {
            CriteriaTriggers.field_192133_m.func_192229_a(☃xxx, ☃x);
         }

         int ☃xxx = Block.func_196246_j(Blocks.field_196604_cC.func_176223_P());
         ☃.func_175718_b(2001, ☃xx, ☃xxx);
         ☃.func_175718_b(2001, ☃xx.func_177984_a(), ☃xxx);

         for(int ☃xxxx = 0; ☃xxxx < this.func_196355_e().func_177685_b(); ++☃xxxx) {
            BlockWorldState ☃xxxxx = ☃.func_177670_a(0, ☃xxxx, 0);
            ☃.func_195592_c(☃xxxxx.func_177508_d(), Blocks.field_150350_a);
         }
      } else {
         ☃ = this.func_196357_g().func_177681_a(☃, ☃);
         if (☃ != null) {
            for(int ☃ = 0; ☃ < this.func_196357_g().func_177684_c(); ++☃) {
               for(int ☃x = 0; ☃x < this.func_196357_g().func_177685_b(); ++☃x) {
                  ☃.func_180501_a(☃.func_177670_a(☃, ☃x, 0).func_177508_d(), Blocks.field_150350_a.func_176223_P(), 2);
               }
            }

            BlockPos ☃ = ☃.func_177670_a(1, 2, 0).func_177508_d();
            EntityIronGolem ☃x = new EntityIronGolem(☃);
            ☃x.func_70849_f(true);
            ☃x.func_70012_b((double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o() + 0.05, (double)☃.func_177952_p() + 0.5, 0.0F, 0.0F);
            ☃.func_72838_d(☃x);

            for(EntityPlayerMP ☃xx : ☃.func_72872_a(EntityPlayerMP.class, ☃x.func_174813_aQ().func_186662_g(5.0))) {
               CriteriaTriggers.field_192133_m.func_192229_a(☃xx, ☃x);
            }

            for(int ☃xx = 0; ☃xx < 120; ++☃xx) {
               ☃.func_195594_a(
                  Particles.field_197593_D,
                  (double)☃.func_177958_n() + ☃.field_73012_v.nextDouble(),
                  (double)☃.func_177956_o() + ☃.field_73012_v.nextDouble() * 3.9,
                  (double)☃.func_177952_p() + ☃.field_73012_v.nextDouble(),
                  0.0,
                  0.0,
                  0.0
               );
            }

            for(int ☃xx = 0; ☃xx < this.func_196357_g().func_177684_c(); ++☃xx) {
               for(int ☃xxx = 0; ☃xxx < this.func_196357_g().func_177685_b(); ++☃xxx) {
                  BlockWorldState ☃xxxx = ☃.func_177670_a(☃xx, ☃xxx, 0);
                  ☃.func_195592_c(☃xxxx.func_177508_d(), Blocks.field_150350_a);
               }
            }
         }
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_196359_a, ☃.func_195992_f().func_176734_d());
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196359_a);
   }

   protected BlockPattern func_196353_d() {
      if (this.field_196361_b == null) {
         this.field_196361_b = FactoryBlockPattern.func_177660_a()
            .func_177659_a(" ", "#", "#")
            .func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_196604_cC)))
            .func_177661_b();
      }

      return this.field_196361_b;
   }

   protected BlockPattern func_196355_e() {
      if (this.field_196362_c == null) {
         this.field_196362_c = FactoryBlockPattern.func_177660_a()
            .func_177659_a("^", "#", "#")
            .func_177662_a('^', BlockWorldState.func_177510_a(field_196360_A))
            .func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_196604_cC)))
            .func_177661_b();
      }

      return this.field_196362_c;
   }

   protected BlockPattern func_196356_f() {
      if (this.field_196363_y == null) {
         this.field_196363_y = FactoryBlockPattern.func_177660_a()
            .func_177659_a("~ ~", "###", "~#~")
            .func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150339_S)))
            .func_177662_a('~', BlockWorldState.func_177510_a(BlockMaterialMatcher.func_189886_a(Material.field_151579_a)))
            .func_177661_b();
      }

      return this.field_196363_y;
   }

   protected BlockPattern func_196357_g() {
      if (this.field_196364_z == null) {
         this.field_196364_z = FactoryBlockPattern.func_177660_a()
            .func_177659_a("~^~", "###", "~#~")
            .func_177662_a('^', BlockWorldState.func_177510_a(field_196360_A))
            .func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150339_S)))
            .func_177662_a('~', BlockWorldState.func_177510_a(BlockMaterialMatcher.func_189886_a(Material.field_151579_a)))
            .func_177661_b();
      }

      return this.field_196364_z;
   }
}
