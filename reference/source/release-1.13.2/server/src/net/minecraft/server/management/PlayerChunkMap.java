package net.minecraft.server.management;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;

public class PlayerChunkMap {
   private static final Predicate<EntityPlayerMP> field_187308_a = var0 -> var0 != null && !var0.func_175149_v();
   private static final Predicate<EntityPlayerMP> field_187309_b = var0 -> var0 != null
         && (!var0.func_175149_v() || var0.func_71121_q().func_82736_K().func_82766_b("spectatorsGenerateChunks"));
   private final WorldServer field_72701_a;
   private final List<EntityPlayerMP> field_72699_b = Lists.<EntityPlayerMP>newArrayList();
   private final Long2ObjectMap<PlayerChunkMapEntry> field_72700_c = new Long2ObjectOpenHashMap<>(4096);
   private final Set<PlayerChunkMapEntry> field_72697_d = Sets.<PlayerChunkMapEntry>newHashSet();
   private final List<PlayerChunkMapEntry> field_187310_g = Lists.<PlayerChunkMapEntry>newLinkedList();
   private final List<PlayerChunkMapEntry> field_187311_h = Lists.<PlayerChunkMapEntry>newLinkedList();
   private final List<PlayerChunkMapEntry> field_111193_e = Lists.<PlayerChunkMapEntry>newArrayList();
   private int field_72698_e;
   private long field_111192_g;
   private boolean field_187312_l = true;
   private boolean field_187313_m = true;

   public PlayerChunkMap(WorldServer var1) {
      this.field_72701_a = ☃;
      this.func_152622_a(☃.func_73046_m().func_184103_al().func_72395_o());
   }

   public WorldServer func_72688_a() {
      return this.field_72701_a;
   }

   public Iterator<Chunk> func_187300_b() {
      final Iterator<PlayerChunkMapEntry> ☃ = this.field_111193_e.iterator();
      return new AbstractIterator<Chunk>() {
         protected Chunk computeNext() {
            while(☃.hasNext()) {
               PlayerChunkMapEntry ☃ = (PlayerChunkMapEntry)☃.next();
               Chunk ☃x = ☃.func_187266_f();
               if (☃x != null) {
                  if (!☃x.func_186035_j()) {
                     return ☃x;
                  }

                  if (☃.func_187271_a(128.0, PlayerChunkMap.field_187308_a)) {
                     return ☃x;
                  }
               }
            }

            return this.endOfData();
         }
      };
   }

   public void func_72693_b() {
      long ☃ = this.field_72701_a.func_82737_E();
      if (☃ - this.field_111192_g > 8000L) {
         this.field_111192_g = ☃;

         for(int ☃x = 0; ☃x < this.field_111193_e.size(); ++☃x) {
            PlayerChunkMapEntry ☃xx = (PlayerChunkMapEntry)this.field_111193_e.get(☃x);
            ☃xx.func_187280_d();
            ☃xx.func_187279_c();
         }
      }

      if (!this.field_72697_d.isEmpty()) {
         for(PlayerChunkMapEntry ☃ : this.field_72697_d) {
            ☃.func_187280_d();
         }

         this.field_72697_d.clear();
      }

      if (this.field_187312_l && ☃ % 4L == 0L) {
         this.field_187312_l = false;
         Collections.sort(this.field_187311_h, (var0, var1x) -> ComparisonChain.start().compare(var0.func_187270_g(), var1x.func_187270_g()).result());
      }

      if (this.field_187313_m && ☃ % 4L == 2L) {
         this.field_187313_m = false;
         Collections.sort(this.field_187310_g, (var0, var1x) -> ComparisonChain.start().compare(var0.func_187270_g(), var1x.func_187270_g()).result());
      }

      if (!this.field_187311_h.isEmpty()) {
         long ☃ = Util.func_211178_c() + 50000000L;
         int ☃x = 49;
         Iterator<PlayerChunkMapEntry> ☃xx = this.field_187311_h.iterator();

         while(☃xx.hasNext()) {
            PlayerChunkMapEntry ☃xxx = (PlayerChunkMapEntry)☃xx.next();
            if (☃xxx.func_187266_f() == null) {
               boolean ☃xxxx = ☃xxx.func_187269_a(field_187309_b);
               if (☃xxx.func_187268_a(☃xxxx)) {
                  ☃xx.remove();
                  if (☃xxx.func_187272_b()) {
                     this.field_187310_g.remove(☃xxx);
                  }

                  if (--☃x < 0 || Util.func_211178_c() > ☃) {
                     break;
                  }
               }
            }
         }
      }

      if (!this.field_187310_g.isEmpty()) {
         int ☃ = 81;
         Iterator<PlayerChunkMapEntry> ☃x = this.field_187310_g.iterator();

         while(☃x.hasNext()) {
            PlayerChunkMapEntry ☃xx = (PlayerChunkMapEntry)☃x.next();
            if (☃xx.func_187272_b()) {
               ☃x.remove();
               if (--☃ < 0) {
                  break;
               }
            }
         }
      }

      if (this.field_72699_b.isEmpty()) {
         Dimension ☃ = this.field_72701_a.field_73011_w;
         if (!☃.func_76567_e()) {
            this.field_72701_a.func_72863_F().func_73240_a();
         }
      }
   }

