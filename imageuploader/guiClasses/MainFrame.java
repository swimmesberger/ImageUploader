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
package imageuploader.guiClasses;

import imageuploader.HistoryEntry;
import imageuploader.ImageScaler;
import imageuploader.SingleInstanceUtil;
import imageuploader.UploadThread;
import imageuploader.hoster.HosterFactory;
import imageuploader.hoster.ImageUploader;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Thedeath<www.fseek.org>
 */
public class MainFrame extends javax.swing.JFrame
{
    //some options for the app, you can create a gui for that :)
    public static int IMAGE_HOSTER = HosterFactory.IMGUR_UPLOADER;
    public static final boolean TRAY_MODE = false;

    private ImageLabel imageLabel = new ImageLabel(this, null, JLabel.CENTER);
    private ImageField imageField = new ImageField(this);
    private BufferedImage img;
    private ImageUploader uploader;
    
    private boolean firstTray = true;

    /** Creates new form MainFrame */
    public MainFrame()
    {
        try
        {
            if(TRAY_MODE && SingleInstanceUtil.checkRunning())
            {
                JOptionPane.showMessageDialog(this, "The program is already running. Check your SystemTray :)");
                System.exit(0);
            }
            this.setLocationRelativeTo(null);
            initComponents();
            intHoster();
            mainPanel.add(imageField, BorderLayout.CENTER);
            this.setSize(430, 194);
            uploader = HosterFactory.createUploader(IMAGE_HOSTER);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    private void intHoster()
    {
        hosterComboBox.removeAllItems();
        for(String hoster : HosterFactory.HOSTERS)
        {
            hosterComboBox.addItem(hoster);
        }
        hosterComboBox.addItemListener(new ItemListener() 
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                IMAGE_HOSTER = hosterComboBox.getSelectedIndex();
                uploader = HosterFactory.createUploader(IMAGE_HOSTER);
            }
        });
    }

    public void uploadImage(BufferedImage img)
    {
        if (img == null)
        {
            JOptionPane.showMessageDialog(this, "Paste a image before !");
            return;
        }
        UploadThread tr = new UploadThread(this, img, uploader);
        tr.start();
        this.img = null;
    }

    public void setImage(BufferedImage img)
    {
        setImage(img, true);
        this.repaint();
    }

    public void setImage(BufferedImage img, boolean pack)
    {
        if (img == null)
        {
            return;
        }
        this.img = img;
        ImageScaler scal = new ImageScaler();
        int newWidth = mainPanel.getWidth();
        int newHeight = mainPanel.getHeight() - 10;
        if(newWidth > this.img.getWidth())
        {
            newWidth = this.img.getWidth();
        }
        if(newHeight > this.img.getHeight())
        {
            newHeight = this.img.getHeight();
        }
        double scale = determineImageScale(img.getWidth(), img.getHeight(), newWidth, newHeight);
        img = scal.scaleImage(img, new Dimension((int) (img.getWidth() * scale), (int) (img.getHeight() * scale)));
        mainPanel.remove(imageField);
        imageLabel.setText("");
        imageLabel.setIcon(new ImageIcon(img));
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        this.repaint();
        if (pack)
        {
            this.pack();
        }
        imageLabel.requestFocus();
    }

    private double determineImageScale(int sourceWidth, int sourceHeight, int targetWidth, int targetHeight)
    {
        double scalex = (double) targetWidth / sourceWidth;
        double scaley = (double) targetHeight / sourceHeight;
        return Math.min(scalex, scaley);
    }

    public JLabel getInfoLabel()
    {
        return this.infoLabel;
    }

    public void setNormal(String imageURL)
    {
        mainPanel.remove(imageLabel);
        mainPanel.add(imageField, BorderLayout.CENTER);
        this.repaint();
        //this.setSize(430, 194);
        historyWindow.getTableModel().addHistory(new HistoryEntry(imageURL, HosterFactory.HOSTERS[uploader.getID()], new Date()));
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        statusPanel = new javax.swing.JPanel();
        infoLabel = new javax.swing.JLabel();
        uploadButton = new javax.swing.JButton();
        hosterComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ImageUploader");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        mainPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                mainPanelComponentResized(evt);
            }
        });
        mainPanel.setLayout(new java.awt.BorderLayout());

        infoLabel.setText("Paste image here ^");

        uploadButton.setText("Upload");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        hosterComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("to");

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(infoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addComponent(uploadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hosterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(uploadButton)
                .addComponent(hosterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1))
        );

        mainPanel.add(statusPanel, java.awt.BorderLayout.SOUTH);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainPanelComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_mainPanelComponentResized
    {//GEN-HEADEREND:event_mainPanelComponentResized
        setImage(img, false);
    }//GEN-LAST:event_mainPanelComponentResized

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_uploadButtonActionPerformed
    {//GEN-HEADEREND:event_uploadButtonActionPerformed
        uploadImage(img);
    }//GEN-LAST:event_uploadButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        if(TRAY_MODE == false)return;
        if (!getJavaVersion().startsWith("1.6") && !getJavaVersion().startsWith("1.7"))
        {
            System.out.println("Error initializing SystemTray: Tray is supported since Java 1.6. your Version: " + getJavaVersion());
            return;
        }
        if (!SystemTray.isSupported())
        {
            System.out.println("Error initializing SystemTray: Tray isn't supported jet");
            return;
        }
        try
        {
            this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            if(firstTray)
            {
                JOptionPane.showMessageDialog(this, "Program will be minimized to Tray now !");
                firstTray = false;
            }
            SysTray.intTray(this);
            this.setVisible(false);
        } catch (Exception ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void formComponentMoved(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentMoved
    {//GEN-HEADEREND:event_formComponentMoved
        if(MainFrame.fixWindow)
            setWindowRight(mainFrame, historyWindow);
    }//GEN-LAST:event_formComponentMoved

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        if(MainFrame.fixWindow)
            setWindowRight(mainFrame, historyWindow);
    }//GEN-LAST:event_formComponentResized

    public static void setWindowRight(Window first, Window second)
    {
        Point location = first.getLocation();
        Dimension size = first.getSize();
        second.setLocation(location.x + size.width, location.y);
    }
    
    public static void setWindowLeft(Window first, Window second)
    {
        Point location = second.getLocation();
        Dimension size = first.getSize();
        first.setLocation(location.x - size.width, location.y);
    }
    
    public static String getJavaVersion()
    {
        String property = System.getProperty("java.version");
        return property;
    }
    
    private static MainFrame mainFrame;
    private static HistoryWindow historyWindow;
    public static boolean fixWindow = true;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (Exception ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            try
            {
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex1){}
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                mainFrame = new MainFrame();
                historyWindow = new HistoryWindow(mainFrame);
                mainFrame.setVisible(true);
                setWindowRight(mainFrame, historyWindow);
                historyWindow.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox hosterComboBox;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
}
