package net.skin43d.impl.cubes;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;

public class CubeMarkerData {
    
    public byte x;
    public byte y;
    public byte z;
    public byte meta;
    
    public CubeMarkerData(byte x, byte y, byte z, byte meta) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.meta = meta;
    }
    
    public CubeMarkerData(DataInputStream stream) throws IOException {
        readFromStream(stream);
    }
    
    public void writeToStream(DataOutput stream) throws IOException {
        stream.writeByte(x);
        stream.writeByte(y);
        stream.writeByte(z);
        stream.writeByte(meta);
    }
    
    private void readFromStream(DataInput stream) throws IOException {
        x = stream.readByte();
        y = stream.readByte();
        z = stream.readByte();
        meta = stream.readByte();
    }

    @Override
    public String toString() {
        return "CubeMarkerData [x=" + x + ", y=" + y + ", z=" + z + ", meta=" + meta + "]";
    }
    
    
}
