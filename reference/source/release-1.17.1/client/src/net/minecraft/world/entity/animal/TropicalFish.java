package net.minecraft.world.entity.animal;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class TropicalFish extends AbstractSchoolingFish {
   public static final String BUCKET_VARIANT_TAG = "BucketVariantTag";
   private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(TropicalFish.class, EntityDataSerializers.INT);
   public static final int BASE_SMALL = 0;
   public static final int BASE_LARGE = 1;
   private static final int BASES = 2;
   private static final ResourceLocation[] BASE_TEXTURE_LOCATIONS = new ResourceLocation[]{
      new ResourceLocation("textures/entity/fish/tropical_a.png"), new ResourceLocation("textures/entity/fish/tropical_b.png")
   };
   private static final ResourceLocation[] PATTERN_A_TEXTURE_LOCATIONS = new ResourceLocation[]{
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_1.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_2.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_3.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_4.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_5.png"),
      new ResourceLocation("textures/entity/fish/tropical_a_pattern_6.png")
   };
   private static final ResourceLocation[] PATTERN_B_TEXTURE_LOCATIONS = new ResourceLocation[]{
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_1.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_2.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_3.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_4.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_5.png"),
      new ResourceLocation("textures/entity/fish/tropical_b_pattern_6.png")
   };
   private static final int PATTERNS = 6;
   private static final int COLORS = 15;
   public static final int[] COMMON_VARIANTS = new int[]{
      calculateVariant(TropicalFish.Pattern.STRIPEY, DyeColor.ORANGE, DyeColor.GRAY),
      calculateVariant(TropicalFish.Pattern.FLOPPER, DyeColor.GRAY, DyeColor.GRAY),
      calculateVariant(TropicalFish.Pattern.FLOPPER, DyeColor.GRAY, DyeColor.BLUE),
      calculateVariant(TropicalFish.Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.GRAY),
      calculateVariant(TropicalFish.Pattern.SUNSTREAK, DyeColor.BLUE, DyeColor.GRAY),
      calculateVariant(TropicalFish.Pattern.KOB, DyeColor.ORANGE, DyeColor.WHITE),
      calculateVariant(TropicalFish.Pattern.SPOTTY, DyeColor.PINK, DyeColor.LIGHT_BLUE),
      calculateVariant(TropicalFish.Pattern.BLOCKFISH, DyeColor.PURPLE, DyeColor.YELLOW),
      calculateVariant(TropicalFish.Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.RED),
      calculateVariant(TropicalFish.Pattern.SPOTTY, DyeColor.WHITE, DyeColor.YELLOW),
      calculateVariant(TropicalFish.Pattern.GLITTER, DyeColor.WHITE, DyeColor.GRAY),
      calculateVariant(TropicalFish.Pattern.CLAYFISH, DyeColor.WHITE, DyeColor.ORANGE),
      calculateVariant(TropicalFish.Pattern.DASHER, DyeColor.CYAN, DyeColor.PINK),
      calculateVariant(TropicalFish.Pattern.BRINELY, DyeColor.LIME, DyeColor.LIGHT_BLUE),
      calculateVariant(TropicalFish.Pattern.BETTY, DyeColor.RED, DyeColor.WHITE),
      calculateVariant(TropicalFish.Pattern.SNOOPER, DyeColor.GRAY, DyeColor.RED),
      calculateVariant(TropicalFish.Pattern.BLOCKFISH, DyeColor.RED, DyeColor.WHITE),
      calculateVariant(TropicalFish.Pattern.FLOPPER, DyeColor.WHITE, DyeColor.YELLOW),
      calculateVariant(TropicalFish.Pattern.KOB, DyeColor.RED, DyeColor.WHITE),
      calculateVariant(TropicalFish.Pattern.SUNSTREAK, DyeColor.GRAY, DyeColor.WHITE),
      calculateVariant(TropicalFish.Pattern.DASHER, DyeColor.CYAN, DyeColor.YELLOW),
      calculateVariant(TropicalFish.Pattern.FLOPPER, DyeColor.YELLOW, DyeColor.YELLOW)
   };
   private boolean isSchool = true;

   private static int calculateVariant(TropicalFish.Pattern var0, DyeColor var1, DyeColor var2) {
      return â˜ƒ.getBase() & 0xFF | (â˜ƒ.getIndex() & 0xFF) << 8 | (â˜ƒ.getId() & 0xFF) << 16 | (â˜ƒ.getId() & 0xFF) << 24;
   }

   public TropicalFish(EntityType<? extends TropicalFish> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public static String getPredefinedName(int var0) {
      return "entity.minecraft.tropical_fish.predefined." + â˜ƒ;
   }

   public static DyeColor getBaseColor(int var0) {
      return DyeColor.byId(getBaseColorIdx(â˜ƒ));
   }

   public static DyeColor getPatternColor(int var0) {
      return DyeColor.byId(getPatternColorIdx(â˜ƒ));
   }

   public static String getFishTypeName(int var0) {
      int â˜ƒ = getBaseVariant(â˜ƒ);
      int â˜ƒx = getPatternVariant(â˜ƒ);
      return "entity.minecraft.tropical_fish.type." + TropicalFish.Pattern.getPatternName(â˜ƒ, â˜ƒx);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("Variant", this.getVariant());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setVariant(â˜ƒ.getInt("Variant"));
   }

   public void setVariant(int var1) {
      this.entityData.set(DATA_ID_TYPE_VARIANT, â˜ƒ);
   }

   @Override
   public boolean isMaxGroupSizeReached(int var1) {
      return !this.isSchool;
   }

   public int getVariant() {
      return this.entityData.get(DATA_ID_TYPE_VARIANT);
   }

   @Override
   public void saveToBucketTag(ItemStack var1) {
      super.saveToBucketTag(â˜ƒ);
      CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
      â˜ƒ.putInt("BucketVariantTag", this.getVariant());
   }

   @Override
   public ItemStack getBucketItemStack() {
      return new ItemStack(Items.TROPICAL_FISH_BUCKET);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.TROPICAL_FISH_AMBIENT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.TROPICAL_FISH_DEATH;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource var1) {
      return SoundEvents.TROPICAL_FISH_HURT;
   }

   @Override
   protected SoundEvent getFlopSound() {
      return SoundEvents.TROPICAL_FISH_FLOP;
   }

   private static int getBaseColorIdx(int var0) {
      return (â˜ƒ & 0xFF0000) >> 16;
   }

   public float[] getBaseColor() {
      return DyeColor.byId(getBaseColorIdx(this.getVariant())).getTextureDiffuseColors();
   }

   private static int getPatternColorIdx(int var0) {
      return (â˜ƒ & 0xFF000000) >> 24;
   }

   public float[] getPatternColor() {
      return DyeColor.byId(getPatternColorIdx(this.getVariant())).getTextureDiffuseColors();
   }

   public static int getBaseVariant(int var0) {
      return Math.min(â˜ƒ & 0xFF, 1);
   }

   public int getBaseVariant() {
      return getBaseVariant(this.getVariant());
   }

   private static int getPatternVariant(int var0) {
      return Math.min((â˜ƒ & 0xFF00) >> 8, 5);
   }

   public ResourceLocation getPatternTextureLocation() {
      return getBaseVariant(this.getVariant()) == 0
         ? PATTERN_A_TEXTURE_LOCATIONS[getPatternVariant(this.getVariant())]
         : PATTERN_B_TEXTURE_LOCATIONS[getPatternVariant(this.getVariant())];
   }

   public ResourceLocation getBaseTextureLocation() {
      return BASE_TEXTURE_LOCATIONS[getBaseVariant(this.getVariant())];
   }

   @Nullable
   @Override
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor var1, DifficultyInstance var2, MobSpawnType var3, @Nullable SpawnGroupData var4, @Nullable CompoundTag var5
   ) {
      â˜ƒ = super.finalizeSpawn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ == MobSpawnType.BUCKET && â˜ƒ != null && â˜ƒ.contains("BucketVariantTag", 3)) {
         this.setVariant(â˜ƒ.getInt("BucketVariantTag"));
         return â˜ƒ;
      } else {
         int â˜ƒx;
         int â˜ƒxx;
         int â˜ƒxxx;
         int â˜ƒxxxx;
         if (â˜ƒ instanceof TropicalFish.TropicalFishGroupData â˜ƒ) {
            â˜ƒx = â˜ƒ.base;
            â˜ƒxx = â˜ƒ.pattern;
            â˜ƒxxx = â˜ƒ.baseColor;
            â˜ƒxxxx = â˜ƒ.patternColor;
         } else if ((double)this.random.nextFloat() < 0.9) {
            int â˜ƒ = Util.getRandom(COMMON_VARIANTS, this.random);
            â˜ƒx = â˜ƒ & 0xFF;
            â˜ƒxx = (â˜ƒ & 0xFF00) >> 8;
            â˜ƒxxx = (â˜ƒ & 0xFF0000) >> 16;
            â˜ƒxxxx = (â˜ƒ & 0xFF000000) >> 24;
            â˜ƒ = new TropicalFish.TropicalFishGroupData(this, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
         } else {
            this.isSchool = false;
            â˜ƒx = this.random.nextInt(2);
            â˜ƒxx = this.random.nextInt(6);
            â˜ƒxxx = this.random.nextInt(15);
            â˜ƒxxxx = this.random.nextInt(15);
         }

         this.setVariant(â˜ƒx | â˜ƒxx << 8 | â˜ƒxxx << 16 | â˜ƒxxxx << 24);
         return â˜ƒ;
      }
   }

   static enum Pattern {
      KOB(0, 0),
      SUNSTREAK(0, 1),
      SNOOPER(0, 2),
      DASHER(0, 3),
      BRINELY(0, 4),
      SPOTTY(0, 5),
      FLOPPER(1, 0),
      STRIPEY(1, 1),
      GLITTER(1, 2),
      BLOCKFISH(1, 3),
      BETTY(1, 4),
      CLAYFISH(1, 5);

      private final int base;
      private final int index;
      private static final TropicalFish.Pattern[] VALUES = values();

      private Pattern(int var3, int var4) {
         this.base = â˜ƒ;
         this.index = â˜ƒ;
      }

      public int getBase() {
         return this.base;
      }

      public int getIndex() {
         return this.index;
      }

      public static String getPatternName(int var0, int var1) {
         return VALUES[â˜ƒ + 6 * â˜ƒ].getName();
      }

      public String getName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   static class TropicalFishGroupData extends AbstractSchoolingFish.SchoolSpawnGroupData {
      final int base;
      final int pattern;
      final int baseColor;
      final int patternColor;

      TropicalFishGroupData(TropicalFish var1, int var2, int var3, int var4, int var5) {
         super(â˜ƒ);
         this.base = â˜ƒ;
         this.pattern = â˜ƒ;
         this.baseColor = â˜ƒ;
         this.patternColor = â˜ƒ;
      }
   }
}
