package ec3.common.magic;

import java.lang.reflect.Method;
import java.util.Arrays;








import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.Notifier;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import ec3.utils.common.ECUtils;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * 
 * @author Modbder
 * @Description ASM - we implemented some code into your code, so you can code while you code!
 */
public class ASMHandler implements IClassTransformer
{

	public String nbttagcompoundClassName = NBTTagCompound.class.getName().replace('.', '/');
	public String itemStackClassName = ItemStack.class.getName().replace('.', '/');
	public String worldClassName = World.class.getName().replace('.', '/');
	public String entityClassName = Entity.class.getName().replace('.', '/');
	public String miscUtils = MiscUtils.class.getName().replace('.', '/');
	public String ecutils = ECUtils.class.getName().replace('.', '/');
	public String toolClass = "tconstruct.library.tools.ToolCore";
	
	//getInteger
	public String method_getInt = "f";
	//setInteger
	public String method_setInt = "a";
	//onUpdate
	public String method_onUpdate = "a";
	
	public boolean obfuscated()
	{
		try
		{
			Method m = NBTTagCompound.class.getMethod("f", String.class);
			if(m != null)
			{
				return true;
			}
			return false;
		}catch(Exception e)
		{
			remap();
			return false;
		}
	}
	
	public void remap()
	{
		method_getInt = "getInteger";
		method_setInt = "setInteger";
		method_onUpdate = "onUpdate";
	}
	
	@Override
	public byte[] transform(String name, String transformedName,byte[] basicClass)
	{
		if(name.equals(toolClass))
		{
			Notifier.notifyCustomMod("EssentialCraft", "Hello, TConstruct. I was just about toSUDDENTLYUNEXPECTEDASMPACHING!!!");
			return handleTransform(basicClass,name,obfuscated());
		}
		return basicClass;
	}
	