   public boolean func_152621_a(int var1, int var2) {
      long ☃ = func_187307_d(☃, ☃);
      return this.field_72700_c.get(☃) != null;
   }

   @Nullable
   public PlayerChunkMapEntry func_187301_b(int var1, int var2) {
      return this.field_72700_c.get(func_187307_d(☃, ☃));
   }

   private PlayerChunkMapEntry func_187302_c(int var1, int var2) {
      long ☃ = func_187307_d(☃, ☃);
      PlayerChunkMapEntry ☃x = this.field_72700_c.get(☃);
      if (☃x == null) {
         ☃x = new PlayerChunkMapEntry(this, ☃, ☃);
         this.field_72700_c.put(☃, ☃x);
         this.field_111193_e.add(☃x);
         if (☃x.func_187266_f() == null) {
            this.field_187311_h.add(☃x);
         }

         if (!☃x.func_187272_b()) {
            this.field_187310_g.add(☃x);
         }
      }

      return ☃x;
   }

   public void func_180244_a(BlockPos var1) {
      int ☃ = ☃.func_177958_n() >> 4;
      int ☃x = ☃.func_177952_p() >> 4;
      PlayerChunkMapEntry ☃xx = this.func_187301_b(☃, ☃x);
      if (☃xx != null) {
         ☃xx.func_187265_a(☃.func_177958_n() & 15, ☃.func_177956_o(), ☃.func_177952_p() & 15);
      }
   }

   public void func_72683_a(EntityPlayerMP var1) {
      int ☃ = (int)☃.field_70165_t >> 4;
      int ☃x = (int)☃.field_70161_v >> 4;
      ☃.field_71131_d = ☃.field_70165_t;
      ☃.field_71132_e = ☃.field_70161_v;

      for(int ☃xx = ☃ - this.field_72698_e; ☃xx <= ☃ + this.field_72698_e; ++☃xx) {
         for(int ☃xxx = ☃x - this.field_72698_e; ☃xxx <= ☃x + this.field_72698_e; ++☃xxx) {
            this.func_187302_c(☃xx, ☃xxx).func_187276_a(☃);
         }
      }

      this.field_72699_b.add(☃);
      this.func_187306_e();
   }

   public void func_72695_c(EntityPlayerMP var1) {
      int ☃ = (int)☃.field_71131_d >> 4;
      int ☃x = (int)☃.field_71132_e >> 4;

      for(int ☃xx = ☃ - this.field_72698_e; ☃xx <= ☃ + this.field_72698_e; ++☃xx) {
         for(int ☃xxx = ☃x - this.field_72698_e; ☃xxx <= ☃x + this.field_72698_e; ++☃xxx) {
            PlayerChunkMapEntry ☃xxxx = this.func_187301_b(☃xx, ☃xxx);
            if (☃xxxx != null) {
               ☃xxxx.func_187277_b(☃);
            }
         }
      }

      this.field_72699_b.remove(☃);
      this.func_187306_e();
   }

   private boolean func_72684_a(int var1, int var2, int var3, int var4, int var5) {
      int ☃ = ☃ - ☃;
      int ☃x = ☃ - ☃;
      if (☃ < -☃ || ☃ > ☃) {
         return false;
      } else {
         return ☃x >= -☃ && ☃x <= ☃;
      }
   }

