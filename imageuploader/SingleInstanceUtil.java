/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageuploader;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *
 * @author Simon
 */
public class SingleInstanceUtil
{
    private static File f;
    private static FileChannel channel;
    private static FileLock lock;
    
    public static boolean checkRunning()
    {
        try
        {
            f = new File("ImageUploader.lock");
            // Check if the lock exist
            if (f.exists())
            {
                // if exist try to delete it
                f.delete();
            }
            // Try to get the lock
            channel = new RandomAccessFile(f, "rw").getChannel();
            lock = channel.tryLock();
            if (lock == null)
            {
                // File is lock by other application
                channel.close();
                return true;
            }
            // Add shutdown hook to release lock when application shutdown
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
            return false;
        } catch (IOException e)
        {
            throw new RuntimeException("Could not start process.", e);
        }
        
    }

    public static void unlockFile()
    {
        // release and delete file lock
        try
        {
            if (lock != null)
            {
                lock.release();
                channel.close();
                f.delete();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    static class ShutdownHook extends Thread
    {

        @Override
        public void run()
        {
            unlockFile();
        }
    }
}
