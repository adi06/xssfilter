package asu.cs541.ss.xssfilter.util;

public class EscapeUtils {
	
	public static String escapeHtml(String text){
		if(text == null || text.isEmpty()) return text;
		
		StringBuffer sb = new StringBuffer();
		   int n = text.length();
		   for (int i = 0; i < n; i++) {
		      char ch = text.charAt(i);
		      switch (ch) {
		         case '<': sb.append("&lt;"); break;
		         case '>': sb.append("&gt;"); break;
		         case '&': sb.append("&amp;"); break;
		         case '"': sb.append("&quot;"); break;
		         case ' ': sb.append("&nbsp;"); break; 
		         
		         default : sb.append(ch); break;
		      }
		   }
		   return sb.toString();
	}
}

