package eg.coursera.week4.pubsub

trait Subscriber {
  def handler(pub: Publisher)
}