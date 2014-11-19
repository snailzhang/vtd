package com.esd.db.model;

public class voiceNoteWithBLOBs extends voiceNote {
    private String noteContentText;

    private byte[] noteContent;
    
    private byte[] noteImage;

    public String getNoteContentText() {
        return noteContentText;
    }

    public void setNoteContentText(String noteContentText) {
        this.noteContentText = noteContentText == null ? null : noteContentText.trim();
    }

    public byte[] getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(byte[] noteContent) {
        this.noteContent = noteContent;
    }

	public byte[] getNoteImage() {
		return noteImage;
	}

	public void setNoteImage(byte[] noteImage) {
		this.noteImage = noteImage;
	}
    
}