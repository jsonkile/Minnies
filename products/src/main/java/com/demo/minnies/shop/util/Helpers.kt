package com.demo.minnies.shop.util

import com.demo.minnies.database.models.Category
import com.demo.minnies.database.models.Product

/**
 * A sample products data set to prepopulate DB with
 */
val mockProducts = listOf(
    Product(
        0,
        "Nike Shirt",
        "https://images.unsplash.com/photo-1606105961732-6332674f4ee6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjF8fG5pa2V8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(12, 13),
        Category.Top,
        0.5,
        rating = 4.5
    ),

    Product(
        1,
        "Under Armour Hoodie",
        "https://images.unsplash.com/photo-1559278092-640149b5a287?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8dW5kZXIlMjBhcm1vdXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(12, 13),
        Category.Top,
        0.9,
        rating = 4.0
    ),

    Product(
        2,
        "Puma Shirt",
        "https://images.unsplash.com/photo-1538406641397-91f410ec4ec2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHB1bWF8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(12, 13),
        Category.Top,
        0.7
    ),

    Product(
        3,
        "Adidas Shirt",
        "https://images.unsplash.com/photo-1511746315387-c4a76990fdce?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8YWRpZGFzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(12, 13),
        Category.Top,
        0.2
    ),

    Product(
        4,
        "Vans Retro",
        "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTB8fHNwb3J0fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(41, 42),
        Category.Kicks,
        3.7,
        rating = 4.1
    ),

    Product(
        5,
        "Clark Retro",
        "https://images.unsplash.com/photo-1529900748604-07564a03e7a6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTY2fHxzcG9ydHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(45, 39),
        Category.Kicks,
        5.2
    ),

    Product(
        6,
        "Puma Slides",
        "https://images.unsplash.com/photo-1619253341026-74c609e6ce50?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDF8fHB1bWElMjBzbGlkZXN8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(45, 39),
        Category.Kicks,
        3.2
    ),

    Product(
        7,
        "Under Armour Joggers",
        "https://images.unsplash.com/photo-1617602269951-f41db1a32e09?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8dW5kZXIlMjBhcm1vdXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(38, 34),
        Category.Shorts,
        2.4
    ),

    Product(
        8,
        "Nike Sweat Shorts",
        "https://images.unsplash.com/photo-1619474413782-35a2004d37fc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTl8fG5pa2UlMjBzaG9ydHN8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION, listOf(38, 34),
        Category.Shorts,
        1.8
    ),

    Product(
        9,
        "Nike Bike Pants",
        "https://images.unsplash.com/photo-1541694458248-5aa2101c77df?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTE3fHxzcG9ydHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION,
        listOf(38, 34),
        Category.Shorts,
        2.0
    ),

    Product(
        10,
        "Adidas Running Shorts",
        "https://images.unsplash.com/photo-1547941126-3d5322b218b0?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjR8fHNwb3J0fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION, listOf(38, 34),
        Category.Shorts,
        2.3
    ),

    Product(
        11,
        "Versace Prism",
        "https://images.unsplash.com/photo-1606105961732-6332674f4ee6?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjF8fG5pa2V8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION, listOf(12, 13),
        Category.Top,
        0.5,
        true,
        rating = 4.3
    ),

    Product(
        12,
        "Nike Plush Slippers",
        "https://images.unsplash.com/photo-1559278092-640149b5a287?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8dW5kZXIlMjBhcm1vdXJ8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION, listOf(12, 13),
        Category.Accessory,
        10.3,
        true,
        rating = 4.8
    ),

    Product(
        13,
        "Balanciaga Boomers",
        "https://images.unsplash.com/photo-1538406641397-91f410ec4ec2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHB1bWF8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60",
        MOCK_PRODUCT_DESCRIPTION, listOf(12, 13),
        Category.Accessory,
        8.9,
        true,
        rating = 4.5
    )
)