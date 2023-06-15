"# myfirstproject" 
import java.net.*;
import java.io.*;
public class checkproject
{
public static void main(String arr[]) throws Exception
{

  URL url = new URL("http://localhost:8084/ocasGetToken?uri=pavan");
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
con.setRequestProperty("Authorization","Basic YXNkZjoxMjM0NQ==");
con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");


BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
StringBuffer output=new StringBuffer("");
String line="";
while((line=br.readLine())!=null)
output.append(line+"\n");

System.out.println(output);

}
}
