package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Apache FreeMarker Sample.
 */
public class SampleGenerator {

  /**
   * Command line interface.
   */
  public static void main(String[] args) {

    //====== Build template parameter ======//
    Map<String, Object> paramMap = new HashMap<>();
      
    // build itemList
    List<Map<String, String>> itemList = new ArrayList<>();
    Map<String, String> itemMap1 = new HashMap<>();
    itemMap1.put("param", "param1");
    itemMap1.put("value", "99.1");
    itemMap1.put("comment", "hogehoge");
    itemList.add(itemMap1);
    Map<String, String> itemMap2 = new HashMap<>();
    itemMap2.put("param", "param2");
    itemMap2.put("value", "98.2");
    itemMap2.put("comment", "hogehoge2");
    itemList.add(itemMap2);
    paramMap.put("itemList", itemList);
    
    // build mapValue
    Map<String, String> mapValue = new HashMap<>();
    mapValue.put("param3", "97.3");
    mapValue.put("param4", "96.4");
    paramMap.put("mapValue", mapValue);
    System.out.println(paramMap.toString());
    
    //====== Initialize template ======//
    // New Config
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
    
    // Encoding
    cfg.setDefaultEncoding("UTF-8");

    // Set template path
    cfg.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), "/");
  
    //====== Output ======//
    try {

      // Get template
      Template template = cfg.getTemplate("template.html");
      
      // Write to system out
      Writer out = new OutputStreamWriter(System.out);
      template.process(paramMap, out);
      out.flush();
      
      // Write to file
      try (Writer fw = new FileWriter(new File("result.html"))) {
        template.process(paramMap, fw);
        out.flush();
      }
      
    } catch(IOException | TemplateException ex) {
      ex.printStackTrace();
      System.exit(1);
    }
  }

}