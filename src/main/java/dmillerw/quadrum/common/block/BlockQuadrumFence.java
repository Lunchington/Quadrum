package dmillerw.quadrum.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.lib.BlockStaticMethodHandler;
import dmillerw.quadrum.common.lib.IQuadrumObject;
import dmillerw.quadrum.common.lib.TabQuadrum;
import net.minecraft.block.BlockFence;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author dmillerw
 */
public class BlockQuadrumFence extends BlockFence implements IQuadrumObject {

    private final BlockData blockData;

    private BlockQuadrumFence(BlockData blockData) {
        super("", blockData.getBlockMaterial());

        this.blockData = blockData;
        
        setStepSound(blockData.getBlockSound());
        setHardness(blockData.hardness);
        setResistance(blockData.resistance);
        setBlockName(blockData.name);
        setCreativeTab(TabQuadrum.BLOCK);

        if (blockData.requiresTool) {
            setHarvestLevel(blockData.getHarvestTool(), blockData.miningLevel);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return blockData.transparent ? 1 : 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        TextureLoader.registerIcons(register, this);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return TextureLoader.getIcon(this, "default");
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return BlockStaticMethodHandler.getDrops(this, blockData, world, x, y, z, metadata, fortune);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return blockData.collision ? super.getCollisionBoundingBoxFromPool(world, x, y, z) : null;
    }

    @Override
    public BlockData get() {
        return blockData;
    }
}
