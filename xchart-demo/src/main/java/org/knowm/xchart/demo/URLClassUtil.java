package org.knowm.xchart.demo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.internal.series.Series;
import org.knowm.xchart.style.Styler;

public class URLClassUtil {

  private static final String DEMO_CHARTS_PACKAGE = "org.knowm.xchart.demo.charts";

  @SuppressWarnings("unchecked")
  public static List<ExampleChart<Chart<Styler, Series>>> getAllDemoCharts(URL url)
      throws Exception {
    List<ExampleChart<Chart<Styler, Series>>> demoCharts = new ArrayList<>();
    List<Class<?>> classes = getAllAssignedClasses(url);
    // sort
    Collections.sort(
        classes,
        new Comparator<Class<?>>() {
          @Override
          public int compare(Class<?> c1, Class<?> c2) {
            return c1.getName().compareTo(c2.getName());
          }
        });
    for (Class<?> c : classes) {
      demoCharts.add(((ExampleChart<Chart<Styler, Series>>) c.newInstance()));
    }
    return demoCharts;
  }

  public static List<Class<?>> getAllAssignedClasses(URL url)
      throws ClassNotFoundException, IOException {
    List<Class<?>> classes = null;
    String type = url.getProtocol();
    if ("file".equals(type)) {
      classes = DemoChartsUtil.getClassesByFile(new File(url.getFile()), DEMO_CHARTS_PACKAGE);
    } else if ("jar".equals(type)) {
      classes = DemoChartsUtil.getClassesByJar(url.getPath());
    }
    List<Class<?>> allAssignedClasses = new ArrayList<>();
    if (classes != null) {
      for (Class<?> c : classes) {
        if (ExampleChart.class.isAssignableFrom(c) && !ExampleChart.class.equals(c)) {
          allAssignedClasses.add(c);
        }
      }
    }
    return allAssignedClasses;
  }
}
