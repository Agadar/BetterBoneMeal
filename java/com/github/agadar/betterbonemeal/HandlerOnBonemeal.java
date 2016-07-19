package com.github.agadar.betterbonemeal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerOnBonemeal 
{
	@SubscribeEvent 
	public void onBoneMealUse(BonemealEvent event) 
	{
		// If we're not server-side, ignore this.
		//if (!event.world.isRemote)
		//{
		//	return;
		//}
		
		// Retrieve the block position and the block itself from the event.
		BlockPos blockPos = event.pos;
		Block block = event.block.getBlock();

		// Code for if the block is reed or cactus
		if (block instanceof BlockReed || block instanceof BlockCactus) 
		{
			BlockPos oneBlockUp = blockPos.up();	// retrieve block 1 block up
			
			// If the block directly up is air and either the block directly below or the block below that
			// is not part of the plant, then we can grow the plant.
			if (event.world.isAirBlock(oneBlockUp) && 
					(event.world.getBlockState(blockPos.down(1)).getBlock() != block || 
						event.world.getBlockState(blockPos.down(2)).getBlock() != block)) 
			{
				event.world.setBlockState(oneBlockUp, block.getDefaultState());
				event.setResult(Result.ALLOW);
			}
			else
			{
				BlockPos twoBlocksUp = oneBlockUp.up();	// retrieve block 2 blocks up
				
				// Else if the block directly up is part of the plant, the block directly up from that is air,
				// and the block directly below is not part of the plant, then we can also grow the plant.
				if (event.world.getBlockState(oneBlockUp).getBlock() == block && 
							event.world.isAirBlock(twoBlocksUp) && 
								event.world.getBlockState(blockPos.down(1)).getBlock() != block) 
				{
					event.world.setBlockState(twoBlocksUp, block.getDefaultState());
					event.setResult(Result.ALLOW);
				}
			}
		}
		// Code for if the block is nether wart
		else if (block == Blocks.nether_wart) 
		{
			IBlockState blockState = event.world.getBlockState(blockPos);
			int age = blockState.getValue(BlockNetherWart.AGE);
			
			if (age < 3)
	        {
				blockState = blockState.withProperty(BlockNetherWart.AGE, age + 1);
				event.world.setBlockState(blockPos, blockState);
	            event.setResult(Result.ALLOW);
	        }
		}
	}
}
