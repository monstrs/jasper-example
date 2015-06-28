package ru.monstrs.jasper.controller

import javax.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable
import net.sf.jasperreports.engine.data.JsonDataSource
import ru.monstrs.jasper.report.RegradesReport
import ru.monstrs.jasper.service.ReportGenerator

@Controller
@RequestMapping(Array("/reports/{report}"))
class ReportsController {
  @Autowired
  protected var regradesReport: RegradesReport = _

  @Autowired
  protected var reportGenerator: ReportGenerator = _

  @RequestMapping(Array("pdf"))
  def pdf(response: HttpServletResponse, @PathVariable("report") report: String) {
    reportGenerator.generatePDF(regradesReport.getJasperDesign, getDataSource, response)
  }

  @RequestMapping(Array("excel"))
  def excel(response: HttpServletResponse, @PathVariable("report") report: String) {
    reportGenerator.generateExcel(regradesReport.getJasperDesign, getDataSource, response)
  }

  def getDataSource = {
    val data = this.getClass.getClassLoader.getResourceAsStream("reports/regrades.json")
    new JsonDataSource(data)
  }
}