	public byte[] handleTransform(byte[] transformingClass, String classpath, boolean isObfuscated)
	{
		/*
		System.out.println("**************** EC3 transform running, obf: "+isObfuscated+" *********************** ");
	    ClassNode classNode = new ClassNode(); //Creating actual ClassNode
	    ClassReader classReader = new ClassReader(transformingClass); //Reading Class from bytes
	    classReader.accept(classNode, 0); //Initializing ClassNode
	    ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
	    
	    System.out.println(" *Patch status: pre-patch interfaces printout");
	    System.out.println(classNode.interfaces);
	    System.out.println(" *Patch status: interfaces patching");
	    System.out.println(" *Patch status: adding a new interface of ec3/api/IItemRequiresMRU");
	    classNode.interfaces.add("ec3/api/IItemRequiresMRU");
	    System.out.println(" *Patch status: post-patch interfaces printout");
	    System.out.println(classNode.interfaces);
	    System.out.println(" *Patch status: success?");
	    
	    System.out.println();
	    
	    System.out.println(" *Patch status: pre-patch fields printout");
	    System.out.println(classNode.fields);
	    FieldNode nodeMRU = new FieldNode(Opcodes.ASM5, Opcodes.ACC_PUBLIC, "currentMRU", Type.INT_TYPE.getDescriptor(), null, 0);
	    FieldNode nodeMaxMRU = new FieldNode(Opcodes.ASM5, Opcodes.ACC_PUBLIC, "maxMRU", Type.INT_TYPE.getDescriptor(), null, 5000);
	    System.out.println(" *Patch status: fields patching");
	    System.out.println(" *Patch status: adding a FieldNode "+nodeMRU+" with fieldname "+nodeMRU.name+" and fieldvalue "+nodeMRU.value);
	    classNode.fields.add(nodeMRU);
	    System.out.println(" *Patch status: adding a FieldNode "+nodeMaxMRU+" with fieldname "+nodeMaxMRU.name+" and fieldvalue "+nodeMaxMRU.value);
	    classNode.fields.add(nodeMaxMRU);
	    System.out.println(" *Patch status: post-patch fields printout");
	    System.out.println(classNode.fields);
	    System.out.println(" *Patch status: success?");
	    
	    System.out.println();
	    
	    System.out.println(" *Patch status: pre-patch methods printout");
	    System.out.println(" *Patch status: methods patching");
	    System.out.println(" *Patch status: generating MethodNode");
	    MethodNode method_getMRU = new MethodNode(Opcodes.ASM5,Opcodes.ACC_PUBLIC,"getMRU","(L"+itemStackClassName+";)"+Type.INT_TYPE.getDescriptor(),null,null);
	    System.out.println(" 	**Patch status: generated a MethodNode "+method_getMRU+" with methodname "+method_getMRU.name);
	    System.out.println(" 	**Patch status: creating an Instruction List for "+method_getMRU+" with methodname "+method_getMRU.name);
	    System.out.println("		***Patch status: creating method body");
	    
	    MethodVisitor mv = writer.visitMethod(method_getMRU.access, method_getMRU.name, method_getMRU.desc, null, null);
	    
	    Label l0 = new Label();
	    mv.visitCode();
	    mv.visitLabel(l0);
	    mv.visitVarInsn(Opcodes.ALOAD, 1);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, miscUtils, "getStackTag", "(L"+itemStackClassName+";)"+"L"+nbttagcompoundClassName+";",false);
	    mv.visitLdcInsn("mru");
	    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, nbttagcompoundClassName, method_getInt, "(Ljava/lang/String;)I", false);
	    mv.visitInsn(Opcodes.IRETURN);
	    Label l1 = new Label();
	    mv.visitLabel(l1);
	    mv.visitLocalVariable("this", "L"+toolClass.replace('.', '/')+";", null, l0, l1, 0);
	    mv.visitLocalVariable("stack", "L"+itemStackClassName+";", null, l0, l1, 1);
	    mv.visitMaxs(0, 0);
	    mv.visitEnd();
	    method_getMRU.accept(mv);

	    System.out.println(" 	**Patch status: possible success?");
	    
	    System.out.println();
	    
	    System.out.println(" *Patch status: generating MethodNode");
	    MethodNode method_getMaxMRU = new MethodNode(Opcodes.ASM5,Opcodes.ACC_PUBLIC,"getMaxMRU","(L"+itemStackClassName+";)"+Type.INT_TYPE.getDescriptor(),null,null);
	    System.out.println(" 	**Patch status: generated a MethodNode "+method_getMaxMRU+" with methodname "+method_getMaxMRU.name);
	    
	    System.out.println(" 	**Patch status: creating an Instruction List for "+method_getMaxMRU+" with methodname "+method_getMaxMRU.name);
	    System.out.println("		***Patch status: creating method body");
	    
	    mv = writer.visitMethod(method_getMaxMRU.access, method_getMaxMRU.name, method_getMaxMRU.desc, null, null);
	    
	    l0 = new Label();
	    mv.visitCode();
	    mv.visitLabel(l0);
	    mv.visitVarInsn(Opcodes.ALOAD, 0);
	    mv.visitFieldInsn(Opcodes.GETFIELD, toolClass.replace('.', '/'), "maxMRU", "I");
	    mv.visitInsn(Opcodes.IRETURN);
	    l1 = new Label();
	    mv.visitLabel(l1);
	    mv.visitLocalVariable("this", "L"+toolClass.replace('.', '/')+";", null, l0, l1, 0);
	    mv.visitLocalVariable("stack", "L"+itemStackClassName+";", null, l0, l1, 1);
	    mv.visitMaxs(1, 2);
	    mv.visitEnd();
	    method_getMaxMRU.accept(mv);
	    System.out.println(" 	**Patch status: possible success?");
	    
	    System.out.println();
	    
	    System.out.println(" *Patch status: generating MethodNode");
	    MethodNode method_setMRU = new MethodNode(Opcodes.ASM5,Opcodes.ACC_PUBLIC,"setMRU","(L"+itemStackClassName+";I)"+Type.BOOLEAN_TYPE.getDescriptor(),null,null);
	    System.out.println(" 	**Patch status: generated a MethodNode "+method_setMRU+" with methodname "+method_setMRU.name);
	    System.out.println(" 	**Patch status: creating an Instruction List for "+method_setMRU+" with methodname "+method_setMRU.name);
	    System.out.println("		***Patch status: creating method body");
	    
	    mv = writer.visitMethod(method_setMRU.access, method_setMRU.name, method_setMRU.desc, null, null);
	    
	    l0 = new Label();
	    mv.visitCode();
	    mv.visitLabel(l0);
	    mv.visitVarInsn(Opcodes.ALOAD, 1);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, miscUtils, "getStackTag", "(L"+itemStackClassName+";)"+"L"+nbttagcompoundClassName+";", false);
	    mv.visitLdcInsn("mru");
	    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, nbttagcompoundClassName, method_getInt, "(Ljava/lang/String;)I", false);
	    mv.visitVarInsn(Opcodes.ILOAD, 2);
	    mv.visitInsn(Opcodes.IADD);
	    l1 = new Label();
	    mv.visitJumpInsn(Opcodes.IFLT, l1);
	    mv.visitVarInsn(Opcodes.ALOAD, 1);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, miscUtils, "getStackTag", "(L"+itemStackClassName+";)"+"L"+nbttagcompoundClassName+";", false);
	    mv.visitLdcInsn("mru");
	    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, nbttagcompoundClassName, method_getInt, "(Ljava/lang/String;)I", false);
	    mv.visitVarInsn(Opcodes.ILOAD, 2);
	    mv.visitInsn(Opcodes.IADD);
	    mv.visitVarInsn(Opcodes.ALOAD, 1);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, miscUtils, "getStackTag", "(L"+itemStackClassName+";)"+"L"+nbttagcompoundClassName+";", false);
	    mv.visitLdcInsn("maxMRU");
	    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, nbttagcompoundClassName, method_getInt, "(Ljava/lang/String;)I", false);
	    mv.visitJumpInsn(Opcodes.IF_ICMPGT, l1);
	    Label l2 = new Label();
	    mv.visitLabel(l2);
	    mv.visitVarInsn(Opcodes.ALOAD, 1);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, miscUtils, "getStackTag", "(L"+itemStackClassName+";)"+"L"+nbttagcompoundClassName+";", false);
	    mv.visitLdcInsn("mru");
	    mv.visitVarInsn(Opcodes.ALOAD, 1);
	    mv.visitMethodInsn(Opcodes.INVOKESTATIC, miscUtils, "getStackTag", "(L"+itemStackClassName+";)"+"L"+nbttagcompoundClassName+";", false);
	    mv.visitLdcInsn("mru");
	    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, nbttagcompoundClassName, method_getInt, "(Ljava/lang/String;)I", false);
	    mv.visitVarInsn(Opcodes.ILOAD, 2);
	    mv.visitInsn(Opcodes.IADD);
	    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, nbttagcompoundClassName, method_setInt, "(Ljava/lang/String;I)V", false);
	    Label l3 = new Label();
	    mv.visitLabel(l3);
	    mv.visitInsn(Opcodes.ICONST_1);
	    mv.visitInsn(Opcodes.IRETURN);
	    mv.visitLabel(l1);
	    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
	    mv.visitInsn(Opcodes.ICONST_0);
	    mv.visitInsn(Opcodes.IRETURN);
	    Label l4 = new Label();
	    mv.visitLabel(l4);
	    mv.visitLocalVariable("this", "L"+toolClass.replace('.', '/')+";", null, l0, l4, 0);
	    mv.visitLocalVariable("stack", "L"+itemStackClassName+";", null, l0, l4, 1);
	    mv.visitLocalVariable("amount", "I", null, l0, l4, 2);
	    mv.visitMaxs(0, 0);
	    mv.visitEnd();
	    method_setMRU.accept(mv);
	    System.out.println(" 	**Patch status: possible success?");
	    
	    System.out.println();
	    
	    System.out.println(" *Patch status: searching for "+method_onUpdate+"(onUpdate) method...");
	    fcn:for(int i = 0; i < classNode.methods.size(); ++i)
	    {
	    	MethodNode method = classNode.methods.get(i);
	    	
	    	if(method.name.equals(method_onUpdate) && method.desc.equals("(L"+itemStackClassName+";L"+worldClassName+";L"+entityClassName+";IZ)V"))
	    	{
	    		 System.out.println(" *Patch status: found target method "+method_onUpdate+"(onUpdate)");
	    		 System.out.println(" *Patch status: patching instructions in target method: "+method_onUpdate+"(onUpdate)");
	    		 InsnList instructions = method.instructions;
	    		 
	    		 InsnList toInject = new InsnList();
	    		 0: ItemStack is,
	    		 1: World w,
	    		 2: Entity holder,
	    		 3: int slot,
	    		 4: boolean held
	    		 toInject.add(new VarInsnNode(Opcodes.ALOAD,1));
	    		 toInject.add(new VarInsnNode(Opcodes.ALOAD,0));
	    		 toInject.add(new FieldInsnNode(Opcodes.GETFIELD, toolClass.replace('.', '/'), "maxMRU", Type.INT_TYPE.getDescriptor()));
	    		 toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ecutils, "initMRUTag", "(L"+itemStackClassName+";I)V",false));
	    		 
	    		 instructions.insert(method.instructions.get(1), toInject);
	    		 System.out.println(" *Patch status: success patching instructions in target method: "+method_onUpdate+"(onUpdate)?");
	    		 break fcn;
	    	}
	    }
	    
	    System.out.println(" *Patch status: overwrighting original class");
	    classNode.accept(writer);
	    System.out.println(" *Patch status: success?");
	    System.out.println(" *Patch status: adding patched/new methods");
	    classNode.methods.add(method_getMRU);
	    classNode.methods.add(method_getMaxMRU);
	    classNode.methods.add(method_setMRU);
	    System.out.println(" *Patch status: success?");
	    
	    System.out.println(" *Patch status: ending patch methods summary:");
	    for(int i = 0; i < classNode.methods.size(); ++i)
	    {
	    	 System.out.println(classNode.methods.get(i).name+":"+classNode.methods.get(i).desc);
	    }
	    System.out.println(" *Patch status: patching finished. Result: Possible Success.");
	    System.out.println("**************** EC3 transform finish, obf: "+isObfuscated+" *********************** ");
	    */
		return transformingClass;
	}

}
