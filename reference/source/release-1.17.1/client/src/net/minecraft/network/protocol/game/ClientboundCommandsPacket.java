package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.annotation.Nullable;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundCommandsPacket implements Packet<ClientGamePacketListener> {
   private static final byte MASK_TYPE = 3;
   private static final byte FLAG_EXECUTABLE = 4;
   private static final byte FLAG_REDIRECT = 8;
   private static final byte FLAG_CUSTOM_SUGGESTIONS = 16;
   private static final byte TYPE_ROOT = 0;
   private static final byte TYPE_LITERAL = 1;
   private static final byte TYPE_ARGUMENT = 2;
   private final RootCommandNode<SharedSuggestionProvider> root;

   public ClientboundCommandsPacket(RootCommandNode<SharedSuggestionProvider> var1) {
      this.root = â˜ƒ;
   }

   public ClientboundCommandsPacket(FriendlyByteBuf var1) {
      List<ClientboundCommandsPacket.Entry> â˜ƒ = â˜ƒ.readList(ClientboundCommandsPacket::readNode);
      resolveEntries(â˜ƒ);
      int â˜ƒx = â˜ƒ.readVarInt();
      this.root = (RootCommandNode)((ClientboundCommandsPacket.Entry)â˜ƒ.get(â˜ƒx)).node;
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      Object2IntMap<CommandNode<SharedSuggestionProvider>> â˜ƒ = enumerateNodes(this.root);
      List<CommandNode<SharedSuggestionProvider>> â˜ƒx = getNodesInIdOrder(â˜ƒ);
      â˜ƒ.writeCollection(â˜ƒx, (var1x, var2x) -> writeNode(var1x, var2x, â˜ƒ));
      â˜ƒ.writeVarInt(â˜ƒ.get(this.root));
   }

   private static void resolveEntries(List<ClientboundCommandsPacket.Entry> var0) {
      List<ClientboundCommandsPacket.Entry> â˜ƒ = Lists.<ClientboundCommandsPacket.Entry>newArrayList(â˜ƒ);

      while(!â˜ƒ.isEmpty()) {
         boolean â˜ƒx = â˜ƒ.removeIf(var1x -> var1x.build(â˜ƒ));
         if (!â˜ƒx) {
            throw new IllegalStateException("Server sent an impossible command tree");
         }
      }
   }

   private static Object2IntMap<CommandNode<SharedSuggestionProvider>> enumerateNodes(RootCommandNode<SharedSuggestionProvider> var0) {
      Object2IntMap<CommandNode<SharedSuggestionProvider>> â˜ƒ = new Object2IntOpenHashMap<>();
      Queue<CommandNode<SharedSuggestionProvider>> â˜ƒx = Queues.<CommandNode<SharedSuggestionProvider>>newArrayDeque();
      â˜ƒx.add(â˜ƒ);

      CommandNode<SharedSuggestionProvider> â˜ƒ;
      while((â˜ƒ = (CommandNode)â˜ƒx.poll()) != null) {
         if (!â˜ƒ.containsKey(â˜ƒ)) {
            int â˜ƒxx = â˜ƒ.size();
            â˜ƒ.put(â˜ƒ, â˜ƒxx);
            â˜ƒx.addAll(â˜ƒ.getChildren());
            if (â˜ƒ.getRedirect() != null) {
               â˜ƒx.add(â˜ƒ.getRedirect());
            }
         }
      }

      return â˜ƒ;
   }

   private static List<CommandNode<SharedSuggestionProvider>> getNodesInIdOrder(Object2IntMap<CommandNode<SharedSuggestionProvider>> var0) {
      ObjectArrayList<CommandNode<SharedSuggestionProvider>> â˜ƒ = new ObjectArrayList<>(â˜ƒ.size());
      â˜ƒ.size(â˜ƒ.size());

      for(Object2IntMap.Entry<CommandNode<SharedSuggestionProvider>> â˜ƒx : Object2IntMaps.fastIterable(â˜ƒ)) {
         â˜ƒ.set(â˜ƒx.getIntValue(), (CommandNode<SharedSuggestionProvider>)â˜ƒx.getKey());
      }

      return â˜ƒ;
   }

   private static ClientboundCommandsPacket.Entry readNode(FriendlyByteBuf var0) {
      byte â˜ƒ = â˜ƒ.readByte();
      int[] â˜ƒx = â˜ƒ.readVarIntArray();
      int â˜ƒxx = (â˜ƒ & 8) != 0 ? â˜ƒ.readVarInt() : 0;
      ArgumentBuilder<SharedSuggestionProvider, ?> â˜ƒxxx = createBuilder(â˜ƒ, â˜ƒ);
      return new ClientboundCommandsPacket.Entry(â˜ƒxxx, â˜ƒ, â˜ƒxx, â˜ƒx);
   }

   @Nullable
   private static ArgumentBuilder<SharedSuggestionProvider, ?> createBuilder(FriendlyByteBuf var0, byte var1) {
      int â˜ƒ = â˜ƒ & 3;
      if (â˜ƒ == 2) {
         String â˜ƒx = â˜ƒ.readUtf();
         ArgumentType<?> â˜ƒxx = ArgumentTypes.deserialize(â˜ƒ);
         if (â˜ƒxx == null) {
            return null;
         } else {
            RequiredArgumentBuilder<SharedSuggestionProvider, ?> â˜ƒx = RequiredArgumentBuilder.argument(â˜ƒx, â˜ƒxx);
            if ((â˜ƒ & 16) != 0) {
               â˜ƒx.suggests(SuggestionProviders.getProvider(â˜ƒ.readResourceLocation()));
            }

            return â˜ƒx;
         }
      } else {
         return â˜ƒ == 1 ? LiteralArgumentBuilder.literal(â˜ƒ.readUtf()) : null;
      }
   }

   private static void writeNode(FriendlyByteBuf var0, CommandNode<SharedSuggestionProvider> var1, Map<CommandNode<SharedSuggestionProvider>, Integer> var2) {
      byte â˜ƒ = 0;
      if (â˜ƒ.getRedirect() != null) {
         â˜ƒ = (byte)(â˜ƒ | 8);
      }

      if (â˜ƒ.getCommand() != null) {
         â˜ƒ = (byte)(â˜ƒ | 4);
      }

      if (â˜ƒ instanceof RootCommandNode) {
         â˜ƒ = (byte)(â˜ƒ | 0);
      } else if (â˜ƒ instanceof ArgumentCommandNode) {
         â˜ƒ = (byte)(â˜ƒ | 2);
         if (((ArgumentCommandNode)â˜ƒ).getCustomSuggestions() != null) {
            â˜ƒ = (byte)(â˜ƒ | 16);
         }
      } else {
         if (!(â˜ƒ instanceof LiteralCommandNode)) {
            throw new UnsupportedOperationException("Unknown node type " + â˜ƒ);
         }

         â˜ƒ = (byte)(â˜ƒ | 1);
      }

      â˜ƒ.writeByte(â˜ƒ);
      â˜ƒ.writeVarInt(â˜ƒ.getChildren().size());

      for(CommandNode<SharedSuggestionProvider> â˜ƒ : â˜ƒ.getChildren()) {
         â˜ƒ.writeVarInt(â˜ƒ.get(â˜ƒ));
      }

      if (â˜ƒ.getRedirect() != null) {
         â˜ƒ.writeVarInt(â˜ƒ.get(â˜ƒ.getRedirect()));
      }

      if (â˜ƒ instanceof ArgumentCommandNode â˜ƒ) {
         â˜ƒ.writeUtf(â˜ƒ.getName());
         ArgumentTypes.serialize(â˜ƒ, â˜ƒ.getType());
         if (â˜ƒ.getCustomSuggestions() != null) {
            â˜ƒ.writeResourceLocation(SuggestionProviders.getName(â˜ƒ.getCustomSuggestions()));
         }
      } else if (â˜ƒ instanceof LiteralCommandNode) {
         â˜ƒ.writeUtf(((LiteralCommandNode)â˜ƒ).getLiteral());
      }
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleCommands(this);
   }

   public RootCommandNode<SharedSuggestionProvider> getRoot() {
      return this.root;
   }

   static class Entry {
      @Nullable
      private final ArgumentBuilder<SharedSuggestionProvider, ?> builder;
      private final byte flags;
      private final int redirect;
      private final int[] children;
      @Nullable
      CommandNode<SharedSuggestionProvider> node;

      Entry(@Nullable ArgumentBuilder<SharedSuggestionProvider, ?> var1, byte var2, int var3, int[] var4) {
         this.builder = â˜ƒ;
         this.flags = â˜ƒ;
         this.redirect = â˜ƒ;
         this.children = â˜ƒ;
      }

      public boolean build(List<ClientboundCommandsPacket.Entry> var1) {
         if (this.node == null) {
            if (this.builder == null) {
               this.node = new RootCommandNode<>();
            } else {
               if ((this.flags & 8) != 0) {
                  if (((ClientboundCommandsPacket.Entry)â˜ƒ.get(this.redirect)).node == null) {
                     return false;
                  }

                  this.builder.redirect(((ClientboundCommandsPacket.Entry)â˜ƒ.get(this.redirect)).node);
               }

               if ((this.flags & 4) != 0) {
                  this.builder.executes(var0 -> 0);
               }

               this.node = this.builder.build();
            }
         }

         for(int â˜ƒ : this.children) {
            if (((ClientboundCommandsPacket.Entry)â˜ƒ.get(â˜ƒ)).node == null) {
               return false;
            }
         }

         for(int â˜ƒ : this.children) {
            CommandNode<SharedSuggestionProvider> â˜ƒx = ((ClientboundCommandsPacket.Entry)â˜ƒ.get(â˜ƒ)).node;
            if (!(â˜ƒx instanceof RootCommandNode)) {
               this.node.addChild(â˜ƒx);
            }
         }

         return true;
      }
   }
}
