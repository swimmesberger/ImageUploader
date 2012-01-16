/*
 * Copyright (C) 2011 Simon Wimmesberger<www.fseek.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package imageuploader.hoster.imgur;

import imageuploader.hoster.HosterImage;

public class ImgurImage implements HosterImage
{
    private String name;
    private String title; 
    private String caption;
    private String hash;
    private String deletehash;
    private String datetime;
    private String type; 
    private boolean animated;
    private int width;
    private int height;
    private long size;
    private int views; 
    private int bandwidth;
    private String original;
    private String imgur_page;
    private String delete_page;
    private String small_square;
    private String large_thumbnail;

    public ImgurImage(String name, String title, String caption, String hash, String deletehash, String datetime, String type, boolean animated, int width, int height, long size, int views, int bandwidth, String original, String imgur_page, String delete_page, String small_square, String large_thumbnail)
    {
        this.name = name;
        this.title = title;
        this.caption = caption;
        this.hash = hash;
        this.deletehash = deletehash;
        this.datetime = datetime;
        this.type = type;
        this.animated = animated;
        this.width = width;
        this.height = height;
        this.size = size;
        this.views = views;
        this.bandwidth = bandwidth;
        this.original = original;
        this.imgur_page = imgur_page;
        this.delete_page = delete_page;
        this.small_square = small_square;
        this.large_thumbnail = large_thumbnail;
    }

    public ImgurImage()
    {
    }
    
    

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the caption
     */
    public String getCaption()
    {
        return caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    /**
     * @return the hash
     */
    public String getHash()
    {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash)
    {
        this.hash = hash;
    }

    /**
     * @return the deletehash
     */
    public String getDeletehash()
    {
        return deletehash;
    }

    /**
     * @param deletehash the deletehash to set
     */
    public void setDeletehash(String deletehash)
    {
        this.deletehash = deletehash;
    }

    /**
     * @return the datetime
     */
    public String getDatetime()
    {
        return datetime;
    }

    /**
     * @param datetime the datetime to set
     */
    public void setDatetime(String datetime)
    {
        this.datetime = datetime;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the animated
     */
    public boolean isAnimated()
    {
        return animated;
    }

    /**
     * @param animated the animated to set
     */
    public void setAnimated(boolean animated)
    {
        this.animated = animated;
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * @return the size
     */
    public long getSize()
    {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size)
    {
        this.size = size;
    }

    /**
     * @return the views
     */
    public int getViews()
    {
        return views;
    }

    /**
     * @param views the views to set
     */
    public void setViews(int views)
    {
        this.views = views;
    }

    /**
     * @return the bandwidth
     */
    public int getBandwidth()
    {
        return bandwidth;
    }

    /**
     * @param bandwidth the bandwidth to set
     */
    public void setBandwidth(int bandwidth)
    {
        this.bandwidth = bandwidth;
    }

    /**
     * @return the original
     */
    public String getOriginal()
    {
        return original;
    }

    /**
     * @param original the original to set
     */
    public void setOriginal(String original)
    {
        this.original = original;
    }

    /**
     * @return the imgur_page
     */
    public String getImgur_page()
    {
        return imgur_page;
    }

    /**
     * @param imgur_page the imgur_page to set
     */
    public void setImgur_page(String imgur_page)
    {
        this.imgur_page = imgur_page;
    }

    /**
     * @return the delete_page
     */
    public String getDelete_page()
    {
        return delete_page;
    }

    /**
     * @param delete_page the delete_page to set
     */
    public void setDelete_page(String delete_page)
    {
        this.delete_page = delete_page;
    }

    /**
     * @return the small_square
     */
    public String getSmall_square()
    {
        return small_square;
    }

    /**
     * @param small_square the small_square to set
     */
    public void setSmall_square(String small_square)
    {
        this.small_square = small_square;
    }

    /**
     * @return the large_thumbnail
     */
    public String getLarge_thumbnail()
    {
        return large_thumbnail;
    }

    /**
     * @param large_thumbnail the large_thumbnail to set
     */
    public void setLarge_thumbnail(String large_thumbnail)
    {
        this.large_thumbnail = large_thumbnail;
    }

    @Override
    public String getImageURL()
    {
        return getOriginal();
    }
    
    
}
