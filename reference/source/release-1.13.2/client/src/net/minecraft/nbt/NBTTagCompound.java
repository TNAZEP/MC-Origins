package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagCompound implements INBTBase {
   private static final Logger field_191551_b = LogManager.getLogger();
   private static final Pattern field_193583_c = Pattern.compile("[A-Za-z0-9._+-]+");
   private final Map<String, INBTBase> field_74784_a = Maps.newHashMap();

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      for(String ☃ : this.field_74784_a.keySet()) {
         INBTBase ☃x = (INBTBase)this.field_74784_a.get(☃);
         func_150298_a(☃, ☃x, ☃);
      }

      ☃.writeByte(0);
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(384L);
      if (☃ > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.field_74784_a.clear();

         byte ☃;
         while((☃ = func_152447_a(☃, ☃)) != 0) {
            String ☃ = func_152448_b(☃, ☃);
            ☃.func_152450_a((long)(224 + 16 * ☃.length()));
            INBTBase ☃x = func_152449_a(☃, ☃, ☃, ☃ + 1, ☃);
            if (this.field_74784_a.put(☃, ☃x) != null) {
               ☃.func_152450_a(288L);
            }
         }
      }
   }

   public Set<String> func_150296_c() {
      return this.field_74784_a.keySet();
   }

   @Override
   public byte func_74732_a() {
      return 10;
   }

   public int func_186856_d() {
      return this.field_74784_a.size();
   }

   public void func_74782_a(String var1, INBTBase var2) {
      this.field_74784_a.put(☃, ☃);
   }

   public void func_74774_a(String var1, byte var2) {
      this.field_74784_a.put(☃, new NBTTagByte(☃));
   }

   public void func_74777_a(String var1, short var2) {
      this.field_74784_a.put(☃, new NBTTagShort(☃));
   }

   public void func_74768_a(String var1, int var2) {
      this.field_74784_a.put(☃, new NBTTagInt(☃));
   }

   public void func_74772_a(String var1, long var2) {
      this.field_74784_a.put(☃, new NBTTagLong(☃));
   }

   public void func_186854_a(String var1, UUID var2) {
      this.func_74772_a(☃ + "Most", ☃.getMostSignificantBits());
      this.func_74772_a(☃ + "Least", ☃.getLeastSignificantBits());
   }

   @Nullable
   public UUID func_186857_a(String var1) {
      return new UUID(this.func_74763_f(☃ + "Most"), this.func_74763_f(☃ + "Least"));
   }

   public boolean func_186855_b(String var1) {
      return this.func_150297_b(☃ + "Most", 99) && this.func_150297_b(☃ + "Least", 99);
   }

   public void func_74776_a(String var1, float var2) {
      this.field_74784_a.put(☃, new NBTTagFloat(☃));
   }

   public void func_74780_a(String var1, double var2) {
      this.field_74784_a.put(☃, new NBTTagDouble(☃));
   }

   public void func_74778_a(String var1, String var2) {
      this.field_74784_a.put(☃, new NBTTagString(☃));
   }

   public void func_74773_a(String var1, byte[] var2) {
      this.field_74784_a.put(☃, new NBTTagByteArray(☃));
   }

   public void func_74783_a(String var1, int[] var2) {
      this.field_74784_a.put(☃, new NBTTagIntArray(☃));
   }

   public void func_197646_b(String var1, List<Integer> var2) {
      this.field_74784_a.put(☃, new NBTTagIntArray(☃));
   }

   public void func_197644_a(String var1, long[] var2) {
      this.field_74784_a.put(☃, new NBTTagLongArray(☃));
   }

   public void func_202168_c(String var1, List<Long> var2) {
      this.field_74784_a.put(☃, new NBTTagLongArray(☃));
   }

   public void func_74757_a(String var1, boolean var2) {
      this.func_74774_a(☃, (byte)(☃ ? 1 : 0));
   }

   public INBTBase func_74781_a(String var1) {
      return (INBTBase)this.field_74784_a.get(☃);
   }

   public byte func_150299_b(String var1) {
      INBTBase ☃ = (INBTBase)this.field_74784_a.get(☃);
      return ☃ == null ? 0 : ☃.func_74732_a();
   }

   public boolean func_74764_b(String var1) {
      return this.field_74784_a.containsKey(☃);
   }

   public boolean func_150297_b(String var1, int var2) {
      int ☃ = this.func_150299_b(☃);
      if (☃ == ☃) {
         return true;
      } else if (☃ != 99) {
         return false;
      } else {
         return ☃ == 1 || ☃ == 2 || ☃ == 3 || ☃ == 4 || ☃ == 5 || ☃ == 6;
      }
   }

   public byte func_74771_c(String var1) {
      try {
         if (this.func_150297_b(☃, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(☃)).func_150290_f();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public short func_74765_d(String var1) {
      try {
         if (this.func_150297_b(☃, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(☃)).func_150289_e();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public int func_74762_e(String var1) {
      try {
         if (this.func_150297_b(☃, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(☃)).func_150287_d();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public long func_74763_f(String var1) {
      try {
         if (this.func_150297_b(☃, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(☃)).func_150291_c();
         }
      } catch (ClassCastException var3) {
      }

      return 0L;
   }

   public float func_74760_g(String var1) {
      try {
         if (this.func_150297_b(☃, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(☃)).func_150288_h();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0F;
   }

   public double func_74769_h(String var1) {
      try {
         if (this.func_150297_b(☃, 99)) {
            return ((NBTPrimitive)this.field_74784_a.get(☃)).func_150286_g();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0;
   }

   public String func_74779_i(String var1) {
      try {
         if (this.func_150297_b(☃, 8)) {
            return ((INBTBase)this.field_74784_a.get(☃)).func_150285_a_();
         }
      } catch (ClassCastException var3) {
      }

      return "";
   }

   public byte[] func_74770_j(String var1) {
      try {
         if (this.func_150297_b(☃, 7)) {
            return ((NBTTagByteArray)this.field_74784_a.get(☃)).func_150292_c();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.func_82581_a(☃, 7, var3));
      }

      return new byte[0];
   }

   public int[] func_74759_k(String var1) {
      try {
         if (this.func_150297_b(☃, 11)) {
            return ((NBTTagIntArray)this.field_74784_a.get(☃)).func_150302_c();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.func_82581_a(☃, 11, var3));
      }

      return new int[0];
   }

   public long[] func_197645_o(String var1) {
      try {
         if (this.func_150297_b(☃, 12)) {
            return ((NBTTagLongArray)this.field_74784_a.get(☃)).func_197652_h();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.func_82581_a(☃, 12, var3));
      }

      return new long[0];
   }

   public NBTTagCompound func_74775_l(String var1) {
      try {
         if (this.func_150297_b(☃, 10)) {
            return (NBTTagCompound)this.field_74784_a.get(☃);
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.func_82581_a(☃, 10, var3));
      }

      return new NBTTagCompound();
   }

   public NBTTagList func_150295_c(String var1, int var2) {
      try {
         if (this.func_150299_b(☃) == 9) {
            NBTTagList ☃ = (NBTTagList)this.field_74784_a.get(☃);
            if (!☃.isEmpty() && ☃.func_150303_d() != ☃) {
               return new NBTTagList();
            }

            return ☃;
         }
      } catch (ClassCastException var4) {
         throw new ReportedException(this.func_82581_a(☃, 9, var4));
      }

      return new NBTTagList();
   }

   public boolean func_74767_n(String var1) {
      return this.func_74771_c(☃) != 0;
   }

   public void func_82580_o(String var1) {
      this.field_74784_a.remove(☃);
   }

   @Override
   public String toString() {
      StringBuilder ☃ = new StringBuilder("{");
      Collection<String> ☃x = this.field_74784_a.keySet();
      if (field_191551_b.isDebugEnabled()) {
         List<String> ☃xx = Lists.newArrayList(this.field_74784_a.keySet());
         Collections.sort(☃xx);
         ☃x = ☃xx;
      }

      for(String ☃ : ☃x) {
         if (☃.length() != 1) {
            ☃.append(',');
         }

         ☃.append(func_193582_s(☃)).append(':').append(this.field_74784_a.get(☃));
      }

      return ☃.append('}').toString();
   }

   public boolean isEmpty() {
      return this.field_74784_a.isEmpty();
   }

   private CrashReport func_82581_a(String var1, int var2, ClassCastException var3) {
      CrashReport ☃ = CrashReport.func_85055_a(☃, "Reading NBT data");
      CrashReportCategory ☃x = ☃.func_85057_a("Corrupt NBT tag", 1);
      ☃x.func_189529_a("Tag type found", () -> field_82578_b[((INBTBase)this.field_74784_a.get(☃)).func_74732_a()]);
      ☃x.func_189529_a("Tag type expected", () -> field_82578_b[☃]);
      ☃x.func_71507_a("Tag name", ☃);
      return ☃;
   }

   public NBTTagCompound func_74737_b() {
      NBTTagCompound ☃ = new NBTTagCompound();

      for(String ☃x : this.field_74784_a.keySet()) {
         ☃.func_74782_a(☃x, ((INBTBase)this.field_74784_a.get(☃x)).func_74737_b());
      }

      return ☃;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagCompound && Objects.equals(this.field_74784_a, ((NBTTagCompound)☃).field_74784_a);
      }
   }

   public int hashCode() {
      return this.field_74784_a.hashCode();
   }

   private static void func_150298_a(String var0, INBTBase var1, DataOutput var2) throws IOException {
      ☃.writeByte(☃.func_74732_a());
      if (☃.func_74732_a() != 0) {
         ☃.writeUTF(☃);
         ☃.func_74734_a(☃);
      }
   }

   private static byte func_152447_a(DataInput var0, NBTSizeTracker var1) throws IOException {
      return ☃.readByte();
   }

   private static String func_152448_b(DataInput var0, NBTSizeTracker var1) throws IOException {
      return ☃.readUTF();
   }

   static INBTBase func_152449_a(byte var0, String var1, DataInput var2, int var3, NBTSizeTracker var4) throws IOException {
      INBTBase ☃ = INBTBase.func_150284_a(☃);

      try {
         ☃.func_152446_a(☃, ☃, ☃);
         return ☃;
      } catch (IOException var9) {
         CrashReport ☃x = CrashReport.func_85055_a(var9, "Loading NBT data");
         CrashReportCategory ☃xx = ☃x.func_85058_a("NBT Tag");
         ☃xx.func_71507_a("Tag name", ☃);
         ☃xx.func_71507_a("Tag type", ☃);
         throw new ReportedException(☃x);
      }
   }

   public NBTTagCompound func_197643_a(NBTTagCompound var1) {
      for(String ☃ : ☃.field_74784_a.keySet()) {
         INBTBase ☃x = (INBTBase)☃.field_74784_a.get(☃);
         if (☃x.func_74732_a() == 10) {
            if (this.func_150297_b(☃, 10)) {
               NBTTagCompound ☃xx = this.func_74775_l(☃);
               ☃xx.func_197643_a((NBTTagCompound)☃x);
            } else {
               this.func_74782_a(☃, ☃x.func_74737_b());
            }
         } else {
            this.func_74782_a(☃, ☃x.func_74737_b());
         }
      }

      return this;
   }

   protected static String func_193582_s(String var0) {
      return field_193583_c.matcher(☃).matches() ? ☃ : NBTTagString.func_197654_a(☃, true);
   }

   protected static ITextComponent func_197642_t(String var0) {
      if (field_193583_c.matcher(☃).matches()) {
         return new TextComponentString(☃).func_211708_a(field_197638_b);
      } else {
         ITextComponent ☃ = new TextComponentString(NBTTagString.func_197654_a(☃, false)).func_211708_a(field_197638_b);
         return new TextComponentString("\"").func_150257_a(☃).func_150258_a("\"");
      }
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      if (this.field_74784_a.isEmpty()) {
         return new TextComponentString("{}");
      } else {
         ITextComponent ☃ = new TextComponentString("{");
         Collection<String> ☃x = this.field_74784_a.keySet();
         if (field_191551_b.isDebugEnabled()) {
            List<String> ☃xx = Lists.newArrayList(this.field_74784_a.keySet());
            Collections.sort(☃xx);
            ☃x = ☃xx;
         }

         if (!☃.isEmpty()) {
            ☃.func_150258_a("\n");
         }

         ITextComponent ☃;
         for(Iterator<String> ☃ = ☃x.iterator(); ☃.hasNext(); ☃.func_150257_a(☃)) {
            String ☃x = (String)☃.next();
            ☃ = new TextComponentString(Strings.repeat(☃, ☃ + 1))
               .func_150257_a(func_197642_t(☃x))
               .func_150258_a(String.valueOf(':'))
               .func_150258_a(" ")
               .func_150257_a(((INBTBase)this.field_74784_a.get(☃x)).func_199850_a(☃, ☃ + 1));
            if (☃.hasNext()) {
               ☃.func_150258_a(String.valueOf(',')).func_150258_a(☃.isEmpty() ? " " : "\n");
            }
         }

         if (!☃.isEmpty()) {
            ☃.func_150258_a("\n").func_150258_a(Strings.repeat(☃, ☃));
         }

         ☃.func_150258_a("}");
         return ☃;
      }
   }
}
