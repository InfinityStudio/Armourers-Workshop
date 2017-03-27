package net.skin43d.skin3d;

import io.netty.buffer.ByteBuf;

public interface ISkinDye {
    byte[] getDyeColour(int index);
    
    boolean haveDyeInSlot(int index);
    
    void addDye(byte[] rgbt);
    
    void addDye(int index, byte[] rgbt);
    
    void removeDye(int index);
    
    int getNumberOfDyes();
    
    void writeToBuf(ByteBuf buf);
    
    void readFromBuf(ByteBuf buf);
}
