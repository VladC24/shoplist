package models

import scala.collection.mutable

object Shop {

  private var productList = mutable.Seq[List[(String, Int)]](List.empty).head

  def getProductList = {
    productList
  }

  def addProduct(label: String, price: Int) = {
    productList = (label, price) :: productList
  }

  def removeProduct(label: String, price: Int) = {
    productList = productList.filterNot(l => l == (label, price))
  }

  def createOffer(product: String, price: Int) = ???

}
