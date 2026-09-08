package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.biome.Biome;

public abstract class StructureStart {
   protected final List<StructurePiece> field_75075_a = Lists.<StructurePiece>newArrayList();
   protected MutableBoundingBox field_75074_b;
   protected int field_143024_c;
   protected int field_143023_d;
   private Biome field_202505_e;
   private int field_212688_f;

   public StructureStart() {
   }

   public StructureStart(int var1, int var2, Biome var3, SharedSeedRandom var4, long var5) {
      this.field_143024_c = ☃;
      this.field_143023_d = ☃;
      this.field_202505_e = ☃;
      ☃.func_202425_c(☃, this.field_143024_c, this.field_143023_d);
   }

   public MutableBoundingBox func_75071_a() {
      return this.field_75074_b;
   }

   public List<StructurePiece> func_186161_c() {
      return this.field_75075_a;
   }

   public void func_75068_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
      synchronized(this.field_75075_a) {
         Iterator<StructurePiece> ☃ = this.field_75075_a.iterator();

         while(☃.hasNext()) {
            StructurePiece ☃x = (StructurePiece)☃.next();
            if (☃x.func_74874_b().func_78884_a(☃) && !☃x.func_74875_a(☃, ☃, ☃, ☃)) {
               ☃.remove();
            }
         }

         this.func_202500_a(☃);
      }
   }

   protected void func_202500_a(IBlockReader var1) {
      this.field_75074_b = MutableBoundingBox.func_78887_a();

      for(StructurePiece ☃ : this.field_75075_a) {
         this.field_75074_b.func_78888_b(☃.func_74874_b());
      }
   }

   public NBTTagCompound func_143021_a(int var1, int var2) {
      NBTTagCompound ☃ = new NBTTagCompound();
      if (this.func_75069_d()) {
         ☃.func_74778_a("id", StructureIO.func_143033_a(this));
         ☃.func_74778_a("biome", IRegistry.field_212624_m.func_177774_c(this.field_202505_e).toString());
         ☃.func_74768_a("ChunkX", ☃);
         ☃.func_74768_a("ChunkZ", ☃);
         ☃.func_74768_a("references", this.field_212688_f);
         ☃.func_74782_a("BB", this.field_75074_b.func_151535_h());
         NBTTagList var4 = new NBTTagList();
         synchronized(this.field_75075_a) {
            for(StructurePiece ☃x : this.field_75075_a) {
               var4.add((INBTBase)☃x.func_143010_b());
            }
         }

         ☃.func_74782_a("Children", var4);
         this.func_143022_a(☃);
         return ☃;
      } else {
         ☃.func_74778_a("id", "INVALID");
         return ☃;
      }
   }

   public void func_143022_a(NBTTagCompound var1) {
   }

   public void func_143020_a(IWorld var1, NBTTagCompound var2) {
      this.field_143024_c = ☃.func_74762_e("ChunkX");
      this.field_143023_d = ☃.func_74762_e("ChunkZ");
      this.field_212688_f = ☃.func_74762_e("references");
      this.field_202505_e = ☃.func_74764_b("biome")
         ? IRegistry.field_212624_m.func_212608_b(new ResourceLocation(☃.func_74779_i("biome")))
         : ☃.func_72863_F()
            .func_201711_g()
            .func_202090_b()
            .func_180300_a(new BlockPos((this.field_143024_c << 4) + 9, 0, (this.field_143023_d << 4) + 9), Biomes.field_76772_c);
      if (☃.func_74764_b("BB")) {
         this.field_75074_b = new MutableBoundingBox(☃.func_74759_k("BB"));
      }

      NBTTagList ☃ = ☃.func_150295_c("Children", 10);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         this.field_75075_a.add(StructureIO.func_143032_b(☃.func_150305_b(☃x), ☃));
      }

      this.func_143017_b(☃);
   }

   public void func_143017_b(NBTTagCompound var1) {
   }

   protected void func_75067_a(IWorldReaderBase var1, Random var2, int var3) {
      int ☃ = ☃.func_181545_F() - ☃;
      int ☃x = this.field_75074_b.func_78882_c() + 1;
      if (☃x < ☃) {
         ☃x += ☃.nextInt(☃ - ☃x);
      }

      int ☃ = ☃x - this.field_75074_b.field_78894_e;
      this.field_75074_b.func_78886_a(0, ☃, 0);

      for(StructurePiece ☃x : this.field_75075_a) {
         ☃x.func_181138_a(0, ☃, 0);
      }
   }

   protected void func_75070_a(IBlockReader var1, Random var2, int var3, int var4) {
      int ☃x = ☃ - ☃ + 1 - this.field_75074_b.func_78882_c();
      int ☃;
      if (☃x > 1) {
         ☃ = ☃ + ☃.nextInt(☃x);
      } else {
         ☃ = ☃;
      }

      int ☃ = ☃ - this.field_75074_b.field_78895_b;
      this.field_75074_b.func_78886_a(0, ☃, 0);

      for(StructurePiece ☃x : this.field_75075_a) {
         ☃x.func_181138_a(0, ☃, 0);
      }
   }

   public boolean func_75069_d() {
      return true;
   }

   public void func_175787_b(ChunkPos var1) {
   }

   public int func_143019_e() {
      return this.field_143024_c;
   }

   public int func_143018_f() {
      return this.field_143023_d;
   }

   public BlockPos func_204294_a() {
      return new BlockPos(this.field_143024_c << 4, 0, this.field_143023_d << 4);
   }

   public boolean func_212687_g() {
      return this.field_212688_f < this.func_212686_i();
   }

   public void func_212685_h() {
      ++this.field_212688_f;
   }

   protected int func_212686_i() {
      return 1;
   }
}
