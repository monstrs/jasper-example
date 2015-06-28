package ru.monstrs.jasper

import org.springframework.context.annotation.{Bean, Configuration, ComponentScan}
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.web.servlet.view.{InternalResourceViewResolver, JstlView}

@EnableWebMvc
@ComponentScan(basePackages = Array("ru.monstrs.jasper"))
@Configuration
class Config extends WebMvcConfigurerAdapter {
  @Bean
  def propertyPlaceholderConfigurer = {
    new PropertySourcesPlaceholderConfigurer()
  }
}
