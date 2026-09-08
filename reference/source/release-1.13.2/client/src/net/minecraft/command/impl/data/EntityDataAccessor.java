package net.minecraft.command.impl.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityDataAccessor implements IDataAccessor {
   private static final SimpleCommandExceptionType field_198927_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.data.entity.invalid"));
   public static final DataCommand.IDataProvider field_198926_a = new DataCommand.IDataProvider() {
      @Override
      public IDataAccessor func_198919_a(CommandContext<CommandSource> var1) throws CommandSyntaxException {
         return new EntityDataAccessor(EntityArgument.func_197088_a(☃, "target"));
      }

      @Override
      public ArgumentBuilder<CommandSource, ?> func_198920_a(
         ArgumentBuilder<CommandSource, ?> var1, Function<ArgumentBuilder<CommandSource, ?>, ArgumentBuilder<CommandSource, ?>> var2
      ) {
         return ☃.then(
            Commands.func_197057_a("entity").then((ArgumentBuilder<CommandSource, ?>)☃.apply(Commands.func_197056_a("target", EntityArgument.func_197086_a())))
         );
      }
   };
   private final Entity field_198928_c;

   public EntityDataAccessor(Entity var1) {
      this.field_198928_c = ☃;
   }

   @Override
   public void func_198925_a(NBTTagCompound var1) throws CommandSyntaxException {
      if (this.field_198928_c instanceof EntityPlayer) {
         throw field_198927_b.create();
      } else {
         UUID ☃ = this.field_198928_c.func_110124_au();
         this.field_198928_c.func_70020_e(☃);
         this.field_198928_c.func_184221_a(☃);
      }
   }

   @Override
   public NBTTagCompound func_198923_a() {
      return NBTPredicate.func_196981_b(this.field_198928_c);
   }

   @Override
   public ITextComponent func_198921_b() {
      return new TextComponentTranslation("commands.data.entity.modified", this.field_198928_c.func_145748_c_());
   }

   @Override
   public ITextComponent func_198924_b(INBTBase var1) {
      return new TextComponentTranslation("commands.data.entity.query", this.field_198928_c.func_145748_c_(), ☃.func_197637_c());
   }

   @Override
   public ITextComponent func_198922_a(NBTPathArgument.NBTPath var1, double var2, int var4) {
      return new TextComponentTranslation("commands.data.entity.get", ☃, this.field_198928_c.func_145748_c_(), String.format(Locale.ROOT, "%.2f", ☃), ☃);
   }
}
