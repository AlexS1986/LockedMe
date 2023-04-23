package com.lockedme;

public class File {

	private String name;
	
	public File(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public boolean isDirectory() {
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof File)) {
            return false;
        }

        File other = (File) obj;
        return this.name.equals(other.name);
    }
	
}
