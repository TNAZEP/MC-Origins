package net.minecraft.gametest.framework;

import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class GameTestHelper {
   private final GameTestInfo testInfo;
   private boolean finalCheckAdded;

   public GameTestHelper(GameTestInfo var1) {
      this.testInfo = â˜ƒ;
   }

   public ServerLevel getLevel() {
      return this.testInfo.getLevel();
   }

   public BlockState getBlockState(BlockPos var1) {
      return this.getLevel().getBlockState(this.absolutePos(â˜ƒ));
   }

   @Nullable
   public BlockEntity getBlockEntity(BlockPos var1) {
      return this.getLevel().getBlockEntity(this.absolutePos(â˜ƒ));
   }

   public void killAllEntities() {
      AABB â˜ƒ = this.getBounds();
      List<Entity> â˜ƒx = this.getLevel().getEntitiesOfClass(Entity.class, â˜ƒ.inflate(1.0), var0 -> !(var0 instanceof Player));
      â˜ƒx.forEach(Entity::kill);
   }

   public ItemEntity spawnItem(Item var1, float var2, float var3, float var4) {
      ServerLevel â˜ƒ = this.getLevel();
      Vec3 â˜ƒx = this.absoluteVec(new Vec3((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ));
      ItemEntity â˜ƒxx = new ItemEntity(â˜ƒ, â˜ƒx.x, â˜ƒx.y, â˜ƒx.z, new ItemStack(â˜ƒ, 1));
      â˜ƒxx.setDeltaMovement(0.0, 0.0, 0.0);
      â˜ƒ.addFreshEntity(â˜ƒxx);
      return â˜ƒxx;
   }

   public <E extends Entity> E spawn(EntityType<E> var1, BlockPos var2) {
      return this.spawn(â˜ƒ, Vec3.atBottomCenterOf(â˜ƒ));
   }

   public <E extends Entity> E spawn(EntityType<E> var1, Vec3 var2) {
      ServerLevel â˜ƒ = this.getLevel();
      E â˜ƒx = â˜ƒ.create(â˜ƒ);
      if (â˜ƒx instanceof Mob) {
         ((Mob)â˜ƒx).setPersistenceRequired();
      }

      Vec3 â˜ƒ = this.absoluteVec(â˜ƒ);
      â˜ƒx.moveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒx.getYRot(), â˜ƒx.getXRot());
      â˜ƒ.addFreshEntity(â˜ƒx);
      return â˜ƒx;
   }

   public <E extends Entity> E spawn(EntityType<E> var1, int var2, int var3, int var4) {
      return this.spawn(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public <E extends Entity> E spawn(EntityType<E> var1, float var2, float var3, float var4) {
      return this.spawn(â˜ƒ, new Vec3((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ));
   }

   public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> var1, BlockPos var2) {
      E â˜ƒ = this.spawn(â˜ƒ, â˜ƒ);
      â˜ƒ.removeFreeWill();
      return â˜ƒ;
   }

   public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> var1, int var2, int var3, int var4) {
      return this.spawnWithNoFreeWill(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> var1, Vec3 var2) {
      E â˜ƒ = this.spawn(â˜ƒ, â˜ƒ);
      â˜ƒ.removeFreeWill();
      return â˜ƒ;
   }

   public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> var1, float var2, float var3, float var4) {
      return this.spawnWithNoFreeWill(â˜ƒ, new Vec3((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ));
   }

   public GameTestSequence walkTo(Mob var1, BlockPos var2, float var3) {
      return this.startSequence().thenExecuteAfter(2, () -> {
         Path â˜ƒ = â˜ƒ.getNavigation().createPath(this.absolutePos(â˜ƒ), 0);
         â˜ƒ.getNavigation().moveTo(â˜ƒ, (double)â˜ƒ);
      });
   }

   public void pressButton(int var1, int var2, int var3) {
      this.pressButton(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void pressButton(BlockPos var1) {
      this.assertBlockState(â˜ƒ, var0 -> var0.is(BlockTags.BUTTONS), () -> "Expected button");
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      BlockState â˜ƒx = this.getLevel().getBlockState(â˜ƒ);
      ButtonBlock â˜ƒxx = (ButtonBlock)â˜ƒx.getBlock();
      â˜ƒxx.press(â˜ƒx, this.getLevel(), â˜ƒ);
   }

   public void useBlock(BlockPos var1) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      BlockState â˜ƒx = this.getLevel().getBlockState(â˜ƒ);
      â˜ƒx.use(this.getLevel(), this.makeMockPlayer(), InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.atCenterOf(â˜ƒ), Direction.NORTH, â˜ƒ, true));
   }

   public LivingEntity makeAboutToDrown(LivingEntity var1) {
      â˜ƒ.setAirSupply(0);
      â˜ƒ.setHealth(0.25F);
      return â˜ƒ;
   }

   public Player makeMockPlayer() {
      return new Player(this.getLevel(), BlockPos.ZERO, 0.0F, new GameProfile(UUID.randomUUID(), "test-mock-player")) {
         @Override
         public boolean isSpectator() {
            return false;
         }

         @Override
         public boolean isCreative() {
            return true;
         }
      };
   }

   public void pullLever(int var1, int var2, int var3) {
      this.pullLever(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void pullLever(BlockPos var1) {
      this.assertBlockPresent(Blocks.LEVER, â˜ƒ);
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      BlockState â˜ƒx = this.getLevel().getBlockState(â˜ƒ);
      LeverBlock â˜ƒxx = (LeverBlock)â˜ƒx.getBlock();
      â˜ƒxx.pull(â˜ƒx, this.getLevel(), â˜ƒ);
   }

   public void pulseRedstone(BlockPos var1, long var2) {
      this.setBlock(â˜ƒ, Blocks.REDSTONE_BLOCK);
      this.runAfterDelay(â˜ƒ, () -> this.setBlock(â˜ƒ, Blocks.AIR));
   }

   public void destroyBlock(BlockPos var1) {
      this.getLevel().destroyBlock(this.absolutePos(â˜ƒ), false, null);
   }

   public void setBlock(int var1, int var2, int var3, Block var4) {
      this.setBlock(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
   }

   public void setBlock(int var1, int var2, int var3, BlockState var4) {
      this.setBlock(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
   }

   public void setBlock(BlockPos var1, Block var2) {
      this.setBlock(â˜ƒ, â˜ƒ.defaultBlockState());
   }

   public void setBlock(BlockPos var1, BlockState var2) {
      this.getLevel().setBlock(this.absolutePos(â˜ƒ), â˜ƒ, 3);
   }

   public void setNight() {
      this.setDayTime(13000);
   }

   public void setDayTime(int var1) {
      this.getLevel().setDayTime((long)â˜ƒ);
   }

   public void assertBlockPresent(Block var1, int var2, int var3, int var4) {
      this.assertBlockPresent(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void assertBlockPresent(Block var1, BlockPos var2) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ);
      this.assertBlock(â˜ƒ, var2x -> â˜ƒ.is(â˜ƒ), "Expected " + â˜ƒ.getName().getString() + ", got " + â˜ƒ.getBlock().getName().getString());
   }

   public void assertBlockNotPresent(Block var1, int var2, int var3, int var4) {
      this.assertBlockNotPresent(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void assertBlockNotPresent(Block var1, BlockPos var2) {
      this.assertBlock(â˜ƒ, var3 -> !this.getBlockState(â˜ƒ).is(â˜ƒ), "Did not expect " + â˜ƒ.getName().getString());
   }

   public void succeedWhenBlockPresent(Block var1, int var2, int var3, int var4) {
      this.succeedWhenBlockPresent(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void succeedWhenBlockPresent(Block var1, BlockPos var2) {
      this.succeedWhen(() -> this.assertBlockPresent(â˜ƒ, â˜ƒ));
   }

   public void assertBlock(BlockPos var1, Predicate<Block> var2, String var3) {
      this.assertBlock(â˜ƒ, â˜ƒ, (Supplier<String>)(() -> â˜ƒ));
   }

   public void assertBlock(BlockPos var1, Predicate<Block> var2, Supplier<String> var3) {
      this.assertBlockState(â˜ƒ, var1x -> â˜ƒ.test(var1x.getBlock()), â˜ƒ);
   }

   public <T extends Comparable<T>> void assertBlockProperty(BlockPos var1, Property<T> var2, T var3) {
      this.assertBlockState(
         â˜ƒ, var2x -> var2x.hasProperty(â˜ƒ) && var2x.<T>getValue(â˜ƒ).equals(â˜ƒ), () -> "Expected property " + â˜ƒ.getName() + " to be " + â˜ƒ
      );
   }

   public <T extends Comparable<T>> void assertBlockProperty(BlockPos var1, Property<T> var2, Predicate<T> var3, String var4) {
      this.assertBlockState(â˜ƒ, var2x -> â˜ƒ.test(var2x.<T>getValue(â˜ƒ)), () -> â˜ƒ);
   }

   public void assertBlockState(BlockPos var1, Predicate<BlockState> var2, Supplier<String> var3) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ);
      if (!â˜ƒ.test(â˜ƒ)) {
         throw new GameTestAssertPosException((String)â˜ƒ.get(), this.absolutePos(â˜ƒ), â˜ƒ, this.testInfo.getTick());
      }
   }

   public void assertEntityPresent(EntityType<?> var1) {
      List<? extends Entity> â˜ƒ = this.getLevel().getEntities(â˜ƒ, this.getBounds(), Entity::isAlive);
      if (â˜ƒ.isEmpty()) {
         throw new GameTestAssertException("Expected " + â˜ƒ.toShortString() + " to exist");
      }
   }

   public void assertEntityPresent(EntityType<?> var1, int var2, int var3, int var4) {
      this.assertEntityPresent(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void assertEntityPresent(EntityType<?> var1, BlockPos var2) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      List<? extends Entity> â˜ƒx = this.getLevel().getEntities(â˜ƒ, new AABB(â˜ƒ), Entity::isAlive);
      if (â˜ƒx.isEmpty()) {
         throw new GameTestAssertPosException("Expected " + â˜ƒ.toShortString(), â˜ƒ, â˜ƒ, this.testInfo.getTick());
      }
   }

   public void assertEntityPresent(EntityType<?> var1, BlockPos var2, double var3) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      List<? extends Entity> â˜ƒx = this.getLevel().getEntities(â˜ƒ, new AABB(â˜ƒ).inflate(â˜ƒ), Entity::isAlive);
      if (â˜ƒx.isEmpty()) {
         throw new GameTestAssertPosException("Expected " + â˜ƒ.toShortString(), â˜ƒ, â˜ƒ, this.testInfo.getTick());
      }
   }

   public void assertEntityInstancePresent(Entity var1, int var2, int var3, int var4) {
      this.assertEntityInstancePresent(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void assertEntityInstancePresent(Entity var1, BlockPos var2) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      List<? extends Entity> â˜ƒx = this.getLevel().getEntities(â˜ƒ.getType(), new AABB(â˜ƒ), Entity::isAlive);
      â˜ƒx.stream()
         .filter(var1x -> var1x == â˜ƒ)
         .findFirst()
         .orElseThrow(() -> new GameTestAssertPosException("Expected " + â˜ƒ.getType().toShortString(), â˜ƒ, â˜ƒ, this.testInfo.getTick()));
   }

   public void assertItemEntityCountIs(Item var1, BlockPos var2, double var3, int var5) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      List<ItemEntity> â˜ƒx = this.getLevel().getEntities(EntityType.ITEM, new AABB(â˜ƒ).inflate(â˜ƒ), Entity::isAlive);
      int â˜ƒxx = 0;

      for(Entity â˜ƒxxx : â˜ƒx) {
         ItemEntity â˜ƒxxxx = (ItemEntity)â˜ƒxxx;
         if (â˜ƒxxxx.getItem().getItem().equals(â˜ƒ)) {
            â˜ƒxx += â˜ƒxxxx.getItem().getCount();
         }
      }

      if (â˜ƒxx != â˜ƒ) {
         throw new GameTestAssertPosException(
            "Expected " + â˜ƒ + " " + â˜ƒ.getDescription().getString() + " items to exist (found " + â˜ƒxx + ")", â˜ƒ, â˜ƒ, this.testInfo.getTick()
         );
      }
   }

   public void assertItemEntityPresent(Item var1, BlockPos var2, double var3) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);

      for(Entity â˜ƒx : this.getLevel().getEntities(EntityType.ITEM, new AABB(â˜ƒ).inflate(â˜ƒ), Entity::isAlive)) {
         ItemEntity â˜ƒxx = (ItemEntity)â˜ƒx;
         if (â˜ƒxx.getItem().getItem().equals(â˜ƒ)) {
            return;
         }
      }

      throw new GameTestAssertPosException("Expected " + â˜ƒ.getDescription().getString() + " item", â˜ƒ, â˜ƒ, this.testInfo.getTick());
   }

   public void assertEntityNotPresent(EntityType<?> var1) {
      List<? extends Entity> â˜ƒ = this.getLevel().getEntities(â˜ƒ, this.getBounds(), Entity::isAlive);
      if (!â˜ƒ.isEmpty()) {
         throw new GameTestAssertException("Did not expect " + â˜ƒ.toShortString() + " to exist");
      }
   }

   public void assertEntityNotPresent(EntityType<?> var1, int var2, int var3, int var4) {
      this.assertEntityNotPresent(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void assertEntityNotPresent(EntityType<?> var1, BlockPos var2) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      List<? extends Entity> â˜ƒx = this.getLevel().getEntities(â˜ƒ, new AABB(â˜ƒ), Entity::isAlive);
      if (!â˜ƒx.isEmpty()) {
         throw new GameTestAssertPosException("Did not expect " + â˜ƒ.toShortString(), â˜ƒ, â˜ƒ, this.testInfo.getTick());
      }
   }

   public void assertEntityTouching(EntityType<?> var1, double var2, double var4, double var6) {
      Vec3 â˜ƒ = new Vec3(â˜ƒ, â˜ƒ, â˜ƒ);
      Vec3 â˜ƒx = this.absoluteVec(â˜ƒ);
      Predicate<? super Entity> â˜ƒxx = var1x -> var1x.getBoundingBox().intersects(â˜ƒ, â˜ƒ);
      List<? extends Entity> â˜ƒxxx = this.getLevel().getEntities(â˜ƒ, this.getBounds(), â˜ƒxx);
      if (â˜ƒxxx.isEmpty()) {
         throw new GameTestAssertException("Expected " + â˜ƒ.toShortString() + " to touch " + â˜ƒx + " (relative " + â˜ƒ + ")");
      }
   }

   public void assertEntityNotTouching(EntityType<?> var1, double var2, double var4, double var6) {
      Vec3 â˜ƒ = new Vec3(â˜ƒ, â˜ƒ, â˜ƒ);
      Vec3 â˜ƒx = this.absoluteVec(â˜ƒ);
      Predicate<? super Entity> â˜ƒxx = var1x -> !var1x.getBoundingBox().intersects(â˜ƒ, â˜ƒ);
      List<? extends Entity> â˜ƒxxx = this.getLevel().getEntities(â˜ƒ, this.getBounds(), â˜ƒxx);
      if (â˜ƒxxx.isEmpty()) {
         throw new GameTestAssertException("Did not expect " + â˜ƒ.toShortString() + " to touch " + â˜ƒx + " (relative " + â˜ƒ + ")");
      }
   }

   public <E extends Entity, T> void assertEntityData(BlockPos var1, EntityType<E> var2, Function<? super E, T> var3, @Nullable T var4) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      List<E> â˜ƒx = this.getLevel().getEntities(â˜ƒ, new AABB(â˜ƒ), Entity::isAlive);
      if (â˜ƒx.isEmpty()) {
         throw new GameTestAssertPosException("Expected " + â˜ƒ.toShortString(), â˜ƒ, â˜ƒ, this.testInfo.getTick());
      } else {
         for(E â˜ƒ : â˜ƒx) {
            T â˜ƒx = (T)â˜ƒ.apply(â˜ƒ);
            if (â˜ƒx == null) {
               if (â˜ƒ != null) {
                  throw new GameTestAssertException("Expected entity data to be: " + â˜ƒ + ", but was: " + â˜ƒx);
               }
            } else if (!â˜ƒx.equals(â˜ƒ)) {
               throw new GameTestAssertException("Expected entity data to be: " + â˜ƒ + ", but was: " + â˜ƒx);
            }
         }
      }
   }

   public void assertContainerEmpty(BlockPos var1) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      BlockEntity â˜ƒx = this.getLevel().getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof BaseContainerBlockEntity && !((BaseContainerBlockEntity)â˜ƒx).isEmpty()) {
         throw new GameTestAssertException("Container should be empty");
      }
   }

   public void assertContainerContains(BlockPos var1, Item var2) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      BlockEntity â˜ƒx = this.getLevel().getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof BaseContainerBlockEntity && ((BaseContainerBlockEntity)â˜ƒx).countItem(â˜ƒ) != 1) {
         throw new GameTestAssertException("Container should contain: " + â˜ƒ);
      }
   }

   public void assertSameBlockStates(BoundingBox var1, BlockPos var2) {
      BlockPos.betweenClosedStream(â˜ƒ).forEach(var3 -> {
         BlockPos â˜ƒ = â˜ƒ.offset(var3.getX() - â˜ƒ.minX(), var3.getY() - â˜ƒ.minY(), var3.getZ() - â˜ƒ.minZ());
         this.assertSameBlockState(var3, â˜ƒ);
      });
   }

   public void assertSameBlockState(BlockPos var1, BlockPos var2) {
      BlockState â˜ƒ = this.getBlockState(â˜ƒ);
      BlockState â˜ƒx = this.getBlockState(â˜ƒ);
      if (â˜ƒ != â˜ƒx) {
         this.fail("Incorrect state. Expected " + â˜ƒx + ", got " + â˜ƒ, â˜ƒ);
      }
   }

   public void assertAtTickTimeContainerContains(long var1, BlockPos var3, Item var4) {
      this.runAtTickTime(â˜ƒ, () -> this.assertContainerContains(â˜ƒ, â˜ƒ));
   }

   public void assertAtTickTimeContainerEmpty(long var1, BlockPos var3) {
      this.runAtTickTime(â˜ƒ, () -> this.assertContainerEmpty(â˜ƒ));
   }

   public <E extends Entity, T> void succeedWhenEntityData(BlockPos var1, EntityType<E> var2, Function<E, T> var3, T var4) {
      this.succeedWhen(() -> this.assertEntityData(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public <E extends Entity> void assertEntityProperty(E var1, Predicate<E> var2, String var3) {
      if (!â˜ƒ.test(â˜ƒ)) {
         throw new GameTestAssertException("Entity " + â˜ƒ + " failed " + â˜ƒ + " test");
      }
   }

   public <E extends Entity, T> void assertEntityProperty(E var1, Function<E, T> var2, String var3, T var4) {
      T â˜ƒ = (T)â˜ƒ.apply(â˜ƒ);
      if (!â˜ƒ.equals(â˜ƒ)) {
         throw new GameTestAssertException("Entity " + â˜ƒ + " value " + â˜ƒ + "=" + â˜ƒ + " is not equal to expected " + â˜ƒ);
      }
   }

   public void succeedWhenEntityPresent(EntityType<?> var1, int var2, int var3, int var4) {
      this.succeedWhenEntityPresent(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void succeedWhenEntityPresent(EntityType<?> var1, BlockPos var2) {
      this.succeedWhen(() -> this.assertEntityPresent(â˜ƒ, â˜ƒ));
   }

   public void succeedWhenEntityNotPresent(EntityType<?> var1, int var2, int var3, int var4) {
      this.succeedWhenEntityNotPresent(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void succeedWhenEntityNotPresent(EntityType<?> var1, BlockPos var2) {
      this.succeedWhen(() -> this.assertEntityNotPresent(â˜ƒ, â˜ƒ));
   }

   public void succeed() {
      this.testInfo.succeed();
   }

   private void ensureSingleFinalCheck() {
      if (this.finalCheckAdded) {
         throw new IllegalStateException("This test already has final clause");
      } else {
         this.finalCheckAdded = true;
      }
   }

   public void succeedIf(Runnable var1) {
      this.ensureSingleFinalCheck();
      this.testInfo.createSequence().thenWaitUntil(0L, â˜ƒ).thenSucceed();
   }

   public void succeedWhen(Runnable var1) {
      this.ensureSingleFinalCheck();
      this.testInfo.createSequence().thenWaitUntil(â˜ƒ).thenSucceed();
   }

   public void succeedOnTickWhen(int var1, Runnable var2) {
      this.ensureSingleFinalCheck();
      this.testInfo.createSequence().thenWaitUntil((long)â˜ƒ, â˜ƒ).thenSucceed();
   }

   public void runAtTickTime(long var1, Runnable var3) {
      this.testInfo.setRunAtTickTime(â˜ƒ, â˜ƒ);
   }

   public void runAfterDelay(long var1, Runnable var3) {
      this.runAtTickTime(this.testInfo.getTick() + â˜ƒ, â˜ƒ);
   }

   public void randomTick(BlockPos var1) {
      BlockPos â˜ƒ = this.absolutePos(â˜ƒ);
      ServerLevel â˜ƒx = this.getLevel();
      â˜ƒx.getBlockState(â˜ƒ).randomTick(â˜ƒx, â˜ƒ, â˜ƒx.random);
   }

   public void fail(String var1, BlockPos var2) {
      throw new GameTestAssertPosException(â˜ƒ, this.absolutePos(â˜ƒ), â˜ƒ, this.getTick());
   }

   public void fail(String var1, Entity var2) {
      throw new GameTestAssertPosException(â˜ƒ, â˜ƒ.blockPosition(), this.relativePos(â˜ƒ.blockPosition()), this.getTick());
   }

   public void fail(String var1) {
      throw new GameTestAssertException(â˜ƒ);
   }

   public void failIf(Runnable var1) {
      this.testInfo.createSequence().thenWaitUntil(â˜ƒ).thenFail(() -> new GameTestAssertException("Fail conditions met"));
   }

   public void failIfEver(Runnable var1) {
      LongStream.range(this.testInfo.getTick(), (long)this.testInfo.getTimeoutTicks()).forEach(var2 -> this.testInfo.setRunAtTickTime(var2, â˜ƒ::run));
   }

   public GameTestSequence startSequence() {
      return this.testInfo.createSequence();
   }

   public BlockPos absolutePos(BlockPos var1) {
      BlockPos â˜ƒ = this.testInfo.getStructureBlockPos();
      BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ);
      return StructureTemplate.transform(â˜ƒx, Mirror.NONE, this.testInfo.getRotation(), â˜ƒ);
   }

   public BlockPos relativePos(BlockPos var1) {
      BlockPos â˜ƒ = this.testInfo.getStructureBlockPos();
      Rotation â˜ƒx = this.testInfo.getRotation().getRotated(Rotation.CLOCKWISE_180);
      BlockPos â˜ƒxx = StructureTemplate.transform(â˜ƒ, Mirror.NONE, â˜ƒx, â˜ƒ);
      return â˜ƒxx.subtract(â˜ƒ);
   }

   public Vec3 absoluteVec(Vec3 var1) {
      Vec3 â˜ƒ = Vec3.atLowerCornerOf(this.testInfo.getStructureBlockPos());
      return StructureTemplate.transform(â˜ƒ.add(â˜ƒ), Mirror.NONE, this.testInfo.getRotation(), this.testInfo.getStructureBlockPos());
   }

   public long getTick() {
      return this.testInfo.getTick();
   }

   private AABB getBounds() {
      return this.testInfo.getStructureBounds();
   }

   private AABB getRelativeBounds() {
      AABB â˜ƒ = this.testInfo.getStructureBounds();
      return â˜ƒ.move(BlockPos.ZERO.subtract(this.absolutePos(BlockPos.ZERO)));
   }

   public void forEveryBlockInStructure(Consumer<BlockPos> var1) {
      AABB â˜ƒ = this.getRelativeBounds();
      BlockPos.MutableBlockPos.betweenClosedStream(â˜ƒ.move(0.0, 1.0, 0.0)).forEach(â˜ƒ);
   }

   public void onEachTick(Runnable var1) {
      LongStream.range(this.testInfo.getTick(), (long)this.testInfo.getTimeoutTicks()).forEach(var2 -> this.testInfo.setRunAtTickTime(var2, â˜ƒ::run));
   }
}
