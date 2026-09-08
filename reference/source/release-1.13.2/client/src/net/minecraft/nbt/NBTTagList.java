package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagList extends NBTTagCollection<INBTBase> {
   private static final Logger field_179239_b = LogManager.getLogger();
   private List<INBTBase> field_74747_a = Lists.<INBTBase>newArrayList();
   private byte field_74746_b = 0;

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      if (this.field_74747_a.isEmpty()) {
         this.field_74746_b = 0;
      } else {
         this.field_74746_b = ((INBTBase)this.field_74747_a.get(0)).func_74732_a();
      }

      ☃.writeByte(this.field_74746_b);
      ☃.writeInt(this.field_74747_a.size());

      for(int ☃ = 0; ☃ < this.field_74747_a.size(); ++☃) {
         ((INBTBase)this.field_74747_a.get(☃)).func_74734_a(☃);
      }
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(296L);
      if (☃ > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.field_74746_b = ☃.readByte();
         int ☃ = ☃.readInt();
         if (this.field_74746_b == 0 && ☃ > 0) {
            throw new RuntimeException("Missing type on ListTag");
         } else {
            ☃.func_152450_a(32L * (long)☃);
            this.field_74747_a = Lists.<INBTBase>newArrayListWithCapacity(☃);

            for(int ☃ = 0; ☃ < ☃; ++☃) {
               INBTBase ☃x = INBTBase.func_150284_a(this.field_74746_b);
               ☃x.func_152446_a(☃, ☃ + 1, ☃);
               this.field_74747_a.add(☃x);
            }
         }
      }
   }

   @Override
   public byte func_74732_a() {
      return 9;
   }

   @Override
   public String toString() {
      StringBuilder ☃ = new StringBuilder("[");

      for(int ☃x = 0; ☃x < this.field_74747_a.size(); ++☃x) {
         if (☃x != 0) {
            ☃.append(',');
         }

         ☃.append(this.field_74747_a.get(☃x));
      }

      return ☃.append(']').toString();
   }

   public boolean add(INBTBase var1) {
      if (☃.func_74732_a() == 0) {
         field_179239_b.warn("Invalid TagEnd added to ListTag");
         return false;
      } else {
         if (this.field_74746_b == 0) {
            this.field_74746_b = ☃.func_74732_a();
         } else if (this.field_74746_b != ☃.func_74732_a()) {
            field_179239_b.warn("Adding mismatching tag types to tag list");
            return false;
         }

         this.field_74747_a.add(☃);
         return true;
      }
   }

   @Override
   public INBTBase set(int var1, INBTBase var2) {
      if (☃.func_74732_a() == 0) {
         field_179239_b.warn("Invalid TagEnd added to ListTag");
         return (INBTBase)this.field_74747_a.get(☃);
      } else if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         if (this.field_74746_b == 0) {
            this.field_74746_b = ☃.func_74732_a();
         } else if (this.field_74746_b != ☃.func_74732_a()) {
            field_179239_b.warn("Adding mismatching tag types to tag list");
            return (INBTBase)this.field_74747_a.get(☃);
         }

         return (INBTBase)this.field_74747_a.set(☃, ☃);
      } else {
         field_179239_b.warn("index out of bounds to set tag in tag list");
         return null;
      }
   }

   public INBTBase remove(int var1) {
      return (INBTBase)this.field_74747_a.remove(☃);
   }

   public boolean isEmpty() {
      return this.field_74747_a.isEmpty();
   }

   public NBTTagCompound func_150305_b(int var1) {
      if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         INBTBase ☃ = (INBTBase)this.field_74747_a.get(☃);
         if (☃.func_74732_a() == 10) {
            return (NBTTagCompound)☃;
         }
      }

      return new NBTTagCompound();
   }

   public NBTTagList func_202169_e(int var1) {
      if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         INBTBase ☃ = (INBTBase)this.field_74747_a.get(☃);
         if (☃.func_74732_a() == 9) {
            return (NBTTagList)☃;
         }
      }

      return new NBTTagList();
   }

   public short func_202170_f(int var1) {
      if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         INBTBase ☃ = (INBTBase)this.field_74747_a.get(☃);
         if (☃.func_74732_a() == 2) {
            return ((NBTTagShort)☃).func_150289_e();
         }
      }

      return 0;
   }

   public int func_186858_c(int var1) {
      if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         INBTBase ☃ = (INBTBase)this.field_74747_a.get(☃);
         if (☃.func_74732_a() == 3) {
            return ((NBTTagInt)☃).func_150287_d();
         }
      }

      return 0;
   }

   public int[] func_150306_c(int var1) {
      if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         INBTBase ☃ = (INBTBase)this.field_74747_a.get(☃);
         if (☃.func_74732_a() == 11) {
            return ((NBTTagIntArray)☃).func_150302_c();
         }
      }

      return new int[0];
   }

   public double func_150309_d(int var1) {
      if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         INBTBase ☃ = (INBTBase)this.field_74747_a.get(☃);
         if (☃.func_74732_a() == 6) {
            return ((NBTTagDouble)☃).func_150286_g();
         }
      }

      return 0.0;
   }

   public float func_150308_e(int var1) {
      if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         INBTBase ☃ = (INBTBase)this.field_74747_a.get(☃);
         if (☃.func_74732_a() == 5) {
            return ((NBTTagFloat)☃).func_150288_h();
         }
      }

      return 0.0F;
   }

   public String func_150307_f(int var1) {
      if (☃ >= 0 && ☃ < this.field_74747_a.size()) {
         INBTBase ☃ = (INBTBase)this.field_74747_a.get(☃);
         return ☃.func_74732_a() == 8 ? ☃.func_150285_a_() : ☃.toString();
      } else {
         return "";
      }
   }

   @Override
   public INBTBase get(int var1) {
      return (INBTBase)(☃ >= 0 && ☃ < this.field_74747_a.size() ? (INBTBase)this.field_74747_a.get(☃) : new NBTTagEnd());
   }

   @Override
   public int size() {
      return this.field_74747_a.size();
   }

   @Override
   public INBTBase func_197647_c(int var1) {
      return (INBTBase)this.field_74747_a.get(☃);
   }

   @Override
   public void func_197648_a(int var1, INBTBase var2) {
      this.field_74747_a.set(☃, ☃);
   }

   @Override
   public void func_197649_b(int var1) {
      this.field_74747_a.remove(☃);
   }

   public NBTTagList func_74737_b() {
      NBTTagList ☃ = new NBTTagList();
      ☃.field_74746_b = this.field_74746_b;

      for(INBTBase ☃x : this.field_74747_a) {
         INBTBase ☃xx = ☃x.func_74737_b();
         ☃.field_74747_a.add(☃xx);
      }

      return ☃;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagList && Objects.equals(this.field_74747_a, ((NBTTagList)☃).field_74747_a);
      }
   }

   public int hashCode() {
      return this.field_74747_a.hashCode();
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      if (this.isEmpty()) {
         return new TextComponentString("[]");
      } else {
         ITextComponent ☃ = new TextComponentString("[");
         if (!☃.isEmpty()) {
            ☃.func_150258_a("\n");
         }

         for(int ☃ = 0; ☃ < this.field_74747_a.size(); ++☃) {
            ITextComponent ☃x = new TextComponentString(Strings.repeat(☃, ☃ + 1));
            ☃x.func_150257_a(((INBTBase)this.field_74747_a.get(☃)).func_199850_a(☃, ☃ + 1));
            if (☃ != this.field_74747_a.size() - 1) {
               ☃x.func_150258_a(String.valueOf(',')).func_150258_a(☃.isEmpty() ? " " : "\n");
            }

            ☃.func_150257_a(☃x);
         }

         if (!☃.isEmpty()) {
            ☃.func_150258_a("\n").func_150258_a(Strings.repeat(☃, ☃));
         }

         ☃.func_150258_a("]");
         return ☃;
      }
   }

   public int func_150303_d() {
      return this.field_74746_b;
   }
}
