package ec3.utils.common;

import java.util.List;

import ec3.common.entity.EntityMRUPresence;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;

public class CommandCreateMRUCU extends CommandBase 
{
    public String getCommandName()
    {
        return "createMRUCU";
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "/createMRUCU <player> <mruAmount> <balance>";
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
    	int var4 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[2], 1);
        EntityPlayerMP player = par2ArrayOfStr.length == 0 ? getCommandSenderAsPlayer(par1ICommandSender) : getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        if(var3>0&&var3<60000&&var4>0&&var4<20000)
        {
        	EntityMRUPresence mru = new EntityMRUPresence(player.worldObj);
        	mru.setPositionAndRotation(player.posX, player.posY+1, player.posZ, 0, 0);
        	mru.setMRU(var3);
        	mru.setBalance((float)var4/10000);
        	player.worldObj.spawnEntityInWorld(mru);
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
