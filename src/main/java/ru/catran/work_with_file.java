package ru.catran;

import java.io.*;
import java.util.*;

/**
 * Created by comp on 15.07.2017.
 * @autor: ALexey Androsov
 */
public class work_with_file {
    public static void readWithFileInByteArray(File file) {

        try (FileInputStream is = new FileInputStream(file)){

            byte[] a = new byte[is.available()];
            is.read(a);
            for (int i = 0; i < a.length; i++) {
                System.out.print((char)a[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    private static void staplingFiles(String[] files, File fileWrite) {
        List<FileInputStream> al = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            try {
                al.add(new FileInputStream(files[i]));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }


        try {
            SequenceInputStream sis = new SequenceInputStream(Collections.enumeration(al));
            byte[] arr = new byte[sis.available() * files.length];//вот здесь непонятно как задать размер массива, потому что sis.available() маловат
            int x;
            int count = 0;

            while ((x = sis.read()) != -1) {
                arr[count] = (byte) x;
                count++;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(fileWrite);
            fileOutputStream.write(arr, 0, arr.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void pagedOutputToTheConsole(File file, int page) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] arr = new byte[fileInputStream.available()];
            fileInputStream.read(arr);
            int x;


            for (int i = 0; i < 1800; i++) {
                if ((((page - 1) * 1800) + i) > arr.length) // проверяем, не вышли ли за пределы массива
                    break;
                x = arr[((page - 1) * 1800) + i];
                System.out.print((char)x);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readWithFileInByteArray(new File("1.txt"));


        String[] files = new String[]{"1.txt", "2.txt", "3.txt", "4.txt", "5.txt"};
        File file = new File("6.txt");
        staplingFiles(files, file);

        File file2 = new File("7.txt");
        long l = System.currentTimeMillis();
        pagedOutputToTheConsole(file2, 2);
        System.out.println(System.currentTimeMillis() - l);
    }
}
