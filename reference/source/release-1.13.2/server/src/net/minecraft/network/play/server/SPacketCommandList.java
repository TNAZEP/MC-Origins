package net.minecraft.network.play.server;

import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCommandList implements Packet<INetHandlerPlayClient> {
   private RootCommandNode<ISuggestionProvider> field_197697_a;

   public SPacketCommandList() {
   }

   public SPacketCommandList(RootCommandNode<ISuggestionProvider> var1) {
      this.field_197697_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      SPacketCommandList.Entry[] ☃ = new SPacketCommandList.Entry[☃.func_150792_a()];
      Deque<SPacketCommandList.Entry> ☃x = new ArrayDeque(☃.length);

      for(int ☃xx = 0; ☃xx < ☃.length; ++☃xx) {
         ☃[☃xx] = this.func_197692_c(☃);
         ☃x.add(☃[☃xx]);
      }

      while(!☃x.isEmpty()) {
         boolean ☃xx = false;
         Iterator<SPacketCommandList.Entry> ☃xxx = ☃x.iterator();

         while(☃xxx.hasNext()) {
            SPacketCommandList.Entry ☃xxxx = (SPacketCommandList.Entry)☃xxx.next();
            if (☃xxxx.func_197723_a(☃)) {
               ☃xxx.remove();
               ☃xx = true;
            }
         }

         if (!☃xx) {
            throw new IllegalStateException("Server sent an impossible command tree");
         }
      }

      this.field_197697_a = (RootCommandNode)☃[☃.func_150792_a()].field_197730_e;
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      Map<CommandNode<ISuggestionProvider>, Integer> ☃ = Maps.newHashMap();
      Deque<CommandNode<ISuggestionProvider>> ☃x = new ArrayDeque();
      ☃x.add(this.field_197697_a);

      while(!☃x.isEmpty()) {
         CommandNode<ISuggestionProvider> ☃xx = (CommandNode)☃x.pollFirst();
         if (!☃.containsKey(☃xx)) {
            int ☃xxx = ☃.size();
            ☃.put(☃xx, ☃xxx);
            ☃x.addAll(☃xx.getChildren());
            if (☃xx.getRedirect() != null) {
               ☃x.add(☃xx.getRedirect());
            }
         }
      }

      CommandNode<ISuggestionProvider>[] ☃xx = new CommandNode[☃.size()];

      for(java.util.Map.Entry<CommandNode<ISuggestionProvider>, Integer> ☃xxx : ☃.entrySet()) {
         ☃xx[☃xxx.getValue()] = (CommandNode)☃xxx.getKey();
      }

      ☃.func_150787_b(☃xx.length);

      for(CommandNode<ISuggestionProvider> ☃xxx : ☃xx) {
         this.func_197696_a(☃, ☃xxx, ☃);
      }

      ☃.func_150787_b(☃.get(this.field_197697_a));
   }

   private SPacketCommandList.Entry func_197692_c(PacketBuffer var1) {
      byte ☃ = ☃.readByte();
      int[] ☃x = ☃.func_186863_b();
      int ☃xx = (☃ & 8) != 0 ? ☃.func_150792_a() : 0;
      ArgumentBuilder<ISuggestionProvider, ?> ☃xxx = this.func_197695_a(☃, ☃);
      return new SPacketCommandList.Entry(☃xxx, ☃, ☃xx, ☃x);
   }

   @Nullable
   private ArgumentBuilder<ISuggestionProvider, ?> func_197695_a(PacketBuffer var1, byte var2) {
      int ☃ = ☃ & 3;
      if (☃ == 2) {
         String ☃x = ☃.func_150789_c(32767);
         ArgumentType<?> ☃xx = ArgumentTypes.func_197486_a(☃);
         if (☃xx == null) {
            return null;
         } else {
            RequiredArgumentBuilder<ISuggestionProvider, ?> ☃x = RequiredArgumentBuilder.argument(☃x, ☃xx);
            if ((☃ & 16) != 0) {
               ☃x.suggests(SuggestionProviders.func_197498_a(☃.func_192575_l()));
            }

            return ☃x;
         }
      } else {
         return ☃ == 1 ? LiteralArgumentBuilder.literal(☃.func_150789_c(32767)) : null;
      }
   }

   private void func_197696_a(PacketBuffer var1, CommandNode<ISuggestionProvider> var2, Map<CommandNode<ISuggestionProvider>, Integer> var3) {
      byte ☃ = 0;
      if (☃.getRedirect() != null) {
         ☃ = (byte)(☃ | 8);
      }

      if (☃.getCommand() != null) {
         ☃ = (byte)(☃ | 4);
      }

      if (☃ instanceof RootCommandNode) {
         ☃ = (byte)(☃ | 0);
      } else if (☃ instanceof ArgumentCommandNode) {
         ☃ = (byte)(☃ | 2);
         if (((ArgumentCommandNode)☃).getCustomSuggestions() != null) {
            ☃ = (byte)(☃ | 16);
         }
      } else {
         if (!(☃ instanceof LiteralCommandNode)) {
            throw new UnsupportedOperationException("Unknown node type " + ☃);
         }

         ☃ = (byte)(☃ | 1);
      }

      ☃.writeByte(☃);
      ☃.func_150787_b(☃.getChildren().size());

      for(CommandNode<ISuggestionProvider> ☃ : ☃.getChildren()) {
         ☃.func_150787_b(☃.get(☃));
      }

      if (☃.getRedirect() != null) {
         ☃.func_150787_b(☃.get(☃.getRedirect()));
      }

      if (☃ instanceof ArgumentCommandNode) {
         ArgumentCommandNode<ISuggestionProvider, ?> ☃ = (ArgumentCommandNode)☃;
         ☃.func_180714_a(☃.getName());
         ArgumentTypes.func_197484_a(☃, ☃.getType());
         if (☃.getCustomSuggestions() != null) {
            ☃.func_192572_a(SuggestionProviders.func_197497_a(☃.getCustomSuggestions()));
         }
      } else if (☃ instanceof LiteralCommandNode) {
         ☃.func_180714_a(((LiteralCommandNode)☃).getLiteral());
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_195511_a(this);
   }

   static class Entry {
      @Nullable
      private final ArgumentBuilder<ISuggestionProvider, ?> field_197726_a;
      private final byte field_197727_b;
      private final int field_197728_c;
      private final int[] field_197729_d;
      private CommandNode<ISuggestionProvider> field_197730_e;

      private Entry(@Nullable ArgumentBuilder<ISuggestionProvider, ?> var1, byte var2, int var3, int[] var4) {
         this.field_197726_a = ☃;
         this.field_197727_b = ☃;
         this.field_197728_c = ☃;
         this.field_197729_d = ☃;
      }

      public boolean func_197723_a(SPacketCommandList.Entry[] var1) {
         if (this.field_197730_e == null) {
            if (this.field_197726_a == null) {
               this.field_197730_e = new RootCommandNode<>();
            } else {
               if ((this.field_197727_b & 8) != 0) {
                  if (☃[this.field_197728_c].field_197730_e == null) {
                     return false;
                  }

                  this.field_197726_a.redirect(☃[this.field_197728_c].field_197730_e);
               }

               if ((this.field_197727_b & 4) != 0) {
                  this.field_197726_a.executes(var0 -> 0);
               }

               this.field_197730_e = this.field_197726_a.build();
            }
         }

         for(int ☃ : this.field_197729_d) {
            if (☃[☃].field_197730_e == null) {
               return false;
            }
         }

         for(int ☃ : this.field_197729_d) {
            CommandNode<ISuggestionProvider> ☃x = ☃[☃].field_197730_e;
            if (!(☃x instanceof RootCommandNode)) {
               this.field_197730_e.addChild(☃x);
            }
         }

         return true;
      }
   }
}
