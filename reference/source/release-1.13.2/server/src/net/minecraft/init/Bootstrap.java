package net.minecraft.init;

import java.io.PrintStream;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarvedPumpkin;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSkullWither;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.EntityOptions;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBoneMeal;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionType;
import net.minecraft.server.DebugLoggingPrintStream;
import net.minecraft.stats.StatList;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LoggingPrintStream;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bootstrap {
   public static final PrintStream field_179872_a = System.out;
   private static boolean field_151355_a;
   private static final Logger field_179871_c = LogManager.getLogger();

   public static boolean func_179869_a() {
      return field_151355_a;
   }

   static void func_151353_a() {
      BlockDispenser.func_199774_a(Items.field_151032_g, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile func_82499_a(World var1, IPosition var2, ItemStack var3) {
            EntityTippedArrow ☃ = new EntityTippedArrow(☃, ☃.func_82615_a(), ☃.func_82617_b(), ☃.func_82616_c());
            ☃.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
            return ☃;
         }
      });
      BlockDispenser.func_199774_a(Items.field_185167_i, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile func_82499_a(World var1, IPosition var2, ItemStack var3) {
            EntityTippedArrow ☃ = new EntityTippedArrow(☃, ☃.func_82615_a(), ☃.func_82617_b(), ☃.func_82616_c());
            ☃.func_184555_a(☃);
            ☃.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
            return ☃;
         }
      });
      BlockDispenser.func_199774_a(Items.field_185166_h, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile func_82499_a(World var1, IPosition var2, ItemStack var3) {
            EntityArrow ☃ = new EntitySpectralArrow(☃, ☃.func_82615_a(), ☃.func_82617_b(), ☃.func_82616_c());
            ☃.field_70251_a = EntityArrow.PickupStatus.ALLOWED;
            return ☃;
         }
      });
      BlockDispenser.func_199774_a(Items.field_151110_aK, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile func_82499_a(World var1, IPosition var2, ItemStack var3) {
            return new EntityEgg(☃, ☃.func_82615_a(), ☃.func_82617_b(), ☃.func_82616_c());
         }
      });
      BlockDispenser.func_199774_a(Items.field_151126_ay, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile func_82499_a(World var1, IPosition var2, ItemStack var3) {
            return new EntitySnowball(☃, ☃.func_82615_a(), ☃.func_82617_b(), ☃.func_82616_c());
         }
      });
      BlockDispenser.func_199774_a(Items.field_151062_by, new BehaviorProjectileDispense() {
         @Override
         protected IProjectile func_82499_a(World var1, IPosition var2, ItemStack var3) {
            return new EntityExpBottle(☃, ☃.func_82615_a(), ☃.func_82617_b(), ☃.func_82616_c());
         }

         @Override
         protected float func_82498_a() {
            return super.func_82498_a() * 0.5F;
         }

         @Override
         protected float func_82500_b() {
            return super.func_82500_b() * 1.25F;
         }
      });
      BlockDispenser.func_199774_a(Items.field_185155_bH, new IBehaviorDispenseItem() {
         @Override
         public ItemStack dispense(IBlockSource var1, final ItemStack var2) {
            return (new BehaviorProjectileDispense() {
               @Override
               protected IProjectile func_82499_a(World var1, IPosition var2x, ItemStack var3) {
                  return new EntityPotion(☃, ☃.func_82615_a(), ☃.func_82617_b(), ☃.func_82616_c(), ☃.func_77946_l());
               }

               @Override
               protected float func_82498_a() {
                  return super.func_82498_a() * 0.5F;
               }

               @Override
               protected float func_82500_b() {
                  return super.func_82500_b() * 1.25F;
               }
            }).dispense(☃, ☃);
         }
      });
      BlockDispenser.func_199774_a(Items.field_185156_bI, new IBehaviorDispenseItem() {
         @Override
         public ItemStack dispense(IBlockSource var1, final ItemStack var2) {
            return (new BehaviorProjectileDispense() {
               @Override
               protected IProjectile func_82499_a(World var1, IPosition var2x, ItemStack var3) {
                  return new EntityPotion(☃, ☃.func_82615_a(), ☃.func_82617_b(), ☃.func_82616_c(), ☃.func_77946_l());
               }

               @Override
               protected float func_82498_a() {
                  return super.func_82498_a() * 0.5F;
               }

               @Override
               protected float func_82500_b() {
                  return super.func_82500_b() * 1.25F;
               }
            }).dispense(☃, ☃);
         }
      });
      BehaviorDefaultDispenseItem ☃ = new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            EnumFacing ☃ = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
            EntityType<?> ☃x = ((ItemSpawnEgg)☃.func_77973_b()).func_208076_b(☃.func_77978_p());
            if (☃x != null) {
               ☃x.func_208049_a(☃.func_197524_h(), ☃, null, ☃.func_180699_d().func_177972_a(☃), ☃ != EnumFacing.UP, false);
            }

            ☃.func_190918_g(1);
            return ☃;
         }
      };

      for(ItemSpawnEgg ☃x : ItemSpawnEgg.func_195985_g()) {
         BlockDispenser.func_199774_a(☃x, ☃);
      }

      BlockDispenser.func_199774_a(Items.field_196152_dE, new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            EnumFacing ☃ = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
            double ☃x = ☃.func_82615_a() + (double)☃.func_82601_c();
            double ☃xx = (double)((float)☃.func_180699_d().func_177956_o() + 0.2F);
            double ☃xxx = ☃.func_82616_c() + (double)☃.func_82599_e();
            EntityFireworkRocket ☃xxxx = new EntityFireworkRocket(☃.func_197524_h(), ☃x, ☃xx, ☃xxx, ☃);
            ☃.func_197524_h().func_72838_d(☃xxxx);
            ☃.func_190918_g(1);
            return ☃;
         }

         @Override
         protected void func_82485_a(IBlockSource var1) {
            ☃.func_197524_h().func_175718_b(1004, ☃.func_180699_d(), 0);
         }
      });
      BlockDispenser.func_199774_a(Items.field_151059_bz, new BehaviorDefaultDispenseItem() {
         @Override
         public ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            EnumFacing ☃ = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
            IPosition ☃x = BlockDispenser.func_149939_a(☃);
            double ☃xx = ☃x.func_82615_a() + (double)((float)☃.func_82601_c() * 0.3F);
            double ☃xxx = ☃x.func_82617_b() + (double)((float)☃.func_96559_d() * 0.3F);
            double ☃xxxx = ☃x.func_82616_c() + (double)((float)☃.func_82599_e() * 0.3F);
            World ☃xxxxx = ☃.func_197524_h();
            Random ☃xxxxxx = ☃xxxxx.field_73012_v;
            double ☃xxxxxxx = ☃xxxxxx.nextGaussian() * 0.05 + (double)☃.func_82601_c();
            double ☃xxxxxxxx = ☃xxxxxx.nextGaussian() * 0.05 + (double)☃.func_96559_d();
            double ☃xxxxxxxxx = ☃xxxxxx.nextGaussian() * 0.05 + (double)☃.func_82599_e();
            ☃xxxxx.func_72838_d(new EntitySmallFireball(☃xxxxx, ☃xx, ☃xxx, ☃xxxx, ☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx));
            ☃.func_190918_g(1);
            return ☃;
         }

         @Override
         protected void func_82485_a(IBlockSource var1) {
            ☃.func_197524_h().func_175718_b(1018, ☃.func_180699_d(), 0);
         }
      });
      BlockDispenser.func_199774_a(Items.field_151124_az, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.OAK));
      BlockDispenser.func_199774_a(Items.field_185150_aH, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.SPRUCE));
      BlockDispenser.func_199774_a(Items.field_185151_aI, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.BIRCH));
      BlockDispenser.func_199774_a(Items.field_185152_aJ, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.JUNGLE));
      BlockDispenser.func_199774_a(Items.field_185154_aL, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.DARK_OAK));
      BlockDispenser.func_199774_a(Items.field_185153_aK, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.ACACIA));
      IBehaviorDispenseItem ☃x = new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            ItemBucket ☃ = (ItemBucket)☃.func_77973_b();
            BlockPos ☃x = ☃.func_180699_d().func_177972_a(☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            World ☃xx = ☃.func_197524_h();
            if (☃.func_180616_a(null, ☃xx, ☃x, null)) {
               ☃.func_203792_a(☃xx, ☃, ☃x);
               return new ItemStack(Items.field_151133_ar);
            } else {
               return this.field_150841_b.dispense(☃, ☃);
            }
         }
      };
      BlockDispenser.func_199774_a(Items.field_151129_at, ☃x);
      BlockDispenser.func_199774_a(Items.field_151131_as, ☃x);
      BlockDispenser.func_199774_a(Items.field_203796_aM, ☃x);
      BlockDispenser.func_199774_a(Items.field_203797_aN, ☃x);
      BlockDispenser.func_199774_a(Items.field_203795_aL, ☃x);
      BlockDispenser.func_199774_a(Items.field_204272_aO, ☃x);
      BlockDispenser.func_199774_a(Items.field_151133_ar, new BehaviorDefaultDispenseItem() {
         private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();

         @Override
         public ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            IWorld ☃ = ☃.func_197524_h();
            BlockPos ☃x = ☃.func_180699_d().func_177972_a(☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            IBlockState ☃xx = ☃.func_180495_p(☃x);
            Block ☃xxx = ☃xx.func_177230_c();
            if (☃xxx instanceof IBucketPickupHandler) {
               Fluid ☃xxxx = ((IBucketPickupHandler)☃xxx).func_204508_a(☃, ☃x, ☃xx);
               if (!(☃xxxx instanceof FlowingFluid)) {
                  return super.func_82487_b(☃, ☃);
               } else {
                  Item ☃xxxx = ☃xxxx.func_204524_b();
                  ☃.func_190918_g(1);
                  if (☃.func_190926_b()) {
                     return new ItemStack(☃xxxx);
                  } else {
                     if (☃.<TileEntityDispenser>func_150835_j().func_146019_a(new ItemStack(☃xxxx)) < 0) {
                        this.field_150840_b.dispense(☃, new ItemStack(☃xxxx));
                     }

                     return ☃;
                  }
               }
            } else {
               return super.func_82487_b(☃, ☃);
            }
         }
      });
      BlockDispenser.func_199774_a(Items.field_151033_d, new Bootstrap.BehaviorDispenseOptional() {
         @Override
         protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            World ☃ = ☃.func_197524_h();
            this.field_190911_b = true;
            BlockPos ☃x = ☃.func_180699_d().func_177972_a(☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            if (ItemFlintAndSteel.func_201825_a(☃, ☃x)) {
               ☃.func_175656_a(☃x, Blocks.field_150480_ab.func_176223_P());
            } else {
               Block ☃ = ☃.func_180495_p(☃x).func_177230_c();
               if (☃ instanceof BlockTNT) {
                  ((BlockTNT)☃).func_196534_a(☃, ☃x);
                  ☃.func_175698_g(☃x);
               } else {
                  this.field_190911_b = false;
               }
            }

            if (this.field_190911_b && ☃.func_96631_a(1, ☃.field_73012_v, null)) {
               ☃.func_190920_e(0);
            }

            return ☃;
         }
      });
      BlockDispenser.func_199774_a(Items.field_196106_bc, new Bootstrap.BehaviorDispenseOptional() {
         @Override
         protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            this.field_190911_b = true;
            World ☃ = ☃.func_197524_h();
            BlockPos ☃x = ☃.func_180699_d().func_177972_a(☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            if (!ItemBoneMeal.func_195966_a(☃, ☃, ☃x) && !ItemBoneMeal.func_203173_b(☃, ☃, ☃x, null)) {
               this.field_190911_b = false;
            } else if (!☃.field_72995_K) {
               ☃.func_175718_b(2005, ☃x, 0);
            }

            return ☃;
         }
      });
      BlockDispenser.func_199774_a(Blocks.field_150335_W, new BehaviorDefaultDispenseItem() {
         @Override
         protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            World ☃ = ☃.func_197524_h();
            BlockPos ☃x = ☃.func_180699_d().func_177972_a(☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            EntityTNTPrimed ☃xx = new EntityTNTPrimed(☃, (double)☃x.func_177958_n() + 0.5, (double)☃x.func_177956_o(), (double)☃x.func_177952_p() + 0.5, null);
            ☃.func_72838_d(☃xx);
            ☃.func_184148_a(null, ☃xx.field_70165_t, ☃xx.field_70163_u, ☃xx.field_70161_v, SoundEvents.field_187904_gd, SoundCategory.BLOCKS, 1.0F, 1.0F);
            ☃.func_190918_g(1);
            return ☃;
         }
      });
      Bootstrap.BehaviorDispenseOptional ☃xx = new Bootstrap.BehaviorDispenseOptional() {
         @Override
         protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            this.field_190911_b = !ItemArmor.func_185082_a(☃, ☃).func_190926_b();
            return ☃;
         }
      };
      BlockDispenser.func_199774_a(Items.field_196185_dy, ☃xx);
      BlockDispenser.func_199774_a(Items.field_196186_dz, ☃xx);
      BlockDispenser.func_199774_a(Items.field_196151_dA, ☃xx);
      BlockDispenser.func_199774_a(Items.field_196182_dv, ☃xx);
      BlockDispenser.func_199774_a(Items.field_196184_dx, ☃xx);
      BlockDispenser.func_199774_a(
         Items.field_196183_dw,
         new Bootstrap.BehaviorDispenseOptional() {
            @Override
            protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
               World ☃ = ☃.func_197524_h();
               EnumFacing ☃x = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
               BlockPos ☃xx = ☃.func_180699_d().func_177972_a(☃x);
               this.field_190911_b = true;
               if (☃.func_175623_d(☃xx) && BlockSkullWither.func_196299_b(☃, ☃xx, ☃)) {
                  ☃.func_180501_a(
                     ☃xx,
                     Blocks.field_196705_eO
                        .func_176223_P()
                        .func_206870_a(
                           BlockSkull.field_196294_a, Integer.valueOf(☃x.func_176740_k() == EnumFacing.Axis.Y ? 0 : ☃x.func_176734_d().func_176736_b() * 4)
                        ),
                     3
                  );
                  TileEntity ☃xxx = ☃.func_175625_s(☃xx);
                  if (☃xxx instanceof TileEntitySkull) {
                     BlockSkullWither.func_196298_a(☃, ☃xx, (TileEntitySkull)☃xxx);
                  }
   
                  ☃.func_190918_g(1);
               } else if (ItemArmor.func_185082_a(☃, ☃).func_190926_b()) {
                  this.field_190911_b = false;
               }
   
               return ☃;
            }
         }
      );
      BlockDispenser.func_199774_a(Blocks.field_196625_cS, new Bootstrap.BehaviorDispenseOptional() {
         @Override
         protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
            World ☃ = ☃.func_197524_h();
            BlockPos ☃x = ☃.func_180699_d().func_177972_a(☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
            BlockCarvedPumpkin ☃xx = (BlockCarvedPumpkin)Blocks.field_196625_cS;
            this.field_190911_b = true;
            if (☃.func_175623_d(☃x) && ☃xx.func_196354_a(☃, ☃x)) {
               if (!☃.field_72995_K) {
                  ☃.func_180501_a(☃x, ☃xx.func_176223_P(), 3);
               }

               ☃.func_190918_g(1);
            } else {
               ItemStack ☃ = ItemArmor.func_185082_a(☃, ☃);
               if (☃.func_190926_b()) {
                  this.field_190911_b = false;
               }
            }

            return ☃;
         }
      });
      BlockDispenser.func_199774_a(Blocks.field_204409_il.func_199767_j(), new Bootstrap.BehaviorDispenseShulkerBox());

      for(EnumDyeColor ☃xxx : EnumDyeColor.values()) {
         BlockDispenser.func_199774_a(BlockShulkerBox.func_190952_a(☃xxx).func_199767_j(), new Bootstrap.BehaviorDispenseShulkerBox());
      }
   }

   public static void func_151354_b() {
      if (!field_151355_a) {
         field_151355_a = true;
         SoundEvent.func_187504_b();
         Fluid.func_207195_i();
         Block.func_149671_p();
         BlockFire.func_149843_e();
         Potion.func_188411_k();
         Enchantment.func_185257_f();
         if (EntityType.func_200718_a(EntityType.field_200729_aH) == null) {
            throw new IllegalStateException("Failed loading EntityTypes");
         } else {
            Item.func_150900_l();
            PotionType.func_185175_b();
            PotionBrewing.func_185207_a();
            Biome.func_185358_q();
            EntityOptions.func_197445_a();
            ParticleType.func_197576_c();
            func_151353_a();
            ArgumentTypes.func_197483_a();
            BiomeProviderType.func_212580_a();
            TileEntityType.func_212641_a();
            ChunkGeneratorType.func_212675_a();
            DimensionType.func_212680_a();
            PaintingType.func_200831_a();
            StatList.func_212734_a();
            IRegistry.func_212613_e();
            if (SharedConstants.field_206244_b) {
               func_210839_a("block", IRegistry.field_212618_g, Block::func_149739_a);
               func_210839_a("biome", IRegistry.field_212624_m, Biome::func_210773_k);
               func_210839_a("enchantment", IRegistry.field_212628_q, Enchantment::func_77320_a);
               func_210839_a("item", IRegistry.field_212630_s, Item::func_77658_a);
               func_210839_a("effect", IRegistry.field_212631_t, Potion::func_76393_a);
               func_210839_a("entity", IRegistry.field_212629_r, EntityType::func_210760_d);
            }

            func_179868_d();
         }
      }
   }

   private static <T> void func_210839_a(String var0, IRegistry<T> var1, Function<T, String> var2) {
      LanguageMap ☃ = LanguageMap.func_74808_a();
      ☃.iterator().forEachRemaining(var4 -> {
         String ☃ = (String)☃.apply(var4);
         if (!☃.func_210813_b(☃)) {
            field_179871_c.warn("Missing translation for {}: {} (key: '{}')", ☃, ☃.func_177774_c((T)var4), ☃);
         }
      });
   }

   private static void func_179868_d() {
      if (field_179871_c.isDebugEnabled()) {
         System.setErr(new DebugLoggingPrintStream("STDERR", System.err));
         System.setOut(new DebugLoggingPrintStream("STDOUT", field_179872_a));
      } else {
         System.setErr(new LoggingPrintStream("STDERR", System.err));
         System.setOut(new LoggingPrintStream("STDOUT", field_179872_a));
      }
   }

   public static class BehaviorDispenseBoat extends BehaviorDefaultDispenseItem {
      private final BehaviorDefaultDispenseItem field_185026_b = new BehaviorDefaultDispenseItem();
      private final EntityBoat.Type field_185027_c;

      public BehaviorDispenseBoat(EntityBoat.Type var1) {
         this.field_185027_c = ☃;
      }

      @Override
      public ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
         EnumFacing ☃x = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
         World ☃xx = ☃.func_197524_h();
         double ☃xxx = ☃.func_82615_a() + (double)((float)☃x.func_82601_c() * 1.125F);
         double ☃xxxx = ☃.func_82617_b() + (double)((float)☃x.func_96559_d() * 1.125F);
         double ☃xxxxx = ☃.func_82616_c() + (double)((float)☃x.func_82599_e() * 1.125F);
         BlockPos ☃xxxxxx = ☃.func_180699_d().func_177972_a(☃x);
         double ☃;
         if (☃xx.func_204610_c(☃xxxxxx).func_206884_a(FluidTags.field_206959_a)) {
            ☃ = 1.0;
         } else {
            if (!☃xx.func_180495_p(☃xxxxxx).func_196958_f() || !☃xx.func_204610_c(☃xxxxxx.func_177977_b()).func_206884_a(FluidTags.field_206959_a)) {
               return this.field_185026_b.dispense(☃, ☃);
            }

            ☃ = 0.0;
         }

         EntityBoat ☃ = new EntityBoat(☃xx, ☃xxx, ☃xxxx + ☃, ☃xxxxx);
         ☃.func_184458_a(this.field_185027_c);
         ☃.field_70177_z = ☃x.func_185119_l();
         ☃xx.func_72838_d(☃);
         ☃.func_190918_g(1);
         return ☃;
      }

      @Override
      protected void func_82485_a(IBlockSource var1) {
         ☃.func_197524_h().func_175718_b(1000, ☃.func_180699_d(), 0);
      }
   }

   public abstract static class BehaviorDispenseOptional extends BehaviorDefaultDispenseItem {
      protected boolean field_190911_b = true;

      @Override
      protected void func_82485_a(IBlockSource var1) {
         ☃.func_197524_h().func_175718_b(this.field_190911_b ? 1000 : 1001, ☃.func_180699_d(), 0);
      }
   }

   static class BehaviorDispenseShulkerBox extends Bootstrap.BehaviorDispenseOptional {
      private BehaviorDispenseShulkerBox() {
      }

      @Override
      protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
         this.field_190911_b = false;
         Item ☃ = ☃.func_77973_b();
         if (☃ instanceof ItemBlock) {
            EnumFacing ☃x = ☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a);
            BlockPos ☃xx = ☃.func_180699_d().func_177972_a(☃x);
            EnumFacing ☃xxx = ☃.func_197524_h().func_175623_d(☃xx.func_177977_b()) ? ☃x : EnumFacing.UP;
            this.field_190911_b = ((ItemBlock)☃).func_195942_a(new Bootstrap.DispensePlaceContext(☃.func_197524_h(), ☃xx, ☃x, ☃, ☃xxx))
               == EnumActionResult.SUCCESS;
            if (this.field_190911_b) {
               ☃.func_190918_g(1);
            }
         }

         return ☃;
      }
   }

   static class DispensePlaceContext extends BlockItemUseContext {
      private final EnumFacing field_196015_j;

      public DispensePlaceContext(World var1, BlockPos var2, EnumFacing var3, ItemStack var4, EnumFacing var5) {
         super(☃, null, ☃, ☃, ☃, 0.5F, 0.0F, 0.5F);
         this.field_196015_j = ☃;
      }

      @Override
      public BlockPos func_195995_a() {
         return this.field_196008_i;
      }

      @Override
      public boolean func_196011_b() {
         return this.field_196006_g.func_180495_p(this.field_196008_i).func_196953_a(this);
      }

      @Override
      public boolean func_196012_c() {
         return this.func_196011_b();
      }

      @Override
      public EnumFacing func_196010_d() {
         return EnumFacing.DOWN;
      }

      @Override
      public EnumFacing[] func_196009_e() {
         switch(this.field_196015_j) {
            case DOWN:
            default:
               return new EnumFacing[]{EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.UP};
            case UP:
               return new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST};
            case NORTH:
               return new EnumFacing[]{EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.UP, EnumFacing.SOUTH};
            case SOUTH:
               return new EnumFacing[]{EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST, EnumFacing.UP, EnumFacing.NORTH};
            case WEST:
               return new EnumFacing[]{EnumFacing.DOWN, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.EAST};
            case EAST:
               return new EnumFacing[]{EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.WEST};
         }
      }

      @Override
      public EnumFacing func_195992_f() {
         return this.field_196015_j.func_176740_k() == EnumFacing.Axis.Y ? EnumFacing.NORTH : this.field_196015_j;
      }

      @Override
      public boolean func_195998_g() {
         return false;
      }

      @Override
      public float func_195990_h() {
         return (float)(this.field_196015_j.func_176736_b() * 90);
      }
   }
}
