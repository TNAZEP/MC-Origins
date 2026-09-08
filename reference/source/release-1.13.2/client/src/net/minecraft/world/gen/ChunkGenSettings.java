package net.minecraft.world.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ChunkGenSettings implements IChunkGenSettings {
   protected int field_202180_a = 32;
   protected int field_211732_b = 8;
   protected int field_202181_b = 32;
   protected int field_202182_c = 5;
   protected int field_202183_d = 32;
   protected int field_202184_e = 128;
   protected int field_202185_f = 3;
   protected int field_202186_g = 32;
   protected int field_211733_i = 8;
   protected int field_204027_h = 16;
   protected int field_211734_k = 8;
   protected int field_202187_h = 20;
   protected int field_211735_m = 11;
   protected int field_204749_j = 16;
   protected int field_211736_o = 8;
   protected int field_202188_i = 80;
   protected int field_211737_q = 20;
   protected IBlockState field_205536_l = Blocks.field_150348_b.func_176223_P();
   protected IBlockState field_205537_m = Blocks.field_150355_j.func_176223_P();

   @Override
   public int func_202173_a() {
      return this.field_202180_a;
   }

   @Override
   public int func_211729_b() {
      return this.field_211732_b;
   }

   @Override
   public int func_202174_b() {
      return this.field_202181_b;
   }

   @Override
   public int func_202171_c() {
      return this.field_202182_c;
   }

   @Override
   public int func_202172_d() {
      return this.field_202183_d;
   }

   @Override
   public int func_202176_e() {
      return this.field_202184_e;
   }

   @Override
   public int func_202175_f() {
      return this.field_202185_f;
   }

   @Override
   public int func_202177_g() {
      return this.field_202186_g;
   }

   @Override
   public int func_211731_i() {
      return this.field_211733_i;
   }

   @Override
   public int func_204748_h() {
      return this.field_204749_j;
   }

   @Override
   public int func_211730_k() {
      return this.field_211736_o;
   }

   @Override
   public int func_204026_h() {
      return this.field_204027_h;
   }

   @Override
   public int func_211727_m() {
      return this.field_211734_k;
   }

   @Override
   public int func_202178_h() {
      return this.field_202187_h;
   }

   @Override
   public int func_211728_o() {
      return this.field_211735_m;
   }

   @Override
   public int func_202179_i() {
      return this.field_202188_i;
   }

   @Override
   public int func_211726_q() {
      return this.field_211737_q;
   }

   @Override
   public IBlockState func_205532_l() {
      return this.field_205536_l;
   }

   @Override
   public IBlockState func_205533_m() {
      return this.field_205537_m;
   }

   public void func_205535_a(IBlockState var1) {
      this.field_205536_l = ☃;
   }

   public void func_205534_b(IBlockState var1) {
      this.field_205537_m = ☃;
   }
}
