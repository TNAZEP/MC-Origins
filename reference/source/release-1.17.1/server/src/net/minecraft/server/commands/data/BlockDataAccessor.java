package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockDataAccessor implements DataAccessor {
   static final SimpleCommandExceptionType ERROR_NOT_A_BLOCK_ENTITY = new SimpleCommandExceptionType(new TranslatableComponent("commands.data.block.invalid"));
   public static final Function<String, DataCommands.DataProvider> PROVIDER = var0 -> new DataCommands.DataProvider() {
         @Override
         public DataAccessor access(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException {
            BlockPos â˜ƒ = BlockPosArgument.getLoadedBlockPos(â˜ƒ, â˜ƒ + "Pos");
            BlockEntity â˜ƒx = â˜ƒ.getSource().getLevel().getBlockEntity(â˜ƒ);
            if (â˜ƒx == null) {
               throw BlockDataAccessor.ERROR_NOT_A_BLOCK_ENTITY.create();
            } else {
               return new BlockDataAccessor(â˜ƒx, â˜ƒ);
            }
         }

         @Override
         public ArgumentBuilder<CommandSourceStack, ?> wrap(
            ArgumentBuilder<CommandSourceStack, ?> var1, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> var2
         ) {
            return â˜ƒ.then(
               Commands.literal("block").then((ArgumentBuilder<CommandSourceStack, ?>)â˜ƒ.apply(Commands.argument(â˜ƒ + "Pos", BlockPosArgument.blockPos())))
            );
         }
      };
   private final BlockEntity entity;
   private final BlockPos pos;

   public BlockDataAccessor(BlockEntity var1, BlockPos var2) {
      this.entity = â˜ƒ;
      this.pos = â˜ƒ;
   }

   @Override
   public void setData(CompoundTag var1) {
      â˜ƒ.putInt("x", this.pos.getX());
      â˜ƒ.putInt("y", this.pos.getY());
      â˜ƒ.putInt("z", this.pos.getZ());
      BlockState â˜ƒ = this.entity.getLevel().getBlockState(this.pos);
      this.entity.load(â˜ƒ);
      this.entity.setChanged();
      this.entity.getLevel().sendBlockUpdated(this.pos, â˜ƒ, â˜ƒ, 3);
   }

   @Override
   public CompoundTag getData() {
      return this.entity.save(new CompoundTag());
   }

   @Override
   public Component getModifiedSuccess() {
      return new TranslatableComponent("commands.data.block.modified", this.pos.getX(), this.pos.getY(), this.pos.getZ());
   }

   @Override
   public Component getPrintSuccess(Tag var1) {
      return new TranslatableComponent("commands.data.block.query", this.pos.getX(), this.pos.getY(), this.pos.getZ(), NbtUtils.toPrettyComponent(â˜ƒ));
   }

   @Override
   public Component getPrintSuccess(NbtPathArgument.NbtPath var1, double var2, int var4) {
      return new TranslatableComponent(
         "commands.data.block.get", â˜ƒ, this.pos.getX(), this.pos.getY(), this.pos.getZ(), String.format(Locale.ROOT, "%.2f", â˜ƒ), â˜ƒ
      );
   }
}
