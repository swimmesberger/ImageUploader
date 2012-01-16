/*
 * Copyright (C) 2011 Thedeath<www.fseek.org>
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
package imageuploader.guiClasses;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.TransferHandler;

public class ImageTransferhandler extends TransferHandler
{

    private MainFrame frame;

    public ImageTransferhandler(MainFrame frame)
    {
        this.frame = frame;
    }
    public static DataFlavor[] supportedFlavors = null;

    static
    {
        try
        {
            supportedFlavors = new DataFlavor[]
            {
                new DataFlavor("application/x-java-url; class=java.net.URL"), new DataFlavor("image/bmp; class=java.io.InputStream"), DataFlavor.imageFlavor, DataFlavor.javaFileListFlavor, new DataFlavor("text/uri-list; class=java.lang.String; charset=Unicode")
            };
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(ImageTransferhandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean canImport(TransferSupport support)
    {
        DataFlavor[] dataFlavors = support.getDataFlavors();
        DataFlavor flav = dataFlavors[0];
        if (dataFlavors.length >= 3)
        {
            if (dataFlavors[2].getMimeType().equals(supportedFlavors[4].getMimeType()))
            {
                return true;
            }
        }
        for (DataFlavor f : supportedFlavors)
        {
            if (flav.getMimeType().equals(f.getMimeType()))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean importData(TransferSupport support)
    {
        Transferable transferable = support.getTransferable();
        DataFlavor[] dataFlavors = support.getDataFlavors();
        BufferedImage transferData = null;
        transferData = getImage(transferable);
        if (transferData == null)
        {
            transferData = getImageFromFile(transferable);
        }
        if (transferData == null)
        {
            transferData = getFromURL(transferable);
        }
        if (transferData == null)
        {
            transferData = getFromString(transferable);
        }
        if (transferData != null)
        {
            frame.setImage(transferData);
        }
        else
        {
            System.out.println("UNSUPPORTED FLAVOR:");
            System.out.println("HumanPresentableName: " + dataFlavors[0].getHumanPresentableName());
            System.out.println("DefaultRepresentationClass: " + dataFlavors[0].getDefaultRepresentationClassAsString());
            System.out.println("PrimaryMIMEType: " + dataFlavors[0].getPrimaryType());
            System.out.println("MIMEType: " + dataFlavors[0].getMimeType());
        }
        return false;
    }

    private BufferedImage getImageFromFile(Transferable transferable)
    {
        try
        {
            Collection col = (Collection) transferable.getTransferData(supportedFlavors[3]);
            ArrayList arrList = new ArrayList(col);
            return getImageFromList(arrList);
        } catch (Exception ex)
        {
            //Logger.getLogger(ImageTransferhandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private BufferedImage getImageFromList(List l) throws IOException
    {
        File f = (File) l.get(0);
        return ImageIO.read(f);
    }

    private BufferedImage getImage(Transferable transferable)
    {
        try
        {
            return (BufferedImage) transferable.getTransferData(supportedFlavors[2]);
        } catch (Exception ex)
        {
            //Logger.getLogger(ImageTransferhandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private BufferedImage getFromURL(Transferable transferable)
    {
        try
        {
            java.net.URL url = (java.net.URL) transferable.getTransferData(supportedFlavors[0]);
            return ImageIO.read(url.openStream());
        } catch (Exception ex)
        {
            //Logger.getLogger(ImageTransferhandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static java.util.List textURIListToFileList(String data)
    {
        java.util.List list = new java.util.ArrayList(1);
        for (java.util.StringTokenizer st = new java.util.StringTokenizer(data, "\r\n");
        st.hasMoreTokens();)
        {
            String s = st.nextToken();
            if (s.startsWith("#"))
            {
                // the line is a comment (as per the RFC 2483)
                continue;
            }
            try
            {
                java.net.URI uri = new java.net.URI(s);
                java.io.File file = new java.io.File(uri);
                list.add(file);
            } catch (java.net.URISyntaxException e)
            {
                // malformed URI
            } catch (IllegalArgumentException e)
            {
                // the URI is not a valid 'file:' URI
            }
        }
        return list;
    }

    private BufferedImage getFromString(Transferable transferable)
    {
        try
        {
            String str = (String) transferable.getTransferData(supportedFlavors[4]);
            List textURIListToFileList = textURIListToFileList(str);
            return getImageFromList(textURIListToFileList);
        } catch (Exception ex)
        {
            Logger.getLogger(ImageTransferhandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
