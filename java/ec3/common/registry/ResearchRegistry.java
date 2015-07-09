package ec3.common.registry;

import java.util.Hashtable;
import java.util.Random;

import DummyCore.Utils.MiscUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ec3.api.ApiCore;
import ec3.api.CategoryEntry;
import ec3.api.DiscoveryEntry;
import ec3.api.PageEntry;
import ec3.api.StructureBlock;
import ec3.api.StructureRecipe;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemBaublesWearable;
import ec3.common.item.ItemGun;
import ec3.common.item.ItemsCore;
import ec3.utils.common.ECUtils;

public class ResearchRegistry {
	
	public static Hashtable<String, Integer> stringIDS = new Hashtable<String, Integer>();
	
	public static void registerBasicCategory()
	{
		ItemStack book = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book).setInteger("tier", 0);
		ItemStack book_t1 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t1).setInteger("tier", 1);
		basic
		.apendDiscovery(
				new DiscoveryEntry("ec3.disc.book")
				.setReferal(book)
				.setDisplayStack(new ItemStack(ItemsCore.research_book,0,0))
				.apendPage(
						new PageEntry("ec3.page.book_0")
							.setText(StatCollector.translateToLocal("ec3.page.book_0.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_1")
							.setRecipe(ECUtils.findRecipeByIS(book,2))
							.setText(StatCollector.translateToLocal("ec3.page.book_1.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_2")
							.setText(StatCollector.translateToLocal("ec3.page.book_2.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_3")
							.setText(StatCollector.translateToLocal("ec3.page.book_3.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_4")
							.setText(StatCollector.translateToLocal("ec3.page.book_4.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_5")
							.setText(StatCollector.translateToLocal("ec3.page.book_5.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_6")
							.setText(StatCollector.translateToLocal("ec3.page.book_6.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_7")
							.setText(StatCollector.translateToLocal("ec3.page.book_7.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_8")
							.setText(StatCollector.translateToLocal("ec3.page.book_8.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_9")
							.setText(StatCollector.translateToLocal("ec3.page.book_9.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_10")
							.setText(StatCollector.translateToLocal("ec3.page.book_10.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_11")
							.setText(StatCollector.translateToLocal("ec3.page.book_11.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_12")
							.setText(StatCollector.translateToLocal("ec3.page.book_12.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_13")
							.setText(StatCollector.translateToLocal("ec3.page.book_13.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_14")
							.setText(StatCollector.translateToLocal("ec3.page.book_14.txt"))
						)
				.apendPage(
						new PageEntry("ec3.page.book_15")
							.setText(StatCollector.translateToLocal("ec3.page.book_15.txt"))
							.setRecipe(
									new ShapedOreRecipe(book_t1, new Object[]{
											"EGE",
											"GBG",
											"EGE",
											'E',"elementalCore",
											'G',"shardElemental",
											'B',book
									}))
						)
				)
		.apendDiscovery(
				new DiscoveryEntry("ec3.disc.wind")
				.setReferal(new ItemStack(ItemsCore.bottledWind,0,0),new ItemStack(ItemsCore.imprisonedWind,0,0),new ItemStack(ItemsCore.windKeeper,0,0),new ItemStack(ItemsCore.magicArmorItems[12],0,0),new ItemStack(ItemsCore.magicArmorItems[13],0,0),new ItemStack(ItemsCore.magicArmorItems[14],0,0),new ItemStack(ItemsCore.magicArmorItems[15],0,0),new ItemStack(ItemsCore.air_potion,0,0))
				.setDisplayStack(new ResourceLocation("essentialcraft","textures/special/wind_icon.png")
				)
					.apendPage(
								new PageEntry("ec3.page.wind_0")
									.setText(StatCollector.translateToLocal("ec3.page.wind_0.txt"))
							)
					.apendPage(
								new PageEntry("ec3.page.wind_1")
									.setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/windTouch.png"))
									.setText(StatCollector.translateToLocal("ec3.page.wind_1.txt"))
							)
					.apendPage(
								new PageEntry("ec3.page.wind_2")
									.setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/windMages.png"))
									.setText(StatCollector.translateToLocal("ec3.page.wind_2.txt"))
							)	
					.apendPage(
								new PageEntry("ec3.page.wind_3")
								.setDisplayStacks(new ItemStack(ItemsCore.bottledWind,1,0),new ItemStack(ItemsCore.imprisonedWind,1,0),new ItemStack(ItemsCore.windKeeper,1,0))
								.setText(StatCollector.translateToLocal("ec3.page.wind_3.txt"))
							)
					.apendPage(
								new PageEntry("ec3.page.wind_4")
								.setDisplayStacks(new ItemStack(ItemsCore.magicArmorItems[12],1,0),new ItemStack(ItemsCore.magicArmorItems[13],1,0),new ItemStack(ItemsCore.magicArmorItems[14],1,0),new ItemStack(ItemsCore.magicArmorItems[15],1,0))
								.setText(StatCollector.translateToLocal("ec3.page.wind_4.txt"))
							)
					.apendPage(
								new PageEntry("ec3.page.wind_5")
								.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.air_potion,1,0), 3))
								.setText(StatCollector.translateToLocal("ec3.page.wind_5.txt"))
							)
	)
		.apendDiscovery(
				new DiscoveryEntry("ec3.disc.drops")
				.setReferal(new ItemStack(ItemsCore.drops,1,0),new ItemStack(ItemsCore.drops,1,1),new ItemStack(ItemsCore.drops,1,2),new ItemStack(ItemsCore.drops,1,3),new ItemStack(ItemsCore.drops,1,4),new ItemStack(BlocksCore.compressed,1,0),new ItemStack(BlocksCore.compressed,1,1),new ItemStack(BlocksCore.compressed,1,2),new ItemStack(BlocksCore.compressed,1,3),new ItemStack(BlocksCore.compressed,1,4),new ItemStack(ItemsCore.weak_elemental_axe),new ItemStack(ItemsCore.weak_elemental_hoe),new ItemStack(ItemsCore.weak_elemental_pick),new ItemStack(ItemsCore.weak_elemental_shovel),new ItemStack(ItemsCore.weak_elemental_sword))
				.setDisplayStack(new ItemStack(ItemsCore.drops,1,4))
				
				.apendPage(
							new PageEntry("ec3.page.drops_0")
							.setText(StatCollector.translateToLocal("ec3.page.drops_0.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.drops_1")
							.setRecipe(new StructureRecipe(new ItemStack(ItemsCore.drops,1,0),
									new StructureBlock(Blocks.netherrack, 0, 0, 0, 0),
									new StructureBlock(Blocks.lava, 0, 1, 0, 0),
									new StructureBlock(Blocks.lava, 0, -1, 0, 0),
									new StructureBlock(Blocks.lava, 0, 0, 0, 1),
									new StructureBlock(Blocks.lava, 0, 0, 0, -1)
									))
							.setText(StatCollector.translateToLocal("ec3.page.drops_1.txt"))		
						)
				.apendPage(
							new PageEntry("ec3.page.drops_2")
							.setRecipe(new StructureRecipe(new ItemStack(ItemsCore.drops,1,1),
									new StructureBlock(Blocks.ice, 0, 0, 0, 0),
									new StructureBlock(Blocks.water, 0, 1, 0, 0),
									new StructureBlock(Blocks.water, 0, -1, 0, 0),
									new StructureBlock(Blocks.water, 0, 0, 0, 1),
									new StructureBlock(Blocks.water, 0, 0, 0, -1)
									))
							.setText(StatCollector.translateToLocal("ec3.page.drops_2.txt"))		
						)	
				.apendPage(
							new PageEntry("ec3.page.drops_3")
							.setRecipe(new StructureRecipe(new ItemStack(ItemsCore.drops,1,2),
									new StructureBlock(Blocks.grass, 0, 0, 0, 0),
									new StructureBlock(Blocks.mossy_cobblestone, 0, 1, 0, 0),
									new StructureBlock(Blocks.mossy_cobblestone, 0, -1, 0, 0),
									new StructureBlock(Blocks.mossy_cobblestone, 0, 0, 0, 1),
									new StructureBlock(Blocks.mossy_cobblestone, 0, 0, 0, -1)
									))
							.setText(StatCollector.translateToLocal("ec3.page.drops_3.txt"))		
							)	
				.apendPage(
							new PageEntry("ec3.page.drops_4")
							.setRecipe(new StructureRecipe(new ItemStack(ItemsCore.drops,1,3),
									new StructureBlock(Blocks.quartz_block, 0, 0, 0, 0),
									new StructureBlock(Blocks.sand, 0, 1, 0, 0),
									new StructureBlock(Blocks.sand, 0, -1, 0, 0),
									new StructureBlock(Blocks.sand, 0, 0, 0, 1),
									new StructureBlock(Blocks.sand, 0, 0, 0, -1)
									))
							.setText(StatCollector.translateToLocal("ec3.page.drops_4.txt"))		
						)	
				.apendPage(
							new PageEntry("ec3.page.drops_11")
							.setText(StatCollector.translateToLocal("ec3.page.drops_11.txt"))
							.setDisplayStacks(new ItemStack(BlocksCore.oreDrops,1,0),new ItemStack(BlocksCore.oreDrops,1,1),new ItemStack(BlocksCore.oreDrops,1,2),new ItemStack(BlocksCore.oreDrops,1,3),new ItemStack(BlocksCore.oreDrops,1,4))
						)
				.apendPage(
							new PageEntry("ec3.page.drops_12")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.compressed,1,0), 3))
						)	
				.apendPage(
							new PageEntry("ec3.page.drops_13")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.compressed,1,1), 3))
						)	
				.apendPage(
							new PageEntry("ec3.page.drops_14")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.compressed,1,2), 3))
						)	
				.apendPage(
							new PageEntry("ec3.page.drops_15")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.compressed,1,3), 3))
						)	
				.apendPage(
							new PageEntry("ec3.page.drops_16")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.compressed,1,4), 3))
						)	
				.apendPage(
							new PageEntry("ec3.page.drops_5")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.drops,1,4), 3))
							.setText(StatCollector.translateToLocal("ec3.page.drops_5.txt"))		
						)
				.apendPage(
							new PageEntry("ec3.page.drops_6")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.weak_elemental_axe,1,0), 2))	
						)
				.apendPage(
							new PageEntry("ec3.page.drops_7")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.weak_elemental_hoe,1,0), 2))	
						)
				.apendPage(
							new PageEntry("ec3.page.drops_8")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.weak_elemental_pick,1,0), 2))	
						)
				.apendPage(
							new PageEntry("ec3.page.drops_9")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.weak_elemental_shovel,1,0), 2))	
						)
				.apendPage(
							new PageEntry("ec3.page.drops_10")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.weak_elemental_sword,1,0), 3))	
						)
								
	)
		.apendDiscovery(
				new DiscoveryEntry("ec3.disc.miscCrafts")
				.setReferal(new ItemStack(ItemsCore.genericItem,0,1),new ItemStack(ItemsCore.genericItem,0,21),new ItemStack(ItemsCore.genericItem,1,22),new ItemStack(ItemsCore.genericItem,1,23),new ItemStack(ItemsCore.genericItem,1,25),new ItemStack(ItemsCore.genericItem,1,26),new ItemStack(ItemsCore.genericItem,1,28),new ItemStack(ItemsCore.genericItem,1,29))
				.setDisplayStack(new ItemStack(ItemsCore.genericItem,0,1))
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_0")
							.setText(StatCollector.translateToLocal("ec3.page.miscCrafts_0.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_1")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,0,1), 2))
						)
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_2")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,0,21), 2))
						)	
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_3")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,0,22), 2))
						)
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_4")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,0,23), 2))
						)	
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_5")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,0,25), 2))
						)
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_6")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,0,26), 2))
						)	
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_7")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,0,28), 2))
						)
				.apendPage(
							new PageEntry("ec3.page.miscCrafts_8")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,0,29), 2))
						)	
		)
		.apendDiscovery(
				new DiscoveryEntry("ec3.disc.mru")
				.setReferal(new ItemStack(ItemsCore.mruMover1))
				.setDisplayStack(new ResourceLocation("essentialcraft","textures/special/basical_knowledge_icon.png"))
				.apendPage(
							new PageEntry("ec3.page.mru_0")
							.setText(StatCollector.translateToLocal("ec3.page.mru_0.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_1")
							.setText(StatCollector.translateToLocal("ec3.page.mru_1.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_2")
							.setText(StatCollector.translateToLocal("ec3.page.mru_2.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_3")
							.setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/mru_lowest.png"))
							.setText(StatCollector.translateToLocal("ec3.page.mru_3.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_4")
							.setText(StatCollector.translateToLocal("ec3.page.mru_4.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_5")
							.setText(StatCollector.translateToLocal("ec3.page.mru_5.txt"))
						)	
				.apendPage(
							new PageEntry("ec3.page.mru_6")
							.setText(StatCollector.translateToLocal("ec3.page.mru_6.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_7")
							.setText(StatCollector.translateToLocal("ec3.page.mru_7.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_8")
							.setText(StatCollector.translateToLocal("ec3.page.mru_8.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_9")
							.setText(StatCollector.translateToLocal("ec3.page.mru_9.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_10")
							.setText(StatCollector.translateToLocal("ec3.page.mru_10.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_11")
							.setText(StatCollector.translateToLocal("ec3.page.mru_11.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_12")
							.setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/mru_pressence.png"))
							.setText(StatCollector.translateToLocal("ec3.page.mru_12.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_13")
							.setText(StatCollector.translateToLocal("ec3.page.mru_13.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_14")
							.setText(StatCollector.translateToLocal("ec3.page.mru_14.txt"))
							.setDisplayStacks(new ItemStack(BlocksCore.lightCorruption[0],1,7),new ItemStack(BlocksCore.lightCorruption[1],1,7),new ItemStack(BlocksCore.lightCorruption[3],1,7))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_15")
							.setText(StatCollector.translateToLocal("ec3.page.mru_15.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.mru_16")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.mruMover1), 3))
						)
				)
		.apendDiscovery(
				new DiscoveryEntry("ec3.disc.soulStone")
				.setReferal(new ItemStack(ItemsCore.soulStone))
				.setDisplayStack(new ItemStack(ItemsCore.soulStone,0,1))
				.apendPage(
							new PageEntry("ec3.page.soulStone_0")
							.setText(StatCollector.translateToLocal("ec3.page.soulStone_0.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.soulStone_1")
							.setRecipe(new StructureRecipe(new ItemStack(ItemsCore.soulStone,1,0),
									new StructureBlock(Blocks.emerald_block, 0, 0, 0, 0)
									))
							.setText(StatCollector.translateToLocal("ec3.page.soulStone_1.txt"))		
						)
				.apendPage(
							new PageEntry("ec3.page.soulStone_2")
							.setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/soulStone.png"))
							.setText(StatCollector.translateToLocal("ec3.page.soulStone_2.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.soulStone_3")
							.setText(StatCollector.translateToLocal("ec3.page.soulStone_3.txt"))
						)		
				.apendPage(
							new PageEntry("ec3.page.soulStone_43")
							.setText(StatCollector.translateToLocal("ec3.page.soulStone_4.txt"))
						)	
				.apendPage(
							new PageEntry("ec3.page.soulStone_5")
							.setText(StatCollector.translateToLocal("ec3.page.soulStone_5.txt"))
						)
				.apendPage(
							new PageEntry("ec3.page.soulStone_6")
							.setText(StatCollector.translateToLocal("ec3.page.soulStone_6.txt"))
						)
				)
		.apendDiscovery(
				new DiscoveryEntry("ec3.disc.decorations")
				.setReferal(new ItemStack(ItemsCore.magicalChisel))
				.setDisplayStack(new ItemStack(ItemsCore.magicalChisel))
				.apendPage(next("decorations").setText(StatCollector.translateToLocal("ec3.page.decorations_0.txt")))
				.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicalChisel), 2))
						.setDisplayStacks(new ItemStack(ItemsCore.magicalSlag),new ItemStack(BlocksCore.concrete),new ItemStack(BlocksCore.fortifiedStone),new ItemStack(BlocksCore.magicPlating),new ItemStack(BlocksCore.platingPale),new ItemStack(BlocksCore.voidStone),new ItemStack(BlocksCore.coldStone)))
				.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,1), 2)))
					.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,0), 2)))
						.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,2), 2)))
							.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,3), 2)))
								.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,4), 2)))
									.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,5), 2)))
										.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,6), 2)))
											.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,7), 2)))
												.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,8), 2)))
													.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,9), 2)))
														.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,10), 2)))
															.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,11), 2)))
																.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,12), 2)))
																	.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,13), 2)))
																		.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,14), 2)))
																			.apendPage(next("decorations").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fancyBlocks.get(0),1,15), 2)))
				)
		.apendDiscovery(
					new DiscoveryEntry("ec3.disc.overworld")
					.setReferal(new ItemStack(ItemsCore.baublesCore,1,0),new ItemStack(ItemsCore.baublesCore,1,1),new ItemStack(ItemsCore.baublesCore,1,2),new ItemStack(ItemsCore.baublesCore,1,3),new ItemStack(ItemsCore.baublesCore,1,4),new ItemStack(ItemsCore.baublesCore,1,5),new ItemStack(ItemsCore.baublesCore,1,6),new ItemStack(ItemsCore.baublesCore,1,7),new ItemStack(ItemsCore.baublesCore,1,8),new ItemStack(ItemsCore.baublesCore,1,9),new ItemStack(ItemsCore.baublesCore,1,10),new ItemStack(ItemsCore.baublesCore,1,11),new ItemStack(ItemsCore.baublesCore,1,12),new ItemStack(ItemsCore.baublesCore,1,13),new ItemStack(ItemsCore.baublesCore,1,14),new ItemStack(ItemsCore.baublesCore,1,15),new ItemStack(ItemsCore.baublesCore,1,16),new ItemStack(ItemsCore.baublesCore,1,17),new ItemStack(ItemsCore.baublesCore,1,18),new ItemStack(ItemsCore.baublesCore,1,19),new ItemStack(ItemsCore.baublesCore,1,20),new ItemStack(ItemsCore.baublesCore,1,21),new ItemStack(ItemsCore.baublesCore,1,22),new ItemStack(ItemsCore.baublesCore,1,23),new ItemStack(ItemsCore.baublesCore,1,24),new ItemStack(ItemsCore.baublesCore,1,25),new ItemStack(ItemsCore.baublesCore,1,26),new ItemStack(ItemsCore.baublesCore,1,27),new ItemStack(ItemsCore.baublesCore,1,28),new ItemStack(ItemsCore.baublesCore,1,29),new ItemStack(ItemsCore.baublesCore,1,30),new ItemStack(ItemsCore.baublesCore,1,31))
					.setDisplayStack(new ItemStack(ItemsCore.baublesCore,1,14))
					
					.apendPage(next("overworld").setText(StatCollector.translateToLocal("ec3.page.overworld_0.txt")))
					.apendPage(next("overworld").setText(StatCollector.translateToLocal("ec3.page.overworld_1.txt")))
					.apendPage(next("overworld").setDisplayStacks(new ItemStack(ItemsCore.baublesCore,1,0),new ItemStack(ItemsCore.baublesCore,1,1),new ItemStack(ItemsCore.baublesCore,1,2),new ItemStack(ItemsCore.baublesCore,1,3),new ItemStack(ItemsCore.baublesCore,1,4),new ItemStack(ItemsCore.baublesCore,1,5),new ItemStack(ItemsCore.baublesCore,1,6),new ItemStack(ItemsCore.baublesCore,1,7),new ItemStack(ItemsCore.baublesCore,1,8),new ItemStack(ItemsCore.baublesCore,1,9),new ItemStack(ItemsCore.baublesCore,1,10),new ItemStack(ItemsCore.baublesCore,1,11),new ItemStack(ItemsCore.baublesCore,1,12),new ItemStack(ItemsCore.baublesCore,1,13),new ItemStack(ItemsCore.baublesCore,1,14),new ItemStack(ItemsCore.baublesCore,1,15),new ItemStack(ItemsCore.baublesCore,1,16),new ItemStack(ItemsCore.baublesCore,1,17),new ItemStack(ItemsCore.baublesCore,1,18),new ItemStack(ItemsCore.baublesCore,1,19),new ItemStack(ItemsCore.baublesCore,1,20),new ItemStack(ItemsCore.baublesCore,1,21),new ItemStack(ItemsCore.baublesCore,1,22),new ItemStack(ItemsCore.baublesCore,1,23),new ItemStack(ItemsCore.baublesCore,1,24),new ItemStack(ItemsCore.baublesCore,1,25),new ItemStack(ItemsCore.baublesCore,1,26),new ItemStack(ItemsCore.baublesCore,1,27),new ItemStack(ItemsCore.baublesCore,1,28),new ItemStack(ItemsCore.baublesCore,1,29),new ItemStack(ItemsCore.baublesCore,1,30),new ItemStack(ItemsCore.baublesCore,1,31)))
				)
	;
	}
	
	public static void registerMruCategory()
	{
		ItemStack book_t1 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t1).setInteger("tier", 1);
		ItemStack book_t2 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t2).setInteger("tier", 2);
		ItemStack pistol = new ItemStack(ItemsCore.pistol,1,0);
		ItemGun.createRandomGun(pistol);
		ItemStack rifle = new ItemStack(ItemsCore.rifle,1,0);
		ItemGun.createRandomGun(rifle);
		ItemStack sniper = new ItemStack(ItemsCore.sniper,1,0);
		ItemGun.createRandomGun(sniper);
		ItemStack gatling = new ItemStack(ItemsCore.gatling,1,0);
		ItemGun.createRandomGun(gatling);
		mru
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.boundGem")
					.setReferal(new ItemStack(ItemsCore.bound_gem,0,0))
					.setDisplayStack(new ItemStack(ItemsCore.bound_gem))
					.apendPage(
							new PageEntry("ec3.page.boundGem_0")
							.setText(StatCollector.translateToLocal("ec3.page.boundGem_0.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.boundGem_1")
							.setText(StatCollector.translateToLocal("ec3.page.boundGem_1.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.boundGem_2")
							.setText(StatCollector.translateToLocal("ec3.page.boundGem_2.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.boundGem_3")
							.setText(StatCollector.translateToLocal("ec3.page.boundGem_3.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.boundGem_4")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.bound_gem), 2))
							.setText(StatCollector.translateToLocal("ec3.page.boundGem_4.txt"))
							)							
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.matrixDestructor")
					.setReferal(new ItemStack(BlocksCore.matrixAbsorber,0,0))
					.setDisplayStack(new ItemStack(BlocksCore.matrixAbsorber))
					.apendPage(
							new PageEntry("ec3.page.matrixDestructor_0")
							.setText(StatCollector.translateToLocal("ec3.page.matrixDestructor_0.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.matrixDestructor_1")
							.setText(StatCollector.translateToLocal("ec3.page.matrixDestructor_1.txt"))
							)		
					.apendPage(
							new PageEntry("ec3.page.matrixDestructor_2")
							.setText(StatCollector.translateToLocal("ec3.page.matrixDestructor_2.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.matrixDestructor_3")
							.setText(StatCollector.translateToLocal("ec3.page.matrixDestructor_3.txt"))
							.setDisplayStacks(new ItemStack(ItemsCore.soulStone,1,0))
							)
					.apendPage(
							new PageEntry("ec3.page.matrixDestructor_4")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.matrixAbsorber), 2))
							)
							
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.radiatingChamber")
					.setReferal(new ItemStack(BlocksCore.radiatingChamber,0,0))
					.setDisplayStack(new ItemStack(BlocksCore.radiatingChamber))
					.apendPage(
							new PageEntry("ec3.page.radiatingChamber_0")
							.setText(StatCollector.translateToLocal("ec3.page.radiatingChamber_0.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.radiatingChamber_1")
							.setText(StatCollector.translateToLocal("ec3.page.radiatingChamber_1.txt"))
							)		
					.apendPage(
							new PageEntry("ec3.page.radiatingChamber_2")
							.setText(StatCollector.translateToLocal("ec3.page.radiatingChamber_2.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.radiatingChamber_3")
							.setText(StatCollector.translateToLocal("ec3.page.radiatingChamber_3.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.radiatingChamber_4")
							.setText(StatCollector.translateToLocal("ec3.page.radiatingChamber_4.txt"))
							)		
					.apendPage(
							new PageEntry("ec3.page.radiatingChamber_5")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.radiatingChamber), 2))
							)								
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.miscRadiation")
					
					.setReferal(
							new ItemStack(ItemsCore.genericItem,0,3),
							new ItemStack(ItemsCore.genericItem,0,43),
							new ItemStack(ItemsCore.genericItem,0,44),
							new ItemStack(BlocksCore.fortifiedStone,0,0),
							new ItemStack(BlocksCore.fortifiedGlass,0,0),
							new ItemStack(ItemsCore.genericItem,0,24),
							new ItemStack(ItemsCore.genericItem,0,31),
							new ItemStack(ItemsCore.elemental_pick,1,0),
							new ItemStack(ItemsCore.elemental_axe,1,0),
							new ItemStack(ItemsCore.elemental_sword,1,0),
							new ItemStack(ItemsCore.elemental_hoe,1,0),
							new ItemStack(ItemsCore.elemental_shovel,1,0)
							)
					.setDisplayStack(new ItemStack(ItemsCore.genericItem,1,44))
					.apendPage(
							new PageEntry("ec3.page.miscRadiation_0")
							.setText(StatCollector.translateToLocal("ec3.page.miscRadiation_0.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.miscRadiation_1")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,3), 5))
							)		
					.apendPage(
							new PageEntry("ec3.page.miscRadiation_2")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,43), 5))
							)
					.apendPage(
							new PageEntry("ec3.page.miscRadiation_3")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,44), 5))
							)
					.apendPage(
							new PageEntry("ec3.page.miscRadiation_4")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fortifiedStone,1,0), 5))
							)
					.apendPage(
							new PageEntry("ec3.page.miscRadiation_5")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.fortifiedGlass,1,0), 5))
							)
					.apendPage(
							new PageEntry("ec3.page.miscRadiation_6")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,24), 2))
							)
					.apendPage(
							new PageEntry("ec3.page.miscRadiation_7")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,31), 2))
							)
					.apendPage(
							new PageEntry("ec3.page.drops_6")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elemental_axe,1,0), 2))	
						)
				    .apendPage(
							new PageEntry("ec3.page.drops_7")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elemental_hoe,1,0), 2))	
						)
			    	.apendPage(
							new PageEntry("ec3.page.drops_8")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elemental_pick,1,0), 2))	
						)
	    			.apendPage(
							new PageEntry("ec3.page.drops_9")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elemental_shovel,1,0), 2))	
						)
		    		.apendPage(
							new PageEntry("ec3.page.drops_10")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elemental_sword,1,0), 3))	
						)
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicianTable")
					.setReferal(new ItemStack(BlocksCore.magicianTable,0,0))
					.setDisplayStack(new ItemStack(BlocksCore.magicianTable,1,0))
					.apendPage(
							new PageEntry("ec3.page.magicianTable_0")
							.setText(StatCollector.translateToLocal("ec3.page.magicianTable_0.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.magicianTable_1")
							.setText(StatCollector.translateToLocal("ec3.page.magicianTable_1.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.magicianTable_2")
							.setText(StatCollector.translateToLocal("ec3.page.magicianTable_2.txt"))
							)
					.apendPage(
							new PageEntry("ec3.page.magicianTable_3")
							.setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicianTable), 2))
							)	
					.apendPage(
							new PageEntry("ec3.page.magicianTable_4")
							.setDisplayStacks((ItemStack[]) ec3.api.MagicianTableUpgrades.upgradeStacks.toArray(new ItemStack[ec3.api.MagicianTableUpgrades.upgradeStacks.size()]))
							.setText(StatCollector.translateToLocal("ec3.page.magicianTable_4.txt"))
							)								
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicianCrafts")
					.setReferal(
							new ItemStack(ItemsCore.genericItem,0,0),
							new ItemStack(ItemsCore.genericItem,0,5),
							new ItemStack(ItemsCore.genericItem,0,6),
							new ItemStack(ItemsCore.genericItem,0,4),
							new ItemStack(ItemsCore.genericItem,0,7),
							new ItemStack(ItemsCore.genericItem,0,8),
							new ItemStack(ItemsCore.genericItem,0,9),
							new ItemStack(ItemsCore.genericItem,0,10),
							new ItemStack(ItemsCore.genericItem,0,11),
							new ItemStack(ItemsCore.genericItem,0,12),
							new ItemStack(ItemsCore.genericItem,0,13),
							new ItemStack(ItemsCore.genericItem,0,14),
							new ItemStack(ItemsCore.genericItem,0,15),
							new ItemStack(ItemsCore.genericItem,0,16),
							new ItemStack(ItemsCore.genericItem,0,20),
							new ItemStack(ItemsCore.genericItem,0,34),
							new ItemStack(ItemsCore.genericItem,0,32),
							new ItemStack(ItemsCore.genericItem,0,27),
							new ItemStack(ItemsCore.genericItem,0,30),
							new ItemStack(ItemsCore.genericItem,0,33),
							new ItemStack(ItemsCore.genericItem,0,79)
							)
						.setDisplayStack(new ItemStack(ItemsCore.genericItem,1,14))
						.apendPage(
							next("magicianCrafts")
							.setText(StatCollector.translateToLocal("ec3.page.magicianCrafts_0.txt"))
							)	
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(5), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(6), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(10), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(20), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(4), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(7), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(11), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(9), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(8), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(0), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(32), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(34), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(12), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(13), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(14), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(15), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(16), 6)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(27), 2)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(30), 2)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(33), 2)))
						.apendPage(next("magicianCrafts").setRecipe(ECUtils.findRecipeByIS(generic(79), 6)))
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.paleItems")
					.setReferal(generic(38),generic(39),generic(40),generic(41),generic(42),new ItemStack(BlocksCore.platingPale),new ItemStack(BlocksCore.blockPale))
					.setDisplayStack(new ItemStack(BlocksCore.platingPale))
					.apendPage(next("paleItems").setText(StatCollector.translateToLocal("ec3.page.paleItems_0.txt")))
					.apendPage(next("paleItems").setRecipe(ECUtils.findRecipeByIS(generic(39), 5)))
					.apendPage(next("paleItems").setRecipe(ECUtils.findRecipeByIS(generic(38), 5)))
					.apendPage(next("paleItems").setRecipe(ECUtils.findRecipeByIS(generic(40), 5)))
					.apendPage(next("paleItems").setRecipe(ECUtils.findRecipeByIS(generic(41), 6)))
					.apendPage(next("paleItems").setRecipe(ECUtils.findRecipeByIS(generic(42), 5)))
					.apendPage(next("paleItems").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.platingPale), 2)))
					.apendPage(next("paleItems").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.blockPale), 2)))
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicMonocle")
					.setReferal(new ItemStack(ItemsCore.magicMonocle,0,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicMonocle,0,0))
					.apendPage(next("magicMonocle").setText(StatCollector.translateToLocal("ec3.page.magicMonocle_0.txt")))
					.apendPage(next("magicMonocle").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicMonocle), 2)))
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mrucumover2")
					.setReferal(new ItemStack(ItemsCore.mruMover_t2,0,0))
					.setDisplayStack(new ItemStack(ItemsCore.mruMover_t2,0,0))
					.apendPage(next("mrucumover2").setText(StatCollector.translateToLocal("ec3.page.mrucumover2_0.txt")))
					.apendPage(next("mrucumover2").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.mruMover_t2), 2)))
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mrucuec")
					.setReferal(new ItemStack(BlocksCore.magicPlating,0,0),new ItemStack(BlocksCore.ecController,0,0))
					.setDisplayStack(new ItemStack(BlocksCore.ecController,0,0))
					.apendPage(next("mrucuec").setText(StatCollector.translateToLocal("ec3.page.mrucuec_0.txt")))
					.apendPage(next("mrucuec").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicPlating,1,0), 2)))
					.apendPage(next("mrucuec").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ecController,1,0), 2)))
					.apendPage(next("mrucuec").setText(StatCollector.translateToLocal("ec3.page.mrucuec_3.txt")))
					.apendPage(next("mrucuec").setDisplayStacks(new ItemStack(BlocksCore.fortifiedStone),new ItemStack(BlocksCore.fortifiedGlass),new ItemStack(BlocksCore.magicPlating),new ItemStack(BlocksCore.platingPale),new ItemStack(BlocksCore.voidStone),new ItemStack(BlocksCore.voidGlass), new ItemStack(BlocksCore.invertedBlock,1,0)).setText(StatCollector.translateToLocal("ec3.page.mrucuec_4.txt")))
					.apendPage(next("mrucuec").setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/mrucuec.png")))
					.apendPage(next("mrucuec").setText(StatCollector.translateToLocal("ec3.page.mrucuec_6.txt")))
					.apendPage(next("mrucuec").setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/mrucuec_mru.png")))
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mrucuec.state")
					.setReferal(new ItemStack(BlocksCore.ecStateChecker))
					.setDisplayStack(new ItemStack(BlocksCore.ecStateChecker,0,0))
					.apendPage(next("mrucuec.state").setText(StatCollector.translateToLocal("ec3.page.mrucuec.state_0.txt")))
					.apendPage(next("mrucuec.state").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ecStateChecker), 2)))
					.apendPage(next("mrucuec.state").setText(StatCollector.translateToLocal("ec3.page.mrucuec.state_2.txt")))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mrucuec.in")
					.setReferal(new ItemStack(BlocksCore.ecAcceptor))
					.setDisplayStack(new ItemStack(BlocksCore.ecAcceptor,0,0))
					.apendPage(next("mrucuec.in").setText(StatCollector.translateToLocal("ec3.page.mrucuec.in_0.txt")))
					.apendPage(next("mrucuec.in").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ecAcceptor), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mrucuec.out")
					.setReferal(new ItemStack(BlocksCore.ecEjector))
					.setDisplayStack(new ItemStack(BlocksCore.ecEjector,0,0))
					.apendPage(next("mrucuec.out").setText(StatCollector.translateToLocal("ec3.page.mrucuec.out_0.txt")))
					.apendPage(next("mrucuec.out").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ecEjector), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mrucuec.storage")
					.setReferal(new ItemStack(BlocksCore.ecHoldingChamber))
					.setDisplayStack(new ItemStack(BlocksCore.ecHoldingChamber,0,0))
					.apendPage(next("mrucuec.storage").setText(StatCollector.translateToLocal("ec3.page.mrucuec.storage_0.txt")))
					.apendPage(next("mrucuec.storage").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ecHoldingChamber), 2)))
					.apendPage(next("mrucuec.storage").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.storage,1,2), 6)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mrucuec.balancer")
					.setReferal(new ItemStack(BlocksCore.ecBalancer))
					.setDisplayStack(new ItemStack(BlocksCore.ecBalancer,0,0))
					.apendPage(next("mrucuec.balancer").setText(StatCollector.translateToLocal("ec3.page.mrucuec.balancer_0.txt")))
					.apendPage(next("mrucuec.balancer").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ecBalancer), 2)))
					)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mrucuec.redstone")
					.setReferal(new ItemStack(BlocksCore.ecRedstoneController))
					.setDisplayStack(new ItemStack(BlocksCore.ecRedstoneController,0,0))
					.apendPage(next("mrucuec.redstone").setText(StatCollector.translateToLocal("ec3.page.mrucuec.redstone_0.txt")))
					.apendPage(next("mrucuec.redstone").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ecRedstoneController), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.naturalFurnace")
					.setReferal(new ItemStack(BlocksCore.naturalFurnace))
					.setDisplayStack(new ItemStack(BlocksCore.naturalFurnace,0,0))
					.apendPage(next("naturalFurnace").setText(StatCollector.translateToLocal("ec3.page.naturalFurnace_0.txt")))
					.apendPage(next("naturalFurnace").setText(StatCollector.translateToLocal("ec3.page.naturalFurnace_1.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.naturalFurnace), 2)))
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.heatGenerator")
					.setReferal(new ItemStack(BlocksCore.heatGenerator))
					.setDisplayStack(new ItemStack(BlocksCore.heatGenerator,0,0))
					.apendPage(next("heatGenerator").setText(StatCollector.translateToLocal("ec3.page.heatGenerator_0.txt")))
					.apendPage(next("heatGenerator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.heatGenerator), 2)))
					.apendPage(next("heatGenerator").setRecipe(new StructureRecipe(
							new ItemStack(BlocksCore.heatGenerator),
							new StructureBlock(BlocksCore.heatGenerator, 0, 0, 0, 0),
							new StructureBlock(Blocks.netherrack,0, 2, 0, 0),
							new StructureBlock(Blocks.netherrack,0, -2, 0, 0),
							new StructureBlock(Blocks.netherrack,0, 0, 0, 2),
							new StructureBlock(Blocks.netherrack,0, 0, 0, -2),
							new StructureBlock(BlocksCore.air, 0,0, 0, -1),
							new StructureBlock(BlocksCore.air,0, 0, 0, 1),
							new StructureBlock(BlocksCore.air,0, -1, 0, 0),
							new StructureBlock(BlocksCore.air,0, 1, 0, 0)
							))
							.setText("5MRU/tick")
							)
							.apendPage(next("mrucuec.heatGenerator").setRecipe(new StructureRecipe(
							new ItemStack(BlocksCore.heatGenerator),
							new StructureBlock(BlocksCore.heatGenerator, 0, 0, 0, 0),
							new StructureBlock(Blocks.fire,0, 2, 0, 0),
							new StructureBlock(Blocks.fire,0, -2, 0, 0),
							new StructureBlock(Blocks.fire,0, 0, 0, 2),
							new StructureBlock(Blocks.fire,0, 0, 0, -2),
							new StructureBlock(BlocksCore.air,0, 0, 0, -1),
							new StructureBlock(BlocksCore.air,0,0, 0, 1),
							new StructureBlock(BlocksCore.air,0, -1, 0, 0),
							new StructureBlock(BlocksCore.air,0, 1, 0, 0)
							))
							.setText("8MRU/tick")
							)
							.apendPage(next("mrucuec.heatGenerator").setRecipe(new StructureRecipe(
							new ItemStack(BlocksCore.heatGenerator),
							new StructureBlock(BlocksCore.heatGenerator, 0, 0, 0, 0),
							new StructureBlock(Blocks.lava,0, 2, 0, 0),
							new StructureBlock(Blocks.lava,0, -2, 0, 0),
							new StructureBlock(Blocks.lava, 0,0, 0, 2),
							new StructureBlock(Blocks.lava,0, 0, 0, -2),
							new StructureBlock(BlocksCore.air,0, 0, 0, -1),
							new StructureBlock(BlocksCore.air,0, 0, 0, 1),
							new StructureBlock(BlocksCore.air,0, -1, 0, 0),
							new StructureBlock(BlocksCore.air,0, 1, 0, 0)
							))
							.setText("16MRU/tick")
							)
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalMirrors")
					.setReferal(new ItemStack(BlocksCore.magicalMirror),new ItemStack(ItemsCore.controlRod))
					.setDisplayStack(new ItemStack(BlocksCore.magicalMirror,0,0))
					 
					.apendPage(next("magicalMirrors").setText(StatCollector.translateToLocal("ec3.page.magicalMirrors_0.txt")))
					.apendPage(next("magicalMirrors").setText(StatCollector.translateToLocal("ec3.page.magicalMirrors_1.txt")))
					.apendPage(next("magicalMirrors").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicalMirror), 2)))
					.apendPage(next("magicalMirrors").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.controlRod), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalAssembler")
					.setReferal(new ItemStack(BlocksCore.assembler))
					.setDisplayStack(new ItemStack(BlocksCore.assembler,0,0))
					 
					.apendPage(next("magicalAssembler").setText(StatCollector.translateToLocal("ec3.page.magicalAssembler_0.txt")))
					.apendPage(next("magicalAssembler").setText(StatCollector.translateToLocal("ec3.page.magicalAssembler_1.txt")))
					.apendPage(next("magicalAssembler").setText(StatCollector.translateToLocal("ec3.page.magicalAssembler_2.txt")))
					.apendPage(next("magicalAssembler").setText(StatCollector.translateToLocal("ec3.page.magicalAssembler_3.txt")))
					.apendPage(next("magicalAssembler").setText(StatCollector.translateToLocal("ec3.page.magicalAssembler_4.txt")))
					.apendPage(next("magicalAssembler").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.assembler), 2)))
					)						
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.corruptionCleaner")
					 
					.setReferal(new ItemStack(BlocksCore.corruptionCleaner))
					.setDisplayStack(new ItemStack(BlocksCore.corruptionCleaner,0,0))
					.apendPage(next("corruptionCleaner").setText(StatCollector.translateToLocal("ec3.page.corruptionCleaner_0.txt")))
					.apendPage(next("corruptionCleaner").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.corruptionCleaner), 2)))
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.rayTower")
					.setReferal(new ItemStack(BlocksCore.rayTower))
					.setDisplayStack(new ItemStack(BlocksCore.rayTower,0,0))
					.apendPage(next("rayTower").setText(StatCollector.translateToLocal("ec3.page.rayTower_0.txt")))
					.apendPage(next("rayTower").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.rayTower), 2)))
					)					
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.book_t2")
					.setDisplayStack(book_t2)
					.apendPage(next("book_t2").setText(StatCollector.translateToLocal("ec3.page.book_t2_0.txt")))
					.apendPage(next("book_t2").setRecipe(ECUtils.findRecipeByIS(book_t2, 6)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalActivator")
					
					.setDisplayStack(new ItemStack(BlocksCore.rightClicker))
					.setReferal(new ItemStack(BlocksCore.rightClicker))
					.apendPage(next("magicalActivator").setText(StatCollector.translateToLocal("ec3.page.magicalActivator_0.txt")))
					.apendPage(next("magicalActivator").setText(StatCollector.translateToLocal("ec3.page.magicalActivator_1.txt")))
					.apendPage(next("magicalActivator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.rightClicker,1,0), 2)))
					.apendPage(next("magicalActivator").setText(StatCollector.translateToLocal("ec3.page.magicalActivator_2.txt")))
					.apendPage(next("magicalActivator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.rightClicker,1,1), 3)))
					.apendPage(next("magicalActivator").setText(StatCollector.translateToLocal("ec3.page.magicalActivator_3.txt")))
					.apendPage(next("magicalActivator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.rightClicker,1,2), 3)))
					.apendPage(next("magicalActivator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.rightClicker,1,3), 3)))
					.apendPage(next("magicalActivator").setText(StatCollector.translateToLocal("ec3.page.magicalActivator_4.txt")))
					.apendPage(next("magicalActivator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.rightClicker,1,4), 3)))
					.apendPage(next("magicalActivator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.rightClicker,1,5), 3)))
					)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.redstoneTransmitter")
					.setDisplayStack(new ItemStack(BlocksCore.redstoneTransmitter))
					.setReferal(new ItemStack(BlocksCore.redstoneTransmitter))
					
					.apendPage(next("redstoneTransmitter").setText(StatCollector.translateToLocal("ec3.page.redstoneTransmitter_0.txt")))
					.apendPage(next("redstoneTransmitter").setText(StatCollector.translateToLocal("ec3.page.redstoneTransmitter_1.txt")))
					.apendPage(next("redstoneTransmitter").setText(StatCollector.translateToLocal("ec3.page.redstoneTransmitter_2.txt")))
					.apendPage(next("redstoneTransmitter").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.redstoneTransmitter), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalHopper")
					.setDisplayStack(new ItemStack(BlocksCore.magicalHopper))
					.setReferal(new ItemStack(BlocksCore.magicalHopper))
					
					.apendPage(next("magicalHopper").setText(StatCollector.translateToLocal("ec3.page.magicalHopper_0.txt")))
					.apendPage(next("magicalHopper").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicalHopper), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.metadataManager")
					.setDisplayStack(new ItemStack(BlocksCore.metadataManager))
					.setReferal(new ItemStack(BlocksCore.metadataManager))
					
					.apendPage(next("metadataManager").setText(StatCollector.translateToLocal("ec3.page.metadataManager_0.txt")))
					.apendPage(next("metadataManager").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.metadataManager), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.blockBreaker")
					.setDisplayStack(new ItemStack(BlocksCore.blockBreaker))
					.setReferal(new ItemStack(BlocksCore.blockBreaker))
					
					.apendPage(next("blockBreaker").setText(StatCollector.translateToLocal("ec3.page.blockBreaker_0.txt")))
					.apendPage(next("blockBreaker").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.blockBreaker), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.weaponMaker")
					.setDisplayStack(new ItemStack(BlocksCore.weaponMaker))
					.setReferal(new ItemStack(BlocksCore.weaponMaker))
					
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_0.txt")))
					.apendPage(next("weaponMaker").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.weaponMaker), 2)))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_1.txt")))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_2.txt")).setDisplayStacks(pistol))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_3.txt")).setDisplayStacks(rifle))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_4.txt")).setDisplayStacks(sniper))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_5.txt")).setDisplayStacks(gatling))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_6.txt")))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_7.txt")).setDisplayStacks(new ItemStack(ItemsCore.genericItem,1,5),new ItemStack(ItemsCore.genericItem,1,39),new ItemStack(ItemsCore.genericItem,1,50),new ItemStack(ItemsCore.genericItem,1,35),new ItemStack(ItemsCore.genericItem,1,52)))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_8.txt")))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_9.txt")))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_10.txt")))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_11.txt")))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_12.txt")))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_13.txt")))
					.apendPage(next("weaponMaker").setText(StatCollector.translateToLocal("ec3.page.weaponMaker_14.txt")))
					)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magmaticFurnace")
					.setDisplayStack(new ItemStack(BlocksCore.furnaceMagic))
					.setReferal(new ItemStack(BlocksCore.furnaceMagic))
					
					.apendPage(next("magmaticFurnace").setText(StatCollector.translateToLocal("ec3.page.magmaticFurnace_0.txt")))
					.apendPage(next("magmaticFurnace").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.furnaceMagic,1,0), 2)))
					.apendPage(next("magmaticFurnace").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.furnaceMagic,1,4), 2)))
					.apendPage(next("magmaticFurnace").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.furnaceMagic,1,8), 2)))
					.apendPage(next("magmaticFurnace").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.furnaceMagic,1,12), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalChest")
					.setDisplayStack(new ItemStack(BlocksCore.chest))
					.setReferal(new ItemStack(BlocksCore.chest,1,0),new ItemStack(BlocksCore.chest,1,1))
					.setNew()
					.apendPage(next("magicalChest").setText(StatCollector.translateToLocal("ec3.page.magicalChest_0.txt")))
					.apendPage(next("magicalChest").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.chest,1,0), 2)))
					.apendPage(next("magicalChest").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.chest,1,1), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.replanter")
					.setDisplayStack(new ItemStack(BlocksCore.device,1,0))
					.setReferal(new ItemStack(BlocksCore.device,1,0))
					.setNew()
					.apendPage(next("replanter").setText(StatCollector.translateToLocal("ec3.page.replanter_0.txt")))
					.apendPage(next("replanter").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.device,1,0), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.itemShuffler")
					.setDisplayStack(new ItemStack(BlocksCore.device,1,1))
					.setReferal(new ItemStack(BlocksCore.device,1,1))
					.setNew()
					.apendPage(next("itemShuffler").setText(StatCollector.translateToLocal("ec3.page.itemShuffler_0.txt")))
					.apendPage(next("itemShuffler").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.device,1,1), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.crafter")
					.setDisplayStack(new ItemStack(BlocksCore.device,1,2))
					.setReferal(new ItemStack(BlocksCore.device,1,2),new ItemStack(ItemsCore.craftingFrame,1,0))
					.setNew()
					.apendPage(next("crafter").setText(StatCollector.translateToLocal("ec3.page.crafter_0.txt")))
					.apendPage(next("crafter").setText(StatCollector.translateToLocal("ec3.page.crafter_1.txt")))
					.apendPage(next("crafter").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.device,1,2), 2)))
					.apendPage(next("crafter").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.craftingFrame,1,0), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.breeder")
					.setDisplayStack(new ItemStack(BlocksCore.device,1,3))
					.setReferal(new ItemStack(BlocksCore.device,1,3))
					.setNew()
					.apendPage(next("breeder").setText(StatCollector.translateToLocal("ec3.page.breeder_0.txt")))
					.apendPage(next("breeder").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.device,1,3), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.shearingStation")
					.setDisplayStack(new ItemStack(BlocksCore.device,1,5))
					.setReferal(new ItemStack(BlocksCore.device,1,5))
					.setNew()
					.apendPage(next("shearingStation").setText(StatCollector.translateToLocal("ec3.page.shearingStation_0.txt")))
					.apendPage(next("shearingStation").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.device,1,5), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.separator")
					.setDisplayStack(new ItemStack(BlocksCore.device,1,6))
					.setReferal(new ItemStack(BlocksCore.device,1,6),new ItemStack(BlocksCore.device,1,6))
					.setNew()
					.apendPage(next("separator").setText(StatCollector.translateToLocal("ec3.page.separator_0.txt")))
					.apendPage(next("separator").setText(StatCollector.translateToLocal("ec3.page.separator_1.txt")))
					.apendPage(next("separator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.device,1,6), 2)))
					.apendPage(next("separator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.device,1,7), 2)))
					)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.advBreaker")
					.setDisplayStack(new ItemStack(BlocksCore.advBreaker,1,0))
					.setReferal(new ItemStack(BlocksCore.advBreaker,1,0),new ItemStack(ItemsCore.filter,1,0))
					.setNew()
					.apendPage(next("advBreaker").setText(StatCollector.translateToLocal("ec3.page.advBreaker_0.txt")))
					.apendPage(next("advBreaker").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.advBreaker,1,0), 2)))
					.apendPage(next("advBreaker").setText(StatCollector.translateToLocal("ec3.page.advBreaker_1.txt")))
					.apendPage(next("advBreaker").setText(StatCollector.translateToLocal("ec3.page.advBreaker_2.txt")))
					.apendPage(next("advBreaker").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.filter,1,0), 2)))
					.apendPage(next("advBreaker").setText(StatCollector.translateToLocal("ec3.page.advBreaker_3.txt")))
					.apendPage(next("advBreaker").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.filter,1,2), 2)))
					.apendPage(next("advBreaker").setText(StatCollector.translateToLocal("ec3.page.advBreaker_4.txt")))
					.apendPage(next("advBreaker").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.filter,1,1), 2)))		
					.apendPage(next("advBreaker").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.filter,1,3), 2)))		
					)	
		;
	}
	
	public static void registerEngineersCategory()
	{
		ItemStack book_t2 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t2).setInteger("tier", 2);
		ItemStack book_t3 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t3).setInteger("tier", 3);
		ItemStack crystal = new ItemStack(BlocksCore.elementalCrystal);
		MiscUtils.getStackTag(crystal).setFloat("size",100);
		MiscUtils.getStackTag(crystal).setFloat("fire",100);
		eng
		.apendDiscovery(
				new DiscoveryEntry("ec3.disc.crystals")
					.setReferal(crystal,new ItemStack(BlocksCore.crystalFormer),new ItemStack(BlocksCore.crystalController),new ItemStack(BlocksCore.crystalExtractor),new ItemStack(ItemsCore.elementalFuel,0,0),new ItemStack(ItemsCore.elementalFuel,0,1),new ItemStack(ItemsCore.elementalFuel,0,2),new ItemStack(ItemsCore.elementalFuel,0,3),new ItemStack(ItemsCore.fFocus),new ItemStack(ItemsCore.wFocus),new ItemStack(ItemsCore.eFocus),new ItemStack(ItemsCore.aFocus))
					.setDisplayStack(crystal)
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_0.txt")))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_1.txt")))
					.apendPage(next("crystals").setDisplayStacks(crystal).setText(StatCollector.translateToLocal("ec3.page.crystals_2.txt")))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_3.txt")))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_4.txt")))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_5.txt")))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.crystalFormer), 2)))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_7.txt")))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_8.txt")))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_9.txt")))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_10.txt")))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_11.txt")))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.crystalController), 2)))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_13.txt")))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.crystalExtractor), 2)))
					.apendPage(next("crystals").setText(StatCollector.translateToLocal("ec3.page.crystals_15.txt")))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elementalFuel,1,0), 2)))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elementalFuel,1,1), 2)))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elementalFuel,1,2), 2)))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.elementalFuel,1,3), 2)))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.fFocus,1,0), 2)))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.wFocus,1,0), 2)))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.eFocus,1,0), 2)))
					.apendPage(next("crystals").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.aFocus,1,0), 2)))
				)
			.apendDiscovery(
				new DiscoveryEntry("ec3.disc.matrixSwitch")
					.setReferal(new ItemStack(ItemsCore.matrixProj,1,0),new ItemStack(ItemsCore.matrixProj,1,1),new ItemStack(ItemsCore.matrixProj,1,2),new ItemStack(ItemsCore.matrixProj,1,3),new ItemStack(ItemsCore.matrixProj,1,4))
					.setDisplayStack(new ItemStack(ItemsCore.matrixProj,1,3))
					.apendPage(next("matrixSwitch").setText(StatCollector.translateToLocal("ec3.page.matrixSwitch_0.txt")))
					.apendPage(next("matrixSwitch").setText(StatCollector.translateToLocal("ec3.page.matrixSwitch_1.txt")))
					.apendPage(next("matrixSwitch").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.matrixProj,1,0), 5)))
					.apendPage(next("matrixSwitch").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.matrixProj,1,1), 5)))
					.apendPage(next("matrixSwitch").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.matrixProj,1,2), 5)))
					.apendPage(next("matrixSwitch").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.matrixProj,1,3), 5)))
				)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.enderGen")
					.setReferal(new ItemStack(BlocksCore.enderGenerator,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.enderGenerator,1,0))
					.apendPage(next("enderGen").setText(StatCollector.translateToLocal("ec3.page.enderGen_0.txt")))
					.apendPage(next("enderGen").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.enderGenerator,1,0), 2)))
				)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.coldDistillator")
					 
					.setReferal(new ItemStack(BlocksCore.coldStone,1,0),new ItemStack(BlocksCore.coldDistillator,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.coldDistillator,1,0))
					.apendPage(next("coldDistillator").setText(StatCollector.translateToLocal("ec3.page.coldDistillator_0.txt")))
					.apendPage(next("coldDistillator").setText(StatCollector.translateToLocal("ec3.page.coldDistillator_1.txt")))
					.apendPage(next("coldDistillator").setText(StatCollector.translateToLocal("ec3.page.coldDistillator_2.txt")))
					.apendPage(next("coldDistillator").setText(StatCollector.translateToLocal("ec3.page.coldDistillator_3.txt")).setDisplayStacks(new ItemStack(Blocks.snow_layer)))
					.apendPage(next("coldDistillator").setText(StatCollector.translateToLocal("ec3.page.coldDistillator_4.txt")).setDisplayStacks(new ItemStack(Blocks.snow)))
					.apendPage(next("coldDistillator").setText(StatCollector.translateToLocal("ec3.page.coldDistillator_5.txt")).setDisplayStacks(new ItemStack(Blocks.ice)))
					.apendPage(next("coldDistillator").setText(StatCollector.translateToLocal("ec3.page.coldDistillator_6.txt")).setDisplayStacks(new ItemStack(Blocks.packed_ice)))
					.apendPage(next("coldDistillator").setText(StatCollector.translateToLocal("ec3.page.coldDistillator_7.txt")).setDisplayStacks(new ItemStack(BlocksCore.coldStone)))
					.apendPage(next("coldDistillator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.coldStone,1,0), 2)))
					.apendPage(next("coldDistillator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.coldDistillator,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.solarAbsorber")
					.setReferal(new ItemStack(BlocksCore.solarPrism,1,0),new ItemStack(BlocksCore.sunRayAbsorber,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.solarPrism,1,0))
					 
					.apendPage(next("solarAbsorber").setText(StatCollector.translateToLocal("ec3.page.solarAbsorber_0.txt")))
					.apendPage(next("solarAbsorber").setText(StatCollector.translateToLocal("ec3.page.solarAbsorber_1.txt")))
					.apendPage(next("solarAbsorber").setText(StatCollector.translateToLocal("ec3.page.solarAbsorber_2.txt")))
					.apendPage(next("solarAbsorber").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.solarPrism,1,0), 2)))
					.apendPage(next("solarAbsorber").setRecipe(new StructureRecipe(new ItemStack(BlocksCore.solarPrism,1,0),
							new StructureBlock(BlocksCore.solarPrism, 0, 0, 0, 0),
							new StructureBlock(BlocksCore.air, 0, 1, 0, 0),
							new StructureBlock(BlocksCore.air, 0, -1, 0, 0),
							new StructureBlock(BlocksCore.air, 0, 0, 0, 1),
							new StructureBlock(BlocksCore.air, 0, 0, 0, -1),
							new StructureBlock(BlocksCore.air, 0, 1, 0, 1),
							new StructureBlock(BlocksCore.air, 0, 1, 0, -1),
							new StructureBlock(BlocksCore.air, 0, -1, 0, 1),
							new StructureBlock(BlocksCore.air, 0, -1, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 2),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, -2)
							)))
					.apendPage(next("solarAbsorber").setText(StatCollector.translateToLocal("ec3.page.solarAbsorber_5.txt")))	
					.apendPage(next("solarAbsorber").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.sunRayAbsorber,1,0), 2)))
				)
				.apendDiscovery(
					new DiscoveryEntry("ec3.disc.moonWell")
					 
					.setReferal(new ItemStack(BlocksCore.moonWell,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.moonWell,1,0))
					.apendPage(next("moonWell").setText(StatCollector.translateToLocal("ec3.page.moonWell_0.txt")))
					.apendPage(next("moonWell").setText(StatCollector.translateToLocal("ec3.page.moonWell_1.txt")))
					.apendPage(next("moonWell").setText(StatCollector.translateToLocal("ec3.page.moonWell_2.txt")))
					.apendPage(next("moonWell").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.moonWell,1,0), 2)))
				)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.reactor")
					 
					.setReferal(new ItemStack(BlocksCore.reactor,1,0),new ItemStack(BlocksCore.reactorSupport,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.reactor,1,0))
					.apendPage(next("reactor").setText(StatCollector.translateToLocal("ec3.page.reactor_0.txt")))
					.apendPage(next("reactor").setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/reactor.png")))
					.apendPage(next("reactor").setText(StatCollector.translateToLocal("ec3.page.reactor_2.txt")))
					.apendPage(next("reactor").setRecipe(new StructureRecipe(new ItemStack(BlocksCore.reactor,1,0),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, 1, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 1, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, 1, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, -1, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, -1, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, -1, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, -1)
							)))
					.apendPage(next("reactor").setRecipe(new StructureRecipe(new ItemStack(BlocksCore.reactor,1,0),
							new StructureBlock(BlocksCore.reactor, 0, 0, 0, 0),
							new StructureBlock(BlocksCore.air, 0, 0, 0, 1),
							new StructureBlock(BlocksCore.air, 0, 0, 0, -1),
							new StructureBlock(BlocksCore.reactorSupport, 0, 1, 0, 0),
							new StructureBlock(BlocksCore.air, 0, 1, 0, 1),
							new StructureBlock(BlocksCore.air, 0, 1, 0, -1),
							new StructureBlock(BlocksCore.air, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.air, 0, 2, 0, 1),
							new StructureBlock(BlocksCore.air, 0, 2, 0, -1),
							new StructureBlock(BlocksCore.reactorSupport, 0, -1, 0, 0),
							new StructureBlock(BlocksCore.air, 0, -1, 0, 1),
							new StructureBlock(BlocksCore.air, 0, -1, 0, -1),
							new StructureBlock(BlocksCore.air, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.air, 0, -2, 0, 1),
							new StructureBlock(BlocksCore.air, 0, -2, 0, -1)
							)))
					.apendPage(next("reactor").setRecipe(new StructureRecipe(new ItemStack(BlocksCore.reactor,1,0),
							new StructureBlock(BlocksCore.air, 0, 0, 0, 0),
							new StructureBlock(BlocksCore.air, 0, 0, 0, 1),
							new StructureBlock(BlocksCore.air, 0, 0, 0, -1),
							new StructureBlock(BlocksCore.reactorSupport, 0, 1, 0, 0),
							new StructureBlock(BlocksCore.air, 0, 1, 0, 1),
							new StructureBlock(BlocksCore.air, 0, 1, 0, -1),
							new StructureBlock(BlocksCore.air, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.air, 0, 2, 0, 1),
							new StructureBlock(BlocksCore.air, 0, 2, 0, -1),
							new StructureBlock(BlocksCore.reactorSupport, 0, -1, 0, 0),
							new StructureBlock(BlocksCore.air, 0, -1, 0, 1),
							new StructureBlock(BlocksCore.air, 0, -1, 0, -1),
							new StructureBlock(BlocksCore.air, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.air, 0, -2, 0, 1),
							new StructureBlock(BlocksCore.air, 0, -2, 0, -1)
							)))
					.apendPage(next("reactor").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.reactorSupport,1,0), 2)))	
					.apendPage(next("reactor").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.reactor,1,0), 2)))		
					.apendPage(next("reactor").setText(StatCollector.translateToLocal("ec3.page.reactor_8.txt")))	
				)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.soulStorage")
					.setReferal(new ItemStack(BlocksCore.chargingChamber,1,0),new ItemStack(ItemsCore.storage,1,0),new ItemStack(ItemsCore.storage,1,1),new ItemStack(ItemsCore.storage,1,2),new ItemStack(ItemsCore.storage,1,3),new ItemStack(ItemsCore.storage,1,4))
					.setDisplayStack(new ItemStack(ItemsCore.storage,1,4))
					.apendPage(next("soulStorage").setText(StatCollector.translateToLocal("ec3.page.soulStorage_0.txt")))
					.apendPage(next("soulStorage").setText(StatCollector.translateToLocal("ec3.page.soulStorage_1.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.storage,1,0), 6)))
					.apendPage(next("soulStorage").setText(StatCollector.translateToLocal("ec3.page.soulStorage_2.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.storage,1,1), 6)))
					.apendPage(next("soulStorage").setText(StatCollector.translateToLocal("ec3.page.soulStorage_3.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.storage,1,2), 6)))
					.apendPage(next("soulStorage").setText(StatCollector.translateToLocal("ec3.page.soulStorage_4.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.storage,1,3), 6)))
					.apendPage(next("soulStorage").setText(StatCollector.translateToLocal("ec3.page.soulStorage_5.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.storage,1,4), 6)))
					.apendPage(next("soulStorage").setText(StatCollector.translateToLocal("ec3.page.soulStorage_6.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.chargingChamber,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalDigger")
					 
					.setReferal(new ItemStack(ItemsCore.magicalDigger,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicalDigger,1,0))
					.apendPage(next("magicalDigger").setText(StatCollector.translateToLocal("ec3.page.magicalDigger_0.txt")))
					.apendPage(next("magicalDigger").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicalDigger,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalLantern")
					 
					.setReferal(new ItemStack(ItemsCore.magicalLantern,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicalLantern,1,0))
					.apendPage(next("magicalLantern").setText(StatCollector.translateToLocal("ec3.page.magicalLantern_0.txt")))
					.apendPage(next("magicalLantern").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicalLantern,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magnetizingStaff")
					 
					.setReferal(new ItemStack(ItemsCore.magnetizingStaff,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magnetizingStaff,1,0))
					.apendPage(next("magnetizingStaff").setText(StatCollector.translateToLocal("ec3.page.magnetizingStaff_0.txt")))
					.apendPage(next("magnetizingStaff").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magnetizingStaff,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.spawnerCollector")
					 
					.setReferal(new ItemStack(ItemsCore.spawnerCollector,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.spawnerCollector,1,0))
					.apendPage(next("spawnerCollector").setText(StatCollector.translateToLocal("ec3.page.spawnerCollector_0.txt")))
					.apendPage(next("spawnerCollector").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.spawnerCollector,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.lifeStaff")
					 
					.setReferal(new ItemStack(ItemsCore.staffOfLife,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.staffOfLife,1,0))
					.apendPage(next("lifeStaff").setText(StatCollector.translateToLocal("ec3.page.lifeStaff_0.txt")))
					.apendPage(next("lifeStaff").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.staffOfLife,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.holyMace")
					 
					.setReferal(new ItemStack(ItemsCore.holyMace,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.holyMace,1,0))
					.apendPage(next("holyMace").setText(StatCollector.translateToLocal("ec3.page.holyMace_0.txt")))
					.apendPage(next("holyMace").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.holyMace,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.emeraldHeart")
					 
					.setReferal(new ItemStack(ItemsCore.emeraldHeart,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.emeraldHeart,1,0))
					.apendPage(next("emeraldHeart").setText(StatCollector.translateToLocal("ec3.page.emeraldHeart_0.txt")))
					.apendPage(next("emeraldHeart").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.emeraldHeart,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalWings")
					 
					.setReferal(new ItemStack(ItemsCore.magicalWings,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicalWings,1,0))
					.apendPage(next("magicalWings").setText(StatCollector.translateToLocal("ec3.page.magicalWings_0.txt")))
					.apendPage(next("magicalWings").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicalWings,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalPorkchop")
					 
					.setReferal(new ItemStack(ItemsCore.magicalPorkchop,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicalPorkchop,1,0))
					.apendPage(next("magicalPorkchop").setText(StatCollector.translateToLocal("ec3.page.magicalPorkchop_0.txt")))
					.apendPage(next("magicalPorkchop").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicalPorkchop,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalWater")
					 
					.setReferal(new ItemStack(ItemsCore.magicWaterBottle,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicWaterBottle,1,0))
					.apendPage(next("magicalWater").setText(StatCollector.translateToLocal("ec3.page.magicalWater_0.txt")))
					.apendPage(next("magicalWater").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicWaterBottle,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalShield")
					 
					.setReferal(new ItemStack(ItemsCore.magicalShield,1,0),new ItemStack(ItemsCore.spikyShield,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicalShield,1,0))
					.apendPage(next("magicalShield").setText(StatCollector.translateToLocal("ec3.page.magicalShield_0.txt")))
					.apendPage(next("magicalShield").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicalShield,1,0), 2)))
					.apendPage(next("magicalShield").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.spikyShield,1,0), 2)))
				)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.biomeWand")
					 
					.setReferal(new ItemStack(ItemsCore.biomeWand,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.biomeWand,1,0))
					.apendPage(next("biomeWand").setText(StatCollector.translateToLocal("ec3.page.biomeWand_0.txt")))
					.apendPage(next("biomeWand").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.biomeWand,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.chaosFork")
					 
					.setReferal(new ItemStack(ItemsCore.chaosFork,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.chaosFork,1,0))
					.apendPage(next("chaosFork").setText(StatCollector.translateToLocal("ec3.page.chaosFork_0.txt")))
					.apendPage(next("chaosFork").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.chaosFork,1,0), 2)))
				)					
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.frozenMace") 
					.setReferal(new ItemStack(ItemsCore.frozenMace,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.frozenMace,1,0))
					.apendPage(next("frozenMace").setText(StatCollector.translateToLocal("ec3.page.frozenMace_0.txt")))
					.apendPage(next("frozenMace").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.frozenMace,1,0), 2)))
				)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magmaticStaff") 
					.setReferal(new ItemStack(ItemsCore.magmaticStaff,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magmaticStaff,1,0))
					.apendPage(next("magmaticStaff").setText(StatCollector.translateToLocal("ec3.page.magmaticStaff_0.txt")))
					.apendPage(next("magmaticStaff").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magmaticStaff,1,0), 2)))
				)						
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mhs") 
					.setReferal(new ItemStack(ItemsCore.magicArmorItems[0],1,0),new ItemStack(ItemsCore.magicArmorItems[1],1,0),new ItemStack(ItemsCore.magicArmorItems[2],1,0),new ItemStack(ItemsCore.magicArmorItems[3],1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicArmorItems[0],1,0))
					.apendPage(next("mhs").setText(StatCollector.translateToLocal("ec3.page.mhs_0.txt")))
					.apendPage(next("mhs").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicArmorItems[0],1,0), 2)))
					.apendPage(next("mhs").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicArmorItems[1],1,0), 2)))
					.apendPage(next("mhs").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicArmorItems[2],1,0), 2)))
					.apendPage(next("mhs").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicArmorItems[3],1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.elementalCharms")
					.setReferal(new ItemStack(ItemsCore.charm,1,0),new ItemStack(ItemsCore.charm,1,1),new ItemStack(ItemsCore.charm,1,2),new ItemStack(ItemsCore.charm,1,3),new ItemStack(ItemsCore.charm,1,4),new ItemStack(ItemsCore.charm,1,5),new ItemStack(ItemsCore.charm,1,6),new ItemStack(ItemsCore.charm,1,7),new ItemStack(ItemsCore.charm,1,8),new ItemStack(ItemsCore.charm,1,9))
					.setDisplayStack(new ItemStack(ItemsCore.charm,1,7))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_0.txt")))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_1.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,0), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_2.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,1), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_3.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,2), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_4.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,3), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_5.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,4), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_6.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,5), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_7.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,6), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_8.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,7), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_9.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,8), 2)))
					.apendPage(next("elementalCharms").setText(StatCollector.translateToLocal("ec3.page.elementalCharms_10.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.charm,1,9), 2)))
				)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mruCoil") 
					.setReferal(new ItemStack(ItemsCore.playerList,1,0),new ItemStack(BlocksCore.mruCoil,1,0),new ItemStack(BlocksCore.mruCoilHardener,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.mruCoil,1,0))
					.apendPage(next("mruCoil").setText(StatCollector.translateToLocal("ec3.page.mruCoil_0.txt")))
					.apendPage(next("mruCoil").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mruCoil,1,0), 2)))
					.apendPage(next("mruCoil").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mruCoilHardener,1,0), 2)))
					.apendPage(next("mruCoil").setRecipe(new StructureRecipe(new ItemStack(BlocksCore.mruCoil,1,0),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, 1, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 1, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, 1, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, -1, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, -1, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, -1, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 2),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, -2),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 3),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, -3),
							new StructureBlock(BlocksCore.magicPlating, 0, -3, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 3, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, -3, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, 3, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, -3, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, 3, 0, -1),
							new StructureBlock(BlocksCore.magicPlating, 0, 1, 0, 3),
							new StructureBlock(BlocksCore.magicPlating, 0, 1, 0, -3),
							new StructureBlock(BlocksCore.magicPlating, 0, -1, 0, 3),
							new StructureBlock(BlocksCore.magicPlating, 0, -1, 0, -3)
							)))
					.apendPage(next("mruCoil").setText(StatCollector.translateToLocal("ec3.page.mruCoil_4.txt")))
					.apendPage(next("mruCoil").setText(StatCollector.translateToLocal("ec3.page.mruCoil_5.txt")))
					.apendPage(next("mruCoil").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.playerList,1,0), 2)))
					.apendPage(next("mruCoil").setText(StatCollector.translateToLocal("ec3.page.mruCoil_7.txt")))
				)				
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.potionSpreader") 
					.setReferal(new ItemStack(BlocksCore.potionSpreader,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.potionSpreader,1,0))
					.apendPage(next("potionSpreader").setText(StatCollector.translateToLocal("ec3.page.potionSpreader_0.txt")))
					.apendPage(next("potionSpreader").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.potionSpreader,1,0), 2)))
				)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalEnchanter") 
					.setReferal(new ItemStack(BlocksCore.magicalEnchanter,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.magicalEnchanter,1,0))
					.apendPage(next("magicalEnchanter").setText(StatCollector.translateToLocal("ec3.page.magicalEnchanter_0.txt")))
					.apendPage(next("magicalEnchanter").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicalEnchanter,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.monsterHolder") 
					.setReferal(new ItemStack(BlocksCore.monsterClinger,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.monsterClinger,1,0))
					.apendPage(next("monsterHolder").setText(StatCollector.translateToLocal("ec3.page.monsterHolder_0.txt")))
					.apendPage(next("monsterHolder").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.monsterClinger,1,0), 2)))
				)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalJukebox") 
					.setReferal(new ItemStack(BlocksCore.magicalJukebox,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.magicalJukebox,1,0))
					.apendPage(next("magicalJukebox").setText(StatCollector.translateToLocal("ec3.page.magicalJukebox_0.txt")))
					.apendPage(next("magicalJukebox").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicalJukebox,1,0), 2)))
				)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalRepairer") 
					.setReferal(new ItemStack(BlocksCore.magicalRepairer,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.magicalRepairer,1,0))
					.apendPage(next("magicalRepairer").setText(StatCollector.translateToLocal("ec3.page.magicalRepairer_0.txt")))
					.apendPage(next("magicalRepairer").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicalRepairer,1,0), 2)))
				)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.monsterDuplicator") 
					.setReferal(new ItemStack(BlocksCore.monsterHarvester,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.monsterHarvester,1,0))
					.apendPage(next("monsterDuplicator").setText(StatCollector.translateToLocal("ec3.page.monsterDuplicator_0.txt")))
					.apendPage(next("monsterDuplicator").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.monsterHarvester,1,0), 2)))
				)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magmaticSmeltery") 
					.setReferal(new ItemStack(BlocksCore.magmaticSmeltery,1,0))
					.setDisplayStack(new ItemStack(BlocksCore.magmaticSmeltery,1,0))
					.apendPage(next("magmaticSmeltery").setText(StatCollector.translateToLocal("ec3.page.magmaticSmeltery_0.txt")))
					.apendPage(next("magmaticSmeltery").setImg(new ResourceLocation("essentialcraft","textures/special/bookIcons/smeltery.png")))
					.apendPage(next("magmaticSmeltery").setText(StatCollector.translateToLocal("ec3.page.magmaticSmeltery_2.txt")))
					.apendPage(next("magmaticSmeltery").setText(StatCollector.translateToLocal("ec3.page.magmaticSmeltery_3.txt")))
					.apendPage(next("magmaticSmeltery").setText(StatCollector.translateToLocal("ec3.page.magmaticSmeltery_4.txt")))
					.apendPage(next("magmaticSmeltery").setText(StatCollector.translateToLocal("ec3.page.magmaticSmeltery_5.txt")))
					.apendPage(next("magmaticSmeltery").setText(StatCollector.translateToLocal("ec3.page.magmaticSmeltery_6.txt")))
					.apendPage(next("magmaticSmeltery").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magmaticSmeltery,1,0), 2)))
				)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalQuarry") 
					.setReferal(new ItemStack(BlocksCore.magicalQuarry,1,0),generic(18),generic(19),generic(17),generic(80),generic(81),generic(78),generic(77))
					.setDisplayStack(new ItemStack(BlocksCore.magicalQuarry,1,0))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_0.txt")))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_1.txt")))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_2.txt")))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_3.txt")))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_4.txt")).setRecipe(ECUtils.findRecipeByIS(generic(18), 2)))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_5.txt")).setRecipe(ECUtils.findRecipeByIS(generic(19), 2)))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_6.txt")).setRecipe(ECUtils.findRecipeByIS(generic(17), 2)))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_7.txt")).setRecipe(ECUtils.findRecipeByIS(generic(80), 2)))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_8.txt")).setRecipe(ECUtils.findRecipeByIS(generic(81), 2)))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_9.txt")).setRecipe(ECUtils.findRecipeByIS(generic(78), 2)))
					.apendPage(next("magicalQuarry").setText(StatCollector.translateToLocal("ec3.page.magicalQuarry_10.txt")).setRecipe(ECUtils.findRecipeByIS(generic(77), 2)))
					.apendPage(next("magicalQuarry").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicalQuarry,1,0), 2)))
				)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.hoanna") 
					.setReferal(new ItemStack(ItemsCore.windTablet,1,0))
					.setDisplayStack(new ItemStack(ItemsCore.windTablet,1,0))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_0.txt")))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_1.txt")))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_2.txt")))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_3.txt")))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_4.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.windTablet,1,0), 2)))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_5.txt")))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_6.txt")))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_7.txt")))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_8.txt")))
					.apendPage(next("hoanna").setText(StatCollector.translateToLocal("ec3.page.hoanna_9.txt")))
					.apendPage(next("hoanna").setRecipe(ECUtils.findRecipeByIS(book_t3, 2)))
				)				
		;
	}
	
	public static void registerHoannaCategory()
	{
		ItemStack lootable = new ItemStack(ItemsCore.bauble,1,0);
		ItemBaublesWearable.initRandomTag(lootable, new Random());
		hoanna
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.radiation")
						.setDisplayStack(new ResourceLocation("essentialcraft","textures/special/radiation_icon.png"))
							.apendPage(next("radiation").setText(StatCollector.translateToLocal("ec3.page.radiation_0.txt")))
							.apendPage(next("radiation").setText(StatCollector.translateToLocal("ec3.page.radiation_1.txt")))
							.apendPage(next("radiation").setText(StatCollector.translateToLocal("ec3.page.radiation_2.txt")))
							.apendPage(next("radiation").setText(StatCollector.translateToLocal("ec3.page.radiation_3.txt")))
							.apendPage(next("radiation").setText(StatCollector.translateToLocal("ec3.page.radiation_4.txt")))
							.apendPage(next("radiation").setText(StatCollector.translateToLocal("ec3.page.radiation_5.txt")))
							.apendPage(next("radiation").setText(StatCollector.translateToLocal("ec3.page.radiation_6.txt")))
							.apendPage(next("radiation").setText(StatCollector.translateToLocal("ec3.page.radiation_7.txt")))
			)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.loot")
						.setDisplayStack(generic(37))
						.setReferal(generic(35),generic(36),generic(37),new ItemStack(ItemsCore.titanite,1,0),new ItemStack(ItemsCore.twinkling_titanite,1,0),new ItemStack(ItemsCore.ember,1,0),new ItemStack(ItemsCore.ember,1,1),new ItemStack(ItemsCore.ember,1,2),new ItemStack(ItemsCore.ember,1,3),new ItemStack(ItemsCore.ember,1,4),new ItemStack(ItemsCore.ember,1,5),new ItemStack(ItemsCore.ember,1,6),new ItemStack(ItemsCore.ember,1,7))
							.apendPage(next("loot").setText(StatCollector.translateToLocal("ec3.page.loot_0.txt")))
							.apendPage(next("loot").setDisplayStacks(generic(35),generic(36),generic(37),new ItemStack(ItemsCore.titanite,1,0),new ItemStack(ItemsCore.twinkling_titanite,1,0),new ItemStack(ItemsCore.ember,1,0),new ItemStack(ItemsCore.ember,1,1),new ItemStack(ItemsCore.ember,1,2),new ItemStack(ItemsCore.ember,1,3),new ItemStack(ItemsCore.ember,1,4),new ItemStack(ItemsCore.ember,1,5),new ItemStack(ItemsCore.ember,1,6),new ItemStack(ItemsCore.ember,1,7),lootable))
			)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.voidStone")
					.setDisplayStack(new ItemStack(BlocksCore.voidStone,1,0))
					.setReferal(new ItemStack(BlocksCore.voidStone,1,0),new ItemStack(BlocksCore.voidGlass,1,0))
					.apendPage(next("voidStone").setText(StatCollector.translateToLocal("ec3.page.voidStone_0.txt")))
					.apendPage(next("voidStone").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.voidStone), 2)))
					.apendPage(next("voidStone").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.voidGlass), 2)))
			)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.voidArmor") 
					.setReferal(new ItemStack(ItemsCore.magicArmorItems[4],1,0),new ItemStack(ItemsCore.magicArmorItems[5],1,0),new ItemStack(ItemsCore.magicArmorItems[6],1,0),new ItemStack(ItemsCore.magicArmorItems[7],1,0))
					.setDisplayStack(new ItemStack(ItemsCore.magicArmorItems[5],1,0))
					.apendPage(next("voidArmor").setText(StatCollector.translateToLocal("ec3.page.voidArmor_0.txt")))
					.apendPage(next("voidArmor").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicArmorItems[4],1,0), 2)))
					.apendPage(next("voidArmor").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicArmorItems[5],1,0), 2)))
					.apendPage(next("voidArmor").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicArmorItems[6],1,0), 2)))
					.apendPage(next("voidArmor").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicArmorItems[7],1,0), 2)))
			)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalTeleporter") 
					.setReferal(new ItemStack(BlocksCore.magicalTeleporter))
					.setDisplayStack(new ItemStack(BlocksCore.magicalTeleporter))
					.apendPage(next("magicalTeleporter").setText(StatCollector.translateToLocal("ec3.page.magicalTeleporter_0.txt")))
					.apendPage(next("magicalTeleporter").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicalTeleporter), 2)))
					.apendPage(next("magicalTeleporter").setText(StatCollector.translateToLocal("ec3.page.magicalTeleporter_2.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.magicalTeleporter), 
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 2),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 1),
							new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 0),
							new StructureBlock(BlocksCore.magicalTeleporter, 0, 0, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 0),
							new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -2),
							new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2)
							)
					))
					.apendPage(next("magicalTeleporter").setText(StatCollector.translateToLocal("ec3.page.magicalTeleporter_3.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.magicalTeleporter), 
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -2)
						)
					))
				.apendPage(next("magicalTeleporter").setText(StatCollector.translateToLocal("ec3.page.magicalTeleporter_4.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.magicalTeleporter), 
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -2)
						)
				))
			)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalFurnace") 
					.setReferal(new ItemStack(BlocksCore.magicalFurnace))
					.setDisplayStack(new ItemStack(BlocksCore.magicalFurnace))
					.apendPage(next("magicalFurnace").setText(StatCollector.translateToLocal("ec3.page.magicalFurnace_0.txt")))
					.apendPage(next("magicalFurnace").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.magicalFurnace), 2)))
					.apendPage(next("magicalFurnace").setText(StatCollector.translateToLocal("ec3.page.magicalFurnace_2.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.magicalFurnace), 
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2)
							)
					))
					.apendPage(next("magicalFurnace").setText(StatCollector.translateToLocal("ec3.page.magicalFurnace_3.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.magicalFurnace), 
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
							new StructureBlock(BlocksCore.magicalFurnace, 0, 0, 0, 0)
						)
					))
				.apendPage(next("magicalFurnace").setText(StatCollector.translateToLocal("ec3.page.magicalFurnace_4.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.magicalFurnace), 
						new StructureBlock(BlocksCore.heatGenerator, 0, -2, 0, -2),
						new StructureBlock(BlocksCore.heatGenerator, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.heatGenerator, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.heatGenerator, 0, 2, 0, 2)
						)
				))
			)
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.emberForge") 
					.setReferal(new ItemStack(BlocksCore.emberForge))
					.setDisplayStack(new ItemStack(BlocksCore.emberForge))
					.apendPage(next("emberForge").setText(StatCollector.translateToLocal("ec3.page.emberForge_0.txt")))
					.apendPage(next("emberForge").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.emberForge), 2)))
					.apendPage(next("emberForge").setText(StatCollector.translateToLocal("ec3.page.emberForge_2.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.emberForge), 
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 1),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -1),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 0, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2)
							)
					))
					.apendPage(next("emberForge").setText(StatCollector.translateToLocal("ec3.page.emberForge_3.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.emberForge), 
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2),
							new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
							new StructureBlock(BlocksCore.platingPale, 0, 0, 0, -2),
							new StructureBlock(BlocksCore.platingPale, 0, 0, 0, 2),
							new StructureBlock(BlocksCore.platingPale, 0, 2, 0, 0),
							new StructureBlock(BlocksCore.platingPale, 0, -2, 0, 0),
							new StructureBlock(BlocksCore.emberForge, 0, 0, 0, 0)
						)
					))
				.apendPage(next("emberForge").setText(StatCollector.translateToLocal("ec3.page.emberForge_4.txt")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.emberForge), 
						new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, -2),
						new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, 2)
						)
				))
			)
			
			/*
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.mim")
					 
					.setDisplayStack(new ItemStack(BlocksCore.mim,1,0))
					.setReferal(new ItemStack(BlocksCore.mim,1,0),new ItemStack(BlocksCore.minEjector,1,0),new ItemStack(BlocksCore.minInjector,1,0),new ItemStack(ItemsCore.filter,1,0))
					.apendPage(next("mim").setText(StatCollector.translateToLocal("ec3.page.mim_0.txt")))
					.apendPage(next("mim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.filter,1,0), 2)))
					.apendPage(next("mim").setText(StatCollector.translateToLocal("ec3.page.mim_2.txt")))
					.apendPage(next("mim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.filter,1,1), 2)))
					.apendPage(next("mim").setText(StatCollector.translateToLocal("ec3.page.mim_4.txt")))
					.apendPage(next("mim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.minEjector,1,0), 2)))
					.apendPage(next("mim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.minEjector,1,6), 2)))
					.apendPage(next("mim").setText(StatCollector.translateToLocal("ec3.page.mim_7.txt")))
					.apendPage(next("mim").setText(StatCollector.translateToLocal("ec3.page.mim_8.txt")))
					.apendPage(next("mim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.minInjector,1,0), 2)))
					.apendPage(next("mim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.minInjector,1,6), 2)))
					.apendPage(next("mim").setText(StatCollector.translateToLocal("ec3.page.mim_11.txt")))
					.apendPage(next("mim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mim,1,0), 2)))
					.apendPage(next("mim").setText(StatCollector.translateToLocal("ec3.page.mim_13.txt")))
			)	
			*/
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.o8elisk")
					.setDisplayStack(new ItemStack(BlocksCore.darknessObelisk,1,0))
					.setReferal(new ItemStack(BlocksCore.darknessObelisk,1,0))
					 
					.apendPage(next("o8elisk").setText(StatCollector.translateToLocal("ec3.page.o8elisk_0.txt")))
					.apendPage(next("o8elisk").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.darknessObelisk), 2)))
			)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.ultraHeat")
					.setDisplayStack(new ItemStack(BlocksCore.ultraHeatGen,1,0))
					.setReferal(new ItemStack(BlocksCore.ultraHeatGen,1,0))
					 
					.apendPage(next("ultraHeat").setText(StatCollector.translateToLocal("ec3.page.ultraHeat_0.txt")))
					.apendPage(next("ultraHeat").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ultraHeatGen), 2)))
			)		
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.ultraFlower")
					.setDisplayStack(new ItemStack(BlocksCore.ultraFlowerBurner,1,0))
					.setReferal(new ItemStack(BlocksCore.ultraFlowerBurner,1,0))
					 
					.apendPage(next("ultraFlower").setText(StatCollector.translateToLocal("ec3.page.ultraFlower_0.txt")))
					.apendPage(next("ultraFlower").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.ultraFlowerBurner), 2)))
			)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.magicalBuilder")
					.setDisplayStack(new ItemStack(ItemsCore.magicalBuilder,1,0))
					.setReferal(new ItemStack(ItemsCore.magicalBuilder,1,0))
					
					.apendPage(next("magicalBuilder").setText(StatCollector.translateToLocal("ec3.page.magicalBuilder_0.txt")))
					.apendPage(next("magicalBuilder").setText(StatCollector.translateToLocal("ec3.page.magicalBuilder_1.txt")))
					.apendPage(next("magicalBuilder").setText(StatCollector.translateToLocal("ec3.page.magicalBuilder_2.txt")))
					.apendPage(next("magicalBuilder").setText(StatCollector.translateToLocal("ec3.page.magicalBuilder_3.txt")))
					.apendPage(next("magicalBuilder").setText(StatCollector.translateToLocal("ec3.page.magicalBuilder_4.txt")))
					.apendPage(next("magicalBuilder").setText(StatCollector.translateToLocal("ec3.page.magicalBuilder_5.txt")))
					.apendPage(next("magicalBuilder").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.magicalBuilder), 2)))
			)	
			.apendDiscovery(new DiscoveryEntry("hologram")
					
					.setDisplayStack(new ItemStack(ItemsCore.genericItem,1,76))
					.setReferal(new ItemStack(ItemsCore.genericItem,1,70),new ItemStack(ItemsCore.genericItem,1,71),new ItemStack(ItemsCore.genericItem,1,72),new ItemStack(ItemsCore.genericItem,1,73),new ItemStack(BlocksCore.holopad,1,0))
					.apendPage(next("hologram").setText(StatCollector.translateToLocal("ec3.page.hologram_0.txt")))
					.apendPage(next("hologram").setText(StatCollector.translateToLocal("ec3.page.hologram_1.txt")).setDisplayStacks(new ItemStack(ItemsCore.genericItem,1,70),new ItemStack(ItemsCore.genericItem,1,71),new ItemStack(ItemsCore.genericItem,1,72),new ItemStack(ItemsCore.genericItem,1,73)))
					.apendPage(next("hologram").setText(StatCollector.translateToLocal("ec3.page.hologram_2.txt")))
					.apendPage(next("hologram").setText(StatCollector.translateToLocal("ec3.page.hologram_3.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.holopad), 2)))
			)
			.apendDiscovery(new DiscoveryEntry("redSuit")
					
					.setDisplayStack(new ItemStack(ItemsCore.computer_helmet,1,0))
					.setReferal(new ItemStack(ItemsCore.computer_helmet,1,0),new ItemStack(ItemsCore.computer_chestplate,1,0),new ItemStack(ItemsCore.computer_leggings,1,0),new ItemStack(ItemsCore.computer_boots,1,0),new ItemStack(ItemsCore.computerBoard,1,0),gen(56),gen(57),gen(58),gen(59),gen(60),gen(61),gen(62),gen(63),gen(64),gen(65),gen(66),gen(67),gen(68),gen(69),gen(74),gen(75))
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_0.txt")))
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_1.txt")))
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_2.txt")))
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_3.txt")))
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_4.txt")))
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_5.txt")))
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_6.txt")))
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_7.txt")))
					
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(54), 6)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(53), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(59), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(74), 2)))	
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(68), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(69), 2)))
					
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(56), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(60), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(63), 2)))
					
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(65), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(67), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(66), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(58), 2)))
					
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(75), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(57), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(64), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(61), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(gen(62), 2)))
					
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.computer_helmet), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.computer_chestplate), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.computer_leggings), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.computer_boots), 2)))
					.apendPage(next("redSuit").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.computerBoard), 2)))
					
					.apendPage(next("redSuit").setText(StatCollector.translateToLocal("ec3.page.redSuit_?.txt")))
			)	
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.newMim")
					.setNew()
					.setDisplayStack(new ItemStack(BlocksCore.newMim,1,0))
					.setReferal(new ItemStack(BlocksCore.newMim,1,0),new ItemStack(BlocksCore.mimEjector,1,0),new ItemStack(BlocksCore.mimInjector,1,0),new ItemStack(BlocksCore.mimEjectorP,1,0),new ItemStack(BlocksCore.mimInjectorP,1,0))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_0.txt")))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_1.txt")))
					.apendPage(next("newMim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.newMim), 2)))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_2.txt")))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_3.txt")))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_4.txt")))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_5.txt")))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_6.txt")))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_7.txt")))
					.apendPage(next("newMim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mimEjector), 2)))
					.apendPage(next("newMim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mimInjector), 2)))
					.apendPage(next("newMim").setText(StatCollector.translateToLocal("ec3.page.newMim_8.txt")))
					.apendPage(next("newMim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mimEjectorP), 2)))
					.apendPage(next("newMim").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mimInjectorP), 2)))
					)
			.apendDiscovery(new DiscoveryEntry("newMimInv")
					.setNew()
					.setDisplayStack(new ItemStack(BlocksCore.mimInvStorage))
					.setReferal(new ItemStack(BlocksCore.mimInvStorage),new ItemStack(ItemsCore.inventoryGem))
					.apendPage(next("newMimInv").setText(StatCollector.translateToLocal("ec3.page.newMimInv_0.txt")))
					.apendPage(next("newMimInv").setText(StatCollector.translateToLocal("ec3.page.newMimInv_1.txt")))
					.apendPage(next("newMimInv").setText(StatCollector.translateToLocal("ec3.page.newMimInv_2.txt")))
					.apendPage(next("newMimInv").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mimInvStorage), 2)))
					.apendPage(next("newMimInv").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.inventoryGem), 2)))
			)
			.apendDiscovery(new DiscoveryEntry("newMimCraft")
					.setNew()
					.setDisplayStack(new ItemStack(BlocksCore.mimCrafter))
					.setReferal(new ItemStack(BlocksCore.mimCrafter))
					.apendPage(next("newMimCraft").setText(StatCollector.translateToLocal("ec3.page.newMimCraft_0.txt")))
					.apendPage(next("newMimCraft").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mimCrafter), 2)))
			)
			.apendDiscovery(new DiscoveryEntry("newMimScreen")
					.setNew()
					.setDisplayStack(new ItemStack(BlocksCore.mimScreen))
					.setReferal(new ItemStack(BlocksCore.mimScreen))
					.apendPage(next("newMimScreen").setText(StatCollector.translateToLocal("ec3.page.newMimScreen_0.txt")))
					.apendPage(next("newMimScreen").setText(StatCollector.translateToLocal("ec3.page.newMimScreen_1.txt")))
					.apendPage(next("newMimScreen").setText(StatCollector.translateToLocal("ec3.page.newMimScreen_2.txt")))
					.apendPage(next("newMimScreen").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mimScreen), 2)))
			)
		;
	}
	
	public static ItemStack generic(int meta)
	{
		return new ItemStack(ItemsCore.genericItem,0,meta);
	}
	
	public static ItemStack gen(int meta)
	{
		return new ItemStack(ItemsCore.genericItem,0,meta);
	}
	
	public static void registerEnderCategory()
	{
		ender
		.apendDiscovery(
				new DiscoveryEntry("enderstar")
				
				.setDisplayStack(new ItemStack(ItemsCore.genericItem,1,48))
				.apendPage(next("enderstar").setText(StatCollector.translateToLocal("ec3.page.enderstar_0.txt")))
				.apendPage(next("enderstar").setText(StatCollector.translateToLocal("ec3.page.enderstar_1.txt")))
				.apendPage(next("enderstar").setText(StatCollector.translateToLocal("ec3.page.enderstar_2.txt")))
				)
		.apendDiscovery(
				new DiscoveryEntry("mithriline")
				
				.setDisplayStack(new ItemStack(ItemsCore.genericItem,1,51))
				.setReferal(new ItemStack(ItemsCore.genericItem,1,51),new ItemStack(ItemsCore.genericItem,1,50),new ItemStack(ItemsCore.genericItem,1,49),new ItemStack(ItemsCore.genericItem,1,48),new ItemStack(BlocksCore.invertedBlock,1,0))
				.apendPage(next("mithriline").setText(StatCollector.translateToLocal("ec3.page.mithriline_0.txt")))
				.apendPage(next("mithriline").setText(StatCollector.translateToLocal("ec3.page.mithriline_1.txt")).setDisplayStacks(new ItemStack(ItemsCore.genericItem,1,51)))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,50), 2)))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,49), 6)))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,48), 2)))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.genericItem,1,50), 2)))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.invertedBlock,1,0), 2)).setText(StatCollector.translateToLocal("ec3.page.mithriline_2.txt")))
				.apendPage(next("mithriline").setText(StatCollector.translateToLocal("ec3.page.mithriline_3.txt")))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mithrilineCrystal,1,0), 2)))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mithrilineCrystal,1,3), 2)))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mithrilineCrystal,1,6), 2)))
				.apendPage(next("mithriline").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mithrilineCrystal,1,9), 2)))
				)
		.apendDiscovery(
				new DiscoveryEntry("mithrilineFurnace")
				
				.setDisplayStack(new ItemStack(BlocksCore.mithrilineFurnace))
				.setReferal(new ItemStack(BlocksCore.mithrilineFurnace))
				.apendPage(next("mithrilineFurnace").setText(StatCollector.translateToLocal("ec3.page.mithrilineFurnace_0.txt")))
				.apendPage(next("mithrilineFurnace").setText(StatCollector.translateToLocal("ec3.page.mithrilineFurnace_1.txt")))
				.apendPage(next("mithrilineFurnace").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.mithrilineFurnace,1,0), 2)))
				.apendPage(next("mithrilineFurnace").setText(StatCollector.translateToLocal("ec3.txt.layer_-1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.mithrilineFurnace),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 1),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, -1),
						new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 1),
						new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -1),
						new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 1),
						new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -1),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, -2),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, -2)
						)))
				.apendPage(next("mithrilineFurnace").setText(StatCollector.translateToLocal("ec3.txt.layer_0")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.mithrilineFurnace),
						new StructureBlock(BlocksCore.mithrilineFurnace, 0, 0, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, -2),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, -2)
						)))
				.apendPage(next("mithrilineFurnace").setText(StatCollector.translateToLocal("ec3.txt.layer_1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.mithrilineFurnace),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, -2)
						)))
				.apendPage(next("mithrilineFurnace").setText(StatCollector.translateToLocal("ec3.txt.layer_2")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.mithrilineFurnace),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, -2)
						)))						
				)
		.apendDiscovery(
				new DiscoveryEntry("windRune")
				
				.setDisplayStack(new ItemStack(BlocksCore.windRune))
				.setReferal(new ItemStack(BlocksCore.windRune))
				.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.page.windRune_0.txt")))
				.apendPage(next("windRune").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.windRune,1,0), 2)))
				.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.txt.layer_-1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.windRune),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 0),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 1),
						new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, -1),
						new StructureBlock(BlocksCore.platingPale, 0, 1, 0, 1),
						new StructureBlock(BlocksCore.platingPale, 0, 2, 0, 1),
						new StructureBlock(BlocksCore.platingPale, 0, 1, 0, 2),
						new StructureBlock(BlocksCore.platingPale, 0, -1, 0, 1),
						new StructureBlock(BlocksCore.platingPale, 0, -2, 0, 1),
						new StructureBlock(BlocksCore.platingPale, 0, -1, 0, 2),
						new StructureBlock(BlocksCore.platingPale, 0, 1, 0, -1),
						new StructureBlock(BlocksCore.platingPale, 0, 2, 0, -1),
						new StructureBlock(BlocksCore.platingPale, 0, 1, 0, -2),
						new StructureBlock(BlocksCore.platingPale, 0, -1, 0, -1),
						new StructureBlock(BlocksCore.platingPale, 0, -2, 0, -1),
						new StructureBlock(BlocksCore.platingPale, 0, -1, 0, -2),
						new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, 0),
						new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, 0),
						new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 2),
						new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, -2)
						)))	
						.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.txt.layer_0")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.windRune),
								new StructureBlock(BlocksCore.windRune, 0, 0, 0, 0),
								new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 1),
								new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 1),
								new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -1),
								new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -1),
								new StructureBlock(BlocksCore.magicPlating, 0, 2, 0, 0),
								new StructureBlock(BlocksCore.magicPlating, 0, -2, 0, 0),
								new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, 2),
								new StructureBlock(BlocksCore.magicPlating, 0, 0, 0, -2),
								new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, 2),
								new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, 2),
								new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, -2),
								new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, -2)
						)))	
						.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.txt.layer_1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.windRune),
								new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 1),
								new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 1),
								new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -1),
								new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -1)
						)))	
						.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.txt.layer_2")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.windRune),
								new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, 1),
								new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, 1),
								new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, -1),
								new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, -1)
						)))	
						.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.page.windRune_1.txt")))
						.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.page.windRune_2.txt")))
						.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.page.windRune_3.txt")))
						.apendPage(next("windRune").setText(StatCollector.translateToLocal("ec3.page.windRune_4.txt")))
						.apendPage(next("windRune").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.wind_elemental_axe), 2)))
						.apendPage(next("windRune").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.wind_elemental_pick), 2)))
						.apendPage(next("windRune").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.wind_elemental_hoe), 2)))
						.apendPage(next("windRune").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.wind_elemental_shovel), 2)))
						.apendPage(next("windRune").setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.wind_elemental_sword), 2)))
				)
		.apendDiscovery(
				new DiscoveryEntry("playerPentacle")
				
				.setDisplayStack(new ItemStack(BlocksCore.playerPentacle))
				.setReferal(new ItemStack(BlocksCore.playerPentacle))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.page.playerPentacle_0.txt")))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.page.playerPentacle_1.txt")))
				.apendPage(next("playerPentacle").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.playerPentacle), 2)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_-1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 2),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 2),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -2),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -2)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_0")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.playerPentacle, 0, 0, 0, 0),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, -2)
					)))
					
					.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.page.playerPentacle_2.txt")))
					.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_-1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 2),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 2),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -2),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -2)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_0")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.playerPentacle, 0, 0, 0, 0),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, -2),
						new StructureBlock(BlocksCore.blockPale, 0, 1, 0, 1),
						new StructureBlock(BlocksCore.blockPale, 0, -1, 0, 1),
						new StructureBlock(BlocksCore.blockPale, 0, 1, 0, -1),
						new StructureBlock(BlocksCore.blockPale, 0, -1, 0, -1)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 1, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -1, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 1, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -1, 0, -1)
					)))
					
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.page.playerPentacle_3.txt")))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_-1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 2),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 2),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -2),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -2)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_0")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.playerPentacle, 0, 0, 0, 0),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, -2),
						new StructureBlock(BlocksCore.blockPale, 0, 1, 0, 1),
						new StructureBlock(BlocksCore.blockPale, 0, -1, 0, 1),
						new StructureBlock(BlocksCore.blockPale, 0, 1, 0, -1),
						new StructureBlock(BlocksCore.blockPale, 0, -1, 0, -1),
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 1, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -1, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 1, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -1, 0, -1),
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_2")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.mithrilineCrystal, 6, 2, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 6, -2, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 6, 2, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 6, -2, 0, -2)
					)))
		
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.page.playerPentacle_4.txt")))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_-1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 0, 0, 0),
					new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 1),
					new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, -1),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, 2),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, 2),
					new StructureBlock(BlocksCore.invertedBlock, 0, 1, 0, -2),
					new StructureBlock(BlocksCore.invertedBlock, 0, -1, 0, -2),
					new StructureBlock(BlocksCore.demonicPlating, 0, 0, 0, 2),
					new StructureBlock(BlocksCore.demonicPlating, 0, 0, 0, -2),
					new StructureBlock(BlocksCore.demonicPlating, 0, 2, 0, 0),
					new StructureBlock(BlocksCore.demonicPlating, 0, -2, 0, 0)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_0")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.playerPentacle, 0, 0, 0, 0),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, 1, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 0, -1, 0, -2),
						new StructureBlock(BlocksCore.blockPale, 0, 1, 0, 1),
						new StructureBlock(BlocksCore.blockPale, 0, -1, 0, 1),
						new StructureBlock(BlocksCore.blockPale, 0, 1, 0, -1),
						new StructureBlock(BlocksCore.blockPale, 0, -1, 0, -1),
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 1, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -1, 0, 1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 1, 0, -1),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -1, 0, -1),
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, -2)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_2")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.mithrilineCrystal, 6, 2, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 6, -2, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 6, 2, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 6, -2, 0, -2)
					)))
				.apendPage(next("playerPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_3")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.playerPentacle),
						new StructureBlock(BlocksCore.mithrilineCrystal, 9, 2, 0, 0),
						new StructureBlock(BlocksCore.mithrilineCrystal, 9, -2, 0, 0),
						new StructureBlock(BlocksCore.mithrilineCrystal, 9, 0, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 9, 0, 0, -2)
					)))
				)
		.apendDiscovery(
				new DiscoveryEntry("demonicPentacle")
				
				.setDisplayStack(new ItemStack(BlocksCore.demonicPentacle))
				.apendPage(next("demonicPentacle").setText(StatCollector.translateToLocal("ec3.page.demonicPentacle_0.txt")))
				.apendPage(next("demonicPentacle").setText(StatCollector.translateToLocal("ec3.page.demonicPentacle_1.txt")))
				.apendPage(next("demonicPentacle").setText(StatCollector.translateToLocal("ec3.page.demonicPentacle_2.txt")))
				.apendPage(next("demonicPentacle").setRecipe(ECUtils.findRecipeByIS(new ItemStack(BlocksCore.demonicPentacle), 2)))
				.apendPage(next("demonicPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_-1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.demonicPentacle),
						new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 0),
						
						new StructureBlock(BlocksCore.voidStone, 0, 2, 0, 0),
						new StructureBlock(BlocksCore.voidStone, 0, -2, 0, 0),
						new StructureBlock(BlocksCore.voidStone, 0, 0, 0, 2),
						new StructureBlock(BlocksCore.voidStone, 0, 0, 0, -2),
						
						new StructureBlock(BlocksCore.voidStone, 0, 3, 0, 1),
						new StructureBlock(BlocksCore.voidStone, 0, 3, 0, -1),
						new StructureBlock(BlocksCore.voidStone, 0, -3, 0, 1),
						new StructureBlock(BlocksCore.voidStone, 0, -3, 0, -1),
						new StructureBlock(BlocksCore.voidStone, 0, 1, 0, 3),
						new StructureBlock(BlocksCore.voidStone, 0, -1, 0, 3),
						new StructureBlock(BlocksCore.voidStone, 0, 1, 0, -3),
						new StructureBlock(BlocksCore.voidStone, 0, -1, 0, -3),
						
						new StructureBlock(BlocksCore.platingPale, 0, 1, 0, 0),
						new StructureBlock(BlocksCore.platingPale, 0, -1, 0, 0),
						new StructureBlock(BlocksCore.platingPale, 0, 0, 0, 1),
						new StructureBlock(BlocksCore.platingPale, 0, 0, 0, -1),
						
						new StructureBlock(BlocksCore.platingPale, 0, 2, 0, 1),
						new StructureBlock(BlocksCore.platingPale, 0, 2, 0, -1),
						new StructureBlock(BlocksCore.platingPale, 0, -2, 0, 1),
						new StructureBlock(BlocksCore.platingPale, 0, -2, 0, -1),
						new StructureBlock(BlocksCore.platingPale, 0, 1, 0, 2),
						new StructureBlock(BlocksCore.platingPale, 0, -1, 0, 2),
						new StructureBlock(BlocksCore.platingPale, 0, 1, 0, -2),
						new StructureBlock(BlocksCore.platingPale, 0, -1, 0, -2),
						
						new StructureBlock(BlocksCore.platingPale, 0, 3, 0, 2),
						new StructureBlock(BlocksCore.platingPale, 0, 3, 0, -2),
						new StructureBlock(BlocksCore.platingPale, 0, -3, 0, 2),
						new StructureBlock(BlocksCore.platingPale, 0, -3, 0, -2),
						new StructureBlock(BlocksCore.platingPale, 0, 2, 0, 3),
						new StructureBlock(BlocksCore.platingPale, 0, -2, 0, 3),
						new StructureBlock(BlocksCore.platingPale, 0, 2, 0, -3),
						new StructureBlock(BlocksCore.platingPale, 0, -2, 0, -3)
					)))
				.apendPage(next("demonicPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_0")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.demonicPentacle),
						new StructureBlock(BlocksCore.demonicPentacle, 0, 0, 0, 0),
						
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, 2),
						new StructureBlock(BlocksCore.invertedBlock, 0, 2, 0, -2),
						new StructureBlock(BlocksCore.invertedBlock, 0, -2, 0, -2),
						
						new StructureBlock(Blocks.glowstone, 0, 3, 0, 0),
						new StructureBlock(Blocks.glowstone, 0, -3, 0, 0),
						new StructureBlock(Blocks.glowstone, 0, 0, 0, 3),
						new StructureBlock(Blocks.glowstone, 0, 0, 0, -3),
						
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 3, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 3, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -3, 0, 2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -3, 0, -2),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 2, 0, 3),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -2, 0, 3),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, 2, 0, -3),
						new StructureBlock(BlocksCore.mithrilineCrystal, 3, -2, 0, -3)
					)))		
					.apendPage(next("demonicPentacle").setText(StatCollector.translateToLocal("ec3.txt.layer_1")).setRecipe(new StructureRecipe(new ItemStack(BlocksCore.demonicPentacle),
							
							new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, 2),
							new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, 2),
							new StructureBlock(BlocksCore.mithrilineCrystal, 0, 2, 0, -2),
							new StructureBlock(BlocksCore.mithrilineCrystal, 0, -2, 0, -2),
							
							new StructureBlock(BlocksCore.mithrilineCrystal, 6, 3, 0, 0),
							new StructureBlock(BlocksCore.mithrilineCrystal, 6, -3, 0, 0),
							new StructureBlock(BlocksCore.mithrilineCrystal, 6, 0, 0, 3),
							new StructureBlock(BlocksCore.mithrilineCrystal, 6, 0, 0, -3)
						)))
					.apendPage(next("demonicPentacle").setText(StatCollector.translateToLocal("ec3.page.demonicPentacle_3.txt")).setRecipe(ECUtils.findRecipeByIS(new ItemStack(ItemsCore.soulScriber), 2)))
					
				)
		;
	}
	
	public static PageEntry next(String genID)
	{
		if(!stringIDS.containsKey(genID))
			stringIDS.put(genID, 0);
		int ptt = stringIDS.get(genID);
		stringIDS.put(genID, ptt+1);
		return new PageEntry("ec3.page."+genID+"_"+ptt);
	}
	
	public static void init()
	{
		stringIDS.clear();ApiCore.categories.clear();basic.discoveries.clear();mru.discoveries.clear();eng.discoveries.clear();hoanna.discoveries.clear();shade.discoveries.clear();ender.discoveries.clear();
		
		registerBasicCategory();
		registerMruCategory();
		
		registerEngineersCategory();
		
		registerHoannaCategory();
		
		registerEnderCategory();
		
		mru.setDisplayStack(new ItemStack(ItemsCore.drops,1,4));
		eng.setDisplayStack(new ItemStack(ItemsCore.wFocus,1,0));
		hoanna.setDisplayStack(new ItemStack(ItemsCore.genericItem,1,37));
		shade.setDisplayStack(new ItemStack(BlocksCore.lightCorruption[2],1,6));
		ender.setDisplayStack(new ItemStack(BlocksCore.mithrilineCrystal,1,0));
		
		ApiCore.categories.add(basic);
		ApiCore.categories.add(mru);
		ApiCore.categories.add(eng);
		ApiCore.categories.add(hoanna);
		ApiCore.categories.add(shade);
		ApiCore.categories.add(ender);
	}
	
	public static final CategoryEntry basic = new CategoryEntry("ec3.basic").setDisplayStack(new ResourceLocation("essentialcraft","textures/special/basical_knowledge_icon.png"));
	public static final CategoryEntry mru = new CategoryEntry("ec3.mru").setTier(1);
	public static final CategoryEntry eng = new CategoryEntry("ec3.eng").setTier(2);
	public static final CategoryEntry ender = new CategoryEntry("ec3.ender").setTier(2);
	public static final CategoryEntry hoanna = new CategoryEntry("ec3.hoanna").setTier(3).setSpecificTexture(new ResourceLocation("essentialcraft","textures/gui/research_book_hoanna.png"));
	public static final CategoryEntry shade = new CategoryEntry("ec3.shade").setTier(4);

}
