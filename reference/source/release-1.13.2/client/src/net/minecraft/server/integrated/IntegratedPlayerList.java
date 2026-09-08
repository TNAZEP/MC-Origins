package net.minecraft.server.integrated;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class IntegratedPlayerList extends PlayerList {
   private NBTTagCompound field_72416_e;

   public IntegratedPlayerList(IntegratedServer var1) {
      super(☃);
      this.func_152611_a(10);
   }

   @Override
   protected void func_72391_b(EntityPlayerMP var1) {
      if (☃.func_200200_C_().getString().equals(this.func_72365_p().func_71214_G())) {
         this.field_72416_e = ☃.func_189511_e(new NBTTagCompound());
      }

      super.func_72391_b(☃);
   }

   @Override
   public ITextComponent func_206258_a(SocketAddress var1, GameProfile var2) {
      return (ITextComponent)(☃.getName().equalsIgnoreCase(this.func_72365_p().func_71214_G()) && this.func_152612_a(☃.getName()) != null
         ? new TextComponentTranslation("multiplayer.disconnect.name_taken")
         : super.func_206258_a(☃, ☃));
   }

   public IntegratedServer func_72365_p() {
      return (IntegratedServer)super.func_72365_p();
   }

   @Override
   public NBTTagCompound func_72378_q() {
      return this.field_72416_e;
   }
}
