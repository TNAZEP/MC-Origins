package net.minecraft.entity.passive;

import java.util.Locale;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosestWithoutMoving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraft.world.storage.loot.LootTableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant {
   private static final Logger field_190674_bx = LogManager.getLogger();
   private static final DataParameter<Integer> field_184752_bw = EntityDataManager.func_187226_a(EntityVillager.class, DataSerializers.field_187192_b);
   private int field_70955_e;
   private boolean field_70952_f;
   private boolean field_70953_g;
   private Village field_70954_d;
   @Nullable
   private EntityPlayer field_70962_h;
   @Nullable
   private MerchantRecipeList field_70963_i;
   private int field_70961_j;
   private boolean field_70959_by;
   private boolean field_175565_bs;
   private int field_70956_bz;
   private String field_82189_bL;
   private int field_175563_bv;
   private int field_175562_bw;
   private boolean field_82190_bM;
   private boolean field_175564_by;
   private final InventoryBasic field_175560_bz = new InventoryBasic(new TextComponentString("Items"), 8);
   private static final EntityVillager.ITradeList[][][][] field_175561_bA = new EntityVillager.ITradeList[][][][]{
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151015_O, new EntityVillager.PriceInfo(18, 22)),
                        new EntityVillager.EmeraldForItems(Items.field_151174_bG, new EntityVillager.PriceInfo(15, 19)),
                        new EntityVillager.EmeraldForItems(Items.field_151172_bF, new EntityVillager.PriceInfo(15, 19)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151025_P, new EntityVillager.PriceInfo(-4, -2))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Blocks.field_150423_aK, new EntityVillager.PriceInfo(8, 13)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151158_bO, new EntityVillager.PriceInfo(-3, -2))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Blocks.field_150440_ba, new EntityVillager.PriceInfo(7, 12)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151034_e, new EntityVillager.PriceInfo(-7, -5))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.field_151106_aX, new EntityVillager.PriceInfo(-10, -6)),
                        new EntityVillager.ListItemForEmeralds(Blocks.field_150414_aQ, new EntityVillager.PriceInfo(1, 1))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151007_F, new EntityVillager.PriceInfo(15, 20)),
                        new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ItemAndEmeraldToItem(
                           Items.field_196086_aW, new EntityVillager.PriceInfo(6, 6), Items.field_196102_ba, new EntityVillager.PriceInfo(6, 6)
                        ),
                        new EntityVillager.ItemAndEmeraldToItem(
                           Items.field_196087_aX, new EntityVillager.PriceInfo(6, 6), Items.field_196104_bb, new EntityVillager.PriceInfo(6, 6)
                        )
                  },
                  {new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151112_aM, new EntityVillager.PriceInfo(7, 8))}
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Blocks.field_196556_aL, new EntityVillager.PriceInfo(16, 22)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151097_aZ, new EntityVillager.PriceInfo(3, 4))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196556_aL), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196557_aM), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196558_aN), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196559_aO), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196560_aP), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196561_aQ), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196562_aR), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196563_aS), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196564_aT), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196565_aU), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196566_aV), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196567_aW), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196568_aX), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196569_aY), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196570_aZ), new EntityVillager.PriceInfo(1, 2)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Blocks.field_196602_ba), new EntityVillager.PriceInfo(1, 2))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151007_F, new EntityVillager.PriceInfo(15, 20)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151032_g, new EntityVillager.PriceInfo(-12, -8))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.field_151031_f, new EntityVillager.PriceInfo(2, 3)),
                        new EntityVillager.ItemAndEmeraldToItem(
                           Blocks.field_150351_n, new EntityVillager.PriceInfo(10, 10), Items.field_151145_ak, new EntityVillager.PriceInfo(6, 10)
                        )
                  }
            }
      },
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151121_aF, new EntityVillager.PriceInfo(24, 36)),
                        new EntityVillager.ListEnchantedBookForEmeralds()
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151122_aG, new EntityVillager.PriceInfo(8, 10)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151111_aL, new EntityVillager.PriceInfo(10, 12)),
                        new EntityVillager.ListItemForEmeralds(Blocks.field_150342_X, new EntityVillager.PriceInfo(3, 4))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151164_bB, new EntityVillager.PriceInfo(2, 2)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151113_aN, new EntityVillager.PriceInfo(10, 12)),
                        new EntityVillager.ListItemForEmeralds(Blocks.field_150359_w, new EntityVillager.PriceInfo(-5, -3))
                  },
                  {new EntityVillager.ListEnchantedBookForEmeralds()},
                  {new EntityVillager.ListEnchantedBookForEmeralds()},
                  {new EntityVillager.ListItemForEmeralds(Items.field_151057_cb, new EntityVillager.PriceInfo(20, 22))}
            },
            {
                  {new EntityVillager.EmeraldForItems(Items.field_151121_aF, new EntityVillager.PriceInfo(24, 36))},
                  {new EntityVillager.EmeraldForItems(Items.field_151111_aL, new EntityVillager.PriceInfo(1, 1))},
                  {new EntityVillager.ListItemForEmeralds(Items.field_151148_bJ, new EntityVillager.PriceInfo(7, 11))},
                  {
                        new EntityVillager.TreasureMapForEmeralds(new EntityVillager.PriceInfo(12, 20), "Monument", MapDecoration.Type.MONUMENT),
                        new EntityVillager.TreasureMapForEmeralds(new EntityVillager.PriceInfo(16, 28), "Mansion", MapDecoration.Type.MANSION)
                  }
            }
      },
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151078_bh, new EntityVillager.PriceInfo(36, 40)),
                        new EntityVillager.EmeraldForItems(Items.field_151043_k, new EntityVillager.PriceInfo(8, 10))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.field_151137_ax, new EntityVillager.PriceInfo(-4, -1)),
                        new EntityVillager.ListItemForEmeralds(new ItemStack(Items.field_196128_bn), new EntityVillager.PriceInfo(-2, -1))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.field_151079_bi, new EntityVillager.PriceInfo(4, 7)),
                        new EntityVillager.ListItemForEmeralds(Blocks.field_150426_aN, new EntityVillager.PriceInfo(-3, -1))
                  },
                  {new EntityVillager.ListItemForEmeralds(Items.field_151062_by, new EntityVillager.PriceInfo(3, 11))}
            }
      },
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151028_Y, new EntityVillager.PriceInfo(4, 6))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151030_Z, new EntityVillager.PriceInfo(10, 14))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151163_ad, new EntityVillager.PriceInfo(16, 19))
                  },
                  {
                        new EntityVillager.ListItemForEmeralds(Items.field_151029_X, new EntityVillager.PriceInfo(5, 7)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151022_W, new EntityVillager.PriceInfo(9, 11)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151020_U, new EntityVillager.PriceInfo(5, 7)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151023_V, new EntityVillager.PriceInfo(11, 15))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151036_c, new EntityVillager.PriceInfo(6, 8))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151040_l, new EntityVillager.PriceInfo(9, 10))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151048_u, new EntityVillager.PriceInfo(12, 15)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151056_x, new EntityVillager.PriceInfo(9, 12))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151037_a, new EntityVillager.PriceInfo(5, 7))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151042_j, new EntityVillager.PriceInfo(7, 9)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151035_b, new EntityVillager.PriceInfo(9, 11))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151045_i, new EntityVillager.PriceInfo(3, 4)),
                        new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151046_w, new EntityVillager.PriceInfo(12, 15))
                  }
            }
      },
      {
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151147_al, new EntityVillager.PriceInfo(14, 18)),
                        new EntityVillager.EmeraldForItems(Items.field_151076_bf, new EntityVillager.PriceInfo(14, 18))
                  },
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151044_h, new EntityVillager.PriceInfo(16, 24)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151157_am, new EntityVillager.PriceInfo(-7, -5)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151077_bg, new EntityVillager.PriceInfo(-8, -6))
                  }
            },
            {
                  {
                        new EntityVillager.EmeraldForItems(Items.field_151116_aA, new EntityVillager.PriceInfo(9, 12)),
                        new EntityVillager.ListItemForEmeralds(Items.field_151026_S, new EntityVillager.PriceInfo(2, 4))
                  },
                  {new EntityVillager.ListEnchantedItemForEmeralds(Items.field_151027_R, new EntityVillager.PriceInfo(7, 12))},
                  {new EntityVillager.ListItemForEmeralds(Items.field_151141_av, new EntityVillager.PriceInfo(8, 10))}
            }
      },
      {new EntityVillager.ITradeList[0][]}
   };

   public EntityVillager(World var1) {
      this(☃, 0);
   }

   public EntityVillager(World var1, int var2) {
      super(EntityType.field_200756_av, ☃);
      this.func_70938_b(☃);
      this.func_70105_a(0.6F, 1.95F);
      ((PathNavigateGround)this.func_70661_as()).func_179688_b(true);
      this.func_98053_h(true);
   }

   @Override
   protected void func_184651_r() {
      this.field_70714_bg.func_75776_a(0, new EntityAISwimming(this));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6, 0.6));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityEvoker.class, 12.0F, 0.8, 0.8));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityVindicator.class, 8.0F, 0.8, 0.8));
      this.field_70714_bg.func_75776_a(1, new EntityAIAvoidEntity(this, EntityVex.class, 8.0F, 0.6, 0.6));
      this.field_70714_bg.func_75776_a(1, new EntityAITradePlayer(this));
      this.field_70714_bg.func_75776_a(1, new EntityAILookAtTradePlayer(this));
      this.field_70714_bg.func_75776_a(2, new EntityAIMoveIndoors(this));
      this.field_70714_bg.func_75776_a(3, new EntityAIRestrictOpenDoor(this));
      this.field_70714_bg.func_75776_a(4, new EntityAIOpenDoor(this, true));
      this.field_70714_bg.func_75776_a(5, new EntityAIMoveTowardsRestriction(this, 0.6));
      this.field_70714_bg.func_75776_a(6, new EntityAIVillagerMate(this));
      this.field_70714_bg.func_75776_a(7, new EntityAIFollowGolem(this));
      this.field_70714_bg.func_75776_a(9, new EntityAIWatchClosestWithoutMoving(this, EntityPlayer.class, 3.0F, 1.0F));
      this.field_70714_bg.func_75776_a(9, new EntityAIVillagerInteract(this));
      this.field_70714_bg.func_75776_a(9, new EntityAIWanderAvoidWater(this, 0.6));
      this.field_70714_bg.func_75776_a(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
   }

   private void func_175552_ct() {
      if (!this.field_175564_by) {
         this.field_175564_by = true;
         if (this.func_70631_g_()) {
            this.field_70714_bg.func_75776_a(8, new EntityAIPlay(this, 0.32));
         } else if (this.func_70946_n() == 0) {
            this.field_70714_bg.func_75776_a(6, new EntityAIHarvestFarmland(this, 0.6));
         }
      }
   }

   @Override
   protected void func_175500_n() {
      if (this.func_70946_n() == 0) {
         this.field_70714_bg.func_75776_a(8, new EntityAIHarvestFarmland(this, 0.6));
      }

      super.func_175500_n();
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5);
   }

   @Override
   protected void func_70619_bc() {
      if (--this.field_70955_e <= 0) {
         BlockPos ☃ = new BlockPos(this);
         this.field_70170_p.func_175714_ae().func_176060_a(☃);
         this.field_70955_e = 70 + this.field_70146_Z.nextInt(50);
         this.field_70954_d = this.field_70170_p.func_175714_ae().func_176056_a(☃, 32);
         if (this.field_70954_d == null) {
            this.func_110177_bN();
         } else {
            BlockPos ☃ = this.field_70954_d.func_180608_a();
            this.func_175449_a(☃, this.field_70954_d.func_75568_b());
            if (this.field_82190_bM) {
               this.field_82190_bM = false;
               this.field_70954_d.func_82683_b(5);
            }
         }
      }

      if (!this.func_70940_q() && this.field_70961_j > 0) {
         --this.field_70961_j;
         if (this.field_70961_j <= 0) {
            if (this.field_70959_by) {
               for(MerchantRecipe ☃ : this.field_70963_i) {
                  if (☃.func_82784_g()) {
                     ☃.func_82783_a(this.field_70146_Z.nextInt(6) + this.field_70146_Z.nextInt(6) + 2);
                  }
               }

               this.func_175554_cu();
               this.field_70959_by = false;
               if (this.field_70954_d != null && this.field_82189_bL != null) {
                  this.field_70170_p.func_72960_a(this, (byte)14);
                  this.field_70954_d.func_82688_a(this.field_82189_bL, 1);
               }
            }

            this.func_195064_c(new PotionEffect(MobEffects.field_76428_l, 200, 0));
         }
      }

      super.func_70619_bc();
   }

   @Override
   public boolean func_184645_a(EntityPlayer var1, EnumHand var2) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      boolean ☃x = ☃.func_77973_b() == Items.field_151057_cb;
      if (☃x) {
         ☃.func_111282_a(☃, this, ☃);
         return true;
      } else if (☃.func_77973_b() != Items.field_196172_da && this.func_70089_S() && !this.func_70940_q() && !this.func_70631_g_()) {
         if (this.field_70963_i == null) {
            this.func_175554_cu();
         }

         if (☃ == EnumHand.MAIN_HAND) {
            ☃.func_195066_a(StatList.field_188074_H);
         }

         if (!this.field_70170_p.field_72995_K && !this.field_70963_i.isEmpty()) {
            this.func_70932_a_(☃);
            ☃.func_180472_a(this);
         } else if (this.field_70963_i.isEmpty()) {
            return super.func_184645_a(☃, ☃);
         }

         return true;
      } else {
         return super.func_184645_a(☃, ☃);
      }
   }

   @Override
   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_187214_a(field_184752_bw, 0);
   }

   @Override
   public void func_70014_b(NBTTagCompound var1) {
      super.func_70014_b(☃);
      ☃.func_74768_a("Profession", this.func_70946_n());
      ☃.func_74768_a("Riches", this.field_70956_bz);
      ☃.func_74768_a("Career", this.field_175563_bv);
      ☃.func_74768_a("CareerLevel", this.field_175562_bw);
      ☃.func_74757_a("Willing", this.field_175565_bs);
      if (this.field_70963_i != null) {
         ☃.func_74782_a("Offers", this.field_70963_i.func_77202_a());
      }

      NBTTagList ☃ = new NBTTagList();

      for(int ☃x = 0; ☃x < this.field_175560_bz.func_70302_i_(); ++☃x) {
         ItemStack ☃xx = this.field_175560_bz.func_70301_a(☃x);
         if (!☃xx.func_190926_b()) {
            ☃.add((INBTBase)☃xx.func_77955_b(new NBTTagCompound()));
         }
      }

      ☃.func_74782_a("Inventory", ☃);
   }

   @Override
   public void func_70037_a(NBTTagCompound var1) {
      super.func_70037_a(☃);
      this.func_70938_b(☃.func_74762_e("Profession"));
      this.field_70956_bz = ☃.func_74762_e("Riches");
      this.field_175563_bv = ☃.func_74762_e("Career");
      this.field_175562_bw = ☃.func_74762_e("CareerLevel");
      this.field_175565_bs = ☃.func_74767_n("Willing");
      if (☃.func_150297_b("Offers", 10)) {
         NBTTagCompound ☃ = ☃.func_74775_l("Offers");
         this.field_70963_i = new MerchantRecipeList(☃);
      }

      NBTTagList ☃ = ☃.func_150295_c("Inventory", 10);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         ItemStack ☃xx = ItemStack.func_199557_a(☃.func_150305_b(☃x));
         if (!☃xx.func_190926_b()) {
            this.field_175560_bz.func_174894_a(☃xx);
         }
      }

      this.func_98053_h(true);
      this.func_175552_ct();
   }

   @Override
   public boolean func_70692_ba() {
      return false;
   }

   @Override
   protected SoundEvent func_184639_G() {
      return this.func_70940_q() ? SoundEvents.field_187914_gn : SoundEvents.field_187910_gj;
   }

   @Override
   protected SoundEvent func_184601_bQ(DamageSource var1) {
      return SoundEvents.field_187912_gl;
   }

   @Override
   protected SoundEvent func_184615_bR() {
      return SoundEvents.field_187911_gk;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_191184_at;
   }

   public void func_70938_b(int var1) {
      this.field_70180_af.func_187227_b(field_184752_bw, ☃);
   }

   public int func_70946_n() {
      return Math.max(this.field_70180_af.func_187225_a(field_184752_bw) % 6, 0);
   }

   public boolean func_70941_o() {
      return this.field_70952_f;
   }

   public void func_70947_e(boolean var1) {
      this.field_70952_f = ☃;
   }

   public void func_70939_f(boolean var1) {
      this.field_70953_g = ☃;
   }

   public boolean func_70945_p() {
      return this.field_70953_g;
   }

   @Override
   public void func_70604_c(@Nullable EntityLivingBase var1) {
      super.func_70604_c(☃);
      if (this.field_70954_d != null && ☃ != null) {
         this.field_70954_d.func_75575_a(☃);
         if (☃ instanceof EntityPlayer) {
            int ☃ = -1;
            if (this.func_70631_g_()) {
               ☃ = -3;
            }

            this.field_70954_d.func_82688_a(((EntityPlayer)☃).func_146103_bH().getName(), ☃);
            if (this.func_70089_S()) {
               this.field_70170_p.func_72960_a(this, (byte)13);
            }
         }
      }
   }

   @Override
   public void func_70645_a(DamageSource var1) {
      if (this.field_70954_d != null) {
         Entity ☃ = ☃.func_76346_g();
         if (☃ != null) {
            if (☃ instanceof EntityPlayer) {
               this.field_70954_d.func_82688_a(((EntityPlayer)☃).func_146103_bH().getName(), -2);
            } else if (☃ instanceof IMob) {
               this.field_70954_d.func_82692_h();
            }
         } else {
            EntityPlayer ☃ = this.field_70170_p.func_72890_a(this, 16.0);
            if (☃ != null) {
               this.field_70954_d.func_82692_h();
            }
         }
      }

      super.func_70645_a(☃);
   }

   @Override
   public void func_70932_a_(@Nullable EntityPlayer var1) {
      this.field_70962_h = ☃;
   }

   @Nullable
   @Override
   public EntityPlayer func_70931_l_() {
      return this.field_70962_h;
   }

   public boolean func_70940_q() {
      return this.field_70962_h != null;
   }

   public boolean func_175550_n(boolean var1) {
      if (!this.field_175565_bs && ☃ && this.func_175553_cp()) {
         boolean ☃ = false;

         for(int ☃x = 0; ☃x < this.field_175560_bz.func_70302_i_(); ++☃x) {
            ItemStack ☃xx = this.field_175560_bz.func_70301_a(☃x);
            if (!☃xx.func_190926_b()) {
               if (☃xx.func_77973_b() == Items.field_151025_P && ☃xx.func_190916_E() >= 3) {
                  ☃ = true;
                  this.field_175560_bz.func_70298_a(☃x, 3);
               } else if ((☃xx.func_77973_b() == Items.field_151174_bG || ☃xx.func_77973_b() == Items.field_151172_bF) && ☃xx.func_190916_E() >= 12) {
                  ☃ = true;
                  this.field_175560_bz.func_70298_a(☃x, 12);
               }
            }

            if (☃) {
               this.field_70170_p.func_72960_a(this, (byte)18);
               this.field_175565_bs = true;
               break;
            }
         }
      }

      return this.field_175565_bs;
   }

   public void func_175549_o(boolean var1) {
      this.field_175565_bs = ☃;
   }

   @Override
   public void func_70933_a(MerchantRecipe var1) {
      ☃.func_77399_f();
      this.field_70757_a = -this.func_70627_aG();
      this.func_184185_a(SoundEvents.field_187915_go, this.func_70599_aP(), this.func_70647_i());
      int ☃ = 3 + this.field_70146_Z.nextInt(4);
      if (☃.func_180321_e() == 1 || this.field_70146_Z.nextInt(5) == 0) {
         this.field_70961_j = 40;
         this.field_70959_by = true;
         this.field_175565_bs = true;
         if (this.field_70962_h != null) {
            this.field_82189_bL = this.field_70962_h.func_146103_bH().getName();
         } else {
            this.field_82189_bL = null;
         }

         ☃ += 5;
      }

      if (☃.func_77394_a().func_77973_b() == Items.field_151166_bC) {
         this.field_70956_bz += ☃.func_77394_a().func_190916_E();
      }

      if (☃.func_180322_j()) {
         this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 0.5, this.field_70161_v, ☃));
      }

      if (this.field_70962_h instanceof EntityPlayerMP) {
         CriteriaTriggers.field_192138_r.func_192234_a((EntityPlayerMP)this.field_70962_h, this, ☃.func_77397_d());
      }
   }

   @Override
   public void func_110297_a_(ItemStack var1) {
      if (!this.field_70170_p.field_72995_K && this.field_70757_a > -this.func_70627_aG() + 20) {
         this.field_70757_a = -this.func_70627_aG();
         this.func_184185_a(☃.func_190926_b() ? SoundEvents.field_187913_gm : SoundEvents.field_187915_go, this.func_70599_aP(), this.func_70647_i());
      }
   }

   @Nullable
   @Override
   public MerchantRecipeList func_70934_b(EntityPlayer var1) {
      if (this.field_70963_i == null) {
         this.func_175554_cu();
      }

      return this.field_70963_i;
   }

   private void func_175554_cu() {
      EntityVillager.ITradeList[][][] ☃ = field_175561_bA[this.func_70946_n()];
      if (this.field_175563_bv != 0 && this.field_175562_bw != 0) {
         ++this.field_175562_bw;
      } else {
         this.field_175563_bv = this.field_70146_Z.nextInt(☃.length) + 1;
         this.field_175562_bw = 1;
      }

      if (this.field_70963_i == null) {
         this.field_70963_i = new MerchantRecipeList();
      }

      int ☃ = this.field_175563_bv - 1;
      int ☃x = this.field_175562_bw - 1;
      if (☃ >= 0 && ☃ < ☃.length) {
         EntityVillager.ITradeList[][] ☃xx = ☃[☃];
         if (☃x >= 0 && ☃x < ☃xx.length) {
            EntityVillager.ITradeList[] ☃xxx = ☃xx[☃x];

            for(EntityVillager.ITradeList ☃xxxx : ☃xxx) {
               ☃xxxx.func_190888_a(this, this.field_70963_i, this.field_70146_Z);
            }
         }
      }
   }

   @Override
   public World func_190670_t_() {
      return this.field_70170_p;
   }

   @Override
   public BlockPos func_190671_u_() {
      return new BlockPos(this);
   }

   @Override
   public ITextComponent func_145748_c_() {
      Team ☃ = this.func_96124_cp();
      ITextComponent ☃x = this.func_200201_e();
      if (☃x != null) {
         return ScorePlayerTeam.func_200541_a(☃, ☃x).func_211710_a(var1x -> var1x.func_150209_a(this.func_174823_aP()).func_179989_a(this.func_189512_bd()));
      } else {
         if (this.field_70963_i == null) {
            this.func_175554_cu();
         }

         String ☃ = null;
         switch(this.func_70946_n()) {
            case 0:
               if (this.field_175563_bv == 1) {
                  ☃ = "farmer";
               } else if (this.field_175563_bv == 2) {
                  ☃ = "fisherman";
               } else if (this.field_175563_bv == 3) {
                  ☃ = "shepherd";
               } else if (this.field_175563_bv == 4) {
                  ☃ = "fletcher";
               }
               break;
            case 1:
               if (this.field_175563_bv == 1) {
                  ☃ = "librarian";
               } else if (this.field_175563_bv == 2) {
                  ☃ = "cartographer";
               }
               break;
            case 2:
               ☃ = "cleric";
               break;
            case 3:
               if (this.field_175563_bv == 1) {
                  ☃ = "armorer";
               } else if (this.field_175563_bv == 2) {
                  ☃ = "weapon_smith";
               } else if (this.field_175563_bv == 3) {
                  ☃ = "tool_smith";
               }
               break;
            case 4:
               if (this.field_175563_bv == 1) {
                  ☃ = "butcher";
               } else if (this.field_175563_bv == 2) {
                  ☃ = "leatherworker";
               }
               break;
            case 5:
               ☃ = "nitwit";
         }

         if (☃ != null) {
            ITextComponent ☃ = new TextComponentTranslation(this.func_200600_R().func_210760_d() + '.' + ☃)
               .func_211710_a(var1x -> var1x.func_150209_a(this.func_174823_aP()).func_179989_a(this.func_189512_bd()));
            if (☃ != null) {
               ☃.func_211708_a(☃.func_178775_l());
            }

            return ☃;
         } else {
            return super.func_145748_c_();
         }
      }
   }

   @Override
   public float func_70047_e() {
      return this.func_70631_g_() ? 0.81F : 1.62F;
   }

   @Nullable
   @Override
   public IEntityLivingData func_204210_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3) {
      return this.func_190672_a(☃, ☃, ☃, true);
   }

   public IEntityLivingData func_190672_a(DifficultyInstance var1, @Nullable IEntityLivingData var2, @Nullable NBTTagCompound var3, boolean var4) {
      ☃ = super.func_204210_a(☃, ☃, ☃);
      if (☃) {
         this.func_70938_b(this.field_70170_p.field_73012_v.nextInt(6));
      }

      this.func_175552_ct();
      this.func_175554_cu();
      return ☃;
   }

   public void func_82187_q() {
      this.field_82190_bM = true;
   }

   public EntityVillager func_90011_a(EntityAgeable var1) {
      EntityVillager ☃ = new EntityVillager(this.field_70170_p);
      ☃.func_204210_a(this.field_70170_p.func_175649_E(new BlockPos(☃)), null, null);
      return ☃;
   }

   @Override
   public boolean func_184652_a(EntityPlayer var1) {
      return false;
   }

   @Override
   public void func_70077_a(EntityLightningBolt var1) {
      if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
         EntityWitch ☃ = new EntityWitch(this.field_70170_p);
         ☃.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
         ☃.func_204210_a(this.field_70170_p.func_175649_E(new BlockPos(☃)), null, null);
         ☃.func_94061_f(this.func_175446_cd());
         if (this.func_145818_k_()) {
            ☃.func_200203_b(this.func_200201_e());
            ☃.func_174805_g(this.func_174833_aM());
         }

         this.field_70170_p.func_72838_d(☃);
         this.func_70106_y();
      }
   }

   public InventoryBasic func_175551_co() {
      return this.field_175560_bz;
   }

   @Override
   protected void func_175445_a(EntityItem var1) {
      ItemStack ☃ = ☃.func_92059_d();
      Item ☃x = ☃.func_77973_b();
      if (this.func_175558_a(☃x)) {
         ItemStack ☃xx = this.field_175560_bz.func_174894_a(☃);
         if (☃xx.func_190926_b()) {
            ☃.func_70106_y();
         } else {
            ☃.func_190920_e(☃xx.func_190916_E());
         }
      }
   }

   private boolean func_175558_a(Item var1) {
      return ☃ == Items.field_151025_P
         || ☃ == Items.field_151174_bG
         || ☃ == Items.field_151172_bF
         || ☃ == Items.field_151015_O
         || ☃ == Items.field_151014_N
         || ☃ == Items.field_185164_cV
         || ☃ == Items.field_185163_cU;
   }

   public boolean func_175553_cp() {
      return this.func_175559_s(1);
   }

   public boolean func_175555_cq() {
      return this.func_175559_s(2);
   }

   public boolean func_175557_cr() {
      boolean ☃ = this.func_70946_n() == 0;
      if (☃) {
         return !this.func_175559_s(5);
      } else {
         return !this.func_175559_s(1);
      }
   }

   private boolean func_175559_s(int var1) {
      boolean ☃ = this.func_70946_n() == 0;

      for(int ☃x = 0; ☃x < this.field_175560_bz.func_70302_i_(); ++☃x) {
         ItemStack ☃xx = this.field_175560_bz.func_70301_a(☃x);
         Item ☃xxx = ☃xx.func_77973_b();
         int ☃xxxx = ☃xx.func_190916_E();
         if (☃xxx == Items.field_151025_P && ☃xxxx >= 3 * ☃
            || ☃xxx == Items.field_151174_bG && ☃xxxx >= 12 * ☃
            || ☃xxx == Items.field_151172_bF && ☃xxxx >= 12 * ☃
            || ☃xxx == Items.field_185164_cV && ☃xxxx >= 12 * ☃) {
            return true;
         }

         if (☃ && ☃xxx == Items.field_151015_O && ☃xxxx >= 9 * ☃) {
            return true;
         }
      }

      return false;
   }

   public boolean func_175556_cs() {
      for(int ☃ = 0; ☃ < this.field_175560_bz.func_70302_i_(); ++☃) {
         Item ☃x = this.field_175560_bz.func_70301_a(☃).func_77973_b();
         if (☃x == Items.field_151014_N || ☃x == Items.field_151174_bG || ☃x == Items.field_151172_bF || ☃x == Items.field_185163_cU) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean func_174820_d(int var1, ItemStack var2) {
      if (super.func_174820_d(☃, ☃)) {
         return true;
      } else {
         int ☃ = ☃ - 300;
         if (☃ >= 0 && ☃ < this.field_175560_bz.func_70302_i_()) {
            this.field_175560_bz.func_70299_a(☃, ☃);
            return true;
         } else {
            return false;
         }
      }
   }

   static class EmeraldForItems implements EntityVillager.ITradeList {
      public Item field_179405_a;
      public EntityVillager.PriceInfo field_179404_b;

      public EmeraldForItems(IItemProvider var1, EntityVillager.PriceInfo var2) {
         this.field_179405_a = ☃.func_199767_j();
         this.field_179404_b = ☃;
      }

      @Override
      public void func_190888_a(IMerchant var1, MerchantRecipeList var2, Random var3) {
         ItemStack ☃ = new ItemStack(this.field_179405_a, this.field_179404_b == null ? 1 : this.field_179404_b.func_179412_a(☃));
         ☃.add(new MerchantRecipe(☃, Items.field_151166_bC));
      }
   }

   interface ITradeList {
      void func_190888_a(IMerchant var1, MerchantRecipeList var2, Random var3);
   }

   static class ItemAndEmeraldToItem implements EntityVillager.ITradeList {
      public ItemStack field_199763_a;
      public EntityVillager.PriceInfo field_179409_b;
      public ItemStack field_199764_c;
      public EntityVillager.PriceInfo field_179408_d;

      public ItemAndEmeraldToItem(IItemProvider var1, EntityVillager.PriceInfo var2, Item var3, EntityVillager.PriceInfo var4) {
         this.field_199763_a = new ItemStack(☃);
         this.field_179409_b = ☃;
         this.field_199764_c = new ItemStack(☃);
         this.field_179408_d = ☃;
      }

      @Override
      public void func_190888_a(IMerchant var1, MerchantRecipeList var2, Random var3) {
         int ☃ = this.field_179409_b.func_179412_a(☃);
         int ☃x = this.field_179408_d.func_179412_a(☃);
         ☃.add(
            new MerchantRecipe(
               new ItemStack(this.field_199763_a.func_77973_b(), ☃),
               new ItemStack(Items.field_151166_bC),
               new ItemStack(this.field_199764_c.func_77973_b(), ☃x)
            )
         );
      }
   }

   static class ListEnchantedBookForEmeralds implements EntityVillager.ITradeList {
      public ListEnchantedBookForEmeralds() {
      }

      @Override
      public void func_190888_a(IMerchant var1, MerchantRecipeList var2, Random var3) {
         Enchantment ☃ = IRegistry.field_212628_q.func_186801_a(☃);
         int ☃x = MathHelper.func_76136_a(☃, ☃.func_77319_d(), ☃.func_77325_b());
         ItemStack ☃xx = ItemEnchantedBook.func_92111_a(new EnchantmentData(☃, ☃x));
         int ☃xxx = 2 + ☃.nextInt(5 + ☃x * 10) + 3 * ☃x;
         if (☃.func_185261_e()) {
            ☃xxx *= 2;
         }

         if (☃xxx > 64) {
            ☃xxx = 64;
         }

         ☃.add(new MerchantRecipe(new ItemStack(Items.field_151122_aG), new ItemStack(Items.field_151166_bC, ☃xxx), ☃xx));
      }
   }

   static class ListEnchantedItemForEmeralds implements EntityVillager.ITradeList {
      public ItemStack field_179407_a;
      public EntityVillager.PriceInfo field_179406_b;

      public ListEnchantedItemForEmeralds(Item var1, EntityVillager.PriceInfo var2) {
         this.field_179407_a = new ItemStack(☃);
         this.field_179406_b = ☃;
      }

      @Override
      public void func_190888_a(IMerchant var1, MerchantRecipeList var2, Random var3) {
         int ☃ = 1;
         if (this.field_179406_b != null) {
            ☃ = this.field_179406_b.func_179412_a(☃);
         }

         ItemStack ☃ = new ItemStack(Items.field_151166_bC, ☃);
         ItemStack ☃x = EnchantmentHelper.func_77504_a(☃, new ItemStack(this.field_179407_a.func_77973_b()), 5 + ☃.nextInt(15), false);
         ☃.add(new MerchantRecipe(☃, ☃x));
      }
   }

   static class ListItemForEmeralds implements EntityVillager.ITradeList {
      public ItemStack field_179403_a;
      public EntityVillager.PriceInfo field_179402_b;

      public ListItemForEmeralds(Block var1, EntityVillager.PriceInfo var2) {
         this(new ItemStack(☃), ☃);
      }

      public ListItemForEmeralds(Item var1, EntityVillager.PriceInfo var2) {
         this(new ItemStack(☃), ☃);
      }

      public ListItemForEmeralds(ItemStack var1, EntityVillager.PriceInfo var2) {
         this.field_179403_a = ☃;
         this.field_179402_b = ☃;
      }

      @Override
      public void func_190888_a(IMerchant var1, MerchantRecipeList var2, Random var3) {
         int ☃ = 1;
         if (this.field_179402_b != null) {
            ☃ = this.field_179402_b.func_179412_a(☃);
         }

         ItemStack ☃;
         ItemStack ☃x;
         if (☃ < 0) {
            ☃ = new ItemStack(Items.field_151166_bC);
            ☃x = new ItemStack(this.field_179403_a.func_77973_b(), -☃);
         } else {
            ☃ = new ItemStack(Items.field_151166_bC, ☃);
            ☃x = new ItemStack(this.field_179403_a.func_77973_b());
         }

         ☃.add(new MerchantRecipe(☃, ☃x));
      }
   }

   static class PriceInfo extends Tuple<Integer, Integer> {
      public PriceInfo(int var1, int var2) {
         super(☃, ☃);
         if (☃ < ☃) {
            EntityVillager.field_190674_bx.warn("PriceRange({}, {}) invalid, {} smaller than {}", ☃, ☃, ☃, ☃);
         }
      }

      public int func_179412_a(Random var1) {
         return this.func_76341_a() >= this.func_76340_b()
            ? this.func_76341_a()
            : this.func_76341_a() + ☃.nextInt(this.func_76340_b() - this.func_76341_a() + 1);
      }
   }

   static class TreasureMapForEmeralds implements EntityVillager.ITradeList {
      public EntityVillager.PriceInfo field_190889_a;
      public String field_190890_b;
      public MapDecoration.Type field_190891_c;

      public TreasureMapForEmeralds(EntityVillager.PriceInfo var1, String var2, MapDecoration.Type var3) {
         this.field_190889_a = ☃;
         this.field_190890_b = ☃;
         this.field_190891_c = ☃;
      }

      @Override
      public void func_190888_a(IMerchant var1, MerchantRecipeList var2, Random var3) {
         int ☃ = this.field_190889_a.func_179412_a(☃);
         World ☃x = ☃.func_190670_t_();
         BlockPos ☃xx = ☃x.func_211157_a(this.field_190890_b, ☃.func_190671_u_(), 100, true);
         if (☃xx != null) {
            ItemStack ☃xxx = ItemMap.func_195952_a(☃x, ☃xx.func_177958_n(), ☃xx.func_177952_p(), (byte)2, true, true);
            ItemMap.func_190905_a(☃x, ☃xxx);
            MapData.func_191094_a(☃xxx, ☃xx, "+", this.field_190891_c);
            ☃xxx.func_200302_a(new TextComponentTranslation("filled_map." + this.field_190890_b.toLowerCase(Locale.ROOT)));
            ☃.add(new MerchantRecipe(new ItemStack(Items.field_151166_bC, ☃), new ItemStack(Items.field_151111_aL), ☃xxx));
         }
      }
   }
}
