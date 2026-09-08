package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAbstractSkull;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSign;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoneMeal;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldRenderer implements IWorldEventListener, AutoCloseable, IResourceManagerReloadListener {
   private static final Logger field_147599_m = LogManager.getLogger();
   private static final ResourceLocation field_110927_h = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation field_110928_i = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation field_110925_j = new ResourceLocation("textures/environment/clouds.png");
   private static final ResourceLocation field_110926_k = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation field_175006_g = new ResourceLocation("textures/misc/forcefield.png");
   public static final EnumFacing[] field_200006_a = EnumFacing.values();
   private final Minecraft field_72777_q;
   private final TextureManager field_72770_i;
   private final RenderManager field_175010_j;
   private WorldClient field_72769_h;
   private Set<RenderChunk> field_175009_l = Sets.<RenderChunk>newLinkedHashSet();
   private List<WorldRenderer.ContainerLocalRenderInformation> field_72755_R = Lists.<WorldRenderer.ContainerLocalRenderInformation>newArrayListWithCapacity(
      69696
   );
   private final Set<TileEntity> field_181024_n = Sets.<TileEntity>newHashSet();
   private ViewFrustum field_175008_n;
   private int field_72772_v = -1;
   private int field_72771_w = -1;
   private int field_72781_x = -1;
   private final VertexFormat field_175014_r;
   private VertexBuffer field_175013_s;
   private VertexBuffer field_175012_t;
   private VertexBuffer field_175011_u;
   private final int field_204606_x = 28;
   private boolean field_204607_y = true;
   private int field_204608_z = -1;
   private VertexBuffer field_204601_A;
   private int field_72773_u;
   private final Map<Integer, DestroyBlockProgress> field_72738_E = Maps.newHashMap();
   private final Map<BlockPos, ISound> field_147593_P = Maps.<BlockPos, ISound>newHashMap();
   private final TextureAtlasSprite[] field_94141_F = new TextureAtlasSprite[10];
   private Framebuffer field_175015_z;
   private ShaderGroup field_174991_A;
   private double field_174992_B = Double.MIN_VALUE;
   private double field_174993_C = Double.MIN_VALUE;
   private double field_174987_D = Double.MIN_VALUE;
   private int field_174988_E = Integer.MIN_VALUE;
   private int field_174989_F = Integer.MIN_VALUE;
   private int field_174990_G = Integer.MIN_VALUE;
   private double field_174997_H = Double.MIN_VALUE;
   private double field_174998_I = Double.MIN_VALUE;
   private double field_174999_J = Double.MIN_VALUE;
   private double field_175000_K = Double.MIN_VALUE;
   private double field_174994_L = Double.MIN_VALUE;
   private int field_204602_S = Integer.MIN_VALUE;
   private int field_204603_T = Integer.MIN_VALUE;
   private int field_204604_U = Integer.MIN_VALUE;
   private Vec3d field_204605_V = Vec3d.field_186680_a;
   private int field_204800_W = -1;
   private ChunkRenderDispatcher field_174995_M;
   private ChunkRenderContainer field_174996_N;
   private int field_72739_F = -1;
   private int field_72740_G = 2;
   private int field_72748_H;
   private int field_72749_I;
   private int field_72750_J;
   private boolean field_175002_T;
   private ClippingHelper field_175001_U;
   private final Vector4f[] field_175004_V = new Vector4f[8];
   private final Vector3d field_175003_W = new Vector3d();
   private boolean field_175005_X;
   private IRenderChunkFactory field_175007_a;
   private double field_147596_f;
   private double field_147597_g;
   private double field_147602_h;
   private boolean field_147595_R = true;
   private boolean field_184386_ad;
   private final Set<BlockPos> field_184387_ae = Sets.<BlockPos>newHashSet();

   public WorldRenderer(Minecraft var1) {
      this.field_72777_q = ☃;
      this.field_175010_j = ☃.func_175598_ae();
      this.field_72770_i = ☃.func_110434_K();
      this.field_72770_i.func_110577_a(field_175006_g);
      GlStateManager.func_187421_b(3553, 10242, 10497);
      GlStateManager.func_187421_b(3553, 10243, 10497);
      GlStateManager.func_179144_i(0);
      this.func_174971_n();
      this.field_175005_X = OpenGlHelper.func_176075_f();
      if (this.field_175005_X) {
         this.field_174996_N = new VboRenderList();
         this.field_175007_a = RenderChunk::new;
      } else {
         this.field_174996_N = new RenderList();
         this.field_175007_a = ListedRenderChunk::new;
      }

      this.field_175014_r = new VertexFormat();
      this.field_175014_r.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
      this.func_174963_q();
      this.func_174980_p();
      this.func_174964_o();
   }

   public void close() {
      if (this.field_174991_A != null) {
         this.field_174991_A.close();
      }
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      this.func_174971_n();
   }

   private void func_174971_n() {
      TextureMap ☃ = this.field_72777_q.func_147117_R();
      this.field_94141_F[0] = ☃.func_195424_a(ModelBakery.field_207770_h);
      this.field_94141_F[1] = ☃.func_195424_a(ModelBakery.field_207771_i);
      this.field_94141_F[2] = ☃.func_195424_a(ModelBakery.field_207772_j);
      this.field_94141_F[3] = ☃.func_195424_a(ModelBakery.field_207773_k);
      this.field_94141_F[4] = ☃.func_195424_a(ModelBakery.field_207774_l);
      this.field_94141_F[5] = ☃.func_195424_a(ModelBakery.field_207775_m);
      this.field_94141_F[6] = ☃.func_195424_a(ModelBakery.field_207776_n);
      this.field_94141_F[7] = ☃.func_195424_a(ModelBakery.field_207777_o);
      this.field_94141_F[8] = ☃.func_195424_a(ModelBakery.field_207778_p);
      this.field_94141_F[9] = ☃.func_195424_a(ModelBakery.field_207779_q);
   }

   public void func_174966_b() {
      if (OpenGlHelper.field_148824_g) {
         if (ShaderLinkHelper.func_148074_b() == null) {
            ShaderLinkHelper.func_148076_a();
         }

         ResourceLocation ☃ = new ResourceLocation("shaders/post/entity_outline.json");

         try {
            this.field_174991_A = new ShaderGroup(this.field_72777_q.func_110434_K(), this.field_72777_q.func_195551_G(), this.field_72777_q.func_147110_a(), ☃);
            this.field_174991_A.func_148026_a(this.field_72777_q.field_195558_d.func_198109_k(), this.field_72777_q.field_195558_d.func_198091_l());
            this.field_175015_z = this.field_174991_A.func_177066_a("final");
         } catch (IOException var3) {
            field_147599_m.warn("Failed to load shader: {}", ☃, var3);
            this.field_174991_A = null;
            this.field_175015_z = null;
         } catch (JsonSyntaxException var4) {
            field_147599_m.warn("Failed to load shader: {}", ☃, var4);
            this.field_174991_A = null;
            this.field_175015_z = null;
         }
      } else {
         this.field_174991_A = null;
         this.field_175015_z = null;
      }
   }

   public void func_174975_c() {
      if (this.func_174985_d()) {
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ONE
         );
         this.field_175015_z.func_178038_a(this.field_72777_q.field_195558_d.func_198109_k(), this.field_72777_q.field_195558_d.func_198091_l(), false);
         GlStateManager.func_179084_k();
      }
   }

   protected boolean func_174985_d() {
      return this.field_175015_z != null && this.field_174991_A != null && this.field_72777_q.field_71439_g != null;
   }

   private void func_174964_o() {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      if (this.field_175011_u != null) {
         this.field_175011_u.func_177362_c();
      }

      if (this.field_72781_x >= 0) {
         GLAllocation.func_74523_b(this.field_72781_x);
         this.field_72781_x = -1;
      }

      if (this.field_175005_X) {
         this.field_175011_u = new VertexBuffer(this.field_175014_r);
         this.func_174968_a(☃x, -16.0F, true);
         ☃x.func_178977_d();
         ☃x.func_178965_a();
         this.field_175011_u.func_181722_a(☃x.func_178966_f());
      } else {
         this.field_72781_x = GLAllocation.func_74526_a(1);
         GlStateManager.func_187423_f(this.field_72781_x, 4864);
         this.func_174968_a(☃x, -16.0F, true);
         ☃.func_78381_a();
         GlStateManager.func_187415_K();
      }
   }

   private void func_174980_p() {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      if (this.field_175012_t != null) {
         this.field_175012_t.func_177362_c();
      }

      if (this.field_72771_w >= 0) {
         GLAllocation.func_74523_b(this.field_72771_w);
         this.field_72771_w = -1;
      }

      if (this.field_175005_X) {
         this.field_175012_t = new VertexBuffer(this.field_175014_r);
         this.func_174968_a(☃x, 16.0F, false);
         ☃x.func_178977_d();
         ☃x.func_178965_a();
         this.field_175012_t.func_181722_a(☃x.func_178966_f());
      } else {
         this.field_72771_w = GLAllocation.func_74526_a(1);
         GlStateManager.func_187423_f(this.field_72771_w, 4864);
         this.func_174968_a(☃x, 16.0F, false);
         ☃.func_78381_a();
         GlStateManager.func_187415_K();
      }
   }

   private void func_174968_a(BufferBuilder var1, float var2, boolean var3) {
      int ☃ = 64;
      int ☃x = 6;
      ☃.func_181668_a(7, DefaultVertexFormats.field_181705_e);

      for(int ☃xx = -384; ☃xx <= 384; ☃xx += 64) {
         for(int ☃xxx = -384; ☃xxx <= 384; ☃xxx += 64) {
            float ☃xxxx = (float)☃xx;
            float ☃xxxxx = (float)(☃xx + 64);
            if (☃) {
               ☃xxxxx = (float)☃xx;
               ☃xxxx = (float)(☃xx + 64);
            }

            ☃.func_181662_b((double)☃xxxx, (double)☃, (double)☃xxx).func_181675_d();
            ☃.func_181662_b((double)☃xxxxx, (double)☃, (double)☃xxx).func_181675_d();
            ☃.func_181662_b((double)☃xxxxx, (double)☃, (double)(☃xxx + 64)).func_181675_d();
            ☃.func_181662_b((double)☃xxxx, (double)☃, (double)(☃xxx + 64)).func_181675_d();
         }
      }
   }

   private void func_174963_q() {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      if (this.field_175013_s != null) {
         this.field_175013_s.func_177362_c();
      }

      if (this.field_72772_v >= 0) {
         GLAllocation.func_74523_b(this.field_72772_v);
         this.field_72772_v = -1;
      }

      if (this.field_175005_X) {
         this.field_175013_s = new VertexBuffer(this.field_175014_r);
         this.func_180444_a(☃x);
         ☃x.func_178977_d();
         ☃x.func_178965_a();
         this.field_175013_s.func_181722_a(☃x.func_178966_f());
      } else {
         this.field_72772_v = GLAllocation.func_74526_a(1);
         GlStateManager.func_179094_E();
         GlStateManager.func_187423_f(this.field_72772_v, 4864);
         this.func_180444_a(☃x);
         ☃.func_78381_a();
         GlStateManager.func_187415_K();
         GlStateManager.func_179121_F();
      }
   }

   private void func_180444_a(BufferBuilder var1) {
      Random ☃ = new Random(10842L);
      ☃.func_181668_a(7, DefaultVertexFormats.field_181705_e);

      for(int ☃x = 0; ☃x < 1500; ++☃x) {
         double ☃xx = (double)(☃.nextFloat() * 2.0F - 1.0F);
         double ☃xxx = (double)(☃.nextFloat() * 2.0F - 1.0F);
         double ☃xxxx = (double)(☃.nextFloat() * 2.0F - 1.0F);
         double ☃xxxxx = (double)(0.15F + ☃.nextFloat() * 0.1F);
         double ☃xxxxxx = ☃xx * ☃xx + ☃xxx * ☃xxx + ☃xxxx * ☃xxxx;
         if (☃xxxxxx < 1.0 && ☃xxxxxx > 0.01) {
            ☃xxxxxx = 1.0 / Math.sqrt(☃xxxxxx);
            ☃xx *= ☃xxxxxx;
            ☃xxx *= ☃xxxxxx;
            ☃xxxx *= ☃xxxxxx;
            double ☃xxxxxxx = ☃xx * 100.0;
            double ☃xxxxxxxx = ☃xxx * 100.0;
            double ☃xxxxxxxxx = ☃xxxx * 100.0;
            double ☃xxxxxxxxxx = Math.atan2(☃xx, ☃xxxx);
            double ☃xxxxxxxxxxx = Math.sin(☃xxxxxxxxxx);
            double ☃xxxxxxxxxxxx = Math.cos(☃xxxxxxxxxx);
            double ☃xxxxxxxxxxxxx = Math.atan2(Math.sqrt(☃xx * ☃xx + ☃xxxx * ☃xxxx), ☃xxx);
            double ☃xxxxxxxxxxxxxx = Math.sin(☃xxxxxxxxxxxxx);
            double ☃xxxxxxxxxxxxxxx = Math.cos(☃xxxxxxxxxxxxx);
            double ☃xxxxxxxxxxxxxxxx = ☃.nextDouble() * Math.PI * 2.0;
            double ☃xxxxxxxxxxxxxxxxx = Math.sin(☃xxxxxxxxxxxxxxxx);
            double ☃xxxxxxxxxxxxxxxxxx = Math.cos(☃xxxxxxxxxxxxxxxx);

            for(int ☃xxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxxx) {
               double ☃xxxxxxxxxxxxxxxxxxxx = 0.0;
               double ☃xxxxxxxxxxxxxxxxxxxxx = (double)((☃xxxxxxxxxxxxxxxxxxx & 2) - 1) * ☃xxxxx;
               double ☃xxxxxxxxxxxxxxxxxxxxxx = (double)((☃xxxxxxxxxxxxxxxxxxx + 1 & 2) - 1) * ☃xxxxx;
               double ☃xxxxxxxxxxxxxxxxxxxxxxx = 0.0;
               double ☃xxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxx;
               double ☃xxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxx;
               double ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx + 0.0 * ☃xxxxxxxxxxxxxxx;
               double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 * ☃xxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx;
               double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxx;
               double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxx;
               ☃.func_181662_b(☃xxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                  .func_181675_d();
            }
         }
      }
   }

   public void func_72732_a(@Nullable WorldClient var1) {
      if (this.field_72769_h != null) {
         this.field_72769_h.func_72848_b(this);
      }

      this.field_174992_B = Double.MIN_VALUE;
      this.field_174993_C = Double.MIN_VALUE;
      this.field_174987_D = Double.MIN_VALUE;
      this.field_174988_E = Integer.MIN_VALUE;
      this.field_174989_F = Integer.MIN_VALUE;
      this.field_174990_G = Integer.MIN_VALUE;
      this.field_175010_j.func_78717_a(☃);
      this.field_72769_h = ☃;
      if (☃ != null) {
         ☃.func_72954_a(this);
         this.func_72712_a();
      } else {
         this.field_175009_l.clear();
         this.field_72755_R.clear();
         if (this.field_175008_n != null) {
            this.field_175008_n.func_178160_a();
            this.field_175008_n = null;
         }

         if (this.field_174995_M != null) {
            this.field_174995_M.func_188244_g();
         }

         this.field_174995_M = null;
      }
   }

   public void func_72712_a() {
      if (this.field_72769_h != null) {
         if (this.field_174995_M == null) {
            this.field_174995_M = new ChunkRenderDispatcher();
         }

         this.field_147595_R = true;
         this.field_204607_y = true;
         BlockLeaves.func_196475_b(this.field_72777_q.field_71474_y.field_74347_j);
         this.field_72739_F = this.field_72777_q.field_71474_y.field_151451_c;
         boolean ☃ = this.field_175005_X;
         this.field_175005_X = OpenGlHelper.func_176075_f();
         if (☃ && !this.field_175005_X) {
            this.field_174996_N = new RenderList();
            this.field_175007_a = ListedRenderChunk::new;
         } else if (!☃ && this.field_175005_X) {
            this.field_174996_N = new VboRenderList();
            this.field_175007_a = RenderChunk::new;
         }

         if (☃ != this.field_175005_X) {
            this.func_174963_q();
            this.func_174980_p();
            this.func_174964_o();
         }

         if (this.field_175008_n != null) {
            this.field_175008_n.func_178160_a();
         }

         this.func_174986_e();
         synchronized(this.field_181024_n) {
            this.field_181024_n.clear();
         }

         this.field_175008_n = new ViewFrustum(this.field_72769_h, this.field_72777_q.field_71474_y.field_151451_c, this, this.field_175007_a);
         if (this.field_72769_h != null) {
            Entity ☃ = this.field_72777_q.func_175606_aa();
            if (☃ != null) {
               this.field_175008_n.func_178163_a(☃.field_70165_t, ☃.field_70161_v);
            }
         }

         this.field_72740_G = 2;
      }
   }

   protected void func_174986_e() {
      this.field_175009_l.clear();
      this.field_174995_M.func_178514_b();
   }

   public void func_72720_a(int var1, int var2) {
      this.func_174979_m();
      if (OpenGlHelper.field_148824_g) {
         if (this.field_174991_A != null) {
            this.field_174991_A.func_148026_a(☃, ☃);
         }
      }
   }

   public void func_180446_a(Entity var1, ICamera var2, float var3) {
      if (this.field_72740_G > 0) {
         --this.field_72740_G;
      } else {
         double ☃ = ☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * (double)☃;
         double ☃x = ☃.field_70167_r + (☃.field_70163_u - ☃.field_70167_r) * (double)☃;
         double ☃xx = ☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * (double)☃;
         this.field_72769_h.field_72984_F.func_76320_a("prepare");
         TileEntityRendererDispatcher.field_147556_a
            .func_190056_a(
               this.field_72769_h,
               this.field_72777_q.func_110434_K(),
               this.field_72777_q.field_71466_p,
               this.field_72777_q.func_175606_aa(),
               this.field_72777_q.field_71476_x,
               ☃
            );
         this.field_175010_j
            .func_180597_a(
               this.field_72769_h,
               this.field_72777_q.field_71466_p,
               this.field_72777_q.func_175606_aa(),
               this.field_72777_q.field_147125_j,
               this.field_72777_q.field_71474_y,
               ☃
            );
         this.field_72748_H = 0;
         this.field_72749_I = 0;
         this.field_72750_J = 0;
         Entity ☃xxx = this.field_72777_q.func_175606_aa();
         double ☃xxxx = ☃xxx.field_70142_S + (☃xxx.field_70165_t - ☃xxx.field_70142_S) * (double)☃;
         double ☃xxxxx = ☃xxx.field_70137_T + (☃xxx.field_70163_u - ☃xxx.field_70137_T) * (double)☃;
         double ☃xxxxxx = ☃xxx.field_70136_U + (☃xxx.field_70161_v - ☃xxx.field_70136_U) * (double)☃;
         TileEntityRendererDispatcher.field_147554_b = ☃xxxx;
         TileEntityRendererDispatcher.field_147555_c = ☃xxxxx;
         TileEntityRendererDispatcher.field_147552_d = ☃xxxxxx;
         this.field_175010_j.func_178628_a(☃xxxx, ☃xxxxx, ☃xxxxxx);
         this.field_72777_q.field_71460_t.func_180436_i();
         this.field_72769_h.field_72984_F.func_76318_c("global");
         this.field_72748_H = this.field_72769_h.func_212419_R();

         for(int ☃xxxxxxx = 0; ☃xxxxxxx < this.field_72769_h.field_73007_j.size(); ++☃xxxxxxx) {
            Entity ☃xxxxxxxx = (Entity)this.field_72769_h.field_73007_j.get(☃xxxxxxx);
            ++this.field_72749_I;
            if (☃xxxxxxxx.func_145770_h(☃, ☃x, ☃xx)) {
               this.field_175010_j.func_188388_a(☃xxxxxxxx, ☃, false);
            }
         }

         this.field_72769_h.field_72984_F.func_76318_c("entities");
         List<Entity> ☃xxxxxxx = Lists.<Entity>newArrayList();
         List<Entity> ☃xxxxxxxx = Lists.<Entity>newArrayList();

         try (BlockPos.PooledMutableBlockPos ☃xxxxxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
            for(WorldRenderer.ContainerLocalRenderInformation ☃xxxxxxxxxx : this.field_72755_R) {
               Chunk ☃xxxxxxxxxxx = this.field_72769_h.func_175726_f(☃xxxxxxxxxx.field_178036_a.func_178568_j());
               ClassInheritanceMultiMap<Entity> ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx.func_177429_s()[☃xxxxxxxxxx.field_178036_a.func_178568_j().func_177956_o() / 16];
               if (!☃xxxxxxxxxxxx.isEmpty()) {
                  for(Entity ☃xxxxxxxxxxxxx : ☃xxxxxxxxxxxx) {
                     boolean ☃xxxxxxxxxxxxxx = this.field_175010_j.func_178635_a(☃xxxxxxxxxxxxx, ☃, ☃, ☃x, ☃xx)
                        || ☃xxxxxxxxxxxxx.func_184215_y(this.field_72777_q.field_71439_g);
                     if (☃xxxxxxxxxxxxxx) {
                        boolean ☃xxxxxxxxxxxxxxx = this.field_72777_q.func_175606_aa() instanceof EntityLivingBase
                           && ((EntityLivingBase)this.field_72777_q.func_175606_aa()).func_70608_bn();
                        if ((☃xxxxxxxxxxxxx != this.field_72777_q.func_175606_aa() || this.field_72777_q.field_71474_y.field_74320_O != 0 || ☃xxxxxxxxxxxxxxx)
                           && (
                              !(☃xxxxxxxxxxxxx.field_70163_u >= 0.0)
                                 || !(☃xxxxxxxxxxxxx.field_70163_u < 256.0)
                                 || this.field_72769_h.func_175667_e(☃xxxxxxxxx.func_189535_a(☃xxxxxxxxxxxxx))
                           )) {
                           ++this.field_72749_I;
                           this.field_175010_j.func_188388_a(☃xxxxxxxxxxxxx, ☃, false);
                           if (this.func_184383_a(☃xxxxxxxxxxxxx, ☃xxx, ☃)) {
                              ☃xxxxxxx.add(☃xxxxxxxxxxxxx);
                           }

                           if (this.field_175010_j.func_188390_b(☃xxxxxxxxxxxxx)) {
                              ☃xxxxxxxx.add(☃xxxxxxxxxxxxx);
                           }
                        }
                     }
                  }
               }
            }
         }

         if (!☃xxxxxxxx.isEmpty()) {
            for(Entity ☃xxxxxxxxx : ☃xxxxxxxx) {
               this.field_175010_j.func_188389_a(☃xxxxxxxxx, ☃);
            }
         }

         if (this.func_174985_d() && (!☃xxxxxxx.isEmpty() || this.field_184386_ad)) {
            this.field_72769_h.field_72984_F.func_76318_c("entityOutlines");
            this.field_175015_z.func_147614_f();
            this.field_184386_ad = !☃xxxxxxx.isEmpty();
            if (!☃xxxxxxx.isEmpty()) {
               GlStateManager.func_179143_c(519);
               GlStateManager.func_179106_n();
               this.field_175015_z.func_147610_a(false);
               RenderHelper.func_74518_a();
               this.field_175010_j.func_178632_c(true);

               for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃xxxxxxx.size(); ++☃xxxxxxxxx) {
                  this.field_175010_j.func_188388_a((Entity)☃xxxxxxx.get(☃xxxxxxxxx), ☃, false);
               }

               this.field_175010_j.func_178632_c(false);
               RenderHelper.func_74519_b();
               GlStateManager.func_179132_a(false);
               this.field_174991_A.func_148018_a(☃);
               GlStateManager.func_179145_e();
               GlStateManager.func_179132_a(true);
               GlStateManager.func_179127_m();
               GlStateManager.func_179147_l();
               GlStateManager.func_179142_g();
               GlStateManager.func_179143_c(515);
               GlStateManager.func_179126_j();
               GlStateManager.func_179141_d();
            }

            this.field_72777_q.func_147110_a().func_147610_a(false);
         }

         this.field_72769_h.field_72984_F.func_76318_c("blockentities");
         RenderHelper.func_74519_b();

         for(WorldRenderer.ContainerLocalRenderInformation ☃xxxxxxxxx : this.field_72755_R) {
            List<TileEntity> ☃xxxxxxxxxx = ☃xxxxxxxxx.field_178036_a.func_178571_g().func_178485_b();
            if (!☃xxxxxxxxxx.isEmpty()) {
               for(TileEntity ☃xxxxxxxxxxx : ☃xxxxxxxxxx) {
                  TileEntityRendererDispatcher.field_147556_a.func_180546_a(☃xxxxxxxxxxx, ☃, -1);
               }
            }
         }

         synchronized(this.field_181024_n) {
            for(TileEntity ☃xxxxxxxxx : this.field_181024_n) {
               TileEntityRendererDispatcher.field_147556_a.func_180546_a(☃xxxxxxxxx, ☃, -1);
            }
         }

         this.func_180443_s();

         for(DestroyBlockProgress ☃xxxxxxxxx : this.field_72738_E.values()) {
            BlockPos ☃xxxxxxxxxx = ☃xxxxxxxxx.func_180246_b();
            IBlockState ☃xxxxxxxxxxx = this.field_72769_h.func_180495_p(☃xxxxxxxxxx);
            if (☃xxxxxxxxxxx.func_177230_c().func_149716_u()) {
               TileEntity ☃xxxxxxxxxxxx = this.field_72769_h.func_175625_s(☃xxxxxxxxxx);
               if (☃xxxxxxxxxxxx instanceof TileEntityChest && ☃xxxxxxxxxxx.func_177229_b(BlockChest.field_196314_b) == ChestType.LEFT) {
                  ☃xxxxxxxxxx = ☃xxxxxxxxxx.func_177972_a(((EnumFacing)☃xxxxxxxxxxx.func_177229_b(BlockChest.field_176459_a)).func_176746_e());
                  ☃xxxxxxxxxxxx = this.field_72769_h.func_175625_s(☃xxxxxxxxxx);
               }

               if (☃xxxxxxxxxxxx != null && ☃xxxxxxxxxxx.func_191057_i()) {
                  TileEntityRendererDispatcher.field_147556_a.func_180546_a(☃xxxxxxxxxxxx, ☃, ☃xxxxxxxxx.func_73106_e());
               }
            }
         }

         this.func_174969_t();
         this.field_72777_q.field_71460_t.func_175072_h();
         this.field_72777_q.field_71424_I.func_76319_b();
      }
   }

   private boolean func_184383_a(Entity var1, Entity var2, ICamera var3) {
      boolean ☃ = ☃ instanceof EntityLivingBase && ((EntityLivingBase)☃).func_70608_bn();
      if (☃ == ☃ && this.field_72777_q.field_71474_y.field_74320_O == 0 && !☃) {
         return false;
      } else if (☃.func_184202_aL()) {
         return true;
      } else if (this.field_72777_q.field_71439_g.func_175149_v()
         && this.field_72777_q.field_71474_y.field_178883_an.func_151470_d()
         && ☃ instanceof EntityPlayer) {
         return ☃.field_70158_ak || ☃.func_78546_a(☃.func_174813_aQ()) || ☃.func_184215_y(this.field_72777_q.field_71439_g);
      } else {
         return false;
      }
   }

   public String func_72735_c() {
      int ☃ = this.field_175008_n.field_178164_f.length;
      int ☃x = this.func_184382_g();
      return String.format(
         "C: %d/%d %sD: %d, L: %d, %s",
         ☃x,
         ☃,
         this.field_72777_q.field_175612_E ? "(s) " : "",
         this.field_72739_F,
         this.field_184387_ae.size(),
         this.field_174995_M == null ? "null" : this.field_174995_M.func_178504_a()
      );
   }

   protected int func_184382_g() {
      int ☃ = 0;

      for(WorldRenderer.ContainerLocalRenderInformation ☃x : this.field_72755_R) {
         CompiledChunk ☃xx = ☃x.field_178036_a.field_178590_b;
         if (☃xx != CompiledChunk.field_178502_a && !☃xx.func_178489_a()) {
            ++☃;
         }
      }

      return ☃;
   }

   public String func_72723_d() {
      return "E: " + this.field_72749_I + "/" + this.field_72748_H + ", B: " + this.field_72750_J;
   }

   public void func_195473_a(Entity var1, float var2, ICamera var3, int var4, boolean var5) {
      if (this.field_72777_q.field_71474_y.field_151451_c != this.field_72739_F) {
         this.func_72712_a();
      }

      this.field_72769_h.field_72984_F.func_76320_a("camera");
      double ☃ = ☃.field_70165_t - this.field_174992_B;
      double ☃x = ☃.field_70163_u - this.field_174993_C;
      double ☃xx = ☃.field_70161_v - this.field_174987_D;
      if (this.field_174988_E != ☃.field_70176_ah
         || this.field_174989_F != ☃.field_70162_ai
         || this.field_174990_G != ☃.field_70164_aj
         || ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx > 16.0) {
         this.field_174992_B = ☃.field_70165_t;
         this.field_174993_C = ☃.field_70163_u;
         this.field_174987_D = ☃.field_70161_v;
         this.field_174988_E = ☃.field_70176_ah;
         this.field_174989_F = ☃.field_70162_ai;
         this.field_174990_G = ☃.field_70164_aj;
         this.field_175008_n.func_178163_a(☃.field_70165_t, ☃.field_70161_v);
      }

      this.field_72769_h.field_72984_F.func_76318_c("renderlistcamera");
      double ☃ = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃x = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      this.field_174996_N.func_178004_a(☃, ☃x, ☃xx);
      this.field_72769_h.field_72984_F.func_76318_c("cull");
      if (this.field_175001_U != null) {
         Frustum ☃xxx = new Frustum(this.field_175001_U);
         ☃xxx.func_78547_a(this.field_175003_W.field_181059_a, this.field_175003_W.field_181060_b, this.field_175003_W.field_181061_c);
         ☃ = ☃xxx;
      }

      this.field_72777_q.field_71424_I.func_76318_c("culling");
      BlockPos ☃ = new BlockPos(☃, ☃x + (double)☃.func_70047_e(), ☃xx);
      RenderChunk ☃x = this.field_175008_n.func_178161_a(☃);
      BlockPos ☃xx = new BlockPos(MathHelper.func_76128_c(☃ / 16.0) * 16, MathHelper.func_76128_c(☃x / 16.0) * 16, MathHelper.func_76128_c(☃xx / 16.0) * 16);
      float ☃xxx = ☃.func_195050_f(☃);
      float ☃xxxx = ☃.func_195046_g(☃);
      this.field_147595_R = this.field_147595_R
         || !this.field_175009_l.isEmpty()
         || ☃.field_70165_t != this.field_174997_H
         || ☃.field_70163_u != this.field_174998_I
         || ☃.field_70161_v != this.field_174999_J
         || (double)☃xxx != this.field_175000_K
         || (double)☃xxxx != this.field_174994_L;
      this.field_174997_H = ☃.field_70165_t;
      this.field_174998_I = ☃.field_70163_u;
      this.field_174999_J = ☃.field_70161_v;
      this.field_175000_K = (double)☃xxx;
      this.field_174994_L = (double)☃xxxx;
      boolean ☃xxxxx = this.field_175001_U != null;
      this.field_72777_q.field_71424_I.func_76318_c("update");
      if (!☃xxxxx && this.field_147595_R) {
         this.field_147595_R = false;
         this.field_72755_R = Lists.<WorldRenderer.ContainerLocalRenderInformation>newArrayList();
         Queue<WorldRenderer.ContainerLocalRenderInformation> ☃xxxxxx = Queues.<WorldRenderer.ContainerLocalRenderInformation>newArrayDeque();
         Entity.func_184227_b(MathHelper.func_151237_a((double)this.field_72777_q.field_71474_y.field_151451_c / 8.0, 1.0, 2.5));
         boolean ☃xxxxxxx = this.field_72777_q.field_175612_E;
         if (☃x != null) {
            boolean ☃xxxxxxxx = false;
            WorldRenderer.ContainerLocalRenderInformation ☃xxxxxxxxx = new WorldRenderer.ContainerLocalRenderInformation(☃x, null, 0);
            Set<EnumFacing> ☃xxxxxxxxxx = this.func_174978_c(☃);
            if (☃xxxxxxxxxx.size() == 1) {
               Vector3f ☃xxxxxxxxxxx = this.func_195474_a(☃, (double)☃);
               EnumFacing ☃xxxxxxxxxxxx = EnumFacing.func_176737_a(☃xxxxxxxxxxx.func_195899_a(), ☃xxxxxxxxxxx.func_195900_b(), ☃xxxxxxxxxxx.func_195902_c())
                  .func_176734_d();
               ☃xxxxxxxxxx.remove(☃xxxxxxxxxxxx);
            }

            if (☃xxxxxxxxxx.isEmpty()) {
               ☃xxxxxxxx = true;
            }

            if (☃xxxxxxxx && !☃) {
               this.field_72755_R.add(☃xxxxxxxxx);
            } else {
               if (☃ && this.field_72769_h.func_180495_p(☃).func_200015_d(this.field_72769_h, ☃)) {
                  ☃xxxxxxx = false;
               }

               ☃x.func_178577_a(☃);
               ☃xxxxxx.add(☃xxxxxxxxx);
            }
         } else {
            int ☃xxxxxx = ☃.func_177956_o() > 0 ? 248 : 8;

            for(int ☃xxxxxxx = -this.field_72739_F; ☃xxxxxxx <= this.field_72739_F; ++☃xxxxxxx) {
               for(int ☃xxxxxxxx = -this.field_72739_F; ☃xxxxxxxx <= this.field_72739_F; ++☃xxxxxxxx) {
                  RenderChunk ☃xxxxxxxxx = this.field_175008_n.func_178161_a(new BlockPos((☃xxxxxxx << 4) + 8, ☃xxxxxx, (☃xxxxxxxx << 4) + 8));
                  if (☃xxxxxxxxx != null && ☃.func_78546_a(☃xxxxxxxxx.field_178591_c)) {
                     ☃xxxxxxxxx.func_178577_a(☃);
                     ☃xxxxxx.add(new WorldRenderer.ContainerLocalRenderInformation(☃xxxxxxxxx, null, 0));
                  }
               }
            }
         }

         this.field_72777_q.field_71424_I.func_76320_a("iteration");

         while(!☃xxxxxx.isEmpty()) {
            WorldRenderer.ContainerLocalRenderInformation ☃xxxxxx = (WorldRenderer.ContainerLocalRenderInformation)☃xxxxxx.poll();
            RenderChunk ☃xxxxxxx = ☃xxxxxx.field_178036_a;
            EnumFacing ☃xxxxxxxx = ☃xxxxxx.field_178034_b;
            this.field_72755_R.add(☃xxxxxx);

            for(EnumFacing ☃xxxxxxxxx : field_200006_a) {
               RenderChunk ☃xxxxxxxxxx = this.func_181562_a(☃xx, ☃xxxxxxx, ☃xxxxxxxxx);
               if ((!☃xxxxxxx || !☃xxxxxx.func_189560_a(☃xxxxxxxxx.func_176734_d()))
                  && (!☃xxxxxxx || ☃xxxxxxxx == null || ☃xxxxxxx.func_178571_g().func_178495_a(☃xxxxxxxx.func_176734_d(), ☃xxxxxxxxx))
                  && ☃xxxxxxxxxx != null
                  && ☃xxxxxxxxxx.func_178577_a(☃)
                  && ☃.func_78546_a(☃xxxxxxxxxx.field_178591_c)) {
                  WorldRenderer.ContainerLocalRenderInformation ☃xxxxxxxxxxx = new WorldRenderer.ContainerLocalRenderInformation(
                     ☃xxxxxxxxxx, ☃xxxxxxxxx, ☃xxxxxx.field_178032_d + 1
                  );
                  ☃xxxxxxxxxxx.func_189561_a(☃xxxxxx.field_178035_c, ☃xxxxxxxxx);
                  ☃xxxxxx.add(☃xxxxxxxxxxx);
               }
            }
         }

         this.field_72777_q.field_71424_I.func_76319_b();
      }

      this.field_72777_q.field_71424_I.func_76318_c("captureFrustum");
      if (this.field_175002_T) {
         this.func_174984_a(☃, ☃x, ☃xx);
         this.field_175002_T = false;
      }

      this.field_72777_q.field_71424_I.func_76318_c("rebuildNear");
      Set<RenderChunk> ☃ = this.field_175009_l;
      this.field_175009_l = Sets.<RenderChunk>newLinkedHashSet();

      for(WorldRenderer.ContainerLocalRenderInformation ☃x : this.field_72755_R) {
         RenderChunk ☃xx = ☃x.field_178036_a;
         if (☃xx.func_178569_m() || ☃.contains(☃xx)) {
            this.field_147595_R = true;
            BlockPos ☃xxx = ☃xx.func_178568_j().func_177982_a(8, 8, 8);
            boolean ☃xxxx = ☃xxx.func_177951_i(☃) < 768.0;
            if (!☃xx.func_188281_o() && !☃xxxx) {
               this.field_175009_l.add(☃xx);
            } else {
               this.field_72777_q.field_71424_I.func_76320_a("build near");
               this.field_174995_M.func_178505_b(☃xx);
               ☃xx.func_188282_m();
               this.field_72777_q.field_71424_I.func_76319_b();
            }
         }
      }

      this.field_175009_l.addAll(☃);
      this.field_72777_q.field_71424_I.func_76319_b();
   }

   private Set<EnumFacing> func_174978_c(BlockPos var1) {
      VisGraph ☃ = new VisGraph();
      BlockPos ☃x = new BlockPos(☃.func_177958_n() >> 4 << 4, ☃.func_177956_o() >> 4 << 4, ☃.func_177952_p() >> 4 << 4);
      Chunk ☃xx = this.field_72769_h.func_175726_f(☃x);

      for(BlockPos.MutableBlockPos ☃xxx : BlockPos.func_177975_b(☃x, ☃x.func_177982_a(15, 15, 15))) {
         if (☃xx.func_180495_p(☃xxx).func_200015_d(this.field_72769_h, ☃xxx)) {
            ☃.func_178606_a(☃xxx);
         }
      }

      return ☃.func_178609_b(☃);
   }

   @Nullable
   private RenderChunk func_181562_a(BlockPos var1, RenderChunk var2, EnumFacing var3) {
      BlockPos ☃ = ☃.func_181701_a(☃);
      if (MathHelper.func_76130_a(☃.func_177958_n() - ☃.func_177958_n()) > this.field_72739_F * 16) {
         return null;
      } else if (☃.func_177956_o() < 0 || ☃.func_177956_o() >= 256) {
         return null;
      } else {
         return MathHelper.func_76130_a(☃.func_177952_p() - ☃.func_177952_p()) > this.field_72739_F * 16 ? null : this.field_175008_n.func_178161_a(☃);
      }
   }

   private void func_174984_a(double var1, double var3, double var5) {
   }

   protected Vector3f func_195474_a(Entity var1, double var2) {
      float ☃ = (float)((double)☃.field_70127_C + (double)(☃.field_70125_A - ☃.field_70127_C) * ☃);
      float ☃x = (float)((double)☃.field_70126_B + (double)(☃.field_70177_z - ☃.field_70126_B) * ☃);
      if (Minecraft.func_71410_x().field_71474_y.field_74320_O == 2) {
         ☃ += 180.0F;
      }

      float ☃ = MathHelper.func_76134_b(-☃x * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ☃x = MathHelper.func_76126_a(-☃x * (float) (Math.PI / 180.0) - (float) Math.PI);
      float ☃xx = -MathHelper.func_76134_b(-☃ * (float) (Math.PI / 180.0));
      float ☃xxx = MathHelper.func_76126_a(-☃ * (float) (Math.PI / 180.0));
      return new Vector3f(☃x * ☃xx, ☃xxx, ☃ * ☃xx);
   }

   public int func_195464_a(BlockRenderLayer var1, double var2, Entity var4) {
      RenderHelper.func_74518_a();
      if (☃ == BlockRenderLayer.TRANSLUCENT) {
         this.field_72777_q.field_71424_I.func_76320_a("translucent_sort");
         double ☃ = ☃.field_70165_t - this.field_147596_f;
         double ☃x = ☃.field_70163_u - this.field_147597_g;
         double ☃xx = ☃.field_70161_v - this.field_147602_h;
         if (☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx > 1.0) {
            this.field_147596_f = ☃.field_70165_t;
            this.field_147597_g = ☃.field_70163_u;
            this.field_147602_h = ☃.field_70161_v;
            int ☃xxx = 0;

            for(WorldRenderer.ContainerLocalRenderInformation ☃xxxx : this.field_72755_R) {
               if (☃xxxx.field_178036_a.field_178590_b.func_178492_d(☃) && ☃xxx++ < 15) {
                  this.field_174995_M.func_178509_c(☃xxxx.field_178036_a);
               }
            }
         }

         this.field_72777_q.field_71424_I.func_76319_b();
      }

      this.field_72777_q.field_71424_I.func_76320_a("filterempty");
      int ☃ = 0;
      boolean ☃x = ☃ == BlockRenderLayer.TRANSLUCENT;
      int ☃xx = ☃x ? this.field_72755_R.size() - 1 : 0;
      int ☃xxx = ☃x ? -1 : this.field_72755_R.size();
      int ☃xxxx = ☃x ? -1 : 1;

      for(int ☃xxxxx = ☃xx; ☃xxxxx != ☃xxx; ☃xxxxx += ☃xxxx) {
         RenderChunk ☃xxxxxx = ((WorldRenderer.ContainerLocalRenderInformation)this.field_72755_R.get(☃xxxxx)).field_178036_a;
         if (!☃xxxxxx.func_178571_g().func_178491_b(☃)) {
            ++☃;
            this.field_174996_N.func_178002_a(☃xxxxxx, ☃);
         }
      }

      this.field_72777_q.field_71424_I.func_194339_b(() -> "render_" + ☃);
      this.func_174982_a(☃);
      this.field_72777_q.field_71424_I.func_76319_b();
      return ☃;
   }

   private void func_174982_a(BlockRenderLayer var1) {
      this.field_72777_q.field_71460_t.func_180436_i();
      if (OpenGlHelper.func_176075_f()) {
         GlStateManager.func_187410_q(32884);
         OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
         GlStateManager.func_187410_q(32888);
         OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
         GlStateManager.func_187410_q(32888);
         OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
         GlStateManager.func_187410_q(32886);
      }

      this.field_174996_N.func_178001_a(☃);
      if (OpenGlHelper.func_176075_f()) {
         for(VertexFormatElement ☃ : DefaultVertexFormats.field_176600_a.func_177343_g()) {
            VertexFormatElement.EnumUsage ☃x = ☃.func_177375_c();
            int ☃xx = ☃.func_177369_e();
            switch(☃x) {
               case POSITION:
                  GlStateManager.func_187429_p(32884);
                  break;
               case UV:
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a + ☃xx);
                  GlStateManager.func_187429_p(32888);
                  OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
                  break;
               case COLOR:
                  GlStateManager.func_187429_p(32886);
                  GlStateManager.func_179117_G();
            }
         }
      }

      this.field_72777_q.field_71460_t.func_175072_h();
   }

   private void func_174965_a(Iterator<DestroyBlockProgress> var1) {
      while(☃.hasNext()) {
         DestroyBlockProgress ☃ = (DestroyBlockProgress)☃.next();
         int ☃x = ☃.func_82743_f();
         if (this.field_72773_u - ☃x > 400) {
            ☃.remove();
         }
      }
   }

   public void func_72734_e() {
      ++this.field_72773_u;
      if (this.field_72773_u % 20 == 0) {
         this.func_174965_a(this.field_72738_E.values().iterator());
      }

      if (!this.field_184387_ae.isEmpty() && !this.field_174995_M.func_188248_h() && this.field_175009_l.isEmpty()) {
         Iterator<BlockPos> ☃ = this.field_184387_ae.iterator();

         while(☃.hasNext()) {
            BlockPos ☃x = (BlockPos)☃.next();
            ☃.remove();
            int ☃xx = ☃x.func_177958_n();
            int ☃xxx = ☃x.func_177956_o();
            int ☃xxxx = ☃x.func_177952_p();
            this.func_184385_a(☃xx - 1, ☃xxx - 1, ☃xxxx - 1, ☃xx + 1, ☃xxx + 1, ☃xxxx + 1, false);
         }
      }
   }

   private void func_180448_r() {
      GlStateManager.func_179106_n();
      GlStateManager.func_179118_c();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      RenderHelper.func_74518_a();
      GlStateManager.func_179132_a(false);
      this.field_72770_i.func_110577_a(field_110926_k);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();

      for(int ☃xx = 0; ☃xx < 6; ++☃xx) {
         GlStateManager.func_179094_E();
         if (☃xx == 1) {
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
         }

         if (☃xx == 2) {
            GlStateManager.func_179114_b(-90.0F, 1.0F, 0.0F, 0.0F);
         }

         if (☃xx == 3) {
            GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
         }

         if (☃xx == 4) {
            GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
         }

         if (☃xx == 5) {
            GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
         }

         ☃x.func_181668_a(7, DefaultVertexFormats.field_181709_i);
         ☃x.func_181662_b(-100.0, -100.0, -100.0).func_187315_a(0.0, 0.0).func_181669_b(40, 40, 40, 255).func_181675_d();
         ☃x.func_181662_b(-100.0, -100.0, 100.0).func_187315_a(0.0, 16.0).func_181669_b(40, 40, 40, 255).func_181675_d();
         ☃x.func_181662_b(100.0, -100.0, 100.0).func_187315_a(16.0, 16.0).func_181669_b(40, 40, 40, 255).func_181675_d();
         ☃x.func_181662_b(100.0, -100.0, -100.0).func_187315_a(16.0, 0.0).func_181669_b(40, 40, 40, 255).func_181675_d();
         ☃.func_78381_a();
         GlStateManager.func_179121_F();
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179141_d();
   }

   public void func_195465_a(float var1) {
      if (this.field_72777_q.field_71441_e.field_73011_w.func_186058_p() == DimensionType.THE_END) {
         this.func_180448_r();
      } else if (this.field_72777_q.field_71441_e.field_73011_w.func_76569_d()) {
         GlStateManager.func_179090_x();
         Vec3d ☃ = this.field_72769_h.func_72833_a(this.field_72777_q.func_175606_aa(), ☃);
         float ☃x = (float)☃.field_72450_a;
         float ☃xx = (float)☃.field_72448_b;
         float ☃xxx = (float)☃.field_72449_c;
         GlStateManager.func_179124_c(☃x, ☃xx, ☃xxx);
         Tessellator ☃xxxx = Tessellator.func_178181_a();
         BufferBuilder ☃xxxxx = ☃xxxx.func_178180_c();
         GlStateManager.func_179132_a(false);
         GlStateManager.func_179127_m();
         GlStateManager.func_179124_c(☃x, ☃xx, ☃xxx);
         if (this.field_175005_X) {
            this.field_175012_t.func_177359_a();
            GlStateManager.func_187410_q(32884);
            GlStateManager.func_187420_d(3, 5126, 12, 0);
            this.field_175012_t.func_177358_a(7);
            this.field_175012_t.func_177361_b();
            GlStateManager.func_187429_p(32884);
         } else {
            GlStateManager.func_179148_o(this.field_72771_w);
         }

         GlStateManager.func_179106_n();
         GlStateManager.func_179118_c();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         RenderHelper.func_74518_a();
         float[] ☃ = this.field_72769_h.field_73011_w.func_76560_a(this.field_72769_h.func_72826_c(☃), ☃);
         if (☃ != null) {
            GlStateManager.func_179090_x();
            GlStateManager.func_179103_j(7425);
            GlStateManager.func_179094_E();
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(MathHelper.func_76126_a(this.field_72769_h.func_72929_e(☃)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
            float ☃x = ☃[0];
            float ☃xx = ☃[1];
            float ☃xxx = ☃[2];
            ☃xxxxx.func_181668_a(6, DefaultVertexFormats.field_181706_f);
            ☃xxxxx.func_181662_b(0.0, 100.0, 0.0).func_181666_a(☃x, ☃xx, ☃xxx, ☃[3]).func_181675_d();
            int ☃xxxx = 16;

            for(int ☃xxxxx = 0; ☃xxxxx <= 16; ++☃xxxxx) {
               float ☃xxxxxx = (float)☃xxxxx * (float) (Math.PI * 2) / 16.0F;
               float ☃xxxxxxx = MathHelper.func_76126_a(☃xxxxxx);
               float ☃xxxxxxxx = MathHelper.func_76134_b(☃xxxxxx);
               ☃xxxxx.func_181662_b((double)(☃xxxxxxx * 120.0F), (double)(☃xxxxxxxx * 120.0F), (double)(-☃xxxxxxxx * 40.0F * ☃[3]))
                  .func_181666_a(☃[0], ☃[1], ☃[2], 0.0F)
                  .func_181675_d();
            }

            ☃xxxx.func_78381_a();
            GlStateManager.func_179121_F();
            GlStateManager.func_179103_j(7424);
         }

         GlStateManager.func_179098_w();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
         );
         GlStateManager.func_179094_E();
         float ☃ = 1.0F - this.field_72769_h.func_72867_j(☃);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, ☃);
         GlStateManager.func_179114_b(-90.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(this.field_72769_h.func_72826_c(☃) * 360.0F, 1.0F, 0.0F, 0.0F);
         float ☃x = 30.0F;
         this.field_72770_i.func_110577_a(field_110928_i);
         ☃xxxxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         ☃xxxxx.func_181662_b((double)(-☃x), 100.0, (double)(-☃x)).func_187315_a(0.0, 0.0).func_181675_d();
         ☃xxxxx.func_181662_b((double)☃x, 100.0, (double)(-☃x)).func_187315_a(1.0, 0.0).func_181675_d();
         ☃xxxxx.func_181662_b((double)☃x, 100.0, (double)☃x).func_187315_a(1.0, 1.0).func_181675_d();
         ☃xxxxx.func_181662_b((double)(-☃x), 100.0, (double)☃x).func_187315_a(0.0, 1.0).func_181675_d();
         ☃xxxx.func_78381_a();
         ☃x = 20.0F;
         this.field_72770_i.func_110577_a(field_110927_h);
         int ☃xx = this.field_72769_h.func_72853_d();
         int ☃xxx = ☃xx % 4;
         int ☃xxxx = ☃xx / 4 % 2;
         float ☃xxxxx = (float)(☃xxx + 0) / 4.0F;
         float ☃xxxxxx = (float)(☃xxxx + 0) / 2.0F;
         float ☃xxxxxxx = (float)(☃xxx + 1) / 4.0F;
         float ☃xxxxxxxx = (float)(☃xxxx + 1) / 2.0F;
         ☃xxxxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         ☃xxxxx.func_181662_b((double)(-☃x), -100.0, (double)☃x).func_187315_a((double)☃xxxxxxx, (double)☃xxxxxxxx).func_181675_d();
         ☃xxxxx.func_181662_b((double)☃x, -100.0, (double)☃x).func_187315_a((double)☃xxxxx, (double)☃xxxxxxxx).func_181675_d();
         ☃xxxxx.func_181662_b((double)☃x, -100.0, (double)(-☃x)).func_187315_a((double)☃xxxxx, (double)☃xxxxxx).func_181675_d();
         ☃xxxxx.func_181662_b((double)(-☃x), -100.0, (double)(-☃x)).func_187315_a((double)☃xxxxxxx, (double)☃xxxxxx).func_181675_d();
         ☃xxxx.func_78381_a();
         GlStateManager.func_179090_x();
         float ☃xxxxxxxxx = this.field_72769_h.func_72880_h(☃) * ☃;
         if (☃xxxxxxxxx > 0.0F) {
            GlStateManager.func_179131_c(☃xxxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxx);
            if (this.field_175005_X) {
               this.field_175013_s.func_177359_a();
               GlStateManager.func_187410_q(32884);
               GlStateManager.func_187420_d(3, 5126, 12, 0);
               this.field_175013_s.func_177358_a(7);
               this.field_175013_s.func_177361_b();
               GlStateManager.func_187429_p(32884);
            } else {
               GlStateManager.func_179148_o(this.field_72772_v);
            }
         }

         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179084_k();
         GlStateManager.func_179141_d();
         GlStateManager.func_179127_m();
         GlStateManager.func_179121_F();
         GlStateManager.func_179090_x();
         GlStateManager.func_179124_c(0.0F, 0.0F, 0.0F);
         double ☃ = this.field_72777_q.field_71439_g.func_174824_e(☃).field_72448_b - this.field_72769_h.func_72919_O();
         if (☃ < 0.0) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(0.0F, 12.0F, 0.0F);
            if (this.field_175005_X) {
               this.field_175011_u.func_177359_a();
               GlStateManager.func_187410_q(32884);
               GlStateManager.func_187420_d(3, 5126, 12, 0);
               this.field_175011_u.func_177358_a(7);
               this.field_175011_u.func_177361_b();
               GlStateManager.func_187429_p(32884);
            } else {
               GlStateManager.func_179148_o(this.field_72781_x);
            }

            GlStateManager.func_179121_F();
         }

         if (this.field_72769_h.field_73011_w.func_76561_g()) {
            GlStateManager.func_179124_c(☃x * 0.2F + 0.04F, ☃xx * 0.2F + 0.04F, ☃xxx * 0.6F + 0.1F);
         } else {
            GlStateManager.func_179124_c(☃x, ☃xx, ☃xxx);
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, -((float)(☃ - 16.0)), 0.0F);
         GlStateManager.func_179148_o(this.field_72781_x);
         GlStateManager.func_179121_F();
         GlStateManager.func_179098_w();
         GlStateManager.func_179132_a(true);
      }
   }

   public void func_195466_a(float var1, double var2, double var4, double var6) {
      if (this.field_72777_q.field_71441_e.field_73011_w.func_76569_d()) {
         float ☃ = 12.0F;
         float ☃x = 4.0F;
         double ☃xx = 2.0E-4;
         double ☃xxx = (double)(((float)this.field_72773_u + ☃) * 0.03F);
         double ☃xxxx = (☃ + ☃xxx) / 12.0;
         double ☃xxxxx = (double)(this.field_72769_h.field_73011_w.func_76571_f() - (float)☃ + 0.33F);
         double ☃xxxxxx = ☃ / 12.0 + 0.33F;
         ☃xxxx -= (double)(MathHelper.func_76128_c(☃xxxx / 2048.0) * 2048);
         ☃xxxxxx -= (double)(MathHelper.func_76128_c(☃xxxxxx / 2048.0) * 2048);
         float ☃xxxxxxx = (float)(☃xxxx - (double)MathHelper.func_76128_c(☃xxxx));
         float ☃xxxxxxxx = (float)(☃xxxxx / 4.0 - (double)MathHelper.func_76128_c(☃xxxxx / 4.0)) * 4.0F;
         float ☃xxxxxxxxx = (float)(☃xxxxxx - (double)MathHelper.func_76128_c(☃xxxxxx));
         Vec3d ☃xxxxxxxxxx = this.field_72769_h.func_72824_f(☃);
         int ☃xxxxxxxxxxx = (int)Math.floor(☃xxxx);
         int ☃xxxxxxxxxxxx = (int)Math.floor(☃xxxxx / 4.0);
         int ☃xxxxxxxxxxxxx = (int)Math.floor(☃xxxxxx);
         if (☃xxxxxxxxxxx != this.field_204602_S
            || ☃xxxxxxxxxxxx != this.field_204603_T
            || ☃xxxxxxxxxxxxx != this.field_204604_U
            || this.field_72777_q.field_71474_y.func_181147_e() != this.field_204800_W
            || this.field_204605_V.func_72436_e(☃xxxxxxxxxx) > 2.0E-4) {
            this.field_204602_S = ☃xxxxxxxxxxx;
            this.field_204603_T = ☃xxxxxxxxxxxx;
            this.field_204604_U = ☃xxxxxxxxxxxxx;
            this.field_204605_V = ☃xxxxxxxxxx;
            this.field_204800_W = this.field_72777_q.field_71474_y.func_181147_e();
            this.field_204607_y = true;
         }

         if (this.field_204607_y) {
            this.field_204607_y = false;
            Tessellator ☃ = Tessellator.func_178181_a();
            BufferBuilder ☃x = ☃.func_178180_c();
            if (this.field_204601_A != null) {
               this.field_204601_A.func_177362_c();
            }

            if (this.field_204608_z >= 0) {
               GLAllocation.func_74523_b(this.field_204608_z);
               this.field_204608_z = -1;
            }

            if (this.field_175005_X) {
               this.field_204601_A = new VertexBuffer(DefaultVertexFormats.field_181712_l);
               this.func_204600_a(☃x, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxxxxx);
               ☃x.func_178977_d();
               ☃x.func_178965_a();
               this.field_204601_A.func_181722_a(☃x.func_178966_f());
            } else {
               this.field_204608_z = GLAllocation.func_74526_a(1);
               GlStateManager.func_187423_f(this.field_204608_z, 4864);
               this.func_204600_a(☃x, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxxxxx);
               ☃.func_78381_a();
               GlStateManager.func_187415_K();
            }
         }

         GlStateManager.func_179129_p();
         this.field_72770_i.func_110577_a(field_110925_j);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(12.0F, 1.0F, 12.0F);
         GlStateManager.func_179109_b(-☃xxxxxxx, ☃xxxxxxxx, -☃xxxxxxxxx);
         if (this.field_175005_X && this.field_204601_A != null) {
            this.field_204601_A.func_177359_a();
            GlStateManager.func_187410_q(32884);
            GlStateManager.func_187410_q(32888);
            OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
            GlStateManager.func_187410_q(32886);
            GlStateManager.func_187410_q(32885);
            GlStateManager.func_187420_d(3, 5126, 28, 0);
            GlStateManager.func_187405_c(2, 5126, 28, 12);
            GlStateManager.func_187406_e(4, 5121, 28, 20);
            GlStateManager.func_204611_f(5120, 28, 24);
            int ☃ = this.field_204800_W == 2 ? 0 : 1;

            for(int ☃x = ☃; ☃x < 2; ++☃x) {
               if (☃x == 0) {
                  GlStateManager.func_179135_a(false, false, false, false);
               } else {
                  GlStateManager.func_179135_a(true, true, true, true);
               }

               this.field_204601_A.func_177358_a(7);
            }

            this.field_204601_A.func_177361_b();
            GlStateManager.func_187429_p(32884);
            GlStateManager.func_187429_p(32888);
            GlStateManager.func_187429_p(32886);
            GlStateManager.func_187429_p(32885);
            OpenGlHelper.func_176072_g(OpenGlHelper.field_176089_P, 0);
         } else if (this.field_204608_z >= 0) {
            int ☃ = this.field_204800_W == 2 ? 0 : 1;

            for(int ☃x = ☃; ☃x < 2; ++☃x) {
               if (☃x == 0) {
                  GlStateManager.func_179135_a(false, false, false, false);
               } else {
                  GlStateManager.func_179135_a(true, true, true, true);
               }

               GlStateManager.func_179148_o(this.field_204608_z);
            }
         }

         GlStateManager.func_179121_F();
         GlStateManager.func_179117_G();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179084_k();
         GlStateManager.func_179089_o();
      }
   }

   private void func_204600_a(BufferBuilder var1, double var2, double var4, double var6, Vec3d var8) {
      float ☃ = 4.0F;
      float ☃x = 0.00390625F;
      int ☃xx = 8;
      int ☃xxx = 4;
      float ☃xxxx = 9.765625E-4F;
      float ☃xxxxx = (float)MathHelper.func_76128_c(☃) * 0.00390625F;
      float ☃xxxxxx = (float)MathHelper.func_76128_c(☃) * 0.00390625F;
      float ☃xxxxxxx = (float)☃.field_72450_a;
      float ☃xxxxxxxx = (float)☃.field_72448_b;
      float ☃xxxxxxxxx = (float)☃.field_72449_c;
      float ☃xxxxxxxxxx = ☃xxxxxxx * 0.9F;
      float ☃xxxxxxxxxxx = ☃xxxxxxxx * 0.9F;
      float ☃xxxxxxxxxxxx = ☃xxxxxxxxx * 0.9F;
      float ☃xxxxxxxxxxxxx = ☃xxxxxxx * 0.7F;
      float ☃xxxxxxxxxxxxxx = ☃xxxxxxxx * 0.7F;
      float ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxx * 0.7F;
      float ☃xxxxxxxxxxxxxxxx = ☃xxxxxxx * 0.8F;
      float ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxx * 0.8F;
      float ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxx * 0.8F;
      ☃.func_181668_a(7, DefaultVertexFormats.field_181712_l);
      float ☃xxxxxxxxxxxxxxxxxxx = (float)Math.floor(☃ / 4.0) * 4.0F;
      if (this.field_204800_W == 2) {
         for(int ☃xxxxxxxxxxxxxxxxxxxx = -3; ☃xxxxxxxxxxxxxxxxxxxx <= 4; ++☃xxxxxxxxxxxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxxxxxxxxxxx = -3; ☃xxxxxxxxxxxxxxxxxxxxx <= 4; ++☃xxxxxxxxxxxxxxxxxxxxx) {
               float ☃xxxxxxxxxxxxxxxxxxxxxx = (float)(☃xxxxxxxxxxxxxxxxxxxx * 8);
               float ☃xxxxxxxxxxxxxxxxxxxxxxx = (float)(☃xxxxxxxxxxxxxxxxxxxxx * 8);
               if (☃xxxxxxxxxxxxxxxxxxx > -5.0F) {
                  ☃.func_181662_b((double)(☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F), (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .func_187315_a(
                        (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxx), (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxxx)
                     )
                     .func_181666_a(☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, 0.8F)
                     .func_181663_c(0.0F, -1.0F, 0.0F)
                     .func_181675_d();
                  ☃.func_181662_b((double)(☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F), (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .func_187315_a(
                        (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxx), (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxxx)
                     )
                     .func_181666_a(☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, 0.8F)
                     .func_181663_c(0.0F, -1.0F, 0.0F)
                     .func_181675_d();
                  ☃.func_181662_b((double)(☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F), (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .func_187315_a(
                        (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxx), (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxxx)
                     )
                     .func_181666_a(☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, 0.8F)
                     .func_181663_c(0.0F, -1.0F, 0.0F)
                     .func_181675_d();
                  ☃.func_181662_b((double)(☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F), (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .func_187315_a(
                        (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxx), (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxxx)
                     )
                     .func_181666_a(☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, 0.8F)
                     .func_181663_c(0.0F, -1.0F, 0.0F)
                     .func_181675_d();
               }

               if (☃xxxxxxxxxxxxxxxxxxx <= 5.0F) {
                  ☃.func_181662_b(
                        (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                        (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                     )
                     .func_187315_a(
                        (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxx), (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxxx)
                     )
                     .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.8F)
                     .func_181663_c(0.0F, 1.0F, 0.0F)
                     .func_181675_d();
                  ☃.func_181662_b(
                        (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                        (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                     )
                     .func_187315_a(
                        (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxx), (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxxx)
                     )
                     .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.8F)
                     .func_181663_c(0.0F, 1.0F, 0.0F)
                     .func_181675_d();
                  ☃.func_181662_b(
                        (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                        (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                     )
                     .func_187315_a(
                        (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxx), (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxxx)
                     )
                     .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.8F)
                     .func_181663_c(0.0F, 1.0F, 0.0F)
                     .func_181675_d();
                  ☃.func_181662_b(
                        (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                        (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                     )
                     .func_187315_a(
                        (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxx), (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxxx)
                     )
                     .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.8F)
                     .func_181663_c(0.0F, 1.0F, 0.0F)
                     .func_181675_d();
               }

               if (☃xxxxxxxxxxxxxxxxxxxx > -1) {
                  for(int ☃xxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxx < 8; ++☃xxxxxxxxxxxxxxxxxxxxxx) {
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 0.8F)
                        .func_181663_c(-1.0F, 0.0F, 0.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 0.8F)
                        .func_181663_c(-1.0F, 0.0F, 0.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 0.8F)
                        .func_181663_c(-1.0F, 0.0F, 0.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 0.8F)
                        .func_181663_c(-1.0F, 0.0F, 0.0F)
                        .func_181675_d();
                  }
               }

               if (☃xxxxxxxxxxxxxxxxxxxx <= 1) {
                  for(int ☃xxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxx < 8; ++☃xxxxxxxxxxxxxxxxxxxxxx) {
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 0.8F)
                        .func_181663_c(1.0F, 0.0F, 0.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 0.8F)
                        .func_181663_c(1.0F, 0.0F, 0.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 0.8F)
                        .func_181663_c(1.0F, 0.0F, 0.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 0.8F)
                        .func_181663_c(1.0F, 0.0F, 0.0F)
                        .func_181675_d();
                  }
               }

               if (☃xxxxxxxxxxxxxxxxxxxxx > -1) {
                  for(int ☃xxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxx < 8; ++☃xxxxxxxxxxxxxxxxxxxxxx) {
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 0.8F)
                        .func_181663_c(0.0F, 0.0F, -1.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 0.8F)
                        .func_181663_c(0.0F, 0.0F, -1.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 0.8F)
                        .func_181663_c(0.0F, 0.0F, -1.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 0.8F)
                        .func_181663_c(0.0F, 0.0F, -1.0F)
                        .func_181675_d();
                  }
               }

               if (☃xxxxxxxxxxxxxxxxxxxxx <= 1) {
                  for(int ☃xxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxx < 8; ++☃xxxxxxxxxxxxxxxxxxxxxx) {
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 0.8F)
                        .func_181663_c(0.0F, 0.0F, 1.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 0.8F)
                        .func_181663_c(0.0F, 0.0F, 1.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 0.8F)
                        .func_181663_c(0.0F, 0.0F, 1.0F)
                        .func_181675_d();
                     ☃.func_181662_b(
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .func_187315_a(
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + ☃xxxxx),
                           (double)((☃xxxxxxxxxxxxxxxxxxxxxxx + (float)☃xxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + ☃xxxxxx)
                        )
                        .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 0.8F)
                        .func_181663_c(0.0F, 0.0F, 1.0F)
                        .func_181675_d();
                  }
               }
            }
         }
      } else {
         int ☃ = 1;
         int ☃x = 32;

         for(int ☃xx = -32; ☃xx < 32; ☃xx += 32) {
            for(int ☃xxx = -32; ☃xxx < 32; ☃xxx += 32) {
               ☃.func_181662_b((double)(☃xx + 0), (double)☃xxxxxxxxxxxxxxxxxxx, (double)(☃xxx + 32))
                  .func_187315_a((double)((float)(☃xx + 0) * 0.00390625F + ☃xxxxx), (double)((float)(☃xxx + 32) * 0.00390625F + ☃xxxxxx))
                  .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.8F)
                  .func_181663_c(0.0F, -1.0F, 0.0F)
                  .func_181675_d();
               ☃.func_181662_b((double)(☃xx + 32), (double)☃xxxxxxxxxxxxxxxxxxx, (double)(☃xxx + 32))
                  .func_187315_a((double)((float)(☃xx + 32) * 0.00390625F + ☃xxxxx), (double)((float)(☃xxx + 32) * 0.00390625F + ☃xxxxxx))
                  .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.8F)
                  .func_181663_c(0.0F, -1.0F, 0.0F)
                  .func_181675_d();
               ☃.func_181662_b((double)(☃xx + 32), (double)☃xxxxxxxxxxxxxxxxxxx, (double)(☃xxx + 0))
                  .func_187315_a((double)((float)(☃xx + 32) * 0.00390625F + ☃xxxxx), (double)((float)(☃xxx + 0) * 0.00390625F + ☃xxxxxx))
                  .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.8F)
                  .func_181663_c(0.0F, -1.0F, 0.0F)
                  .func_181675_d();
               ☃.func_181662_b((double)(☃xx + 0), (double)☃xxxxxxxxxxxxxxxxxxx, (double)(☃xxx + 0))
                  .func_187315_a((double)((float)(☃xx + 0) * 0.00390625F + ☃xxxxx), (double)((float)(☃xxx + 0) * 0.00390625F + ☃xxxxxx))
                  .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 0.8F)
                  .func_181663_c(0.0F, -1.0F, 0.0F)
                  .func_181675_d();
            }
         }
      }
   }

   public void func_174967_a(long var1) {
      this.field_147595_R |= this.field_174995_M.func_178516_a(☃);
      if (!this.field_175009_l.isEmpty()) {
         Iterator<RenderChunk> ☃ = this.field_175009_l.iterator();

         while(☃.hasNext()) {
            RenderChunk ☃xx = (RenderChunk)☃.next();
            boolean ☃x;
            if (☃xx.func_188281_o()) {
               ☃x = this.field_174995_M.func_178505_b(☃xx);
            } else {
               ☃x = this.field_174995_M.func_178507_a(☃xx);
            }

            if (!☃x) {
               break;
            }

            ☃xx.func_188282_m();
            ☃.remove();
            long ☃x = ☃ - Util.func_211178_c();
            if (☃x < 0L) {
               break;
            }
         }
      }
   }

   public void func_180449_a(Entity var1, float var2) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      WorldBorder ☃xx = this.field_72769_h.func_175723_af();
      double ☃xxx = (double)(this.field_72777_q.field_71474_y.field_151451_c * 16);
      if (!(☃.field_70165_t < ☃xx.func_177728_d() - ☃xxx)
         || !(☃.field_70165_t > ☃xx.func_177726_b() + ☃xxx)
         || !(☃.field_70161_v < ☃xx.func_177733_e() - ☃xxx)
         || !(☃.field_70161_v > ☃xx.func_177736_c() + ☃xxx)) {
         double ☃xxxx = 1.0 - ☃xx.func_177745_a(☃) / ☃xxx;
         ☃xxxx = Math.pow(☃xxxx, 4.0);
         double ☃xxxxx = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
         double ☃xxxxxx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
         double ☃xxxxxxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
         );
         this.field_72770_i.func_110577_a(field_175006_g);
         GlStateManager.func_179132_a(false);
         GlStateManager.func_179094_E();
         int ☃xxxxxxxx = ☃xx.func_177734_a().func_177766_a();
         float ☃xxxxxxxxx = (float)(☃xxxxxxxx >> 16 & 0xFF) / 255.0F;
         float ☃xxxxxxxxxx = (float)(☃xxxxxxxx >> 8 & 0xFF) / 255.0F;
         float ☃xxxxxxxxxxx = (float)(☃xxxxxxxx & 0xFF) / 255.0F;
         GlStateManager.func_179131_c(☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, (float)☃xxxx);
         GlStateManager.func_179136_a(-3.0F, -3.0F);
         GlStateManager.func_179088_q();
         GlStateManager.func_179092_a(516, 0.1F);
         GlStateManager.func_179141_d();
         GlStateManager.func_179129_p();
         float ☃xxxxxxxxxxxx = (float)(Util.func_211177_b() % 3000L) / 3000.0F;
         float ☃xxxxxxxxxxxxx = 0.0F;
         float ☃xxxxxxxxxxxxxx = 0.0F;
         float ☃xxxxxxxxxxxxxxx = 128.0F;
         ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         ☃x.func_178969_c(-☃xxxxx, -☃xxxxxx, -☃xxxxxxx);
         double ☃xxxxxxxxxxxxxxxx = Math.max((double)MathHelper.func_76128_c(☃xxxxxxx - ☃xxx), ☃xx.func_177736_c());
         double ☃xxxxxxxxxxxxxxxxx = Math.min((double)MathHelper.func_76143_f(☃xxxxxxx + ☃xxx), ☃xx.func_177733_e());
         if (☃xxxxx > ☃xx.func_177728_d() - ☃xxx) {
            float ☃xxxxxxxxxxxxxxxxxx = 0.0F;

            for(double ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx; ☃xxxxxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxx; ☃xxxxxxxxxxxxxxxxxx += 0.5F) {
               double ☃xxxxxxxxxxxxxxxxxxxx = Math.min(1.0, ☃xxxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxxxx);
               float ☃xxxxxxxxxxxxxxxxxxxxx = (float)☃xxxxxxxxxxxxxxxxxxxx * 0.5F;
               ☃x.func_181662_b(☃xx.func_177728_d(), 256.0, ☃xxxxxxxxxxxxxxxxxxx)
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxx), (double)(☃xxxxxxxxxxxx + 0.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xx.func_177728_d(), 256.0, ☃xxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxx)
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxx), (double)(☃xxxxxxxxxxxx + 0.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xx.func_177728_d(), 0.0, ☃xxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxx)
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxx), (double)(☃xxxxxxxxxxxx + 128.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xx.func_177728_d(), 0.0, ☃xxxxxxxxxxxxxxxxxxx)
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxx), (double)(☃xxxxxxxxxxxx + 128.0F))
                  .func_181675_d();
               ++☃xxxxxxxxxxxxxxxxxxx;
            }
         }

         if (☃xxxxx < ☃xx.func_177726_b() + ☃xxx) {
            float ☃xxxx = 0.0F;

            for(double ☃xxxxx = ☃xxxxxxxxxxxxxxxx; ☃xxxxx < ☃xxxxxxxxxxxxxxxxx; ☃xxxx += 0.5F) {
               double ☃xxxxxx = Math.min(1.0, ☃xxxxxxxxxxxxxxxxx - ☃xxxxx);
               float ☃xxxxxxx = (float)☃xxxxxx * 0.5F;
               ☃x.func_181662_b(☃xx.func_177726_b(), 256.0, ☃xxxxx)
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 0.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xx.func_177726_b(), 256.0, ☃xxxxx + ☃xxxxxx)
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 0.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xx.func_177726_b(), 0.0, ☃xxxxx + ☃xxxxxx)
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 128.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xx.func_177726_b(), 0.0, ☃xxxxx)
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 128.0F))
                  .func_181675_d();
               ++☃xxxxx;
            }
         }

         ☃xxxxxxxxxxxxxxxx = Math.max((double)MathHelper.func_76128_c(☃xxxxx - ☃xxx), ☃xx.func_177726_b());
         ☃xxxxxxxxxxxxxxxxx = Math.min((double)MathHelper.func_76143_f(☃xxxxx + ☃xxx), ☃xx.func_177728_d());
         if (☃xxxxxxx > ☃xx.func_177733_e() - ☃xxx) {
            float ☃xxxx = 0.0F;

            for(double ☃xxxxx = ☃xxxxxxxxxxxxxxxx; ☃xxxxx < ☃xxxxxxxxxxxxxxxxx; ☃xxxx += 0.5F) {
               double ☃xxxxxx = Math.min(1.0, ☃xxxxxxxxxxxxxxxxx - ☃xxxxx);
               float ☃xxxxxxx = (float)☃xxxxxx * 0.5F;
               ☃x.func_181662_b(☃xxxxx, 256.0, ☃xx.func_177733_e())
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 0.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xxxxx + ☃xxxxxx, 256.0, ☃xx.func_177733_e())
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 0.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xxxxx + ☃xxxxxx, 0.0, ☃xx.func_177733_e())
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 128.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xxxxx, 0.0, ☃xx.func_177733_e())
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 128.0F))
                  .func_181675_d();
               ++☃xxxxx;
            }
         }

         if (☃xxxxxxx < ☃xx.func_177736_c() + ☃xxx) {
            float ☃xxxx = 0.0F;

            for(double ☃xxxxx = ☃xxxxxxxxxxxxxxxx; ☃xxxxx < ☃xxxxxxxxxxxxxxxxx; ☃xxxx += 0.5F) {
               double ☃xxxxxx = Math.min(1.0, ☃xxxxxxxxxxxxxxxxx - ☃xxxxx);
               float ☃xxxxxxx = (float)☃xxxxxx * 0.5F;
               ☃x.func_181662_b(☃xxxxx, 256.0, ☃xx.func_177736_c())
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 0.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xxxxx + ☃xxxxxx, 256.0, ☃xx.func_177736_c())
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 0.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xxxxx + ☃xxxxxx, 0.0, ☃xx.func_177736_c())
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 128.0F))
                  .func_181675_d();
               ☃x.func_181662_b(☃xxxxx, 0.0, ☃xx.func_177736_c())
                  .func_187315_a((double)(☃xxxxxxxxxxxx + ☃xxxx), (double)(☃xxxxxxxxxxxx + 128.0F))
                  .func_181675_d();
               ++☃xxxxx;
            }
         }

         ☃.func_78381_a();
         ☃x.func_178969_c(0.0, 0.0, 0.0);
         GlStateManager.func_179089_o();
         GlStateManager.func_179118_c();
         GlStateManager.func_179136_a(0.0F, 0.0F);
         GlStateManager.func_179113_r();
         GlStateManager.func_179141_d();
         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
         GlStateManager.func_179132_a(true);
      }
   }

   private void func_180443_s() {
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179147_l();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 0.5F);
      GlStateManager.func_179136_a(-1.0F, -10.0F);
      GlStateManager.func_179088_q();
      GlStateManager.func_179092_a(516, 0.1F);
      GlStateManager.func_179141_d();
      GlStateManager.func_179094_E();
   }

   private void func_174969_t() {
      GlStateManager.func_179118_c();
      GlStateManager.func_179136_a(0.0F, 0.0F);
      GlStateManager.func_179113_r();
      GlStateManager.func_179141_d();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179121_F();
   }

   public void func_174981_a(Tessellator var1, BufferBuilder var2, Entity var3, float var4) {
      double ☃ = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃x = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      if (!this.field_72738_E.isEmpty()) {
         this.field_72770_i.func_110577_a(TextureMap.field_110575_b);
         this.func_180443_s();
         ☃.func_181668_a(7, DefaultVertexFormats.field_176600_a);
         ☃.func_178969_c(-☃, -☃x, -☃xx);
         ☃.func_78914_f();
         Iterator<DestroyBlockProgress> ☃xxx = this.field_72738_E.values().iterator();

         while(☃xxx.hasNext()) {
            DestroyBlockProgress ☃xxxx = (DestroyBlockProgress)☃xxx.next();
            BlockPos ☃xxxxx = ☃xxxx.func_180246_b();
            Block ☃xxxxxx = this.field_72769_h.func_180495_p(☃xxxxx).func_177230_c();
            if (!(☃xxxxxx instanceof BlockChest)
               && !(☃xxxxxx instanceof BlockEnderChest)
               && !(☃xxxxxx instanceof BlockSign)
               && !(☃xxxxxx instanceof BlockAbstractSkull)) {
               double ☃xxxxxxx = (double)☃xxxxx.func_177958_n() - ☃;
               double ☃xxxxxxxx = (double)☃xxxxx.func_177956_o() - ☃x;
               double ☃xxxxxxxxx = (double)☃xxxxx.func_177952_p() - ☃xx;
               if (☃xxxxxxx * ☃xxxxxxx + ☃xxxxxxxx * ☃xxxxxxxx + ☃xxxxxxxxx * ☃xxxxxxxxx > 1024.0) {
                  ☃xxx.remove();
               } else {
                  IBlockState ☃xxxxxxx = this.field_72769_h.func_180495_p(☃xxxxx);
                  if (!☃xxxxxxx.func_196958_f()) {
                     int ☃xxxxxxxx = ☃xxxx.func_73106_e();
                     TextureAtlasSprite ☃xxxxxxxxx = this.field_94141_F[☃xxxxxxxx];
                     BlockRendererDispatcher ☃xxxxxxxxxx = this.field_72777_q.func_175602_ab();
                     ☃xxxxxxxxxx.func_175020_a(☃xxxxxxx, ☃xxxxx, ☃xxxxxxxxx, this.field_72769_h);
                  }
               }
            }
         }

         ☃.func_78381_a();
         ☃.func_178969_c(0.0, 0.0, 0.0);
         this.func_174969_t();
      }
   }

   public void func_72731_b(EntityPlayer var1, RayTraceResult var2, int var3, float var4) {
      if (☃ == 0 && ☃.field_72313_a == RayTraceResult.Type.BLOCK) {
         BlockPos ☃ = ☃.func_178782_a();
         IBlockState ☃x = this.field_72769_h.func_180495_p(☃);
         if (!☃x.func_196958_f() && this.field_72769_h.func_175723_af().func_177746_a(☃)) {
            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(
               GlStateManager.SourceFactor.SRC_ALPHA,
               GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
               GlStateManager.SourceFactor.ONE,
               GlStateManager.DestFactor.ZERO
            );
            GlStateManager.func_187441_d(Math.max(2.5F, (float)this.field_72777_q.field_195558_d.func_198109_k() / 1920.0F * 2.5F));
            GlStateManager.func_179090_x();
            GlStateManager.func_179132_a(false);
            GlStateManager.func_179128_n(5889);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(1.0F, 1.0F, 0.999F);
            double ☃xx = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
            double ☃xxx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
            double ☃xxxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
            func_195463_b(
               ☃x.func_196954_c(this.field_72769_h, ☃),
               (double)☃.func_177958_n() - ☃xx,
               (double)☃.func_177956_o() - ☃xxx,
               (double)☃.func_177952_p() - ☃xxxx,
               0.0F,
               0.0F,
               0.0F,
               0.4F
            );
            GlStateManager.func_179121_F();
            GlStateManager.func_179128_n(5888);
            GlStateManager.func_179132_a(true);
            GlStateManager.func_179098_w();
            GlStateManager.func_179084_k();
         }
      }
   }

   public static void func_195470_a(VoxelShape var0, double var1, double var3, double var5, float var7, float var8, float var9, float var10) {
      List<AxisAlignedBB> ☃ = ☃.func_197756_d();
      int ☃x = MathHelper.func_76143_f((double)☃.size() / 3.0);

      for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
         AxisAlignedBB ☃xxx = (AxisAlignedBB)☃.get(☃xx);
         float ☃xxxx = ((float)☃xx % (float)☃x + 1.0F) / (float)☃x;
         float ☃xxxxx = (float)(☃xx / ☃x);
         float ☃xxxxxx = ☃xxxx * (float)(☃xxxxx == 0.0F ? 1 : 0);
         float ☃xxxxxxx = ☃xxxx * (float)(☃xxxxx == 1.0F ? 1 : 0);
         float ☃xxxxxxxx = ☃xxxx * (float)(☃xxxxx == 2.0F ? 1 : 0);
         func_195463_b(VoxelShapes.func_197881_a(☃xxx.func_72317_d(0.0, 0.0, 0.0)), ☃, ☃, ☃, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 1.0F);
      }
   }

   public static void func_195463_b(VoxelShape var0, double var1, double var3, double var5, float var7, float var8, float var9, float var10) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      ☃x.func_181668_a(1, DefaultVertexFormats.field_181706_f);
      ☃.func_197754_a((var11x, var13, var15, var17, var19, var21) -> {
         ☃.func_181662_b(var11x + ☃, var13 + ☃, var15 + ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
         ☃.func_181662_b(var17 + ☃, var19 + ☃, var21 + ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      });
      ☃.func_78381_a();
   }

   public static void func_189697_a(AxisAlignedBB var0, float var1, float var2, float var3, float var4) {
      func_189694_a(☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c, ☃.field_72336_d, ☃.field_72337_e, ☃.field_72334_f, ☃, ☃, ☃, ☃);
   }

   public static void func_189694_a(
      double var0, double var2, double var4, double var6, double var8, double var10, float var12, float var13, float var14, float var15
   ) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      ☃x.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      func_189698_a(☃x, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      ☃.func_78381_a();
   }

   public static void func_189698_a(
      BufferBuilder var0, double var1, double var3, double var5, double var7, double var9, double var11, float var13, float var14, float var15, float var16
   ) {
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, 0.0F).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, 0.0F).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, 0.0F).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, 0.0F).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, 0.0F).func_181675_d();
   }

   public static void func_189696_b(AxisAlignedBB var0, float var1, float var2, float var3, float var4) {
      func_189695_b(☃.field_72340_a, ☃.field_72338_b, ☃.field_72339_c, ☃.field_72336_d, ☃.field_72337_e, ☃.field_72334_f, ☃, ☃, ☃, ☃);
   }

   public static void func_189695_b(
      double var0, double var2, double var4, double var6, double var8, double var10, float var12, float var13, float var14, float var15
   ) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      ☃x.func_181668_a(5, DefaultVertexFormats.field_181706_f);
      func_189693_b(☃x, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      ☃.func_78381_a();
   }

   public static void func_189693_b(
      BufferBuilder var0, double var1, double var3, double var5, double var7, double var9, double var11, float var13, float var14, float var15, float var16
   ) {
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a(☃, ☃, ☃, ☃).func_181675_d();
   }

   private void func_184385_a(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      this.field_175008_n.func_187474_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_184376_a(IBlockReader var1, BlockPos var2, IBlockState var3, IBlockState var4, int var5) {
      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p();
      this.func_184385_a(☃ - 1, ☃x - 1, ☃xx - 1, ☃ + 1, ☃x + 1, ☃xx + 1, (☃ & 8) != 0);
   }

   @Override
   public void func_174959_b(BlockPos var1) {
      this.field_184387_ae.add(☃.func_185334_h());
   }

   @Override
   public void func_147585_a(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.func_184385_a(☃ - 1, ☃ - 1, ☃ - 1, ☃ + 1, ☃ + 1, ☃ + 1, false);
   }

   @Override
   public void func_184377_a(@Nullable SoundEvent var1, BlockPos var2) {
      ISound ☃ = (ISound)this.field_147593_P.get(☃);
      if (☃ != null) {
         this.field_72777_q.func_147118_V().func_147683_b(☃);
         this.field_147593_P.remove(☃);
      }

      if (☃ != null) {
         ItemRecord ☃ = ItemRecord.func_185074_a(☃);
         if (☃ != null) {
            this.field_72777_q.field_71456_v.func_73833_a(☃.func_200299_h().func_150254_d());
         }

         ISound var5 = SimpleSound.func_184372_a(☃, (float)☃.func_177958_n(), (float)☃.func_177956_o(), (float)☃.func_177952_p());
         this.field_147593_P.put(☃, var5);
         this.field_72777_q.func_147118_V().func_147682_a(var5);
      }

      this.func_193054_a(this.field_72769_h, ☃, ☃ != null);
   }

   private void func_193054_a(World var1, BlockPos var2, boolean var3) {
      for(EntityLivingBase ☃ : ☃.func_72872_a(EntityLivingBase.class, new AxisAlignedBB(☃).func_186662_g(3.0))) {
         ☃.func_191987_a(☃, ☃);
      }
   }

   @Override
   public void func_184375_a(@Nullable EntityPlayer var1, SoundEvent var2, SoundCategory var3, double var4, double var6, double var8, float var10, float var11) {
   }

   @Override
   public void func_195461_a(IParticleData var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      this.func_195462_a(☃, ☃, false, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_195462_a(IParticleData var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14) {
      try {
         this.func_195469_b(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } catch (Throwable var19) {
         CrashReport ☃ = CrashReport.func_85055_a(var19, "Exception while adding particle");
         CrashReportCategory ☃x = ☃.func_85058_a("Particle being added");
         ☃x.func_71507_a("ID", ☃.func_197554_b().func_197570_d());
         ☃x.func_71507_a("Parameters", ☃.func_197555_a());
         ☃x.func_189529_a("Position", () -> CrashReportCategory.func_85074_a(☃, ☃, ☃));
         throw new ReportedException(☃);
      }
   }

   private <T extends IParticleData> void func_195467_a(T var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this.func_195461_a(☃, ☃.func_197554_b().func_197575_f(), ☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Nullable
   private Particle func_195471_b(IParticleData var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      return this.func_195469_b(☃, ☃, false, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Nullable
   private Particle func_195469_b(
      IParticleData var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14
   ) {
      Entity ☃ = this.field_72777_q.func_175606_aa();
      if (this.field_72777_q != null && ☃ != null && this.field_72777_q.field_71452_i != null) {
         int ☃x = this.func_190572_a(☃);
         double ☃xx = ☃.field_70165_t - ☃;
         double ☃xxx = ☃.field_70163_u - ☃;
         double ☃xxxx = ☃.field_70161_v - ☃;
         if (☃) {
            return this.field_72777_q.field_71452_i.func_199280_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         } else if (☃xx * ☃xx + ☃xxx * ☃xxx + ☃xxxx * ☃xxxx > 1024.0) {
            return null;
         } else {
            return ☃x > 1 ? null : this.field_72777_q.field_71452_i.func_199280_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         }
      } else {
         return null;
      }
   }

   private int func_190572_a(boolean var1) {
      int ☃ = this.field_72777_q.field_71474_y.field_74362_aa;
      if (☃ && ☃ == 2 && this.field_72769_h.field_73012_v.nextInt(10) == 0) {
         ☃ = 1;
      }

      if (☃ == 1 && this.field_72769_h.field_73012_v.nextInt(3) == 0) {
         ☃ = 2;
      }

      return ☃;
   }

   @Override
   public void func_72703_a(Entity var1) {
   }

   @Override
   public void func_72709_b(Entity var1) {
   }

   public void func_72728_f() {
   }

   @Override
   public void func_180440_a(int var1, BlockPos var2, int var3) {
      switch(☃) {
         case 1023:
         case 1028:
         case 1038:
            Entity ☃ = this.field_72777_q.func_175606_aa();
            if (☃ != null) {
               double ☃x = (double)☃.func_177958_n() - ☃.field_70165_t;
               double ☃xx = (double)☃.func_177956_o() - ☃.field_70163_u;
               double ☃xxx = (double)☃.func_177952_p() - ☃.field_70161_v;
               double ☃xxxx = Math.sqrt(☃x * ☃x + ☃xx * ☃xx + ☃xxx * ☃xxx);
               double ☃xxxxx = ☃.field_70165_t;
               double ☃xxxxxx = ☃.field_70163_u;
               double ☃xxxxxxx = ☃.field_70161_v;
               if (☃xxxx > 0.0) {
                  ☃xxxxx += ☃x / ☃xxxx * 2.0;
                  ☃xxxxxx += ☃xx / ☃xxxx * 2.0;
                  ☃xxxxxxx += ☃xxx / ☃xxxx * 2.0;
               }

               if (☃ == 1023) {
                  this.field_72769_h.func_184134_a(☃xxxxx, ☃xxxxxx, ☃xxxxxxx, SoundEvents.field_187855_gD, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
               } else if (☃ == 1038) {
                  this.field_72769_h.func_184134_a(☃xxxxx, ☃xxxxxx, ☃xxxxxxx, SoundEvents.field_193782_bq, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
               } else {
                  this.field_72769_h.func_184134_a(☃xxxxx, ☃xxxxxx, ☃xxxxxxx, SoundEvents.field_187522_aL, SoundCategory.HOSTILE, 5.0F, 1.0F, false);
               }
            }
      }
   }

   @Override
   public void func_180439_a(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      Random ☃ = this.field_72769_h.field_73012_v;
      switch(☃) {
         case 1000:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187574_as, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1001:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187576_at, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
            break;
         case 1002:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187578_au, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
            break;
         case 1003:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187528_aR, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
            break;
         case 1004:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187634_bp, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
            break;
         case 1005:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187611_cI, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1006:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187875_gN, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1007:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187879_gP, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1008:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187613_bi, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1009:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 0.5F, 2.6F + (☃.nextFloat() - ☃.nextFloat()) * 0.8F, false);
            break;
         case 1010:
            if (Item.func_150899_d(☃) instanceof ItemRecord) {
               this.field_72769_h.func_184149_a(☃, ((ItemRecord)Item.func_150899_d(☃)).func_185075_h());
            } else {
               this.field_72769_h.func_184149_a(☃, null);
            }
            break;
         case 1011:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187608_cH, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1012:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187873_gM, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1013:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187877_gO, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1014:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187610_bh, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1015:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187559_bL, SoundCategory.HOSTILE, 10.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1016:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187557_bK, SoundCategory.HOSTILE, 10.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1017:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187527_aQ, SoundCategory.HOSTILE, 10.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1018:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187606_E, SoundCategory.HOSTILE, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1019:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187927_ha, SoundCategory.HOSTILE, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1020:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187928_hb, SoundCategory.HOSTILE, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1021:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187929_hc, SoundCategory.HOSTILE, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1022:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187926_gz, SoundCategory.HOSTILE, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1024:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187853_gC, SoundCategory.HOSTILE, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1025:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187744_z, SoundCategory.NEUTRAL, 0.05F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1026:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187945_hs, SoundCategory.HOSTILE, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1027:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187941_ho, SoundCategory.NEUTRAL, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1029:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187680_c, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1030:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187698_i, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1031:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187689_f, SoundCategory.BLOCKS, 0.3F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1032:
            this.field_72777_q.func_147118_V().func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_187812_eh, ☃.nextFloat() * 0.4F + 0.8F));
            break;
         case 1033:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187542_ac, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1034:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187540_ab, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1035:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_187621_J, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1036:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187614_cJ, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1037:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187617_cK, SoundCategory.BLOCKS, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1039:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_203257_fu, SoundCategory.HOSTILE, 0.3F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1040:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_204783_kG, SoundCategory.NEUTRAL, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1041:
            this.field_72769_h.func_184156_a(☃, SoundEvents.field_207378_dT, SoundCategory.NEUTRAL, 2.0F, (☃.nextFloat() - ☃.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 2000:
            EnumFacing ☃x = EnumFacing.func_82600_a(☃);
            int ☃xx = ☃x.func_82601_c();
            int ☃xxx = ☃x.func_96559_d();
            int ☃xxxx = ☃x.func_82599_e();
            double ☃xxxxx = (double)☃.func_177958_n() + (double)☃xx * 0.6 + 0.5;
            double ☃xxxxxx = (double)☃.func_177956_o() + (double)☃xxx * 0.6 + 0.5;
            double ☃xxxxxxx = (double)☃.func_177952_p() + (double)☃xxxx * 0.6 + 0.5;

            for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < 10; ++☃xxxxxxxx) {
               double ☃xxxxxxxxx = ☃.nextDouble() * 0.2 + 0.01;
               double ☃xxxxxxxxxx = ☃xxxxx + (double)☃xx * 0.01 + (☃.nextDouble() - 0.5) * (double)☃xxxx * 0.5;
               double ☃xxxxxxxxxxx = ☃xxxxxx + (double)☃xxx * 0.01 + (☃.nextDouble() - 0.5) * (double)☃xxx * 0.5;
               double ☃xxxxxxxxxxxx = ☃xxxxxxx + (double)☃xxxx * 0.01 + (☃.nextDouble() - 0.5) * (double)☃xx * 0.5;
               double ☃xxxxxxxxxxxxx = (double)☃xx * ☃xxxxxxxxx + ☃.nextGaussian() * 0.01;
               double ☃xxxxxxxxxxxxxx = (double)☃xxx * ☃xxxxxxxxx + ☃.nextGaussian() * 0.01;
               double ☃xxxxxxxxxxxxxxx = (double)☃xxxx * ☃xxxxxxxxx + ☃.nextGaussian() * 0.01;
               this.func_195467_a(Particles.field_197601_L, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx);
            }
            break;
         case 2001:
            IBlockState ☃x = Block.func_196257_b(☃);
            if (!☃x.func_196958_f()) {
               SoundType ☃xx = ☃x.func_177230_c().func_185467_w();
               this.field_72769_h
                  .func_184156_a(☃, ☃xx.func_185845_c(), SoundCategory.BLOCKS, (☃xx.func_185843_a() + 1.0F) / 2.0F, ☃xx.func_185847_b() * 0.8F, false);
            }

            this.field_72777_q.field_71452_i.func_180533_a(☃, ☃x);
            break;
         case 2002:
         case 2007:
            double ☃x = (double)☃.func_177958_n();
            double ☃xx = (double)☃.func_177956_o();
            double ☃xxx = (double)☃.func_177952_p();

            for(int ☃xxxx = 0; ☃xxxx < 8; ++☃xxxx) {
               this.func_195467_a(
                  new ItemParticleData(Particles.field_197591_B, new ItemStack(Items.field_185155_bH)),
                  ☃x,
                  ☃xx,
                  ☃xxx,
                  ☃.nextGaussian() * 0.15,
                  ☃.nextDouble() * 0.2,
                  ☃.nextGaussian() * 0.15
               );
            }

            float ☃xxxx = (float)(☃ >> 16 & 0xFF) / 255.0F;
            float ☃xxxxx = (float)(☃ >> 8 & 0xFF) / 255.0F;
            float ☃xxxxxx = (float)(☃ >> 0 & 0xFF) / 255.0F;
            IParticleData ☃xxxxxxx = ☃ == 2007 ? Particles.field_197590_A : Particles.field_197620_m;

            for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < 100; ++☃xxxxxxxx) {
               double ☃xxxxxxxxx = ☃.nextDouble() * 4.0;
               double ☃xxxxxxxxxx = ☃.nextDouble() * Math.PI * 2.0;
               double ☃xxxxxxxxxxx = Math.cos(☃xxxxxxxxxx) * ☃xxxxxxxxx;
               double ☃xxxxxxxxxxxx = 0.01 + ☃.nextDouble() * 0.5;
               double ☃xxxxxxxxxxxxx = Math.sin(☃xxxxxxxxxx) * ☃xxxxxxxxx;
               Particle ☃xxxxxxxxxxxxxx = this.func_195471_b(
                  ☃xxxxxxx,
                  ☃xxxxxxx.func_197554_b().func_197575_f(),
                  ☃x + ☃xxxxxxxxxxx * 0.1,
                  ☃xx + 0.3,
                  ☃xxx + ☃xxxxxxxxxxxxx * 0.1,
                  ☃xxxxxxxxxxx,
                  ☃xxxxxxxxxxxx,
                  ☃xxxxxxxxxxxxx
               );
               if (☃xxxxxxxxxxxxxx != null) {
                  float ☃xxxxxxxxxxxxxxx = 0.75F + ☃.nextFloat() * 0.25F;
                  ☃xxxxxxxxxxxxxx.func_70538_b(☃xxxx * ☃xxxxxxxxxxxxxxx, ☃xxxxx * ☃xxxxxxxxxxxxxxx, ☃xxxxxx * ☃xxxxxxxxxxxxxxx);
                  ☃xxxxxxxxxxxxxx.func_70543_e((float)☃xxxxxxxxx);
               }
            }

            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187825_fO, SoundCategory.NEUTRAL, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 2003:
            double ☃x = (double)☃.func_177958_n() + 0.5;
            double ☃xx = (double)☃.func_177956_o();
            double ☃xxx = (double)☃.func_177952_p() + 0.5;

            for(int ☃xxxx = 0; ☃xxxx < 8; ++☃xxxx) {
               this.func_195467_a(
                  new ItemParticleData(Particles.field_197591_B, new ItemStack(Items.field_151061_bv)),
                  ☃x,
                  ☃xx,
                  ☃xxx,
                  ☃.nextGaussian() * 0.15,
                  ☃.nextDouble() * 0.2,
                  ☃.nextGaussian() * 0.15
               );
            }

            for(double ☃xxxx = 0.0; ☃xxxx < Math.PI * 2; ☃xxxx += Math.PI / 20) {
               this.func_195467_a(
                  Particles.field_197599_J,
                  ☃x + Math.cos(☃xxxx) * 5.0,
                  ☃xx - 0.4,
                  ☃xxx + Math.sin(☃xxxx) * 5.0,
                  Math.cos(☃xxxx) * -5.0,
                  0.0,
                  Math.sin(☃xxxx) * -5.0
               );
               this.func_195467_a(
                  Particles.field_197599_J,
                  ☃x + Math.cos(☃xxxx) * 5.0,
                  ☃xx - 0.4,
                  ☃xxx + Math.sin(☃xxxx) * 5.0,
                  Math.cos(☃xxxx) * -7.0,
                  0.0,
                  Math.sin(☃xxxx) * -7.0
               );
            }
            break;
         case 2004:
            for(int ☃x = 0; ☃x < 20; ++☃x) {
               double ☃xx = (double)☃.func_177958_n() + 0.5 + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5) * 2.0;
               double ☃xxx = (double)☃.func_177956_o() + 0.5 + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5) * 2.0;
               double ☃xxxx = (double)☃.func_177952_p() + 0.5 + ((double)this.field_72769_h.field_73012_v.nextFloat() - 0.5) * 2.0;
               this.field_72769_h.func_195594_a(Particles.field_197601_L, ☃xx, ☃xxx, ☃xxxx, 0.0, 0.0, 0.0);
               this.field_72769_h.func_195594_a(Particles.field_197631_x, ☃xx, ☃xxx, ☃xxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 2005:
            ItemBoneMeal.func_195965_a(this.field_72769_h, ☃, ☃);
            break;
         case 2006:
            for(int ☃x = 0; ☃x < 200; ++☃x) {
               float ☃xx = ☃.nextFloat() * 4.0F;
               float ☃xxx = ☃.nextFloat() * (float) (Math.PI * 2);
               double ☃xxxx = (double)(MathHelper.func_76134_b(☃xxx) * ☃xx);
               double ☃xxxxx = 0.01 + ☃.nextDouble() * 0.5;
               double ☃xxxxxx = (double)(MathHelper.func_76126_a(☃xxx) * ☃xx);
               Particle ☃xxxxxxx = this.func_195471_b(
                  Particles.field_197616_i,
                  false,
                  (double)☃.func_177958_n() + ☃xxxx * 0.1,
                  (double)☃.func_177956_o() + 0.3,
                  (double)☃.func_177952_p() + ☃xxxxxx * 0.1,
                  ☃xxxx,
                  ☃xxxxx,
                  ☃xxxxxx
               );
               if (☃xxxxxxx != null) {
                  ☃xxxxxxx.func_70543_e(☃xx);
               }
            }

            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187523_aM, SoundCategory.HOSTILE, 1.0F, this.field_72769_h.field_73012_v.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 3000:
            this.field_72769_h
               .func_195590_a(
                  Particles.field_197626_s,
                  true,
                  (double)☃.func_177958_n() + 0.5,
                  (double)☃.func_177956_o() + 0.5,
                  (double)☃.func_177952_p() + 0.5,
                  0.0,
                  0.0,
                  0.0
               );
            this.field_72769_h
               .func_184156_a(
                  ☃,
                  SoundEvents.field_187598_bd,
                  SoundCategory.BLOCKS,
                  10.0F,
                  (1.0F + (this.field_72769_h.field_73012_v.nextFloat() - this.field_72769_h.field_73012_v.nextFloat()) * 0.2F) * 0.7F,
                  false
               );
            break;
         case 3001:
            this.field_72769_h
               .func_184156_a(☃, SoundEvents.field_187525_aO, SoundCategory.HOSTILE, 64.0F, 0.8F + this.field_72769_h.field_73012_v.nextFloat() * 0.3F, false);
      }
   }

   @Override
   public void func_180441_b(int var1, BlockPos var2, int var3) {
      if (☃ >= 0 && ☃ < 10) {
         DestroyBlockProgress ☃ = (DestroyBlockProgress)this.field_72738_E.get(☃);
         if (☃ == null
            || ☃.func_180246_b().func_177958_n() != ☃.func_177958_n()
            || ☃.func_180246_b().func_177956_o() != ☃.func_177956_o()
            || ☃.func_180246_b().func_177952_p() != ☃.func_177952_p()) {
            ☃ = new DestroyBlockProgress(☃, ☃);
            this.field_72738_E.put(☃, ☃);
         }

         ☃.func_73107_a(☃);
         ☃.func_82744_b(this.field_72773_u);
      } else {
         this.field_72738_E.remove(☃);
      }
   }

   public boolean func_184384_n() {
      return this.field_175009_l.isEmpty() && this.field_174995_M.func_188247_f();
   }

   public void func_174979_m() {
      this.field_147595_R = true;
      this.field_204607_y = true;
   }

   public void func_181023_a(Collection<TileEntity> var1, Collection<TileEntity> var2) {
      synchronized(this.field_181024_n) {
         this.field_181024_n.removeAll(☃);
         this.field_181024_n.addAll(☃);
      }
   }

   class ContainerLocalRenderInformation {
      private final RenderChunk field_178036_a;
      private final EnumFacing field_178034_b;
      private byte field_178035_c;
      private final int field_178032_d;

      private ContainerLocalRenderInformation(RenderChunk var2, @Nullable EnumFacing var3, int var4) {
         this.field_178036_a = ☃;
         this.field_178034_b = ☃;
         this.field_178032_d = ☃;
      }

      public void func_189561_a(byte var1, EnumFacing var2) {
         this.field_178035_c = (byte)(this.field_178035_c | ☃ | 1 << ☃.ordinal());
      }

      public boolean func_189560_a(EnumFacing var1) {
         return (this.field_178035_c & 1 << ☃.ordinal()) > 0;
      }
   }
}
