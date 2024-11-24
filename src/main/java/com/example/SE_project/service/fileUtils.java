package com.example.SE_project.service;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
public class fileUtils {

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION); // Maximum compression
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024]; // Buffer to store compressed data
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp); // Compress the data
            outputStream.write(tmp, 0, size); // Write the compressed data to the output stream
        }
        try {
            outputStream.close(); // Close the output stream
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray(); // Return the compressed data
    }


    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

}