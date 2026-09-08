package net.minecraft.network.rcon;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;

public class RConConsoleSource implements ICommandSource {
   private final StringBuffer field_70009_b = new StringBuffer();
   private final MinecraftServer field_184171_b;

   public RConConsoleSource(MinecraftServer var1) {
      this.field_184171_b = ☃;
   }

   public void func_70007_b() {
      this.field_70009_b.setLength(0);
   }

   public String func_70008_c() {
      return this.field_70009_b.toString();
   }

   public CommandSource func_195540_f() {
      WorldServer ☃ = this.field_184171_b.func_71218_a(DimensionType.OVERWORLD);
      return new CommandSource(
         this, new Vec3d(☃.func_175694_M()), Vec2f.field_189974_a, ☃, 4, "Recon", new TextComponentString("Rcon"), this.field_184171_b, null
      );
   }

   @Override
   public void func_145747_a(ITextComponent var1) {
      this.field_70009_b.append(☃.getString());
   }

   @Override
   public boolean func_195039_a() {
      return true;
   }

   @Override
   public boolean func_195040_b() {
      return true;
   }

   @Override
   public boolean func_195041_r_() {
      return this.field_184171_b.func_195569_l();
   }
}
