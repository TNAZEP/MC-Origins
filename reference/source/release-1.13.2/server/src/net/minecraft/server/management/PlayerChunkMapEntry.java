package net.minecraft.server.management;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketUnloadChunk;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerChunkMapEntry {
   private static final Logger field_187281_a = LogManager.getLogger();
   private final PlayerChunkMap field_187282_b;
   private final List<EntityPlayerMP> field_187283_c = Lists.<EntityPlayerMP>newArrayList();
   private final ChunkPos field_187284_d;
   private final short[] field_187285_e = new short[64];
   @Nullable
   private Chunk field_187286_f;
   private int field_187287_g;
   private int field_187288_h;
   private long field_187289_i;
   private boolean field_187290_j;

   public PlayerChunkMapEntry(PlayerChunkMap var1, int var2, int var3) {
      this.field_187282_b = ☃;
      this.field_187284_d = new ChunkPos(☃, ☃);
      ChunkProviderServer ☃ = ☃.func_72688_a().func_72863_F();
      ☃.func_212469_a(☃, ☃);
      this.field_187286_f = ☃.func_186025_d(☃, ☃, true, false);
   }

   public ChunkPos func_187264_a() {
      return this.field_187284_d;
   }

   public void func_187276_a(EntityPlayerMP var1) {
      if (this.field_187283_c.contains(☃)) {
         field_187281_a.debug("Failed to add player. {} already is in chunk {}, {}", ☃, this.field_187284_d.field_77276_a, this.field_187284_d.field_77275_b);
      } else {
         if (this.field_187283_c.isEmpty()) {
            this.field_187289_i = this.field_187282_b.func_72688_a().func_82737_E();
         }

         this.field_187283_c.add(☃);
         if (this.field_187290_j) {
            this.func_187278_c(☃);
         }
      }
   }

   public void func_187277_b(EntityPlayerMP var1) {
      if (this.field_187283_c.contains(☃)) {
         if (this.field_187290_j) {
            ☃.field_71135_a.func_147359_a(new SPacketUnloadChunk(this.field_187284_d.field_77276_a, this.field_187284_d.field_77275_b));
         }

         this.field_187283_c.remove(☃);
         if (this.field_187283_c.isEmpty()) {
            this.field_187282_b.func_187305_b(this);
         }
      }
   }

   public boolean func_187268_a(boolean var1) {
      if (this.field_187286_f != null) {
         return true;
      } else {
         this.field_187286_f = this.field_187282_b
            .func_72688_a()
            .func_72863_F()
            .func_186025_d(this.field_187284_d.field_77276_a, this.field_187284_d.field_77275_b, true, ☃);
         return this.field_187286_f != null;
      }
   }

   public boolean func_187272_b() {
      if (this.field_187290_j) {
         return true;
      } else if (this.field_187286_f == null) {
         return false;
      } else if (!this.field_187286_f.func_150802_k()) {
         return false;
      } else {
         this.field_187287_g = 0;
         this.field_187288_h = 0;
         this.field_187290_j = true;
         if (!this.field_187283_c.isEmpty()) {
            Packet<?> ☃ = new SPacketChunkData(this.field_187286_f, 65535);

            for(EntityPlayerMP ☃x : this.field_187283_c) {
               ☃x.field_71135_a.func_147359_a(☃);
               this.field_187282_b.func_72688_a().func_73039_n().func_85172_a(☃x, this.field_187286_f);
            }
         }

         return true;
      }
   }

   public void func_187278_c(EntityPlayerMP var1) {
      if (this.field_187290_j) {
         ☃.field_71135_a.func_147359_a(new SPacketChunkData(this.field_187286_f, 65535));
         this.field_187282_b.func_72688_a().func_73039_n().func_85172_a(☃, this.field_187286_f);
      }
   }

   public void func_187279_c() {
      long ☃ = this.field_187282_b.func_72688_a().func_82737_E();
      if (this.field_187286_f != null) {
         this.field_187286_f.func_177415_c(this.field_187286_f.func_177416_w() + ☃ - this.field_187289_i);
      }

      this.field_187289_i = ☃;
   }

   public void func_187265_a(int var1, int var2, int var3) {
      if (this.field_187290_j) {
         if (this.field_187287_g == 0) {
            this.field_187282_b.func_187304_a(this);
         }

         this.field_187288_h |= 1 << (☃ >> 4);
         if (this.field_187287_g < 64) {
            short ☃ = (short)(☃ << 12 | ☃ << 8 | ☃);

            for(int ☃x = 0; ☃x < this.field_187287_g; ++☃x) {
               if (this.field_187285_e[☃x] == ☃) {
                  return;
               }
            }

            this.field_187285_e[this.field_187287_g++] = ☃;
         }
      }
   }

   public void func_187267_a(Packet<?> var1) {
      if (this.field_187290_j) {
         for(int ☃ = 0; ☃ < this.field_187283_c.size(); ++☃) {
            ((EntityPlayerMP)this.field_187283_c.get(☃)).field_71135_a.func_147359_a(☃);
         }
      }
   }

   public void func_187280_d() {
      if (this.field_187290_j && this.field_187286_f != null) {
         if (this.field_187287_g != 0) {
            if (this.field_187287_g == 1) {
               int ☃ = (this.field_187285_e[0] >> 12 & 15) + this.field_187284_d.field_77276_a * 16;
               int ☃x = this.field_187285_e[0] & 255;
               int ☃xx = (this.field_187285_e[0] >> 8 & 15) + this.field_187284_d.field_77275_b * 16;
               BlockPos ☃xxx = new BlockPos(☃, ☃x, ☃xx);
               this.func_187267_a(new SPacketBlockChange(this.field_187282_b.func_72688_a(), ☃xxx));
               if (this.field_187282_b.func_72688_a().func_180495_p(☃xxx).func_177230_c().func_149716_u()) {
                  this.func_187273_a(this.field_187282_b.func_72688_a().func_175625_s(☃xxx));
               }
            } else if (this.field_187287_g == 64) {
               this.func_187267_a(new SPacketChunkData(this.field_187286_f, this.field_187288_h));
            } else {
               this.func_187267_a(new SPacketMultiBlockChange(this.field_187287_g, this.field_187285_e, this.field_187286_f));

               for(int ☃ = 0; ☃ < this.field_187287_g; ++☃) {
                  int ☃x = (this.field_187285_e[☃] >> 12 & 15) + this.field_187284_d.field_77276_a * 16;
                  int ☃xx = this.field_187285_e[☃] & 255;
                  int ☃xxx = (this.field_187285_e[☃] >> 8 & 15) + this.field_187284_d.field_77275_b * 16;
                  BlockPos ☃xxxx = new BlockPos(☃x, ☃xx, ☃xxx);
                  if (this.field_187282_b.func_72688_a().func_180495_p(☃xxxx).func_177230_c().func_149716_u()) {
                     this.func_187273_a(this.field_187282_b.func_72688_a().func_175625_s(☃xxxx));
                  }
               }
            }

            this.field_187287_g = 0;
            this.field_187288_h = 0;
         }
      }
   }

   private void func_187273_a(@Nullable TileEntity var1) {
      if (☃ != null) {
         SPacketUpdateTileEntity ☃ = ☃.func_189518_D_();
         if (☃ != null) {
            this.func_187267_a(☃);
         }
      }
   }

   public boolean func_187275_d(EntityPlayerMP var1) {
      return this.field_187283_c.contains(☃);
   }

   public boolean func_187269_a(Predicate<EntityPlayerMP> var1) {
      return this.field_187283_c.stream().anyMatch(☃);
   }

   public boolean func_187271_a(double var1, Predicate<EntityPlayerMP> var3) {
      int ☃ = 0;

      for(int ☃x = this.field_187283_c.size(); ☃ < ☃x; ++☃) {
         EntityPlayerMP ☃xx = (EntityPlayerMP)this.field_187283_c.get(☃);
         if (☃.test(☃xx) && this.field_187284_d.func_185327_a(☃xx) < ☃ * ☃) {
            return true;
         }
      }

      return false;
   }

   public boolean func_187274_e() {
      return this.field_187290_j;
   }

   @Nullable
   public Chunk func_187266_f() {
      return this.field_187286_f;
   }

   public double func_187270_g() {
      double ☃ = Double.MAX_VALUE;

      for(EntityPlayerMP ☃x : this.field_187283_c) {
         double ☃xx = this.field_187284_d.func_185327_a(☃x);
         if (☃xx < ☃) {
            ☃ = ☃xx;
         }
      }

      return ☃;
   }
}
