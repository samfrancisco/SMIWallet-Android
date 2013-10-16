package com.voyagerinnovation.smiwallet.utilities;

public class Logger {

	private final static boolean SHOULD_LOG = true;
	
	public static int v(String tag, String string) {
	    if (SHOULD_LOG) return android.util.Log.v(tag, string);
	    else return 0;
	}
	public static int v(String tag, String string, Throwable tr) {
	    if (SHOULD_LOG) return android.util.Log.v(tag, string, tr);
	    else return 0;
	}
	public static int i(String tag, String string) {
	    if (SHOULD_LOG) return android.util.Log.i(tag, string);
	    else return 0;
	}
	public static int i(String tag, String string, Throwable tr) {
	    if (SHOULD_LOG) return android.util.Log.i(tag, string, tr);
	    else return 0;
	}
	public static int d(String tag, String string) {
	    if (SHOULD_LOG) return android.util.Log.d(tag, string);
	    else return 0;
	}		
	public static int d(String tag, String string, Throwable tr) {
	    if (SHOULD_LOG) return android.util.Log.d(tag, string, tr);
	    else return 0;
	}
	public static int w(String tag, String string) {
	    return android.util.Log.w(tag, string);
	}
	public static int w(String tag, Throwable tr) {
	    return android.util.Log.w(tag, tr);
	}
	public static int w(String tag, String string, Throwable tr) {
	    return android.util.Log.w(tag, string, tr);
	}
	public static int e(String tag, String string) {
	    return android.util.Log.e(tag, string);
	}
	public static int e(String tag, String string, Throwable tr) {
		return android.util.Log.e(tag, string);
	}
	public static String getStackTraceString (Throwable tr){
		return android.util.Log.getStackTraceString(tr);
	}
	public static int println(int priority, String tag, String msg){
		return android.util.Log.println(priority, tag, msg);
	}
	public static int wtf(String tag, Throwable tr){
		return android.util.Log.wtf(tag,tr);
	}
	public static int wtf(String tag,String msg){
		return android.util.Log.wtf(tag, msg);
	}
	public static int wtf(String tag,String msg,Throwable tr){
		return android.util.Log.wtf(tag, msg, tr);
	}
	public static boolean isLoggable(String tag, int level){
		return android.util.Log.isLoggable(tag, level);
	}
	
}
