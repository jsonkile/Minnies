package com.demo.minnies.shop.util

import com.demo.minnies.database.models.ProductCategory
import com.demo.minnies.database.models.Product
import com.demo.minnies.shared.utils.Currency
import com.demo.minnies.shared.utils.toFormattedPrice
import com.demo.minnies.shop.presentation.models.ViewProduct

/**
 * A sample products data set to prepopulate DB with
 */
val mockProducts = listOf(
    Product(
        name = "Nike Shirt",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/40/2996402/1.jpg?4649",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(12, 13),
        productCategory = ProductCategory.Top,
        price = 0.5,
        rating = 4.5
    ),

    Product(
        name = "Under Armour Hoodie",
        image = "https://underarmour.scene7.com/is/image/Underarmour/V5-1373880-012_BC?rp=standard-0pad|gridTileDesktop&scl=1&fmt=jpg&qlt=50&resMode=sharp2&cache=on,on&bgc=F0F0F0&wid=512&hei=640&size=512,640",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(12, 13),
        productCategory = ProductCategory.Top,
        price = 0.9,
        rating = 4.0,
        featured = true
    ),

    Product(
        name = "Puma Shirt",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/11/2052231/1.jpg?7897",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(12, 13),
        productCategory = ProductCategory.Top,
        price = 0.7
    ),

    Product(
        name = "Adidas Shirt",
        image = "https://assets.adidas.com/images/w_276,h_276,f_auto,q_auto,fl_lossy,c_fill,g_auto/f9867ea840304be1bc41adab00fc76b1_9366/HB7444_21_model.jpg",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(12, 13),
        productCategory = ProductCategory.Top,
        price = 0.2
    ),

    Product(
        name = "Vans Retro",
        image = "https://images.asos-media.com/products/vans-ua-old-skool-trainers-in-leopard-print-multi/203591162-1-black?wid=317&fit=constrain",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(41, 42),
        productCategory = ProductCategory.Kicks,
        price = 3.7,
        rating = 4.1
    ),

    Product(
        name = "Clark Retro",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/05/522457/1.jpg?5958",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(45, 39),
        productCategory = ProductCategory.Kicks,
        price = 5.2
    ),

    Product(
        name = "Puma Slides",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/14/528929/1.jpg?8606",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(45, 39),
        productCategory = ProductCategory.Kicks,
        price = 3.2,
        featured = true
    ),

    Product(
        name = "Under Armour Joggers",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/33/5552412/1.jpg?5907",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(38, 34),
        productCategory = ProductCategory.Shorts,
        price = 2.4
    ),

    Product(
        name = "Nike Sweat Shorts",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/41/5560331/1.jpg?5231",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(38, 34),
        productCategory = ProductCategory.Shorts,
        price = 1.8
    ),

    Product(
        name = "Nike Bike Pants",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/36/172318/1.jpg?8067",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(38, 34),
        productCategory = ProductCategory.Shorts,
        price = 2.0
    ),

    Product(
        name = "Adidas Running Shorts",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/81/9027011/1.jpg?8891",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(38, 34),
        productCategory = ProductCategory.Shorts,
        price = 2.3,
        featured = true
    ),

    Product(
        name = "Versace Prism",
        image = "https://ng.jumia.is/unsafe/fit-in/500x500/filters:fill(white)/product/64/153998/1.jpg?5815",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(12, 13),
        productCategory = ProductCategory.Accessory,
        price = 0.5,
        featured = true,
        rating = 4.3
    ),

    Product(
        name = "Nike Duffle Bag",
        image = "https://ng.jumia.is/unsafe/fit-in/500x500/filters:fill(white)/product/84/2698502/1.jpg?1344",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(12, 13),
        productCategory = ProductCategory.Accessory,
        price = 10.3,
        featured = false,
        rating = 4.8
    ),

    Product(
        name = "Balanciaga Boomers",
        image = "https://ng.jumia.is/unsafe/fit-in/300x300/filters:fill(white)/product/69/518153/1.jpg?7357",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(12, 13),
        productCategory = ProductCategory.Accessory,
        price = 8.9,
        featured = true,
        rating = 4.5
    ),

    Product(
        name = "Under Armour Blitzing Adj Cap",
        image = "https://www.tradeinn.com/h/13941/139418734/under-armour-blitzing-adj-cap.jpg",
        description = MOCK_PRODUCT_DESCRIPTION,
        sizes = listOf(12, 13),
        productCategory = ProductCategory.Accessory,
        price = 2.9,
        featured = false,
        rating = 4.2
    )
)


fun Product.toView(userCurrencyPreference: Currency): ViewProduct {

    return ViewProduct(
        name = name,
        description = description,
        formattedPrice =
        "${userCurrencyPreference.sign}${price.toFormattedPrice(userCurrencyPreference)}",
        price = price,
        sizes = sizes,
        image = image,
        productCategory = productCategory,
        featured = featured,
        rating = rating,
        id = id
    )
}