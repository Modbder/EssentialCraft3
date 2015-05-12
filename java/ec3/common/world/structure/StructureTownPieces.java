package ec3.common.world.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import DummyCore.Utils.Coord2D;
import cpw.mods.fml.common.eventhandler.Event.Result;
import ec3.common.block.BlocksCore;
import ec3.common.world.ECExplosion;
import ec3.common.world.WorldGenDestroyedHouse;
import ec3.common.world.WorldGenMRUSpreader;
import ec3.common.world.WorldGenMRUTower;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class StructureTownPieces
{
    public static void registerVillagePieces()
    {
        MapGenStructureIO.func_143031_a(StructureTownPieces.House1.class, "TViBH");
        MapGenStructureIO.func_143031_a(StructureTownPieces.Field1.class, "TViDF");
        MapGenStructureIO.func_143031_a(StructureTownPieces.Torch.class, "TViL");
        MapGenStructureIO.func_143031_a(StructureTownPieces.Hall.class, "TViPH");
        MapGenStructureIO.func_143031_a(StructureTownPieces.House4Garden.class, "TViSH");
        MapGenStructureIO.func_143031_a(StructureTownPieces.WoodHut.class, "TViSmH");
        MapGenStructureIO.func_143031_a(StructureTownPieces.Church.class, "TViST");
        MapGenStructureIO.func_143031_a(StructureTownPieces.House2.class, "TViS");
        MapGenStructureIO.func_143031_a(StructureTownPieces.Start.class, "TViStart");
        MapGenStructureIO.func_143031_a(StructureTownPieces.Path.class, "TViSR");
        MapGenStructureIO.func_143031_a(StructureTownPieces.House3.class, "TViTRH");
        MapGenStructureIO.func_143031_a(StructureTownPieces.Well.class, "TViW");
    }

    public static List getStructureVillageWeightedPieceList(Random p_75084_0_, int p_75084_1_)
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.House4Garden.class, 4, 30));
        arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.Church.class, 20, 10));
        arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.House1.class, 20, 30));
        arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.WoodHut.class, 3, 100));
        arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.Hall.class, 15, 60));
        arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.Field1.class, 3, 20));
        arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.House2.class, 15,30));
        arraylist.add(new StructureTownPieces.PieceWeight(StructureTownPieces.House3.class, 8, 30));

        Iterator iterator = arraylist.iterator();

        while (iterator.hasNext())
        {
            if (((StructureTownPieces.PieceWeight)iterator.next()).villagePiecesLimit == 0)
            {
                iterator.remove();
            }
        }

        return arraylist;
    }

    private static int func_75079_a(List p_75079_0_)
    {
        boolean flag = false;
        int i = 0;
        StructureTownPieces.PieceWeight pieceweight;

        for (Iterator iterator = p_75079_0_.iterator(); iterator.hasNext(); i += pieceweight.villagePieceWeight)
        {
            pieceweight = (StructureTownPieces.PieceWeight)iterator.next();

            if (pieceweight.villagePiecesLimit > 0 && pieceweight.villagePiecesSpawned < pieceweight.villagePiecesLimit)
            {
                flag = true;
            }
        }

        return flag ? i : -1;
    }

    private static StructureTownPieces.Village func_75083_a(StructureTownPieces.Start p_75083_0_, StructureTownPieces.PieceWeight p_75083_1_, List p_75083_2_, Random p_75083_3_, int p_75083_4_, int p_75083_5_, int p_75083_6_, int p_75083_7_, int p_75083_8_)
    {
        Class oclass = p_75083_1_.villagePieceClass;
        Object object = null;

        if (oclass == StructureTownPieces.House4Garden.class)
        {
            object = StructureTownPieces.House4Garden.func_74912_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (oclass == StructureTownPieces.Church.class)
        {
            object = StructureTownPieces.Church.func_74919_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (oclass == StructureTownPieces.House1.class)
        {
            object = StructureTownPieces.House1.func_74898_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (oclass == StructureTownPieces.WoodHut.class)
        {
            object = StructureTownPieces.WoodHut.func_74908_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (oclass == StructureTownPieces.Hall.class)
        {
            object = StructureTownPieces.Hall.func_74906_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (oclass == StructureTownPieces.Field1.class)
        {
            object = StructureTownPieces.Field1.func_74900_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (oclass == StructureTownPieces.Field2.class)
        {
            object = StructureTownPieces.Field2.func_74902_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (oclass == StructureTownPieces.House2.class)
        {
            object = StructureTownPieces.House2.func_74915_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else if (oclass == StructureTownPieces.House3.class)
        {
            object = StructureTownPieces.House3.func_74921_a(p_75083_0_, p_75083_2_, p_75083_3_, p_75083_4_, p_75083_5_, p_75083_6_, p_75083_7_, p_75083_8_);
        }
        else
        {
          
        }

        return (StructureTownPieces.Village)object;
    }

    /**
     * attempts to find a next Village Component to be spawned
     */
    private static StructureTownPieces.Village getNextVillageComponent(StructureTownPieces.Start p_75081_0_, List p_75081_1_, Random p_75081_2_, int p_75081_3_, int p_75081_4_, int p_75081_5_, int p_75081_6_, int p_75081_7_)
    {
        int j1 = func_75079_a(p_75081_0_.structureVillageWeightedPieceList);

        if (j1 <= 0)
        {
            return null;
        }
        else
        {
            int k1 = 0;

            while (k1 < 5)
            {
                ++k1;
                int l1 = p_75081_2_.nextInt(j1);
                Iterator iterator = p_75081_0_.structureVillageWeightedPieceList.iterator();

                while (iterator.hasNext())
                {
                    StructureTownPieces.PieceWeight pieceweight = (StructureTownPieces.PieceWeight)iterator.next();
                    l1 -= pieceweight.villagePieceWeight;

                    if (l1 < 0)
                    {
                        if (!pieceweight.canSpawnMoreVillagePiecesOfType(p_75081_7_) || pieceweight == p_75081_0_.structVillagePieceWeight && p_75081_0_.structureVillageWeightedPieceList.size() > 1)
                        {
                            break;
                        }

                        StructureTownPieces.Village village = func_75083_a(p_75081_0_, pieceweight, p_75081_1_, p_75081_2_, p_75081_3_, p_75081_4_, p_75081_5_, p_75081_6_, p_75081_7_);

                        if (village != null)
                        {
                            ++pieceweight.villagePiecesSpawned;
                            p_75081_0_.structVillagePieceWeight = pieceweight;

                            if (!pieceweight.canSpawnMoreVillagePieces())
                            {
                                p_75081_0_.structureVillageWeightedPieceList.remove(pieceweight);
                            }

                            return village;
                        }
                    }
                }
            }

            StructureBoundingBox structureboundingbox = StructureTownPieces.Torch.func_74904_a(p_75081_0_, p_75081_1_, p_75081_2_, p_75081_3_, p_75081_4_, p_75081_5_, p_75081_6_);

            if (structureboundingbox != null)
            {
                return new StructureTownPieces.Torch(p_75081_0_, p_75081_7_, p_75081_2_, structureboundingbox, p_75081_6_);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * attempts to find a next Structure Component to be spawned, private Village function
     */
    private static StructureComponent getNextVillageStructureComponent(StructureTownPieces.Start p_75077_0_, List p_75077_1_, Random p_75077_2_, int p_75077_3_, int p_75077_4_, int p_75077_5_, int p_75077_6_, int p_75077_7_)
    {
        if (p_75077_7_ > 500)
        {
            return null;
        }
        else if (Math.abs(p_75077_3_ - p_75077_0_.getBoundingBox().minX) <= 600 && Math.abs(p_75077_5_ - p_75077_0_.getBoundingBox().minZ) <= 600)
        {
            StructureTownPieces.Village village = getNextVillageComponent(p_75077_0_, p_75077_1_, p_75077_2_, p_75077_3_, p_75077_4_, p_75077_5_, p_75077_6_, p_75077_7_ + 1);

            if (village != null)
            {
                if (true)
                {
                    p_75077_1_.add(village);
                    p_75077_0_.field_74932_i.add(village);
                    return village;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    private static StructureComponent getNextComponentVillagePath(StructureTownPieces.Start p_75080_0_, List p_75080_1_, Random p_75080_2_, int p_75080_3_, int p_75080_4_, int p_75080_5_, int p_75080_6_, int p_75080_7_)
    {
        if (p_75080_7_ > 50 + p_75080_0_.terrainType)
        {
            return null;
        }
        else if (Math.abs(p_75080_3_ - p_75080_0_.getBoundingBox().minX) <= 600 && Math.abs(p_75080_5_ - p_75080_0_.getBoundingBox().minZ) <= 600)
        {
            StructureBoundingBox structureboundingbox = StructureTownPieces.Path.func_74933_a(p_75080_0_, p_75080_1_, p_75080_2_, p_75080_3_, p_75080_4_, p_75080_5_, p_75080_6_);

            if (structureboundingbox != null && structureboundingbox.minY > 10)
            {
                StructureTownPieces.Path path = new StructureTownPieces.Path(p_75080_0_, p_75080_7_, p_75080_2_, structureboundingbox, p_75080_6_);
                path.getBoundingBox();
				path.getBoundingBox();
                path.getBoundingBox();
				path.getBoundingBox();
                path.getBoundingBox();
				path.getBoundingBox();
                path.getBoundingBox();
				path.getBoundingBox();
                if (true)
                {
                    p_75080_1_.add(path);
                    p_75080_0_.field_74930_j.add(path);
                    return path;
                }
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    public static class Church extends StructureTownPieces.Village
        {
            public Church() {}

            public Church(StructureTownPieces.Start p_i2102_1_, int p_i2102_2_, Random p_i2102_3_, StructureBoundingBox p_i2102_4_, int p_i2102_5_)
            {
                super(p_i2102_1_, p_i2102_2_);
                this.coordBaseMode = p_i2102_5_;
                this.boundingBox = p_i2102_4_;
            }

            public static StructureTownPieces.Church func_74919_a(StructureTownPieces.Start p_74919_0_, List p_74919_1_, Random p_74919_2_, int p_74919_3_, int p_74919_4_, int p_74919_5_, int p_74919_6_, int p_74919_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74919_3_, p_74919_4_, p_74919_5_, 0, 0, 0, 5, 12, 9, p_74919_6_);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74919_1_, structureboundingbox) == null ? new StructureTownPieces.Church(p_74919_0_, p_74919_7_, p_74919_2_, structureboundingbox, p_74919_6_) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
                }
                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
             	  new WorldGenMRUTower().generate(p_74875_1_, p_74875_2_, p_74875_3_.minX,  this.getAverageGroundLevel(p_74875_1_, p_74875_3_), p_74875_3_.minZ);
                return true;
            }

            /**
             * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
             */
            protected int getVillagerType(int p_74888_1_)
            {
                return 2;
            }
        }

    public static class Field1 extends StructureTownPieces.Village
        {
            /** First crop type for this field. */
            private Block cropTypeA;
            /** Second crop type for this field. */
            private Block cropTypeB;
            /** Third crop type for this field. */
            private Block cropTypeC;
            /** Fourth crop type for this field. */
            private Block cropTypeD;
            
            public static Coord2D currentStructureCoords, prevStructureCoords;

            public Field1() {}

            public Field1(StructureTownPieces.Start p_i2095_1_, int p_i2095_2_, Random p_i2095_3_, StructureBoundingBox p_i2095_4_, int p_i2095_5_)
            {
                super(p_i2095_1_, p_i2095_2_);
                this.coordBaseMode = p_i2095_5_;
                this.boundingBox = p_i2095_4_;
                this.cropTypeA = this.func_151559_a(p_i2095_3_);
                this.cropTypeB = this.func_151559_a(p_i2095_3_);
                this.cropTypeC = this.func_151559_a(p_i2095_3_);
                this.cropTypeD = this.func_151559_a(p_i2095_3_);
               
            }

            protected void func_143012_a(NBTTagCompound p_143012_1_)
            {
                super.func_143012_a(p_143012_1_);
                p_143012_1_.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
                p_143012_1_.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
                p_143012_1_.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
                p_143012_1_.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
            }

            protected void func_143011_b(NBTTagCompound p_143011_1_)
            {
                super.func_143011_b(p_143011_1_);
                this.cropTypeA = Block.getBlockById(p_143011_1_.getInteger("CA"));
                this.cropTypeB = Block.getBlockById(p_143011_1_.getInteger("CB"));
                this.cropTypeC = Block.getBlockById(p_143011_1_.getInteger("CC"));
                this.cropTypeD = Block.getBlockById(p_143011_1_.getInteger("CD"));
            }

            private Block func_151559_a(Random p_151559_1_)
            {
                switch (p_151559_1_.nextInt(5))
                {
                    case 0:
                        return Blocks.carrots;
                    case 1:
                        return Blocks.potatoes;
                    default:
                        return Blocks.wheat;
                }
            }

            public static StructureTownPieces.Field1 func_74900_a(StructureTownPieces.Start p_74900_0_, List p_74900_1_, Random p_74900_2_, int p_74900_3_, int p_74900_4_, int p_74900_5_, int p_74900_6_, int p_74900_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74900_3_, p_74900_4_, p_74900_5_, 0, 0, 0, 13, 4, 9, p_74900_6_);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74900_1_, structureboundingbox) == null ? new StructureTownPieces.Field1(p_74900_0_, p_74900_7_, p_74900_2_, structureboundingbox, p_74900_6_) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
                }
                return true;
            }
        }

    public static class Field2 extends StructureTownPieces.Village
        {
            /** First crop type for this field. */
            private Block cropTypeA;
            /** Second crop type for this field. */
            private Block cropTypeB;

            public Field2() {}

            public Field2(StructureTownPieces.Start p_i2096_1_, int p_i2096_2_, Random p_i2096_3_, StructureBoundingBox p_i2096_4_, int p_i2096_5_)
            {
                super(p_i2096_1_, p_i2096_2_);
                this.coordBaseMode = p_i2096_5_;
                this.boundingBox = p_i2096_4_;
                this.cropTypeA = this.func_151560_a(p_i2096_3_);
                this.cropTypeB = this.func_151560_a(p_i2096_3_);
            }

            protected void func_143012_a(NBTTagCompound p_143012_1_)
            {
                super.func_143012_a(p_143012_1_);
                p_143012_1_.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
                p_143012_1_.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
            }

            protected void func_143011_b(NBTTagCompound p_143011_1_)
            {
                super.func_143011_b(p_143011_1_);
                this.cropTypeA = Block.getBlockById(p_143011_1_.getInteger("CA"));
                this.cropTypeB = Block.getBlockById(p_143011_1_.getInteger("CB"));
            }

            private Block func_151560_a(Random p_151560_1_)
            {
                switch (p_151560_1_.nextInt(5))
                {
                    case 0:
                        return Blocks.carrots;
                    case 1:
                        return Blocks.potatoes;
                    default:
                        return Blocks.wheat;
                }
            }

            public static StructureTownPieces.Field2 func_74902_a(StructureTownPieces.Start p_74902_0_, List p_74902_1_, Random p_74902_2_, int p_74902_3_, int p_74902_4_, int p_74902_5_, int p_74902_6_, int p_74902_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74902_3_, p_74902_4_, p_74902_5_, 0, 0, 0, 7, 4, 9, p_74902_6_);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74902_1_, structureboundingbox) == null ? new StructureTownPieces.Field2(p_74902_0_, p_74902_7_, p_74902_2_, structureboundingbox, p_74902_6_) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
                }

                return true;
            }
        }

    public static class Hall extends StructureTownPieces.Village
        {
            public Hall() {}

            public Hall(StructureTownPieces.Start p_i2099_1_, int p_i2099_2_, Random p_i2099_3_, StructureBoundingBox p_i2099_4_, int p_i2099_5_)
            {
                super(p_i2099_1_, p_i2099_2_);
                this.coordBaseMode = p_i2099_5_;
                this.boundingBox = p_i2099_4_;
            }

            public static StructureTownPieces.Hall func_74906_a(StructureTownPieces.Start p_74906_0_, List p_74906_1_, Random p_74906_2_, int p_74906_3_, int p_74906_4_, int p_74906_5_, int p_74906_6_, int p_74906_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74906_3_, p_74906_4_, p_74906_5_, 0, 0, 0, 9, 7, 11, p_74906_6_);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74906_1_, structureboundingbox) == null ? new StructureTownPieces.Hall(p_74906_0_, p_74906_7_, p_74906_2_, structureboundingbox, p_74906_6_) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
                }
                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
            	   new WorldGenMRUSpreader().generate(p_74875_1_, p_74875_2_, p_74875_3_.minX,  this.getAverageGroundLevel(p_74875_1_, p_74875_3_), p_74875_3_.minZ);
                return true;
            }

            /**
             * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
             */
            protected int getVillagerType(int p_74888_1_)
            {
                return p_74888_1_ == 0 ? 4 : 0;
            }
        }

    public static class House1 extends StructureTownPieces.Village
        {
    	public House1() {}

            public House1(StructureTownPieces.Start p_i2094_1_, int p_i2094_2_, Random p_i2094_3_, StructureBoundingBox p_i2094_4_, int p_i2094_5_)
            {
                super(p_i2094_1_, p_i2094_2_);
                this.coordBaseMode = p_i2094_5_;
                this.boundingBox = p_i2094_4_;
            }

            public static StructureTownPieces.House1 func_74898_a(StructureTownPieces.Start p_74898_0_, List p_74898_1_, Random p_74898_2_, int p_74898_3_, int p_74898_4_, int p_74898_5_, int p_74898_6_, int p_74898_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74898_3_, p_74898_4_, p_74898_5_, 0, 0, 0, 9, 9, 6, p_74898_6_);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74898_1_, structureboundingbox) == null ? new StructureTownPieces.House1(p_74898_0_, p_74898_7_, p_74898_2_, structureboundingbox, p_74898_6_) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
                }

                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
                	new WorldGenDestroyedHouse(p_74875_2_.nextInt(2)+1,5).generate(p_74875_1_, p_74875_2_, p_74875_3_.minX,  this.getAverageGroundLevel(p_74875_1_, p_74875_3_), p_74875_3_.minZ);
                return true;
            }

            /**
             * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
             */
            protected int getVillagerType(int p_74888_1_)
            {
                return 1;
            }
        }

    public static class House2 extends StructureTownPieces.Village
        {
            /** List of items that Village's Blacksmith chest can contain. */
            public static final WeightedRandomChestContent[] villageBlacksmithChestContents = new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)};
            private boolean hasMadeChest;

            public House2() {}

            public House2(StructureTownPieces.Start p_i2103_1_, int p_i2103_2_, Random p_i2103_3_, StructureBoundingBox p_i2103_4_, int p_i2103_5_)
            {
                super(p_i2103_1_, p_i2103_2_);
                this.coordBaseMode = p_i2103_5_;
                this.boundingBox = p_i2103_4_;
            }

            public static StructureTownPieces.House2 func_74915_a(StructureTownPieces.Start p_74915_0_, List p_74915_1_, Random p_74915_2_, int p_74915_3_, int p_74915_4_, int p_74915_5_, int p_74915_6_, int p_74915_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74915_3_, p_74915_4_, p_74915_5_, 0, 0, 0, 10, 6, 7, p_74915_6_);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74915_1_, structureboundingbox) == null ? new StructureTownPieces.House2(p_74915_0_, p_74915_7_, p_74915_2_, structureboundingbox, p_74915_6_) : null;
            }

            protected void func_143012_a(NBTTagCompound p_143012_1_)
            {
                super.func_143012_a(p_143012_1_);
                p_143012_1_.setBoolean("Chest", this.hasMadeChest);
            }

            protected void func_143011_b(NBTTagCompound p_143011_1_)
            {
                super.func_143011_b(p_143011_1_);
                this.hasMadeChest = p_143011_1_.getBoolean("Chest");
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
                }

                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
            	   new WorldGenDestroyedHouse(p_74875_2_.nextInt(5)+1,4).generate(p_74875_1_, p_74875_2_, p_74875_3_.minX,  this.getAverageGroundLevel(p_74875_1_, p_74875_3_), p_74875_3_.minZ);
                return true;
            }

            /**
             * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
             */
            protected int getVillagerType(int p_74888_1_)
            {
                return 3;
            }
        }

    public static class House3 extends StructureTownPieces.Village
        {

            public House3() {}

            public House3(StructureTownPieces.Start p_i2106_1_, int p_i2106_2_, Random p_i2106_3_, StructureBoundingBox p_i2106_4_, int p_i2106_5_)
            {
                super(p_i2106_1_, p_i2106_2_);
                this.coordBaseMode = p_i2106_5_;
                this.boundingBox = p_i2106_4_;
            }

            public static StructureTownPieces.House3 func_74921_a(StructureTownPieces.Start p_74921_0_, List p_74921_1_, Random p_74921_2_, int p_74921_3_, int p_74921_4_, int p_74921_5_, int p_74921_6_, int p_74921_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74921_3_, p_74921_4_, p_74921_5_, 0, 0, 0, 9, 7, 12, p_74921_6_);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74921_1_, structureboundingbox) == null ? new StructureTownPieces.House3(p_74921_0_, p_74921_7_, p_74921_2_, structureboundingbox, p_74921_6_) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
                }
                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
                	new WorldGenDestroyedHouse(p_74875_2_.nextInt(9)+1,3).generate(p_74875_1_, p_74875_2_, p_74875_3_.minX,  this.getAverageGroundLevel(p_74875_1_, p_74875_3_), p_74875_3_.minZ);
                return true;
            }
        }

    public static class House4Garden extends StructureTownPieces.Village
        {
            private boolean isRoofAccessible;
            public House4Garden() {}

            public House4Garden(StructureTownPieces.Start p_i2100_1_, int p_i2100_2_, Random p_i2100_3_, StructureBoundingBox p_i2100_4_, int p_i2100_5_)
            {
                super(p_i2100_1_, p_i2100_2_);
                this.coordBaseMode = p_i2100_5_;
                this.boundingBox = p_i2100_4_;
                this.isRoofAccessible = p_i2100_3_.nextBoolean();
            }

            protected void func_143012_a(NBTTagCompound p_143012_1_)
            {
                super.func_143012_a(p_143012_1_);
                p_143012_1_.setBoolean("Terrace", this.isRoofAccessible);
            }

            protected void func_143011_b(NBTTagCompound p_143011_1_)
            {
                super.func_143011_b(p_143011_1_);
                this.isRoofAccessible = p_143011_1_.getBoolean("Terrace");
            }

            public static StructureTownPieces.House4Garden func_74912_a(StructureTownPieces.Start p_74912_0_, List p_74912_1_, Random p_74912_2_, int p_74912_3_, int p_74912_4_, int p_74912_5_, int p_74912_6_, int p_74912_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74912_3_, p_74912_4_, p_74912_5_, 0, 0, 0, 5, 6, 5, p_74912_6_);
                return StructureComponent.findIntersecting(p_74912_1_, structureboundingbox) != null ? null : new StructureTownPieces.House4Garden(p_74912_0_, p_74912_7_, p_74912_2_, structureboundingbox, p_74912_6_);
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
                }

                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
                	new WorldGenDestroyedHouse(p_74875_2_.nextInt(3)+1,7).generate(p_74875_1_, p_74875_2_, p_74875_3_.minX,  this.getAverageGroundLevel(p_74875_1_, p_74875_3_), p_74875_3_.minZ);
                return true;
            }
        }

    public static class Path extends StructureTownPieces.Road
        {
            private int averageGroundLevel;

            public Path() {}

            public Path(StructureTownPieces.Start p_i2105_1_, int p_i2105_2_, Random p_i2105_3_, StructureBoundingBox p_i2105_4_, int p_i2105_5_)
            {
                super(p_i2105_1_, p_i2105_2_);
                this.coordBaseMode = p_i2105_5_;
                this.boundingBox = p_i2105_4_;
                this.averageGroundLevel = Math.max(p_i2105_4_.getXSize(), p_i2105_4_.getZSize());
            }

            protected void func_143012_a(NBTTagCompound p_143012_1_)
            {
                super.func_143012_a(p_143012_1_);
                p_143012_1_.setInteger("Length", this.averageGroundLevel);
            }

            protected void func_143011_b(NBTTagCompound p_143011_1_)
            {
                super.func_143011_b(p_143011_1_);
                this.averageGroundLevel = p_143011_1_.getInteger("Length");
            }

            /**
             * Initiates construction of the Structure Component picked, at the current Location of StructGen
             */
            public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
            {
                boolean flag = false;
                int i;
                StructureComponent structurecomponent1;

                for (i = p_74861_3_.nextInt(18); i < this.averageGroundLevel - 8; i += 2 + p_74861_3_.nextInt(18))
                {
                    structurecomponent1 = this.getNextComponentNN((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, i);

                    if (structurecomponent1 != null)
                    {
                        i += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
                        flag = true;
                    }
                }

                for (i = p_74861_3_.nextInt(18); i < this.averageGroundLevel - 8; i += 2 + p_74861_3_.nextInt(18))
                {
                    structurecomponent1 = this.getNextComponentPP((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, i);

                    if (structurecomponent1 != null)
                    {
                        i += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
                        flag = true;
                    }
                }

                if (flag && p_74861_3_.nextInt(18) > 0)
                {
                    switch (this.coordBaseMode)
                    {
                        case 0:
                            StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1, this.getComponentType());
                            break;
                        case 1:
                            StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                            break;
                        case 2:
                            StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, this.getComponentType());
                            break;
                        case 3:
                            StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                    }
                }

                if (flag && p_74861_3_.nextInt(18) > 0)
                {
                    switch (this.coordBaseMode)
                    {
                        case 0:
                            StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3, this.getComponentType());
                            break;
                        case 1:
                            StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                            break;
                        case 2:
                            StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, this.getComponentType());
                            break;
                        case 3:
                            StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                    }
                }
            }

            public static StructureBoundingBox func_74933_a(StructureTownPieces.Start p_74933_0_, List p_74933_1_, Random p_74933_2_, int p_74933_3_, int p_74933_4_, int p_74933_5_, int p_74933_6_)
            {
                for (int i1 = 7 * MathHelper.getRandomIntegerInRange(p_74933_2_, 3, 5); i1 >= 7; i1 -= 7)
                {
                    StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74933_3_, p_74933_4_, p_74933_5_, 0, 0, 0, 5, 5, i1, p_74933_6_);

                    if (StructureComponent.findIntersecting(p_74933_1_, structureboundingbox) == null)
                    {
                        return structureboundingbox;
                    }
                }

                return null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                Block block = this.func_151558_b(BlocksCore.concrete, 0);

                for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i)
                {
                    for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j)
                    {
                        if (p_74875_3_.isVecInside(i, 64, j))
                        {
                            int k = p_74875_1_.getTopSolidOrLiquidBlock(i, j) - 1;
                            p_74875_1_.setBlock(i, k, j, block, 0, 2);
                        }
                    }
                }

                return true;
            }
        }

    public static class PieceWeight
        {
            /** The Class object for the represantation of this village piece. */
            public Class villagePieceClass;
            public final int villagePieceWeight;
            public int villagePiecesSpawned;
            public int villagePiecesLimit;

            public PieceWeight(Class p_i2098_1_, int p_i2098_2_, int p_i2098_3_)
            {
                this.villagePieceClass = p_i2098_1_;
                this.villagePieceWeight = p_i2098_2_;
                this.villagePiecesLimit = p_i2098_3_;
            }

            public boolean canSpawnMoreVillagePiecesOfType(int p_75085_1_)
            {
                return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
            }

            public boolean canSpawnMoreVillagePieces()
            {
                return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
            }
        }

    public abstract static class Road extends StructureTownPieces.Village
        {

            public Road() {}

            protected Road(StructureTownPieces.Start p_i2108_1_, int p_i2108_2_)
            {
                super(p_i2108_1_, p_i2108_2_);
            }
        }

    public static class Start extends StructureTownPieces.Well
        {
            public WorldChunkManager worldChunkMngr;
            /** Boolean that determines if the village is in a desert or not. */
            public boolean inDesert;
            /** World terrain type, 0 for normal, 1 for flap map */
            public int terrainType;
            public StructureTownPieces.PieceWeight structVillagePieceWeight;
            /**
             * Contains List of all spawnable Structure Piece Weights. If no more Pieces of a type can be spawned, they
             * are removed from this list
             */
            public List structureVillageWeightedPieceList;
            public List field_74932_i = new ArrayList();
            public List field_74930_j = new ArrayList();
            public BiomeGenBase biome;

            public Start() {}

            public Start(WorldChunkManager p_i2104_1_, int p_i2104_2_, Random p_i2104_3_, int p_i2104_4_, int p_i2104_5_, List p_i2104_6_, int p_i2104_7_)
            {
                super((StructureTownPieces.Start)null, 0, p_i2104_3_, p_i2104_4_, p_i2104_5_);
                this.worldChunkMngr = p_i2104_1_;
                this.structureVillageWeightedPieceList = p_i2104_6_;
                this.terrainType = p_i2104_7_;
                BiomeGenBase biomegenbase = p_i2104_1_.getBiomeGenAt(p_i2104_4_, p_i2104_5_);
                this.inDesert = biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills;
                this.biome = biomegenbase;
            }

            public WorldChunkManager getWorldChunkManager()
            {
                return this.worldChunkMngr;
            }
        }

    public static class Torch extends StructureTownPieces.Village
        {

            public Torch() {}

            public Torch(StructureTownPieces.Start p_i2097_1_, int p_i2097_2_, Random p_i2097_3_, StructureBoundingBox p_i2097_4_, int p_i2097_5_)
            {
                super(p_i2097_1_, p_i2097_2_);
                this.coordBaseMode = p_i2097_5_;
                this.boundingBox = p_i2097_4_;
            }

            public static StructureBoundingBox func_74904_a(StructureTownPieces.Start p_74904_0_, List p_74904_1_, Random p_74904_2_, int p_74904_3_, int p_74904_4_, int p_74904_5_, int p_74904_6_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74904_3_, p_74904_4_, p_74904_5_, 0, 0, 0, 3, 4, 2, p_74904_6_);
                return StructureComponent.findIntersecting(p_74904_1_, structureboundingbox) != null ? null : structureboundingbox;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
                }

                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
                {
	                this.fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 2, 3, 1, Blocks.air, Blocks.air, false);
	                this.placeBlockAtCurrentPosition(p_74875_1_, BlocksCore.fence[0], 0, 1, 0, 0, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, BlocksCore.fence[0], 0, 1, 1, 0, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, BlocksCore.fence[0], 0, 1, 2, 0, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, BlocksCore.voidStone, 15, 1, 3, 0, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, BlocksCore.torch, 0, 0, 3, 0, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, BlocksCore.torch, 0, 1, 3, 1, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, BlocksCore.torch, 0, 2, 3, 0, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, BlocksCore.torch, 0, 1, 3, -1, p_74875_3_);
                }
                return true;
            }
        }

    public abstract static class Village extends StructureComponent
        {
            protected int field_143015_k = -1;
            /** The number of villagers that have been spawned in this component. */
            private int villagersSpawned;
            private boolean field_143014_b;
            private StructureTownPieces.Start startPiece;

            public Village() {}

            protected Village(StructureTownPieces.Start p_i2107_1_, int p_i2107_2_)
            {
                super(p_i2107_2_);

                if (p_i2107_1_ != null)
                {
                    this.field_143014_b = p_i2107_1_.inDesert;
                    startPiece = p_i2107_1_;
                }
            }

            protected void func_143012_a(NBTTagCompound p_143012_1_)
            {
                p_143012_1_.setInteger("HPos", this.field_143015_k);
                p_143012_1_.setInteger("VCount", this.villagersSpawned);
                p_143012_1_.setBoolean("Desert", this.field_143014_b);
            }

            protected void func_143011_b(NBTTagCompound p_143011_1_)
            {
                this.field_143015_k = p_143011_1_.getInteger("HPos");
                this.villagersSpawned = p_143011_1_.getInteger("VCount");
                this.field_143014_b = p_143011_1_.getBoolean("Desert");
            }

            /**
             * Gets the next village component, with the bounding box shifted -1 in the X and Z direction.
             */
            protected StructureComponent getNextComponentNN(StructureTownPieces.Start p_74891_1_, List p_74891_2_, Random p_74891_3_, int p_74891_4_, int p_74891_5_)
            {
                switch (this.coordBaseMode)
                {
                    case 0:
                        return StructureTownPieces.getNextVillageStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, 1, this.getComponentType());
                    case 1:
                        return StructureTownPieces.getNextVillageStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, 2, this.getComponentType());
                    case 2:
                        return StructureTownPieces.getNextVillageStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, 1, this.getComponentType());
                    case 3:
                        return StructureTownPieces.getNextVillageStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, 2, this.getComponentType());
                    default:
                        return null;
                }
            }

            /**
             * Gets the next village component, with the bounding box shifted +1 in the X and Z direction.
             */
            protected StructureComponent getNextComponentPP(StructureTownPieces.Start p_74894_1_, List p_74894_2_, Random p_74894_3_, int p_74894_4_, int p_74894_5_)
            {
                switch (this.coordBaseMode)
                {
                    case 0:
                        return StructureTownPieces.getNextVillageStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, 3, this.getComponentType());
                    case 1:
                        return StructureTownPieces.getNextVillageStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                    case 2:
                        return StructureTownPieces.getNextVillageStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, 3, this.getComponentType());
                    case 3:
                        return StructureTownPieces.getNextVillageStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                    default:
                        return null;
                }
            }

            /**
             * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of
             * all the levels in the BB's horizontal rectangle).
             */
            protected int getAverageGroundLevel(World p_74889_1_, StructureBoundingBox p_74889_2_)
            {
                int i = 0;
                int j = 0;
                if(this.boundingBox == null)
                	this.boundingBox = p_74889_2_;
                for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k)
                {
                    for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l)
                    {
                        if (p_74889_2_.isVecInside(l, 64, k))
                        {
                            i += Math.max(p_74889_1_.getTopSolidOrLiquidBlock(l, k), p_74889_1_.provider.getAverageGroundLevel());
                            ++j;
                        }
                    }
                }

                if (j == 0)
                {
                    return -1;
                }
                else
                {
                    return i / j;
                }
            }

            protected static boolean canVillageGoDeeper(StructureBoundingBox p_74895_0_)
            {
                return p_74895_0_ != null && p_74895_0_.minY > 10;
            }

            /**
             * Spawns a number of villagers in this component. Parameters: world, component bounding box, x offset, y
             * offset, z offset, number of villagers
             */
            protected void spawnVillagers(World p_74893_1_, StructureBoundingBox p_74893_2_, int p_74893_3_, int p_74893_4_, int p_74893_5_, int p_74893_6_)
            {
                if (this.villagersSpawned < p_74893_6_)
                {
                    for (int i1 = this.villagersSpawned; i1 < p_74893_6_; ++i1)
                    {
                        int j1 = this.getXWithOffset(p_74893_3_ + i1, p_74893_5_);
                        int k1 = this.getYWithOffset(p_74893_4_);
                        int l1 = this.getZWithOffset(p_74893_3_ + i1, p_74893_5_);

                        if (!p_74893_2_.isVecInside(j1, k1, l1))
                        {
                            break;
                        }

                        ++this.villagersSpawned;
                    }
                }
            }

            /**
             * Returns the villager type to spawn in this component, based on the number of villagers already spawned.
             */
            protected int getVillagerType(int p_74888_1_)
            {
                return 0;
            }

            protected Block func_151558_b(Block p_151558_1_, int p_151558_2_)
            {
                BiomeEvent.GetVillageBlockID event = new BiomeEvent.GetVillageBlockID(startPiece == null ? null : startPiece.biome, p_151558_1_, p_151558_2_);
                MinecraftForge.TERRAIN_GEN_BUS.post(event);
                if (event.getResult() == Result.DENY) return event.replacement;
                if (this.field_143014_b)
                {
                    if (p_151558_1_ == Blocks.log || p_151558_1_ == Blocks.log2)
                    {
                        return Blocks.sandstone;
                    }

                    if (p_151558_1_ == Blocks.cobblestone)
                    {
                        return Blocks.sandstone;
                    }

                    if (p_151558_1_ == Blocks.planks)
                    {
                        return Blocks.sandstone;
                    }

                    if (p_151558_1_ == Blocks.oak_stairs)
                    {
                        return Blocks.sandstone_stairs;
                    }

                    if (p_151558_1_ == Blocks.stone_stairs)
                    {
                        return Blocks.sandstone_stairs;
                    }

                    if (p_151558_1_ == Blocks.gravel)
                    {
                        return Blocks.sandstone;
                    }
                }

                return p_151558_1_;
            }

            protected int func_151557_c(Block p_151557_1_, int p_151557_2_)
            {
                BiomeEvent.GetVillageBlockMeta event = new BiomeEvent.GetVillageBlockMeta(startPiece == null ? null : startPiece.biome, p_151557_1_, p_151557_2_);
                MinecraftForge.TERRAIN_GEN_BUS.post(event);
                if (event.getResult() == Result.DENY) return event.replacement;
                if (this.field_143014_b)
                {
                    if (p_151557_1_ == Blocks.log || p_151557_1_ == Blocks.log2)
                    {
                        return 0;
                    }

                    if (p_151557_1_ == Blocks.cobblestone)
                    {
                        return 0;
                    }

                    if (p_151557_1_ == Blocks.planks)
                    {
                        return 2;
                    }
                }

                return p_151557_2_;
            }

            /**
             * current Position depends on currently set Coordinates mode, is computed here
             */
            protected void placeBlockAtCurrentPosition(World p_151550_1_, Block p_151550_2_, int p_151550_3_, int p_151550_4_, int p_151550_5_, int p_151550_6_, StructureBoundingBox p_151550_7_)
            {
                Block block1 = this.func_151558_b(p_151550_2_, p_151550_3_);
                int i1 = this.func_151557_c(p_151550_2_, p_151550_3_);
                super.placeBlockAtCurrentPosition(p_151550_1_, block1, i1, p_151550_4_, p_151550_5_, p_151550_6_, p_151550_7_);
            }

            /**
             * arguments: (World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int
             * maxY, int maxZ, int placeBlock, int replaceBlock, boolean alwaysreplace)
             */
            protected void fillWithBlocks(World p_151549_1_, StructureBoundingBox p_151549_2_, int p_151549_3_, int p_151549_4_, int p_151549_5_, int p_151549_6_, int p_151549_7_, int p_151549_8_, Block p_151549_9_, Block p_151549_10_, boolean p_151549_11_)
            {
                Block block2 = this.func_151558_b(p_151549_9_, 0);
                int k1 = this.func_151557_c(p_151549_9_, 0);
                Block block3 = this.func_151558_b(p_151549_10_, 0);
                int l1 = this.func_151557_c(p_151549_10_, 0);
                super.fillWithMetadataBlocks(p_151549_1_, p_151549_2_, p_151549_3_, p_151549_4_, p_151549_5_, p_151549_6_, p_151549_7_, p_151549_8_, block2, k1, block3, l1, p_151549_11_);
            }

            protected void func_151554_b(World p_151554_1_, Block p_151554_2_, int p_151554_3_, int p_151554_4_, int p_151554_5_, int p_151554_6_, StructureBoundingBox p_151554_7_)
            {
                Block block1 = this.func_151558_b(p_151554_2_, p_151554_3_);
                int i1 = this.func_151557_c(p_151554_2_, p_151554_3_);
                super.func_151554_b(p_151554_1_, block1, i1, p_151554_4_, p_151554_5_, p_151554_6_, p_151554_7_);
            }
        }

    public static class Well extends StructureTownPieces.Village
        {

            public Well() {}

            public Well(StructureTownPieces.Start p_i2109_1_, int p_i2109_2_, Random p_i2109_3_, int p_i2109_4_, int p_i2109_5_)
            {
                super(p_i2109_1_, p_i2109_2_);
                this.coordBaseMode = p_i2109_3_.nextInt(4);

                switch (this.coordBaseMode)
                {
                    case 0:
                    case 2:
                        this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                        break;
                    default:
                        this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                }
            }

            /**
             * Initiates construction of the Structure Component picked, at the current Location of StructGen
             */
            public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
            {
                StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 1, this.getComponentType());
                StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, 3, this.getComponentType());
                StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, 2, this.getComponentType());
                StructureTownPieces.getNextComponentVillagePath((StructureTownPieces.Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
                }

                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
                {
	                this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 4, 12, 4, Blocks.cobblestone, Blocks.flowing_water, false);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.air, 0, 2, 12, 2, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.air, 0, 3, 12, 2, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.air, 0, 2, 12, 3, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.air, 0, 3, 12, 3, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.fence, 0, 1, 13, 1, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.fence, 0, 1, 14, 1, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.fence, 0, 4, 13, 1, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.fence, 0, 4, 14, 1, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.fence, 0, 1, 13, 4, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.fence, 0, 1, 14, 4, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.fence, 0, 4, 13, 4, p_74875_3_);
	                this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.fence, 0, 4, 14, 4, p_74875_3_);
	                this.fillWithBlocks(p_74875_1_, p_74875_3_, 1, 15, 1, 4, 15, 4, Blocks.cobblestone, Blocks.cobblestone, false);
                }
                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
                	
                for (int i = 0; i <= 5; ++i)
                {
                    for (int j = 0; j <= 5; ++j)
                    {
                        if (j == 0 || j == 5 || i == 0 || i == 5)
                        {
                            this.placeBlockAtCurrentPosition(p_74875_1_, Blocks.gravel, 0, j, 11, i, p_74875_3_);
                            this.clearCurrentPositionBlocksUpwards(p_74875_1_, j, 12, i, p_74875_3_);
                        }
                    }
                }

                return true;
            }
        }

    public static class WoodHut extends StructureTownPieces.Village
        {
            private boolean isTallHouse;
            private int tablePosition;

            public WoodHut() {}

            public WoodHut(StructureTownPieces.Start p_i2101_1_, int p_i2101_2_, Random p_i2101_3_, StructureBoundingBox p_i2101_4_, int p_i2101_5_)
            {
                super(p_i2101_1_, p_i2101_2_);
                this.coordBaseMode = p_i2101_5_;
                this.boundingBox = p_i2101_4_;
                this.isTallHouse = p_i2101_3_.nextBoolean();
                this.tablePosition = p_i2101_3_.nextInt(3);
            }

            protected void func_143012_a(NBTTagCompound p_143012_1_)
            {
                super.func_143012_a(p_143012_1_);
                p_143012_1_.setInteger("T", this.tablePosition);
                p_143012_1_.setBoolean("C", this.isTallHouse);
            }

            protected void func_143011_b(NBTTagCompound p_143011_1_)
            {
                super.func_143011_b(p_143011_1_);
                this.tablePosition = p_143011_1_.getInteger("T");
                this.isTallHouse = p_143011_1_.getBoolean("C");
            }

            public static StructureTownPieces.WoodHut func_74908_a(StructureTownPieces.Start p_74908_0_, List p_74908_1_, Random p_74908_2_, int p_74908_3_, int p_74908_4_, int p_74908_5_, int p_74908_6_, int p_74908_7_)
            {
                StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_74908_3_, p_74908_4_, p_74908_5_, 0, 0, 0, 4, 6, 5, p_74908_6_);
                return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_74908_1_, structureboundingbox) == null ? new StructureTownPieces.WoodHut(p_74908_0_, p_74908_7_, p_74908_2_, structureboundingbox, p_74908_6_) : null;
            }

            /**
             * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
             * Mineshafts at the end, it adds Fences...
             */
            public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
            {
                if (this.field_143015_k < 0)
                {
                    this.field_143015_k = this.getAverageGroundLevel(p_74875_1_, p_74875_3_);

                    if (this.field_143015_k < 0)
                    {
                        return true;
                    }

                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
                }
                //TODO do
                if(p_74875_1_.provider.dimensionId == Config.dimensionID)
                {
	                ECExplosion explosion = new ECExplosion(p_74875_1_, null, p_74875_3_.minX,  this.getAverageGroundLevel(p_74875_1_, p_74875_3_)-3, p_74875_3_.minZ, 15F);
	                explosion.doExplosionA();
	                explosion.doExplosionB(true);
                }
                return true;
            }
        }
}