package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

/**
  * @author yan_h
  * @since 2018/8/6
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
    def index = Action {implicit request =>
      Ok(views.html.index("Your new Application is ready!"))
    }
}