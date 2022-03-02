package controllers

import akka.shapeless.HList.ListCompat.::
import models.Shop
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject._

@Singleton
class ShopController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def merchantPage: Action[AnyContent] = Action { implicit request =>
    val productList = Shop.getProductList
    Ok(views.html.merchantPage(productList))
  }

  def customerPage: Action[AnyContent] = Action {
    val productList = Shop.getProductList
    Ok(views.html.customerPage(productList))
  }

  def addProducts(): Action[AnyContent] = Action { implicit request =>
    request.body.asFormUrlEncoded.map {  args=>
      val product = args("newProduct").head
      val price = args("Price").head.toInt
      Shop.addProduct(product, price)
      Redirect(routes.ShopController.merchantPage)
    }.getOrElse(Redirect(routes.ShopController.merchantPage))
  }

  def removeProduct(product: String, price: Int, offer: Option[Int], description: Option[String]): Action[AnyContent] = Action {
    Shop.removeProduct(product, price, offer, description)
    Redirect(routes.ShopController.merchantPage)
  }

  def buyProduct(product: String, price: Int, offer: Option[Int], description: Option[String]): Action[AnyContent] = Action {
    Shop.removeProduct(product, price, offer, description)
    Redirect(routes.ShopController.customerPage)
  }

//  def countdown(n: Int)(f: Int => Unit): Future[Unit] = async {
//    var i = n
//    while (i > 0) {
//      f(i)
//      await {
//        delay(1)
//      }
//      i -= 1
//    }
//  }

  def newOffer(): Action[AnyContent] = Action { implicit request =>
    request.body.asFormUrlEncoded.map {  args=>
      val newOffer = args("newOffer").head.toInt
      val offerDescription = args("offerDescription").head
      val productLabel = args("productLabel").head
      val productList = Shop.getProductList
      productList.map { product =>
        if(product._1 == productLabel) {
          product.copy(_2 = newOffer, _4 = offerDescription) :: productList
        } else {
          Nil
        }
      }
      Redirect(routes.ShopController.merchantPage)
    }.getOrElse(Redirect(routes.ShopController.merchantPage))
  }
}
