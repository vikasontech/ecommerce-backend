package com.webtutsplus.ecommerce.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "categories")
data class Category (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "category_name")
    val categoryName: @NotBlank String,
    val description: @NotBlank String,
    val imageUrl: @NotBlank String,

    // add imageURL here
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val products: Set<Product>,

)