package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.GameRules;

public class ServerFunctionManager {
   private static final Component NO_RECURSIVE_TRACES = new TranslatableComponent("commands.debug.function.noRecursion");
   private static final ResourceLocation TICK_FUNCTION_TAG = new ResourceLocation("tick");
   private static final ResourceLocation LOAD_FUNCTION_TAG = new ResourceLocation("load");
   final MinecraftServer server;
   @Nullable
   private ServerFunctionManager.ExecutionContext context;
   private List<CommandFunction> ticking = ImmutableList.of();
   private boolean postReload;
   private ServerFunctionLibrary library;

   public ServerFunctionManager(MinecraftServer var1, ServerFunctionLibrary var2) {
      this.server = â˜ƒ;
      this.library = â˜ƒ;
      this.postReload(â˜ƒ);
   }

   public int getCommandLimit() {
      return this.server.getGameRules().getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH);
   }

   public CommandDispatcher<CommandSourceStack> getDispatcher() {
      return this.server.getCommands().getDispatcher();
   }

   public void tick() {
      this.executeTagFunctions(this.ticking, TICK_FUNCTION_TAG);
      if (this.postReload) {
         this.postReload = false;
         Collection<CommandFunction> â˜ƒ = this.library.getTags().getTagOrEmpty(LOAD_FUNCTION_TAG).getValues();
         this.executeTagFunctions(â˜ƒ, LOAD_FUNCTION_TAG);
      }
   }

   private void executeTagFunctions(Collection<CommandFunction> var1, ResourceLocation var2) {
      this.server.getProfiler().push(â˜ƒ::toString);

      for(CommandFunction â˜ƒ : â˜ƒ) {
         this.execute(â˜ƒ, this.getGameLoopSender());
      }

      this.server.getProfiler().pop();
   }

   public int execute(CommandFunction var1, CommandSourceStack var2) {
      return this.execute(â˜ƒ, â˜ƒ, null);
   }

   public int execute(CommandFunction var1, CommandSourceStack var2, @Nullable ServerFunctionManager.TraceCallbacks var3) {
      if (this.context != null) {
         if (â˜ƒ != null) {
            this.context.reportError(NO_RECURSIVE_TRACES.getString());
            return 0;
         } else {
            this.context.delayFunctionCall(â˜ƒ, â˜ƒ);
            return 0;
         }
      } else {
         int var4;
         try {
            this.context = new ServerFunctionManager.ExecutionContext(â˜ƒ);
            var4 = this.context.runTopCommand(â˜ƒ, â˜ƒ);
         } finally {
            this.context = null;
         }

         return var4;
      }
   }

   public void replaceLibrary(ServerFunctionLibrary var1) {
      this.library = â˜ƒ;
      this.postReload(â˜ƒ);
   }

   private void postReload(ServerFunctionLibrary var1) {
      this.ticking = ImmutableList.copyOf(â˜ƒ.getTags().getTagOrEmpty(TICK_FUNCTION_TAG).getValues());
      this.postReload = true;
   }

   public CommandSourceStack getGameLoopSender() {
      return this.server.createCommandSourceStack().withPermission(2).withSuppressedOutput();
   }

   public Optional<CommandFunction> get(ResourceLocation var1) {
      return this.library.getFunction(â˜ƒ);
   }

   public Tag<CommandFunction> getTag(ResourceLocation var1) {
      return this.library.getTag(â˜ƒ);
   }

   public Iterable<ResourceLocation> getFunctionNames() {
      return this.library.getFunctions().keySet();
   }

   public Iterable<ResourceLocation> getTagNames() {
      return this.library.getTags().getAvailableTags();
   }

   class ExecutionContext {
      private int depth;
      @Nullable
      private final ServerFunctionManager.TraceCallbacks tracer;
      private final Deque<ServerFunctionManager.QueuedCommand> commandQueue = Queues.<ServerFunctionManager.QueuedCommand>newArrayDeque();
      private final List<ServerFunctionManager.QueuedCommand> nestedCalls = Lists.<ServerFunctionManager.QueuedCommand>newArrayList();

      ExecutionContext(@Nullable ServerFunctionManager.TraceCallbacks var2) {
         this.tracer = â˜ƒ;
      }

      void delayFunctionCall(CommandFunction var1, CommandSourceStack var2) {
         int â˜ƒ = ServerFunctionManager.this.getCommandLimit();
         if (this.commandQueue.size() + this.nestedCalls.size() < â˜ƒ) {
            this.nestedCalls.add(new ServerFunctionManager.QueuedCommand(â˜ƒ, this.depth, new CommandFunction.FunctionEntry(â˜ƒ)));
         }
      }

      int runTopCommand(CommandFunction var1, CommandSourceStack var2) {
         int â˜ƒ = ServerFunctionManager.this.getCommandLimit();
         int â˜ƒx = 0;
         CommandFunction.Entry[] â˜ƒxx = â˜ƒ.getEntries();

         for(int â˜ƒxxx = â˜ƒxx.length - 1; â˜ƒxxx >= 0; --â˜ƒxxx) {
            this.commandQueue.push(new ServerFunctionManager.QueuedCommand(â˜ƒ, 0, â˜ƒxx[â˜ƒxxx]));
         }

         while(!this.commandQueue.isEmpty()) {
            try {
               ServerFunctionManager.QueuedCommand â˜ƒxxx = (ServerFunctionManager.QueuedCommand)this.commandQueue.removeFirst();
               ServerFunctionManager.this.server.getProfiler().push(â˜ƒxxx::toString);
               this.depth = â˜ƒxxx.depth;
               â˜ƒxxx.execute(ServerFunctionManager.this, this.commandQueue, â˜ƒ, this.tracer);
               if (!this.nestedCalls.isEmpty()) {
                  Lists.reverse(this.nestedCalls).forEach(this.commandQueue::addFirst);
                  this.nestedCalls.clear();
               }
            } finally {
               ServerFunctionManager.this.server.getProfiler().pop();
            }

            if (++â˜ƒx >= â˜ƒ) {
               return â˜ƒx;
            }
         }

         return â˜ƒx;
      }

      public void reportError(String var1) {
         if (this.tracer != null) {
            this.tracer.onError(this.depth, â˜ƒ);
         }
      }
   }

   public static class QueuedCommand {
      private final CommandSourceStack sender;
      final int depth;
      private final CommandFunction.Entry entry;

      public QueuedCommand(CommandSourceStack var1, int var2, CommandFunction.Entry var3) {
         this.sender = â˜ƒ;
         this.depth = â˜ƒ;
         this.entry = â˜ƒ;
      }

      public void execute(
         ServerFunctionManager var1, Deque<ServerFunctionManager.QueuedCommand> var2, int var3, @Nullable ServerFunctionManager.TraceCallbacks var4
      ) {
         try {
            this.entry.execute(â˜ƒ, this.sender, â˜ƒ, â˜ƒ, this.depth, â˜ƒ);
         } catch (CommandSyntaxException var6) {
            if (â˜ƒ != null) {
               â˜ƒ.onError(this.depth, var6.getRawMessage().getString());
            }
         } catch (Exception var7) {
            if (â˜ƒ != null) {
               â˜ƒ.onError(this.depth, var7.getMessage());
            }
         }
      }

      public String toString() {
         return this.entry.toString();
      }
   }

   public interface TraceCallbacks {
      void onCommand(int var1, String var2);

      void onReturn(int var1, String var2, int var3);

      void onError(int var1, String var2);

      void onCall(int var1, ResourceLocation var2, int var3);
   }
}
