package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemSpawnEgg extends Item {
   private static final Map<EntityType<?>, ItemSpawnEgg> field_195987_b = Maps.<EntityType<?>, ItemSpawnEgg>newIdentityHashMap();
   private final int field_195988_c;
   private final int field_195989_d;
   private final EntityType<?> field_200890_d;

   public ItemSpawnEgg(EntityType<?> var1, int var2, int var3, Item.Properties var4) {
      super(☃);
      this.field_200890_d = ☃;
      this.field_195988_c = ☃;
      this.field_195989_d = ☃;
      field_195987_b.put(☃, this);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      if (☃.field_72995_K) {
         return EnumActionResult.SUCCESS;
      } else {
         ItemStack ☃ = ☃.func_195996_i();
         BlockPos ☃x = ☃.func_195995_a();
         EnumFacing ☃xx = ☃.func_196000_l();
         IBlockState ☃xxx = ☃.func_180495_p(☃x);
         Block ☃xxxx = ☃xxx.func_177230_c();
         if (☃xxxx == Blocks.field_150474_ac) {
            TileEntity ☃xxxxx = ☃.func_175625_s(☃x);
            if (☃xxxxx instanceof TileEntityMobSpawner) {
               MobSpawnerBaseLogic ☃xxxxxx = ((TileEntityMobSpawner)☃xxxxx).func_145881_a();
               EntityType<?> ☃xxxxxxx = this.func_208076_b(☃.func_77978_p());
               if (☃xxxxxxx != null) {
                  ☃xxxxxx.func_200876_a(☃xxxxxxx);
                  ☃xxxxx.func_70296_d();
                  ☃.func_184138_a(☃x, ☃xxx, ☃xxx, 3);
               }

               ☃.func_190918_g(1);
               return EnumActionResult.SUCCESS;
            }
         }

         BlockPos ☃;
         if (☃xxx.func_196952_d(☃, ☃x).func_197766_b()) {
            ☃ = ☃x;
         } else {
            ☃ = ☃x.func_177972_a(☃xx);
         }

         EntityType<?> ☃ = this.func_208076_b(☃.func_77978_p());
         if (☃ == null || ☃.func_208049_a(☃, ☃, ☃.func_195999_j(), ☃, true, !Objects.equals(☃x, ☃) && ☃xx == EnumFacing.UP) != null) {
            ☃.func_190918_g(1);
         }

         return EnumActionResult.SUCCESS;
      }
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.field_72995_K) {
         return new ActionResult<>(EnumActionResult.PASS, ☃);
      } else {
         RayTraceResult ☃ = this.func_77621_a(☃, ☃, true);
         if (☃ != null && ☃.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos ☃x = ☃.func_178782_a();
            if (!(☃.func_180495_p(☃x).func_177230_c() instanceof BlockFlowingFluid)) {
               return new ActionResult<>(EnumActionResult.PASS, ☃);
            } else if (☃.func_175660_a(☃, ☃x) && ☃.func_175151_a(☃x, ☃.field_178784_b, ☃)) {
               EntityType<?> ☃x = this.func_208076_b(☃.func_77978_p());
               if (☃x != null && ☃x.func_208049_a(☃, ☃, ☃, ☃x, false, false) != null) {
                  if (!☃.field_71075_bZ.field_75098_d) {
                     ☃.func_190918_g(1);
                  }

                  ☃.func_71029_a(StatList.field_75929_E.func_199076_b(this));
                  return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
               } else {
                  return new ActionResult<>(EnumActionResult.PASS, ☃);
               }
            } else {
               return new ActionResult<>(EnumActionResult.FAIL, ☃);
            }
         } else {
            return new ActionResult<>(EnumActionResult.PASS, ☃);
         }
      }
   }

   public boolean func_208077_a(@Nullable NBTTagCompound var1, EntityType<?> var2) {
      return Objects.equals(this.func_208076_b(☃), ☃);
   }

   public int func_195983_a(int var1) {
      return ☃ == 0 ? this.field_195988_c : this.field_195989_d;
   }

   public static ItemSpawnEgg func_200889_b(@Nullable EntityType<?> var0) {
      return (ItemSpawnEgg)field_195987_b.get(☃);
   }

   public static Iterable<ItemSpawnEgg> func_195985_g() {
      return Iterables.unmodifiableIterable(field_195987_b.values());
   }

   @Nullable
   public EntityType<?> func_208076_b(@Nullable NBTTagCompound var1) {
      if (☃ != null && ☃.func_150297_b("EntityTag", 10)) {
         NBTTagCompound ☃ = ☃.func_74775_l("EntityTag");
         if (☃.func_150297_b("id", 8)) {
            return EntityType.func_200713_a(☃.func_74779_i("id"));
         }
      }

      return this.field_200890_d;
   }
}
