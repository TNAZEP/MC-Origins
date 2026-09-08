package net.minecraft.client.particle;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.init.Particles;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;

public class ParticleManager {
   private static final ResourceLocation field_110737_b = new ResourceLocation("textures/particle/particles.png");
   protected World field_78878_a;
   private final ArrayDeque<Particle>[][] field_78876_b = new ArrayDeque[4][];
   private final Queue<ParticleEmitter> field_178933_d = Queues.<ParticleEmitter>newArrayDeque();
   private final TextureManager field_78877_c;
   private final Random field_78875_d = new Random();
   private final Int2ObjectMap<IParticleFactory<?>> field_178932_g = new Int2ObjectOpenHashMap<>();
   private final Queue<Particle> field_187241_h = Queues.<Particle>newArrayDeque();

   public ParticleManager(World var1, TextureManager var2) {
      this.field_78878_a = ☃;
      this.field_78877_c = ☃;

      for(int ☃ = 0; ☃ < 4; ++☃) {
         this.field_78876_b[☃] = new ArrayDeque[2];

         for(int ☃x = 0; ☃x < 2; ++☃x) {
            this.field_78876_b[☃][☃x] = Queues.newArrayDeque();
         }
      }

      this.func_178930_c();
   }

   private void func_178930_c() {
      this.func_199283_a(Particles.field_197608_a, new ParticleSpell.AmbientMobFactory());
      this.func_199283_a(Particles.field_197609_b, new ParticleHeart.AngryVillagerFactory());
      this.func_199283_a(Particles.field_197610_c, new Barrier.Factory());
      this.func_199283_a(Particles.field_197611_d, new ParticleDigging.Factory());
      this.func_199283_a(Particles.field_197612_e, new ParticleBubble.Factory());
      this.func_199283_a(Particles.field_203220_f, new ParticleBubbleColumnUp.Factory());
      this.func_199283_a(Particles.field_203217_T, new ParticleBubblePop.Factory());
      this.func_199283_a(Particles.field_197613_f, new ParticleCloud.Factory());
      this.func_199283_a(Particles.field_197614_g, new ParticleCrit.Factory());
      this.func_199283_a(Particles.field_203218_U, new ParticleCurrentDown.Factory());
      this.func_199283_a(Particles.field_197615_h, new ParticleCrit.DamageIndicatorFactory());
      this.func_199283_a(Particles.field_197616_i, new ParticleDragonBreath.Factory());
      this.func_199283_a(Particles.field_206864_X, new ParticleSuspendedTown.DolphinSpeedFactory());
      this.func_199283_a(Particles.field_197617_j, new ParticleDrip.LavaFactory());
      this.func_199283_a(Particles.field_197618_k, new ParticleDrip.WaterFactory());
      this.func_199283_a(Particles.field_197619_l, new ParticleRedstone.Factory());
      this.func_199283_a(Particles.field_197620_m, new ParticleSpell.Factory());
      this.func_199283_a(Particles.field_197621_n, new ParticleMobAppearance.Factory());
      this.func_199283_a(Particles.field_197622_o, new ParticleCrit.MagicFactory());
      this.func_199283_a(Particles.field_197623_p, new ParticleEnchantmentTable.EnchantmentTable());
      this.func_199283_a(Particles.field_197624_q, new ParticleEndRod.Factory());
      this.func_199283_a(Particles.field_197625_r, new ParticleSpell.MobFactory());
      this.func_199283_a(Particles.field_197626_s, new ParticleExplosionHuge.Factory());
      this.func_199283_a(Particles.field_197627_t, new ParticleExplosionLarge.Factory());
      this.func_199283_a(Particles.field_197628_u, new ParticleFallingDust.Factory());
      this.func_199283_a(Particles.field_197629_v, new ParticleFirework.Factory());
      this.func_199283_a(Particles.field_197630_w, new ParticleWaterWake.Factory());
      this.func_199283_a(Particles.field_197631_x, new ParticleFlame.Factory());
      this.func_199283_a(Particles.field_197632_y, new ParticleSuspendedTown.HappyVillagerFactory());
      this.func_199283_a(Particles.field_197633_z, new ParticleHeart.Factory());
      this.func_199283_a(Particles.field_197590_A, new ParticleSpell.InstantFactory());
      this.func_199283_a(Particles.field_197591_B, new ParticleBreaking.Factory());
      this.func_199283_a(Particles.field_197592_C, new ParticleBreaking.SlimeFactory());
      this.func_199283_a(Particles.field_197593_D, new ParticleBreaking.SnowballFactory());
      this.func_199283_a(Particles.field_197594_E, new ParticleSmokeLarge.Factory());
      this.func_199283_a(Particles.field_197595_F, new ParticleLava.Factory());
      this.func_199283_a(Particles.field_197596_G, new ParticleSuspendedTown.Factory());
      this.func_199283_a(Particles.field_205167_W, new ParticleEnchantmentTable.NautilusFactory());
      this.func_199283_a(Particles.field_197597_H, new ParticleNote.Factory());
      this.func_199283_a(Particles.field_197598_I, new ParticleExplosion.Factory());
      this.func_199283_a(Particles.field_197599_J, new ParticlePortal.Factory());
      this.func_199283_a(Particles.field_197600_K, new ParticleRain.Factory());
      this.func_199283_a(Particles.field_197601_L, new ParticleSmokeNormal.Factory());
      this.func_199283_a(Particles.field_197602_M, new ParticleSpit.Factory());
      this.func_199283_a(Particles.field_197603_N, new ParticleSweepAttack.Factory());
      this.func_199283_a(Particles.field_197604_O, new ParticleTotem.Factory());
      this.func_199283_a(Particles.field_203219_V, new ParticleSquidInk.Factory());
      this.func_199283_a(Particles.field_197605_P, new ParticleSuspend.Factory());
      this.func_199283_a(Particles.field_197606_Q, new ParticleSplash.Factory());
      this.func_199283_a(Particles.field_197607_R, new ParticleSpell.WitchFactory());
   }

