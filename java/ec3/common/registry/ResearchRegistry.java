package ec3.common.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import ec3.api.ApiCore;
import ec3.api.CategoryEntry;
import ec3.api.DiscoveryEntry;
import ec3.api.PageEntry;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemsCore;

public class ResearchRegistry {
	
	public static void init()
	{
		basic
			.apendDiscovery(
					new DiscoveryEntry("ec3.disc.wind")
					.setReferal(new ItemStack(ItemsCore.bottledWind,0,0),new ItemStack(ItemsCore.imprisonedWind,0,0),new ItemStack(ItemsCore.windKeeper,0,0),new ItemStack(ItemsCore.magicArmorItems[12],0,0),new ItemStack(ItemsCore.magicArmorItems[13],0,0),new ItemStack(ItemsCore.magicArmorItems[14],0,0),new ItemStack(ItemsCore.magicArmorItems[15],0,0))
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
		);
		
		mru.setDisplayStack(new ItemStack(ItemsCore.drops,1,4));
		eng.setDisplayStack(new ItemStack(ItemsCore.wFocus,1,0));
		hoanna.setDisplayStack(new ItemStack(ItemsCore.genericItem,1,37));
		shade.setDisplayStack(new ItemStack(BlocksCore.lightCorruption[2],1,6));
		ApiCore.categories.add(basic);
		ApiCore.categories.add(mru);
		ApiCore.categories.add(eng);
		ApiCore.categories.add(hoanna);
		ApiCore.categories.add(shade);
	}
	
	public static final CategoryEntry basic = new CategoryEntry("ec3.basic").setDisplayStack(new ResourceLocation("essentialcraft","textures/special/basical_knowledge_icon.png"));
	public static final CategoryEntry mru = new CategoryEntry("ec3.mru").setTier(1);
	public static final CategoryEntry eng = new CategoryEntry("ec3.eng").setTier(2);
	public static final CategoryEntry hoanna = new CategoryEntry("ec3.hoanna").setTier(3).setSpecificTexture(new ResourceLocation("essentialcraft","textures/gui/research_book_hoanna.png"));
	public static final CategoryEntry shade = new CategoryEntry("ec3.shade").setTier(4);

}
