package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class FossilsFeature extends Feature<NoFeatureConfig> {
   private static final ResourceLocation field_189890_a = new ResourceLocation("fossil/spine_1");
   private static final ResourceLocation field_189891_b = new ResourceLocation("fossil/spine_2");
   private static final ResourceLocation field_189892_c = new ResourceLocation("fossil/spine_3");
   private static final ResourceLocation field_189893_d = new ResourceLocation("fossil/spine_4");
   private static final ResourceLocation field_189894_e = new ResourceLocation("fossil/spine_1_coal");
   private static final ResourceLocation field_189895_f = new ResourceLocation("fossil/spine_2_coal");
   private static final ResourceLocation field_189896_g = new ResourceLocation("fossil/spine_3_coal");
   private static final ResourceLocation field_189897_h = new ResourceLocation("fossil/spine_4_coal");
   private static final ResourceLocation field_189898_i = new ResourceLocation("fossil/skull_1");
   private static final ResourceLocation field_189899_j = new ResourceLocation("fossil/skull_2");
   private static final ResourceLocation field_189900_k = new ResourceLocation("fossil/skull_3");
   private static final ResourceLocation field_189901_l = new ResourceLocation("fossil/skull_4");
   private static final ResourceLocation field_189902_m = new ResourceLocation("fossil/skull_1_coal");
   private static final ResourceLocation field_189903_n = new ResourceLocation("fossil/skull_2_coal");
   private static final ResourceLocation field_189904_o = new ResourceLocation("fossil/skull_3_coal");
   private static final ResourceLocation field_189905_p = new ResourceLocation("fossil/skull_4_coal");
   private static final ResourceLocation[] field_189906_q = new ResourceLocation[]{
      field_189890_a, field_189891_b, field_189892_c, field_189893_d, field_189898_i, field_189899_j, field_189900_k, field_189901_l
   };
   private static final ResourceLocation[] field_189907_r = new ResourceLocation[]{
      field_189894_e, field_189895_f, field_189896_g, field_189897_h, field_189902_m, field_189903_n, field_189904_o, field_189905_p
   };

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      Random ☃ = ☃.func_201674_k();
      Rotation[] ☃x = Rotation.values();
      Rotation ☃xx = ☃x[☃.nextInt(☃x.length)];
      int ☃xxx = ☃.nextInt(field_189906_q.length);
      TemplateManager ☃xxxx = ☃.func_72860_G().func_186340_h();
      Template ☃xxxxx = ☃xxxx.func_200220_a(field_189906_q[☃xxx]);
      Template ☃xxxxxx = ☃xxxx.func_200220_a(field_189907_r[☃xxx]);
      ChunkPos ☃xxxxxxx = new ChunkPos(☃);
      MutableBoundingBox ☃xxxxxxxx = new MutableBoundingBox(
         ☃xxxxxxx.func_180334_c(), 0, ☃xxxxxxx.func_180333_d(), ☃xxxxxxx.func_180332_e(), 256, ☃xxxxxxx.func_180330_f()
      );
      PlacementSettings ☃xxxxxxxxx = new PlacementSettings().func_186220_a(☃xx).func_186223_a(☃xxxxxxxx).func_189950_a(☃);
      BlockPos ☃xxxxxxxxxx = ☃xxxxx.func_186257_a(☃xx);
      int ☃xxxxxxxxxxx = ☃.nextInt(16 - ☃xxxxxxxxxx.func_177958_n());
      int ☃xxxxxxxxxxxx = ☃.nextInt(16 - ☃xxxxxxxxxx.func_177952_p());
      int ☃xxxxxxxxxxxxx = 256;

      for(int ☃xxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxx < ☃xxxxxxxxxx.func_177958_n(); ++☃xxxxxxxxxxxxxx) {
         for(int ☃xxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxx < ☃xxxxxxxxxx.func_177958_n(); ++☃xxxxxxxxxxxxxxx) {
            ☃xxxxxxxxxxxxx = Math.min(
               ☃xxxxxxxxxxxxx,
               ☃.func_201676_a(
                  Heightmap.Type.OCEAN_FLOOR_WG, ☃.func_177958_n() + ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxx, ☃.func_177952_p() + ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxx
               )
            );
         }
      }

      int ☃xxxxxxxxxxxxxx = Math.max(☃xxxxxxxxxxxxx - 15 - ☃.nextInt(10), 10);
      BlockPos ☃xxxxxxxxxxxxxxx = ☃xxxxx.func_189961_a(☃.func_177982_a(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx), Mirror.NONE, ☃xx);
      ☃xxxxxxxxx.func_189946_a(0.9F);
      ☃xxxxx.func_189962_a(☃, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxx, 4);
      ☃xxxxxxxxx.func_189946_a(0.1F);
      ☃xxxxxx.func_189962_a(☃, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxx, 4);
      return true;
   }
}