   public <T extends IParticleData> void func_199283_a(ParticleType<T> var1, IParticleFactory<T> var2) {
      this.field_178932_g.put(IRegistry.field_212632_u.func_148757_b(☃), ☃);
   }

   public void func_199282_a(Entity var1, IParticleData var2) {
      this.field_178933_d.add(new ParticleEmitter(this.field_78878_a, ☃, ☃));
   }

   public void func_199281_a(Entity var1, IParticleData var2, int var3) {
      this.field_178933_d.add(new ParticleEmitter(this.field_78878_a, ☃, ☃, ☃));
   }

   @Nullable
   public Particle func_199280_a(IParticleData var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      Particle ☃ = this.func_199927_b(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (☃ != null) {
         this.func_78873_a(☃);
         return ☃;
      } else {
         return null;
      }
   }

   @Nullable
   private <T extends IParticleData> Particle func_199927_b(T var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      IParticleFactory<T> ☃ = (IParticleFactory)this.field_178932_g.get(IRegistry.field_212632_u.func_148757_b(☃.func_197554_b()));
      return ☃ == null ? null : ☃.func_199234_a(☃, this.field_78878_a, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   public void func_78873_a(Particle var1) {
      this.field_187241_h.add(☃);
   }

   public void func_78868_a() {
      for(int ☃ = 0; ☃ < 4; ++☃) {
         this.func_178922_a(☃);
      }

      if (!this.field_178933_d.isEmpty()) {
         List<ParticleEmitter> ☃ = Lists.<ParticleEmitter>newArrayList();

         for(ParticleEmitter ☃x : this.field_178933_d) {
            ☃x.func_189213_a();
            if (!☃x.func_187113_k()) {
               ☃.add(☃x);
            }
         }

         this.field_178933_d.removeAll(☃);
      }

      if (!this.field_187241_h.isEmpty()) {
         for(Particle ☃ = (Particle)this.field_187241_h.poll(); ☃ != null; ☃ = (Particle)this.field_187241_h.poll()) {
            int ☃x = ☃.func_70537_b();
            int ☃xx = ☃.func_187111_c() ? 0 : 1;
            if (this.field_78876_b[☃x][☃xx].size() >= 16384) {
               this.field_78876_b[☃x][☃xx].removeFirst();
            }

            this.field_78876_b[☃x][☃xx].add(☃);
         }
      }
   }

   private void func_178922_a(int var1) {
      this.field_78878_a.field_72984_F.func_76320_a(String.valueOf(☃));

      for(int ☃ = 0; ☃ < 2; ++☃) {
         this.field_78878_a.field_72984_F.func_76320_a(String.valueOf(☃));
         this.func_187240_a(this.field_78876_b[☃][☃]);
         this.field_78878_a.field_72984_F.func_76319_b();
      }

      this.field_78878_a.field_72984_F.func_76319_b();
   }

   private void func_187240_a(Queue<Particle> var1) {
      if (!☃.isEmpty()) {
         Iterator<Particle> ☃ = ☃.iterator();

         while(☃.hasNext()) {
            Particle ☃x = (Particle)☃.next();
            this.func_178923_d(☃x);
            if (!☃x.func_187113_k()) {
               ☃.remove();
            }
         }
      }
   }

   private void func_178923_d(Particle var1) {
      try {
         ☃.func_189213_a();
      } catch (Throwable var6) {
         CrashReport ☃ = CrashReport.func_85055_a(var6, "Ticking Particle");
         CrashReportCategory ☃x = ☃.func_85058_a("Particle being ticked");
         int ☃xx = ☃.func_70537_b();
         ☃x.func_189529_a("Particle", ☃::toString);
         ☃x.func_189529_a("Particle Type", () -> {
            if (☃ == 0) {
               return "MISC_TEXTURE";
            } else if (☃ == 1) {
               return "TERRAIN_TEXTURE";
            } else {
               return ☃ == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + ☃;
            }
         });
         throw new ReportedException(☃);
      }
   }

   public void func_78874_a(Entity var1, float var2) {
      float ☃ = ActiveRenderInfo.func_178808_b();
      float ☃x = ActiveRenderInfo.func_178803_d();
      float ☃xx = ActiveRenderInfo.func_178805_e();
      float ☃xxx = ActiveRenderInfo.func_178807_f();
      float ☃xxxx = ActiveRenderInfo.func_178809_c();
      Particle.field_70556_an = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      Particle.field_70554_ao = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      Particle.field_70555_ap = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      Particle.field_190016_K = ☃.func_70676_i(☃);
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      GlStateManager.func_179092_a(516, 0.003921569F);

      for(int ☃xxxxx = 0; ☃xxxxx < 3; ++☃xxxxx) {
         for(int ☃xxxxxx = 0; ☃xxxxxx < 2; ++☃xxxxxx) {
            if (!this.field_78876_b[☃xxxxx][☃xxxxxx].isEmpty()) {
               switch(☃xxxxxx) {
                  case 0:
                     GlStateManager.func_179132_a(false);
                     break;
                  case 1:
                     GlStateManager.func_179132_a(true);
               }

               switch(☃xxxxx) {
                  case 0:
                  default:
                     this.field_78877_c.func_110577_a(field_110737_b);
                     break;
                  case 1:
                     this.field_78877_c.func_110577_a(TextureMap.field_110575_b);
               }

               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               Tessellator ☃xxxxxxx = Tessellator.func_178181_a();
               BufferBuilder ☃xxxxxxxx = ☃xxxxxxx.func_178180_c();
               ☃xxxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181704_d);

               for(Particle ☃xxxxxxxxx : this.field_78876_b[☃xxxxx][☃xxxxxx]) {
                  try {
                     ☃xxxxxxxxx.func_180434_a(☃xxxxxxxx, ☃, ☃, ☃, ☃xxxx, ☃x, ☃xx, ☃xxx);
                  } catch (Throwable var18) {
                     CrashReport ☃xxxxxxxxxx = CrashReport.func_85055_a(var18, "Rendering Particle");
                     CrashReportCategory ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_85058_a("Particle being rendered");
                     int ☃xxxxxxxxxxxx = ☃xxxxx;
                     ☃xxxxxxxxxxx.func_189529_a("Particle", ☃xxxxxxxxx::toString);
                     ☃xxxxxxxxxxx.func_189529_a("Particle Type", () -> {
                        if (☃ == 0) {
                           return "MISC_TEXTURE";
                        } else if (☃ == 1) {
                           return "TERRAIN_TEXTURE";
                        } else {
                           return ☃ == 3 ? "ENTITY_PARTICLE_TEXTURE" : "Unknown - " + ☃;
                        }
                     });
                     throw new ReportedException(☃xxxxxxxxxx);
                  }
               }

               ☃xxxxxxx.func_78381_a();
            }
         }
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179084_k();
      GlStateManager.func_179092_a(516, 0.1F);
   }

   public void func_78872_b(Entity var1, float var2) {
      float ☃ = ActiveRenderInfo.func_178808_b();
      float ☃x = ActiveRenderInfo.func_178803_d();
      float ☃xx = ActiveRenderInfo.func_178805_e();
      float ☃xxx = ActiveRenderInfo.func_178807_f();
      float ☃xxxx = ActiveRenderInfo.func_178809_c();
      Particle.field_70556_an = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      Particle.field_70554_ao = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      Particle.field_70555_ap = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      Particle.field_190016_K = ☃.func_70676_i(☃);

      for(int ☃xxxxx = 0; ☃xxxxx < 2; ++☃xxxxx) {
         Queue<Particle> ☃xxxxxx = this.field_78876_b[3][☃xxxxx];
         if (!☃xxxxxx.isEmpty()) {
            Tessellator ☃xxxxxxx = Tessellator.func_178181_a();
            BufferBuilder ☃xxxxxxxx = ☃xxxxxxx.func_178180_c();

            for(Particle ☃xxxxxxxxx : ☃xxxxxx) {
               ☃xxxxxxxxx.func_180434_a(☃xxxxxxxx, ☃, ☃, ☃, ☃xxxx, ☃x, ☃xx, ☃xxx);
            }
         }
      }
   }

   public void func_78870_a(@Nullable World var1) {
      this.field_78878_a = ☃;

      for(int ☃ = 0; ☃ < 4; ++☃) {
         for(int ☃x = 0; ☃x < 2; ++☃x) {
            this.field_78876_b[☃][☃x].clear();
         }
      }

      this.field_178933_d.clear();
   }

   public void func_180533_a(BlockPos var1, IBlockState var2) {
      if (!☃.func_196958_f()) {
         VoxelShape ☃ = ☃.func_196954_c(this.field_78878_a, ☃);
         double ☃x = 0.25;
         ☃.func_197755_b(
            (var3x, var5, var7, var9, var11, var13) -> {
               double ☃ = Math.min(1.0, var9 - var3x);
               double ☃x = Math.min(1.0, var11 - var5);
               double ☃xx = Math.min(1.0, var13 - var7);
               int ☃xxx = Math.max(2, MathHelper.func_76143_f(☃ / 0.25));
               int ☃xxxx = Math.max(2, MathHelper.func_76143_f(☃x / 0.25));
               int ☃xxxxx = Math.max(2, MathHelper.func_76143_f(☃xx / 0.25));
   
               for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxx; ++☃xxxxxx) {
                  for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xxxx; ++☃xxxxxxx) {
                     for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃xxxxx; ++☃xxxxxxxx) {
                        double ☃xxxxxxxxx = ((double)☃xxxxxx + 0.5) / (double)☃xxx;
                        double ☃xxxxxxxxxx = ((double)☃xxxxxxx + 0.5) / (double)☃xxxx;
                        double ☃xxxxxxxxxxx = ((double)☃xxxxxxxx + 0.5) / (double)☃xxxxx;
                        double ☃xxxxxxxxxxxx = ☃xxxxxxxxx * ☃ + var3x;
                        double ☃xxxxxxxxxxxxx = ☃xxxxxxxxxx * ☃x + var5;
                        double ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxx * ☃xx + var7;
                        this.func_78873_a(
                           new ParticleDigging(
                                 this.field_78878_a,
                                 (double)☃.func_177958_n() + ☃xxxxxxxxxxxx,
                                 (double)☃.func_177956_o() + ☃xxxxxxxxxxxxx,
                                 (double)☃.func_177952_p() + ☃xxxxxxxxxxxxxx,
                                 ☃xxxxxxxxx - 0.5,
                                 ☃xxxxxxxxxx - 0.5,
                                 ☃xxxxxxxxxxx - 0.5,
                                 ☃
                              )
                              .func_174846_a(☃)
                        );
                     }
                  }
               }
            }
         );
      }
   }

