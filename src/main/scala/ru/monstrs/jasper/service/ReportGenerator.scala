package ru.monstrs.jasper.service

import java.util.HashMap
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import net.sf.jasperreports.engine.design.JasperDesign
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JsonDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimplePdfExporterConfiguration
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput

trait ReportGenerator {
  def generatePDF(jasperDesign: JasperDesign, dataSource: JsonDataSource, response: HttpServletResponse)
  def generateExcel(jasperDesign: JasperDesign, dataSource: JsonDataSource, response: HttpServletResponse)
}

@Component
class ReportGeneratorImpl extends ReportGenerator {
  def generatePDF(jasperDesign: JasperDesign, dataSource: JsonDataSource, response: HttpServletResponse) {
    val output = response.getOutputStream
    val exporter: JRPdfExporter = new JRPdfExporter()

    try {
      val report = JasperCompileManager.compileReport(jasperDesign)
      val jasperPrint = JasperFillManager.fillReport(report, new HashMap[String, Object](), dataSource)

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint))
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output))

      exporter.exportReport()

      response.setContentType("application/pdf")
      output.flush()
    } finally {
      output.close()
    }
  }

  def generateExcel(jasperDesign: JasperDesign, dataSource: JsonDataSource, response: HttpServletResponse) {
    val output = response.getOutputStream
    val exporter = new JRXlsxExporter()

    try {
      val report = JasperCompileManager.compileReport(jasperDesign)
      val jasperPrint = JasperFillManager.fillReport(report, new HashMap[String, Object](), dataSource)

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint))
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(output))

      val configuration = new SimpleXlsxReportConfiguration()
      configuration.setOnePagePerSheet(false)
      exporter.setConfiguration(configuration)

      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")

      exporter.exportReport()

      output.flush()
    } finally {
      output.close()
    }
  }
}
