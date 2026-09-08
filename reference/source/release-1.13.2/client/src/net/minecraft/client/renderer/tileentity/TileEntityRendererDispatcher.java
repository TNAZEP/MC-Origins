package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.model.ModelShulker;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityConduit;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class TileEntityRendererDispatcher {
   private final Map<Class<? extends TileEntity>, TileEntityRenderer<? extends TileEntity>> field_147559_m = Maps.newHashMap();
   public static TileEntityRendererDispatcher field_147556_a = new TileEntityRendererDispatcher();
   private FontRenderer field_147557_n;
   public static double field_147554_b;
   public static double field_147555_c;
   public static double field_147552_d;
   public TextureManager field_147553_e;
   public World field_147550_f;
   public Entity field_147551_g;
   public float field_147562_h;
   public float field_147563_i;
   public RayTraceResult field_190057_j;
   public double field_147560_j;
   public double field_147561_k;
   public double field_147558_l;

   private TileEntityRendererDispatcher() {
      this.field_147559_m.put(TileEntitySign.class, new TileEntitySignRenderer());
      this.field_147559_m.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
      this.field_147559_m.put(TileEntityPiston.class, new TileEntityPistonRenderer());
      this.field_147559_m.put(TileEntityChest.class, new TileEntityChestRenderer());
      this.field_147559_m.put(TileEntityEnderChest.class, new TileEntityChestRenderer());
      this.field_147559_m.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
      this.field_147559_m.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
      this.field_147559_m.put(TileEntityEndGateway.class, new TileEntityEndGatewayRenderer());
      this.field_147559_m.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
      this.field_147559_m.put(TileEntitySkull.class, new TileEntitySkullRenderer());
      this.field_147559_m.put(TileEntityBanner.class, new TileEntityBannerRenderer());
      this.field_147559_m.put(TileEntityStructure.class, new TileEntityStructureRenderer());
      this.field_147559_m.put(TileEntityShulkerBox.class, new TileEntityShulkerBoxRenderer(new ModelShulker()));
      this.field_147559_m.put(TileEntityBed.class, new TileEntityBedRenderer());
      this.field_147559_m.put(TileEntityConduit.class, new TileEntityConduitRenderer());

      for(TileEntityRenderer<?> ☃ : this.field_147559_m.values()) {
         ☃.func_147497_a(this);
      }
   }

   public <T extends TileEntity> TileEntityRenderer<T> func_147546_a(Class<? extends TileEntity> var1) {
      TileEntityRenderer<? extends TileEntity> ☃ = (TileEntityRenderer)this.field_147559_m.get(☃);
      if (☃ == null && ☃ != TileEntity.class) {
         ☃ = this.func_147546_a(☃.getSuperclass());
         this.field_147559_m.put(☃, ☃);
      }

      return ☃;
   }

   @Nullable
   public <T extends TileEntity> TileEntityRenderer<T> func_147547_b(@Nullable TileEntity var1) {
      return ☃ == null ? null : this.func_147546_a(☃.getClass());
   }

   public void func_190056_a(World var1, TextureManager var2, FontRenderer var3, Entity var4, RayTraceResult var5, float var6) {
      if (this.field_147550_f != ☃) {
         this.func_147543_a(☃);
      }

      this.field_147553_e = ☃;
      this.field_147551_g = ☃;
      this.field_147557_n = ☃;
      this.field_190057_j = ☃;
      this.field_147562_h = ☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃;
      this.field_147563_i = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
      this.field_147560_j = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      this.field_147561_k = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      this.field_147558_l = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
   }

   public void func_180546_a(TileEntity var1, float var2, int var3) {
      if (☃.func_145835_a(this.field_147560_j, this.field_147561_k, this.field_147558_l) < ☃.func_145833_n()) {
         RenderHelper.func_74519_b();
         int ☃ = this.field_147550_f.func_175626_b(☃.func_174877_v(), 0);
         int ☃x = ☃ % 65536;
         int ☃xx = ☃ / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)☃x, (float)☃xx);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos ☃xxx = ☃.func_174877_v();
         this.func_203602_a(
            ☃,
            (double)☃xxx.func_177958_n() - field_147554_b,
            (double)☃xxx.func_177956_o() - field_147555_c,
            (double)☃xxx.func_177952_p() - field_147552_d,
            ☃,
            ☃,
            false
         );
      }
   }

   public void func_147549_a(TileEntity var1, double var2, double var4, double var6, float var8) {
      this.func_203602_a(☃, ☃, ☃, ☃, ☃, -1, false);
   }

   public void func_203601_b(TileEntity var1) {
      this.func_203602_a(☃, 0.0, 0.0, 0.0, 0.0F, -1, true);
   }

   public void func_203602_a(TileEntity var1, double var2, double var4, double var6, float var8, int var9, boolean var10) {
      TileEntityRenderer<TileEntity> ☃ = this.func_147547_b(☃);
      if (☃ != null) {
         try {
            if (!☃ && (!☃.func_145830_o() || !☃.func_195044_w().func_177230_c().func_149716_u())) {
               return;
            }

            ☃.func_199341_a(☃, ☃, ☃, ☃, ☃, ☃);
         } catch (Throwable var15) {
            CrashReport ☃x = CrashReport.func_85055_a(var15, "Rendering Block Entity");
            CrashReportCategory ☃xx = ☃x.func_85058_a("Block Entity Details");
            ☃.func_145828_a(☃xx);
            throw new ReportedException(☃x);
         }
      }
   }

   public void func_147543_a(@Nullable World var1) {
      this.field_147550_f = ☃;
      if (☃ == null) {
         this.field_147551_g = null;
      }
   }

   public FontRenderer func_147548_a() {
      return this.field_147557_n;
   }
}
