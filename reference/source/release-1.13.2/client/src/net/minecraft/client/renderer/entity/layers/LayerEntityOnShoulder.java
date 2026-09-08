package net.minecraft.client.renderer.entity.layers;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderParrot;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelParrot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class LayerEntityOnShoulder implements LayerRenderer<EntityPlayer> {
   private final RenderManager field_192867_c;
   protected RenderLivingBase<? extends EntityLivingBase> field_192865_a;
   private ModelBase field_192868_d;
   private ResourceLocation field_192869_e;
   private UUID field_192870_f;
   private EntityType<?> field_192871_g;
   protected RenderLivingBase<? extends EntityLivingBase> field_192866_b;
   private ModelBase field_192872_h;
   private ResourceLocation field_192873_i;
   private UUID field_192874_j;
   private EntityType<?> field_192875_k;

   public LayerEntityOnShoulder(RenderManager var1) {
      this.field_192867_c = ☃;
   }

   public void func_177141_a(EntityPlayer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (☃.func_192023_dk() != null || ☃.func_192025_dl() != null) {
         GlStateManager.func_179091_B();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         NBTTagCompound ☃ = ☃.func_192023_dk();
         if (!☃.isEmpty()) {
            LayerEntityOnShoulder.DataHolder ☃x = this.func_200695_a(
               ☃, this.field_192870_f, ☃, this.field_192865_a, this.field_192868_d, this.field_192869_e, this.field_192871_g, ☃, ☃, ☃, ☃, ☃, ☃, ☃, true
            );
            this.field_192870_f = ☃x.field_192882_a;
            this.field_192865_a = ☃x.field_192883_b;
            this.field_192869_e = ☃x.field_192885_d;
            this.field_192868_d = ☃x.field_192884_c;
            this.field_192871_g = ☃x.field_200698_e;
         }

         NBTTagCompound ☃ = ☃.func_192025_dl();
         if (!☃.isEmpty()) {
            LayerEntityOnShoulder.DataHolder ☃x = this.func_200695_a(
               ☃, this.field_192874_j, ☃, this.field_192866_b, this.field_192872_h, this.field_192873_i, this.field_192875_k, ☃, ☃, ☃, ☃, ☃, ☃, ☃, false
            );
            this.field_192874_j = ☃x.field_192882_a;
            this.field_192866_b = ☃x.field_192883_b;
            this.field_192873_i = ☃x.field_192885_d;
            this.field_192872_h = ☃x.field_192884_c;
            this.field_192875_k = ☃x.field_200698_e;
         }

         GlStateManager.func_179101_C();
      }
   }

   private LayerEntityOnShoulder.DataHolder func_200695_a(
      EntityPlayer var1,
      @Nullable UUID var2,
      NBTTagCompound var3,
      RenderLivingBase<? extends EntityLivingBase> var4,
      ModelBase var5,
      ResourceLocation var6,
      EntityType<?> var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      boolean var15
   ) {
      if (☃ == null || !☃.equals(☃.func_186857_a("UUID"))) {
         ☃ = ☃.func_186857_a("UUID");
         ☃ = EntityType.func_200713_a(☃.func_74779_i("id"));
         if (☃ == EntityType.field_200783_W) {
            ☃ = new RenderParrot(this.field_192867_c);
            ☃ = new ModelParrot();
            ☃ = RenderParrot.field_192862_a[☃.func_74762_e("Variant")];
         }
      }

      ☃.func_110776_a(☃);
      GlStateManager.func_179094_E();
      float ☃ = ☃.func_70093_af() ? -1.3F : -1.5F;
      float ☃x = ☃ ? 0.4F : -0.4F;
      GlStateManager.func_179109_b(☃x, ☃, 0.0F);
      if (☃ == EntityType.field_200783_W) {
         ☃ = 0.0F;
      }

      ☃.func_78086_a(☃, ☃, ☃, ☃);
      ☃.func_78087_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      ☃.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      GlStateManager.func_179121_F();
      return new LayerEntityOnShoulder.DataHolder(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }

   class DataHolder {
      public UUID field_192882_a;
      public RenderLivingBase<? extends EntityLivingBase> field_192883_b;
      public ModelBase field_192884_c;
      public ResourceLocation field_192885_d;
      public EntityType<?> field_200698_e;

      public DataHolder(UUID var2, RenderLivingBase<? extends EntityLivingBase> var3, ModelBase var4, ResourceLocation var5, EntityType<?> var6) {
         this.field_192882_a = ☃;
         this.field_192883_b = ☃;
         this.field_192884_c = ☃;
         this.field_192885_d = ☃;
         this.field_200698_e = ☃;
      }
   }
}
