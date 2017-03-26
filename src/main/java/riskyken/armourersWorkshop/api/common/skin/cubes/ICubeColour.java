package riskyken.armourersWorkshop.api.common.skin.cubes;

import net.minecraft.nbt.NBTTagCompound;

public interface ICubeColour {
    
    byte getRed(int side);
    
    byte getGreen(int side);
        
    byte getBlue(int side);
    
    byte getPaintType(int side);
    
    byte[] getRed();
    
    byte[] getGreen();
    
    byte[] getBlue();
    
    byte[] getPaintType();
    
    void setColour(int colour, int side);
    
    @Deprecated
    void setColour(int colour);
    
    void setRed(byte red, int side);
    
    void setGreen(byte green, int side);
    
    void setBlue(byte blue, int side);
    
    void setPaintType(byte type, int side);
    
//    void readFromNBT(NBTTagCompound compound);
//
//    void writeToNBT(NBTTagCompound compound);
}
