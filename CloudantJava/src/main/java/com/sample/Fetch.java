package com.sample;

public class Fetch {
	public String _id, _rev;
	public String title;
	public String shortdesc;
	public String id;
	public String rating;
    public String price;
    public String img;

	boolean hasRequiredFields() {
		if (id != null && !id.isEmpty() && title != null && !title.isEmpty() && shortdesc != null && !shortdesc.isEmpty()
				&& rating != null && !rating.isEmpty() && price != null && !price.isEmpty() && img != null && !img.isEmpty()) {
			return true;
		}
		return false;
	}
}