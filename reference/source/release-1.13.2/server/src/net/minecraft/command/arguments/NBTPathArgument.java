package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.command.CommandSource;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCollection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextComponentTranslation;

public class NBTPathArgument implements ArgumentType<NBTPathArgument.NBTPath> {
   private static final Collection<String> field_201316_a = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", ".");
   private static final DynamicCommandExceptionType field_197153_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("arguments.nbtpath.child.invalid", var0)
   );
   private static final DynamicCommandExceptionType field_197154_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("arguments.nbtpath.element.invalid", var0)
   );
   private static final SimpleCommandExceptionType field_201948_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("arguments.nbtpath.node.invalid")
   );

   public static NBTPathArgument func_197149_a() {
      return new NBTPathArgument();
   }

   public static NBTPathArgument.NBTPath func_197148_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, NBTPathArgument.NBTPath.class);
   }

   public NBTPathArgument.NBTPath parse(StringReader var1) throws CommandSyntaxException {
      List<NBTPathArgument.INode> ☃ = Lists.<NBTPathArgument.INode>newArrayList();
      int ☃x = ☃.getCursor();

      while(☃.canRead() && ☃.peek() != ' ') {
         switch(☃.peek()) {
            case '"':
               ☃.add(new NBTPathArgument.ChildNode(☃.readString()));
               break;
            case '[':
               ☃.skip();
               ☃.add(new NBTPathArgument.ElementNode(☃.readInt()));
               ☃.expect(']');
               break;
            default:
               ☃.add(new NBTPathArgument.ChildNode(this.func_197151_a(☃)));
         }

         if (☃.canRead()) {
            char ☃xx = ☃.peek();
            if (☃xx != ' ' && ☃xx != '[') {
               ☃.expect('.');
            }
         }
      }

      return new NBTPathArgument.NBTPath(☃.getString().substring(☃x, ☃.getCursor()), (NBTPathArgument.INode[])☃.toArray(new NBTPathArgument.INode[0]));
   }

   private String func_197151_a(StringReader var1) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();

      while(☃.canRead() && func_197146_a(☃.peek())) {
         ☃.skip();
      }

      if (☃.getCursor() == ☃) {
         throw field_201948_d.createWithContext(☃);
      } else {
         return ☃.getString().substring(☃, ☃.getCursor());
      }
   }

   @Override
   public Collection<String> getExamples() {
      return field_201316_a;
   }

   private static boolean func_197146_a(char var0) {
      return ☃ != ' ' && ☃ != '"' && ☃ != '[' && ☃ != ']' && ☃ != '.';
   }

   static class ChildNode implements NBTPathArgument.INode {
      private final String field_197139_a;

      public ChildNode(String var1) {
         this.field_197139_a = ☃;
      }

      @Override
      public INBTBase func_197137_a(INBTBase var1) throws CommandSyntaxException {
         if (☃ instanceof NBTTagCompound) {
            return ((NBTTagCompound)☃).func_74781_a(this.field_197139_a);
         } else {
            throw NBTPathArgument.field_197153_a.create(this.field_197139_a);
         }
      }

      @Override
      public INBTBase func_197135_a(INBTBase var1, Supplier<INBTBase> var2) throws CommandSyntaxException {
         if (☃ instanceof NBTTagCompound) {
            NBTTagCompound ☃ = (NBTTagCompound)☃;
            if (☃.func_74764_b(this.field_197139_a)) {
               return ☃.func_74781_a(this.field_197139_a);
            } else {
               INBTBase ☃ = (INBTBase)☃.get();
               ☃.func_74782_a(this.field_197139_a, ☃);
               return ☃;
            }
         } else {
            throw NBTPathArgument.field_197153_a.create(this.field_197139_a);
         }
      }

      @Override
      public INBTBase func_197134_a() {
         return new NBTTagCompound();
      }

      @Override
      public void func_197136_a(INBTBase var1, INBTBase var2) throws CommandSyntaxException {
         if (☃ instanceof NBTTagCompound) {
            NBTTagCompound ☃ = (NBTTagCompound)☃;
            ☃.func_74782_a(this.field_197139_a, ☃);
         } else {
            throw NBTPathArgument.field_197153_a.create(this.field_197139_a);
         }
      }

      @Override
      public void func_197133_b(INBTBase var1) throws CommandSyntaxException {
         if (☃ instanceof NBTTagCompound) {
            NBTTagCompound ☃ = (NBTTagCompound)☃;
            if (☃.func_74764_b(this.field_197139_a)) {
               ☃.func_82580_o(this.field_197139_a);
               return;
            }
         }

         throw NBTPathArgument.field_197153_a.create(this.field_197139_a);
      }
   }

   static class ElementNode implements NBTPathArgument.INode {
      private final int field_197138_a;

      public ElementNode(int var1) {
         this.field_197138_a = ☃;
      }

      @Override
      public INBTBase func_197137_a(INBTBase var1) throws CommandSyntaxException {
         if (☃ instanceof NBTTagCollection) {
            NBTTagCollection<?> ☃ = (NBTTagCollection)☃;
            if (☃.size() > this.field_197138_a) {
               return ☃.func_197647_c(this.field_197138_a);
            }
         }

         throw NBTPathArgument.field_197154_b.create(this.field_197138_a);
      }

      @Override
      public INBTBase func_197135_a(INBTBase var1, Supplier<INBTBase> var2) throws CommandSyntaxException {
         return this.func_197137_a(☃);
      }

      @Override
      public INBTBase func_197134_a() {
         return new NBTTagList();
      }

      @Override
      public void func_197136_a(INBTBase var1, INBTBase var2) throws CommandSyntaxException {
         if (☃ instanceof NBTTagCollection) {
            NBTTagCollection<?> ☃ = (NBTTagCollection)☃;
            if (☃.size() > this.field_197138_a) {
               ☃.func_197648_a(this.field_197138_a, ☃);
               return;
            }
         }

         throw NBTPathArgument.field_197154_b.create(this.field_197138_a);
      }

      @Override
      public void func_197133_b(INBTBase var1) throws CommandSyntaxException {
         if (☃ instanceof NBTTagCollection) {
            NBTTagCollection<?> ☃ = (NBTTagCollection)☃;
            if (☃.size() > this.field_197138_a) {
               ☃.func_197649_b(this.field_197138_a);
               return;
            }
         }

         throw NBTPathArgument.field_197154_b.create(this.field_197138_a);
      }
   }

   interface INode {
      INBTBase func_197137_a(INBTBase var1) throws CommandSyntaxException;

      INBTBase func_197135_a(INBTBase var1, Supplier<INBTBase> var2) throws CommandSyntaxException;

      INBTBase func_197134_a();

      void func_197136_a(INBTBase var1, INBTBase var2) throws CommandSyntaxException;

      void func_197133_b(INBTBase var1) throws CommandSyntaxException;
   }

   public static class NBTPath {
      private final String field_197144_a;
      private final NBTPathArgument.INode[] field_197145_b;

      public NBTPath(String var1, NBTPathArgument.INode[] var2) {
         this.field_197144_a = ☃;
         this.field_197145_b = ☃;
      }

      public INBTBase func_197143_a(INBTBase var1) throws CommandSyntaxException {
         for(NBTPathArgument.INode ☃ : this.field_197145_b) {
            ☃ = ☃.func_197137_a(☃);
         }

         return ☃;
      }

      public INBTBase func_197142_a(INBTBase var1, INBTBase var2) throws CommandSyntaxException {
         for(int ☃ = 0; ☃ < this.field_197145_b.length; ++☃) {
            NBTPathArgument.INode ☃x = this.field_197145_b[☃];
            if (☃ < this.field_197145_b.length - 1) {
               int ☃xx = ☃ + 1;
               ☃ = ☃x.func_197135_a(☃, () -> this.field_197145_b[☃].func_197134_a());
            } else {
               ☃x.func_197136_a(☃, ☃);
            }
         }

         return ☃;
      }

      public String toString() {
         return this.field_197144_a;
      }

      public void func_197140_b(INBTBase var1) throws CommandSyntaxException {
         for(int ☃ = 0; ☃ < this.field_197145_b.length; ++☃) {
            NBTPathArgument.INode ☃x = this.field_197145_b[☃];
            if (☃ < this.field_197145_b.length - 1) {
               ☃ = ☃x.func_197137_a(☃);
            } else {
               ☃x.func_197133_b(☃);
            }
         }
      }
   }
}
