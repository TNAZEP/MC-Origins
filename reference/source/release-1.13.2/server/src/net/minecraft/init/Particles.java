package net.minecraft.init;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class Particles {
   public static final BasicParticleType field_197608_a;
   public static final BasicParticleType field_197609_b;
   public static final BasicParticleType field_197610_c;
   public static final ParticleType<BlockParticleData> field_197611_d;
   public static final BasicParticleType field_197612_e;
   public static final BasicParticleType field_203220_f;
   public static final BasicParticleType field_197613_f;
   public static final BasicParticleType field_197614_g;
   public static final BasicParticleType field_197615_h;
   public static final BasicParticleType field_197616_i;
   public static final BasicParticleType field_197617_j;
   public static final BasicParticleType field_197618_k;
   public static final ParticleType<RedstoneParticleData> field_197619_l;
   public static final BasicParticleType field_197620_m;
   public static final BasicParticleType field_197621_n;
   public static final BasicParticleType field_197622_o;
   public static final BasicParticleType field_197623_p;
   public static final BasicParticleType field_197624_q;
   public static final BasicParticleType field_197625_r;
   public static final BasicParticleType field_197626_s;
   public static final BasicParticleType field_197627_t;
   public static final ParticleType<BlockParticleData> field_197628_u;
   public static final BasicParticleType field_197629_v;
   public static final BasicParticleType field_197630_w;
   public static final BasicParticleType field_197631_x;
   public static final BasicParticleType field_197632_y;
   public static final BasicParticleType field_197633_z;
   public static final BasicParticleType field_197590_A;
   public static final ParticleType<ItemParticleData> field_197591_B;
   public static final BasicParticleType field_197592_C;
   public static final BasicParticleType field_197593_D;
   public static final BasicParticleType field_197594_E;
   public static final BasicParticleType field_197595_F;
   public static final BasicParticleType field_197596_G;
   public static final BasicParticleType field_197597_H;
   public static final BasicParticleType field_197598_I;
   public static final BasicParticleType field_197599_J;
   public static final BasicParticleType field_197600_K;
   public static final BasicParticleType field_197601_L;
   public static final BasicParticleType field_197602_M;
   public static final BasicParticleType field_197603_N;
   public static final BasicParticleType field_197604_O;
   public static final BasicParticleType field_197605_P;
   public static final BasicParticleType field_197606_Q;
   public static final BasicParticleType field_197607_R;
   public static final BasicParticleType field_203217_T;
   public static final BasicParticleType field_203218_U;
   public static final BasicParticleType field_203219_V;
   public static final BasicParticleType field_205167_W;
   public static final BasicParticleType field_206864_X;

   private static <T extends ParticleType<?>> T func_197589_a(String var0) {
      T ☃ = (T)IRegistry.field_212632_u.func_212608_b(new ResourceLocation(☃));
      if (☃ == null) {
         throw new IllegalStateException("Invalid or unknown particle type: " + ☃);
      } else {
         return ☃;
      }
   }

   static {
      if (!Bootstrap.func_179869_a()) {
         throw new RuntimeException("Accessed particles before Bootstrap!");
      } else {
         field_197608_a = func_197589_a("ambient_entity_effect");
         field_197609_b = func_197589_a("angry_villager");
         field_197610_c = func_197589_a("barrier");
         field_197611_d = func_197589_a("block");
         field_197612_e = func_197589_a("bubble");
         field_203220_f = func_197589_a("bubble_column_up");
         field_197613_f = func_197589_a("cloud");
         field_197614_g = func_197589_a("crit");
         field_197615_h = func_197589_a("damage_indicator");
         field_197616_i = func_197589_a("dragon_breath");
         field_197617_j = func_197589_a("dripping_lava");
         field_197618_k = func_197589_a("dripping_water");
         field_197619_l = func_197589_a("dust");
         field_197620_m = func_197589_a("effect");
         field_197621_n = func_197589_a("elder_guardian");
         field_197622_o = func_197589_a("enchanted_hit");
         field_197623_p = func_197589_a("enchant");
         field_197624_q = func_197589_a("end_rod");
         field_197625_r = func_197589_a("entity_effect");
         field_197626_s = func_197589_a("explosion_emitter");
         field_197627_t = func_197589_a("explosion");
         field_197628_u = func_197589_a("falling_dust");
         field_197629_v = func_197589_a("firework");
         field_197630_w = func_197589_a("fishing");
         field_197631_x = func_197589_a("flame");
         field_197632_y = func_197589_a("happy_villager");
         field_197633_z = func_197589_a("heart");
         field_197590_A = func_197589_a("instant_effect");
         field_197591_B = func_197589_a("item");
         field_197592_C = func_197589_a("item_slime");
         field_197593_D = func_197589_a("item_snowball");
         field_197594_E = func_197589_a("large_smoke");
         field_197595_F = func_197589_a("lava");
         field_197596_G = func_197589_a("mycelium");
         field_197597_H = func_197589_a("note");
         field_197598_I = func_197589_a("poof");
         field_197599_J = func_197589_a("portal");
         field_197600_K = func_197589_a("rain");
         field_197601_L = func_197589_a("smoke");
         field_197602_M = func_197589_a("spit");
         field_197603_N = func_197589_a("sweep_attack");
         field_197604_O = func_197589_a("totem_of_undying");
         field_197605_P = func_197589_a("underwater");
         field_197606_Q = func_197589_a("splash");
         field_197607_R = func_197589_a("witch");
         field_203217_T = func_197589_a("bubble_pop");
         field_203218_U = func_197589_a("current_down");
         field_203219_V = func_197589_a("squid_ink");
         field_205167_W = func_197589_a("nautilus");
         field_206864_X = func_197589_a("dolphin");
      }
   }
}
