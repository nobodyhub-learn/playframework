package controllers

import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.mvc.{AbstractController, ControllerComponents}

/**
  * @author yan_h
  * @since 2018/8/6
  */
@Singleton
class ConfigController @Inject()(
                              config: Configuration,
                              cc: ControllerComponents) extends AbstractController(cc) {
  def getConfig = Action {
    Ok(config.get[Seq[String]]("config.foo").mkString(","))
  }
}
