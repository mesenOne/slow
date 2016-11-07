package com.indulge.freedom.who.bean;

import java.io.Serializable;

/**
 * 图片实体
 * Created by Nereo on 2015/4/7.
 */
public class Image implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -9188856219714998422L;
	public String path;
    public String name;
    public long time;
    public String uri;
    public String thumailUri;

    public Image(String path, String name, long time,String uri,String thumailUri){
        this.path = path;
        this.name = name;
        this.time = time;
        this.uri=uri;
        this.thumailUri=thumailUri;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }

	@Override
	public String toString() {
		return "Image [path=" + path + ", name=" + name + ", time=" + time
				+ ", uri=" + uri + ", thumailUri=" + thumailUri + "]";
	}
    
}
