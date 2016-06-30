/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.block.networking;


import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import appeng.block.AEBaseTileBlock;
import appeng.core.features.AEFeature;
import appeng.tile.networking.TileController;


public class BlockController extends AEBaseTileBlock
{

	public static enum ControllerBlockState implements IStringSerializable
	{
		offline, online, conflicted;

		@Override
		public String getName()
		{
			return this.name();
		}

	};

	public static final PropertyEnum CONTROLLER_STATE = PropertyEnum.create( "state", ControllerBlockState.class );

	@Override
	protected IProperty[] getAEStates()
	{
		return new IProperty[] { AE_BLOCK_FORWARD, AE_BLOCK_UP, CONTROLLER_STATE };
	}

	@Override
	public int getMetaFromState( final IBlockState state )
	{
		return ( (ControllerBlockState) state.getValue( CONTROLLER_STATE ) ).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta( final int meta )
	{
		return this.getDefaultState().withProperty( CONTROLLER_STATE, ControllerBlockState.offline );
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	public BlockController()
	{
		super( Material.IRON );
		this.setTileEntity( TileController.class );
		this.setHardness( 6 );
		this.setFeature( EnumSet.of( AEFeature.Channels ) );
	}

	@Override
	public void neighborChanged( final IBlockState state, final World w, final BlockPos pos, final Block neighborBlock )
	{
		final TileController tc = this.getTileEntity( w, pos );
		if( tc != null )
		{
			tc.onNeighborChange( false );
		}
	}
}
