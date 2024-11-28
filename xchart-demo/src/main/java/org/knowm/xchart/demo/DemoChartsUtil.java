package org.knowm.xchart.demo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.series.Series;
import org.knowm.xchart.style.Styler;

public class DemoChartsUtil {

  private static final String DEMO_CHARTS_PACKAGE = "org.knowm.xchart.demo.charts";

  public static List<ExampleChart<Chart<Styler, Series>>> getAllDemoCharts() {
    
    List<ExampleChart<Chart<Styler, Series>>> demoCharts = null;
    String packagePath = DEMO_CHARTS_PACKAGE.replace(".", "/");
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    URL url = loader.getResource(packagePath);
    if (url != null) {
      try {
        demoCharts = URLClassUtil.getAllDemoCharts(url);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return demoCharts;
  }

  public static List<Class<?>> getClassesByFile(File dir, String pk) throws ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<>();
    if (!dir.exists()) {
      return classes;
    }
    String fileName = "";
    for (File f : dir.listFiles()) {
      fileName = f.getName();
      if (f.isDirectory()) {
        classes.addAll(getClassesByFile(f, pk + "." + fileName));
      } else if (fileName.endsWith(".class")) {
        classes.add(
            Class.forName(pk + "." + fileName.substring(0, fileName.length() - ".class".length())));
      }
    }
    return classes;
  }

  @SuppressWarnings("resource")
  public static List<Class<?>> getClassesByJar(String jarPath)
      throws IOException, ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<>();
    String[] jarInfo = jarPath.split("!");
    String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
    String packagePath = jarInfo[1].substring(1);
    Enumeration<JarEntry> entrys = new JarFile(jarFilePath).entries();
    JarEntry jarEntry = null;
    String entryName = "";
    String className = "";
    while (entrys.hasMoreElements()) {
      jarEntry = entrys.nextElement();
      entryName = jarEntry.getName();
      if (entryName.endsWith(".class") && entryName.startsWith(packagePath)) {
        className = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
        classes.add(Class.forName(className));
      }
    }
    return classes;
  }
}
