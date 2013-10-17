package com.voyagerinnovation.smiwallet.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

public class BumpUtils {

	private BumpUtils() {}
	
	public static byte[] objectToByte(Serializable object) throws IOException {
	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] converted = null;	
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(object);
		  converted = bos.toByteArray();	
		} finally {
		  out.close();
		  bos.close();
		}	
		return converted;
	}
	
	public static Object byteToObject(byte[] byteArray) throws  IOException, ClassNotFoundException, StreamCorruptedException {
		
		ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
		ObjectInput in = null;
		Object object = null;	
		try {
		  in = new ObjectInputStream(bis);
		  object = in.readObject(); 		
		} finally {
		  bis.close();
		  in.close();
		}	
		return object;
	}
}







