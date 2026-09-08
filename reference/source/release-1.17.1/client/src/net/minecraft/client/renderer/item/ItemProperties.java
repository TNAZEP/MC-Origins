package net.minecraft.client.renderer.item;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.phys.Vec3;

public class ItemProperties {
   private static final Map<ResourceLocation, ItemPropertyFunction> GENERIC_PROPERTIES = Maps.<ResourceLocation, ItemPropertyFunction>newHashMap();
   private static final String TAG_CUSTOM_MODEL_DATA = "CustomModelData";
   private static final ResourceLocation DAMAGED = new ResourceLocation("damaged");
   private static final ResourceLocation DAMAGE = new ResourceLocation("damage");
   private static final ClampedItemPropertyFunction PROPERTY_DAMAGED = (var0, var1, var2, var3) -> var0.isDamaged() ? 1.0F : 0.0F;
   private static final ClampedItemPropertyFunction PROPERTY_DAMAGE = (var0, var1, var2, var3) -> Mth.clamp(
         (float)var0.getDamageValue() / (float)var0.getMaxDamage(), 0.0F, 1.0F
      );
   private static final Map<Item, Map<ResourceLocation, ItemPropertyFunction>> PROPERTIES = Maps.newHashMap();

   private static ClampedItemPropertyFunction registerGeneric(ResourceLocation var0, ClampedItemPropertyFunction var1) {
      GENERIC_PROPERTIES.put(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   private static void registerCustomModelData(ItemPropertyFunction var0) {
      GENERIC_PROPERTIES.put(new ResourceLocation("custom_model_data"), â˜ƒ);
   }

   private static void register(Item var0, ResourceLocation var1, ClampedItemPropertyFunction var2) {
      ((Map)PROPERTIES.computeIfAbsent(â˜ƒ, var0x -> Maps.newHashMap())).put(â˜ƒ, â˜ƒ);
   }

   @Nullable
   public static ItemPropertyFunction getProperty(Item var0, ResourceLocation var1) {
      if (â˜ƒ.getMaxDamage() > 0) {
         if (DAMAGE.equals(â˜ƒ)) {
            return PROPERTY_DAMAGE;
         }

         if (DAMAGED.equals(â˜ƒ)) {
            return PROPERTY_DAMAGED;
         }
      }

      ItemPropertyFunction â˜ƒ = (ItemPropertyFunction)GENERIC_PROPERTIES.get(â˜ƒ);
      if (â˜ƒ != null) {
         return â˜ƒ;
      } else {
         Map<ResourceLocation, ItemPropertyFunction> â˜ƒ = (Map)PROPERTIES.get(â˜ƒ);
         return â˜ƒ == null ? null : (ItemPropertyFunction)â˜ƒ.get(â˜ƒ);
      }
   }

   static {
      registerGeneric(new ResourceLocation("lefthanded"), (var0, var1, var2, var3) -> var2 != null && var2.getMainArm() != HumanoidArm.RIGHT ? 1.0F : 0.0F);
      registerGeneric(
         new ResourceLocation("cooldown"),
         (var0, var1, var2, var3) -> var2 instanceof Player ? ((Player)var2).getCooldowns().getCooldownPercent(var0.getItem(), 0.0F) : 0.0F
      );
      registerCustomModelData((var0, var1, var2, var3) -> var0.hasTag() ? (float)var0.getTag().getInt("CustomModelData") : 0.0F);
      register(Items.BOW, new ResourceLocation("pull"), (var0, var1, var2, var3) -> {
         if (var2 == null) {
            return 0.0F;
         } else {
            return var2.getUseItem() != var0 ? 0.0F : (float)(var0.getUseDuration() - var2.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      register(
         Items.BOW, new ResourceLocation("pulling"), (var0, var1, var2, var3) -> var2 != null && var2.isUsingItem() && var2.getUseItem() == var0 ? 1.0F : 0.0F
      );
      register(Items.BUNDLE, new ResourceLocation("filled"), (var0, var1, var2, var3) -> BundleItem.getFullnessDisplay(var0));
      register(Items.CLOCK, new ResourceLocation("time"), new ClampedItemPropertyFunction() {
         private double rotation;
         private double rota;
         private long lastUpdateTick;

         @Override
         public float unclampedCall(ItemStack var1, @Nullable ClientLevel var2, @Nullable LivingEntity var3, int var4) {
            Entity â˜ƒ = (Entity)(â˜ƒ != null ? â˜ƒ : â˜ƒ.getEntityRepresentation());
            if (â˜ƒ == null) {
               return 0.0F;
            } else {
               if (â˜ƒ == null && â˜ƒ.level instanceof ClientLevel) {
                  â˜ƒ = (ClientLevel)â˜ƒ.level;
               }

               if (â˜ƒ == null) {
                  return 0.0F;
               } else {
                  double â˜ƒ;
                  if (â˜ƒ.dimensionType().natural()) {
                     â˜ƒ = (double)â˜ƒ.getTimeOfDay(1.0F);
                  } else {
                     â˜ƒ = Math.random();
                  }

                  â˜ƒ = this.wobble(â˜ƒ, â˜ƒ);
                  return (float)â˜ƒ;
               }
            }
         }

         private double wobble(Level var1, double var2) {
            if (â˜ƒ.getGameTime() != this.lastUpdateTick) {
               this.lastUpdateTick = â˜ƒ.getGameTime();
               double â˜ƒ = â˜ƒ - this.rotation;
               â˜ƒ = Mth.positiveModulo(â˜ƒ + 0.5, 1.0) - 0.5;
               this.rota += â˜ƒ * 0.1;
               this.rota *= 0.9;
               this.rotation = Mth.positiveModulo(this.rotation + this.rota, 1.0);
            }

            return this.rotation;
         }
      });
      register(Items.COMPASS, new ResourceLocation("angle"), new ClampedItemPropertyFunction() {
         private final ItemProperties.CompassWobble wobble = new ItemProperties.CompassWobble();
         private final ItemProperties.CompassWobble wobbleRandom = new ItemProperties.CompassWobble();

         @Override
         public float unclampedCall(ItemStack var1, @Nullable ClientLevel var2, @Nullable LivingEntity var3, int var4) {
            Entity â˜ƒ = (Entity)(â˜ƒ != null ? â˜ƒ : â˜ƒ.getEntityRepresentation());
            if (â˜ƒ == null) {
               return 0.0F;
            } else {
               if (â˜ƒ == null && â˜ƒ.level instanceof ClientLevel) {
                  â˜ƒ = (ClientLevel)â˜ƒ.level;
               }

               BlockPos â˜ƒ = CompassItem.isLodestoneCompass(â˜ƒ) ? this.getLodestonePosition(â˜ƒ, â˜ƒ.getOrCreateTag()) : this.getSpawnPosition(â˜ƒ);
               long â˜ƒx = â˜ƒ.getGameTime();
               if (â˜ƒ != null && !(â˜ƒ.position().distanceToSqr((double)â˜ƒ.getX() + 0.5, â˜ƒ.position().y(), (double)â˜ƒ.getZ() + 0.5) < 1.0E-5F)) {
                  boolean â˜ƒxx = â˜ƒ instanceof Player && ((Player)â˜ƒ).isLocalPlayer();
                  double â˜ƒxxx = 0.0;
                  if (â˜ƒxx) {
                     â˜ƒxxx = (double)â˜ƒ.getYRot();
                  } else if (â˜ƒ instanceof ItemFrame) {
                     â˜ƒxxx = this.getFrameRotation((ItemFrame)â˜ƒ);
                  } else if (â˜ƒ instanceof ItemEntity) {
                     â˜ƒxxx = (double)(180.0F - ((ItemEntity)â˜ƒ).getSpin(0.5F) / (float) (Math.PI * 2) * 360.0F);
                  } else if (â˜ƒ != null) {
                     â˜ƒxxx = (double)â˜ƒ.yBodyRot;
                  }

                  â˜ƒxxx = Mth.positiveModulo(â˜ƒxxx / 360.0, 1.0);
                  double â˜ƒxxx = this.getAngleTo(Vec3.atCenterOf(â˜ƒ), â˜ƒ) / (float) (Math.PI * 2);
                  double â˜ƒxx;
                  if (â˜ƒxx) {
                     if (this.wobble.shouldUpdate(â˜ƒx)) {
                        this.wobble.update(â˜ƒx, 0.5 - (â˜ƒxxx - 0.25));
                     }

                     â˜ƒxx = â˜ƒxxx + this.wobble.rotation;
                  } else {
                     â˜ƒxx = 0.5 - (â˜ƒxxx - 0.25 - â˜ƒxxx);
                  }

                  return Mth.positiveModulo((float)â˜ƒxx, 1.0F);
               } else {
                  if (this.wobbleRandom.shouldUpdate(â˜ƒx)) {
                     this.wobbleRandom.update(â˜ƒx, Math.random());
                  }

                  double â˜ƒ = this.wobbleRandom.rotation + (double)((float)this.hash(â˜ƒ) / 2.1474836E9F);
                  return Mth.positiveModulo((float)â˜ƒ, 1.0F);
               }
            }
         }

         private int hash(int var1) {
            return â˜ƒ * 1327217883;
         }

         @Nullable
         private BlockPos getSpawnPosition(ClientLevel var1) {
            return â˜ƒ.dimensionType().natural() ? â˜ƒ.getSharedSpawnPos() : null;
         }

         @Nullable
         private BlockPos getLodestonePosition(Level var1, CompoundTag var2) {
            boolean â˜ƒ = â˜ƒ.contains("LodestonePos");
            boolean â˜ƒx = â˜ƒ.contains("LodestoneDimension");
            if (â˜ƒ && â˜ƒx) {
               Optional<ResourceKey<Level>> â˜ƒxx = CompassItem.getLodestoneDimension(â˜ƒ);
               if (â˜ƒxx.isPresent() && â˜ƒ.dimension() == â˜ƒxx.get()) {
                  return NbtUtils.readBlockPos(â˜ƒ.getCompound("LodestonePos"));
               }
            }

            return null;
         }

         private double getFrameRotation(ItemFrame var1) {
            Direction â˜ƒ = â˜ƒ.getDirection();
            int â˜ƒx = â˜ƒ.getAxis().isVertical() ? 90 * â˜ƒ.getAxisDirection().getStep() : 0;
            return (double)Mth.wrapDegrees(180 + â˜ƒ.get2DDataValue() * 90 + â˜ƒ.getRotation() * 45 + â˜ƒx);
         }

         private double getAngleTo(Vec3 var1, Entity var2) {
            return Math.atan2(â˜ƒ.z() - â˜ƒ.getZ(), â˜ƒ.x() - â˜ƒ.getX());
         }
      });
      register(
         Items.CROSSBOW,
         new ResourceLocation("pull"),
         (var0, var1, var2, var3) -> {
            if (var2 == null) {
               return 0.0F;
            } else {
               return CrossbowItem.isCharged(var0)
                  ? 0.0F
                  : (float)(var0.getUseDuration() - var2.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(var0);
            }
         }
      );
      register(
         Items.CROSSBOW,
         new ResourceLocation("pulling"),
         (var0, var1, var2, var3) -> var2 != null && var2.isUsingItem() && var2.getUseItem() == var0 && !CrossbowItem.isCharged(var0) ? 1.0F : 0.0F
      );
      register(Items.CROSSBOW, new ResourceLocation("charged"), (var0, var1, var2, var3) -> var2 != null && CrossbowItem.isCharged(var0) ? 1.0F : 0.0F);
      register(
         Items.CROSSBOW,
         new ResourceLocation("firework"),
         (var0, var1, var2, var3) -> var2 != null && CrossbowItem.isCharged(var0) && CrossbowItem.containsChargedProjectile(var0, Items.FIREWORK_ROCKET)
               ? 1.0F
               : 0.0F
      );
      register(Items.ELYTRA, new ResourceLocation("broken"), (var0, var1, var2, var3) -> ElytraItem.isFlyEnabled(var0) ? 0.0F : 1.0F);
      register(Items.FISHING_ROD, new ResourceLocation("cast"), (var0, var1, var2, var3) -> {
         if (var2 == null) {
            return 0.0F;
         } else {
            boolean â˜ƒ = var2.getMainHandItem() == var0;
            boolean â˜ƒx = var2.getOffhandItem() == var0;
            if (var2.getMainHandItem().getItem() instanceof FishingRodItem) {
               â˜ƒx = false;
            }

            return (â˜ƒ || â˜ƒx) && var2 instanceof Player && ((Player)var2).fishing != null ? 1.0F : 0.0F;
         }
      });
      register(
         Items.SHIELD,
         new ResourceLocation("blocking"),
         (var0, var1, var2, var3) -> var2 != null && var2.isUsingItem() && var2.getUseItem() == var0 ? 1.0F : 0.0F
      );
      register(
         Items.TRIDENT,
         new ResourceLocation("throwing"),
         (var0, var1, var2, var3) -> var2 != null && var2.isUsingItem() && var2.getUseItem() == var0 ? 1.0F : 0.0F
      );
      register(Items.LIGHT, new ResourceLocation("level"), (var0, var1, var2, var3) -> {
         CompoundTag â˜ƒ = var0.getTagElement("BlockStateTag");

         try {
            if (â˜ƒ != null) {
               Tag â˜ƒx = â˜ƒ.get(LightBlock.LEVEL.getName());
               if (â˜ƒx != null) {
                  return (float)Integer.parseInt(â˜ƒx.getAsString()) / 16.0F;
               }
            }
         } catch (NumberFormatException var6) {
         }

         return 1.0F;
      });
   }

   static class CompassWobble {
      double rotation;
      private double deltaRotation;
      private long lastUpdateTick;

      boolean shouldUpdate(long var1) {
         return this.lastUpdateTick != â˜ƒ;
      }

      void update(long var1, double var3) {
         this.lastUpdateTick = â˜ƒ;
         double â˜ƒ = â˜ƒ - this.rotation;
         â˜ƒ = Mth.positiveModulo(â˜ƒ + 0.5, 1.0) - 0.5;
         this.deltaRotation += â˜ƒ * 0.1;
         this.deltaRotation *= 0.8;
         this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0);
      }
   }
}
