package ec3.common.tile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemsCore;
import ec3.utils.common.ECUtils;

public class TileMagicalJukebox extends TileMRUGeneric{
	
	public int recordCooldownTime, recordPlayed;
	
	public TileMagicalJukebox()
	{
		 super();
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(2);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity()
	{
		String[] achievementNames = new String[]{"Top Secret!","Secret Achievement 2014!","Never give up on your secrets!","Go,Go secret rangers!","Such Secrets!","The Secret is a Lie!","Secret Bro!","Secrets, Secrets everywhere!","Secrets,Secrets,Secrets!","Never give up!","Unachievable!","Too much Secrets!","Very Secret!","Top Secret of 2164!","The Secret Paradize!"};
		String[] monsterNames = new String[]{"jeb_","Bob","Jordan","Monstro","Isaac's Fork","Bucker","Arthem","Cube","2XStuffed","Rainbow!","StringFormatException","java","Secret!!!","Ronny","Clementine","Jack","Roland","The Tower","Judjment","Jorji","EZIC","Roshan","Ursa","Enderbro","Zombie","poodiepie","Cartman","Markiplier","The Game Theorist!","Direwolf20","Pahimar","Eloraam","CoTH","[CoTH]","ForgeDevName","Player000","Herobrine","[DATA REMOVED]","SCP-126","No More!","The longest list!","Much Text","Thanks!","Time","Moon","Sun","Earth","Mars","Theory","Bang!","Poo","Lol 69","146%","2000","1337","OVER 9000!","++i + ++i","Brain","<--->>---<<-","Hello World!","Doge","The Wurm","Spice!","Happy",":3","^_^"};
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
		IInventory inv = this;
		int slotNum = 0;
		TileEntity tile = this;
		if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null)
		{
			ItemStack s = inv.getStackInSlot(slotNum);
			int[] o = ItemBoundGem.getCoords(s);
			if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
			{
    			if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU)
    			{
    				this.setBalance(((ITEHasMRU) tile.getWorldObj().getTileEntity(o[0], o[1], o[2])).getBalance());
    			}
    		}
		}
		
