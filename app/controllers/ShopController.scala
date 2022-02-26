package controllers

import models.Shop
import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject._

@Singleton
class ShopController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def merchantPage = Action { implicit request =>
    val productList = Shop.getProductList
    Ok(views.html.merchantPage(productList))
  }

  def customerPage = Action {
    val productList = Shop.getProductList
    Ok(views.html.customerPage(productList))
  }

  def addProducts = Action { implicit request =>
    request.body.asFormUrlEncoded.map {  args=>
      val product = args("newProduct").head
      val price = args("Price").head.toInt
      Shop.addProduct(product, price)
      Redirect(routes.ShopController.merchantPage)
    }.getOrElse(Redirect(routes.ShopController.merchantPage))
  }

  def removeProduct(product: String, price: Int) = Action {
    Shop.removeProduct(product, price)
    Redirect(routes.ShopController.merchantPage)
  }

  def buyProduct(product: String, price: Int) = Action {
    Shop.removeProduct(product, price)
    Redirect(routes.ShopController.customerPage)
  }
}
