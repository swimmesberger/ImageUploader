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

package org.fseek.simon.imageuploader.hoster;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;


public abstract class UploaderTemplate implements ImageUploader
{
    public int progress = 0;
    public int id;

    public UploaderTemplate(int id)
    {
        this.id = id;
    }

    @Override
    public int getID()
    {
        return id;
    }
    
    
    
    public static final String lineEnd = "\r\n";
    public static final String twoHyphens = "--";
   
    public HosterImage uploadFile(BufferedImage imag, String ext)
    {
        String fileName = generateRandomString() + "." + ext;
        HttpURLConnection conn = null;
        DataOutputStream dos;
        String boundary = "-----------------------------"+getUniqueId();
        System.out.println("HOSTER: " + this.getClass().getSimpleName());
        try 
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imag, ext, baos);
            
            URL url = new URL(getURL());
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP
            conn.setInstanceFollowRedirects(true);

            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            
            //write header
            writeHeader(conn, boundary);
            progress += 25;
            
            dos = new DataOutputStream(conn.getOutputStream());
            //write data
            //write first boundary
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            writeData(conn, dos, baos, fileName, boundary);
            //write last boundary
            dos.writeBytes(lineEnd + twoHyphens + boundary + twoHyphens + lineEnd);
            progress += 25;
            // close streams
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) 
        {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseUrl(conn);
    }
    
    private HosterImage parseUrl(HttpURLConnection conn)
    {
        try
        {
            progress += 25;
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            boolean directURL = false;
            String directURLString = "";
            String line;
            while ((line = rd.readLine()) != null)
            {
                if (directURL)
                {
                    String searchString = getFirstSearchString();
                    String secSearchString = getEndSearchString();
                    int indexOf = line.indexOf(searchString);
                    int secIndexOf = line.indexOf(secSearchString);
                    if (indexOf != -1)
                    {
                        directURLString = line.substring(indexOf + searchString.length(), secIndexOf);
                        break;
                    }
                }
                if (line.contains(getSearchStringContains()))
                {
                    directURL = true;
                }
            }
            rd.close();
            directURLString = pastParseURL(directURLString);
            progress += 25;
            System.out.println("directURLString = " + directURLString);
            return new SimpleHosterImage(directURLString);
        } catch (IOException ioex)
        {
            ioex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public HosterImage uploadFile(BufferedImage imag)
    {
        return uploadFile(imag, "png");
    }

    @Override
    public HosterImage uploadFile(File f)
    {
        try
        {
            String name = f.getName();
            String[] split = name.split("\\.");
            String ext = "png";
            if (split.length > 0)
            {
                ext = split[split.length - 1];
            }
            BufferedImage imag = ImageIO.read(f);
            return uploadFile(imag, ext);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static String generateRandomString()
    {
        Random r = new Random();
        String token = Long.toString(Math.abs(r.nextLong()), 36);
        return token;
    }
    
    public static long getUniqueId()
    {
        Random rand = new Random();
        return rand.nextLong();
    }
    
    public static byte[] hexStringToByteArray(String s) 
    {
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) 
            {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
            }
            return data;
    }

    @Override
    public int getProgress()
    {
        return progress;
    }
    
    public abstract String getURL();
    public abstract String getFirstSearchString();
    public abstract String getEndSearchString();
    public abstract String pastParseURL(String directUrl);
    public abstract void writeData(HttpURLConnection conn, DataOutputStream dos, ByteArrayOutputStream baos, String fileName, String boundary)throws IOException;
    public abstract void writeHeader(HttpURLConnection conn, String boundary);
    
    public abstract String getSearchStringContains();

}
