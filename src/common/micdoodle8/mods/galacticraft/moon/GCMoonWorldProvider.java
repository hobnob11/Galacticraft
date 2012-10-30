package micdoodle8.mods.galacticraft.moon;

import micdoodle8.mods.galacticraft.core.GalacticraftWorldProvider;
import net.minecraft.src.Chunk;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Vec3;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

/**
 * Copyright 2012, micdoodle8
 * 
 *  All rights reserved.
 *
 */
public class GCMoonWorldProvider extends GalacticraftWorldProvider
{
    private float[] colorsSunriseSunset = new float[4];
    
	public GCMoonWorldProvider()
    {
        this.setDimension(GCMoonConfigManager.dimensionIDMoon);
        this.dimensionId = GCMoonConfigManager.dimensionIDMoon;
    }
	
	@Override
    protected void generateLightBrightnessTable()
    {
        float var1 = 0.0F;

        for (int var2 = 0; var2 <= 15; ++var2)
        {
            float var3 = 1.0F - var2 / 15.0F;
            this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
        }
    }

	@Override
    public float[] calcSunriseSunsetColors(float var1, float var2)
    {
		return null;
    }

	@Override
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new GCMoonWorldChunkManager(this.worldObj, 0F);
    }

	@SideOnly(Side.CLIENT)
	@Override
    public Vec3 getFogColor(float var1, float var2)
    {
        return this.worldObj.func_82732_R().getVecFromPool((double)100F / 255F, (double)107F / 255F, (double)125F / 255F);
    }
	
	@Override
    public float calculateCelestialAngle(long par1, float par3)
    {
        int var4 = (int)(par1 % 24000L);
        float var5 = (var4 + par3) / 24000.0F - 0.25F;

        if (var5 < 0.0F)
        {
            ++var5;
        }

        if (var5 > 1.0F)
        {
            --var5;
        }

        float var6 = var5;
        var5 = 1.0F - (float)((Math.cos(var5 * Math.PI) + 1.0D) / 2.0D);
        var5 = var6 + (var5 - var6) / 3.0F;
        return var5;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        float var2 = this.worldObj.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * (float)Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
        {
            var3 = 0.0F;
        }

        if (var3 > 1.0F)
        {
            var3 = 1.0F;
        }

        return var3 * var3 * 0.5F + 0.3F;
    }
    
	public float calculatePhobosAngle(long par1, float par3)
	{
		return this.calculateCelestialAngle(par1, par3) * 3000;
	}
	
	public float calculateDeimosAngle(long par1, float par3)
	{
		return calculatePhobosAngle(par1, par3) * 0.0000000001F;
	}

	@Override
    public IChunkProvider getChunkProvider()
    {
        return new GCMoonChunkProvider(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled());
    }
	
	@Override
	public void updateWeather()
	{
        worldObj.getWorldInfo().setRainTime(0);
        worldObj.getWorldInfo().setRaining(false);
        worldObj.getWorldInfo().setThunderTime(0);
        worldObj.getWorldInfo().setThundering(false);
	}

    @Override
	public boolean isSkyColored()
    {
        return false;
    }
    
    @Override
	public double getHorizon()
    {
    	return 44.0D;
    }

    @Override
	public int getAverageGroundLevel()
    {
        return 44;
    }
    
    @Override
	public boolean isSurfaceWorld()
    {
        return false;
    }

    @Override
    public boolean canCoordinateBeSpawn(int var1, int var2)
    {
//        int var3 = this.worldObj.getFirstUncoveredBlock(var1, var2);
//        return var3 == GCBlocks.marsGrass.blockID;
    	return true;
    }
    
    @Override
    public boolean canRespawnHere()
    {
        return false;
    }
    
    @Override
    public String getSaveFolder()
    {
    	return "DIM" + GCMoonConfigManager.dimensionIDMoon;
    }

    @Override
	public String getWelcomeMessage()
    {
        return "Entering The Moon";
    }

    @Override
	public String getDepartMessage()
    {
        return "Leaving The Moon";
    }

	@Override
	public String getDimensionName() 
	{
		return "Moon";
	}

	@Override
    public boolean canSnowAt(int x, int y, int z)
    {
        return false;
    }

	@Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
    {
        return false;
    }
    
    @Override
	public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }
    
    @Override
	public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }
}
