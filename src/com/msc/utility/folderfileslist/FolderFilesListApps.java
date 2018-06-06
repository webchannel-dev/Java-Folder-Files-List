package com.msc.utility.folderfileslist;

/**
 * @Decsriptoin : Target of this apps is to get all filenames inside the inputed folder and subfolder into Spreadsheet   .
 * @author : MAVAJ SUN CO
 * @Date : 8-March-2011
 * @Email : info@mavajsunco.com
 * @Website : www.MavajSunCo.com
 **/
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;
import jxl.*;
import jxl.write.*;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.image.RenderedImage;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class FolderFilesListApps {

    static int i = 0;
    static Vector fileList = new Vector();

    static void showDir(int indent, File file) throws IOException {
        for (int i = 0; i < indent; i++) {
        }
        if (file.isFile()) {
            Vector f = new Vector();
            f.add(file.getName());
            f.add(file.length());
            System.out.println(++i + " -" + file);
//            if (file.getName().endsWith(".png") || file.getName().endsWith(".gif") || file.getName().endsWith(".jpg") || file.getName().endsWith(".tif")) {
//                int[] i = imageFileProperlies(file);
//                f.add(i[0]);
//                f.add(i[1]);
//            } else {
//                f.add(0);
//                f.add(0);
//            }
           
            fileList.add(f);
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                showDir(indent + 4, files[i]);
            }
        }
    }

    static void exporttoExcel(String filePath) throws WriteException, IOException {
        WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath));
        WritableSheet sheet = workbook.createSheet("Folder Files list", 0);
        for (int i = 0; i < fileList.size(); i++) {
            Label fileName = new Label(0, i, (String) ((Vector) fileList.get(i)).get(0));
            sheet.addCell(fileName);
            Label fieSize = new Label(1, i, ((Vector) fileList.get(i)).get(1).toString());
            sheet.addCell(fieSize);
//            Label width = new Label(2, i, ((Vector) fileList.get(i)).get(2).toString());
//            sheet.addCell(width);
//            Label height = new Label(3, i, ((Vector) fileList.get(i)).get(3).toString());
//            sheet.addCell(height);
        }
        workbook.write();
        workbook.close();
    }

    static int[] imageFileProperlies(File file) throws IOException {
        int[] i = new int[2];
        FileInputStream in = new FileInputStream(file);
        // ImageIcon image = new ImageIcon(file.getAbsolutePath());
        BufferedImage img = ImageIO.read(in);
        i[0] = img.getWidth();
        i[1] = img.getHeight();

        System.out.print("  "+i[0]+"*");
        System.out.println(i[1]+"  ");
        return i;
    }

    public static void main(String arg[]) throws IOException, WriteException {
        showDir(1, new File("c:/temp/"));
//        for (int i = 0; i < fileList.size(); i++) {
//            System.out.print(((Vector) fileList.get(i)).get(0) + " - ");
//            System.out.print(((Vector) fileList.get(i)).get(1) + " - ");
//            System.out.print(((Vector) fileList.get(i)).get(2) + " - ");
//            System.out.println(((Vector) fileList.get(i)).get(3));
//
//        }
            exporttoExcel("c:/temp/FileNameList.xls");
    }
}
