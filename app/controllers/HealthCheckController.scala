package controllers

import javax.inject.Inject

import utils.JsonUtil
import play.api.mvc._

import scala.concurrent.Future

/**
  * Created by wangchunhui on 2017/10/27.
  */
class HealthCheckController @Inject()(cc: ControllerComponents) extends BaseController(cc) {

  /**
    * 深度健康检查
    * @return
    */
  def index(): Action[AnyContent] = Action.async{ implicit request: Request[AnyContent] =>
    Future {
      Ok(JsonUtil.returnSuccess(status_reason = "success"))
    }

  }
}
