package com.webtutsplus.ecommerce.service

import com.webtutsplus.ecommerce.model.WishList
import com.webtutsplus.ecommerce.repository.WishListRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class WishListService(private val wishListRepository: WishListRepository) {
    fun createWishlist(wishList: WishList) {
        wishListRepository.save(wishList)
    }

    fun readWishList(userId: Int): List<WishList> {
        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId)
    }
}