		if(this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == ItemsCore.record_secret && this.recordCooldownTime <= 0)
		{
			this.recordPlayed = 1;
			this.recordCooldownTime = 257*20;
			if(!this.worldObj.isRemote)
				this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1005, xCoord, yCoord, zCoord, Item.getIdFromItem(this.getStackInSlot(1).getItem()));
            return;
		}
		
		if(this.getStackInSlot(1) == null && this.recordCooldownTime > 100)
		{
			recordCooldownTime = 0;
			recordPlayed = 0;
			return;
		}
		
		if(this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == ItemsCore.record_secret && this.recordCooldownTime > 0)
		{
			double randomX = MathUtils.randomDouble(this.worldObj.rand)*12;
			double randomY = MathUtils.randomDouble(this.worldObj.rand)*12;
			double randomZ = MathUtils.randomDouble(this.worldObj.rand)*12;
			double randomColorR = this.worldObj.rand.nextDouble();
			double randomColorG = this.worldObj.rand.nextDouble();
			double randomColorB = this.worldObj.rand.nextDouble();
			boolean randomBool = this.worldObj.rand.nextBoolean();
			for(int i = 0; i < 50; ++i)
				this.worldObj.spawnParticle("mobSpell", xCoord+randomX, yCoord+randomY, zCoord+randomZ, randomColorR, randomColorG, randomColorB);
			for(int i = 0; i < 100; ++i)
			{
				if(randomBool)
					this.worldObj.spawnParticle("crit", xCoord+randomX, yCoord+randomY, zCoord+randomZ, MathUtils.randomDouble(this.worldObj.rand), 1.1D+MathUtils.randomDouble(this.worldObj.rand), MathUtils.randomDouble(this.worldObj.rand));
				else
					this.worldObj.spawnParticle("magicCrit", xCoord+randomX, yCoord+randomY, zCoord+randomZ, MathUtils.randomDouble(this.worldObj.rand), 1.1D+MathUtils.randomDouble(this.worldObj.rand), MathUtils.randomDouble(this.worldObj.rand));
			}
			--recordCooldownTime;
			
			int achievementID = this.worldObj.rand.nextInt(achievementNames.length);
			ChatStyle greenItalicAchievement = new ChatStyle().setColor(EnumChatFormatting.GREEN).setItalic(true);
			ChatComponentText achName = new ChatComponentText(achievementNames[achievementID]+"\n");
			achName.setChatStyle(greenItalicAchievement);
			ChatComponentText achievement = new ChatComponentText("Achievement");
			achievement.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE).setItalic(true));
			achName.appendSibling(achievement);
			achName.appendSibling(new ChatComponentText("\nAchievable!"));
			HoverEvent achievementEvent = new HoverEvent(Action.SHOW_TEXT, achName);
			ChatStyle achievementStyle = new ChatStyle().setColor(EnumChatFormatting.GREEN);
			achievementStyle.setChatHoverEvent(achievementEvent);
			
			ChatComponentText achievementText = new ChatComponentText("["+achievementNames[achievementID]+"]");
			achievementText.setChatStyle(achievementStyle);
			List<EntityPlayer> playerLst = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord-6, yCoord-6, zCoord-6, xCoord+6, yCoord+6, zCoord+6));
			if(!playerLst.isEmpty() && this.worldObj.getWorldTime() % 60 == 0 && !this.worldObj.isRemote)
			{
				EntityPlayer player = playerLst.get(this.worldObj.rand.nextInt(playerLst.size()));
				player.addChatMessage(new ChatComponentText(player.getDisplayName()+" has just earned the achievement ").appendSibling(achievementText));
			}
			List<EntitySheep> sheepLst = this.worldObj.getEntitiesWithinAABB(EntitySheep.class, AxisAlignedBB.getBoundingBox(xCoord-16, yCoord-16, zCoord-16, xCoord+16, yCoord+16, zCoord+16));
			if(!sheepLst.isEmpty() && this.worldObj.getWorldTime() % 20 == 0 && !this.worldObj.isRemote)
			{
				for(int t = 0; t < sheepLst.size(); ++t)
				{
					EntitySheep sheep = sheepLst.get(t);
					sheep.setFleeceColor(this.worldObj.rand.nextInt(16));
					sheep.setCustomNameTag(monsterNames[this.worldObj.rand.nextInt(monsterNames.length)]);
				}
			}
			List<EntityLiving> baseLst = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xCoord-16, yCoord-16, zCoord-16, xCoord+16, yCoord+16, zCoord+16));
			if(!baseLst.isEmpty() && this.worldObj.getWorldTime() % 10 == 0 && !this.worldObj.isRemote)
			{
				for(int t = 0; t < baseLst.size(); ++t)
				{
					EntityLiving sheep = baseLst.get(t);
					sheep.setCustomNameTag(monsterNames[this.worldObj.rand.nextInt(monsterNames.length)]);
				}
			}
			
			int randomBlockID = this.worldObj.rand.nextInt(6);
			
			Block fallingBlock = Blocks.red_flower;
			
			switch(randomBlockID)
			{
				case 0:
				{
					fallingBlock = Blocks.red_flower;
					break;
				}
				case 1:
				{
					fallingBlock = Blocks.yellow_flower;
					break;
				}
				case 2:
				{
					fallingBlock = Blocks.red_mushroom;
					break;
				}
				case 3:
				{
					fallingBlock = Blocks.brown_mushroom;
					break;
				}
				case 4:
				{
					fallingBlock = Blocks.tallgrass;
					break;
				}
				case 5:
				{
					fallingBlock = Blocks.waterlily;
					break;
				}
			}
			
			if(this.worldObj.getWorldTime()%20 == 0)
			{
				EntityFallingBlock flower = new EntityFallingBlock(worldObj, xCoord+MathUtils.randomDouble(this.worldObj.rand)*16, 255, zCoord+MathUtils.randomDouble(this.worldObj.rand)*16, fallingBlock);
				flower.field_145812_b = 3;
				if(!this.worldObj.isRemote)
					this.worldObj.spawnEntityInWorld(flower);
			}
				worldObj.spawnParticle("note", (double)xCoord + 0.5D+MathUtils.randomDouble(this.worldObj.rand)*3, (double)yCoord + 1.2D, (double)zCoord + 0.5D+MathUtils.randomDouble(this.worldObj.rand)*3, (double)MathUtils.randomDouble(this.worldObj.rand)*24 / 24.0D, 0.0D, 0.0D);
			return;
		}
		
		if(this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() instanceof ItemRecord && this.recordPlayed == 0 && this.recordCooldownTime == 0 && this.getMRU() > 500)
		{
			this.setMRU(this.getMRU()-500);
			this.recordPlayed = 1;
			this.recordCooldownTime = 100;
			if(!this.worldObj.isRemote)
				this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1005, xCoord, yCoord, zCoord, Item.getIdFromItem(this.getStackInSlot(1).getItem()));
		}
		
		if(this.getStackInSlot(1) != null)
		{
			if(this.worldObj.rand.nextFloat() <= 0.33F)
				worldObj.spawnParticle("note", (double)xCoord + 0.5D, (double)yCoord + 1.2D, (double)zCoord + 0.5D+MathUtils.randomDouble(this.worldObj.rand)/2, (double)MathUtils.randomDouble(this.worldObj.rand)*24 / 24.0D, 0.0D, 0.0D);
		}
		
		if(this.recordCooldownTime > 0)
			--this.recordCooldownTime;
		
		if(this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() instanceof ItemRecord && this.recordPlayed == 1 && this.recordCooldownTime == 0 && this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && this.getMRU() > 500)
		{
			this.setMRU(this.getMRU()-500);
			this.recordCooldownTime = 100;
			if(!this.worldObj.isRemote)
				this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1005, xCoord, yCoord, zCoord, Item.getIdFromItem(this.getStackInSlot(1).getItem()));
		}
		if(this.getStackInSlot(1) == null || !(this.getStackInSlot(1).getItem() instanceof ItemRecord) && this.recordPlayed == 1)
		{
			this.recordPlayed = 0;
			worldObj.playAuxSFX(1005, xCoord, yCoord, zCoord, 0);
			worldObj.playRecord((String)null, xCoord, yCoord, zCoord);
		}
	}

	@Override
	public int[] getOutputSlots() {
		return new int[]{1};
	}
}
