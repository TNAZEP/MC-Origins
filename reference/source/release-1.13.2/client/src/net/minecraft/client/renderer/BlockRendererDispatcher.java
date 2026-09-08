package net.minecraft.client.renderer;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.fluid.IFluidState;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class BlockRendererDispatcher implements IResourceManagerReloadListener {
   private final BlockModelShapes field_175028_a;
   private final BlockModelRenderer field_175027_c;
   private final ChestRenderer field_175024_d = new ChestRenderer();
   private final BlockFluidRenderer field_175025_e;
   private final Random field_195476_e = new Random();

   public BlockRendererDispatcher(BlockModelShapes var1, BlockColors var2) {
      this.field_175028_a = ☃;
      this.field_175027_c = new BlockModelRenderer(☃);
      this.field_175025_e = new BlockFluidRenderer();
   }

   public BlockModelShapes func_175023_a() {
      return this.field_175028_a;
   }

   public void func_175020_a(IBlockState var1, BlockPos var2, TextureAtlasSprite var3, IWorldReader var4) {
      if (☃.func_185901_i() == EnumBlockRenderType.MODEL) {
         IBakedModel ☃ = this.field_175028_a.func_178125_b(☃);
         long ☃x = ☃.func_209533_a(☃);
         IBakedModel ☃xx = new SimpleBakedModel.Builder(☃, ☃, ☃, this.field_195476_e, ☃x).func_177645_b();
         this.field_175027_c.func_199324_a(☃, ☃xx, ☃, ☃, Tessellator.func_178181_a().func_178180_c(), true, this.field_195476_e, ☃x);
      }
   }

   public boolean func_195475_a(IBlockState var1, BlockPos var2, IWorldReader var3, BufferBuilder var4, Random var5) {
      try {
         EnumBlockRenderType ☃ = ☃.func_185901_i();
         if (☃ == EnumBlockRenderType.INVISIBLE) {
            return false;
         } else {
            switch(☃) {
               case MODEL:
                  return this.field_175027_c.func_199324_a(☃, this.func_184389_a(☃), ☃, ☃, ☃, true, ☃, ☃.func_209533_a(☃));
               case ENTITYBLOCK_ANIMATED:
                  return false;
               default:
                  return false;
            }
         }
      } catch (Throwable var9) {
         CrashReport ☃ = CrashReport.func_85055_a(var9, "Tesselating block in world");
         CrashReportCategory ☃x = ☃.func_85058_a("Block being tesselated");
         CrashReportCategory.func_175750_a(☃x, ☃, ☃);
         throw new ReportedException(☃);
      }
   }

   public boolean func_205318_a(BlockPos var1, IWorldReader var2, BufferBuilder var3, IFluidState var4) {
      try {
         return this.field_175025_e.func_205346_a(☃, ☃, ☃, ☃);
      } catch (Throwable var8) {
         CrashReport ☃ = CrashReport.func_85055_a(var8, "Tesselating liquid in world");
         CrashReportCategory ☃x = ☃.func_85058_a("Block being tesselated");
         CrashReportCategory.func_175750_a(☃x, ☃, null);
         throw new ReportedException(☃);
      }
   }

   public BlockModelRenderer func_175019_b() {
      return this.field_175027_c;
   }

   public IBakedModel func_184389_a(IBlockState var1) {
      return this.field_175028_a.func_178125_b(☃);
   }

   public void func_175016_a(IBlockState var1, float var2) {
      EnumBlockRenderType ☃ = ☃.func_185901_i();
      if (☃ != EnumBlockRenderType.INVISIBLE) {
         switch(☃) {
            case MODEL:
               IBakedModel ☃x = this.func_184389_a(☃);
               this.field_175027_c.func_178266_a(☃x, ☃, ☃, true);
               break;
            case ENTITYBLOCK_ANIMATED:
               this.field_175024_d.func_178175_a(☃.func_177230_c(), ☃);
         }
      }
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      this.field_175025_e.func_178268_a();
   }
}
