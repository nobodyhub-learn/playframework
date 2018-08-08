package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

/**
  * @author yan_h
  */
@Singleton
class TemplateController @Inject()(
                                    cc: ControllerComponents) extends AbstractController(cc) {
  def index() = Action {
    Ok(views.html.main())
  }
}
