package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class EntityPainting extends EntityHanging {
   public PaintingType field_70522_e;

   public EntityPainting(World var1) {
      super(EntityType.field_200782_V, ☃);
   }

   public EntityPainting(World var1, BlockPos var2, EnumFacing var3) {
      super(EntityType.field_200782_V, ☃, ☃);
      List<PaintingType> ☃ = Lists.<PaintingType>newArrayList();
      int ☃x = 0;

      for(PaintingType ☃xx : IRegistry.field_212620_i) {
         this.field_70522_e = ☃xx;
         this.func_174859_a(☃);
         if (this.func_70518_d()) {
            ☃.add(☃xx);
            int ☃xxx = ☃xx.func_200834_b() * ☃xx.func_200832_c();
            if (☃xxx > ☃x) {
               ☃x = ☃xxx;
            }
         }
      }

      if (!☃.isEmpty()) {
         Iterator<PaintingType> ☃xx = ☃.iterator();

         while(☃xx.hasNext()) {
            PaintingType ☃xxx = (PaintingType)☃xx.next();
            if (☃xxx.func_200834_b() * ☃xxx.func_200832_c() < ☃x) {
               ☃xx.remove();
            }
         }

         this.field_70522_e = (PaintingType)☃.get(this.field_70146_Z.nextInt(☃.size()));
      }

      this.func_174859_a(☃);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      ☃.func_74778_a("Motive", IRegistry.field_212620_i.func_177774_c(this.field_70522_e).toString());
      super.func_70014_b(☃);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      this.field_70522_e = IRegistry.field_212620_i.func_82594_a(ResourceLocation.func_208304_a(☃.func_74779_i("Motive")));
      super.func_70037_a(☃);
   }

   @Override
   public int func_82329_d() {
      return this.field_70522_e.func_200834_b();
   }

   @Override
   public int func_82330_g() {
      return this.field_70522_e.func_200832_c();
   }

   @Override
   public void func_110128_b(@Nullable Entity var1) {
      if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
         this.func_184185_a(SoundEvents.field_187691_dJ, 1.0F, 1.0F);
         if (☃ instanceof EntityPlayer) {
            EntityPlayer ☃ = (EntityPlayer)☃;
            if (☃.field_71075_bZ.field_75098_d) {
               return;
            }
         }

         this.func_199703_a(Items.field_151159_an);
      }
   }

   @Override
   public void func_184523_o() {
      this.func_184185_a(SoundEvents.field_187694_dK, 1.0F, 1.0F);
   }

   @Override
   public void func_70012_b(double var1, double var3, double var5, float var7, float var8) {
      this.func_70107_b(☃, ☃, ☃);
   }
}
