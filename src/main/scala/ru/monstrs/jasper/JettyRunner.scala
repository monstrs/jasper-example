package ru.monstrs.jasper

import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.webapp.WebXmlConfiguration
import org.eclipse.jetty.server.Connector
import org.eclipse.jetty.server.HttpConfiguration
import org.eclipse.jetty.server.HttpConnectionFactory
import org.eclipse.jetty.server.SecureRequestCustomizer
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.SslConnectionFactory

object JettyRunner {
  def main(args: Array[String]) {
    val server = new Server(8080)

    val httpConfig = new HttpConfiguration()

    val http = new ServerConnector(server,new HttpConnectionFactory(httpConfig))  
    http.setPort(8080)
    http.setIdleTimeout(30000)

    val handler: WebAppContext = new WebAppContext
    val resourceBase = this.getClass.getClassLoader.getResource("webapp").toExternalForm

    handler.setContextPath("/")
    handler.setResourceBase(resourceBase)
    handler.setServer(server)
    server.setHandler(handler)

    try {
      server.start()
      server.join()
    } catch {
      case e: Exception => {
        e.printStackTrace()
        System.exit(1)
      }
    }
  }
}
