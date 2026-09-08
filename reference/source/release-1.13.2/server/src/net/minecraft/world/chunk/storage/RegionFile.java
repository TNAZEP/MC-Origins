package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;
import net.minecraft.util.Util;

public class RegionFile {
   private static final byte[] field_76720_a = new byte[4096];
   private final File field_76718_b;
   private RandomAccessFile field_76719_c;
   private final int[] field_76716_d = new int[1024];
   private final int[] field_76717_e = new int[1024];
   private List<Boolean> field_76714_f;
   private int field_76715_g;
   private long field_76721_h;

   public RegionFile(File var1) {
      this.field_76718_b = ☃;
      this.field_76715_g = 0;

      try {
         if (☃.exists()) {
            this.field_76721_h = ☃.lastModified();
         }

         this.field_76719_c = new RandomAccessFile(☃, "rw");
         if (this.field_76719_c.length() < 4096L) {
            this.field_76719_c.write(field_76720_a);
            this.field_76719_c.write(field_76720_a);
            this.field_76715_g += 8192;
         }

         if ((this.field_76719_c.length() & 4095L) != 0L) {
            for(int ☃ = 0; (long)☃ < (this.field_76719_c.length() & 4095L); ++☃) {
               this.field_76719_c.write(0);
            }
         }

         int ☃ = (int)this.field_76719_c.length() / 4096;
         this.field_76714_f = Lists.newArrayListWithCapacity(☃);

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            this.field_76714_f.add(true);
         }

         this.field_76714_f.set(0, false);
         this.field_76714_f.set(1, false);
         this.field_76719_c.seek(0L);

         for(int ☃x = 0; ☃x < 1024; ++☃x) {
            int ☃xx = this.field_76719_c.readInt();
            this.field_76716_d[☃x] = ☃xx;
            if (☃xx != 0 && (☃xx >> 8) + (☃xx & 0xFF) <= this.field_76714_f.size()) {
               for(int ☃xxx = 0; ☃xxx < (☃xx & 0xFF); ++☃xxx) {
                  this.field_76714_f.set((☃xx >> 8) + ☃xxx, false);
               }
            }
         }

         for(int ☃x = 0; ☃x < 1024; ++☃x) {
            int ☃xx = this.field_76719_c.readInt();
            this.field_76717_e[☃x] = ☃xx;
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }
   }

