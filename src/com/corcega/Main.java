package com.corcega;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class Main {
    public static void main(String[] args) {
        try (FileOutputStream binFile = new FileOutputStream("data.dat");
        FileChannel  binChannel = binFile.getChannel()){

            ByteBuffer buffer = ByteBuffer.allocate(100);
//            byte[] outputBytes = "Hello World!".getBytes();
//            byte[] outputBytes2 = "Nice to meet you".getBytes();
//            buffer.put(outputBytes).putInt(245).putInt(-98765).put(outputBytes2).putInt(1000);
//            buffer.flip();

            long[] numbersPositionInData = new long[3]; //added for challenge
            byte[] outputBytes = "Hello World!".getBytes();
            buffer.put(outputBytes);
            long int1Pos = outputBytes.length;
            numbersPositionInData[2] = buffer.position(); //added for challenge
            buffer.putInt(245);
            long int2Pos = int1Pos + Integer.BYTES;
            numbersPositionInData[1] = buffer.position(); //added for challenge
            buffer.putInt(-98765);
            byte[] outputBytes2 = "Nice to meet you".getBytes();
            buffer.put(outputBytes2);
            long int3Pos = int2Pos + Integer.BYTES + outputBytes2.length;
            numbersPositionInData[0] = buffer.position(); //added for challenge
            buffer.putInt(1000);
            buffer.flip();

            binChannel.write(buffer);



            RandomAccessFile ra = new RandomAccessFile("data.dat", "rwd");
            FileChannel channel = ra.getChannel();

            ByteBuffer readBuffer = ByteBuffer.allocate(100);
            channel.read(readBuffer);
            readBuffer.flip();
            byte[] inputString = new byte[outputBytes.length];
            readBuffer.get(inputString);
            System.out.println("inputString = " + new String(inputString));
            System.out.println("int1 = " + readBuffer.getInt());
            System.out.println("int2 = " + readBuffer.getInt());
            byte[] inputString2 = new byte[outputBytes2.length];
            readBuffer.get(inputString2);
            System.out.println("inputString2 = " + new String(inputString2));
            System.out.println("int3 = " + readBuffer.getInt());

            System.out.println("Now backwards: ");
            for (int i = 0; i < 3; i++) {
                System.out.println(
                        readBuffer.getInt((int) numbersPositionInData[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