   public void func_72685_d(EntityPlayerMP var1) {
      int ☃ = (int)☃.field_70165_t >> 4;
      int ☃x = (int)☃.field_70161_v >> 4;
      double ☃xx = ☃.field_71131_d - ☃.field_70165_t;
      double ☃xxx = ☃.field_71132_e - ☃.field_70161_v;
      double ☃xxxx = ☃xx * ☃xx + ☃xxx * ☃xxx;
      if (!(☃xxxx < 64.0)) {
         int ☃xxxxx = (int)☃.field_71131_d >> 4;
         int ☃xxxxxx = (int)☃.field_71132_e >> 4;
         int ☃xxxxxxx = this.field_72698_e;
         int ☃xxxxxxxx = ☃ - ☃xxxxx;
         int ☃xxxxxxxxx = ☃x - ☃xxxxxx;
         if (☃xxxxxxxx != 0 || ☃xxxxxxxxx != 0) {
            for(int ☃xxxxxxxxxx = ☃ - ☃xxxxxxx; ☃xxxxxxxxxx <= ☃ + ☃xxxxxxx; ++☃xxxxxxxxxx) {
               for(int ☃xxxxxxxxxxx = ☃x - ☃xxxxxxx; ☃xxxxxxxxxxx <= ☃x + ☃xxxxxxx; ++☃xxxxxxxxxxx) {
                  if (!this.func_72684_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx)) {
                     this.func_187302_c(☃xxxxxxxxxx, ☃xxxxxxxxxxx).func_187276_a(☃);
                  }

                  if (!this.func_72684_a(☃xxxxxxxxxx - ☃xxxxxxxx, ☃xxxxxxxxxxx - ☃xxxxxxxxx, ☃, ☃x, ☃xxxxxxx)) {
                     PlayerChunkMapEntry ☃xxxxxxxxxxxx = this.func_187301_b(☃xxxxxxxxxx - ☃xxxxxxxx, ☃xxxxxxxxxxx - ☃xxxxxxxxx);
                     if (☃xxxxxxxxxxxx != null) {
                        ☃xxxxxxxxxxxx.func_187277_b(☃);
                     }
                  }
               }
            }

            ☃.field_71131_d = ☃.field_70165_t;
            ☃.field_71132_e = ☃.field_70161_v;
            this.func_187306_e();
         }
      }
   }

   public boolean func_72694_a(EntityPlayerMP var1, int var2, int var3) {
      PlayerChunkMapEntry ☃ = this.func_187301_b(☃, ☃);
      return ☃ != null && ☃.func_187275_d(☃) && ☃.func_187274_e();
   }

   public void func_152622_a(int var1) {
      ☃ = MathHelper.func_76125_a(☃, 3, 32);
      if (☃ != this.field_72698_e) {
         int ☃ = ☃ - this.field_72698_e;

         for(EntityPlayerMP ☃x : Lists.newArrayList(this.field_72699_b)) {
            int ☃xx = (int)☃x.field_70165_t >> 4;
            int ☃xxx = (int)☃x.field_70161_v >> 4;
            if (☃ > 0) {
               for(int ☃xxxx = ☃xx - ☃; ☃xxxx <= ☃xx + ☃; ++☃xxxx) {
                  for(int ☃xxxxx = ☃xxx - ☃; ☃xxxxx <= ☃xxx + ☃; ++☃xxxxx) {
                     PlayerChunkMapEntry ☃xxxxxx = this.func_187302_c(☃xxxx, ☃xxxxx);
                     if (!☃xxxxxx.func_187275_d(☃x)) {
                        ☃xxxxxx.func_187276_a(☃x);
                     }
                  }
               }
            } else {
               for(int ☃xx = ☃xx - this.field_72698_e; ☃xx <= ☃xx + this.field_72698_e; ++☃xx) {
                  for(int ☃xxx = ☃xxx - this.field_72698_e; ☃xxx <= ☃xxx + this.field_72698_e; ++☃xxx) {
                     if (!this.func_72684_a(☃xx, ☃xxx, ☃xx, ☃xxx, ☃)) {
                        this.func_187302_c(☃xx, ☃xxx).func_187277_b(☃x);
                     }
                  }
               }
            }
         }

         this.field_72698_e = ☃;
         this.func_187306_e();
      }
   }

   private void func_187306_e() {
      this.field_187312_l = true;
      this.field_187313_m = true;
   }

   public static int func_72686_a(int var0) {
      return ☃ * 16 - 16;
   }

   private static long func_187307_d(int var0, int var1) {
      return (long)☃ + 2147483647L | (long)☃ + 2147483647L << 32;
   }

   public void func_187304_a(PlayerChunkMapEntry var1) {
      this.field_72697_d.add(☃);
   }

   public void func_187305_b(PlayerChunkMapEntry var1) {
      ChunkPos ☃ = ☃.func_187264_a();
      long ☃x = func_187307_d(☃.field_77276_a, ☃.field_77275_b);
      ☃.func_187279_c();
      this.field_72700_c.remove(☃x);
      this.field_111193_e.remove(☃);
      this.field_72697_d.remove(☃);
      this.field_187310_g.remove(☃);
      this.field_187311_h.remove(☃);
      Chunk ☃xx = ☃.func_187266_f();
      if (☃xx != null) {
         this.func_72688_a().func_72863_F().func_189549_a(☃xx);
      }
   }
}