   @Nullable
   public synchronized DataInputStream func_76704_a(int var1, int var2) {
      if (this.func_76705_d(☃, ☃)) {
         return null;
      } else {
         try {
            int ☃ = this.func_76707_e(☃, ☃);
            if (☃ == 0) {
               return null;
            } else {
               int ☃ = ☃ >> 8;
               int ☃x = ☃ & 0xFF;
               if (☃ + ☃x > this.field_76714_f.size()) {
                  return null;
               } else {
                  this.field_76719_c.seek((long)(☃ * 4096));
                  int ☃ = this.field_76719_c.readInt();
                  if (☃ > 4096 * ☃x) {
                     return null;
                  } else if (☃ <= 0) {
                     return null;
                  } else {
                     byte ☃ = this.field_76719_c.readByte();
                     if (☃ == 1) {
                        byte[] ☃x = new byte[☃ - 1];
                        this.field_76719_c.read(☃x);
                        return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(☃x))));
                     } else if (☃ == 2) {
                        byte[] ☃ = new byte[☃ - 1];
                        this.field_76719_c.read(☃);
                        return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(☃))));
                     } else {
                        return null;
                     }
                  }
               }
            }
         } catch (IOException var9) {
            return null;
         }
      }
   }

   public boolean func_212167_b(int var1, int var2) {
      if (this.func_76705_d(☃, ☃)) {
         return false;
      } else {
         int ☃ = this.func_76707_e(☃, ☃);
         if (☃ == 0) {
            return false;
         } else {
            int ☃ = ☃ >> 8;
            int ☃x = ☃ & 0xFF;
            if (☃ + ☃x > this.field_76714_f.size()) {
               return false;
            } else {
               try {
                  this.field_76719_c.seek((long)(☃ * 4096));
                  int ☃ = this.field_76719_c.readInt();
                  if (☃ > 4096 * ☃x) {
                     return false;
                  } else {
                     return ☃ > 0;
                  }
               } catch (IOException var7) {
                  return false;
               }
            }
         }
      }
   }

   @Nullable
   public DataOutputStream func_76710_b(int var1, int var2) {
      return this.func_76705_d(☃, ☃) ? null : new DataOutputStream(new BufferedOutputStream(new DeflaterOutputStream(new RegionFile.ChunkBuffer(☃, ☃))));
   }

   protected synchronized void func_76706_a(int var1, int var2, byte[] var3, int var4) {
      try {
         int ☃ = this.func_76707_e(☃, ☃);
         int ☃x = ☃ >> 8;
         int ☃xx = ☃ & 0xFF;
         int ☃xxx = (☃ + 5) / 4096 + 1;
         if (☃xxx >= 256) {
            return;
         }

         if (☃x != 0 && ☃xx == ☃xxx) {
            this.func_76712_a(☃x, ☃, ☃);
         } else {
            for(int ☃ = 0; ☃ < ☃xx; ++☃) {
               this.field_76714_f.set(☃x + ☃, true);
            }

            int ☃ = this.field_76714_f.indexOf(true);
            int ☃x = 0;
            if (☃ != -1) {
               for(int ☃xx = ☃; ☃xx < this.field_76714_f.size(); ++☃xx) {
                  if (☃x != 0) {
                     if (this.field_76714_f.get(☃xx)) {
                        ++☃x;
                     } else {
                        ☃x = 0;
                     }
                  } else if (this.field_76714_f.get(☃xx)) {
                     ☃ = ☃xx;
                     ☃x = 1;
                  }

                  if (☃x >= ☃xxx) {
                     break;
                  }
               }
            }

            if (☃x >= ☃xxx) {
               ☃x = ☃;
               this.func_76711_a(☃, ☃, ☃ << 8 | ☃xxx);

               for(int ☃ = 0; ☃ < ☃xxx; ++☃) {
                  this.field_76714_f.set(☃x + ☃, false);
               }

               this.func_76712_a(☃x, ☃, ☃);
            } else {
               this.field_76719_c.seek(this.field_76719_c.length());
               ☃x = this.field_76714_f.size();

               for(int ☃ = 0; ☃ < ☃xxx; ++☃) {
                  this.field_76719_c.write(field_76720_a);
                  this.field_76714_f.add(false);
               }

               this.field_76715_g += 4096 * ☃xxx;
               this.func_76712_a(☃x, ☃, ☃);
               this.func_76711_a(☃, ☃, ☃x << 8 | ☃xxx);
            }
         }

         this.func_76713_b(☃, ☃, (int)(Util.func_211179_d() / 1000L));
      } catch (IOException var12) {
         var12.printStackTrace();
      }
   }

   private void func_76712_a(int var1, byte[] var2, int var3) throws IOException {
      this.field_76719_c.seek((long)(☃ * 4096));
      this.field_76719_c.writeInt(☃ + 1);
      this.field_76719_c.writeByte(2);
      this.field_76719_c.write(☃, 0, ☃);
   }

   private boolean func_76705_d(int var1, int var2) {
      return ☃ < 0 || ☃ >= 32 || ☃ < 0 || ☃ >= 32;
   }

   private int func_76707_e(int var1, int var2) {
      return this.field_76716_d[☃ + ☃ * 32];
   }

   public boolean func_76709_c(int var1, int var2) {
      return this.func_76707_e(☃, ☃) != 0;
   }

   private void func_76711_a(int var1, int var2, int var3) throws IOException {
      this.field_76716_d[☃ + ☃ * 32] = ☃;
      this.field_76719_c.seek((long)((☃ + ☃ * 32) * 4));
      this.field_76719_c.writeInt(☃);
   }

   private void func_76713_b(int var1, int var2, int var3) throws IOException {
      this.field_76717_e[☃ + ☃ * 32] = ☃;
      this.field_76719_c.seek((long)(4096 + (☃ + ☃ * 32) * 4));
      this.field_76719_c.writeInt(☃);
   }

   public void func_76708_c() throws IOException {
      if (this.field_76719_c != null) {
         this.field_76719_c.close();
      }
   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private final int field_76722_b;
      private final int field_76723_c;

      public ChunkBuffer(int var2, int var3) {
         super(8096);
         this.field_76722_b = ☃;
         this.field_76723_c = ☃;
      }

      public void close() {
         RegionFile.this.func_76706_a(this.field_76722_b, this.field_76723_c, this.buf, this.count);
      }
   }
}
