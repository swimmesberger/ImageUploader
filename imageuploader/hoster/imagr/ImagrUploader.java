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

// experimental NOT WORKING AT THE MOMENT !!!
package imageuploader.hoster.imagr;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import imageuploader.hoster.HosterImage;
import imageuploader.hoster.ImageUploader;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicNameValuePair;

public class ImagrUploader implements ImageUploader
{

    private static final String URL = "http://imagr.eu/img/";

    public HosterImage uploadFile(BufferedImage imag, String ext)
    {
        try
        {
            // Creates Byte Array from picture
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imag, ext, baos);
            // Construct data
            String pictureData = Base64.encode(baos.toByteArray());
            String[] keys = new String[]
            {
                "boundary=---------------------------24464570528145"
            };
            String[] vals = new String[]
            {
                createUniqueId()
            };
            String sendRequest = sendRequest(keys, vals);
            System.out.println(sendRequest);
        } catch (IOException ex)
        {
            Logger.getLogger(ImagrUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String sendRequest(String[] keys, String[] val) throws IllegalStateException, IOException
    {
        final DefaultHttpClient client = new DefaultHttpClient();
        FileBody bin = new FileBody(new File("w1.png"), "image/png");
        final HttpPost httpPost = new HttpPost(URL);
        client.getCookieStore().addCookie(new BasicClientCookie2("PHPSESSID", "6e15blqstbnu720lrbr414s9b1"));
        
        StringBody comment = new StringBody("Filename: " + "w1.png");
        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("boundary", new StringBody("---------------------------24464570528145"));
        reqEntity.addPart("source0n", bin);
        reqEntity.addPart("filename", comment);
        httpPost.setEntity(reqEntity);
        
        
        //client.s("Cookie", "PHPSESSID==6e15blqstbnu720lrbr414s9b1");
//        List<NameValuePair> postParams = addValue(keys, val);
//        httpPost.setEntity(new UrlEncodedFormEntity(postParams));
        HttpResponse response = client.execute(httpPost);
        InputStream in = response.getEntity().getContent();
        String s = inputStreamToString(in);
        return s;
    }

    private List<NameValuePair> addValue(String[] key, String[] val)
    {
        final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        for (int i = 0; i < key.length; i++)
        {
            postParams.add(new BasicNameValuePair(key[i], val[i]));
        }
        return postParams;
    }

    private String inputStreamToString(InputStream in) throws IOException
    {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(in));
        while ((line = rd.readLine()) != null)
        {
            total.append(line);
            total.append(Character.LINE_SEPARATOR);
        }
        return total.toString();
    }

    public static void main(String[] args)
    {
        ImagrUploader upl = new ImagrUploader();
        upl.uploadFile(new File("w1.png"));
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
            Logger.getLogger(ImagrUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getProgress()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HosterImage uploadFile(BufferedImage imag)
    {
        return uploadFile(imag, "png");
    }

    /**
     * Helper to create unique id.
     */
    private static final class UniqueCreator
    {

        static int counter = 1;

        public static final int getUniqueId()
        {
            if (counter > 0)
            {
                return ++counter;
            }
            counter = 1;
            return getUniqueId();
        }
    }

    private static final String createUniqueId()
    {
        StringBuilder result = new StringBuilder();
        result.append("---Part=").append(UniqueCreator.getUniqueId());
        result.append('_').append(result.hashCode());
        result.append('_').append(System.currentTimeMillis());
        return result.toString();
    }
}
