package org.knowm.xchart;

import org.knowm.xchart.internal.ChartBuilder;

public class CategoryChartBuilder extends ChartBuilder<CategoryChartBuilder, CategoryChart> {

  String xAxisTitle = "";
  String yAxisTitle = "";

  public CategoryChartBuilder() {}

  public CategoryChartBuilder xAxisTitle(String xAxisTitle) {

    this.xAxisTitle = xAxisTitle;
    return this;
  }

  public CategoryChartBuilder yAxisTitle(String yAxisTitle) {

    this.yAxisTitle = yAxisTitle;
    return this;
  }

  /**
   * return fully built Chart_Category
   *
   * @return a CategoryChart
   */
  @Override
  public CategoryChart build() {
    // Pass the configured properties to CategoryChart during creation
    CategoryChart chart = new CategoryChart(this.width, this.height, this.chartTheme);
    chart.setTitle(this.title);
    chart.setXAxisTitle(this.xAxisTitle);
    chart.setYAxisTitle(this.yAxisTitle);
    return chart;
  }
}
