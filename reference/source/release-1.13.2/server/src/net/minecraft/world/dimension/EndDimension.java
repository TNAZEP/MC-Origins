package net.minecraft.world.dimension;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.EndGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class EndDimension extends Dimension {
   public static final BlockPos field_209958_g = new BlockPos(100, 50, 0);
   private DragonFightManager field_186064_g;

   @Override
   public void func_76572_b() {
      NBTTagCompound ☃ = this.field_76579_a.func_72912_H().func_186347_a(DimensionType.THE_END);
      this.field_186064_g = this.field_76579_a instanceof WorldServer
         ? new DragonFightManager((WorldServer)this.field_76579_a, ☃.func_74775_l("DragonFight"))
         : null;
      this.field_191067_f = false;
   }

   @Override
   public IChunkGenerator<?> func_186060_c() {
      EndGenSettings ☃ = ChunkGeneratorType.field_206913_d.func_205483_a();
      ☃.func_205535_a(Blocks.field_150377_bs.func_176223_P());
      ☃.func_205534_b(Blocks.field_150350_a.func_176223_P());
      ☃.func_205538_a(this.func_177496_h());
      return ChunkGeneratorType.field_206913_d
         .create(
            this.field_76579_a,
            BiomeProviderType.field_205463_e.func_205457_a(BiomeProviderType.field_205463_e.func_205458_a().func_205446_a(this.field_76579_a.func_72905_C())),
            ☃
         );
   }

   @Override
   public float func_76563_a(long var1, float var3) {
      return 0.0F;
   }

   @Override
   public boolean func_76567_e() {
      return false;
   }

   @Override
   public boolean func_76569_d() {
      return false;
   }

   @Nullable
   @Override
   public BlockPos func_206920_a(ChunkPos var1, boolean var2) {
      Random ☃ = new Random(this.field_76579_a.func_72905_C());
      BlockPos ☃x = new BlockPos(☃.func_180334_c() + ☃.nextInt(15), 0, ☃.func_180330_f() + ☃.nextInt(15));
      return this.field_76579_a.func_184141_c(☃x).func_185904_a().func_76230_c() ? ☃x : null;
   }

   @Override
   public BlockPos func_177496_h() {
      return field_209958_g;
   }

   @Nullable
   @Override
   public BlockPos func_206921_a(int var1, int var2, boolean var3) {
      return this.func_206920_a(new ChunkPos(☃ >> 4, ☃ >> 4), ☃);
   }

   @Override
   public DimensionType func_186058_p() {
      return DimensionType.THE_END;
   }

   @Override
   public void func_186057_q() {
      NBTTagCompound ☃ = new NBTTagCompound();
      if (this.field_186064_g != null) {
         ☃.func_74782_a("DragonFight", this.field_186064_g.func_186088_a());
      }

      this.field_76579_a.func_72912_H().func_186345_a(DimensionType.THE_END, ☃);
   }

   @Override
   public void func_186059_r() {
      if (this.field_186064_g != null) {
         this.field_186064_g.func_186105_b();
      }
   }

   @Nullable
   public DragonFightManager func_186063_s() {
      return this.field_186064_g;
   }
}
