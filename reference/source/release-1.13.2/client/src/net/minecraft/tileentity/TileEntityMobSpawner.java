package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity implements ITickable {
   private final MobSpawnerBaseLogic field_145882_a = new MobSpawnerBaseLogic() {
      @Override
      public void func_98267_a(int var1) {
         TileEntityMobSpawner.this.field_145850_b.func_175641_c(TileEntityMobSpawner.this.field_174879_c, Blocks.field_150474_ac, ☃, 0);
      }

      @Override
      public World func_98271_a() {
         return TileEntityMobSpawner.this.field_145850_b;
      }

      @Override
      public BlockPos func_177221_b() {
         return TileEntityMobSpawner.this.field_174879_c;
      }

      @Override
      public void func_184993_a(WeightedSpawnerEntity var1) {
         super.func_184993_a(☃);
         if (this.func_98271_a() != null) {
            IBlockState ☃ = this.func_98271_a().func_180495_p(this.func_177221_b());
            this.func_98271_a().func_184138_a(TileEntityMobSpawner.this.field_174879_c, ☃, ☃, 4);
         }
      }
   };

   public TileEntityMobSpawner() {
      super(TileEntityType.field_200979_j);
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_145882_a.func_98270_a(☃);
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      this.field_145882_a.func_189530_b(☃);
      return ☃;
   }

   @Override
   public void func_73660_a() {
      this.field_145882_a.func_98278_g();
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 1, this.func_189517_E_());
   }

   @Override
   public NBTTagCompound func_189517_E_() {
      NBTTagCompound ☃ = this.func_189515_b(new NBTTagCompound());
      ☃.func_82580_o("SpawnPotentials");
      return ☃;
   }

   @Override
   public boolean func_145842_c(int var1, int var2) {
      return this.field_145882_a.func_98268_b(☃) ? true : super.func_145842_c(☃, ☃);
   }

   @Override
   public boolean func_183000_F() {
      return true;
   }

   public MobSpawnerBaseLogic func_145881_a() {
      return this.field_145882_a;
   }
}
