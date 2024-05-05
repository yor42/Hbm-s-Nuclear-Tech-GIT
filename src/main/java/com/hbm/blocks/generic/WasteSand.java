package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.modules.ItemHazardModule;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WasteSand extends BlockFalling implements IItemHazard {

	public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	ItemHazardModule module;
	
	public WasteSand(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setTickRandomly(false);
		this.setHarvestLevel("shovel", 0);
		this.module = new ItemHazardModule();
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	public WasteSand(Material materialIn, SoundType type, String s) {
		this(materialIn, s);
		setSoundType(type);
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 1;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{META});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(META);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(META, meta);
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entity) {
		if (entity instanceof EntityLivingBase && this == ModBlocks.waste_sand) {

    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 15 * 20, 4));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.waste_sand_red) {

    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 20 * 20, 3));
    	}
    	
    	if (entity instanceof EntityLivingBase && this == ModBlocks.waste_gravel) {
    	
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 2 * 60 * 20, 2));
    	}
    	if (entity instanceof EntityLivingBase && (this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red)) {
    	
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 20, 49));
    	}
	}

	@Override
	public boolean canEntitySpawn(IBlockState state, Entity entityIn){
		return ContaminationUtil.isRadImmune(entityIn);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red) {
			return ModItems.trinitite;
		}
		return Item.getItemFromBlock(this);
	}
}
