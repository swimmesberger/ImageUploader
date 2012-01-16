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

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import imageuploader.hoster.ImageUploader;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Simon Wimmesberger<www.fseek.org>
 */
public class ImgurUploader implements ImageUploader
{   
    private int progress = 0;
    private int insProgress = 100;

    public ImgurUploader()
    {
    }

    
    @Override
    public ImgurImage uploadFile(BufferedImage imag)
    {
        return uploadFile(imag, "png");
    }
    
    public ImgurImage uploadFile(BufferedImage imag, String ext)
    {
        try
        {
            // Creates Byte Array from picture
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imag, ext, baos);
            progress += 25;
            try
            {
                // Construct data
                String data = URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode("5a8683c13e5ba0e388b89dafc652d1cd", "UTF-8");
                data += "&" + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(Base64.encode(baos.toByteArray()), "UTF-8");

                // Send data
                URL url = new URL("http://api.imgur.com/2/upload");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
                progress += 25;
                try
                {
                    org.dom4j.Document document = parse(conn.getInputStream());
                    Element rootElement = document.getRootElement();
                    progress += 25;
                    ImgurImage imgInfo = new ImgurImage();
                    parseXML(rootElement, imgInfo);
                    progress = 100;
                    return imgInfo;
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                    progress = -1;
                }

            } catch (Exception e)
            {
                e.printStackTrace();
                progress = -1;
            }
        } catch (IOException ex)
        {
            Logger.getLogger(ImgurUploader.class.getName()).log(Level.SEVERE, null, ex);
            progress = -1;
        }
        return null;
    }
    
    @Override
    public ImgurImage uploadFile(File f)
    {
        try
        {
            String name = f.getName();
            String[] split = name.split("\\.");
            String ext = "png";
            if(split.length > 0)
            {
                ext = split[split.length-1];
            }
            BufferedImage imag = ImageIO.read(f);
            return uploadFile(imag, ext);
        } catch (IOException ex)
        {
            Logger.getLogger(ImgurUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    protected org.dom4j.Document parse(InputStream in) throws Exception
    {
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(in);
        return document;
    }

    private void parseXML(Element n, ImgurImage img) throws Exception
    {
        addToEngine(n, img);
        for (int i = 0; i < n.nodeCount(); i++)
        {
            Node node = n.node(i);
            if (node instanceof Element)
            {
                parseXML((Element) node, img);
            }
        }
    }

    private void addToEngine(Element n, ImgurImage img) throws Exception
    {
        String nodeName = n.getName();
        Object nodeData = n.getData();
        
        Class<? extends ImgurImage> aClass = img.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for(int i = 0; i<declaredFields.length; i++)
        {
            Field f = declaredFields[i];
            f.setAccessible(true);
            if(nodeName.equals(f.getName()))
            {
                try
                {
                    Class<?> type = f.getType();

                    if(type.toString().equals("boolean"))
                    {
                        f.setBoolean(img, Boolean.parseBoolean(nodeData.toString()));
                    }
                    else if(type.toString().equals("int"))
                    {
                        f.setInt(img, Integer.parseInt(nodeData.toString()));
                    }
                    else if(type.toString().equals("long"))
                    {
                        f.setLong(img, Long.parseLong(nodeData.toString()));
                    }
                    else
                    {
                        f.set(img, nodeData);
                    }
                }catch(Exception ex){ex.printStackTrace();}
            }
        }
    }
    
    /**
     * @return the progress
     */
    @Override
    public int getProgress()
    {
        return progress;
    }

    /**
     * @return the insProgress
     */
    public int getInsProgress()
    {
        return insProgress;
    }
}
