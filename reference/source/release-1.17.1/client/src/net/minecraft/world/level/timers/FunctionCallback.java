package net.minecraft.world.level.timers;

import net.minecraft.commands.CommandFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;

public class FunctionCallback implements TimerCallback<MinecraftServer> {
   final ResourceLocation functionId;

   public FunctionCallback(ResourceLocation var1) {
      this.functionId = â˜ƒ;
   }

   public void handle(MinecraftServer var1, TimerQueue<MinecraftServer> var2, long var3) {
      ServerFunctionManager â˜ƒ = â˜ƒ.getFunctions();
      â˜ƒ.get(this.functionId).ifPresent(var1x -> â˜ƒ.execute(var1x, â˜ƒ.getGameLoopSender()));
   }

   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionCallback> {
      public Serializer() {
         super(new ResourceLocation("function"), FunctionCallback.class);
      }

      public void serialize(CompoundTag var1, FunctionCallback var2) {
         â˜ƒ.putString("Name", â˜ƒ.functionId.toString());
      }

      public FunctionCallback deserialize(CompoundTag var1) {
         ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ.getString("Name"));
         return new FunctionCallback(â˜ƒ);
      }
   }
}
