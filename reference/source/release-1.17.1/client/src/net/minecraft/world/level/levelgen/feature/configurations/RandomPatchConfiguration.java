package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.blockplacers.BlockPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class RandomPatchConfiguration implements FeatureConfiguration {
   public static final Codec<RandomPatchConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(var0x -> var0x.stateProvider),
               BlockPlacer.CODEC.fieldOf("block_placer").forGetter(var0x -> var0x.blockPlacer),
               BlockState.CODEC
                  .listOf()
                  .fieldOf("whitelist")
                  .forGetter(var0x -> (List)var0x.whitelist.stream().map(Block::defaultBlockState).collect(Collectors.toList())),
               BlockState.CODEC.listOf().fieldOf("blacklist").forGetter(var0x -> ImmutableList.copyOf(var0x.blacklist)),
               ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(var0x -> var0x.tries),
               ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xspread").orElse(7).forGetter(var0x -> var0x.xspread),
               ExtraCodecs.NON_NEGATIVE_INT.fieldOf("yspread").orElse(3).forGetter(var0x -> var0x.yspread),
               ExtraCodecs.NON_NEGATIVE_INT.fieldOf("zspread").orElse(7).forGetter(var0x -> var0x.zspread),
               Codec.BOOL.fieldOf("can_replace").orElse(false).forGetter(var0x -> var0x.canReplace),
               Codec.BOOL.fieldOf("project").orElse(true).forGetter(var0x -> var0x.project),
               Codec.BOOL.fieldOf("need_water").orElse(false).forGetter(var0x -> var0x.needWater)
            )
            .apply(var0, RandomPatchConfiguration::new)
   );
   public final BlockStateProvider stateProvider;
   public final BlockPlacer blockPlacer;
   public final Set<Block> whitelist;
   public final Set<BlockState> blacklist;
   public final int tries;
   public final int xspread;
   public final int yspread;
   public final int zspread;
   public final boolean canReplace;
   public final boolean project;
   public final boolean needWater;

   private RandomPatchConfiguration(
      BlockStateProvider var1,
      BlockPlacer var2,
      List<BlockState> var3,
      List<BlockState> var4,
      int var5,
      int var6,
      int var7,
      int var8,
      boolean var9,
      boolean var10,
      boolean var11
   ) {
      this(
         â˜ƒ,
         â˜ƒ,
         (Set<Block>)â˜ƒ.stream().map(BlockBehaviour.BlockStateBase::getBlock).collect(Collectors.toSet()),
         ImmutableSet.copyOf(â˜ƒ),
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ
      );
   }

   RandomPatchConfiguration(
      BlockStateProvider var1,
      BlockPlacer var2,
      Set<Block> var3,
      Set<BlockState> var4,
      int var5,
      int var6,
      int var7,
      int var8,
      boolean var9,
      boolean var10,
      boolean var11
   ) {
      this.stateProvider = â˜ƒ;
      this.blockPlacer = â˜ƒ;
      this.whitelist = â˜ƒ;
      this.blacklist = â˜ƒ;
      this.tries = â˜ƒ;
      this.xspread = â˜ƒ;
      this.yspread = â˜ƒ;
      this.zspread = â˜ƒ;
      this.canReplace = â˜ƒ;
      this.project = â˜ƒ;
      this.needWater = â˜ƒ;
   }

   public static class GrassConfigurationBuilder {
      private final BlockStateProvider stateProvider;
      private final BlockPlacer blockPlacer;
      private Set<Block> whitelist = ImmutableSet.of();
      private Set<BlockState> blacklist = ImmutableSet.of();
      private int tries = 64;
      private int xspread = 7;
      private int yspread = 3;
      private int zspread = 7;
      private boolean canReplace;
      private boolean project = true;
      private boolean needWater;

      public GrassConfigurationBuilder(BlockStateProvider var1, BlockPlacer var2) {
         this.stateProvider = â˜ƒ;
         this.blockPlacer = â˜ƒ;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder whitelist(Set<Block> var1) {
         this.whitelist = â˜ƒ;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder blacklist(Set<BlockState> var1) {
         this.blacklist = â˜ƒ;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder tries(int var1) {
         this.tries = â˜ƒ;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder xspread(int var1) {
         this.xspread = â˜ƒ;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder yspread(int var1) {
         this.yspread = â˜ƒ;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder zspread(int var1) {
         this.zspread = â˜ƒ;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder canReplace() {
         this.canReplace = true;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder noProjection() {
         this.project = false;
         return this;
      }

      public RandomPatchConfiguration.GrassConfigurationBuilder needWater() {
         this.needWater = true;
         return this;
      }

      public RandomPatchConfiguration build() {
         return new RandomPatchConfiguration(
            this.stateProvider,
            this.blockPlacer,
            this.whitelist,
            this.blacklist,
            this.tries,
            this.xspread,
            this.yspread,
            this.zspread,
            this.canReplace,
            this.project,
            this.needWater
         );
      }
   }
}
