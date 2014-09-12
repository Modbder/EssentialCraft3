package ec3.utils.common;

import java.util.List;

import DummyCore.Utils.Coord3D;
import ec3.common.entity.EntityMRUPresence;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;

public class CommandSetBalance extends CommandBase 
{
    public String getCommandName()
    {
        return "setBalance";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/setBalance <player> <balance>";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 3;
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
    	int var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[1], 1);
        EntityPlayerMP player = par2ArrayOfStr.length == 0 ? getCommandSenderAsPlayer(par1ICommandSender) : getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
		EntityMRUPresence mru = (EntityMRUPresence) ECUtils.getClosestMRUCU(player.worldObj, new Coord3D(player.posX,player.posY,player.posZ), 16);
		if(mru==null)
		{
			
		}else
		{
			if(var3>20000)
			{
				
			}else
			{
				if(var3<1)
				{
					
				}else
				{
					mru.setBalance((float)var3/10000);
					
				}
			}
		}
     }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, this.getAllOnlineUsernames()) : null;
    }

    /**
     * Return all usernames currently connected to the server.
     */
    protected String[] getAllOnlineUsernames()
    {
        return MinecraftServer.getServer().getAllUsernames();
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(int par1)
    {
        return par1 == 0;
    }
}
