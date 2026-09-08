package net.minecraft.world.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixTypes;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimensionSavedDataManager {
   private static final Logger field_212776_a = LogManager.getLogger();
   private final DimensionType field_212777_b;
   private Map<String, WorldSavedData> field_212778_c = Maps.newHashMap();
   private final Object2IntMap<String> field_212779_d = new Object2IntOpenHashMap();
   @Nullable
   private final ISaveHandler field_212780_e;

   public DimensionSavedDataManager(DimensionType var1, @Nullable ISaveHandler var2) {
      this.field_212777_b = ☃;
      this.field_212780_e = ☃;
      this.field_212779_d.defaultReturnValue(-1);
   }

   @Nullable
   public <T extends WorldSavedData> T func_201067_a(Function<String, T> var1, String var2) {
      WorldSavedData ☃ = (WorldSavedData)this.field_212778_c.get(☃);
      if (☃ == null && this.field_212780_e != null) {
         try {
            File ☃x = this.field_212780_e.func_212423_a(this.field_212777_b, ☃);
            if (☃x != null && ☃x.exists()) {
               ☃ = (WorldSavedData)☃.apply(☃);
               ☃.func_76184_a(func_212774_a(this.field_212780_e, this.field_212777_b, ☃, 1631).func_74775_l("data"));
               this.field_212778_c.put(☃, ☃);
            }
         } catch (Exception var5) {
            field_212776_a.error("Error loading saved data: {}", ☃, var5);
         }
      }

      return (T)☃;
   }

   public void func_75745_a(String var1, WorldSavedData var2) {
      this.field_212778_c.put(☃, ☃);
   }

   public void func_75746_b() {
      try {
         this.field_212779_d.clear();
         if (this.field_212780_e == null) {
            return;
         }

         File ☃ = this.field_212780_e.func_212423_a(this.field_212777_b, "idcounts");
         if (☃ != null && ☃.exists()) {
            DataInputStream ☃x = new DataInputStream(new FileInputStream(☃));
            NBTTagCompound ☃xx = CompressedStreamTools.func_74794_a(☃x);
            ☃x.close();

            for(String ☃xxx : ☃xx.func_150296_c()) {
               if (☃xx.func_150297_b(☃xxx, 99)) {
                  this.field_212779_d.put(☃xxx, ☃xx.func_74762_e(☃xxx));
               }
            }
         }
      } catch (Exception var6) {
         field_212776_a.error("Could not load aux values", var6);
      }
   }

   public int func_75743_a(String var1) {
      int ☃ = this.field_212779_d.getInt(☃) + 1;
      this.field_212779_d.put(☃, ☃);
      if (this.field_212780_e == null) {
         return ☃;
      } else {
         try {
            File ☃ = this.field_212780_e.func_212423_a(this.field_212777_b, "idcounts");
            if (☃ != null) {
               NBTTagCompound ☃x = new NBTTagCompound();

               for(Entry<String> ☃xx : this.field_212779_d.object2IntEntrySet()) {
                  ☃x.func_74768_a((String)☃xx.getKey(), ☃xx.getIntValue());
               }

               DataOutputStream ☃xx = new DataOutputStream(new FileOutputStream(☃));
               CompressedStreamTools.func_74800_a(☃x, ☃xx);
               ☃xx.close();
            }
         } catch (Exception var7) {
            field_212776_a.error("Could not get free aux value {}", ☃, var7);
         }

         return ☃;
      }
   }

   public static NBTTagCompound func_212774_a(ISaveHandler var0, DimensionType var1, String var2, int var3) throws IOException {
      File ☃ = ☃.func_212423_a(☃, ☃);
      FileInputStream ☃x = new FileInputStream(☃);
      Throwable var6 = null;

      NBTTagCompound var9;
      try {
         NBTTagCompound ☃xx = CompressedStreamTools.func_74796_a(☃x);
         int ☃xxx = ☃xx.func_150297_b("DataVersion", 99) ? ☃xx.func_74762_e("DataVersion") : 1343;
         var9 = NBTUtil.func_210821_a(☃.func_197718_i(), DataFixTypes.SAVED_DATA, ☃xx, ☃xxx, ☃);
      } catch (Throwable var18) {
         var6 = var18;
         throw var18;
      } finally {
         if (☃x != null) {
            if (var6 != null) {
               try {
                  ☃x.close();
               } catch (Throwable var17) {
                  var6.addSuppressed(var17);
               }
            } else {
               ☃x.close();
            }
         }
      }

      return var9;
   }

   public void func_212775_b() {
      if (this.field_212780_e != null) {
         for(WorldSavedData ☃ : this.field_212778_c.values()) {
            if (☃.func_76188_b()) {
               this.func_75747_a(☃);
               ☃.func_76186_a(false);
            }
         }
      }
   }

   private void func_75747_a(WorldSavedData var1) {
      if (this.field_212780_e != null) {
         try {
            File ☃ = this.field_212780_e.func_212423_a(this.field_212777_b, ☃.func_195925_e());
            if (☃ != null) {
               NBTTagCompound ☃x = new NBTTagCompound();
               ☃x.func_74782_a("data", ☃.func_189551_b(new NBTTagCompound()));
               ☃x.func_74768_a("DataVersion", 1631);
               FileOutputStream ☃xx = new FileOutputStream(☃);
               CompressedStreamTools.func_74799_a(☃x, ☃xx);
               ☃xx.close();
            }
         } catch (Exception var5) {
            field_212776_a.error("Could not save data {}", ☃, var5);
         }
      }
   }
}
