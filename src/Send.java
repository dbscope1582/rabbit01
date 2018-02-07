import java.util.*;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.*;
import com.rabbitmq.client.AMQP.BasicProperties;
import java.util.Map.Entry;
import java.io.*;
import org.xml.sax.InputSource;

public class Send {

  private static Channel createChannel(String queueName) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(Constants.rabbitServer());
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(queueName, false, false, false, null);
    return channel;
    //return null;
  }

  private static String getEad(String fileName)  throws Exception{
    FileReader reader = new FileReader(fileName);
    BufferedReader buffer= new BufferedReader(reader);
    String line="";
    String ret="";
    while((line=buffer.readLine()) != null) {
      ret=ret+line;
    }
    return ret;
  }

  private static String getXML(String fileName, String encoding) throws Exception {
    String rootedFileName="../xml/"+fileName;
    BufferedReader in = new BufferedReader(
      new InputStreamReader(
        new FileInputStream(rootedFileName),encoding));
    
    String str="";
    String ret="";
    while((str=in.readLine()) != null){
        ret=ret+str;
    }
    in.close();
    return ret;
  }

  public static byte[] getMessageAsBytes(String fileName) throws Exception {
    return getXML(fileName, "UTF-8").getBytes("UTF-8");
  }

  public static void main(String[] argv) throws Exception {
    Channel channel =createChannel(Constants.QUEUE_NAME);
    
    String filename="ead2002.xml";
    if(argv.length==1){
      filename=argv[0];
    }

    java.util.Map<String,Object> headers= new java.util.HashMap<String,Object>();
    headers.put("routing_key","publish");
    headers.put("uuid",UUID.randomUUID().toString());
    headers.put("standard","EAD");
    headers.put("version","2002");
    headers.put("vendor","scope");

    BasicProperties properties= new BasicProperties.Builder()
    .headers(headers)
    .appId("application/xml")
    .contentEncoding("UTF-8")
    .build();

    //message=getEad("ead2002.xml");
    //message="<? xml  version = \"1.0\"  encoding = \"UTF-8\" ?>"
    //+"<! DOCTYPE   ead  PUBLIC \"+//ISBN 1-931666-00-8//DTD ead.dtd (Encoded Archival Description (EAD) Version 2002)//EN\" \"ead.dtd\">"
    //+"< ead   audience = \"external\" ></ead>";
    
    channel.basicPublish(Constants.ExchangeName, "publish",properties,getMessageAsBytes(filename));
    System.out.println(" [x] Sent '" + getXML(filename, "UTF-8") + "'");

    channel.close();
    channel.getConnection().close();
    //connection.close();
  }
}
