package net.minecraft.world.level.timers;

import net.minecraft.commands.CommandFunction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerFunctionManager;
import net.minecraft.tags.Tag;

public class FunctionTagCallback implements TimerCallback<MinecraftServer> {
   final ResourceLocation tagId;

   public FunctionTagCallback(ResourceLocation var1) {
      this.tagId = â˜ƒ;
   }

   public void handle(MinecraftServer var1, TimerQueue<MinecraftServer> var2, long var3) {
      ServerFunctionManager â˜ƒ = â˜ƒ.getFunctions();
      Tag<CommandFunction> â˜ƒx = â˜ƒ.getTag(this.tagId);

      for(CommandFunction â˜ƒxx : â˜ƒx.getValues()) {
         â˜ƒ.execute(â˜ƒxx, â˜ƒ.getGameLoopSender());
      }
   }

   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionTagCallback> {
      public Serializer() {
         super(new ResourceLocation("function_tag"), FunctionTagCallback.class);
      }

      public void serialize(CompoundTag var1, FunctionTagCallback var2) {
         â˜ƒ.putString("Name", â˜ƒ.tagId.toString());
      }

      public FunctionTagCallback deserialize(CompoundTag var1) {
         ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ.getString("Name"));
         return new FunctionTagCallback(â˜ƒ);
      }
   }
}
