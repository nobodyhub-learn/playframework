package controllers

import akka.util.ByteString
import javax.inject.Inject
import play.api.Logger
import play.api.http.HttpEntity
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author yan_h
  * @since 2018/8/6
  */
class HttpController @Inject()(loggingAction: LoggingAction, cc: ControllerComponents)
  extends AbstractController(cc) {

  def index = Action {
    Ok("It works!")
  }

  def hello(name: String) = Action {
    Ok(s"Hello ${name}")
  }

  def helloWorld = Action {
    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Strict(ByteString("Hello World!"), Some("text/plain"))
    )
  }

  def echo = Action { implicit request =>
    val rst = implicitMethod("Got Request: ")
    Ok(rst)
  }

  def implicitMethod(p: String)(implicit request: Request[_]): String = {
    p + request
  }

  def error = Action {
    InternalServerError("Oops")
  }

  def error2 = Action {
    Ok(s"Restult: ${1 / 0}")
  }

  def strange = Action {
    Status(1019)("Strange response type")
  }

  def redirect = Action {
    Redirect(routes.HttpController.index()).as(HTML)
  }

  def todo = TODO

  def session = Action { request =>
    Ok("Hello World!").withSession(
      request.session
        + ("userId" -> "yan_h")
        - "PLAY_SESSION"
    )
  }

  def flash = Action { implicit request =>
    Ok(views.html.index("Flash Test")).flashing("success" -> "SUCCESS")

  }

  def customAction = loggingAction {
    Ok("Hello World! Custom Action")
  }

  def customAction2 = Logging {
    Action {
      Ok("Hello World! Custom Action")
    }
  }
}

class LoggingAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    Logger.info("call from LoggingAction")
    block(request)
  }

  override protected def composeAction[A](action: Action[A]): Action[A] = Logging(action)
}

case class Logging[A](action: Action[A]) extends Action[A] {
  def apply(request: Request[A]): Future[Result] = {
    Logger.info("call from Logging")
    action(request)
  }

  override def parser: BodyParser[A] = action.parser

  override def executionContext: ExecutionContext = action.executionContext
}


