package com.esd.db.model;

public class packWithBLOBs extends pack {

    private byte[] packFile;

    private String packFormat;

    public byte[] getPackFile() {
        return packFile;
    }

    public void setPackFile(byte[] packFile) {
        this.packFile = packFile;
    }

    public String getPackFormat() {
        return packFormat;
    }

    public void setPackFormat(String packFormat) {
        this.packFormat = packFormat == null ? null : packFormat.trim();
    }
}