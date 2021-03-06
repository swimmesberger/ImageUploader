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
package org.fseek.simon.imageuploader.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.plaf.metal.MetalIconFactory;

public class SysTray
{


    private static Image getImage() throws HeadlessException
    {

        Icon defaultIcon = MetalIconFactory.getTreeComputerIcon();

        Image img = new BufferedImage(defaultIcon.getIconWidth(),
        defaultIcon.getIconHeight(),
        BufferedImage.TYPE_4BYTE_ABGR);

        defaultIcon.paintIcon(new Panel(), img.getGraphics(), 0, 0);

        return img;

    }

    private static PopupMenu createPopupMenu() throws
    HeadlessException
    {

        PopupMenu menu = new PopupMenu();

        MenuItem exit = new MenuItem("Exit");

        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                System.exit(0);

            }
        });

        menu.add(exit);

        return menu;

    }

    public static void intTray(final MainFrame frame) throws Exception
    {
        
        final TrayIcon icon = new TrayIcon(getImage(),
        "ImageUploader", createPopupMenu());
        icon.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.setVisible(true);
                SystemTray.getSystemTray().remove(icon);
            }
        });
        SystemTray.getSystemTray().add(icon);
    }
}
