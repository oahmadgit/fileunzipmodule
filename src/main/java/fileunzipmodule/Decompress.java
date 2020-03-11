package fileunzipmodule;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.mule.util.FileUtils;

public class Decompress {
	 public Decompress(String zipFilePath, String destDir,String bkpDir, String id) {
	        File dir = new File(destDir);
	        // create output directory if it doesn't exist
	        if(!dir.exists()) dir.mkdirs();
	        FileInputStream fis;
	        //buffer for read and write data to file
	        byte[] buffer = new byte[1024];
	        try {
	            fis = new FileInputStream(zipFilePath);
	            ZipInputStream zis = new ZipInputStream(fis);
	            ZipEntry ze = zis.getNextEntry();
	            while(ze != null){
	            	//complete filename with ext
	                String fileName = ze.getName();
	                //file extension
	                String ext = fileName.substring(fileName.lastIndexOf("."));
	                //filename
	                String file = fileName.substring(0, fileName.lastIndexOf("."));
	                
	                //for output
	                File newFile = new File(destDir + File.separator + id +"-" + file + ext);
	                System.out.println("Unzipping to "+newFile.getAbsolutePath());
	                //create directories for sub directories in zip
	                new File(newFile.getParent()).mkdirs();
	                FileOutputStream fos = new FileOutputStream(newFile);
	                int len;
	                while ((len = zis.read(buffer)) > 0) {
	                fos.write(buffer, 0, len);
	                }
	                fos.close();
	                //close this ZipEntry
	                zis.closeEntry();
	                
	                //bkp of output file
	                FileUtils.copyFile(new File(zipFilePath),new File(bkpDir + File.separator + id +"-" + file + ".zip"));
	                System.out.println("Backing up to: "+bkpDir + File.separator + id +"-" + file + ".zip");
	                ze = zis.getNextEntry();
	            }
	            //close last ZipEntry
	            zis.closeEntry();
	            zis.close();
	            fis.close();
	            
	            //delete input file
	            new File(zipFilePath).delete();
	            System.out.println("Input file DELETED");
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	    }

}
