package net.minecraft.client.settings;

import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import java.io.File;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreativeSettings {
   private static final Logger field_192566_b = LogManager.getLogger();
   private final File field_192567_c;
   private final DataFixer field_206251_c;
   private final HotbarSnapshot[] field_192568_d = new HotbarSnapshot[9];
   private boolean field_206252_e;

   public CreativeSettings(File var1, DataFixer var2) {
      this.field_192567_c = new File(☃, "hotbar.nbt");
      this.field_206251_c = ☃;

      for(int ☃ = 0; ☃ < 9; ++☃) {
         this.field_192568_d[☃] = new HotbarSnapshot();
      }
   }

   private void func_206250_b() {
      try {
         NBTTagCompound ☃ = CompressedStreamTools.func_74797_a(this.field_192567_c);
         if (☃ == null) {
            return;
         }

         if (!☃.func_150297_b("DataVersion", 99)) {
            ☃.func_74768_a("DataVersion", 1343);
         }

         ☃ = NBTUtil.func_210822_a(this.field_206251_c, DataFixTypes.HOTBAR, ☃, ☃.func_74762_e("DataVersion"));

         for(int ☃ = 0; ☃ < 9; ++☃) {
            this.field_192568_d[☃].func_192833_a(☃.func_150295_c(String.valueOf(☃), 10));
         }
      } catch (Exception var3) {
         field_192566_b.error("Failed to load creative mode options", var3);
      }
   }

   public void func_192564_b() {
      try {
         NBTTagCompound ☃ = new NBTTagCompound();
         ☃.func_74768_a("DataVersion", 1631);

         for(int ☃x = 0; ☃x < 9; ++☃x) {
            ☃.func_74782_a(String.valueOf(☃x), this.func_192563_a(☃x).func_192834_a());
         }

         CompressedStreamTools.func_74795_b(☃, this.field_192567_c);
      } catch (Exception var3) {
         field_192566_b.error("Failed to save creative mode options", var3);
      }
   }

   public HotbarSnapshot func_192563_a(int var1) {
      if (!this.field_206252_e) {
         this.func_206250_b();
         this.field_206252_e = true;
      }

      return this.field_192568_d[☃];
   }
}
