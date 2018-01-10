package filters

import javax.inject.Inject

import akka.stream.Materializer
import exceptions.UnknownSystemException
import utils.IdGenerator
import play.api.Logger
import play.api.libs.typedmap.TypedKey
import play.api.mvc.{Cookie, Filter, RequestHeader, Result}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by sheep3 on 2017/10/27.
  */
class LogTrackFilter @Inject()(implicit val mat: Materializer, ec: ExecutionContext) extends Filter {

  override def apply(next: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
    if (request.uri.startsWith("/file/")) {
      next(request)
    } else {
      val startTime = System.currentTimeMillis()
      val trackId = IdGenerator.getId.toString
      val newRequest = request.addAttr[String](LogUtil.LogKey, trackId)
      LogUtil.log(newRequest).info(s"=> ${request.method} ${request.host}${request.uri}")
      next(newRequest).map { result =>
        LogUtil.log(newRequest).info(s"=> exec_time: ${System.currentTimeMillis() - startTime}ms")
        result.withCookies(Cookie("logger-track-id", trackId))
      }
    }
  }
}

object LogUtil {
  val LogKey: TypedKey[String] = TypedKey[String]("LogHash")

  def log(implicit request: RequestHeader): DSLog = {
    request.attrs.get(LogUtil.LogKey) match  {
      case Some(id) =>
        DSLog(id)
      case _ => throw new UnknownSystemException("找不到trackId")
    }
  }
}

class DSLog(trackId: String) {
  def info(msg: String): Unit ={
    Logger.info(msg.withTrack)
  }

  def debug(msg: String): Unit ={
    Logger.debug(msg.withTrack)
  }

  def error(msg: String): Unit ={
    Logger.error(msg.withTrack)
  }

  def error(msg: String, exception: Throwable): Unit ={
    Logger.error(msg.withTrack, exception)
  }

  implicit class MsgWithTrack(msg: String) {
    def withTrack: String ={
      s"$trackId - $msg"
    }
  }
}
object DSLog{
  def apply(trackId: String): DSLog = new DSLog(trackId)
}
