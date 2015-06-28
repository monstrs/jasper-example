package ru.monstrs.jasper.report

import org.springframework.stereotype.Component
import net.sf.jasperreports.engine.JRException
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.JasperPrintManager
import net.sf.jasperreports.engine.design.JasperDesign
import net.sf.jasperreports.engine.design.JRDesignBand
import net.sf.jasperreports.engine.design.JRDesignExpression
import net.sf.jasperreports.engine.design.JRDesignField
import net.sf.jasperreports.engine.design.JRDesignFrame
import net.sf.jasperreports.engine.design.JRDesignGroup
import net.sf.jasperreports.engine.design.JRDesignLine
import net.sf.jasperreports.engine.design.JRDesignParameter
import net.sf.jasperreports.engine.design.JRDesignQuery
import net.sf.jasperreports.engine.design.JRDesignSection
import net.sf.jasperreports.engine.design.JRDesignStaticText
import net.sf.jasperreports.engine.design.JRDesignStyle
import net.sf.jasperreports.engine.design.JRDesignTextField
import net.sf.jasperreports.engine.design.JRDesignVariable
import net.sf.jasperreports.engine.export.JRCsvExporter
import net.sf.jasperreports.engine.export.JRRtfExporter
import net.sf.jasperreports.engine.export.JRXlsExporter
import net.sf.jasperreports.engine.export.oasis.JROdsExporter
import net.sf.jasperreports.engine.export.oasis.JROdtExporter
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
import net.sf.jasperreports.engine.`type`.CalculationEnum
import net.sf.jasperreports.engine.`type`.HorizontalTextAlignEnum
import net.sf.jasperreports.engine.`type`.HorizontalAlignEnum
import net.sf.jasperreports.engine.`type`.ModeEnum
import net.sf.jasperreports.engine.`type`.PositionTypeEnum
import net.sf.jasperreports.engine.`type`.ResetTypeEnum
import net.sf.jasperreports.engine.`type`.SplitTypeEnum
import net.sf.jasperreports.engine.util.AbstractSampleApp
import net.sf.jasperreports.engine.util.JRLoader
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleHtmlExporterOutput
import net.sf.jasperreports.export.SimpleOdsReportConfiguration
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import net.sf.jasperreports.export.SimpleWriterExporterOutput
import net.sf.jasperreports.export.SimpleXlsReportConfiguration
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration
import scala.collection.JavaConversions._

trait RegradesReport {
  def getJasperDesign: JasperDesign
}

@Component
class RegradesReportImpl extends RegradesReport {
  def getJasperDesign(): JasperDesign = {
    val jasperDesign = new JasperDesign()
    jasperDesign.setLanguage("groovy")
    jasperDesign.setName("Regrades")
    jasperDesign.setPageWidth(595)
    jasperDesign.setPageHeight(842)
    jasperDesign.setColumnWidth(555)
    jasperDesign.setColumnSpacing(0)
    jasperDesign.setLeftMargin(20)
    jasperDesign.setRightMargin(20)
    jasperDesign.setTopMargin(20)
    jasperDesign.setBottomMargin(20)

    //Fonts
    val normalStyle = new JRDesignStyle()
    normalStyle.setName("Sans_Normal")
    normalStyle.setDefault(true)
    normalStyle.setFontName("DejaVu Sans")
    normalStyle.setFontSize(10)
    normalStyle.setPdfFontName("Helvetica")
    normalStyle.setPdfEncoding("UTF-8")
    normalStyle.setPdfEmbedded(false)
    jasperDesign.addStyle(normalStyle)

    //Fields
    var field = new JRDesignField()
    field.setName("branch")
    field.setValueClass(classOf[java.lang.String])
    jasperDesign.addField(field)

    field = new JRDesignField()
    field.setName("confirmed_at")
    field.setValueClass(classOf[java.lang.String])
    jasperDesign.addField(field)

    field = new JRDesignField()
    field.setName("number")
    field.setValueClass(classOf[java.lang.String])
    jasperDesign.addField(field)

    field = new JRDesignField()
    field.setName("stock.name")
    field.setValueClass(classOf[java.lang.String])
    jasperDesign.addField(field)

    field = new JRDesignField()
    field.setName("confirmed_by_employee.full_name")
    field.setValueClass(classOf[java.lang.String])
    jasperDesign.addField(field)

    field = new JRDesignField()
    field.setName("note")
    field.setValueClass(classOf[java.lang.String])
    jasperDesign.addField(field)

    //Title
    var band = new JRDesignBand()
    band.setHeight(20)
    var textField = new JRDesignTextField()
    textField.setBlankWhenNull(true)
    textField.setStretchWithOverflow(true)
    textField.setX(0)
    textField.setY(0)
    textField.setWidth(555)
    textField.setHeight(20)
    textField.setHorizontalAlignment(HorizontalAlignEnum.LEFT)
    textField.setStyle(normalStyle)
    textField.setExpression(new JRDesignExpression("$F{branch}.toUpperCase()"))
    band.addElement(textField)

    jasperDesign.setTitle(band)

    //Header
    band = new JRDesignBand()
    band.setHeight(1)
    var line = new JRDesignLine()
    line.setX(0)
    line.setY(0)
    line.setWidth(555)
    line.setHeight(0)
    band.addElement(line)
    jasperDesign.setPageHeader(band)

    band = new JRDesignBand()
    band.setHeight(65)
    textField = new JRDesignTextField()
    textField.setBlankWhenNull(true)
    textField.setStretchWithOverflow(true)
    textField.setX(0)
    textField.setY(23)
    textField.setWidth(555)
    textField.setHeight(20)
    textField.setHorizontalAlignment(HorizontalAlignEnum.CENTER)
    textField.setStyle(normalStyle)
    textField.setExpression(new JRDesignExpression("(\"Коррекция количества № \" + $F{number} + \" от \" + $F{confirmed_at}).toUpperCase()"))
    band.addElement(textField)
    jasperDesign.getDetailSection.asInstanceOf[JRDesignSection]
      .addBand(band)

    band = new JRDesignBand()
    band.setHeight(200)
    textField = new JRDesignTextField()
    textField.setBlankWhenNull(true)
    textField.setStretchWithOverflow(true)
    textField.setX(0)
    textField.setY(0)
    textField.setWidth(190)
    textField.setHeight(15)
    textField.setStyle(normalStyle)
    textField.setExpression(new JRDesignExpression("\"СКЛАД : \" + $F{stock.name}"))
    band.addElement(textField)

    textField = new JRDesignTextField()
    textField.setBlankWhenNull(true)
    textField.setStretchWithOverflow(true)
    textField.setX(0)
    textField.setY(17)
    textField.setWidth(190)
    textField.setHeight(15)
    textField.setStyle(normalStyle)
    textField.setExpression(new JRDesignExpression("\"ПРОВЕЛ : \" + $F{confirmed_by_employee.full_name}"))
    band.addElement(textField)

    textField = new JRDesignTextField()
    textField.setBlankWhenNull(true)
    textField.setStretchWithOverflow(true)
    textField.setX(0)
    textField.setY(34)
    textField.setWidth(190)
    textField.setHeight(15)
    textField.setStyle(normalStyle)
    textField.setExpression(new JRDesignExpression("\"ПРИЧИНА : \" + $F{note}"))
    band.addElement(textField)
    jasperDesign.getDetailSection.asInstanceOf[JRDesignSection]
      .addBand(band)

    jasperDesign
  }
}
