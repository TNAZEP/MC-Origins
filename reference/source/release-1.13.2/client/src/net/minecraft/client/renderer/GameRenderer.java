package net.minecraft.client.renderer;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.resources.SimpleResource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.GameType;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRenderer implements AutoCloseable, IResourceManagerReloadListener {
   private static final Logger field_147710_q = LogManager.getLogger();
   private static final ResourceLocation field_110924_q = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation field_110923_r = new ResourceLocation("textures/environment/snow.png");
   private final Minecraft field_78531_r;
   private final IResourceManager field_147711_ac;
   private final Random field_78537_ab = new Random();
   private float field_78530_s;
   public final FirstPersonRenderer field_78516_c;
   private final MapItemRenderer field_147709_v;
   private int field_78529_t;
   private Entity field_78528_u;
   private final float field_78490_B = 4.0F;
   private float field_78491_C = 4.0F;
   private float field_78507_R;
   private float field_78506_S;
   private float field_82831_U;
   private float field_82832_V;
   private boolean field_175074_C = true;
   private boolean field_175073_D = true;
   private long field_184374_E;
   private long field_78508_Y = Util.func_211177_b();
   private final LightTexture field_78513_d;
   private int field_78534_ac;
   private final float[] field_175076_N = new float[1024];
   private final float[] field_175077_O = new float[1024];
   private final FogRenderer field_205003_A;
   private boolean field_175078_W;
   private double field_78503_V = 1.0;
   private double field_78502_W;
   private double field_78509_X;
   private ItemStack field_190566_ab;
   private int field_190567_ac;
   private float field_190568_ad;
   private float field_190569_ae;
   private ShaderGroup field_147707_d;
   private float field_203000_X;
   private float field_203001_Y;
   private static final ResourceLocation[] field_147712_ad = new ResourceLocation[]{
      new ResourceLocation("shaders/post/notch.json"),
      new ResourceLocation("shaders/post/fxaa.json"),
      new ResourceLocation("shaders/post/art.json"),
      new ResourceLocation("shaders/post/bumpy.json"),
      new ResourceLocation("shaders/post/blobs2.json"),
      new ResourceLocation("shaders/post/pencil.json"),
      new ResourceLocation("shaders/post/color_convolve.json"),
      new ResourceLocation("shaders/post/deconverge.json"),
      new ResourceLocation("shaders/post/flip.json"),
      new ResourceLocation("shaders/post/invert.json"),
      new ResourceLocation("shaders/post/ntsc.json"),
      new ResourceLocation("shaders/post/outline.json"),
      new ResourceLocation("shaders/post/phosphor.json"),
      new ResourceLocation("shaders/post/scan_pincushion.json"),
      new ResourceLocation("shaders/post/sobel.json"),
      new ResourceLocation("shaders/post/bits.json"),
      new ResourceLocation("shaders/post/desaturate.json"),
      new ResourceLocation("shaders/post/green.json"),
      new ResourceLocation("shaders/post/blur.json"),
      new ResourceLocation("shaders/post/wobble.json"),
      new ResourceLocation("shaders/post/blobs.json"),
      new ResourceLocation("shaders/post/antialias.json"),
      new ResourceLocation("shaders/post/creeper.json"),
      new ResourceLocation("shaders/post/spider.json")
   };
   public static final int field_147708_e = field_147712_ad.length;
   private int field_147713_ae = field_147708_e;
   private boolean field_175083_ad;
   private int field_175084_ae;

   public GameRenderer(Minecraft var1, IResourceManager var2) {
      this.field_78531_r = ☃;
      this.field_147711_ac = ☃;
      this.field_78516_c = ☃.func_175597_ag();
      this.field_147709_v = new MapItemRenderer(☃.func_110434_K());
      this.field_78513_d = new LightTexture(this);
      this.field_205003_A = new FogRenderer(this);
      this.field_147707_d = null;

      for(int ☃ = 0; ☃ < 32; ++☃) {
         for(int ☃x = 0; ☃x < 32; ++☃x) {
            float ☃xx = (float)(☃x - 16);
            float ☃xxx = (float)(☃ - 16);
            float ☃xxxx = MathHelper.func_76129_c(☃xx * ☃xx + ☃xxx * ☃xxx);
            this.field_175076_N[☃ << 5 | ☃x] = -☃xxx / ☃xxxx;
            this.field_175077_O[☃ << 5 | ☃x] = ☃xx / ☃xxxx;
         }
      }
   }

   public void close() {
      this.field_78513_d.close();
      this.field_147709_v.close();
      this.func_181022_b();
   }

   public boolean func_147702_a() {
      return OpenGlHelper.field_148824_g && this.field_147707_d != null;
   }

   public void func_181022_b() {
      if (this.field_147707_d != null) {
         this.field_147707_d.close();
      }

      this.field_147707_d = null;
      this.field_147713_ae = field_147708_e;
   }

   public void func_175071_c() {
      this.field_175083_ad = !this.field_175083_ad;
   }

   public void func_175066_a(@Nullable Entity var1) {
      if (OpenGlHelper.field_148824_g) {
         if (this.field_147707_d != null) {
            this.field_147707_d.close();
         }

         this.field_147707_d = null;
         if (☃ instanceof EntityCreeper) {
            this.func_175069_a(new ResourceLocation("shaders/post/creeper.json"));
         } else if (☃ instanceof EntitySpider) {
            this.func_175069_a(new ResourceLocation("shaders/post/spider.json"));
         } else if (☃ instanceof EntityEnderman) {
            this.func_175069_a(new ResourceLocation("shaders/post/invert.json"));
         }
      }
   }

   private void func_175069_a(ResourceLocation var1) {
      if (this.field_147707_d != null) {
         this.field_147707_d.close();
      }

      try {
         this.field_147707_d = new ShaderGroup(this.field_78531_r.func_110434_K(), this.field_147711_ac, this.field_78531_r.func_147110_a(), ☃);
         this.field_147707_d.func_148026_a(this.field_78531_r.field_195558_d.func_198109_k(), this.field_78531_r.field_195558_d.func_198091_l());
         this.field_175083_ad = true;
      } catch (IOException var3) {
         field_147710_q.warn("Failed to load shader: {}", ☃, var3);
         this.field_147713_ae = field_147708_e;
         this.field_175083_ad = false;
      } catch (JsonSyntaxException var4) {
         field_147710_q.warn("Failed to load shader: {}", ☃, var4);
         this.field_147713_ae = field_147708_e;
         this.field_175083_ad = false;
      }
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      if (this.field_147707_d != null) {
         this.field_147707_d.close();
      }

      this.field_147707_d = null;
      if (this.field_147713_ae == field_147708_e) {
         this.func_175066_a(this.field_78531_r.func_175606_aa());
      } else {
         this.func_175069_a(field_147712_ad[this.field_147713_ae]);
      }
   }

   public void func_78464_a() {
      if (OpenGlHelper.field_148824_g && ShaderLinkHelper.func_148074_b() == null) {
         ShaderLinkHelper.func_148076_a();
      }

      this.func_78477_e();
      this.field_78513_d.func_205107_a();
      this.field_78491_C = 4.0F;
      if (this.field_78531_r.func_175606_aa() == null) {
         this.field_78531_r.func_175607_a(this.field_78531_r.field_71439_g);
      }

      this.field_203001_Y = this.field_203000_X;
      this.field_203000_X += (this.field_78531_r.func_175606_aa().func_70047_e() - this.field_203000_X) * 0.5F;
      ++this.field_78529_t;
      this.field_78516_c.func_78441_a();
      this.func_78484_h();
      this.field_82832_V = this.field_82831_U;
      if (this.field_78531_r.field_71456_v.func_184046_j().func_184053_e()) {
         this.field_82831_U += 0.05F;
         if (this.field_82831_U > 1.0F) {
            this.field_82831_U = 1.0F;
         }
      } else if (this.field_82831_U > 0.0F) {
         this.field_82831_U -= 0.0125F;
      }

      if (this.field_190567_ac > 0) {
         --this.field_190567_ac;
         if (this.field_190567_ac == 0) {
            this.field_190566_ab = null;
         }
      }
   }

   public ShaderGroup func_147706_e() {
      return this.field_147707_d;
   }

   public void func_147704_a(int var1, int var2) {
      if (OpenGlHelper.field_148824_g) {
         if (this.field_147707_d != null) {
            this.field_147707_d.func_148026_a(☃, ☃);
         }

         this.field_78531_r.field_71438_f.func_72720_a(☃, ☃);
      }
   }

   public void func_78473_a(float var1) {
      Entity ☃ = this.field_78531_r.func_175606_aa();
      if (☃ != null) {
         if (this.field_78531_r.field_71441_e != null) {
            this.field_78531_r.field_71424_I.func_76320_a("pick");
            this.field_78531_r.field_147125_j = null;
            double ☃x = (double)this.field_78531_r.field_71442_b.func_78757_d();
            this.field_78531_r.field_71476_x = ☃.func_174822_a(☃x, ☃, RayTraceFluidMode.NEVER);
            Vec3d ☃xx = ☃.func_174824_e(☃);
            boolean ☃xxx = false;
            int ☃xxxx = 3;
            double ☃xxxxx = ☃x;
            if (this.field_78531_r.field_71442_b.func_78749_i()) {
               ☃xxxxx = 6.0;
               ☃x = ☃xxxxx;
            } else {
               if (☃x > 3.0) {
                  ☃xxx = true;
               }

               ☃x = ☃x;
            }

            if (this.field_78531_r.field_71476_x != null) {
               ☃xxxxx = this.field_78531_r.field_71476_x.field_72307_f.func_72438_d(☃xx);
            }

            Vec3d ☃x = ☃.func_70676_i(1.0F);
            Vec3d ☃xx = ☃xx.func_72441_c(☃x.field_72450_a * ☃x, ☃x.field_72448_b * ☃x, ☃x.field_72449_c * ☃x);
            this.field_78528_u = null;
            Vec3d ☃xxx = null;
            float ☃xxxx = 1.0F;
            List<Entity> ☃xxxxx = this.field_78531_r
               .field_71441_e
               .func_175674_a(
                  ☃,
                  ☃.func_174813_aQ().func_72321_a(☃x.field_72450_a * ☃x, ☃x.field_72448_b * ☃x, ☃x.field_72449_c * ☃x).func_72314_b(1.0, 1.0, 1.0),
                  EntitySelectors.field_180132_d.and(Entity::func_70067_L)
               );
            double ☃xxxxxx = ☃xxxxx;

            for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxxx.size(); ++☃xxxxxxx) {
               Entity ☃xxxxxxxx = (Entity)☃xxxxx.get(☃xxxxxxx);
               AxisAlignedBB ☃xxxxxxxxx = ☃xxxxxxxx.func_174813_aQ().func_186662_g((double)☃xxxxxxxx.func_70111_Y());
               RayTraceResult ☃xxxxxxxxxx = ☃xxxxxxxxx.func_72327_a(☃xx, ☃xx);
               if (☃xxxxxxxxx.func_72318_a(☃xx)) {
                  if (☃xxxxxx >= 0.0) {
                     this.field_78528_u = ☃xxxxxxxx;
                     ☃xxx = ☃xxxxxxxxxx == null ? ☃xx : ☃xxxxxxxxxx.field_72307_f;
                     ☃xxxxxx = 0.0;
                  }
               } else if (☃xxxxxxxxxx != null) {
                  double ☃xxxxxxxx = ☃xx.func_72438_d(☃xxxxxxxxxx.field_72307_f);
                  if (☃xxxxxxxx < ☃xxxxxx || ☃xxxxxx == 0.0) {
                     if (☃xxxxxxxx.func_184208_bv() == ☃.func_184208_bv()) {
                        if (☃xxxxxx == 0.0) {
                           this.field_78528_u = ☃xxxxxxxx;
                           ☃xxx = ☃xxxxxxxxxx.field_72307_f;
                        }
                     } else {
                        this.field_78528_u = ☃xxxxxxxx;
                        ☃xxx = ☃xxxxxxxxxx.field_72307_f;
                        ☃xxxxxx = ☃xxxxxxxx;
                     }
                  }
               }
            }

            if (this.field_78528_u != null && ☃xxx && ☃xx.func_72438_d(☃xxx) > 3.0) {
               this.field_78528_u = null;
               this.field_78531_r.field_71476_x = new RayTraceResult(RayTraceResult.Type.MISS, ☃xxx, null, new BlockPos(☃xxx));
            }

            if (this.field_78528_u != null && (☃xxxxxx < ☃xxxxx || this.field_78531_r.field_71476_x == null)) {
               this.field_78531_r.field_71476_x = new RayTraceResult(this.field_78528_u, ☃xxx);
               if (this.field_78528_u instanceof EntityLivingBase || this.field_78528_u instanceof EntityItemFrame) {
                  this.field_78531_r.field_147125_j = this.field_78528_u;
               }
            }

            this.field_78531_r.field_71424_I.func_76319_b();
         }
      }
   }

   private void func_78477_e() {
      float ☃ = 1.0F;
      if (this.field_78531_r.func_175606_aa() instanceof AbstractClientPlayer) {
         AbstractClientPlayer ☃x = (AbstractClientPlayer)this.field_78531_r.func_175606_aa();
         ☃ = ☃x.func_175156_o();
      }

      this.field_78506_S = this.field_78507_R;
      this.field_78507_R += (☃ - this.field_78507_R) * 0.5F;
      if (this.field_78507_R > 1.5F) {
         this.field_78507_R = 1.5F;
      }

      if (this.field_78507_R < 0.1F) {
         this.field_78507_R = 0.1F;
      }
   }

   private double func_195459_a(float var1, boolean var2) {
      if (this.field_175078_W) {
         return 90.0;
      } else {
         Entity ☃ = this.field_78531_r.func_175606_aa();
         double ☃x = 70.0;
         if (☃) {
            ☃x = this.field_78531_r.field_71474_y.field_74334_X;
            ☃x *= (double)(this.field_78506_S + (this.field_78507_R - this.field_78506_S) * ☃);
         }

         if (☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_110143_aJ() <= 0.0F) {
            float ☃ = (float)((EntityLivingBase)☃).field_70725_aQ + ☃;
            ☃x /= (double)((1.0F - 500.0F / (☃ + 500.0F)) * 2.0F + 1.0F);
         }

         IFluidState ☃ = ActiveRenderInfo.func_206243_b(this.field_78531_r.field_71441_e, ☃, ☃);
         if (!☃.func_206888_e()) {
            ☃x = ☃x * 60.0 / 70.0;
         }

         return ☃x;
      }
   }

   private void func_78482_e(float var1) {
      if (this.field_78531_r.func_175606_aa() instanceof EntityLivingBase) {
         EntityLivingBase ☃ = (EntityLivingBase)this.field_78531_r.func_175606_aa();
         float ☃x = (float)☃.field_70737_aN - ☃;
         if (☃.func_110143_aJ() <= 0.0F) {
            float ☃xx = (float)☃.field_70725_aQ + ☃;
            GlStateManager.func_179114_b(40.0F - 8000.0F / (☃xx + 200.0F), 0.0F, 0.0F, 1.0F);
         }

         if (☃x < 0.0F) {
            return;
         }

         ☃x /= (float)☃.field_70738_aO;
         ☃x = MathHelper.func_76126_a(☃x * ☃x * ☃x * ☃x * (float) Math.PI);
         float ☃ = ☃.field_70739_aP;
         GlStateManager.func_179114_b(-☃, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(-☃x * 14.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179114_b(☃, 0.0F, 1.0F, 0.0F);
      }
   }

   private void func_78475_f(float var1) {
      if (this.field_78531_r.func_175606_aa() instanceof EntityPlayer) {
         EntityPlayer ☃ = (EntityPlayer)this.field_78531_r.func_175606_aa();
         float ☃x = ☃.field_70140_Q - ☃.field_70141_P;
         float ☃xx = -(☃.field_70140_Q + ☃x * ☃);
         float ☃xxx = ☃.field_71107_bF + (☃.field_71109_bG - ☃.field_71107_bF) * ☃;
         float ☃xxxx = ☃.field_70727_aS + (☃.field_70726_aT - ☃.field_70727_aS) * ☃;
         GlStateManager.func_179109_b(
            MathHelper.func_76126_a(☃xx * (float) Math.PI) * ☃xxx * 0.5F, -Math.abs(MathHelper.func_76134_b(☃xx * (float) Math.PI) * ☃xxx), 0.0F
         );
         GlStateManager.func_179114_b(MathHelper.func_76126_a(☃xx * (float) Math.PI) * ☃xxx * 3.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179114_b(Math.abs(MathHelper.func_76134_b(☃xx * (float) Math.PI - 0.2F) * ☃xxx) * 5.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(☃xxxx, 1.0F, 0.0F, 0.0F);
      }
   }

   private void func_78467_g(float var1) {
      Entity ☃ = this.field_78531_r.func_175606_aa();
      float ☃x = this.field_203001_Y + (this.field_203000_X - this.field_203001_Y) * ☃;
      double ☃xx = ☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * (double)☃;
      double ☃xxx = ☃.field_70167_r + (☃.field_70163_u - ☃.field_70167_r) * (double)☃ + (double)☃.func_70047_e();
      double ☃xxxx = ☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * (double)☃;
      if (☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_70608_bn()) {
         ☃x = (float)((double)☃x + 1.0);
         GlStateManager.func_179109_b(0.0F, 0.3F, 0.0F);
         if (!this.field_78531_r.field_71474_y.field_74325_U) {
            BlockPos ☃xxxxx = new BlockPos(☃);
            IBlockState ☃xxxxxx = this.field_78531_r.field_71441_e.func_180495_p(☃xxxxx);
            Block ☃xxxxxxx = ☃xxxxxx.func_177230_c();
            if (☃xxxxxxx instanceof BlockBed) {
               GlStateManager.func_179114_b(((EnumFacing)☃xxxxxx.func_177229_b(BlockBed.field_185512_D)).func_185119_l(), 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.func_179114_b(☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃ + 180.0F, 0.0F, -1.0F, 0.0F);
            GlStateManager.func_179114_b(☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃, -1.0F, 0.0F, 0.0F);
         }
      } else if (this.field_78531_r.field_71474_y.field_74320_O > 0) {
         double ☃ = (double)(this.field_78491_C + (4.0F - this.field_78491_C) * ☃);
         if (this.field_78531_r.field_71474_y.field_74325_U) {
            GlStateManager.func_179109_b(0.0F, 0.0F, (float)(-☃));
         } else {
            float ☃ = ☃.field_70177_z;
            float ☃x = ☃.field_70125_A;
            if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
               ☃x += 180.0F;
            }

            double ☃ = (double)(-MathHelper.func_76126_a(☃ * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃x * (float) (Math.PI / 180.0))) * ☃;
            double ☃x = (double)(MathHelper.func_76134_b(☃ * (float) (Math.PI / 180.0)) * MathHelper.func_76134_b(☃x * (float) (Math.PI / 180.0))) * ☃;
            double ☃xx = (double)(-MathHelper.func_76126_a(☃x * (float) (Math.PI / 180.0))) * ☃;

            for(int ☃xxx = 0; ☃xxx < 8; ++☃xxx) {
               float ☃xxxx = (float)((☃xxx & 1) * 2 - 1);
               float ☃xxxxx = (float)((☃xxx >> 1 & 1) * 2 - 1);
               float ☃xxxxxx = (float)((☃xxx >> 2 & 1) * 2 - 1);
               ☃xxxx *= 0.1F;
               ☃xxxxx *= 0.1F;
               ☃xxxxxx *= 0.1F;
               RayTraceResult ☃xxxxxxx = this.field_78531_r
                  .field_71441_e
                  .func_72933_a(
                     new Vec3d(☃xx + (double)☃xxxx, ☃xxx + (double)☃xxxxx, ☃xxxx + (double)☃xxxxxx),
                     new Vec3d(☃xx - ☃ + (double)☃xxxx + (double)☃xxxxxx, ☃xxx - ☃xx + (double)☃xxxxx, ☃xxxx - ☃x + (double)☃xxxxxx)
                  );
               if (☃xxxxxxx != null) {
                  double ☃xxxxxxxx = ☃xxxxxxx.field_72307_f.func_72438_d(new Vec3d(☃xx, ☃xxx, ☃xxxx));
                  if (☃xxxxxxxx < ☃) {
                     ☃ = ☃xxxxxxxx;
                  }
               }
            }

            if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
               GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.func_179114_b(☃.field_70125_A - ☃x, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(☃.field_70177_z - ☃, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179109_b(0.0F, 0.0F, (float)(-☃));
            GlStateManager.func_179114_b(☃ - ☃.field_70177_z, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(☃x - ☃.field_70125_A, 1.0F, 0.0F, 0.0F);
         }
      } else if (!this.field_175078_W) {
         GlStateManager.func_179109_b(0.0F, 0.0F, 0.05F);
      }

      if (!this.field_78531_r.field_71474_y.field_74325_U) {
         GlStateManager.func_179114_b(☃.func_195050_f(☃), 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(☃.func_195046_g(☃) + 180.0F, 0.0F, 1.0F, 0.0F);
      }

      GlStateManager.func_179109_b(0.0F, -☃x, 0.0F);
   }

   private void func_195460_g(float var1) {
      this.field_78530_s = (float)(this.field_78531_r.field_71474_y.field_151451_c * 16);
      GlStateManager.func_179128_n(5889);
      GlStateManager.func_179096_D();
      if (this.field_78503_V != 1.0) {
         GlStateManager.func_179109_b((float)this.field_78502_W, (float)(-this.field_78509_X), 0.0F);
         GlStateManager.func_179139_a(this.field_78503_V, this.field_78503_V, 1.0);
      }

      GlStateManager.func_199294_a(
         Matrix4f.func_195876_a(
            this.func_195459_a(☃, true),
            (float)this.field_78531_r.field_195558_d.func_198109_k() / (float)this.field_78531_r.field_195558_d.func_198091_l(),
            0.05F,
            this.field_78530_s * MathHelper.field_180189_a
         )
      );
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179096_D();
      this.func_78482_e(☃);
      if (this.field_78531_r.field_71474_y.field_74336_f) {
         this.func_78475_f(☃);
      }

      float ☃ = this.field_78531_r.field_71439_g.field_71080_cy
         + (this.field_78531_r.field_71439_g.field_71086_bY - this.field_78531_r.field_71439_g.field_71080_cy) * ☃;
      if (☃ > 0.0F) {
         int ☃x = 20;
         if (this.field_78531_r.field_71439_g.func_70644_a(MobEffects.field_76431_k)) {
            ☃x = 7;
         }

         float ☃x = 5.0F / (☃ * ☃ + 5.0F) - ☃ * 0.04F;
         ☃x *= ☃x;
         GlStateManager.func_179114_b(((float)this.field_78529_t + ☃) * (float)☃x, 0.0F, 1.0F, 1.0F);
         GlStateManager.func_179152_a(1.0F / ☃x, 1.0F, 1.0F);
         GlStateManager.func_179114_b(-((float)this.field_78529_t + ☃) * (float)☃x, 0.0F, 1.0F, 1.0F);
      }

      this.func_78467_g(☃);
   }

   private void func_195457_h(float var1) {
      if (!this.field_175078_W) {
         GlStateManager.func_179128_n(5889);
         GlStateManager.func_179096_D();
         GlStateManager.func_199294_a(
            Matrix4f.func_195876_a(
               this.func_195459_a(☃, false),
               (float)this.field_78531_r.field_195558_d.func_198109_k() / (float)this.field_78531_r.field_195558_d.func_198091_l(),
               0.05F,
               this.field_78530_s * 2.0F
            )
         );
         GlStateManager.func_179128_n(5888);
         GlStateManager.func_179096_D();
         GlStateManager.func_179094_E();
         this.func_78482_e(☃);
         if (this.field_78531_r.field_71474_y.field_74336_f) {
            this.func_78475_f(☃);
         }

         boolean ☃ = this.field_78531_r.func_175606_aa() instanceof EntityLivingBase && ((EntityLivingBase)this.field_78531_r.func_175606_aa()).func_70608_bn();
         if (this.field_78531_r.field_71474_y.field_74320_O == 0
            && !☃
            && !this.field_78531_r.field_71474_y.field_74319_N
            && this.field_78531_r.field_71442_b.func_178889_l() != GameType.SPECTATOR) {
            this.func_180436_i();
            this.field_78516_c.func_78440_a(☃);
            this.func_175072_h();
         }

         GlStateManager.func_179121_F();
         if (this.field_78531_r.field_71474_y.field_74320_O == 0 && !☃) {
            this.field_78516_c.func_78447_b(☃);
            this.func_78482_e(☃);
         }

         if (this.field_78531_r.field_71474_y.field_74336_f) {
            this.func_78475_f(☃);
         }
      }
   }

   public void func_175072_h() {
      this.field_78513_d.func_205108_b();
   }

   public void func_180436_i() {
      this.field_78513_d.func_205109_c();
   }

   public float func_180438_a(EntityLivingBase var1, float var2) {
      int ☃ = ☃.func_70660_b(MobEffects.field_76439_r).func_76459_b();
      return ☃ > 200 ? 1.0F : 0.7F + MathHelper.func_76126_a(((float)☃ - ☃) * (float) Math.PI * 0.2F) * 0.3F;
   }

   public void func_195458_a(float var1, long var2, boolean var4) {
      if (!this.field_78531_r.func_195544_aj()
         && this.field_78531_r.field_71474_y.field_82881_y
         && (!this.field_78531_r.field_71474_y.field_85185_A || !this.field_78531_r.field_71417_B.func_198031_d())) {
         if (Util.func_211177_b() - this.field_78508_Y > 500L) {
            this.field_78531_r.func_71385_j();
         }
      } else {
         this.field_78508_Y = Util.func_211177_b();
      }

      if (!this.field_78531_r.field_71454_w) {
         int ☃ = (int)(
            this.field_78531_r.field_71417_B.func_198024_e()
               * (double)this.field_78531_r.field_195558_d.func_198107_o()
               / (double)this.field_78531_r.field_195558_d.func_198105_m()
         );
         int ☃x = (int)(
            this.field_78531_r.field_71417_B.func_198026_f()
               * (double)this.field_78531_r.field_195558_d.func_198087_p()
               / (double)this.field_78531_r.field_195558_d.func_198083_n()
         );
         int ☃xx = this.field_78531_r.field_71474_y.field_74350_i;
         if (☃ && this.field_78531_r.field_71441_e != null) {
            this.field_78531_r.field_71424_I.func_76320_a("level");
            int ☃xxx = Math.min(Minecraft.func_175610_ah(), ☃xx);
            ☃xxx = Math.max(☃xxx, 60);
            long ☃xxxx = Util.func_211178_c() - ☃;
            long ☃xxxxx = Math.max((long)(1000000000 / ☃xxx / 4) - ☃xxxx, 0L);
            this.func_78471_a(☃, Util.func_211178_c() + ☃xxxxx);
            if (this.field_78531_r.func_71356_B() && this.field_184374_E < Util.func_211177_b() - 1000L) {
               this.field_184374_E = Util.func_211177_b();
               if (!this.field_78531_r.func_71401_C().func_184106_y()) {
                  this.func_184373_n();
               }
            }

            if (OpenGlHelper.field_148824_g) {
               this.field_78531_r.field_71438_f.func_174975_c();
               if (this.field_147707_d != null && this.field_175083_ad) {
                  GlStateManager.func_179128_n(5890);
                  GlStateManager.func_179094_E();
                  GlStateManager.func_179096_D();
                  this.field_147707_d.func_148018_a(☃);
                  GlStateManager.func_179121_F();
               }

               this.field_78531_r.func_147110_a().func_147610_a(true);
            }

            this.field_78531_r.field_71424_I.func_76318_c("gui");
            if (!this.field_78531_r.field_71474_y.field_74319_N || this.field_78531_r.field_71462_r != null) {
               GlStateManager.func_179092_a(516, 0.1F);
               this.field_78531_r.field_195558_d.func_198094_a();
               this.func_190563_a(this.field_78531_r.field_195558_d.func_198107_o(), this.field_78531_r.field_195558_d.func_198087_p(), ☃);
               this.field_78531_r.field_71456_v.func_175180_a(☃);
            }

            this.field_78531_r.field_71424_I.func_76319_b();
         } else {
            GlStateManager.func_179083_b(0, 0, this.field_78531_r.field_195558_d.func_198109_k(), this.field_78531_r.field_195558_d.func_198091_l());
            GlStateManager.func_179128_n(5889);
            GlStateManager.func_179096_D();
            GlStateManager.func_179128_n(5888);
            GlStateManager.func_179096_D();
            this.field_78531_r.field_195558_d.func_198094_a();
         }

         if (this.field_78531_r.field_71462_r != null) {
            GlStateManager.func_179086_m(256);

            try {
               this.field_78531_r.field_71462_r.func_73863_a(☃, ☃x, this.field_78531_r.func_193989_ak());
            } catch (Throwable var13) {
               CrashReport ☃ = CrashReport.func_85055_a(var13, "Rendering screen");
               CrashReportCategory ☃x = ☃.func_85058_a("Screen render details");
               ☃x.func_189529_a("Screen name", () -> this.field_78531_r.field_71462_r.getClass().getCanonicalName());
               ☃x.func_189529_a(
                  "Mouse location",
                  () -> String.format(
                        Locale.ROOT,
                        "Scaled: (%d, %d). Absolute: (%f, %f)",
                        ☃,
                        ☃,
                        this.field_78531_r.field_71417_B.func_198024_e(),
                        this.field_78531_r.field_71417_B.func_198026_f()
                     )
               );
               ☃x.func_189529_a(
                  "Screen size",
                  () -> String.format(
                        Locale.ROOT,
                        "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f",
                        this.field_78531_r.field_195558_d.func_198107_o(),
                        this.field_78531_r.field_195558_d.func_198087_p(),
                        this.field_78531_r.field_195558_d.func_198109_k(),
                        this.field_78531_r.field_195558_d.func_198091_l(),
                        this.field_78531_r.field_195558_d.func_198100_s()
                     )
               );
               throw new ReportedException(☃);
            }
         }
      }
   }

   private void func_184373_n() {
      if (this.field_78531_r.field_71438_f.func_184382_g() > 10
         && this.field_78531_r.field_71438_f.func_184384_n()
         && !this.field_78531_r.func_71401_C().func_184106_y()) {
         NativeImage ☃ = ScreenShotHelper.func_198052_a(
            this.field_78531_r.field_195558_d.func_198109_k(), this.field_78531_r.field_195558_d.func_198091_l(), this.field_78531_r.func_147110_a()
         );
         SimpleResource.field_199031_a.execute(() -> {
            int ☃ = ☃.func_195702_a();
            int ☃x = ☃.func_195714_b();
            int ☃xx = 0;
            int ☃xxx = 0;
            if (☃ > ☃x) {
               ☃xx = (☃ - ☃x) / 2;
               ☃ = ☃x;
            } else {
               ☃xxx = (☃x - ☃) / 2;
               ☃x = ☃;
            }

            try (NativeImage ☃ = new NativeImage(64, 64, false)) {
               ☃.func_195708_a(☃xx, ☃xxx, ☃, ☃x, ☃);
               ☃.func_209271_a(this.field_78531_r.func_71401_C().func_184109_z());
            } catch (IOException var27) {
               field_147710_q.warn("Couldn't save auto screenshot", var27);
            } finally {
               ☃.close();
            }
         });
      }
   }

   public void func_152430_c(float var1) {
      this.field_78531_r.field_195558_d.func_198094_a();
   }

   private boolean func_175070_n() {
      if (!this.field_175073_D) {
         return false;
      } else {
         Entity ☃ = this.field_78531_r.func_175606_aa();
         boolean ☃x = ☃ instanceof EntityPlayer && !this.field_78531_r.field_71474_y.field_74319_N;
         if (☃x && !((EntityPlayer)☃).field_71075_bZ.field_75099_e) {
            ItemStack ☃xx = ((EntityPlayer)☃).func_184614_ca();
            if (this.field_78531_r.field_71476_x != null && this.field_78531_r.field_71476_x.field_72313_a == RayTraceResult.Type.BLOCK) {
               BlockPos ☃xxx = this.field_78531_r.field_71476_x.func_178782_a();
               Block ☃xxxx = this.field_78531_r.field_71441_e.func_180495_p(☃xxx).func_177230_c();
               if (this.field_78531_r.field_71442_b.func_178889_l() == GameType.SPECTATOR) {
                  ☃x = ☃xxxx.func_149716_u() && this.field_78531_r.field_71441_e.func_175625_s(☃xxx) instanceof IInventory;
               } else {
                  BlockWorldState ☃xxx = new BlockWorldState(this.field_78531_r.field_71441_e, ☃xxx, false);
                  ☃x = !☃xx.func_190926_b()
                     && (
                        ☃xx.func_206848_a(this.field_78531_r.field_71441_e.func_205772_D(), ☃xxx)
                           || ☃xx.func_206847_b(this.field_78531_r.field_71441_e.func_205772_D(), ☃xxx)
                     );
               }
            }
         }

         return ☃x;
      }
   }

   public void func_78471_a(float var1, long var2) {
      this.field_78513_d.func_205106_a(☃);
      if (this.field_78531_r.func_175606_aa() == null) {
         this.field_78531_r.func_175607_a(this.field_78531_r.field_71439_g);
      }

      this.func_78473_a(☃);
      GlStateManager.func_179126_j();
      GlStateManager.func_179141_d();
      GlStateManager.func_179092_a(516, 0.5F);
      this.field_78531_r.field_71424_I.func_76320_a("center");
      this.func_181560_a(☃, ☃);
      this.field_78531_r.field_71424_I.func_76319_b();
   }

   private void func_181560_a(float var1, long var2) {
      WorldRenderer ☃ = this.field_78531_r.field_71438_f;
      ParticleManager ☃x = this.field_78531_r.field_71452_i;
      boolean ☃xx = this.func_175070_n();
      GlStateManager.func_179089_o();
      this.field_78531_r.field_71424_I.func_76318_c("clear");
      GlStateManager.func_179083_b(0, 0, this.field_78531_r.field_195558_d.func_198109_k(), this.field_78531_r.field_195558_d.func_198091_l());
      this.field_205003_A.func_78466_h(☃);
      GlStateManager.func_179086_m(16640);
      this.field_78531_r.field_71424_I.func_76318_c("camera");
      this.func_195460_g(☃);
      ActiveRenderInfo.func_197924_a(this.field_78531_r.field_71439_g, this.field_78531_r.field_71474_y.field_74320_O == 2, this.field_78530_s);
      this.field_78531_r.field_71424_I.func_76318_c("frustum");
      ClippingHelperImpl.func_78558_a();
      this.field_78531_r.field_71424_I.func_76318_c("culling");
      ICamera ☃xxx = new Frustum();
      Entity ☃xxxx = this.field_78531_r.func_175606_aa();
      double ☃xxxxx = ☃xxxx.field_70142_S + (☃xxxx.field_70165_t - ☃xxxx.field_70142_S) * (double)☃;
      double ☃xxxxxx = ☃xxxx.field_70137_T + (☃xxxx.field_70163_u - ☃xxxx.field_70137_T) * (double)☃;
      double ☃xxxxxxx = ☃xxxx.field_70136_U + (☃xxxx.field_70161_v - ☃xxxx.field_70136_U) * (double)☃;
      ☃xxx.func_78547_a(☃xxxxx, ☃xxxxxx, ☃xxxxxxx);
      if (this.field_78531_r.field_71474_y.field_151451_c >= 4) {
         this.field_205003_A.func_78468_a(-1, ☃);
         this.field_78531_r.field_71424_I.func_76318_c("sky");
         GlStateManager.func_179128_n(5889);
         GlStateManager.func_179096_D();
         GlStateManager.func_199294_a(
            Matrix4f.func_195876_a(
               this.func_195459_a(☃, true),
               (float)this.field_78531_r.field_195558_d.func_198109_k() / (float)this.field_78531_r.field_195558_d.func_198091_l(),
               0.05F,
               this.field_78530_s * 2.0F
            )
         );
         GlStateManager.func_179128_n(5888);
         ☃.func_195465_a(☃);
         GlStateManager.func_179128_n(5889);
         GlStateManager.func_179096_D();
         GlStateManager.func_199294_a(
            Matrix4f.func_195876_a(
               this.func_195459_a(☃, true),
               (float)this.field_78531_r.field_195558_d.func_198109_k() / (float)this.field_78531_r.field_195558_d.func_198091_l(),
               0.05F,
               this.field_78530_s * MathHelper.field_180189_a
            )
         );
         GlStateManager.func_179128_n(5888);
      }

      this.field_205003_A.func_78468_a(0, ☃);
      GlStateManager.func_179103_j(7425);
      if (☃xxxx.field_70163_u + (double)☃xxxx.func_70047_e() < 128.0) {
         this.func_195456_a(☃, ☃, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx);
      }

      this.field_78531_r.field_71424_I.func_76318_c("prepareterrain");
      this.field_205003_A.func_78468_a(0, ☃);
      this.field_78531_r.func_110434_K().func_110577_a(TextureMap.field_110575_b);
      RenderHelper.func_74518_a();
      this.field_78531_r.field_71424_I.func_76318_c("terrain_setup");
      ☃.func_195473_a(☃xxxx, ☃, ☃xxx, this.field_175084_ae++, this.field_78531_r.field_71439_g.func_175149_v());
      this.field_78531_r.field_71424_I.func_76318_c("updatechunks");
      this.field_78531_r.field_71438_f.func_174967_a(☃);
      this.field_78531_r.field_71424_I.func_76318_c("terrain");
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179094_E();
      GlStateManager.func_179118_c();
      ☃.func_195464_a(BlockRenderLayer.SOLID, (double)☃, ☃xxxx);
      GlStateManager.func_179141_d();
      ☃.func_195464_a(BlockRenderLayer.CUTOUT_MIPPED, (double)☃, ☃xxxx);
      this.field_78531_r.func_110434_K().func_110581_b(TextureMap.field_110575_b).func_174936_b(false, false);
      ☃.func_195464_a(BlockRenderLayer.CUTOUT, (double)☃, ☃xxxx);
      this.field_78531_r.func_110434_K().func_110581_b(TextureMap.field_110575_b).func_174935_a();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179092_a(516, 0.1F);
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179121_F();
      GlStateManager.func_179094_E();
      RenderHelper.func_74519_b();
      this.field_78531_r.field_71424_I.func_76318_c("entities");
      ☃.func_180446_a(☃xxxx, ☃xxx, ☃);
      RenderHelper.func_74518_a();
      this.func_175072_h();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179121_F();
      if (☃xx && this.field_78531_r.field_71476_x != null) {
         EntityPlayer ☃ = (EntityPlayer)☃xxxx;
         GlStateManager.func_179118_c();
         this.field_78531_r.field_71424_I.func_76318_c("outline");
         ☃.func_72731_b(☃, this.field_78531_r.field_71476_x, 0, ☃);
         GlStateManager.func_179141_d();
      }

      if (this.field_78531_r.field_184132_p.func_190074_a()) {
         this.field_78531_r.field_184132_p.func_190073_a(☃, ☃);
      }

      this.field_78531_r.field_71424_I.func_76318_c("destroyProgress");
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      this.field_78531_r.func_110434_K().func_110581_b(TextureMap.field_110575_b).func_174936_b(false, false);
      ☃.func_174981_a(Tessellator.func_178181_a(), Tessellator.func_178181_a().func_178180_c(), ☃xxxx, ☃);
      this.field_78531_r.func_110434_K().func_110581_b(TextureMap.field_110575_b).func_174935_a();
      GlStateManager.func_179084_k();
      this.func_180436_i();
      this.field_78531_r.field_71424_I.func_76318_c("litParticles");
      ☃x.func_78872_b(☃xxxx, ☃);
      RenderHelper.func_74518_a();
      this.field_205003_A.func_78468_a(0, ☃);
      this.field_78531_r.field_71424_I.func_76318_c("particles");
      ☃x.func_78874_a(☃xxxx, ☃);
      this.func_175072_h();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179089_o();
      this.field_78531_r.field_71424_I.func_76318_c("weather");
      this.func_78474_d(☃);
      GlStateManager.func_179132_a(true);
      ☃.func_180449_a(☃xxxx, ☃);
      GlStateManager.func_179084_k();
      GlStateManager.func_179089_o();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179092_a(516, 0.1F);
      this.field_205003_A.func_78468_a(0, ☃);
      GlStateManager.func_179147_l();
      GlStateManager.func_179132_a(false);
      this.field_78531_r.func_110434_K().func_110577_a(TextureMap.field_110575_b);
      GlStateManager.func_179103_j(7425);
      this.field_78531_r.field_71424_I.func_76318_c("translucent");
      ☃.func_195464_a(BlockRenderLayer.TRANSLUCENT, (double)☃, ☃xxxx);
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179089_o();
      GlStateManager.func_179084_k();
      GlStateManager.func_179106_n();
      if (☃xxxx.field_70163_u + (double)☃xxxx.func_70047_e() >= 128.0) {
         this.field_78531_r.field_71424_I.func_76318_c("aboveClouds");
         this.func_195456_a(☃, ☃, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx);
      }

      this.field_78531_r.field_71424_I.func_76318_c("hand");
      if (this.field_175074_C) {
         GlStateManager.func_179086_m(256);
         this.func_195457_h(☃);
      }
   }

   private void func_195456_a(WorldRenderer var1, float var2, double var3, double var5, double var7) {
      if (this.field_78531_r.field_71474_y.func_181147_e() != 0) {
         this.field_78531_r.field_71424_I.func_76318_c("clouds");
         GlStateManager.func_179128_n(5889);
         GlStateManager.func_179096_D();
         GlStateManager.func_199294_a(
            Matrix4f.func_195876_a(
               this.func_195459_a(☃, true),
               (float)this.field_78531_r.field_195558_d.func_198109_k() / (float)this.field_78531_r.field_195558_d.func_198091_l(),
               0.05F,
               this.field_78530_s * 4.0F
            )
         );
         GlStateManager.func_179128_n(5888);
         GlStateManager.func_179094_E();
         this.field_205003_A.func_78468_a(0, ☃);
         ☃.func_195466_a(☃, ☃, ☃, ☃);
         GlStateManager.func_179106_n();
         GlStateManager.func_179121_F();
         GlStateManager.func_179128_n(5889);
         GlStateManager.func_179096_D();
         GlStateManager.func_199294_a(
            Matrix4f.func_195876_a(
               this.func_195459_a(☃, true),
               (float)this.field_78531_r.field_195558_d.func_198109_k() / (float)this.field_78531_r.field_195558_d.func_198091_l(),
               0.05F,
               this.field_78530_s * MathHelper.field_180189_a
            )
         );
         GlStateManager.func_179128_n(5888);
      }
   }

   private void func_78484_h() {
      float ☃ = this.field_78531_r.field_71441_e.func_72867_j(1.0F);
      if (!this.field_78531_r.field_71474_y.field_74347_j) {
         ☃ /= 2.0F;
      }

      if (☃ != 0.0F) {
         this.field_78537_ab.setSeed((long)this.field_78529_t * 312987231L);
         Entity ☃ = this.field_78531_r.func_175606_aa();
         IWorldReaderBase ☃x = this.field_78531_r.field_71441_e;
         BlockPos ☃xx = new BlockPos(☃);
         int ☃xxx = 10;
         double ☃xxxx = 0.0;
         double ☃xxxxx = 0.0;
         double ☃xxxxxx = 0.0;
         int ☃xxxxxxx = 0;
         int ☃xxxxxxxx = (int)(100.0F * ☃ * ☃);
         if (this.field_78531_r.field_71474_y.field_74362_aa == 1) {
            ☃xxxxxxxx >>= 1;
         } else if (this.field_78531_r.field_71474_y.field_74362_aa == 2) {
            ☃xxxxxxxx = 0;
         }

         for(int ☃ = 0; ☃ < ☃xxxxxxxx; ++☃) {
            BlockPos ☃x = ☃x.func_205770_a(
               Heightmap.Type.MOTION_BLOCKING,
               ☃xx.func_177982_a(
                  this.field_78537_ab.nextInt(10) - this.field_78537_ab.nextInt(10), 0, this.field_78537_ab.nextInt(10) - this.field_78537_ab.nextInt(10)
               )
            );
            Biome ☃xx = ☃x.func_180494_b(☃x);
            BlockPos ☃xxx = ☃x.func_177977_b();
            if (☃x.func_177956_o() <= ☃xx.func_177956_o() + 10
               && ☃x.func_177956_o() >= ☃xx.func_177956_o() - 10
               && ☃xx.func_201851_b() == Biome.RainType.RAIN
               && ☃xx.func_180626_a(☃x) >= 0.15F) {
               double ☃xxxxxx = this.field_78537_ab.nextDouble();
               double ☃xxxxxxx = this.field_78537_ab.nextDouble();
               IBlockState ☃xxxxxxxx = ☃x.func_180495_p(☃xxx);
               IFluidState ☃xxxxxxxxx = ☃x.func_204610_c(☃x);
               VoxelShape ☃xxxxxxxxxx = ☃xxxxxxxx.func_196952_d(☃x, ☃xxx);
               double ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_197760_b(EnumFacing.Axis.Y, ☃xxxxxx, ☃xxxxxxx);
               double ☃xxxxxxxxxxxx = (double)☃xxxxxxxxx.func_206885_f();
               double ☃xxxx;
               double ☃xxxxx;
               if (☃xxxxxxxxxxx >= ☃xxxxxxxxxxxx) {
                  ☃xxxx = ☃xxxxxxxxxxx;
                  ☃xxxxx = ☃xxxxxxxxxx.func_197764_a(EnumFacing.Axis.Y, ☃xxxxxx, ☃xxxxxxx);
               } else {
                  ☃xxxx = 0.0;
                  ☃xxxxx = 0.0;
               }

               if (☃xxxx > -Double.MAX_VALUE) {
                  if (!☃xxxxxxxxx.func_206884_a(FluidTags.field_206960_b) && ☃xxxxxxxx.func_177230_c() != Blocks.field_196814_hQ) {
                     if (this.field_78537_ab.nextInt(++☃xxxxxxx) == 0) {
                        ☃xxxx = (double)☃xxx.func_177958_n() + ☃xxxxxx;
                        ☃xxxxx = (double)((float)☃xxx.func_177956_o() + 0.1F) + ☃xxxx - 1.0;
                        ☃xxxxxx = (double)☃xxx.func_177952_p() + ☃xxxxxxx;
                     }

                     this.field_78531_r
                        .field_71441_e
                        .func_195594_a(
                           Particles.field_197600_K,
                           (double)☃xxx.func_177958_n() + ☃xxxxxx,
                           (double)((float)☃xxx.func_177956_o() + 0.1F) + ☃xxxx,
                           (double)☃xxx.func_177952_p() + ☃xxxxxxx,
                           0.0,
                           0.0,
                           0.0
                        );
                  } else {
                     this.field_78531_r
                        .field_71441_e
                        .func_195594_a(
                           Particles.field_197601_L,
                           (double)☃x.func_177958_n() + ☃xxxxxx,
                           (double)((float)☃x.func_177956_o() + 0.1F) - ☃xxxxx,
                           (double)☃x.func_177952_p() + ☃xxxxxxx,
                           0.0,
                           0.0,
                           0.0
                        );
                  }
               }
            }
         }

         if (☃xxxxxxx > 0 && this.field_78537_ab.nextInt(3) < this.field_78534_ac++) {
            this.field_78534_ac = 0;
            if (☃xxxxx > (double)(☃xx.func_177956_o() + 1)
               && ☃x.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃xx).func_177956_o() > MathHelper.func_76141_d((float)☃xx.func_177956_o())) {
               this.field_78531_r.field_71441_e.func_184134_a(☃xxxx, ☃xxxxx, ☃xxxxxx, SoundEvents.field_187919_gs, SoundCategory.WEATHER, 0.1F, 0.5F, false);
            } else {
               this.field_78531_r.field_71441_e.func_184134_a(☃xxxx, ☃xxxxx, ☃xxxxxx, SoundEvents.field_187918_gr, SoundCategory.WEATHER, 0.2F, 1.0F, false);
            }
         }
      }
   }

   protected void func_78474_d(float var1) {
      float ☃ = this.field_78531_r.field_71441_e.func_72867_j(☃);
      if (!(☃ <= 0.0F)) {
         this.func_180436_i();
         Entity ☃x = this.field_78531_r.func_175606_aa();
         World ☃xx = this.field_78531_r.field_71441_e;
         int ☃xxx = MathHelper.func_76128_c(☃x.field_70165_t);
         int ☃xxxx = MathHelper.func_76128_c(☃x.field_70163_u);
         int ☃xxxxx = MathHelper.func_76128_c(☃x.field_70161_v);
         Tessellator ☃xxxxxx = Tessellator.func_178181_a();
         BufferBuilder ☃xxxxxxx = ☃xxxxxx.func_178180_c();
         GlStateManager.func_179129_p();
         GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         GlStateManager.func_179092_a(516, 0.1F);
         double ☃xxxxxxxx = ☃x.field_70142_S + (☃x.field_70165_t - ☃x.field_70142_S) * (double)☃;
         double ☃xxxxxxxxx = ☃x.field_70137_T + (☃x.field_70163_u - ☃x.field_70137_T) * (double)☃;
         double ☃xxxxxxxxxx = ☃x.field_70136_U + (☃x.field_70161_v - ☃x.field_70136_U) * (double)☃;
         int ☃xxxxxxxxxxx = MathHelper.func_76128_c(☃xxxxxxxxx);
         int ☃xxxxxxxxxxxx = 5;
         if (this.field_78531_r.field_71474_y.field_74347_j) {
            ☃xxxxxxxxxxxx = 10;
         }

         int ☃x = -1;
         float ☃xx = (float)this.field_78529_t + ☃;
         ☃xxxxxxx.func_178969_c(-☃xxxxxxxx, -☃xxxxxxxxx, -☃xxxxxxxxxx);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();

         for(int ☃xxxx = ☃xxxxx - ☃xxxxxxxxxxxx; ☃xxxx <= ☃xxxxx + ☃xxxxxxxxxxxx; ++☃xxxx) {
            for(int ☃xxxxx = ☃xxx - ☃xxxxxxxxxxxx; ☃xxxxx <= ☃xxx + ☃xxxxxxxxxxxx; ++☃xxxxx) {
               int ☃xxxxxx = (☃xxxx - ☃xxxxx + 16) * 32 + ☃xxxxx - ☃xxx + 16;
               double ☃xxxxxxx = (double)this.field_175076_N[☃xxxxxx] * 0.5;
               double ☃xxxxxxxx = (double)this.field_175077_O[☃xxxxxx] * 0.5;
               ☃xxx.func_181079_c(☃xxxxx, 0, ☃xxxx);
               Biome ☃xxxxxxxxx = ☃xx.func_180494_b(☃xxx);
               if (☃xxxxxxxxx.func_201851_b() != Biome.RainType.NONE) {
                  int ☃xxxxxxxxxx = ☃xx.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃xxx).func_177956_o();
                  int ☃xxxxxxxxxxx = ☃xxxx - ☃xxxxxxxxxxxx;
                  int ☃xxxxxxxxxxxx = ☃xxxx + ☃xxxxxxxxxxxx;
                  if (☃xxxxxxxxxxx < ☃xxxxxxxxxx) {
                     ☃xxxxxxxxxxx = ☃xxxxxxxxxx;
                  }

                  if (☃xxxxxxxxxxxx < ☃xxxxxxxxxx) {
                     ☃xxxxxxxxxxxx = ☃xxxxxxxxxx;
                  }

                  int ☃xxxxxxxxxx = ☃xxxxxxxxxx;
                  if (☃xxxxxxxxxx < ☃xxxxxxxxxxx) {
                     ☃xxxxxxxxxx = ☃xxxxxxxxxxx;
                  }

                  if (☃xxxxxxxxxxx != ☃xxxxxxxxxxxx) {
                     this.field_78537_ab.setSeed((long)(☃xxxxx * ☃xxxxx * 3121 + ☃xxxxx * 45238971 ^ ☃xxxx * ☃xxxx * 418711 + ☃xxxx * 13761));
                     ☃xxx.func_181079_c(☃xxxxx, ☃xxxxxxxxxxx, ☃xxxx);
                     float ☃xxxxxxxxxx = ☃xxxxxxxxx.func_180626_a(☃xxx);
                     if (☃xxxxxxxxxx >= 0.15F) {
                        if (☃x != 0) {
                           if (☃x >= 0) {
                              ☃xxxxxx.func_78381_a();
                           }

                           ☃x = 0;
                           this.field_78531_r.func_110434_K().func_110577_a(field_110924_q);
                           ☃xxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181704_d);
                        }

                        double ☃xxxxxxxxxxx = -(
                              (double)(this.field_78529_t + ☃xxxxx * ☃xxxxx * 3121 + ☃xxxxx * 45238971 + ☃xxxx * ☃xxxx * 418711 + ☃xxxx * 13761 & 31)
                                 + (double)☃
                           )
                           / 32.0
                           * (3.0 + this.field_78537_ab.nextDouble());
                        double ☃xxxxxxxxxxxx = (double)((float)☃xxxxx + 0.5F) - ☃x.field_70165_t;
                        double ☃xxxxxxxxxxxxx = (double)((float)☃xxxx + 0.5F) - ☃x.field_70161_v;
                        float ☃xxxxxxxxxxxxxx = MathHelper.func_76133_a(☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxx * ☃xxxxxxxxxxxxx) / (float)☃xxxxxxxxxxxx;
                        float ☃xxxxxxxxxxxxxxx = ((1.0F - ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx) * 0.5F + 0.5F) * ☃;
                        ☃xxx.func_181079_c(☃xxxxx, ☃xxxxxxxxxx, ☃xxxx);
                        int ☃xxxxxxxxxxxxxxxx = ☃xx.func_175626_b(☃xxx, 0);
                        int ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx >> 16 & 65535;
                        int ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx & 65535;
                        ☃xxxxxxx.func_181662_b((double)☃xxxxx - ☃xxxxxxx + 0.5, (double)☃xxxxxxxxxxxx, (double)☃xxxx - ☃xxxxxxxx + 0.5)
                           .func_187315_a(0.0, (double)☃xxxxxxxxxxx * 0.25 + ☃xxxxxxxxxxx)
                           .func_181666_a(1.0F, 1.0F, 1.0F, ☃xxxxxxxxxxxxxxx)
                           .func_187314_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx)
                           .func_181675_d();
                        ☃xxxxxxx.func_181662_b((double)☃xxxxx + ☃xxxxxxx + 0.5, (double)☃xxxxxxxxxxxx, (double)☃xxxx + ☃xxxxxxxx + 0.5)
                           .func_187315_a(1.0, (double)☃xxxxxxxxxxx * 0.25 + ☃xxxxxxxxxxx)
                           .func_181666_a(1.0F, 1.0F, 1.0F, ☃xxxxxxxxxxxxxxx)
                           .func_187314_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx)
                           .func_181675_d();
                        ☃xxxxxxx.func_181662_b((double)☃xxxxx + ☃xxxxxxx + 0.5, (double)☃xxxxxxxxxxx, (double)☃xxxx + ☃xxxxxxxx + 0.5)
                           .func_187315_a(1.0, (double)☃xxxxxxxxxxxx * 0.25 + ☃xxxxxxxxxxx)
                           .func_181666_a(1.0F, 1.0F, 1.0F, ☃xxxxxxxxxxxxxxx)
                           .func_187314_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx)
                           .func_181675_d();
                        ☃xxxxxxx.func_181662_b((double)☃xxxxx - ☃xxxxxxx + 0.5, (double)☃xxxxxxxxxxx, (double)☃xxxx - ☃xxxxxxxx + 0.5)
                           .func_187315_a(0.0, (double)☃xxxxxxxxxxxx * 0.25 + ☃xxxxxxxxxxx)
                           .func_181666_a(1.0F, 1.0F, 1.0F, ☃xxxxxxxxxxxxxxx)
                           .func_187314_a(☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx)
                           .func_181675_d();
                     } else {
                        if (☃x != 1) {
                           if (☃x >= 0) {
                              ☃xxxxxx.func_78381_a();
                           }

                           ☃x = 1;
                           this.field_78531_r.func_110434_K().func_110577_a(field_110923_r);
                           ☃xxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181704_d);
                        }

                        double ☃xxxxxxxxxx = (double)(-((float)(this.field_78529_t & 511) + ☃) / 512.0F);
                        double ☃xxxxxxxxxxx = this.field_78537_ab.nextDouble() + (double)☃xx * 0.01 * (double)((float)this.field_78537_ab.nextGaussian());
                        double ☃xxxxxxxxxxxx = this.field_78537_ab.nextDouble() + (double)(☃xx * (float)this.field_78537_ab.nextGaussian()) * 0.001;
                        double ☃xxxxxxxxxxxxx = (double)((float)☃xxxxx + 0.5F) - ☃x.field_70165_t;
                        double ☃xxxxxxxxxxxxxx = (double)((float)☃xxxx + 0.5F) - ☃x.field_70161_v;
                        float ☃xxxxxxxxxxxxxxx = MathHelper.func_76133_a(☃xxxxxxxxxxxxx * ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx)
                           / (float)☃xxxxxxxxxxxx;
                        float ☃xxxxxxxxxxxxxxxx = ((1.0F - ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx) * 0.3F + 0.5F) * ☃;
                        ☃xxx.func_181079_c(☃xxxxx, ☃xxxxxxxxxx, ☃xxxx);
                        int ☃xxxxxxxxxxxxxxxxx = (☃xx.func_175626_b(☃xxx, 0) * 3 + 15728880) / 4;
                        int ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx >> 16 & 65535;
                        int ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx & 65535;
                        ☃xxxxxxx.func_181662_b((double)☃xxxxx - ☃xxxxxxx + 0.5, (double)☃xxxxxxxxxxxx, (double)☃xxxx - ☃xxxxxxxx + 0.5)
                           .func_187315_a(0.0 + ☃xxxxxxxxxxx, (double)☃xxxxxxxxxxx * 0.25 + ☃xxxxxxxxxx + ☃xxxxxxxxxxxx)
                           .func_181666_a(1.0F, 1.0F, 1.0F, ☃xxxxxxxxxxxxxxxx)
                           .func_187314_a(☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx)
                           .func_181675_d();
                        ☃xxxxxxx.func_181662_b((double)☃xxxxx + ☃xxxxxxx + 0.5, (double)☃xxxxxxxxxxxx, (double)☃xxxx + ☃xxxxxxxx + 0.5)
                           .func_187315_a(1.0 + ☃xxxxxxxxxxx, (double)☃xxxxxxxxxxx * 0.25 + ☃xxxxxxxxxx + ☃xxxxxxxxxxxx)
                           .func_181666_a(1.0F, 1.0F, 1.0F, ☃xxxxxxxxxxxxxxxx)
                           .func_187314_a(☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx)
                           .func_181675_d();
                        ☃xxxxxxx.func_181662_b((double)☃xxxxx + ☃xxxxxxx + 0.5, (double)☃xxxxxxxxxxx, (double)☃xxxx + ☃xxxxxxxx + 0.5)
                           .func_187315_a(1.0 + ☃xxxxxxxxxxx, (double)☃xxxxxxxxxxxx * 0.25 + ☃xxxxxxxxxx + ☃xxxxxxxxxxxx)
                           .func_181666_a(1.0F, 1.0F, 1.0F, ☃xxxxxxxxxxxxxxxx)
                           .func_187314_a(☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx)
                           .func_181675_d();
                        ☃xxxxxxx.func_181662_b((double)☃xxxxx - ☃xxxxxxx + 0.5, (double)☃xxxxxxxxxxx, (double)☃xxxx - ☃xxxxxxxx + 0.5)
                           .func_187315_a(0.0 + ☃xxxxxxxxxxx, (double)☃xxxxxxxxxxxx * 0.25 + ☃xxxxxxxxxx + ☃xxxxxxxxxxxx)
                           .func_181666_a(1.0F, 1.0F, 1.0F, ☃xxxxxxxxxxxxxxxx)
                           .func_187314_a(☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx)
                           .func_181675_d();
                     }
                  }
               }
            }
         }

         if (☃x >= 0) {
            ☃xxxxxx.func_78381_a();
         }

         ☃xxxxxxx.func_178969_c(0.0, 0.0, 0.0);
         GlStateManager.func_179089_o();
         GlStateManager.func_179084_k();
         GlStateManager.func_179092_a(516, 0.1F);
         this.func_175072_h();
      }
   }

   public void func_191514_d(boolean var1) {
      this.field_205003_A.func_205090_a(☃);
   }

   public void func_190564_k() {
      this.field_190566_ab = null;
      this.field_147709_v.func_148249_a();
   }

   public MapItemRenderer func_147701_i() {
      return this.field_147709_v;
   }

   public static void func_189692_a(
      FontRenderer var0, String var1, float var2, float var3, float var4, int var5, float var6, float var7, boolean var8, boolean var9
   ) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(☃, ☃, ☃);
      GlStateManager.func_187432_a(0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(-☃, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)(☃ ? -1 : 1) * ☃, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(-0.025F, -0.025F, 0.025F);
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(false);
      if (!☃) {
         GlStateManager.func_179097_i();
      }

      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      int ☃ = ☃.func_78256_a(☃) / 2;
      GlStateManager.func_179090_x();
      Tessellator ☃x = Tessellator.func_178181_a();
      BufferBuilder ☃xx = ☃x.func_178180_c();
      ☃xx.func_181668_a(7, DefaultVertexFormats.field_181706_f);
      ☃xx.func_181662_b((double)(-☃ - 1), (double)(-1 + ☃), 0.0).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
      ☃xx.func_181662_b((double)(-☃ - 1), (double)(8 + ☃), 0.0).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
      ☃xx.func_181662_b((double)(☃ + 1), (double)(8 + ☃), 0.0).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
      ☃xx.func_181662_b((double)(☃ + 1), (double)(-1 + ☃), 0.0).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
      ☃x.func_78381_a();
      GlStateManager.func_179098_w();
      if (!☃) {
         ☃.func_211126_b(☃, (float)(-☃.func_78256_a(☃) / 2), (float)☃, 553648127);
         GlStateManager.func_179126_j();
      }

      GlStateManager.func_179132_a(true);
      ☃.func_211126_b(☃, (float)(-☃.func_78256_a(☃) / 2), (float)☃, ☃ ? 553648127 : -1);
      GlStateManager.func_179145_e();
      GlStateManager.func_179084_k();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179121_F();
   }

   public void func_190565_a(ItemStack var1) {
      this.field_190566_ab = ☃;
      this.field_190567_ac = 40;
      this.field_190568_ad = this.field_78537_ab.nextFloat() * 2.0F - 1.0F;
      this.field_190569_ae = this.field_78537_ab.nextFloat() * 2.0F - 1.0F;
   }

   private void func_190563_a(int var1, int var2, float var3) {
      if (this.field_190566_ab != null && this.field_190567_ac > 0) {
         int ☃ = 40 - this.field_190567_ac;
         float ☃x = ((float)☃ + ☃) / 40.0F;
         float ☃xx = ☃x * ☃x;
         float ☃xxx = ☃x * ☃xx;
         float ☃xxxx = 10.25F * ☃xxx * ☃xx - 24.95F * ☃xx * ☃xx + 25.5F * ☃xxx - 13.8F * ☃xx + 4.0F * ☃x;
         float ☃xxxxx = ☃xxxx * (float) Math.PI;
         float ☃xxxxxx = this.field_190568_ad * (float)(☃ / 4);
         float ☃xxxxxxx = this.field_190569_ae * (float)(☃ / 4);
         GlStateManager.func_179141_d();
         GlStateManager.func_179094_E();
         GlStateManager.func_179123_a();
         GlStateManager.func_179126_j();
         GlStateManager.func_179129_p();
         RenderHelper.func_74519_b();
         GlStateManager.func_179109_b(
            (float)(☃ / 2) + ☃xxxxxx * MathHelper.func_76135_e(MathHelper.func_76126_a(☃xxxxx * 2.0F)),
            (float)(☃ / 2) + ☃xxxxxxx * MathHelper.func_76135_e(MathHelper.func_76126_a(☃xxxxx * 2.0F)),
            -50.0F
         );
         float ☃xxxxxxxx = 50.0F + 175.0F * MathHelper.func_76126_a(☃xxxxx);
         GlStateManager.func_179152_a(☃xxxxxxxx, -☃xxxxxxxx, ☃xxxxxxxx);
         GlStateManager.func_179114_b(900.0F * MathHelper.func_76135_e(MathHelper.func_76126_a(☃xxxxx)), 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(6.0F * MathHelper.func_76134_b(☃x * 8.0F), 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(6.0F * MathHelper.func_76134_b(☃x * 8.0F), 0.0F, 0.0F, 1.0F);
         this.field_78531_r.func_175599_af().func_181564_a(this.field_190566_ab, ItemCameraTransforms.TransformType.FIXED);
         GlStateManager.func_179099_b();
         GlStateManager.func_179121_F();
         RenderHelper.func_74518_a();
         GlStateManager.func_179089_o();
         GlStateManager.func_179097_i();
      }
   }

   public Minecraft func_205000_l() {
      return this.field_78531_r;
   }

   public float func_205002_d(float var1) {
      return this.field_82832_V + (this.field_82831_U - this.field_82832_V) * ☃;
   }

   public float func_205001_m() {
      return this.field_78530_s;
   }
}
