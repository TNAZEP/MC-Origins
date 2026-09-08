package net.minecraft.world.storage;

import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveHandler implements ISaveHandler, IPlayerFileData {
   private static final Logger field_151478_a = LogManager.getLogger();
   private final File field_75770_b;
   private final File field_75771_c;
   private final long field_75769_e = Util.func_211177_b();
   private final String field_75767_f;
   private final TemplateManager field_186342_h;
   protected final DataFixer field_186341_a;

   public SaveHandler(File var1, String var2, @Nullable MinecraftServer var3, DataFixer var4) {
      this.field_186341_a = ☃;
      this.field_75770_b = new File(☃, ☃);
      this.field_75770_b.mkdirs();
      this.field_75771_c = new File(this.field_75770_b, "playerdata");
      this.field_75767_f = ☃;
      if (☃ != null) {
         this.field_75771_c.mkdirs();
         this.field_186342_h = new TemplateManager(☃, this.field_75770_b, ☃);
      } else {
         this.field_186342_h = null;
      }

      this.func_75766_h();
   }

   private void func_75766_h() {
      try {
         File ☃ = new File(this.field_75770_b, "session.lock");
         DataOutputStream ☃x = new DataOutputStream(new FileOutputStream(☃));

         try {
            ☃x.writeLong(this.field_75769_e);
         } finally {
            ☃x.close();
         }
      } catch (IOException var7) {
         var7.printStackTrace();
         throw new RuntimeException("Failed to check session lock, aborting");
      }
   }

   @Override
   public File func_75765_b() {
      return this.field_75770_b;
   }

   @Override
   public void func_75762_c() throws SessionLockException {
      try {
         File ☃ = new File(this.field_75770_b, "session.lock");
         DataInputStream ☃x = new DataInputStream(new FileInputStream(☃));

         try {
            if (☃x.readLong() != this.field_75769_e) {
               throw new SessionLockException("The save is being accessed from another location, aborting");
            }
         } finally {
            ☃x.close();
         }
      } catch (IOException var7) {
         throw new SessionLockException("Failed to check session lock, aborting");
      }
   }

   @Override
   public IChunkLoader func_75763_a(Dimension var1) {
      throw new RuntimeException("Old Chunk Storage is no longer supported.");
   }

   @Nullable
   @Override
   public WorldInfo func_75757_d() {
      File ☃ = new File(this.field_75770_b, "level.dat");
      if (☃.exists()) {
         WorldInfo ☃x = SaveFormatOld.func_186353_a(☃, this.field_186341_a);
         if (☃x != null) {
            return ☃x;
         }
      }

      ☃ = new File(this.field_75770_b, "level.dat_old");
      return ☃.exists() ? SaveFormatOld.func_186353_a(☃, this.field_186341_a) : null;
   }

   @Override
   public void func_75755_a(WorldInfo var1, @Nullable NBTTagCompound var2) {
      NBTTagCompound ☃ = ☃.func_76082_a(☃);
      NBTTagCompound ☃x = new NBTTagCompound();
      ☃x.func_74782_a("Data", ☃);

      try {
         File ☃xx = new File(this.field_75770_b, "level.dat_new");
         File ☃xxx = new File(this.field_75770_b, "level.dat_old");
         File ☃xxxx = new File(this.field_75770_b, "level.dat");
         CompressedStreamTools.func_74799_a(☃x, new FileOutputStream(☃xx));
         if (☃xxx.exists()) {
            ☃xxx.delete();
         }

         ☃xxxx.renameTo(☃xxx);
         if (☃xxxx.exists()) {
            ☃xxxx.delete();
         }

         ☃xx.renameTo(☃xxxx);
         if (☃xx.exists()) {
            ☃xx.delete();
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }
   }

   @Override
   public void func_75761_a(WorldInfo var1) {
      this.func_75755_a(☃, null);
   }

   @Override
   public void func_75753_a(EntityPlayer var1) {
      try {
         NBTTagCompound ☃ = ☃.func_189511_e(new NBTTagCompound());
         File ☃x = new File(this.field_75771_c, ☃.func_189512_bd() + ".dat.tmp");
         File ☃xx = new File(this.field_75771_c, ☃.func_189512_bd() + ".dat");
         CompressedStreamTools.func_74799_a(☃, new FileOutputStream(☃x));
         if (☃xx.exists()) {
            ☃xx.delete();
         }

         ☃x.renameTo(☃xx);
      } catch (Exception var5) {
         field_151478_a.warn("Failed to save player data for {}", ☃.func_200200_C_().getString());
      }
   }

   @Nullable
   @Override
   public NBTTagCompound func_75752_b(EntityPlayer var1) {
      NBTTagCompound ☃ = null;

      try {
         File ☃x = new File(this.field_75771_c, ☃.func_189512_bd() + ".dat");
         if (☃x.exists() && ☃x.isFile()) {
            ☃ = CompressedStreamTools.func_74796_a(new FileInputStream(☃x));
         }
      } catch (Exception var4) {
         field_151478_a.warn("Failed to load player data for {}", ☃.func_200200_C_().getString());
      }

      if (☃ != null) {
         int ☃x = ☃.func_150297_b("DataVersion", 3) ? ☃.func_74762_e("DataVersion") : -1;
         ☃.func_70020_e(NBTUtil.func_210822_a(this.field_186341_a, DataFixTypes.PLAYER, ☃, ☃x));
      }

      return ☃;
   }

   @Override
   public IPlayerFileData func_75756_e() {
      return this;
   }

   @Override
   public String[] func_75754_f() {
      String[] ☃ = this.field_75771_c.list();
      if (☃ == null) {
         ☃ = new String[0];
      }

      for(int ☃ = 0; ☃ < ☃.length; ++☃) {
         if (☃[☃].endsWith(".dat")) {
            ☃[☃] = ☃[☃].substring(0, ☃[☃].length() - 4);
         }
      }

      return ☃;
   }

   @Override
   public void func_75759_a() {
   }

   @Override
   public File func_212423_a(DimensionType var1, String var2) {
      File ☃ = new File(☃.func_212679_a(this.field_75770_b), "data");
      ☃.mkdirs();
      return new File(☃, ☃ + ".dat");
   }

   @Override
   public TemplateManager func_186340_h() {
      return this.field_186342_h;
   }

   @Override
   public DataFixer func_197718_i() {
      return this.field_186341_a;
   }
}
