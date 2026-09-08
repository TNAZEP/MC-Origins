package net.minecraft.command.impl.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class BlockDataAccessor implements IDataAccessor {
   private static final SimpleCommandExceptionType field_198931_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.data.block.invalid"));
   public static final DataCommand.IDataProvider field_198930_a = new DataCommand.IDataProvider() {
      @Override
      public IDataAccessor func_198919_a(CommandContext<CommandSource> var1) throws CommandSyntaxException {
         BlockPos ☃ = BlockPosArgument.func_197273_a(☃, "pos");
         TileEntity ☃x = ☃.getSource().func_197023_e().func_175625_s(☃);
         if (☃x == null) {
            throw BlockDataAccessor.field_198931_b.create();
         } else {
            return new BlockDataAccessor(☃x, ☃);
         }
      }

      @Override
      public ArgumentBuilder<CommandSource, ?> func_198920_a(
         ArgumentBuilder<CommandSource, ?> var1, Function<ArgumentBuilder<CommandSource, ?>, ArgumentBuilder<CommandSource, ?>> var2
      ) {
         return ☃.then(
            Commands.func_197057_a("block").then((ArgumentBuilder<CommandSource, ?>)☃.apply(Commands.func_197056_a("pos", BlockPosArgument.func_197276_a())))
         );
      }
   };
   private final TileEntity field_198932_c;
   private final BlockPos field_198933_d;

   public BlockDataAccessor(TileEntity var1, BlockPos var2) {
      this.field_198932_c = ☃;
      this.field_198933_d = ☃;
   }

   @Override
   public void func_198925_a(NBTTagCompound var1) {
      ☃.func_74768_a("x", this.field_198933_d.func_177958_n());
      ☃.func_74768_a("y", this.field_198933_d.func_177956_o());
      ☃.func_74768_a("z", this.field_198933_d.func_177952_p());
      this.field_198932_c.func_145839_a(☃);
      this.field_198932_c.func_70296_d();
      IBlockState ☃ = this.field_198932_c.func_145831_w().func_180495_p(this.field_198933_d);
      this.field_198932_c.func_145831_w().func_184138_a(this.field_198933_d, ☃, ☃, 3);
   }

   @Override
   public NBTTagCompound func_198923_a() {
      return this.field_198932_c.func_189515_b(new NBTTagCompound());
   }

   @Override
   public ITextComponent func_198921_b() {
      return new TextComponentTranslation(
         "commands.data.block.modified", this.field_198933_d.func_177958_n(), this.field_198933_d.func_177956_o(), this.field_198933_d.func_177952_p()
      );
   }

   @Override
   public ITextComponent func_198924_b(INBTBase var1) {
      return new TextComponentTranslation(
         "commands.data.block.query",
         this.field_198933_d.func_177958_n(),
         this.field_198933_d.func_177956_o(),
         this.field_198933_d.func_177952_p(),
         ☃.func_197637_c()
      );
   }

   @Override
   public ITextComponent func_198922_a(NBTPathArgument.NBTPath var1, double var2, int var4) {
      return new TextComponentTranslation(
         "commands.data.block.get",
         ☃,
         this.field_198933_d.func_177958_n(),
         this.field_198933_d.func_177956_o(),
         this.field_198933_d.func_177952_p(),
         String.format(Locale.ROOT, "%.2f", ☃),
         ☃
      );
   }
}
