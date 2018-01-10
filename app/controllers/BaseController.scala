package controllers

import filters.{DSLog, LogUtil}
import play.api.mvc.{AbstractController, ControllerComponents, RequestHeader}

import scala.concurrent.ExecutionContext

/**
  * Created by sheep3 on 2017/10/29.
  */
abstract class BaseController(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val postfixOps: languageFeature.postfixOps = scala.language.postfixOps
  implicit val global: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
  def log(implicit request: RequestHeader): DSLog = LogUtil.log(request)
}
