package controllers

import javax.inject.Singleton
import play.api.http.HttpErrorHandler
import play.api.mvc.{RequestHeader, Result, Results}

import scala.concurrent.Future

/**
  * @author yan_h
  */
@Singleton
class ErrorHandler extends HttpErrorHandler{
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Results.Status(statusCode)("A client error occurred: " + message)
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful(
      Results.InternalServerError("A server error occurred: " + exception.getMessage)
    )
  }

}
