package net.minecraft.world.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockStemGrown;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumDirection8;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpgradeData {
   private static final Logger field_209162_b = LogManager.getLogger();
   public static final UpgradeData field_196994_a = new UpgradeData();
   private static final EnumDirection8[] field_208832_b = EnumDirection8.values();
   private final EnumSet<EnumDirection8> field_196995_b = EnumSet.noneOf(EnumDirection8.class);
   private final int[][] field_196996_c = new int[16][];
   private static final Map<Block, UpgradeData.IBlockFixer> field_196997_d = new IdentityHashMap();
   private static final Set<UpgradeData.IBlockFixer> field_208833_f = Sets.<UpgradeData.IBlockFixer>newHashSet();

   private UpgradeData() {
   }

   public UpgradeData(NBTTagCompound var1) {
      this();
      if (☃.func_150297_b("Indices", 10)) {
         NBTTagCompound ☃ = ☃.func_74775_l("Indices");

         for(int ☃x = 0; ☃x < this.field_196996_c.length; ++☃x) {
            String ☃xx = String.valueOf(☃x);
            if (☃.func_150297_b(☃xx, 11)) {
               this.field_196996_c[☃x] = ☃.func_74759_k(☃xx);
            }
         }
      }

      int ☃ = ☃.func_74762_e("Sides");

      for(EnumDirection8 ☃x : EnumDirection8.values()) {
         if ((☃ & 1 << ☃x.ordinal()) != 0) {
            this.field_196995_b.add(☃x);
         }
      }
   }

   public void func_196990_a(Chunk var1) {
      this.func_196989_a(☃);

      for(EnumDirection8 ☃ : field_208832_b) {
         func_196991_a(☃, ☃);
      }

      World ☃ = ☃.func_177412_p();
      field_208833_f.forEach(var1x -> var1x.func_208826_a(☃));
   }

   private static void func_196991_a(Chunk var0, EnumDirection8 var1) {
      World ☃ = ☃.func_177412_p();
      if (☃.func_196966_y().field_196995_b.remove(☃)) {
         Set<EnumFacing> ☃x = ☃.func_197532_a();
         int ☃xx = 0;
         int ☃xxx = 15;
         boolean ☃xxxx = ☃x.contains(EnumFacing.EAST);
         boolean ☃xxxxx = ☃x.contains(EnumFacing.WEST);
         boolean ☃xxxxxx = ☃x.contains(EnumFacing.SOUTH);
         boolean ☃xxxxxxx = ☃x.contains(EnumFacing.NORTH);
         boolean ☃xxxxxxxx = ☃x.size() == 1;
         int ☃xxxxxxxxx = (☃.field_76635_g << 4) + (!☃xxxxxxxx || !☃xxxxxxx && !☃xxxxxx ? (☃xxxxx ? 0 : 15) : 1);
         int ☃xxxxxxxxxx = (☃.field_76635_g << 4) + (!☃xxxxxxxx || !☃xxxxxxx && !☃xxxxxx ? (☃xxxxx ? 0 : 15) : 14);
         int ☃xxxxxxxxxxx = (☃.field_76647_h << 4) + (!☃xxxxxxxx || !☃xxxx && !☃xxxxx ? (☃xxxxxxx ? 0 : 15) : 1);
         int ☃xxxxxxxxxxxx = (☃.field_76647_h << 4) + (!☃xxxxxxxx || !☃xxxx && !☃xxxxx ? (☃xxxxxxx ? 0 : 15) : 14);
         EnumFacing[] ☃xxxxxxxxxxxxx = EnumFacing.values();
         BlockPos.MutableBlockPos ☃xxxxxxxxxxxxxx = new BlockPos.MutableBlockPos();

         for(BlockPos.MutableBlockPos ☃xxxxxxxxxxxxxxx : BlockPos.func_191531_b(☃xxxxxxxxx, 0, ☃xxxxxxxxxxx, ☃xxxxxxxxxx, ☃.func_72800_K() - 1, ☃xxxxxxxxxxxx)) {
            IBlockState ☃xxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxxxxxxx);
            IBlockState ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx;

            for(EnumFacing ☃xxxxxxxxxxxxxxxxxx : ☃xxxxxxxxxxxxx) {
               ☃xxxxxxxxxxxxxx.func_189533_g(☃xxxxxxxxxxxxxxx).func_189536_c(☃xxxxxxxxxxxxxxxxxx);
               ☃xxxxxxxxxxxxxxxxx = func_196987_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, ☃, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx);
            }

            Block.func_196263_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃, ☃xxxxxxxxxxxxxxx, 18);
         }
      }
   }

   private static IBlockState func_196987_a(IBlockState var0, EnumFacing var1, IWorld var2, BlockPos.MutableBlockPos var3, BlockPos.MutableBlockPos var4) {
      return ((UpgradeData.IBlockFixer)field_196997_d.getOrDefault(☃.func_177230_c(), UpgradeData.BlockFixers.DEFAULT))
         .func_196982_a(☃, ☃, ☃.func_180495_p(☃), ☃, ☃, ☃);
   }

   private void func_196989_a(Chunk var1) {
      try (
         BlockPos.PooledMutableBlockPos ☃ = BlockPos.PooledMutableBlockPos.func_185346_s();
         BlockPos.PooledMutableBlockPos ☃x = BlockPos.PooledMutableBlockPos.func_185346_s();
      ) {
         IWorld ☃xx = ☃.func_177412_p();

         for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
            ChunkSection ☃xxxx = ☃.func_76587_i()[☃xxx];
            int[] ☃xxxxx = this.field_196996_c[☃xxx];
            this.field_196996_c[☃xxx] = null;
            if (☃xxxx != null && ☃xxxxx != null && ☃xxxxx.length > 0) {
               EnumFacing[] ☃xxxxxx = EnumFacing.values();
               BlockStateContainer<IBlockState> ☃xxxxxxx = ☃xxxx.func_186049_g();

               for(int ☃xxxxxxxx : ☃xxxxx) {
                  int ☃xxxxxxxxx = ☃xxxxxxxx & 15;
                  int ☃xxxxxxxxxx = ☃xxxxxxxx >> 8 & 15;
                  int ☃xxxxxxxxxxx = ☃xxxxxxxx >> 4 & 15;
                  ☃.func_181079_c(☃xxxxxxxxx + (☃.field_76635_g << 4), ☃xxxxxxxxxx + (☃xxx << 4), ☃xxxxxxxxxxx + (☃.field_76647_h << 4));
                  IBlockState ☃xxxxxxxxxxxx = ☃xxxxxxx.func_186015_a(☃xxxxxxxx);
                  IBlockState ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxx;

                  for(EnumFacing ☃xxxxxxxxxxxxxx : ☃xxxxxx) {
                     ☃x.func_189533_g(☃).func_189536_c(☃xxxxxxxxxxxxxx);
                     if (☃.func_177958_n() >> 4 == ☃.field_76635_g && ☃.func_177952_p() >> 4 == ☃.field_76647_h) {
                        ☃xxxxxxxxxxxxx = func_196987_a(☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xx, ☃, ☃x);
                     }
                  }

                  Block.func_196263_a(☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, ☃xx, ☃, 18);
               }
            }
         }

         for(int ☃xxx = 0; ☃xxx < this.field_196996_c.length; ++☃xxx) {
            if (this.field_196996_c[☃xxx] != null) {
               field_209162_b.warn("Discarding update data for section {} for chunk ({} {})", ☃xxx, ☃.field_76635_g, ☃.field_76647_h);
            }

            this.field_196996_c[☃xxx] = null;
         }
      }
   }

   public boolean func_196988_a() {
      for(int[] ☃ : this.field_196996_c) {
         if (☃ != null) {
            return false;
         }
      }

      return this.field_196995_b.isEmpty();
   }

   public NBTTagCompound func_196992_b() {
      NBTTagCompound ☃ = new NBTTagCompound();
      NBTTagCompound ☃x = new NBTTagCompound();

      for(int ☃xx = 0; ☃xx < this.field_196996_c.length; ++☃xx) {
         String ☃xxx = String.valueOf(☃xx);
         if (this.field_196996_c[☃xx] != null && this.field_196996_c[☃xx].length != 0) {
            ☃x.func_74783_a(☃xxx, this.field_196996_c[☃xx]);
         }
      }

      if (!☃x.isEmpty()) {
         ☃.func_74782_a("Indices", ☃x);
      }

      int ☃xx = 0;

      for(EnumDirection8 ☃xxx : this.field_196995_b) {
         ☃xx |= 1 << ☃xxx.ordinal();
      }

      ☃.func_74774_a("Sides", (byte)☃xx);
      return ☃;
   }

   static enum BlockFixers implements UpgradeData.IBlockFixer {
      BLACKLIST(
         Blocks.field_190976_dk,
         Blocks.field_150427_aO,
         Blocks.field_196860_iS,
         Blocks.field_196862_iT,
         Blocks.field_196864_iU,
         Blocks.field_196866_iV,
         Blocks.field_196868_iW,
         Blocks.field_196870_iX,
         Blocks.field_196872_iY,
         Blocks.field_196874_iZ,
         Blocks.field_196877_ja,
         Blocks.field_196878_jb,
         Blocks.field_196879_jc,
         Blocks.field_196880_jd,
         Blocks.field_196881_je,
         Blocks.field_196882_jf,
         Blocks.field_196883_jg,
         Blocks.field_196884_jh,
         Blocks.field_150467_bQ,
         Blocks.field_196717_eY,
         Blocks.field_196718_eZ,
         Blocks.field_150380_bt,
         Blocks.field_150351_n,
         Blocks.field_150354_m,
         Blocks.field_196611_F,
         Blocks.field_196649_cc,
         Blocks.field_150444_as
      ) {
         @Override
         public IBlockState func_196982_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
            return ☃;
         }
      },
      DEFAULT {
         @Override
         public IBlockState func_196982_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
            return ☃.func_196956_a(☃, ☃.func_180495_p(☃), ☃, ☃, ☃);
         }
      },
      CHEST(Blocks.field_150486_ae, Blocks.field_150447_bR) {
         @Override
         public IBlockState func_196982_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
            if (☃.func_177230_c() == ☃.func_177230_c()
               && ☃.func_176740_k().func_176722_c()
               && ☃.func_177229_b(BlockChest.field_196314_b) == ChestType.SINGLE
               && ☃.func_177229_b(BlockChest.field_196314_b) == ChestType.SINGLE) {
               EnumFacing ☃ = ☃.func_177229_b(BlockChest.field_176459_a);
               if (☃.func_176740_k() != ☃.func_176740_k() && ☃ == ☃.func_177229_b(BlockChest.field_176459_a)) {
                  ChestType ☃x = ☃ == ☃.func_176746_e() ? ChestType.LEFT : ChestType.RIGHT;
                  ☃.func_180501_a(☃, ☃.func_206870_a(BlockChest.field_196314_b, ☃x.func_208081_a()), 18);
                  if (☃ == EnumFacing.NORTH || ☃ == EnumFacing.EAST) {
                     TileEntity ☃xx = ☃.func_175625_s(☃);
                     TileEntity ☃xxx = ☃.func_175625_s(☃);
                     if (☃xx instanceof TileEntityChest && ☃xxx instanceof TileEntityChest) {
                        TileEntityChest.func_199722_a((TileEntityChest)☃xx, (TileEntityChest)☃xxx);
                     }
                  }

                  return ☃.func_206870_a(BlockChest.field_196314_b, ☃x);
               }
            }

            return ☃;
         }
      },
      LEAVES(true, Blocks.field_196572_aa, Blocks.field_196647_Y, Blocks.field_196574_ab, Blocks.field_196648_Z, Blocks.field_196642_W, Blocks.field_196645_X) {
         private final ThreadLocal<List<ObjectSet<BlockPos>>> field_208828_g = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));

         @Override
         public IBlockState func_196982_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
            IBlockState ☃ = ☃.func_196956_a(☃, ☃.func_180495_p(☃), ☃, ☃, ☃);
            if (☃ != ☃) {
               int ☃x = ☃.func_177229_b(BlockStateProperties.field_208514_aa);
               List<ObjectSet<BlockPos>> ☃xx = (List)this.field_208828_g.get();
               if (☃xx.isEmpty()) {
                  for(int ☃xxx = 0; ☃xxx < 7; ++☃xxx) {
                     ☃xx.add(new ObjectOpenHashSet());
                  }
               }

               ((ObjectSet)☃xx.get(☃x)).add(☃.func_185334_h());
            }

            return ☃;
         }

         @Override
         public void func_208826_a(IWorld var1) {
            BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos();
            List<ObjectSet<BlockPos>> ☃x = (List)this.field_208828_g.get();

            for(int ☃xx = 2; ☃xx < ☃x.size(); ++☃xx) {
               int ☃xxx = ☃xx - 1;
               ObjectSet<BlockPos> ☃xxxx = (ObjectSet)☃x.get(☃xxx);
               ObjectSet<BlockPos> ☃xxxxx = (ObjectSet)☃x.get(☃xx);

               for(BlockPos ☃xxxxxx : ☃xxxx) {
                  IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxxxxx);
                  if (☃xxxxxxx.func_177229_b(BlockStateProperties.field_208514_aa) >= ☃xxx) {
                     ☃.func_180501_a(☃xxxxxx, ☃xxxxxxx.func_206870_a(BlockStateProperties.field_208514_aa, Integer.valueOf(☃xxx)), 18);
                     if (☃xx != 7) {
                        for(EnumFacing ☃xxxxxxxx : field_208827_f) {
                           ☃.func_189533_g(☃xxxxxx).func_189536_c(☃xxxxxxxx);
                           IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃);
                           if (☃xxxxxxxxx.func_196959_b(BlockStateProperties.field_208514_aa)
                              && ☃xxxxxxx.func_177229_b(BlockStateProperties.field_208514_aa) > ☃xx) {
                              ☃xxxxx.add(☃.func_185334_h());
                           }
                        }
                     }
                  }
               }
            }

            ☃x.clear();
         }
      },
      STEM_BLOCK(Blocks.field_150394_bc, Blocks.field_150393_bb) {
         @Override
         public IBlockState func_196982_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
            if (☃.func_177229_b(BlockStem.field_176484_a) == 7) {
               BlockStemGrown ☃ = ((BlockStem)☃.func_177230_c()).func_208486_d();
               if (☃.func_177230_c() == ☃) {
                  return ☃.func_196523_e().func_176223_P().func_206870_a(BlockHorizontal.field_185512_D, ☃);
               }
            }

            return ☃;
         }
      };

      public static final EnumFacing[] field_208827_f = EnumFacing.values();

      private BlockFixers(Block... var3) {
         this(false, ☃);
      }

      private BlockFixers(boolean var3, Block... var4) {
         for(Block ☃ : ☃) {
            UpgradeData.field_196997_d.put(☃, this);
         }

         if (☃) {
            UpgradeData.field_208833_f.add(this);
         }
      }
   }

   public interface IBlockFixer {
      IBlockState func_196982_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6);

      default void func_208826_a(IWorld var1) {
      }
   }
}
