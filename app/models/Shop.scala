package models

import scala.collection.mutable

object Shop {

  private var productList = mutable.Seq[List[(String, Int, Option[Int], Option[String])]](List.empty).head

  def getProductList: List[(String, Int, Option[Int], Option[String])] = {
    productList
  }

  def addProduct(label: String, price: Int): Unit = {
    productList = (label, price, None, None) :: productList
  }

  def removeProduct(label: String, price: Int, offer: Option[Int], description: Option[String]): Unit = {
    productList = productList.filterNot(l => l == (label, price, offer, description))
  }

}
