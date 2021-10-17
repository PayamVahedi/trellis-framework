package com.hamrasta.trellis.core.metadata;

public enum MemoryUnit {
    B(1),
    KB(B.getSize() * 1024),
    MB(KB.getSize() * 1024),
    GB(MB.getSize() * 1024),
    TB(GB.getSize() * 1024),
    PB(TB.getSize() * 1024),
    EB(PB.getSize() * 1024);

    private long size;

    public long getSize() {
        return size;
    }

    MemoryUnit(long size) {
        this.size = size;
    }
}
