package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class ItemParticleOption implements ParticleOptions {
   public static final ParticleOptions.Deserializer<ItemParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<ItemParticleOption>() {
      public ItemParticleOption fromCommand(ParticleType<ItemParticleOption> var1, StringReader var2) throws CommandSyntaxException {
         â˜ƒ.expect(' ');
         ItemParser â˜ƒ = new ItemParser(â˜ƒ, false).parse();
         ItemStack â˜ƒx = new ItemInput(â˜ƒ.getItem(), â˜ƒ.getNbt()).createItemStack(1, false);
         return new ItemParticleOption(â˜ƒ, â˜ƒx);
      }

      public ItemParticleOption fromNetwork(ParticleType<ItemParticleOption> var1, FriendlyByteBuf var2) {
         return new ItemParticleOption(â˜ƒ, â˜ƒ.readItem());
      }
   };
   private final ParticleType<ItemParticleOption> type;
   private final ItemStack itemStack;

   public static Codec<ItemParticleOption> codec(ParticleType<ItemParticleOption> var0) {
      return ItemStack.CODEC.xmap(var1 -> new ItemParticleOption(â˜ƒ, var1), var0x -> var0x.itemStack);
   }

   public ItemParticleOption(ParticleType<ItemParticleOption> var1, ItemStack var2) {
      this.type = â˜ƒ;
      this.itemStack = â˜ƒ;
   }

   @Override
   public void writeToNetwork(FriendlyByteBuf var1) {
      â˜ƒ.writeItem(this.itemStack);
   }

   @Override
   public String writeToString() {
      return Registry.PARTICLE_TYPE.getKey(this.getType()) + " " + new ItemInput(this.itemStack.getItem(), this.itemStack.getTag()).serialize();
   }

   @Override
   public ParticleType<ItemParticleOption> getType() {
      return this.type;
   }

   public ItemStack getItem() {
      return this.itemStack;
   }
}
