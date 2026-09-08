package net.minecraft.world.entity.ai.village.poi;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class PoiType {
   private static final Supplier<Set<PoiType>> ALL_JOB_POI_TYPES = Suppliers.memoize(
      () -> (Set)Registry.VILLAGER_PROFESSION.stream().map(VillagerProfession::getJobPoiType).collect(Collectors.toSet())
   );
   public static final Predicate<PoiType> ALL_JOBS = var0 -> ((Set)ALL_JOB_POI_TYPES.get()).contains(var0);
   public static final Predicate<PoiType> ALL = var0 -> true;
   private static final Set<BlockState> BEDS = (Set<BlockState>)ImmutableList.of(
         Blocks.RED_BED,
         Blocks.BLACK_BED,
         Blocks.BLUE_BED,
         Blocks.BROWN_BED,
         Blocks.CYAN_BED,
         Blocks.GRAY_BED,
         Blocks.GREEN_BED,
         Blocks.LIGHT_BLUE_BED,
         Blocks.LIGHT_GRAY_BED,
         Blocks.LIME_BED,
         Blocks.MAGENTA_BED,
         Blocks.ORANGE_BED,
         Blocks.PINK_BED,
         Blocks.PURPLE_BED,
         Blocks.WHITE_BED,
         Blocks.YELLOW_BED
      )
      .stream()
      .flatMap(var0 -> var0.getStateDefinition().getPossibleStates().stream())
      .filter(var0 -> var0.getValue(BedBlock.PART) == BedPart.HEAD)
      .collect(ImmutableSet.toImmutableSet());
   private static final Set<BlockState> CAULDRONS = (Set<BlockState>)ImmutableList.of(
         Blocks.CAULDRON, Blocks.LAVA_CAULDRON, Blocks.WATER_CAULDRON, Blocks.POWDER_SNOW_CAULDRON
      )
      .stream()
      .flatMap(var0 -> var0.getStateDefinition().getPossibleStates().stream())
      .collect(ImmutableSet.toImmutableSet());
   private static final Map<BlockState, PoiType> TYPE_BY_STATE = Maps.<BlockState, PoiType>newHashMap();
   public static final PoiType UNEMPLOYED = register("unemployed", ImmutableSet.of(), 1, ALL_JOBS, 1);
   public static final PoiType ARMORER = register("armorer", getBlockStates(Blocks.BLAST_FURNACE), 1, 1);
   public static final PoiType BUTCHER = register("butcher", getBlockStates(Blocks.SMOKER), 1, 1);
   public static final PoiType CARTOGRAPHER = register("cartographer", getBlockStates(Blocks.CARTOGRAPHY_TABLE), 1, 1);
   public static final PoiType CLERIC = register("cleric", getBlockStates(Blocks.BREWING_STAND), 1, 1);
   public static final PoiType FARMER = register("farmer", getBlockStates(Blocks.COMPOSTER), 1, 1);
   public static final PoiType FISHERMAN = register("fisherman", getBlockStates(Blocks.BARREL), 1, 1);
   public static final PoiType FLETCHER = register("fletcher", getBlockStates(Blocks.FLETCHING_TABLE), 1, 1);
   public static final PoiType LEATHERWORKER = register("leatherworker", CAULDRONS, 1, 1);
   public static final PoiType LIBRARIAN = register("librarian", getBlockStates(Blocks.LECTERN), 1, 1);
   public static final PoiType MASON = register("mason", getBlockStates(Blocks.STONECUTTER), 1, 1);
   public static final PoiType NITWIT = register("nitwit", ImmutableSet.of(), 1, 1);
   public static final PoiType SHEPHERD = register("shepherd", getBlockStates(Blocks.LOOM), 1, 1);
   public static final PoiType TOOLSMITH = register("toolsmith", getBlockStates(Blocks.SMITHING_TABLE), 1, 1);
   public static final PoiType WEAPONSMITH = register("weaponsmith", getBlockStates(Blocks.GRINDSTONE), 1, 1);
   public static final PoiType HOME = register("home", BEDS, 1, 1);
   public static final PoiType MEETING = register("meeting", getBlockStates(Blocks.BELL), 32, 6);
   public static final PoiType BEEHIVE = register("beehive", getBlockStates(Blocks.BEEHIVE), 0, 1);
   public static final PoiType BEE_NEST = register("bee_nest", getBlockStates(Blocks.BEE_NEST), 0, 1);
   public static final PoiType NETHER_PORTAL = register("nether_portal", getBlockStates(Blocks.NETHER_PORTAL), 0, 1);
   public static final PoiType LODESTONE = register("lodestone", getBlockStates(Blocks.LODESTONE), 0, 1);
   public static final PoiType LIGHTNING_ROD = register("lightning_rod", getBlockStates(Blocks.LIGHTNING_ROD), 0, 1);
   protected static final Set<BlockState> ALL_STATES = new ObjectOpenHashSet<>(TYPE_BY_STATE.keySet());
   private final String name;
   private final Set<BlockState> matchingStates;
   private final int maxTickets;
   private final Predicate<PoiType> predicate;
   private final int validRange;

   private static Set<BlockState> getBlockStates(Block var0) {
      return ImmutableSet.copyOf(â˜ƒ.getStateDefinition().getPossibleStates());
   }

   private PoiType(String var1, Set<BlockState> var2, int var3, Predicate<PoiType> var4, int var5) {
      this.name = â˜ƒ;
      this.matchingStates = ImmutableSet.copyOf(â˜ƒ);
      this.maxTickets = â˜ƒ;
      this.predicate = â˜ƒ;
      this.validRange = â˜ƒ;
   }

   private PoiType(String var1, Set<BlockState> var2, int var3, int var4) {
      this.name = â˜ƒ;
      this.matchingStates = ImmutableSet.copyOf(â˜ƒ);
      this.maxTickets = â˜ƒ;
      this.predicate = var1x -> var1x == this;
      this.validRange = â˜ƒ;
   }

   public String getName() {
      return this.name;
   }

   public int getMaxTickets() {
      return this.maxTickets;
   }

   public Predicate<PoiType> getPredicate() {
      return this.predicate;
   }

   public boolean is(BlockState var1) {
      return this.matchingStates.contains(â˜ƒ);
   }

   public int getValidRange() {
      return this.validRange;
   }

   public String toString() {
      return this.name;
   }

   private static PoiType register(String var0, Set<BlockState> var1, int var2, int var3) {
      return registerBlockStates(Registry.register(Registry.POINT_OF_INTEREST_TYPE, new ResourceLocation(â˜ƒ), new PoiType(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)));
   }

   private static PoiType register(String var0, Set<BlockState> var1, int var2, Predicate<PoiType> var3, int var4) {
      return registerBlockStates(Registry.register(Registry.POINT_OF_INTEREST_TYPE, new ResourceLocation(â˜ƒ), new PoiType(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)));
   }

   private static PoiType registerBlockStates(PoiType var0) {
      â˜ƒ.matchingStates.forEach(var1 -> {
         PoiType â˜ƒ = (PoiType)TYPE_BY_STATE.put(var1, â˜ƒ);
         if (â˜ƒ != null) {
            throw (IllegalStateException)Util.pauseInIde(new IllegalStateException(String.format("%s is defined in too many tags", var1)));
         }
      });
      return â˜ƒ;
   }

   public static Optional<PoiType> forState(BlockState var0) {
      return Optional.ofNullable((PoiType)TYPE_BY_STATE.get(â˜ƒ));
   }
}
