package utils

/**
  * Created by sheep3 on 2017/9/22.
  */
object IdGenerator {
  // 时间基线 2017/7/7 7:7:7
  val timeBaseLine: Long = 1499382427000L
  val serverId: Int = 1
  val serverIdBits: Long = 10
  val sequenceIdBits: Long = 10
  val maxServerId: Long = ~(-1L << serverIdBits)
  val maxSequence: Long = ~(-1L << serverIdBits)
  val timeBitsOffset: Long = serverIdBits + sequenceIdBits
  val serverIdBitsOffset: Long = sequenceIdBits
  var lastTimestamp: Long = -1L
  var sequence: Long = -1L
  def getId: Long = {
    this.synchronized {
      var currentTime = System.currentTimeMillis
      if (currentTime < lastTimestamp) {
        throw new IllegalStateException("Err clock")
      }
      sequence = (sequence + 1) & maxSequence
      if (lastTimestamp == currentTime) {
        /**
          * sequence用完
          */
        if (sequence == 0) {
          currentTime = System.currentTimeMillis
          while (currentTime <= lastTimestamp) {
            currentTime = System.currentTimeMillis
          }
        }
      }
      lastTimestamp = currentTime
      ((currentTime - timeBaseLine) << timeBitsOffset) | (serverId << serverIdBitsOffset) | sequence
    }
  }
}
