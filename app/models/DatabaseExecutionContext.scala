package models

import javax.inject._

import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext

/**
 * 数据库相关开发参照 https://github.com/sheepmen/s_rank/blob/master/app/models/TopicRepository.scala
 */
@Singleton
class DatabaseExecutionContext @Inject()(system: ActorSystem) extends CustomExecutionContext(system, "database.dispatcher")