   public void func_180532_a(BlockPos var1, EnumFacing var2) {
      IBlockState ☃ = this.field_78878_a.func_180495_p(☃);
      if (☃.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
         int ☃x = ☃.func_177958_n();
         int ☃xx = ☃.func_177956_o();
         int ☃xxx = ☃.func_177952_p();
         float ☃xxxx = 0.1F;
         AxisAlignedBB ☃xxxxx = ☃.func_196954_c(this.field_78878_a, ☃).func_197752_a();
         double ☃xxxxxx = (double)☃x + this.field_78875_d.nextDouble() * (☃xxxxx.field_72336_d - ☃xxxxx.field_72340_a - 0.2F) + 0.1F + ☃xxxxx.field_72340_a;
         double ☃xxxxxxx = (double)☃xx + this.field_78875_d.nextDouble() * (☃xxxxx.field_72337_e - ☃xxxxx.field_72338_b - 0.2F) + 0.1F + ☃xxxxx.field_72338_b;
         double ☃xxxxxxxx = (double)☃xxx + this.field_78875_d.nextDouble() * (☃xxxxx.field_72334_f - ☃xxxxx.field_72339_c - 0.2F) + 0.1F + ☃xxxxx.field_72339_c;
         if (☃ == EnumFacing.DOWN) {
            ☃xxxxxxx = (double)☃xx + ☃xxxxx.field_72338_b - 0.1F;
         }

         if (☃ == EnumFacing.UP) {
            ☃xxxxxxx = (double)☃xx + ☃xxxxx.field_72337_e + 0.1F;
         }

         if (☃ == EnumFacing.NORTH) {
            ☃xxxxxxxx = (double)☃xxx + ☃xxxxx.field_72339_c - 0.1F;
         }

         if (☃ == EnumFacing.SOUTH) {
            ☃xxxxxxxx = (double)☃xxx + ☃xxxxx.field_72334_f + 0.1F;
         }

         if (☃ == EnumFacing.WEST) {
            ☃xxxxxx = (double)☃x + ☃xxxxx.field_72340_a - 0.1F;
         }

         if (☃ == EnumFacing.EAST) {
            ☃xxxxxx = (double)☃x + ☃xxxxx.field_72336_d + 0.1F;
         }

         this.func_78873_a(
            new ParticleDigging(this.field_78878_a, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, 0.0, 0.0, 0.0, ☃).func_174846_a(☃).func_70543_e(0.2F).func_70541_f(0.6F)
         );
      }
   }

   public String func_78869_b() {
      int ☃ = 0;

      for(int ☃x = 0; ☃x < 4; ++☃x) {
         for(int ☃xx = 0; ☃xx < 2; ++☃xx) {
            ☃ += this.field_78876_b[☃x][☃xx].size();
         }
      }

      return String.valueOf(☃);
   }
}
