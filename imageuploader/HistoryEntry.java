/*
 * Copyright (C) 2012 Thedeath<www.fseek.org>
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
package imageuploader;

import java.io.File;
import java.util.Date;

/**
 *
 * @author Thedeath<www.fseek.org>
 */
public class HistoryEntry
{
    public static final String valueSeperator = ";";
    private String url;
    private String hoster;
    private Date uploadDate;

    public HistoryEntry(String url, String hoster, Date uploadDate)
    {
        this.url = url;
        this.hoster = hoster;
        this.uploadDate = uploadDate;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the hoster
     */
    public String getHoster()
    {
        return hoster;
    }

    /**
     * @param hoster the hoster to set
     */
    public void setHoster(String hoster)
    {
        this.hoster = hoster;
    }

    /**
     * @return the uploadDate
     */
    public Date getUploadDate()
    {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(Date uploadDate)
    {
        this.uploadDate = uploadDate;
    }
    
    @Override
    public String toString()
    {
        return url + valueSeperator + hoster + valueSeperator + uploadDate.getTime();
    }


    
}
