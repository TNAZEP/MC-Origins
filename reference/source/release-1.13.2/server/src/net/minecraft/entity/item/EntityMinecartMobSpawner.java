package net.minecraft.entity.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart {
   private final MobSpawnerBaseLogic field_98040_a = new MobSpawnerBaseLogic() {
      @Override
      public void func_98267_a(int var1) {
         EntityMinecartMobSpawner.this.field_70170_p.func_72960_a(EntityMinecartMobSpawner.this, (byte)☃);
      }

      @Override
      public World func_98271_a() {
         return EntityMinecartMobSpawner.this.field_70170_p;
      }

      @Override
      public BlockPos func_177221_b() {
         return new BlockPos(EntityMinecartMobSpawner.this);
      }
   };

   public EntityMinecartMobSpawner(World var1) {
      super(EntityType.field_200777_Q, ☃);
   }

   public EntityMinecartMobSpawner(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200777_Q, ☃, ☃, ☃, ☃);
   }

   @Override
   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.SPAWNER;
   }

   @Override
   public IBlockState func_180457_u() {
      return Blocks.field_150474_ac.func_176223_P();
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.field_98040_a.func_98270_a(☃);
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      this.field_98040_a.func_189530_b(☃);
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      this.field_98040_a.func_98278_g();
   }

   @Override
   public boolean func_184213_bq() {
      return true;
   }
}
