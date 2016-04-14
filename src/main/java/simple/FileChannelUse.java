package simple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelUse {
	
	public static void test()  
	{
		RandomAccessFile aFile=null;
		 
		System.out.println("start!");
		try {
			aFile = new RandomAccessFile("data.txt","rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not Found!");
			e.printStackTrace();
		}
		FileChannel inChannel=aFile.getChannel();
		
		ByteBuffer buf=ByteBuffer.allocate(48);
		 
		int bytesRead;
		try {
			bytesRead = inChannel.read(buf);
			
			while(bytesRead!=-1)
			{
				System.out.println("Read: "+bytesRead);
				
				buf.flip();
				
				while(buf.hasRemaining())
				{
					System.out.println((char)buf.get());
				}
				
				buf.clear();
				bytesRead=inChannel.read(buf);
			}
			
			aFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IoExepton!");
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) throws IOException
	{
		test();
		
	}

}
