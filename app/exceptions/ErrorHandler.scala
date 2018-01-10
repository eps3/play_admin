package exceptions

import javax.inject.Singleton

import utils.JsonUtil
import play.api.Logger
import play.api.http.ContentTypes._
import play.api.http.HttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent.Future

/**
  * Created by admin on 2017/10/12.
  */
@Singleton
class ErrorHandler extends HttpErrorHandler {
  /**
    * onClientError
    * @param request The request that caused the client error.
    * @param statusCode The error status code. Must be greater or equal to 400, and less than 500.
    * @param message The error message.
    * @return JsonError
    */
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful(
      Status(statusCode)(JsonUtil.returnError(status_code = statusCode, status_reason = "A client error occurred: " + message)).as(JSON)
    )
  }

  /**
    * onServerError
    * @param request The request that triggered the server error.
    * @param exception The server error.
    * @return JsonError
    */
  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    exception match {
      case k: KnownSystemException =>
        Future.successful(
          Ok(JsonUtil.returnError(status_reason = k.getMessage))
        )
      case _ =>
        Logger.error(s"ServerError -> ${request.host}${request.uri}", exception)
        Future.successful(
          InternalServerError(JsonUtil.returnError(status_reason = "A server error occurred: " + exception.getMessage)).as(JSON)
        )
    }
  }
}
